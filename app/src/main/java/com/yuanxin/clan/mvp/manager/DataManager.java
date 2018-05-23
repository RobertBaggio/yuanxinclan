package com.yuanxin.clan.mvp.manager;


import android.support.v4.util.ArrayMap;
import android.util.Xml;

import com.yuanxin.clan.mvp.entity.Entity;
import com.yuanxin.clan.mvp.model.BaseVo;
import com.yuanxin.clan.mvp.network.NetworkManager;
import com.yuanxin.clan.mvp.network.RequestInfo;
import com.yuanxin.clan.mvp.network.ResponseInfo;
import com.yuanxin.clan.mvp.presenter.Callback;
import com.yuanxin.clan.mvp.result.CacheResult;
import com.yuanxin.clan.mvp.service.BaseService;
import com.yuanxin.clan.mvp.service.ConvertService;
import com.yuanxin.clan.mvp.utils.LogUtils;
import com.yuanxin.clan.mvp.utils.NumberUtils;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.MvpView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * 数据管理统管类，负责管理所有服务器的接口数据以及本地SD保存的数据
 *
 * @author lch
 *         date 2016/6/20.
 */
public class DataManager {
    //从本地数据库加载数据
    public static final int MODE_LOCAL = 0x1;
    //从本地服务器加载数据
    public static final int MODE_LOCAL_SERVER = 0x2;
    //从远程服务器加载数据
    public static final int MODE_REMOTE_SERVER = 0x3;

    private ArrayMap<String, BaseService<? extends Entity>> dataServices;

    private static DataManager instance;

    private DataManager() {
        initSyncServices();
    }

    private void initSyncServices() {
        // 2017/1/7 初始化所有的dataSyncConfig.xml中配置的数据处理service
        try {
            InputStream inputStream = UIUtils.getContext().getAssets().open("dataSyncConfig.xml");
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        dataServices = new ArrayMap<>();
                        break;
                    case XmlPullParser.START_TAG:
                        String name = parser.getName();
                        if ("SyncInfo".equals(name)) {
                            initService(parser);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }

                eventType = parser.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    private void initService(XmlPullParser parser) {
        String syncApi = parser.getAttributeValue(null, "syncApi");
        String serviceName = parser.getAttributeValue(null, "syncService");
        BaseService<? extends Entity> convertService = dataServices.get(syncApi);
        if (convertService != null) {
            dataServices.put(syncApi, convertService);
            return;
        }

        try {
            Class<? extends BaseService<? extends Entity>> aClass = (Class<? extends BaseService<? extends Entity>>) Class.forName(serviceName);
            convertService = aClass.newInstance();
            convertService.setSyncPeriod(NumberUtils.parseInt(parser.getAttributeValue(null, "syncPeriod"), 0));
            dataServices.put(syncApi, convertService);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static DataManager getDefault() {
        if (instance == null) {
            synchronized (DataManager.class) {
                instance = new DataManager();
            }
            return instance;
        }
        return instance;
    }

    /**
     * 检查请求是否设置了合法的缓存时间，如果是则先从缓存里面请求数据
     * 并将从缓存中加载到的数据解析成VO对象，并回调presenter的onSuccess方法。
     * 如果解析失败回调presenter的onError方法。
     *
     * @param requestInfo 请求对象
     * @param callback    回调presenter
     */
    public void loadData(final RequestInfo requestInfo, final Callback callback) {
        if (requestInfo.isNeedMockData()) {
            ResponseInfo responseInfo = new ResponseInfo(ResponseInfo.SUCCESS);
            //模拟数据需要对象的
            try {
                BaseVo dataVo = requestInfo.getDataClass().newInstance();
                dataVo.doMock();
                responseInfo.setDataVo(dataVo);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            callback.onSuccess(responseInfo);
            return;
        }

        loadData(requestInfo, requestInfo.getDataLoadMode(), callback);
    }

    /**
     * @param requestInfo
     * @param dataLoadMode 数据加载模式，分为MODE_LOCAL:本地数据库模式， MODE_LOCAL_SERVER、MODE_REMOTE_SERVER：服务器加载模式
     * @param callback
     */
    public void loadData(final RequestInfo requestInfo, int dataLoadMode, final Callback callback) {
        switch (dataLoadMode) {
            case MODE_LOCAL:
                loadDataFromLocal(requestInfo, callback);
                break;
            case MODE_LOCAL_SERVER:
            case MODE_REMOTE_SERVER:
                if (NetworkManager.isNetWorkConnected()) {
                    loadDataFromServer(requestInfo, callback);
                }

//                loadDataFromLocal(requestInfo, callback);
                break;
        }
    }

    /**
     * 从服务器加载数据
     *
     * @param requestInfo
     * @param callback
     */
    private void loadDataFromServer(RequestInfo requestInfo, Callback callback) {
        long cacheTime = requestInfo.getDataExpireTime();
        if (cacheTime > 0 && requestInfo.getRequestType() == RequestInfo.REQUEST_GET) {
            TaskManager.getDefault().post(new CacheDataRunnable(requestInfo, callback));
        } else {
            if (requestInfo.getRequestType() == RequestInfo.REQUEST_GET) {
                NetworkManager.getDefault().doGet(requestInfo, callback);
            } else {
                NetworkManager.getDefault().doPost(requestInfo, callback);
            }
        }
    }

    /**
     * 根据参数的类型增删改查数据，如果Id不为空，那么当前操作只正对一条数据。如果Id为空，jsonData不为空，那么当前操作为批量数据操作
     * 数据操作成功后把结果封装到ResponseInfo里面去，然后调用回调方法,把结果分发到主线程。
     */

    public void loadDataFromLocal(final RequestInfo requestInfo, final Callback callback) {
        if (dataServices == null) {
            return;
        }

        final ConvertService<? extends Entity> convertService = dataServices.get(requestInfo.getApi());
        if (convertService == null) {
            return;
        }

        TaskManager.getDefault().post(new Runnable() {
            @Override
            public void run() {
                ResponseInfo responseInfo = new ResponseInfo(ResponseInfo.SUCCESS);
                responseInfo.setUrl(requestInfo.getApi());
                List<? extends Entity> entities;
                try {
                    entities = convertService.doAction(requestInfo.getApi(), requestInfo.getRequestParams());
                } catch (Exception e) {
                    LogUtils.e(e);
                    return;
                }

                BaseVo dataVo;
                if (requestInfo.getDataClass() == null) {
                    dataVo = new BaseVo();
                } else {
                    try {
                        dataVo = requestInfo.getDataClass().newInstance();
                    } catch (InstantiationException e) {
                        LogUtils.e(e);
                        return;
                    } catch (IllegalAccessException e) {
                        LogUtils.e(e);
                        return;
                    }
                }

                dataVo.setQueryData(entities);
                dataVo.setDataType(BaseVo.TYPE_DB);
                responseInfo.setDataVo(dataVo);
                postCallback(callback, responseInfo);
            }
        });
    }


    /**
     * 将网络请求的结果通过主线程分发
     *
     * @param callback
     * @param responseInfo
     */
    public void postCallback(final Callback callback, final ResponseInfo responseInfo) {
        if (callback == null) {
            return;
        }

        ConvertService<? extends Entity> convertService = dataServices.get(responseInfo.getUrl());
        BaseVo dataVo = responseInfo.getDataVo();
        if (convertService != null && dataVo != null) {
            convertService.doSync(responseInfo.getUrl(), dataVo.getSyncDataList());
        }

        if (UIUtils.isRunInMainThread()) {
            if (responseInfo.getState() == ResponseInfo.SUCCESS) {
                callback.onSuccess(responseInfo);
            } else {
                callback.onError(responseInfo);
            }
            return;
        }

        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                if (responseInfo.getState() == ResponseInfo.SUCCESS) {
                    callback.onSuccess(responseInfo);
                } else {
                    callback.onError(responseInfo);
                }
            }
        });
    }

    /**
     * 重新登录
     *
     * @param callback
     * @param requestInfo
     */
    public void postRestLogin(final Callback callback, final RequestInfo requestInfo) {
        if (callback == null) {
            return;
        }

        if (UIUtils.isRunInMainThread()) {
            callback.onUNLogin(requestInfo);
            return;
        }

        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                callback.onUNLogin(requestInfo);
            }
        });
    }


    /**
     * View层对象生命周期终结，取消所有网络请求级缓存请求。
     *
     * @param detachView
     */
    public void onViewDetach(MvpView detachView) {
        NetworkManager.getDefault().cancellAll(detachView);
    }


    /**
     * APP已关闭退出，释放所有占用资源和线程
     */
    public void onApplicationDestroy() {
        dataServices.clear();
        dataServices = null;
        instance = null;
        TaskManager.getDefault().destory();
        NetworkManager.getDefault().destory();
        CacheManager.getDefault().closeCache();
    }

    private static class CacheDataRunnable implements Runnable {
        RequestInfo requestInfo;
        Callback callback;

        public CacheDataRunnable(RequestInfo requestInfo, Callback callback) {
            this.requestInfo = requestInfo;
            this.callback = callback;
        }

        @Override
        public void run() {
            String key = CacheManager.getDefault().sortUrl(requestInfo.getUrl(), requestInfo.getRequestParams());
            CacheResult cacheResult = CacheManager.getDefault().readFromCache(key, requestInfo.getDataExpireTime());

            if (cacheResult.isValid) {
                ResponseInfo responseInfo;
                try {
                    responseInfo = new ResponseInfo(ResponseInfo.SUCCESS);
                    responseInfo.setDataVo(BaseVo.parseDataVo(cacheResult.cacheData, requestInfo.getDataClass()));
                    DataManager.getDefault().postCallback(callback, responseInfo);
                } catch (Exception ex) {
                    LogUtils.e("解析缓存数据失败：" + cacheResult.cacheData);
                    responseInfo = new ResponseInfo(ResponseInfo.FAILURE);
                    DataManager.getDefault().postCallback(callback, responseInfo);
                }
            } else {
                if (requestInfo.getRequestType() == RequestInfo.REQUEST_GET) {
                    NetworkManager.getDefault().doGet(requestInfo, callback);
                } else {
                    NetworkManager.getDefault().doPost(requestInfo, callback);
                }
            }
        }
    }
}

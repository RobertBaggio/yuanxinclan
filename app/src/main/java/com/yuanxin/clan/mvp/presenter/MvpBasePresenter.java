package com.yuanxin.clan.mvp.presenter;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okserver.listener.UploadListener;
import com.lzy.okserver.upload.UploadInfo;
import com.lzy.okserver.upload.UploadManager;
import com.yuanxin.clan.mvp.manager.DataManager;
import com.yuanxin.clan.mvp.model.BaseVo;
import com.yuanxin.clan.mvp.network.ApiConfig;
import com.yuanxin.clan.mvp.network.RequestInfo;
import com.yuanxin.clan.mvp.network.ResponseInfo;
import com.yuanxin.clan.mvp.utils.LogUtils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.MvpView;

import java.io.File;
import java.io.Serializable;

import okhttp3.Response;


/**
 * @author lch
 *         date 2015/10/27.
 */
public class MvpBasePresenter extends MvpPresenter<MvpView> implements Callback {
    protected boolean needDialog = true;


    public void getData(String url, ArrayMap<String, Serializable> params) {
        getData(url, params, null);
    }


    public void getData(String url, ArrayMap<String, Serializable> params, Class<? extends BaseVo> dataClass) {
        if (isViewAttached() && needDialog) {
            getView().onLoading();
        }

        RequestInfo requestInfo = new RequestInfo(url, dataClass);
        requestInfo.setRequestType(RequestInfo.REQUEST_GET);
        requestInfo.setRequestParams(params);
        DataManager.getDefault().loadData(requestInfo, this);
    }

    public void getData(String url, Class<? extends BaseVo> dataClass) {
        getData(url, null, dataClass);
    }

    public void postData(String url, ArrayMap<String, Serializable> params) {
        postData(url, params, null);
    }

    public void postData(String url, ArrayMap<String, Serializable> params, Class<? extends BaseVo> dataClass) {
        if (isViewAttached() && needDialog) {
            getView().onLoading();
        }

        RequestInfo requestInfo = new RequestInfo(url, dataClass);
        requestInfo.setRequestType(RequestInfo.REQUEST_POST);
        requestInfo.setRequestParams(params);
        DataManager.getDefault().loadData(requestInfo, this);
    }


    public void loadData(String api, ArrayMap<String, Serializable> params) {
        final RequestInfo requestInfo = new RequestInfo(api, null);
        requestInfo.setRequestParams(params);
        DataManager.getDefault().loadDataFromLocal(requestInfo, MvpBasePresenter.this);
    }

    public void upload(final String url, ArrayMap<String, Serializable> params, final Class<? extends BaseVo> dataClass) {
        if (isViewAttached() && needDialog) {
            getView().onLoading();
        }

        final PostRequest request = OkGo.post(ApiConfig.HOST + url);

        Serializable value;
        for (String key : params.keySet()) {
            value = params.get(key);
            if (value instanceof String) {
                request.params(key, (String) params.get(key));
            } else if (value instanceof File) {
                request.params(key, (File) params.get(key));
            } else if (value instanceof Integer) {
                request.params(key, (int) params.get(key));
            } else if (value instanceof Float) {
                request.params(key, (float) params.get(key));
            } else if (value instanceof Double) {
                request.params(key, (double) params.get(key));
            } else {
                request.params(key, (boolean) params.get(key));
            }
        }

        UploadManager.getInstance().addTask(url, request, new UploadListener<BaseVo>() {
            @Override
            public void onProgress(UploadInfo uploadInfo) {
                if (isViewAttached()) {
                    getView().uploadProgress(uploadInfo.getUrl(), uploadInfo.getUploadLength(), uploadInfo.getTotalLength(), uploadInfo.getProgress(), uploadInfo.getNetworkSpeed());
                }
            }

            @Override
            public void onFinish(BaseVo baseVo) {
                if (isViewAttached()) {
                    getView().onStopLoading();
                    getView().onUploadSuccess(url, baseVo);
                }
            }

            @Override
            public void onError(UploadInfo uploadInfo, String errorMsg, Exception e) {
                ResponseInfo responseInfo = new ResponseInfo(ResponseInfo.UPLOAD_ERROR, errorMsg);
                responseInfo.setUrl(uploadInfo.getUrl());
                if (isViewAttached()) {
                    getView().onStopLoading();
                    getView().onError(responseInfo);
                }
            }

            @Override
            public BaseVo parseNetworkResponse(Response response) throws Exception {
                BaseVo baseVo = null;
                JSONObject jsonObject = JSON.parseObject(response.body().string());
                try {
                    baseVo = BaseVo.parseDataVo(jsonObject.getString("data"), dataClass);
                } catch (Exception ex) {
                    LogUtils.d(ex.getMessage());
                }

                return baseVo;
            }
        });
    }


    public void download(String url, ArrayMap<String, Serializable> params, final Class<? extends BaseVo> dataClass) {
        if (isViewAttached() && needDialog) {
            getView().onLoading();
        }
    }


    /**
     *
     */
    public void postData(String url, Class<? extends BaseVo> dataClass) {
        postData(url, null, dataClass);
    }

    /**
     * 请求成功，回调View层方法处理成功的结果
     *
     * @param responseInfo 包含的返回数据的BaseVo子类对象
     */
    @Override
    public void onSuccess(ResponseInfo responseInfo) {
        if (isViewAttached()) {
            getView().beforeSuccess();
            getView().showContentView(responseInfo.getUrl(), responseInfo.getDataVo());
            getView().onStopLoading();
        } else {
            LogUtils.e("MvpView已被销毁，onSuccess方法无法回调showContentView方法");
            LogUtils.e("url: " + responseInfo.getUrl() + "data: " + responseInfo.getDataVo());
        }
    }


    public boolean isNeedDialog() {
        return needDialog;
    }

    public void setNeedDialog(boolean needDialog) {
        this.needDialog = needDialog;
    }

    /**
     * 请求失败，回调View层的方法处理错误信息
     *
     * @param responseInfo 包含错误码和错误信息的BaseVo子类对象
     */
    @Override
    public void onError(ResponseInfo responseInfo) {
        if (isViewAttached()) {
            getView().onStopLoading();

            switch (responseInfo.getState()) {
                case ResponseInfo.FAILURE:
                case ResponseInfo.CACHE_PARSE_ERROR:
                case ResponseInfo.JSON_PARSE_ERROR:
                    ToastUtil.showWarning(UIUtils.getContext(), responseInfo.getMsg(), Toast.LENGTH_LONG);
                    break;
                case ResponseInfo.TIME_OUT:
                    ToastUtil.showWarning(UIUtils.getContext(), "网络连接不稳定，请检查网络设置", Toast.LENGTH_LONG);
                    break;
                case ResponseInfo.NO_INTERNET_ERROR:
                    ToastUtil.showWarning(UIUtils.getContext(), "没有可用的网络，请检查您的网络设置", Toast.LENGTH_LONG);
                    break;
                case ResponseInfo.SERVER_UNAVAILABLE:
                    ToastUtil.showWarning(UIUtils.getContext(), "接口不可用!", Toast.LENGTH_LONG);
                    break;
                case ResponseInfo.UN_LOGIN:
                    Context context;
                    if (getView() instanceof Context) {
                        context = (Context) getView();
                    } else {
                        context = ((Fragment) getView()).getContext();
                    }
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
                    break;
                default:
                    ToastUtil.showLong(responseInfo.getMsg());
                    break;
            }

            getView().onError(responseInfo);
        } else {
            LogUtils.e("MvpView已分离，onError方法无法回调MvpView层的方法: " + viewClassName);
        }
    }

    /**
     * 未登录，回调View层的方法处理
     *
     * @param requestInfo 包含错误码和错误信息的BaseVo子类对象
     */
    @Override
    public void onUNLogin(final RequestInfo requestInfo) {
        if (isViewAttached()) {
//            LoginPresenter loginPresenter = new LoginPresenter();
//            loginPresenter.setAfterLogin(new LoginPresenter.AfterLogin() {
//                @Override
//                public void loginSuccess() {
//                    //重新登录后要换掉sid的值
//                    requestInfo.getRequestParams().put("sid", MainApplication.getInstance().getSid());
//                    //登录成功后继续之前的操作
//                    DataManager.getDefault().loadData(requestInfo, MvpBasePresenter.this);
//                }
//            });
//            MvpView view = getView();
//            if (!loginPresenter.autoLogin()) {
//                getView().onStopLoading();
//                Activity context = null;
//                if (view instanceof Context) {
//                    context = ((Activity) view);
//                } else if (view instanceof Fragment) {
//                    context = ((Fragment) view).getActivity();
//                }
//                if (context != null) {
//                    context.startActivity(new Intent(context, LoginActivity.class));
//                    //context.finish();
//                }
//            }
        } else {
            LogUtils.e("MvpView已分离，onStop方法无法回调MvpView层的方法");
        }
    }

    @Override
    public void detachView() {
        super.detachView();
        //取消默认的还未完成的请求
        DataManager.getDefault().onViewDetach(getView());
    }
}

package com.yuanxin.clan.mvp.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.lzy.okgo.request.PostRequest;
import com.yuanxin.clan.BuildConfig;
import com.yuanxin.clan.mvp.manager.CacheManager;
import com.yuanxin.clan.mvp.manager.DataManager;
import com.yuanxin.clan.mvp.model.BaseVo;
import com.yuanxin.clan.mvp.presenter.Callback;
import com.yuanxin.clan.mvp.utils.LogUtils;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.MvpView;

import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.SocketTimeoutException;
import java.util.Map;

import okhttp3.Call;

/**
 * @author lch
 *         date 2016/5/30.
 */
public class NetworkManager {

    private static NetworkManager instance;

    private NetworkManager() {

    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                instance = new NetworkManager();
            }
            return instance;
        }
        return instance;
    }


    /**
     * 执行加载数据,如果有做数据缓存，先从缓存里面读取数据,
     * 如果缓存数据有效，返回缓存数据，如果缓存失效，重新从
     * 网路请求数据，并将数据缓存到本地SD卡,记录缓存写入时间
     *
     * @param callback 数据加载成功后的回调方法
     */
    public void doGet(RequestInfo requestInfo, final Callback callback) {
        if (!isNetWorkConnected()) {
            ResponseInfo responseInfo = new ResponseInfo(ResponseInfo.NO_INTERNET_ERROR);
            callback.onError(responseInfo);
            return;
        }

        GetRequest getRequest = OkGo.get(requestInfo.getUrl());

        ArrayMap<String, Serializable> params = requestInfo.getRequestParams();
        if (params != null) {
            for (Map.Entry<String, Serializable> entry : params.entrySet()) {
                getRequest.params(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        getRequest.execute(new ResponseCallback(callback, requestInfo));
    }


    /**
     * 执行加载数据,如果有做数据缓存，先从缓存里面读取数据,
     * 如果缓存数据有效，返回缓存数据，如果缓存失效，重新从
     * 网路请求数据，并将数据缓存到本地SD卡,记录缓存写入时间
     *
     * @param callback 数据加载成功后的回调方法
     */
    public void doPost(RequestInfo requestInfo, final Callback callback) {
        if (!isNetWorkConnected()) {
            ResponseInfo responseInfo = new ResponseInfo(ResponseInfo.NO_INTERNET_ERROR);
            callback.onError(responseInfo);
            return;
        }

        PostRequest postRequest = setParam(requestInfo.getRequestParams(), OkGo.post(requestInfo.getUrl()));

        postRequest.execute(new ResponseCallback(callback, requestInfo));
    }


    public void upload(String url, ArrayMap<String, Serializable> params, FileCallback callback) {
        if (params == null) {
            return;
        }

        setParam(params, OkGo.post(url)).execute(callback);
    }

    private PostRequest setParam(ArrayMap<String, Serializable> params, PostRequest post) {
        if (params == null) {
            return post;
        }

        for (Map.Entry<String, Serializable> entry : params.entrySet()) {
            if (entry.getValue() instanceof File) {
                post.params(entry.getKey(), (File) entry.getValue());
            } else {
                post.params(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }

        return post;
    }

    public void download(String url, ArrayMap<String, Serializable> params, FileCallback callback) {
        if (params == null) {
            return;
        }
        setParam(params, OkGo.post(url)).execute(callback);
    }


    /**
     * 检查网络是否已经连接
     *
     * @return
     */
    public static boolean isNetWorkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) UIUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return mNetworkInfo != null && mNetworkInfo.isConnected();
    }

    public void cancellAll(MvpView view) {
//        OkGo.getInstance().cancelTag(view);
    }

    public void destory() {
        instance = null;
    }

    class ResponseCallback extends StringCallback {
        private Callback callback;
        private String filePath;
        private RequestInfo requestInfo;

        public ResponseCallback(Callback callback, RequestInfo requestInfo) {
            this.callback = callback;
            this.requestInfo = requestInfo;
        }

        public ResponseCallback(Callback callback, RequestInfo requestInfo, String filePath) {
            this.callback = callback;
            this.requestInfo = requestInfo;
            this.filePath = filePath;
        }

        @Override
        public void onError(Call call, okhttp3.Response response, Exception error) {
            ResponseInfo responseInfo;
            if (error instanceof SocketTimeoutException) {
                responseInfo = new ResponseInfo(ResponseInfo.TIME_OUT);
            } else {
                responseInfo = new ResponseInfo(ResponseInfo.FAILURE);
                responseInfo.setMsg("连接服务器失败!");
                responseInfo.setErrorObject(error);
                if (response != null) {
                    responseInfo.setUrl(response.request().url().url().getPath());
                }
            }

            DataManager.getDefault().postCallback(callback, responseInfo);
        }


        private void dispatchJsonResult(String response, String url, String type) {
            ResponseInfo responseInfo;
            try {
                JSONObject jsonObject = JSON.parseObject(response);
                responseInfo = new ResponseInfo(parseResultStatus(jsonObject.getString("state")), jsonObject.getString("msg"));
                responseInfo.setResponseType(type);
                url = url.substring(1, url.length());
                responseInfo.setUrl(url);
                String data = jsonObject.getString("rows");

                if (BuildConfig.DEBUG) {
                    LogUtils.d("请求URL: " + url + ",msg: " + responseInfo.getMsg() + ", 接口返回数据：" + data);
                }
                //没有登录
                if (responseInfo.getState() == ResponseInfo.UN_LOGIN) {
                    DataManager.getDefault().postRestLogin(callback, requestInfo);
                    return;
                }

                if (responseInfo.getState() != ResponseInfo.SUCCESS) {
                    DataManager.getDefault().postCallback(callback, responseInfo);
                    return;
                }

                BaseVo baseVo = BaseVo.parseDataVo(data, requestInfo.getDataClass());
                if (responseInfo.getState() != ResponseInfo.SUCCESS && baseVo == null) {
                    responseInfo.setState(ResponseInfo.JSON_PARSE_ERROR);
                    responseInfo.setMsg("请求结果数据解析失败！");
                }

                responseInfo.setDataVo(baseVo);
                DataManager.getDefault().postCallback(callback, responseInfo);
                //缓存数据
                if (requestInfo.getDataExpireTime() > 0 && !TextUtils.isEmpty(data)) {
                    String key = CacheManager.getDefault().sortUrl(requestInfo.getUrl(), requestInfo.getRequestParams());
                    CacheManager.getDefault().writeToCache(key, data);
                }

            } catch (JSONException ex) {
                responseInfo = new ResponseInfo(ResponseInfo.JSON_PARSE_ERROR);
                LogUtils.e(ex);
                responseInfo.setMsg(ex.getMessage());
                DataManager.getDefault().postCallback(callback, responseInfo);
            } catch (Exception e) {
                LogUtils.e("数据处理异常，原始数据：" + response);
                LogUtils.e(e);
                responseInfo = new ResponseInfo(ResponseInfo.FAILURE);
                responseInfo.setResponseType(type);
                responseInfo.setMsg("数据处理异常!");
                DataManager.getDefault().postCallback(callback, responseInfo);
            }
        }


        @Override
        public void onSuccess(String content, Call call, okhttp3.Response response) {
            ResponseInfo responseInfo;
            if (response.isSuccessful()) {
                String type = response.body().contentType().subtype();
                if (type.equals("json")) {
                    dispatchJsonResult(content, response.request().url().url().getPath(), type);
                } else {
                    responseInfo = new ResponseInfo(ResponseInfo.FAILURE);
                    responseInfo.setResponseType(type);
                    responseInfo.setUrl(response.request().url().url().getPath());
                    responseInfo.setMsg("无法解析请求结果");
                    try {
                        responseInfo.setRawData(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (BuildConfig.DEBUG) {
                        LogUtils.e("无法识别的请求结果, 类型：" + type + ", url: " + responseInfo.getUrl() + ", body: " + responseInfo.getRawData());
                    }
                    DataManager.getDefault().postCallback(callback, responseInfo);
                }
            } else {
                responseInfo = new ResponseInfo(ResponseInfo.FAILURE);
                responseInfo.setState(ResponseInfo.SERVER_UNAVAILABLE);
                DataManager.getDefault().postCallback(callback, responseInfo);
            }
        }
    }

    public int parseResultStatus(String status) {
        if ("success".equals(status)) {
            return ResponseInfo.SUCCESS;
        } else if ("failure".equals(status)) {
            return ResponseInfo.FAILURE;
        } else if ("relogin".equals(status)) {
            return ResponseInfo.UN_LOGIN;
        }
        return ResponseInfo.FAILURE;
    }
}

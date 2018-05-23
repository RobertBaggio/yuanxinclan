package com.yuanxin.clan.mvp.presenter;


import com.yuanxin.clan.mvp.network.RequestInfo;
import com.yuanxin.clan.mvp.network.ResponseInfo;

public interface Callback {

    /**
     * 请求成功回调方法
     */
    void onSuccess(ResponseInfo resoponseInfo);

    /**
     * 请求失败回调方法
     */
    void onError(ResponseInfo resoponseInfo);

    /**
     * 未登录回调方法
     */
    void onUNLogin(RequestInfo requestInfo);
}

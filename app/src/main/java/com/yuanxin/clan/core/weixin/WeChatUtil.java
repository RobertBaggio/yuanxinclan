package com.yuanxin.clan.core.weixin;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.LoadingDialog;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/6 0006 11:17
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class WeChatUtil {

    private final String TAG = "WeChatUtil";

    private final String SCOPE = "snsapi_userinfo";

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;


    //第三方程序发送时用来标识其请求的唯一性的标志，由第三方程序调用sendReq时传入，由微信终端回传，state字符串长度不能超过1K
    private String strState;

    /**
     * 微信登陆请求响应
     */
    private OnWeChatLoginListener onWeChatLoginListener;

    /**
     * 第三方登陆响应
     */
    private ThirdLoginListener thirdLoginListener;

    private LoadingDialog loadingDialog;

    private static WeChatUtil INSTANCE;

    public static WeChatUtil getInstance() {
        return INSTANCE;
    }

    public static WeChatUtil getInstance(final Context mContext, final ThirdLoginListener thirdLoginListener) {
        INSTANCE = new WeChatUtil(mContext, thirdLoginListener);
        return INSTANCE;
    }

    private WeChatUtil(final Context mContext, final ThirdLoginListener thirdLoginListener) {
        loadingDialog = new LoadingDialog(mContext);
        this.thirdLoginListener = thirdLoginListener;
        strState = mContext.getPackageName() + String.valueOf(System.currentTimeMillis());

        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(mContext, Constants.APP_ID, false);
        api.registerApp(Constants.APP_ID);

        onWeChatLoginListener = new OnWeChatLoginListener() {
            @Override
            public void onSuccess(String code, String token) {
                WeChatTask weChatTask = new WeChatTask(mContext, code, token, thirdLoginListener);
                weChatTask.getWeChatInfo();
            }
        };
    }


    public void login() {
        if (!api.isWXAppInstalled()) {
            ToastUtil.showToastShort(R.string.error_third_no_wechat);
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = SCOPE;
        req.state = strState;
        api.sendReq(req);
        loadingDialog.show();
    }

    /**
     * 设置反馈
     *
     * @param iwxapiEventHandler
     * @param intent
     */
    public void setHandlerIntent(Intent intent, IWXAPIEventHandler iwxapiEventHandler) {
        api.handleIntent(intent, iwxapiEventHandler);
        loadingDialog.dismiss();
        if (isWeChatSuccess(intent) && onWeChatLoginListener != null) {
            onWeChatLoginListener.onSuccess(getCode(intent), getToken(intent));
            Log.v(TAG, intent.getExtras().toString());
        }
    }

    public String getStrState() {
        return strState;
    }


    /**
     * 获取返回code
     *
     * @param intent
     * @return
     */
    protected String getCode(Intent intent) {
        String url = intent.getStringExtra("_wxapi_sendauth_resp_url");
        if (TextUtils.isEmpty(url))
            return null;
        String start = Constants.APP_ID + "://oauth?code=";
        String end = "&state=" + this.getStrState();
        String code = url.substring(url.indexOf(start) + start.length(), url.indexOf(end));
        return code;
    }

    private String getToken(Intent intent) {
        return intent.getStringExtra("_wxapi_sendauth_resp_token");
    }

    /**
     * 是否登陆成功
     *
     * @param intent
     * @return
     */
    private boolean isWeChatSuccess(Intent intent) {
        return intent.getIntExtra("_wxapi_baseresp_errcode", -1) == BaseResp.ErrCode.ERR_OK;
    }

    /**
     * 销毁
     */
    public static void destroy() {
        INSTANCE = null;
    }

}

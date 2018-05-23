package com.yuanxin.clan.core.weixin;

import android.content.Context;
import android.os.AsyncTask;

import com.yuanxin.clan.core.util.LoadingDialog;

import org.json.JSONObject;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/6 0006 11:20
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class WeChatTask {

    private Context mContext;

    private GetWeChatInfo getWechatInfo;

    private LoadingDialog loadingDialog;

    private String strCode;
    private String strToken;

    private ThirdLoginListener thirdLoginListener;

    public WeChatTask(Context mContext, String strCode, String strToken, ThirdLoginListener thirdLoginListener) {
        this.mContext = mContext;
        this.strCode = strCode;
        this.strToken = strToken;
        this.thirdLoginListener = thirdLoginListener;
        loadingDialog = new LoadingDialog(mContext);
    }

    public void getWeChatInfo() {
        if (getWechatInfo != null && getWechatInfo.getStatus() != AsyncTask.Status.FINISHED)
            getWechatInfo.cancel(true);
        loadingDialog.show();
        getWechatInfo = new GetWeChatInfo();
        getWechatInfo.execute();

    }

    private class GetWeChatInfo extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            return HttpWeChat.sendRequest(mContext, strCode);
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonResponse = new JSONObject(s);
                String strWeChatID = jsonResponse.getString("openid");
                String strExpiresIn = jsonResponse.getString("expires_in");
                if (thirdLoginListener != null)
                    thirdLoginListener.onComplete("WX", strWeChatID, strToken, strExpiresIn);
            } catch (Exception e) {
            }
            loadingDialog.dismiss();
            super.onPostExecute(s);
        }
    }
}

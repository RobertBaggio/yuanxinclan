package com.yuanxin.clan.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanxin.clan.core.weixin.Constants;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/1 0001 18:00
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
//    private PayResultListener payResultListener = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        payResultListener = ((PayResultListener) WXPayEntryActivity.this);
        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //回调结果
            Log.v("lgq","微信支付成功。onResp。"+resp.errCode);
            if (resp.errCode == 0) {
                Log.v("lgq","微信支付成功。onResp==0。"+resp.errCode);

//                BaseApplication.isPay = true;//设置付完成
//                payResultListener.onSuccess();
                Toast.makeText(WXPayEntryActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
//                if (BaseApplication.pay_Flag == 1) {
//                    RechargeActivity.updateData();
//                } else if (BaseApplication.pay_Flag == 2) {
//                    UpgradePayActivity.updateData();
//                } else if (BaseApplication.pay_Flag == 3) {
//                    BuyPeachActivity.updateData();
//                }
            } else if (resp.errCode == -2) {
                Toast.makeText(WXPayEntryActivity.this, "支付取消", Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

}

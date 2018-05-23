/************************************************************
 * * Hyphenate CONFIDENTIAL
 * __________________
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * NOTICE: All information contained herein is, and remains
 * the property of Hyphenate Inc..
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Hyphenate Inc.
 */
package com.yuanxin.clan.core.receiver;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.hyphenate.EMCallBack;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.MainActivity;
import com.yuanxin.clan.core.activity.MyOrderActivity;
import com.yuanxin.clan.core.activity.MySellOrderActivity;
import com.yuanxin.clan.core.app.AppManager;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.event.AgreeRefundEvent;
import com.yuanxin.clan.core.event.BusinessMsgEvent;
import com.yuanxin.clan.core.event.ChatContactEvent;
import com.yuanxin.clan.core.event.DeliverGoodsEvent;
import com.yuanxin.clan.core.event.FriendChangeEvent;
import com.yuanxin.clan.core.event.LoginEvent;
import com.yuanxin.clan.core.event.RefundEvent;
import com.yuanxin.clan.core.event.RefundSuccessEvent;
import com.yuanxin.clan.core.event.TakeDeliverGoodsEvent;
import com.yuanxin.clan.core.event.WaitingDeliverGoodsEvent;
import com.yuanxin.clan.core.huanxin.BusinessMessage;
import com.yuanxin.clan.core.huanxin.BusinessMessageDao;
import com.yuanxin.clan.core.huanxin.BusinessMsgActivity;
import com.yuanxin.clan.core.huanxin.BuyerMessage;
import com.yuanxin.clan.core.huanxin.BuyerMessageDao;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.huanxin.SellerMessage;
import com.yuanxin.clan.core.huanxin.SellerMessageDao;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.JPushUtil;
import com.yuanxin.clan.core.util.JpushCode;
import com.yuanxin.clan.core.util.Logger;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.jpush.android.api.JPushInterface;

import static com.yuanxin.clan.mvp.utils.UIUtils.getResources;

/**
 *
 */
public class MyJPushReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        SellerMessageDao sellerMessageDao = new SellerMessageDao(context);
        BuyerMessageDao buyerMessageDao = new BuyerMessageDao(context);
        BusinessMessageDao businessMessageDao = new BusinessMessageDao(context);
        try {
            Bundle bundle = intent.getExtras();
            String action = intent.getAction();
            Logger.d("[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
//            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            String param = bundle.getString(JPushInterface.EXTRA_EXTRA);
            String extra_param = FastJsonUtils.parseObject(param).getString(JpushCode.EXTRA_KEY);
            String data = FastJsonUtils.parseObject(extra_param).getString("data");
            int code = 0;
            try {
                code = Integer.valueOf(FastJsonUtils.parseObject(extra_param).getIntValue(JpushCode.CODE_KEY));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                Logger.d("[MyReceiver] 接收Registration Id : " + regId);
                //send the Registration Id to your server...
                EventBus.getDefault().post(new LoginEvent(regId));
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction()) ||
                JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                    Logger.d("[MyReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                switch (code) {
                    case JpushCode.DELETE_FRIEND:
                        processDeleteFriend(context, data);
                        break;
                    case JpushCode.ADD_FRIEND:
                        /// 添加好友
                        break;
                    case JpushCode.EP_DELETE_FRIEND:
                        /// 删除群成员
                        break;
                    case JpushCode.LOG_OUT:
//                        logOut(context);
                        Logger.e("jpush log out");
//                        if (!UserNative.readIsLogin()) {
//                            break;
//                        }
//                        EventBus.getDefault().post(new OutLoginByJpushEvent());
                        break;
                    case JpushCode.ORDER_REFUNDING:
                        //申请退款
                        EventBus.getDefault().post(new RefundEvent(action, data, code));
                        // 此处SellerMessage 需要初始化
                        sellerMessageDao.saveMessage(new SellerMessage());
                        break;
                    case JpushCode.ORDER_SEND_GODDS:
                        ///发货
                        EventBus.getDefault().post(new DeliverGoodsEvent(action, data, code));
                        buyerMessageDao.saveMessage(new BuyerMessage());
                        break;
                    case JpushCode.ORDER_SUCCESS:
                        ///收货成功
                        EventBus.getDefault().post(new TakeDeliverGoodsEvent(action, data, code));
                        // 此处SellerMessage 需要初始化
                        sellerMessageDao.saveMessage(new SellerMessage());
                        break;
                    case JpushCode.ORDER_PENDING_GOODS:
                        ///待发货
                        EventBus.getDefault().post(new WaitingDeliverGoodsEvent(action, data, code));
                        buyerMessageDao.saveMessage(new BuyerMessage());
                        break;
                    case JpushCode.ORDER_REFUND:
                        ///同意退款
                        EventBus.getDefault().post(new AgreeRefundEvent(action, data, code));
                        buyerMessageDao.saveMessage(new BuyerMessage());
                        break;
                    case JpushCode.ORDER_REFUNDED:
                        ///退款返回
                        EventBus.getDefault().post(new RefundSuccessEvent(action, data, code));
                        buyerMessageDao.saveMessage(new BuyerMessage());

//                        EventBus.getDefault().post(new BuyerMsgEvent(action, data, code));
//                        BuyerMessage buyerMessage = FastJsonUtils.parseObject(data, BuyerMessage.class);
//                        buyerMessage.setId(String.valueOf(buyerMessage.getBusinessMsgId()));
//                        if (businessMessageDao.exist(businessMessage)) {
//                            //                        context.startActivity(new Intent(context, MyOrderActivity.class));
//                        } else {
//                            businessMessageDao.saveMessage(businessMessage);
//                        }

                        break;
                    case JpushCode.BUSINESS_MSG:
                        /**
                         "code":400,
                         "data":{
                         "businessAreaLogo":"upload/images/image/20170905170532183.3.726-_750632d11013430da2f13d30130a59db.jpeg",
                         "businessAreaNm":"圆心商圈",
                         "businessMsgContent":"通天塔",
                         "businessMsgId":30,
                         "businessMsgReadId":28,
                         "businessMsgTitle":"通天塔",
                         "createDt":"2017-10-31 10:31:58.0",
                         "createId":1439,
                         "createNm":"林浩杰",
                         "delFlg":1,
                         "msgRead":0,
                         "type":3,
                         "userId":1313
                         }
                         * **/
                        //商圈消息
                        EventBus.getDefault().post(new BusinessMsgEvent(action, data, code));
                        Logger.e(FastJsonUtils.parseObject(data).toString());
                        BusinessMessage businessMessage = FastJsonUtils.parseObject(data, BusinessMessage.class);
                        businessMessage.setId(String.valueOf(businessMessage.getBusinessMsgId()));
                        businessMessageDao.saveMessage(businessMessage);
                        break;

                }
            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                Logger.d("[MyReceiver] 用户点击打开了通知");
                switch (code) {
                    case JpushCode.DELETE_FRIEND:
                        processDeleteFriend(context, data);
                        break;
                    case JpushCode.ADD_FRIEND:
                        /// 添加好友
                        break;
                    case JpushCode.EP_DELETE_FRIEND:
                        /// 删除群成员
                        break;
                    case JpushCode.LOG_OUT:
//                        logOut(context);
                        Logger.e("jpush log out");
//                        if (!UserNative.readIsLogin()) {
//                            break;
//                        }
//                        EventBus.getDefault().post(new OutLoginByJpushEvent());
                        break;
                    case JpushCode.ORDER_REFUNDING:
                        //申请退款
                        context.startActivity(new Intent(context, MySellOrderActivity.class));
                        break;
                    case JpushCode.ORDER_SEND_GODDS:
                        ///发货
                        context.startActivity(new Intent(context, MyOrderActivity.class));
                        break;
                    case JpushCode.ORDER_SUCCESS:
                        ///收货成功
                        context.startActivity(new Intent(context, MySellOrderActivity.class));
                        break;
                    case JpushCode.ORDER_PENDING_GOODS:
                        ///待发货
                        context.startActivity(new Intent(context, MyOrderActivity.class));
                        break;
                    case JpushCode.ORDER_REFUND:
                        ///同意退款
                        context.startActivity(new Intent(context, MyOrderActivity.class));
                        break;
                    case JpushCode.ORDER_REFUNDED:
                        ///退款返回
                        context.startActivity(new Intent(context, MyOrderActivity.class));
                        break;
                    case JpushCode.BUSINESS_MSG:
                        /**
                         "code":400,
                         "data":{
                         "businessAreaLogo":"upload/images/image/20170905170532183.3.726-_750632d11013430da2f13d30130a59db.jpeg",
                         "businessAreaNm":"圆心商圈",
                         "businessMsgContent":"通天塔",
                         "businessMsgId":30,
                         "businessMsgReadId":28,
                         "businessMsgTitle":"通天塔",
                         "createDt":"2017-10-31 10:31:58.0",
                         "createId":1439,
                         "createNm":"林浩杰",
                         "delFlg":1,
                         "msgRead":0,
                         "type":3,
                         "userId":1313
                         }
                         * **/
                        //商圈消息
                        Intent i = new Intent(context, BusinessMsgActivity.class);
                        i.putExtras(bundle);
                        //i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
                        context.startActivity(i);
                        break;

                }

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                Logger.d("[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if(JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                Logger.w("[MyReceiver]" + intent.getAction() +" connected state change to "+connected);
            } else {
                Logger.d("[MyReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            }else if(key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)){
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    Logger.i("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it =  json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " +json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    Logger.e("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

    //send msg to MainActivity
    private void processCustomMessage(Context context, Bundle bundle) {
        // 此处可更新UI
        if (MainActivity.isForeground) {
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//            Intent msgIntent = new Intent(MainActivity.MESSAGE_RECEIVED_ACTION);
//            msgIntent.putExtra(MainActivity.KEY_MESSAGE, message);
//            if (!ExampleUtil.isEmpty(extras)) {
//                try {
//                    JSONObject extraJson = new JSONObject(extras);
//                    if (extraJson.length() > 0) {
//                        msgIntent.putExtra(MainActivity.KEY_EXTRAS, extras);
//                    }
//                } catch (JSONException e) {
//
//                }
//
//            }
//            LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
        }
    }

    private void processDeleteFriend(Context context, String param) {
        // 此处可更新UI
        ChatContactEvent chatContactEvent = FastJsonUtils.parseObject(param, ChatContactEvent.class);
        EventBus.getDefault().post(new FriendChangeEvent());
        // extras 删除好友的电话
        EventBus.getDefault().post(chatContactEvent);

//        EventBus.getDefault().post(new ChatGroupEvent());
    }
    private void logOut(Context context){
        JPushInterface.deleteAlias(context, JPushUtil.sequence);
        UserNative.saveIsLogin(false);
        UserNative.clearData();
        AppManager.getAppManager().AppExit(context);
        //环信退出
        final ProgressDialog pd = new ProgressDialog(context);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoHelper.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {

            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {

            }
        });
    }
}

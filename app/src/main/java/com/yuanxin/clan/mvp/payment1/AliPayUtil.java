package com.yuanxin.clan.mvp.payment1;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.progress.ProgressDialogHandler;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * ProjectName: yuanxinclan
 * Describe: 支付宝支付
 * Author: xjc
 * Date: 2017/6/16 0016 15:29
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class AliPayUtil {

    private Context context;
    private ProgressDialogHandler mProgressDialogHandler;
    private PayResultListener payResultListener = null;
    private String orderid;

    public AliPayUtil(Context context) {
        this.context = context;
        this.mProgressDialogHandler = new ProgressDialogHandler(context, null, true);
        if (context instanceof PayResultListener) {
            payResultListener = ((PayResultListener) context);
        }
    }

    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    /**
     * 调起支付，传签名加密字符串
     *
     * @param signParamsInfo
     */
    public void doPay(String signParamsInfo) {
        DoPaymentTask paymentTask = new DoPaymentTask();
        paymentTask.execute(signParamsInfo);
    }

    private class DoPaymentTask extends AsyncTask<String, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(String... params) {
            // 构造PayTask 对象
            PayTask alipay = new PayTask((Activity) context);
            // 调用支付接口，获取支付结果
            Map<String, String> result = alipay.payV2(params[0], true);
            return result;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult(result);
            /**
             对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            Log.i("支付宝支付响应状态", resultStatus);
            // 判断resultStatus 为9000则代表支付成功
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                verifyPay();
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                // 判断resultStatus 为非“9000”则代表可能支付失败
                // “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                if (TextUtils.equals(resultStatus, "8000")) {
                    if (payResultListener != null) {
                        payResultListener.onProcess();
                    }
                } else {
                    // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                    if (payResultListener != null) {
                        payResultListener.onFail();
                    }
                }
//                9000	订单支付成功
//                8000	正在处理中，支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//                4000	订单支付失败
//                5000	重复请求
//                6001	用户中途取消
//                6002	网络连接出错
//                6004	支付结果未知（有可能已经支付成功），请查询商户订单列表中订单的支付状态
//                其它	其它支付错误
            }
        }
    }

    /**
     * 验证是否支付成功
     */
    public void verifyPay() {
        showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = Url.getpayNotice;
        RequestParams params = new RequestParams();
        params.put("orderNumber",orderid);
        params.put("userid",UserNative.getId());
        params.put("sign",1);
        params.put("key", UserNative.getAesKes());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(context, "网络连接异常", Toast.LENGTH_SHORT);
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                dismissProgressDialog();
                try {
                    JSONObject object = new JSONObject(s);
                    Log.i("lgq","支付宝支付成功。。。"+s);
                    if (object.getString("success").equals("true")) {
                        if (payResultListener != null) {
                            payResultListener.onSuccess();
                        }
                    } else {
                        ToastUtil.showWarning(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    /**
     * 通过订单id获取签名加密的字符串，并去支付
     *
     * @param orderNo
     */
    public void getSignToPay(String orderNo) {
        orderid = orderNo;
        showProgressDialog();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = Url.getAliSignPayInfo;
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNo);//订单id
        params.put("userId", UserNative.getId());
        params.put("key", UserNative.getAesKes());
        params.put("foreign", 1);
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(context, "网络连接异常", Toast.LENGTH_SHORT);
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                dismissProgressDialog();
                Log.i("lgq","通过订单id获取签名加密的字符串.////==="+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String sign = object.getString("data");
                        doPay(sign);
                    } else {
                        ToastUtil.showWarning(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

}

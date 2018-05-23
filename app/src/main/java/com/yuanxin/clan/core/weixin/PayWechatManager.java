package com.yuanxin.clan.core.weixin;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/1 0001 17:40
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Xml;

import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanxin.clan.mvp.payment.weixin.MD5;

import org.xmlpull.v1.XmlPullParser;

import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 微信支付调起工具
 * Created by William on 2016/6/19.
 */
public class PayWechatManager {

    private final String TAG = "PayWechatManager";

    private Context context;

    // APPID
    public String APP_ID;

    // 商户号
    private String MCH_ID;

    // API密钥，在商户平台设置
    private String API_KEY;

    private String orderOn;

    private String productfeeName;

    private String price;

    private String orderType;

    // 支付结果回调(通知后台服务器)
    private String callBackUrl;

    public PayWechatManager(Context context) {
        this.context = context;
    }

    /**
     * @param APP_ID
     * @param MCH_ID
     * @param API_KEY
     * @param orderOn
     * @param productfeeName
     * @param price
     * @param callBackUrl
     * @param orderType      //附加参数，不传为null。不能为""
     */
    public void toWeChatPay(String APP_ID, String MCH_ID, String API_KEY, String orderOn, String productfeeName, String price, String callBackUrl, String orderType) {
        this.APP_ID = APP_ID;
        this.MCH_ID = MCH_ID;
        this.API_KEY = API_KEY;
        this.orderOn = orderOn;
        this.productfeeName = productfeeName;
        this.price = price;
        this.callBackUrl = callBackUrl;
        this.orderType = orderType;// 附加数据
        GetPrepayIdTask getPrepayId = new GetPrepayIdTask();
        getPrepayId.execute();
    }

    /**
     * 异步任务，提交订单(发起支付)
     */
    class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected void onPreExecute() {
            // ui.showLoadingDialog();
        }

        @Override
        protected Map<String, String> doInBackground(Void... params) {
            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");

            // 创建微信订单信息
            String entity = createWeChatOrder();

            // 生成微信订单
            byte[] buf = HttpWxUtile.httpPost(url, entity);
            // 这里容易出错误,错误信息请见：https://pay.weixin.qq.com/wiki/doc/api/app.php?chapter=9_3&index=5

            String content = new String(buf);
            Map<String, String> xml = decodeXml(content);
            return xml;
        }

        @Override
        protected void onPostExecute(Map<String, String> result) {
            /** 用于存储支付信息的StringBuffer(用于打印订单信息) */
            StringBuffer sbInfo = new StringBuffer();
            sbInfo.append("prepay_id\n" + result.get("prepay_id") + "\n\n");
            createPayReq(result, sbInfo);
        }

    }

    /**
     * 根据您的订单信息 生成 微信产品支付订单信息
     */
    private String createWeChatOrder() {
        StringBuffer xml = new StringBuffer();
        try {
            String nonceStr = genNonceStr();
            xml.append("</xml>");
            List<KeyValue> packageParams = new LinkedList<KeyValue>();
            packageParams.add(new KeyValue("appid", APP_ID));
            packageParams.add(new KeyValue("attach", orderType));
            packageParams.add(new KeyValue("body", productfeeName));
            packageParams.add(new KeyValue("mch_id", MCH_ID));
            packageParams.add(new KeyValue("nonce_str", nonceStr));
            packageParams.add(new KeyValue("notify_url", callBackUrl));
            packageParams.add(new KeyValue("out_trade_no", orderOn));
            packageParams.add(new KeyValue("spbill_create_ip", "127.0.0.1"));
            packageParams.add(new KeyValue("total_fee", getTotalFee(price)));
            // packageParams.add(new KeyValue("total_fee", price));
            packageParams.add(new KeyValue("trade_type", "APP"));
            String sign = genPackageSign(packageParams);
            packageParams.add(new KeyValue("sign", sign));
            String xmlstring = toXml(packageParams);

            return new String(xmlstring.toString().getBytes(), "ISO8859-1");
            // return xmlstring;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * doingBackground
     */
    private Map<String, String> decodeXml(final String content) {
        try {
            Map<String, String> xml = new HashMap<String, String>();
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(new StringReader(content));
            int event = parser.getEventType();
            while (event != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (event) {
                    case XmlPullParser.START_DOCUMENT:

                        break;
                    case XmlPullParser.START_TAG:

                        if ("xml".equals(nodeName) == false) {
                            // 实例化student对象
                            xml.put(nodeName, parser.nextText());
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                }
                event = parser.next();
            }

            return xml;
        } catch (Exception e) {

        }
        return null;

    }

    // 将totalfee从单位(元)装换成整数的(分)
    private String getTotalFee(String total_fee) {
        total_fee = Double.valueOf(total_fee) * 100 / 1 + "";
        total_fee = total_fee.substring(0, total_fee.indexOf("."));
        return total_fee;
    }

    private String toXml(List<KeyValue> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");
            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    private String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 创建订单支付请求
     */
    private void createPayReq(final Map<String, String> orderInfo, StringBuffer sbInfo) {
        PayReq req = new PayReq();
        req.appId = APP_ID;
        req.partnerId = MCH_ID;
        req.prepayId = orderInfo.get("prepay_id");
        req.packageValue = "Sign=WXPay";
        req.nonceStr = genNonceStr();
        req.timeStamp = String.valueOf(genTimeStamp());
        List<KeyValue> signParams = new LinkedList<KeyValue>();
        signParams.add(new KeyValue("appid", req.appId));
        signParams.add(new KeyValue("noncestr", req.nonceStr));
        signParams.add(new KeyValue("package", req.packageValue));
        signParams.add(new KeyValue("partnerid", req.partnerId));
        signParams.add(new KeyValue("prepayid", req.prepayId));
        signParams.add(new KeyValue("timestamp", req.timeStamp));
        req.sign = genAppSign(sbInfo, signParams);
        sbInfo.append("sign\n" + req.sign + "\n\n");
        // 发起支付
        sendPayReq(req);
    }

    // 发起支付，当前页面结束
    private void sendPayReq(final PayReq req) {
        IWXAPI wxApi = WXAPIFactory.createWXAPI(context, null);
        wxApi.registerApp(req.appId);
        wxApi.sendReq(req);
    }

    /**
     * 生成签名
     */
    @SuppressLint("DefaultLocale")
    private String genPackageSign(List<KeyValue> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    @SuppressLint("DefaultLocale")
    private String genAppSign(StringBuffer sbInfo, List<KeyValue> params) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName());
            sb.append('=');
            sb.append(params.get(i).getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(API_KEY);
        sbInfo.append("sign str\n" + sb.toString() + "\n\n");
        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return appSign;
    }

    class KeyValue {

        private String name;
        private String value;

        public KeyValue(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

}


//package com.yuanxin.clan.mvp.payment.weixin;
//
//import android.app.ActivityManager;
//import android.content.Context;
//import android.os.AsyncTask;
//import android.text.TextUtils;
//import android.util.Log;
//import android.util.Xml;
//
//import com.tencent.mm.opensdk.modelpay.PayReq;
//import com.tencent.mm.opensdk.openapi.IWXAPI;
//import com.tencent.mm.opensdk.openapi.WXAPIFactory;
//import com.yuanxin.clan.core.http.Url;
//import com.yuanxin.clan.mvp.MainApplication;
//import com.yuanxin.clan.mvp.utils.ToastUtil;
//import com.yuanxin.clan.mvp.utils.UIUtils;
//
//import org.apache.http.NameValuePair;
//import org.apache.http.message.BasicNameValuePair;
//import org.xmlpull.v1.XmlPullParser;
//
//import java.io.StringReader;
//import java.io.UnsupportedEncodingException;
//import java.lang.ref.WeakReference;
//import java.util.HashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
///**
// * @author lch
// * @date 2015/7/25
// */
//public class WXPayUtil {
//    public static String orderNo;
//    private String total_fee;
//    private IWXAPI wxApi;
//    private String callBackUrl;
//    private String orderInfo;
//    public static String paySuccessToUrl;
//    public static WeakReference<Context> contextRef;
//
//
//    public WXPayUtil(Context context, String orderNo, String total_fee, String callBackURL, String orderInfo, String paySuccessToUrl) {
////        if (!WXPayUtil.isWeChatExist(context)) {
////            ToastUtil.showLong(context, "请先安装微信客户端");
////            return;
////        }
//
//        WXPayUtil.orderNo = orderNo;
//        this.total_fee = String.valueOf((int) (Float.parseFloat(total_fee) * 100));
//        this.callBackUrl = callBackURL;
//        WXPayUtil.contextRef = new WeakReference<Context>(context);
//        wxApi = WXAPIFactory.createWXAPI(context, null);
//        wxApi.registerApp(MainApplication.paymentKey.getWXAPP_Id());
//        this.orderInfo = orderInfo;
//        WXPayUtil.paySuccessToUrl = paySuccessToUrl;
//        getPrePayId();
//    }
//
//
//    /**
//     * 微信支付第一步统一下单
//     * 参数发送请求到https://api.mch.weixin.qq.com/pay/unifiedorder这个
//     * api,请求成功后返回
//     * <xml>
//     * <return_code><![CDATA[SUCCESS]]></return_code>
//     * <return_msg><![CDATA[OK]]></return_msg>
//     * <appid><![CDATA[wx2421b1c4370ec43b]]></appid>
//     * <mch_id><![CDATA[10000100]]></mch_id>
//     * <nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str>
//     * <sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign>
//     * <result_code><![CDATA[SUCCESS]]></result_code>
//     * <prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id>
//     * <trade_type><![CDATA[JSAPI]]></trade_type>
//     * </xml>
//     */
//    public void getPrePayId() {
//        GetPrepayIdTask prepayIdTask = new GetPrepayIdTask();
//        prepayIdTask.execute();
//    }
//
//
//    /**
//     * @author lch
//     * @date 2015/7/25
//     * 获取微信prepayId
//     */
//    private class GetPrepayIdTask extends AsyncTask<Void, Void, Map<String, String>> {
//
//        @Override
//        protected void onPreExecute() {
//
//        }
//
//        @Override
//        protected void onPostExecute(Map<String, String> result) {
//            String status = result.get("return_code");
//            if (status != null && status.equals("SUCCESS")) {
//                sendPayRequest(result);
//            } else {
//                ToastUtil.showLong(UIUtils.getContext(), "支付失败!");
//                Log.e("WXPrepayIdTask", "获取PrepayId失败!" + result.get("return_msg"));
//                WXPayUtil.contextRef = null;
//                WXPayUtil.orderNo = null;
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            super.onCancelled();
//        }
//
//        @Override
//        protected Map<String, String> doInBackground(Void... params) {
//            String url = String.format("https://api.mch.weixin.qq.com/pay/unifiedorder");
//            String entity = getPrepayParams();
//            byte[] buf = Util.httpPost(url, entity);
//            String content = new String(buf);
//            Map<String, String> xml = decodeXml(content);
//            return xml;
//        }
//    }
//
//    /**
//     * 微信支付第二步
//     * 使用第一步获取到的prepayid及支付api需要的其他参数一起发送支付请求
//     * IWXAPI api;
//     * PayReq request = new PayReq();
//     * request.appId = "wxd930ea5d5a258f4f";
//     * request.partnerId = "1900000109";
//     * request.prepayId= "1101000000140415649af9fc314aa427",;
//     * request.packageValue = "Sign=WXPay";
//     * request.nonceStr= "1101000000140429eb40476f8896f4c9";
//     * request.timeStamp= "1398746574";
//     * request.sign= "7FFECB600D7157C5AA49810D2D8F28BC2811827B";
//     * api.sendReq(req);
//     */
//    public void sendPayRequest(Map<String, String> orderInfo) {
//        PayReq req = new PayReq();
//        req.appId = MainApplication.paymentKey.getWXAPP_Id();
//        req.partnerId = MainApplication.paymentKey.getWXMCH_ID();
//        req.prepayId = orderInfo.get("prepay_id");
//        req.packageValue = "Sign=WXPay";
//        req.nonceStr = genNonceStr();
//        req.timeStamp = String.valueOf(genTimeStamp());
//
//        List<NameValuePair> signParams = new LinkedList<NameValuePair>();
//        signParams.add(new BasicNameValuePair("appid", req.appId));
//        signParams.add(new BasicNameValuePair("noncestr", req.nonceStr));
//        signParams.add(new BasicNameValuePair("package", req.packageValue));
//        signParams.add(new BasicNameValuePair("partnerid", req.partnerId));
//        signParams.add(new BasicNameValuePair("prepayid", req.prepayId));
//        signParams.add(new BasicNameValuePair("timestamp", req.timeStamp));
//
//        req.sign = genAppSign(signParams);
//
//        wxApi.sendReq(req);
//    }
//
//    /**
//     * 生成获取prepayId所需要的参数的字符串
//     *
//     * @return
//     */
//    public String getPrepayParams() {
//        String nonceStr = genNonceStr();
//
//        List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
//        packageParams.add(new BasicNameValuePair("appid", MainApplication.paymentKey.getWXAPP_Id()));
//        packageParams.add(new BasicNameValuePair("body", TextUtils.isEmpty(orderInfo) ? orderNo : orderInfo));
//        packageParams.add(new BasicNameValuePair("mch_id", MainApplication.paymentKey.getWXMCH_ID()));
//        packageParams.add(new BasicNameValuePair("nonce_str", nonceStr));
//        packageParams.add(new BasicNameValuePair("notify_url", TextUtils.isEmpty(callBackUrl) ? Url.WECHAT_CALLBACK_URL : callBackUrl));
//        packageParams.add(new BasicNameValuePair("out_trade_no", orderNo));
//        packageParams.add(new BasicNameValuePair("spbill_create_ip", "127.0.0.1"));
//        packageParams.add(new BasicNameValuePair("total_fee", total_fee));
//        packageParams.add(new BasicNameValuePair("trade_type", "APP"));
//
//        String sign = genPackageSign(packageParams);
//        packageParams.add(new BasicNameValuePair("sign", sign));
//        String params = toXml(packageParams);
//        try {
//            return new String(params.getBytes(), "ISO8859-1");
//        } catch (UnsupportedEncodingException e) {
//            Log.e("WXPayUtil", "预支付订单编码转换失败!");
//            e.printStackTrace();
//        }
//
//        return params;
//    }
//
//    /**
//     * 将所有参数转为XML格式数据
//     *
//     * @param params
//     * @return
//     */
//    public static String toXml(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//        sb.append("<xml>");
//        for (int i = 0; i < params.size(); i++) {
//            sb.append("<").append(params.get(i).getName()).append(">");
//            sb.append(params.get(i).getValue());
//            sb.append("</").append(params.get(i).getName()).append(">");
//        }
//
//        sb.append("</xml>");
//
//        return sb.toString();
//    }
//
//    public static Map<String, String> decodeXml(String content) {
//
//        try {
//            Map<String, String> xml = new HashMap<String, String>();
//            XmlPullParser parser = Xml.newPullParser();
//            parser.setInput(new StringReader(content));
//            int event = parser.getEventType();
//            while (event != XmlPullParser.END_DOCUMENT) {
//
//                String nodeName = parser.getName();
//                switch (event) {
//                    case XmlPullParser.START_DOCUMENT:
//
//                        break;
//                    case XmlPullParser.START_TAG:
//                        if ("xml".equals(nodeName) == false) {
//                            //实例化student对象
//                            xml.put(nodeName, parser.nextText());
//                        }
//                        break;
//                    case XmlPullParser.END_TAG:
//                        break;
//                }
//                event = parser.next();
//            }
//
//            return xml;
//        } catch (Exception e) {
//            Log.e("微信支付", e.toString());
//        }
//        return null;
//
//    }
//
//    /**
//     * s生成随机数
//     *
//     * @return
//     */
//    private static String genNonceStr() {
//        Random random = new Random();
//        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
//    }
//
//    /**
//     * 生成时间戳
//     *
//     * @return
//     */
//    private static long genTimeStamp() {
//        return System.currentTimeMillis() / 1000;
//    }
//
//    /**
//     * 生成签名
//     */
//
//    public static String genPackageSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//        sb.append("key=");
//        sb.append(MainApplication.paymentKey.getWXAPI_Key());
//
//
//        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion", packageSign);
//        return packageSign;
//    }
//
//    public static String genAppSign(List<NameValuePair> params) {
//        StringBuilder sb = new StringBuilder();
//
//        for (int i = 0; i < params.size(); i++) {
//            sb.append(params.get(i).getName());
//            sb.append('=');
//            sb.append(params.get(i).getValue());
//            sb.append('&');
//        }
//
//        sb.append("key=");
//        sb.append(MainApplication.paymentKey.getWXAPI_Key());
//
//        String appSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
//        Log.e("orion", appSign);
//        return appSign;
//    }
//
//
//    public static boolean isWeChatExist(Context context) {
//        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
//        boolean isExist = false;
//        for (int i = 0; i < appProcessInfos.size(); i++) {
//            if (appProcessInfos.get(i).processName.equals("com.tencent.mm")) {
//                isExist = true;
//                break;
//            }
//        }
//        return isExist;
//    }
//}

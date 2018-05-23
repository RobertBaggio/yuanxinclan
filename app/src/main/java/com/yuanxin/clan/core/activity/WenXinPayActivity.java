package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.WeiXinPayUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/15.
 */
//商户服务器生成支付订单，先调用统一下单API(详见第7节)生成预付单，获取到prepay_id后将参数再次签名传输给APP发起支付。以下是调起微信支付的关键代码
//步骤1：用户在商户APP中选择商品，提交订单，选择微信支付。

//步骤2：商户后台收到用户支付单，调用微信支付统一下单接口。参见【统一下单API】。

//步骤3：统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。注意：package的值格式为Sign=WXPay

//步骤4：商户APP调起微信支付。api参见本章节【app端开发步骤说明】

//步骤5：商户后台接收支付通知。api参见【支付结果通知API】

//步骤6：商户后台查询支付结果。，api参见【查询订单API】
public class WenXinPayActivity extends BaseActivity implements IWXAPIEventHandler {
    @BindView(R.id.wei_xin_pay_image)
    ImageView weiXinPayImage;
    @BindView(R.id.wei_xin_pay_layout)
    LinearLayout weiXinPayLayout;
    @BindView(R.id.activity_wei_xin_pay_button)
    Button activityWeiXinPayButton;
    private static final String APP_ID = "wx39ed86850623068d";//已修改为自己的
    private IWXAPI api;

    @Override
    public int getViewLayout() {
        return R.layout.activity_wei_xin_pay;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        //        registerApp();注册一
        regToWx();
    }

    //    //注册APPID 方法二
    private void regToWx() {
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
    }

//    //注册APPID
//    private void registerApp() {
//        api = WXAPIFactory.createWXAPI(WenXinPayActivity.this, null);
//        api.registerApp("wxd930ea5d5a258f4f");
//    }

    @OnClick(R.id.activity_wei_xin_pay_button)//微信
    public void onClick() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;//检查微信版本是否支持支付
        if (isPaySupported) {
            String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
            activityWeiXinPayButton.setEnabled(false);
            ToastUtil.showInfo(WenXinPayActivity.this, "获取订单中...", Toast.LENGTH_SHORT);
            try {
                byte[] buf = WeiXinPayUtil.httpGet(url);//网络请求
                if (buf != null && buf.length > 0) {
                    String content = new String(buf);//结果
                    Log.e("get server pay params:", content);
                    JSONObject json = new JSONObject(content);
                    if (null != json && !json.has("retcode")) {
                        PayReq req = new PayReq();
                        //req.appId = "wxf8b4f85f3a794e77";  // 测试用appId
                        req.appId = json.getString("appid");
                        req.partnerId = json.getString("partnerid");
                        req.prepayId = json.getString("prepayid");
                        req.nonceStr = json.getString("noncestr");
                        req.timeStamp = json.getString("timestamp");
                        req.packageValue = json.getString("package");
                        req.sign = json.getString("sign");
                        req.extData = "app data"; // optional
                        ToastUtil.showInfo(WenXinPayActivity.this, "正常调起支付", Toast.LENGTH_SHORT);
                        // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                        api.sendReq(req);
                    } else {
                        Log.d("PAY_GET", "返回错误" + json.getString("retmsg"));
                        ToastUtil.showWarning(WenXinPayActivity.this, "返回错误" + json.getString("retmsg"), Toast.LENGTH_SHORT);
                    }
                } else {
                    Log.d("PAY_GET", "服务器请求错误");
                    ToastUtil.showWarning(WenXinPayActivity.this, "服务器请求错误", Toast.LENGTH_SHORT);
                }
            } catch (Exception e) {
                Log.e("PAY_GET", "异常：" + e.getMessage());
                ToastUtil.showError(WenXinPayActivity.this, "异常：" + e.getMessage(), Toast.LENGTH_SHORT);
            }
            activityWeiXinPayButton.setEnabled(true);


        } else {
            ToastUtil.showWarning(WenXinPayActivity.this, String.valueOf(isPaySupported), Toast.LENGTH_SHORT);

        }


    }

    //implements IWXAPIEventHandler
    @Override
    public void onReq(BaseReq baseReq) {

    }

    //implements IWXAPIEventHandler
    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp.errCode == 0) {
            ToastUtil.showSuccess(getApplicationContext(), "支付成功!", Toast.LENGTH_SHORT);
            finish();
        } else if (baseResp.errCode == -1) {
            ToastUtil.showWarning(getApplicationContext(), "支付失败", Toast.LENGTH_SHORT);
        } else if (baseResp.errCode == -2) {
            ToastUtil.showInfo(getApplicationContext(), "取消支付", Toast.LENGTH_SHORT);
        }
    }
}

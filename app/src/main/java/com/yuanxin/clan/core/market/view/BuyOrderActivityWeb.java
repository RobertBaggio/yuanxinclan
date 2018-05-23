package com.yuanxin.clan.core.market.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.activity.OkPayActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.OrderCommodityAdapter;
import com.yuanxin.clan.core.market.bean.OrderCommodity;
import com.yuanxin.clan.core.market.bean.PostAddress;
import com.yuanxin.clan.core.progress.ProgressDialogHandler;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.payment1.AliPayUtil;
import com.yuanxin.clan.mvp.payment1.AliPayUtilTwo;
import com.yuanxin.clan.mvp.payment1.PayResultListener;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;


/**
 * Created by lenovo1 on 2017/4/3.
 * 提交订单 立即下单
 */
public class BuyOrderActivityWeb extends BaseActivity implements View.OnClickListener, PayResultListener {
    @BindView(R.id.activity_buy_order_left_layout)
    LinearLayout mActivityBuyOrderLeftLayout;
    @BindView(R.id.activity_buy_order_middle_layout)
    TextView mActivityBuyOrderMiddleLayout;
    @BindView(R.id.activity_buy_order_right_layout)
    LinearLayout mActivityBuyOrderRightLayout;
    @BindView(R.id.activity_buy_order_head_layout)
    RelativeLayout mActivityBuyOrderHeadLayout;
    @BindView(R.id.activity_buy_order_button)
    TextView mActivityBuyOrderButton;
    @BindView(R.id.flyCommodity)
    FamiliarRecyclerView mFlyCommodity;



    TextView mActivityBuyOrderAddressNameText;
    TextView mActivityBuyOrderAddressPhoneText;
    TextView mActivityBuyOrderAddressAddressText;
    RelativeLayout mActivityBuyOrderAllAddressLayout;
    TextView mTvTotalPrice;
    TextView epnameitem;
    LinearLayout epliitem;


    private List<OrderCommodity> orderList;
    public static final int SINGLE = 1; //立即购买，单个商品
    public static final int MULTIPLE = 2;  //购物车，多个商品
    private int type = SINGLE;
    private PostAddress mGoodsAddressEntity;
    private double amountTotal = 0;
    private View headerView, footerView;
    private ProgressDialogHandler mProgressDialogHandler;
    private PayReq req;
    private int ab =3 ;
    private String ddhao;
    private String uUid;
    private String intype;
    private String intypeone;
    private String murl;
    private String orderid;
    private String servicetype;
    private More_LoadDialog mMore_loadDialog;

    @Override
    public int getViewLayout() {
        return R.layout.activity_buy_order;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        LayoutInflater factory = LayoutInflater.from(this);
//        headerView = factory.inflate(R.layout.activity_buy_order_header, mFlyCommodity, false);
        headerView = factory.inflate(R.layout.new_buy_header, mFlyCommodity, false);
        footerView = factory.inflate(R.layout.activity_buy_order_footer, mFlyCommodity, false);
        mMore_loadDialog = new More_LoadDialog(this);


        loadView();
        uUid = getIntent().getStringExtra("orderUuid");
        orderid = getIntent().getStringExtra("orderid");
        orderList = (List<OrderCommodity>) getIntent().getSerializableExtra("datas");
        type = getIntent().getIntExtra("type", MULTIPLE);
        intype = getIntent().getStringExtra("intype");
        servicetype = getIntent().getStringExtra("servicetype");
        intypeone = getIntent().getStringExtra("intypeone");
        if (intypeone.equals("yes")){
            mActivityBuyOrderAllAddressLayout.setVisibility(View.GONE);
            epliitem.setVisibility(View.VISIBLE);
            epnameitem.setText(MyShareUtil.getSharedString("buyepname"));
        }else {
            epliitem.setVisibility(View.GONE);
            mActivityBuyOrderAllAddressLayout.setVisibility(View.VISIBLE);
        }
        getDefaultAddress();
        initRecyclerView();
        initDataView();
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
    private void loadView() {
        mActivityBuyOrderAddressNameText = ButterKnife.findById(headerView, R.id.activity_buy_order_address_name_text);
        mActivityBuyOrderAddressPhoneText = ButterKnife.findById(headerView, R.id.activity_buy_order_address_phone_text);
        mActivityBuyOrderAddressAddressText = ButterKnife.findById(headerView, R.id.activity_buy_order_address_address_text);
        mActivityBuyOrderAllAddressLayout = ButterKnife.findById(headerView, R.id.activity_buy_order_all_address_layout);
        mTvTotalPrice = ButterKnife.findById(footerView, R.id.tvTotalPrice);
        epliitem = ButterKnife.findById(headerView,R.id.epliitem);
        epnameitem = ButterKnife.findById(headerView,R.id.epnameitem);
        mActivityBuyOrderAllAddressLayout.setOnClickListener(this);
    }

    private void initDataView() {
        if (orderList == null) {
            orderList = new ArrayList<>();
            return;
        }
        for (int i = 0; i < orderList.size(); i++) {
            amountTotal += (orderList.get(i).getCommodityPrice() * orderList.get(i).getCommodityNumber() + orderList.get(i).getExpressCost());
        }
        mTvTotalPrice.setText(amountTotal + "元");
    }

    private void initRecyclerView() {
        if (orderList == null) {
            orderList = new ArrayList<>();
        }
        OrderCommodityAdapter adapter = new OrderCommodityAdapter(orderList);
        adapter.addHeaderView(headerView);
        adapter.addFooterView(footerView);
//www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/market-good-info&commodityId=182&appFlg=1
//www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/epMallInfo&commodityId=
//www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/serviceMall-info&shopId=1975&appFlg=1
        mFlyCommodity.setAdapter(adapter);
        adapter.setBidListentr(new OrderCommodityAdapter.OnBidChangeListener() {
            @Override
            public void onCheckedChange(String id) {
                Log.v("lgq","url===="+id+"..."+intype);
                if (intype.equals("js")){
                    murl ="http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/market-good-info&commodityId="+id+"&appFlg=1";
                }else if (intype.equals("qy")){
                    murl ="http:www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/epMallInfo&commodityId="+id+"&appFlg=1";
                }else if (intype.equals("fw")){
                    murl ="http:www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/serviceMall-info&shopId="+id+"&appFlg=1";
                }
                Log.v("lgq","url===="+murl);
                startActivity(new Intent(BuyOrderActivityWeb.this, HomeADactivity.class).putExtra("url", murl));
            }
        });

    }

    private void getDefaultAddress() {
        String url = Url.getMyDeftaultAddress;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        mGoodsAddressEntity = FastJsonUtils.parseObject(object.getString("data"), PostAddress.class);
                        initAddressDate();
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void initAddressDate() {
        if (mGoodsAddressEntity == null) {
            mActivityBuyOrderAddressNameText.setText("");//人
            mActivityBuyOrderAddressPhoneText.setText("");//电话
            mActivityBuyOrderAddressAddressText.setText("");
            return;
        }
        //设置地址信息
        mActivityBuyOrderAddressNameText.setText(mGoodsAddressEntity.getReceiver());//人
        mActivityBuyOrderAddressPhoneText.setText(mGoodsAddressEntity.getPhone());//电话
        mActivityBuyOrderAddressAddressText.setText(mGoodsAddressEntity.getProvince() + mGoodsAddressEntity.getCity() + mGoodsAddressEntity.getArea() + mGoodsAddressEntity.getDetail());
    }

    /**
     * 商品id
     *
     * @param stringList
     * @return
     */
    public static String jointId(List<OrderCommodity> stringList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                sb.append(stringList.get(i).getShopListId());
            } else {
                sb.append("," + stringList.get(i).getShopListId());
            }
        }
        return sb.toString();
    }

    /**
     * 商户id
     *
     * @param stringList
     * @return
     */
    public static String jointBId(List<OrderCommodity> stringList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                sb.append(stringList.get(i).getBusinessId());
            } else {
                sb.append("," + stringList.get(i).getBusinessId());
            }
        }
        return sb.toString();
    }

    /**
     * 运费
     *
     * @param stringList
     * @return
     */
    public static String jointPost(List<OrderCommodity> stringList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                sb.append(stringList.get(i).getExpressCost());
            } else {
                sb.append("," + stringList.get(i).getExpressCost());
            }
        }
        return sb.toString();
    }

    /**
     * 价格
     *
     * @param stringList
     * @return
     */
    public static String jointPrice(List<OrderCommodity> stringList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                sb.append(stringList.get(i).getCommodityPrice());
            } else {
                sb.append("," + stringList.get(i).getCommodityPrice());
            }
        }
        return sb.toString();
    }

    private void cartSettlement() {//  购物车结算
        String url = Url.carBuy;
        RequestParams params = new RequestParams();
        putParams(params);
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.i("lgq","cartSettlement==="+s);
                    if (object.getString("success").equals("true")) {
                        String orderNo = object.getString("data");
                        orderid = orderNo;
                        if (payType == wxPayType) {
                            toWxPay(orderNo);
                        } else if (payType == aliPayType) {
                            toAliPay(orderNo);
                        }
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void putParams(RequestParams params) {
        for (int i = 0; i < orderList.size(); i++) {
            params.put("orders[" + i + "].userId", UserNative.getId());//购买者id
            params.put("orders[" + i + "].userNm", UserNative.getName());//购买者姓名
            params.put("orders[" + i + "].businessId", orderList.get(i).getBusinessId());//商家id
            params.put("orders[" + i + "].province", mGoodsAddressEntity.getProvince());//省
            params.put("orders[" + i + "].city", mGoodsAddressEntity.getCity());//市
            params.put("orders[" + i + "].area", mGoodsAddressEntity.getArea());//区
            params.put("orders[" + i + "].detail", mGoodsAddressEntity.getDetail());//详细地址
            params.put("orders[" + i + "].phone", mGoodsAddressEntity.getPhone());//手机号码
            params.put("orders[" + i + "].consignee", mGoodsAddressEntity.getReceiver());//收件人
            params.put("orders[" + i + "].orderPrice", orderList.get(i).getCommodityPrice() * orderList.get(i).getCommodityNumber());//订单总价格
            params.put("orders[" + i + "].expressCost", orderList.get(i).getExpressCost());// 运费
            params.put("orders[" + i + "].shopList[0].shopListId", orderList.get(i).getCommodityId());//商品item的id  Integer[] idStr
        }
    }

//    private void getParamsStr() {
//        List<OrderJson> orderJsonList = new ArrayList<>();
//            OrderJson json;
//        for (int i = 0; i < orderList.size(); i++) {
//            json=new OrderJson();
//            params.put("orders[].userId", UserNative.getId());//购买者id
//            params.put("orders[].userNm", UserNative.getName());//购买者姓名
//            params.put("orders[].businessId", jointBId(orderList));//商家id
//            params.put("orders[].shopList[].shopListId", jointId(orderList));//商品item的id  Integer[] idStr
//            params.put("orders[].province", mGoodsAddressEntity.getProvince());//省
//            params.put("orders[].city", mGoodsAddressEntity.getCity());//市
//            params.put("orders[].area", mGoodsAddressEntity.getArea());//区
//            params.put("orders[].detail", mGoodsAddressEntity.getDetail());//详细地址
//            params.put("orders[].phone", mGoodsAddressEntity.getPhone());//手机号码
//            params.put("orders[].consignee", mGoodsAddressEntity.getReceiver());//收件人
//            params.put("orders[].orderPrice", jointPrice(orderList));//订单总价格
//            params.put("orders[].expressCost", jointPost(orderList));// 运费
//        }
//    }

    @OnClick({R.id.activity_buy_order_button, R.id.activity_buy_order_left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_buy_order_left_layout:
                finish();
                break;
            case R.id.activity_buy_order_all_address_layout:
                Intent intent = new Intent(BuyOrderActivityWeb.this, OrderAddressActivity.class);
//                Intent intent = new Intent(BuyOrderActivityWeb.this, GoodsAddressActivity.class);
                intent.putExtra("isChooseAddress", true);
                MyShareUtil.sharedPstring("orderid",orderid);
                startActivityForResult(intent, 369);
                break;
            case R.id.activity_buy_order_button:
                if (mGoodsAddressEntity == null&&!intypeone.equals("yes")) {
                    ToastUtil.showInfo(BuyOrderActivityWeb.this, "请编辑收货地址", Toast.LENGTH_SHORT);
                    return;
                }
                showDialog();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 369 && resultCode == RESULT_OK) {
            mGoodsAddressEntity = (PostAddress) data.getSerializableExtra("address");
            initAddressDate();
        }
    }

    private Dialog payDlg;
    private CheckBox wxPayBox, aliPayBox;
    private int payType = noPayType;
    public static final int noPayType = 0, wxPayType = 1, aliPayType = 2;

    private void showDialog() {
        if (payDlg == null) {
            payDlg = new Dialog(BuyOrderActivityWeb.this, R.style.WhiteDialog);
            Window window = payDlg.getWindow();
            window.setContentView(R.layout.dialog_by);
            View oneLayout = window.findViewById(R.id.dialog_buy_check_box_one_layout);
            View twoLayout = window.findViewById(R.id.dialog_buy_check_box_two_layout);
            wxPayBox = (CheckBox) window.findViewById(R.id.dialog_buy_check_box_one);
            aliPayBox = (CheckBox) window.findViewById(R.id.dialog_buy_check_box_two);
            TextView button = (TextView) window.findViewById(R.id.dialog_buy_button);
            wxPayBox.setTag(payDlg);
            aliPayBox.setTag(payDlg);
            twoLayout.setTag(payDlg);
            oneLayout.setTag(payDlg);
            button.setTag(payDlg);
            oneLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (wxPayBox.isChecked()) {
                        wxPayBox.setChecked(false);
                        payType = noPayType;
                    } else {
                        payType = wxPayType;
                        wxPayBox.setChecked(true);
                        aliPayBox.setChecked(false);
                    }
                }
            });
            twoLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (aliPayBox.isChecked()) {
                        aliPayBox.setChecked(false);
                        payType = noPayType;
                    } else {
                        payType = aliPayType;
                        aliPayBox.setChecked(true);
                        wxPayBox.setChecked(false);
                    }
                }
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (payType != noPayType) {
                        gotoPay();
                        payDlg.dismiss();
                    }
                }
            });
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = this.getWindowManager().getDefaultDisplay().getWidth();
            params.height = (int) (this.getWindowManager().getDefaultDisplay().getHeight() * 0.4);
            window.setGravity(Gravity.BOTTOM);
            window.setAttributes(params);
            // TODO: 2017/6/16 0016 先隐藏微信支付。。。
//            oneLayout.setVisibility(View.GONE);
        }
        payDlg.show();
    }

    private void gotoPay() {
        Log.e("hxw", type + "");
        if (type == SINGLE) {
            singleBuy();
        } else {
            cartSettlement();
        }
    }

    private void singleBuy() {
        Log.i("lgq","支付宝支付成功。。11111。");
        if (payType == wxPayType) {
            toWxPay(uUid);
        } else if (payType == aliPayType) {
            toAliPay(uUid);
            Log.i("lgq","支付宝支付成功。。222222222。");
        }
    }

    private void toAliPay(String orderNo) {
        ddhao = orderNo;
        ab = 9;
        if (intypeone.equals("yes")){
            new AliPayUtil(BuyOrderActivityWeb.this).getSignToPay(orderNo);

        }else {
            new AliPayUtilTwo(BuyOrderActivityWeb.this).getSignToPay(orderNo);
            Log.i("lgq","支付宝支付成功。。品牌【【【【。");
        }
//        new AliPayUtil(BuyOrderActivityWeb.this).getSignToPay(orderNo);
    }




    private void toWxPay(String orderNo) {
        ddhao = orderNo;
        getWXSignToPay(orderNo);

    }

    /**
     * 通过订单id获取签名加密的字符串，并去支付
     *
     * @param orderNo
     */
    public void getWXSignToPay(String orderNo) {
        showProgressDialog();
        String url = Url.getWxSignPayInfo;
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNo);//订单id
        params.put("signId", 1);//订单id
        params.put("userId", UserNative.getId());
//        if (intypeone.equals("yes")){
            params.put("foreign", 1);
//        }else {
//            params.put("foreign", 0);
//        }

        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(BuyOrderActivityWeb.this, "网络连接异常", Toast.LENGTH_SHORT);
                dismissProgressDialog();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                dismissProgressDialog();
                Log.i("lgq","微信APIFH.////==="+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");

                        String appid= array.getString("appid");
                        String noncestr= array.getString("noncestr");
                        String mpackage= array.getString("package");
                        String partnerid= array.getString("partnerid");
                        String prepayid= array.getString("prepayid");
                        String sign= array.getString("sign");
                        String timestamp= array.getString("timestamp");

                        req = new PayReq();
                        req.appId = appid;
                        req.partnerId = partnerid;
                        req.prepayId = prepayid;
                        req.packageValue = mpackage;
                        req.nonceStr = noncestr;
                        req.timeStamp = timestamp;
//                        List<KeyValue> signParams = new LinkedList<KeyValue>();
//                        signParams.add(new KeyValue("appid", req.appId));
//                        signParams.add(new KeyValue("noncestr", req.nonceStr));
//                        signParams.add(new KeyValue("package", req.packageValue));
//                        signParams.add(new KeyValue("partnerid", req.partnerId));
//                        signParams.add(new KeyValue("prepayid", req.prepayId));
//                        signParams.add(new KeyValue("timestamp", req.timeStamp));
                        req.sign = timestamp;

//                            pay();
                        doPay(req);
                    } else {
                        ToastUtil.showWarning(BuyOrderActivityWeb.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(BuyOrderActivityWeb.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    public void doPay(final PayReq req) {
        ab=6;
        IWXAPI wxApi = WXAPIFactory.createWXAPI(BuyOrderActivityWeb.this, null);
        wxApi.registerApp(req.appId);
        wxApi.sendReq(req);
    }

    /**
     * 验证是否支付成功
     */
    public void verifyPay() {
//        showProgressDialog();
        mMore_loadDialog.show();
        String url = Url.getpayNotice;
        RequestParams params = new RequestParams();
        params.put("orderNumber",ddhao);
        params.put("userId",UserNative.getId());
        params.put("sign",2);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMore_loadDialog.dismiss();
                ToastUtil.showWarning(BuyOrderActivityWeb.this, "网络连接异常", Toast.LENGTH_SHORT);
//                dismissProgressDialog();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                dismissProgressDialog();
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String a = object.getString("out_trade_no");
                        String string = object.getString("msg");
                        Log.v("lgq","wx支付成功。verifyPay。。"+a+"...."+ddhao);
                        if (string.equals("支付成功")&&ddhao.length()>5){
                            ToastUtil.showSuccess(BuyOrderActivityWeb.this, "订单支付成功", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(BuyOrderActivityWeb.this, OkPayActivity.class);
                            intent.putExtra("ord",a);
                            intent.putExtra("ifok","ok");
                            intent.putExtra("servicetype",servicetype);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.showError(BuyOrderActivityWeb.this, "订单支付失败", Toast.LENGTH_SHORT);
//                            Intent intent = new Intent(BuyOrderActivity.this, OkPayActivity.class);
//                            intent.putExtra("ord",ddhao);
//                            intent.putExtra("ifok","nook");
//                            startActivity(intent);
                        }
//                        if (payResultListener != null) {
//                            payResultListener.onSuccess();
//                        }
                    } else {
//                        ToastUtil.showWarning(BuyOrderActivityWeb.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(BuyOrderActivityWeb.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    @Override
    public void onSuccess() {
//        Toast.makeText(this, "支付成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, OkPayActivity.class);
        intent.putExtra("ord",ddhao);
        intent.putExtra("ifok","ok");
        intent.putExtra("servicetype",servicetype);
        startActivity(intent);
//        EventBus.getDefault().post(new PaySuccessEvent());
//        startActivity(new Intent(this, MyOrderActivity.class));
        finish();

    }

    @Override
    public void onFail() {
//        Intent intent = new Intent(this, OkPayActivity.class);
//        intent.putExtra("ord",ddhao);
//        intent.putExtra("ifok","nook");
//        startActivity(intent);
        ToastUtil.showError(this, "支付失败", Toast.LENGTH_SHORT);
    }

    @Override
    public void onProcess() {
        ToastUtil.showInfo(this, "支付结果确认中", Toast.LENGTH_SHORT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDefaultAddress();
        Log.v("lgq","............onresume");
        if (ab==6){
            verifyPay();
        }
        ab=3;
    }


}

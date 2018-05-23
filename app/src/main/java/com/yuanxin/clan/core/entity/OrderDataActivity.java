package com.yuanxin.clan.core.entity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.OkPayActivity;
import com.yuanxin.clan.core.activity.PhotoScanActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.payment1.AliPayUtilTwo;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *订单详情
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class OrderDataActivity extends BaseActivity {
    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activity_exchange_phone_left_layout;
    @BindView(R.id.ifpayli)
    LinearLayout ifpayli;
    @BindView(R.id.ddwliuli)
    LinearLayout ddwliuli;
    @BindView(R.id.wliushowli)
    LinearLayout wliushowli;
    @BindView(R.id.namedateorder)
    TextView namedateorder;
    @BindView(R.id.ddwliucontentname)
    TextView ddwliucontentname;
    @BindView(R.id.phonedateorder)
    TextView phonedateorder;
    @BindView(R.id.shidateorder)
    TextView shidateorder;
    @BindView(R.id.qudateorder)
    TextView qudateorder;
    @BindView(R.id.dataadreesdateorder)
    TextView dataadreesdateorder;
    @BindView(R.id.timedateorder)
    TextView timedateorder;
    @BindView(R.id.item_item_fragment_my_order_all_price)
    TextView item_item_fragment_my_order_all_price;
    @BindView(R.id.item_item_fragment_my_order_all_name)
    TextView item_item_fragment_my_order_all_name;
    @BindView(R.id.item_item_fragment_my_order_all_choose_one)
    TextView item_item_fragment_my_order_all_choose_one;
    @BindView(R.id.item_item_fragment_my_order_all_choose_two)
    TextView item_item_fragment_my_order_all_choose_two;
    @BindView(R.id.item_item_fragment_my_order_all_number)
    TextView item_item_fragment_my_order_all_number;
    @BindView(R.id.ddallpayte)
    TextView ddallpayte;
    @BindView(R.id.ddyunfeite)
    TextView ddyunfeite;
    @BindView(R.id.ddnumte)
    TextView ddnumte;
    @BindView(R.id.ddzhifucode)
    TextView ddzhifucode;
    @BindView(R.id.ddwuliuname)
    TextView ddwuliuname;
    @BindView(R.id.ddwuliucode)
    TextView ddwuliucode;
    @BindView(R.id.ddstatue)
    TextView ddstatue;
    @BindView(R.id.item_fragment_my_order_all_state)
    TextView item_fragment_my_order_all_state;
    @BindView(R.id.ddtobuyte)
    TextView ddtobuyte;
    @BindView(R.id.item_item_fragment_my_order_all_image)
    ImageView item_item_fragment_my_order_all_image;
    private OnItemClickListener mOnItemClickListener;
    private String ddhao;
    private int ab =3 ;
    private PayReq req;



    private FragmentMyOrderAllEntity entity;
    private String uuid;
    private String expressNumber,expressCompany,commodityColor,image,commodityPrice,commodityNm,commoditySp,commodityId;
    private int commodityNumber;
    private String imageurl;
    private boolean ifshow=true;

    @Override
    public int getViewLayout() {
        return R.layout.orderdatalayout;
    }
    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        uuid = getIntent().getStringExtra("orderUuid");
//        int nopay = getIntent().getIntExtra("nopay",0);
//        if (nopay==1){
//            ddtobuyte.setVisibility(View.VISIBLE);
//        }
        if (getIntent().getExtras().containsKey("entity")) {
            entity = getIntent().getParcelableExtra("entity");
            updatedata(entity);
        } else {
            getGankInto();
        }
    }
    @OnClick({R.id.activity_exchange_phone_left_layout,R.id.ddwliuli,R.id.ddtobuyte,R.id.item_item_fragment_my_order_all_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                onBackPressed();
                break;
            case R.id.ddtobuyte:
                showDialog();
//                tobuy();
                break;
            case R.id.item_item_fragment_my_order_all_image:
                Intent intent = new Intent();
                intent.putExtra("image", imageurl);
                intent.setClass(OrderDataActivity.this, PhotoScanActivity.class);
                startActivity(intent);
                break;
            case R.id.ddwliuli:
                if (!ifshow){
                    wliushowli.setVisibility(View.VISIBLE);
                    ifshow = !ifshow;
                }else {
                    wliushowli.setVisibility(View.GONE);
                    ifshow = !ifshow;
                }


                break;


        }
    }
    //全部订单
    private void getGankInto() {
        String url = Url.orderdata;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//企业id
        params.put("orderUuid", uuid);//企业id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(OrderDataActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.v("lgq","订单详情。。。"+s);
                    if (object.getString("success").equals("true")) {
                        String datastring = object.getString("data");
                        JSONObject dataob =new JSONObject(datastring);
                        JSONArray dataArray = dataob.getJSONArray("orders");
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            String businessId = dataObject.getString("businessId");
                            String orderId = dataObject.getString("orderUuid");//订单号
                            String createNm = dataObject.getString("createNm");//订单号
                            String updateNm = dataObject.getString("updateNm");//订单号
                            String phone = dataObject.getString("phone");//订单号
                            String city = dataObject.getString("city");//订单号
                            String province = dataObject.getString("province");//订单号
                            String area = dataObject.getString("area");//订单号
                            String detail = dataObject.getString("detail");//订单号
                            String createDt = dataObject.getString("createDt");//订单号
                            String paymentName = dataObject.getString("paymentName");//订单号
                            String orderStatusName = dataObject.getString("orderStatusName");//订单号


                            String orderStatus = dataObject.getString("orderStatus");//订单状态 1待付款 2待收货 3 订单完成
                            String orderPrice = dataObject.getString("orderPrice");//总价
                            String expressCost = dataObject.getString("expressCost");//运费
                            String updateDt = dataObject.getString("updateDt");//运费
                            String shopList = dataObject.getString("shopList");
                            expressNumber=dataObject.getString("expressNumber");
                            expressCompany=dataObject.getString("expressCompany");
                            if (!shopList.equals("null")) {
                                JSONArray shopListArray = dataObject.getJSONArray("shopList");
                                for (int c = 0; c < shopListArray.length(); c++) {
                                    JSONObject shopListObject = shopListArray.getJSONObject(c);
                                    commodityColor = shopListObject.getString("commodityColor");//颜色
                                    String commodityImage1 = shopListObject.getString("commodityImage1");//图片
                                    image = Url.img_domain+ commodityImage1+Url.imageStyle640x640;
                                    commodityNumber = shopListObject.getInt("commodityNumber");//数量
                                    commodityPrice = shopListObject.getString("commodityPrice");//单价
                                    commodityNm = shopListObject.getString("commodityNm");//名称
                                    commoditySp = shopListObject.getString("commoditySp");//规格
                                    commodityId = shopListObject.getString("commodityId");//规格
                                }
                            }
                            entity = new FragmentMyOrderAllEntity();//外层数据
                            entity.setCreateNm(createNm);
                            entity.setPhone(phone);
                            entity.setProvince(province);
                            entity.setCity(city);
                            entity.setArea(area);
                            entity.setDetail(detail);
                            entity.setCreateDt(createDt);
                            entity.setCommodityImage1(image);
                            entity.setPrice(commodityPrice);
                            entity.setCommodityNm(commodityNm);
                            entity.setCommoditySp(commoditySp);
                            entity.setCommodityColor(commodityColor);
                            entity.setTotal(orderPrice);//总金额
                            entity.setNumber(commodityNumber);//共多少件
                            entity.setCarriage(expressCost);//含运费
                            entity.setPaymentName(paymentName);
                            entity.setOrderId(orderId);//订单号
                            entity.setState(orderStatusName);//待付款
                            entity.setExpressCompany(expressCompany);
                            entity.setExpressNumber(expressNumber);
                            entity.setCommodityId(commodityId);
                            entity.setUpdateNm(updateNm);
                            if (orderStatusName.equals("待付款")){
                                ddtobuyte.setVisibility(View.VISIBLE);
                            }

                        }

                        updatedata(entity);
                    } else {
                        ToastUtil.showWarning(OrderDataActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    ToastUtil.showWarning(OrderDataActivity.this, "数据解析出错", Toast.LENGTH_SHORT);
                    Logger.e("数据解析出错");

                }
            }
        });
    }
    private void updatedata(FragmentMyOrderAllEntity entity){
        try {

            imageurl = entity.getCommodityImage1();
            ImageManager.loadRoundCornerImage(OrderDataActivity.this, entity.getCommodityImage1(), R.drawable.list_img, item_item_fragment_my_order_all_image);
            if (TextUtil.isEmpty(entity.getConsignee())){
                namedateorder.setText(entity.getCreateNm());
            }else {
                namedateorder.setText(entity.getConsignee());
            }
            ddwliucontentname.setText(entity.getPaymentName());
            if (entity.getOrderStatus().equals("1")){
                ifpayli.setVisibility(View.GONE);
            }
            phonedateorder.setText(entity.getPhone());
            shidateorder.setText(entity.getCity());
            qudateorder.setText(entity.getArea());
            dataadreesdateorder.setText(entity.getDetail());
            timedateorder.setText(entity.getCreateDt());
            item_item_fragment_my_order_all_price.setText(entity.getPrice());
            item_item_fragment_my_order_all_name.setText(entity.getCommodityNm());
            item_item_fragment_my_order_all_choose_one.setText("规 格："+entity.getCommoditySp());
            item_item_fragment_my_order_all_choose_two.setText("颜 色："+entity.getCommodityColor());
            item_item_fragment_my_order_all_number.setText("x"+entity.getNumber());
            ddnumte.setText("共（" + entity.getNumber() + "）" + "件商品合计：" );
            ddallpayte.setText(entity.getTotal()+" 元");
            ddyunfeite.setText(entity.getCarriage());
            ddzhifucode.setText(entity.getOrderId());
            if (TextUtil.isEmpty(entity.getExpressCompany())){
                ddwuliuname.setText("数据缺失~");
                ddwuliucode.setText("888888~");
                ddwuliuname.setTextColor(getResources().getColor(R.color.gray_white));
                ddwuliucode.setTextColor(getResources().getColor(R.color.gray_white));
            }else {
                ddwuliuname.setText(entity.getExpressCompany());
                ddwuliucode.setText(entity.getExpressNumber());
            }
            ddstatue.setText(entity.getState());
            item_fragment_my_order_all_state.setText(entity.getState());

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public interface OnItemClickListener {
        void onupdate();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


    private Dialog payDlg;
    private CheckBox wxPayBox, aliPayBox;
    private int payType = noPayType;
    public static final int noPayType = 0, wxPayType = 1, aliPayType = 2;

    private void showDialog() {
        if (payDlg == null) {
            payDlg = new Dialog(OrderDataActivity.this, R.style.WhiteDialog);
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
//                        gotoPay();
                        if (payType == wxPayType) {
                            toWxPay(ddzhifucode.getText().toString());
                        } else if (payType == aliPayType) {
                            toAliPay(ddzhifucode.getText().toString());
                        }
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


    private void toAliPay(String orderNo) {
        ddhao = orderNo;
        ab = 9;
        new AliPayUtilTwo(OrderDataActivity.this).getSignToPay(orderNo);
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
        String url = Url.getWxSignPayInfo;
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNo);//订单id
        params.put("signId", 1);//订单id
        params.put("userid",UserNative.getId());//订单id
        params.put("foreign", 0);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(OrderDataActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
//                dismissProgressDialog();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                dismissProgressDialog();
                Log.v("lgq","微信APIFH.////==="+s);
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
                        ToastUtil.showWarning(OrderDataActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(BuyOrderActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }
    public void doPay(final PayReq req) {
        ab=6;
        IWXAPI wxApi = WXAPIFactory.createWXAPI(OrderDataActivity.this, null);
        wxApi.registerApp(req.appId);
        wxApi.sendReq(req);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.v("lgq","............onresume");
        if (ab==6){
            verifyPay();
        }
        ab=3;
    }
    /**
     * 验证是否支付成功
     */
    public void verifyPay() {
        String url = Url.getWXpayNotice;
        if (ab==9){
            url=Url.getAlipayNotice;
        }
        RequestParams params = new RequestParams();

        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(OrderDataActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
//                dismissProgressDialog();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                dismissProgressDialog();
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String a = object.getString("out_trade_no");
                        Log.v("lgq","wx支付成功。verifyPay。。"+a+"...."+ddhao);
                        if (a.equals(ddhao)&&ddhao.length()>5){
                            ToastUtil.showSuccess(OrderDataActivity.this, "订单支付成功", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(OrderDataActivity.this, OkPayActivity.class);
                            intent.putExtra("ord",ddhao);
                            intent.putExtra("ifok","ok");
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.showError(OrderDataActivity.this, "订单支付失败", Toast.LENGTH_SHORT);
//                            Intent intent = new Intent(BuyOrderActivity.this, OkPayActivity.class);
//                            intent.putExtra("ord",ddhao);
//                            intent.putExtra("ifok","nook");
//                            startActivity(intent);
                        }
//                        if (payResultListener != null) {
//                            payResultListener.onSuccess();
//                        }
                    } else {
                        ToastUtil.showWarning(OrderDataActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(BuyOrderActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }
}

package com.yuanxin.clan.core.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.OkPayActivity;
import com.yuanxin.clan.core.adapter.MybugnopayAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FragmentMyOrderAllEntity;
import com.yuanxin.clan.core.entity.MyOrderAllEntity;
import com.yuanxin.clan.core.entity.OrderDataActivity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.payment1.AliPayUtilTwo;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/2/25.
 * //待付款  购买
 */
public class FragmentMyOrderHavePaid extends BaseFragment implements OrderDataActivity.OnItemClickListener{
    @BindView(R.id.fragment_my_order_all_recycler_view)
    RecyclerView fragmentMyOrderAllRecyclerView;
    @BindView(R.id.fragment_my_order_all_spring_view)
    SpringView fragmentMyOrderAllSpringView;
    private MybugnopayAdapter adapter;//CompanyInformationDetailChooseAdapter
    private List<FragmentMyOrderAllEntity> allEntities = new ArrayList<>();//所有数据
    private List<MyOrderAllEntity> detailList = new ArrayList<>();//订单数据
    private String consignee, commodityColor, image, commodityPrice, commodityNm, commoditySp,createNm;
    private int  numberOne,commodityNumber;
    private String expressNumber,expressCompany,commodityImage1;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_order_have_paid;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    @Override
    public void onupdate() {
        Log.v("lgq","huidiao......");
        allEntities.clear();
        getGankInto(1);
    }

    private void initRecyclerView() {
        adapter = new MybugnopayAdapter(getContext(), allEntities);
        adapter.setOnItemClickListener(new MybugnopayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent _intent2 = new Intent(getContext(),OrderDataActivity.class);
                _intent2.putExtra("orderUuid",allEntities.get(position).getOrderUuid());
                _intent2.putExtra("entity", allEntities.get(position));
                _intent2.putExtra("nopay",1);
                startActivity(_intent2);
            }
        });
        adapter.setOnItemTobuyClickListener(new MybugnopayAdapter.OnItemTobuyClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ddhao = allEntities.get(position).getOrderUuid();
                showDialog();
            }
        });
        fragmentMyOrderAllRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMyOrderAllRecyclerView.setAdapter(adapter);
        getGankInto(1);
        fragmentMyOrderAllSpringView.setHeader(new RotationHeader(getContext()));
        fragmentMyOrderAllSpringView.setFooter(new RotationFooter(getContext()));
        fragmentMyOrderAllSpringView.setType(SpringView.Type.OVERLAP);
        fragmentMyOrderAllSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentMyOrderAllSpringView.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage = 1;
                Log.v("lgq","j....."+currentPage);
                getGankInto(currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentMyOrderAllSpringView.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage += 1;
                if (currentPage>pageCount){
                    ToastUtil.showWarning(getContext(), "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getGankInto(currentPage);
            }
        });
    }

    private void getGankInto(int page) {
        String url = Url.newgetMymarketOrder;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//企业id
        params.put("pageNumber", page);//企业id
        params.put("orderStatus", 1);//待付款
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        pageCount = Integer.parseInt(object.getString("pageCount"));
                        JSONArray dataArray = object.getJSONArray("data");
                        detailList.clear();//里层数据
                        allEntities.clear();//外层数据
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            String businessId = dataObject.getString("businessId");
                            String orderStatus = dataObject.getString("orderStatus");//订单状态 1待付款 2待收货 3 订单完成
                            String orderId = dataObject.getString("orderId");//订单号
                            String orderUuid = dataObject.getString("orderUuid");//订单号
                            String province = dataObject.getString("province");
                            String city = dataObject.getString("city");
                            String area = dataObject.getString("area");
                            createNm = dataObject.getString("createNm");
                            consignee = dataObject.getString("consignee");
                            String detail = dataObject.getString("detail");
                            String phone = dataObject.getString("phone");
                            String orderPrice = dataObject.getString("orderPrice");//总价
                            String expressCost = dataObject.getString("expressCost");//运费
                            String createDt = dataObject.getString("createDt");//运费
                            String shopList = dataObject.getString("shopList");
                            if (!shopList.equals("null")) {
                                JSONArray shopListArray = dataObject.getJSONArray("shopList");
                                for (int c = 0; c < shopListArray.length(); c++) {
                                    JSONObject shopListObject = shopListArray.getJSONObject(c);
                                    commodityColor = shopListObject.getString("commodityColor");//颜色
                                    commodityImage1 = shopListObject.getString("commodityImage1");//图片
                                    commodityNumber = shopListObject.getInt("commodityNumber");//数量
//                                        try {
//                                            if (!commodityNumber.equals("null")) {
//                                                number = Integer.valueOf(commodityNumber);
//                                                numberOne = numberOne + number;
//                                            } else {
//                                                number = 0;
//                                                numberOne = numberOne + number;
//                                            }
//                                        } catch (Exception e) {
//                                            e.printStackTrace();
//                                        }
                                    commodityColor = shopListObject.getString("commodityColor");//颜色
                                    commodityImage1 = Url.img_domain+shopListObject.getString("commodityImage1")+Url.imageStyle640x640;//图片
                                    image = Url.img_domain + commodityImage1+Url.imageStyle640x640;
                                    commodityNumber = shopListObject.getInt("commodityNumber");//数量
                                    commodityPrice = shopListObject.getString("commodityPrice");//单价
                                    commodityNm = shopListObject.getString("commodityNm");//名称
                                    commoditySp = shopListObject.getString("commoditySp");//规格

                                    MyOrderAllEntity detailEntity = new MyOrderAllEntity();//里层数据
                                    detailEntity.setImage(image);
                                    detailEntity.setPrice(commodityPrice);//单价
                                    detailEntity.setNumber(commodityNumber);//件数
                                    detailEntity.setName(commodityNm);//商品名称
                                    detailEntity.setChooseOne(commoditySp);//规格
                                    detailEntity.setChooseTwo(commodityColor);//颜色
                                    detailList.add(detailEntity);
                                }
                                adapter.getGoodsInfo(detailList);
                            }
                            FragmentMyOrderAllEntity entity = new FragmentMyOrderAllEntity();//外层数据
                            entity.setBusinessId(businessId);
                            entity.setOrderId(orderId);//订单号
                            entity.setNumber(commodityNumber);//共多少件
                            entity.setTotal(orderPrice);//总金额
                            entity.setCarriage(expressCost);//含运费
                            entity.setCreateDt(createDt);
                            entity.setPrice(commodityPrice);
                            entity.setCommoditySp(commoditySp);
                            entity.setCommodityColor(commodityColor);
                            entity.setCommodityNm(commodityNm);
                            entity.setCommodityImage1(commodityImage1);
                            entity.setExpressCompany(expressCompany);
                            entity.setExpressNumber(expressNumber);
                            entity.setOrderUuid(orderUuid);
                            entity.setOrderStatus(orderStatus);
                            entity.setProvince(province);
                            entity.setCity(city);
                            entity.setArea(area);
                            entity.setDetail(detail);
                            entity.setPhone(phone);
                            entity.setConsignee(consignee);
                            entity.setCreateNm(createNm);
                            allEntities.add(entity);

                        }
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");

                }
            }
        });
    }

    private Dialog payDlg;
    private CheckBox wxPayBox, aliPayBox;
    private int payType = noPayType;
    public static final int noPayType = 0, wxPayType = 1, aliPayType = 2;
    private String ddhao;
    private int ab =3 ;
    private PayReq req;

    private void showDialog() {
        if (payDlg == null) {
            payDlg = new Dialog(getActivity(), R.style.WhiteDialog);
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
                            toWxPay(ddhao);
                        } else if (payType == aliPayType) {
                            toAliPay(ddhao);
                        }
                        payDlg.dismiss();
                    }
                }
            });
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
            params.height = (int) (getActivity().getWindowManager().getDefaultDisplay().getHeight() * 0.4);
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
        new AliPayUtilTwo(getActivity()).getSignToPay(orderNo);
    }

    private void toWxPay(String orderNo) {
        ddhao = orderNo;
        getWXSignToPay(orderNo);

    }

    public void getWXSignToPay(String orderNo) {
        String url = Url.getWxSignPayInfo;
        RequestParams params = new RequestParams();
        params.put("orderNumber", orderNo);//订单id
        params.put("signId", 1);//订单id
        params.put("userid",UserNative.getId());//订单id
        params.put("foreign", 0);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
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
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
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
        IWXAPI wxApi = WXAPIFactory.createWXAPI(getActivity(), null);
        wxApi.registerApp(req.appId);
        wxApi.sendReq(req);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v("lgq","............onresume");
        if (ab==6){
            getGankInto(1);
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

        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
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
                            ToastUtil.showSuccess(getActivity(), "订单支付成功", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(getActivity(), OkPayActivity.class);
                            intent.putExtra("ord",ddhao);
                            intent.putExtra("ifok","ok");
                            startActivity(intent);
                        }else {
                            ToastUtil.showError(getActivity(), "订单支付失败", Toast.LENGTH_SHORT);
                            //                            Intent intent = new Intent(BuyOrderActivity.this, OkPayActivity.class);
//                            intent.putExtra("ord",ddhao);
//                            intent.putExtra("ifok","nook");
//                            startActivity(intent);
                        }
//                        if (payResultListener != null) {
//                            payResultListener.onSuccess();
//                        }
                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(BuyOrderActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

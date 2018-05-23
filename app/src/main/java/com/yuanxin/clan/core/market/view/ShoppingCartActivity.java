package com.yuanxin.clan.core.market.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.BusinessIdsentity;
import com.yuanxin.clan.core.entity.Newcareentity;
import com.yuanxin.clan.core.event.PaySuccessEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.ShoppingCommodityAdapter;
import com.yuanxin.clan.core.market.bean.OrderCommodity;
import com.yuanxin.clan.core.market.bean.ShoppingCommodity;
import com.yuanxin.clan.core.recyclerview.DividerItemDecoration;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/1.
 * //购物车类
 */

public class ShoppingCartActivity extends BaseActivity {

    @BindView(R.id.activity_shopping_cart_new_new_left_layout)
    LinearLayout activityShoppingCartNewNewLeftLayout;
    @BindView(R.id.activity_shopping_cart_new_new_tvTitle)
    TextView activityShoppingCartNewNewTvTitle;
    @BindView(R.id.activity_shopping_cart_new_new_edit)
    TextView activityShoppingCartNewNewEdit;
    @BindView(R.id.activity_shopping_cart_new_new_tvEditAll)
    LinearLayout activityShoppingCartNewNewTvEditAll;
    @BindView(R.id.activity_shopping_cart_new_new_title_bar)
    RelativeLayout activityShoppingCartNewNewTitleBar;
    @BindView(R.id.spvShoping)
    SpringView spvShoping;
    @BindView(R.id.activity_shopping_cart_new_new_recycler_view)
    RecyclerView activityShoppingCartNewNewRecyclerView;
    @BindView(R.id.activity_shopping_cart_new_new_checkbox)
    CheckBox activityShoppingCartNewNewCheckbox;
    @BindView(R.id.activity_shopping_cart_new_new_check_box)
    TextView activityShoppingCartNewNewCheckBox;
    @BindView(R.id.activity_shopping_cart_new_new_tvCountMoney)
    TextView activityShoppingCartNewNewTvCountMoney;
    @BindView(R.id.activity_shopping_cart_new_new_btnSettle)
    TextView activityShoppingCartNewNewBtnSettle;
    @BindView(R.id.activity_shopping_cart_new_new_rlBottomBar)
    RelativeLayout activityShoppingCartNewNewRlBottomBar;

    private List<ShoppingCommodity> shoppingCartEntites = new ArrayList<>();
    private List<BusinessIdsentity> mBusinessIdsentities = new ArrayList<>();
    private ShoppingCommodityAdapter adapter;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    public int getViewLayout() {
        return R.layout.activity_shopping_cart_test;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        EventBus.getDefault().register(this);
        getWebInfo();
        initRecyclerView();
    }

    private void getWebInfo() {//购物车详情
        String url = Url.getGetCartDetailList;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
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
                        JSONArray dataArray = object.getJSONArray("data");
                        int numid=0;
                        mBusinessIdsentities.clear();
                        shoppingCartEntites.clear();//里层数据
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            int businessId = dataObject.getInt("businessId");//订单号
                            String businessNm = dataObject.getString("businessNm");//订单号
                            String shopCar = dataObject.getString("shopCar");
                            if (!shopCar.equals("null")) {
                                JSONArray shopCarArray = dataObject.getJSONArray("shopCar");
                                for (int c = 0; c < shopCarArray.length(); c++) {
                                    JSONObject shopListObject = shopCarArray.getJSONObject(c);
                                    numid++;
                                    int carId = shopListObject.getInt("carId");//颜色
                                    String shopList = shopListObject.getString("shopList");
                                    JSONObject object2 = new JSONObject(shopList);

                                    String commodityColor = object2.getString("commodityColor");
                                    String commoditySp = object2.getString("commoditySp");
                                    String commodityNm = object2.getString("commodityNm");
                                    String commodityImage1 =object2.getString("commodityImage1");
                                    String commodityId =object2.getString("commodityId");
                                    String businessId2 =object2.getString("businessId");
                                    int commodityNumber =object2.getInt("commodityNumber");
                                    double commodityPrice =object2.getDouble("commodityPrice");

                                    ShoppingCommodity shoppingCommodity = new ShoppingCommodity();
                                    OrderCommodity orderCommodity = new OrderCommodity();
                                    shoppingCommodity.setCarId(carId);
                                    orderCommodity.setCommodityColor(commodityColor);
                                    orderCommodity.setCommoditySp(commoditySp);
                                    orderCommodity.setCommodityNm(commodityNm);
                                    orderCommodity.setCommodityImage1(commodityImage1);
                                    orderCommodity.setCommodityId(commodityId);
                                    orderCommodity.setBusinessId(businessId2);
                                    orderCommodity.setCommodityNumber(commodityNumber);
                                    orderCommodity.setCommodityPrice(commodityPrice);
                                    shoppingCommodity.setShopList(orderCommodity);
                                    shoppingCommodity.setOneid(c);
                                    shoppingCommodity.setNumid(numid);

                                    shoppingCartEntites.add(shoppingCommodity);
                                }
                            }
                            Newcareentity entity = new Newcareentity();//外层数据
                            BusinessIdsentity businessIdsentity = new BusinessIdsentity();
                            businessIdsentity.setBusinessId(businessId);
                            businessIdsentity.setBusinessNm(businessNm);
                            mBusinessIdsentities.add(businessIdsentity);
                        }
//                        shoppingCartEntites.clear();
//                        shoppingCartEntites.addAll(FastJsonUtils.getObjectsList(object.getString("data"), ShoppingCommodity.class));
//                        Log.v("lgq","cid==="+shoppingCartEntites.get(0).getCarId());
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void initRecyclerView() {
        adapter = new ShoppingCommodityAdapter(getApplicationContext(), shoppingCartEntites,mBusinessIdsentities);
        adapter.setOnMyCheckedChangeListener(new ShoppingCommodityAdapter.OnMyCheckedChangeListener() {
            @Override
            public void onCheckedChange(List<ShoppingCommodity> buyList) {
                float totalMoney = 0;
                for (int i = 0; i < buyList.size(); i++) {
                    OrderCommodity entity = buyList.get(i).getShopList();
                    totalMoney += (entity.getCommodityNumber() * entity.getCommodityPrice());
                    Log.v("Lgq","all====="+totalMoney+".......number==="+entity.getCommodityNumber()+"......"+entity.getCommodityPrice());
                }
                activityShoppingCartNewNewTvCountMoney.setText("共计：" + totalMoney + "元");
                if (buyList.size() == shoppingCartEntites.size()) {
                    activityShoppingCartNewNewCheckbox.setChecked(true);
                } else {
                    activityShoppingCartNewNewCheckbox.setChecked(false);
                }
            }
        });
        adapter.setOnItemClickListener(new ShoppingCommodityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/serviceMall-info&shopId=1975&appFlg=1
//                www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/epMallInfo&commodityId=1362&appFlg=1
                String url;
                if (shoppingCartEntites.get(position).getShopList().getCommodityColor().equals("-1")){
                    url = "http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/serviceMall-info&shopId="+shoppingCartEntites.get(position).getShopList().getCommodityId()+"&appFlg=1";
                }else {
                    url = "http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/epMallInfo&commodityId="+shoppingCartEntites.get(position).getShopList().getCommodityId()+"&appFlg=1";
                }
                startActivity(new Intent(ShoppingCartActivity.this, HomeADactivity.class).putExtra("url", url));
            }
        });
        adapter.setBidListentr(new ShoppingCommodityAdapter.OnBidChangeListener() {
            @Override
            public void onCheckedChange( boolean sel,int id) {
                adapter.onebidcleckALL(sel,id);
            }
        });
        activityShoppingCartNewNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityShoppingCartNewNewRecyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL_LIST));
        activityShoppingCartNewNewRecyclerView.setAdapter(adapter);
        spvShoping.setHeader(new RotationHeader(this));
        spvShoping.setType(SpringView.Type.OVERLAP);
        spvShoping.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                getWebInfo();
                activityShoppingCartNewNewCheckbox.setChecked(false);
                activityShoppingCartNewNewTvCountMoney.setText("共计：0元");
                adapter.clearData();
            }

            @Override
            public void onLoadmore() {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getWebInfo();
//        adapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(PaySuccessEvent paySuccessEvent) {
        //支付成功就关闭购物车页面
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.activity_shopping_cart_new_new_left_layout, R.id.activity_shopping_cart_new_new_tvEditAll, R.id.activity_shopping_cart_new_new_checkbox, R.id.activity_shopping_cart_new_new_btnSettle})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_shopping_cart_new_new_left_layout:
                finish();
                break;
            case R.id.activity_shopping_cart_new_new_tvEditAll://编辑
                if (activityShoppingCartNewNewEdit.getText().toString().equals("编辑")) {
                    activityShoppingCartNewNewBtnSettle.setText("删除");
                    activityShoppingCartNewNewEdit.setText("完成");
                } else {
                    activityShoppingCartNewNewBtnSettle.setText("结算");
                    activityShoppingCartNewNewEdit.setText("编辑");
                }
                break;
            case R.id.activity_shopping_cart_new_new_checkbox://选项框
                if (activityShoppingCartNewNewCheckbox.isChecked()) {
                    adapter.cleckAll(true);
                } else {//全部不选
                    adapter.cleckAll(false);
                }
                break;
            case R.id.activity_shopping_cart_new_new_btnSettle://结算
                if (adapter.getDataList().size()==0) {
                    ToastUtil.showInfo(getApplicationContext(), "没有选择商品", Toast.LENGTH_SHORT);
                    return;
                }
                if (activityShoppingCartNewNewBtnSettle.getText().toString().equals("结算")) {
                    List<OrderCommodity> temp = new ArrayList<>();
                    StringBuffer stringBuffer=new StringBuffer();

                    for (int i = 0; i < adapter.getDataList().size(); i++) {
//                        adapter.getDataList().get(i).getShopList().setExpressCost(10);//默认十元运费
                        temp.add(adapter.getDataList().get(i).getShopList());
                        if (i==adapter.getDataList().size()-1){
                            stringBuffer.append(adapter.getDataList().get(i).getCarId());
                        }else {
                            stringBuffer.append(adapter.getDataList().get(i).getCarId()+";");
                        }

                    }
                    Intent intent = new Intent(ShoppingCartActivity.this, BuyOrderActivity.class);
                    intent.putExtra("datas", (Serializable) temp);
                    intent.putExtra("cid",stringBuffer.toString());
                    intent.putExtra("type", BuyOrderActivity.MULTIPLE);
                    startActivity(intent);
                } else {//删除
                    deleteCartGoods(jointId(adapter.getDataList()));
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intentThree = new Intent("com.example.broadcasttest.ADD_GOODS_TO_CAR_FRESH");
                    localBroadcastManager.sendBroadcast(intentThree); // 发送本地广播
                }
                break;
        }
    }

    /**
     * 商品id
     *
     * @param stringList
     * @return
     */
    public static String jointId(List<ShoppingCommodity> stringList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                sb.append(stringList.get(i).getCarId());
            } else {
                sb.append(";" + stringList.get(i).getCarId());
            }
        }
        return sb.toString();
    }

    private void deleteCartGoods(String carId) {
        String url = Url.updataDeleteCar;
        RequestParams params = new RequestParams();
        params.put("carIds", carId);
        params.put("del", true);
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), "删除成功", Toast.LENGTH_SHORT);
                        getWebInfo();
                        activityShoppingCartNewNewCheckbox.setChecked(false);
                        activityShoppingCartNewNewTvCountMoney.setText("共计：0元");
                        adapter.clearData();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }
}

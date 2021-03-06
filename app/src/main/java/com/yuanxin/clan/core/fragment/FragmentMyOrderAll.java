package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.FragmentMyOrderAllAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FragmentMyOrderAllEntity;
import com.yuanxin.clan.core.entity.MyOrderAllEntity;
import com.yuanxin.clan.core.entity.OrderDataActivity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
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
 * //全部订单 购物
 */
public class FragmentMyOrderAll extends BaseFragment {

    @BindView(R.id.fragment_my_order_all_recycler_view)
    RecyclerView fragmentMyOrderAllRecyclerView;
    @BindView(R.id.fragment_my_order_all_spring_view)
    SpringView fragmentMyOrderAllSpringView;
    private FragmentMyOrderAllAdapter adapter;//CompanyInformationDetailChooseAdapter
    private List<FragmentMyOrderAllEntity> allEntities = new ArrayList<>();//外面的数据
    private List<MyOrderAllEntity> detailList = new ArrayList<>();//里面的数据
    private String status, commodityColor, image, commodityPrice, commodityNm, commoditySp,commodityImage1;
    private String expressNumber,expressCompany,updateNm,createNm,consignee,paymentName;
    private int number, numberOne,commodityNumber;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_order_all;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new FragmentMyOrderAllAdapter(getContext(), allEntities);
        adapter.setOnItemClickListener(new FragmentMyOrderAllAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent _intent2 = new Intent(getContext(),OrderDataActivity.class);
                _intent2.putExtra("orderUuid",allEntities.get(position).getOrderId());
                _intent2.putExtra("entity", allEntities.get(position));
                startActivity(_intent2);
            }
        });



        fragmentMyOrderAllRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMyOrderAllRecyclerView.setAdapter(adapter);
        fragmentMyOrderAllRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
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
                allEntities.clear();//外层数据

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
                    ToastUtil.showInfo(getContext(), "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getGankInto(currentPage);
            }
        });
    }

//全部订单
    private void getGankInto(int page) {
        String url = Url.newgetMymarketOrder;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//企业id
        params.put("pageNumber", page);//企业id
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
//                    Log.v("lgq","全部订单。。。"+s);
                    if (object.getString("success").equals("true")) {
                        pageCount = Integer.parseInt(object.getString("pageCount"));
                        JSONArray dataArray = object.getJSONArray("data");
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            String businessId = dataObject.getString("businessId");
                            String orderId = dataObject.getString("orderUuid");//订单号
                            String createDt = dataObject.getString("createDt");//订单号
                            String orderStatus = dataObject.getString("orderStatus");//订单状态 1待付款 2待收货 3 订单完成
                            if (orderStatus.equals("1")) {
                                status = "待付款";
                            } else if (orderStatus.equals("2")) {
                                status = "待收货";
                            } else if (orderStatus.equals("3")) {
                                status = "订单完成";
                            }else if (orderStatus.equals("5")){
                                status="申请退款中";
                            }else if (orderStatus.equals("4")){
                                status="交易完成";
                            }
                            String province = dataObject.getString("province");
                            String city = dataObject.getString("city");
                            String area = dataObject.getString("area");
                            String detail = dataObject.getString("detail");
                            String phone = dataObject.getString("phone");
                            String orderPrice = dataObject.getString("orderPrice");//总价
                            String expressCost = dataObject.getString("expressCost");//运费
                            String updateDt = dataObject.getString("updateDt");//运费
                            String shopList = dataObject.getString("shopList");
                            expressNumber=dataObject.getString("expressNumber");
                            expressCompany=dataObject.getString("expressCompany");
                            updateNm=dataObject.getString("updateNm");
                            createNm=dataObject.getString("createNm");
                            consignee=dataObject.getString("consignee");
                            paymentName=dataObject.getString("paymentName");
                            if (!shopList.equals("null")) {
                                JSONArray shopListArray = dataObject.getJSONArray("shopList");
                                detailList.clear();//里层数据
                                for (int c = 0; c < shopListArray.length(); c++) {
                                    JSONObject shopListObject = shopListArray.getJSONObject(c);
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
                            }
                            FragmentMyOrderAllEntity entity = new FragmentMyOrderAllEntity();//外层数据
                            entity.setBusinessId(businessId);
                            entity.setOrderId(orderId);//订单号
                            entity.setState(status);//待付款
                            entity.setNumber(commodityNumber);//共多少件
                            entity.setTotal(orderPrice);//总金额
                            entity.setCarriage(expressCost);//含运费
                            entity.setUpdateDt(updateDt);
                            entity.setPrice(commodityPrice);
                            entity.setCommoditySp(commoditySp);
                            entity.setCommodityColor(commodityColor);
                            entity.setCommodityNm(commodityNm);
                            entity.setCommodityImage1(commodityImage1);
                            entity.setExpressCompany(expressCompany);
                            entity.setExpressNumber(expressNumber);
                            entity.setOrderStatus(orderStatus);
                            entity.setCreateDt(createDt);
                            entity.setProvince(province);
                            entity.setCity(city);
                            entity.setArea(area);
                            entity.setDetail(detail);
                            entity.setPhone(phone);
                            entity.setUpdateNm(updateNm);
                            entity.setCreateNm(createNm);
                            entity.setConsignee(consignee);
                            entity.setPaymentName(paymentName);
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


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

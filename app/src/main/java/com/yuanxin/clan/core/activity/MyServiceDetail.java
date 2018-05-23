package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.indicator.MyServiceINadapter;
import com.yuanxin.clan.core.entity.MyService_inEntity;
import com.yuanxin.clan.core.entity.MyService_outEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/3 0003 14:53
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyServiceDetail extends BaseActivity {

    @BindView(R.id.activity_company_introduce_head_image_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_recycler_view)
    RecyclerView fragmentMyAllCrowdFundingRecyclerView;
    @BindView(R.id.unamete)
    TextView unamete;
    @BindView(R.id.uphonete)
    TextView uphonete;
    @BindView(R.id.serviceepnamete)
    TextView serviceepnamete;
    @BindView(R.id.servicedanhaote)
    TextView servicedanhaote;
    @BindView(R.id.servicetimete)
    TextView servicetimete;
    @BindView(R.id.servicenamete)
    TextView servicenamete;
    @BindView(R.id.serviceztte)
    TextView serviceztte;
    @BindView(R.id.serviceimage)
    ImageView serviceimage;


    private MyServiceINadapter adapter;
    private List<MyService_inEntity> mMyService_inEntities = new ArrayList<>();
    private List<MyService_outEntity> mMyService_outEntities = new ArrayList<>();
    private String shopListId,orderUuid,commodityId,zt;

    @Override
    public int getViewLayout() {
        return R.layout.wodefuwudetail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();
    }



    private void initRecyclerView() {
        orderUuid = getIntent().getStringExtra("orderUuid");
        shopListId = getIntent().getStringExtra("shopListId");
        commodityId = getIntent().getStringExtra("commodityId");
        zt = getIntent().getStringExtra("zt");
        adapter = new MyServiceINadapter(MyServiceDetail.this,mMyService_inEntities);

        fragmentMyAllCrowdFundingRecyclerView.setLayoutManager(new LinearLayoutManager(MyServiceDetail.this));
        fragmentMyAllCrowdFundingRecyclerView.setAdapter(adapter);
        fragmentMyAllCrowdFundingRecyclerView.setFocusable(false);//导航栏切换不再focuse
        fragmentMyAllCrowdFundingRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getMyBusinessDistrict();
        if (!zt.equals("0")){
            getMyicttwo();
        }

//        getMyBusinessDistrict(1);

    }

    private void getMyBusinessDistrict( ) {
        String url = Url.orderByUuidAndShopId;
        RequestParams params = new RequestParams();
        params.put("orderUuid", orderUuid);//用户id
        params.put("shopListId", shopListId);//用户id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(MyserviceActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (TextUtil.isEmpty(object.getString("data"))){
                        return;
                    }
                    if (object.getString("success").equals("true")) {
                        mMyService_inEntities.clear();
                        JSONObject dataobj = new JSONObject(object.getString("data"));
                        String name = dataobj.getString("createNm");
                        String phone = dataobj.getString("phone");
                        String epNm = dataobj.getString("epNm");
                        String createDt = dataobj.getString("createDt");
                        String orderUuid = dataobj.getString("orderUuid");
                        unamete.setText(name);
                        uphonete.setText(phone);
                        serviceepnamete.setText(epNm);
                        servicetimete.setText(createDt);
                        servicedanhaote.setText(orderUuid);

                            String shopList = dataobj.getString("shopList");
                            if (!shopList.equals("null")||!TextUtil.isEmpty(shopList)) {
                                JSONArray shopCarArray = dataobj.getJSONArray("shopList");
                                for (int c = 0; c < shopCarArray.length(); c++) {
                                    JSONObject shopListObject = shopCarArray.getJSONObject(c);
                                    String commodityNm = shopListObject.getString("commodityNm");
                                    String procedureId = shopListObject.getString("procedureId");
                                    String procedureName = shopListObject.getString("procedureName");
                                    String commodityImage1 = shopListObject.getString("commodityImage1");
                                    String image = Url.img_domain+commodityImage1+Url.imageStyle640x640;
                                    ImageManager.load(MyServiceDetail.this, image, R.drawable.list_img, serviceimage);
                                    servicenamete.setText(commodityNm);
                                    if (procedureId.equals("1")){
                                        serviceztte.setText("服务已结束");
                                    }else if (procedureId.equals("0")){
                                        serviceztte.setText("服务未开始");
                                    }else {
                                        serviceztte.setText(procedureName);
                                    }

                                }
                            }
                    } else {
                        ToastUtil.showWarning(MyServiceDetail.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    private void getMyicttwo( ) {
        String url = Url.yxServiceProcedure;
        RequestParams params = new RequestParams();
        params.put("explain.explainOrderUuid", orderUuid);//用户id
        params.put("explain.explainShopListId", shopListId);//用户id
        params.put("commodityId", commodityId);//用户id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(MyserviceActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (TextUtil.isEmpty(object.getString("data"))){
                        return;
                    }
                    if (object.getString("success").equals("true")) {
                        JSONArray dataArray = object.getJSONArray("data");
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);

                            String shopList = dataObject.getString("explain");
                            if (!shopList.equals("null")||!TextUtil.isEmpty(shopList)) {
                                JSONObject shopCarArray = dataObject.getJSONObject("explain");
                                    String explainStartDt = shopCarArray.getString("explainStartDt");
                                    String explainReason = shopCarArray.getString("explainReason");

                                    MyService_inEntity inEntity = new MyService_inEntity();
                                    inEntity.setExplainReason(explainReason);
                                    inEntity.setExplainStartDt(explainStartDt);

                                    mMyService_inEntities.add(inEntity);

                            }
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(MyServiceDetail.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @OnClick({R.id.activity_company_introduce_head_image_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_introduce_head_image_layout:
                finish();
                break;
        }
    }
}

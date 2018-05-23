package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.XmtouzAdapter;
import com.yuanxin.clan.core.entity.XmTouzEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/12/5 0005 18:50
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class XmtouzActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{

    @BindView(R.id.left_layout)
    LinearLayout eftLayout;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.kongbai)
    LinearLayout kongbai;
    @BindView(R.id.business_name)
    TextView business_name;
    @BindView(R.id.business_status)
    TextView business_status;


    private int pageCount;
    private int pagenum=1;
    private XmtouzAdapter adapter;
    private int total = 0;
    private List<XmTouzEntity> businessDistrictListEntities = new ArrayList<>();
    private List<String> businessnames = new ArrayList<>();
    private List<String> businessids =new ArrayList<>();
    private List<String> statuss =new ArrayList<>();
    private String investmentIndustryId="-1",businessAreaId,statusid="-1";

    @Override
    public int getViewLayout() {
        return R.layout.xmtouzi_layout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        businessAreaId = getIntent().getStringExtra("businessAreaId");
        initRecyclerView();
        getBusinessidlist();
        getBusinessTop();
        statuss.add(0,"全部");
        statuss.add(1,"未开始");
        statuss.add(2,"已结束");
        statuss.add(3,"进行中");

    }

    private void initRecyclerView() {
        adapter = new XmtouzAdapter(XmtouzActivity.this, businessDistrictListEntities);
        adapter.setOnItemClickListener(new XmtouzAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), TouZiDetailwebActivity.class);//商圈详情
                intent.putExtra("businessAreaId", businessDistrictListEntities.get(position).getBusinessAreaId());
                intent.putExtra("investmentProjectId", businessDistrictListEntities.get(position).getInvestmentProjectId());
                intent.putExtra("title", businessDistrictListEntities.get(position).getInvestmentProjectNm());

                startActivity(intent);
            }
        });
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recycler_view.setNestedScrollingEnabled(false);//禁止滑动
        recycler_view.setAdapter(adapter);
        recycler_view.setFocusable(false);//导航栏切换不再focuse

    }

    @OnClick({R.id.left_layout,R.id.business_name,R.id.business_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.business_name:
                onBusinessPicker();
                break;
            case R.id.business_status:
                onBusinessstatusPicker();
                break;

        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pagenum++;
        if (pagenum>pageCount){
            ToastUtil.showInfo(getApplicationContext(), "已加载完！", Toast.LENGTH_SHORT);
            p2rv.onFooterRefreshComplete(1);

            return;
        }
        getBusinessTop();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        businessDistrictListEntities.clear();
        pagenum=1;
        getBusinessTop();
    }

    private void getBusinessidlist() {
        String url = Url.businessxmidlist;
        RequestParams params = new RequestParams();
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            String investmentIndustryId = businessObject.getString("investmentIndustryId");
                            String investmentIndustryNm = businessObject.getString("investmentIndustryNm");

                            businessnames.add(investmentIndustryNm);
                            businessids.add(investmentIndustryId);


                        }
                        businessnames.add(0,"全部");


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

    public void onBusinessPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        OptionPicker picker = new OptionPicker(this, businessnames);//行业列表名称
        picker.setCycleDisable(true);//不禁用循环
        picker.setTopBackgroundColor(0xFFEEEEEE);//头部背景
        picker.setTopHeight(50);
        picker.setTopLineColor(0xFF33B5E5);//中间分割线
        picker.setTopLineHeight(1);
        picker.setTitleText(isChinese ? "请选择" : "Please pick");
        picker.setTitleTextColor(0xFF999999);//中间字颜色
        picker.setTitleTextSize(13);
        picker.setCancelTextColor(0xFF33B5E5);//取消颜色
        picker.setCancelTextSize(14);
        picker.setSubmitTextColor(0xFF33B5E5);//确定颜色
        picker.setSubmitTextSize(14);
        picker.setTextColor(0xFF33B5E5, 0xFF999999);//选中，非选中
        WheelView.LineConfig config = new WheelView.LineConfig();
        config.setColor(0xFF33B5E5);//线颜色
        config.setAlpha(140);//线透明度
        config.setRatio((float) (1.0 / 8.0));//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(0xFFE1E1E1);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        picker.setSelectedIndex(3);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                business_name.setText(item);
                if (item.equals("全部")){
                    investmentIndustryId = "-1";
                }else {
                    investmentIndustryId=businessids.get(index-1);
                }

                businessDistrictListEntities.clear();
                getBusinessTop();
            }
        });
        picker.show();
    }

    public void onBusinessstatusPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        OptionPicker picker = new OptionPicker(this, statuss);//行业列表名称
        picker.setCycleDisable(true);//不禁用循环
        picker.setTopBackgroundColor(0xFFEEEEEE);//头部背景
        picker.setTopHeight(50);
        picker.setTopLineColor(0xFF33B5E5);//中间分割线
        picker.setTopLineHeight(1);
        picker.setTitleText(isChinese ? "请选择" : "Please pick");
        picker.setTitleTextColor(0xFF999999);//中间字颜色
        picker.setTitleTextSize(13);
        picker.setCancelTextColor(0xFF33B5E5);//取消颜色
        picker.setCancelTextSize(14);
        picker.setSubmitTextColor(0xFF33B5E5);//确定颜色
        picker.setSubmitTextSize(14);
        picker.setTextColor(0xFF33B5E5, 0xFF999999);//选中，非选中
        WheelView.LineConfig config = new WheelView.LineConfig();
        config.setColor(0xFF33B5E5);//线颜色
        config.setAlpha(140);//线透明度
        config.setRatio((float) (1.0 / 8.0));//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(0xFFE1E1E1);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        picker.setSelectedIndex(1);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                business_status.setText(item);
                if (item.equals("全部")){
                    statusid = "-1";
                }else if (item.equals("未开始")){
                    statusid="1";
                }else if (item.equals("已结束")){
                    statusid="2";
                }else if (item.equals("进行中")){
                    statusid="3";
                }

                businessDistrictListEntities.clear();
                getBusinessTop();
            }
        });
        picker.show();
    }

    private void getBusinessTop() {
        String url = Url.businessInvestmentProject;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pagenum);//用户id
        params.put("businessAreaId", businessAreaId);//用户id
        params.put("state", statusid);//用户id
        params.put("investmentIndustryId", investmentIndustryId);//用户id
        params.put("appFlg", 1);//用户id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        pageCount = object.getInt("pageCount");
                        total = object.getInt("total");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        if (total == 0){
                            kongbai.setVisibility(View.VISIBLE);

                            return;
                        }else {
                            kongbai.setVisibility(View.GONE);

                        }
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            String businessAreaId = businessObject.getString("businessAreaId");
                            String investmentProjectId = businessObject.getString("investmentProjectId");
                            String investmentIndustryId = businessObject.getString("investmentIndustryId");
                            String investmentProjectNm = businessObject.getString("investmentProjectNm");

                            double investmentProjectAll = businessObject.getDouble("investmentProjectAll");
                            double investmentProjectSingle = businessObject.getDouble("investmentProjectSingle");
                            double fundraisingMoney = businessObject.getDouble("fundraisingMoney");
                            int buyNumber = businessObject.getInt("buyNumber");
                            int allNumber = businessObject.getInt("allNumber");
                            String startTime = businessObject.getString("startTime");
                            String endTime = businessObject.getString("endTime");
                            String investmentProjectImage1 = businessObject.getString("investmentProjectImage1");

                            String image = Url.img_domain + investmentProjectImage1+Url.imageStyle640x640;//图片


                            XmTouzEntity entity = new XmTouzEntity();
                            entity.setInvestmentProjectImage1(image);
                            entity.setInvestmentProjectSingle(investmentProjectSingle);
                            entity.setEndTime(endTime);
                            entity.setStartTime(startTime);
                            entity.setBusinessAreaId(businessAreaId);
                            entity.setInvestmentIndustryId(investmentIndustryId);
                            entity.setBuyNumber(buyNumber);
                            entity.setAllNumber(allNumber);
                            entity.setFundraisingMoney(fundraisingMoney);
                            entity.setInvestmentProjectId(investmentProjectId);
                            entity.setInvestmentProjectNm(investmentProjectNm);
                            entity.setInvestmentProjectAll(investmentProjectAll);
//                            entity.setAccessPath(accessPath);
                            businessDistrictListEntities.add(entity);
                        }
                        adapter.notifyDataSetChanged();
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.notifyDataSetChanged();
//                            }
//                        }, 500);
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) recycler_view.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(227)*businessDistrictListEntities.size();// 控件的高强制设成20

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
}

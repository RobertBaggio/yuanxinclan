package com.yuanxin.clan.core.news.view;

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
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.bean.NewMarketitem;
import com.yuanxin.clan.core.market.bean.TradesEntity;
import com.yuanxin.clan.core.market.view.TradesDetailActivity;
import com.yuanxin.clan.core.news.adapter.NeardtradesAdapter;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/28 0028 18:36
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MiniTradesListActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.title_text)
    TextView mTextView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;


    private List<TradesEntity> mEntities = new ArrayList<>();

    private List<AdvertisementsEntity> adsTopList = new ArrayList<>();
    private List<NewMarketitem>  mNewMarketitems = new ArrayList<>();
    private NeardtradesAdapter mMyTradesStatusAdapter;
    private int currentPage=1 ;// 当前页面，从0开始计数
    private int totalPage = 0;
    private String typeid;
    private String url;


    @Override
    public int getViewLayout() {
        return R.layout.alllistlayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        typeid = getIntent().getStringExtra("id");
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        mTextView.setText(getIntent().getStringExtra("title"));
        initRecyclerView();
        getBusinesslist(1);
    }



    private void initRecyclerView() {
        mMyTradesStatusAdapter = new NeardtradesAdapter(MiniTradesListActivity.this, mEntities);
        mMyTradesStatusAdapter.setOnItemClickListener(new NeardtradesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MiniTradesListActivity.this, TradesDetailActivity.class);
                intent.putExtra("id", String.valueOf(mEntities.get(position).getExhibitionId()));
                intent.putExtra("name",mEntities.get(position).getHallNm());
                intent.putExtra("image",mEntities.get(position).getExhibitionTitleImg());
                String time = DateDistance.getDistanceTimeToZW(mEntities.get(position).getStarTime())+" 至 "+DateDistance.getDistanceTimeToZW(mEntities.get(position).getEndTime());
                intent.putExtra("time","会展时间："+time);
                startActivity(intent);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(mMyTradesStatusAdapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse

    }


    private void getBusinesslist(int id) {
        RequestParams params = new RequestParams();
        url = Url.newmarketlist;
        params.put("pageNumber",id);
        params.put("industryId",typeid);

        doHttpGet(url, params, new RequestCallback() {
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
                    currentPage = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
//                        org.json.JSONObject jsonObject = object.getJSONObject("data");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            TradesEntity advertisementsEntity = new TradesEntity();
                            String exhibitionTitle = businessObject.getString("exhibitionTitle");
                            String hallNm = businessObject.getString("hallNm");
                            String status = businessObject.getString("status");
                            String businessPhone = businessObject.getString("businessPhone");
                            String exhibitionTitleImg = businessObject.getString("exhibitionTitleImg");
                            String starTime = businessObject.getString("starTime");
                            String endTime = businessObject.getString("endTime");
                            String apart = businessObject.getString("apart");
                            int exhibitionId=businessObject.getInt("exhibitionId");
                            String string = Url.img_domain  + exhibitionTitleImg + Url.imageStyle750x350;
                            advertisementsEntity.setExhibitionTitleImg(string);
                            advertisementsEntity.setExhibitionTitle(exhibitionTitle);
                            advertisementsEntity.setEndTime(endTime);
                            advertisementsEntity.setStarTime(starTime);
                            advertisementsEntity.setHallNm(hallNm);
                            advertisementsEntity.setExhibitionId(exhibitionId);
                            advertisementsEntity.setStatus(status);
                            advertisementsEntity.setBusinessPhone(businessPhone);
                            advertisementsEntity.setApart(apart);
                            mEntities.add(advertisementsEntity);
                        }

                        mMyTradesStatusAdapter.notifyDataSetChanged();
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

    @OnClick(R.id.activity_yuan_xin_fair_new_left_layout)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        totalPage++;
        if (totalPage==currentPage||totalPage>currentPage){
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showInfo(getApplicationContext(), "已加载完", Toast.LENGTH_SHORT);
            return;
        }
        getBusinesslist(totalPage);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        totalPage=1;
        mEntities.clear();
        getBusinesslist(totalPage);
    }
}

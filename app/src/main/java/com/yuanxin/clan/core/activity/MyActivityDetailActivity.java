package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.fragment.ActivityDetailFileFragment;
import com.yuanxin.clan.core.fragment.ActivityDetailOneFragment;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.ActivityDetailAdatper;
import com.yuanxin.clan.core.market.bean.HuiyiBean;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/21 0021 11:31
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyActivityDetailActivity extends BaseActivity {
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_right_layout)
    LinearLayout activityYuanXinFairNewRightLayout;
    @BindView(R.id.activitycontentte)
    TextView activitycontentte;
    @BindView(R.id.activity_yuan_xin_fair_new_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;

    @BindView(R.id.activity_my_crowd_funding_tab_layout)
    TabLayout activityMyCrowdFundingTabLayout;
    @BindView(R.id.activity_my_crowd_funding_viewPager)
    ViewPager activityMyCrowdFundingViewPager;

    private List<HuiyiBean> mHuiyiBeen=new ArrayList<>();
    private MyFragmentAdapter adapter;
    private ActivityDetailAdatper adapterlist;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private ActivityDetailOneFragment mActivityDetailOneFragment;
    private ActivityDetailFileFragment mActivityDetailFileFragment;

    @Override
    public int getViewLayout() {
        return R.layout.myactivitydetaillayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        initview();
        initRecyclerView();
        initViewPager();
    }


    private void initViewPager() {
        mActivityDetailOneFragment = new ActivityDetailOneFragment();
        mActivityDetailFileFragment = new ActivityDetailFileFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(mActivityDetailOneFragment);
        fragmentList.add(mActivityDetailFileFragment);
        titleList = new ArrayList<>();
        titleList.add("活动详情");
        titleList.add("活动资料");
//        titleList.add("需求");
        activityMyCrowdFundingTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(0)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(1)));
//        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(2)));
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyCrowdFundingViewPager.setAdapter(adapter);
        activityMyCrowdFundingViewPager.setOffscreenPageLimit(11);
        activityMyCrowdFundingTabLayout.setupWithViewPager(activityMyCrowdFundingViewPager);
    }

    private void initview(){
        String s = getIntent().getStringExtra("te");
        MyShareUtil.sharedPstring("te",s);
        String filestring = getIntent().getStringExtra("file");
        MyShareUtil.sharedPstring("file",filestring);
        activitycontentte.setText(s);

        String [] stringArr= filestring.split(",");
        for (int i=0;i<stringArr.length;i++){
            Log.v("Lgq","url==="+ Url.img_domain+"/"+stringArr[i]);
            String one = stringArr[i];
            if (TextUtils.isEmpty(one)) {
                continue;
            }
            String [] stringArr2= one.split("\\.");
            String two = stringArr2[0];
            String type=stringArr2[1];
            String [] stringArr3= two.split("/");
            String name = stringArr3[stringArr3.length-1];
            String url = Url.img_domain+"/"+stringArr[i];

            HuiyiBean huiyiBean = new HuiyiBean();
            huiyiBean.setName(name);
            huiyiBean.setUrl(url);
            huiyiBean.setType(type);

            mHuiyiBeen.add(huiyiBean);
        }
    }

    private void initRecyclerView() {
        adapterlist = new ActivityDetailAdatper(MyActivityDetailActivity.this, mHuiyiBeen);
        adapterlist.setOnItemClickListener(new ActivityDetailAdatper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mHuiyiBeen.get(position).getUrl()));
                startActivity(webIntent);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(adapterlist);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse

        adapterlist.notifyDataSetChanged();

    }
    @OnClick(R.id.activity_yuan_xin_fair_new_left_layout)
    public void onClick() {
        finish();
    }
}


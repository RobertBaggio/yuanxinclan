package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.fragment.FragmentMyTeamworkBusinessAll;
import com.yuanxin.clan.core.fragment.FragmentMyTeamworkBusinessNeed;
import com.yuanxin.clan.core.fragment.FragmentMyTeamworkBusinessSupply;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/28.
 * 合作企业
 */
public class TeamworkBusinessActivity extends BaseActivity {

    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_my_teamwork_business_tab_layout)
    TabLayout activityMyTeamworkBusinessTabLayout;
    @BindView(R.id.activity_my_teamwork_business_viewPager)
    ViewPager activityMyTeamworkBusinessViewPager;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private FragmentMyTeamworkBusinessAll fragmentMyTeamworkBusinessAll;//我的服务商
    private FragmentMyTeamworkBusinessSupply fragmentMyTeamworkBusinessSupply;//我的供应商
    private FragmentMyTeamworkBusinessNeed fragmentMyTeamworkBusinessNeed;//我的客户
    private MyFragmentAdapter adapter;


    @Override
    public int getViewLayout() {
        return R.layout.activity_my_teamwork_business;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initViewPager();
        Log.v("lgq","返回。。。。。。。。initView");
    }

    @Override
    protected void onResume() {
//        initViewPager();
        super.onResume();
        Log.v("lgq","返回。。。。。。。。合作activity");
//        initViewPager();
        adapter.notifyDataSetChanged();
    }

    private void initViewPager() {
        fragmentMyTeamworkBusinessAll = new FragmentMyTeamworkBusinessAll();
        fragmentMyTeamworkBusinessSupply = new FragmentMyTeamworkBusinessSupply();
        fragmentMyTeamworkBusinessNeed = new FragmentMyTeamworkBusinessNeed();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyTeamworkBusinessAll);
        fragmentList.add(fragmentMyTeamworkBusinessSupply);
        fragmentList.add(fragmentMyTeamworkBusinessNeed);

        titleList = new ArrayList<>();
        titleList.add("我的服务商");
        titleList.add("我的供应商");
        titleList.add("我的客户");

//        activityYuanXinFairTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可以滚动
        activityMyTeamworkBusinessTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动


        activityMyTeamworkBusinessTabLayout.addTab(activityMyTeamworkBusinessTabLayout.newTab().setText(titleList.get(0)));
        activityMyTeamworkBusinessTabLayout.addTab(activityMyTeamworkBusinessTabLayout.newTab().setText(titleList.get(1)));
        activityMyTeamworkBusinessTabLayout.addTab(activityMyTeamworkBusinessTabLayout.newTab().setText(titleList.get(2)));
//        fragmentATablayout.addTab(fragmentATablayout.newTab().setText(titleList.get(2)));
//        fragmentATablayout.addTab(fragmentATablayout.newTab().setText(titleList.get(3)));
//        fragmentATablayout.addTab(fragmentATablayout.newTab().setText(titleList.get(4)));

        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyTeamworkBusinessViewPager.setAdapter(adapter);
        activityMyTeamworkBusinessViewPager.setOffscreenPageLimit(11);
        activityMyTeamworkBusinessTabLayout.setupWithViewPager(activityMyTeamworkBusinessViewPager);
    }


    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_right_layout:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("lgq","返回。。。。。。。。onStop");
    }

}

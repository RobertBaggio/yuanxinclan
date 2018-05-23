package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.fragment.FragmentMyMarketNeed;
import com.yuanxin.clan.core.fragment.FragmentMyMarketSupply;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/28.
 */
//我的集市
public class MyMarketActivity extends BaseActivity {


    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_my_market_tab_layout)
    TabLayout activityMyMarketTabLayout;
    @BindView(R.id.activity_my_market_viewPager)
    ViewPager activityMyMarketViewPager;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private FragmentMyMarketSupply fragmentMyMarketSupply;//我的集市 供应
    private FragmentMyMarketNeed fragmentMyMarketNeed;////在售礼品
    private MyFragmentAdapter adapter;


    @Override
    public int getViewLayout() {
        return R.layout.activity_my_market;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initViewPager();
    }

    private void initViewPager() {
        fragmentMyMarketSupply = new FragmentMyMarketSupply();//集市商品
        fragmentMyMarketNeed = new FragmentMyMarketNeed();//定制商品
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyMarketSupply);
        fragmentList.add(fragmentMyMarketNeed);
        titleList = new ArrayList<>();
        titleList.add("集市商品");
        titleList.add("在售礼品");
        activityMyMarketTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityMyMarketTabLayout.addTab(activityMyMarketTabLayout.newTab().setText(titleList.get(0)));
        activityMyMarketTabLayout.addTab(activityMyMarketTabLayout.newTab().setText(titleList.get(1)));
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyMarketViewPager.setAdapter(adapter);
        activityMyMarketViewPager.setOffscreenPageLimit(2);
        activityMyMarketTabLayout.setupWithViewPager(activityMyMarketViewPager);
    }

    @OnClick({R.id.activity_exchange_phone_left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
        }
    }


}

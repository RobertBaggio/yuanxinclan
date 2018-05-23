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
import com.yuanxin.clan.core.fragment.FragmentMyOrderAll;
import com.yuanxin.clan.core.fragment.FragmentMyOrderFinish;
import com.yuanxin.clan.core.fragment.FragmentMyOrderHavePaid;
import com.yuanxin.clan.core.fragment.FragmentMyOrderNonPayment;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/25.
 * //我的订单
 */
public class MyOrderActivity extends BaseActivity {

    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_my_order_tab_layout)
    TabLayout activityMyOrderTabLayout;
    @BindView(R.id.activity_my_order_view_pager)
    ViewPager activityMyOrderViewPager;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private FragmentMyOrderAll fragmentMyOrderAll;//全部
    private FragmentMyOrderHavePaid fragmentMyOrderHavePaid;//待付款
    private FragmentMyOrderNonPayment fragmentMyOrderNonPayment;//待收货
    private FragmentMyOrderFinish fragmentMyOrderFinish;//已完成
    private MyFragmentAdapter adapter;


    @Override
    public int getViewLayout() {
        return R.layout.activity_my_order;
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
        fragmentMyOrderAll = new FragmentMyOrderAll();//全部订单
        fragmentMyOrderHavePaid = new FragmentMyOrderHavePaid();//待付款
        fragmentMyOrderNonPayment = new FragmentMyOrderNonPayment();//待收货
        fragmentMyOrderFinish = new FragmentMyOrderFinish();//已完成
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyOrderAll);
        fragmentList.add(fragmentMyOrderHavePaid);
        fragmentList.add(fragmentMyOrderNonPayment);
        fragmentList.add(fragmentMyOrderFinish);

        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("待付款");
        titleList.add("待收货");
        titleList.add("已完成");
        activityMyOrderTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(0)));
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(1)));
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(2)));
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(3)));
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyOrderViewPager.setAdapter(adapter);
        activityMyOrderViewPager.setOffscreenPageLimit(4);
        activityMyOrderTabLayout.setupWithViewPager(activityMyOrderViewPager);
    }


    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_right_layout://卖货订单
                startActivity(new Intent(MyOrderActivity.this, MySellOrderActivity.class));
//                finish();
                break;
        }
    }


}

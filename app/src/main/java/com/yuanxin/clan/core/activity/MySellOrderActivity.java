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
import com.yuanxin.clan.core.fragment.FragmentMySellOrderAll;
import com.yuanxin.clan.core.fragment.FragmentMySellOrderFinish;
import com.yuanxin.clan.core.fragment.FragmentMySellOrderHavePaid;
import com.yuanxin.clan.core.fragment.FragmentMySellOrderNonPayment;
import com.yuanxin.clan.core.fragment.OkSellFragment;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/21.
 * //卖货订单
 */
public class MySellOrderActivity extends BaseActivity {
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
    private FragmentMySellOrderAll fragmentMySellOrderAll;//待付款
    private FragmentMySellOrderHavePaid fragmentMySellOrderHavePaid;//待收货
    private FragmentMySellOrderNonPayment fragmentMySellOrderNonPayment;//已发货
    private FragmentMySellOrderFinish fragmentMySellOrderFinish;//待退款
    private OkSellFragment mOkSellFragment;//已完成
    private MyFragmentAdapter adapter;

    @Override
    public int getViewLayout() {
        return R.layout.activity_my_sell_order;
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
        fragmentMySellOrderAll = new FragmentMySellOrderAll();
        fragmentMySellOrderHavePaid = new FragmentMySellOrderHavePaid();
        fragmentMySellOrderNonPayment = new FragmentMySellOrderNonPayment();
        fragmentMySellOrderFinish = new FragmentMySellOrderFinish();
        mOkSellFragment = new OkSellFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMySellOrderAll);
        fragmentList.add(fragmentMySellOrderHavePaid);
        fragmentList.add(fragmentMySellOrderNonPayment);
        fragmentList.add(fragmentMySellOrderFinish);
        fragmentList.add(mOkSellFragment);

        titleList = new ArrayList<>();
        titleList.add("待付款");
        titleList.add("待发货");
        titleList.add("已发货");
        titleList.add("待退款");
        titleList.add("已完成");
        activityMyOrderTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(0)));
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(1)));
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(2)));
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(3)));
        activityMyOrderTabLayout.addTab(activityMyOrderTabLayout.newTab().setText(titleList.get(4)));
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
                startActivity(new Intent(MySellOrderActivity.this, MyOrderActivity.class));
                finish();
                break;
        }
    }
}

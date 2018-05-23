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
import com.yuanxin.clan.core.fragment.MeToAppointmentFragment;
import com.yuanxin.clan.core.fragment.ToMeAppointmentFmt;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * 我的预约
 */

public class AppointmentActivity extends BaseActivity {
    @BindView(R.id.activity_my_crowd_funding_tab_layout)
    TabLayout activityMyCrowdFundingTabLayout;
    @BindView(R.id.activity_my_crowd_funding_viewPager)
    ViewPager activityMyCrowdFundingViewPager;
    @BindView(R.id.activity_business_district_library_left_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.activity_business_district_library_right_layout)
    LinearLayout activityBusinessDistrictLibraryRightLayout;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private MeToAppointmentFragment fragmentMyAddBusiness;
    private ToMeAppointmentFmt fragmentMyPublishBusiness;
    private MyFragmentAdapter adapter;


    @Override
    public int getViewLayout() {
        return R.layout.myappointmentlayout;
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
        fragmentMyAddBusiness = new MeToAppointmentFragment();
        fragmentMyPublishBusiness = new ToMeAppointmentFmt();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyAddBusiness);
        fragmentList.add(fragmentMyPublishBusiness);
        titleList = new ArrayList<>();
        titleList.add("我预约的");
        titleList.add("预约我的");
        activityMyCrowdFundingTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(0)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(1)));
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyCrowdFundingViewPager.setAdapter(adapter);
        activityMyCrowdFundingViewPager.setOffscreenPageLimit(11);
        activityMyCrowdFundingTabLayout.setupWithViewPager(activityMyCrowdFundingViewPager);
    }

    @OnClick({R.id.activity_business_district_library_left_layout, R.id.activity_business_district_library_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_district_library_left_layout:
                finish();
                break;
            case R.id.activity_business_district_library_right_layout:
//                startActivity(new Intent(MyBusinsCActivity.this, MyBusinessDistrictSetUpActivity.class));//创建
                break;
        }
    }
}

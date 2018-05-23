package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.fragment.FragmentMyAllCrowdFunding;
import com.yuanxin.clan.core.fragment.FragmentMyLaunchCrowdFunding;
import com.yuanxin.clan.core.fragment.FragmentMyTakePartCrowdFunding;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/22.
 */
//我的众筹
public class MyCrowdFundingActivity extends BaseActivity {

    @BindView(R.id.activity_my_crowd_funding_head_image_layout)
    LinearLayout activityMyCrowdFundingHeadImageLayout;
    @BindView(R.id.activity_my_crowd_funding_right_layout)
    LinearLayout activityMyCrowdFundingRightLayout;
    @BindView(R.id.activity_my_crowd_funding_head_layout)
    RelativeLayout activityMyCrowdFundingHeadLayout;
    @BindView(R.id.activity_my_crowd_funding_tab_layout)
    TabLayout activityMyCrowdFundingTabLayout;
    @BindView(R.id.activity_my_crowd_funding_viewPager)
    ViewPager activityMyCrowdFundingViewPager;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private FragmentMyAllCrowdFunding fragmentMyAllCrowdFunding;
    private FragmentMyTakePartCrowdFunding fragmentMyTakePartCrowdFunding;
    private FragmentMyLaunchCrowdFunding fragmentMyLaunchCrowdFunding;//发起
    private MyFragmentAdapter adapter;


    @Override
    public int getViewLayout() {
        return R.layout.activity_my_crowd_funding;
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
        fragmentMyAllCrowdFunding = new FragmentMyAllCrowdFunding();
        fragmentMyTakePartCrowdFunding = new FragmentMyTakePartCrowdFunding();
        fragmentMyLaunchCrowdFunding = new FragmentMyLaunchCrowdFunding();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyAllCrowdFunding);
        fragmentList.add(fragmentMyTakePartCrowdFunding);
        fragmentList.add(fragmentMyLaunchCrowdFunding);

        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("我参与的");
        titleList.add("我发起的");

        activityMyCrowdFundingTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动


        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(0)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(1)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(2)));

        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyCrowdFundingViewPager.setAdapter(adapter);
        activityMyCrowdFundingViewPager.setOffscreenPageLimit(11);
        activityMyCrowdFundingTabLayout.setupWithViewPager(activityMyCrowdFundingViewPager);
    }

    @OnClick({R.id.activity_my_crowd_funding_head_image_layout, R.id.activity_my_crowd_funding_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_my_crowd_funding_head_image_layout:
                finish();
                break;
            case R.id.activity_my_crowd_funding_right_layout:
                startActivity(new Intent(this, LaunchCrowdFundingActivity.class));
                break;

        }
    }


}

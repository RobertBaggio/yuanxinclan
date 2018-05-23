package com.yuanxin.clan.core.market.view;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.fragment.AllGongXuFragment;
import com.yuanxin.clan.core.fragment.GongYinFragment;
import com.yuanxin.clan.core.fragment.XuQiuFragment;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/26 0026 13:40
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class GongXuActivity extends BaseActivity {

    @BindView(R.id.activity_gx_layout)
    TabLayout activityMyCrowdFundingTabLayout;
    @BindView(R.id.activity_mygx_viewPager)
    ViewPager activityMyCrowdFundingViewPager;
    @BindView(R.id.left_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.right_layout)
    LinearLayout right_layout;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private AllGongXuFragment fragmentMyAddBusiness;
    private GongYinFragment fragmentMyPublishBusiness;
    private XuQiuFragment mXuQiuFragment;
    private MyFragmentAdapter adapter;
    private PopupWindow popupWindow;


    @Override
    public int getViewLayout() {
        return R.layout.gongxu_activity;
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
        fragmentMyAddBusiness = new AllGongXuFragment();
        fragmentMyPublishBusiness = new GongYinFragment();
        mXuQiuFragment = new XuQiuFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyAddBusiness);
        fragmentList.add(fragmentMyPublishBusiness);
        fragmentList.add(mXuQiuFragment);
        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("供应");
        titleList.add("需求");
        activityMyCrowdFundingTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(0)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(1)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(2)));
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyCrowdFundingViewPager.setAdapter(adapter);
        activityMyCrowdFundingViewPager.setOffscreenPageLimit(11);
        activityMyCrowdFundingTabLayout.setupWithViewPager(activityMyCrowdFundingViewPager);
    }

    @OnClick({R.id.left_layout,R.id.right_layout, })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                finish();
                break;
            case R.id.right_layout:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//                showxiala();
                showAddDialog();
                break;
//            case R.id.activity_business_district_library_right_layout:
//                startActivity(new Intent(MyBusinessDistrictActivity.this, MyBusinessDistrictSetUpActivity.class));//创建
//                break;
        }
    }

    Dialog addDlg;
    private void showAddDialog() {
        if (addDlg == null) {
            addDlg = new Dialog(GongXuActivity.this, R.style.WhiteDialog);
            Window window = addDlg.getWindow();
            window.setContentView(R.layout.gxfabuxiala);
            View oneLayout = window.findViewById(R.id.dialog_huan_xin_layout_one);
            View twoLayout = window.findViewById(R.id.dialog_huan_xin_layout_two);

            oneLayout.setTag(addDlg);
            twoLayout.setTag(addDlg);

            oneLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(GongXuActivity.this, OutputGongyActivity.class).putExtra("type","new"));
                    addDlg.dismiss();
                }
            });
            twoLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(GongXuActivity.this, OutputXuQiuActivity.class).putExtra("type","new")); //添加  对方同意后 加到通讯录 点击通讯录 跳到聊天详情 当有聊天记录 会话列表就会有显示
                    addDlg.dismiss();
                }
            });

            Display display = GongXuActivity.this.getWindowManager().getDefaultDisplay();
            int minHeight = (int) (display.getHeight() * 0.2);              //使用这种方式更改了dialog的框高
            LinearLayout layout = (LinearLayout) window.findViewById(R.id.dialog_huan_xin_layout);
            layout.setMinimumHeight(minHeight);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (display.getWidth() * 0.5);                     //使用这种方式更改了dialog的框宽
            window.setGravity(Gravity.TOP | Gravity.RIGHT);
            window.setAttributes(params);
        }
        addDlg.show();
    }

}


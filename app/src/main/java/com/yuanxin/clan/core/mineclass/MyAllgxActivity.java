package com.yuanxin.clan.core.mineclass;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.market.view.OutputGongyActivity;
import com.yuanxin.clan.core.market.view.OutputXuQiuActivity;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/10 0010 11:19
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyAllgxActivity extends BaseActivity {
    @BindView(R.id.activity_shangji_tab_layout)
    TabLayout activityMyCrowdFundingTabLayout;
    @BindView(R.id.activity_my_crowd_funding_viewPager)
    ViewPager activityMyCrowdFundingViewPager;
    @BindView(R.id.left_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.right_layout)
    LinearLayout right_layout;
    @BindView(R.id.wdgxte)
    TextView wdgxte;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private MyAllgxFragment fragmentMyAddBusiness;
    private MyGongYinFragment fragmentMyPublishBusiness;
    private MyXuQiuFragment mXuQiuFragment;
    private MyFragmentAdapter adapter;
    private PopupWindow popupWindow;


    @Override
    public int getViewLayout() {
        return R.layout.gongxulayout;
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
        wdgxte.setText("我的商机");
        fragmentMyAddBusiness = new MyAllgxFragment();
        fragmentMyPublishBusiness = new MyGongYinFragment();
        mXuQiuFragment = new MyXuQiuFragment();
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
            addDlg = new Dialog(MyAllgxActivity.this, R.style.WhiteDialog);
            Window window = addDlg.getWindow();
            window.setContentView(R.layout.gxfabuxiala);
            View oneLayout = window.findViewById(R.id.dialog_huan_xin_layout_one);
            View twoLayout = window.findViewById(R.id.dialog_huan_xin_layout_two);

            oneLayout.setTag(addDlg);
            twoLayout.setTag(addDlg);

            oneLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MyAllgxActivity.this, OutputGongyActivity.class).putExtra("type","new"));
                    addDlg.dismiss();
                }
            });
            twoLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MyAllgxActivity.this, OutputXuQiuActivity.class).putExtra("type","new")); //添加  对方同意后 加到通讯录 点击通讯录 跳到聊天详情 当有聊天记录 会话列表就会有显示
                    addDlg.dismiss();
                }
            });

            Display display = MyAllgxActivity.this.getWindowManager().getDefaultDisplay();
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

    public void showxiala() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.select_gongxuxiala, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentview, 400, 230);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        RelativeLayout gy = (RelativeLayout) contentview.findViewById(R.id.fabugongyre);
        RelativeLayout xq = (RelativeLayout) contentview.findViewById(R.id.fabuxuqiure);
        gy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                startActivity(new Intent(MyAllgxActivity.this, OutputGongyActivity.class).putExtra("type","new"));
            }
        });
        xq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                startActivity(new Intent(MyAllgxActivity.this, OutputXuQiuActivity.class).putExtra("type","new"));
            }
        });
//        tt.setOnClickListener(this);
//        nv.setOnClickListener(this);

        contentview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();
                    return true;
                }
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new Drawable() {
            @Override
            public void draw(Canvas canvas) {

            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return PixelFormat.UNKNOWN;
            }
        });
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popupWindow.showAsDropDown(right_layout);
        popupWindow.showAtLocation(right_layout, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}

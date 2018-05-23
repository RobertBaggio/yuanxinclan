package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.MainActivity;
import com.yuanxin.clan.core.adapter.GuidePageAdapter;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.MainApplication;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ProjectName: new_yuanxin
 * Describe:
 * Author: xjc
 * Date: 2017/8/28 0028 9:49
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class GuideActivity extends BaseActivity implements  ViewPager.OnPageChangeListener{

    @BindView(R.id.vp_guide)
    ViewPager vp;

    private int typeid;

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    public int getViewLayout() {
        return R.layout.guide_activity;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initData();
        typeid = getIntent().getIntExtra("type",9);
        MyShareUtil.sharedPint("vc", MainApplication.getVersionCode());
        MyShareUtil.sharedPint("topin",1);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 3)
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                        startActivity(intent);
//                    if (typeid ==1){
//                        Intent intent = new Intent(GuideActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                    }else if (typeid==2){
//                        Intent intent = new Intent(GuideActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    }
                    finish();
                }
            }, 500);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    public void initData() {
        List<View> guideList = new ArrayList<>();
        guideList.add(getGuideView(R.drawable.guide_1));
        guideList.add(getGuideView(R.drawable.guide_2));
        guideList.add(getGuideView(R.drawable.guide_3));
//        guideList.add(getGuideView(R.drawable.guide_4));
        RelativeLayout rl = new RelativeLayout(this);
        rl.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        rl.setBackgroundColor(this.getResources().getColor(R.color.tran));
        guideList.add(rl);
        GuidePageAdapter vpAdapter = new GuidePageAdapter(guideList);
        vp.setAdapter(vpAdapter);
        vp.setOnPageChangeListener(this);
    }

    private View getGuideView(int nDrawableID) {
        ImageView iv = new ImageView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        iv.setLayoutParams(lp);
        iv.setBackgroundColor(this.getResources().getColor(R.color.mine_page_color));
        iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
        iv.setImageResource(nDrawableID);
        return iv;
    }
}

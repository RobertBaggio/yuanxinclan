package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.adapter.TradeslistAdapter;
import com.yuanxin.clan.core.market.bean.TradesZGEntity;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/8 0008 18:09
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class NoFinishActivity extends BaseActivity {
    //public class TradesListActivity extends BaseActivity {
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_company_information_detail_middle_text)
    TextView titleTextView;
    @BindView(R.id.closete)
    TextView closete;



    private List<TradesZGEntity> adsTopList = new ArrayList<>();
    private TradeslistAdapter mTradeslistAdapter;

    @Override
    public int getViewLayout() {
        return R.layout.nofinis;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        String title = getIntent().getStringExtra("title");
        if (!TextUtil.isEmpty(title)) {
            titleTextView.setText(title);
        }
    }



    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.closete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
            case R.id.closete:
                finish();
                break;
        }
    }

}

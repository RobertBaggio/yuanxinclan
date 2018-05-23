package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/5/8.
 */
public class NetworkDetailActivity extends BaseActivity {
    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_complaint_advice_edit)
    TextView activityComplaintAdviceEdit;

    @Override
    public int getViewLayout() {
        return R.layout.activity_network_detail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }

    @OnClick(R.id.activity_exchange_phone_left_layout)
    public void onClick() {
        finish();
    }
}



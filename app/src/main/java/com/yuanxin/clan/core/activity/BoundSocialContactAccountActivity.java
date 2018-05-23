package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/27.
 */
public class BoundSocialContactAccountActivity extends BaseActivity {
    @BindView(R.id.activity_bound_social_contact_account_left_layout)
    LinearLayout activityBoundSocialContactAccountLeftLayout;
    @BindView(R.id.activity_bound_social_contact_account_right_layout)
    LinearLayout activityBoundSocialContactAccountRightLayout;
    @BindView(R.id.activity_bound_social_contact_account_qq_layout)
    RelativeLayout activityBoundSocialContactAccountQqLayout;
    @BindView(R.id.activity_bound_social_contact_account_wei_xin_layout)
    RelativeLayout activityBoundSocialContactAccountWeiXinLayout;


    @Override
    public int getViewLayout() {
        return R.layout.activity_bound_social_contact_account;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }

    @OnClick({R.id.activity_bound_social_contact_account_left_layout, R.id.activity_bound_social_contact_account_qq_layout, R.id.activity_bound_social_contact_account_wei_xin_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_bound_social_contact_account_left_layout:
                finish();
                break;
            case R.id.activity_bound_social_contact_account_qq_layout:
                break;
            case R.id.activity_bound_social_contact_account_wei_xin_layout:
                break;
        }
    }
}

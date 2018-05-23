package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/28.
 */
public class MyAccountSafeActivity extends BaseActivity {


    @BindView(R.id.window_head_left_image)
    ImageView windowHeadLeftImage;
    @BindView(R.id.window_head_left_image_layout)
    LinearLayout windowHeadLeftImageLayout;
    @BindView(R.id.window_head_left_text)
    TextView windowHeadLeftText;
    @BindView(R.id.window_head_left_layout)
    LinearLayout windowHeadLeftLayout;
    @BindView(R.id.window_head_name)
    TextView windowHeadName;
    @BindView(R.id.center_headname_ll)
    LinearLayout centerHeadnameLl;
    @BindView(R.id.window_head_right_layout)
    LinearLayout windowHeadRightLayout;
    @BindView(R.id.window_head_layout)
    RelativeLayout windowHeadLayout;
    @BindView(R.id.activity_account_safe_bound_phone)
    RelativeLayout activityAccountSafeBoundPhone;
    @BindView(R.id.activity_account_safe_bound_account_bound)
    RelativeLayout activityAccountSafeBoundAccountBound;
    @BindView(R.id.activity_account_safe_bound_setting_password)
    RelativeLayout activityAccountSafeBoundSettingPassword;


    @Override
    public int getViewLayout() {
        return R.layout.activity_account_safe;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initView();
    }

    private void initView() {
        windowHeadLayout.setBackgroundColor(getResources().getColor(R.color.businesstop));
        windowHeadLeftText.setVisibility(View.GONE);
        windowHeadLeftImage.setImageResource(R.drawable.ease_mm_title_back);
        windowHeadName.setTextColor(getResources().getColor(R.color.white));
        windowHeadRightLayout.setVisibility(View.GONE);
        windowHeadName.setText("账号安全");
    }


    @OnClick({R.id.window_head_left_image_layout, R.id.activity_account_safe_bound_phone, R.id.activity_account_safe_bound_account_bound, R.id.activity_account_safe_bound_setting_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.window_head_left_image_layout:
                finish();
                break;
            case R.id.activity_account_safe_bound_phone://换绑手机
                startActivity(new Intent(MyAccountSafeActivity.this, ExchangePhoneActivity.class));
                break;
            case R.id.activity_account_safe_bound_account_bound://社交账号绑定
                startActivity(new Intent(MyAccountSafeActivity.this, BoundSocialContactAccountActivity.class));
                break;
            case R.id.activity_account_safe_bound_setting_password://设置密码
                startActivity(new Intent(MyAccountSafeActivity.this, ChangePasswordActivity.class));
                break;
        }
    }
}

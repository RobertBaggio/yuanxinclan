package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/28.
 */
public class ThinkTankGoToApproveActivity extends BaseActivity {
    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_think_tank_go_to_approve_head_layout)
    RelativeLayout activityThinkTankGoToApproveHeadLayout;
    @BindView(R.id.activity_think_tank_go_to_approve_no_specialist_text)
    TextView activityThinkTankGoToApproveNoSpecialistText;
    @BindView(R.id.activity_think_tank_go_to_approve_no_specialist_all)
    TextView activityThinkTankGoToApproveNoSpecialistAll;
    @BindView(R.id.activity_think_tank_go_to_approve_button)
    Button activityThinkTankGoToApproveButton;


    @Override
    public int getViewLayout() {
        return R.layout.activity_think_tank_go_to_approve;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }


    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_think_tank_go_to_approve_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_think_tank_go_to_approve_button:
                Intent intent = new Intent(ThinkTankGoToApproveActivity.this, ThinkTankDetailEditActivity.class);
                intent.putExtra("state", "4");
                startActivity(intent);
                finish();
                break;
        }
    }
}

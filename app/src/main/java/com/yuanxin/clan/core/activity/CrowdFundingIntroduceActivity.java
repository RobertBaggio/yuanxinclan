package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/30.
 */
public class CrowdFundingIntroduceActivity extends BaseActivity {
    @BindView(R.id.activity_crowd_funding_introduce_head_image_layout)
    LinearLayout activityCrowdFundingIntroduceHeadImageLayout;
    @BindView(R.id.activity_crowd_funding_introduce_right_layout)
    LinearLayout activityCrowdFundingIntroduceRightLayout;
    @BindView(R.id.activity_crowd_funding_introduce_introduce_edit)
    EditText activityCrowdFundingIntroduceIntroduceEdit;


    @Override
    public int getViewLayout() {
        return R.layout.activity_crowd_funding_introduce;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        getInfo();
    }

    private void getInfo() {
//        Intent intent=getIntent();
//        String introduce=intent.getStringExtra("introduce");
//        activityCompanyIntroduceEdit.setText(introduce);
    }


    @OnClick({R.id.activity_crowd_funding_introduce_head_image_layout, R.id.activity_crowd_funding_introduce_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_crowd_funding_introduce_head_image_layout:
                finish();
                break;
            case R.id.activity_crowd_funding_introduce_right_layout:
                String companyIntroduce = activityCrowdFundingIntroduceIntroduceEdit.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("companyIntroduce", companyIntroduce);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

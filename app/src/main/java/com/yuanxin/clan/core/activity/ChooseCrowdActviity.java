package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/30.
 */
public class ChooseCrowdActviity extends BaseActivity {
    @BindView(R.id.activity_crowd_funding_introduce_head_image_layout)
    LinearLayout activityCrowdFundingIntroduceHeadImageLayout;
    @BindView(R.id.activity_crowd_funding_introduce_right_layout)
    LinearLayout activityCrowdFundingIntroduceRightLayout;
    @BindView(R.id.activity_choose_crowd_head_layout)
    RelativeLayout activityChooseCrowdHeadLayout;
    @BindView(R.id.activity_choose_crowd_all_text)
    TextView activityChooseCrowdAllText;
    @BindView(R.id.activity_choose_crowd_line_one)
    TextView activityChooseCrowdLineOne;
    @BindView(R.id.activity_choose_crowd_business_text)
    TextView activityChooseCrowdBusinessText;
    @BindView(R.id.activity_choose_crowd_line_two)
    TextView activityChooseCrowdLineTwo;


    @Override
    public int getViewLayout() {
        return R.layout.activity_choose_crowd;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }

    @OnClick({R.id.activity_crowd_funding_introduce_head_image_layout, R.id.activity_choose_crowd_all_text, R.id.activity_choose_crowd_business_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_crowd_funding_introduce_head_image_layout:
                break;
            case R.id.activity_choose_crowd_all_text:
                Intent intent = new Intent();
                intent.putExtra("Name", "全部");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.activity_choose_crowd_business_text:
                Intent intentOne = new Intent();
                intentOne.putExtra("Name", "商圈");
                setResult(RESULT_OK, intentOne);
                finish();
                break;
        }
    }
}

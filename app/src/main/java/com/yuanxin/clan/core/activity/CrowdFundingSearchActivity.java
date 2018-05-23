package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/1.
 */
public class CrowdFundingSearchActivity extends BaseActivity {


    @BindView(R.id.activity_crowd_funding_search_left_image)
    ImageView activityCrowdFundingSearchLeftImage;
    @BindView(R.id.activity_crowd_funding_search_edit)
    EditText activityCrowdFundingSearchEdit;
    @BindView(R.id.activity_think_search_layout)
    LinearLayout activityThinkSearchLayout;
    @BindView(R.id.activity_crowd_funding_search_right_image)
    TextView activityCrowdFundingSearchRightImage;
    @BindView(R.id.activity_crowd_funding_search_clear_text)
    TextView activityCrowdFundingSearchClearText;
    @BindView(R.id.activity_crowd_funding_search_recycler_view)
    RecyclerView activityCrowdFundingSearchRecyclerView;


    @Override
    public int getViewLayout() {
        return R.layout.activity_crowd_funding_search;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }


    @OnClick({R.id.activity_crowd_funding_search_left_image, R.id.activity_crowd_funding_search_right_image, R.id.activity_crowd_funding_search_clear_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_crowd_funding_search_left_image:
                finish();
                break;
            case R.id.activity_crowd_funding_search_right_image:
                String inputString = activityCrowdFundingSearchEdit.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("crowdFundingInputString", inputString);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.activity_crowd_funding_search_clear_text:
                break;
        }
    }
}

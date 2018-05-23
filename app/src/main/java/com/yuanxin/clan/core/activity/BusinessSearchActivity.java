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
 * Created by lenovo1 on 2017/3/28.
 */
public class BusinessSearchActivity extends BaseActivity {
    @BindView(R.id.activity_business_search_left_image)
    ImageView activityBusinessSearchLeftImage;
    @BindView(R.id.activity_business_search_edit)
    EditText activityBusinessSearchEdit;
    @BindView(R.id.activity_business_search_layout)
    LinearLayout activityBusinessSearchLayout;
    @BindView(R.id.activity_business_search_right_image)
    TextView activityBusinessSearchRightImage;
    @BindView(R.id.activity_business_search_clear_text)
    TextView activityBusinessSearchClearText;
    @BindView(R.id.activity_business_search_recycler_view)
    RecyclerView activityBusinessSearchRecyclerView;

    @Override
    public int getViewLayout() {
        return R.layout.activity_business_search;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }

    @OnClick({R.id.activity_business_search_left_image, R.id.activity_business_search_layout, R.id.activity_business_search_right_image, R.id.activity_business_search_clear_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_search_left_image:
                finish();
                break;
            case R.id.activity_business_search_layout:
                break;
            case R.id.activity_business_search_right_image:
                String inputString = activityBusinessSearchEdit.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("companyInputString", inputString);
                setResult(RESULT_OK, intent);
                finish();

                break;
            case R.id.activity_business_search_clear_text:
                break;
        }
    }
}

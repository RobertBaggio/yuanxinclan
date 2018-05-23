package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/28.
 */
public class BusinessDistrictSetUpIntroduceActivity extends BaseActivity {
    @BindView(R.id.activity_business_district_set_up_introduce_left_layout)
    LinearLayout activityBusinessDistrictSetUpIntroduceLeftLayout;
    @BindView(R.id.activity_business_district_set_up_introduce_right_layout)
    LinearLayout activityBusinessDistrictSetUpIntroduceRightLayout;
    @BindView(R.id.activity_business_district_set_up_introduce_advice_edit)
    EditText activityBusinessDistrictSetUpIntroduceAdviceEdit;


    @Override
    public int getViewLayout() {
        return R.layout.activity_business_district_set_up_introduce;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        getInfo();
    }

    private void getInfo() {//从后台获取的数据
        String introduce = getIntent().getStringExtra("introduce");
        activityBusinessDistrictSetUpIntroduceAdviceEdit.setText(TextUtil.isEmpty(introduce) ? null : introduce);
    }


    @OnClick({R.id.activity_business_district_set_up_introduce_left_layout, R.id.activity_business_district_set_up_introduce_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_district_set_up_introduce_left_layout:
                finish();
                break;
            case R.id.activity_business_district_set_up_introduce_right_layout:
                String companyIntroduce = activityBusinessDistrictSetUpIntroduceAdviceEdit.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("BusinessDistrictIntroduce", companyIntroduce);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

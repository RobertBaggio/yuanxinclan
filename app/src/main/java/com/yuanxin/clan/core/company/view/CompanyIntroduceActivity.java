package com.yuanxin.clan.core.company.view;

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
 * Created by lenovo1 on 2017/3/21.
 * 企业简介类
 */
public class CompanyIntroduceActivity extends BaseActivity {
    @BindView(R.id.activity_company_introduce_head_image_layout)
    LinearLayout activityCompanyIntroduceHeadImageLayout;
    @BindView(R.id.activity_company_introduce_right_layout)
    LinearLayout activityCompanyIntroduceRightLayout;
    @BindView(R.id.activity_company_introduce_edit)
    EditText activityCompanyIntroduceEdit;


    @Override
    public int getViewLayout() {
        return R.layout.activity_company_introduce;
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
        Intent intent = getIntent();
        String introduce = intent.getStringExtra("introduce");
        activityCompanyIntroduceEdit.setText(introduce + "");
        activityCompanyIntroduceEdit.setSelection(activityCompanyIntroduceEdit.getText().length());
    }

    @OnClick({R.id.activity_company_introduce_head_image_layout, R.id.activity_company_introduce_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_introduce_head_image_layout:
                finish();
                break;
            case R.id.activity_company_introduce_right_layout:
                String companyIntroduce = activityCompanyIntroduceEdit.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("companyIntroduce", companyIntroduce);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }


}

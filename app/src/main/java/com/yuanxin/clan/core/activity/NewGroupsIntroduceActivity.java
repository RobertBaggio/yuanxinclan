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
 * Created by lenovo1 on 2017/5/13.
 */
public class NewGroupsIntroduceActivity extends BaseActivity {
    @BindView(R.id.activity_company_introduce_head_image_layout)
    LinearLayout activityCompanyIntroduceHeadImageLayout;
    @BindView(R.id.activity_company_introduce_right_layout)
    LinearLayout activityCompanyIntroduceRightLayout;
    @BindView(R.id.activity_company_introduce_edit)
    EditText activityCompanyIntroduceEdit;


    @Override
    public int getViewLayout() {
        return R.layout.activity_new_groups_introduce;
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
        String introduce = getIntent().getStringExtra("introduce");
        if (TextUtil.isEmpty(introduce)) {
            activityCompanyIntroduceEdit.setText(introduce + "");
        } else {
            activityCompanyIntroduceEdit.setText(introduce);
        }
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

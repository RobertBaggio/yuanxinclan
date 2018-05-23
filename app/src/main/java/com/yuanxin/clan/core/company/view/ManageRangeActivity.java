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
 */
public class ManageRangeActivity extends BaseActivity {
    @BindView(R.id.activity_manage_range_head_image_layout)
    LinearLayout activityManageRangeHeadImageLayout;
    @BindView(R.id.activity_manage_range_right_layout)
    LinearLayout activityManageRangeRightLayout;
    @BindView(R.id.activity_manage_range_edit)
    EditText activityManageRangeEdit;


    @Override
    public int getViewLayout() {
        return R.layout.activity_manage_range;
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
        String introduce = getIntent().getStringExtra("range");
        activityManageRangeEdit.setText(introduce + "");
        activityManageRangeEdit.setSelection(activityManageRangeEdit.getText().length());
    }

    @OnClick({R.id.activity_manage_range_head_image_layout, R.id.activity_manage_range_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_manage_range_head_image_layout:
                finish();
                break;
            case R.id.activity_manage_range_right_layout:
                String edit = activityManageRangeEdit.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("manageRange", edit);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

}

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
 * Created by lenovo1 on 2017/3/16.
 */
public class FairPublishTypeChooseActivity extends BaseActivity {
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
    @BindView(R.id.supply)
    TextView supply;
    @BindView(R.id.publish)
    TextView publish;

    @Override
    public int getViewLayout() {
        return R.layout.activity_fair_publish_type_choose;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }

    @OnClick({R.id.window_head_left_image, R.id.supply, R.id.publish})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.window_head_left_image:
                break;
            case R.id.supply:
                String choose = supply.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("data_return", choose);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.publish:
                String publishChoose = publish.getText().toString().trim();
                Intent intentPulich = new Intent();
                intentPulich.putExtra("data_return", publishChoose);
                setResult(RESULT_OK, intentPulich);
                finish();
                break;
        }
    }
}

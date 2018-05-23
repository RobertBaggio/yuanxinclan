package com.yuanxin.clan.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lenovo1 on 2017/5/11.
 */
public class ChooseNanWomanActivity extends Activity {
    @BindView(R.id.activity_company_info_left_layout)
    LinearLayout activityCompanyInfoLeftLayout;
    @BindView(R.id.activity_company_info_right_layout)
    LinearLayout activityCompanyInfoRightLayout;
    @BindView(R.id.boy)
    TextView boy;
    @BindView(R.id.girl)
    TextView girl;
    public Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_nan_woman);
        unbinder = ButterKnife.bind(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();

    }

    @OnClick({R.id.boy, R.id.girl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.boy:
                Intent intent = new Intent();
                intent.putExtra("boy", "男");
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.girl:
                Intent intentOne = new Intent();
                intentOne.putExtra("boy", "女");
                setResult(RESULT_OK, intentOne);
                finish();
                break;
        }
    }
}

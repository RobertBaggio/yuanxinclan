package com.yuanxin.clan.core.huanxin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.yuanxin.clan.R.id.failure;

/**
 * Created by lenovo1 on 2017/3/27.
 */
public class BusinessMsgApplyResultActivity extends BaseActivity {
    @BindView(R.id.success)
    LinearLayout successLayout;
    @BindView(R.id.successTime)
    TextView successTime;
    @BindView(failure)
    LinearLayout failureLayout;
    @BindView(R.id.failureTime)
    TextView failureTime;
    @BindView(R.id.success_icon)
    TextView successIcon;
    @BindView(R.id.failure_icon)
    TextView failureIcon;
    @BindView(R.id.success_tip)
    TextView successTip;
    @BindView(R.id.failure_tip)
    TextView failureTip;

    @Override
    public int getViewLayout() {
        return R.layout.activity_business_msg_apply_result;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        BusinessMessage msg = intent.getParcelableExtra("msg");
        SiteData siteData = msg.getSiteData();
        RepairsData repairsData = msg.getRepairsData();
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        successIcon.setTypeface(iconfont);
        failureIcon.setTypeface(iconfont);
        if (siteData != null) {
            switch (siteData.getProcessState()) {
                //预约成功
                case 2:
                    successLayout.setVisibility(View.VISIBLE);
                    failureLayout.setVisibility(View.GONE);
                    successTime.setText(siteData.getDateUsed());
                    successTip.setText("恭喜您已通过申请！");
                    break;
                //预约失败
                case 3:
                    successLayout.setVisibility(View.GONE);
                    failureLayout.setVisibility(View.VISIBLE);
                    failureTime.setText(siteData.getDateUsed());
                    failureTip.setText("抱歉，您未通过申请！");
                    break;
            }
        } else if (repairsData != null) {
            switch (repairsData.getProcessState()) {
                //处理报修
                case 2:
                    successLayout.setVisibility(View.VISIBLE);
                    failureLayout.setVisibility(View.GONE);
                    successTime.setText(com.yuanxin.clan.core.util.Utils.getStrTime3(String.valueOf(repairsData.getCreateDt())));
                    successTip.setText("已派人处理");
                    break;
                //拒绝报修
                case 3:
                    successLayout.setVisibility(View.GONE);
                    failureLayout.setVisibility(View.VISIBLE);
                    failureTime.setText(com.yuanxin.clan.core.util.Utils.getStrTime3(String.valueOf(repairsData.getCreateDt())));
                    failureTip.setText("拒绝报修");
                    break;
            }
        }
    }

    @OnClick(R.id.activity_exchange_phone_left_layout)
    public void onClick() {
        finish();
    }
}

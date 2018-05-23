package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/28.
 */
public class ComplaintAdviceActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_complaint_advice_edit)
    EditText activityComplaintAdviceEdit;
    private TextView line;

    @Override
    public int getViewLayout() {
        return R.layout.activity_complaint_advice;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

    }

    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout://返回
                finish();
                break;
            case R.id.activity_exchange_phone_right_layout://提交
                String advice = activityComplaintAdviceEdit.getText().toString().trim();
                getWebInfo(advice);
//                Dialog dlg = new Dialog(ComplaintAdviceActivity.this, R.style.WhiteDialog);
//                View dlgView = View.inflate(ComplaintAdviceActivity.this, R.layout.dialog, null);
//                View tvFacebook = dlgView.findViewById(R.id.dialog_new_title);
//                tvFacebook.setTag(dlg);
//                tvFacebook.setOnClickListener(this);
//                View tvTwitter = dlgView.findViewById(R.id.dialog_new_message);
//                tvTwitter.setTag(dlg);
//                line = (TextView) dlgView.findViewById(R.id.dialog_new_line);
//                line.setTag(dlg);
//                tvTwitter.setOnClickListener(this);
//                new CountDownTimer(6000, 1000) {
//                    public void onTick(long millisUntilFinished) {
//                        line.setText(millisUntilFinished / 1000 + "秒后自动跳转");
//                    }
//
//                    public void onFinish() {
//                        startActivity(new Intent(ComplaintAdviceActivity.this, MainActivity.class));
//                    }
//                }.start();
//                dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dlg.setContentView(dlgView);
//                dlg.show();
                break;
        }
    }

    private void getWebInfo(String suggestionContent) {
        String url = Url.addAdvice;
        RequestParams params = new RequestParams();
        params.put("createId", UserNative.getId());
        params.put("createNm", UserNative.getName());
        params.put("suggestionContent", suggestionContent);
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }
        });
    }


}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.TimerUtil;
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
//账号安全 更换密码
public class ChangePasswordActivity extends BaseActivity {
    @BindView(R.id.activity_change_password_left_layout)
    LinearLayout activityChangePasswordLeftLayout;
    @BindView(R.id.activity_change_password_right_layout)
    LinearLayout activityChangePasswordRightLayout;
    @BindView(R.id.activity_change_password_phone_edit)
    EditText activityChangePasswordPhoneEdit;
    @BindView(R.id.activity_exchange_phone_send_code)
    TextView activityExchangePhoneSendCode;
    @BindView(R.id.activity_change_password_code_edit)
    EditText activityChangePasswordCodeEdit;
    @BindView(R.id.activity_change_password_password_edit)
    EditText activityChangePasswordPasswordEdit;
    @BindView(R.id.activity_change_password_button)
    Button activityChangePasswordButton;
    private SubscriberOnNextListener getSmsCodeOnNext;
    private String code;
    private int mMin = 31;
    private TimerUtil timerUtil;


    @Override
    public int getViewLayout() {
        return R.layout.activity_change_password;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initTimerUtil();
    }
    private void initTimerUtil() {
        TimerUtil.OnTimerChangeListener onTimerChangeListener = new TimerUtil.OnTimerChangeListener() {
            @Override
            public void doChange() {
                doTimeChange();
            }
        };
        timerUtil = new TimerUtil(0, 1000, onTimerChangeListener);
    }
    private void doTimeChange() {
        if (activityExchangePhoneSendCode == null)
            return;
        mMin--;
        if (mMin < 0) {
            timeStop();
            activityExchangePhoneSendCode.setText("获取验证码");
            return;
        }
        activityExchangePhoneSendCode.setText(String.valueOf(mMin)+"秒后重发");
    }
    /**
     * 停止计时器
     */
    private void timeStop() {
        mMin = 31;
        activityExchangePhoneSendCode.setEnabled(true);
        timerUtil.timeStop();
    }
    /**
     * 计时器开始
     */
    private void timeStart() {
        activityExchangePhoneSendCode.setEnabled(false);
        timerUtil.timeStart();
    }



    @OnClick({R.id.activity_change_password_left_layout, R.id.activity_exchange_phone_send_code, R.id.activity_change_password_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_change_password_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_send_code:
                String phone = activityChangePasswordPhoneEdit.getText().toString().trim();
                if (phone.equals("")) {
                    ToastUtil.showInfo(ChangePasswordActivity.this, "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!Utils.isPhoneNumber(phone)) {
                    ToastUtil.showInfo(ChangePasswordActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    activityChangePasswordPhoneEdit.setText("");
                    return;
                }
                sendMessageCode(phone);
                break;
            case R.id.activity_change_password_button:
                String phoneOne = activityChangePasswordPhoneEdit.getText().toString().trim();
                if (phoneOne.equals("")) {
                    ToastUtil.showInfo(ChangePasswordActivity.this, "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!Utils.isPhoneNumber(phoneOne)) {
                    ToastUtil.showInfo(ChangePasswordActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    activityChangePasswordPhoneEdit.setText("");
                    return;
                }
                String password = activityChangePasswordPasswordEdit.getText().toString().trim();//密码
                if (password.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT);
                    return;
                }
                String sendCode = activityChangePasswordCodeEdit.getText().toString().trim();//验证码
                if (sendCode.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!sendCode.equals(code)) {
                    ToastUtil.showInfo(getApplicationContext(), "输入的验证码有误，请重新输入", Toast.LENGTH_LONG);
                    activityChangePasswordCodeEdit.setText("");
                    return;
                }
                forgetCode(phoneOne, password);
                break;
        }
    }

    private void forgetCode(String phone, String pwd) {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = Url.forgetCode;
        RequestParams params = new RequestParams();
        params.put("phone", phone);//企业id
        params.put("pwd", pwd);//企业id

        //pwd

        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }
        });
    }


    private void sendMessageCode(String phone) {

        AsyncHttpClient client = new AsyncHttpClient();
        String url = Url.forgetSendCode;
        RequestParams params = new RequestParams();
        params.put("phone", phone);//企业id

        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        timeStart();
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        code = object.getString("data");
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }
        });
    }
}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
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
 * Created by lenovo1 on 2017/3/26.
 * 换绑手机
 */
public class ExchangePhoneActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_exchange_phone_password_edit)
    EditText activityExchangePhonePasswordEdit;
    @BindView(R.id.activity_exchange_phone_phone_edit)
    EditText activityExchangePhonePhoneEdit;
    @BindView(R.id.activity_exchange_phone_send_code)
    TextView activityExchangePhoneSendCode;
    @BindView(R.id.activity_exchange_phone_code_edit)
    EditText activityExchangePhoneCodeEdit;
    @BindView(R.id.activity_exchange_phone_button)
    Button activityExchangePhoneButton;
    private SubscriberOnNextListener getSmsCodeOnNext;
    private String code;
    private int mMin = 31;
    private TimerUtil timerUtil;


    @Override
    public int getViewLayout() {
        return R.layout.activity_exchange_phone;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initTimerUtil();
    }


    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_send_code, R.id.activity_exchange_phone_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_send_code:
                String phone = activityExchangePhonePhoneEdit.getText().toString().trim();
                if (phone.equals("")) {
                    Toast.makeText(ExchangePhoneActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isPhoneNumber(phone)) {
                    Toast.makeText(ExchangePhoneActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    activityExchangePhonePhoneEdit.setText("");
                    return;
                }
                getSessageCode();

                break;
            case R.id.activity_exchange_phone_button://确认修改
                String codeNumber = activityExchangePhoneCodeEdit.getText().toString().trim();
                if (!codeNumber.equals(code)) {
                    Toast.makeText(getApplicationContext(), "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = activityExchangePhonePasswordEdit.getText().toString().trim();
                if (password.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String phoneNumber = activityExchangePhonePhoneEdit.getText().toString().trim();
                if (phoneNumber.equals("")) {
                    Toast.makeText(ExchangePhoneActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isPhoneNumber(phoneNumber)) {
                    Toast.makeText(ExchangePhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    activityExchangePhonePhoneEdit.setText("");
                    return;
                }
                ExchangePhone(phoneNumber, password);
        }
    }

    private void ExchangePhone(String userPhone, String userPwd) {
        String url = Url.changePhone;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//企业id
        params.put("userPhone", userPhone);//企业id
        params.put("userPwd", userPwd);//企业id
        doHttpPost(url, params, new RequestCallback() {
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

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }
        });
    }


    private void getSessageCode() {
        String phone = activityExchangePhonePhoneEdit.getText().toString().trim();
        String url = Url.changePhoneCode;
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

}

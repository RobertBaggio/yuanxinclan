package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
 * Created by lenovo1 on 2017/4/24.
 * 找回密码  重置密码
 */
public class FindPasswordActivity extends BaseActivity {
    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_find_password_text)
    TextView activityFindPasswordText;
    @BindView(R.id.activity_login_common_register_code)
    EditText activityLoginCommonRegisterCode;
    @BindView(R.id.activity_login_common_register_send_code)
    TextView activityLoginCommonRegisterSendCode;
    @BindView(R.id.activity_login_common_register_password)
    EditText activityLoginCommonRegisterPassword;
    @BindView(R.id.activity_login_common_register_register)
    Button activityLoginCommonRegisterRegister;
    @BindView(R.id.phone)
    EditText phoneEdittext;
    private String code, phone;
    private TimerUtil timerUtil;
    private int mMin = 31;


    @Override
    public int getViewLayout() {
        return R.layout.activity_find_password;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
//        phone = getIntent().getStringExtra("phone");
        phoneEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone = phoneEdittext.getText().toString();
                // 判断输入不为空，按钮可点击
                if (Utils.isPhoneNumber(phoneEdittext.getText().toString())) {
                    activityLoginCommonRegisterRegister.setEnabled(true);//可用
                    activityLoginCommonRegisterRegister.setBackgroundResource(R.drawable.login_red_2_0);
                } else {
                    activityLoginCommonRegisterRegister.setEnabled(false);
                    activityLoginCommonRegisterRegister.setBackgroundResource(R.drawable.border_grey_2_0);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        initTimerUtil();
//        getCode();
    }

    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_login_common_register_send_code, R.id.activity_login_common_register_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_login_common_register_send_code://60秒后重新获取
                if (!Utils.isPhoneNumber(phoneEdittext.getText().toString())) {
                    ToastUtil.showWarning(this, "请输入正确的手机号码", Toast.LENGTH_SHORT);
                    break;
                }
                getCode();
                break;
            case R.id.activity_login_common_register_register://修改密码完成
                String sendCode = activityLoginCommonRegisterCode.getText().toString().trim();//验证码
                if (sendCode.equals("")) {
                    ToastUtil.showInfo(this, "请输入验证码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!sendCode.equals(code)) {
                    ToastUtil.showWarning(this, "输入的验证码有误，请重新输入", Toast.LENGTH_SHORT);
                    activityLoginCommonRegisterCode.setText("");
                    return;
                }
                String password = activityLoginCommonRegisterPassword.getText().toString().trim();//密码
                if (password.equals("")) {
                    ToastUtil.showInfo(this, "请输入密码", Toast.LENGTH_SHORT);
                    return;
                }
                forgetCode(password);
                break;
        }
    }

    private void getCode() {
        sendMessageCode(phone);
        activityFindPasswordText.setVisibility(View.VISIBLE);
        activityFindPasswordText.setText("短信验证码已发送至" + phone + "，请注意查收");
        timeStart();
    }

    private void forgetCode(String pwd) {
        String url = Url.forgetCode;
        RequestParams params = new RequestParams();
        params.put("phone", phone);//企业id
        params.put("pwd", pwd);//企业id
        doHttpPostNotVerify(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
//                        setResult(2059);
                        startActivity(new Intent(FindPasswordActivity.this, LoginActivity.class));
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

        doHttpPostNotVerify(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
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
        if (activityLoginCommonRegisterSendCode == null)
            return;
        mMin--;
        if (mMin < 0) {
            timeStop();
            activityLoginCommonRegisterSendCode.setText("获取验证码");
            return;
        }
        activityLoginCommonRegisterSendCode.setText(String.valueOf(mMin)+"秒后重发");
    }
    /**
     * 停止计时器
     */
    private void timeStop() {
        mMin = 31;
        activityLoginCommonRegisterSendCode.setEnabled(true);
        timerUtil.timeStop();
    }
    /**
     * 计时器开始
     */
    private void timeStart() {
        activityLoginCommonRegisterSendCode.setEnabled(false);
        timerUtil.timeStart();
    }

}

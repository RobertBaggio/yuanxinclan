package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.MainActivity;
import com.yuanxin.clan.core.event.ThirdLoginEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
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
 * 绑定账号
 */
public class BindingPhoneActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_exchange_phone_phone_edit)
    EditText activityExchangePhonePhoneEdit;
    @BindView(R.id.activity_exchange_phone_send_code)
    TextView activityExchangePhoneSendCode;
    @BindView(R.id.activity_exchange_phone_code_edit)
    EditText activityExchangePhoneCodeEdit;
    @BindView(R.id.activity_exchange_phone_button)
    Button activityExchangePhoneButton;
    @BindView(R.id.user_agreement)
    TextView userAgreementTextView;
    private SubscriberOnNextListener getSmsCodeOnNext;
    private String code;
    private int mMin = 31;
    private TimerUtil timerUtil;
    private SpannableString sps;
    private ThirdLoginEvent thirdLoginEvent;

    @Override
    public int getViewLayout() {
        return R.layout.activity_binding_phone;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initUserAgreement();
        initTimerUtil();
        try {
            thirdLoginEvent = FastJsonUtils.parseObject(getIntent().getStringExtra("third_info"), ThirdLoginEvent.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initUserAgreement() {
        //创建一个 SpannableString对象
        sps = new SpannableString("点击绑定，即表示同意《圆心部落用户协议》");
        //设置字体前景色
//        sps.setSpan(new ForegroundColorSpan(Color.BLUE), 15, sps.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//        sps.setSpan(new URLSpan("tel:4155551212"), 15, sps.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        sps.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                startActivity(new Intent(BindingPhoneActivity.this, NetworkDetailActivity.class));
            }
            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setColor(getResources().getColor(R.color.blue));
                ds.setUnderlineText(false);    //去除超链接的下划线
            }
        }, 15, sps.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        userAgreementTextView.setText(sps);
        userAgreementTextView.setMovementMethod(LinkMovementMethod.getInstance());
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
                    ToastUtil.showInfo(BindingPhoneActivity.this, "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!Utils.isPhoneNumber(phone)) {
                    ToastUtil.showInfo(BindingPhoneActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT);
                    activityExchangePhonePhoneEdit.setText("");
                    return;
                }
                getSessageCode();

                break;
            case R.id.activity_exchange_phone_button://完成绑定
                String codeNumber = activityExchangePhoneCodeEdit.getText().toString().trim();
                if (!codeNumber.equals(code)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入正确的验证码", Toast.LENGTH_SHORT);
                    return;
                }
                String phoneNumber = activityExchangePhonePhoneEdit.getText().toString().trim();
                if (phoneNumber.equals("")) {
                    ToastUtil.showInfo(BindingPhoneActivity.this, "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!Utils.isPhoneNumber(phoneNumber)) {
                    ToastUtil.showInfo(BindingPhoneActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    activityExchangePhonePhoneEdit.setText("");
                    return;
                }
//                ExchangePhone(phoneNumber, password);
                // 绑定第三方信息到账号
                BindingPhone(phoneNumber, thirdLoginEvent);
                break;
        }
    }

    private void BindingPhone(String userPhone, ThirdLoginEvent thirdLoginEvent) {
        String url = Url.bindWechat;
        RequestParams params = new RequestParams();
        params.put("wechatUid", thirdLoginEvent.getUserId());
        params.put("userPhone", userPhone);
        doHttpPostNotVerify(url, params, new BaseActivity.RequestCallback() {
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
                        startActivity(new Intent(BindingPhoneActivity.this, MainActivity.class));
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


    private void getSessageCode() {
        String phone = activityExchangePhonePhoneEdit.getText().toString().trim();
        String url = Url.changePhoneCode;
        RequestParams params = new RequestParams();
        params.put("phone", phone);//企业id

        doHttpGetNotVerify(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

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

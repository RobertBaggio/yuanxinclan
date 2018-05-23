package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/24.
 * 找密码验证手机
 */
public class ForgetPasswordActivity extends BaseActivity {
    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_login_common_register_phone)
    EditText activityLoginCommonRegisterPhone;
    @BindView(R.id.activity_login_common_register_register)
    Button activityLoginCommonRegisterRegister;
    private String code;


    @Override
    public int getViewLayout() {
        return R.layout.activity_forget_password;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        activityLoginCommonRegisterRegister.setEnabled(false);
        activityLoginCommonRegisterRegister.setBackgroundResource(R.color.grey);
        activityLoginCommonRegisterPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 判断输入不为空，按钮可点击
                if (Utils.isPhoneNumber(activityLoginCommonRegisterPhone.getText().toString())) {
                    activityLoginCommonRegisterRegister.setEnabled(true);//可用
                    activityLoginCommonRegisterRegister.setBackgroundResource(R.color.my_info_head_bg);
                } else {
                    activityLoginCommonRegisterRegister.setEnabled(false);
                    activityLoginCommonRegisterRegister.setBackgroundResource(R.color.grey);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_login_common_register_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_login_common_register_register:
                String phone = activityLoginCommonRegisterPhone.getText().toString();
                if (phone.equals("")) {
                    ToastUtil.showInfo(ForgetPasswordActivity.this, "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!Utils.isPhoneNumber(phone)) {
                    ToastUtil.showInfo(ForgetPasswordActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    activityLoginCommonRegisterPhone.setText("");
                    return;
                }
                Intent intent = new Intent(ForgetPasswordActivity.this, FindPasswordActivity.class);
                intent.putExtra("phone", phone);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2059 && resultCode == 2059) {
            finish();
        }
    }
}

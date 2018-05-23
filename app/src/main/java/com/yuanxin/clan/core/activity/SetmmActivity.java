package com.yuanxin.clan.core.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/6/28 0028 18:08
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class SetmmActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout backli;
    private RelativeLayout bdli,szli,zdli,tixianli;
    private String code;
    private EditText phoneet,yzmet,firstpw,secondpw;
    private TextView getyzmte;
    private Button commit;

    @Override
    public int getViewLayout() {
        return R.layout.setmmla;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        backli =(LinearLayout)findViewById(R.id.backacli);
        phoneet =(EditText)findViewById(R.id.setzfmm_password_phone_edit) ;
        commit = (Button)findViewById(R.id.szzfmmbt_register);
        yzmet =(EditText)findViewById(R.id.yzmetregister_code) ;
        firstpw=(EditText)findViewById(R.id.diyicimm) ;
        secondpw =(EditText)findViewById(R.id.diercimm) ;
        phoneet.setText(UserNative.getPhone());
        phoneet.setEnabled(false);
        getyzmte =(TextView)findViewById(R.id.getyzm_phone_send_code);
        backli.setOnClickListener(this);
        commit.setOnClickListener(this);
        getyzmte.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backacli:
                onBackPressed();
                break;
            case R.id.bandingac:
                startActivity(new Intent(SetmmActivity.this, MyaccountActivity.class));
                break;
            case R.id.szmmac:
                break;
            case R.id.szzfmmbt_register:
                String first = firstpw.getText().toString();
                String second = secondpw.getText().toString();
                String authcode = yzmet.getText().toString();
                if (!authcode.equals(code)){
                    ToastUtil.showWarning(SetmmActivity.this, "验证码错误", Toast.LENGTH_SHORT);
                    return;
                }
                if (!first.equals(second)){
                    ToastUtil.showWarning(SetmmActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT);
                    return;
                }
                if (first.length()<6){
                    ToastUtil.showWarning(SetmmActivity.this, "密码不得小于6位", Toast.LENGTH_SHORT);
                    return;
                }
                commitpassword(first);

                break;
            case R.id.getyzm_phone_send_code:
                String phone = phoneet.getText().toString().trim();
                if (phone.equals("")) {
                    ToastUtil.showInfo(SetmmActivity.this, "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!Utils.isPhoneNumber(phone)) {
                    ToastUtil.showInfo(SetmmActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    phoneet.setText("");
                    return;
                }
                sendMessageCode(phone);
                break;
        }}

    private void sendMessageCode(String phone) {
        String url = Url.forgetSendCode;
        RequestParams params = new RequestParams();
        params.put("phone", phone);//企业id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                    Log.v("Lgq","获取验证码。。"+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        code = object.getString("data");
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }
    private void commitpassword(final String password){
        String url = Url.commitpassword;
        RequestParams params = new RequestParams();
        params.put("acountId", UserNative.getAccoutid());
        params.put("accountPwd", password);
        params.put("userId", UserNative.getId());
        params.put("userNm", UserNative.getName());
//        Log.v("lgq",".......acountId="+UserNative.getAccoutid()+"...accountPwd="+password+"...userid="+UserNative.getId()+"...d登录="+UserNative.getName());
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(SetmmActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(SetmmActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("accountPwd", password);// id

                        editor.commit();//提交修改
                        onBackPressed();
                    } else {
                    }
                } catch (JSONException e) {
//                    Toast.makeText(SetmmActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }


}

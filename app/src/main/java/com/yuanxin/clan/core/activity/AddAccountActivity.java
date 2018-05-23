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
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/6/28 0028 17:58
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class AddAccountActivity extends BaseActivity {

    @BindView(R.id.szzfmmbt_register)
    Button szzfmmbt_register;
    @BindView(R.id.backacli)
    LinearLayout backacli;
    @BindView(R.id.topte)
    TextView topte;
    @BindView(R.id.add_nameet)
    EditText add_nameet;
    @BindView(R.id.accountet_add)
    EditText accountet_add;

    private String type;

    @Override
    public int getViewLayout() {
        return R.layout.addaccountla;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        type = getIntent().getStringExtra("type");
        if (type.equals("wx"))
            topte.setText("微信绑定");
        else
            topte.setText("支付宝绑定");
//        Log.v("lgq","......ssss=="+type);
    }

    @OnClick({R.id.szzfmmbt_register,R.id.backacli})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.szzfmmbt_register://我的服务
                String name = add_nameet.getText().toString();
                String account = accountet_add.getText().toString();
                if (name.length()==0||account.length()==0){
                    ToastUtil.showInfo(AddAccountActivity.this, "请输入账号或密码", Toast.LENGTH_SHORT);
                    return;
                }
                commitpassword();
                break;
            case R.id.backacli:
                onBackPressed();
                break;
        }
    }
//    wechatAcount   wechatNm
    private void commitpassword(){
        String url = Url.commitpassword;
        String name = add_nameet.getText().toString();
        String account = accountet_add.getText().toString();

        RequestParams params = new RequestParams();
        params.put("acountId", UserNative.getAccoutid());
        if (type.equals("wx")){
            params.put("wechatAcount", account);
            params.put("wechatNm", name);
        }if (type.equals("ali")){
            params.put("alipayNm", name);
            params.put("aliapyAccount", account);
        }
        params.put("userId", UserNative.getId());
        params.put("userNm", UserNative.getName());
//        Log.v("lgq",".......acountId="+UserNative.getAccoutid()+"...accountPwd="+password+"...userid="+UserNative.getId()+"...d登录="+UserNative.getName());
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(AddAccountActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.v("lgq","绑定返回。。。。"+s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showInfo(AddAccountActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        if (type.equals("wx"))
                        editor.putString("wxbd", "ok");// id
                        if (type.equals("ali"))
                        editor.putString("zfbd", "ok");//epId
                        editor.commit();//提交修改
                        Intent _intent = new
                                Intent(AddAccountActivity.this,MyaccountActivity.class);
                        //在Intent对象当中添加一个键值对
                        _intent.putExtra("ok",type);
                        startActivity(_intent);
                        finish();
                    } else {
                        ToastUtil.showInfo(AddAccountActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });
    }

}

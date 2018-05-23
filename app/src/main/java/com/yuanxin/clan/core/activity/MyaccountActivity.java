package com.yuanxin.clan.core.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/6/28 0028 16:50
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyaccountActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout backli;
    private RelativeLayout bdli,szli,zdli,tixianli;
    private String password;
    private int acountId;
    private double acountBalance, delayBalance;
    private TextView balance;
    private TextView delay_balance;

    @Override
    public int getViewLayout() {
        return R.layout.myaccountactivity;
    }

    @Override

    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        backli =(LinearLayout)findViewById(R.id.backacli);
        bdli =(RelativeLayout)findViewById(R.id.bandingac);
        szli =(RelativeLayout)findViewById(R.id.szmmac);
        zdli =(RelativeLayout)findViewById(R.id.zdmxac);
        tixianli =(RelativeLayout)findViewById(R.id.tixre);
        balance = (TextView)findViewById(R.id.balance_value);
        delay_balance = (TextView) findViewById(R.id.delay_balance_value);
        backli.setOnClickListener(this);
        bdli.setOnClickListener(this);
        szli.setOnClickListener(this);
        zdli.setOnClickListener(this);
        tixianli.setOnClickListener(this);
        getdata();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backacli:
                onBackPressed();
                break;
            case R.id.bandingac:
                startActivity(new Intent(MyaccountActivity.this, BoundActivity.class));
                break;
            case R.id.szmmac:
                startActivity(new Intent(MyaccountActivity.this, SetmmActivity.class));
                break;
            case R.id.zdmxac:
                startActivity(new Intent(MyaccountActivity.this, CheckDetailActivity.class));
                break;
            case R.id.tixre:
                startActivity(new Intent(MyaccountActivity.this, TiXianActivity.class));
                break;
        }}
    private void getdata(){

        String url = Url.myaccounturl;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(MyaccountActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        String aliname = array.getString("alipayNm");
                        String aliaccount = array.getString("aliapyAccount");
                        String wxname = array.getString("wechatNm");
                        String wxaccount = array.getString("wechatAcount");
                        String accountPwd = array.getString("accountPwd");
                        password = array.getString("accountPwd");
                        acountBalance = array.getDouble("acountBalance");
                        delayBalance = array.getDouble("allBalance");
                        acountId = array.getInt("acountId");
                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("acountId", String.valueOf(acountId));// id
                        editor.putString("accountPwd", password);//epId
                        editor.putString("aliname", aliname);//epId
                        editor.putString("aliaccount", aliaccount);//epId
                        editor.putString("wxname", wxname);//epId
                        editor.putString("wxaccount", wxaccount);//epId
                        editor.putString("accountPwd", accountPwd);//epId
                        editor.putString("acountBalance", String.valueOf(acountBalance));//epId
                        editor.commit();//提交修改
                        balance.setText(String.valueOf(acountBalance));
                        delay_balance.setText(String.valueOf(delayBalance));
                    } else {
                    }
                } catch (JSONException e) {
//                    Toast.makeText(MyaccountActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

}

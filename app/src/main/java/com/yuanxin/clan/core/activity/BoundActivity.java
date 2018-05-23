package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.mvp.view.BaseActivity;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/6/28 0028 17:54
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class BoundActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout backli,addaccountli;
    private RelativeLayout bdli,szli,zdli;
    private TextView wxaccountte,wxnamete,wxboundte;
    private TextView aliaccountte,alinamete,aliboundte;
    private ImageView wximage,aliimage;

    @Override
    public int getViewLayout() {
        return R.layout.boundla;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        String wxok = UserNative.getWxbd();
        String aliok = UserNative.getAlibd();
        Log.v("lgq","lg......"+aliok);
        backli =(LinearLayout)findViewById(R.id.backacli);
        wxboundte = (TextView)findViewById(R.id.wxboundte);
        aliboundte = (TextView)findViewById(R.id.aliboundte);
        wxnamete = (TextView)findViewById(R.id.wxnamete);
        wxaccountte=(TextView)findViewById(R.id.wxaccountte);
        alinamete = (TextView)findViewById(R.id.alipnamete) ;
        aliaccountte=(TextView)findViewById(R.id.aliaccountte);
        wximage =(ImageView)findViewById(R.id.wxboundimage);
        aliimage =(ImageView)findViewById(R.id.alipimage);
        if (wxok.equals("ok")&&UserNative.getWxaccount().length()>3){
            wximage.setImageResource(R.drawable.personal_center_account_bound_selected);
            wxnamete.setText("微信账号："+UserNative.getWxname());
            wxaccountte.setText("姓名："+UserNative.getWxaccount());
//            wxboundte.setText("解绑");
        }if (aliok.equals("ok")&&UserNative.getAliaccount().length()>3){
            aliimage.setImageResource(R.drawable.personal_center_account_bound_selected);
            alinamete.setText("姓名："+UserNative.getAliname());
            aliaccountte.setText("支付宝账号："+UserNative.getAliaccount());
//            aliboundte.setText("解绑");
        }

        addaccountli =(LinearLayout)findViewById(R.id.addli_right_layout);
        backli.setOnClickListener(this);
        addaccountli.setOnClickListener(this);
        wxboundte.setOnClickListener(this);
        aliboundte.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backacli:
                onBackPressed();
                break;
            case R.id.wxboundte:
                Intent _intent = new
                Intent(BoundActivity.this,AddAccountActivity.class);
                //在Intent对象当中添加一个键值对
                _intent.putExtra("type","wx");
                startActivity(_intent);
                finish();
                break;
            case R.id.aliboundte:
                Intent _intent2 = new
                Intent(BoundActivity.this,AddAccountActivity.class);
                //在Intent对象当中添加一个键值对
                _intent2.putExtra("type","ali");
                startActivity(_intent2);
                finish();
                break;
        }}
    @Override
    protected void onResume() {
        super.onResume();
//        Log.i("lgq", "onResume called.");

    }

}

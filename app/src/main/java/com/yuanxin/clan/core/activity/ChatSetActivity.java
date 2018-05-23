package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.hyphenate.chat.EMOptions;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.huanxin.DemoModel;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: new_yuanxin
 * Describe:
 * Author: xjc
 * Date: 2017/8/25 0025 11:57
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ChatSetActivity extends BaseActivity{

    @BindView(R.id.backacli)
    LinearLayout backacli;
    @BindView(R.id.addli_right_layout)
    LinearLayout addli_right_layout;
    @BindView(R.id.concealli)
    LinearLayout concealli;
    @BindView(R.id.newnewsli)
    LinearLayout newnewsli;
    @BindView(R.id.voiceli)
    LinearLayout voiceli;
    @BindView(R.id.shakeli)
    LinearLayout shakeli;
    @BindView(R.id.newssw)
    Switch newssw;
    @BindView(R.id.ycsw)
    Switch ycsw;
    @BindView(R.id.sysw)
    Switch sysw;
    @BindView(R.id.zdsw)
    Switch zdsw;

    private boolean ifconceal,ifnews,ifvoice,ifshake;
    private int ifyc,ifxx,ifzd,ifsy;
    private DemoModel settingsModel;
    private EMOptions chatOptions;

    @Override
    public int getViewLayout() {
        return R.layout.chatsetlayout;
    }
    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
//        setStatusBar(this.getResources().getColor(R.color.mine_page_color));
        settingsModel = DemoHelper.getInstance().getModel();
        ifxx= MyShareUtil.getSharedInt("ifxx");
        ifsy= MyShareUtil.getSharedInt("ifsy");
        ifyc= MyShareUtil.getSharedInt("ifyc");
        ifzd= MyShareUtil.getSharedInt("ifzd");
        if (ifxx==1){
            newssw.setChecked(true);
        }else if (ifxx == 0){
            newssw.setChecked(false);
        }
        if (ifsy==1){
            sysw.setChecked(true);
        }else if (ifsy == 0){
            sysw.setChecked(false);
        }
        if (ifzd==1){
            zdsw.setChecked(true);
        }else if (ifzd == 0){
            zdsw.setChecked(false);
        }
        if (ifyc==1){
            ycsw.setChecked(true);
        }else if (ifyc == 0){
            ycsw.setChecked(false);
        }
        setlistenswith();
    }
    @OnClick({R.id.backacli, R.id.newnewsli,R.id.voiceli,R.id.shakeli,R.id.concealli,R.id.addli_right_layout })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backacli:
                finish();
                break;
            case R.id.addli_right_layout:
                onBackPressed();
                break;
            case R.id.concealli:
                if (ycsw.isChecked())
                    ifconceal=false;
                if (ifconceal){
                    ycsw.setChecked(true);
                    ifconceal=false;
                }else {
                    ycsw.setChecked(false);
                    ifconceal=true;
                }
                break;
            case R.id.newnewsli:
                if (newssw.isChecked())
                    ifnews=false;
                if (ifnews){
                    newssw.setChecked(true);
                    ifnews=false;


                }else {
                    newssw.setChecked(false);
                    ifnews = true;
                }
                break;
            case R.id.voiceli:
                if (sysw.isChecked())
                    ifvoice=false;
                if (ifvoice){
                    sysw.setChecked(true);
                    ifvoice=false;
                }else {
                    sysw.setChecked(false);
                    ifvoice=true;
                }
                break;
            case R.id.shakeli:
                if (zdsw.isChecked())
                    ifshake=false;
                if (ifshake){
                    zdsw.setChecked(true);
                    ifshake=false;
                }else {
                    zdsw.setChecked(false);
                    ifshake=true;
                }
                break;


        }
    }
    public void setlistenswith(){
        newssw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MyShareUtil.sharedPint("ifxx",1);
                    sysw.setEnabled(true);
                    zdsw.setEnabled(true);
                    sysw.setChecked(true);
                    zdsw.setChecked(true);
                    settingsModel.setSettingMsgNotification(true);
                    settingsModel.setSettingMsgVibrate(true);
                    settingsModel.setSettingMsgSound(true);
                }else{
                    MyShareUtil.sharedPint("ifxx",0);
                    MyShareUtil.sharedPint("ifzd",0);
                    MyShareUtil.sharedPint("ifsy",0);
                    sysw.setEnabled(false);
                    zdsw.setEnabled(false);
                    sysw.setChecked(false);
                    zdsw.setChecked(false);
                    settingsModel.setSettingMsgNotification(false);
                    settingsModel.setSettingMsgVibrate(false);
                    settingsModel.setSettingMsgSound(false);
                }
            }
        });
        sysw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MyShareUtil.sharedPint("ifsy",1);
                    settingsModel.setSettingMsgSound(true);

                }else{
                    MyShareUtil.sharedPint("ifsy",0);
                    settingsModel.setSettingMsgSound(false);

                }
            }
        });
        zdsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    settingsModel.setSettingMsgVibrate(true);
                    MyShareUtil.sharedPint("ifzd",1);
                }else{
                    MyShareUtil.sharedPint("ifzd",0);
                    settingsModel.setSettingMsgVibrate(false);

                }
            }
        });
        ycsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    MyShareUtil.sharedPint("ifyc",1);
                    nosee();
                }else{
                    MyShareUtil.sharedPint("ifyc",0);
                    yessee();
                }
            }
        });
    }

    private void nosee() {
        String url = Url.setBusinessSee;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        params.put("userNm", UserNative.getName());//用户id
        params.put("seeFlg", 0);//用户id
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
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
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }
    private void yessee() {
        String url = Url.setBusinessSee;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        params.put("userNm", UserNative.getName());//用户id
        params.put("seeFlg", 1);//用户id
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
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
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

}

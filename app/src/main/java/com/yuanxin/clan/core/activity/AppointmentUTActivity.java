package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.AppointmentEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.view.TimePickerDia;
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
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/22 0022 14:22
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class AppointmentUTActivity extends BaseActivity implements TimePickerDia.TimePickerDialogInterface{
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.utte)
    TextView utte;
    @BindView(R.id.qrte)
    TextView qrte;
    @BindView(R.id.uttemin)
    TextView uttemin;

    private String bVisitorPhone;
    private int visitAppointmentId,aVisitorId,bVisitorId;
    private AppointmentEntity a;
    private TimePickerDia mTimePickerDialog ;

    @Override
    public int getViewLayout() {
        return R.layout.appointment_ut;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.my_info_head_bg));
        initview();
        mTimePickerDialog = new TimePickerDia(AppointmentUTActivity.this);
    }

    private void initview(){
        a = new AppointmentEntity();
        a = (AppointmentEntity) getIntent().getSerializableExtra("datas");
        bVisitorPhone = a.getbVisitorPhone();
        aVisitorId = a.getaVisitorId();
        visitAppointmentId=a.getVisitAppointmentId();
        bVisitorId=a.getbVisitorId();

    }

    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.qrte,R.id.utte,R.id.uttemin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
            case R.id.utte:
                mTimePickerDialog.showDateAndTimePickerDialog();
                break;
            case R.id.qrte:
                changeTime();
                break;
        }
    }



    private void changeTime() {
        String url = Url.changeTime;
        RequestParams params = new RequestParams();
        params.put("visitAppointmentId", visitAppointmentId);//用户id
        params.put("aVisitorId", aVisitorId);
        params.put("time", utte.getText().toString());
        params.put("userNm", UserNative.getName());
        params.put("userId", UserNative.getId());
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(AppointmentUTActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(AppointmentUTActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        onBackPressed();
                        MyShareUtil.sharedPstring("ud",utte.getText().toString());
//                        Intent intent = new Intent(AppointmentUTActivity.this, ToMeAppointDetailActivity.class);
//                        intent.putExtra("datas", (Serializable) a);
//                        intent.putExtra("ud",utte.getText().toString());
//                        startActivity(intent);
                    } else {
                        ToastUtil.showWarning(AppointmentUTActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    @Override
    public void positiveListener() {
        int hour = mTimePickerDialog.getHour();
        int minute = mTimePickerDialog.getMinute();
        int year = mTimePickerDialog.getYear();
        int month = mTimePickerDialog.getMonth();
        int day = mTimePickerDialog.getDay();
        utte.setText(year+"-"+month+"-"+day+" "+hour+":"+minute);
    }

    @Override
    public void negativeListener() {

    }
}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.jpinyin.ChineseHelper;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * 预约拜访类
 */

public class AppointmentOneActivity  extends BaseActivity implements TimePickerDia.TimePickerDialogInterface{
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.bfqynamete)
    TextView bfqynamete;
    @BindView(R.id.bfrnamete)
    TextView bfrnamete;
    @BindView(R.id.qxzsjte)
    TextView qxzsjte;
    @BindView(R.id.qrte)
    TextView qrte;
    @BindView(R.id.bfyyet)
    EditText bfyyet;

    private String bVisitorPhone;
    private String bVisitorId;
    private AppointmentEntity a;
    private TimePickerDia mTimePickerDialog ;
    private String bVisitorNm,bVisitorCompany;

    @Override
    public int getViewLayout() {
        return R.layout.appointmentone;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        initview();
        mTimePickerDialog = new TimePickerDia(AppointmentOneActivity.this);
    }

    private void initview(){
        Intent intent =getIntent();
        bVisitorId = intent.getStringExtra("id");
        bVisitorNm =intent.getStringExtra("name");
        bVisitorCompany =intent.getStringExtra("ac");
        bVisitorPhone =intent.getStringExtra("ph");
        bfrnamete.setText(bVisitorNm);
        bfqynamete.setText(bVisitorCompany);
    }

    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.qrte,R.id.qxzsjte})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
            case R.id.qxzsjte:
                mTimePickerDialog.showDateAndTimePickerDialog();
                break;
            case R.id.qrte:
                if (TextUtil.isEmpty(qxzsjte.getText().toString())|| ChineseHelper.containsChinese(qxzsjte.getText().toString())==true){
                    ToastUtil.showInfo(AppointmentOneActivity.this, "请选择拜访时间", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtil.isEmpty(bfyyet.getText().toString())){
                    ToastUtil.showInfo(AppointmentOneActivity.this, "请输入拜访原因", Toast.LENGTH_SHORT);
                    return;
                }
                visitadd();
                break;
        }
    }



    private void visitadd() {
        String url = Url.visitadd;
        RequestParams params = new RequestParams();
        params.put("aVisitorCompany", UserNative.getEpNm());//用户id
        params.put("aVisitorId", UserNative.getId());
        params.put("aVisitorNm", UserNative.getName());
        params.put("aVisitorPhone", UserNative.getPhone());

        params.put("bVisitorId", bVisitorId);
        params.put("bVisitorNm", bVisitorNm);
        params.put("bVisitorPhone", bVisitorPhone);
        params.put("bVisitorCompany", bVisitorCompany);
        params.put("reason", bfyyet.getText().toString());
        params.put("time", qxzsjte.getText().toString()+":00");
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(AppointmentOneActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(AppointmentOneActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        onBackPressed();

                    } else {
                        ToastUtil.showWarning(AppointmentOneActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
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
        qxzsjte.setText(year+"-"+month+"-"+day+" "+hour+":"+minute);
    }

    @Override
    public void negativeListener() {

    }
}


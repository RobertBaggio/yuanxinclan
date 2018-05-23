package com.yuanxin.clan.core.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.AppointmentEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 */

public class ToMeAppointDetailActivity extends BaseActivity {
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.apenamete)
    TextView apenamete;
    @BindView(R.id.aponeimete)
    TextView aponeimete;
    @BindView(R.id.apstatuste)
    TextView apstatuste;
    @BindView(R.id.apcausete)
    TextView apcausete;
    @BindView(R.id.apnamete)
    TextView apnamete;
    @BindView(R.id.apphonete)
    TextView apphonete;
    @BindView(R.id.agreete)
    TextView agreete;
    @BindView(R.id.aputimete)
    TextView aputimete;
    @BindView(R.id.aputte)
    TextView aputte;
    @BindView(R.id.apimage)
    ImageView apimage;
    @BindView(R.id.aputli)
    LinearLayout aputli;
    @BindView(R.id.apmyutli)
    LinearLayout apmyutli;
    @BindView(R.id.aptomeli)
    LinearLayout aptomeli;

    private String bVisitorPhone;
    private int visitAppointmentId,aVisitorId,bVisitorId;
    private AppointmentEntity a;

    @Override
    public int getViewLayout() {
        return R.layout.tomeappointdetail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
//        setStatusBar(this.getResources().getColor(R.color.my_info_head_bg));
        initview();

    }

    private void initview(){
        String iftome = getIntent().getStringExtra("tome");

        a = new AppointmentEntity();
        a = (AppointmentEntity) getIntent().getSerializableExtra("datas");
        String ename = a.getEname();
        String time = a.getTime();
        String uname = a.getUname();
        String status = a.getStatus();
        bVisitorPhone = a.getbVisitorPhone();
        aVisitorId = a.getaVisitorId();
        visitAppointmentId=a.getVisitAppointmentId();
        bVisitorId=a.getbVisitorId();
        String cause = a.getReason();

        apstatuste.setText(status);
        apnamete.setText(uname);
        aponeimete.setText(DateDistance.yuyuetimetodate(time));
        apenamete.setText(ename);
        apphonete.setText(bVisitorPhone);
        apcausete.setText(cause);

        if (iftome.equals("1")){
            aptomeli.setVisibility(View.VISIBLE);
        }
        if (status.equals("预约时间变更")){
            apimage.setImageResource(R.drawable.chage);
            aputli.setVisibility(View.VISIBLE);
            apmyutli.setVisibility(View.VISIBLE);
            aputimete.setText(time);
        }else if (status.equals("预约成功")){
            aptomeli.setVisibility(View.GONE);
            apimage.setImageResource(R.drawable.icon_right);
        }else if (status.equals("拒绝预约")){
            apimage.setImageResource(R.drawable.defeatedred);
            aptomeli.setVisibility(View.GONE);
        }else {
            apimage.setImageResource(R.drawable.waitting);
        }
        if (status.equals("预约成功")){

        }

    }

    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.apgiveupte,R.id.aprejectte,R.id.apagreeutte,R.id.aputte,R.id.agreete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
            case R.id.agreete:
                showagreedialog();
                break;
            case R.id.aprejectte:
                shownoagreedialog();
                break;
            case R.id.apagreeutte:
                showchagetdialog();
                break;
            case R.id.apgiveupte:
                shownochagetdialog();
                break;
            case R.id.aputte:
                Intent intent = new Intent(ToMeAppointDetailActivity.this, AppointmentUTActivity.class);
                intent.putExtra("datas", (Serializable) a);
                startActivity(intent);
                break;
        }
    }

    private void agreeViSit() {
        String url = Url.agreevisit;
        RequestParams params = new RequestParams();
        params.put("visitAppointmentId", visitAppointmentId);//用户id
        params.put("aVisitorId", aVisitorId);
        params.put("bVisitorPhone", bVisitorPhone);
        params.put("userNm", UserNative.getName());
        params.put("userId", UserNative.getId());
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(ToMeAppointDetailActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        onBackPressed();
                        ToastUtil.showSuccess(ToMeAppointDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(ToMeAppointDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    private void refuseVisit() {

        String url = Url.refuseVisit;
        RequestParams params = new RequestParams();
        params.put("visitAppointmentId", visitAppointmentId);//用户id
        params.put("aVisitorId", aVisitorId);
        params.put("userNm", UserNative.getName());
        params.put("userId", UserNative.getId());
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(ToMeAppointDetailActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(ToMeAppointDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(ToMeAppointDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    private void agreeChangeTime() {
        String url = Url.agreeChangeTime;
        RequestParams params = new RequestParams();
        params.put("visitAppointmentId", visitAppointmentId);//用户id
        params.put("bVisitorId", bVisitorId);
        params.put("userNm", UserNative.getName());
        params.put("userId", UserNative.getId());
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(ToMeAppointDetailActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(ToMeAppointDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(ToMeAppointDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    private void refuseChangeTime() {
        String url = Url.refuseChangeTime;
        RequestParams params = new RequestParams();
        params.put("visitAppointmentId", visitAppointmentId);//用户id
        params.put("bVisitorId", bVisitorId);
        params.put("userNm", UserNative.getName());
        params.put("userId", UserNative.getId());
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(ToMeAppointDetailActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(ToMeAppointDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(ToMeAppointDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String ss = MyShareUtil.getSharedString("ud");
        if (ss.length()>3){
            apmyutli.setVisibility(View.VISIBLE);
            aputimete.setText(ss);
            MyShareUtil.sharedPstring("ud","");
        }
    }
    private void showagreedialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(ToMeAppointDetailActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否同意预约！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        agreeViSit();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }
    private void shownoagreedialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(ToMeAppointDetailActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否拒绝预约！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refuseVisit();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }
    private void showchagetdialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(ToMeAppointDetailActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否同意更改预约时间！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        agreeChangeTime();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }
    private void shownochagetdialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(ToMeAppointDetailActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否拒绝更改预约时间！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        refuseChangeTime();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

}

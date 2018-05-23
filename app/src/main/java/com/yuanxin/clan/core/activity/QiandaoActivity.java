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
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/15 0015 14:57
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class QiandaoActivity extends BaseActivity {

    @BindView(R.id.rlLeft)
    LinearLayout rlLeft;
    @BindView(R.id.outli)
    LinearLayout outli;
    @BindView(R.id.inlayout)
    LinearLayout inlayout;
    @BindView(R.id.backli)
    LinearLayout backli;
    @BindView(R.id.qdnamete)
    TextView qdnamete;
    @BindView(R.id.qdtimete)
    TextView qdtimete;
    @BindView(R.id.qdcontentte)
    TextView qdcontentte;
    @BindView(R.id.qddresste)
    TextView qddresste;
    @BindView(R.id.nowtimete)
    TextView nowtimete;
    @BindView(R.id.lilongte)
    TextView lilongte;
    @BindView(R.id.nowtimeteout)
    TextView nowtimeteout;


    private String id;
    private String zl;
    private String filestring;
    private String url;
    /**
     * 商圈活动扫码接口
     */
    private static final String ACTION = "businessActivitySign/scanCode";
    /**
     * 商圈活动跳转
     */
    private static final String ACTION_CODE = "businessActivitySign/code";
    /**
     * 商圈活动跳转页面
     */
    private static final String ACTION_REDIRECT = "wechatweb/code-success";
    /**
     * 签到页面
     */
    public static final String SIGN = "wechatweb/attendance";
    /**
     * 重复签到
     */
    public static final String SIGN_REPEAT = "wechatweb/sign-repeat";
    /**
     * 签到时间未到
     */
    public static final String SIGN_TIME_NOT_ARRIVED = "wechatweb/sign-time-not-arrived";
    /**
     * 签退页面
     */
    public static final String SIGN_OUT = "wechatweb/sign-out";
    /**
     * 绑定页面
     */
    public static final String BINDING = "wechatweb/fill-form";
    /**
     * 活动失效页面
     */
    public static final String INVALID = "wechatweb/invalid";
    /**
     * 商圈活动抽奖
     */
    public static final String ACTION_DRAW_GAME = "";
    /**
     * 商圈活动抽奖扫码签到
     */
    public static final String ACTION_DRAW = "business/draw/scanCodeSign";
    /**
     * 不是商圈人员
     */
    public static final String SIGN_NOT_BUSINESS = "wechatweb/sign_not_business";


    @Override
    public int getViewLayout() {
        return R.layout.qiandaolayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        id = getIntent().getStringExtra("id");
        zl = getIntent().getStringExtra("zl");
        url = getIntent().getStringExtra("url");

        getqiandaocontent(id);
    }

    @OnClick({R.id.rlLeft,R.id.qdtonextte,R.id.backli})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                onBackPressed();
                break;
            case R.id.backli:
                finish();
                break;
            case R.id.qdtonextte:
                if (filestring.equals("null")|| TextUtil.isEmpty(filestring)){
                    ToastUtil.showInfo(QiandaoActivity.this, "无资料", Toast.LENGTH_SHORT);
                    return;
                }
                Intent intent = new Intent();
                intent.putExtra("file",filestring);
                intent.setClass(QiandaoActivity.this, HuiyiZiliaoActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getqiandaocontent(String userId) {
        String url = Url.businessActivitySign;
        RequestParams params = new RequestParams();
        params.put("id", userId);
        params.put("userPhone", UserNative.getPhone());
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(QiandaoActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
//                        entityList.remove(position);
                        JSONObject one = new JSONObject(object.getString("data")) ;
                        JSONObject data = new JSONObject(one.getString("data"));
                        String status = one.getString("url");
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
                        String thistime = df.format(new Date());
                        nowtimeteout.setText(thistime);
                        if (status.equals("wechatweb/attendance")){
                            inlayout.setVisibility(View.VISIBLE);
                            showdata(data);
                        }else {
                            outli.setVisibility(View.VISIBLE);
                            backli.setVisibility(View.VISIBLE);
                        }
                    } else {
                        ToastUtil.showWarning(QiandaoActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(UserProfileActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }
    public void showdata(JSONObject dataObject){
        try {
                String name = dataObject.getString("sponsor");
                String introduce = dataObject.getString("introduce");
                String startTime = dataObject.getString("startTime");
                String endTime = dataObject.getString("endTime");
                String city = dataObject.getString("city");
                String province = dataObject.getString("province");
                String area = dataObject.getString("area");
                String detail = dataObject.getString("detail");
                filestring = dataObject.getString("file");

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String thistime = df.format(new Date());

                nowtimete.setText(thistime);
//                introduce ="中文网第三方都是访问发废物潍坊额无法我饿中文网第三方都是访问发废物潍坊额无法我饿中文网第三方都是访问发废物潍坊额无法我饿中文网第三方都是访问发废物潍坊额无法我饿中文网第三方都是访问发废物潍坊额无法我饿中文网第三方都是访问发废物潍坊额无法我饿";

                qdcontentte.setText(introduce);
                qdnamete.setText("主办单位："+name);
                qddresste.setText("地址："+province+"-"+city+"-"+area+"  "+detail);
                qdtimete.setText("时间："+ DateDistance.getDistanceTimeToZW(startTime)+"  至  "+DateDistance.getDistanceTimeToZW(endTime));

                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) lilongte.getLayoutParams(); //取控件textView当前的布局参数

               double a= Math.floor(introduce.length()/23);
                int   c   =   (new   Double(a)).intValue();
                linearParams.width = 10;// 控件的宽强制设成30
                linearParams.height = 47*c+80;// 控件的高强制设成20

                lilongte.setLayoutParams(linearParams);
//            }
//        }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

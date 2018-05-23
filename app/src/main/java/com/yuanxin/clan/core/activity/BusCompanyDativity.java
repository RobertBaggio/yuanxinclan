package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.CommonString;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.WebViewJavaScriptFunction;
import com.yuanxin.clan.mvp.view.X5WebView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/19 0019 22:35
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class BusCompanyDativity extends BaseActivity {


    @BindView(R.id.tbsContent)
    X5WebView activityBaseWebAddWebView;
    @BindView(R.id.activity_yuan_xin_crowd_left_image)
    ImageView activityYuanXinCrowdLeftImage;
    @BindView(R.id.activity_yuan_xin_crowd_close)
    LinearLayout activityYuanXinCrowdClose;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activityYuanXinCrowdLeftLayout;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activityYuanXinCrowdMiddleText;
    //    @BindView(R.id.activity_t_yuan_xin_crowd_right_image)
//    ImageView activityTYuanXinCrowdRightImage;
//    @BindView(R.id.activity_t_yuan_xin_crowd_right_image_layout)
//    LinearLayout activityTYuanXinCrowdRightImageLayout;
    @BindView(R.id.activity_news_chat_base_web_head_layout)
    RelativeLayout activityNewsChatBaseWebHeadLayout;
    private boolean flag;
    private int newsId, userId, epId;
    private String userNm, businessAreaId;
    private LocalBroadcastManager localBroadcastManager;
    private String epStyleType;
    private String url;
    private String groupid;
    private String name;


    @Override
    public int getViewLayout() {
        return R.layout.mysqxqla;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initModule();
        initWebView();
        activityYuanXinCrowdClose.setVisibility(View.GONE);
    }


    public void initModule() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        businessAreaId = bundle.getString("businessAreaId");
        epStyleType = bundle.getString("epStyleType");
        groupid = bundle.getString("gid");
        name = bundle.getString("name");
        activityYuanXinCrowdMiddleText.setText("企业详情");
        Log.v("lgq","群聊id。。。。==="+groupid);
        if (TextUtil.isEmpty(epStyleType)) {
            //没有就用默认的
            epStyleType = "business";
        }
        String url = getIntent().getStringExtra("url");
//        userId = UserNative.getId();
//        url = Url.urlWeb + "/" + epStyleType + "&param=" + businessAreaId + "&appFlg=1";
//        Log.v("lgq","url==="+url);
        activityBaseWebAddWebView.loadUrl(url);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityBaseWebAddWebView = null;
    }

    @OnClick({R.id.activity_yuan_xin_crowd_left_layout, R.id.activity_yuan_xin_crowd_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_crowd_left_layout:
                onBackPressed();
                break;
            case R.id.activity_yuan_xin_crowd_close:
                finish();
                break;
//            case R.id.activity_t_yuan_xin_crowd_right_image_layout://收藏
//                if (TextUtil.isEmpty(groupid)){
//                    Toast.makeText(getApplicationContext(), "该群不存在", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                gotoliaotian();

//                if (!flag) {
//                    activityTYuanXinCrowdRightImage.setImageResource(R.drawable.news_collecte_pre);
//                    collecte();//收藏
//                    //发送广播
//                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
//                    Intent intent = new Intent("com.example.broadcasttest.BUSINESS_FRESH");
//                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
//                } else {
//                    activityTYuanXinCrowdRightImage.setImageResource(R.drawable.news_collecte_nomal);
//                    deleteCollecte();//取消收藏
//                    //发送广播
//                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
//                    Intent intent = new Intent("com.example.broadcasttest.BUSINESS_FRESH");
//                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
//                }
//                flag = !flag;
//                break;
        }
    }

    private void initWebView() {
        activityBaseWebAddWebView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }
            @JavascriptInterface
            public void goToBusinessChatRoom() {
//            window.yxbl_app.toChat()  //js调用android源码方法,发起环信聊天
                if (TextUtil.isEmpty(groupid)){
                    ToastUtil.showError(getApplicationContext(), "该群不存在", Toast.LENGTH_SHORT);
                    return;
                }
                gotoliaotian();
            }
            @JavascriptInterface
            public void toLogin() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

        }, CommonString.js2Android);
    }

    private void gotoliaotian() {
        userId = UserNative.getId();
        userNm = UserNative.getName();
        String url = Url.gotoliaotian;
        RequestParams params = new RequestParams();
        params.put("userId", userId);//省userId
        params.put("businessAreaId", businessAreaId);//用户名
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq","商圈id返回。。。"+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        Intent intent = new Intent(BusCompanyDativity.this, PersionChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_ID, groupid);
                        intent.putExtra(Constant.USER_NAME, UserNative.getName());
                        intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                        intent.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                        intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                        intent.putExtra("type", name);
                        startActivity(intent);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (activityBaseWebAddWebView.canGoBack()) {
            activityBaseWebAddWebView.goBack();
        } else {
//            super.onBackPressed();
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && activityBaseWebAddWebView.canGoBack()) {
            activityBaseWebAddWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

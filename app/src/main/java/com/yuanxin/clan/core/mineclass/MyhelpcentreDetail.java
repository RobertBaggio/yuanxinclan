package com.yuanxin.clan.core.mineclass;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.LoginActivity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.mvp.utils.CommonString;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.WebViewJavaScriptFunction;
import com.yuanxin.clan.mvp.view.X5WebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/17 0017 18:11
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyhelpcentreDetail extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.activity_chat_base_web_talk)
    TextView activityChatBaseWebTalk;
    @BindView(R.id.activity_yuan_xin_crowd_left_image)
    ImageView activityYuanXinCrowdLeftImage;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activityYuanXinCrowdLeftLayout;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView titleddleText;
    @BindView(R.id.activity_news_chat_base_web_head_layout)
    RelativeLayout activityNewsChatBaseWebHeadLayout;
    @BindView(R.id.zixunshouchangimage)
    ImageView zixunshouchangimage;
    @BindView(R.id.activity_chat_baseweb_webview)
    X5WebView activityChatBasewebWebview;
    @BindView(R.id.zixunfenxiangimage)
    ImageView zixunfenxiangimage;
    //    @BindView(R.id.appbar)
//    AppBarLayout mappBarLayout;
    private boolean flag=true;
    private LocalBroadcastManager localBroadcastManager;
    private String title,id;

    private int refreshTime = 0;
    @Override
    public int getViewLayout() {
        return R.layout.myhelpcentredetail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
//        mappBarLayout.addOnOffsetChangedListener(this);
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        enableLiteWndFunc();
        zixunfenxiangimage.setVisibility(View.GONE);
        zixunshouchangimage.setVisibility(View.GONE);
        title = getIntent().getStringExtra("title");
        id = getIntent().getStringExtra("id");
        titleddleText.setText(title);
//        http://192.168.1.116:8080/yuanxinbuluo/weixin/getJsp?url=wechatweb/question-detail&param=1

//        userId = UserNative.getId();
        String url = Url.urlWeb + "/question-detail&param=" + id;

        activityChatBasewebWebview.loadUrl(url);

        activityChatBasewebWebview.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }


            @JavascriptInterface
            public void toLogin() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            @JavascriptInterface
            public void onPhone(String toChatUsername) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+toChatUsername));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            @JavascriptInterface
            public void toCall(String toChatUsername) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+toChatUsername));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            /**
             * 测试坐标上传是否成功
             *
             * @param latitude
             * @param longitude
             */
            @JavascriptInterface
            public void outLocation(String latitude, String longitude) {
                System.out.println("javascript输出：" + latitude + "  " + longitude);
            }
        }, CommonString.js2Android);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (refreshTime != 0) {
                    activityChatBasewebWebview.reload();
                }
                refreshTime++;
            }
        });
        refreshLayout.autoRefresh();
        activityChatBasewebWebview.setListener(new X5WebView.OnScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void scrollHeight(int h) {
                // 这里解决SmartRefreshLayout与X5Webview的滚动冲突
                if (h == 0) {
                    refreshLayout.setEnableRefresh(true);
                    refreshLayout.setEnableOverScrollDrag(true);
                } else {
                    refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableOverScrollDrag(false);
                }
            }
        });
        activityChatBasewebWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(view, url);
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        try {
            super.onConfigurationChanged(newConfig);
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {

            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // /////////////////////////////////////////
    // 向webview发出信息
    private void enableX5FullscreenFunc() {

        if (activityChatBasewebWebview.getX5WebViewExtension() != null) {
            ToastUtil.showInfo(this, "开启X5全屏播放模式", Toast.LENGTH_LONG);
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            activityChatBasewebWebview.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void disableX5FullscreenFunc() {
        if (activityChatBasewebWebview.getX5WebViewExtension() != null) {
            ToastUtil.showInfo(this, "恢复webkit初始状态", Toast.LENGTH_LONG);
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", true);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            activityChatBasewebWebview.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enableLiteWndFunc() {
        if (activityChatBasewebWebview.getX5WebViewExtension() != null) {
//            Toast.makeText(this, "开启小窗模式", Toast.LENGTH_LONG).show();
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", true);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 2);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            activityChatBasewebWebview.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }

    private void enablePageVideoFunc() {
        if (activityChatBasewebWebview.getX5WebViewExtension() != null) {
            ToastUtil.showInfo(this, "页面内全屏播放模式", Toast.LENGTH_LONG);
            Bundle data = new Bundle();

            data.putBoolean("standardFullScreen", false);// true表示标准全屏，会调起onShowCustomView()，false表示X5全屏；不设置默认false，

            data.putBoolean("supportLiteWnd", false);// false：关闭小窗；true：开启小窗；不设置默认true，

            data.putInt("DefaultVideoScreen", 1);// 1：以页面内开始播放，2：以全屏开始播放；不设置默认：1

            activityChatBasewebWebview.getX5WebViewExtension().invokeMiscMethod("setVideoParams",
                    data);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityChatBasewebWebview = null;
    }


    @OnClick({R.id.activity_yuan_xin_crowd_left_layout,R.id.zixunfenxiangimage, R.id.zixunshouchangimage, R.id.activity_chat_base_web_talk})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_crowd_left_layout:
                goBack();
                break;
            case R.id.zixunfenxiangimage://分享
                break;
            case R.id.zixunshouchangimage://收藏
                if (flag) {
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.COMPANY_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                } else {
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.COMPANY_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                }
                break;
        }
    }

    /* 改写物理按键返回的逻辑 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //返回操作
    private void goBack() {
        if (activityChatBasewebWebview.canGoBack()) {
            // 返回上一页面
            activityChatBasewebWebview.goBack();
//            activityChatBasewebWebview.reload();
        } else {
            //最顶页就关闭
            finish();
        }
    }
}

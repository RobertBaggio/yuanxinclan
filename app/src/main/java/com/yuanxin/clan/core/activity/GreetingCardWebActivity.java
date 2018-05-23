package com.yuanxin.clan.core.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.utils.ShareTypes;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebBackForwardList;
import com.tencent.smtt.sdk.WebHistoryItem;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.event.ShareCardEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.view.PhotoBrowserActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.share.ShareDialog;
import com.yuanxin.clan.mvp.share.ShareInfoVo;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.X5WebView;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan
 * Describe: 贺卡页
 * Date: 2017/6/13 0013 11:29
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class GreetingCardWebActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.activity_yuan_xin_left_layout)
    LinearLayout backLayout;
    @BindView(R.id.share)
    ImageView shareBtn;
    @BindView(R.id.activity_baseweb_webview)
    X5WebView webView;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView mTitleText;

    private String mtitle;
    private String mcontent;
    private String mlogo;
    private String eid;
    private String url;
    private String mshareUrl;

    private int refreshTime = 0;
    @Override
    public int getViewLayout() {
        return R.layout.newgreelayout;
    }//隐藏评论

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        EventBus.getDefault().register(this);
//        initWebView();
        setWebView();
        initModule();
        eid = UserNative.getEpId();
//        getEpInfo();
        setStatusBar(this.getResources().getColor(R.color.mine_page_color));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (refreshTime != 0) {
                    webView.reload();
                }
                refreshTime ++;
            }
        });
        refreshLayout.autoRefresh();
    }
    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void setWebView() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSetting.setAllowFileAccess(true);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        webSetting.setLoadsImagesAutomatically(true);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);

        webView.setSaveEnabled(true);
        webView.addJavascriptInterface(new JavascriptInterface(this), "yxbl_app");
        webView.setWebViewClient(new WebClient());
//        activityChatBasewebWebview.setWebChromeClient(new WebChrome());
        webView.setVerticalScrollBarEnabled(false);
        webView.setVerticalScrollbarOverlay(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setHorizontalScrollbarOverlay(false);
        webView.setProgressbarDrawable(getResources().getDrawable(R.color.mine_page_color));

        webView.setListener(new X5WebView.OnScrollListener() {
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
    }
    //js通信接口
    class JavascriptInterface {
        private Context context;

        JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String imageUrls, String img) {
            Intent intent = new Intent();
            intent.putExtra("imageUrls", imageUrls);
            intent.putExtra("curImageUrl", img);
            intent.setClass(GreetingCardWebActivity.this, PhotoBrowserActivity.class);
            startActivity(intent);
        }

        @android.webkit.JavascriptInterface
        public void toLogin() {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        @android.webkit.JavascriptInterface
        public void toCompanyDetail(int epId, String accessPath) {
//            window.yxbl_app.toCompanyDetail()  //js调用android源码方法
            Intent intent = new Intent(GreetingCardWebActivity.this, CompanyDetailWebActivity.class);
            intent.putExtra("epId", epId);
            intent.putExtra("accessPath", accessPath);
            startActivity(intent);
        }
        @android.webkit.JavascriptInterface
        public void getGreetingCardProc(String title, String imgUrl, String content, String url) {
            mtitle = title;
            mlogo = imgUrl;
            mcontent = content;
            mshareUrl = url;
            mTitleText.setText(mtitle);
        }


    }


    @SuppressLint("SetJavaScriptEnabled")
    private class WebClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (url.contains("card-list")) {
                shareBtn.setVisibility(View.GONE);
            }else{
                mTitleText.setText(getCardNameFromUrlParams(url));
                shareBtn.setVisibility(View.VISIBLE);
//                shareUrl = url;
            }
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            refreshLayout.finishRefresh();
            // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
//            view.loadUrl(CommonString.imgClick);
        }
    }
    private String getCardNameFromUrlParams(String url) {
        String result = "电子贺卡";
        try {
            result = URLDecoder.decode(TextUtil.URLRequest(url).get("cardname").replace("!", "%"), "UTF-8");
        } catch (Exception e) {
            Logger.e(e.toString());
        }
        return result;
    }

    public void initModule() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
//        http://192.168.1.102/yuanxinbuluo/weixin/getJsp?url=wechatweb/yxGreetingCards&epId=936&appFlg=0&userNm=!E5!AD!99!E5!85!89!E7!A3!8A
        if (!TextUtil.isEmpty(url)) {
            webView.loadUrl(intent.getStringExtra("url"));
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop(){
        super.onStop();
//        webView.removeAllViews();
//        webView.destroy();
    }

    private void showShare() {
        // 分享信息要完善
        ShareInfoVo shareInfoVo = new ShareInfoVo();
//        shareInfoVo.setTitle(getCardNameFromUrlParams(webView.getUrl()));
        shareInfoVo.setTitle(mtitle);
//        http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/greeting-card-1&epId=936&appFlg=0
//        shareInfoVo.setUrl(webView.getUrl());
        shareInfoVo.setUrl(mshareUrl);
//        shareInfoVo.setContent(UserNative.getEpNm() + " "+ UserNative.getName() + " " + "预祝您阖家幸福团圆安康");//隐藏内容
        shareInfoVo.setContent(mcontent);//隐藏内容
//        int cardNum = Integer.parseInt(TextUtil.URLRequest(webView.getUrl()).get("number"));
//            shareInfoVo.setImgUrl(Url.img_domain+"/"+logo+Url.imageStyle640x640);
//        shareInfoVo.setImgUrl(Url.img_domain+"/"+"s" + cardNum + "_0.jpg-style_webp_375x225");
        shareInfoVo.setImgUrl(mlogo);
        // 贺卡1
//        shareInfoVo.setShareCardNum(cardNum);
        // "贺卡分享"
        shareInfoVo.setShareQiliaoType(ShareTypes.GREETING_CARD);
        ShareDialog.showShareDialog(getSupportFragmentManager(), shareInfoVo);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ShareCardEvent shareCardEvent) {
        // Android版本变量
        final int version = Build.VERSION.SDK_INT;
// 因为该方法在 Android 4.4 版本才可使用，所以使用时需进行版本判断
        if (version < 18) {
            // 贺卡分享000000001~0000000030，这里需拼接num
            webView.loadUrl("javascript:analysis('" + getShareNum(shareCardEvent.getmCardNum()) + "')");
        } else {
            webView.evaluateJavascript("javascript:analysis('" + getShareNum(shareCardEvent.getmCardNum()) + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    Logger.e(value);
                }
            });
        }
    }

    private String getShareNum(int num) {
        String result = "0000000";
        if (num < 10) {
            result += ("0" + num);
        } else {
            result += num;
        }
        return result;
    }

    private void getEpInfo() {
        String url = Url.getEpInfo;
        RequestParams params = new RequestParams();
        params.put("epId", UserNative.getEpId());//收藏项目ID newsId
        params.put("userId", UserNative.getId());//省userId
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        try {
                            JSONObject ep = object.getJSONObject("data");
//                            title = ep.getString("epNm");
//                            logo = ep.getString("epImage1");
//                            content = ep.getString("epDetail");
                        }catch (Exception e) {

                        }
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @OnClick({R.id.activity_yuan_xin_left_layout, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_left_layout:
                goBack();
                break;
            case R.id.share://分享
                if (TextUtil.isEmpty(mtitle)){
                    ToastUtil.showWarning(getApplicationContext(), "等待加载中", Toast.LENGTH_SHORT);
                    return;
                }

                showShare();
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
        if (webView.canGoBack()) {
            // 返回上一页面
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            WebBackForwardList backForwardList = webView.copyBackForwardList();
            if (backForwardList != null && backForwardList.getSize() != 0) {
                //当前页面在历史队列中的位置
                int currentIndex = backForwardList.getCurrentIndex();
                WebHistoryItem historyItem =
                        backForwardList.getItemAtIndex(currentIndex - 1);
                if (historyItem != null) {
                    String backPageUrl = historyItem.getUrl();
                    //url拿到可以进行操作
                    if (backPageUrl.contains("card-list")) {
                        mTitleText.setText(mtitle);
                        shareBtn.setVisibility(View.GONE);
                    }
                }
            }
            webView.goBack();
        } else {
            //最顶页就关闭
            if (webView!=null){
                webView.removeAllViews();
                webView.destroy();
                EventBus.getDefault().unregister(this);
            }
            finish();
        }
    }
}

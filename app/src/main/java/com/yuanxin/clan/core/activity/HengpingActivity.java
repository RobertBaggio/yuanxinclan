package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.RelativeLayout;

import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.view.ShoppingCartActivity;
import com.yuanxin.clan.mvp.utils.CommonString;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.WebViewJavaScriptFunction;
import com.yuanxin.clan.mvp.view.X5WebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 */

public class HengpingActivity extends BaseActivity {

    @BindView(R.id.webview)
    X5WebView mWebview;
    @BindView(R.id.rlLeft)
    RelativeLayout mRlLeft;
    @BindView(R.id.rlRight)
    RelativeLayout mRlRight;
    @BindView(R.id.rlWebMenu)
    RelativeLayout mRlWebMenu;


    @Override
    public int getViewLayout() {
        return R.layout.modityactivity;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initWebView();
        String url = getIntent().getStringExtra("url");
        String hs = getIntent().getStringExtra("hs");
        if (hs.equals("hp")){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        int id = getIntent().getIntExtra("id",0);
        if (id>0){
            url= Url.urlWeb+"/epMallInfo"+"&commodityId="+id+"&appFlg=1";
        }
        Log.v("lgq","........"+url);
//        www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/epMallInfo&commodityId=1362&appFlg=1
        mWebview.loadUrl(url);
//        rlRighttext.setVisibility(View.VISIBLE);

        mWebview.addJavascriptInterface(new WebViewJavaScriptFunction() {

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
    }

    private void initWebView() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int mDensity = metrics.densityDpi;
        mWebview.setInitialScale(25);
        if (mDensity == 120) {
            mWebview.getSettings().setDefaultZoom(com.tencent.smtt.sdk.WebSettings.ZoomDensity.CLOSE);
        }else if (mDensity == 160) {
            mWebview.getSettings().setDefaultZoom(com.tencent.smtt.sdk.WebSettings.ZoomDensity.MEDIUM);
        }else if (mDensity >= 240) {
            mWebview.getSettings().setDefaultZoom(com.tencent.smtt.sdk.WebSettings.ZoomDensity.FAR);
        }
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

    }

    @Override
    protected void onDestroy() {
        mWebview.removeAllViews();
        mWebview.destroy();
        super.onDestroy();
    }

    @OnClick({R.id.rlLeft, R.id.rlRight})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                goBack();
                break;
            case R.id.rlRight:
                startActivity(new Intent(this, ShoppingCartActivity.class));
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
        if (mWebview.canGoBack()) {
            // 返回上一页面
            mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
            mWebview.goBack();
        } else {
            //最顶页就关闭
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mWebview.removeAllViews();
        mWebview.destroy();
    }

}

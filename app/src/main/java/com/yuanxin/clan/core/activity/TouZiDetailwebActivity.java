package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.mvp.utils.CommonString;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.WebViewJavaScriptFunction;
import com.yuanxin.clan.mvp.view.X5WebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 */

public class TouZiDetailwebActivity extends BaseActivity{

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.webview)
    X5WebView mWebview;
    @BindView(R.id.rlLeft)
    LinearLayout mRlLeft;
    @BindView(R.id.title_text)
    TextView title_text;

    private String shopListId ,commodityId,orderUuid,intype,url,title;
    private String businessAreaId,investmentProjectId;

    private int refreshTime = 0;

    @Override
    public int getViewLayout() {
        return R.layout.touzdetail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        title = getIntent().getStringExtra("title");
        businessAreaId = getIntent().getStringExtra("businessAreaId");
        investmentProjectId = getIntent().getStringExtra("investmentProjectId");
        title_text.setText(title);

        url = Url.urlWeb+"/project-info&investmentProjectId="+investmentProjectId+"&businessAreaId="+businessAreaId+"&appFlg=1";

    //        http://192.168.200:8080/yuanxinbuluo/weixin/getJsp?url=wechatweb/circle-style-one&businessAreaId=517&appFlg=1
//        http://192.168.1.102/yuanxinbuluo/weixin/getJsp?url=wechatweb/serviceOrder-detail&orderUuid=10000000407967951&shopListId=110&commodityId=1988
//        http://192.168.1.102/yuanxinbuluo/weixin/getJsp?url=wechatweb/project-info&investmentProjectId=6&businessAreaId=373
    initWebView();
        mWebview.loadUrl(url);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (refreshTime != 0) {
                    mWebview.reload();
                }
                refreshTime++;
            }
        });
        refreshLayout.autoRefresh();

    }

    private void initWebView() {
        mWebview.setListener(new X5WebView.OnScrollListener() {
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
        mWebview.setProgressbarDrawable(getResources().getDrawable(R.drawable.progressbar_blue));
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
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
                view.loadUrl(CommonString.getCommodityDescription);
            }
        });
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
            public void toChat(String toChatUsername) {
//            window.yxbl_app.toChat()  //js调用android源码方法,发起环信聊天
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//                Log.e("lgq", toChatUsername);
//                Intent intent = new Intent(MyserviceDetailWebActivity.this, PersionChatActivity.class);
//                intent.putExtra(Constant.EXTRA_USER_ID, toChatUsername);
//                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
//                intent.putExtra("type", "与商家聊天中");
//                startActivity(intent);
            }

            @JavascriptInterface
            public void lookShoppingCar() {
//            window.yxbl_app.lookShoppingCar()  //js调用android源码方法,查看购物车
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//                startActivity(new Intent(MyserviceDetailWebActivity.this, ShoppingCartActivity.class));
            }

            @JavascriptInterface
            public void addGoodToCar(String commodityId, String commodityPrice, String commodityNumber, String commodityColor, String commoditySp, String userId, int shopDataFrom) {
//            window.yxbl_app.addGoodToCar()  //js调用android源码方法,加入购物车
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }

            }

            @JavascriptInterface
            public void toBuy(String commodityId, String commodityNm, String commodityImage1, String commodityPrice, String commodityNumber, String commodityColor, String commoditySp, String userId, int shopDataFrom) {
//            window.yxbl_app.toBuy()  //js调用android源码方法,立即购买
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }

            }

            @JavascriptInterface
            public void scanCode() {
//            window.yxbl_app.scanCode()  //js调用android源码方法,二维码扫描
            }


            @JavascriptInterface
            public void payWXpay(String orderid, String money, String callbackUrl, String describe, String paySuccessToUrl) {
            }

            @JavascriptInterface
            public void payAlipay(String orderid, String money, String callbackUrl, String describe, String paySuccessToUrl) {
            }

            @JavascriptInterface
            public void shareToApp(String title, String content, String imgUrl, String url) {
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


    @OnClick({R.id.rlLeft, })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                finish();
                break;

        }
    }

}

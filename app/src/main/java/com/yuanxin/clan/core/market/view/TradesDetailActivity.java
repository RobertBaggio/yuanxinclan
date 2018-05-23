package com.yuanxin.clan.core.market.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.easeui.utils.ShareTypes;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.LoginActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.bean.CommdoityDetail;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.share.ShareDialog;
import com.yuanxin.clan.mvp.share.ShareInfoVo;
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
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/29 0029 17:23
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
//http://192.168.1.102/yuanxinbuluo/weixin/getJsp?url=wechatweb/exhibition-detail&param=7&appFlg=0
public class TradesDetailActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.webview)
    X5WebView mWebview;
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activity_yuan_xin_fair_new_left_layout;
    @BindView(R.id.activity_company_information_detail_right_image)
    ImageView activity_company_information_detail_right_image;

    private int refreshTime = 0;

    private String logo, title, content, collect, mallNm,shareurl;
    @Override
    public int getViewLayout() {
        return R.layout.tradesdetalilayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        initWebView();
//        intent.putExtra("name",mEntities.get(position).getHallNm());
//        intent.putExtra("image",mEntities.get(position).getExhibitionTitleImg());
//        String time = DateDistance.getDistanceTimeToZW(mEntities.get(position).getStarTime())+" 至 "+DateDistance.getDistanceTimeToZW(mEntities.get(position).getEndTime());
//        intent.putExtra("time","会展时间："+mEntities.get(position).getEndTime()+mEntities.get(position).getExhibitionTitle());
        shareurl =Url.urlWeb+ "/exhibition-detail&param="+getIntent().getStringExtra("id")+"&appFlg=0";
        title = getIntent().getStringExtra("name");
        content = getIntent().getStringExtra("time");
        logo = getIntent().getStringExtra("image");

        mWebview.loadUrl(shareurl);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                if (refreshTime != 0) {
//                    mWebview.reload();
//                }
//                refreshTime++;
                mWebview.reload();
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
                view.loadUrl(CommonString.imgClickWithShowClass);
            }
        });
        mWebview.setProgressbarDrawable(getResources().getDrawable(R.drawable.progressbar_yellow));
        mWebview.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }
            @JavascriptInterface
            public void onPhone(String toChatUsername) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+toChatUsername));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            @JavascriptInterface
            public void toLogin() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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

    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.activity_company_information_detail_right_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                goBack();
                break;
            case R.id.activity_company_information_detail_right_image:
                showShare();
                break;

        }
    }

    private void showShare() {
        ShareInfoVo shareInfoVo = new ShareInfoVo();
//        Log.v("Lgq","url=="+url+"------------share-==="+shareurl);
        try{
            if (TextUtil.isEmpty(content)){
                content="圆心部落服务";
            }
            if (TextUtil.isEmpty(shareurl)){
                shareInfoVo.setTitle(title);
                shareInfoVo.setUrl(shareurl+"&links=share" );
            }else if(shareurl.contains("business-card")){
                shareInfoVo.setTitle(title+"-名片");
                shareInfoVo.setUrl(shareurl +"&links=share");
            }else if(shareurl.contains("epnewsdetail")){
                shareInfoVo.setTitle(title+"-动态");
                shareInfoVo.setUrl(shareurl +"&links=share");
            } else {
                shareInfoVo.setTitle(title);
                shareInfoVo.setUrl(shareurl+"&links=share" );
            }
            shareInfoVo.setContent(content);//隐藏内容
//       logo="http://images.yxtribe.com//upload/images/ep/916/20170802113827617.PNG-style_webp_640x640";
//            logo="http://www.yxtribe.com/yuanxinbuluo/upload/images/ep/287/20170509154527190.jpg";
            shareInfoVo.setImgUrl(logo);
            Log.i("lgq","lolg===="+content);
            //"企业分享"
            shareInfoVo.setShareQiliaoType(ShareTypes.COMPANY_MAIN_PAGE);
            ShareDialog.showShareDialog(getSupportFragmentManager(), shareInfoVo);
        }catch (Exception e){
            e.printStackTrace();
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
//        if (mWebview.canGoBack()) {
//            // 返回上一页面
//            mWebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
//            mWebview.goBack();
//        } else {
//            //最顶页就关闭
//            finish();
//        }
        mWebview.clearHistory();
        finish();
    }

    //加入购物车
    private void addGoodsToCar(CommdoityDetail mCommdoityDetail, int shopDataFrom) {
        if (mCommdoityDetail == null) {
            return;
        }
        String url = Url.addToCart;
        RequestParams params = new RequestParams();
        params.put("userId", Integer.valueOf(UserNative.getId()));//用户id
        //设置选择的颜色
        params.put("commodityColor", mCommdoityDetail.getCommodityColor());//颜色
        //设置选择的规格
        params.put("commoditySp", mCommdoityDetail.getCommoditySp());//商品规格
        params.put("commodityNumber", Integer.valueOf(mCommdoityDetail.getCommodityQuantity()));//商品数量
        params.put("commodityPrice", Double.valueOf(mCommdoityDetail.getCommodityPrice()));//商品价钱
        params.put("commodityId", Integer.valueOf(mCommdoityDetail.getCommodityId()));//商品id
        params.put("businessId", Integer.valueOf(mCommdoityDetail.getUserId()));//商家id
        params.put("delFlg", 1);
        params.put("shopDataFrom", shopDataFrom);
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), "加入购物车成功！", Toast.LENGTH_SHORT);
//                        startActivity(new Intent(ExternalWebActivity.this, ShoppingCartActivity.class));
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


}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.GongXuEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.market.bean.CommdoityDetail;
import com.yuanxin.clan.core.market.bean.OrderCommodity;
import com.yuanxin.clan.core.market.view.BuyOrderActivityWeb;
import com.yuanxin.clan.core.market.view.OutputGongyActivity;
import com.yuanxin.clan.core.market.view.OutputXuQiuActivity;
import com.yuanxin.clan.core.market.view.ShoppingCartActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.utils.CommonString;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.WebViewJavaScriptFunction;
import com.yuanxin.clan.mvp.view.X5WebView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/27 0027 13:51
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class GongXuDetailActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.webview)
    X5WebView mWebview;
    @BindView(R.id.rlLeft)
    LinearLayout mRlLeft;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activity_yuan_xin_crowd_middle_text;
    private GongXuEntity mGongXuEntity;

    private int refreshTime = 0;

    @Override
    public int getViewLayout() {
        return R.layout.gongxudetailla;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initWebView();
        String url = getIntent().getStringExtra("url");
        mGongXuEntity = (GongXuEntity) getIntent().getSerializableExtra("datas");
        String title = getIntent().getStringExtra("title");
        activity_yuan_xin_crowd_middle_text.setText(title);
//        activity_yuan_xin_crowd_middle_text.setText(title);
        int id = getIntent().getIntExtra("id",0);
        if (id>0){
            url= Url.urlWeb+"/epMallInfo"+"&commodityId="+id+"&appFlg=1";
        }
        Log.v("lgq","........"+url);
        Log.v("lgq","urll====  "+url);
//        www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/epMallInfo&commodityId=1362&appFlg=1
        mWebview.loadUrl(url);

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
            public void onPageFinished(WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(view, url);
                refreshLayout.finishRefresh();
            }
        });
        mWebview.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }
            @JavascriptInterface
            public void toChat(String toChatUsername) {
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                if (toChatUsername.equals(UserNative.getPhone())){
                    Toast.makeText(getApplicationContext(), "我发布的！", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(GongXuDetailActivity.this, PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, toChatUsername);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra(Constant.USER_NAME, UserNative.getName());
                intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                intent.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                intent.putExtra("type", "与商家聊天中");
                startActivity(intent);
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

            @JavascriptInterface
            public void onEdit() {
                if (mGongXuEntity.getSupplyDemand().equals("0")){
                    Intent intent = new Intent(GongXuDetailActivity.this, OutputGongyActivity.class);
                    intent.putExtra("datas", (Serializable) mGongXuEntity);
                    intent.putExtra("type", "my");
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent = new Intent(GongXuDetailActivity.this, OutputXuQiuActivity.class);
                    intent.putExtra("datas", (Serializable) mGongXuEntity);
                    intent.putExtra("type", "my");
                    startActivity(intent);
                    finish();
                }

            }


            @JavascriptInterface
            public void lookShoppingCar() {
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//            window.yxbl_app.lookShoppingCar()  //js调用android源码方法,查看购物车
                startActivity(new Intent(GongXuDetailActivity.this, ShoppingCartActivity.class));
            }

            @JavascriptInterface
            public void addGoodToCar(String commodityId, String commodityPrice, String commodityNumber, String commodityColor, String commoditySp, String userId, int shopDataFrom) {
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//            window.yxbl_app.addGoodToCar()  //js调用android源码方法,加入购物车
                CommdoityDetail commdoityDetail = new CommdoityDetail();
                commdoityDetail.setCommodityId(Integer.valueOf(commodityId));
                commdoityDetail.setCommodityPrice(Double.valueOf(commodityPrice));
                commdoityDetail.setCommodityQuantity(Integer.valueOf(commodityNumber));
                commdoityDetail.setCommodityColor(commodityColor);
                commdoityDetail.setCommoditySp(commoditySp);
                commdoityDetail.setUserId(userId);
                addGoodsToCar(commdoityDetail, shopDataFrom);
            }

            @JavascriptInterface
            public void toBuy(String commodityId, String commodityNm, String commodityImage1, String commodityPrice, String commodityNumber, String commodityColor, String commoditySp, String userId, int shopDataFrom) {
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//            window.yxbl_app.toBuy()  //js调用android源码方法,立即购买
                CommdoityDetail commdoityDetail = new CommdoityDetail();
                commdoityDetail.setCommodityId(Integer.valueOf(commodityId));
                commdoityDetail.setCommodityNm(commodityNm);
                commdoityDetail.setCommodityImage1(commodityImage1);
                commdoityDetail.setCommodityPrice(Double.valueOf(commodityPrice));
                commdoityDetail.setCommodityQuantity(Integer.valueOf(commodityNumber));
                commdoityDetail.setCommodityColor(commodityColor);
                commdoityDetail.setCommoditySp(commoditySp);
                commdoityDetail.setCreateId(Integer.valueOf(userId));
                goToBuy(commdoityDetail, shopDataFrom);
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

    @OnClick({R.id.rlLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                goBack();
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
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
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
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    //立即购买
    private void goToBuy(final CommdoityDetail mCommdoityDetail, int shopDataFrom) {
        if (mCommdoityDetail == null) {
            return;
        }
        String url = Url.postOrderForm;
        RequestParams params = new RequestParams();
        params.put("businessId", String.valueOf(mCommdoityDetail.getCreateId()));//商家Id
        params.put("orderPrice", mCommdoityDetail.getCommodityPrice());//价格
        params.put("expressCost", 0);//运费
        params.put("userId", UserNative.getId());//用户Id
        params.put("userNm", UserNative.getName());//商品价钱
        params.put("commodityId", mCommdoityDetail.getCommodityId());//商品id
        params.put("commodityColor", mCommdoityDetail.getCommodityColor());//颜色
        params.put("commoditySp", mCommdoityDetail.getCommoditySp());
        params.put("commodityNumber", mCommdoityDetail.getCommodityQuantity());
        params.put("commodityPrice", mCommdoityDetail.getCommodityPrice());
        params.put("delFlag", 1);
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
//                        startActivity(new Intent(ExternalWebActivity.this, ShoppingCartActivity.class));
                        //把数据发送到订单页面
                        //把当前的商品数据打包处理
                        List<OrderCommodity> temp = new ArrayList<>();
                        OrderCommodity orderCommdoity = new OrderCommodity();
                        orderCommdoity.setCommodityId(String.valueOf(mCommdoityDetail.getCommodityId()));
                        //设置选择的颜色
                        orderCommdoity.setCommodityColor(mCommdoityDetail.getCommodityColor());
                        //设置选择的规格
                        orderCommdoity.setCommoditySp(mCommdoityDetail.getCommoditySp());
                        orderCommdoity.setCommodityNumber(mCommdoityDetail.getCommodityQuantity());
                        orderCommdoity.setCommodityNm(mCommdoityDetail.getCommodityNm());
                        orderCommdoity.setCommodityImage1Full(mCommdoityDetail.getCommodityImage1());
                        orderCommdoity.setCommodityPrice(mCommdoityDetail.getCommodityPrice());
                        orderCommdoity.setExpressCost(0);//默认0元运费
                        orderCommdoity.setBusinessId(String.valueOf(mCommdoityDetail.getCreateId()));
                        temp.add(orderCommdoity);

                        Intent intent = new Intent(GongXuDetailActivity.this, BuyOrderActivityWeb.class);
                        intent.putExtra("orderUuid", object.getString("data"));
                        intent.putExtra("datas", (Serializable) temp);
                        intent.putExtra("type", BuyOrderActivityWeb.SINGLE);
                        intent.putExtra("servicetype", "no");
                        startActivity(intent);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }
}

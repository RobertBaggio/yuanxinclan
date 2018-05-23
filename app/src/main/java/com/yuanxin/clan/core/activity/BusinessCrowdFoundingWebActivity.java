package com.yuanxin.clan.core.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.market.bean.CommdoityDetail;
import com.yuanxin.clan.core.market.bean.OrderCommodity;
import com.yuanxin.clan.core.market.view.BuyOrderActivityWeb;
import com.yuanxin.clan.core.market.view.ShoppingCartActivity;
import com.yuanxin.clan.core.util.Logger;
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
 * Created by lenovo1 on 2017/3/27.
 * 园区成员
 */
public class BusinessCrowdFoundingWebActivity extends BaseActivity {

@BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tbsContent)
    X5WebView activityBaseWebAddWebView;
    @BindView(R.id.activity_yuan_xin_crowd_left_image)
    ImageView activityYuanXinCrowdLeftImage;
    @BindView(R.id.zixunshouchangimage)
    ImageView zixunshouchangimage;
    @BindView(R.id.activity_yuan_xin_crowd_close)
    LinearLayout activityYuanXinCrowdClose;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activityYuanXinCrowdLeftLayout;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activityYuanXinCrowdMiddleText;
//    @BindView(R.id.activity_t_yuan_xin_crowd_right_image) zixunshouchangimage
//    ImageView activityTYuanXinCrowdRightImage;
//    @BindView(R.id.activity_t_yuan_xin_crowd_right_image_layout)
//    LinearLayout activityTYuanXinCrowdRightImageLayout;
    @BindView(R.id.activity_news_chat_base_web_head_layout)
    RelativeLayout activityNewsChatBaseWebHeadLayout;
    private boolean flag=true;
    private int newsId, userId, epId;
    private String userNm, businessAreaId;
    private LocalBroadcastManager localBroadcastManager;
    private String epStyleType;
    private String url;
    private String groupid;
    private String name;
    private int refreshTime = 0;

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
//        activityYuanXinCrowdMiddleText.setText("众筹");
        String url = getIntent().getStringExtra("url");
        activityBaseWebAddWebView.loadUrl(url);
        initWebView();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (refreshTime != 0) {
                    activityBaseWebAddWebView.reload();
                }
                refreshTime++;
            }
        });
        refreshLayout.autoRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityBaseWebAddWebView = null;
    }

    @OnClick({R.id.activity_yuan_xin_crowd_left_layout, R.id.activity_yuan_xin_crowd_close,R.id.zixunshouchangimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_crowd_left_layout:
                onBackPressed();
                break;
            case R.id.activity_yuan_xin_crowd_close:
                finish();
                break;
            case R.id.zixunshouchangimage://收藏
//                if (TextUtil.isEmpty(groupid)){
//                    Toast.makeText(getApplicationContext(), "该群不存在", Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                gotoliaotian();

                if (flag) {
                    collecte();//收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.BUSINESS_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                } else {
                    deleteCollecte();//取消收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.BUSINESS_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                }
//                flag = !flag;
                break;
        }
    }

    private void deleteCollecte() {
        userId = UserNative.getId();
        userNm = UserNative.getName();
        String url = Url.DeleteCollecteone;
        RequestParams params = new RequestParams();
        params.put("keyId", businessAreaId);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("type", 2);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        params.put("del", true);
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        zixunshouchangimage.setImageResource(R.drawable.news_icon_collect);
                        flag=true;
                        ToastUtil.showInfo(getApplicationContext(), "取消收藏", Toast.LENGTH_SHORT);

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

    private void initWebView() {
        activityBaseWebAddWebView.setListener(new X5WebView.OnScrollListener() {
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
        activityBaseWebAddWebView.setWebViewClient(new WebClient());
        activityBaseWebAddWebView.setProgressbarDrawable(getResources().getDrawable(R.drawable.progressbar_business_area_red));
        activityBaseWebAddWebView.addJavascriptInterface(new WebViewJavaScriptFunction() {

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
//            window.yxbl_app.toChat()  //js调用android源码方法,发起环信聊天
                Log.e("lgq", toChatUsername);
                Intent intent = new Intent(BusinessCrowdFoundingWebActivity.this, PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, toChatUsername);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra("type", "与商家聊天中");
                startActivity(intent);
            }
            @JavascriptInterface
            public void toLogin() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            @JavascriptInterface
            public void lookShoppingCar() {
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//            window.yxbl_app.lookShoppingCar()  //js调用android源码方法,查看购物车
                startActivity(new Intent(BusinessCrowdFoundingWebActivity.this, ShoppingCartActivity.class));
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
            public void searchMember(String businessAreaId) {
                //搜索商圈成员
                Intent intent = new Intent(BusinessCrowdFoundingWebActivity.this, BusinessMemberSearchActivity.class);
                intent.putExtra("businessAreaId", Integer.parseInt(businessAreaId));
                startActivity(intent);
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
    @SuppressLint("SetJavaScriptEnabled")
    private class WebClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            view.getSettings().setJavaScriptEnabled(true);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            view.getSettings().setJavaScriptEnabled(true);
            super.onPageFinished(view, url);
            // 刷新
            refreshLayout.finishRefresh();

            // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
//            view.loadUrl(CommonString.imgClick);
        }
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
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
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

                        Intent intent = new Intent(BusinessCrowdFoundingWebActivity.this, BuyOrderActivityWeb.class);
                        intent.putExtra("orderUuid", object.getString("data"));
                        intent.putExtra("datas", (Serializable) temp);
                        intent.putExtra("type", BuyOrderActivityWeb.SINGLE);
                        intent.putExtra("intypeone", "no");
                        intent.putExtra("servicetype", "no");
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

    private void collecte() {
        userId = UserNative.getId();
        userNm = UserNative.getName();
        String url = Url.addCollecte;
        RequestParams params = new RequestParams();
        params.put("keyId", businessAreaId);//收藏项目ID newsId
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        params.put("del", false);
        params.put("type", 2);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        flag=false;
                        zixunshouchangimage.setImageResource(R.drawable.news_icon_collect_pre);
                        ToastUtil.showSuccess(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT);

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
            super.onBackPressed();
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

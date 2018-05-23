package com.yuanxin.clan.core.activity;

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
 * ProjectName: yuanxinclan_new
 * 圈子详情
 */

public class QuanziDetailwbActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.webview)
    X5WebView mWebview;
    @BindView(R.id.rlLeft)
    LinearLayout mRlLeft;
    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.zixunshouchangimage)
    ImageView zixunshouchangimage;


    private String shopListId ,commodityId,orderUuid,intype,url,title;
    private String businessAreaId;
    private boolean flag=true;

    private int refreshTime = 0;
    @Override
    public int getViewLayout() {
        return R.layout.quanzidetail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {


        String enshrine =intent.getStringExtra("enshrine");
        intype = getIntent().getStringExtra("intype");
        title = getIntent().getStringExtra("title");
        title_text.setText(title);

        if (intype.equals("fw")){
            orderUuid = getIntent().getStringExtra("orderUuid");
            shopListId = getIntent().getStringExtra("shopListId");
            commodityId = getIntent().getStringExtra("commodityId");
            url = Url.urlWeb+"/serviceOrder-detail&orderUuid="+orderUuid+"&shopListId="+shopListId+"&commodityId="+commodityId;
        }else if (intype.equals("qz")){
            businessAreaId = getIntent().getStringExtra("businessAreaId");
            url = Url.urlWeb+"/circle-style-one&businessAreaId="+businessAreaId+"&appFlg=1";
        }
        if (!TextUtil.isEmpty(enshrine)){
            flag=false;
            zixunshouchangimage.setImageResource(R.drawable.news_icon_collect_pre);
        }

        initWebView();
        try{
            String url2 = getIntent().getStringExtra("url");
            String title = getIntent().getStringExtra("title");
            Log.v("lgq","url=2222=="+url2);
            if (!TextUtil.isEmpty(url2)){
                zixunshouchangimage.setVisibility(View.GONE);
                mWebview.loadUrl(url2);
                if (!TextUtil.isEmpty(title)) {
                    title_text.setText(title);
                } else {
                    title_text.setText("商圈");
                }
            }else {
                mWebview.loadUrl(url);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        mWebview.loadUrl(url);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                if (refreshTime != 0) {
                    mWebview.reload();
//                }
//                refreshTime++;
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
                //                www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/business-style-one&param=424&appFlg=1
                Log.v("lgq","url000000-----"+url);
                if (url.startsWith(Url.urlHost + "/weixin/getJsp?url=wechatweb/circle-vip")) {
                    startActivity(new Intent(QuanziDetailwbActivity.this, QuanziMemberActivity.class).putExtra("url", url));
//                    rlRighttext.setVisibility(View.VISIBLE);
                }else if (url.startsWith(Url.urlHost + "/weixin/getJsp?url=wechatweb/crowdfund-info")) {
                    startActivity(new Intent(QuanziDetailwbActivity.this, BusinessCrowdFoundingWebActivity.class).putExtra("url", url));
//                    rlRighttext.setVisibility(View.VISIBLE);
                } else if(url.startsWith("http://images.yxtribe.com/upload/images/business/")) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(webIntent);
                } else {
//                    rlRighttext.setVisibility(View.GONE);
                    url += "&appFlg=1";
                    Log.v("lgq","........... "+url);
                    view.loadUrl(url);
                }
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
            public void toChat(String toChatUsername) {
//            window.yxbl_app.toChat()  //js调用android源码方法,发起环信聊天
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Log.e("lgq", toChatUsername);
                Intent intent = new Intent(QuanziDetailwbActivity.this, PersionChatActivity.class);
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
            public void shareToApp(String title, String content, String imgUrl, String url) {
            }

            @JavascriptInterface
            public void gotoPaiZhao(String id) {
                Intent intent = new Intent();
                // web页面跳转投资列表
                intent.setClass(QuanziDetailwbActivity.this, PaiZhaolistActivity.class);
                startActivity(intent.putExtra("id", id));
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

            /*
            * 抓取商品详情描述
            * */
            @JavascriptInterface
            public void gotoInvestment(String id) {
                startActivity(new Intent(QuanziDetailwbActivity.this, XmtouzActivity.class).putExtra("businessAreaId",id));
            }

            @JavascriptInterface
            public void BusinessVisit(String aid,String aname,String ac,String ap) {
                // 测试商圈成员搜索，发布版本放开
                Intent intent = new Intent(QuanziDetailwbActivity.this, AppointmentOneActivity.class);
                intent.putExtra("id", aid);
                intent.putExtra("name", aname);
                intent.putExtra("ac", ac);
                intent.putExtra("ph", ap);
                startActivity(intent);
            }
        }, CommonString.js2Android);
    }


    @OnClick({R.id.rlLeft, R.id.zixunfenxiangimage, R.id.zixunshouchangimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                onBackPressed();
                break;
            case R.id.zixunshouchangimage:
                MyShareUtil.sharedPint("sc",1);

                if (flag) {
                    collecte();//收藏

                } else {
                    deleteCollecte();//取消收藏

                }
                break;

        }
    }

    private void collecte() {
        String url = Url.addCollecte;
        RequestParams params = new RequestParams();
        params.put("keyId", businessAreaId);//收藏项目ID newsId
        params.put("userId", UserNative.getId());//省userId
        params.put("userNm", UserNative.getName());//用户名
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

    private void deleteCollecte() {
        String url = Url.DeleteCollecteone;
        RequestParams params = new RequestParams();
        params.put("keyId", businessAreaId);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("type", 2);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("userId", UserNative.getId());//省userId
        params.put("userNm", UserNative.getName());//用户名
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


    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()) {
            mWebview.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebview.canGoBack()) {
            mWebview.goBack();
//            activityBaseWebAddWebView.reload();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

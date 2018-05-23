package com.yuanxin.clan.core.news.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.utils.ShareTypes;
import com.loopj.android.http.RequestParams;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.tencent.sonic.sdk.SonicCacheInterceptor;
import com.tencent.sonic.sdk.SonicConstants;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;
import com.tencent.sonic.sdk.SonicSessionConnection;
import com.tencent.sonic.sdk.SonicSessionConnectionInterceptor;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.LoginActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.bean.NewEntity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.mvp.share.ShareDialog;
import com.yuanxin.clan.mvp.share.ShareInfoVo;
import com.yuanxin.clan.mvp.utils.CommonString;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.X5WebView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan
 * Describe: 资讯详情网页页面
 * Date: 2017/6/13 0013 11:29
 */
public class NewsDetailWebActivity extends BaseActivity {

    @BindView(R.id.activity_yuan_xin_crowd_left_image)
    ImageView activityYuanXinCrowdLeftImage;
    @BindView(R.id.zixunshouchangimage)
    ImageView zixunshouchangimage;
    @BindView(R.id.zixunfenxiangimage)
    ImageView zixunfenxiangimage;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activityYuanXinCrowdLeftLayout;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activityYuanXinCrowdMiddleText;
    @BindView(R.id.activity_t_yuan_xin_crowd_right_image_layout)
    LinearLayout activityTYuanXinCrowdRightImageLayout;
    @BindView(R.id.activity_news_chat_base_web_head_layout)
    RelativeLayout activityNewsChatBaseWebHeadLayout;
    @BindView(R.id.activity_news_chat_base_web_line_one)
    TextView activityNewsChatBaseWebLineOne;
    @BindView(R.id.activity_chat_baseweb_webview)
    X5WebView activityChatBasewebWebview;
    @BindView(R.id.activity_news_chat_base_web_line_two)
    TextView activityNewsChatBaseWebLineTwo;
    @BindView(R.id.activity_news_chat_base_web_collect_image)
    ImageView activityNewsChatBaseWebCollectImage;
    @BindView(R.id.activity_news_chat_base_web_collect_layout)
    LinearLayout activityNewsChatBaseWebCollectLayout;
    @BindView(R.id.activity_news_chat_base_web_publish_layout)
    ImageView activityNewsChatBaseWebPublishLayout;
    @BindView(R.id.activity_news_chat_base_web_buttom_layout)
    RelativeLayout activityNewsChatBaseWebButtomLayout;
    @BindView(R.id.activity_news_chat_base_web_buttom_input_layout)
    RelativeLayout activityNewsChatBaseWebButtomInputLayout;
    @BindView(R.id.activity_news_chat_base_web_edit)
    EditText activityNewsChatBaseWebEdit;
    @BindView(R.id.activity_news_chat_base_web_text)
    TextView activityNewsChatBaseWebText;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.activity_news_chat_base_web_comment_layout)
    LinearLayout activityNewsChatBaseWebTalk;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private int collect = 0;//没有收藏
    private LocalBroadcastManager localBroadcastManager;
    private boolean flag=true;
    private int userId;
    private String userNm;
    private NewEntity news;
    private String url;
    private int newsid;
    private int newsBasicTypeId;
    private My_LoadingDialog mMy_loadingDialog;
    private int refreshTime = 0;

    private SonicSession sonicSession;
    private int one ;

    @Override
    public int getViewLayout() {
        return R.layout.activity_news_chat_base_web;
    }//隐藏评论

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        one = getIntent().getIntExtra("one",-1);
//        Toast.makeText(this, "create sonic session fail!=="+one, Toast.LENGTH_LONG).show();
        userId = UserNative.getId();
        userNm = UserNative.getName();
        //使用弱引用，这样避免内存泄露
        WeakReference<Context> contextWeakReference = new WeakReference<Context>(this);
        mMy_loadingDialog = My_LoadingDialog.getInstance(getApplicationContext());
        setWebView();
        initModule();
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
//                if (refreshTime != 0) {
//                    mWebview.reload();
//                }
//                refreshTime++;
                activityChatBasewebWebview.reload();
            }
        });
        refreshLayout.autoRefresh();

    }

    @SuppressLint({"JavascriptInterface", "SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void setWebView() {
        if (one == 2) {


        // if it's sonic mode , startup sonic session at first time

        SonicSessionConfig.Builder sessionConfigBuilder = new SonicSessionConfig.Builder();
        sessionConfigBuilder.setSupportLocalServer(true);

        // if it's offline pkg mode, we need to intercept the session connection

        sessionConfigBuilder.setCacheInterceptor(new SonicCacheInterceptor(null) {
            @Override
            public String getCacheData(SonicSession session) {
                return null; // offline pkg does not need cache
            }
        });

        sessionConfigBuilder.setConnectionInterceptor(new SonicSessionConnectionInterceptor() {
            @Override
            public SonicSessionConnection getConnection(SonicSession session, Intent intent) {
                return new OfflinePkgSessionConnection(NewsDetailWebActivity.this, session, intent);
            }
        });

    }

        WebSettings webSetting = activityChatBasewebWebview.getSettings();
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

        activityChatBasewebWebview.setSaveEnabled(true);

            activityChatBasewebWebview.addJavascriptInterface(new JavascriptInterface(this), CommonString.js2Android);

//        activityChatBasewebWebview.addJavascriptInterface(new JavascriptInterface(this), CommonString.js2Android);

        activityChatBasewebWebview.setWebViewClient(new WebClient());
//        activityChatBasewebWebview.setWebChromeClient(new WebChrome());
        activityChatBasewebWebview.setVerticalScrollBarEnabled(false);
        activityChatBasewebWebview.setVerticalScrollbarOverlay(false);
        activityChatBasewebWebview.setHorizontalScrollBarEnabled(false);
        activityChatBasewebWebview.setHorizontalScrollbarOverlay(false);
        activityChatBasewebWebview.setProgressbarDrawable(getResources().getDrawable(R.drawable.progressbar_news_grey));
        activityChatBasewebWebview.clearCache(true);
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
            intent.setClass(NewsDetailWebActivity.this, PhotoBrowserActivity.class);
            startActivity(intent);
        }

        @android.webkit.JavascriptInterface
        public void toLogin() {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }
        @android.webkit.JavascriptInterface
        public void toCompanyDetail(int epId, String accessPath) {
//            window.yxbl_app.toCompanyDetail()  //js调用android源码方法
            Intent intent = new Intent(NewsDetailWebActivity.this, CompanyDetailWebActivity.class);
            intent.putExtra("epId", epId);
            intent.putExtra("accessPath", accessPath);
            startActivity(intent);
        }
        private  String toJsString(String value) {
            if (value == null) {
                return "null";
            }
            StringBuilder out = new StringBuilder(1024);
            for (int i = 0, length = value.length(); i < length; i++) {
                char c = value.charAt(i);


                switch (c) {
                    case '"':
                    case '\\':
                    case '/':
                        out.append('\\').append(c);
                        break;

                    case '\t':
                        out.append("\\t");
                        break;

                    case '\b':
                        out.append("\\b");
                        break;

                    case '\n':
                        out.append("\\n");
                        break;

                    case '\r':
                        out.append("\\r");
                        break;

                    case '\f':
                        out.append("\\f");
                        break;

                    default:
                        if (c <= 0x1F) {
                            out.append(String.format("\\u%04x", (int) c));
                        } else {
                            out.append(c);
                        }
                        break;
                }

            }
            return out.toString();
        }

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
            refreshLayout.finishRefresh();
            if (one == 2) {
            if (sonicSession != null) {
                sonicSession.getSessionClient().pageFinish(url);
            }
        }
            // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
//            view.loadUrl(CommonString.imgClick);
            view.loadUrl(CommonString.imgClickWithShowClass);
        }
    }

    public void initModule() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        news = (NewEntity) bundle.getSerializable("news");
        newsid = intent.getIntExtra("newsid",-1);
        int mycollect = intent.getIntExtra("mc",0);
        try{
            int id = news.getNewsBasicTypeId();
            newsBasicTypeId = (id == 0)? 1: id;//1.资讯  2.企业动态  3.商圈动态
            boolean ifcollect = news.getCollect();
            Log.v("lgq",".....11........shouc==="+news.getNewsId()+"....."+ifcollect+".....myc=="+mycollect);
            if (ifcollect){
                flag=false;
                zixunshouchangimage.setImageResource(R.drawable.news_icon_collec_pre);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
//        type 1资讯  2企业动态  3商圈动态
        if (newsBasicTypeId==0){
            newsBasicTypeId=1;
        }
        if (newsid>=0){
            url = Url.urlWebNews + newsid + "&userId=" + userId + "&appFlg=1&type=" + newsBasicTypeId;
        }else {
            url = Url.urlWebNews + news.getNewsId() + "&userId=" + userId + "&appFlg=1&type=" + newsBasicTypeId;
        }
        if (mycollect==1){
            flag=false;
            zixunshouchangimage.setImageResource(R.drawable.news_icon_collec_pre);
        }
        Log.v("lgq",".....22..shouc==="+newsid+"...url==="+url+"..."+newsBasicTypeId);
        activityChatBasewebWebview.loadUrl(url);
//        http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/news-detail&param=2919&userId=1130&appFlg=1&type=0
//        http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/news-detail&param=8652&userId=1130&appFlg=1&type=1
//        http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/news-detail&param=12209&userId=1368&appFlg=1&type=1
//        http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/news-detail&param=12209&userId=1368&appFlg=1&type=0
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityChatBasewebWebview = null;
    }

    private void showShare() {
        ShareInfoVo shareInfoVo = new ShareInfoVo();
        shareInfoVo.setTitle(news.getTitle());
        shareInfoVo.setUrl(url + "&forwardUserId="+userId+"&links=share");
        shareInfoVo.setContent(news.getEpNm());//隐藏内容
        shareInfoVo.setImgUrl(news.getImg());
        //"新闻分享"
        shareInfoVo.setShareQiliaoType(ShareTypes.INFORMATION_INFO);
        ShareDialog.showShareDialog(getSupportFragmentManager(), shareInfoVo);
    }

    @OnClick({R.id.tv_send, R.id.activity_news_chat_base_web_text,R.id.zixunfenxiangimage,R.id.zixunshouchangimage, R.id.activity_news_chat_base_web_comment_layout, R.id.activity_yuan_xin_crowd_left_layout, R.id.activity_t_yuan_xin_crowd_right_image_layout, R.id.activity_news_chat_base_web_collect_layout, R.id.activity_news_chat_base_web_publish_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_news_chat_base_web_text://评论
            case R.id.activity_news_chat_base_web_comment_layout://评论
                if (activityNewsChatBaseWebButtomInputLayout.getVisibility() == View.GONE) {
                    activityNewsChatBaseWebButtomInputLayout.setVisibility(View.VISIBLE);
                    activityNewsChatBaseWebButtomLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_send:
                String talk = activityNewsChatBaseWebEdit.getText().toString();
                addNewsTalk(talk);
                activityNewsChatBaseWebButtomLayout.setVisibility(View.VISIBLE);
                activityNewsChatBaseWebButtomInputLayout.setVisibility(View.GONE);
                break;
            case R.id.activity_yuan_xin_crowd_left_layout:
                onBackPressed();
                break;
            case R.id.zixunfenxiangimage://分享
                showShare();
                break;
            case R.id.zixunshouchangimage://收藏
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                if (flag) {
                    collecte();//收藏
                    Log.v("lgq","...true");
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.NEWS_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                } else{

                    Log.v("lgq","...false");
                    deleteCollecte();//取消收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.NEWS_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                }
                break;
            case R.id.activity_news_chat_base_web_publish_layout://发布 走Web端
                startActivity(new Intent(NewsDetailWebActivity.this, PublishNewsChatBaseWebActivity.class));
                break;
        }
    }

    private void addNewsTalk(String talk) {
        String url = Url.addNewsTalk;
        RequestParams params = new RequestParams();
        params.put("createId", userId);//收藏项目ID newsId
        params.put("createNm", userNm);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("newsCommentContent", talk);//省userId
        params.put("newsId", news.getNewsId());//用户名
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.v("Lgq","detail===="+s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
//                        initModule();//评论后
                        activityNewsChatBaseWebEdit.setText("");
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });


    }

    private void deleteCollecte() {
        String url = Url.DeleteCollecteone;
        mMy_loadingDialog.show();
        RequestParams params = new RequestParams();
        if (newsid>0){
            params.put("keyId", newsid);//收藏项目ID newsId
        }else
            params.put("keyId", news.getNewsId());//收藏项目ID newsId

        params.put("type", 1);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        params.put("del", true);
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), "取消收藏成功", Toast.LENGTH_SHORT);
                        zixunshouchangimage.setImageResource(R.drawable.news_icon_collect_b);
                        flag=true;
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    private void collecte() {
        String url = Url.addCollecte;
        RequestParams params = new RequestParams();
        mMy_loadingDialog.show();
        if (newsid>0){
            params.put("keyId", newsid);//收藏项目ID newsId
        }else
            params.put("keyId", news.getNewsId());//收藏项目ID newsId
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        params.put("type", 1);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
        params.put("del", false);
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT);
                        zixunshouchangimage.setImageResource(R.drawable.news_icon_collec_pre);
                        flag =false;
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if (activityChatBasewebWebview.canGoBack()) {
            activityChatBasewebWebview.goBack();
        } else {
//            super.onBackPressed();
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private static class OfflinePkgSessionConnection extends SonicSessionConnection {

        private final WeakReference<Context> context;

        public OfflinePkgSessionConnection(Context context, SonicSession session, Intent intent) {
            super(session, intent);
            this.context = new WeakReference<Context>(context);
        }

        @Override
        protected int internalConnect() {
            Context ctx = context.get();
            if (null != ctx) {
                try {
                    InputStream offlineHtmlInputStream = ctx.getAssets().open("sonic-demo-index.html");
                    responseStream = new BufferedInputStream(offlineHtmlInputStream);
                    return SonicConstants.ERROR_CODE_SUCCESS;
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
            return SonicConstants.ERROR_CODE_UNKNOWN;
        }

        @Override
        protected BufferedInputStream internalGetResponseStream() {
            return responseStream;
        }

        @Override
        public void disconnect() {
            if (null != responseStream) {
                try {
                    responseStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getResponseCode() {
            return 200;
        }

        @Override
        public Map<String, List<String>> getResponseHeaderFields() {
            return new HashMap<>(0);
        }

        @Override
        public String getResponseHeaderField(String key) {
            return "";
        }
    }

}

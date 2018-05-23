package com.yuanxin.clan.core.company.view;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.utils.ShareTypes;
import com.loopj.android.http.RequestParams;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.ExternalWebActivity;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.activity.LoginActivity;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.news.view.PhotoBrowserActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
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

import static com.yuanxin.clan.core.http.Url.getEpInfo;

/**
 * Created by lenovo1 on 2017/3/15.
 * 企业库详情
 */
//public class CompanyDetailWebActivity extends BaseActivity implements AppBarLayout.OnOffsetChangedListener {
public class CompanyDetailWebActivity extends BaseActivity {

//    @BindView(R.id.refreshLayout)
//    SmartRefreshLayout refreshLayout;

    @BindView(R.id.activity_yuan_xin_crowd_left_image)
    ImageView activityYuanXinCrowdLeftImage;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activityYuanXinCrowdLeftLayout;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activityYuanXinCrowdMiddleText;
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
    private int userId, epId;
    private String userNm, url;
    private LocalBroadcastManager localBroadcastManager;
    private String accessPath;
    private CompanyDetail companys;
    private String logo, title, content, collect, mallNm;
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    private int uid;
    private String shareurl;
    private int refreshTime = 0;

    @Override
    public int getViewLayout() {
        return R.layout.activity_chat_base_web;
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
        initModule();
        uid = getIntent().getIntExtra("uid",0);
//        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
//            @Override
//            public void onRefresh(RefreshLayout refreshlayout) {
////                if (refreshTime != 0) {
////                    mWebview.reload();
////                }
////                refreshTime++;
//                activityChatBasewebWebview.reload();
//            }
//        });
//        refreshLayout.autoRefresh();
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
    public void initModule() {
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
//                if (h == 0) {
//                    refreshLayout.setEnableRefresh(true);
//                    refreshLayout.setEnableOverScrollDrag(true);
//                } else {
//                    refreshLayout.setEnableRefresh(false);
//                    refreshLayout.setEnableOverScrollDrag(false);
//                }
            }
        });
        //全屏播放视频
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        activityChatBasewebWebview.getView().setOverScrollMode(View.OVER_SCROLL_ALWAYS);
        activityChatBasewebWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                shareurl=url;
//                Log.v("lgq","urol..====="+shareurl);
                if (url.startsWith(Url.urlHost + "/weixin/getJsp?url=wechatweb/enterpriseMall")) {
//                    http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/serviceMall-info&shopId=1783&appFlg=0
                    //是企业商城就在当前的webview中跳转到新的url
//                    startActivity(new Intent(CompanyDetailWebActivity.this, ExternalWebActivity.class).putExtra("url", url));
                    Intent intent = new Intent(CompanyDetailWebActivity.this, ExternalWebActivity.class);
                    intent.putExtra("logo", Url.img_domain+"/"+logo+Url.imageStyle640x640);
                    intent.putExtra("title", TextUtil.isEmpty(mallNm) ? title : mallNm);
                    intent.putExtra("content",TextUtil.isEmpty(content)?"企业商城": content);
                    intent.putExtra("url",url);
                    intent.putExtra("epId",epId);
                    startActivity(intent);
                }else if (url.startsWith(Url.urlHost + "/weixin/getJsp?url=wechatweb/serviceMall-info")&&uid ==UserNative.getId()){
                    startActivity(new Intent(CompanyDetailWebActivity.this, HomeADactivity.class).putExtra("url", url));
                } else if (url.contains("taobao.com") || url.contains("jd.com") || url.contains("1688.com")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } else {
                    if (!url.contains("tel:")) {
                        view.loadUrl(url);
                    }
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
//                activityChatBasewebWebview.loadUrl("javascript:window.yxbl_app.getBodyHeight($(document.body).height())");
                super.onPageFinished(view, url);
//                refreshLayout.finishRefresh();
                // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
                view.loadUrl(CommonString.imgClickWithShowClass);
            }
        });
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        logo = intent.getStringExtra("logo");
        Log.v("lgq","lolg==111=="+logo);
        title =intent.getStringExtra("title");
        collect = intent.getStringExtra("collect");
        content = intent.getStringExtra("content");
        companys = (CompanyDetail) bundle.getSerializable("companys");
        epId = bundle.getInt("epId");
//        epLinktel = bundle.getString("epLinktel");
        accessPath = bundle.getString("accessPath");
        getEpInfo();

        if (TextUtil.isEmpty(accessPath)) {
            //没有就用默认的
            accessPath = "epdetail";
//            accessPath = "ep-style-2";
        }
        userId = UserNative.getId();
        url = Url.urlWeb + "/" + accessPath + "&param=" + epId + "&appFlg=1" + "&userId=" + userId;
        // 从首页为你推荐跳过来
        String loadUrl = getIntent().getStringExtra("url");
        if(!TextUtil.isEmpty(loadUrl)) {
            url = loadUrl;
        }
        activityChatBasewebWebview.loadUrl(url);


        activityChatBasewebWebview.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }
            @JavascriptInterface
            public void toChat(String toChatUsername) {
                    Intent intent = new Intent(CompanyDetailWebActivity.this, PersionChatActivity.class);
                    intent.putExtra(Constant.EXTRA_USER_ID, toChatUsername);
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                    intent.putExtra(Constant.USER_NAME, UserNative.getName());
                    intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                    String add = UserNative.getCity()+UserNative.getArea()+UserNative.getDetail();
                    intent.putExtra(Constant.ADDRESS,add );
                    intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                    intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                    intent.putExtra("type", "与商家聊天中");
                    startActivity(intent);
//                dad(toChatUsername, type);
            }
            @JavascriptInterface
            public void onPhone(String toChatUsername) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+toChatUsername));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            /**
             * 在线沟通
             *
             */
            @JavascriptInterface
            public void chatCallOrEpChat(String number, String toChatUsername) {

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

            @JavascriptInterface
            public void openImage(String imageUrls, String img) {
                Intent intent = new Intent();
                intent.putExtra("imageUrls", imageUrls);
                intent.putExtra("curImageUrl", img);
                intent.setClass(CompanyDetailWebActivity.this, PhotoBrowserActivity.class);
                startActivity(intent);
            }

            @JavascriptInterface
            public void toLogin() {
                startActivity(new Intent(CompanyDetailWebActivity.this, LoginActivity.class));
            }

            /**
             * 高度
             */
            @JavascriptInterface
            public void getBodyHeight(String number) {
                int webViewHeight =  Integer.parseInt(number.split("[.]")[0]);
                Log.i("lgqqqqq======  ", "webViewHeight" + webViewHeight);
//                try {
//                    ViewGroup.LayoutParams linearParams2 =(ViewGroup.LayoutParams) activityChatBasewebWebview.getLayoutParams(); //取控件textView当前的布局参数
//                    linearParams2.height = (int)(webViewHeight*getResources().getDisplayMetrics().density)/2;// 控件的高强制设成20
//                    activityChatBasewebWebview.setLayoutParams(linearParams2);
//                } catch (Exception e) {
//                    Logger.e(e.toString());
//                }
            }
        }, CommonString.js2Android);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityChatBasewebWebview = null;
    }

    private void getEpInfo() {
        userId = UserNative.getId();
        String url = getEpInfo;
        RequestParams params = new RequestParams();
        params.put("epId", epId);//收藏项目ID newsId
        params.put("userId", userId);//省userId
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
                            if (TextUtil.isEmpty(title)){
                                String shortNm = ep.getString("epShortNm");
                                if (TextUtil.isEmpty(shortNm)){
                                    title = ep.getString("epNm");
                                }else {
                                    title = shortNm;
                                }
                            }
                            mallNm = ep.getString("mallNm");
                            activityYuanXinCrowdMiddleText.setText(title);
                            logo = ep.getString("epImage1");
                            collect = ep.getString("enshrine");
                            Log.v("lgq","ssscc==="+collect);
                            content = ep.getString("epDetail");
                            if (!TextUtil.isEmpty(collect) && !collect.equals("null") && collect.length()>0){
                                zixunshouchangimage.setImageResource(R.drawable.news_icon_collect_pre);
                                flag=false;
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        zixunfenxiangimage.setVisibility(View.GONE);
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @OnClick({R.id.activity_yuan_xin_crowd_left_layout,R.id.zixunfenxiangimage, R.id.zixunshouchangimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_crowd_left_layout:
                goBack();
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
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.COMPANY_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                } else {
                    deleteCollecte();//取消收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.COMPANY_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                }
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
            shareInfoVo.setUrl(url+"&links=share" );
        }else if(shareurl.contains("business-card")){
            shareInfoVo.setTitle(title+"-名片");
            shareInfoVo.setUrl(shareurl +"&links=share");
        }else if(shareurl.contains("epnewsdetail")){
            shareInfoVo.setTitle(title+"-动态");
            shareInfoVo.setUrl(shareurl +"&links=share");
        } else {
            shareInfoVo.setTitle(title);
            shareInfoVo.setUrl(url+"&links=share" );
        }
        shareInfoVo.setContent(content);//隐藏内容
//       logo="http://images.yxtribe.com//upload/images/ep/916/20170802113827617.PNG-style_webp_640x640";
//            logo="http://www.yxtribe.com/yuanxinbuluo/upload/images/ep/287/20170509154527190.jpg";
            shareInfoVo.setImgUrl(Url.img_domain+logo+Url.imageStyle640x640);
            Log.v("lgq","lolg===="+Url.img_domain+logo+Url.imageStyle640x640);
            //"企业分享"
            shareInfoVo.setShareQiliaoType(ShareTypes.COMPANY_MAIN_PAGE);
        ShareDialog.showShareDialog(getSupportFragmentManager(), shareInfoVo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void deleteCollecte() {
        userId = UserNative.getId();
        userNm = UserNative.getName();
        String url = Url.addCollecte;
        RequestParams params = new RequestParams();
        params.put("keyId", epId);//收藏项目ID newsId
        params.put("type", 3);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        params.put("del", true);
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
                        flag =true;
                        zixunshouchangimage.setImageResource(R.drawable.news_icon_collect);
                        ToastUtil.showSuccess(getApplicationContext(), "取消收藏成功", Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
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
        params.put("keyId", epId);//收藏项目ID newsId
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        params.put("del", false);
        params.put("type", 3);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
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
                        flag=false;
                        zixunshouchangimage.setImageResource(R.drawable.news_icon_collect_pre);
                        ToastUtil.showSuccess(getApplicationContext(), "收藏成功", Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    public boolean onTouchEvent(MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            Log.v("lgq","...x2="+x2);
            if(y1 - y2 > 20) {
                activityNewsChatBaseWebHeadLayout.setBackgroundColor(Color.parseColor("#43adff"));

//                CompanyDetailWebActivity.this.getWindow().setStatusBarColor(Color.parseColor("#212121"));
//                Toast.makeText(CompanyDetailWebActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 > 20) {
//                Toast.makeText(CompanyDetailWebActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
//                activityNewsChatBaseWebHeadLayout.setBackgroundColor(Color.parseColor("#43adff"));
                activityNewsChatBaseWebHeadLayout.setBackgroundColor(Color.TRANSPARENT);

            } else if(x1 - x2 > 50) {
//                Toast.makeText(CompanyDetailWebActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
//                Toast.makeText(CompanyDetailWebActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }

    //更新dialog
    public void dad(final String phone) {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(CompanyDetailWebActivity.this);
        View view = inflater.inflate(R.layout.phone_e_dialog, null);
        dialog = new Dialog(CompanyDetailWebActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.AnimViewshow);

//        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        dialogWindow.setGravity(Gravity.CENTER);

//        lp.width = 900; // 宽度
//        lp.height = 650; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        LinearLayout ph = (LinearLayout) view.findViewById(R.id.callphoneli);
        LinearLayout ea = (LinearLayout) view.findViewById(R.id.easelikeli);
        TextView dismiss = (TextView)view.findViewById(R.id.dissmisste);
        StringBuilder sb = new StringBuilder();

        ea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Intent intent = new Intent(CompanyDetailWebActivity.this, PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, phone);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra(Constant.USER_NAME, UserNative.getName());
                intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                String add = UserNative.getCity()+UserNative.getArea()+UserNative.getDetail();
                intent.putExtra(Constant.ADDRESS,add );
                intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                intent.putExtra("type", "与商家聊天中");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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
    @Override
    protected void onResume() {
        super.onResume();
    }


}

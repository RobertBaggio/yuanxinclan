package com.yuanxin.clan.core.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.lypeer.fcpermission.FcPermissions;
import com.lypeer.fcpermission.impl.FcPermissionsCallbacks;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.market.view.GongXuActivity;
import com.yuanxin.clan.core.news.view.PhotoBrowserActivity;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
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

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/4/23 0023 10:41
 */

public class HBusinessDetailwebActivity extends BaseActivity implements FcPermissionsCallbacks {

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
    private static final int REQUEST_IMAGE3 = 5;
    private More_LoadDialog mMy_loadingDialog;
    private SubscriberOnNextListener fileUploadOnNextListener;

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
        initModule();
        initWebView();
        mMy_loadingDialog =new  More_LoadDialog(this);
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);
                    for (int b = 0; b < listString.size(); b++) {
                        String imageUrl = listString.get(b);
                        activityBaseWebAddWebView.loadUrl("javascript:getPicFromJava('" + imageUrl + "')");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                }
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
            }
        };

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (refreshTime != 0) {
                    activityBaseWebAddWebView.reload();
                }
                refreshTime ++;
            }
        });
        refreshLayout.autoRefresh();
    }


    public void initModule() {
        Intent intent = getIntent();
        int ifmy = intent.getIntExtra("myc",0);
        String enshrine =intent.getStringExtra("enshrine");

        Log.v("lgq","群聊id。。。。==="+groupid);
        if (TextUtil.isEmpty(epStyleType)) {
            //没有就用默认的
            epStyleType = "business";
        }
        if (ifmy>0){
//            zixunshouchangimage.setVisibility(View.GONE);
            enshrine="true";
        }
        if (!TextUtil.isEmpty(enshrine)){
            flag=false;
            zixunshouchangimage.setImageResource(R.drawable.news_icon_collect_pre);
        }
        userId = UserNative.getId();


//        url = "http://192.168.1.102/yuanxinbuluo/weixin/getJsp?url=wechatweb/association-style-one&param=534&appFlg=0";
//               http://192.168.1.102/yuanxinbuluo/weixin/getJsp?url=wechatweb/garden-style-one&param=697&appFlg=0
//        Log.v("lgq","url==="+url);
        try{
            String url2 = getIntent().getStringExtra("url");
            String title = getIntent().getStringExtra("title");
            Log.v("lgq","url=2222=="+url2);
            if (!TextUtil.isEmpty(url2)){
                zixunshouchangimage.setVisibility(View.GONE);
                activityBaseWebAddWebView.loadUrl(url2);
                if (!TextUtil.isEmpty(title)) {
                    activityYuanXinCrowdMiddleText.setText(title);
                } else {
                    activityYuanXinCrowdMiddleText.setText("商圈");
                }
            }else {
                activityBaseWebAddWebView.loadUrl(url);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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
                MyShareUtil.sharedPint("sc",1);

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
                        ToastUtil.showSuccess(getApplicationContext(), "取消收藏", Toast.LENGTH_SHORT);

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
        activityBaseWebAddWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(webIntent);
            }
        });
        activityBaseWebAddWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(Url.urlHost + "/weixin/getJsp?url=wechatweb/business-member")||url.startsWith(Url.urlHost+"/weixin/getJsp?url=wechatweb/association-members")) {
//                    http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/association-style-one&param=450&appFlg=1
                    http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/garden-members&param=697&appFlg=1&appFlg=1
                    startActivity(new Intent(HBusinessDetailwebActivity.this, My_BusMemberActivity.class).putExtra("url", url));
//                }else if (url.startsWith(Url.urlHost + "/weixin/getJsp?url=wechatweb/crowdfund-info")) {
                }else if (url.startsWith(Url.urlHost + "/weixin/getJsp?url=wechatweb/garden-members")) {
                    startActivity(new Intent(HBusinessDetailwebActivity.this, BusinessCrowdFoundingWebActivity.class).putExtra("url", url));
                } else if(url.startsWith("http://images.yxtribe.com/upload/images/business/")) {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(webIntent);
                } else {
                    url += "&appFlg=1";
                    view.loadUrl(url);
                    Log.v("lgq","ulrllll===   "+url);
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
                // 刷新
                refreshLayout.finishRefresh();
                // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
                view.loadUrl(CommonString.imgClickWithShowClass);
            }
        });
        activityBaseWebAddWebView.setProgressbarDrawable(getResources().getDrawable(R.drawable.progressbar_business_area_red));
        activityBaseWebAddWebView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }
            @JavascriptInterface
            public void goToBusinessChatRoom() {
//            window.yxbl_app.toChat()  //js调用android源码方法,发起环信聊天
                if (TextUtil.isEmpty(groupid)){
                    ToastUtil.showWarning(getApplicationContext(), "该群不存在", Toast.LENGTH_SHORT);
                    return;
                }
                gotoliaotian();
            }
            @JavascriptInterface
            public void toLogin() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            @JavascriptInterface
            public void toCompanyDetail(String epId, String accessPath, String a) {
//            window.yxbl_app.toCompanyDetail()  //js调用android源码方法
                Intent intent = new Intent(HBusinessDetailwebActivity.this, CompanyDetailWebActivity.class);
                intent.putExtra("epId", Integer.parseInt(epId));
                intent.putExtra("accessPath", accessPath);
                startActivity(intent);
            }
            @JavascriptInterface
            public void BusinessVisit(int aid,String aname,String ac,String ap) {
//                Log.v("Lgq","newo[==="+aname);
//            window.yxbl_app.toCompanyDetail()  //js调用android源码方法
//                Intent intent = new Intent(BusinessDetailWebActivity.this, CompanyDetailWebActivity.class);
//                intent.putExtra("epId", Integer.parseInt(epId));
//                intent.putExtra("accessPath", accessPath);
//                startActivity(intent);
            }
            @JavascriptInterface
            public void openImage(String imageUrls, String img) {
                Intent intent = new Intent();
                intent.putExtra("imageUrls", imageUrls);
                intent.putExtra("curImageUrl", img);
                Log.v("lgq","tophoto===== "+img+".....  "+ imageUrls);
                intent.setClass(HBusinessDetailwebActivity.this, PhotoBrowserActivity.class);
                startActivity(intent);
            }
            @JavascriptInterface
            public void gotoGongxu() {
                Intent intent = new Intent();
                intent.setClass(HBusinessDetailwebActivity.this, GongXuActivity.class);
                startActivity(intent);
            }
            @JavascriptInterface
            public void gotoInvestment(String id) {
                Intent intent = new Intent();
                // web页面跳转投资列表
                intent.setClass(HBusinessDetailwebActivity.this, GongXuActivity.class);
                startActivity(intent.putExtra("id", id));
            }
            @JavascriptInterface
            public void gotoPaiZhao(String id) {
                Intent intent = new Intent();
                // web页面跳转投资列表
                intent.setClass(HBusinessDetailwebActivity.this, PaiZhaolistActivity.class);
                startActivity(intent.putExtra("id", id));
            }
            @JavascriptInterface
            public void toChat(String toChatUsername) {
                dad(toChatUsername);
            }
            @JavascriptInterface
            public void getPic() {
                requestPermission();
                MultiImageSelector.create(HBusinessDetailwebActivity.this)
                        .showCamera(true) // 是否显示相机. 默认为显示
//                .count(int) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
//                    .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(HBusinessDetailwebActivity.this, REQUEST_IMAGE3);
            }
        }, CommonString.js2Android);
    }

    //    多图选择开始
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FcPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void requestPermission() {
        FcPermissions.requestPermissions(this, getString(R.string.prompt_request_storage), FcPermissions.REQ_PER_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionsGranted(int i, List<String> list) {

    }

    @Override
    public void onPermissionsDenied(int i, List<String> list) {
        FcPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.prompt_we_need),
                R.string.setting, R.string.cancel, null, list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE3) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int a = 0; a < pathImage.size(); a++) {
                    String path = pathImage.get(0);
                    // 回调Js 方法，传递获取的图片链接地址
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, this), "user", new File(path));
                }
            }
        }
    }


    public void dad(final String phone) {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(HBusinessDetailwebActivity.this);
        View view = inflater.inflate(R.layout.phone_e_dialog, null);
        dialog = new Dialog(HBusinessDetailwebActivity.this);
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
                Intent intent = new Intent(HBusinessDetailwebActivity.this, PersionChatActivity.class);
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

    private void gotoliaotian() {
        userId = UserNative.getId();
        userNm = UserNative.getName();
        String url = Url.gotoliaotian;
        RequestParams params = new RequestParams();
        params.put("userId", userId);//省userId
        params.put("businessAreaId", businessAreaId);//用户名
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq","商圈id返回。。。"+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        Intent intent = new Intent(HBusinessDetailwebActivity.this, PersionChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_ID, groupid);
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                        intent.putExtra("type", name);
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
//            activityBaseWebAddWebView.reload();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

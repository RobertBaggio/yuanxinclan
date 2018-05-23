package com.yuanxin.clan.core;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.melink.bqmmsdk.sdk.BQMM;
import com.umeng.analytics.MobclickAgent;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.BusinessDistrictLibraryActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.event.ChangeFragmentEvent;
import com.yuanxin.clan.core.event.MainEvent;
import com.yuanxin.clan.core.event.MessageChangeEvent;
import com.yuanxin.clan.core.event.OutLoginByJpushEvent;
import com.yuanxin.clan.core.event.OutLoginEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.BusinessMessageDao;
import com.yuanxin.clan.core.huanxin.BuyerMessageDao;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.huanxin.PermissionsManager;
import com.yuanxin.clan.core.huanxin.PermissionsResultAction;
import com.yuanxin.clan.core.huanxin.SellerMessageDao;
import com.yuanxin.clan.core.news.HomeGongxuFragment;
import com.yuanxin.clan.core.util.JPushUtil;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.core.weiboPopup.MoreWindow;
import com.yuanxin.clan.core.weiboPopup.PopupMenuUtil;
import com.yuanxin.clan.mvp.MainApplication;
import com.yuanxin.clan.mvp.service.UpdateAppService;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.MainBaseActivity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * ProjectName: yuanxinclan
 * Describe: 主页
 * Author: xjc
 * Date: 2017/6/19 0019 12:49
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class MainActivity extends MainBaseActivity {

    @BindView(R.id.imgMenuHome)
    ImageView mImgMenuHome;
    @BindView(R.id.lyMenuHome)
    LinearLayout mLyMenuHome;
    @BindView(R.id.imgMenuNews)
    ImageView mImgMenuNews;
    @BindView(R.id.lyMenuNews)
    LinearLayout mLyMenuNews;
    @BindView(R.id.businessTab)
    TextView mBusinessText;
    @BindView(R.id.remenuBusiness)
    RelativeLayout mLyMenuBusiness;
    @BindView(R.id.imgMenuChat)
    ImageView mImgMenuChat;
    @BindView(R.id.lyMenuChat)
    LinearLayout mLyMenuChat;
    @BindView(R.id.imgMenuCenter)
    ImageView mImgMenuCenter;
    @BindView(R.id.lyMenuCenter)
    LinearLayout mLyMenuCenter;
    @BindView(R.id.lyMeun)
    LinearLayout mLyMeun;
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.tvChatNum)
    TextView mTvChatNum;
    @BindView(R.id.shouyete)
    TextView shouyete;
    @BindView(R.id.zixunte)
    TextView zixunte;
    @BindView(R.id.qiliaote)
    TextView qiliaote;
    @BindView(R.id.wodete)
    TextView wodete;
    @BindView(R.id.remenuChat)
    RelativeLayout remenuChat;
    @BindView(R.id.imgMenuBusiness)
    ImageView publishBtn;

    private FragmentTransaction fragmentTransaction;
//    private Fragment homeFragment, newsFragment, epChatFragment, mineFragment;
    private Fragment homeFragment, gxFragment, epChatFragment, mineFragment;
    private String wbovs;
    private JSONArray updateContent;
    private final String mPageName = "MainPage";
    public static boolean isForeground = false;
    private ProgressDialog mloadpd;
    private boolean progressShow;
    private String up;
    private AsyncHttpClient client = new AsyncHttpClient();
    private MoreWindow mMoreWindow;

    @Override
    public int getViewLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        MainApplication.initScreen(this);
        requestPermissions();
//        onClick(mLyMenuHome);
        changeTag(R.id.lyMenuHome);
        showHotPrint();
        updapp();//更新
//        this.getResources().getColor(R.color.huan_xin_title);
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        MyShareUtil.sharedPint("delegroup",0);
        DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息
        /**
         * BQMM集成
         * 首先从AndroidManifest.xml中取得appId和appSecret，然后对BQMM SDK进行初始化
         */
        try {
            Bundle bundle = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;
            BQMM.getInstance().initConfig(MainActivity.this, bundle.getString("bqmm_app_id"), bundle.getString("bqmm_app_secret"));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        mloadpd = new ProgressDialog(MainActivity.this);
        mloadpd.setCanceledOnTouchOutside(false);
        mloadpd.setOnCancelListener(new DialogInterface.OnCancelListener() {//取消

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d("main", "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        mloadpd.setMessage(getString(R.string.Is_landing));
        if (!UserNative.readIsLogin()&& !TextUtil.isEmpty(UserNative.getEpId())){
            Login(UserNative.getPhone(), UserNative.getPwd());
        }
    }
    private void showMoreWindow(View view) {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(this);
            mMoreWindow.init();
        }

        mMoreWindow.showMoreWindow(view,100);
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }


    @OnClick({R.id.lyMenuHome, R.id.lyMenuNews, R.id.remenuBusiness, R.id.remenuChat, R.id.lyMenuCenter})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.lyMenuHome:
//                SoundUtils.playSoundByVolume(MainActivity.this, R.raw.click_water, 0.2f);
                setStatusBar(this.getResources().getColor(R.color.bqmm_transparent));
//                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//                getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                setStatusBar(this.getResources().getColor(R.color.black ));
//                getWindow().setStatusBarColor(getResources().getColor(R.color.transparent));
                changeTag(view.getId());
                break;
            case R.id.lyMenuNews:
//                SoundUtils.playSoundByVolume(MainActivity.this, R.raw.click_water, 0.2f);
                setStatusBar(this.getResources().getColor(R.color.main_2_0_0));
                changeTag(view.getId());
                break;
            case R.id.remenuBusiness:
//                setStatusBar(this.getResources().getColor(R.color.main_2_0_0));
//                changeTag(view.getId());
//                showMoreWindow(view);

                PopupMenuUtil.getInstance()._show(MainActivity.this, publishBtn);
                break;
            case R.id.remenuChat:
//                SoundUtils.playSoundByVolume(MainActivity.this, R.raw.click_water, 0.2f);
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//                setStatusBar(this.getResources().getColor(R.color.huan_xin_title));
                setStatusBar(this.getResources().getColor(R.color.main_2_0_0));
                changeTag(view.getId());
                break;
            case R.id.lyMenuCenter:
                // 未登录跳转登陆
//                if (! UserNative.readIsLogin()){
//                    toLogin();
//                    return;
//                }
//                SoundUtils.playSoundByVolume(MainActivity.this, R.raw.click_water, 0.2f);
                setStatusBar(this.getResources().getColor(R.color.main_2_0_0));
                changeTag(view.getId());
                break;
            default:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MainEvent mainEvent) {
        changeTag(mainEvent.getChangId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OutLoginEvent outLoginEvent) {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(MessageChangeEvent messageChangeEvent) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                showHotPrint();
//                if (e==null){
//                    e=new Employee();
//                    //需要调用的时候先注册,传入Boss类型对象和员工参数
//                    e1=e.zhuce(new ConversationListFragment(),e);
//                }
//                e1.dosomething();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChangeFragmentEvent changeFragmentEvent) {
        changeTag(changeFragmentEvent.getViewId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OutLoginByJpushEvent outLoginEvent) {
        Logger.e("out login");
        MyShareUtil.sharedPint("ifchat",0);
        outlogin(true);
//        finish();
    }

    @TargetApi(23)
    private void requestPermissions() {
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
//				Toast.makeText(MainActivity.this, "All permissions have been granted", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDenied(String permission) {
                //Toast.makeText(MainActivity.this, "Permission " + permission + " has been denied", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void outlogin(final Boolean otherDevice){
        // UMENG 统计退出登陆
        MobclickAgent.onProfileSignOff();
//        JPushInterface.deleteAlias(MainActivity.this, JPushUtil.sequence);
        JPushUtil.deleteJPushAlias(MainActivity.this);
        UserNative.saveIsLogin(false);
//        UserNative.clearData();
        ToastUtil.showInfo(MainActivity.this, "您的账号在其他设备登录！", Toast.LENGTH_SHORT);

    }

    /**
     * 根据控件id切换菜单选项
     *
     * @param id
     */
    void changeTag(int id) {
        // 跳转商圈
        up = MyShareUtil.getSharedString("updata");
        Log.v("lgq",".........changeTag."+up);

        MyShareUtil.sharedPstring("updata","-1");
        if (id == R.id.remenuBusiness) {
//            clearAllStyle();
            startActivity(new Intent(this, BusinessDistrictLibraryActivity.class));////圆心商圈
            return;
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        if (up.equals("2"));{
//            fragmentTransaction.remove(homeFragment);
//            fragmentTransaction.remove(gxFragment);
//            fragmentTransaction.remove(epChatFragment);
//            fragmentTransaction.remove(mineFragment);
//        }
        clearAllStyle();
        hideAll();
        switch (id) {
            case R.id.lyMenuHome:
                mImgMenuHome.setSelected(true);
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.fl_content, homeFragment);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                shouyete.setTextColor(getResources().getColor(R.color.main_2_0_0));
                zixunte.setTextColor(getResources().getColor(R.color.shouyehui));
                mBusinessText.setTextColor(getResources().getColor(R.color.shouyehui));
                qiliaote.setTextColor(getResources().getColor(R.color.shouyehui));
                wodete.setTextColor(getResources().getColor(R.color.shouyehui));
                break;
            case R.id.lyMenuNews:
                mImgMenuNews.setSelected(true);
                if (gxFragment == null) {
                    gxFragment = new HomeGongxuFragment();
                    fragmentTransaction.add(R.id.fl_content, gxFragment);
                } else {
                    fragmentTransaction.show(gxFragment);
                }
                shouyete.setTextColor(getResources().getColor(R.color.shouyehui));
                zixunte.setTextColor(getResources().getColor(R.color.main_2_0_0));
                mBusinessText.setTextColor(getResources().getColor(R.color.shouyehui));
                qiliaote.setTextColor(getResources().getColor(R.color.shouyehui));
                wodete.setTextColor(getResources().getColor(R.color.shouyehui));
                break;
            case R.id.remenuChat:
                mImgMenuChat.setSelected(true);
                if (epChatFragment == null) {
                    epChatFragment = new NewEpChatFragment();
                    fragmentTransaction.add(R.id.fl_content, epChatFragment);
                } else {
                    fragmentTransaction.show(epChatFragment);
                }
                shouyete.setTextColor(getResources().getColor(R.color.shouyehui));
                zixunte.setTextColor(getResources().getColor(R.color.shouyehui));
                mBusinessText.setTextColor(getResources().getColor(R.color.shouyehui));
                qiliaote.setTextColor(getResources().getColor(R.color.main_2_0_0));
                wodete.setTextColor(getResources().getColor(R.color.shouyehui));
                break;
            case R.id.lyMenuCenter:
                mImgMenuCenter.setSelected(true);
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.fl_content, mineFragment);
                } else {
                    fragmentTransaction.show(mineFragment);
                }
                shouyete.setTextColor(getResources().getColor(R.color.shouyehui));
                zixunte.setTextColor(getResources().getColor(R.color.shouyehui));
                mBusinessText.setTextColor(getResources().getColor(R.color.shouyehui));
                qiliaote.setTextColor(getResources().getColor(R.color.shouyehui));
                wodete.setTextColor(getResources().getColor(R.color.main_2_0_0));
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 清除所有的菜单样式
     */
    private void clearAllStyle() {
        mImgMenuHome.setSelected(false);
        mImgMenuNews.setSelected(false);
        mImgMenuChat.setSelected(false);
        mImgMenuCenter.setSelected(false);
    }
    /**
     * 隐藏所有碎片
     */
    void hideAll() {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (gxFragment != null) {
            fragmentTransaction.hide(gxFragment);
        }
        if (epChatFragment != null) {
            fragmentTransaction.hide(epChatFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
    }

//    private long firstTime;

    @Override
    public void onBackPressed() {
        Intent home=new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.cancelRequests(this, true);
        UserNative.saveIsLogin(false);
        EventBus.getDefault().unregister(this);
        MyShareUtil.sharedPstring("selenewsid","1");
        MyShareUtil.sharedPstring("shangquantop","0");
        MyShareUtil.sharedPstring("qiyekutop","0");
        MyShareUtil.sharedPstring("qiyekutitle","0");
        MyShareUtil.sharedPint("ifchat",0);

    }

    //设置未读消息数量
    public void showHotPrint() {
        int inviteNumber = DemoHelper.getInstance().getUnreadMsgsCount();
//        int inviteNumber = DemoDBManager.getInstance().getUnreadNotifyCount();
        SellerMessageDao sellerMessageDao = new SellerMessageDao(this);
        BuyerMessageDao buyerMessageDao = new BuyerMessageDao(this);
        BusinessMessageDao businessMessageDao = new BusinessMessageDao(this);
        int sellerMsgNumber = sellerMessageDao.getUnreadMessagesCount();
        int buyerMsgNumber = buyerMessageDao.getUnreadMessagesCount();
        int businessMsgNumber = businessMessageDao.getUnreadMessagesCount();
        int number = inviteNumber + sellerMsgNumber + buyerMsgNumber + businessMsgNumber;
        if (number > 0) {
//        EaseConversationListFragment.refresh();
//            mConversationListFragment.onResume();
            mTvChatNum.setVisibility(View.VISIBLE);
            mTvChatNum.setText(String.valueOf(number));
            ShortcutBadger.applyCount(getApplicationContext(), number); //for 1.1.4+
        } else {
            mTvChatNum.setVisibility(View.GONE);
            ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
        }
    }

    @Override
    protected void onResume() {
        isForeground = true;
        //极光推送的启动
        Log.v("lgq","..onDestroy.onResume.mmmmmmmm");
        JPushInterface.onResume(this);
        super.onResume();
        showHotPrint();
        MobclickAgent.onPageStart(mPageName);
        MobclickAgent.onResume(this);

    }

    @Override
    protected void onPause() {
        isForeground = false;
        //极光推送的暂停
//        JPushInterface.onPause(this);
        Log.v("lgq","..onPause.."+1);
        super.onPause();
        MobclickAgent.onPageEnd(mPageName);
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("lgq","..onStop.."+1);
    }

    private void updapp() {
        String url = Url.updload;
        RequestParams params = new RequestParams();
        params.put("versionCode", MainApplication.getVersionCode());//用户id
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
//                    Log.i("lgq","更新-----"+s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        updateContent = array.getJSONArray("updateContent");
                        String url = array.getString("url");
                        int web = array.getInt("versionCode");
                        int loca = MainApplication.getVersionCode();
                        if (web>loca){
                            dad();
                        }
                        wbovs = String.valueOf(web);
                        MyShareUtil.sharedPstring(R.string.APP_UPDATE_URL,url);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }
        });
    }

    //更新dialog
    public void dad() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        View view = inflater.inflate(R.layout.updateappla, null);
        dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setOnKeyListener(dd);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

        lp.width = Utils.getScreenWidth(MainActivity.this) - 40; // 宽度
        lp.height = Utils.getViewMeasureHeight(view) + 100; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);
//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        TextView qd = (TextView)view.findViewById(R.id.qdapk);
        TextView qx = (TextView)view.findViewById(R.id.qcapk);
        TextView co = (TextView)view.findViewById(R.id.qcapkcontent);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < updateContent.length(); i++) {
            try {
                if (i == updateContent.length()-1) {
                    sb.append((i+1) + "、" + updateContent.getString(i));
                } else {
                    sb.append((i+1) + "、" + updateContent.getString(i) + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        co.setText(sb.toString());
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(MainActivity.this, UpdateAppService.class);
                updateIntent.putExtra("titleId", R.string.app_name);
                updateIntent.putExtra(UpdateAppService.APK_VERSION, wbovs);
                MainActivity.this.startService(updateIntent);
                dialog.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    private DialogInterface.OnKeyListener dd = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent keyEvent) {
            if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                return true;
            }
            return false;
        }
    };

    private void Login(final String currentUsername, final String currentPassword) {
        mloadpd.show();
        JPushUtil.initJPush(MainActivity.this);
        RequestParams params = new RequestParams();
        params.put("registrationId", JPushInterface.getRegistrationID(MainActivity.this));
        params.put("loginDevice", "android_" + JPushUtil.getDeviceId(this));
        params.put("userNm", currentUsername);
        params.put("userPwd", currentPassword);
//        doHttpGet(Url.login, params, new RequestCallback() {
               client.get(Url.login, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        String userIdOne = array.getString("userId");
                        int  userId = Integer.valueOf(userIdOne);
                        String role = array.getString("role");//1系统管理员 2 运营管理员3普通用户 39客服
                        String userNm = array.getString("userNm");
                        String userPhone = array.getString("userPhone");
                        String easemobUuid = array.getString("easemobUuid").trim(); //环信
                        if (easemobUuid.equals("null")) {
                            easemobUuid = "0";
                        }
                        String epId = array.getString("epId");
                        if (epId.equals("null")) {
                            epId = "0";
                        }
                        String userImage = array.getString("userImage");//头像
//                        String userImageNeed = Url.urlHost + userImage;
                        String userImageNeed = Url.img_domain + userImage + Url.imageStyle640x640;

                        String userSex = array.getString("userSex");//性别
                        String province = array.getString("province");//
                        String city = array.getString("city");//
                        String area = array.getString("area");//
                        String detail = array.getString("detail");//
                        String company = array.getString("company");//
                        String expertPosition = array.getString("expertPosition");//高级经理
                        String epNm = array.getString("epNm");//
                        String epPosition = array.getString("epPosition");//高级经理
                        String addressId = array.getString("addressId");
                        String userPwd = array.getString("userPwd");
                        //epRole
                        String epRole = array.getString("epRole");//1 管理员 2 普通成员
                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putInt("userId", userId);// id
                        editor.putString("role", role);//epId
                        editor.putString("epId", epId);
                        editor.putString("userNm", userNm);
                        editor.putString("userPhone", userPhone);
                        editor.putString("userImage", userImageNeed);//头像
                        editor.putString("userSex", userSex);//性别
                        editor.putString("province", province);//性别
                        editor.putString("city", city);//性别
                        editor.putString("area", area);//性别
                        editor.putString("detail", detail);//性别
                        editor.putString("company", company);//性别
                        editor.putString("expertPosition", expertPosition);//性别
                        editor.putString("epNm", epNm);//性别
                        editor.putString("epPosition", epPosition);//性别
                        editor.putString("addressId", addressId);//性别
                        editor.putString("easemobUuid", easemobUuid);//性别
                        editor.putString("userPwd", userPwd);//性别
                        editor.putString("epRole", epRole);//1 管理员 2 普通成员
                        MyShareUtil.sharedPstring("loginpw",currentPassword);
                        MyShareUtil.sharedPstring("loginph",currentUsername);
                        editor.commit();//提交修改

                        //环信用
                        SharedPreferences sharedPreferencesOne = getSharedPreferences("huanXin", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editorOne = sharedPreferencesOne.edit();//获取编辑器
                        editorOne.putString("userNm", userNm);//环信昵称
                        editorOne.putString("userPhone", userPhone);//电话
                        editorOne.putString("userImage", userImageNeed);//头像
                        editorOne.commit();//提交修改
                        //环信用 结束
                        UserNative.saveIsLogin(true);
                        JPushUtil.initJPush(MainActivity.this);
//                        Log.v("lgq","......name="+currentUsername+".....pw="+currentPassword+"...userNm="+userNm+"....ph="+userPhone+"..."+userImageNeed);
                        LoginWenXin(currentUsername, currentPassword, userNm, userPhone, userImageNeed);

                        // UMENG 统计登陆
                        MobclickAgent.onProfileSignIn(String.valueOf(UserNative.getId()));
                    } else {
                        mloadpd.dismiss();
                        ToastUtil.showError(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                    mloadpd.dismiss();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mloadpd.dismiss();
            }
        });
    }

    private void LoginWenXin(final String username, String password, final String userNm, final String userPhone, final String userImageNeed) {
        //开始 环信
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            ToastUtil.showWarning(this, getString(R.string.network_isnot_available), Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showWarning(this, getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showWarning(this, getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT);
            return;
        }
        progressShow = true;
        EMClient.getInstance().login(username, password, new EMCallBack() {//测试密码

            @Override
            public void onSuccess() {
                //环信头像新改
//                AppSPUtils.setValueToPrefrences("name", loginBean.getName());
//                AppSPUtils.setValueToPrefrences("logoUrl", loginBean.getLogoUrl());
                // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
//                UserProfileManager userProfileManager = new UserProfileManager();
//                userProfileManager.getCurrentUserInfo().setNick(userNm);
//                PreferenceManager.getInstance().setCurrentUserNick(userNm);
//                userProfileManager.getCurrentUserInfo().setAvatar(userImageNeed);
//                PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);
//                PreferenceManager.getInstance().setCurrentUserName(userPhone);
//
//                DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id

                // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
//                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(userNm);
//                DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(userImageNeed);
//                DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id
//                //环信头像新改结束
                mloadpd.dismiss();
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
//                PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);
            }

            @Override
            public void onProgress(int progress, String status) {
//                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
//                Log.d(TAG, "login: onError: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        mloadpd.dismiss();
                        Log.v("Lgq","....EMError.USER_ALREADY_EXIST=="+ EMError.USER_ALREADY_LOGIN+".....code=="+code);
//                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message, Toast.LENGTH_SHORT).show();
                        if (EMError.USER_ALREADY_LOGIN==code||code==EMError.USER_AUTHENTICATION_FAILED){//已经登录。验证失败。可通过
//                            UserProfileManager userProfileManager = new UserProfileManager();
//                            userProfileManager.getCurrentUserInfo().setNick(userNm);
//                            PreferenceManager.getInstance().setCurrentUserNick(userNm);
//
//                            userProfileManager.getCurrentUserInfo().setAvatar(userImageNeed);
//                            PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);
//
////                            DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(userNm);
//                            DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(userImageNeed);
//                            DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id
//                            //环信头像新改结束
//                            EMClient.getInstance().groupManager().loadAllGroups();
//                            EMClient.getInstance().chatManager().loadAllConversations();
//
//                            boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(UserNative.getName());//昵称
//                            if (!updatenick) {//没有更新头像
//                                Log.e("LoginActivity", "update current user nick fail");
//                            }
//                            if (!MainActivity.this.isFinishing() && pd.isShowing()) {
//                                pd.dismiss();
//                            }
//                            DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息
////                            DemoHelper.getInstance().setCurrentUserName(username);
////                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
////                            startActivity(intent);
////                            finish();
                            // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
//                            DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(userNm);
//                            DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(userImageNeed);
//                            DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id
//                            //环信头像新改结束
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
//                            PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);

                        }
                    }
                });
            }
        });
    }

}

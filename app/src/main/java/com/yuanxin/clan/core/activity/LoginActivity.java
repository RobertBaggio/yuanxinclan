package com.yuanxin.clan.core.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.umeng.analytics.MobclickAgent;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.event.LoginEvent;
import com.yuanxin.clan.core.event.ThirdLoginEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.JPushUtil;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.core.weixin.ThirdLoginListener;
import com.yuanxin.clan.core.weixin.WeChatUtil;
import com.yuanxin.clan.mvp.share.AuthorizeUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

/**
 * Created by lenovo1 on 2017/1/26.
 * 登录首页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, AuthorizeUtil.AuthorizeListener {


    @BindView(R.id.activity_login_image)
    ImageView activityLoginImage;
    @BindView(R.id.activity_login_common_register)
    TextView activityLoginCommonRegister;
    @BindView(R.id.activity_login_member_register)
    TextView activityLoginMemberRegister;
    @BindView(R.id.activity_login_head_layout)
    RelativeLayout activityLoginHeadLayout;
    @BindView(R.id.login_tel)
    EditText loginTel;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_button)
    Button loginButton;
    @BindView(R.id.login_errowimage)
    ImageView login_errowimage;
    @BindView(R.id.activity_login_wei_xin_text)
    ImageView activityLoginWeiXinText;
    @BindView(R.id.activity_login_wei_xin_text_layout)
    LinearLayout activityLoginWeiXinTextLayout;
    @BindView(R.id.activity_login_qq_text)
    ImageView activityLoginQqText;
    @BindView(R.id.activity_login_qq_text_layout)
    LinearLayout activityLoginQqTextLayout;
    @BindView(R.id.deleli)
    LinearLayout deleli;

    ConnectivityManager manager;
    @BindView(R.id.activity_login_layout)
    LinearLayout activityLoginLayout;
    @BindView(R.id.activity_login_forget_password)
    TextView activityLoginForgetPassword;

    @BindView(R.id.register_text)
    TextView registerBtn;

    private String currentUsername,currentPassword;
    private boolean progressShow;
    private static final String TAG = "LoginActivity";
    private int userId;
    private String epId;
    private String easemobUuid;
    private ProgressDialog mloadpd;
    WeChatUtil weChatUtil;

    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public int getViewLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        EventBus.getDefault().register(this);
        String pw =MyShareUtil.getSharedString("loginpw");
        String ph =MyShareUtil.getSharedString("loginph");
        MyShareUtil.sharedPint("dlone",0);
        if (!TextUtils.isEmpty(ph)){
            loginTel.setText(ph);
            loginPassword.setText(pw);
        }
        loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
        // UMENG 统计
        MobclickAgent.setDebugMode(true);
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.openActivityDurationTrack(false);
        // MobclickAgent.setAutoLocation(true);
        // MobclickAgent.setSessionContinueMillis(1000);
        // MobclickAgent.startWithConfigure(
        // new UMAnalyticsConfig(mContext, "4f83c5d852701564c0000011", "Umeng",
        // EScenarioType.E_UM_NORMAL));
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);


        loginTel.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
                Log.e("输入过程中执行该方法", "文字变化");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // 输入前的监听
                Log.e("输入前确认执行该方法", "开始输入");

            }

            @Override
            public void afterTextChanged(Editable s) {
                // 输入后的监听
                Log.e("输入结束执行该方法", "输入结束");
                if (Utils.isPhoneNumber(loginTel.getText().toString())){
                    login_errowimage.setImageResource(R.drawable.login_right);
                }else {
                    login_errowimage.setImageResource(R.drawable.login_errow);
                }
            }
        });

        mloadpd = new ProgressDialog(LoginActivity.this);
        mloadpd.setCanceledOnTouchOutside(false);
        mloadpd.setOnCancelListener(new DialogInterface.OnCancelListener() {//取消

            @Override
            public void onCancel(DialogInterface dialog) {
                Log.d(TAG, "EMClient.getInstance().onCancel");
                progressShow = false;
            }
        });
        mloadpd.setMessage(getString(R.string.Is_landing));

        if (getIntent().getBooleanExtra("other_device", false)) {
            ToastUtil.showInfo(this, "你的账号已在其他设备登陆", Toast.LENGTH_SHORT);
        }
    }
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            currentUsername = loginTel.getText().toString().trim();
            currentPassword = loginPassword.getText().toString().trim();
            Login(currentUsername, currentPassword);
            //处理事件
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(final LoginEvent loginEvent) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                currentUsername = loginTel.getText().toString().trim();
                if (currentUsername.equals("") && !Utils.isPhoneNumber(currentUsername)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入正确的电话号码", Toast.LENGTH_SHORT);
                    return;
                }
                currentPassword = loginPassword.getText().toString().trim();
                Login(currentUsername, currentPassword);
            }
        });
    }
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(final ThirdLoginEvent thLoginEvent) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                LoginWithWechatOrQQ(thLoginEvent);
            }
        });
    }
    /**
     * 初始化第三方登陆工具
     */
    private void initThirdLogin() {
        ThirdLoginListener thirdLoginListener = new ThirdLoginListener() {
            @Override
            public void onComplete(String strThirdType, String strUniqueID, String strAccessToken, String strExpiresIn) {
//                TasksUtils.thirdLogin(LoginActivity.this, strThirdType, dealListener, strUniqueID, strAccessToken, strExpiresIn);//第三方注册
            }
        };
        //微信登陆
        weChatUtil = WeChatUtil.getInstance(this, thirdLoginListener);
    }
    @OnClick({R.id.activity_login_forget_password, R.id.login_button, R.id.activity_login_wei_xin_text_layout,
            R.id.activity_login_qq_text_layout, R.id.activity_login_common_register,
            R.id.activity_login_member_register,R.id.deleli,R.id.login_errowimage, R.id.register_text})
    public void onClick(View view) {
        AuthorizeUtil authorizeUtil = new AuthorizeUtil(this);
        switch (view.getId()) {
            case R.id.activity_login_forget_password:
                startActivity(new Intent(LoginActivity.this, FindPasswordActivity.class));
                break;
            case R.id.activity_login_wei_xin_text_layout:
                //微信登录

                authorizeUtil.auth(Wechat.NAME, LoginActivity.this);
                break;
            case R.id.activity_login_qq_text_layout:
                //QQ登录
                authorizeUtil.auth(QQ.NAME, LoginActivity.this);
                break;
            case R.id.login_button:
                currentUsername = loginTel.getText().toString().trim();
                if (currentUsername.equals("") && !Utils.isPhoneNumber(currentUsername)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入正确的电话号码", Toast.LENGTH_SHORT);
                    return;
                }
                String currentPassword = loginPassword.getText().toString().trim();
//                //先自己登录，再微信登录
                mloadpd.show();
                String JpushReID = JPushInterface.getRegistrationID(this);
                Login(currentUsername, currentPassword);
//                Login(currentUsername, currentPassword, JpushReID);
                break;
            case R.id.activity_login_common_register:
            case R.id.register_text:
                startActivity(new Intent(LoginActivity.this, LoginCommonRegisterActivity.class));
                finish();
//                startActivity(new Intent(LoginActivity.this, CompleteEpActivity.class));
                break;
            case R.id.activity_login_member_register:
//                startActivity(new Intent(LoginActivity.this, LoginMemberRegisterActivity.class));
                startActivity(new Intent(LoginActivity.this, CompleteEpActivity.class));
                break;
            case R.id.deleli:
                finish();
                break;
            case R.id.login_errowimage:
                loginTel.setText("");
                break;
//            case R.id.activity_login_qr_code://二维码扫描
//                new IntentIntegrator(this).initiateScan(); //大屏幕扫描
////                new IntentIntegrator(this).setCaptureActivity(ToolbarCaptureActivity.class).initiateScan();//小屏幕扫描 需要修改
            default:
                break;
        }
    }
    private void Login(final String currentUsername, final String currentPassword) {
//        JPushUtil.initJPush(LoginActivity.this);
        RequestParams params = new RequestParams();
        params.put("registrationId", JPushInterface.getRegistrationID(LoginActivity.this));
        params.put("loginDevice", "android_" + JPushUtil.getDeviceId(this));
        params.put("userNm", currentUsername);
        params.put("userPwd", currentPassword);
        client.post(Url.login, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mloadpd.dismiss();
//                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        String userIdOne = array.getString("userId");
                        userId = Integer.valueOf(userIdOne);
                        String role = array.getString("role");//1系统管理员 2 运营管理员3普通用户 4 5
                        String userNm = array.getString("userNm");
                        String userPhone = array.getString("userPhone");
                        easemobUuid = array.getString("easemobUuid").trim(); //环信
                        if (easemobUuid.equals("null")) {
                            easemobUuid = "0";
                        }
                        epId = array.getString("epId");
                        if (epId.equals("null")) {
                            epId = "0";
                        }
                        String userImage = array.getString("userImage");//头像
                        String epImage1 = array.getString("epImage1");//头像
//                        String userImageNeed = Url.urlHost + userImage;
                        String userImageNeed = Url.img_domain + userImage + Url.imageStyle640x640;
                        String ependimage=Url.img_domain+epImage1+Url.imageStyle640x640;
                        String userSex = array.getString("userSex");//性别
                        String province = array.getString("province");
                        String city = array.getString("city");
                        String area = array.getString("area");
                        String detail = array.getString("detail");
                        String company = array.getString("company");
                        String expertPosition = array.getString("expertPosition");//高级经理
                        String epNm = array.getString("epNm");
                        String epPosition = array.getString("epPosition");//高级经理
                        String addressId = array.getString("addressId");
//                        String userPwd = array.getString("userPwd");//隐藏测试
                        //epRole
                        String epRole = array.getString("epRole");//1 管理员 2 普通成员
                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putInt("userId", userId);// id
                        editor.putString("role", role);//epId
                        editor.putString("epId", epId);
                        MyShareUtil.sharedPstring("eplogo",ependimage);
                        editor.putString("userNm", userNm);
                        editor.putString("userPhone", userPhone);
                        editor.putString("userImage", userImageNeed);//头像
                        editor.putString("userSex", userSex);//性别
                        editor.putString("province", province);//省
                        editor.putString("city", city);//市
                        editor.putString("area", area);//
                        editor.putString("detail", detail);
                        editor.putString("company", company);
                        editor.putString("expertPosition", expertPosition);
                        editor.putString("epNm", epNm);
                        editor.putString("epPosition", epPosition);
                        editor.putString("addressId", addressId);
                        editor.putString("easemobUuid", easemobUuid);
                        editor.putString("userPwd", currentPassword);
                        editor.putString("epRole", epRole);//1 管理员 2 普通成员
                        editor.putString("aesKes", array.getString("aesKes"));//测试关
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
                        JPushUtil.initJPush(LoginActivity.this);
//                        Log.v("lgq","......name="+currentUsername+".....pw="+currentPassword+"...userNm="+userNm+"....ph="+userPhone+"..."+userImageNeed);
                        LoginWenXin(currentUsername, currentPassword, userNm, userPhone, userImageNeed, false);

                        // UMENG 统计登陆
                        MobclickAgent.onProfileSignIn(String.valueOf(UserNative.getId()));
                    } else {
                        mloadpd.dismiss();
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mloadpd.dismiss();
                }
            }
        });
    }

    private void LoginWithWechatOrQQ(final ThirdLoginEvent thirdLoginEvent) {
        RequestParams params = new RequestParams();
        params.put("registrationId", thirdLoginEvent.getRegistrationID());
        params.put("loginDevice", "android_" + JPushUtil.getDeviceId(this));
        params.put("wechatUid", thirdLoginEvent.getUserId());
        client.post(Url.getWechatLogin, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        String userIdOne = array.getString("userId");
                        userId = Integer.valueOf(userIdOne);
                        String role = array.getString("role");//1系统管理员 2 运营管理员3普通用户 4 5
                        String userNm = array.getString("userNm");
                        String userPhone = array.getString("userPhone");
                        easemobUuid = array.getString("easemobUuid").trim(); //环信
                        if (easemobUuid.equals("null")) {
                            easemobUuid = "0";
                        }
                        epId = array.getString("epId");
                        if (epId.equals("null")) {
                            epId = "0";
                        }
                        String userImage = array.getString("userImage");//头像
                        String userImageNeed = Url.img_domain + userImage +Url.imageStyle208x208;;
                        String userSex = array.getString("userSex");//性别
                        String province = array.getString("province");//
                        String city = array.getString("city");//
                        String area = array.getString("area");//
                        String company = array.getString("company");//
                        String expertPosition = array.getString("expertPosition");//高级经理
                        String epNm = array.getString("epNm");//
                        String epPosition = array.getString("epPosition");//高级经理
                        String addressId = array.getString("addressId");
                        String userPwd = array.getString("userPwd");
                        if (TextUtil.isEmpty(userPwd)) {
                            userPwd = "123456";
                        }
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
                        editor.putString("company", company);//性别
                        editor.putString("expertPosition", expertPosition);//性别
                        editor.putString("epNm", epNm);//性别
                        editor.putString("epPosition", epPosition);//性别
                        editor.putString("addressId", addressId);//性别
                        editor.putString("easemobUuid", easemobUuid);//性别
                        editor.putString("userPwd", userPwd);//性别
                        editor.putString("epRole", epRole);//1 管理员 2 普通成员
                        editor.putString("aesKes", array.getString("aesKes"));
                        MyShareUtil.sharedPstring("loginpw",userPwd);
                        MyShareUtil.sharedPstring("loginph",userPhone);
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
                        JPushUtil.initJPush(LoginActivity.this);
//                        Log.v("lgq","......name="+currentUsername+".....pw="+currentPassword+"...userNm="+userNm+"....ph="+userPhone+"..."+userImageNeed);
                        LoginWenXin(userPhone, userPwd, userNm, userPhone, userImageNeed, true);

                        // UMENG 统计登陆
                        MobclickAgent.onProfileSignIn(String.valueOf(UserNative.getId()));
                    } else {
                        startActivity(new Intent(LoginActivity.this, BindingPhoneActivity.class).putExtra("third_info", FastJsonUtils.toJSONString(thirdLoginEvent)));
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void LoginWenXin(final String username, String password, final String userNm, final String userPhone, final String userImageNeed, final Boolean isThirdLogin) {
        //开始 环信
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            ToastUtil.showWarning(this, getString(R.string.network_isnot_available), Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(username)) {
            ToastUtil.showWarning(this, getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT);
            return;
        }
        if (!isThirdLogin && TextUtils.isEmpty(password)) {
            ToastUtil.showWarning(this, getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT);
            return;
        }
        progressShow = true;

//        final long start = System.currentTimeMillis();
        // call login method
        Log.d(TAG, "EMClient.getInstance().login");
//        if (username.equals("13164716840"))
//            password="123456";
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
//                /**
//                 * 注意： 登录成功后需要调用EMClient.getInstance().chatManager().loadAllConversations(); 和EMClient.getInstance().groupManager().loadAllGroups();。
//
//                 以上两个方法是为了保证进入主页面后本地会话和群组都 load 完毕。
//
//                 另外如果登录过，APP 长期在后台再进的时候也可能会导致加载到内存的群组和会话为空，可以在主页面的 oncreate 里也加上这两句代码，当然，更好的办法应该是放在程序的开屏页，可参考 Demo 的 SplashActivity。
//                 *
//                 * **/
//                EMClient.getInstance().groupManager().loadAllGroups();
//                EMClient.getInstance().chatManager().loadAllConversations();
////                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息

                // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
//                DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(userNm);
//                DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(userImageNeed);
//                DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id
                //环信头像新改结束
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
//                PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);

//                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(UserNative.getName());//昵称
//                if (!updatenick) {//没有更新头像
//                    Log.e("LoginActivity", "update current user nick fail");
//                }
//                if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                mloadpd.dismiss();
//                }
//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息
//                DemoHelper.getInstance().setCurrentUserName(username);
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                if (!progressShow) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        mloadpd.dismiss();
                        Log.v("Lgq","....EMError.USER_ALREADY_EXIST=="+EMError.USER_ALREADY_LOGIN+".....code=="+code);
//                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message, Toast.LENGTH_SHORT).show();
                        if (EMError.USER_NOT_FOUND==code){
                            ToastUtil.showWarning(getApplicationContext(), "环信用户不存在！", Toast.LENGTH_SHORT);
                            return;
                        }
//                        if (EMError.USER_ALREADY_LOGIN==code||code==EMError.USER_AUTHENTICATION_FAILED){//已经登录。验证失败。可通过
                        if (EMError.USER_ALREADY_LOGIN==code){ //已经登录。验证失败。可通过
//                            UserProfileManager userProfileManager = new UserProfileManager();
//                            userProfileManager.getCurrentUserInfo().setNick(userNm);
//                            PreferenceManager.getInstance().setCurrentUserNick(userNm);
//
//                            userProfileManager.getCurrentUserInfo().setAvatar(userImageNeed);
//                            PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);
//
//                            DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(userNm);
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
//                            if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
//                                pd.dismiss();
//                            }
//                            DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息
////                            DemoHelper.getInstance().setCurrentUserName(username);

                            // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
//                            DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(userNm);
//                            DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(userImageNeed);
//                            DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id
                            //环信头像新改结束
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();
//                            PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);

//                            boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(UserNative.getName());//昵称
//                            if (!updatenick) {//没有更新头像
//                                Log.e("LoginActivity", "update current user nick fail");
//                            }
//                            if (!LoginActivity.this.isFinishing() && pd.isShowing()) {
                            mloadpd.dismiss();
//                            }
//                            DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息
//                            DemoHelper.getInstance().setCurrentUserName(username);
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.showWarning(getApplicationContext(), "环信登录失败！", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });
    }

    //生成二维码代码  传入一个字符串，生成一个二维码的Bitmap
    Bitmap encodeAsBitmap(String str) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) { // ?
            return null;
        }
        // 如果不使用 ZXing Android Embedded 的话，要写的代码
//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);
        return bitmap;
    }


    @Override
    public void onComplete(String thirdLoginEventString) {
//        LoginWithWechatOrQQ(thirdLoginEventString);
    }

    @Override
    public void onError() {
        Logger.e("login fail");
        ToastUtil.showWarning(LoginActivity.this, "授权操作失败", Toast.LENGTH_SHORT);
    }

    @Override
    public void onCancel() {
        Logger.e("login cancel");
        ToastUtil.showInfo(LoginActivity.this, "授权操作已取消", Toast.LENGTH_SHORT);
    }
}

package com.yuanxin.clan.core.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.MainActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.huanxin.PreferenceManager;
import com.yuanxin.clan.core.huanxin.UserProfileManager;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.JPushUtil;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.TimerUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
//普通用户登录

/**
 * Created by lenovo1 on 2017/2/20.
 * 快速注册类
 */
public class LoginCommonRegisterActivity extends BaseActivity {

    @BindView(R.id.kejianimage)
    ImageView kejianimage;
    @BindView(R.id.window_head_left_layout)
    LinearLayout windowHeadLeftLayout;
    @BindView(R.id.activity_login_common_register_phone)
    EditText activityLoginCommonRegisterPhone;
    @BindView(R.id.activity_login_common_register_code)
    EditText activityLoginCommonRegisterCode;
    @BindView(R.id.activity_login_common_register_send_code)
    TextView activityLoginCommonRegisterSendCode;
    @BindView(R.id.activity_login_common_register_password)
    EditText activityLoginCommonRegisterPassword;
    @BindView(R.id.activity_login_common_register_register)
    Button activityLoginCommonRegisterRegister;
    @BindView(R.id.activity_login_common_register_login)
    TextView activityLoginCommonRegisterLogin;
    private SubscriberOnNextListener getSmsCodeOnNext, registerOnNext;
    private String code;
    private int ifkejian=0;
    private int mMin = 31;
    private TimerUtil timerUtil;
    private More_LoadDialog mMore_loadDialog;
    private static final String TAG = "LoginActivity";
    private int userId;
    private String epId;
    private String easemobUuid;


    @Override
    public int getViewLayout() {
        return R.layout.activity_login_common_register;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mMore_loadDialog = new More_LoadDialog(this);
        initOnNext();
        initView();
        initTimerUtil();
    }

    private void initView() {
        activityLoginCommonRegisterPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

    }

    private void initOnNext() {
        getSmsCodeOnNext = new SubscriberOnNextListener<BaseJsonEntity>() {
            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                    mMore_loadDialog.dismiss();
                    code = t.getData().toString();
                    timeStart();
                } else {
                    mMore_loadDialog.dismiss();
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        };
        registerOnNext = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    Log.v("lgq","注册返回。。。"+t);
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginCommonRegisterActivity.this, LoginActivity.class));
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    @OnClick({R.id.activity_login_common_register_send_code,R.id.kejianimage, R.id.activity_login_common_register_register, R.id.activity_login_common_register_login, R.id.window_head_left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_common_register_send_code://获取验证码
                String phone = activityLoginCommonRegisterPhone.getText().toString().trim();
                if (phone.equals("")) {
                    Toast.makeText(LoginCommonRegisterActivity.this, "请输入手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!Utils.isPhoneNumber(phone)) {
                    Toast.makeText(LoginCommonRegisterActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    activityLoginCommonRegisterPhone.setText("");
                    return;
                }
                mMore_loadDialog.show();
                HttpMethods.getInstance().getSmsCode(new ProgressSubscriber<BaseJsonEntity>(getSmsCodeOnNext, LoginCommonRegisterActivity.this), phone);
                break;
            case R.id.activity_login_common_register_register://普通注册

                String referrer_phone = activityLoginCommonRegisterPhone.getText().toString().trim();
                if ((TextUtil.isEmpty(referrer_phone)) && (!Utils.isPhoneNumber(referrer_phone))) {
                    Toast.makeText(LoginCommonRegisterActivity.this, "请输入正确手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                String password = activityLoginCommonRegisterPassword.getText().toString().trim();
                if (password.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                String sendCode = activityLoginCommonRegisterCode.getText().toString().trim();
                if (sendCode.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!sendCode.equals(code)) {
                    Toast.makeText(getApplicationContext(), "输入的验证码有误，请重新输入", Toast.LENGTH_LONG).show();
                    activityLoginCommonRegisterCode.setText("");
                    return;
                }
                addInfo();
//                HttpMethods.getInstance().register(new ProgressSubscriber(registerOnNext, LoginCommonRegisterActivity.this), referrer_phone, password);

                break;
            case R.id.activity_login_common_register_login: //登录
//                dad();
                startActivity(new Intent(LoginCommonRegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.window_head_left_layout://返回
                finish();
                break;
            case R.id.kejianimage://返回
                if (ifkejian==0){
                    activityLoginCommonRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    activityLoginCommonRegisterPassword.setSelection(activityLoginCommonRegisterPassword.getText().length());
                    ifkejian=1;
                    kejianimage.setImageResource(R.drawable.register_see);
                }else if (ifkejian==1){
                    //设置密码为隐藏的
                    activityLoginCommonRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    activityLoginCommonRegisterPassword.setSelection(activityLoginCommonRegisterPassword.getText().length());
                    ifkejian=0;
                    kejianimage.setImageResource(R.drawable.see_show);
                }
                break;
        }
    }
//    user/register
private void addInfo() {
    mMore_loadDialog.show();

    String url= Url.register;
    RequestParams params = new RequestParams();
    final String phone = activityLoginCommonRegisterPhone.getText().toString().trim();
    final String password = activityLoginCommonRegisterPassword.getText().toString().trim();
    params.put("userPhone", phone);//商圈名称
    params.put("userPwd", password);//商圈名称

    doHttpPost(url, params, new RequestCallback() {
        @Override
        public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
            ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            mMore_loadDialog.dismiss();
        }

        @Override
        public void onSuccess(int i, Header[] headers, String s) {
            mMore_loadDialog.dismiss();
            try {
                JSONObject object = new JSONObject(s);
                activityLoginCommonRegisterRegister.setEnabled(false);
                Log.v("lgq","shu========"+s);
                if (object.getString("success").equals("true")) {
                    SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putString("userPhone", phone);
                    editor.commit();
                    ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    Login(phone,password);
//                    dad();
//                    finish();
                } else {
                    ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                }
            } catch (JSONException e) {
                Logger.d("json 解析出错");
            }
        }
    });
}


    private void initTimerUtil() {
        TimerUtil.OnTimerChangeListener onTimerChangeListener = new TimerUtil.OnTimerChangeListener() {
            @Override
            public void doChange() {
                doTimeChange();
            }
        };
        timerUtil = new TimerUtil(0, 1000, onTimerChangeListener);
    }
    private void doTimeChange() {
        if (activityLoginCommonRegisterSendCode == null)
            return;
        mMin--;
        if (mMin < 0) {
            timeStop();
            activityLoginCommonRegisterSendCode.setText("获取验证码");
            return;
        }
        activityLoginCommonRegisterSendCode.setText(String.valueOf(mMin)+"秒后重发");
    }
    /**
     * 停止计时器
     */
    private void timeStop() {
        mMin = 31;
        activityLoginCommonRegisterSendCode.setEnabled(true);
        timerUtil.timeStop();
    }
    /**
     * 计时器开始
     */
    private void timeStart() {
        activityLoginCommonRegisterSendCode.setEnabled(false);
        timerUtil.timeStart();
    }

    //更新dialog
    public void dad() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(LoginCommonRegisterActivity.this);
        View view = inflater.inflate(R.layout.after_register_dialog, null);
        dialog = new Dialog(LoginCommonRegisterActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

        lp.width = Utils.getScreenWidth(LoginCommonRegisterActivity.this) - 40; // 宽度
        lp.height = Utils.getViewMeasureHeight(view) + 70; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        TextView tomain = (TextView)view.findViewById(R.id.tomainte);
        TextView toep = (TextView)view.findViewById(R.id.toepte);
//        TextView co = (TextView)view.findViewById(R.id.qcapkcontent);
        StringBuilder sb = new StringBuilder();

//        co.setText(sb.toString());
        tomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(LoginCommonRegisterActivity.this, MainActivity.class);
                LoginCommonRegisterActivity.this.startActivity(updateIntent);
                dialog.dismiss();
                finish();
            }
        });
        toep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(LoginCommonRegisterActivity.this, CompleteEpActivity.class);
                LoginCommonRegisterActivity.this.startActivity(updateIntent);
                dialog.dismiss();
                finish();
            }
        });
        dialog.show();
    }

    private void Login(final String currentUsername, final String currentPassword) {
//        JPushUtil.initJPush(LoginActivity.this);
        RequestParams params = new RequestParams();
        params.put("registrationId", JPushInterface.getRegistrationID(LoginCommonRegisterActivity.this));
        params.put("loginDevice", "android_" + JPushUtil.getDeviceId(this));
        params.put("userNm", currentUsername);
        params.put("userPwd", currentPassword);
        mMore_loadDialog.show();
        doHttpPost(Url.login, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMore_loadDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
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
                        editor.putString("userPwd", currentPassword);//性别
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
                        JPushUtil.initJPush(LoginCommonRegisterActivity.this);
//                        Log.v("lgq","......name="+currentUsername+".....pw="+currentPassword+"...userNm="+userNm+"....ph="+userPhone+"..."+userImageNeed);
                        LoginWenXin(currentUsername, currentPassword, userNm, userPhone, userImageNeed, false);

                        // UMENG 统计登陆
                        MobclickAgent.onProfileSignIn(String.valueOf(UserNative.getId()));
                    } else {
                        mMore_loadDialog.dismiss();
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    mMore_loadDialog.dismiss();
                }
            }
        });
    }

    private void LoginWenXin(final String username, String password, final String userNm, final String userPhone, final String userImageNeed, final Boolean isThirdLogin) {
        //开始 环信
        mMore_loadDialog.show();
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

        EMClient.getInstance().login(username, password, new EMCallBack() {//测试密码

            @Override
            public void onSuccess() {
                //环信头像新改
//                AppSPUtils.setValueToPrefrences("name", loginBean.getName());
//                AppSPUtils.setValueToPrefrences("logoUrl", loginBean.getLogoUrl());
                // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
                UserProfileManager userProfileManager = new UserProfileManager();
                userProfileManager.getCurrentUserInfo().setNick(userNm);
                PreferenceManager.getInstance().setCurrentUserNick(userNm);
                userProfileManager.getCurrentUserInfo().setAvatar(userImageNeed);
                PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);
                PreferenceManager.getInstance().setCurrentUserName(userPhone);

                DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id

//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息
                mMore_loadDialog.dismiss();
                handler.sendMessage(new Message());
//                dad();
//                Intent intent = new Intent(LoginCommonRegisterActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                Log.d(TAG, "login: onError: " + code);
                UserNative.saveIsLogin(false);
                mMore_loadDialog.dismiss();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Log.v("Lgq","....EMError.USER_ALREADY_EXIST=="+ EMError.USER_ALREADY_LOGIN+".....code=="+code);
//                        Toast.makeText(getApplicationContext(), getString(R.string.Login_failed) + message, Toast.LENGTH_SHORT).show();
                        if (EMError.USER_NOT_FOUND==code){
                            ToastUtil.showWarning(getApplicationContext(), "环信用户不存在！", Toast.LENGTH_SHORT);
                            return;
                        }
//                        if (EMError.USER_ALREADY_LOGIN==code||code==EMError.USER_AUTHENTICATION_FAILED){//已经登录。验证失败。可通过
                        if (EMError.USER_ALREADY_LOGIN==code){ //已经登录。验证失败。可通过
                            UserProfileManager userProfileManager = new UserProfileManager();
                            userProfileManager.getCurrentUserInfo().setNick(userNm);
                            PreferenceManager.getInstance().setCurrentUserNick(userNm);

                            userProfileManager.getCurrentUserInfo().setAvatar(userImageNeed);
                            PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);

                            DemoHelper.getInstance().getUserProfileManager().updateCurrentUserNickName(userNm);
                            DemoHelper.getInstance().getUserProfileManager().setCurrentUserAvatar(userImageNeed);
                            DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id
                            //环信头像新改结束
                            EMClient.getInstance().groupManager().loadAllGroups();
                            EMClient.getInstance().chatManager().loadAllConversations();

                            boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(UserNative.getName());//昵称
                            if (!updatenick) {//没有更新头像
                                Log.e("LoginActivity", "update current user nick fail");
                            }

                            DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息
//                            DemoHelper.getInstance().setCurrentUserName(username);
                            Intent intent = new Intent(LoginCommonRegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            ToastUtil.showWarning(getApplicationContext(), "环信登录失败！", Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });
    }
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            dad();
        }
    };
}

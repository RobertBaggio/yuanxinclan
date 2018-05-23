package com.yuanxin.clan.mvp.share;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.mob.MobSDK;
import com.umeng.analytics.MobclickAgent;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.MainActivity;
import com.yuanxin.clan.core.activity.BindingPhoneActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.event.ThirdLoginEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.huanxin.PreferenceManager;
import com.yuanxin.clan.core.huanxin.UserProfileManager;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.JPushUtil;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.jpush.android.api.JPushInterface;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import static com.lzy.imagepicker.ImagePicker.TAG;

/**
 * ProjectName: yuanxinclan
 * Describe: 第三方授权登录操作
 * Author: xjc
 * Date: 2017/6/19 0019 16:30
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class AuthorizeUtil {

    private AuthorizeListener mAuthorizeListener;

    public AuthorizeUtil(AuthorizeListener authorizeListener) {
        mAuthorizeListener = authorizeListener;
    }

    public void auth(final String platformName, AuthorizeListener listener) {
        mAuthorizeListener = listener;
        Log.v("lgq","name...."+platformName);
        ToastUtil.showLong(UIUtils.getContext(), "正在准备授权中，请稍候");
        try {
//            <!-- 填写您从Mob开发者后台中得到的Appkey和AppSecret -->
//        <meta-data android:name="Mob-AppKey" android:value="1ec1fb0830e8f"/>
//        <meta-data android:name="Mob-AppSecret" android:value="3554e2c9da53a7bf6fd5ac5ef878349a"/>
            // 通过代码注册你的AppKey和AppSecret
//            MobSDK.init(UIUtils.getContext(), "1ec1fb0830e8f", "3554e2c9da53a7bf6fd5ac5ef878349a");
//            MobSDK.init(UIUtils.getContext(), "wx0e2ee22e4f6cf2a6", "a3058b4012f514601ea2b1ba6894a72e");
            MobSDK.init(UIUtils.getContext(), "1b4f2089393d6", "428b8c761d0cd45297cd569f45a1ed59");
            Platform platform = ShareSDK.getPlatform(platformName);
            if (platform.isAuthValid()) {
                platform.removeAccount(true);
            }
            platform.setPlatformActionListener(new PlatformActionListener() {

                @Override
                public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                    1、用户触发第三方登录事件
//                    2、showUser(null)请求授权用户的资料（这个过程中可能涉及授权操作）
//                    3、如果onComplete()方法被回调，将其参数Hashmap代入你应用的Login流程
//                    4、否则提示错误，调用removeAccount(true)方法，删除可能的授权缓存数据
//                    5、Login时客户端发送用户资料中的用户ID给服务端
//                    6、服务端判定用户是已注册用户，则引导用户进入系统，否则返回特定错误码
//                    7、客户端收到“未注册用户”错误码以后，代入用户资料到你应用的Register流程
//                    8、Register时在用户资料中挑选你应用的注册所需字段，并提交服务端注册
//                    9、服务端完成用户注册，成功则反馈客户端引导用户进入系统
//                    10、否则提示错误，调用removeAccount(true)方法，删除可能的授权缓存数据
                    //授权成功

                    Logger.e("授权成功");
                    try {
                        //用户资源都保存到res
                        //通过打印res数据看看有哪些数据是你想要的
                        String accessToken = platform.getDb().getToken();//获取授权
                        String gender = platform.getDb().getUserGender();//性别
                        String userIcon = platform.getDb().getUserIcon();
                        String userName = platform.getDb().getUserName();//获取用户昵称
                        String userId = platform.getDb().getUserId();
                        Logger.e(accessToken + " " + gender + " " + userIcon + " " + userName + " " + userId + " " + platform.getDb());
                        String JpushReID = JPushInterface.getRegistrationID(UIUtils.getContext());
//                if (!TextUtil.isEmpty(JpushReID)) {
//                    Login(currentUsername, currentPassword, JpushReID);
//                } else {
//                    initJPush();
//                }
                        ThirdLoginEvent thirdLoginEvent = new ThirdLoginEvent(JpushReID, userId, userName, gender, userIcon);
//                        EventBus.getDefault().post(thirdLoginEvent);
                        LoginWithWechatOrQQ(thirdLoginEvent, UIUtils.getContext());
//                        mAuthorizeListener.onComplete(FastJsonUtils.toJSONString(thirdLoginEvent));
//                        mAuthorizeListener = null;
//                    //部分没有封装的字段可以通过键值获取，例如下面微信的unionid字段
//                    Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
//                    String unionid = wechat.getDb().get("unionid");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Platform platform, int i, Throwable throwable) {
                    //授权失败
                    Logger.e("授权失败" + throwable);
                    mAuthorizeListener.onError();
                    mAuthorizeListener = null;
                    throwable.printStackTrace();
                }

                @Override
                public void onCancel(Platform platform, int i) {
                    //取消授
                    mAuthorizeListener.onCancel();
                    mAuthorizeListener = null;
                }
            });//设置分享事件回调
            platform.SSOSetting(false); //设置false表示使用SSO授权方式
//            platform.authorize();
            platform.showUser(null);//授权并获取用户信息id，如果account为null，则表示获取授权账户自己的资料
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoginWithWechatOrQQ(final ThirdLoginEvent thirdLoginEvent, Context context) {
        RequestParams params = new RequestParams();
        params.put("registrationId", thirdLoginEvent.getRegistrationID());
        params.put("loginDevice", "android_" + JPushUtil.getDeviceId(context));
        params.put("wechatUid", thirdLoginEvent.getUserId());
        SyncHttpClient client = new SyncHttpClient();
        client.post(Url.getWechatLogin, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(UIUtils.getContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        String userIdOne = array.getString("userId");
                        int userId = Integer.valueOf(userIdOne);
                        String role = array.getString("role");//1系统管理员 2 运营管理员3普通用户 4 5
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
                        SharedPreferences sharedPreferences = UIUtils.getContext().getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
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
                        SharedPreferences sharedPreferencesOne = UIUtils.getContext().getSharedPreferences("huanXin", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editorOne = sharedPreferencesOne.edit();//获取编辑器
                        editorOne.putString("userNm", userNm);//环信昵称
                        editorOne.putString("userPhone", userPhone);//电话
                        editorOne.putString("userImage", userImageNeed);//头像
                        editorOne.commit();//提交修改
                        //环信用 结束
                        UserNative.saveIsLogin(true);
                        JPushUtil.initJPush(UIUtils.getContext());
//                        Log.v("lgq","......name="+currentUsername+".....pw="+currentPassword+"...userNm="+userNm+"....ph="+userPhone+"..."+userImageNeed);
                        LoginWenXin(userPhone, userPwd, userNm, userPhone, userImageNeed, true);

                        // UMENG 统计登陆
                        MobclickAgent.onProfileSignIn(String.valueOf(UserNative.getId()));
                    } else {
                        Intent intent = new Intent(UIUtils.getContext(), BindingPhoneActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("third_info", FastJsonUtils.toJSONString(thirdLoginEvent));
                        UIUtils.getContext().startActivity(intent);
                        Toast.makeText(UIUtils.getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void LoginWenXin(final String username, String password, final String userNm, final String userPhone, final String userImageNeed, final Boolean isThirdLogin) {
        final Context context = UIUtils.getContext();
        //开始 环信
        if (!EaseCommonUtils.isNetWorkConnected(context)) {
            Toast.makeText(context, R.string.network_isnot_available, Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(context, R.string.User_name_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isThirdLogin && TextUtils.isEmpty(password)) {
            Toast.makeText(context, R.string.Password_cannot_be_empty, Toast.LENGTH_SHORT).show();
            return;
        }
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
                UserProfileManager userProfileManager = new UserProfileManager();
                userProfileManager.getCurrentUserInfo().setNick(userNm);
                PreferenceManager.getInstance().setCurrentUserNick(userNm);
                userProfileManager.getCurrentUserInfo().setAvatar(userImageNeed);
                PreferenceManager.getInstance().setCurrentUserAvatar(userImageNeed);
                PreferenceManager.getInstance().setCurrentUserName(userPhone);

                DemoHelper.getInstance().setCurrentUserName(userPhone); // 环信Id

//                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息

                Intent intent = new Intent(context, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }

            @Override
            public void onProgress(int progress, String status) {
                Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {

            }
        });
    }

    public static void authWithApi(String platformName, AuthorizeListener listener) {
        LoginApi api = new LoginApi();
        //设置登陆的平台后执行登陆的方法
        api.setPlatform(platformName);
        api.setOnLoginListener(new OnLoginListener() {
            public boolean onLogin(String platform, HashMap<String, Object> res) {
                // 在这个方法填写尝试的代码，返回true表示还不能登录，需要注册
                // 此处全部给回需要注册
                return true;
            }

            public boolean onRegister(UserInfo info) {
                // 填写处理注册信息的代码，返回true表示数据合法，注册页面可以关闭
                return true;
            }
        });
        api.login(UIUtils.getContext());
    }

    public interface AuthorizeListener {
        public void onComplete(String thirdLoginEventString);

        public void onError();

        public void onCancel();
    }

}

package com.yuanxin.clan.core.app;

import android.content.Context;
import android.content.SharedPreferences;

import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.MainApplication;

/**
 * ProjectName: yuanxinclan
 * Describe: 用户资料常量
 * Author: xjc
 * Date: 2017/6/8 0008 14:37
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class UserNative {
    private static final String IS_LOGIN = "IS_LOGIN";
    private static final String Data = "Data";
    private static final String Tel = "Tel";
    private static final String Password = "Password";
    private static final String City = "City";
    private static final String HuanXinUserName = "HuanXinUserName";
    private static final String isHuanXinLoggedIn = "isHuanXinLoggedIn";
    private static final String USER_ID = "USER_ID";
    private static final String ROLE = "role";
    private static final String accountPwd = "accountPwd";
    private static final String acountBalance = "acountBalance";

    private static final String DATABASE_NAME = "user";
    private static SharedPreferences sharedPreferences;
    private static final String userID = "userId";
    private static final String userName = "userNm";
    private static final String userPhone = "userPhone";
    private static final String wxbd = "wxbd";
    private static final String zfbd = "zfbd";
    private static final String aliname = "aliname";
    private static final String aliaccount = "aliaccount";
    private static final String wxname = "wxname";
    private static final String wxaccount = "wxaccount";
    private static final String acountId = "acountId";
    private static final String userPwd = "userPwd";
    private static final String userImage = "userImage";
    private static final String userSex = "userSex";
    private static final String epId = "epId";
    private static final String epNm = "epNm";
    private static final String epRole = "epRole";
    private static final String epPosition = "epPosition";
    private static final String company = "company";
    private static final String expertPosition = "expertPosition";
    private static final String province = "province";
    private static final String city = "city";
    private static final String area = "area";
    private static final String addressId = "addressId";
    private static final String detail = "detail";
    private static final String AESKES = "aesKes";
    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getSharedPreferences() {
        if (null == sharedPreferences) {
            sharedPreferences = MainApplication.getInstance().getSharedPreferences(DATABASE_NAME, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    //用户id
    public static int getId() {
        return getSharedPreferences().getInt(userID, 0);
    }

    //用户名字
    public static String getName() {
        return getSharedPreferences().getString(userName, "");
    }

    //用户手机号
    public static String getPhone() {
        return getSharedPreferences().getString(userPhone, "");
    }
    //用户手机号
    public static String getDetail() {
        if (getSharedPreferences().getString(detail, "").equals("null")|| TextUtil.isEmpty(getSharedPreferences().getString(detail, ""))){
            return "";
        }
        return getSharedPreferences().getString(detail, "");
    }
    //用户手账户ID
    public static String getAccoutid() {
        return getSharedPreferences().getString(acountId, "");
    }

    //用户密码
    public static String getPwd() {
        return getSharedPreferences().getString(userPwd, "");
    }
    //支付绑定
    public static String getWxbd() {
        return getSharedPreferences().getString(wxbd, "");
    }
    public static String getAlibd() {
        return getSharedPreferences().getString(zfbd, "");
    }
    public static String getAliname() {
        return getSharedPreferences().getString(aliname, "");
    }
    public static String getAliaccount() {
        return getSharedPreferences().getString(aliaccount, "");
    }
    public static String getWxname() {
        return getSharedPreferences().getString(wxname, "");
    }
    public static String getWxaccount() {
        return getSharedPreferences().getString(wxaccount, "");
    }

    //用户头像
    public static String getImage() {
        return getSharedPreferences().getString(userImage, "");
    }

    //用户性别
    public static String getSex() {
        return getSharedPreferences().getString(userSex, "");
    }

    //企业id
    public static String getEpId() {
        return getSharedPreferences().getString(epId, "");
    }

    //企业名称
    public static String getEpNm() {
        return getSharedPreferences().getString(epNm, "");
    }

    //企业角色
    public static String getEpRole() {
        return getSharedPreferences().getString(epRole, "");
    }

    //
    public static String getEpPosition() {
        return getSharedPreferences().getString(epPosition, "");
    }

    public static String getAcountBalance() {
        return getSharedPreferences().getString(acountBalance, "");
    }


    //
    public static String getCompany() {
        return getSharedPreferences().getString(company, "");
    }

    //
    public static String getExpertPosition() {
        return getSharedPreferences().getString(expertPosition, "");
    }

    public static String getAccountPwd() {

        return getSharedPreferences().getString(accountPwd, "");
    }

    //地址-省
    public static String getProvince() {
        return getSharedPreferences().getString(province, "");
    }

    //地址-市
    public static String getCity() {
        return getSharedPreferences().getString(city, "");
    }

    //地址-区
    public static String getArea() {
        return getSharedPreferences().getString(area, "");
    }

    //地址id
    public static String getAddressId() {
        return getSharedPreferences().getString(addressId, "");
    }


    public static void clearData() {
        getSharedPreferences().edit().clear().commit();
    }


    public String readData() {
        return getSharedPreferences().getString(Data, null);
    }

    public void savedData(String data) {
        getSharedPreferences().edit().putString(Data, data).commit();
    }

    public String getTel() {
        return getSharedPreferences().getString(Tel, null);
    }

    public void savedTel(String tel) {
        getSharedPreferences().edit().putString(Tel, tel);
    }

    public String getPassword() {
        return getSharedPreferences().getString(Password, null);
    }

    public void savedPassword(String password) {
        getSharedPreferences().edit().putString(Password, password);
    }

    /**
     * set current username
     *
     * @param username
     */
    //环信 用户id
    public void SavedCurrentHuanXinUserName(String username) {
        getSharedPreferences().edit().putString(HuanXinUserName, username);
    }

    /**
     * get current user's id
     */
    //微信是否已登录
    public String getCurrentHuanXinUsernName() {
        return getSharedPreferences().getString(HuanXinUserName, null);
    }

    //是否登录
    public boolean getHuanXinLoggedIn() {
        return getSharedPreferences().getBoolean(isHuanXinLoggedIn, false);

    }

    public void savedHuanXinLoggedIn(boolean is_login) {
        getSharedPreferences().edit().putBoolean(isHuanXinLoggedIn, is_login).commit();
    }

    public int readUserId() {
        return getSharedPreferences().getInt(USER_ID, 0);
    }

    public void saveUserId(int user_id) {
        getSharedPreferences().edit().putInt(USER_ID, user_id).commit();
    }

    public static String readRole() {
        return getSharedPreferences().getString(ROLE, "");
    }

    public void saveRole(String role) {
        getSharedPreferences().edit().putString(ROLE, role).commit();
    }

    /**
     * 是否登录
     *
     * @return
     */
    public static boolean readIsLogin() {
        return getSharedPreferences().getBoolean(IS_LOGIN, false);
    }

    public static void saveIsLogin(boolean is_login) {
        getSharedPreferences().edit().putBoolean(IS_LOGIN, is_login).commit();
    }

    public static String getAesKes() {
        return getSharedPreferences().getString(AESKES, "");
    }

    public static void setAesKes(String aesKes) {
        getSharedPreferences().edit().putString(AESKES, aesKes).commit();
    }

}

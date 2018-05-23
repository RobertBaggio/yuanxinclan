package com.yuanxin.clan.core.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.yuanxin.clan.mvp.MainApplication;

/**
 * Created by meixi on 2016/11/26. 16:16
 * 邮箱 1085220040@qq.com
 */
public class MyShareUtil {

    private static SharedPreferences sp;
    private static Context mContext;
    // preferences.edit().clear().commit();  清空数据
    private static void initContext() {
        if (mContext == null){
            mContext = MainApplication.getTotalContext();
            sp= mContext.getSharedPreferences("lg", 0);
        }

    }

    public static int  getSharedInt(String key){
        initContext();
        int re = 0;
        re = sp.getInt(key, -1);
        return re;
    }

    public static int getSharedInt(int nResID) {
        initContext();
        return sp.getInt(mContext.getString(nResID), 0);

    }

    public static String getSharedString(int nResID) {
        initContext();
        return sp.getString(mContext.getString(nResID), "");
    }


    public static String getSharedString(String key){
        initContext();
        String s = sp.getString(key,"");
        return s;
    }

    public static long getSharedlong(String key){
        initContext();
        long s = sp.getLong(key, 0);
        return s;
    }
    public static long getSharedlong(int nResID){
        initContext();
        long s = sp.getLong(mContext.getString(nResID), 0);
        return s;
    }

    public static void sharedPint(String key,int value){
        initContext();
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static void sharedPint(int nResID, int content) {
        initContext();
        sp.edit().putInt(mContext.getString(nResID), content).commit();
    }

    public static void sharedPstring(int nResID, String strContent) {
        initContext();
        if (TextUtils.isEmpty(strContent))
            strContent = "";
        sp.edit().putString(mContext.getString(nResID), strContent).commit();
    }

    public static void editLong(int nResID, long content) {
        initContext();
        sp.edit().putLong(mContext.getString(nResID), content).commit();
    }

    public static void editLong(String strKey, long content) {
        initContext();
        sp.edit().putLong(strKey, content).commit();
    }


    public static void sharedPstring(String key,String value){
        initContext();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key,value);
        editor.commit();
    }
    public static void deleP(){
        initContext();
        sp = mContext.getSharedPreferences("lg",0);
        sp.edit().clear().commit();
    }
}

package com.yuanxin.clan.core.weixin;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

import com.yuanxin.clan.mvp.MainApplication;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/6 0006 11:28
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ToastUtil {

    private static Toast longToast;
    private static Toast shortToast;
    private static Context mContext;

    private static void initContext() {
        if (mContext == null)
            mContext = MainApplication.getTotalContext();
    }

    public static void showToastEditEmpty(int strId) {
        initContext();
//        showToastShort(mContext.getString(R.string.error_edit_empty, new Object[]{mContext.getString(strId)}));
    }

    public static void showToastSelectEmpty(int strId) {
        initContext();
//        showToastShort(mContext.getString(R.string.error_select_empty, new Object[]{mContext.getString(strId)}));
    }

    public static void showToastShort(int strId) {
        initContext();
        if (shortToast != null)
            shortToast.cancel();
        if (strId != 0) {
            shortToast = Toast.makeText(mContext, strId, Toast.LENGTH_SHORT);
            shortToast.setGravity(Gravity.TOP, 0, 100);
            shortToast.show();
        }
    }

    public static void showToastShort(String str) {
        initContext();
        if (shortToast != null)
            shortToast.cancel();
        if (!TextUtils.isEmpty(str)) {
            shortToast = Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
            shortToast.setGravity(Gravity.TOP, 0, 100);
            shortToast.show();
        }
    }

    public static void showToastLong(int strId) {
        initContext();
        if (longToast != null)
            longToast.cancel();
        if (strId != 0) {
            longToast = Toast.makeText(mContext, strId, Toast.LENGTH_LONG);
            longToast.setGravity(Gravity.TOP, 0, 100);
            longToast.show();
        }
    }

    public static void showToastLong(String str) {
        initContext();
        if (longToast != null)
            longToast.cancel();
        if (!TextUtils.isEmpty(str)) {
            longToast = Toast.makeText(mContext, str, Toast.LENGTH_LONG);
            longToast.setGravity(Gravity.TOP, 0, 100);
            longToast.show();
        }
    }

}

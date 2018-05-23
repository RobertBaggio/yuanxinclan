package com.yuanxin.clan.mvp.utils;

import android.content.Context;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

/**
 * @author lch
 *         date 2015/9/15.
 */
public class ToastUtil {

    public static void showShort(Context context, String message) {
        showToast(context, message, Toast.LENGTH_SHORT);
    }

    public static void showLong(Context context, String message) {
        showToast(context, message, Toast.LENGTH_LONG);
    }

    public static void showShort(String message) {
        showToast(UIUtils.getContext(), message, Toast.LENGTH_SHORT);
    }

    public static void showLong(String message) {
        showToast(UIUtils.getContext(), message, Toast.LENGTH_LONG);
    }

    public static void showInfo(Context context, String message, int duration) {
        Toasty.info(context, message, duration, true).show();
    }

    public static void showWarning(Context context, String message, int duration) {
        Toasty.warning(context, message, duration, true).show();
    }

    public static void showSuccess(Context context, String message, int duration) {
        Toasty.success(context, message, duration, true).show();
    }

    public static void showError(Context context, String message, int duration) {
        Toasty.error(context, message, duration, true).show();
    }

    private static void showToast(Context context, String message, int type) {
        Toast.makeText(context, message, type).show();
    }

}

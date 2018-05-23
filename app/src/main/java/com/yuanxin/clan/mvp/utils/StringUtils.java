package com.yuanxin.clan.mvp.utils;

import android.text.TextUtils;

/**
 * @author lch
 *         date 2016/6/21.
 */
public class StringUtils {

    public static String formatPhoneNumber(String phoneNo, int start, int replaceLength) {
        if (TextUtils.isEmpty(phoneNo) || phoneNo.length() < (start + replaceLength)) {
            return phoneNo;
        }

        StringBuilder stringBuilder = new StringBuilder(phoneNo);
        return stringBuilder.replace(start, start + replaceLength, "****").toString();
    }
}

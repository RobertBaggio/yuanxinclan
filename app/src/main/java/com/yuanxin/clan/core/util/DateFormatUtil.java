package com.yuanxin.clan.core.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.yuanxin.clan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/19 0019 16:03
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class DateFormatUtil {

    public final static String yyyyMMdd_dot = "yyyy.MM.dd";
    public final static String yyyyMMdd = "yyyy-MM-dd";
    public final static String yyyyMMdd_HHmm_dot = "yyyy.MM.dd HH:mm";
    public final static String yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss";
    public final static String Custom = "yyyy年MM月dd日 HH:mm";

    private static String month[] = {"一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"};

    @SuppressLint("SimpleDateFormat")
    public static String ms2String(int ms, String strFormat) {
        String result = "";
        if (ms == -1) {
            return "";
        }
        Date date = new Date(((long) ms) * 1000);
        SimpleDateFormat sdf = new SimpleDateFormat(strFormat);
        result = sdf.format(date);
        return result;
    }

    public static int getAgeFromTime(int ms) {
        if (ms == 0)
            return 0;
        Date date = new Date(((long) ms) * 1000);
        Calendar caNow = Calendar.getInstance();
        Calendar caData = Calendar.getInstance();
        caData.setTime(date);
        int nYearDef = caNow.get(Calendar.YEAR) - caData.get(Calendar.YEAR);
        return nYearDef > 0 ? nYearDef : 0;
    }

    public static String getDataByLateDay(Context context, int nDay) {
        if (context == null)
            return "";
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, nDay);
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(calendar.get(Calendar.YEAR))).append(context.getResources().getString(R.string.year))
                .append(String.valueOf(calendar.get(Calendar.MONTH) + 1)).append(context.getResources().getString(R.string.month))
                .append(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))).append(context.getResources().getString(R.string.day));
        return sb.toString();
    }

    @SuppressLint("SimpleDateFormat")
    public static String fromTheTimeNow(int refreshTime) {
        int thisTime = (int) (System.currentTimeMillis() / 1000);
        return fromTheTimeNow(thisTime, refreshTime);
    }

//    @SuppressLint("SimpleDateFormat")
//    public static String fromTheTimeNow(long lThisTime, long lRefreshTime) {
//        int thisTime = (int) (lThisTime);
//        int refreshTime = (int) (lRefreshTime / 1000);
//        return fromTheTimeNow(thisTime, refreshTime);
//    }

    @SuppressLint("SimpleDateFormat")
    public static String fromTheTimeNow(int thisTime, int refreshTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(Long.parseLong(String.valueOf(refreshTime)) * 1000));

        Calendar now = Calendar.getInstance();
        now.setTime(new Date(Long.parseLong(String.valueOf(thisTime)) * 1000));

        int nDayDef = now.get(Calendar.DATE) - date.get(Calendar.DATE);
        int nHoursDef = now.get(Calendar.HOUR_OF_DAY) - date.get(Calendar.HOUR_OF_DAY);
        int nMinDef = now.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);

        if (now.get(Calendar.YEAR) - date.get(Calendar.YEAR) != 0)
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date.getTime());
        else if (nDayDef != 0) {
            if (nDayDef > 0 && nDayDef < 4) {
                return nDayDef + "天前";
            } else
                return new SimpleDateFormat("MM-dd HH:mm").format(date.getTime());
        } else if (nHoursDef != 0) {
            if (nHoursDef > 0)
                return nHoursDef + "小时前";
            else
                return -nHoursDef + "小时后";
        } else if (nMinDef != 0) {
            if (nMinDef > 0)
                return nMinDef + "分钟前";
            else
                return -nMinDef + "分钟后";
        } else {
            return "刚刚";
        }
    }

    @SuppressLint("SimpleDateFormat")
    public static void setViewFromTheTimeNow(int thisTime, int refreshTime, TextView tvDay, TextView tvMonth, TextView tvYear) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(Long.parseLong(String.valueOf(refreshTime)) * 1000));

        Calendar now = Calendar.getInstance();
        now.setTime(new Date(Long.parseLong(String.valueOf(thisTime)) * 1000));

        int nDayDef = now.get(Calendar.DATE) - date.get(Calendar.DATE);
        int nHoursDef = now.get(Calendar.HOUR_OF_DAY) - date.get(Calendar.HOUR_OF_DAY);
        int nMinDef = now.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);

        if (now.get(Calendar.YEAR) - date.get(Calendar.YEAR) != 0) {
            setViewTime(new SimpleDateFormat("dd").format(date.getTime()), month[date.get(Calendar.MONTH)], new SimpleDateFormat("yyyy").format(date.getTime()), tvDay, tvMonth, tvYear);
        } else {
            if (nDayDef != 0) {
                if (nDayDef > 0 && nDayDef < 2) {
                    setViewTime("昨天", null, null, tvDay, tvMonth, tvYear);
                } else {
                    setViewTime(new SimpleDateFormat("dd").format(date.getTime()), month[date.get(Calendar.MONTH)], null, tvDay, tvMonth, tvYear);
                }
            } else {
                setViewTime("今天", null, null, tvDay, tvMonth, tvYear);
            }
        }
    }


    /**
     * 设View
     *
     * @param strDay
     * @param strMonth
     * @param strYear
     * @param tvDay
     * @param tvMonth
     * @param tvYear
     */
    private static void setViewTime(String strDay, String strMonth, String strYear, TextView tvDay, TextView tvMonth, TextView tvYear) {
        if (TextUtils.isEmpty(strMonth)) {
            tvMonth.setVisibility(View.GONE);
        } else {
            tvMonth.setVisibility(View.VISIBLE);
            tvMonth.setText(strMonth);
        }
        if (TextUtils.isEmpty(strYear)) {
            tvYear.setVisibility(View.GONE);
        } else {
            tvYear.setVisibility(View.VISIBLE);
            tvYear.setText(strYear);
        }
        tvDay.setText(strDay);
    }

    public static String apartDays(int endTime, int startTime) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(new Date(
                Long.parseLong(String.valueOf(startTime)) * 1000));

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(new Date(Long.parseLong(String.valueOf(endTime)) * 1000));

        int apartDays = endDate.get(Calendar.DATE)
                - startDate.get(Calendar.DATE);

        return String.valueOf(apartDays + 1);
    }

    // 格式化推送信息时间
    @SuppressLint("SimpleDateFormat")
    public static String pushTimeFromNow(int thisTime) {
        Calendar now = Calendar.getInstance();

        Calendar date = Calendar.getInstance();
        date.setTime(new Date(Long.parseLong(String.valueOf(thisTime)) * 1000));

        int nDayDef = now.get(Calendar.DATE) - date.get(Calendar.DATE);
        int nHoursDef = now.get(Calendar.HOUR_OF_DAY)
                - date.get(Calendar.HOUR_OF_DAY);
        int nMinDef = now.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);

        if (now.get(Calendar.YEAR) - date.get(Calendar.YEAR) != 0
                || now.get(Calendar.MONTH) - date.get(Calendar.MONTH) != 0
                || nDayDef >= 1)
            return new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
        else if (nHoursDef != 0) {
            return new SimpleDateFormat("HH:mm").format(date.getTime());
        } else if (nMinDef != 0) {
            if (nMinDef > 0)
                return nMinDef + "分钟前";
            else
                return -nMinDef + "分钟后";
        } else {
            return "刚刚";
        }
    }

    public static String excludingTime(String yyyyMMdd_HHmmss) {
        try {

            String[] str = yyyyMMdd_HHmmss.split(" ");
            if (str != null && str.length > 0) {
                return str[0];
            } else {
                return yyyyMMdd_HHmmss;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return yyyyMMdd_HHmmss;
        }
    }

}

package com.yuanxin.clan.core.util;

import android.annotation.SuppressLint;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Tenny on 2016/10/12.
 */
public class TimeUtil {

    //如果是今天就显示时间，否则就显示日期
    public static String timeFormat(String time) {
        String newFormat = null;
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        Date date = new Date();
        String timeString = dataFormat.format(date);
        String[] timeArray = time.split(" ");
        if (timeString.equals(timeArray[0])) {
            String[] time1 = timeArray[1].split(":");
            newFormat = time1[0] + ":" + time1[1];
            return newFormat;
        } else {
            String[] time0 = timeArray[0].split("-");
            newFormat = time0[1] + "-" + time0[2];
            return newFormat;
        }

    }

    @SuppressLint("SimpleDateFormat")
    public static String fromTheTimeNow(String refreshTime)throws ParseException {
        int thisTime = (int) (System.currentTimeMillis() / 1000);
        String wbstringtiem =dateToStamp(refreshTime);
        int a = Integer.parseInt(wbstringtiem);
        Log.v("lgq","shijianchu==="+a+"....."+thisTime);
        return fromTheTimeNow(thisTime, a);
    }

    @SuppressLint("SimpleDateFormat")
    public static String fromTheTimeNow(int thisTime, int refreshTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(new Date(Long.parseLong(String.valueOf(refreshTime)) * 1000));

        Calendar now = Calendar.getInstance();
        now.setTime(new Date(Long.parseLong(String.valueOf(thisTime)) * 1000));

        int nDayDef = now.get(Calendar.DATE) - date.get(Calendar.DATE);
        int day = nDayDef / (24 * 60 * 60 * 1000);
        int hour = (nDayDef / (60 * 60 * 1000) - day * 24);
        int min = ((nDayDef / (60 * 1000)) - day * 24 * 60 - hour * 60);
        int sec = (nDayDef/1000-day*24*60*60-hour*60*60-min*60);
        Log.v("lgq","shi=="+hour+"feng===="+min+"tian==="+day);
        if (day==1){
            return "昨天 "+ new SimpleDateFormat("MM-dd HH:mm").format(date.getTime());
        }if (hour<1){
            return  min+" 分钟前";
        }if (hour>1&&hour<4){
            return  hour+" 小时前";
        }

        int nHoursDef = now.get(Calendar.HOUR_OF_DAY) - date.get(Calendar.HOUR_OF_DAY);
        int nMinDef = now.get(Calendar.MINUTE) - date.get(Calendar.MINUTE);
        Log.v("Lgq","jinian=="+now.get(Calendar.HOUR_OF_DAY)+"....nanian=="+date.get(Calendar.HOUR_OF_DAY));
        if (now.get(Calendar.YEAR) - date.get(Calendar.YEAR) != 0)
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(date.getTime());
        else if (nDayDef != 0) {
            if (nDayDef > 0 && nDayDef < 4) {
                return nDayDef + "天前";
            } else{

                return new SimpleDateFormat("MM-dd HH:mm").format(date.getTime());
            }


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


//            try{
//
//    }catch(Exception e){
//        e.printStackTrace();
//    }
    /*
       * 将时间转换为时间戳
       */
public static String dateToStamp(String s) throws ParseException {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = simpleDateFormat.parse(s);
    long ts = date.getTime();
    res = String.valueOf(ts);
    return res;
}
    //年
    public static String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    //年龄
    public static int getAge(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        Date today = new Date();
        int age = Integer.parseInt(format.format(today)) - Integer.parseInt(format.format(date));
        return age;
    }

    /*
    * 将时间戳转换为时间
    */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}

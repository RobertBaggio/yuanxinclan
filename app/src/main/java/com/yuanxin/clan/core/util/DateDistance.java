package com.yuanxin.clan.core.util;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateDistance {

    /**
     * 时间工具
     */
    public static long getDistanceDays(String str1, String str2) throws Exception{
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date one;
        Date two;
        long days=0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    public static String timetodate(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(time));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        String date = sf.format(calendar.getTime());
        return date;

    }

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return long[] 返回值为：{天, 时, 分, 秒}
     */
    public static long getTimesChaLong(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            diff = time1 - time2;
            day = diff / (24 * 60 * 60 * 1000);
            Log.v("lgq","........ "+day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return day;
    }
    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        String myhour="";
        String mymin="";
        int mymonth=0;
        int myday=0;
        int twoday=0;
        try {

            final Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
            String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
            twoday = Integer.parseInt(mDay);

            one = df.parse(str1);
            c.setTime(one);
//            Log.v("Lgq","ddd====="+str1);
            two = df.parse(str2);
            myhour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
            if (myhour.length()==1){
                myhour="0"+myhour;
            }
            mymin = String.valueOf(c.get(Calendar.MINUTE));
            if (mymin.equals("0")){
                mymin="00";
            }
            mymonth = c.get(Calendar.MONTH)+1;
            myday = c.get(Calendar.DAY_OF_MONTH);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            diff = time2 - time1;

            day = diff / (24 * 60 * 60 * 1000);
//            Log.v("lgq","tian--==="+day+"...one="+str1+"..-----.two==="+str2);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (twoday-myday==0&&hour==0){
            if (min == 0) {
                return  "刚刚";
            } else {
                return  min + "分钟前";
            }
        }else if (twoday-myday==0&&0<hour&&hour<=2){
            return  "2小时前";
        }else if (twoday-myday==0&&hour>2){
            return myhour+":"+mymin;
        } else if (twoday-myday==1){
            return "昨天 "+myhour+":"+mymin;
        }else if (twoday-myday>1){
            return mymonth + "-" + myday + " " + myhour + ":" + mymin ;
        }
        return mymonth + "-" + myday + " " + myhour + ":" + mymin ;
    }
    public static String getDistanceTimeToZW(String str1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String typestring = "yyyy年MM月dd日";
        String endtime="";
        Date one;
        try {
            one = df.parse(str1);
            endtime = dateToString(one,typestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endtime;
    }

    public static String getDistanceTimeToHM(String str1) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String typestring = "yyyy-MM-dd HH:mm";
        String endtime="";
        Date one;
        try {
            one = df.parse(str1);
            endtime = dateToString(one,typestring);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return endtime;
    }

    public static String dateToString(Date data, String formatType) {
        return new SimpleDateFormat(formatType).format(data);
    }


    public static String timetodatedetail(String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(time));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy/MM/dd   HH:mm:ss");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式

        String date = sf.format(calendar.getTime());
        return date;

    }
    public static String yuyuetimetodate(String time) {
        String dateen="";
        try {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//小写的mm表示的是分钟
            java.util.Date date=sdf.parse(time);
            calendar.setTime(date);
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd  HH:mm");//这里的格式可换"yyyy年-MM月dd日-HH时mm分ss秒"等等格式

            dateen= sf.format(calendar.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
        return dateen;


    }

}

package com.hyphenate.easeui.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * ProjectName: new_yuanxin
 * 环信时间工具
 */

public class EaseTimeUtil {

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

    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分xx秒
     */
    public static String getDistanceTime(String str1, String str2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        int myhour=0;
        int mymin=0;
        int mymonth=0;
        int myday=0;
        int twoday=0;
        try {
            final Calendar c = Calendar.getInstance();
            c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));//获取当前时间的年月日
            String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH));// 获取当前月份的日期号码
            twoday = Integer.parseInt(mDay);

            one = df.parse(str1);
            c.setTime(one);//获取Date类型的年月日
            myhour = c.get(Calendar.HOUR_OF_DAY);//时
            mymin = c.get(Calendar.MINUTE);//分
            mymonth = c.get(Calendar.MONTH)+1;//月
            myday = c.get(Calendar.DAY_OF_MONTH);//天
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        Log.v("lgq","....."+myhour+"............."+mymin+"........."+twoday+"........."+myday);
        if (twoday-myday==0){
            if (myhour<=12){
                return  "早上 " + myhour+":"+mymin;
            }else if (myhour<=18&&myhour>12){
                return  "下午 " + myhour+":"+mymin;
            }else if (myhour<=24&&myhour>18){
                return  "晚上 " + myhour+":"+mymin;
            }
        }else if (twoday-myday==1){
            if (myhour<=12){
                return   "昨天 "+"早上 " + myhour+":"+mymin;
            }else if (myhour<=19&&myhour>12){
                return  "昨天 "+"下午 " + myhour+":"+mymin;
            }else if (myhour<=24&&myhour>19){
                return  "昨天 "+"晚上 " + myhour+":"+mymin;
            }
        }else if (twoday-myday>1) {
            if (myhour <= 12) {
                return mymonth + "月" + myday + "日 ";
            } else if (myhour <= 19 && myhour > 12) {
                return mymonth + "月" + myday + "日 ";
            } else if (myhour <= 24 && myhour > 19) {
                return mymonth + "月" + myday + "日 ";
            }
        }else {
            return mymonth + "月" + myday + "日 "+myhour+":"+mymin;
        }

        return myday + "天" + myhour + "小时" + mymin + "分";
    }

}

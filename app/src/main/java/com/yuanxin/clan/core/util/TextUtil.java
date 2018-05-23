package com.yuanxin.clan.core.util;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class TextUtil {

    public static boolean isEmpty(String str) {

        return TextUtils.isEmpty(str) || "null".equals(str);
    }

    //判断是否有表情
    public static boolean isEmojiCharacter(char codePoint) {
        return !(((codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && codePoint <= 0xD7FF)) || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF)));
    }

    // 设置下划线
    public static TextView setBelowLine(TextView textView) {
        textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); // 下划线
        return textView;
    }

    // 设置斜体字
    public static SpannableString setSpanWord(String word) {
        SpannableString sp = new SpannableString(word);
        // 设置斜体
        sp.setSpan(new StyleSpan(android.graphics.Typeface.BOLD_ITALIC), 0,
                word.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        return sp;
    }

    // 设置字体颜色
    public static SpannableStringBuilder setStringColor(String word,
                                                        String changePart, String color) {
        char[] c = changePart.toCharArray();
        int lastIndex = c.length - 1;
        int start = word.indexOf(c[0]);
        int end = start + lastIndex + 1;
        SpannableStringBuilder style = new SpannableStringBuilder(word);
        style.setSpan(new ForegroundColorSpan(Color.parseColor(color)), start,
                end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return style;
    }

    /**
     * 格式化要显示的字符串，做非空判断
     * 该方法主要做用在ui显示这一块，用于更好地显示字符，防止null字符出现和空指针
     *
     * @param str 要验证的字符串
     * @return 参数若为空或“”或null字符串，则返回空，反之直接返回原有值
     */
    public static String formatString(String str) {
        if (TextUtils.isEmpty(str))
            return null;
        if ("null".equalsIgnoreCase(str))
            return null;
        return str;
    }
    /**
     * 去掉url中的路径，留下请求参数部分
     * @param strURL url地址
     * @return url请求参数部分
     */
    private static String TruncateUrlPage(String strURL)
    {
        String strAllParam=null;
        String[] arrSplit=null;

        strURL=strURL.trim();

        arrSplit=strURL.split("[?]");
        if(strURL.length()>1)
        {
            if(arrSplit.length>1)
            {
                if(arrSplit[1]!=null)
                {
                    strAllParam=arrSplit[1];
                }
            }
        }

        return strAllParam;
    }
    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     * @param URL  url地址
     * @return  url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL)
    {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit=null;

        String strUrlParam=TruncateUrlPage(URL);
        if(strUrlParam==null)
        {
            return mapRequest;
        }
        //每个键值为一组 www.2cto.com
        arrSplit=strUrlParam.split("[&]");
        for(String strSplit:arrSplit)
        {
            String[] arrSplitEqual=null;
            arrSplitEqual= strSplit.split("[=]");

            //解析出键值
            if(arrSplitEqual.length>1)
            {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            }
            else
            {
                if(arrSplitEqual[0]!="")
                {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }
}

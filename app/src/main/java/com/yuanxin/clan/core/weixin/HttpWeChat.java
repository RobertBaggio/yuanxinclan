package com.yuanxin.clan.core.weixin;

import android.content.Context;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/6 0006 11:22
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class HttpWeChat {
    private final static int TIME_OUT = 60000 / 4;// 超时时间

    private static final String strDomain = "https://api.weixin.qq.com/sns/";
    private static final String strUserInfoUrl = "oauth2/access_token";


    public static String sendRequest(Context mContext, String code) {

        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("appid", Constants.APP_ID);
        treeMap.put("secret", Constants.APP_SECRET);
        treeMap.put("code",code);
        treeMap.put("grant_type","authorization_code");

        StringBuffer sb = new StringBuffer();
        sb.append(strDomain);
        sb.append(strUserInfoUrl);
        sb.append("?").append(mixParams(treeMap));
        if (mContext != null)
            return submitHttpGetRequests(sb.toString());
        else
            return null;
    }

    private static String submitHttpGetRequests(String strTotalUrl) {
        HttpGet httpRequest = new HttpGet(strTotalUrl);
        String out = "网络异常";
        try {
          /*发送请求并等待响应*/
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequest);
          /*若状态码为200 ok*/
            if (httpResponse.getStatusLine().getStatusCode() == 200)
                out = EntityUtils.toString(httpResponse.getEntity());
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out;
    }

    public static String mixParams(TreeMap<String, String> treeMap) {
        if (treeMap == null)
            return "";

        StringBuilder sb = new StringBuilder();
        Iterator it = treeMap.entrySet().iterator();
        while (it.hasNext()) {
            // entry的输出结果如key0=value0等
            Map.Entry entry = (Map.Entry) it.next();
            Object key = entry.getKey();
            Object value = entry.getValue();
            sb.append(key).append("=").append(value).append("&");
        }
        String out = sb.toString();
        out = out.substring(0, out.length() - 1);
        return out;
    }

}

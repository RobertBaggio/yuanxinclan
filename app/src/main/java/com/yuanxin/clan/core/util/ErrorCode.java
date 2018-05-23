package com.yuanxin.clan.core.util;

/**
 * Created by Administrator on 2017/10/25 0025.
 */

public class ErrorCode {

    public static final int NO_ERROR = 0;	//无错误
    /**
     * 网络错误 -1000 ~  -1999
     *
     * **/
    public static final int UNKNOWN_SERVER_ERROR =	-1000;	//无法识别服务器返回值
    public static final int NONETWORK_ERROR = -1001; //	网络不可用
    public static final int UNABLE_CONNECT_TO_SERVER = -1003;	//无法连接到服务器

    /**
     * 页面、数据错误 -2000 ~ -2999
     *
     * **/
    public static final int INVALID_PHONE = -2001;	//无效的手机号（注册时会出现）
    public static final int USER_ALREADY_EXISTS = -2002; //当前用户已存在（注册时会出现）
    public static final int INVALID_PASSWORD = -2003; //密码不符合规则（注册时会出现）
    public static final int SMS_UNREACH = -2004; //无法获取短信验证码（注册时会出现）
    public static final int LOGIN_USER_NOT_EXIST = -2005; //用户名不存在
    public static final int LOGIN_INVALID_PASSWORD = -2006; // 登陆密码错误
    public static final int JSON_PARSE_EXCEPTION = -2007;  //数据解析错误
    public static final int INVALID_FILE = -2008;  //无效文件异常，一般文件为0字节时为无效（录制音频，在没有权限的时候会为0）
    public static final int FILE_NOT_FOUND = -2009; //文件不存在异常

    /**
     * 其他错误 -3000 ~ -3999
     *
     */
    public static final int UNAUTHORIZED = -3000; //没有权限
    public static final int MESSAGE_SEND_INVALID_CONTENT = -3001; //用户发了被禁止的内容
}

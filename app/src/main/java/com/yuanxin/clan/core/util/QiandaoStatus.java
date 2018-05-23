package com.yuanxin.clan.core.util;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class QiandaoStatus {
    /**
     * 商圈活动扫码接口
     */
    private static final String ACTION = "businessActivitySign/scanCode";
    /**
     * 商圈活动跳转
     */
    private static final String ACTION_CODE = "businessActivitySign/code";
    /**
     * 商圈活动跳转页面
     */
    private static final String ACTION_REDIRECT = "wechatweb/code-success";
    /**
     * 签到页面
     */
    public static final String SIGN = "wechatweb/attendance";
    /**
     * 重复签到
     */
    public static final String SIGN_REPEAT = "wechatweb/sign-repeat";
    public static final String SIGN_REPEAT_INFO = "重复签到";
    /**
     * 签到时间未到
     */
    public static final String SIGN_TIME_NOT_ARRIVED = "wechatweb/sign-time-not-arrived";
    public static final String SIGN_TIME_NOT_ARRIVED_INFO = "签到时间未到";
    /**
     * 签退页面
     */
    public static final String SIGN_OUT = "wechatweb/sign-out";
    /**
     * 绑定页面
     */
    public static final String BINDING = "wechatweb/fill-form";
    /**
     * 活动失效页面
     */
    public static final String INVALID = "wechatweb/invalid";
    public static final String INVALID_INFO = "活动失效";
    /**
     * 商圈活动抽奖
     */
    public static final String ACTION_DRAW_GAME = "";
    /**
     * 商圈活动抽奖扫码签到
     */
    public static final String ACTION_DRAW = "business/draw/scanCodeSign";
    /**
     * 不是商圈人员
     */
    public static final String SIGN_NOT_BUSINESS = "wechatweb/sign_not_business";
    public static final String SIGN_NOT_BUSINESS_INFO = "非商圈成员";
}

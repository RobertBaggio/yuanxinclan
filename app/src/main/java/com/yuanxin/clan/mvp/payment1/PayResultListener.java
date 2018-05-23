package com.yuanxin.clan.mvp.payment1;

/**
 * ProjectName: yuanxinclan
 * Describe: 支付响应
 * Author: xjc
 * Date: 2017/6/16 0016 15:29
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public interface PayResultListener {
    /**
     * 支付成功完成
     */
    void onSuccess();

    /**
     * 支付遇到错误失败
     */
    void onFail();

    /**
     * 支付已完成，服务器正在处理当中
     */
    void onProcess();
}

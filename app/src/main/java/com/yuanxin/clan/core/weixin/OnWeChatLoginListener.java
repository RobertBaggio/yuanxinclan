package com.yuanxin.clan.core.weixin;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/6 0006 11:18
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public interface OnWeChatLoginListener {
    void onSuccess(String code,String token);
}

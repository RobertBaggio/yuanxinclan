package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 会话变更
 * Author: xjc
 * Date: 2017/6/8 0008 14:37
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class BuyerMsgEvent extends NotificationEvent{
    public BuyerMsgEvent(String at, String ep, int cd) {
        super(at, ep, cd);
    }
}

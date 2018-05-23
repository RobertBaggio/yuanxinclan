package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 聊天组信息变更
 * Author: xjc
 * Date: 2017/6/8 0008 10:44
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ChatGroupEvent {
    /**
     * 接收到群组加入邀请
     */
    public static final int user_invite_you_to_join_group = 0;
    /**
     * 群组邀请被同意
     */
    public static final int user_accept_your_invitation = 1;
    /**
     * 群组邀请被拒绝
     */
    public static final int user_declined_your_invitation = 2;
    /**
     * 用户从组中删除
     */
    public static final int user_is_removed_from_group = 3;
    /**
     * 组解散
     */
    public static final int group_is_dismissed = 4;
    /**
     * 用户申请加入群
     */
    public static final int user_apply_to_join_group = 5;
    /**
     * 加群申请被同意
     */
    public static final int your_application_was_accepted = 6;
    /**
     * 接收邀请时自动加入到群组的通知
     */
    public static final int got_an_invitation = 7;

    public ChatGroupEvent() {
    }

    public ChatGroupEvent(int typeValue) {
        this.type = typeValue;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;
}

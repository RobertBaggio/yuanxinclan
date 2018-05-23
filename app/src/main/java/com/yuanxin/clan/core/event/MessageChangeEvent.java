package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 聊天消息变更
 * Author: xjc
 * Date: 2017/6/8 0008 10:44
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class MessageChangeEvent {

    private int count;
    private String message;

    public MessageChangeEvent(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public MessageChangeEvent() {
    }

    public MessageChangeEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 基本通知时间
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class NotificationEvent {
    private String action;
    private String extraParam;
    private int code;

    public NotificationEvent(String at, String ep, int cd) {
        this.action = at;
        this.extraParam = ep;
        this.code = cd;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExtraParam() {
        return extraParam;
    }

    public void setExtraParam(String extraParam) {
        this.extraParam = extraParam;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

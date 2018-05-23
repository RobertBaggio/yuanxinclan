package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 会话变更
 * Author: xjc
 * Date: 2017/6/8 0008 14:37
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class LoginEvent {
    private String registrationID;

    public LoginEvent() {
    }

    public LoginEvent(String rID) {
        registrationID = rID;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }
}

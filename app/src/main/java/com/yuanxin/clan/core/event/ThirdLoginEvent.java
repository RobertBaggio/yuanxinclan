package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 第三方登陆
 * Author: xjc
 * Date: 2017/6/8 0008 14:37
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ThirdLoginEvent {
    private String registrationID;
    private String userId;
    private String userName;
    private String userIcon;
    private String gender;

    public ThirdLoginEvent() {
    }

    public ThirdLoginEvent(String rID, String userId, String userName, String gender, String userIcon) {
        registrationID = rID;
        this.userId = userId;
        this.userName = userName;
        this.gender =gender;
        this.userIcon = userIcon;
    }

    public String getRegistrationID() {
        return registrationID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}

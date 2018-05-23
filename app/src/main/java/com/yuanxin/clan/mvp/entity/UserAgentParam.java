package com.yuanxin.clan.mvp.entity;

/**
 * Created by Administrator on 2017/7/13 0013.
 */

public class UserAgentParam {
    private String flg;
    private int userId;
    private String userName;
    private String userPhone;
    private String userPwd;
    private String epId;
    private String userPhoto;
    private String key;
    private String serviceArea;
    public UserAgentParam(String flg, int userId, String userName, String userPhone, String userPwd, String epId, String userPhoto, String aeskes,String serviceAre) {
        this.flg = flg;
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userPwd = userPwd;
        this.epId = epId;
        this.userPhoto = userPhoto;
        this.key = aeskes;
        this.serviceArea = serviceAre;
    }

    public String getServiceArea() {
        return serviceArea;
    }

    public void setServiceArea(String serviceArea) {
        this.serviceArea = serviceArea;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFlg() {
        return flg;
    }

    public void setFlg(String flg) {
        this.flg = flg;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public void setEpId(String epId) {
        this.epId = epId;
    }

    public String getEpId() {
        return this.epId;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getAesKes() {
        return key;
    }

    public void setAesKes(String aesKes) {
        this.key = aesKes;
    }
}

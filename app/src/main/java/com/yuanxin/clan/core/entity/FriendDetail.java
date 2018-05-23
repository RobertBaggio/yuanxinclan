package com.yuanxin.clan.core.entity;

import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.bean.businessArea;

/**
 * ProjectName: yuanxinclan
 * Describe: 好友详情
 * Author: xjc
 * Date: 2017/6/26 0026 20:23
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class FriendDetail {


    /**
     * userId : 358
     * userNm : 余先生
     * userPhone : 15015117136
     * userPwd : 123456
     * userBirth :
     * userSex : 0
     * addressId : 374
     * role : 1
     * easemobUuid : 1976de00-30af-11e7-97e5-379886e1cb1d
     * createId : 1
     * createDt : 2017-05-04 17:50:00
     * createNm :
     * post :
     * updateId : 1
     * updateNm :
     * updateDt : 2017-05-04 17:50:00
     * delFlg : 1
     * userImage : null
     * epRoleName : 管理员
     * roleNm : 管理员
     * company : null
     * expertPosition : null
     * epPosition : null
     * businessAreaId : null
     * epNm : 东莞市品胜纺织有限公司
     * epId : 267
     * epRole : 1
     * province : 广东省
     * city : 东莞市
     * area : 虎门镇
     * detail : 宏发永乐布料辅料交易市场E区010A号铺位
     */

    private int userId;
    private String userNm;
    private String userPhone;
    private String userPwd;
    private String userBirth;
    private int userSex;
    private int addressId;
    private int role;
    private String easemobUuid;
    private int createId;
    private String createDt;
    private String createNm;
    private String post;
    private int updateId;
    private String updateNm;
    private String updateDt;
    private int delFlg;
    private String userImage;
    private String epRoleName;
    private String roleNm;
    private String company;
    private String expertPosition;
    private String epPosition;
    private String businessAreaId;
    private String epNm;
    private int epId;
    private int epRole;
    private String province;
    private String city;
    private String area;
    private String detail;
    private businessArea businessArea;

    public com.yuanxin.clan.core.news.bean.businessArea getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(com.yuanxin.clan.core.news.bean.businessArea businessArea) {
        this.businessArea = businessArea;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
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

    public String getUserBirth() {
        return userBirth;
    }

    public void setUserBirth(String userBirth) {
        this.userBirth = userBirth;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEasemobUuid() {
        return easemobUuid;
    }

    public void setEasemobUuid(String easemobUuid) {
        this.easemobUuid = easemobUuid;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getCreateNm() {
        return createNm;
    }

    public void setCreateNm(String createNm) {
        this.createNm = createNm;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public String getUpdateNm() {
        return updateNm;
    }

    public void setUpdateNm(String updateNm) {
        this.updateNm = updateNm;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = Url.img_domain + userImage+Url.imageStyle640x640;
    }

    public String getEpRoleName() {
        return epRoleName;
    }

    public void setEpRoleName(String epRoleName) {
        this.epRoleName = epRoleName;
    }

    public String getRoleNm() {
        return roleNm;
    }

    public void setRoleNm(String roleNm) {
        this.roleNm = roleNm;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getExpertPosition() {
        return expertPosition;
    }

    public void setExpertPosition(String expertPosition) {
        this.expertPosition = expertPosition;
    }

    public String getEpPosition() {
        return epPosition;
    }

    public void setEpPosition(String epPosition) {
        this.epPosition = epPosition;
    }

    public String getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(String businessAreaId) {
        this.businessAreaId = businessAreaId;
    }

    public String getEpNm() {
        return epNm;
    }

    public void setEpNm(String epNm) {
        this.epNm = epNm;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public int getEpRole() {
        return epRole;
    }

    public void setEpRole(int epRole) {
        this.epRole = epRole;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}

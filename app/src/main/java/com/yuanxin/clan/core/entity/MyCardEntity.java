package com.yuanxin.clan.core.entity;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/20 0020 10:24
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyCardEntity {
    private String image;
    private String companyname;
    private String uname;
    private int eid;
    private String phone;
    private String accessPath;
    private String enterpriseCardInfoId;

    public String getEnterpriseCardInfoId() {
        return enterpriseCardInfoId;
    }

    public void setEnterpriseCardInfoId(String enterpriseCardInfoId) {
        this.enterpriseCardInfoId = enterpriseCardInfoId;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}

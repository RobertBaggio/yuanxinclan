package com.yuanxin.clan.core.news.bean;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/10 0010 19:03
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class businessArea {

    private String businessAreaNm;
    private String baPosition;
    private String baViewPath;
    private int businessAreaId;

    public String getBaViewPath() {
        return baViewPath;
    }

    public void setBaViewPath(String baViewPath) {
        this.baViewPath = baViewPath;
    }

    public int getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(int businessAreaId) {
        this.businessAreaId = businessAreaId;
    }

    public String getBusinessAreaNm() {
        return businessAreaNm;
    }

    public void setBusinessAreaNm(String businessAreaNm) {
        this.businessAreaNm = businessAreaNm;
    }

    public String getBaPosition() {
        return baPosition;
    }

    public void setBaPosition(String baRole) {
        this.baPosition = baRole;
    }
}

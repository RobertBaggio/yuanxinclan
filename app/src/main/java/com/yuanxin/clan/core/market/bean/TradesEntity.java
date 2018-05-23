package com.yuanxin.clan.core.market.bean;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/28 0028 19:58
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class TradesEntity {
    private String exhibitionTitle;
    private String exhibitionTitleImg;
    private String hallNm;
    private String starTime;
    private String endTime;
    private String businessPhone;
    private String status;
    private String apart;
    private int exhibitionId;

    public String getApart() {
        return apart;
    }

    public void setApart(String apart) {
        this.apart = apart;
    }

    public String getBusinessPhone() {
        return businessPhone;
    }

    public void setBusinessPhone(String businessPhone) {
        this.businessPhone = businessPhone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExhibitionTitle() {
        return exhibitionTitle;
    }

    public void setExhibitionTitle(String exhibitionTitle) {
        this.exhibitionTitle = exhibitionTitle;
    }

    public String getExhibitionTitleImg() {
        return exhibitionTitleImg;
    }

    public void setExhibitionTitleImg(String exhibitionTitleImg) {
        this.exhibitionTitleImg = exhibitionTitleImg;
    }

    public String getHallNm() {
        return hallNm;
    }

    public void setHallNm(String hallNm) {
        this.hallNm = hallNm;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(int exhibitionId) {
        this.exhibitionId = exhibitionId;
    }
}

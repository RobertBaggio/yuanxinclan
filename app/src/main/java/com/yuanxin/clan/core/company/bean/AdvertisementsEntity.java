package com.yuanxin.clan.core.company.bean;

/**
 * ProjectName: yuanxinclan
 * Describe: 广告类型
 * Author: xjc
 * Date: 2017/6/16 0016 9:27
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class AdvertisementsEntity {

    //（1，广告图）（2，活动快速入口）（3，资讯推荐）（4，商圈推荐）（5，企业推荐）
    /**
     * advertisementId : 43
     * advertisementImg : /upload/images/advertisement/20170508113508171.png
     * advertisementLink :
     * advertisementNm : NAV01
     * advertisementType : 1
     * advertisementTypeNm : 广告图
     * createDt : 2017-05-08 11:35:00
     * createId : 1
     * createNm : 123
     * delFlg : 1
     * isShow : 1
     * updateDt : 2017-05-08 11:35:00
     * updateId : 1
     * updateNm : 123
     */

    private int advertisementId;
    private String advertisementImg;
    private String advertisementLink;
    private String advertisementNm;
    private int advertisementType;
    private String advertisementTypeNm;
    private String createDt;
    private int createId;
    private String createNm;
    private int delFlg;
    private int isShow;
    private String updateDt;
    private String updateId;
    private String updateNm;
    private String province;
    private String city;
    private String advertisementFrom;

    public String getAdvertisementFrom() {
        return advertisementFrom;
    }

    public void setAdvertisementFrom(String advertisementFrom) {
        this.advertisementFrom = advertisementFrom;
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

    public int getAdvertisementId() {
        return advertisementId;
    }

    public void setAdvertisementId(int advertisementId) {
        this.advertisementId = advertisementId;
    }

    public String getAdvertisementImg() {
        return advertisementImg;
    }

    public void setAdvertisementImg(String advertisementImg) {
//        this.advertisementImg = Url.urlHost + advertisementImg;
          this.advertisementImg = advertisementImg;
    }

    public String getAdvertisementLink() {
        return advertisementLink;
    }

    public void setAdvertisementLink(String advertisementLink) {
        this.advertisementLink = advertisementLink;
    }

    public String getAdvertisementNm() {
        return advertisementNm;
    }

    public void setAdvertisementNm(String advertisementNm) {
        this.advertisementNm = advertisementNm;
    }

    public int getAdvertisementType() {
        return advertisementType;
    }

    public void setAdvertisementType(int advertisementType) {
        this.advertisementType = advertisementType;
    }

    public String getAdvertisementTypeNm() {
        return advertisementTypeNm;
    }

    public void setAdvertisementTypeNm(String advertisementTypeNm) {
        this.advertisementTypeNm = advertisementTypeNm;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getCreateNm() {
        return createNm;
    }

    public void setCreateNm(String createNm) {
        this.createNm = createNm;
    }

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateNm() {
        return updateNm;
    }

    public void setUpdateNm(String updateNm) {
        this.updateNm = updateNm;
    }


}

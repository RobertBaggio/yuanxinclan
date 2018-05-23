package com.yuanxin.clan.core.market.bean;

import com.yuanxin.clan.core.http.Url;

import java.io.Serializable;

/**
 * ProjectName: yuanxinclan
 * Describe: 订单商品
 * Author: xjc
 * Date: 2017/6/14 0014 10:52
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class OrderCommodity implements Serializable {

    private String businessId;
    private double expressCost;
    private String userId;
    private String userNm;
    private String commodityId;
    private String commodityColor;
    private String commoditySp;
    public int carId;
    private int commodityNumber;
    private double commodityPrice;
    /**
     * shopListId : 703
     * businessId : null
     * commodityId : 145
     * commodityPrice : 156
     * commodityNm : 商品1
     * commodityImage1 : /upload/images/commodity/20170615095321438.png
     * createId : 580
     * createDt : 2017-06-15 10:16:00
     * createNm : 小熊
     * updateId : null
     * updateDt : null
     * updateNm :
     * delFlg : null
     */

    private int shopListId;
    private String commodityNm;
    private String commodityImage1;
    private int createId;
    private String createDt;
    private String createNm;
    private String updateId;
    private String updateDt;
    private String updateNm;
    private String delFlg;

    private boolean isChecked;

    private int shopDataFrom;
    private int stock;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityColor() {
        return commodityColor;
    }

    public void setCommodityColor(String commodityColor) {
        this.commodityColor = commodityColor;
    }

    public String getCommoditySp() {
        return commoditySp;
    }

    public void setCommoditySp(String commoditySp) {
        this.commoditySp = commoditySp;
    }

    public double getExpressCost() {
        return expressCost;
    }

    public void setExpressCost(double expressCost) {
        this.expressCost = expressCost;
    }

    public int getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(int commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public int getShopListId() {
        return shopListId;
    }

    public void setShopListId(int shopListId) {
        this.shopListId = shopListId;
    }

    public String getCommodityNm() {
        return commodityNm;
    }

    public void setCommodityNm(String commodityNm) {
        this.commodityNm = commodityNm;
    }

    public String getCommodityImage1() {
        return commodityImage1;
    }

    public void setCommodityImage1(String commodityImage1) {
        this.commodityImage1 = Url.img_domain + commodityImage1+Url.imageStyle640x640;
    }

    public void setCommodityImage1Full(String commodityImage1) {
        this.commodityImage1 = Url.img_domain + commodityImage1+Url.imageStyle640x640;
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

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getUpdateNm() {
        return updateNm;
    }

    public void setUpdateNm(String updateNm) {
        this.updateNm = updateNm;
    }

    public String getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(String delFlg) {
        this.delFlg = delFlg;
    }

    public void setShopDataFrom(int dataFrom) {
        shopDataFrom = dataFrom;
    }
    public int getShopDataFrom() {
        return shopDataFrom;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStock() {
        return this.stock;
    }
}

package com.yuanxin.clan.core.market.bean;

/**
 * ProjectName: yuanxinclan
 * Describe: 购物车商品
 * Author: xjc
 * Date: 2017/6/15 0015 10:33
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ShoppingCommodity {

    /**
     * carId : 617
     * shopListId : 703
     * userId : 580
     * updateDt : null
     * updateId : null
     * updateNm :
     * createDt : 2017-06-15 10:16:00
     * createId : 580
     * createNm : 小熊
     * delFlg : 1
     * shopList : {"shopListId":703,"businessId":null,"commodityId":145,"commodityColor":"黄，绿，蓝","commoditySp":"12*12","commodityNumber":1,"commodityPrice":156,"commodityNm":"商品1","commodityImage1":"/upload/images/commodity/20170615095321438.png","createId":580,"createDt":"2017-06-15 10:16:00","createNm":"小熊","updateId":null,"updateDt":null,"updateNm":"","delFlg":null}
     */
    private int numid;
    private int oneid;
    private int carId;
    private int shopListId;
    private int userId;
    private String updateDt;
    private String updateId;
    private String updateNm;
    private String createDt;
    private int createId;
    private String createNm;
    private int delFlg;
    private OrderCommodity shopList;

    public int getNumid() {
        return numid;
    }

    public void setNumid(int numid) {
        this.numid = numid;
    }

    public int getOneid() {
        return oneid;
    }

    public void setOneid(int oneid) {
        this.oneid = oneid;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getShopListId() {
        return shopListId;
    }

    public void setShopListId(int shopListId) {
        this.shopListId = shopListId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public OrderCommodity getShopList() {
        return shopList;
    }

    public void setShopList(OrderCommodity shopList) {
        this.shopList = shopList;
    }
}

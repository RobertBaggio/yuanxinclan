package com.yuanxin.clan.core.market.bean;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe: 发给后台的order
 * Author: xjc
 * Date: 2017/6/16 0016 12:05
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class OrderJson {
    private String businessId;
    private String userId;
    private String province;
    private String city;
    private String area;
    private String detail;
    private String phone;
    private String consignee;
    private String orderPrice;
    private String expressCost;
    private String userNm;
    private List<ShopListBean> shopList;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(String orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getExpressCost() {
        return expressCost;
    }

    public void setExpressCost(String expressCost) {
        this.expressCost = expressCost;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public List<ShopListBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShopListBean> shopList) {
        this.shopList = shopList;
    }

    public static class ShopListBean {
        private String shopListId;
    }
}

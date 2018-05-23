package com.yuanxin.clan.core.entity;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/30 0030 17:01
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyService_outEntity {

    private String orderUuid;
    private String createDt;
    private String orderStatus;
    private int oneid;

    public int getOneid() {
        return oneid;
    }

    public void setOneid(int oneid) {
        this.oneid = oneid;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}

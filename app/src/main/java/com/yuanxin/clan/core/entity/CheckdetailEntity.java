package com.yuanxin.clan.core.entity;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/1 0001 10:43
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class CheckdetailEntity {
    private String billId;
    private int userId;
    private int billType;
    private String billAmount;
    private String createDt;
    private String billTypeNm;
    private String body;

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getBillTypeNm() {
        return billTypeNm;
    }

    public void setBillTypeNm(String billTypeNm) {
        this.billTypeNm = billTypeNm;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

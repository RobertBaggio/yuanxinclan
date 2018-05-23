package com.yuanxin.clan.core.company.bean;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/8 0008 18:15
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class enterprise {
    private String epNm;
    private int epId;
    private int addressId;
    private String legalNm;
    private String epImage1;
    private String epScope;
    private String epDetail;
    private String epLinktel;

    public String getEpLinktel() {
        return epLinktel;
    }

    public void setEpLinktel(String epLinktel) {
        this.epLinktel = epLinktel;
    }

    public String getEpDetail() {
        return epDetail;
    }

    public void setEpDetail(String epDetail) {
        this.epDetail = epDetail;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getLegalNm() {
        return legalNm;
    }

    public void setLegalNm(String legalNm) {
        this.legalNm = legalNm;
    }

    public String getEpImage1() {
        return epImage1;
    }

    public void setEpImage1(String epImage1) {
        this.epImage1 = epImage1;
    }

    public String getEpScope() {
        return epScope;
    }

    public void setEpScope(String epScope) {
        this.epScope = epScope;
    }
}

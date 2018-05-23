package com.yuanxin.clan.core.entity;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/12/6 0006 10:55
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class XmTouzEntity {

    private String investmentProjectId;
    private String businessAreaId;
    private String investmentIndustryId;
    private String investmentProjectNm;
    private String investmentProjectImage1;
    private String startTime;
    private String endTime;
    private int buyNumber;
    private int allNumber;
    private double investmentProjectSingle;
    private double investmentProjectAll;
    private double fundraisingMoney;

    public int getAllNumber() {
        return allNumber;
    }

    public void setAllNumber(int allNumber) {
        this.allNumber = allNumber;
    }

    public String getInvestmentProjectId() {
        return investmentProjectId;
    }

    public void setInvestmentProjectId(String investmentProjectId) {
        this.investmentProjectId = investmentProjectId;
    }

    public String getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(String businessAreaId) {
        this.businessAreaId = businessAreaId;
    }

    public String getInvestmentIndustryId() {
        return investmentIndustryId;
    }

    public void setInvestmentIndustryId(String investmentIndustryId) {
        this.investmentIndustryId = investmentIndustryId;
    }

    public String getInvestmentProjectNm() {
        return investmentProjectNm;
    }

    public void setInvestmentProjectNm(String investmentProjectNm) {
        this.investmentProjectNm = investmentProjectNm;
    }

    public String getInvestmentProjectImage1() {
        return investmentProjectImage1;
    }

    public void setInvestmentProjectImage1(String investmentProjectImage1) {
        this.investmentProjectImage1 = investmentProjectImage1;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }

    public double getInvestmentProjectSingle() {
        return investmentProjectSingle;
    }

    public void setInvestmentProjectSingle(double investmentProjectSingle) {
        this.investmentProjectSingle = investmentProjectSingle;
    }

    public double getInvestmentProjectAll() {
        return investmentProjectAll;
    }

    public void setInvestmentProjectAll(double investmentProjectAll) {
        this.investmentProjectAll = investmentProjectAll;
    }

    public double getFundraisingMoney() {
        return fundraisingMoney;
    }

    public void setFundraisingMoney(double fundraisingMoney) {
        this.fundraisingMoney = fundraisingMoney;
    }
}

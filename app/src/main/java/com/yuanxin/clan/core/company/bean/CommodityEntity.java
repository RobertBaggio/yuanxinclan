package com.yuanxin.clan.core.company.bean;

/**
//搜索A
 */

public class CommodityEntity {
    private String commodityNm;
    private String commodityDetail;
    private String commodityPrice;
    private String commodityImage1;
    private double maxPrice;
    private double minPrice;
    private int commodityId;

    public String getCommodityNm() {
        return commodityNm;
    }

    public void setCommodityNm(String commodityNm) {
        this.commodityNm = commodityNm;
    }

    public String getCommodityDetail() {
        return commodityDetail;
    }

    public void setCommodityDetail(String commodityDetail) {
        this.commodityDetail = commodityDetail;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public String getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(String commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public String getCommodityImage1() {
        return commodityImage1;
    }

    public void setCommodityImage1(String commodityImage1) {
        this.commodityImage1 = commodityImage1;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }
}

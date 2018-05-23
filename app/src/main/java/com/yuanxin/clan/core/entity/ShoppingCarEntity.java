package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/4/8.
 */

public class ShoppingCarEntity {
    //    int commodityId=shopList.getInt("commodityId");//商品id
//    String commodityColor=shopList.getString("commodityColor");//颜色
//    String commoditySp=shopList.getString("commoditySp");//规格
//    int commodityNumber=shopList.getInt("commodityNumber");//数量
//    int commodityPrice=shopList.getInt("commodityPrice");//价格
//    String commodityNm=shopList.getString("commodityNm");//名称
    private int commodityId;  //商品id
    private String commodityColor;//颜色
    private String commoditySp;//规格
    private int commodityNumber;//数量
    private int commodityPrice;//价格
    private String commodityNm;//名称
    private String commodityImage1;
    private int carId;//删除购物车用
    private int shopListId;//购物车结算用

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
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

    public int getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(int commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public int getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(int commodityPrice) {
        this.commodityPrice = commodityPrice;
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
        this.commodityImage1 = commodityImage1;
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
}

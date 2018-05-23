package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/3/24.
 */
public class YuanXinFairNewGoodsEntity {
    private String image;//图片
    private String goodsName;//名称
    private String goodsPrice;//价格
    private String id;
    private String commodityColor;//颜色
    private String commodityStock;//商品库存
    private String commodityDetail;////商品描述
    private String commoditySp;//商品规格

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(String goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCommodityColor() {
        return commodityColor;
    }

    public void setCommodityColor(String commodityColor) {
        this.commodityColor = commodityColor;
    }

    public String getCommodityStock() {
        return commodityStock;
    }

    public void setCommodityStock(String commodityStock) {
        this.commodityStock = commodityStock;
    }

    public String getCommodityDetail() {
        return commodityDetail;
    }

    public void setCommodityDetail(String commodityDetail) {
        this.commodityDetail = commodityDetail;
    }

    public String getCommoditySp() {
        return commoditySp;
    }

    public void setCommoditySp(String commoditySp) {
        this.commoditySp = commoditySp;
    }
}

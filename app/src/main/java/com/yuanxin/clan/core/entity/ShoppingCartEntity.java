package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/4/1.
 */
public class ShoppingCartEntity {
    private String image;
    private String name;
    private String chooseOne;//规格
    private String chooseTwo;//颜色
    private String price;//价格
    private String number;//数量
    private String id;//删除哪些子项
    private int job_id;//删除哪些子项
    private boolean is_checked;
    private int momeyList;//结算总金额
    private int commodityId;//商品id
    private int shopListId;

    public int getShopListId() {
        return shopListId;
    }

    public void setShopListId(int shopListId) {
        this.shopListId = shopListId;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }


    public int getMomeyList() {
        return momeyList;
    }

    public void setMomeyList(int momeyList) {
        this.momeyList = momeyList;
    }

    public boolean is_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }

    private boolean is_select;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChooseOne() {
        return chooseOne;
    }

    public void setChooseOne(String chooseOne) {
        this.chooseOne = chooseOne;
    }

    public String getChooseTwo() {
        return chooseTwo;
    }

    public void setChooseTwo(String chooseTwo) {
        this.chooseTwo = chooseTwo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}

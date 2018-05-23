package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/4/15.
 */

public class MyOrderAllEntity {
    private String image;
    private String price;
    private String name;
    private int number;
    private String chooseOne;
    private String chooseTwo;
    private String createDt;
    private String businessId;

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int  getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
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
}

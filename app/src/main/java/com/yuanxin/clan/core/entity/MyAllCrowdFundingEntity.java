package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/2/22.
 */
public class MyAllCrowdFundingEntity {
    private String image;
    private String name;
    private Float money;
    private String percent;
    private String number;
    private Float total;
    private String time;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {//无用
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

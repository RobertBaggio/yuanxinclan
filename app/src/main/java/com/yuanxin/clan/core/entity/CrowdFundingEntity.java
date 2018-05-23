package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/2/22.
 */
public class CrowdFundingEntity {
    private String image;
    private String name;
    private int money;
    private String percent;
    private String number;
    private int total;
    private String time;

    public int getCrowdfundId() {
        return crowdfundId;
    }

    public void setCrowdfundId(int crowdfundId) {
        this.crowdfundId = crowdfundId;
    }

    private int crowdfundId;

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

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

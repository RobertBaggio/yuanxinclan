package com.yuanxin.clan.core.market.bean;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/29 0029 13:52
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class TradesZGEntity {

    private String hallNm;
    private String province;
    private String detail;
    private String lon;
    private String lat;
    private String hallDes;
    private int hallId;
    private int oneid;
    private int numbers;

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public int getOneid() {
        return oneid;
    }

    public void setOneid(int oneid) {
        this.oneid = oneid;
    }

    public String getHallNm() {
        return hallNm;
    }

    public void setHallNm(String hallNm) {
        this.hallNm = hallNm;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getHallDes() {
        return hallDes;
    }

    public void setHallDes(String hallDes) {
        this.hallDes = hallDes;
    }

    public int getHallId() {
        return hallId;
    }

    public void setHallId(int hallId) {
        this.hallId = hallId;
    }
}

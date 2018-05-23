package com.yuanxin.clan.core.http;

/**
 * Created by lenovo1 on 2017/1/25.
 */
public class BaseJsonEntity {
    private int pageCount;
    private int total;
    private int pageNumber;
    private int pageSize;
    private String msg;
    private String data;
    private String success;
    private String carouselTime1;
    private String carouselTime2;
    private String carouselTime3;

    public String getCarouselTime1() {
        return carouselTime1;
    }

    public void setCarouselTime1(String carouselTime1) {
        this.carouselTime1 = carouselTime1;
    }

    public String getCarouselTime2() {
        return carouselTime2;
    }

    public void setCarouselTime2(String carouselTime2) {
        this.carouselTime2 = carouselTime2;
    }

    public String getCarouselTime3() {
        return carouselTime3;
    }

    public void setCarouselTime3(String carouselTime3) {
        this.carouselTime3 = carouselTime3;
    }

    public String getCarouselTime4() {
        return carouselTime4;
    }

    public void setCarouselTime4(String carouselTime4) {
        this.carouselTime4 = carouselTime4;
    }

    public String getCarouselTime5() {
        return carouselTime5;
    }

    public void setCarouselTime5(String carouselTime5) {
        this.carouselTime5 = carouselTime5;
    }

    private String carouselTime4;
    private String carouselTime5;

    public String getCarouselTime() {
        return carouselTime1;
    }

    public void setCarouselTime(String carouselTime) {
        this.carouselTime1 = carouselTime;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
}

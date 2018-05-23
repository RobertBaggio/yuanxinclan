package com.yuanxin.clan.core.activity_groups;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/3/28 0028 10:49
 */

public class ActivityData {
    private int status;
    private String area;
    private int time;

    public ActivityData(int status, String area, int time) {
        this.status = status;
        this.area = area;
        this.time = time;
    }
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

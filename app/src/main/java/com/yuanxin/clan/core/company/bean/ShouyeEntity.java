package com.yuanxin.clan.core.company.bean;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/14 0014 10:17
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ShouyeEntity {
    private int epViewId;
    private String epViewNm;
    private String epViewImage;
    private String epAccessPath;
    private String colorString;
    private Boolean current;
    private boolean ifselect;

    public String getColorString() {
        return colorString;
    }

    public void setColorString(String colorString) {
        this.colorString = colorString;
    }

    public boolean isIfselect() {
        return ifselect;
    }

    public void setIfselect(boolean ifselect) {
        this.ifselect = ifselect;
    }

    public int getEpViewId() {
        return epViewId;
    }

    public void setEpViewId(int epViewId) {
        this.epViewId = epViewId;
    }

    public String getEpViewNm() {
        return epViewNm;
    }

    public void setEpViewNm(String epViewNm) {
        this.epViewNm = epViewNm;
    }

    public String getEpViewImage() {
        return epViewImage;
    }

    public void setEpViewImage(String epViewImage) {
        this.epViewImage = epViewImage;
    }

    public String getEpAccessPath() {
        return epAccessPath;
    }

    public void setEpAccessPath(String epAccessPath) {
        this.epAccessPath = epAccessPath;
    }

    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }
}

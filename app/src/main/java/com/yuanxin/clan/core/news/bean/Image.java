package com.yuanxin.clan.core.news.bean;

import java.io.Serializable;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/20 0020 14:49
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class Image implements Serializable {
    private String strImage;
    private int nImageId;
    private String strUrl;

    public String getStrUrl() {
        return strUrl;
    }

    public void setStrUrl(String strUrl) {
        this.strUrl = strUrl;
    }

    public int getnImageId() {
        return nImageId;
    }

    public void setnImageId(int nImageId) {
        this.nImageId = nImageId;
    }

    public String getStrImage() {
        return strImage;
    }

    public void setStrImage(String strImage) {
        this.strImage = strImage;
    }
}

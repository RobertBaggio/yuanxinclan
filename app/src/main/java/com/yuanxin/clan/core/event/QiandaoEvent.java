package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 会话变更
 * Author: xjc
 * Date: 2017/6/8 0008 14:37
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class QiandaoEvent{
    private String imgUrl;
    private String status;
    public QiandaoEvent(String status, String url) {
        status = status;
        imgUrl = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

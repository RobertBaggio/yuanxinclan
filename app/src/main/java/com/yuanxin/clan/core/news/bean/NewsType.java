package com.yuanxin.clan.core.news.bean;

/**
 * ProjectName: yuanxinclan
 * Describe: 资讯类型
 * Author: xjc
 * Date: 2017/6/12 0012 11:46
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class NewsType {

    /**
     * newsTypeId : 1
     * newsTypeNm : 科技
     * createId : 1
     * createNm : null
     * createDt : null
     * updateId : null
     * updateNm : null
     * updateDt : null
     * delFlg : 1
     */

    private String newsTypeId;
    private String newsTypeNm;

    public String getNewsTypeId() {
        return newsTypeId;
    }

    public void setNewsTypeId(String newsTypeId) {
        this.newsTypeId = newsTypeId;
    }

    public String getNewsTypeNm() {
        return newsTypeNm;
    }

    public void setNewsTypeNm(String newsTypeNm) {
        this.newsTypeNm = newsTypeNm;
    }

    @Override
    public boolean equals(Object o){
        if (null == o)
            return false;
        if (!(o instanceof NewsType))
            return false;
        NewsType p = (NewsType)o;
        if (this.newsTypeId.equals(p.getNewsTypeId()))
            return true;
        return false;
    }

}

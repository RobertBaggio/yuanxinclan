package com.yuanxin.clan.core.entity;

import com.yuanxin.clan.core.http.Url;

/**
 * Created by lenovo1 on 2017/5/12.
 */
public class CrowChatEntity {
    private String groupImage;//图片
    private String groupNm;//标题
    private String epAccessPath;//新闻类型
    private String groupId;//新闻类型
    private int businessAreaId;

    public String getEpAccessPath() {
        return epAccessPath;
    }

    public void setEpAccessPath(String epAccessPath) {
        this.epAccessPath = epAccessPath;
    }

    public int getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(int businessAreaId) {
        this.businessAreaId = businessAreaId;
    }

    public String getGroupImage() {
        return groupImage;
    }

    public void setGroupImage(String groupImage) {
        this.groupImage = Url.img_domain + groupImage+Url.imageStyle640x640;
    }

    public String getGroupNm() {
        return groupNm;
    }

    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

package com.yuanxin.clan.core.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by lenovo1 on 2017/4/6.
 */
public class BusinessDistrictListEntity implements MultiItemEntity {
    private String city;
    private String area;
    private String businessAreaNm;
    private String businessAreaType;
    private String image;
    private String businessAreaId;
    private String epAccessPath;
    private String groupId;
    private String name;
    private String accessPath;
    private String industryNm;
    private String enshrine;
    private String reason;
    private String businessAreaDetail;
    private String businessAreaGenre;
    private int status;
    private int memberShip;
    private int collectionCount;

    public static final int style_0 = 0;
    public static final int style_1 = 1;

    private int itemType = style_1;

    public String getBusinessAreaGenre() {
        return businessAreaGenre;
    }

    public void setBusinessAreaGenre(String businessAreaGenre) {
        this.businessAreaGenre = businessAreaGenre;
    }

    public String getBusinessAreaDetail() {
        return businessAreaDetail;
    }

    public void setBusinessAreaDetail(String businessAreaDetail) {
        this.businessAreaDetail = businessAreaDetail;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEnshrine() {
        return enshrine;
    }

    public void setEnshrine(String enshrine) {
        this.enshrine = enshrine;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBusinessAreaType() {
        return businessAreaType;
    }

    public void setBusinessAreaType(String businessAreaType) {
        this.businessAreaType = businessAreaType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryNm() {
        return industryNm;
    }

    public void setIndustryNm(String industryNm) {
        this.industryNm = industryNm;
    }

    public int getMemberShip() {
        return memberShip;
    }

    public void setMemberShip(int memberShip) {
        this.memberShip = memberShip;
    }

    public int getCollectionCount() {
        return collectionCount;
    }

    public void setCollectionCount(int collectionCount) {
        this.collectionCount = collectionCount;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getEpAccessPath() {
        return epAccessPath;
    }

    public void setEpAccessPath(String epAccessPath) {
        this.epAccessPath = epAccessPath;
    }

    public String getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(String businessAreaId) {
        this.businessAreaId = businessAreaId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBusinessAreaNm() {
        return businessAreaNm;
    }

    public void setBusinessAreaNm(String businessAreaNm) {
        this.businessAreaNm = businessAreaNm;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}

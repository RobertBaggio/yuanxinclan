package com.yuanxin.clan.core.market.bean;

import com.yuanxin.clan.core.http.Url;

/**
 *
 * ProjectName: yuanxinclan
 * Describe: 商品详情实体
 * Author: xjc
 * Date: 2017/6/13 0013 17:07
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class CommdoityDetail {

    /**
     * commodityId : 138
     * commodityNm : 测试商品
     * commodityPrice : 123
     * epId : 15
     * marketId : 74
     * commodityColor : null
     * commodityStock : 123
     * createId : 1
     * createNm : 123
     * createDt : 2017-06-13 14:48:00
     * updateId : null
     * updateNm :
     * updateDt : null
     * delFlg : 1
     * commodityDetail : 123
     * commoditySp : 123
     * commodityNumber : null
     * commodityMaterial : null
     * commodityStyle : null
     * commoditySize : null
     * commodityWeight : null
     * commodityImage1 : /upload/images/commodity/20170613144806508.jpg
     * commodityImage2 : null
     * commodityImage3 : null
     * commodityImage4 : null
     * commodityImage5 : null
     * commodityImage6 : null
     * commodityImage7 : null
     * commodityImage8 : null
     * commodityImage9 : null
     * commodityImage10 : null
     * state : 1
     * content :
     * top : 1
     * userId : null
     * epNm : 广东小小鸟科技
     */

    private int commodityId;
    private String commodityNm;
    private double commodityPrice;
    private int epId;
    private int marketId;
    private String commodityColor;
    private int commodityStock;
    private int createId;
    private String createNm;
    private String createDt;
    private String updateId;
    private String updateNm;
    private String updateDt;
    private int delFlg;
    private String commodityDetail;
    private String commoditySp;
    // 货号
    private String commodityNumber;
    private String commodityMaterial;
    private String commodityStyle;
    private String commoditySize;
    private String commodityWeight;
    private String commodityImage1;
    private String commodityImage2;
    private String commodityImage3;
    private String commodityImage4;
    private String commodityImage5;
    private String commodityImage6;
    private String commodityImage7;
    private String commodityImage8;
    private String commodityImage9;
    private String commodityImage10;
    private int state;
    private String content;
    private int top;
    private String userId;
    private String userPhone;
    private String epNm;
    /**
     * commodityStock : null
     * updateId : null
     * updateDt : null
     * commodityImage2 : null
     * commodityImage3 : null
     * commodityImage4 : null
     * commodityImage5 : null
     * commodityImage6 : null
     * commodityImage7 : null
     * commodityImage8 : null
     * commodityImage9 : null
     * commodityImage10 : null
     * userId : null
     * address : null
     * enterprise : {"epId":0,"epNm":null,"legalNm":null,"addressId":null,"epScale":null,"industryId":null,"epDetail":null,"epScope":null,"epLogo":null,"epImage1":null,"epImage2":null,"epImage3":null,"epImage4":null,"epImage5":null,"epImage6":null,"epImage7":null,"epImage8":null,"epImage9":null,"epImage10":null,"createNm":null,"createDt":null,"createId":null,"updateNm":null,"updateDt":null,"status":null,"epLinkman":null,"epLinktel":null,"epCreditCd":null,"updateId":null,"reason":null,"epViewId":null,"epStore":null,"epSlogan":null,"groupId":null,"mallNm":null,"choicenessEnterpriseMall":null,"hotEnterpriseMall":null}
     * market : null
     */

    private String address;
    private EnterpriseDetail enterprise;
    private String market;
    /**
     * commodityPrice : 55555
     * commoditySpecification : 该喝喝
     * commoditySize : null
     * commodityType : 哈哈哈哈
     * commodityImage2 : null
     * commodityImage3 : null
     * commodityImage4 : null
     * commodityImage5 : null
     * commodityImage6 : null
     * commodityImage7 : null
     * commodityImage8 : null
     * commodityImage9 : null
     * commodityImage10 : null
     * content : null
     * updateId : null
     * updateNm : null
     * updateDt : null
     * reason : null
     * typeId : null
     * userId : 580
     * under : 1
     * contact : 哈哈哈哈
     * typeNm :
     * enterprise : {"epId":458,"epNm":"广东省东莞市开发测试有限公司","legalNm":null,"addressId":null,"epScale":null,"industryId":null,"epDetail":null,"epScope":null,"epLogo":null,"epImage1":null,"epImage2":null,"epImage3":null,"epImage4":null,"epImage5":null,"epImage6":null,"epImage7":null,"epImage8":null,"epImage9":null,"epImage10":null,"createNm":null,"createDt":null,"createId":null,"updateNm":null,"updateDt":null,"status":null,"epLinkman":null,"epLinktel":null,"epCreditCd":null,"updateId":null,"reason":null,"epViewId":null,"epStore":null,"epSlogan":null,"groupId":null,"mallNm":null,"choicenessEnterpriseMall":null,"hotEnterpriseMall":null}
     * address : null
     * user : {"userId":580,"userNm":"小熊","userPhone":"15088159458","userPwd":null,"userBirth":null,"userSex":null,"addressId":null,"role":null,"easemobUuid":null,"createId":null,"createDt":null,"createNm":"","post":"","updateId":null,"updateNm":"","updateDt":null,"delFlg":null,"userImage":null,"epRoleName":"","roleNm":"","company":null,"expertPosition":null,"epPosition":null,"businessAreaId":null,"epNm":"","epId":null,"epRole":null,"province":null,"city":null,"area":"","detail":null}
     */

    private String commoditySpecification;
    private String commodityType;
    private String reason;
    private String typeId;
    private int under;
    private String contact;
    private String typeNm;
    private UserDetail user;
    // 数量
    private int commodityQuantity;

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public EnterpriseDetail getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnterpriseDetail enterprise) {
        this.enterprise = enterprise;
    }

    public int getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(int commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityNm() {
        return commodityNm;
    }

    public void setCommodityNm(String commodityNm) {
        this.commodityNm = commodityNm;
    }

    public double getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(double commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public String getCommodityColor() {
        return commodityColor;
    }

    public void setCommodityColor(String commodityColor) {
        this.commodityColor = commodityColor;
    }

    public int getCommodityStock() {
        return commodityStock;
    }

    public void setCommodityStock(int commodityStock) {
        this.commodityStock = commodityStock;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getCreateNm() {
        return createNm;
    }

    public void setCreateNm(String createNm) {
        this.createNm = createNm;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getUpdateNm() {
        return updateNm;
    }

    public void setUpdateNm(String updateNm) {
        this.updateNm = updateNm;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    public String getCommodityDetail() {
        return commodityDetail;
    }

    public void setCommodityDetail(String commodityDetail) {
        this.commodityDetail = commodityDetail;
    }

    public String getCommoditySp() {
        return commoditySp;
    }

    public void setCommoditySp(String commoditySp) {
        this.commoditySp = commoditySp;
    }

    public String getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(String commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public String getCommodityMaterial() {
        return commodityMaterial;
    }

    public void setCommodityMaterial(String commodityMaterial) {
        this.commodityMaterial = commodityMaterial;
    }

    public String getCommodityStyle() {
        return commodityStyle;
    }

    public void setCommodityStyle(String commodityStyle) {
        this.commodityStyle = commodityStyle;
    }

    public String getCommoditySize() {
        return commoditySize;
    }

    public void setCommoditySize(String commoditySize) {
        this.commoditySize = commoditySize;
    }

    public String getCommodityWeight() {
        return commodityWeight;
    }

    public void setCommodityWeight(String commodityWeight) {
        this.commodityWeight = commodityWeight;
    }

    public String getCommodityImage1() {
        return commodityImage1;
    }

    public void setCommodityImage1(String commodityImage1) {
        this.commodityImage1 = commodityImage1;
//        this.commodityImage1 = Url.img_domain + commodityImage1+Url.imageStyle640x640;
    }

    public String getCommodityImage2() {
        return commodityImage2;
    }

    public void setCommodityImage2(String commodityImage2) {
        this.commodityImage2 = Url.urlHost + commodityImage2;
    }

    public String getCommodityImage3() {
        return commodityImage3;
    }

    public void setCommodityImage3(String commodityImage3) {
        this.commodityImage3 = Url.urlHost + commodityImage3;
    }

    public String getCommodityImage4() {
        return commodityImage4;
    }

    public void setCommodityImage4(String commodityImage4) {
        this.commodityImage4 = Url.urlHost + commodityImage4;
    }

    public String getCommodityImage5() {
        return commodityImage5;
    }

    public void setCommodityImage5(String commodityImage5) {
        this.commodityImage5 = Url.urlHost + commodityImage5;
    }

    public String getCommodityImage6() {
        return commodityImage6;
    }

    public void setCommodityImage6(String commodityImage6) {
        this.commodityImage6 = Url.urlHost + commodityImage6;
    }

    public String getCommodityImage7() {
        return commodityImage7;
    }

    public void setCommodityImage7(String commodityImage7) {
        this.commodityImage7 = Url.urlHost + commodityImage7;
    }

    public String getCommodityImage8() {
        return commodityImage8;
    }

    public void setCommodityImage8(String commodityImage8) {
        this.commodityImage8 = Url.urlHost + commodityImage8;
    }

    public String getCommodityImage9() {
        return commodityImage9;
    }

    public void setCommodityImage9(String commodityImage9) {
        this.commodityImage9 = Url.urlHost + commodityImage9;
    }

    public String getCommodityImage10() {
        return commodityImage10;
    }

    public void setCommodityImage10(String commodityImage10) {
        this.commodityImage10 = Url.urlHost + commodityImage10;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEpNm() {
        return epNm;
    }

    public void setEpNm(String epNm) {
        this.epNm = epNm;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }


    public String getCommoditySpecification() {
        return commoditySpecification;
    }

    public void setCommoditySpecification(String commoditySpecification) {
        this.commoditySpecification = commoditySpecification;
    }


    public String getCommodityType() {
        return commodityType;
    }

    public void setCommodityType(String commodityType) {
        this.commodityType = commodityType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public int getUnder() {
        return under;
    }

    public void setUnder(int under) {
        this.under = under;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getTypeNm() {
        return typeNm;
    }

    public void setTypeNm(String typeNm) {
        this.typeNm = typeNm;
    }

    public UserDetail getUser() {
        return user;
    }

    public void setUser(UserDetail user) {
        this.user = user;
    }

    public int getCommodityQuantity() {
        return commodityQuantity;
    }

    public void setCommodityQuantity(int commodityQuantity) {
        this.commodityQuantity = commodityQuantity;
    }
}

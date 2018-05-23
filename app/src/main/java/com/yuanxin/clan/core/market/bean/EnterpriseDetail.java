package com.yuanxin.clan.core.market.bean;

import com.yuanxin.clan.core.http.Url;

/**
 * ProjectName: yuanxinclan
 * Describe: 企业信息
 * Author: xjc
 * Date: 2017/6/13 0013 17:20
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class EnterpriseDetail {

    /**
     * epId : 451
     * epNm : 东莞市问道电子商务科技有限公司
     * legalNm : 钟绍强
     * addressId : 1227
     * epScale : null
     * industryId : 8
     * epDetail : 问道电商，成立于2011年1月，首创“C2B电商营销”业务，是首家基于产地进行联合营销服务的电商高端营销服务企业。 问道电商坚持从消费者出发，基于消费者大数据及粉丝经济研究，反向为产地政府、品牌企业及传统制造企业转型升级电商提供C2B电商营销解决方案，为产地政府策划“产地电商节”，为企业提供“按效果付费的营销服务”。
     * epScope : 旨在用专业的互联网经验，产业互联网模式技术、给传统企业赋予品牌孵化能量，同步进行投融资辅导，快速驱动各大产业互联网的升级迭代。
     * epLogo : null
     * epImage1 : /upload/images/ep//20170531180158643.png
     * epImage2 : null
     * epImage3 : null
     * epImage4 : null
     * epImage5 : null
     * epImage6 : null
     * epImage7 : null
     * epImage8 : null
     * epImage9 : null
     * epImage10 : null
     * createNm : 123
     * createDt : 1497336436000
     * createId : 1
     * updateNm : null
     * updateDt : null
     * status : 1
     * epLinkman : 钟绍强
     * epLinktel : 13509973619
     * epCreditCd : 914419003040034970
     * updateId : null
     * reason : null
     * epViewId : 1
     * epStore : 2
     * epSlogan : null
     * groupId : null
     * mallNm : null
     * choicenessEnterpriseMall : null
     * hotEnterpriseMall : null
     */

    private int epId;
    private String epNm;
    private String legalNm;
    private int addressId;
    private String epScale;
    private int industryId;
    private String epDetail;
    private String epScope;
    private String epLogo;
    private String epImage1;
    private String epImage2;
    private String epImage3;
    private String epImage4;
    private String epImage5;
    private String epImage6;
    private String epImage7;
    private String epImage8;
    private String epImage9;
    private String epImage10;
    private String createNm;
    private long createDt;
    private int createId;
    private String updateNm;
    private String updateDt;
    private int status;
    private String epLinkman;
    private String epLinktel;
    private String epCreditCd;
    private String updateId;
    private String reason;
    private int epViewId;
    private String epStore;
    private String epSlogan;
    private String groupId;
    private String mallNm;
    private String choicenessEnterpriseMall;
    private String hotEnterpriseMall;

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public String getEpNm() {
        return epNm;
    }

    public void setEpNm(String epNm) {
        this.epNm = epNm;
    }

    public String getLegalNm() {
        return legalNm;
    }

    public void setLegalNm(String legalNm) {
        this.legalNm = legalNm;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getEpScale() {
        return epScale;
    }

    public void setEpScale(String epScale) {
        this.epScale = epScale;
    }

    public int getIndustryId() {
        return industryId;
    }

    public void setIndustryId(int industryId) {
        this.industryId = industryId;
    }

    public String getEpDetail() {
        return epDetail;
    }

    public void setEpDetail(String epDetail) {
        this.epDetail = epDetail;
    }

    public String getEpScope() {
        return epScope;
    }

    public void setEpScope(String epScope) {
        this.epScope = epScope;
    }

    public String getEpLogo() {
        return epLogo;
    }

    public void setEpLogo(String epLogo) {
        this.epLogo = Url.img_domain + epLogo+Url.imageStyle640x640;
    }

    public String getEpImage1() {
        return epImage1;
    }

    public void setEpImage1(String epImage1) {
        this.epImage1 = Url.urlHost + epImage1;
    }

    public String getEpImage2() {
        return epImage2;
    }

    public void setEpImage2(String epImage2) {
        this.epImage2 = Url.urlHost + epImage2;
    }

    public String getEpImage3() {
        return epImage3;
    }

    public void setEpImage3(String epImage3) {
        this.epImage3 = Url.urlHost + epImage3;
    }

    public String getEpImage4() {
        return epImage4;
    }

    public void setEpImage4(String epImage4) {
        this.epImage4 = Url.urlHost + epImage4;
    }

    public String getEpImage5() {
        return epImage5;
    }

    public void setEpImage5(String epImage5) {
        this.epImage5 = Url.urlHost + epImage5;
    }

    public String getEpImage6() {
        return epImage6;
    }

    public void setEpImage6(String epImage6) {
        this.epImage6 = Url.urlHost + epImage6;
    }

    public String getEpImage7() {
        return epImage7;
    }

    public void setEpImage7(String epImage7) {
        this.epImage7 = Url.urlHost + epImage7;
    }

    public String getEpImage8() {
        return epImage8;
    }

    public void setEpImage8(String epImage8) {
        this.epImage8 = Url.urlHost + epImage8;
    }

    public String getEpImage9() {
        return epImage9;
    }

    public void setEpImage9(String epImage9) {
        this.epImage9 = Url.urlHost + epImage9;
    }

    public String getEpImage10() {
        return epImage10;
    }

    public void setEpImage10(String epImage10) {
        this.epImage10 = Url.urlHost + epImage10;
    }

    public String getCreateNm() {
        return createNm;
    }

    public void setCreateNm(String createNm) {
        this.createNm = createNm;
    }

    public long getCreateDt() {
        return createDt;
    }

    public void setCreateDt(long createDt) {
        this.createDt = createDt;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getEpLinkman() {
        return epLinkman;
    }

    public void setEpLinkman(String epLinkman) {
        this.epLinkman = epLinkman;
    }

    public String getEpLinktel() {
        return epLinktel;
    }

    public void setEpLinktel(String epLinktel) {
        this.epLinktel = epLinktel;
    }

    public String getEpCreditCd() {
        return epCreditCd;
    }

    public void setEpCreditCd(String epCreditCd) {
        this.epCreditCd = epCreditCd;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getEpViewId() {
        return epViewId;
    }

    public void setEpViewId(int epViewId) {
        this.epViewId = epViewId;
    }

    public String getEpStore() {
        return epStore;
    }

    public void setEpStore(String epStore) {
        this.epStore = epStore;
    }

    public String getEpSlogan() {
        return epSlogan;
    }

    public void setEpSlogan(String epSlogan) {
        this.epSlogan = epSlogan;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getMallNm() {
        return mallNm;
    }

    public void setMallNm(String mallNm) {
        this.mallNm = mallNm;
    }

    public String getChoicenessEnterpriseMall() {
        return choicenessEnterpriseMall;
    }

    public void setChoicenessEnterpriseMall(String choicenessEnterpriseMall) {
        this.choicenessEnterpriseMall = choicenessEnterpriseMall;
    }

    public String getHotEnterpriseMall() {
        return hotEnterpriseMall;
    }

    public void setHotEnterpriseMall(String hotEnterpriseMall) {
        this.hotEnterpriseMall = hotEnterpriseMall;
    }
}

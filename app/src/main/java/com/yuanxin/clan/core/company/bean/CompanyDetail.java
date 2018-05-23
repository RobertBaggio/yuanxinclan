package com.yuanxin.clan.core.company.bean;

import com.yuanxin.clan.core.http.Url;

import java.io.Serializable;

/**
 * ProjectName: yuanxinclan
 * Describe: 企业信息
 * Author: xjc
 * Date: 2017/6/16 0016 9:31
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class CompanyDetail implements Serializable{

    /**
     * epId : 458
     * epNm : 广东省东莞市开发测试有限公司
     * legalNm :
     * addressId : null
     * epScale : null
     * industryId : 6
     * epDetail : 测试
     * epScope : 测试
     * epLogo : /upload/images/image/20170612204828704.jpeg
     * epImage1 :
     * epImage2 :
     * epImage3 :
     * epImage4 :
     * epImage5 :
     * epImage6 :
     * epImage7 :
     * epImage8 :
     * epImage9 :
     * epImage10 :
     * createNm : null
     * createDt : null
     * createId : null
     * updateNm : null
     * updateDt : null
     * status : 3
     * epLinkman : 王尼玛
     * enshrineId
     * epLinktel : 15088159458
     * epCreditCd :
     * updateId : 580
     * reason : null
     * epViewId : 4
     * epStore : 2,4,5
     * epSlogan : 临汾西红柿鸡蛋面内存不足够吗丁圈套盒烟雨
     * groupId : null
     * mallNm : null
     * choicenessEnterpriseMall : null
     * hotEnterpriseMall : null
     * epUser : {"id":null,"epId":null,"userId":580,"position":null,"role":null,"createNm":null,"createId":null,"createDt":null,"updateId":null,"updateNm":null,"updateDt":null,"delFlg":null}
     * industry : {"industryId":null,"industryNm":"房产/建筑","industryDt":null,"createId":null,"createNm":null,"createDt":null,"updateId":null,"updateNm":null,"updateDt":null,"delFlg":null}
     * address : null
     * enshrine : null
     * partner : null
     * updateEpuser : 0
     * oldUserId : null
     * updateAddress : 0
     * epImage : null
     */

    private int epId;
    private String epNm;
    private String epShortNm;
    private String legalNm;
    private String addressId;
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
    private String createDt;
    private String createId;
    private String updateNm;
    private String updateDt;
    private int status;
    private int enshrineId;
    private String epLinkman;
    private String epLinktel;
    private String epCreditCd;
    private int updateId;
    private String reason;
    private int epViewId;
    private String epStore;
    private String epSlogan;
    private String groupId;
    private String mallNm;
    private String choicenessEnterpriseMall;
    private String hotEnterpriseMall;
    private EpUserBean epUser;
    private IndustryEntity industry;
    private AddressBean address;
    private String enshrine;
    private String partner;
    private int updateEpuser;
    private String oldUserId;
    private int updateAddress;
    private String epImage;
    private EpViewBean epView;
    private enterprise enterprise;
    private String accessPath;
    private String epAccessPath;
    private String area;
    private String industryNm;

    public String getIndustryNm() {
        return industryNm;
    }

    public void setIndustryNm(String industryNm) {
        this.industryNm = industryNm;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public com.yuanxin.clan.core.company.bean.enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(com.yuanxin.clan.core.company.bean.enterprise enterprise) {
        this.enterprise = enterprise;
    }
    public int getEnshrineId() {
        return enshrineId;
    }

    public void setEnshrineId(int enshrineId) {
        this.enshrineId = enshrineId;
    }

    public EpViewBean getEpView() {
        return epView;
    }

    public void setEpView(EpViewBean epView) {
        this.epView = epView;
    }

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

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
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
        this.epLogo = epLogo;
    }

    public String getEpImage1() {
        return epImage1;
    }

    public void setEpImage1(String epImage1) {
        this.epImage1 =  epImage1;
    }

    public String getEpImage2() {
        return epImage2;
    }

    public void setEpImage2(String epImage2) {
        this.epImage2 =  epImage2;
    }

    public String getEpImage3() {
        return epImage3;
    }

    public void setEpImage3(String epImage3) {
        this.epImage3 =  epImage3;
    }

    public String getEpImage4() {
        return epImage4;
    }

    public void setEpImage4(String epImage4) {
        this.epImage4 =  epImage4;
    }

    public String getEpImage5() {
        return epImage5;
    }

    public void setEpImage5(String epImage5) {
        this.epImage5 =  epImage5;
    }

    public String getEpImage6() {
        return epImage6;
    }

    public void setEpImage6(String epImage6) {
        this.epImage6 = epImage6;
    }

    public String getEpImage7() {
        return epImage7;
    }

    public void setEpImage7(String epImage7) {
        this.epImage7 = epImage7;
    }

    public String getEpImage8() {
        return epImage8;
    }

    public void setEpImage8(String epImage8) {
        this.epImage8 = epImage8;
    }

    public String getEpImage9() {
        return epImage9;
    }

    public void setEpImage9(String epImage9) {
        this.epImage9 = epImage9;
    }

    public String getEpImage10() {
        return epImage10;
    }

    public void setEpImage10(String epImage10) {
        this.epImage10 = epImage10;
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

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
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

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
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

    public EpUserBean getEpUser() {
        return epUser;
    }

    public void setEpUser(EpUserBean epUser) {
        this.epUser = epUser;
    }

    public IndustryEntity getIndustry() {
        return industry;
    }

    public void setIndustry(IndustryEntity industry) {
        this.industry = industry;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getEnshrine() {
        return enshrine;
    }

    public void setEnshrine(String enshrine) {
        this.enshrine = enshrine;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public int getUpdateEpuser() {
        return updateEpuser;
    }

    public void setUpdateEpuser(int updateEpuser) {
        this.updateEpuser = updateEpuser;
    }

    public String getOldUserId() {
        return oldUserId;
    }

    public void setOldUserId(String oldUserId) {
        this.oldUserId = oldUserId;
    }

    public int getUpdateAddress() {
        return updateAddress;
    }

    public void setUpdateAddress(int updateAddress) {
        this.updateAddress = updateAddress;
    }

    public String getEpImage() {
        return epImage;
    }

    public void setEpImage(String epImage) {
        this.epImage = Url.urlHost + epImage;
    }

    public String getAccessPath() {
        return accessPath;
    }

    public void setAccessPath(String accessPath) {
        this.accessPath = accessPath;
    }

    public String getEpAccessPath() {
        return epAccessPath;
    }

    public void setEpAccessPath(String epAccessPath) {
        this.epAccessPath = epAccessPath;
    }

    public String getEpShortNm() {
        return epShortNm;
    }

    public void setEpShortNm(String epShortNm) {
        this.epShortNm = epShortNm;
    }

    public static class EpViewBean {

        /**
         * epViewId : null
         * epViewNm : null
         * epViewImage : null
         * createId : null
         * createNm : null
         * createDt : null
         * updateId : null
         * updateNm : null
         * updateDt : null
         * delFlg : null
         * epType : null
         * epAccessPath : epdetail
         */

        private String epViewId;
        private String epViewNm;
        private String epViewImage;
        private String createId;
        private String createNm;
        private String createDt;
        private String updateId;
        private String updateNm;
        private String updateDt;
        private String delFlg;
        private String epType;
        private String epAccessPath;

        public String getEpViewId() {
            return epViewId;
        }

        public void setEpViewId(String epViewId) {
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

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
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

        public String getDelFlg() {
            return delFlg;
        }

        public void setDelFlg(String delFlg) {
            this.delFlg = delFlg;
        }

        public String getEpType() {
            return epType;
        }

        public void setEpType(String epType) {
            this.epType = epType;
        }

        public String getEpAccessPath() {
            return epAccessPath;
        }

        public void setEpAccessPath(String epAccessPath) {
            this.epAccessPath = epAccessPath;
        }
    }

    public static class AddressBean {

        /**
         * addressId : null
         * province : 广东省
         * city : 广州市
         * area : 南沙区
         * detail : 南沙110#
         * createId : null
         * createNm : null
         * createDt : null
         * updateId : null
         * updateNm : null
         * updateDt : null
         * delFlg : null
         */

        private int addressId;
        private String province;
        private String city;
        private String area;
        private String detail;
        private String createId;
        private String createNm;
        private String createDt;
        private String updateId;
        private String updateNm;
        private String updateDt;
        private String delFlg;

        public int getAddressId() {
            return addressId;
        }

        public void setAddressId(int addressId) {
            this.addressId = addressId;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
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

        public String getDelFlg() {
            return delFlg;
        }

        public void setDelFlg(String delFlg) {
            this.delFlg = delFlg;
        }
    }

    public static class EpUserBean {
        /**
         * id : null
         * epId : null
         * userId : 580
         * position : null
         * role : null
         * createNm : null
         * createId : null
         * createDt : null
         * updateId : null
         * updateNm : null
         * updateDt : null
         * delFlg : null
         */

        private String id;
        private String epId;
        private int userId;
        private String position;
        private String role;
        private String createNm;
        private String createId;
        private String createDt;
        private String updateId;
        private String updateNm;
        private String updateDt;
        private String delFlg;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEpId() {
            return epId;
        }

        public void setEpId(String epId) {
            this.epId = epId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getCreateNm() {
            return createNm;
        }

        public void setCreateNm(String createNm) {
            this.createNm = createNm;
        }

        public String getCreateId() {
            return createId;
        }

        public void setCreateId(String createId) {
            this.createId = createId;
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

        public String getDelFlg() {
            return delFlg;
        }

        public void setDelFlg(String delFlg) {
            this.delFlg = delFlg;
        }
    }
}

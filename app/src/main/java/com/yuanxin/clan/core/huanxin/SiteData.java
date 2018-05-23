package com.yuanxin.clan.core.huanxin;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/20 0020.
 * 园区场地预约信息
 */

public class SiteData implements Serializable{


    /**
     * applyUserId : 1314
     * businessAreaId : 534
     * contactNumber : 13260667087
     * createDt : 1513922753037
     * createId : 1314
     * createNm : 晏强
     * dateUsed : 2017-12-22
     * delFlg : 1
     * hoursUsed : 9:00-12:00
     * processState : 1
     * propertyRentalId : 2
     * siteApplyId : 20
     * siteNm : 过环境规划结构化
     * siteProposerName : 晏强
     * updateDt : 1513922754650
     * updateId : 1314
     * updateNm : 晏强
     */

    private int applyUserId;
    private int businessAreaId;
    private String contactNumber;
    private long createDt;
    private int createId;
    private String createNm;
    private String dateUsed;
    private int delFlg;
    private String hoursUsed;
    private int processState;
    private int propertyRentalId;
    private int siteApplyId;
    private String siteNm;
    private String siteProposerName;
    private long updateDt;
    private int updateId;
    private String updateNm;
    private String applyExplain;

    public int getApplyUserId() {
        return applyUserId;
    }

    public void setApplyUserId(int applyUserId) {
        this.applyUserId = applyUserId;
    }

    public int getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(int businessAreaId) {
        this.businessAreaId = businessAreaId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
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

    public String getCreateNm() {
        return createNm;
    }

    public void setCreateNm(String createNm) {
        this.createNm = createNm;
    }

    public String getDateUsed() {
        return dateUsed;
    }

    public void setDateUsed(String dateUsed) {
        this.dateUsed = dateUsed;
    }

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    public String getHoursUsed() {
        return hoursUsed;
    }

    public void setHoursUsed(String hoursUsed) {
        this.hoursUsed = hoursUsed;
    }

    public int getProcessState() {
        return processState;
    }

    public void setProcessState(int processState) {
        this.processState = processState;
    }

    public int getPropertyRentalId() {
        return propertyRentalId;
    }

    public void setPropertyRentalId(int propertyRentalId) {
        this.propertyRentalId = propertyRentalId;
    }

    public int getSiteApplyId() {
        return siteApplyId;
    }

    public void setSiteApplyId(int siteApplyId) {
        this.siteApplyId = siteApplyId;
    }

    public String getSiteNm() {
        return siteNm;
    }

    public void setSiteNm(String siteNm) {
        this.siteNm = siteNm;
    }

    public String getSiteProposerName() {
        return siteProposerName;
    }

    public void setSiteProposerName(String siteProposerName) {
        this.siteProposerName = siteProposerName;
    }

    public long getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(long updateDt) {
        this.updateDt = updateDt;
    }

    public int getUpdateId() {
        return updateId;
    }

    public void setUpdateId(int updateId) {
        this.updateId = updateId;
    }

    public String getUpdateNm() {
        return updateNm;
    }

    public void setUpdateNm(String updateNm) {
        this.updateNm = updateNm;
    }

    public String getApplyExplain() {
        return applyExplain;
    }

    public void setApplyExplain(String applyExplain) {
        this.applyExplain = applyExplain;
    }
}

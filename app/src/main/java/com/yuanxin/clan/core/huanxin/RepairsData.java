package com.yuanxin.clan.core.huanxin;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/20 0020.
 * 园区场地预约信息
 */

public class RepairsData implements Serializable{

    /**
     * businessAreaId : 534
     * createDt : 1514269697179
     * createId : 1314
     * createNm : 晏强
     * delFlg : 1
     * faultDescribe : 678678678
     * faultImage1 : http://images.yxtribe.com/upload/images/null/20171226142805333.jpg
     * processState : 1
     * repairsAddress : 67867867
     * repairsUserId : 1314
     * repairsUserName : 867867
     * repairsUserPhone : 8678
     * updateDt : 1514269697179
     * updateId : 1314
     * updateNm : 晏强
     */

    private int businessAreaId;
    private long createDt;
    private int createId;
    private String createNm;
    private int delFlg;
    private String faultDescribe;
    private String faultImage1;
    private String faultImage2;
    private String faultImage3;
    private int processState;
    private String repairsAddress;
    private String repairsId;
    private int repairsUserId;
    private String repairsUserName;
    private String repairsUserPhone;
    private long updateDt;
    private int updateId;
    private String updateNm;

    public RepairsData() {
    }

    public int getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(int businessAreaId) {
        this.businessAreaId = businessAreaId;
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

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    public String getFaultDescribe() {
        return faultDescribe;
    }

    public void setFaultDescribe(String faultDescribe) {
        this.faultDescribe = faultDescribe;
    }

    public int getProcessState() {
        return processState;
    }

    public void setProcessState(int processState) {
        this.processState = processState;
    }

    public String getRepairsAddress() {
        return repairsAddress;
    }

    public void setRepairsAddress(String repairsAddress) {
        this.repairsAddress = repairsAddress;
    }

    public int getRepairsUserId() {
        return repairsUserId;
    }

    public void setRepairsUserId(int repairsUserId) {
        this.repairsUserId = repairsUserId;
    }

    public String getRepairsUserName() {
        return repairsUserName;
    }

    public void setRepairsUserName(String repairsUserName) {
        this.repairsUserName = repairsUserName;
    }

    public String getRepairsUserPhone() {
        return repairsUserPhone;
    }

    public void setRepairsUserPhone(String repairsUserPhone) {
        this.repairsUserPhone = repairsUserPhone;
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

    public String getFaultImage1() {
        return faultImage1;
    }

    public void setFaultImage1(String faultImage1) {
        this.faultImage1 = faultImage1;
    }

    public String getFaultImage2() {
        return faultImage2;
    }

    public void setFaultImage2(String faultImage2) {
        this.faultImage2 = faultImage2;
    }

    public String getFaultImage3() {
        return faultImage3;
    }

    public void setFaultImage3(String faultImage3) {
        this.faultImage3 = faultImage3;
    }

    public String getRepairsId() {
        return repairsId;
    }

    public void setRepairsId(String repairsId) {
        this.repairsId = repairsId;
    }
}

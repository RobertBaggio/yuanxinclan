package com.yuanxin.clan.core.entity;

import com.yuanxin.clan.core.http.Url;

import java.io.Serializable;

/**
 * ProjectName: yuanxinclan
 * Describe: 合同详情实体
 * Author: xjc
 * Date: 2017/6/13 0013 17:07
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class BargainEntity implements Serializable {

    /**
     * contractId : 25
     * contractNm : 淘宝合作
     * userId : 517
     * userNm :
     * createId : 579
     * createDt : 2017-05-23 11:15:00
     * createNm : 123
     * updateId : 1
     * updateNm : 123
     * updateDt : 2017-05-23 11:16:00
     * delFlg : 1
     * contractPath : /upload/images/contract/20170523111547599.doc
     */

    private int contractId;
    private String contractNm;
    private int userId;
    private String userNm;
    private int createId;
    private String createDt;
    private String createNm;
    private int updateId;
    private String updateNm;
    private String updateDt;
    private int delFlg;
    private String contractPath;

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public String getContractNm() {
        return contractNm;
    }

    public void setContractNm(String contractNm) {
        this.contractNm = contractNm;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public int getCreateId() {
        return createId;
    }

    public void setCreateId(int createId) {
        this.createId = createId;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getCreateNm() {
        return createNm;
    }

    public void setCreateNm(String createNm) {
        this.createNm = createNm;
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

    public String getContractPath() {
        return contractPath;
    }

    public void setContractPath(String contractPath) {
        this.contractPath = Url.urlHost + contractPath;
    }
}

package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/4/1.
 */
public class HomePageAnnouncementEntity {

    /**
     * announcementId : 2
     * announcementTitle : 企业福利，现移动官网开通，低至365
     * createId : 1
     * createNm : admin
     * createDt : 1510989147000
     * updateId : 1
     * updateNm : admin
     * updateDt : 1510989658000
     * delFlg : 1
     * announcementContent : 企业福利，现移动官网开通，低至365
     */

    private int announcementId;
    private String announcementTitle;
    private int createId;
    private String createNm;
    private long createDt;
    private int updateId;
    private String updateNm;
    private long updateDt;
    private int delFlg;
    private String announcementContent;

    public int getAnnouncementId() {
        return announcementId;
    }

    public void setAnnouncementId(int announcementId) {
        this.announcementId = announcementId;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
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

    public long getCreateDt() {
        return createDt;
    }

    public void setCreateDt(long createDt) {
        this.createDt = createDt;
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

    public long getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(long updateDt) {
        this.updateDt = updateDt;
    }

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    public String getAnnouncementContent() {
        return announcementContent;
    }

    public void setAnnouncementContent(String announcementContent) {
        this.announcementContent = announcementContent;
    }
}

package com.yuanxin.clan.core.activity;

/**
 * Created by lenovo1 on 2017/3/19.
 */
public class CompanyInformationDetailNewEntity {
    private String epNm;//企业名
    private String address;//地区
    private String industry;//行业
    private String epDetail;//简介
    private String epLogo;//图片
    private int epId;//id
    private String chatPhone;

    public String getChatPhone() {
        return chatPhone;
    }

    public void setChatPhone(String chatPhone) {
        this.chatPhone = chatPhone;
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

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getEpDetail() {
        return epDetail;
    }

    public void setEpDetail(String epDetail) {
        this.epDetail = epDetail;
    }

    public String getEpLogo() {
        return epLogo;
    }

    public void setEpLogo(String epLogo) {
        this.epLogo = epLogo;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }
}

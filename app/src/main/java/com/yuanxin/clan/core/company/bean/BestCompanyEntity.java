package com.yuanxin.clan.core.company.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/22 0022.
 */

public class BestCompanyEntity implements Serializable, MultiItemEntity {
    /**
     * epId : 936
     * epNm : 广东圆心信息科技有限公司
     * epDetail : 圆心部落通过“技术+专业”的完美结合，结合企业发展经营中的痛点，信息化建设缓慢，商务视角效率低，运营成本高等问题，打造一套解决企业强需求的解决方案。通过互联网+的思维，整合行业资源，搭建行业桥梁，拓宽行业交互场景，让各行各业在互联网的大环境中聚合成一个紧密的、和谐的商业交流圈，不仅推动各企业自身的发展，更增加行业黏性，促使行业的合纵连横发展。
     * epImage1 : upload/images/ep/936/20171011192005940.jpg
     * epImage4 : upload/images/ep/936/20171106143936221.jpg
     * label : 投资,金融
     * city : 东莞市
     * industryNm : IT
     * epAccessPath : ep-style-5-homePage
     * viewColor : null
     */

    private int epId;
    private String epNm;
    private String epDetail;
    private String epImage1;
    private String epImage4;
    private String label;
    private String city;
    private String industryNm;
    private String epAccessPath;
    private String viewColor;

    public static final int style_0 = 0;
    private int itemType;

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

    public String getEpDetail() {
        return epDetail;
    }

    public void setEpDetail(String epDetail) {
        this.epDetail = epDetail;
    }

    public String getEpImage1() {
        return epImage1;
    }

    public void setEpImage1(String epImage1) {
        this.epImage1 = epImage1;
    }

    public String getEpImage4() {
        return epImage4;
    }

    public void setEpImage4(String epImage4) {
        this.epImage4 = epImage4;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIndustryNm() {
        return industryNm;
    }

    public void setIndustryNm(String industryNm) {
        this.industryNm = industryNm;
    }

    public String getEpAccessPath() {
        return epAccessPath;
    }

    public void setEpAccessPath(String epAccessPath) {
        this.epAccessPath = epAccessPath;
    }

    public String getViewColor() {
        return viewColor;
    }

    public void setViewColor(String viewColor) {
        this.viewColor = viewColor;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}

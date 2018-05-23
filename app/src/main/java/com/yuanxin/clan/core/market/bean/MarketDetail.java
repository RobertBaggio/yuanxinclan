package com.yuanxin.clan.core.market.bean;

import com.yuanxin.clan.core.http.Url;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe: 集市详情实体
 * Author: xjc
 * Date: 2017/6/13 0013 17:20
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class MarketDetail {

    private int marketId;//集市id
    private int businessAreaId;
    private String createId;
    private String createNm;
    private String createDt;
    private String updateId;
    private String updateNm;
    private String updateDt;
    private String status;
    private String marketNm;
    private String addressId;
    private String marketImg;//图片
    private String startTime;
    private String endTime;
    private String businessArea;
    private String address;
    private String marketEp;
    /**
     * addressId : 1227
     * area : 丰县
     * businessAreaNm : 小小鸟科技
     * city : 徐州市
     * createId : 1
     * delFlg : 1
     * detail : 测试数据
     * endTime : 1497456000000
     * province : 江苏省
     * startTime : 1497283200000
     */

    private String area;
    private String businessAreaNm;
    private String city;
    private int delFlg;
    private String detail;
    private String province;
    /**
     * createId : 1
     * updateId : null
     * updateDt : null
     * delFlg : null
     * addressId : 1227
     * startTime : 1497283200000
     * endTime : 1497456000000
     * businessAreaNm : null
     * province : null
     * city : null
     * area : null
     * detail : null
     * enterprise : [{"epId":451,"epNm":"东莞市问道电子商务科技有限公司","legalNm":"钟绍强","addressId":1227,"epScale":null,"industryId":8,"epDetail":"问道电商，成立于2011年1月，首创\u201cC2B电商营销\u201d业务，是首家基于产地进行联合营销服务的电商高端营销服务企业。 问道电商坚持从消费者出发，基于消费者大数据及粉丝经济研究，反向为产地政府、品牌企业及传统制造企业转型升级电商提供C2B电商营销解决方案，为产地政府策划\u201c产地电商节\u201d，为企业提供\u201c按效果付费的营销服务\u201d。","epScope":"旨在用专业的互联网经验，产业互联网模式技术、给传统企业赋予品牌孵化能量，同步进行投融资辅导，快速驱动各大产业互联网的升级迭代。","epLogo":null,"epImage1":"/upload/images/ep//20170531180158643.png","epImage2":null,"epImage3":null,"epImage4":null,"epImage5":null,"epImage6":null,"epImage7":null,"epImage8":null,"epImage9":null,"epImage10":null,"createNm":"123","createDt":1497336436000,"createId":1,"updateNm":null,"updateDt":null,"status":1,"epLinkman":"钟绍强","epLinktel":"13509973619","epCreditCd":"914419003040034970","updateId":null,"reason":null,"epViewId":1,"epStore":"2","epSlogan":null,"groupId":null,"mallNm":null,"choicenessEnterpriseMall":null,"hotEnterpriseMall":null},{"epId":452,"epNm":"东莞市卡雯琳服饰有限公司","legalNm":"陈建泉","addressId":1227,"epScale":null,"industryId":5,"epDetail":"卡雯琳（东莞）服饰有限公司始创于1993年，以\u201c引领国际时尚潮流\u201d的经营理念，成为国内知名的高级时装品牌代理及经销商。卡雯琳（东莞）服饰有限公司先后有代理销售过欧洲的MAXMARA、CHRISTAIN、GIVENCHY、DIESEL等10余个享负盛名的国际知名品牌。无论是在品牌推广、经营策划、终端管理等方面，卡雯琳（东莞）服饰有限公司都一直在努力钻研，不断创新，在服装品牌代理界获得了诸多好评。","epScope":"销售：服饰、服装、鞋帽、皮包、饰品、围巾、腰带、袜子；有关服装的信息咨询服务；有关计算机信息的咨询服务；电子商务。（依法须经批准的项目，经相关部门批准后方可开展经营活动）〓","epLogo":null,"epImage1":"/upload/images/ep//20170531181704790.png","epImage2":null,"epImage3":null,"epImage4":null,"epImage5":null,"epImage6":null,"epImage7":null,"epImage8":null,"epImage9":null,"epImage10":null,"createNm":"123","createDt":1497336436000,"createId":1,"updateNm":null,"updateDt":null,"status":1,"epLinkman":"陈建泉","epLinktel":"13873315937","epCreditCd":"91441900566624314E","updateId":null,"reason":null,"epViewId":1,"epStore":"2","epSlogan":null,"groupId":null,"mallNm":null,"choicenessEnterpriseMall":null,"hotEnterpriseMall":null},{"epId":448,"epNm":"东莞市以纯集团有限公司","legalNm":"郭东林","addressId":1227,"epScale":null,"industryId":5,"epDetail":"以纯是东莞市以纯集团有限公司旗下的时尚休闲服装品牌。以其紧贴时尚、角逐设计，短短几年时间迅速成为国内外时尚休闲服饰知名品牌之一。以纯集团位于中国最大的时尚之都\u2014\u2014广东虎门。","epScope":"服装，鞋帽","epLogo":null,"epImage1":"/upload/images/ep//20170531174324863.jpg","epImage2":null,"epImage3":null,"epImage4":null,"epImage5":null,"epImage6":null,"epImage7":null,"epImage8":null,"epImage9":null,"epImage10":null,"createNm":"123","createDt":1497336436000,"createId":1,"updateNm":null,"updateDt":null,"status":1,"epLinkman":"郭东林","epLinktel":"15077915641","epCreditCd":"91441900797739864H","updateId":null,"reason":null,"epViewId":1,"epStore":"2,5","epSlogan":null,"groupId":null,"mallNm":null,"choicenessEnterpriseMall":null,"hotEnterpriseMall":null},{"epId":447,"epNm":"广东新航线跨境电子商务服务有限公司","legalNm":"孙媛","addressId":1227,"epScale":null,"industryId":8,"epDetail":"新航线公司专业为企业提供跨境电商人才推荐、岗位技能培训服务。公司于2012年9月成立，创业团队核心成员均来自阿里巴巴多年资深市场负责人员，拥有10年以上跨境电商企业人才招聘、培训及团队打造服务经验。\n\n新航线的定位是服务于高校应用技术转型的第三方平台，核心服务包括：\n\n1.跨境电商课程入校\u2014\u2014帮助学校培养跨境电商\u201c双师型\u201d教师，并和学校一起培训大学生，从而促进学科建设；\n\n2.支持大学生创业\u2014\u2014引入企业商品货源等资源，支持大学生创业；\n3.免费就业输送\u2014\u2014成建制成批量解决毕业生就业难题，也支持优秀学生毕业后自主创业。其中，就业方面，2014年全国有1200多名大学生通过新航线平台成功应聘到广东跨境电商企业上岗。岗位全部是跨境电商外贸业务岗位，且新航线对学  校和学生全部免费服务。\n\n为解决人才困局，东莞跨境电商学院应需而生。在此前，新航线已与华中科技大学文华学院、黑龙江东方学院、黑龙江大学等全国100所高校合作，为东莞企业输送了300名跨境电子商务人才。","epScope":"电子商务服务；软件开发；网络技术开发；信息技术咨询服务；；企业形象策划；企业管理咨询；有关商务信息咨询服务；网页设计；计算机信息技术开发；货物及技术进出口。","epLogo":null,"epImage1":"/upload/images/ep//20170531173337027.jpg","epImage2":null,"epImage3":null,"epImage4":null,"epImage5":null,"epImage6":null,"epImage7":null,"epImage8":null,"epImage9":null,"epImage10":null,"createNm":"123","createDt":1497336436000,"createId":1,"updateNm":null,"updateDt":null,"status":1,"epLinkman":"孙媛","epLinktel":"13711392618","epCreditCd":"914419000537764001","updateId":null,"reason":null,"epViewId":1,"epStore":"2","epSlogan":null,"groupId":null,"mallNm":null,"choicenessEnterpriseMall":null,"hotEnterpriseMall":null}]
     * commodityTop : [{"commodityId":138,"commodityNm":"测试商品","commodityPrice":123,"epId":15,"marketId":74,"commodityColor":null,"commodityStock":123,"createId":1,"createNm":"123","createDt":"2017-06-13 14:48:00","updateId":null,"updateNm":"","updateDt":null,"delFlg":1,"commodityDetail":"123","commoditySp":"123","commodityNumber":null,"commodityMaterial":null,"commodityStyle":null,"commoditySize":null,"commodityWeight":null,"commodityImage1":"/upload/images/commodity/20170613144806508.jpg","commodityImage2":null,"commodityImage3":null,"commodityImage4":null,"commodityImage5":null,"commodityImage6":null,"commodityImage7":null,"commodityImage8":null,"commodityImage9":null,"commodityImage10":null,"state":1,"content":"","top":1,"userId":null,"epNm":"广东小小鸟科技"},{"commodityId":139,"commodityNm":"测试","commodityPrice":123,"epId":13,"marketId":74,"commodityColor":null,"commodityStock":null,"createId":1,"createNm":"123","createDt":"2017-06-13 14:48:00","updateId":null,"updateNm":"","updateDt":null,"delFlg":1,"commodityDetail":"123","commoditySp":"123","commodityNumber":null,"commodityMaterial":null,"commodityStyle":null,"commoditySize":null,"commodityWeight":null,"commodityImage1":"/upload/images/commodity/20170613144836715.jpg","commodityImage2":null,"commodityImage3":null,"commodityImage4":null,"commodityImage5":null,"commodityImage6":null,"commodityImage7":null,"commodityImage8":null,"commodityImage9":null,"commodityImage10":null,"state":1,"content":"","top":2,"userId":null,"epNm":"东莞市圆心信息科技有限公司"}]
     * commodity : [{"commodityId":140,"commodityNm":"123","commodityPrice":123,"epId":0,"marketId":74,"commodityColor":"","commodityStock":null,"createId":1,"createNm":"123","createDt":"2017-06-13 16:29:00","updateId":null,"updateNm":"","updateDt":null,"delFlg":1,"commodityDetail":"123","commoditySp":"","commodityNumber":"","commodityMaterial":"","commodityStyle":"","commoditySize":"","commodityWeight":"","commodityImage1":"/upload/images/commodity/20170613162912012.jpg","commodityImage2":null,"commodityImage3":null,"commodityImage4":null,"commodityImage5":null,"commodityImage6":null,"commodityImage7":null,"commodityImage8":null,"commodityImage9":null,"commodityImage10":null,"state":1,"content":"","top":6,"userId":null,"epNm":""},{"commodityId":142,"commodityNm":"而威尔","commodityPrice":34534,"epId":0,"marketId":74,"commodityColor":"","commodityStock":null,"createId":1,"createNm":"123","createDt":"2017-06-13 16:30:00","updateId":null,"updateNm":"","updateDt":null,"delFlg":1,"commodityDetail":"345345","commoditySp":"","commodityNumber":"","commodityMaterial":"","commodityStyle":"","commoditySize":"","commodityWeight":"","commodityImage1":"/upload/images/commodity/20170613163057565.jpg","commodityImage2":null,"commodityImage3":null,"commodityImage4":null,"commodityImage5":null,"commodityImage6":null,"commodityImage7":null,"commodityImage8":null,"commodityImage9":null,"commodityImage10":null,"state":1,"content":"","top":6,"userId":null,"epNm":""},{"commodityId":143,"commodityNm":"电饭锅","commodityPrice":34,"epId":0,"marketId":74,"commodityColor":"","commodityStock":null,"createId":1,"createNm":"123","createDt":"2017-06-13 16:31:00","updateId":null,"updateNm":"","updateDt":null,"delFlg":1,"commodityDetail":"456","commoditySp":"","commodityNumber":"","commodityMaterial":"","commodityStyle":"","commoditySize":"","commodityWeight":"","commodityImage1":"/upload/images/commodity/20170613163116501.jpg","commodityImage2":null,"commodityImage3":null,"commodityImage4":null,"commodityImage5":null,"commodityImage6":null,"commodityImage7":null,"commodityImage8":null,"commodityImage9":null,"commodityImage10":null,"state":1,"content":"","top":6,"userId":null,"epNm":""}]
     */

    private List<EnterpriseDetail> enterprise;
    private List<CommdoityDetail> commodityTop;
    private List<CommdoityDetail> commodity;


    public int getMarketId() {
        return marketId;
    }

    public void setMarketId(int marketId) {
        this.marketId = marketId;
    }

    public int getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(int businessAreaId) {
        this.businessAreaId = businessAreaId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMarketNm() {
        return marketNm;
    }

    public void setMarketNm(String marketNm) {
        this.marketNm = marketNm;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getMarketImg() {
        return marketImg;
    }

    public void setMarketImg(String marketImg) {
        this.marketImg = Url.urlHost + marketImg;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }


    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(String businessArea) {
        this.businessArea = businessArea;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getMarketEp() {
        return marketEp;
    }

    public void setMarketEp(String marketEp) {
        this.marketEp = marketEp;
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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public List<EnterpriseDetail> getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(List<EnterpriseDetail> enterprise) {
        this.enterprise = enterprise;
    }

    public List<CommdoityDetail> getCommodityTop() {
        return commodityTop;
    }

    public void setCommodityTop(List<CommdoityDetail> commodityTop) {
        this.commodityTop = commodityTop;
    }

    public List<CommdoityDetail> getCommodity() {
        return commodity;
    }

    public void setCommodity(List<CommdoityDetail> commodity) {
        this.commodity = commodity;
    }
}

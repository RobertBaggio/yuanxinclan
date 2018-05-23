package com.yuanxin.clan.core.news.bean;

/**
 * Created by Administrator on 2017/8/4 0004.
 */

public class RecommendNewsEntity {

    /**
     * epNewsId : 193
     * epId : 936
     * title : 小木马测试数据
     * titleImage : /upload/images/ep/936/news/20170803105215855.PNG
     * content : casa
     * createNm : 黄晓雪
     * createDt : 1501728738000
     * createId : 1130
     * updateId : 1130
     * updateNm : 黄晓雪
     * updateDt : 1501728738000
     * delFlg : 1
     * epNm :
     * epLogo : null
     * epLinktel : null
     * province : null
     * city : null
     * area : null
     * detail : null
     * epShortNm : null
     * enterprise : {"epId":null,"epNm":"广东圆心信息科技有限公司","epShortNm":"圆心信息科技","legalNm":null,"addressId":null,"epScale":null,"industryId":null,"epDetail":null,"epScope":null,"epLogo":"/upload/images/ep/936/20170731114155999.PNG","epImage1":null,"epImage2":null,"epImage3":null,"epImage4":null,"epImage5":null,"epImage6":null,"epImage7":null,"epImage8":null,"epImage9":null,"epImage10":null,"createNm":null,"createDt":null,"createId":null,"updateNm":null,"updateDt":null,"status":null,"epLinkman":null,"epLinktel":"13925590988","epCreditCd":null,"updateId":null,"reason":null,"epViewId":null,"epStore":null,"epSlogan":null,"groupId":null,"mallNm":null,"epMallFlg":null,"serviceMallFlg":null,"epShowImage1":null,"epShowImage2":null,"epShowImage3":null,"epShowImage4":null,"epShowImage5":null,"epShowImage6":null,"epShowImage7":null,"epShowImage8":null,"epShowImage9":null}
     * address : {"addressId":null,"province":"广东省","city":"东莞市","area":"东城区","detail":"东城中路大地大厦二楼","createId":null,"createNm":null,"createDt":null,"updateId":null,"updateNm":null,"updateDt":null,"delFlg":null}
     */

    private int epNewsId;
    private int epId;
    private String title;
    private String titleImage;
    private String content;
    private String createNm;
    private long createDt;
    private int createId;
    private int updateId;
    private String updateNm;
    private long updateDt;
    private int delFlg;
    private String epNm;
    private Object epLogo;
    private Object epLinktel;
    private Object province;
    private Object city;
    private Object area;
    private Object detail;
    private Object epShortNm;
    private EnterpriseBean enterprise;
    private AddressBean address;

    public int getEpNewsId() {
        return epNewsId;
    }

    public void setEpNewsId(int epNewsId) {
        this.epNewsId = epNewsId;
    }

    public int getEpId() {
        return epId;
    }

    public void setEpId(int epId) {
        this.epId = epId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getEpNm() {
        return epNm;
    }

    public void setEpNm(String epNm) {
        this.epNm = epNm;
    }

    public Object getEpLogo() {
        return epLogo;
    }

    public void setEpLogo(Object epLogo) {
        this.epLogo = epLogo;
    }

    public Object getEpLinktel() {
        return epLinktel;
    }

    public void setEpLinktel(Object epLinktel) {
        this.epLinktel = epLinktel;
    }

    public Object getProvince() {
        return province;
    }

    public void setProvince(Object province) {
        this.province = province;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public Object getDetail() {
        return detail;
    }

    public void setDetail(Object detail) {
        this.detail = detail;
    }

    public Object getEpShortNm() {
        return epShortNm;
    }

    public void setEpShortNm(Object epShortNm) {
        this.epShortNm = epShortNm;
    }

    public EnterpriseBean getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnterpriseBean enterprise) {
        this.enterprise = enterprise;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public static class EnterpriseBean {
        /**
         * epId : null
         * epNm : 广东圆心信息科技有限公司
         * epShortNm : 圆心信息科技
         * legalNm : null
         * addressId : null
         * epScale : null
         * industryId : null
         * epDetail : null
         * epScope : null
         * epLogo : /upload/images/ep/936/20170731114155999.PNG
         * epImage1 : null
         * epImage2 : null
         * epImage3 : null
         * epImage4 : null
         * epImage5 : null
         * epImage6 : null
         * epImage7 : null
         * epImage8 : null
         * epImage9 : null
         * epImage10 : null
         * createNm : null
         * createDt : null
         * createId : null
         * updateNm : null
         * updateDt : null
         * status : null
         * epLinkman : null
         * epLinktel : 13925590988
         * epCreditCd : null
         * updateId : null
         * reason : null
         * epViewId : null
         * epStore : null
         * epSlogan : null
         * groupId : null
         * mallNm : null
         * epMallFlg : null
         * serviceMallFlg : null
         * epShowImage1 : null
         * epShowImage2 : null
         * epShowImage3 : null
         * epShowImage4 : null
         * epShowImage5 : null
         * epShowImage6 : null
         * epShowImage7 : null
         * epShowImage8 : null
         * epShowImage9 : null
         */

        private Object epId;
        private String epNm;
        private String epShortNm;
        private Object legalNm;
        private Object addressId;
        private Object epScale;
        private Object industryId;
        private Object epDetail;
        private Object epScope;
        private String epLogo;
        private Object epImage1;
        private Object epImage2;
        private Object epImage3;
        private Object epImage4;
        private Object epImage5;
        private Object epImage6;
        private Object epImage7;
        private Object epImage8;
        private Object epImage9;
        private Object epImage10;
        private Object createNm;
        private Object createDt;
        private Object createId;
        private Object updateNm;
        private Object updateDt;
        private Object status;
        private Object epLinkman;
        private String epLinktel;
        private Object epCreditCd;
        private Object updateId;
        private Object reason;
        private Object epViewId;
        private Object epStore;
        private Object epSlogan;
        private Object groupId;
        private Object mallNm;
        private Object epMallFlg;
        private Object serviceMallFlg;
        private Object epShowImage1;
        private Object epShowImage2;
        private Object epShowImage3;
        private Object epShowImage4;
        private Object epShowImage5;
        private Object epShowImage6;
        private Object epShowImage7;
        private Object epShowImage8;
        private Object epShowImage9;

        public Object getEpId() {
            return epId;
        }

        public void setEpId(Object epId) {
            this.epId = epId;
        }

        public String getEpNm() {
            return epNm;
        }

        public void setEpNm(String epNm) {
            this.epNm = epNm;
        }

        public String getEpShortNm() {
            return epShortNm;
        }

        public void setEpShortNm(String epShortNm) {
            this.epShortNm = epShortNm;
        }

        public Object getLegalNm() {
            return legalNm;
        }

        public void setLegalNm(Object legalNm) {
            this.legalNm = legalNm;
        }

        public Object getAddressId() {
            return addressId;
        }

        public void setAddressId(Object addressId) {
            this.addressId = addressId;
        }

        public Object getEpScale() {
            return epScale;
        }

        public void setEpScale(Object epScale) {
            this.epScale = epScale;
        }

        public Object getIndustryId() {
            return industryId;
        }

        public void setIndustryId(Object industryId) {
            this.industryId = industryId;
        }

        public Object getEpDetail() {
            return epDetail;
        }

        public void setEpDetail(Object epDetail) {
            this.epDetail = epDetail;
        }

        public Object getEpScope() {
            return epScope;
        }

        public void setEpScope(Object epScope) {
            this.epScope = epScope;
        }

        public String getEpLogo() {
            return epLogo;
        }

        public void setEpLogo(String epLogo) {
            this.epLogo = epLogo;
        }

        public Object getEpImage1() {
            return epImage1;
        }

        public void setEpImage1(Object epImage1) {
            this.epImage1 = epImage1;
        }

        public Object getEpImage2() {
            return epImage2;
        }

        public void setEpImage2(Object epImage2) {
            this.epImage2 = epImage2;
        }

        public Object getEpImage3() {
            return epImage3;
        }

        public void setEpImage3(Object epImage3) {
            this.epImage3 = epImage3;
        }

        public Object getEpImage4() {
            return epImage4;
        }

        public void setEpImage4(Object epImage4) {
            this.epImage4 = epImage4;
        }

        public Object getEpImage5() {
            return epImage5;
        }

        public void setEpImage5(Object epImage5) {
            this.epImage5 = epImage5;
        }

        public Object getEpImage6() {
            return epImage6;
        }

        public void setEpImage6(Object epImage6) {
            this.epImage6 = epImage6;
        }

        public Object getEpImage7() {
            return epImage7;
        }

        public void setEpImage7(Object epImage7) {
            this.epImage7 = epImage7;
        }

        public Object getEpImage8() {
            return epImage8;
        }

        public void setEpImage8(Object epImage8) {
            this.epImage8 = epImage8;
        }

        public Object getEpImage9() {
            return epImage9;
        }

        public void setEpImage9(Object epImage9) {
            this.epImage9 = epImage9;
        }

        public Object getEpImage10() {
            return epImage10;
        }

        public void setEpImage10(Object epImage10) {
            this.epImage10 = epImage10;
        }

        public Object getCreateNm() {
            return createNm;
        }

        public void setCreateNm(Object createNm) {
            this.createNm = createNm;
        }

        public Object getCreateDt() {
            return createDt;
        }

        public void setCreateDt(Object createDt) {
            this.createDt = createDt;
        }

        public Object getCreateId() {
            return createId;
        }

        public void setCreateId(Object createId) {
            this.createId = createId;
        }

        public Object getUpdateNm() {
            return updateNm;
        }

        public void setUpdateNm(Object updateNm) {
            this.updateNm = updateNm;
        }

        public Object getUpdateDt() {
            return updateDt;
        }

        public void setUpdateDt(Object updateDt) {
            this.updateDt = updateDt;
        }

        public Object getStatus() {
            return status;
        }

        public void setStatus(Object status) {
            this.status = status;
        }

        public Object getEpLinkman() {
            return epLinkman;
        }

        public void setEpLinkman(Object epLinkman) {
            this.epLinkman = epLinkman;
        }

        public String getEpLinktel() {
            return epLinktel;
        }

        public void setEpLinktel(String epLinktel) {
            this.epLinktel = epLinktel;
        }

        public Object getEpCreditCd() {
            return epCreditCd;
        }

        public void setEpCreditCd(Object epCreditCd) {
            this.epCreditCd = epCreditCd;
        }

        public Object getUpdateId() {
            return updateId;
        }

        public void setUpdateId(Object updateId) {
            this.updateId = updateId;
        }

        public Object getReason() {
            return reason;
        }

        public void setReason(Object reason) {
            this.reason = reason;
        }

        public Object getEpViewId() {
            return epViewId;
        }

        public void setEpViewId(Object epViewId) {
            this.epViewId = epViewId;
        }

        public Object getEpStore() {
            return epStore;
        }

        public void setEpStore(Object epStore) {
            this.epStore = epStore;
        }

        public Object getEpSlogan() {
            return epSlogan;
        }

        public void setEpSlogan(Object epSlogan) {
            this.epSlogan = epSlogan;
        }

        public Object getGroupId() {
            return groupId;
        }

        public void setGroupId(Object groupId) {
            this.groupId = groupId;
        }

        public Object getMallNm() {
            return mallNm;
        }

        public void setMallNm(Object mallNm) {
            this.mallNm = mallNm;
        }

        public Object getEpMallFlg() {
            return epMallFlg;
        }

        public void setEpMallFlg(Object epMallFlg) {
            this.epMallFlg = epMallFlg;
        }

        public Object getServiceMallFlg() {
            return serviceMallFlg;
        }

        public void setServiceMallFlg(Object serviceMallFlg) {
            this.serviceMallFlg = serviceMallFlg;
        }

        public Object getEpShowImage1() {
            return epShowImage1;
        }

        public void setEpShowImage1(Object epShowImage1) {
            this.epShowImage1 = epShowImage1;
        }

        public Object getEpShowImage2() {
            return epShowImage2;
        }

        public void setEpShowImage2(Object epShowImage2) {
            this.epShowImage2 = epShowImage2;
        }

        public Object getEpShowImage3() {
            return epShowImage3;
        }

        public void setEpShowImage3(Object epShowImage3) {
            this.epShowImage3 = epShowImage3;
        }

        public Object getEpShowImage4() {
            return epShowImage4;
        }

        public void setEpShowImage4(Object epShowImage4) {
            this.epShowImage4 = epShowImage4;
        }

        public Object getEpShowImage5() {
            return epShowImage5;
        }

        public void setEpShowImage5(Object epShowImage5) {
            this.epShowImage5 = epShowImage5;
        }

        public Object getEpShowImage6() {
            return epShowImage6;
        }

        public void setEpShowImage6(Object epShowImage6) {
            this.epShowImage6 = epShowImage6;
        }

        public Object getEpShowImage7() {
            return epShowImage7;
        }

        public void setEpShowImage7(Object epShowImage7) {
            this.epShowImage7 = epShowImage7;
        }

        public Object getEpShowImage8() {
            return epShowImage8;
        }

        public void setEpShowImage8(Object epShowImage8) {
            this.epShowImage8 = epShowImage8;
        }

        public Object getEpShowImage9() {
            return epShowImage9;
        }

        public void setEpShowImage9(Object epShowImage9) {
            this.epShowImage9 = epShowImage9;
        }
    }

    public static class AddressBean {
        /**
         * addressId : null
         * province : 广东省
         * city : 东莞市
         * area : 东城区
         * detail : 东城中路大地大厦二楼
         * createId : null
         * createNm : null
         * createDt : null
         * updateId : null
         * updateNm : null
         * updateDt : null
         * delFlg : null
         */

        private Object addressId;
        private String province;
        private String city;
        private String area;
        private String detail;
        private Object createId;
        private Object createNm;
        private Object createDt;
        private Object updateId;
        private Object updateNm;
        private Object updateDt;
        private Object delFlg;

        public Object getAddressId() {
            return addressId;
        }

        public void setAddressId(Object addressId) {
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

        public Object getCreateId() {
            return createId;
        }

        public void setCreateId(Object createId) {
            this.createId = createId;
        }

        public Object getCreateNm() {
            return createNm;
        }

        public void setCreateNm(Object createNm) {
            this.createNm = createNm;
        }

        public Object getCreateDt() {
            return createDt;
        }

        public void setCreateDt(Object createDt) {
            this.createDt = createDt;
        }

        public Object getUpdateId() {
            return updateId;
        }

        public void setUpdateId(Object updateId) {
            this.updateId = updateId;
        }

        public Object getUpdateNm() {
            return updateNm;
        }

        public void setUpdateNm(Object updateNm) {
            this.updateNm = updateNm;
        }

        public Object getUpdateDt() {
            return updateDt;
        }

        public void setUpdateDt(Object updateDt) {
            this.updateDt = updateDt;
        }

        public Object getDelFlg() {
            return delFlg;
        }

        public void setDelFlg(Object delFlg) {
            this.delFlg = delFlg;
        }
    }
}

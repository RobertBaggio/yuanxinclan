package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/2/24.
 */
public class BusinessMemberEntity {

    /**
     * memberId : 2003
     * businessAreaId : 501
     * userId : 3843
     * position : 监事长
     * role : 1
     * createId : 1
     * createNm : admin
     * createDt : 1510317423000
     * updateId : 1
     * updateNm : admin
     * updateDt : 1510317440000
     * delFlg : 1
     * type : null
     * user : {"userId":3843,"userNm":"徐地华","userPhone":"13788980288","userPwd":null,"userBirth":null,"userSex":null,"addressId":null,"role":null,"easemobUuid":null,"createId":null,"createDt":null,"createNm":"","post":"","updateId":null,"updateNm":"","updateDt":null,"delFlg":1,"userImage":"upload/images/user/20171111100120424.png","wechatOpenid":null,"loginDevice":null,"jpushId":null,"qqUid":null,"wechatUid":null,"seeFlg":null,"epRoleName":"","roleNm":"","company":null,"expertPosition":null,"epPosition":null,"businessAreaId":null,"epNm":"","epImage1":null,"epId":null,"epRole":null,"epAccessPath":null,"province":null,"city":null,"area":"","detail":null,"businessArea":null,"expertInfo":null,"expertFlg":null}
     * rank : 2
     * userPhone : null
     * groupId : null
     * distinction : null
     * enterprise : {"epId":2411,"epNm":"广东正亚科技股份有限公司","epShortNm":null,"legalNm":null,"addressId":null,"epScale":null,"industryId":33,"epDetail":null,"epScope":null,"epLogo":null,"epImage1":null,"epImage2":null,"epImage3":null,"epImage4":null,"epImage5":null,"epImage6":null,"epImage7":null,"epImage8":null,"epImage9":null,"epImage10":null,"createNm":"admin","createDt":1510317423000,"createId":1,"updateNm":"admin","updateDt":1510317440000,"status":null,"epLinkman":null,"epLinktel":null,"epCreditCd":null,"updateId":1,"reason":null,"epViewId":null,"epStore":null,"epSlogan":null,"groupId":null,"mallNm":null,"epMallFlg":null,"serviceMallFlg":null,"epShowImage1":null,"epShowImage2":null,"epShowImage3":null,"epShowImage4":null,"epShowImage5":null,"epShowImage6":null,"epShowImage7":null,"epShowImage8":null,"epShowImage9":null,"epShowDetail1":null,"epShowDetail2":null,"epShowDetail3":null,"epShowDetail4":null,"epShowDetail5":null,"epShowDetail6":null,"epShowDetail7":null,"epShowDetail8":null,"epShowDetail9":null,"enshrine":null}
     * epView : {"epViewId":null,"epViewNm":null,"epViewImage":null,"createId":null,"createNm":null,"createDt":null,"updateId":null,"updateNm":null,"updateDt":null,"delFlg":null,"epType":null,"epAccessPath":"ep-style-4-homePage"}
     * industry : null
     */

    private int memberId;
    private int businessAreaId;
    private int userId;
    private String position;
    private int role;
    private int createId;
    private String createNm;
    private long createDt;
    private int updateId;
    private String updateNm;
    private long updateDt;
    private int delFlg;
    private Object type;
    private UserBean user;
    private int rank;
    private Object userPhone;
    private Object groupId;
    private Object distinction;
    private EnterpriseBean enterprise;
    private EpViewBean epView;
    private Object industry;

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBusinessAreaId() {
        return businessAreaId;
    }

    public void setBusinessAreaId(int businessAreaId) {
        this.businessAreaId = businessAreaId;
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

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Object getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(Object userPhone) {
        this.userPhone = userPhone;
    }

    public Object getGroupId() {
        return groupId;
    }

    public void setGroupId(Object groupId) {
        this.groupId = groupId;
    }

    public Object getDistinction() {
        return distinction;
    }

    public void setDistinction(Object distinction) {
        this.distinction = distinction;
    }

    public EnterpriseBean getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnterpriseBean enterprise) {
        this.enterprise = enterprise;
    }

    public EpViewBean getEpView() {
        return epView;
    }

    public void setEpView(EpViewBean epView) {
        this.epView = epView;
    }

    public Object getIndustry() {
        return industry;
    }

    public void setIndustry(Object industry) {
        this.industry = industry;
    }

    public static class UserBean {
        /**
         * userId : 3843
         * userNm : 徐地华
         * userPhone : 13788980288
         * userPwd : null
         * userBirth : null
         * userSex : null
         * addressId : null
         * role : null
         * easemobUuid : null
         * createId : null
         * createDt : null
         * createNm :
         * post :
         * updateId : null
         * updateNm :
         * updateDt : null
         * delFlg : 1
         * userImage : upload/images/user/20171111100120424.png
         * wechatOpenid : null
         * loginDevice : null
         * jpushId : null
         * qqUid : null
         * wechatUid : null
         * seeFlg : null
         * epRoleName :
         * roleNm :
         * company : null
         * expertPosition : null
         * epPosition : null
         * businessAreaId : null
         * epNm :
         * epImage1 : null
         * epId : null
         * epRole : null
         * epAccessPath : null
         * province : null
         * city : null
         * area :
         * detail : null
         * businessArea : null
         * expertInfo : null
         * expertFlg : null
         */

        private int userId;
        private String userNm;
        private String userPhone;
        private Object userPwd;
        private Object userBirth;
        private Object userSex;
        private Object addressId;
        private Object role;
        private Object easemobUuid;
        private Object createId;
        private Object createDt;
        private String createNm;
        private String post;
        private Object updateId;
        private String updateNm;
        private Object updateDt;
        private int delFlg;
        private String userImage;
        private Object wechatOpenid;
        private Object loginDevice;
        private Object jpushId;
        private Object qqUid;
        private Object wechatUid;
        private Object seeFlg;
        private String epRoleName;
        private String roleNm;
        private Object company;
        private Object expertPosition;
        private Object epPosition;
        private Object businessAreaId;
        private String epNm;
        private Object epImage1;
        private Object epId;
        private Object epRole;
        private Object epAccessPath;
        private Object province;
        private Object city;
        private String area;
        private Object detail;
        private Object businessArea;
        private Object expertInfo;
        private Object expertFlg;

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

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public Object getUserPwd() {
            return userPwd;
        }

        public void setUserPwd(Object userPwd) {
            this.userPwd = userPwd;
        }

        public Object getUserBirth() {
            return userBirth;
        }

        public void setUserBirth(Object userBirth) {
            this.userBirth = userBirth;
        }

        public Object getUserSex() {
            return userSex;
        }

        public void setUserSex(Object userSex) {
            this.userSex = userSex;
        }

        public Object getAddressId() {
            return addressId;
        }

        public void setAddressId(Object addressId) {
            this.addressId = addressId;
        }

        public Object getRole() {
            return role;
        }

        public void setRole(Object role) {
            this.role = role;
        }

        public Object getEasemobUuid() {
            return easemobUuid;
        }

        public void setEasemobUuid(Object easemobUuid) {
            this.easemobUuid = easemobUuid;
        }

        public Object getCreateId() {
            return createId;
        }

        public void setCreateId(Object createId) {
            this.createId = createId;
        }

        public Object getCreateDt() {
            return createDt;
        }

        public void setCreateDt(Object createDt) {
            this.createDt = createDt;
        }

        public String getCreateNm() {
            return createNm;
        }

        public void setCreateNm(String createNm) {
            this.createNm = createNm;
        }

        public String getPost() {
            return post;
        }

        public void setPost(String post) {
            this.post = post;
        }

        public Object getUpdateId() {
            return updateId;
        }

        public void setUpdateId(Object updateId) {
            this.updateId = updateId;
        }

        public String getUpdateNm() {
            return updateNm;
        }

        public void setUpdateNm(String updateNm) {
            this.updateNm = updateNm;
        }

        public Object getUpdateDt() {
            return updateDt;
        }

        public void setUpdateDt(Object updateDt) {
            this.updateDt = updateDt;
        }

        public int getDelFlg() {
            return delFlg;
        }

        public void setDelFlg(int delFlg) {
            this.delFlg = delFlg;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public Object getWechatOpenid() {
            return wechatOpenid;
        }

        public void setWechatOpenid(Object wechatOpenid) {
            this.wechatOpenid = wechatOpenid;
        }

        public Object getLoginDevice() {
            return loginDevice;
        }

        public void setLoginDevice(Object loginDevice) {
            this.loginDevice = loginDevice;
        }

        public Object getJpushId() {
            return jpushId;
        }

        public void setJpushId(Object jpushId) {
            this.jpushId = jpushId;
        }

        public Object getQqUid() {
            return qqUid;
        }

        public void setQqUid(Object qqUid) {
            this.qqUid = qqUid;
        }

        public Object getWechatUid() {
            return wechatUid;
        }

        public void setWechatUid(Object wechatUid) {
            this.wechatUid = wechatUid;
        }

        public Object getSeeFlg() {
            return seeFlg;
        }

        public void setSeeFlg(Object seeFlg) {
            this.seeFlg = seeFlg;
        }

        public String getEpRoleName() {
            return epRoleName;
        }

        public void setEpRoleName(String epRoleName) {
            this.epRoleName = epRoleName;
        }

        public String getRoleNm() {
            return roleNm;
        }

        public void setRoleNm(String roleNm) {
            this.roleNm = roleNm;
        }

        public Object getCompany() {
            return company;
        }

        public void setCompany(Object company) {
            this.company = company;
        }

        public Object getExpertPosition() {
            return expertPosition;
        }

        public void setExpertPosition(Object expertPosition) {
            this.expertPosition = expertPosition;
        }

        public Object getEpPosition() {
            return epPosition;
        }

        public void setEpPosition(Object epPosition) {
            this.epPosition = epPosition;
        }

        public Object getBusinessAreaId() {
            return businessAreaId;
        }

        public void setBusinessAreaId(Object businessAreaId) {
            this.businessAreaId = businessAreaId;
        }

        public String getEpNm() {
            return epNm;
        }

        public void setEpNm(String epNm) {
            this.epNm = epNm;
        }

        public Object getEpImage1() {
            return epImage1;
        }

        public void setEpImage1(Object epImage1) {
            this.epImage1 = epImage1;
        }

        public Object getEpId() {
            return epId;
        }

        public void setEpId(Object epId) {
            this.epId = epId;
        }

        public Object getEpRole() {
            return epRole;
        }

        public void setEpRole(Object epRole) {
            this.epRole = epRole;
        }

        public Object getEpAccessPath() {
            return epAccessPath;
        }

        public void setEpAccessPath(Object epAccessPath) {
            this.epAccessPath = epAccessPath;
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

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public Object getDetail() {
            return detail;
        }

        public void setDetail(Object detail) {
            this.detail = detail;
        }

        public Object getBusinessArea() {
            return businessArea;
        }

        public void setBusinessArea(Object businessArea) {
            this.businessArea = businessArea;
        }

        public Object getExpertInfo() {
            return expertInfo;
        }

        public void setExpertInfo(Object expertInfo) {
            this.expertInfo = expertInfo;
        }

        public Object getExpertFlg() {
            return expertFlg;
        }

        public void setExpertFlg(Object expertFlg) {
            this.expertFlg = expertFlg;
        }
    }

    public static class EnterpriseBean {
        /**
         * epId : 2411
         * epNm : 广东正亚科技股份有限公司
         * epShortNm : null
         * legalNm : null
         * addressId : null
         * epScale : null
         * industryId : 33
         * epDetail : null
         * epScope : null
         * epLogo : null
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
         * createNm : admin
         * createDt : 1510317423000
         * createId : 1
         * updateNm : admin
         * updateDt : 1510317440000
         * status : null
         * epLinkman : null
         * epLinktel : null
         * epCreditCd : null
         * updateId : 1
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
         * epShowDetail1 : null
         * epShowDetail2 : null
         * epShowDetail3 : null
         * epShowDetail4 : null
         * epShowDetail5 : null
         * epShowDetail6 : null
         * epShowDetail7 : null
         * epShowDetail8 : null
         * epShowDetail9 : null
         * enshrine : null
         */

        private int epId;
        private String epNm;
        private Object epShortNm;
        private Object legalNm;
        private Object addressId;
        private Object epScale;
        private int industryId;
        private Object epDetail;
        private Object epScope;
        private Object epLogo;
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
        private String createNm;
        private long createDt;
        private int createId;
        private String updateNm;
        private long updateDt;
        private Object status;
        private Object epLinkman;
        private Object epLinktel;
        private Object epCreditCd;
        private int updateId;
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
        private Object epShowDetail1;
        private Object epShowDetail2;
        private Object epShowDetail3;
        private Object epShowDetail4;
        private Object epShowDetail5;
        private Object epShowDetail6;
        private Object epShowDetail7;
        private Object epShowDetail8;
        private Object epShowDetail9;
        private Object enshrine;

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

        public Object getEpShortNm() {
            return epShortNm;
        }

        public void setEpShortNm(Object epShortNm) {
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

        public int getIndustryId() {
            return industryId;
        }

        public void setIndustryId(int industryId) {
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

        public Object getEpLogo() {
            return epLogo;
        }

        public void setEpLogo(Object epLogo) {
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

        public long getUpdateDt() {
            return updateDt;
        }

        public void setUpdateDt(long updateDt) {
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

        public Object getEpLinktel() {
            return epLinktel;
        }

        public void setEpLinktel(Object epLinktel) {
            this.epLinktel = epLinktel;
        }

        public Object getEpCreditCd() {
            return epCreditCd;
        }

        public void setEpCreditCd(Object epCreditCd) {
            this.epCreditCd = epCreditCd;
        }

        public int getUpdateId() {
            return updateId;
        }

        public void setUpdateId(int updateId) {
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

        public Object getEpShowDetail1() {
            return epShowDetail1;
        }

        public void setEpShowDetail1(Object epShowDetail1) {
            this.epShowDetail1 = epShowDetail1;
        }

        public Object getEpShowDetail2() {
            return epShowDetail2;
        }

        public void setEpShowDetail2(Object epShowDetail2) {
            this.epShowDetail2 = epShowDetail2;
        }

        public Object getEpShowDetail3() {
            return epShowDetail3;
        }

        public void setEpShowDetail3(Object epShowDetail3) {
            this.epShowDetail3 = epShowDetail3;
        }

        public Object getEpShowDetail4() {
            return epShowDetail4;
        }

        public void setEpShowDetail4(Object epShowDetail4) {
            this.epShowDetail4 = epShowDetail4;
        }

        public Object getEpShowDetail5() {
            return epShowDetail5;
        }

        public void setEpShowDetail5(Object epShowDetail5) {
            this.epShowDetail5 = epShowDetail5;
        }

        public Object getEpShowDetail6() {
            return epShowDetail6;
        }

        public void setEpShowDetail6(Object epShowDetail6) {
            this.epShowDetail6 = epShowDetail6;
        }

        public Object getEpShowDetail7() {
            return epShowDetail7;
        }

        public void setEpShowDetail7(Object epShowDetail7) {
            this.epShowDetail7 = epShowDetail7;
        }

        public Object getEpShowDetail8() {
            return epShowDetail8;
        }

        public void setEpShowDetail8(Object epShowDetail8) {
            this.epShowDetail8 = epShowDetail8;
        }

        public Object getEpShowDetail9() {
            return epShowDetail9;
        }

        public void setEpShowDetail9(Object epShowDetail9) {
            this.epShowDetail9 = epShowDetail9;
        }

        public Object getEnshrine() {
            return enshrine;
        }

        public void setEnshrine(Object enshrine) {
            this.enshrine = enshrine;
        }
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
         * epAccessPath : ep-style-4-homePage
         */

        private Object epViewId;
        private Object epViewNm;
        private Object epViewImage;
        private Object createId;
        private Object createNm;
        private Object createDt;
        private Object updateId;
        private Object updateNm;
        private Object updateDt;
        private Object delFlg;
        private Object epType;
        private String epAccessPath;

        public Object getEpViewId() {
            return epViewId;
        }

        public void setEpViewId(Object epViewId) {
            this.epViewId = epViewId;
        }

        public Object getEpViewNm() {
            return epViewNm;
        }

        public void setEpViewNm(Object epViewNm) {
            this.epViewNm = epViewNm;
        }

        public Object getEpViewImage() {
            return epViewImage;
        }

        public void setEpViewImage(Object epViewImage) {
            this.epViewImage = epViewImage;
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

        public Object getEpType() {
            return epType;
        }

        public void setEpType(Object epType) {
            this.epType = epType;
        }

        public String getEpAccessPath() {
            return epAccessPath;
        }

        public void setEpAccessPath(String epAccessPath) {
            this.epAccessPath = epAccessPath;
        }
    }
}

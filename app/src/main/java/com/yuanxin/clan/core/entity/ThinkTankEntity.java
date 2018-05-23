package com.yuanxin.clan.core.entity;

import com.yuanxin.clan.core.http.Url;

/**
 * Created by lenovo1 on 2017/2/20.
 */
public class ThinkTankEntity {

    /**
     * company : 专家所在公司测试
     * createDt : 2017-05-28 09:02:26.0
     * createId : 1
     * createNm : 123
     * delFlg : 1
     * expertDetail : 专家简介测试
     * expertId : 98
     * expertNm : 专家测试姓名通过
     * image1 : /upload/images/expert//20170528090218822.png
     * image2 : /upload/images/expert//20170528090218838.png
     * image3 : /upload/images/expert//20170528090218807.png
     * image4 : /upload/images/expert//20170528090218807.png
     * image5 : /upload/images/expert//20170528090218807.png
     * image6 : /upload/images/expert//20170528090218807.png
     * image7 : /upload/images/expert//20170528090218853.png
     * position : 专家认证职位测试
     * reason :
     * state : 0
     * title : 专家头衔测试
     * updateDt : 2017-05-31 13:48:25.0
     * updateId : 1
     * updateNm : 123
     * userId : 1
     */

    private String company;
    private String createDt;
    private int createId;
    private String createNm;
    private int delFlg;
    private String expertDetail;
    private int expertId;
    private String expertNm;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
    private String image5;
    private String image6;
    private String image7;
    private String position;
    private String reason;
    private int state;
    private String title;
    private String updateDt;
    private int updateId;
    private String updateNm;
    private int userId;
    /**
     * birth : null
     * sex : null
     * address : null
     * tel : null
     * updateId : null
     * updateNm : null
     * updateDt : null
     * image3 : null
     * image4 : null
     * image5 : null
     * image6 : null
     * image7 : null
     * reason : null
     * userPhone : 15817624331
     */

    private String birth;
    private String sex;
    private String address;
    private String tel;
    private String userPhone;

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
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

    public String getExpertDetail() {
        return expertDetail;
    }

    public void setExpertDetail(String expertDetail) {
        this.expertDetail = expertDetail;
    }

    public int getExpertId() {
        return expertId;
    }

    public void setExpertId(int expertId) {
        this.expertId = expertId;
    }

    public String getExpertNm() {
        return expertNm;
    }

    public void setExpertNm(String expertNm) {
        this.expertNm = expertNm;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = Url.img_domain + image1+Url.imageStyle208x208;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = Url.urlHost + image2;
    }

    public String getImage3() {
        return image3;
    }

    public void setImage3(String image3) {
        this.image3 = Url.urlHost + image3;
    }

    public String getImage4() {
        return image4;
    }

    public void setImage4(String image4) {
        this.image4 = Url.urlHost + image4;
    }

    public String getImage5() {
        return image5;
    }

    public void setImage5(String image5) {
        this.image5 = Url.urlHost + image5;
    }

    public String getImage6() {
        return image6;
    }

    public void setImage6(String image6) {
        this.image6 = Url.urlHost + image6;
    }

    public String getImage7() {
        return image7;
    }

    public void setImage7(String image7) {
        this.image7 = Url.urlHost + image7;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}

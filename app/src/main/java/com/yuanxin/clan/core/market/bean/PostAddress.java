package com.yuanxin.clan.core.market.bean;

import java.io.Serializable;

/**
 * Created by lenovo1 on 2017/4/2.
 */
public class PostAddress implements Serializable {

    /**
     * userAddressId : 94
     * userId : 580
     * receiver : 傻逼
     * defaultAddress : null
     * createId : 580
     * createNm : 小熊
     * createDt : 2017-06-09 10:49:00
     * updateId : null
     * updateNm : null
     * updateDt : null
     * delFlg : 1
     */

    private int userId;
    private String receiver;
    private int createId;
    private String createNm;
    private String createDt;
    private String updateId;
    private String updateNm;
    private String updateDt;
    private int delFlg;
    /**
     * userAddressId : 94
     * province : 广东省
     * city : 东莞市
     * area : 东城镇
     * detail : 路上边走边喊有打你的人就是了
     * phone : 13987654321
     * defaultAddress : null
     * updateId : null
     * updateNm : null
     * updateDt : null
     */

    private int userAddressId;
    private String province;
    private String city;
    private String area;
    private String detail;
    private String phone;
    private int defaultAddress;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
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

    public int getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(int delFlg) {
        this.delFlg = delFlg;
    }

    public int getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(int userAddressId) {
        this.userAddressId = userAddressId;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(int defaultAddress) {
        this.defaultAddress = defaultAddress;
    }
}

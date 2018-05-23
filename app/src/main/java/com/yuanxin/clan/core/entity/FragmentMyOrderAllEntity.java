package com.yuanxin.clan.core.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo1 on 2017/4/14.
 */

public class FragmentMyOrderAllEntity implements Parcelable {
    private String orderId;
    private String state;
    //    private String image;
    private String businessId;
    private String price;
    private String updateDt;
    private String commoditySp;
    private String commodityColor;
    private String commodityNm;
    private String commodityImage1;
    private String expressNumber;
    private String expressCompany;
    private String orderUuid;
    private String orderStatus;
    private String province;
    private String city;
    private String area;
    private String detail;
    private String phone;
    private String createNm;
    private String createDt;
    private String paymentName;
    private String orderStatusName;
    private String commodityId;
    private String updateNm;
    private String consignee;
    private int commodityPrice;
    private int commodityNumber;
    private int expressCost;

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getUpdateNm() {
        return updateNm;
    }

    public void setUpdateNm(String updateNm) {
        this.updateNm = updateNm;
    }

    public String getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(String commodityId) {
        this.commodityId = commodityId;
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

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public int getCommodityPrice() {
        return commodityPrice;
    }

    public void setCommodityPrice(int commodityPrice) {
        this.commodityPrice = commodityPrice;
    }

    public int getCommodityNumber() {
        return commodityNumber;
    }

    public void setCommodityNumber(int commodityNumber) {
        this.commodityNumber = commodityNumber;
    }

    public int getExpressCost() {
        return expressCost;
    }

    public void setExpressCost(int expressCost) {
        this.expressCost = expressCost;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderUuid() {
        return orderUuid;
    }

    public void setOrderUuid(String orderUuid) {
        this.orderUuid = orderUuid;
    }

    public String getExpressNumber() {
        return expressNumber;
    }

    public void setExpressNumber(String expressNumber) {
        this.expressNumber = expressNumber;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getCommodityImage1() {
        return commodityImage1;
    }

    public void setCommodityImage1(String commodityImage1) {
        this.commodityImage1 = commodityImage1;
    }

    public String getCommoditySp() {
        return commoditySp;
    }

    public void setCommoditySp(String commoditySp) {
        this.commoditySp = commoditySp;
    }

    public String getCommodityColor() {
        return commodityColor;
    }

    public void setCommodityColor(String commodityColor) {
        this.commodityColor = commodityColor;
    }

    public String getCommodityNm() {
        return commodityNm;
    }

    public void setCommodityNm(String commodityNm) {
        this.commodityNm = commodityNm;
    }

    public String getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(String updateDt) {
        this.updateDt = updateDt;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    //    private String name;
//    private String chooseOne;
//    private String chooseTwo;
    private int number;
    private String total;
    private String carriage;



    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCarriage() {
        return carriage;
    }

    public void setCarriage(String carriage) {
        this.carriage = carriage;
    }


    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.orderId);
        dest.writeString(this.state);
        dest.writeString(this.businessId);
        dest.writeString(this.price);
        dest.writeString(this.updateDt);
        dest.writeString(this.commoditySp);
        dest.writeString(this.commodityColor);
        dest.writeString(this.commodityNm);
        dest.writeString(this.commodityImage1);
        dest.writeString(this.expressNumber);
        dest.writeString(this.expressCompany);
        dest.writeString(this.orderUuid);
        dest.writeString(this.orderStatus);
        dest.writeString(this.province);
        dest.writeString(this.city);
        dest.writeString(this.area);
        dest.writeString(this.detail);
        dest.writeString(this.phone);
        dest.writeString(this.createNm);
        dest.writeString(this.createDt);
        dest.writeString(this.paymentName);
        dest.writeString(this.orderStatusName);
        dest.writeString(this.commodityId);
        dest.writeString(this.updateNm);
        dest.writeInt(this.commodityPrice);
        dest.writeInt(this.commodityNumber);
        dest.writeInt(this.expressCost);
        dest.writeInt(this.number);
        dest.writeString(this.total);
        dest.writeString(this.carriage);
    }

    public FragmentMyOrderAllEntity() {
    }

    protected FragmentMyOrderAllEntity(Parcel in) {
        this.orderId = in.readString();
        this.state = in.readString();
        this.businessId = in.readString();
        this.price = in.readString();
        this.updateDt = in.readString();
        this.commoditySp = in.readString();
        this.commodityColor = in.readString();
        this.commodityNm = in.readString();
        this.commodityImage1 = in.readString();
        this.expressNumber = in.readString();
        this.expressCompany = in.readString();
        this.orderUuid = in.readString();
        this.orderStatus = in.readString();
        this.province = in.readString();
        this.city = in.readString();
        this.area = in.readString();
        this.detail = in.readString();
        this.phone = in.readString();
        this.createNm = in.readString();
        this.createDt = in.readString();
        this.paymentName = in.readString();
        this.orderStatusName = in.readString();
        this.commodityId = in.readString();
        this.updateNm = in.readString();
        this.commodityPrice = in.readInt();
        this.commodityNumber = in.readInt();
        this.expressCost = in.readInt();
        this.number = in.readInt();
        this.total = in.readString();
        this.carriage = in.readString();
    }

    public static final Parcelable.Creator<FragmentMyOrderAllEntity> CREATOR = new Parcelable.Creator<FragmentMyOrderAllEntity>() {
        @Override
        public FragmentMyOrderAllEntity createFromParcel(Parcel source) {
            return new FragmentMyOrderAllEntity(source);
        }

        @Override
        public FragmentMyOrderAllEntity[] newArray(int size) {
            return new FragmentMyOrderAllEntity[size];
        }
    };
}

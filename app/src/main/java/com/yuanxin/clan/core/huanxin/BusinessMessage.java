/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuanxin.clan.core.huanxin;

import android.os.Parcel;
import android.os.Parcelable;

import xiaofei.library.datastorage.annotation.ClassId;
import xiaofei.library.datastorage.annotation.ObjectId;

@ClassId("BusinessMessage")
public class BusinessMessage implements Parcelable{

    /**
     * businessAreaLogo : upload/images/image/20170905170532183.3.726-_750632d11013430da2f13d30130a59db.jpeg
     * businessAreaNm : 圆心商圈
     * businessMsgContent : 通天塔
     * businessMsgId : 30
     * businessMsgReadId : 28
     * businessMsgTitle : 通天塔
     * createDt : 2017-10-31 10:31:58.0
     * createId : 1439
     * createNm : 林浩杰
     * delFlg : 1
     * msgRead : 0
     * type : 3
     * userId : 1313
     */

    private String businessAreaLogo;
    private String businessAreaNm;
    private String businessMsgContent;
    private int businessMsgId;
    private int businessMsgReadId;
    private String businessMsgTitle;
    private String createDt;
    private int createId;
    private String createNm;
    private int delFlg;
    private int msgRead;
    private int type;
    private int userId;
    private SiteData siteData;
    private RepairsData repairsData;
    @ObjectId
    private String id;

    public BusinessMessage(){

    }
    public String getBusinessAreaLogo() {
        return businessAreaLogo;
    }

    public void setBusinessAreaLogo(String businessAreaLogo) {
        this.businessAreaLogo = businessAreaLogo;
    }

    public String getBusinessAreaNm() {
        return businessAreaNm;
    }

    public void setBusinessAreaNm(String businessAreaNm) {
        this.businessAreaNm = businessAreaNm;
    }

    public String getBusinessMsgContent() {
        return businessMsgContent;
    }

    public void setBusinessMsgContent(String businessMsgContent) {
        this.businessMsgContent = businessMsgContent;
    }

    public int getBusinessMsgId() {
        return businessMsgId;
    }

    public void setBusinessMsgId(int businessMsgId) {
        this.businessMsgId = businessMsgId;
    }

    public int getBusinessMsgReadId() {
        return businessMsgReadId;
    }

    public void setBusinessMsgReadId(int businessMsgReadId) {
        this.businessMsgReadId = businessMsgReadId;
    }

    public String getBusinessMsgTitle() {
        return businessMsgTitle;
    }

    public void setBusinessMsgTitle(String businessMsgTitle) {
        this.businessMsgTitle = businessMsgTitle;
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

    public int getMsgRead() {
        return msgRead;
    }

    public void setMsgRead(int msgRead) {
        this.msgRead = msgRead;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(businessAreaLogo);
        dest.writeString(businessAreaNm);
        dest.writeString(businessMsgContent);
        dest.writeString(businessMsgTitle);
        dest.writeString(createDt);
        dest.writeString(createNm);
        dest.writeInt(businessMsgId);
        dest.writeInt(businessMsgReadId);
        dest.writeInt(createId);
        dest.writeInt(delFlg);
        dest.writeInt(msgRead);
        dest.writeInt(type);
        dest.writeInt(userId);
        dest.writeSerializable(siteData);
        dest.writeSerializable(repairsData);
    }

    protected BusinessMessage(Parcel in) {
        this.businessAreaLogo = in.readString();
        this.businessAreaNm = in.readString();
        this.businessMsgContent = in.readString();
        this.businessMsgTitle = in.readString();
        this.createDt = in.readString();
        this.createNm = in.readString();
        this.businessMsgId = in.readInt();
        this.businessMsgReadId = in.readInt();
        this.createId = in.readInt();
        this.delFlg = in.readInt();
        this.msgRead = in.readInt();
        this.type = in.readInt();
        this.userId = in.readInt();
        this.siteData = (SiteData) in.readSerializable();
        this.repairsData = (RepairsData) in.readSerializable();
}

    public static final Creator<BusinessMessage> CREATOR = new Creator<BusinessMessage>() {
        public BusinessMessage createFromParcel(Parcel source) {
            return new BusinessMessage(source);
        }

        public BusinessMessage[] newArray(int size) {
            return new BusinessMessage[size];
        }
    };

    public SiteData getSiteData() {
        return siteData;
    }

    public void setSiteData(SiteData siteData) {
        this.siteData = siteData;
    }

    public RepairsData getRepairsData() {
        return repairsData;
    }

    public void setRepairsData(RepairsData repairsData) {
        this.repairsData = repairsData;
    }
}




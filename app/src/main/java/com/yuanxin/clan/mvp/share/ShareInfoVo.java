package com.yuanxin.clan.mvp.share;

import android.os.Parcel;
import android.os.Parcelable;

import cn.sharesdk.framework.Platform;

/**
 * 分享实体类
 *
 * @author chc
 */
public class ShareInfoVo implements Parcelable {
    private static final long serialVersionUID = 1L;
    private String title; //要分享的内容的标题
    private String content; //要分享的文字内容
    private String imgUrl; //图片的网络URL
    private String url; //要分享的网址
    private String imgFilePath; //要分享的本地图片的绝对路径
    private String platformName; //要分享的指定平台名称，一般不建议指定
    private int shareType = Platform.SHARE_WEBPAGE; //分享类型
    private int shareCardNum = -1;
    private int shareQiliaoType;

    public int getShareCardNum() {
        return shareCardNum;
    }

    public void setShareCardNum(int shareCardNum) {
        this.shareCardNum = shareCardNum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgFilePath() {
        return imgFilePath;
    }

    public void setImgFilePath(String imgFilePath) {
        this.imgFilePath = imgFilePath;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public int getShareType() {
        return shareType;
    }

    public void setShareType(int shareType) {
        this.shareType = shareType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.imgUrl);
        dest.writeString(this.url);
        dest.writeString(this.imgFilePath);
        dest.writeString(this.platformName);
        dest.writeInt(this.shareType);
        dest.writeInt(this.shareCardNum);
        dest.writeInt(this.shareQiliaoType);
    }

    public ShareInfoVo() {
    }

    protected ShareInfoVo(Parcel in) {
        this.title = in.readString();
        this.content = in.readString();
        this.imgUrl = in.readString();
        this.url = in.readString();
        this.imgFilePath = in.readString();
        this.platformName = in.readString();
        this.shareType = in.readInt();
        this.shareCardNum = in.readInt();
        this.shareQiliaoType = in.readInt();
    }

    public static final Creator<ShareInfoVo> CREATOR = new Creator<ShareInfoVo>() {
        public ShareInfoVo createFromParcel(Parcel source) {
            return new ShareInfoVo(source);
        }

        public ShareInfoVo[] newArray(int size) {
            return new ShareInfoVo[size];
        }
    };

    public int getShareQiliaoType() {
        return shareQiliaoType;
    }

    public void setShareQiliaoType(int shareQiliaoType) {
        this.shareQiliaoType = shareQiliaoType;
    }
}

package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/1/24.
 */
public class FragmentTwoOneEntity {
    //    private String images;
    private String desc;
    private String type;
    private String who;
    private String createdAt;
    private boolean is_select;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url;

//    public String getImages() {
//        return images;
//    }

//    public void setImages(String images) {
//        this.images = images;
//    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean is_select() {
        return is_select;
    }

    public void setIs_select(boolean is_select) {
        this.is_select = is_select;
    }
}


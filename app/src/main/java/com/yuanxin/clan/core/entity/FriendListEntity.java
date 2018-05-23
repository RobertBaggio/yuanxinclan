package com.yuanxin.clan.core.entity;

import java.io.Serializable;

/**
 * Created by lenovo1 on 2017/5/13.
 */
public class FriendListEntity implements Serializable{
    private String image;
    private String name;
    private String phone;
    private boolean is_checked;

    public boolean is_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

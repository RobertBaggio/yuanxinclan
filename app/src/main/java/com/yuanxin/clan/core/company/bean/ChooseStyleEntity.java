package com.yuanxin.clan.core.company.bean;

/**
 * Created by lenovo1 on 2017/4/22.
 */
public class ChooseStyleEntity {
    private String image;
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
}

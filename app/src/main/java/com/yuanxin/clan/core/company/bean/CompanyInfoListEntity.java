package com.yuanxin.clan.core.company.bean;

/**
 * Created by lenovo1 on 2017/4/23.
 */
public class CompanyInfoListEntity {
    private String title;
    private String time;
    private String epNewsId;
    private boolean is_checked;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;

    public boolean is_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public String getEpNewsId() {
        return epNewsId;
    }

    public void setEpNewsId(String epNewsId) {
        this.epNewsId = epNewsId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

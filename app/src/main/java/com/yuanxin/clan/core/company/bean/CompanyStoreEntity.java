package com.yuanxin.clan.core.company.bean;

/**
 * Created by lenovo1 on 2017/4/22.
 */
public class CompanyStoreEntity {
    private String store;
    private boolean is_checked;
    private String job_id;//选中
    private String epStoreId;

    public String getEpStoreId() {
        return epStoreId;
    }

    public void setEpStoreId(String epStoreId) {
        this.epStoreId = epStoreId;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public boolean is_checked() {
        return is_checked;
    }

    public void setIs_checked(boolean is_checked) {
        this.is_checked = is_checked;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }
}

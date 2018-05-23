package com.yuanxin.clan.core.entity;

/**
 * Created by lenovo1 on 2017/4/1.
 */
public class BeanNew {
    private String name;//商品属性名称<br/>
    private int nameLength;//商品属性名称的长度<br/>
    private boolean nameIsSelect;//商品属性是否选中<br/>

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNameLength() {
        return nameLength;
    }

    public void setNameLength(int nameLength) {
        this.nameLength = nameLength;
    }

    public boolean getNameIsSelect() {
        return nameIsSelect;
    }

    public void setNameIsSelect(boolean nameIsSelect) {
        this.nameIsSelect = nameIsSelect;
    }
}

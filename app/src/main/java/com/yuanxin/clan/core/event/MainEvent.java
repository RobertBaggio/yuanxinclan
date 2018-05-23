package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 主页切换事件
 * Author: xjc
 * Date: 2017/6/19 0019 12:43
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MainEvent {
    private int changId;

    public MainEvent(int changId) {
        this.changId = changId;
    }

    public int getChangId() {
        return changId;
    }

    public void setChangId(int changId) {
        this.changId = changId;
    }
}

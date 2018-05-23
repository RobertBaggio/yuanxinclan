package com.yuanxin.clan.core.event;

/**
 * ProjectName: yuanxinclan
 * Describe: 会话变更
 * Author: xjc
 * Date: 2017/6/8 0008 14:37
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ShareCardEvent {
    public ShareCardEvent(int num) {
        this.mCardNum = num;
    }
    public int getmCardNum() {
        return mCardNum;
    }

    public void setmCardNum(int mCardNum) {
        this.mCardNum = mCardNum;
    }

    private int mCardNum;
}

package com.yuanxin.clan.core.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.FriendDetail;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe: 查找好友
 * Author: xjc
 * Date: 2017/6/27 0027 10:01
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class FindFriendAdapter extends BaseQuickAdapter<FriendDetail, BaseViewHolder> {

    public FindFriendAdapter(@Nullable List<FriendDetail> data) {
        super(R.layout.item_findfriend, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FriendDetail item) {
        ImageManager.load(mContext, item.getUserImage(), R.drawable.by, (ImageView) helper.getView(R.id.avatar));
        helper.setText(R.id.em_activity_add_contact_name, item.getUserNm());
        helper.setText(R.id.em_activity_add_contact_phone, item.getUserPhone());
        helper.addOnClickListener(R.id.indicator);
    }
}

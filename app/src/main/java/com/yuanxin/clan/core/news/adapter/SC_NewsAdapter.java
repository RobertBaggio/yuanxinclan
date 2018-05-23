package com.yuanxin.clan.core.news.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.news.bean.NewEntity;
import com.yuanxin.clan.core.util.TimeUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/6 0006 17:33
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */


public class SC_NewsAdapter extends BaseQuickAdapter<NewEntity, BaseViewHolder> {
    public SC_NewsAdapter(@Nullable List<NewEntity> data) {
        super(R.layout.sclayout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewEntity item) {
        ImageManager.load(mContext, item.getNews().getImg(), R.drawable.list_img, (ImageView) helper.getView(R.id.item_gank_image));
        Log.v("lgq","...."+item.getNews().getImg());
        helper.setText(R.id.item_gank_desc, item.getNews().getTitle());//标题
        helper.setText(R.id.item_gank_who, item.getNews().getCreateDt());//类型
        if (item.getCreateDt() == null) {
            helper.setText(R.id.item_gank_createdAt, null);
        } else {
            helper.setText(R.id.item_gank_createdAt, TimeUtil.timeFormat(item.getCreateDt()));//创建时间
        }
    }
}

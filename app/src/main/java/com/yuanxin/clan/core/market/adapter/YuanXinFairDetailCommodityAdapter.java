package com.yuanxin.clan.core.market.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.CommdoityDetail;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe: 集市商品列表
 * Author: xjc
 * Date: 2017/6/13 0013 18:21
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class YuanXinFairDetailCommodityAdapter extends BaseQuickAdapter<CommdoityDetail, BaseViewHolder> {

    public YuanXinFairDetailCommodityAdapter(@Nullable List<CommdoityDetail> data) {
        super(R.layout.item_yuan_xin_fair_company, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CommdoityDetail item) {
        helper.setText(R.id.tvGoodName, item.getCommodityNm());
        helper.setText(R.id.tvGoodPrice, "￥" + item.getCommodityPrice());
        if (!TextUtil.isEmpty(item.getCommodityImage1())) {//无用
            ImageManager.load(mContext, item.getCommodityImage1(), R.drawable.by, (ImageView) helper.getView(R.id.imgGoodImage));
        }
    }
}

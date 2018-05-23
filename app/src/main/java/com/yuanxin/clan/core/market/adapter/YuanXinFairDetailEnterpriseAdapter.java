package com.yuanxin.clan.core.market.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.EnterpriseDetail;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe: 集市详情企业logo
 * Author: xjc
 * Date: 2017/6/13 0013 18:01
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class YuanXinFairDetailEnterpriseAdapter extends BaseQuickAdapter<EnterpriseDetail, BaseViewHolder> {

    public YuanXinFairDetailEnterpriseAdapter(@Nullable List<EnterpriseDetail> data) {
        super(R.layout.item_fair_detail_enterprise, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseDetail item) {
        if (!TextUtil.isEmpty(item.getEpImage1())) { //无用
            ImageManager.load(mContext, item.getEpImage1(), R.drawable.by, (ImageView) helper.getView(R.id.imgLogo));
        }
    }
}

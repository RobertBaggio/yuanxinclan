package com.yuanxin.clan.core.company.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.BestCompanyEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe: 资讯页面适配器
 * Author: xjc
 * Date: 2017/6/20 0020 14:35
 * Date: 2017/6/20 0020 14:35
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class RecommendCompanyAdapter extends BaseMultiItemQuickAdapter<BestCompanyEntity, BaseViewHolder> {
    public RecommendCompanyAdapter(@Nullable List<BestCompanyEntity> data) {
        super(data);
        addItemType(BestCompanyEntity.style_0, R.layout.recommend_company_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, BestCompanyEntity item) {
        switch (helper.getItemViewType()) {
            case BestCompanyEntity.style_0:
                ImageManager.loadBitmap(mContext, Url.img_domain+item.getEpImage1()+Url.imageStyle640x640, R.drawable.list_img, (MLImageView) helper.getView(R.id.image));
                helper.setText(R.id.title, item.getEpNm());
                helper.setText(R.id.content, item.getEpDetail());
                helper.setText(R.id.label, item.getLabel());
                break;
        }
    }
}

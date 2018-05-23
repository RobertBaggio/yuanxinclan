package com.yuanxin.clan.core.news.adapter;

import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/15 0015 17:53
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class NewQiYeKuAdater extends BaseQuickAdapter<CompanyDetail, BaseViewHolder> {
    public NewQiYeKuAdater(@Nullable List<CompanyDetail> data) {
        super(R.layout.item_company_information_detail, data);
        Log.v("lgq",".lllllllllllNewQiYeKuAdaterllll");
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyDetail item) {
        ImageManager.load(mContext, item.getEpImage1(), R.drawable.list_img, (ImageView) helper.getView(R.id.item_company_information_detail_image));
        helper.setText(R.id.item_company_information_detail_name, item.getEpNm());//标题
        helper.setText(R.id.item_company_information_detail_introduce, item.getEpDetail());//类型
        Log.v("lgq",".lllllllllllllll"+item.getEpNm());
        if (item.getAddress().getArea() == null) {
//            holder.itemCompanyInformationDetailArea.setText(null);//地区
            helper.getView(R.id.item_company_information_detail_area).setVisibility(View.GONE);
//            holder.itemCompanyInformationDetailArea.setVisibility(View.GONE);//地区
        } else {
            helper.setText(R.id.item_company_information_detail_area, item.getAddress().getArea());//标题
//            holder.itemCompanyInformationDetailArea.setText(entity.getAddress().getArea());//地区
        }
//        if (entity.getIndustry() == null) {
        if (item.getIndustry().getIndustryNm() == null) {
//            holder.itemCompanyInformationDetailType.setText(null);//行业类型
            helper.getView(R.id.item_company_information_detail_type).setVisibility(View.GONE);
//            holder.itemCompanyInformationDetailType.setVisibility(View.GONE);//行业类型
        } else {
            helper.setText(R.id.item_company_information_detail_type, item.getIndustry().getIndustryNm());//标题
//            holder.itemCompanyInformationDetailType.setText(entity.getIndustry().getIndustryNm());//行业类型
        }
    }
}

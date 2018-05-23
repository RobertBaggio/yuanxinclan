package com.yuanxin.clan.core.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.BusinessDistrictListEntity;
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

public class BusinessWholeSearchAdapter extends BaseMultiItemQuickAdapter<BusinessDistrictListEntity, BaseViewHolder> {
    private OnItemClickListener mOnItemClickListener;
    private String keyWord;
    public BusinessWholeSearchAdapter(@Nullable List<BusinessDistrictListEntity> data, String keyword) {
        super(data);
        addItemType(BusinessDistrictListEntity.style_0, R.layout.item_shangquan_title);
        addItemType(BusinessDistrictListEntity.style_1, R.layout.item_shangquan);
        keyWord = keyword;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BusinessDistrictListEntity item) {
        switch (helper.getItemViewType()) {
            case BusinessDistrictListEntity.style_0:
                String result = "";
                switch (item.getBusinessAreaGenre()) {
                    case "1":
                        result = "商会";
                        break;
                    case "2":
                        result = "协会";
                        break;
                    case "3":
                        result = "圈子";
                        break;
                    case "4":
                        result = "园区";
                        break;
                }
                helper.setText(R.id.title, "与"+ keyWord +"相关的"+ result);
                break;
            case BusinessDistrictListEntity.style_1:
                ImageManager.load(mContext, item.getImage(), R.drawable.banner01, (ImageView) helper.getView(R.id.item_business_district_library_image));//
//        Log.v("Lgq","tp....."+entity.getImage());
                helper.setText(R.id.item_gank_namete, item.getBusinessAreaNm());////名称
                helper.setText(R.id.addresste, item.getCity());//地址
//        Log.v("lgq","l............商系000==="+entity.getBusinessAreaType());
                helper.setText(R.id.leixingte, item.getIndustryNm());//企业类型
                helper.setText(R.id.huiyuante, String.valueOf(item.getMemberShip()));//人数
                helper.setText(R.id.shoucte, String.valueOf(item.getCollectionCount()));//收藏人数
//        holder.fuwuzhonglei.setText(String.valueOf(entity.getIndustryNm()));//服务种类
                helper.setText(R.id.shangxite, item.getBusinessAreaType());//商系
                helper.setText(R.id.neirongte, item.getBusinessAreaDetail());
                //轮播

                //判断是否设置了监听器
                if (mOnItemClickListener != null) {
                    //为ItemView设置监听器
                    helper.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onItemClick(helper.itemView, item); // 2
                        }
                    });
                }
                break;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, BusinessDistrictListEntity entity);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
}

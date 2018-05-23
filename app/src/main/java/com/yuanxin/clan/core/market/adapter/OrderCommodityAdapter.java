package com.yuanxin.clan.core.market.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.OrderCommodity;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/6/14 0014 15:30
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class OrderCommodityAdapter extends BaseQuickAdapter<OrderCommodity, BaseViewHolder> {

    private OnBidChangeListener mOnBidChangeListener;

    public OrderCommodityAdapter(@Nullable List<OrderCommodity> data) {
        super(R.layout.item_order_commodity, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderCommodity item) {
        if (!TextUtil.isEmpty(item.getCommodityImage1())) {
            ImageManager.load(mContext, item.getCommodityImage1(), R.drawable.by, (ImageView) helper.getView(R.id.imgGoodimage));
        }
        helper.setText(R.id.tvGoodName, "商品 "+item.getCommodityNm());
        helper.setText(R.id.tvGoodPrice, "￥" + item.getCommodityPrice());
        helper.setText(R.id.tvGoodNumber, "x" + item.getCommodityNumber());
        helper.setText(R.id.tvGoodSend, "运费" + item.getExpressCost());
        helper.setText(R.id.typetobuyte, "规格 " + item.getCommoditySp());
        helper.setText(R.id.colortobuyte, "颜色  " + item.getCommodityColor());
        if (item.getCommodityColor().equals("-1")){
            helper.setVisible(R.id.colortobuyte,false);
        }else {
            helper.setVisible(R.id.colortobuyte,true);
        }

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnBidChangeListener.onCheckedChange(item.getCommodityId());
            }
        });

        if (!TextUtil.isEmpty(item.getCommodityImage1())){
            helper.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnBidChangeListener.onCheckedChange(item.getCommodityId());
                }
            });

        }


    }

    public interface OnBidChangeListener {
        void onCheckedChange(String id);
    }

    public void setBidListentr(OnBidChangeListener bidListentr){
        this.mOnBidChangeListener =bidListentr;
    }



}

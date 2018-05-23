package com.yuanxin.clan.core.market.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.PostAddress;
import com.yuanxin.clan.core.market.view.EditOrderAddressActivity;
import com.yuanxin.clan.core.market.view.OrderAddressActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/28 0028 11:27
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class OrderAddressAdapter extends RecyclerView.Adapter<OrderAddressAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    private List<PostAddress> entityList;
    private OrderAddressActivity activity;


    public OrderAddressAdapter(Context context, List<PostAddress> entities, OrderAddressActivity activity) {
        this.context = context;
        this.entityList = entities;
        this.activity = activity;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_goods_address, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PostAddress entity = entityList.get(position);
        holder.itemGoodsAddressNameText.setText(entity.getReceiver());//名字
        holder.itemGoodsAddressPhoneText.setText(entity.getPhone());//电话
        holder.itemGoodsAddressAddressText.setText(entity.getProvince() + entity.getCity() + entity.getArea() + entity.getDetail());//地址
        if (entity.getDefaultAddress() == 1) {
//            holder.itemGoodsAddressAllLayout.setBackgroundResource(R.drawable.border_red_no);
            holder.itemGoodsAddressAllLayout.setBackgroundResource(R.drawable.while_graysolid);
            holder.itemGoodsAddressImage.setVisibility(View.VISIBLE);
        } else {
//            holder.itemGoodsAddressAllLayout.setBackgroundResource(R.drawable.border_grey_no);
            holder.itemGoodsAddressAllLayout.setBackgroundResource(R.drawable.while_graysolid);
            holder.itemGoodsAddressImage.setVisibility(View.GONE);
        }
        holder.itemGoodsAddressNameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditOrderAddressActivity.class);
                intent.putExtra("address", (Serializable) entity);
                activity.startActivityForResult(intent, 301);
            }
        });

        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器-
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_goods_address_image)
        ImageView itemGoodsAddressImage;
        @BindView(R.id.item_goods_address_namet)
        TextView itemGoodsAddressNameText;
        @BindView(R.id.item_goods_address_name_edit)
        TextView itemGoodsAddressNameEdit;
        @BindView(R.id.item_goods_address_phone_text)
        TextView itemGoodsAddressPhoneText;
        @BindView(R.id.item_goods_address_phone_layout)
        LinearLayout itemGoodsAddressPhoneLayout;
        @BindView(R.id.item_goods_address_address)
        TextView itemGoodsAddressAddress;
        @BindView(R.id.item_goods_address_address_text)
        TextView itemGoodsAddressAddressText;
        @BindView(R.id.item_goods_address_layout)
        LinearLayout itemGoodsAddressLayout;
        @BindView(R.id.item_goods_address_all_layout)
        RelativeLayout itemGoodsAddressAllLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


}


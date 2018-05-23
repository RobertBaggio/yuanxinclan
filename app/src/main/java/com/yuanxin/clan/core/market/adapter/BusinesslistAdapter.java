package com.yuanxin.clan.core.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.BusinesslistEntity;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/29 0029 16:33
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class BusinesslistAdapter extends RecyclerView.Adapter<BusinesslistAdapter.ViewHolder> {

    private List<BusinesslistEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnItemPhClickListener mOnItemPhClickListener;

    public BusinesslistAdapter(Context context, List<BusinesslistEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.businesslist_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        BusinesslistEntity entity = entityList.get(position);
//        Log.v("lgq","..........."+entity.getExIndustryLog());
//        ImageManager.load(context, entity.getExIndustryLog(), R.drawable.banner01, holder.item_company_member_head_image);
        ImageManager.loadBitmap(context, entity.getExIndustryLog(), R.drawable.chat_icon_personal, holder.item_company_member_head_image);
//        ImageManager.load(context, "http://images.yxtribe.com//upload/images/market/20170701145124172.jpg-style_webp_640x640", R.drawable.banner01, holder.mItemYuanXinFairNewImage);
        try{
            holder.item_company_member_name.setText(entity.getExIndustryNm());

        }catch (Exception e){
            e.printStackTrace();
        }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        holder.item_company_member_head_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition(); // 1
                mOnItemClickListener.onItemClick(holder.itemView, position); // 2
            }
        });


    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_company_member_head_image)
        MLImageView item_company_member_head_image;
        @BindView(R.id.item_company_member_name)
        TextView item_company_member_name;



        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }
    public interface OnItemPhClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemPhClickListener(OnItemPhClickListener mOnItemClickListener) {
        this.mOnItemPhClickListener = mOnItemClickListener;
    }

}


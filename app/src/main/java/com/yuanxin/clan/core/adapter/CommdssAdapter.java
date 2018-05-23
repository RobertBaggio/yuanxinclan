package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.CommodityEntity;
import com.yuanxin.clan.mvp.MainApplication;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/8/5 0005 16:01
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class CommdssAdapter {
//}
public class CommdssAdapter extends RecyclerView.Adapter<CommdssAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<CommodityEntity> entityList;



    public CommdssAdapter(Context context, List<CommodityEntity> entities) {
        this.context = context;
        this.entityList = entities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_commdityss, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CommodityEntity entity = entityList.get(position);
        int w = MainApplication.getnScreenWidth();
//        Log.v("lgq","wwwwwwwwwwww=="+entity.getCommodityImage1());
        holder.itemView.setMinimumWidth(w);
        if (position%2==0){
            holder.itemView.setPadding(0,0,8,0);
        }else {
            holder.itemView.setPadding(8,0,0,0);
        }
        ImageManager.loadBitmap(context, entity.getCommodityImage1(), R.drawable.list_img, holder.activity_news_chaimage);
        try{
            holder.activity_news_name.setText(entity.getCommodityNm());//企业名称
            holder.activity_news_content.setText(entity.getCommodityDetail());//简介
            String price;
            if (entity.getMaxPrice()==entity.getMinPrice()){
                price=entity.getMaxPrice()+"";
            }else {
                price=entity.getMinPrice()+"-"+entity.getMaxPrice();
            }
            holder.activity_nprice.setText(price);
        }catch(Exception e){
            e.printStackTrace();
        }
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
        @BindView(R.id.activity_news_chaimage)
        ImageView activity_news_chaimage;
        @BindView(R.id.activity_news_name)
        TextView activity_news_name;
        @BindView(R.id.activity_news_content)
        TextView activity_news_content;
        @BindView(R.id.activity_nprice)
        TextView activity_nprice;

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

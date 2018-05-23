package com.yuanxin.clan.core.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.TradesZGEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/29 0029 14:00
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class TradeslistAdapter  extends RecyclerView.Adapter<TradeslistAdapter.ViewHolder> {

    private List<TradesZGEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public TradeslistAdapter(Context context, List<TradesZGEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tradeslist_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TradesZGEntity entity = entityList.get(position);
        if (entity.getOneid()==0){
            holder.tradesifshowli.setVisibility(View.VISIBLE);
            holder.ifshouwte.setVisibility(View.VISIBLE );
        }else {
            holder.tradesifshowli.setVisibility(View.GONE);
            holder.ifshouwte.setVisibility(View.GONE );
        }
        try{
            holder.tradesdetailte.setText(entity.getHallDes());
            holder.tradesnamete.setText(entity.getHallNm());
            holder.tradespricete.setText(entity.getProvince());
        }catch (Exception e){
            e.printStackTrace();
        }
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
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

        @BindView(R.id.tradesnamete)
        TextView tradesnamete;
        @BindView(R.id.tradesdetailte)
        TextView tradesdetailte;
        @BindView(R.id.tradespricete)
        TextView tradespricete;
        @BindView(R.id.ifshouwte)
        TextView ifshouwte;
        @BindView(R.id.tradesifshowli)
        LinearLayout tradesifshowli;

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


}

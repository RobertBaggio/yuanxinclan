package com.yuanxin.clan.core.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.TradesEntity;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/29 0029 15:40
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class NeardtradesAdapter extends RecyclerView.Adapter<NeardtradesAdapter.ViewHolder> {

    private List<TradesEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnItemPhClickListener mOnItemPhClickListener;

    public NeardtradesAdapter(Context context, List<TradesEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.tradesstatus_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        TradesEntity entity = entityList.get(position);
//        Log.v("lgq","..........."+entity.getMarketImg());
        ImageManager.load(context, entity.getExhibitionTitleImg(), R.drawable.banner01, holder.marketiamge);
//        ImageManager.load(context, "http://images.yxtribe.com//upload/images/market/20170701145124172.jpg-style_webp_640x640", R.drawable.banner01, holder.mItemYuanXinFairNewImage);
        try{
            holder.marketnamete.setText(entity.getExhibitionTitle());
            holder.markettime.setText(DateDistance.getDistanceTimeToZW(entity.getStarTime())+" 至 "+DateDistance.getDistanceTimeToZW(entity.getEndTime()));
            holder.tradesstatuste.setText(entity.getStatus());
            if (TextUtil.isEmpty(entity.getApart())){
                if (entity.getStatus().equals("报名中"));{
                    holder.tradesstatuste.setTextColor(context.getResources().getColor(R.color.businesstop));
                    holder.tradesstatuste.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemPhClickListener.onItemClick(v,position);
                        }
                    });

                }
                if (entity.getStatus().equals("已结束")){
                    holder.tradesstatuste.setTextColor(context.getResources().getColor(R.color.login_grey));
                }
            }else {
                holder.tradesstatuste.setText(entity.getApart()+" km");
                holder.tradesstatuste.setTextColor(context.getResources().getColor(R.color.businesstop));
            }

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
        @BindView(R.id.marketiamge)
        ImageView marketiamge;
        @BindView(R.id.marketnamete)
        TextView marketnamete;
        @BindView(R.id.markettime)
        TextView markettime;
        @BindView(R.id.tradesstatuste)
        TextView tradesstatuste;


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

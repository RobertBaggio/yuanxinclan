package com.yuanxin.clan.core.adapter.indicator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.MyService_inEntity;
import com.yuanxin.clan.core.market.bean.ShoppingCommodity;
import com.yuanxin.clan.core.util.DateDistance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/3 0003 16:45
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyServiceINadapter extends RecyclerView.Adapter<MyServiceINadapter.ViewHolder> {

    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnBidChangeListener mOnBidChangeListener;
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;
    private List<MyService_inEntity> inEntity = new ArrayList<>();

    public MyServiceINadapter(Context context, List<MyService_inEntity> list) {
        this.context = context;
        this.inEntity = list;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wodedetail, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyService_inEntity entity = inEntity.get(position);

        holder.wufudetailcontentte.setText(entity.getExplainReason());
        holder.wufudetailtimete.setText(DateDistance.timetodatedetail(entity.getExplainStartDt()));
        if (position==0){
            holder.yuandiante.setBackgroundResource(R.drawable.red_shixin_yuan);
        }else {
            holder.yuandiante.setBackgroundResource(R.drawable.lan_yuanshixin);
        }
        if (position==inEntity.size()-1){
            holder.changlineli.setVisibility(View.GONE);
        }

        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
////                    mOnMyCheckedChangeListener.onCheckedChange(buyList);//选择框 选没选 监听
//                    mOnItemClickListener.onItemClick(v,position);
//                }
//            });
        }
    }


    @Override
    public int getItemCount() {
        return inEntity.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.wufudetailtimete)
        TextView wufudetailtimete;
        @BindView(R.id.wufudetailcontentte)
        TextView wufudetailcontentte;
        @BindView(R.id.yuandiante)
        TextView yuandiante;
        @BindView(R.id.changlineli)
        TextView changlineli;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //子项点击事件1
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //返回选中的ITEM数目
    public interface OnMyCheckedChangeListener {
        void onCheckedChange(List<ShoppingCommodity> buyList);
    }

    public interface OnBidChangeListener {
        void onCheckedChange(boolean cc,int id);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
    }

    public void setBidListentr(OnBidChangeListener bidListentr){
        this.mOnBidChangeListener =bidListentr;
    }

}

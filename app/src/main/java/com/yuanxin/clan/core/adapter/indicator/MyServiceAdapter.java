package com.yuanxin.clan.core.adapter.indicator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.MyService_inEntity;
import com.yuanxin.clan.core.entity.MyService_outEntity;
import com.yuanxin.clan.core.market.bean.ShoppingCommodity;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/30 0030 17:25
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyServiceAdapter extends RecyclerView.Adapter<MyServiceAdapter.ViewHolder> {

    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnBidChangeListener mOnBidChangeListener;
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;
    private List<MyService_inEntity> inEntity = new ArrayList<>();
    private List<MyService_outEntity> outEntity = new ArrayList<>();

    public MyServiceAdapter(Context context, List<MyService_inEntity> list, List<MyService_outEntity> list2) {
        this.context = context;
        this.inEntity = list;
        this.outEntity = list2;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_wodefuwu, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyService_inEntity entity = inEntity.get(position);
        if (entity.getOneid()==0&&entity.getMyid()==outEntity.get(position).getOneid()){
            holder.wodefuwuifshouli.setVisibility(View.VISIBLE);
//            holder.iflishow.setVisibility(View.VISIBLE);
            for (int i = 0;i<outEntity.size();i++){
                if (entity.getMyid()==outEntity.get(i).getOneid()){
                    holder.fuwudanhaote.setText(outEntity.get(position).getOrderUuid());
                    if (outEntity.get(position).getOrderStatus().equals("8")){
                        holder.fuwuztte.setText("服务已结束");
                    }else {
                        holder.fuwuztte.setText("服务进行中");
                        holder.fuwuztte.setTextColor(context.getResources().getColor(R.color.fflqiliusaner));
                    }
                }
            }
        }

        if (!TextUtil.isEmpty(entity.getCommodityImage1())) {
            ImageManager.load(context, entity.getCommodityImage1(), R.drawable.list_img, holder.itemShoppingCartImage);
        }
        if (entity.getProcedureId().equals("1")){
            holder.neiztte.setText("服务已结束");
        }else if (entity.getProcedureId().equals("0")){
            holder.neiztte.setText("服务未开始");
        }else {
            holder.neiztte.setText(entity.getProcedureName());
        }

        holder.fuwuneironte.setText(entity.getCommodityNm());
        holder.fuwutimete.setText(entity.getUpdateDt());
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mOnMyCheckedChangeListener.onCheckedChange(buyList);//选择框 选没选 监听
                    mOnItemClickListener.onItemClick(v,position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return inEntity.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.wodefuwuifshouli)
        LinearLayout wodefuwuifshouli;
        @BindView(R.id.fuwuimage)
        ImageView itemShoppingCartImage;
        @BindView(R.id.fuwudanhaote)
        TextView fuwudanhaote;
        @BindView(R.id.fuwuztte)
        TextView fuwuztte;
        @BindView(R.id.fuwuneironte)
        TextView fuwuneironte;
        @BindView(R.id.fuwutimete)
        TextView fuwutimete;
        @BindView(R.id.neiztte)
        TextView neiztte;

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

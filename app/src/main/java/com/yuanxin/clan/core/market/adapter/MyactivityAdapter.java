package com.yuanxin.clan.core.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.Myactivityentity;
import com.yuanxin.clan.core.util.DateDistance;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/21 0021 9:55
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyactivityAdapter extends RecyclerView.Adapter<MyactivityAdapter.ViewHolder> {

    private List<Myactivityentity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public MyactivityAdapter(Context context, List<Myactivityentity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_myactivitylayout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Myactivityentity entity = entityList.get(position);
//        Log.v("lgq","..........."+entity.getMarketImg());
//        ImageManager.load(context, entity.getMarketImg(), R.drawable.banner01, holder.mItemYuanXinFairNewImage);
//        ImageManager.load(context, "http://images.yxtribe.com//upload/images/market/20170701145124172.jpg-style_webp_640x640", R.drawable.banner01, holder.mItemYuanXinFairNewImage);
        try{
            holder.topnamte.setText(entity.getTnmae());
            holder.unamete.setText(entity.getUname());
            holder.onetimete.setText(DateDistance.getDistanceTimeToHM(entity.getStarttime()));
            holder.twotimete.setText(DateDistance.getDistanceTimeToHM(entity.getEndtime()));
        }catch (Exception e){
            e.printStackTrace();
        }
//        Log.v("lgq","tp===="+entity.getMarketImg());
//        holder.mItemYuanXinFairNewName.setText(TextUtil.formatString(entity.getMarketNm()));
//        holder.mItemYuanXinFairNewArea.setText(TextUtil.formatString(entity.getCity()));
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(position); // 2
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.topnamte)
        TextView topnamte;
        @BindView(R.id.unamete)
        TextView unamete;
        @BindView(R.id.onetimete)
        TextView onetimete;
        @BindView(R.id.twotimete)
        TextView twotimete;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}

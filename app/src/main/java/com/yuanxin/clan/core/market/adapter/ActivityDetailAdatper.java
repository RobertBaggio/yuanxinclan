package com.yuanxin.clan.core.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.HuiyiBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/21 0021 13:45
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ActivityDetailAdatper extends RecyclerView.Adapter<ActivityDetailAdatper.ViewHolder> {

    private List<HuiyiBean> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public ActivityDetailAdatper(Context context, List<HuiyiBean> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activitydetail, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        NewMarketitem entity = entityList.get(position);
//        Log.v("lgq","..........."+entity.getMarketImg());
//        ImageManager.load(context, entity.getMarketImg(), R.drawable.banner01, holder.mItemYuanXinFairNewImage);
        holder.namete.setText(entityList.get(position).getName());
//        zixunshouchangimage.setImageResource(R.drawable.news_icon_collect_pre);
        if (entityList.get(position).getType().equals("png")){
            holder.image1.setImageResource(R.drawable.png);
        }else if (entityList.get(position).getType().equals("docx")){
            holder.image1.setImageResource(R.drawable.doc);
        }else if (entityList.get(position).getType().equals("pdf")){
            holder.image1.setImageResource(R.drawable.pdf);
        }else if (entityList.get(position).getType().equals("ppt")){
            holder.image1.setImageResource(R.drawable.ppt);
        }else {
            holder.image1.setImageResource(R.drawable.nullpng);
        }
//        ImageManager.load(context, "http://images.yxtribe.com//upload/images/market/20170701145124172.jpg-style_webp_640x640", R.drawable.banner01, holder.mItemYuanXinFairNewImage);
        //判断是否设置了监听器
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
        @BindView(R.id.image1)
        ImageView image1;
        @BindView(R.id.namete)
        TextView namete;
        @BindView(R.id.li1)
        LinearLayout li1;


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

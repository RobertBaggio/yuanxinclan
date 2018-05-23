package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.GongXuEntity;
import com.yuanxin.clan.core.util.DateDistance;
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
 * Date: 2017/10/26 0026 16:17
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class AllGongXuAdapter extends RecyclerView.Adapter<AllGongXuAdapter.ViewHolder> {
    private List<GongXuEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public AllGongXuAdapter(Context context, List<GongXuEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_gongxu, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        GongXuEntity entity = entityList.get(position);
        if (!TextUtil.isEmpty(entity.getImage1())){
            holder.gongxuimage1.setVisibility(View.VISIBLE);
            ImageManager.load(context, entity.getImage1(), R.drawable.banner01, holder.gongxuimage1);//
        } else {
            holder.gongxuimage1.setVisibility(View.GONE);
        }
        if (!TextUtil.isEmpty(entity.getImage2())){
            holder.gongxuimage2.setVisibility(View.VISIBLE);
            ImageManager.load(context, entity.getImage2(), R.drawable.banner01, holder.gongxuimage2);//
        } else {
            holder.gongxuimage2.setVisibility(View.GONE);
        }
        if (!TextUtil.isEmpty(entity.getImage3())){
            holder.gongxuimage3.setVisibility(View.VISIBLE);
            ImageManager.load(context, entity.getImage3(), R.drawable.banner01, holder.gongxuimage3);//
        } else {
            holder.gongxuimage3.setVisibility(View.GONE);
        }
        if (TextUtil.isEmpty(entity.getImage1()) && TextUtil.isEmpty(entity.getImage2()) && TextUtil.isEmpty(entity.getImage3())){
            holder.ifshouli.setVisibility(View.GONE);
        } else {
            holder.ifshouli.setVisibility(View.VISIBLE);
        }
        if (entity.getSupplyDemand().equals("0")){
            holder.gongyingte.setVisibility(View.VISIBLE);
            holder.xuqiute.setVisibility(View.GONE);
            holder.gongyingleixingte.setText("供应");
            holder.item_gximage.setImageResource(R.drawable.gong_ying2);
        }else {
            holder.gongyingte.setVisibility(View.GONE);
            holder.xuqiute.setVisibility(View.VISIBLE);
            holder.gongyingleixingte.setText("需求");
            holder.item_gximage.setImageResource(R.drawable.gong_ying);
        }
//        Log.v("Lgq","tp....."+entity.getImage());
        holder.gongxuneirongte.setText("             "+entity.getContent());
        holder.item_gxtitlete.setText(entity.getTitle());
        holder.gongxudizite.setText(entity.getCity());
        holder.gongxutimete.setText(DateDistance.timetodate(entity.getCreateDt()));//地址

        //轮播

        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
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
        @BindView(R.id.gongxuimage1)
        ImageView gongxuimage1;
        @BindView(R.id.gongxuimage3)
        ImageView gongxuimage3;
        @BindView(R.id.gongxuimage2)
        ImageView gongxuimage2;
        @BindView(R.id.item_gximage)
        ImageView item_gximage;
        @BindView(R.id.gongyingte)
        TextView gongyingte;
        @BindView(R.id.xuqiute)
        TextView xuqiute;
        @BindView(R.id.gongxuneirongte)
        TextView gongxuneirongte;
        @BindView(R.id.gongxudizite)
        TextView gongxudizite;
        @BindView(R.id.gongyingleixingte)
        TextView gongyingleixingte;
        @BindView(R.id.ifshouli)
        LinearLayout ifshouli;
        @BindView(R.id.gongxutimete)
        TextView gongxutimete;
        @BindView(R.id.item_gxtitlete)
        TextView item_gxtitlete;



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

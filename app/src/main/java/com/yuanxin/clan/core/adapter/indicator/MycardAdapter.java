package com.yuanxin.clan.core.adapter.indicator;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.MyCardEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/20 0020 9:32
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MycardAdapter extends RecyclerView.Adapter<MycardAdapter.ViewHolder> {
    private List<MyCardEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnLockClickListener mOnLockClickListener;
    private OnOnlickKClickListener mOnOnlickKClickListener;
    private OnPhoneKClickListener mOnPhoneKClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public MycardAdapter(Context context, List<MyCardEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mycard, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MyCardEntity entity = entityList.get(position);
        ImageManager.load(context, entity.getImage(), R.drawable.banner01, holder.companeimage);//
//        Log.v("Lgq","tp....."+entity.getImage());
        holder.companynamete.setText(entity.getCompanyname());////名称
        holder.namete.setText(entity.getUname());//地址

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
        holder.lockcompanyte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnLockClickListener.onItemClick(position);
            }
        });
        holder.onlicklinkte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnOnlickKClickListener.onItemClick(position);
            }
        });
        holder.phonelinkte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnPhoneKClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.item_business_district_library_view_flow)
//        ViewFlow itemBusinessDistrictLibraryViewFlow;
        //轮播
//        @BindView(R.id.item_business_district_library_view_flow_indic)
//        CircleFlowIndicator itemBusinessDistrictLibraryViewFlowIndic;
        @BindView(R.id.companeimage)
        ImageView companeimage;
        @BindView(R.id.companynamete)
        TextView companynamete;
        @BindView(R.id.namete)
        TextView namete;
        @BindView(R.id.lockcompanyte)
        TextView lockcompanyte;
        @BindView(R.id.onlicklinkte)
        TextView onlicklinkte;
        @BindView(R.id.phonelinkte)
        TextView phonelinkte;


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

    public interface OnLockClickListener {
        void onItemClick( int position);
    }

    public void setOnLockClickListener(OnLockClickListener mOnItemClickListener) {
        this.mOnLockClickListener = mOnItemClickListener;
    }

    public interface OnOnlickKClickListener {
        void onItemClick( int position);
    }

    public void setOnlickClickListener(OnOnlickKClickListener mOnItemClickListener) {
        this.mOnOnlickKClickListener = mOnItemClickListener;
    }
    public interface OnPhoneKClickListener {
        void onItemClick( int position);
    }

    public void setOnPhonelickListener(OnPhoneKClickListener mOnItemClickListener) {
        this.mOnPhoneKClickListener = mOnItemClickListener;
    }

}

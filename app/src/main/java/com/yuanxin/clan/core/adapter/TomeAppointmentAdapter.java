package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.AppointmentEntity;
import com.yuanxin.clan.core.util.DateDistance;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/21 0021 17:28
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class TomeAppointmentAdapter extends RecyclerView.Adapter<TomeAppointmentAdapter.ViewHolder> {
    private List<AppointmentEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnLockClickListener mOnLockClickListener;
    private OnOnlickKClickListener mOnOnlickKClickListener;
    private OnPhoneKClickListener mOnPhoneKClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public TomeAppointmentAdapter(Context context, List<AppointmentEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_appointmenttome, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AppointmentEntity entity = entityList.get(position);
        holder.appointmentenamete.setText(entity.getEname());////名称
        if (entity.getStatus().equals("预约成功")){
            holder.appointmentstatuste.setTextColor(context.getResources().getColor(R.color.businesstop));
        }
        holder.appointmentstatuste.setText(entity.getStatus());//地址
        holder.appointmenttimete.setText("拜访时间："+ DateDistance.yuyuetimetodate(entity.getTime()));
        holder.appointmentunamete.setText(entity.getUname());
        if (entity.getbVisitorRead().equals("0")){
            holder.tvChatNum.setVisibility(View.VISIBLE);
        }else if (entity.getbVisitorRead().equals("1")){
            holder.tvChatNum.setVisibility(View.GONE);
        }

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

        @BindView(R.id.appointmentenamete)
        TextView appointmentenamete;
        @BindView(R.id.appointmentstatuste)
        TextView appointmentstatuste;
        @BindView(R.id.appointmenttimete)
        TextView appointmenttimete;
        @BindView(R.id.appointmentunamete)
        TextView appointmentunamete;
        @BindView(R.id.tvChatNum)
        TextView tvChatNum;

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

package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.ShouyeEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/14 0014 9:57
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class ShouyeAdapter extends RecyclerView.Adapter<ShouyeAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    private List<ShouyeEntity> entityList;


    public ShouyeAdapter(Context context, List<ShouyeEntity> entities) {
        this.context = context;
        this.entityList = entities;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shouyeadapter, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ShouyeEntity entity = entityList.get(position);
        ImageManager.load(context, Url.img_domain+entity.getEpViewImage()+Url.imageStyle640x640,holder.item_mobangimage);
        holder.item_mobangtext.setText("风格" + entity.getEpViewId());//地区
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


    static

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_mobangimage)
        ImageView item_mobangimage;
        @BindView(R.id.item_mobangtext)
        TextView item_mobangtext;

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

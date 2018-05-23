package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.EpClassEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/8/1 0001 15:14
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class EpClassAdapter {
//}
public class EpClassAdapter extends RecyclerView.Adapter<EpClassAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
//    private ArrayList<String> datas = new ArrayList<>();

    private List<EpClassEntity> entityList;


    public EpClassAdapter(Context context, List<EpClassEntity> entities) {
        this.context = context;
        this.entityList = entities;
//public ActivityInformationChooseAdapter(Context context, ArrayList<String> dataList) {
//        this.context = context;
//        this.datas = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemepclasslayout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        EpClassEntity entity = entityList.get(position);
        ImageManager.load(context,entity.getImage(),holder.id_index_gallery_item_image);
//        holder.itemCompanyInformationDetailName.setText(entity.getEpNm());//企业名称
//        holder.itemCompanyInformationDetailIntroduce.setText(entity.getEpDetail());//简介
//        holder.itemCompanyInformationTextChooseText.setText(entity.getText());//地区
        //判断是否设置了监听器
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

        @BindView(R.id.id_index_gallery_item_image)
        ImageView id_index_gallery_item_image;

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


//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void setVisible(View view,MessageEvent messageEvent) {
//        ViewHolder holder=new ViewHolder(view);
//        holder.itemInformationChooseImage.setVisibility(View.VISIBLE);
//    }
}

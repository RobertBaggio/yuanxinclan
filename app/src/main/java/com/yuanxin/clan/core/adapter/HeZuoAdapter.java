package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/12 0012 22:37
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class HeZuoAdapter {
//}
public class HeZuoAdapter extends RecyclerView.Adapter<HeZuoAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private List<CompanyDetail> entityList;


    public HeZuoAdapter(Context context, List<CompanyDetail> entities) {
        this.context = context;
        this.entityList = entities;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_information_detail, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CompanyDetail entity = entityList.get(position);
        ImageManager.loadBitmap(context, Url.img_domain+entity.getEnterprise().getEpImage1()+Url.imageStyle640x640, R.drawable.list_img, holder.itemCompanyInformationDetailImage);
//        ImageManager.loadBitmap(context,entity.getEpImage1(),holder.itemCompanyInformationDetailImage);//图片
        holder.itemCompanyInformationDetailName.setText(entity.getEnterprise().getEpNm());//企业名称
        holder.itemCompanyInformationDetailIntroduce.setText(entity.getEnterprise().getEpDetail());//简介
        Log.v("lgq",".......地址==="+entity.getAddress());
        if (entity.getAddress() == null) {
//            holder.itemCompanyInformationDetailArea.setText(null);//地区
            holder.itemCompanyInformationDetailArea.setVisibility(View.GONE);//地区
        } else {
            holder.itemCompanyInformationDetailArea.setText(entity.getAddress().getArea());//地区
        }
        if (entity.getIndustry() == null) {
            holder.itemCompanyInformationDetailType.setText(null);//行业id
            holder.itemCompanyInformationDetailType.setVisibility(View.GONE);
        } else {
            holder.itemCompanyInformationDetailType.setText(entity.getIndustry().getIndustryNm());//行业id
        }
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
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemLongClickListener.onItemLongClick(holder.itemView, position); // 2
                    return true;
                }
            });

        }
        holder.itemCompanyInformationDetailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition(); // 1
                mOnItemClickListener.onItemClick(holder.itemView, position); // 2
            }
        });
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_company_information_detail_image)
        MLImageView itemCompanyInformationDetailImage;
        @BindView(R.id.item_company_information_detail_name)
        TextView itemCompanyInformationDetailName;
        @BindView(R.id.item_company_information_detail_introduce)
        TextView itemCompanyInformationDetailIntroduce;
        @BindView(R.id.item_company_information_detail_area)
        TextView itemCompanyInformationDetailArea;
        @BindView(R.id.item_company_information_detail_type)
        TextView itemCompanyInformationDetailType;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }
}

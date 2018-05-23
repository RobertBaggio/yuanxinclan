package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.BusinessMessage;
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
public class BusinessMsgAdapter extends RecyclerView.Adapter<BusinessMsgAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private List<BusinessMessage> entityList;


    public BusinessMsgAdapter(Context context, List<BusinessMessage> entities) {
        this.context = context;
        this.entityList = entities;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qiliao_msg_item, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BusinessMessage entity = entityList.get(position);
        ImageManager.loadBitmap(context, Url.img_domain+entity.getBusinessAreaLogo()+Url.imageStyle640x640, R.drawable.list_img, holder.itemCompanyInformationDetailImage);
//        ImageManager.loadBitmap(context,entity.getEpImage1(),holder.itemCompanyInformationDetailImage);//图片
        holder.itemCompanyInformationDetailName.setText(entity.getBusinessMsgTitle());//企业名称
//        holder.itemCompanyInformationDetailIntroduce.setText(entity.getEnterprise().getEpDetail());//简介
        if (entity.getCreateDt() == null) {
//            holder.itemCompanyInformationDetailArea.setText(null);//地区
            holder.itemCompanyInformationDetailArea.setVisibility(View.GONE);//地区
        } else {
            holder.itemCompanyInformationDetailArea.setText(entity.getCreateDt());//地区
        }
        if (entity.getBusinessAreaNm() == null) {
            holder.itemCompanyInformationDetailType.setText(null);//行业id
            holder.itemCompanyInformationDetailType.setVisibility(View.GONE);
        } else {
            holder.itemCompanyInformationDetailType.setText(entity.getBusinessAreaNm());//行业id
        }
        if (entity.getMsgRead() == 0) {
            holder.businessCount.setVisibility(View.VISIBLE);
        } else {
            holder.businessCount.setVisibility(View.GONE);
        }
        if (entity.getSiteData() != null) {
            holder.status.setVisibility(View.VISIBLE);
            switch (entity.getSiteData().getProcessState()) {
                //待处理
                case 1:
                    holder.status.setText("待处理");
                    holder.status.setTextColor(context.getResources().getColor(R.color.angred));
                    break;
                // 同意
                case 2:
                    holder.status.setText("同意");
                    holder.status.setTextColor(context.getResources().getColor(R.color.businesstop));
                    break;
                // 拒绝
                case 3:
                    holder.status.setText("拒绝");
                    holder.status.setTextColor(context.getResources().getColor(R.color.activity_company_info_grey));
                    break;
            }
        } else if (entity.getRepairsData() != null) {
            holder.status.setVisibility(View.VISIBLE);
            switch (entity.getRepairsData().getProcessState()) {
                //待处理
                case 1:
                    holder.status.setText("待处理");
                    holder.status.setTextColor(context.getResources().getColor(R.color.angred));
                    break;
                // 同意
                case 2:
                    holder.status.setText("同意");
                    holder.status.setTextColor(context.getResources().getColor(R.color.businesstop));
                    break;
                // 拒绝
                case 3:
                    holder.status.setText("拒绝");
                    holder.status.setTextColor(context.getResources().getColor(R.color.activity_company_info_grey));
                    break;
            }
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
        @BindView(R.id.business_logo)
        MLImageView itemCompanyInformationDetailImage;
        @BindView(R.id.business_title)
        TextView itemCompanyInformationDetailName;
        @BindView(R.id.business_time)
        TextView itemCompanyInformationDetailArea;
        @BindView(R.id.business_name)
        TextView itemCompanyInformationDetailType;
        @BindView(R.id.business_count)
        TextView businessCount;
        @BindView(R.id.status)
        TextView status;

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

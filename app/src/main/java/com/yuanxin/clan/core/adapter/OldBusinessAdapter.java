package com.yuanxin.clan.core.adapter;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/26 0026 9:35
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class OldBusinessAdapter {
//}
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.BusinessDistrictListEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/24.
 */
public class OldBusinessAdapter extends RecyclerView.Adapter<OldBusinessAdapter.ViewHolder> {
    private List<BusinessDistrictListEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public OldBusinessAdapter(Context context, List<BusinessDistrictListEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_business_district_library, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BusinessDistrictListEntity entity = entityList.get(position);
        ImageManager.load(context, entity.getImage(), R.drawable.banner01, holder.itemBusinessDistrictLibraryImage);//
        holder.itemBusinessDistrictLibraryName.setText(entity.getBusinessAreaNm());////名称
        holder.itemBusinessDistrictLibraryArea.setText(entity.getArea());//地址
        //轮播

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
        //        @BindView(R.id.item_business_district_library_view_flow)
//        ViewFlow itemBusinessDistrictLibraryViewFlow;
        //轮播
//        @BindView(R.id.item_business_district_library_view_flow_indic)
//        CircleFlowIndicator itemBusinessDistrictLibraryViewFlowIndic;
        @BindView(R.id.item_business_district_library_image)
        ImageView itemBusinessDistrictLibraryImage;
        @BindView(R.id.item_business_district_library_name)
        TextView itemBusinessDistrictLibraryName;
        @BindView(R.id.item_business_district_library_area)
        TextView itemBusinessDistrictLibraryArea;

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

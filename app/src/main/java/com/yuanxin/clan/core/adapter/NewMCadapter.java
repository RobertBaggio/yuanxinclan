package com.yuanxin.clan.core.adapter;

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
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/20 0020 14:19
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class NewMCadapter {
//}
public class NewMCadapter extends RecyclerView.Adapter<NewMCadapter.ViewHolder> {
    private List<BusinessDistrictListEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public NewMCadapter(Context context, List<BusinessDistrictListEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newmyclreatbusins, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BusinessDistrictListEntity entity = entityList.get(position);
        ImageManager.load(context, entity.getImage(), R.drawable.banner01, holder.itemBusinessDistrictLibraryImage);//
//        Log.v("Lgq","tp....."+entity.getImage());
        holder.item_gank_namete.setText(entity.getBusinessAreaNm());////名称
        holder.addresste.setText(entity.getArea());//地址
//        Log.v("lgq","l............商系000==="+entity.getBusinessAreaType());
        holder.leixingte.setText(entity.getIndustryNm());//企业类型
        holder.huiyuante.setText(String.valueOf(entity.getMemberShip()));//人数
        holder.shoucte.setText(String.valueOf(entity.getCollectionCount()));//收藏人数
//        holder.fuwuzhonglei.setText(String.valueOf(entity.getIndustryNm()));//服务种类
        holder.shangxite.setText(entity.getBusinessAreaType());//商系

        if (entity.getStatus()==0){
            holder.statuste.setVisibility(View.VISIBLE);
            holder.statuste.setText("审核中");
        }else if (entity.getStatus()==2){
            holder.statuste.setVisibility(View.VISIBLE);
            holder.shibaite.setVisibility(View.VISIBLE);
            holder.statuste.setText("审核失败");
            holder.shibaite.setText("失败原因："+entity.getReason());
        }
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
        @BindView(R.id.item_gank_namete)
        TextView item_gank_namete;
        @BindView(R.id.addresste)
        TextView addresste;
        @BindView(R.id.leixingte)
        TextView leixingte;
        @BindView(R.id.shangxite)
        TextView shangxite;
        @BindView(R.id.fuwuzhonglei)
        TextView fuwuzhonglei;
        @BindView(R.id.huiyuante)
        TextView huiyuante;
        @BindView(R.id.shoucte)
        TextView shoucte;
        @BindView(R.id.shibaite)
        TextView shibaite;
        @BindView(R.id.statuste)
        TextView statuste;


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

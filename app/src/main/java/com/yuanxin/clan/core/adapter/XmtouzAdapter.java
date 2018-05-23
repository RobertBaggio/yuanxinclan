package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.XmTouzEntity;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/12/5 0005 18:47
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class XmtouzAdapter extends RecyclerView.Adapter<XmtouzAdapter.ViewHolder> {
    private List<XmTouzEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public XmtouzAdapter(Context context, List<XmTouzEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_xm, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        XmTouzEntity entity = entityList.get(position);
        ImageManager.load(context, entity.getInvestmentProjectImage1(), R.drawable.banner01, holder.topimage);//

        holder.xmnamete.setText(entity.getInvestmentProjectNm());////名称
        holder.numbermente.setText(entity.getBuyNumber()+"");

        holder.yigoute.setText(entity.getFundraisingMoney()+"");//企业类型
        try{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String thistime = df.format(new Date());

            double percent = entity.getFundraisingMoney() / entity.getInvestmentProjectAll();
            //输出一下，确认你的小数无误
            System.out.println("小数：" + percent);
            //获取格式化对象
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度2即保留两位小数
            nt.setMinimumFractionDigits(2);
            //最后格式化并输出
            System.out.println("百分数：" + nt.format(percent));
            Log.v("lgq",".......... "+"百分数：" + nt.format(percent)+"...."+entity.getInvestmentProjectAll()+"....."+percent);
            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams)holder.jinduteli.getLayoutParams(); //取控件textView当前的布局参数
            LinearLayout.LayoutParams linearParamste =(LinearLayout.LayoutParams)holder.jindute.getLayoutParams(); //取控件textView当前的布局参数

            if (percent>0.1){
                linearParams.weight =(float)percent;
                linearParamste.weight =(float)percent;
            }else {
                linearParams.weight =(float)0.05;
                linearParamste.weight =(float)0.1;
            }
            holder.jinduteli.setLayoutParams(linearParams);
            holder.jindute.setLayoutParams(linearParamste);


            holder.jindute.setText(nt.format(percent)+"");

            long shengyuday = DateDistance.getTimesChaLong(entity.getEndTime(),thistime);
            long moreday = DateDistance.getTimesChaLong(thistime,entity.getStartTime());
            if (shengyuday<0){
                holder.statusimage.setImageResource(R.drawable.yi_jie_shu);
                holder.shengyute.setText("0");
            }else if (shengyuday>=0&&moreday>0){
                holder.statusimage.setImageResource(R.drawable.zhong_chou_zhong);
                holder.shengyute.setText(shengyuday+"");
            }else if (moreday<=0){
                holder.statusimage.setImageResource(R.drawable.yu_re_zhong);
                holder.shengyute.setText(moreday+"");
            }
        }catch(Exception e){
            e.printStackTrace();
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
        //        @BindView(R.id.item_business_district_library_view_flow)
//        ViewFlow itemBusinessDistrictLibraryViewFlow;
        //轮播
//        @BindView(R.id.item_business_district_library_view_flow_indic)
//        CircleFlowIndicator itemBusinessDistrictLibraryViewFlowIndic;
        @BindView(R.id.topimage)
        ImageView topimage;
        @BindView(R.id.statusimage)
        ImageView statusimage;
        @BindView(R.id.xmnamete)
        TextView xmnamete;
        @BindView(R.id.numbermente)
        TextView numbermente;
        @BindView(R.id.yigoute)
        TextView yigoute;
        @BindView(R.id.shengyute)
        TextView shengyute;
        @BindView(R.id.jindute)
        TextView jindute;
        @BindView(R.id.jinduteli)
        TextView jinduteli;



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

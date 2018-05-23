package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.CheckdetailEntity;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.MainApplication;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/1 0001 10:52
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class CheckdetailAdapter  extends RecyclerView.Adapter<CheckdetailAdapter.ViewHolder> {
    private List<CheckdetailEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public CheckdetailAdapter(Context context, List<CheckdetailEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.checkitemla, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CheckdetailEntity entity = entityList.get(position);
//        ImageManager.loadBitmap(context, entity.getImage(), R.drawable.list_img, holder.itemPresentCustomMadeImage);//图片
        holder.itemPresentCustomMadeCommodity.setText(TextUtil.formatString(entity.getBody()));//商品
        holder.itemCompanyPresentCustomMadeCompany.setText(TextUtil.formatString(entity.getCreateDt()));//公司
        holder.itemPresentCustomMadeMoney.setText(TextUtil.formatString(String.valueOf(entity.getBillAmount())));//区域
        int w = MainApplication.getnScreenWidth();
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.myli.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.width = w;// 控件的高强制设成20

    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkitembodyte)
        TextView itemPresentCustomMadeCommodity;
        @BindView(R.id.checkitemtime)
        TextView itemCompanyPresentCustomMadeCompany;
        @BindView(R.id.checkitemmoney)
        TextView itemPresentCustomMadeMoney;
        @BindView(R.id.myli)
        LinearLayout myli;

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

package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.EpColorselectEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/2/1 0001 17:04
 */

public class EpcolorSelectAdapter extends RecyclerView.Adapter<EpcolorSelectAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    private List<EpColorselectEntity> entityList;


    public EpcolorSelectAdapter(Context context, List<EpColorselectEntity> entities) {
        this.context = context;
        this.entityList = entities;
    }


    //  删除 打勾 全选
    public void cleckAll(int  is_checked) { //全选 删除多少那里要删除全部
        for (int a = 0;a<entityList.size();a++){
            if (is_checked==a){
                entityList.get(a).setIfselect(true);
            }else {
                entityList.get(a).setIfselect(false);
            }
        }
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_epselect_color, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        EpColorselectEntity entity = entityList.get(position);
        holder.stylecolorte.setBackgroundColor(Color.parseColor (entity.getColorString()));
        if (entity.isIfselect()){
            holder.stylecolorimage.setVisibility(View.VISIBLE);
        }else {
            holder.stylecolorimage.setVisibility(View.GONE);
        }
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

        @BindView(R.id.stylecolorte)
        TextView stylecolorte;
        @BindView(R.id.stylecolorimage)
        ImageView stylecolorimage;

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

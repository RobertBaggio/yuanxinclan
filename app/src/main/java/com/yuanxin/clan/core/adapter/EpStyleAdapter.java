package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.ShouyeEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/2/1 0001 15:02
 */

public class EpStyleAdapter extends RecyclerView.Adapter<EpStyleAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    private List<ShouyeEntity> entityList;


    public EpStyleAdapter(Context context, List<ShouyeEntity> entities) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_epstyle, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ShouyeEntity entity = entityList.get(position);
//        ImageManager.load(context, Url.img_domain+entity.getEpViewImage()+ Url.imageStyle640x640,holder.item_mobangimage);
        holder.epstylete.setText("风格" + entity.getEpViewId());//地区
        if (entityList.get(position).isIfselect()){
            holder.epstylete.setBackgroundResource(R.drawable.select_epstyle);
            holder.epstylete.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.epstylete.setBackgroundResource(R.drawable.noselectepstyle);
            holder.epstylete.setTextColor(context.getResources().getColor(R.color.login_black));
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

        @BindView(R.id.epstylete)
        TextView epstylete;

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

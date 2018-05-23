package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.BargainEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/5/16.
 */
public class NewMyBargainAdapter extends RecyclerView.Adapter<NewMyBargainAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<BargainEntity> entityList;


    public NewMyBargainAdapter(Context context, List<BargainEntity> entities) {
        this.context = context;
        this.entityList = entities;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_new_my_bargain, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BargainEntity entity = entityList.get(position);
        holder.itemNewMyBarginName.setText(entity.getContractNm());
        holder.itemNewMyBarginTime.setText(entity.getCreateDt());
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
        @BindView(R.id.item_new_my_bargin_name)
        TextView itemNewMyBarginName;
        @BindView(R.id.item_new_my_bargin_time)
        TextView itemNewMyBarginTime;
        @BindView(R.id.item_new_my_bargin_layout)
        RelativeLayout itemNewMyBarginLayout;

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

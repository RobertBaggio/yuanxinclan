package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.MyTakePartCrowdFundingEntity;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/22.
 */
public class MyTakePartCrowdFundingAdapter extends RecyclerView.Adapter<MyTakePartCrowdFundingAdapter.ViewHolder> {
    private List<MyTakePartCrowdFundingEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public MyTakePartCrowdFundingAdapter(Context context, List<MyTakePartCrowdFundingEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_take_part_crowd_funding, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MyTakePartCrowdFundingEntity entity = entityList.get(position);
        ImageManager.loadBitmap(context, entity.getImage(), holder.itemCrowFundingImage);
        holder.itemCrowFundingName.setText(entity.getName());
        holder.itemCrowFundingHaveMoney.setText(String.valueOf(entity.getMoney()));
        //进度条
        holder.itemCrowFundingRoundCornerProgressBar.setMax(entity.getTotal());
        holder.itemCrowFundingRoundCornerProgressBar.setProgress(entity.getMoney());
        holder.itemCrowFundingPercent.setText(entity.getPercent());
        holder.itemCrowFundingNumberPeople.setText(entity.getNumber());
        holder.itemCrowFundingTotal.setText(String.valueOf(entity.getTotal()));
        holder.itemCrowFundingTime.setText("还剩" + entity.getTime() + "天");
//        holder.itemCrowFundingNumberPeople.setText(TimeUtil.timeFormat(entity.getCreatedAt()));
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


    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_crow_funding_image)
        MLImageView itemCrowFundingImage;
        @BindView(R.id.item_crow_funding_name)
        TextView itemCrowFundingName;
        @BindView(R.id.item_crow_funding_have_money)
        TextView itemCrowFundingHaveMoney;
        @BindView(R.id.item_crow_funding_round_corner_progress_bar)
        RoundCornerProgressBar itemCrowFundingRoundCornerProgressBar;
        @BindView(R.id.item_crow_funding_percent)
        TextView itemCrowFundingPercent;
        @BindView(R.id.item_crow_funding_people)
        TextView itemCrowFundingPeople;
        @BindView(R.id.item_crow_funding_number_people)
        TextView itemCrowFundingNumberPeople;
        @BindView(R.id.item_crow_funding_total)
        TextView itemCrowFundingTotal;
        @BindView(R.id.item_crow_funding_time)
        TextView itemCrowFundingTime;

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

package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.CrowdFundingAllEntity;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/22.
 */
//众筹
public class CrowdFundingAdapter extends RecyclerView.Adapter<CrowdFundingAdapter.ViewHolder> {
    private List<CrowdFundingAllEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private int resultDiv;//百分比
    private String crowdfundSum, crowdfundAll;

    public CrowdFundingAdapter(Context context, List<CrowdFundingAllEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crowd_funding, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CrowdFundingAllEntity entity = entityList.get(position);
        ImageManager.loadBitmap(context, entity.getCrowdfundImage1(), R.drawable.list_img, holder.itemCrowFundingImage);//
        holder.itemCrowFundingName.setText(entity.getCrowdfundNm());//防蓝光护目镜
        holder.itemCrowFundingHaveMoney.setText("¥" + entity.getCrowdfundSum());//800
        crowdfundSum = entity.getCrowdfundSum();

        int crowdfundSumOne = Integer.valueOf(crowdfundSum);
        crowdfundAll = entity.getCrowdfundAll();
        if (crowdfundAll.equals("null")) {
            crowdfundAll = "0";
        }
        int crowdfundAllOne = Integer.valueOf(crowdfundAll);
        holder.itemCrowFundingRoundCornerProgressBar.setMax(crowdfundAllOne);//8600 最大
        holder.itemCrowFundingRoundCornerProgressBar.setProgress(crowdfundSumOne);//800 有多少
        holder.itemCrowFundingPercent.setText(entity.getSchedule() + "%");//20%
        holder.itemCrowFundingNumberPeople.setText(entity.getCrowdfundAll() + "元");//8600万元
        holder.itemCrowFundingTime.setText("还剩" + entity.getSurplusDay() + "天");//剩1天//要改要改要改要改要改要改要改要改要改要改要改要改要改要改
        holder.itemCrowFundingPeople.setText(entity.getParticipations() + "人");//要改要改要改要改要改要改要改要改要改要改要改要改要改要改//要改要改要改要改要改要改要改要改要改要改要改要改要改要改
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_crow_funding_image)
        MLImageView itemCrowFundingImage;   //图片
        @BindView(R.id.item_crow_funding_name)
        TextView itemCrowFundingName;       //防蓝光护目镜
        @BindView(R.id.item_crow_funding_have_money)
        TextView itemCrowFundingHaveMoney;    //¥ 800
        @BindView(R.id.item_crow_funding_round_corner_progress_bar)
        RoundCornerProgressBar itemCrowFundingRoundCornerProgressBar;
        @BindView(R.id.item_crow_funding_percent)
        TextView itemCrowFundingPercent;      //20%
        @BindView(R.id.item_crow_funding_people)
        TextView itemCrowFundingPeople;      //43人
        @BindView(R.id.item_crow_funding_number_people)
        TextView itemCrowFundingNumberPeople;   //8600万元
        @BindView(R.id.item_crow_funding_time)
        TextView itemCrowFundingTime;     //剩1天
        @BindView(R.id.item_crowd_funding_layout)
        LinearLayout itemCrowdFundingLayout;

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

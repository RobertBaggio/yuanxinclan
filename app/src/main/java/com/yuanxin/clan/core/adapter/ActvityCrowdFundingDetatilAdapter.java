package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.CrowdFundingDetailEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/22.
 */
public class ActvityCrowdFundingDetatilAdapter extends RecyclerView.Adapter<ActvityCrowdFundingDetatilAdapter.ViewHolder> {
    private List<CrowdFundingDetailEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public ActvityCrowdFundingDetatilAdapter(Context context, List<CrowdFundingDetailEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crowd_funding_detail_image, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CrowdFundingDetailEntity entity = entityList.get(position);

        if (!entity.getImage().equals("")) {
            ImageManager.load(context, entity.getImage(), holder.activityItemCrowdFundingDetailImage);
        }
//        holder.itemGankDesc.setText(entity.getDesc());
//        holder.itemGankType.setText(entity.getType());
//        holder.itemGankWho.setText(entity.getWho());
//        holder.itemGankCreatedAt.setText(TimeUtil.timeFormat(entity.getCreatedAt()));
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
        @BindView(R.id.activity_item_crowd_funding_detail_image)
        ImageView activityItemCrowdFundingDetailImage;

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

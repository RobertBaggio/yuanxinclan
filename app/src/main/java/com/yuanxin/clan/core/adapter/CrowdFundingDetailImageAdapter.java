package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.CorwdFundingDetailImageEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/10.
 */

public class CrowdFundingDetailImageAdapter extends RecyclerView.Adapter<CrowdFundingDetailImageAdapter.ViewHolder> {
    private List<CorwdFundingDetailImageEntity> entityList;
    private Context context;
    private ActivityEnterprisesIndustryAdapter.OnItemClickListener mOnItemClickListener;

    public CrowdFundingDetailImageAdapter(Context context, List<CorwdFundingDetailImageEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crowd_funding_detail_image_layout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CorwdFundingDetailImageEntity entity = entityList.get(position);
        ImageManager.load(context, entity.getImage(), R.drawable.banner01, holder.itemCrowdFundingDetailImageLayoutImage);//

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
        @BindView(R.id.item_crowd_funding_detail_image_layout_image)
        ImageView itemCrowdFundingDetailImageLayoutImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(ActivityEnterprisesIndustryAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}

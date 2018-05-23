package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.ActivityGankSearchEntity;
import com.yuanxin.clan.core.util.TimeUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/1/24.
 */
public class ActivityGankSearchAdapter extends RecyclerView.Adapter<ActivityGankSearchAdapter.ViewHolder> {
    private List<ActivityGankSearchEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public ActivityGankSearchAdapter(Context context, List<ActivityGankSearchEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity_gank_search, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ActivityGankSearchAdapter.ViewHolder holder, int position) {
        ActivityGankSearchEntity entity = entityList.get(position);
//        ImageManager.load(context,entity.getImages(),holder.itemGankImage);
        holder.itemActivityGankSearchDesc.setText(entity.getDesc());
        holder.itemActivityGankSearchType.setText(entity.getType());
        holder.itemActivityGankSearchWho.setText(entity.getWho());
        holder.itemActivityGankSearchCreatedAt.setText(TimeUtil.timeFormat(entity.getCreatedAt()));
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
        //        @BindView(R.id.item_activity_gank_search_image)
//        ImageView itemActivityGankSearchImage;
        @BindView(R.id.item_activity_gank_search_desc)
        TextView itemActivityGankSearchDesc;
        @BindView(R.id.item_activity_gank_search_type)
        TextView itemActivityGankSearchType;
        @BindView(R.id.item_activity_gank_search_who)
        TextView itemActivityGankSearchWho;
        @BindView(R.id.item_activity_gank_search_createdAt)
        TextView itemActivityGankSearchCreatedAt;

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

package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.BusinessDistrictMemberEntity;
import com.yuanxin.clan.core.widget.activity.RoundImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/24.
 */
public class FragmentBusinessDistrictMemberAdapter extends RecyclerView.Adapter<FragmentBusinessDistrictMemberAdapter.ViewHolder> {
    private List<BusinessDistrictMemberEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public FragmentBusinessDistrictMemberAdapter(Context context, List<BusinessDistrictMemberEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_business_district_member, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BusinessDistrictMemberEntity entity = entityList.get(position);
        ImageManager.loadBitmap(context, entity.getImage(), holder.activityItemDistrictMemberImage);
        holder.activityItemDistrictMemberName.setText(entity.getName());
        holder.activityItemDistrictMemberPosition.setText(entity.getPosition());
//        holder.itemYuanXinFairIntroduce.setText(entity.getIntroduce());
//        holder.itemYuanXinFairMoney.setText(TimeUtil.timeFormat(entity.getMoney()));
//        holder.itemYuanXinFairArea.setText(TimeUtil.timeFormat(entity.getArea()));
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
        @BindView(R.id.activity_item_district_member_image)
        RoundImageView activityItemDistrictMemberImage;
        @BindView(R.id.activity_item_district_member_name)
        TextView activityItemDistrictMemberName;
        @BindView(R.id.activity_item_district_member_position)
        TextView activityItemDistrictMemberPosition;

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

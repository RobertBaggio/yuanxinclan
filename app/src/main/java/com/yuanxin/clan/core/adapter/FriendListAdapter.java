package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.FriendListEntity;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/5/13.
 */
public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {
    private List<FriendListEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public FriendListAdapter(Context context, List<FriendListEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_friend_list, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        FriendListEntity entity = entityList.get(position);
        if (!entity.getImage().equals("")) {
            ImageManager.loadBitmap(context, entity.getImage(), R.drawable.list_img, holder.itemPresentCustomMadeImage);
        }
        holder.itemPresentCustomMadeCommodity.setText(entity.getName());//标题
//        holder.itemGankType.setText(entity.getType());
//        holder.itemGankWho.setText(entity.getType());//类型
//        holder.itemGankCreatedAt.setText(TimeUtil.timeFormat(entity.getCreatedAt()));//创建时间
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
        @BindView(R.id.item_present_custom_made_image)
        MLImageView itemPresentCustomMadeImage;
        @BindView(R.id.item_present_custom_made_commodity)
        TextView itemPresentCustomMadeCommodity;
        @BindView(R.id.item_present_custom_made_state)
        TextView itemPresentCustomMadeState;
        @BindView(R.id.item_present_custom_made_layout)
        LinearLayout itemPresentCustomMadeLayout;

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

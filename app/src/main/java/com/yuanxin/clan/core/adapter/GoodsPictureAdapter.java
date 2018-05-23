package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.GoodsPictureEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/1.
 */
public class GoodsPictureAdapter extends RecyclerView.Adapter<GoodsPictureAdapter.ViewHolder> {
    private List<GoodsPictureEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public GoodsPictureAdapter(Context context, List<GoodsPictureEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_goods_picture, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GoodsPictureEntity entity = entityList.get(position);
        ImageManager.load(context, entity.getPicture(), holder.itemGoodsPicture);
//        holder.itemThinkTankName.setText(entity.getExpertNm());
//        holder.itemThinkTankPosition.setText(entity.getCompany());//公司名称
//        holder.itemThinkTankAddress.setText(entity.getPosition());//三
//        holder.itemThinkTankPositionPosition.setText(entity.getTitle());//二

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
        @BindView(R.id.item_goods_picture)
        ImageView itemGoodsPicture;

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

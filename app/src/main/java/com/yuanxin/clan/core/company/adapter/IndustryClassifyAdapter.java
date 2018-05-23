package com.yuanxin.clan.core.company.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/3/20.
 */
public class IndustryClassifyAdapter extends RecyclerView.Adapter<IndustryClassifyAdapter.ViewHolder> {
    private List<IndustryListEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public IndustryClassifyAdapter(Context context, List<IndustryListEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_industry_classify, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final IndustryClassifyAdapter.ViewHolder holder, int position) {
        IndustryListEntity entity = entityList.get(position);
////        ImageManager.load(context,entity.getImages(),holder.itemGankImage);
//        holder.itemGankDesc.setText(entity.getDesc());
////        holder.itemGankType.setText(entity.getType());
//        holder.itemGankWho.setText(entity.getWho());
        holder.itemIndustryClassifyText.setText(entity.getIndustryNm());
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
        @BindView(R.id.item_industry_classify_text)
        TextView itemIndustryClassifyText;

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

package com.yuanxin.clan.core.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.news.bean.NewsType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/8.
 */
public class ActivityInformationTypeAdapter extends RecyclerView.Adapter<ActivityInformationTypeAdapter.ViewHolder>{
    //    private List<InformationTypeEntity> entityList;
    private Context context;
    private ActivityInformationTypeAdapter.OnItemClickListener mOnItemClickListener;
    List<NewsType> datas = new ArrayList<>();


    //    public ActivityInformationTypeAdapter(Context context, List<InformationTypeEntity> entities) {
//        this.context = context;
//        this.entityList = entities;
    public ActivityInformationTypeAdapter(Context context, List<NewsType> dataList) {
        this.context = context;
        this.datas = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information_type, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ActivityInformationTypeAdapter.ViewHolder holder, int position) {
//        InformationTypeEntity entity=entityList.get(position);
        holder.itemInformationTypeText.setText(datas.get(position).getNewsTypeNm());
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
        return datas.size();
    }

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_information_type_text)
        TextView itemInformationTypeText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(ActivityInformationTypeAdapter.OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

}

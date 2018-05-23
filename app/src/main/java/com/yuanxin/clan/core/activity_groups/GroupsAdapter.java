package com.yuanxin.clan.core.activity_groups;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/3/26 0026 14:25
 */

public class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder> {
    private List<GroupsEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public GroupsAdapter(Context context, List<GroupsEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_huodongquan, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        GroupsEntity entity = entityList.get(position);
        ImageManager.load(context, entity.getActivityImage(), R.drawable.banner01, holder.hdqimage);//
        holder.hdqnamete.setText(entity.getActivityTheme()+"（"+entity.getCity()+"）");////名称
        holder.item_hdqtiemte.setText(DateDistance.getDistanceTimeToHM(entity.getStarTime()));//地址
//        Log.v("lgq","l............商系000==="+entity.getBusinessAreaType());
        holder.hdqstatuste.setText(entity.getStatus());//企业类型
        holder.hdqadresste.setText("地点："+entity.getAdress());//人数
        holder.hdqepte.setText("举办单位："+entity.getActivitySponsor());//收藏人数

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
        @BindView(R.id.hdqimage)
        ImageView hdqimage;
        @BindView(R.id.item_hdqtiemte)
        TextView item_hdqtiemte;
        @BindView(R.id.hdqstatuste)
        TextView hdqstatuste;
        @BindView(R.id.hdqadresste)
        TextView hdqadresste;
        @BindView(R.id.hdqnamete)
        TextView hdqnamete;
        @BindView(R.id.hdqepte)
        TextView hdqepte;

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

package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.MyBargainEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/12.
 */

public class MyBargainAdapter extends RecyclerView.Adapter<MyBargainAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
//    private ArrayList<String> datas = new ArrayList<>();

    private List<MyBargainEntity> entityList;


    public MyBargainAdapter(Context context, List<MyBargainEntity> entities) {
        this.context = context;
        this.entityList = entities;
//public ActivityInformationChooseAdapter(Context context, ArrayList<String> dataList) {
//        this.context = context;
//        this.datas = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_bargain, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MyBargainEntity entity = entityList.get(position);
//        ImageManager.load(context,entity.getImage(),R.drawable.by,holder.itemMyBargain);
        holder.itemMyBargainText.setText(entity.getText());
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器-
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
        @BindView(R.id.item_my_bargain)
        ImageView itemMyBargain;
        @BindView(R.id.item_my_bargain_text)
        TextView itemMyBargainText;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }


}

package com.yuanxin.clan.core.news.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.news.bean.NewsType;
import com.yuanxin.clan.core.news.helper.MyItemTouchCallback;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/9.
 */
public class ActivityInformationChooseAdapter extends RecyclerView.Adapter<ActivityInformationChooseAdapter.ViewHolder> implements MyItemTouchCallback.ItemTouchAdapter{
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<NewsType> datas = new ArrayList<>();

    public Boolean getIs_edit() {
        return is_edit;
    }

    private Boolean is_edit = false;

    //    public ActivityInformationTypeAdapter(Context context, List<InformationTypeEntity> entities) {
//        this.context = context;
//        this.entityList = entities;
    public ActivityInformationChooseAdapter(Context context, List<NewsType> dataList) {
        this.context = context;
        this.datas = dataList;
    }

    public void setEdit() {
        is_edit = !is_edit;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_information_choose, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//        InformationTypeEntity entity=entityList.get(position);
        holder.itemInformationChooseText.setText(datas.get(position).getNewsTypeNm());
        if (is_edit) {
            holder.itemInformationChooseImage.setVisibility(View.VISIBLE);
        } else {
            holder.itemInformationChooseImage.setVisibility(View.GONE);

        }
//        holder.itemInformationChooseText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (is_edit) {
//                    datas.remove(position);
//                    notifyDataSetChanged();
//                }
//            }
//        });
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


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_information_choose_text)
        TextView itemInformationChooseText;
        @BindView(R.id.item_information_choose_image)
        ImageView itemInformationChooseImage;

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
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void setVisible(View view,MessageEvent messageEvent) {
//        ViewHolder holder=new ViewHolder(view);
//        holder.itemInformationChooseImage.setVisibility(View.VISIBLE);
//    }
@Override
public void onMove(int fromPosition, int toPosition) {
//    if (fromPosition == datas.size()-1 || toPosition == datas.size()-1){
//        return;
//    }
    if (fromPosition < toPosition) {
        for (int i = fromPosition; i < toPosition; i++) {
            Collections.swap(datas, i, i + 1);
        }
    } else {
        for (int i = fromPosition; i > toPosition; i--) {
            Collections.swap(datas, i, i - 1);
        }
    }
    notifyItemMoved(fromPosition, toPosition);
}

    @Override
    public void onSwiped(int position) {
        datas.remove(position);
        notifyItemRemoved(position);
    }

}

package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.CompanyPresentCustomMadeEntity;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/16.
 */

public class CompanyPresentCustomMadeAdapter extends RecyclerView.Adapter<CompanyPresentCustomMadeAdapter.ViewHolder> {
    private List<CompanyPresentCustomMadeEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public CompanyPresentCustomMadeAdapter(Context context, List<CompanyPresentCustomMadeEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company_present_custom_made, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CompanyPresentCustomMadeEntity entity = entityList.get(position);
        ImageManager.loadBitmap(context, entity.getImage(), R.drawable.list_img, holder.itemPresentCustomMadeImage);//图片
        holder.itemPresentCustomMadeCommodity.setText(TextUtil.formatString(entity.getName()));//商品
        holder.itemCompanyPresentCustomMadeCompany.setText(TextUtil.formatString(entity.getCompany()));//公司
        holder.itemPresentCustomMadeMoney.setText(TextUtil.formatString(entity.getArea()));//区域
        holder.itemPresentCustomMadeStartTime.setText(TextUtil.formatString(entity.getPrice()));//价格

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
        @BindView(R.id.item_present_custom_made_image)
        MLImageView itemPresentCustomMadeImage;
        @BindView(R.id.item_present_custom_made_commodity)
        TextView itemPresentCustomMadeCommodity;
        @BindView(R.id.item_company_present_custom_made_company)
        TextView itemCompanyPresentCustomMadeCompany;
        @BindView(R.id.item_present_custom_made_money)
        TextView itemPresentCustomMadeMoney;
        @BindView(R.id.item_present_custom_made_company)
        TextView itemPresentCustomMadeCompany;
        @BindView(R.id.item_present_custom_made_start_time)
        TextView itemPresentCustomMadeStartTime;
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

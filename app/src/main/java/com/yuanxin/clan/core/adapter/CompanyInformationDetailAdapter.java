package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/10.
 * 企业库Adapter
 */
public class CompanyInformationDetailAdapter extends RecyclerView.Adapter<CompanyInformationDetailAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<CompanyDetail> entityList;


    public CompanyInformationDetailAdapter(Context context, List<CompanyDetail> entities) {
        this.context = context;
        this.entityList = entities;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_information_detail, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CompanyDetail entity = entityList.get(position);
//        Log.v("lgq","...tt[[pp==="+entity.getEpImage1());
        ImageManager.loadBitmap(context, Url.img_domain+entity.getEpImage1()+Url.imageStyle640x640, R.drawable.list_img, holder.itemCompanyInformationDetailImage);
        try{
            if (entity.getEpNm().length()>14){
                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.itemCompanyInformationDetailName.getLayoutParams(); //取控件textView当前的布局参数
                linearParams.width = UIUtils.dip2px(210);// 控件的高强制设成20
            }
        holder.itemCompanyInformationDetailName.setText(entity.getEpNm());//企业名称
        holder.itemCompanyInformationDetailIntroduce.setText(entity.getEpDetail());//简介
        if (entity.getAddress().getArea() == null) {
            holder.itemCompanyInformationDetailArea.setVisibility(View.GONE);//地区
        } else {
            holder.itemCompanyInformationDetailArea.setText(entity.getAddress().getArea());//地区
        }
//        if (entity.getIndustry() == null) {
        if (entity.getIndustry().getIndustryNm() == null) {
            holder.itemCompanyInformationDetailType.setText(null);//行业类型
            holder.itemCompanyInformationDetailType.setVisibility(View.GONE);//行业类型
        } else {
            holder.itemCompanyInformationDetailType.setText(entity.getIndustry().getIndustryNm());//行业类型
        }
        }catch(Exception e){
            e.printStackTrace();
        }
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器-
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    Log.v("lgq","itemView...........");
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });

        }
        holder.itemCompanyInformationDetailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("lgq","itemCompanyInformationDetailImage...........");
                int position = holder.getLayoutPosition(); // 1
                mOnItemClickListener.onItemClick(holder.itemView, position); // 2
            }
        });
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_company_information_detail_image)
        MLImageView itemCompanyInformationDetailImage;
        @BindView(R.id.item_company_information_detail_name)
        TextView itemCompanyInformationDetailName;
        @BindView(R.id.item_company_information_detail_introduce)
        TextView itemCompanyInformationDetailIntroduce;
        @BindView(R.id.item_company_information_detail_area)
        TextView itemCompanyInformationDetailArea;
        @BindView(R.id.item_company_information_detail_type)
        TextView itemCompanyInformationDetailType;

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

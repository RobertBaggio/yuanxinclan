package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.BusinessMemberEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2017/12/15 0015 17:12
 */

public class QuanzimenAdapter extends RecyclerView.Adapter<QuanzimenAdapter.ViewHolder> {
    private List<BusinessMemberEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnCompanyDetailClickListener mOnCompanyDetailClickListener;
    private OnCompanyHistoryListener mOnCompanyHistoryListener;
    private OnCompanyVisitListener mOnCompanyVisitListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public QuanzimenAdapter(Context context, List<BusinessMemberEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.quanzimenber, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final BusinessMemberEntity entity = entityList.get(position);
        ImageManager.load(context, Url.img_domain + entity.getUser().getUserImage() + Url.imageStyle640x640, R.drawable.banner01, holder.qzimage);//
//        Log.v("Lgq","tp....."+entity.getImage());
        holder.qznamete.setText(entity.getUser().getUserNm());
//        holder.positionText.setText(entity.getPosition());
        holder.qzepnamete.setText(entity.getEnterprise().getEpNm());

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
        holder.qzallli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCompanyDetailClickListener.onItemClick(position);
            }
        });
//        holder.companyHistory.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnCompanyHistoryListener.onItemClick(position);
//            }
//        });
        holder.qzyuyuete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnCompanyVisitListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.item_business_district_library_view_flow)
//        ViewFlow itemBusinessDistrictLibraryViewFlow;
        //轮播
//        @BindView(R.id.item_business_district_library_view_flow_indic)
//        CircleFlowIndicator itemBusinessDistrictLibraryViewFlowIndic;
        @BindView(R.id.qzimage)
        ImageView qzimage;
        @BindView(R.id.qznamete)
        TextView qznamete;
        @BindView(R.id.qzepnamete)
        TextView qzepnamete;
        @BindView(R.id.qzyuyuete)
        TextView qzyuyuete;
        @BindView(R.id.qzallli)
        LinearLayout qzallli;



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

    public interface OnCompanyDetailClickListener {
        void onItemClick(int position);
    }

    public void setOnCompanyDetailClickListener(OnCompanyDetailClickListener mOnItemClickListener) {
        this.mOnCompanyDetailClickListener = mOnItemClickListener;
    }

    public interface OnCompanyHistoryListener {
        void onItemClick(int position);
    }

    public void setOnCompanyHistoryListener(OnCompanyHistoryListener mOnItemClickListener) {
        this.mOnCompanyHistoryListener = mOnItemClickListener;
    }
    public interface OnCompanyVisitListener {
        void onItemClick(int position);
    }

    public void setOnCompanyVisitListener(OnCompanyVisitListener mOnItemClickListener) {
        this.mOnCompanyVisitListener = mOnItemClickListener;
    }

}

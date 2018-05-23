package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.PaizhaoEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/2 0002 11:22
 */

public class PaiZhaoAdapter extends RecyclerView.Adapter<PaiZhaoAdapter.ViewHolder> {
    private List<PaizhaoEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public PaiZhaoAdapter(Context context, List<PaizhaoEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_paizhao, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PaizhaoEntity entity = entityList.get(position);
//        ImageManager.load(context, entity.getImage(), R.drawable.banner01, holder.itemBusinessDistrictLibraryImage);//
//        Log.v("Lgq","tp....."+entity.getImage());
        holder.pzepnamete.setText(entity.getLicensePlateName());////名称
        holder.pzcltimes.setText(entity.getEstablishYear()+"年");//地址
//        Log.v("lgq","l............商系000==="+entity.getBusinessAreaType());
        holder.pzzczj.setText(entity.getRegisteredCapital()+"万");//企业类型
        holder.pzhyleixing.setText(entity.getIndustryNm());//人数
        holder.pzshoujia.setText(entity.getLicensePlatePrice()+"万");//收藏人数
//        holder.fuwuzhonglei.setText(String.valueOf(entity.getIndustryNm()));//服务种类
        holder.pzdiqu.setText(entity.getCity());//商系
//        holder.pzoutputtime.setText(DateDistance.timetodate(entity.getCreateDt()));
        holder.pzoutputtime.setText(entity.getCreateDt());
        //轮播

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
        @BindView(R.id.pzepnamete)
        TextView pzepnamete;
        @BindView(R.id.pzcltimes)
        TextView pzcltimes;
        @BindView(R.id.pzzczj)
        TextView pzzczj;
        @BindView(R.id.pzhyleixing)
        TextView pzhyleixing;
        @BindView(R.id.pzshoujia)
        TextView pzshoujia;
        @BindView(R.id.pzdiqu)
        TextView pzdiqu;
        @BindView(R.id.pzoutputtime)
        TextView pzoutputtime;


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

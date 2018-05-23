package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.CompanySearchEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/3/21.
 */
public class CompanySearchAdapter extends RecyclerView.Adapter<CompanySearchAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
//    private ArrayList<String> datas = new ArrayList<>();

    private List<CompanySearchEntity> entityList;


    public CompanySearchAdapter(Context context, List<CompanySearchEntity> entities) {
        this.context = context;
        this.entityList = entities;
//public ActivityInformationChooseAdapter(Context context, ArrayList<String> dataList) {
//        this.context = context;
//        this.datas = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company_search, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CompanySearchEntity entity = entityList.get(position);
//        ImageManager.load(context,entity.getEpLogo(),holder.itemCompanyInformationDetailImage);
        holder.itemCompanySearchText.setText(entity.getName());//企业名称
//        holder.itemCompanyInformationDetailIntroduce.setText(entity.getEpDetail());//简介
//        holder.itemCompanyInformationDetailArea.setText(entity.getAddressId());//地区
////        holder.itemCompanyInformationDetailType.setText(entity.getIndustryId() + "");
//
//        int industryId=entity.getIndustryId();//int
//        String industryIdNew=String.valueOf(industryId);//转换为String
//        SharedPreferences share=context.getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
//        String industryNm=share.getString(industryIdNew,"");//取名称
//        holder.itemCompanyInformationDetailType.setText(industryNm);//行业id
//        EventBus.getDefault().register(this);
//        MessageEvent event=new MessageEvent("setVisible");
//        setVisible(holder.itemInformationChooseImage,event);
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


    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_company_search_text)
        TextView itemCompanySearchText;

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

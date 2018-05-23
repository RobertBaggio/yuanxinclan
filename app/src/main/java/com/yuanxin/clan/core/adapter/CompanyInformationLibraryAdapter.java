package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.ChatActivity;
import com.yuanxin.clan.core.entity.CompanyInformationLibraryEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/2/10.
 */
public class CompanyInformationLibraryAdapter extends RecyclerView.Adapter<CompanyInformationLibraryAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
//    private ArrayList<String> datas = new ArrayList<>();

    private List<CompanyInformationLibraryEntity> entities;


    public CompanyInformationLibraryAdapter(Context context, List<CompanyInformationLibraryEntity> entities) {
        this.context = context;
        this.entities = entities;
//public ActivityInformationChooseAdapter(Context context, ArrayList<String> dataList) {
//        this.context = context;
//        this.datas = dataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity_company_information_library, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        CompanyInformationLibraryEntity entity = entities.get(position);
        ImageManager.load(context, entity.getImages(), holder.itemCompanyInformationLibraryImage);
        holder.itemCompanyInformationLibraryDesc.setText(entity.getWho());
        holder.itemCompanyInformationLibraryType.setText(entity.getDesc());
        holder.itemCompanyInformationLibraryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ChatActivity.class));
            }
        });
//        EventBus.getDefault().register(this);
//        MessageEvent event=new MessageEvent("setVisible");
//        setVisible(holder.itemInformationChooseImage,event);
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
        return entities.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_company_information_library_image)
        ImageView itemCompanyInformationLibraryImage;
        @BindView(R.id.item_company_information_library_desc)
        TextView itemCompanyInformationLibraryDesc;
        @BindView(R.id.item_company_information_library_type)
        TextView itemCompanyInformationLibraryType;
        @BindView(R.id.item_company_information_library_button)
        TextView itemCompanyInformationLibraryButton;

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
}

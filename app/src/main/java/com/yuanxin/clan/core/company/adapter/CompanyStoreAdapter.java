package com.yuanxin.clan.core.company.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.CompanyStoreEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/22.
 */
public class CompanyStoreAdapter extends RecyclerView.Adapter<CompanyStoreAdapter.ViewHolder> {
    @BindView(R.id.item_shopping_cart_check_image)
    ImageView itemShoppingCartCheckImage;
    @BindView(R.id.item_shopping_cart_test_check_box_layout)
    LinearLayout itemShoppingCartTestCheckBoxLayout;
    private List<CompanyStoreEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private int num = 0;
    private List<CompanyStoreEntity> buyList = new ArrayList<CompanyStoreEntity>();
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;

    public CompanyStoreAdapter(Context context, List<CompanyStoreEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company_store, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CompanyStoreEntity entity = entityList.get(position);
        if (entity.is_checked()) {
            holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.btn_selected);
        } else {
            holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.btn_unselected);
        }
        holder.itemCompanyStoreStore.setText(entity.getStore());
//        holder.itemShoppingCartTestCheckBoxLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (!entity.is_checked()) {
//                    entity.setIs_checked(true);
//                    buyList.add(entity);
//                    holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.btn_selected);
//                } else {
//                    entity.setIs_checked(false);
//                    buyList.remove(entity);
//                    holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.btn_unselected);
//
//                }
//                mOnMyCheckedChangeListener.onCheckedChange(num, buyList);
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
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_shopping_cart_check_image)
        ImageView itemShoppingCartCheckImage;
        @BindView(R.id.item_shopping_cart_test_check_box_layout)
        LinearLayout itemShoppingCartTestCheckBoxLayout;
        @BindView(R.id.item_company_store_store)
        TextView itemCompanyStoreStore;

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

    //返回选中的ITEM数目
    public interface OnMyCheckedChangeListener {
        void onCheckedChange(int num, List<CompanyStoreEntity> buyList);
    }

    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
    }

}

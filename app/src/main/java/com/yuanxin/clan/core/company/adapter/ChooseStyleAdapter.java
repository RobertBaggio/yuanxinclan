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
import com.yuanxin.clan.core.company.bean.ChooseStyleEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/22.
 */
public class ChooseStyleAdapter extends RecyclerView.Adapter<ChooseStyleAdapter.ViewHolder> {
    @BindView(R.id.item_choose_style_image)
    ImageView itemChooseStyleImage;
    @BindView(R.id.item_shopping_cart_check_image)
    ImageView itemShoppingCartCheckImage;
    @BindView(R.id.item_shopping_cart_test_check_box_layout)
    LinearLayout itemShoppingCartTestCheckBoxLayout;
    @BindView(R.id.item_choose_style_text)
    TextView itemChooseStyleText;
    private List<ChooseStyleEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public ChooseStyleAdapter(Context context, List<ChooseStyleEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_choose_style, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        ChooseStyleEntity entity = entityList.get(position);
        ImageManager.load(context, entity.getImage().toString(), R.drawable.banner01, holder.itemChooseStyleImage);
        if (entity.is_checked()) {
            for (int b = 0; b < entityList.size(); b++) {//自己改了
                holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.btn_unselected);
            }
            holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.btn_selected);
        } else {
            holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.btn_unselected);
        }
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


    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_choose_style_image)
        ImageView itemChooseStyleImage;
        @BindView(R.id.item_shopping_cart_check_image)
        ImageView itemShoppingCartCheckImage;
        @BindView(R.id.item_shopping_cart_test_check_box_layout)
        LinearLayout itemShoppingCartTestCheckBoxLayout;

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

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
import com.yuanxin.clan.core.entity.MyOrderAllEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/15.
 * 全部订单 内部 adapter
 */

public class MyOrderAllAdapter extends RecyclerView.Adapter<MyOrderAllAdapter.ViewHolder> {
    private List<MyOrderAllEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public MyOrderAllAdapter(Context context, List<MyOrderAllEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_item_fragment_my_order_all, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        MyOrderAllEntity entity = entityList.get(position);
//        Log.v("lgq","........"+entity.getImage());
        ImageManager.load(context, Url.img_domain+entity.getImage()+Url.imageStyle640x640, R.drawable.by, holder.itemItemFragmentMyOrderAllImage);//图片
        holder.itemItemFragmentMyOrderAllName.setText(entity.getName());//商品
        holder.itemItemFragmentMyOrderAllNumber.setText("x" + entity.getNumber());//数量
//        holder.itemYuanXinFairIntroduce.setText(entity.getIntroduce());
        holder.itemItemFragmentMyOrderAllPrice.setText(entity.getPrice());//价格
        holder.itemItemFragmentMyOrderAllChooseOne.setText("规格：" + entity.getChooseOne());//规格
        holder.itemItemFragmentMyOrderAllChooseTwo.setText("颜色：" + entity.getChooseTwo());//颜色
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
        @BindView(R.id.item_item_fragment_my_order_all_image)
        ImageView itemItemFragmentMyOrderAllImage;
        @BindView(R.id.item_item_fragment_my_order_all_price)
        TextView itemItemFragmentMyOrderAllPrice;
        @BindView(R.id.item_item_fragment_my_order_all_money_layout)
        LinearLayout itemItemFragmentMyOrderAllMoneyLayout;
        @BindView(R.id.item_item_fragment_my_order_all_name)
        TextView itemItemFragmentMyOrderAllName;
        @BindView(R.id.item_item_fragment_my_order_all_number)
        TextView itemItemFragmentMyOrderAllNumber;
        @BindView(R.id.item_item_fragment_my_order_all_choose_one)
        TextView itemItemFragmentMyOrderAllChooseOne;
        @BindView(R.id.item_item_fragment_my_order_all_choose_two)
        TextView itemItemFragmentMyOrderAllChooseTwo;
        @BindView(R.id.item_item_fragment_my_order_all_goods_layout)
        LinearLayout itemItemFragmentMyOrderAllGoodsLayout;

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

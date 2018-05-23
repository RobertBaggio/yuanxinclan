package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.ShoppingCartEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/1.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {

    private List<ShoppingCartEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
//    private boolean isEdit = false;
//    private int num = 0;
//    private boolean flag = false;
//    private boolean isAllCheck = false;
//    private List<String> companyInfoIdList = new ArrayList<>();//选中的数据
//    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;


    public ShoppingCartAdapter(Context context, List<ShoppingCartEntity> list) {
        this.context = context;
        this.entityList = list;
    }

//    public ShoppingCartAdapter(Context context) {
//        this.context = context;
//    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ShoppingCartEntity entity = entityList.get(position);
//        ImageManager.load(context,entity.getImage(),holder.itemShoppingCartImage);
        holder.itemShoppingCartPrice.setText(entity.getPrice());//价格
        holder.itemShoppingCartName.setText(entity.getName());//名称
        holder.itemShoppingCartChooseOne.setText(entity.getChooseOne());//规格
        holder.itemShoppingCartChooseTwo.setText((entity.getChooseTwo()));//颜色
        //加减开始
//        holder.itemShoppingCartAmountView.setGoods_storage(200);////商品库存
//        holder.itemShoppingCartAmountView.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
//            @Override
//            public void onAmountChange(View view, int amount) {
//                String amountOne=String.valueOf(amount);
//
//                Toast.makeText(context.getApplicationContext(), amountOne, Toast.LENGTH_SHORT).show();
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
        @BindView(R.id.item_shopping_cart_checkbox)
        CheckBox itemShoppingCartCheckbox;
        @BindView(R.id.item_shopping_cart_image)
        ImageView itemShoppingCartImage;
        @BindView(R.id.item_shopping_cart_price)
        TextView itemShoppingCartPrice;
        //        @BindView(R.id.item_shopping_cart_amount_view)
//        AmountView itemShoppingCartAmountView;
        @BindView(R.id.item_shopping_cart_name)
        TextView itemShoppingCartName;
        @BindView(R.id.item_shopping_cart_choose_one)
        TextView itemShoppingCartChooseOne;
        @BindView(R.id.item_shopping_cart_choose_two)
        TextView itemShoppingCartChooseTwo;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

//    //返回选中的ITEM数目
//    public interface OnMyCheckedChangeListener {
//        void onCheckedChange(List<String> companyInfoIdList);
//    }
//
//    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
//        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
//    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}

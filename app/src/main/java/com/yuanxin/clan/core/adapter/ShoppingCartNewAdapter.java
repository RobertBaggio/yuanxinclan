package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.ShoppingCartEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/4.
 */


public class ShoppingCartNewAdapter extends RecyclerView.Adapter<ShoppingCartNewAdapter.ViewHolder> {
    private List<ShoppingCartEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private boolean isEdit = false;
    private int num = 0;
    private boolean flag = false;
    private boolean isAllCheck = false;
    private List<String> companyInfoIdList = new ArrayList<>();//选中的数据
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;
    private List<Integer> jobIdList = new ArrayList<Integer>();//子项id 删除的实体
    private List<Integer> shopListId = new ArrayList<>();//结算用
    private Integer momeyList = 0;//总金额
//    private Integer[] carId=new Integer[];


    public ShoppingCartNewAdapter(Context context, List<ShoppingCartEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    //编辑还是不编辑
    public void changeStatus() {
        if (isEdit) {
            isEdit = false;
//            num=0;
        } else {
            isEdit = true;
        }
        notifyDataSetChanged();
    }

    //    //全选
//    public void allCheck(){
//        if(isAllCheck){
//            isAllCheck=false;
//            for (int i = 0; i < entityList.size(); i++){
//                entityList.get(i).setIs_select(false);
//            }
//            notifyDataSetChanged();
//        }else {
//            isAllCheck=true;
//            for (int i = 0; i < entityList.size(); i++){
//                entityList.get(i).setIs_select(true);
//            }
//            notifyDataSetChanged();
//        }
//    }
    //  删除 打勾 全选
    public void cleckAll(boolean is_checked) { //全选 删除多少那里要删除全部
        flag = true;
        if (is_checked) {//如果有打勾 全步删除
            jobIdList.clear();//全部清理
            shopListId.clear();//结算 id
            momeyList = 0;//结算总金额 2
            for (int i = 0; i < entityList.size(); i++) {//统计 个数 实体
                jobIdList.add(entityList.get(i).getJob_id());//全部删除

                shopListId.add(entityList.get(i).getShopListId());//结算id

                String price = entityList.get(i).getPrice();//单价 //结算总金额 3
                int priceOne = Integer.valueOf(price);
                String number = entityList.get(i).getNumber();//数量
                int numberOne = Integer.valueOf(number);
                int allPrice = priceOne * numberOne;
                momeyList = momeyList + allPrice;
            }
            num = entityList.size();//共多少件 全部
        } else {//如果没有打勾全选
            jobIdList.clear();
            shopListId.clear();
            num = 0;
            momeyList = 0;//结算总金额 4
        }
        mOnMyCheckedChangeListener.onCheckedChange(num, jobIdList, shopListId, momeyList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final ShoppingCartEntity entity = entityList.get(position);
        holder.itemShoppingCartCheckbox.setChecked(entity.is_checked());
        holder.itemShoppingCartCheckbox.setOnClickListener(listener);
//        if(entity.is_select()){ //只是全选
//            holder.itemShoppingCartCheckbox.setChecked(true);
//        }else{
//            holder.itemShoppingCartCheckbox.setChecked(false);
//        }
//        ImageManager.load(context,entity.getImage(),holder.itemShoppingCartImage);
        holder.itemShoppingCartPrice.setText(entity.getPrice());//价格
        holder.itemShoppingCartName.setText(entity.getName());//名称
        holder.itemShoppingCartChooseOne.setText(entity.getChooseOne());//规格
        holder.itemShoppingCartChooseTwo.setText((entity.getChooseTwo()));//颜色
        holder.itemShoppingCartEdit.setText(entity.getNumber());//数量

        holder.itemShoppingCartCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {//选择框 选没选 监听
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {//状态切换控件CompoundButton及其子类CheckBox、RadioButton、ToggleButton、switch事件监听

//                if (!flag){
//                    if (b){
//                        num+=1;
//                        jobIdList.add(entity.getJob_id());
//                    }else{
//                        if(num > 0){
//                            num-=1;
//                            jobIdList.remove(entity.getJob_id());
//                        }
//                    }
//                    mOnMyCheckedChangeListener.onCheckedChange(num, jobIdList);
//                }
            }
        });

        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.itemShoppingCartCheckbox.isChecked()) {//点击CheckBox选中
                        holder.itemShoppingCartCheckbox.setChecked(false);//设置没有选中
                        if (num > 0) {//如果有选中
//                            num -= 1;//总数-
                            num = num - 1;
                            jobIdList.remove(entity.getJob_id());//移除选中的

                            shopListId.remove(entity.getShopListId());

                            String price = entity.getPrice();//单价 //结算总金额 5
                            int priceOne = Integer.valueOf(price);
                            String number = entity.getNumber();//数量
                            int numberOne = Integer.valueOf(number);
                            int allPrice = priceOne * numberOne;
                            momeyList = momeyList - allPrice;
                        }


                    } else {
                        holder.itemShoppingCartCheckbox.setChecked(true);//设置选中
//                        num += 1;//总数+1
                        num = num + 1;

                        jobIdList.add(entity.getJob_id());
                        shopListId.add(entity.getShopListId());

                        String price = entity.getPrice();//单价 //结算总金额 6
                        int priceOne = Integer.valueOf(price);
                        String number = entity.getNumber();//数量
                        int numberOne = Integer.valueOf(number);
                        int allPrice = priceOne * numberOne;
                        momeyList = momeyList + allPrice;

                    }
                    mOnMyCheckedChangeListener.onCheckedChange(num, jobIdList, shopListId, momeyList);//选择框 选没选 监听
//                    switch (v.getId()){
//                        case R.id.item_shopping_cart_checkbox:
//                            if (holder.itemShoppingCartCheckbox.isChecked()){//点击CheckBox选中
//                                holder.itemShoppingCartCheckbox.setChecked(false);//设置没有选中
//                                if(num > 0){//如果有选中
//                                    num-=1;//总数-
//                                    jobIdList.remove(entity.getJob_id());//移除选中的
//                                }
//
//
//                            }else {
//                                holder.itemShoppingCartCheckbox.setChecked(true);//设置选中
//                                num+=1;//总数+1
//                                jobIdList.add(entity.getJob_id());
//
//                            }
//                            mOnMyCheckedChangeListener.onCheckedChange(num, jobIdList);//选择框 选没选 监听
//                            break;
//                        default:
//                            break;

//                    }


                }
            });
        }
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.item_shopping_cart_checkbox:


            }
        }
    };


    @Override
    public int getItemCount() {
        return entityList.size();
    }


    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_shopping_cart_checkbox)
        CheckBox itemShoppingCartCheckbox;
        @BindView(R.id.item_shopping_cart_image)
        ImageView itemShoppingCartImage;
        @BindView(R.id.item_shopping_cart_price)
        TextView itemShoppingCartPrice;
        @BindView(R.id.item_shopping_cart_name)
        TextView itemShoppingCartName;
        @BindView(R.id.item_shopping_cart_choose_one)
        TextView itemShoppingCartChooseOne;
        @BindView(R.id.item_shopping_cart_choose_two)
        TextView itemShoppingCartChooseTwo;
        @BindView(R.id.item_shopping_cart_subtract)
        TextView itemShoppingCartSubtract;
        @BindView(R.id.item_shopping_cart_edit)
        TextView itemShoppingCartEdit;
        @BindView(R.id.item_shopping_cart_add)
        TextView itemShoppingCartAdd;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    //子项点击事件1
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    //返回选中的ITEM数目
    public interface OnMyCheckedChangeListener {
        void onCheckedChange(int num, List<Integer> jobIdList, List<Integer> shopIdList, Integer momeyList);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
    }


}

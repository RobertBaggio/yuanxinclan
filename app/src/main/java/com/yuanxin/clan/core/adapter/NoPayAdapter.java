package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.FragmentMyOrderAllEntity;
import com.yuanxin.clan.core.entity.MyOrderAllEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/28 0028 20:19
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class NoPayAdapter {
//}
public class NoPayAdapter extends RecyclerView.Adapter<NoPayAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListenerup mOnItemClickListenerup;
    private boolean ifshowwl;
    private boolean ifqr=true;
    private String price;
    private FragmentMyOrderAllEntity entity;
//    private ArrayList<String> datas = new ArrayList<>();

    private List<FragmentMyOrderAllEntity> entityList;//所有信息
    private List<MyOrderAllEntity> detailList = new ArrayList<>();//商品详情
    private List<List<MyOrderAllEntity>> detailListList = new CopyOnWriteArrayList<>();//数组的数组

    //    private MyOrderAllAdapter adapter;
    private ViewHolder holder;
    private int number;

    public void getGoodsInfo(List<MyOrderAllEntity> detailList) {
        this.detailList = detailList;
    }

    public NoPayAdapter(Context context, List<FragmentMyOrderAllEntity> entities) {
        this.context = context;
        this.entityList = entities;
    }

//    public FragmentMyOrderAllAdapter(Context context, List<FragmentMyOrderAllEntity> entities,List<List<MyOrderAllEntity>> detailList) {
//        this.context = context;
//        this.entityList = entities;
//        this.detailListList=detailList;
//
//    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.selladapterlayout, null);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        try {


            entity = entityList.get(position);
//        ImageManager.load(context,entity.getEpImage1())
//                .placeholder(R.drawable.by).error(R.drawable.by)
//                .into(holder.itemCompanyInformationDetailImage);
//        holder.itemFragmentMyOrderAllOrderNum.setText(entity.getOrderId());//订单号
            holder.itemFragmentMyOrderAllOrderNum.setText(entity.getCreateDt());//订单号
            number = entity.getNumber();
//        int numberOne=Integer.valueOf(number);
//        String price=entity.getPrice();
//        int priceOne=Integer.valueOf(price);
//        int all=numberOne*priceOne;
            String all = entity.getTotal();
            String carriage = entity.getCarriage();
            holder.itemFragmentMyOrderAllState.setText("待付款");//待付款
            holder.ddnumte.setText("数量（" + number + "）" + "合计：" );
            float aa = Float.parseFloat(entity.getTotal());
            holder.ddallpayte.setText(aa+" 元");
            holder.ddyunfeite.setText(entity.getCarriage());
            holder.item_item_fragment_my_order_all_price.setText(entity.getPrice());
            holder.item_item_fragment_my_order_all_name.setText(entity.getCommodityNm());
            holder.item_item_fragment_my_order_all_choose_one.setText("规 格："+entity.getCommoditySp());
            holder.item_item_fragment_my_order_all_choose_two.setText("颜 色："+entity.getCommodityColor());
            ImageManager.load(context,entity.getCommodityImage1(),R.drawable.by,holder.item_item_fragment_my_order_all_image);
            holder.ddwliucontentname.setText(entity.getExpressCompany());
            holder.ddwliucontentcode.setText(entity.getExpressNumber());
            holder.item_item_fragment_my_order_all_number.setText("x"+number);

//        holder.itemFragmentMyOrderAllAll.setText("共（" + number + ")" + "件商品合计：" + all + "元（含运费￥" + carriage + ")");//数量 总价 运费
            //holder.itemFragmentMyOrderAllRecyclerView//
            //里面那个RecyclerView
        }catch (Exception e){
            e.printStackTrace();
        }
//        getGankInto();
//        adapter = new MyOrderAllAdapter(context, detailList);
//        adapter.setOnItemClickListener(new MyOrderAllAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
////                Intent intent = new Intent(getActivity(), CompanyDetailWebActivity.class);
////                intent.putExtra("url", newEntityOnes.get(position).getContent());
////                startActivity(intent);
//            }
//        });
        holder.itemFragmentMyOrderAllRecyclerView.setLayoutManager(new LinearLayoutManager(context));
//        activityOneRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
//        activityYuanXinFairNewRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//
//        activityOneRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
//        holder.itemFragmentMyOrderAllRecyclerView.setAdapter(adapter);
        holder.itemFragmentMyOrderAllRecyclerView.setFocusable(false);//导航栏切换不再focuse


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

            holder.ddwliuli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ifshowwl){
                        holder.ddwliucontentli.setVisibility(View.VISIBLE);
                    }else {
                        holder.ddwliucontentli.setVisibility(View.GONE);
                    }
                    ifshowwl=!ifshowwl;
                }
            });
            holder.xiugaijiagete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListenerup.onItemClickup(v,position);
//                    dad();
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_fragment_my_order_all)
        TextView itemFragmentMyOrderAll;
        @BindView(R.id.xiugaijiagete)
        TextView xiugaijiagete;
        @BindView(R.id.ddwliuline)
        TextView ddwliuline;
        @BindView(R.id.qrshouhuote)
        TextView qrshouhuote;
        @BindView(R.id.item_item_fragment_my_order_all_number)
        TextView item_item_fragment_my_order_all_number;
        @BindView(R.id.item_item_fragment_my_order_all_image)
        ImageView item_item_fragment_my_order_all_image;
        @BindView(R.id.item_fragment_my_order_all_order_num)
        TextView itemFragmentMyOrderAllOrderNum;
        @BindView(R.id.ddwliucontentname)
        TextView ddwliucontentname;
        @BindView(R.id.item_item_fragment_my_order_all_name)
        TextView item_item_fragment_my_order_all_name;
        @BindView(R.id.ddallpayte)
        TextView ddallpayte;
        @BindView(R.id.ddwliucontentcode)
        TextView ddwliucontentcode;
        @BindView(R.id.item_item_fragment_my_order_all_choose_two)
        TextView item_item_fragment_my_order_all_choose_two;
        @BindView(R.id.ddyunfeite)
        TextView ddyunfeite;
        @BindView(R.id.ddwliucontentnamestuta)
        TextView ddwliucontentnamestuta;
        @BindView(R.id.ddnumte)
        TextView ddnumte;
        @BindView(R.id.item_item_fragment_my_order_all_choose_one)
        TextView item_item_fragment_my_order_all_choose_one;
        @BindView(R.id.item_item_fragment_my_order_all_price)
        TextView item_item_fragment_my_order_all_price;
        @BindView(R.id.item_fragment_my_order_all_state)
        TextView itemFragmentMyOrderAllState;
        @BindView(R.id.item_fragment_my_order_all_recycler_view)
        RecyclerView itemFragmentMyOrderAllRecyclerView;
        @BindView(R.id.ddwliuli)
        LinearLayout ddwliuli;
        @BindView(R.id.ddwliucontentli)
        LinearLayout ddwliucontentli;
        @BindView(R.id.ddwliucontentnamestutali)
        LinearLayout ddwliucontentnamestutali;
//        @BindView(R.id.item_fragment_my_order_all_all)
//        TextView itemFragmentMyOrderAllAll;

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



    public interface OnItemClickListenerup{
        void onItemClickup(View view, int position);
    }
    public void setOnItemLongClickListener(NoPayAdapter.OnItemClickListenerup onItemLongClickListener) {
        this.mOnItemClickListenerup = onItemLongClickListener;
    }
}


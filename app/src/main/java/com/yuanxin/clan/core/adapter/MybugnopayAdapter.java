package com.yuanxin.clan.core.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FragmentMyOrderAllEntity;
import com.yuanxin.clan.core.entity.MyOrderAllEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/25 0025 14:24
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MybugnopayAdapter extends RecyclerView.Adapter<MybugnopayAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnItemTobuyClickListener mOnItemTobuyClickListener;
    private boolean ifshowwl;
    private boolean ifqr=true;
    private FragmentMyOrderAllEntity entity;
    private ViewHolder holder;
//    private ArrayList<String> datas = new ArrayList<>();

    private List<FragmentMyOrderAllEntity> entityList;//所有信息
    private List<MyOrderAllEntity> detailList = new ArrayList<>();//商品详情
    private List<List<MyOrderAllEntity>> detailListList = new CopyOnWriteArrayList<>();//数组的数组

//    private MyOrderAllAdapter adapter;

    public void getGoodsInfo(List<MyOrderAllEntity> detailList) {
        this.detailList = detailList;
    }

    public MybugnopayAdapter(Context context, List<FragmentMyOrderAllEntity> entities) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mybuyordernopay, null);
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
            holder.itemFragmentMyOrderAllOrderNum.setText(entity.getCreateDt());//时间
            int number = entity.getNumber();
//        int numberOne=Integer.valueOf(number);
//        String price=entity.getPrice();
//        int priceOne=Integer.valueOf(price);
//        int all=numberOne*priceOne;
            String all = entity.getTotal();
            String status = entity.getOrderStatus();
            if (status.equals("1")){
                holder.itemFragmentMyOrderAllState.setText("待付款");//待付款
                holder.ddwliuli.setVisibility(View.GONE);
                holder.ddwliucontentli.setVisibility(View.GONE);
                holder.ddwliuline.setVisibility(View.GONE);
//                strQr.indexOf("http") > -1
            }else if ("2,3,5,6".indexOf(status)>-1){
                holder.itemFragmentMyOrderAllState.setText("待收货");//待付款

            }else if ("4,7".indexOf(status)>-1){
                holder.itemFragmentMyOrderAllState.setText("已完成");//待付款
            }

            holder.ddnumte.setText("共（" + number + "）" + "件商品合计：" );
            float aa = Float.parseFloat(entity.getPrice());
            holder.ddallpayte.setText(aa*number+" 元");
            holder.ddyunfeite.setText(entity.getCarriage());
            holder.item_item_fragment_my_order_all_price.setText(entity.getPrice());
            holder.item_item_fragment_my_order_all_name.setText(entity.getCommodityNm());
            holder.item_item_fragment_my_order_all_choose_one.setText("规 格："+entity.getCommoditySp());
            holder.item_item_fragment_my_order_all_choose_two.setText("颜 色："+entity.getCommodityColor());
            ImageManager.load(context, entity.getCommodityImage1(),R.drawable.by,holder.item_item_fragment_my_order_all_image);
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
            holder.gotopayte.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemTobuyClickListener.onItemClick(v,position);
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
        @BindView(R.id.ddwliuline)
        TextView ddwliuline;
        @BindView(R.id.gotopayte)
        TextView gotopayte;
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
    public interface OnItemTobuyClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemTobuyClickListener(OnItemTobuyClickListener onItemClickListener) {
        this.mOnItemTobuyClickListener = onItemClickListener;
    }

    private void qrshouhuo(String id) {
        String url = Url.qrshouhuo;
        RequestParams params = new RequestParams();
        params.put("orderUuid",id );//企业id
        params.put("userid", UserNative.getId());//企业id
        params.put("usernm", UserNative.getName());//企业id
        params.put("businessId", entity.getBusinessId());
        params.put("orderPrice", entity.getTotal());
        doHttpPost(url, params, new  BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(context, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        holder.itemFragmentMyOrderAllState.setText("已完成");
                        ifqr =false;
                        ToastUtil.showSuccess(context, object.getString("msg"), Toast.LENGTH_SHORT);
                        Log.v("lgq","........qq");
                    } else {
                        ToastUtil.showWarning(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    ToastUtil.showWarning(context, "数据解析出错", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void showNormalDialogOne(final String id) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您是否确定已收货！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        qrshouhuo(id);
                        Log.v("lgq","...........sss");
//                        finish();
//                    deleteList.add(entity);
                        //entityList.get(position).getUserId()
//                        deleteCompanyMemberToWeb(uid, position);
//                        notifyDataSetChanged();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }

    protected void doHttpPost(String url, RequestParams params, final BaseActivity.RequestCallback c) {
        AsyncHttpClient client = new AsyncHttpClient();
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {//测试1》2
//            ViewUtils.AlertDialog(context, "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//        } else {
            params.put("key", aesKes);
            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    c.onFailure(i, headers, s, throwable);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        c.onSuccess(i, headers, s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//        }
    }

}

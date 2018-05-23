package com.yuanxin.clan.core.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
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
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
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
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/28 0028 10:09
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class SellAdapter {
//}
public class SellAdapter extends RecyclerView.Adapter<SellAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
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

    public SellAdapter(Context context, List<FragmentMyOrderAllEntity> entities) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.okselllayout, null);
        holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
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
            holder.itemFragmentMyOrderAllState.setText("已完成");//待付款
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

    private void updateprice(String id, final String price) {
        String url = Url.updatePrice;
        RequestParams params = new RequestParams();
        params.put("orderId",id );//企业id
        params.put("orderPrice", price);//企业id
        params.put("userId", UserNative.getId());//企业id
        params.put("userNm", UserNative.getName());//企业id
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(context, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        Logger.v("............"+s);
//                        holder.item_item_fragment_my_order_all_price.setText(price);
                        holder.ddallpayte.setText(price);
                        ifqr =false;
                        ToastUtil.showSuccess(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
//                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    ToastUtil.showWarning(context, "数据解析出错", Toast.LENGTH_SHORT);
                }
            }
        });
    }
    //更新dialog
    public void dad() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.updpricedialog, null);
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

//        lp.width = 900; // 宽度
//        lp.height = 670; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
        final EditText priceet = (EditText)view.findViewById(R.id.priceet);
        TextView qd = (TextView)view.findViewById(R.id.qdapk);
        TextView qx = (TextView)view.findViewById(R.id.qcapk);
        final TextView pnull=(TextView)view.findViewById(R.id.pricenullte);
//        co.setText(updateContent);
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                price = priceet.getText().toString();
                if (TextUtil.isEmpty(priceet.getText().toString())){
                    pnull.setVisibility(View.VISIBLE);
                    Logger.v(".....");
                    return;
                }else {
                    pnull.setVisibility(View.GONE);

                }
                float a = Float.parseFloat(price);
                if (a>0){
                    pnull.setVisibility(View.GONE);
                }else {
                    pnull.setVisibility(View.VISIBLE);
                    return;
                }
                updateprice(entity.getOrderId(),price);

                dialog.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
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

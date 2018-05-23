package com.yuanxin.clan.core.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.BusinessIdsentity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.bean.OrderCommodity;
import com.yuanxin.clan.core.market.bean.ShoppingCommodity;
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

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/4.
 */


public class ShoppingCommodityAdapter extends RecyclerView.Adapter<ShoppingCommodityAdapter.ViewHolder> {

    private List<ShoppingCommodity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnBidChangeListener mOnBidChangeListener;
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;
    private List<ShoppingCommodity> buyList = new ArrayList<>();
    private List<BusinessIdsentity> mIdsentities = new ArrayList<>();

    public ShoppingCommodityAdapter(Context context, List<ShoppingCommodity> list, List<BusinessIdsentity> list2) {
        this.context = context;
        this.entityList = list;
        this.mIdsentities = list2;
    }

    //  删除 打勾 全选
    public void cleckAll(boolean is_checked) { //全选 删除多少那里要删除全部
        if (is_checked) {//如果有打勾 全步删除
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).getShopList().setChecked(true);
            }
            buyList.clear();
            buyList.addAll(entityList);
        } else {//如果没有打勾全选
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).getShopList().setChecked(false);
            }
            buyList.clear();
        }
        mOnMyCheckedChangeListener.onCheckedChange(buyList);
        notifyDataSetChanged();
    }

    public void onebidcleckALL(boolean ifc,int bid){
//        buyList.clear();
        if (ifc) {//如果有打勾 全步删除
            for (int j=0;j<buyList.size();j++){
                if (Integer.parseInt(buyList.get(j).getShopList().getBusinessId())==bid){
                    buyList.remove(j);
                }
            }

            for (int i = 0; i < entityList.size(); i++) {
                if (Integer.parseInt(entityList.get(i).getShopList().getBusinessId())==bid){

                    entityList.get(i).getShopList().setChecked(true);
                    ShoppingCommodity shoppingCommodity = new ShoppingCommodity();
                    shoppingCommodity = entityList.get(i);
                    buyList.add(shoppingCommodity);
                }
            }
//            buyList.addAll(entityList);
        } else {//如果没有打勾全选
//            buyList.clear();
            for (int i = 0; i < entityList.size(); i++) {
                if (Integer.parseInt(entityList.get(i).getShopList().getBusinessId())==bid){
                    for (int j=0;j<buyList.size();j++){
                        if (Integer.parseInt(buyList.get(j).getShopList().getBusinessId())==bid){
                            buyList.remove(j);
                        }
                    }
                    entityList.get(i).getShopList().setChecked(false);

//                    buyList.remove(entityList.get(i).getNumid());
                }
//                else {
//                    ShoppingCommodity shoppingCommodity = new ShoppingCommodity();
//                    shoppingCommodity = entityList.get(i);
//                    buyList.add(shoppingCommodity);
//                }
            }
        }
        mOnMyCheckedChangeListener.onCheckedChange(buyList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopping_cart_test, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final OrderCommodity entity = entityList.get(position).getShopList();
        if (entityList.get(position).getOneid()==0){
            holder.ifshoulinear.setVisibility(View.VISIBLE);
            holder.iflishow.setVisibility(View.VISIBLE);
            for (int i = 0;i<mIdsentities.size();i++){
                if (Integer.parseInt(entity.getBusinessId())==mIdsentities.get(i).getBusinessId()){
                    holder.bnamete.setText(mIdsentities.get(i).getBusinessNm());
                }
            }
        }

            if (entity.isChecked()) { //如果是选中就选中
            holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.market_shopping_btn_pre);
                holder.onebid_checkbox.setBackgroundResource(R.drawable.market_shopping_btn_pre);
        } else {//如果不是选中就不选中
            holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.market_shopping_btn_normal);
                holder.onebid_checkbox.setBackgroundResource(R.drawable.market_shopping_btn_normal);
        }
//        Log.v("lgq","tup===="+entity.getCommodityImage1());
        if (!TextUtil.isEmpty(entity.getCommodityImage1())) {
            ImageManager.load(context, entity.getCommodityImage1(), R.drawable.list_img, holder.itemShoppingCartImage);
        }
        holder.itemShoppingCartPrice.setText("¥" + entity.getCommodityPrice());//价格
        holder.itemShoppingCartName.setText(entity.getCommodityNm());//名称
//        holder.imagec.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mOnBidChangeListener.onCheckedChange(Integer.parseInt(entity.getBusinessId()));
//            }
//        });
        holder.onebid_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mOnBidChangeListener.onCheckedChange(isChecked,Integer.parseInt(entity.getBusinessId()));
            }
        });
        holder.bnamete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        holder.leftli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
        /*
        * 1、服务 没有颜色、规格、库存
        * 2、企业
        * 3、礼品
        * 4、集市
        * */
        switch (entity.getShopDataFrom()) {
            case 1:
                holder.itemShoppingCartChooseOne.setVisibility(View.GONE);
                holder.itemShoppingCartChooseTwo.setVisibility(View.GONE);
                break;
            case 2:
            case 3:
            case 4:
            default:
                holder.itemShoppingCartChooseOne.setText("规格：" + entity.getCommoditySp());//规格
                if (entity.getCommodityColor().equals("-1")){
                    holder.itemShoppingCartChooseTwo.setVisibility(View.GONE);
                }
                holder.itemShoppingCartChooseTwo.setText("颜色：" + entity.getCommodityColor());//颜色
                break;
        }
        holder.itemShoppingCartEdit.setText(entity.getCommodityNumber() + "");
        holder.itemShoppingCartTestCheckBoxLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.isChecked()) {
                    entity.setChecked(false);
                    for (int i = 0; i < buyList.size(); i++) {
                        if (buyList.get(i).getCarId() == entityList.get(position).getCarId()) {
                            buyList.remove(i);
                            break;
                        }
                    }
                    holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.market_shopping_btn_normal);
                } else {
                    entity.setChecked(true);
                    buyList.add(entityList.get(position));
                    holder.itemShoppingCartCheckImage.setBackgroundResource(R.drawable.market_shopping_btn_pre);
                }
                mOnMyCheckedChangeListener.onCheckedChange(buyList);
            }
        });

        holder.itemShoppingCartAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = Url.updataDeleteCar;
                RequestParams params = new RequestParams();
                params.put("carIds", entityList.get(position).getCarId());
                params.put("del", false);
                params.put("change", true);
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
                                entity.setCommodityNumber(entity.getCommodityNumber() + 1);
                                holder.itemShoppingCartEdit.setText(String.valueOf(entity.getCommodityNumber()));
                                mOnMyCheckedChangeListener.onCheckedChange(buyList);
                            } else {
                                ToastUtil.showWarning(context, object.getString("msg"), Toast.LENGTH_SHORT);
                            }
                        } catch (JSONException e) {
                            Logger.d("json 解析出错");
                        }
                    }
                });
            }
        });
        holder.itemShoppingCartSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (entity.getCommodityNumber() > 1) {
                    String url = Url.updataDeleteCar;
                    RequestParams params = new RequestParams();
                    params.put("carIds", entityList.get(position).getCarId());
                    params.put("del", false);
                    params.put("change", false);
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
                                    entity.setCommodityNumber(entity.getCommodityNumber() - 1);
                                    holder.itemShoppingCartEdit.setText(String.valueOf(entity.getCommodityNumber()));
                                    mOnMyCheckedChangeListener.onCheckedChange(buyList);
                                } else {
                                    ToastUtil.showWarning(context, object.getString("msg"), Toast.LENGTH_SHORT);
                                }
                            } catch (JSONException e) {
                                Logger.d("json 解析出错");
                            }
                        }
                    });
                }
            }
        });
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mOnMyCheckedChangeListener.onCheckedChange(buyList);//选择框 选没选 监听
                    mOnItemClickListener.onItemClick(v,position);
                }
            });
        }
    }

    public void clearData() {
        buyList.clear();
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }

    public List<ShoppingCommodity> getDataList() {
        return buyList;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_shopping_cart_check_image)
        ImageView itemShoppingCartCheckImage;
        @BindView(R.id.item_shopping_cart_test_check_box_layout)
        LinearLayout itemShoppingCartTestCheckBoxLayout;
        @BindView(R.id.item_shopping_cart_image)
        ImageView itemShoppingCartImage;
        @BindView(R.id.onebid_checkbox)
        CheckBox onebid_checkbox;
        @BindView(R.id.item_shopping_cart_price)
        TextView itemShoppingCartPrice;
        @BindView(R.id.item_shopping_cart_subtract)
        TextView itemShoppingCartSubtract;
        @BindView(R.id.item_shopping_cart_edit)
        TextView itemShoppingCartEdit;
        @BindView(R.id.item_shopping_cart_add)
        TextView itemShoppingCartAdd;
        @BindView(R.id.item_shopping_cart_name)
        TextView itemShoppingCartName;
        @BindView(R.id.item_shopping_cart_choose_one)
        TextView itemShoppingCartChooseOne;
        @BindView(R.id.item_shopping_cart_choose_two)
        TextView itemShoppingCartChooseTwo;
        @BindView(R.id.iflishow)
        TextView iflishow;
        @BindView(R.id.bnamete)
        TextView bnamete;
        @BindView(R.id.ifshoulinear)
        LinearLayout ifshoulinear;
        @BindView(R.id.leftli)
        LinearLayout leftli;

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
        void onCheckedChange(List<ShoppingCommodity> buyList);
    }

    public interface OnBidChangeListener {
        void onCheckedChange(boolean cc,int id);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
    }

    public void setBidListentr(OnBidChangeListener bidListentr){
        this.mOnBidChangeListener =bidListentr;
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

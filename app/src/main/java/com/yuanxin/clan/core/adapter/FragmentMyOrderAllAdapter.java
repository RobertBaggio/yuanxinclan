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
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/14.
 * 全部订单Adapter
 */

public class FragmentMyOrderAllAdapter extends RecyclerView.Adapter<FragmentMyOrderAllAdapter.ViewHolder> {
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private boolean ifshowwl;
    private boolean ifqr=true;
    private FragmentMyOrderAllEntity entity;
    private ViewHolder holder;
//    private ArrayList<String> datas = new ArrayList<>();

    private List<FragmentMyOrderAllEntity> entityList;//所有信息


    public FragmentMyOrderAllAdapter(Context context, List<FragmentMyOrderAllEntity> entities) {
        this.context = context;
        this.entityList = entities;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fragment_my_order_all, null);
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
        holder.itemFragmentMyOrderAllOrderNum.setText(entity.getCreateDt());//时间
        int number = entity.getNumber();
        String all = entity.getTotal();
        String status = entity.getOrderStatus();
            if (status.equals("1")){
                holder.itemFragmentMyOrderAllState.setText("待付款");//待付款
                holder.ddwliuli.setVisibility(View.GONE);
                holder.ddwliucontentli.setVisibility(View.GONE);
                holder.ddwliuline.setVisibility(View.GONE);
                holder.qrshouhuote.setVisibility(View.GONE);

            }else if ("2,3,5,6".indexOf(status)>-1){
                if (status.equals("2")){
                    holder.itemFragmentMyOrderAllState.setText("待发货");//待付款
                    holder.ddwliuli.setVisibility(View.GONE);
//                    holder.qrshouhuote.setVisibility(View.VISIBLE);
                }else if (status.equals("5")){
                    holder.itemFragmentMyOrderAllState.setText("申请退款中");//待付款
                    holder.qrshouhuote.setVisibility(View.GONE);
                }else if (status.equals("3")){
                    holder.itemFragmentMyOrderAllState.setText("待收货");//待付款
                    holder.qrshouhuote.setVisibility(View.VISIBLE);
                }
            }else if ("4,7".indexOf(status)>-1){
                holder.itemFragmentMyOrderAllState.setText("已完成");//待付款
                holder.qrshouhuote.setVisibility(View.GONE);
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
            if (TextUtil.isEmpty(entity.getExpressCompany())){
                holder.ddwliucontentname.setText("数据缺失~");
                holder.ddwliucontentcode.setText("888888~");
                holder.ddwliucontentname.setTextColor(context.getResources().getColor(R.color.gray_white));
                holder.ddwliucontentcode.setTextColor(context.getResources().getColor(R.color.gray_white));
            }else {
                holder.ddwliucontentname.setText(entity.getExpressCompany());
                holder.ddwliucontentcode.setText(entity.getExpressNumber());
            }
            holder.item_item_fragment_my_order_all_number.setText("x"+number);

//        holder.itemFragmentMyOrderAllAll.setText("共（" + number + ")" + "件商品合计：" + all + "元（含运费￥" + carriage + ")");//数量 总价 运费
        //holder.itemFragmentMyOrderAllRecyclerView//
        //里面那个RecyclerView
        }catch (Exception e){
            e.printStackTrace();
        }
        holder.itemFragmentMyOrderAllRecyclerView.setLayoutManager(new LinearLayoutManager(context));
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
            holder.qrshouhuote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (!ifqr){
//                        Toast.makeText(context, "已提交", Toast.LENGTH_SHORT).show();
//                        Log.v("lgq","...............yyy");
//                        return;
//                    }
                    showNormalDialogOne(holder, entity.getOrderId());
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

    private void qrshouhuo(final ViewHolder holder, String id) {
        String url = Url.qrshouhuo;
        RequestParams params = new RequestParams();
        params.put("orderUuid",id );//企业id
        params.put("userid", UserNative.getId());//企业id
        params.put("usernm", UserNative.getName());//企业id
        params.put("businessId", entity.getBusinessId());
        params.put("orderPrice", entity.getTotal());
        doHttpPost(url, params, new BaseFragment.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(context, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        holder.qrshouhuote.setVisibility(View.GONE);
                        holder.itemFragmentMyOrderAllState.setText("已完成");
                        ifqr =false;
                        ToastUtil.showSuccess(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    ToastUtil.showWarning(context, "数据解析出错", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void showNormalDialogOne(final ViewHolder holder,final String id) {
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
                        qrshouhuo(holder, id);
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

    protected void doHttpPost(String url, RequestParams params, final BaseFragment.RequestCallback c) {
        AsyncHttpClient client = new AsyncHttpClient();
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {
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

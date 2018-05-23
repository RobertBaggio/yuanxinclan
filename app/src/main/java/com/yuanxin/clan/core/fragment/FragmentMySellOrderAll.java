package com.yuanxin.clan.core.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.NoPayAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FragmentMyOrderAllEntity;
import com.yuanxin.clan.core.entity.MyOrderAllEntity;
import com.yuanxin.clan.core.entity.OrderDataActivity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/4/21.
 * 待付款 卖家
 */
public class FragmentMySellOrderAll extends BaseFragment {
    @BindView(R.id.fragment_my_order_all_recycler_view)
    RecyclerView fragmentMyOrderAllRecyclerView;
    @BindView(R.id.fragment_my_order_all_spring_view)
    SpringView fragmentMyOrderAllSpringView;
    private String status, commodityColor, image, commodityPrice, commodityNm, commoditySp;
    private int number, numberOne,commodityNumber;
    private NoPayAdapter adapter;//CompanyInformationDetailChooseAdapter
    private List<FragmentMyOrderAllEntity> allEntities = new ArrayList<>();//所有数据
    private List<MyOrderAllEntity> detailList = new ArrayList<>();//订单数据
    private String expressNumber,expressCompany,price;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_sell_order_all;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new NoPayAdapter(getContext(), allEntities);
        adapter.setOnItemClickListener(new NoPayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent _intent2 = new Intent(getContext(),OrderDataActivity.class);
                _intent2.putExtra("orderUuid",allEntities.get(position).getOrderUuid());
                startActivity(_intent2);
            }
        });
        adapter.setOnItemLongClickListener(new NoPayAdapter.OnItemClickListenerup() {
            @Override
            public void onItemClickup(View view, int position) {
                dad(position,allEntities.get(position).getTotal());
            }
        });
        fragmentMyOrderAllRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMyOrderAllRecyclerView.setAdapter(adapter);
        getGankInto(1);

        fragmentMyOrderAllSpringView.setHeader(new RotationHeader(getContext()));
        fragmentMyOrderAllSpringView.setFooter(new RotationFooter(getContext()));
        fragmentMyOrderAllSpringView.setType(SpringView.Type.OVERLAP);
        fragmentMyOrderAllSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentMyOrderAllSpringView.onFinishFreshAndLoad();
                    }
                }, 1000);
                allEntities.clear();//外层数据

                currentPage = 1;
                Log.v("lgq","j....."+currentPage);
                getGankInto(currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentMyOrderAllSpringView.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage += 1;
                if (currentPage>pageCount){
                    ToastUtil.showInfo(getContext(), "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getGankInto(currentPage);
            }
        });
    }

    private void getGankInto(int page) {
        String url = Url.newgetMymarketOrder;
        RequestParams params = new RequestParams();
        params.put("businessId", UserNative.getId());//企业id
        params.put("pageNumber", page);//企业id
        params.put("orderStatus", 1);//企业id
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    pageCount = Integer.parseInt(object.getString("pageCount"));
                    if (object.getString("success").equals("true")) {
                        JSONArray dataArray = object.getJSONArray("data");
                        detailList.clear();//里层数据
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            String orderId = dataObject.getString("orderId");//订单号
                            String orderUuid = dataObject.getString("orderUuid");//订单号
                            String orderStatus = dataObject.getString("orderStatus");//订单状态 1待付款 2待收货 3 订单完成
                            String orderPrice = dataObject.getString("orderPrice");//总价
                            String expressCost = dataObject.getString("expressCost");//运费
                            String createDt = dataObject.getString("createDt");//运费
                            expressNumber=dataObject.getString("expressNumber");
                            expressCompany=dataObject.getString("expressCompany");
                            String shopList = dataObject.getString("shopList");
                            if (!shopList.equals("null")) {
                                JSONArray shopListArray = dataObject.getJSONArray("shopList");
                                for (int c = 0; c < shopListArray.length(); c++) {
                                    JSONObject shopListObject = shopListArray.getJSONObject(c);
                                    commodityColor = shopListObject.getString("commodityColor");//颜色
                                    String commodityImage1 = shopListObject.getString("commodityImage1");//图片
                                    image = Url.img_domain + commodityImage1+Url.imageStyle640x640;
                                    commodityNumber = shopListObject.getInt("commodityNumber");//数量

                                    commodityPrice = shopListObject.getString("commodityPrice");//单价
                                    commodityNm = shopListObject.getString("commodityNm");//名称
                                    commoditySp = shopListObject.getString("commoditySp");//规格
                                    MyOrderAllEntity detailEntity = new MyOrderAllEntity();//里层数据
                                    detailEntity.setImage(image);
                                    detailEntity.setPrice(commodityPrice);//单价
                                    detailEntity.setNumber(commodityNumber);//件数
                                    detailEntity.setName(commodityNm);//商品名称
                                    detailEntity.setChooseOne(commoditySp);//规格
                                    detailEntity.setChooseTwo(commodityColor);//颜色
                                    detailList.add(detailEntity);
                                }
                                adapter.getGoodsInfo(detailList);
                            }
                            FragmentMyOrderAllEntity entity = new FragmentMyOrderAllEntity();//外层数据
                            entity.setOrderId(orderId);//订单号
                            entity.setState(status);//待付款
                            entity.setNumber(commodityNumber);//共多少件
                            entity.setTotal(orderPrice);//总金额
                            entity.setCarriage(expressCost);//含运费
                            entity.setCreateDt(createDt);
                            entity.setPrice(commodityPrice);
                            entity.setCommoditySp(commoditySp);
                            entity.setCommodityColor(commodityColor);
                            entity.setCommodityNm(commodityNm);
                            entity.setCommodityImage1(image);
                            entity.setExpressCompany(expressCompany);
                            entity.setExpressNumber(expressNumber);
                            entity.setOrderUuid(orderUuid);
                            allEntities.add(entity);
                        }
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    ToastUtil.showWarning(getContext(), "数据解析出错", Toast.LENGTH_SHORT);
                }
            }
        });
    }
    //更新dialog
    public void dad(final int posion,String jiage) {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.updpricedialog, null);
        dialog = new Dialog(getContext());
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
        priceet.setText(jiage);
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
                updateprice(allEntities.get(posion).getOrderId(),price);

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

    private void updateprice(String id, final String price) {
        String url = Url.updatePrice;
        RequestParams params = new RequestParams();
        params.put("orderId",id );//企业id
        params.put("orderPrice", price);//企业id
        params.put("userId", UserNative.getId());//企业id
        params.put("userNm", UserNative.getName());//企业id
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Logger.v("............"+s);

                    if (object.getString("success").equals("true")) {
                        allEntities.clear();//外层数据
                        getGankInto(1);
//                        adapter.notifyDataSetChanged();
                        ToastUtil.showSuccess(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
//                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    ToastUtil.showWarning(getContext(), "数据解析出错", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

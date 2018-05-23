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
import com.yuanxin.clan.core.adapter.DaiShouHuoAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FragmentMyOrderAllEntity;
import com.yuanxin.clan.core.entity.MyOrderAllEntity;
import com.yuanxin.clan.core.entity.OrderDataActivity;
import com.yuanxin.clan.core.http.Url;
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
 * Created by lenovo1 on 2017/2/25.
 * //待收货类
 */
public class FragmentMyOrderNonPayment extends BaseFragment {
    @BindView(R.id.fragment_my_order_all_recycler_view)
    RecyclerView fragmentMyOrderAllRecyclerView;
    @BindView(R.id.fragment_my_order_all_spring_view)
    SpringView fragmentMyOrderAllSpringView;

    private DaiShouHuoAdapter adapter;//CompanyInformationDetailChooseAdapter
    private List<FragmentMyOrderAllEntity> allEntities = new ArrayList<>();//所有数据
    private List<MyOrderAllEntity> detailList = new ArrayList<>();//订单数据
    private String status, commodityColor, image, commodityPrice, commodityNm, commoditySp,businessId;
    private int  numberOne,commodityNumber;
    private String expressNumber,expressCompany,commodityImage1,updateNm,createNm,consignee,paymentName;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_order_non_payment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new DaiShouHuoAdapter(getContext(), allEntities);
        adapter.setOnItemClickListener(new DaiShouHuoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent _intent2 = new Intent(getContext(),OrderDataActivity.class);
                _intent2.putExtra("orderUuid",allEntities.get(position).getOrderId());
                _intent2.putExtra("entity", allEntities.get(position));
                startActivity(_intent2);
            }
        });
        adapter.setOnItemClickListenerback(new DaiShouHuoAdapter.OnItemClickListenerback() {
            @Override
            public void onItemClick(View view, int position) {
                dad(position);
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

    private void sqtuik(String con,int id) {
        String url = Url.sqtuik;
        RequestParams params = new RequestParams();
        params.put("orderUuid",allEntities.get(id).getOrderId() );//企业id
        params.put("refundReason", con);//企业id
        params.put("userid", UserNative.getId());//企业id
        params.put("usernm", UserNative.getName());//企业id
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        allEntities.clear();
                        getGankInto(1);
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    ToastUtil.showWarning(getContext(), "数据解析出错", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    //更新dialog
    public void dad(final int posion) {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.sqtuik_dialog, null);
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
        TextView qd = (TextView)view.findViewById(R.id.qdapk);
        TextView qx = (TextView)view.findViewById(R.id.qcapk);
        final TextView pnull=(TextView)view.findViewById(R.id.pricenullte);
//        co.setText(updateContent);
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = priceet.getText().toString();
                if (TextUtil.isEmpty(content)){
                    ToastUtil.showInfo(getContext(), "请输入详细退款理由", Toast.LENGTH_SHORT);
                    return;
                }
                if (content.length()<3){
                    ToastUtil.showInfo(getContext(), "请输入详细退款理由", Toast.LENGTH_SHORT);
                    return;
                }
                sqtuik(content,posion);

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

    private void getGankInto(int page) {
        String url = Url.newgetMymarketOrder;
        RequestParams params = new RequestParams();
//        params.put("userId", UserNative.getId());//企业id
        params.put("userId", UserNative.getId());//企业id
        params.put("pageNumber", page);//企业id
        params.put("orderStatus", "2,3,5,6");//待收货
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        pageCount = Integer.parseInt(object.getString("pageCount"));
                        JSONArray dataArray = object.getJSONArray("data");
                        detailList.clear();//里层数据
                        allEntities.clear();//外层数据
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            String orderStatus = dataObject.getString("orderStatus");//订单状态 1待付款 2待收货 3 订单完成
                            String orderId = dataObject.getString("orderUuid");//订单号
                            businessId = dataObject.getString("businessId");//订单号
                            String orderPrice = dataObject.getString("orderPrice");//总价
                            String province = dataObject.getString("province");
                            String city = dataObject.getString("city");
                            String area = dataObject.getString("area");
                            String detail = dataObject.getString("detail");
                            String phone = dataObject.getString("phone");
                            expressCompany = dataObject.getString("expressCompany");
                            expressNumber = dataObject.getString("expressNumber");
                            updateNm = dataObject.getString("updateNm");
                            createNm = dataObject.getString("createNm");
                            consignee = dataObject.getString("consignee");
                            paymentName = dataObject.getString("paymentName");
                            String expressCost = dataObject.getString("expressCost");//运费
                            String createDt = dataObject.getString("createDt");//运费
                            String shopList = dataObject.getString("shopList");
                            if (!shopList.equals("null")) {
                                JSONArray shopListArray = dataObject.getJSONArray("shopList");
                                for (int c = 0; c < shopListArray.length(); c++) {
                                    JSONObject shopListObject = shopListArray.getJSONObject(c);
                                    commodityColor = shopListObject.getString("commodityColor");//颜色
                                    commodityImage1 = shopListObject.getString("commodityImage1");//图片
                                    commodityNumber = shopListObject.getInt("commodityNumber");//数量

                                    commodityColor = shopListObject.getString("commodityColor");//颜色
                                    commodityImage1 = Url.img_domain+shopListObject.getString("commodityImage1")+Url.imageStyle640x640;//图片
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
                                    detailEntity.setBusinessId(businessId);
                                    detailList.add(detailEntity);
                                }
                            }
                            FragmentMyOrderAllEntity entity = new FragmentMyOrderAllEntity();//外层数据
                            entity.setOrderId(orderId);//订单号
                            entity.setBusinessId(businessId);
                            entity.setNumber(commodityNumber);//共多少件
                            entity.setTotal(orderPrice);//总金额
                            entity.setCarriage(expressCost);//含运费
                            entity.setCreateDt(createDt);
                            entity.setPrice(commodityPrice);
                            entity.setCommoditySp(commoditySp);
                            entity.setCommodityColor(commodityColor);
                            entity.setCommodityNm(commodityNm);
                            entity.setCommodityImage1(commodityImage1);
                            entity.setExpressCompany(expressCompany);
                            entity.setExpressNumber(expressNumber);
                            entity.setOrderStatus(orderStatus);
                            entity.setProvince(province);
                            entity.setCity(city);
                            entity.setArea(area);
                            entity.setDetail(detail);
                            entity.setPhone(phone);
                            entity.setUpdateNm(updateNm);
                            entity.setCreateNm(createNm);
                            entity.setConsignee(consignee);
                            entity.setPaymentName(paymentName);
                            allEntities.add(entity);

                        }
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

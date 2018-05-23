package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.CompanyPresentCustomMadeAdapter;
import com.yuanxin.clan.core.entity.CompanyPresentCustomMadeEntity;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.view.GoodsDetailActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by lenovo1 on 2017/2/24.
 */
//礼品定制 PresentCustomMadeEntity item_present_custom_made PresentCustomMadeAdapter
public class PresentCustomMadeActivity extends BaseActivity {


    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_present_custom_made_area)
    TextView activityPresentCustomMadeArea;
    @BindView(R.id.activity_present_custom_made_type)
    TextView activityPresentCustomMadeType;
    @BindView(R.id.activity_present_custom_made_recycler_view)
    RecyclerView activityPresentCustomMadeRecyclerView;
    @BindView(R.id.address_search_springview)
    SpringView addressSearchSpringview;


    private List<CompanyPresentCustomMadeEntity> companyPresentCustomMadeEntityList = new ArrayList<>();
    private CompanyPresentCustomMadeAdapter adapter;
    private String area, commodityId;
    private int currentPage = 1;// 当前页面，从0开始计数

    @Override
    public int getViewLayout() {
        return R.layout.activity_present_custom_made;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();
        currentPage=1;
        getGankInto(currentPage);
    }

    private void initRecyclerView() {
        adapter = new CompanyPresentCustomMadeAdapter(PresentCustomMadeActivity.this, companyPresentCustomMadeEntityList);
        adapter.setOnItemClickListener(new CompanyPresentCustomMadeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(PresentCustomMadeActivity.this, GoodsDetailActivity.class);
                intent.putExtra("commodityId", companyPresentCustomMadeEntityList.get(position).getCommodityId());
                intent.putExtra("type", GoodsDetailActivity.GIFT);
                startActivity(intent);
                //礼品详情
            }
        });
        activityPresentCustomMadeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityPresentCustomMadeRecyclerView.setAdapter(adapter);
        activityPresentCustomMadeRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityPresentCustomMadeRecyclerView.setNestedScrollingEnabled(false);//禁止滑动

        addressSearchSpringview.setHeader(new RotationHeader(this));
        addressSearchSpringview.setFooter(new RotationFooter(this));
        addressSearchSpringview.setType(SpringView.Type.OVERLAP);
        addressSearchSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addressSearchSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                companyPresentCustomMadeEntityList.clear();
                currentPage = 1;
                getGankInto(currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addressSearchSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage += 1;
                getGankInto(currentPage);
            }
        });

    }

    private void getGankInto(int pageNumber) {//礼品定制列表
        String url = Url.getMyGiftList;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pageNumber);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq","lipingdingzhi-----"+s);
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        JSONArray array = object.getJSONArray("data");
//                        companyPresentCustomMadeEntityList.clear();
                        for (int c = 0; c < array.length(); c++) {
                            JSONObject dataObject = array.getJSONObject(c);
                            commodityId = dataObject.getString("commodityId");
                            String commodityNm = dataObject.getString("commodityNm");//商品
                            String commodityPrice = dataObject.getString("commodityPrice");
                            String commodityImage1 = dataObject.getString("commodityImage1");
                            String image = Url.img_domain + commodityImage1+Url.imageStyle640x640;
                            String addressOne = dataObject.getString("address");
                            if (!addressOne.equals("null")) {
                                JSONObject address = dataObject.getJSONObject("address");//南城区
                                area = address.getString("area");
                            }
                            String enterprise = dataObject.getString("enterprise");
                            String epNm = null;
                            if (!enterprise.equals("null")) {
                                JSONObject enterpriseObject = dataObject.getJSONObject("enterprise");
                                epNm = enterpriseObject.getString("epNm");//公司地址
                            }

                            CompanyPresentCustomMadeEntity entity = new CompanyPresentCustomMadeEntity();
                            entity.setCommodityId(commodityId);
                            entity.setImage(image);
                            entity.setName(commodityNm);
                            entity.setCompany(epNm);
                            entity.setArea(area);
                            entity.setPrice(commodityPrice);
                            companyPresentCustomMadeEntityList.add(entity);
                        }
                        adapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout, R.id.activity_present_custom_made_area, R.id.activity_present_custom_made_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_right_layout://发布
                startActivity(new Intent(PresentCustomMadeActivity.this, EditPresentCustomMadeActivity.class));
                break;
            case R.id.activity_present_custom_made_area:
                onAddressPicker();
                break;
            case R.id.activity_present_custom_made_type:
                onConstellationPicker();
                break;
        }
    }

    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    activityPresentCustomMadeArea.setText(city.getAreaName());
                } else {
                    activityPresentCustomMadeArea.setText(county.getAreaName());
                }
                currentPage = 1;
                getGankInto(currentPage);
            }
        });
        task.execute("广东", "东莞", "东城");
    }

    public void onConstellationPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        OptionPicker picker = new OptionPicker(this,
                isChinese ? new String[]{
                        "全部", "供应市场", "需求市场"
                } : new String[]{
                        "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
                        "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
                });
        picker.setCycleDisable(true);//不禁用循环
        picker.setTopBackgroundColor(0xFFEEEEEE);//头部背景
        picker.setTopHeight(50);
        picker.setTopLineColor(0xFF33B5E5);//中间分割线
        picker.setTopLineHeight(1);
        picker.setTitleText(isChinese ? "请选择" : "Please pick");
        picker.setTitleTextColor(0xFF999999);//中间字颜色
        picker.setTitleTextSize(13);
        picker.setCancelTextColor(0xFF33B5E5);//取消颜色
        picker.setCancelTextSize(14);
        picker.setSubmitTextColor(0xFF33B5E5);//确定颜色
        picker.setSubmitTextSize(14);
        picker.setTextColor(0xFF33B5E5, 0xFF999999);//选中，非选中
        WheelView.LineConfig config = new WheelView.LineConfig();
        config.setColor(0xFF33B5E5);//线颜色
        config.setAlpha(140);//线透明度
        config.setRatio((float) (1.0 / 8.0));//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(0xFFE1E1E1);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        picker.setSelectedIndex(7);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                activityPresentCustomMadeType.setText(item);
                currentPage = 1;
                getGankInto(currentPage);
            }
        });
        picker.show();
    }


}

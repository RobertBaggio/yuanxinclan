package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.indicator.MyServiceAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.MyService_inEntity;
import com.yuanxin.clan.core.entity.MyService_outEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:我的服务类
 */

public class MyserviceActivity extends BaseActivity {

    @BindView(R.id.activity_business_district_library_left_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.fragment_my_all_crowd_funding_recycler_view)
    RecyclerView fragmentMyAllCrowdFundingRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;

    private int currentPage = 1;// 当前页面，从0开始计数
    private int maxpage;
    private int ifoneid=-1;
    private MyServiceAdapter adapter;
    private List<MyService_inEntity> mMyService_inEntities = new ArrayList<>();
    private List<MyService_outEntity> mMyService_outEntities = new ArrayList<>();

    @Override
    public int getViewLayout() {
        return R.layout.myservicelayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();
    }



    private void initRecyclerView() {
        adapter = new MyServiceAdapter(MyserviceActivity.this,mMyService_inEntities,mMyService_outEntities);
        adapter.setOnItemClickListener(new MyServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent companyIntr = new Intent(MyserviceActivity.this, MyServiceDetail.class);
                Intent companyIntr = new Intent(MyserviceActivity.this, MyserviceDetailWebActivity.class);
                companyIntr.putExtra("orderUuid", mMyService_inEntities.get(position).getOrderUuid());
                companyIntr.putExtra("shopListId", mMyService_inEntities.get(position).getShopListId());
                companyIntr.putExtra("commodityId", mMyService_inEntities.get(position).getCommodityId());
                companyIntr.putExtra("zt", mMyService_inEntities.get(position).getProcedureId());
                companyIntr.putExtra("intype", "fw");
                companyIntr.putExtra("title", "服务详情");
                startActivity(companyIntr);
            }
        });
        fragmentMyAllCrowdFundingRecyclerView.setLayoutManager(new LinearLayoutManager(MyserviceActivity.this));
        fragmentMyAllCrowdFundingRecyclerView.setAdapter(adapter);
        fragmentMyAllCrowdFundingRecyclerView.setFocusable(false);//导航栏切换不再focuse
        fragmentMyAllCrowdFundingRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getMyBusinessDistrict(1);
        fragmnetMyCollectArticleSpringview.setHeader(new RotationHeader(MyserviceActivity.this));
        fragmnetMyCollectArticleSpringview.setFooter(new RotationFooter(MyserviceActivity.this));
        fragmnetMyCollectArticleSpringview.setType(SpringView.Type.OVERLAP);
        fragmnetMyCollectArticleSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetMyCollectArticleSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
//                businessDistrictListEntities.clear();
                currentPage = 1;
                mMyService_inEntities.clear();
                mMyService_outEntities.clear();
                getMyBusinessDistrict(currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetMyCollectArticleSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                if (currentPage==maxpage){
                    ToastUtil.showInfo(MyserviceActivity.this, "已加载完！", Toast.LENGTH_SHORT);
                    return;
                }
                currentPage += 1;
                getMyBusinessDistrict(currentPage);
            }
        });

//        getMyBusinessDistrict(1);

    }

    private void getMyBusinessDistrict(int pageNumber) {
        String url = Url.servicePage;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        params.put("pageNumber", pageNumber);//用户id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(MyserviceActivity.this, "网络连接异常", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    maxpage = object.getInt("pageCount");
                    if (TextUtil.isEmpty(object.getString("data"))){
                        return;
                    }
                    if (object.getString("success").equals("true")) {
                        JSONArray dataArray = object.getJSONArray("data");
                        int numid=0;
                        mMyService_inEntities.clear();
                        mMyService_outEntities.clear();//里层数据
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            String orderUuid = dataObject.getString("orderUuid");
                            String orderStatus = dataObject.getString("orderStatus");
                            String createDt = dataObject.getString("createDt");

                            String shopList = dataObject.getString("shopList");
                            ifoneid++;
                            if (!shopList.equals("null")||!TextUtil.isEmpty(shopList)) {
                                JSONArray shopCarArray = dataObject.getJSONArray("shopList");
                                for (int c = 0; c < shopCarArray.length(); c++) {
                                    JSONObject shopListObject = shopCarArray.getJSONObject(c);
                                    numid++;
                                    String commodityImage1 = shopListObject.getString("commodityImage1");
                                    String image = Url.img_domain+commodityImage1+Url.imageStyle640x640;
                                    String commodityNm = shopListObject.getString("commodityNm");
                                    String procedureId = shopListObject.getString("procedureId");
                                    String procedureName = shopListObject.getString("procedureName");
                                    String updateDt = shopListObject.getString("updateDt");
                                    String shopListId = shopListObject.getString("shopListId");
                                    String commodityId = shopListObject.getString("commodityId");

                                    MyService_inEntity inEntity = new MyService_inEntity();
                                    inEntity.setCommodityImage1(image);
                                    inEntity.setCommodityNm(commodityNm);
                                    inEntity.setProcedureId(procedureId);
                                    inEntity.setOrderUuid(orderUuid);
                                    inEntity.setShopListId(shopListId);
                                    inEntity.setProcedureName(procedureName);
                                    inEntity.setOneid(c);
                                    inEntity.setUpdateDt(updateDt);
                                    inEntity.setCommodityId(commodityId);
                                    inEntity.setMyid(ifoneid);

                                    mMyService_inEntities.add(inEntity);
                                }
                            }
                            MyService_outEntity outEntity = new MyService_outEntity();//外层数据
                            outEntity.setOneid(ifoneid);
                            outEntity.setCreateDt(createDt);
                            outEntity.setOrderUuid(orderUuid);
                            outEntity.setOrderStatus(orderStatus);
                            mMyService_outEntities.add(outEntity);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(MyserviceActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.e("数据解析出错");
                }
            }
        });
    }


    @OnClick({R.id.activity_business_district_library_left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_district_library_left_layout:
                finish();
                break;

        }
    }
}

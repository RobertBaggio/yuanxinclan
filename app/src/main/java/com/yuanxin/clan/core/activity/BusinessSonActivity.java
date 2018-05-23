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

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.BusinessDistrictLibraryAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.core.entity.BusinessDistrictListEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.bean.BusinesslistEntity;
import com.yuanxin.clan.core.market.view.MarketImageHolder;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/2 0002 16:58
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class BusinessSonActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_business_district_library_right_layout)
    LinearLayout activity_business_district_library_right_layout;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.activity_company_information_detail_middle_text)
    TextView titletest;
    @BindView(R.id.kongbai)
    LinearLayout kongbai;



    private BusinessDistrictLibraryAdapter adapter;
    private More_LoadDialog mMore_loadDialog;
    private List<BusinesslistEntity> adsTopList = new ArrayList<>();
    private String title ;
    private int pageCount;
    private int total = 0;
    private int pagenum=1;
    private List<BusinessDistrictListEntity> businessDistrictListEntities = new ArrayList<>();

    @Override
    public int getViewLayout() {
        return R.layout.busitradeslayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        title = getIntent().getStringExtra("title");
        titletest.setText(title);
        mMore_loadDialog = new More_LoadDialog(this);
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        initRecyclerView();
        getBusinessTop(1);
    }



    private void initRecyclerView() {
        adapter = new BusinessDistrictLibraryAdapter(BusinessSonActivity.this, businessDistrictListEntities);
        adapter.setOnItemClickListener(new BusinessDistrictLibraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (title.equals("圈子")){
                    Intent intent = new Intent(getApplicationContext(), QuanziDetailwbActivity.class);//圈子详情
                    intent.putExtra("businessAreaId", businessDistrictListEntities.get(position).getBusinessAreaId());
                    intent.putExtra("intype", "qz");
                    intent.putExtra("title", businessDistrictListEntities.get(position).getBusinessAreaNm());
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), BusinessDetailWebActivity.class);//商圈详情
                intent.putExtra("businessAreaId", businessDistrictListEntities.get(position).getBusinessAreaId());
                intent.putExtra("epStyleType", businessDistrictListEntities.get(position).getEpAccessPath());
                intent.putExtra("accessPath", businessDistrictListEntities.get(position).getAccessPath());
                intent.putExtra("gid", businessDistrictListEntities.get(position).getGroupId());
                intent.putExtra("name", businessDistrictListEntities.get(position).getBusinessAreaNm());
                intent.putExtra("enshrine", businessDistrictListEntities.get(position).getEnshrine());
                Log.v("lgq","epStyleType====="+businessDistrictListEntities.get(position).getEpAccessPath());
                startActivity(intent);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(adapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse

    }

    private void getBusinessTop(int page) {
        String url = Url.businessSon;
        RequestParams params = new RequestParams();
        int a=0;
        if (title.equals("商会")){
            a=1;
        }else if (title.equals("协会")){
            a=2;
        } else if (title.equals("圈子")){
            a=3;
        } else if (title.equals("园区")){
            a=4;
        }
        mMore_loadDialog.show();
        params.put("businessAreaGenre", a);//用户id
        params.put("userId", UserNative.getId());//用户id
        params.put("pageNumber", page);//用户id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                mMore_loadDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                mMore_loadDialog.dismiss();
                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        pageCount = object.getInt("pageCount");
                        total = object.getInt("total");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        if (total == 0){
                            kongbai.setVisibility(View.VISIBLE);
                            p2rv.setVisibility(View.GONE);
                            return;
                        }
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            String businessAreaId = businessObject.getString("businessAreaId");//商圈id
                            String enshrine = businessObject.getString("enshrine");//商圈id
                            String businessAreaNm = businessObject.getString("businessAreaNm");//商圈名称
                            String businessAreaDetail = businessObject.getString("businessAreaDetail");//商圈名称
                            String businessAreaType = businessObject.getString("businessAreaType");//商圈名称
                            int collectionCount = businessObject.getInt("collectionCount");
                            int memberShip = businessObject.getInt("memberShip");
                            String groudid = businessObject.getString("groupId");
//                            String accessPath = businessObject.getString("accessPath");
                            JSONObject epView = businessObject.optJSONObject("epView");
                            String epAccessPath;//商圈名称
                            if (epView == null) {
                                epAccessPath = null;
                            } else {
                                epAccessPath = epView.getString("epAccessPath");
                            }
                            String baImage1 = businessObject.getString("baImage1");//商圈名称
                            String image = Url.img_domain + baImage1+Url.imageStyle640x640;//图片
                            String address1 = businessObject.getString("address");
                            String city="";
                            String areaTen="";
                            String industryNm="";
                            if (!address1.equals("null")) {
                                JSONObject addressObject = businessObject.getJSONObject("address");
                                areaTen = addressObject.getString("area");//地区
                                city = addressObject.getString("city");
                            }
                            String ind = businessObject.getString("industry");
                            if (!ind.equals("null")){
                                JSONObject industry = businessObject.getJSONObject("industry");
                                industryNm=industry.getString("industryNm");
                            }

                            BusinessDistrictListEntity entity = new BusinessDistrictListEntity();
                            entity.setImage(image);
                            entity.setArea(areaTen);
                            entity.setBusinessAreaType(businessAreaType);
                            entity.setBusinessAreaNm(businessAreaNm);
                            entity.setBusinessAreaId(businessAreaId);
                            entity.setEpAccessPath(epAccessPath);
                            entity.setGroupId(groudid);
                            entity.setCity(city);
                            entity.setIndustryNm(industryNm);
                            entity.setCollectionCount(collectionCount);
                            entity.setMemberShip(memberShip);
                            entity.setEnshrine(enshrine);
                            entity.setBusinessAreaDetail(businessAreaDetail);
//                            entity.setAccessPath(accessPath);
                            businessDistrictListEntities.add(entity);
                        }
                        adapter.notifyDataSetChanged();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        }, 500);
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityYuanXinFairNewRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(88)*businessDistrictListEntities.size();// 控件的高强制设成20

                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    private void initConvenientBanner(ConvenientBanner cb, final List<AdvertisementsEntity> list) {
        CBViewHolderCreator cv = new CBViewHolderCreator<MarketImageHolder>() {
            public MarketImageHolder createHolder() {
                return new MarketImageHolder();
            }
        };
        cb.setPages(
                cv, list)
                .setPageIndicator(new int[]{R.drawable.banner_iocn_pre, R.drawable.banner_iocn_nomal})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }


    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.activity_business_district_library_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
            case R.id.activity_business_district_library_right_layout:
                Intent intent = new Intent(BusinessSonActivity.this, BusinessSosOneActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pagenum++;
        if (pagenum>pageCount){
            ToastUtil.showInfo(getApplicationContext(), "已加载完！", Toast.LENGTH_SHORT);
            p2rv.onFooterRefreshComplete(1);

            return;
        }
        getBusinessTop(pagenum);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        businessDistrictListEntities.clear();
        getBusinessTop(1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int sc = MyShareUtil.getSharedInt("sc");
        Log.v("lgq","huilail ====="+sc);
        if (sc==1) {
            businessDistrictListEntities.clear();
            getBusinessTop(1);
        }

    }
}

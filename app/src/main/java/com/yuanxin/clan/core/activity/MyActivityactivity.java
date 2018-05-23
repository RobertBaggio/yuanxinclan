package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.MyactivityAdapter;
import com.yuanxin.clan.core.market.bean.Myactivityentity;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
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
 * Describe:
 * Author: xjc
 * Date: 2017/9/21 0021 9:49
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MyActivityactivity extends BaseActivity {
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_right_layout)
    LinearLayout activityYuanXinFairNewRightLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;
    //    @BindView(R.id.address_search_springview)
//    SpringView addressSearchSpringview;
    private SubscriberOnNextListener getMarketListOnNextListener;
    private List<Myactivityentity>  mNewMarketitems = new ArrayList<>();
    private MyactivityAdapter mAdapter;
    private int currentPage ;// 当前页面，从0开始计数
    private int totalPage = 0;
    private boolean  mIsRefreshing = false;
//    private My_LoadingDialog mMy_loadingDialog;
    private int maxpage;

    @Override
    public int getViewLayout() {
        return R.layout.myactivitylayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
//        setStatusBar(this.getResources().getColor(R.color.my_info_head_bg));
//        mMy_loadingDialog = new My_LoadingDialog(MyActivityactivity.this);
        initRecyclerView();
        getMyBusinessDistrict(1);
    }


    private void initRecyclerView() {
        mAdapter = new MyactivityAdapter(MyActivityactivity.this, mNewMarketitems);
        mAdapter.setOnItemClickListener(new MyactivityAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position) {
                Intent intent = new Intent(getApplicationContext(), MyActivityDetailActivity.class);
                intent.putExtra("te", mNewMarketitems.get(position).getUname());
                intent.putExtra("file", mNewMarketitems.get(position).getFile());
                startActivity(intent);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(mAdapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse
        HttpMethods.getInstance().getMarketList(new ProgressSubscriber(getMarketListOnNextListener, MyActivityactivity.this), 1);
        fragmnetMyCollectArticleSpringview.setHeader(new RotationHeader(MyActivityactivity.this));
        fragmnetMyCollectArticleSpringview.setFooter(new RotationFooter(MyActivityactivity.this));
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
                mNewMarketitems.clear();
                currentPage = 1;
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
                currentPage += 1;
                if (currentPage==maxpage){
                    Toast.makeText(MyActivityactivity.this, "已加载完！", Toast.LENGTH_SHORT).show();
                    return;
                }
                getMyBusinessDistrict(currentPage);
            }
        });

    }
    private void getMyBusinessDistrict(int pageNumber) {
        String url = Url.getmyactivity;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        params.put("pageNumber", pageNumber);//用户id
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(MyActivityactivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    mMy_loadingDialog.dismiss();
                    JSONObject object = new JSONObject(s);
                    maxpage = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject businessObject = jsonArray.getJSONObject(a);
                            String tname = businessObject.getString("name");//商圈id
                            String introduce = businessObject.getString("introduce");//图片
                            String startTime = businessObject.getString("startTime");//图片
                            String endTime = businessObject.getString("endTime");//图片
                            String phone = businessObject.getString("phone");//图片
                            String file = businessObject.getString("file");//图片
                            int businessId = businessObject.getInt("businessId");
                            int businessActivityId = businessObject.getInt("businessActivityId");

                            Myactivityentity entity = new Myactivityentity();
                            entity.setPhone(phone);
                            entity.setTnmae(tname);
                            entity.setUname(introduce);
                            entity.setStarttime(startTime);
                            entity.setEndtime(endTime);
                            entity.setFile(file);
                            entity.setBusinessActivityId(businessActivityId);
                            entity.setBusinessId(businessId);
                            mNewMarketitems.add(entity);
                        }
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(MyActivityactivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.e("数据解析出错");
                }
            }
        });
    }


    @OnClick(R.id.activity_yuan_xin_fair_new_left_layout)
    public void onClick() {
        finish();
    }
}

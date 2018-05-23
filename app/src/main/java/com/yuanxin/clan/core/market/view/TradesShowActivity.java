package com.yuanxin.clan.core.market.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.MyTradesStatusAdapter;
import com.yuanxin.clan.core.market.bean.TradesEntity;
import com.yuanxin.clan.core.news.view.BusinessTradesActivity;
import com.yuanxin.clan.core.news.view.NearAndYearActivity;
import com.yuanxin.clan.core.news.view.TradesListActivity;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * 展会
 */

public class TradesShowActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.zhanhuili)
    LinearLayout zhanhuili;
    @BindView(R.id.zglistli)
    LinearLayout zglistli;
    @BindView(R.id.neardli)
    LinearLayout neardli;
    @BindView(R.id.nowyearli)
    LinearLayout nowyearli;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;


    private int currentPage ;// 当前页面，从0开始计数
    private int totalPage = 1;
    private My_LoadingDialog mMy_loadingDialog;
    private List<TradesEntity> mEntities = new ArrayList<>();
    private MyTradesStatusAdapter mMyTradesStatusAdapter;


    @Override
    public int getViewLayout() {
        return R.layout.tradeshowlayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        mMy_loadingDialog = My_LoadingDialog.getInstance(getApplicationContext());
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        initRecyclerView();
        getBusinesslist(1);
    }


    private void initRecyclerView() {
        mMyTradesStatusAdapter = new MyTradesStatusAdapter(TradesShowActivity.this, mEntities);
        mMyTradesStatusAdapter.setOnItemClickListener(new MyTradesStatusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 未登陆要求登陆
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Intent intent = new Intent(TradesShowActivity.this, TradesDetailActivity.class);
                intent.putExtra("id", String.valueOf(mEntities.get(position).getExhibitionId()));
                intent.putExtra("name",mEntities.get(position).getHallNm());
                intent.putExtra("image",mEntities.get(position).getExhibitionTitleImg());
                String time = DateDistance.getDistanceTimeToZW(mEntities.get(position).getStarTime())+" 至 "+DateDistance.getDistanceTimeToZW(mEntities.get(position).getEndTime());
                intent.putExtra("time","会展时间："+time);
                startActivity(intent);
            }
        });
        mMyTradesStatusAdapter.setOnItemPhClickListener(new MyTradesStatusAdapter.OnItemPhClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+mEntities.get(position).getBusinessPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(mMyTradesStatusAdapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse
    }

    private void getBusinesslist(int id) {
        String url = Url.newmarketlist;
        RequestParams params = new RequestParams();
        params.put("pageNumber",id);
//        params.put("industryId",0);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    currentPage = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
//                        org.json.JSONObject jsonObject = object.getJSONObject("data");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            TradesEntity advertisementsEntity = new TradesEntity();
                            String exhibitionTitle = businessObject.getString("exhibitionTitle");
                            String hallNm = businessObject.getString("hallNm");
                            String status = businessObject.getString("status");
                            String businessPhone = businessObject.getString("businessPhone");
                            String exhibitionTitleImg = businessObject.getString("exhibitionTitleImg");
                            String starTime = businessObject.getString("starTime");
                            String endTime = businessObject.getString("endTime");
                            int exhibitionId=businessObject.getInt("exhibitionId");
                            String string = Url.img_domain  + exhibitionTitleImg + Url.imageStyle750x350;
                            advertisementsEntity.setExhibitionTitleImg(string);
                            advertisementsEntity.setExhibitionTitle(exhibitionTitle);
                            advertisementsEntity.setEndTime(endTime);
                            advertisementsEntity.setStarTime(starTime);
                            advertisementsEntity.setHallNm(hallNm);
                            advertisementsEntity.setExhibitionId(exhibitionId);
                            advertisementsEntity.setStatus(status);
                            advertisementsEntity.setBusinessPhone(businessPhone);
                            mEntities.add(advertisementsEntity);
                        }
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityYuanXinFairNewRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(175)*mEntities.size();// 控件的高强制设成20
                        mMyTradesStatusAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.zglistli,R.id.neardli,R.id.nowyearli,R.id.zhanhuili})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
            case R.id.zhanhuili:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(TradesShowActivity.this, BusinessTradesActivity.class));
                break;
            case R.id.zglistli:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(TradesShowActivity.this, TradesListActivity.class));
                break;
            case R.id.neardli:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Intent intent = new Intent(TradesShowActivity.this, NearAndYearActivity.class);//有个聊天的标志
                intent.putExtra("type","n");
                startActivity(intent);
                break;
            case R.id.nowyearli:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Intent intent2 = new Intent(TradesShowActivity.this, NearAndYearActivity.class);//有个聊天的标志
                intent2.putExtra("type","y");
                startActivity(intent2);
                break;
        }
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        totalPage++;
        if (totalPage>currentPage){
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showInfo(getApplicationContext(), "已加载完！", Toast.LENGTH_SHORT);
            return;
        }
        getBusinesslist(totalPage);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        totalPage=1;
        mEntities.clear();
        getBusinesslist(totalPage);
    }
}

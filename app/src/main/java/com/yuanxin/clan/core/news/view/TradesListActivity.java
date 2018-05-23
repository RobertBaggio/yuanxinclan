package com.yuanxin.clan.core.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.TradeslistAdapter;
import com.yuanxin.clan.core.market.bean.TradesZGEntity;
import com.yuanxin.clan.core.util.Logger;
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
 * 展馆列表
 */

public class TradesListActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
//public class TradesListActivity extends BaseActivity {
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;

    private List<TradesZGEntity> adsTopList = new ArrayList<>();
    private TradeslistAdapter mTradeslistAdapter;

    @Override
    public int getViewLayout() {
        return R.layout.alllistlayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        initRecyclerView();
        getBusinessTop();
    }



    private void initRecyclerView() {
        mTradeslistAdapter = new TradeslistAdapter(TradesListActivity.this, adsTopList);
        mTradeslistAdapter.setOnItemClickListener(new TradeslistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (adsTopList.get(position).getNumbers()==0){
                    ToastUtil.showInfo(getApplicationContext(), "暂无展会展出", Toast.LENGTH_SHORT);
                    return;
                }
                Intent intent2 = new Intent(TradesListActivity.this, MiniTradesShowActivity.class);//有个聊天的标志
                intent2.putExtra("id",String.valueOf(adsTopList.get(position).getHallId()));
                intent2.putExtra("title",adsTopList.get(position).getHallNm());
                startActivity(intent2);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(mTradeslistAdapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse
    }

    private void getBusinessTop() {
        String url = Url.gethall;
        RequestParams params = new RequestParams();

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
//                    Log.v("lgq","geshu====="+object);
                    if (object.getString("success").equals("true")) {
//                        org.json.JSONObject jsonObject = object.getJSONObject("data");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            org.json.JSONArray tjsonArray = businessObject.getJSONArray("hallNmList");
                            Log.v("lgq","geshu====="+tjsonArray.length());
                            for (int c = 0;c<tjsonArray.length();c++){
                                JSONObject jsonObject = tjsonArray.getJSONObject(c);
                                String hallNm = jsonObject.getString("hallNm");
                                String hallDes = jsonObject.getString("hallDes");
                                int exhibitionCount = jsonObject.getInt("exhibitionCount");
                                String lon = jsonObject.getString("lon");
                                String lat = jsonObject.getString("lat");
                                String province = jsonObject.getString("province");
                                int hallId = jsonObject.getInt("hallId");

                                TradesZGEntity tradesZGEntity = new TradesZGEntity();
                                tradesZGEntity.setHallNm(hallNm);
                                tradesZGEntity.setHallDes("待开始的展会"+exhibitionCount+"场");
                                tradesZGEntity.setHallId(hallId);
                                tradesZGEntity.setLat(lat);
                                tradesZGEntity.setLon(lon);
                                tradesZGEntity.setProvince(province);
                                tradesZGEntity.setOneid(c);
                                tradesZGEntity.setNumbers(exhibitionCount);
                                adsTopList.add(tradesZGEntity);



                            }
                        }
                        mTradeslistAdapter.notifyDataSetChanged();
                        Logger.e(activityYuanXinFairNewRecyclerView.getLayoutParams().getClass().toString());
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityYuanXinFairNewRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(77)*adsTopList.size()+jsonArray.length()*UIUtils.dip2px(36);
//                        activityCompanyInformationDetailRecyclerView.setLayoutParams(linearParams);

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



    @OnClick(R.id.activity_yuan_xin_fair_new_left_layout)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
        }
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
            p2rv.onFooterRefreshComplete(1);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        adsTopList.clear();
        getBusinessTop();
    }
}

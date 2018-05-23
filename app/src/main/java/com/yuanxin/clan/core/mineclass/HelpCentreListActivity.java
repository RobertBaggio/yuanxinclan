package com.yuanxin.clan.core.mineclass;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.mineclass.adapter.HelpListAdapter;
import com.yuanxin.clan.core.mineclass.entity.QuestionEntity;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

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
 * Date: 2017/11/13 0013 16:36
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class HelpCentreListActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{

    @BindView(R.id.title_text)
    TextView title_text;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;

    private String title;

    private List<QuestionEntity> mEntities = new ArrayList<>();
    private HelpListAdapter mTradeslistAdapter;
    private int pageCount ;// 当前页面，从0开始计数
    private int ab =1;

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
        title = getIntent().getStringExtra("title");
        title_text.setText(title);
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        p2rv.setEnablePullLoadMoreDataStatus(false);
        p2rv.setEnablePullTorefresh(false);
        initRecyclerView();
        wenti(ab);
    }



    private void initRecyclerView() {
        mTradeslistAdapter = new HelpListAdapter(HelpCentreListActivity.this, mEntities);
        mTradeslistAdapter.setOnItemClickListener(new HelpListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent2 = new Intent(HelpCentreListActivity.this, MyhelpcentreDetail.class);//有个聊天的标志
                intent2.putExtra("id",String.valueOf(mEntities.get(position).getQuestionId()));
                intent2.putExtra("title",mEntities.get(position).getQuestionTitle());
                startActivity(intent2);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(mTradeslistAdapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse
    }

    public void wenti(int pageNumber) {//结束会话
        String url = Url.getquestion;
        RequestParams params = new RequestParams();
        int typeab =1;
        if (title.equals("常见问题")){
            typeab = 2;
        }else if (title.equals("使用指南")){
            typeab = 1;
        }
//        params.put("pageNumber",pageNumber);
        params.put("type",typeab);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                ToastUtil.showWarning(HelpCentreListActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (p2rv!=null){
                        p2rv.setRefreshComplete();
                    }
//                    pageCount = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
                        String ph = object.getString("data");
                        JSONArray array = new JSONArray(ph);
                        for (int d = 0; d < array.length(); d++) {
                            JSONObject dataObject = array.getJSONObject(d);
                            String questionId=dataObject.getString("questionId");
                            String questionTitle=dataObject.getString("questionTitle");
                            String questionContent=dataObject.getString("questionContent");


                            QuestionEntity mnewEntity =new QuestionEntity();
                            mnewEntity.setQuestionId(questionId);
                            mnewEntity.setQuestionTitle(questionTitle);
                            mnewEntity.setQuestionContent(questionContent);

                            mEntities.add(mnewEntity);

                        }
                        mTradeslistAdapter.notifyDataSetChanged();
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityYuanXinFairNewRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(44)*mEntities.size();// 控件的高强制设成20

                    } else {
                        ToastUtil.showInfo(HelpCentreListActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
        ab++;
        if (ab==pageCount||ab>pageCount){
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showInfo(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT);
            return;
        }
        wenti(ab);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mEntities.clear();
        ab=1;
        wenti(ab);
    }
}

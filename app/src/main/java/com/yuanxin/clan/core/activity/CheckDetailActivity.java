package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.CheckdetailAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.CheckdetailEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/1 0001 9:28
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class CheckDetailActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout backacli;
    private String type;
    private TextView topte;
    private EditText nameet,accountet;
    private Button mButton;
    private List<CheckdetailEntity> companyPresentCustomMadeEntityList = new ArrayList<>();
        private CheckdetailAdapter adapter;
    @BindView(R.id.activity_present_custom_made_recycler_view)
    RecyclerView activityPresentCustomMadeRecyclerView;
    @BindView(R.id.kongli)
    LinearLayout kongli;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数
    private int mScrollThreshold,lastposion;
    private More_LoadDialog mMore_loadDialog;

    @Override
    public int getViewLayout() {
        return R.layout.checkdetailla;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();
        backacli =(LinearLayout)findViewById(R.id.backacli);
        mMore_loadDialog = new More_LoadDialog(this);
        backacli.setOnClickListener(this);
        getcheckdetail();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backacli:
                onBackPressed();
                break;


        }}
    private void initRecyclerView() {
        adapter = new CheckdetailAdapter(CheckDetailActivity.this, companyPresentCustomMadeEntityList);
//        adapter.setOnItemClickListener(new CheckdetailAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(PresentCustomMadeActivity.this, GoodsDetailActivity.class);
//                intent.putExtra("commodityId", companyPresentCustomMadeEntityList.get(position).getCommodityId());
//                intent.putExtra("type", GoodsDetailActivity.GIFT);
//                startActivity(intent);
//                //礼品详情
//            }
//        });
        activityPresentCustomMadeRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityPresentCustomMadeRecyclerView.setAdapter(adapter);
        activityPresentCustomMadeRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityPresentCustomMadeRecyclerView.setNestedScrollingEnabled(false);//禁止滑动

        activityPresentCustomMadeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                lastposion = manager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = manager.getItemCount();

//                Log.i("lgq","last = =="+lastposion+"....."+totalItemCount);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition

                    if (lastposion == (totalItemCount - 1) && isSlidingToLast) {
                        currentPage++;
                        if (pageCount<currentPage){
                            Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        getcheckdetail();

                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //大于0表示，正在向右滚动；小于等于0 表示停止或向左滚动
                isSlidingToLast = dy > 0;
            }
        });


    }

    private void getcheckdetail(){
        String url = Url.checkdetail;
        RequestParams params = new RequestParams();
        params.put("pageNumber", currentPage);
        params.put("userId", UserNative.getId());
//        params.put("userId", 412);
        mMore_loadDialog.show();
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);
                    pageCount = object.getInt("pageCount");
                    if (pageCount==0){
                        kongli.setVisibility(View.VISIBLE);
                    }
                    if (object.getString("success").equals("true")) {
                        JSONArray objectJSONArray = object.getJSONArray("data");
                        for (int j = 0;j<objectJSONArray.length();j++){
                            JSONObject dataObject = objectJSONArray.getJSONObject(j);
                            String billId = dataObject.getString("billId");
                            String billAmount = dataObject.getString("billAmount");
                            String body = dataObject.getString("body");
                            String billTypeNm = dataObject.getString("billTypeNm");
                            String createDt = dataObject.getString("createDt");

                            CheckdetailEntity checkdetailEntity = new CheckdetailEntity();
                            checkdetailEntity.setBillAmount(billAmount);
                            checkdetailEntity.setBillId(billId);
                            checkdetailEntity.setBody(body);
                            checkdetailEntity.setBillTypeNm(billTypeNm);
                            checkdetailEntity.setCreateDt(createDt);


                            companyPresentCustomMadeEntityList.add(checkdetailEntity);
                        }

                        adapter.notifyDataSetChanged();
                    } else {
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMore_loadDialog.dismiss();
                ToastUtil.showWarning(CheckDetailActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
        });
    }

}

package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.CrowdFundingDetailActivity;
import com.yuanxin.clan.core.adapter.CrowdFundingAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.CrowdFundingAllEntity;
import com.yuanxin.clan.core.entity.CrowdFundingEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
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
 * Created by lenovo1 on 2017/2/22.
 */
//我参与的众筹
public class FragmentMyTakePartCrowdFunding extends BaseFragment {
    @BindView(R.id.fragment_my_take_part_crowd_funding_recycler_view)
    RecyclerView fragmentMyTakePartCrowdFundingRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;

    private List<CrowdFundingEntity> crowdFundingEntities = new ArrayList<>();//想要的数据
    private List<CrowdFundingAllEntity> crowdFundingAllEntities = new ArrayList<>();//所有数据
    private CrowdFundingAdapter crowdFundingAdapter;
    private int currentPage = 1;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_take_part_crowd_funding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        crowdFundingAdapter = new CrowdFundingAdapter(getContext(), crowdFundingAllEntities);
        crowdFundingAdapter.setOnItemClickListener(new CrowdFundingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CrowdFundingDetailActivity.class);
                intent.putExtra("crowdfundId", crowdFundingAllEntities.get(position).getCrowdfundId());
                startActivity(intent);
            }
        });
        fragmentMyTakePartCrowdFundingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        activityOneRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
//        platformContactRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
//
//        activityOneRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        fragmentMyTakePartCrowdFundingRecyclerView.setAdapter(crowdFundingAdapter);
        fragmentMyTakePartCrowdFundingRecyclerView.setFocusable(false);//导航栏切换不再focuse
        fragmentMyTakePartCrowdFundingRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getGankInto(1);
        fragmnetMyCollectArticleSpringview.setHeader(new RotationHeader(getContext()));
        fragmnetMyCollectArticleSpringview.setFooter(new RotationFooter(getContext()));
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
                crowdFundingAllEntities.clear();
                currentPage = 1;
                getGankInto(currentPage);
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
                getGankInto(currentPage);
            }
        });

//        crowdFundingAdapter = new CrowdFundingAdapter(getContext(), crowdFundingAllEntities);
//        crowdFundingAdapter.setOnItemClickListener(new CrowdFundingAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getContext(), CrowdFundingDetailActivity.class);
//                intent.putExtra("crowdfundId", crowdFundingAllEntities.get(position).getCrowdfundId());
//                startActivity(intent);
//            }
//        });
//        fragmentMyAllCrowdFundingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
////        activityOneRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
////        platformContactRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
//
////        fragmentMyAllCrowdFundingRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
//        fragmentMyAllCrowdFundingRecyclerView.setAdapter(crowdFundingAdapter);
//        fragmentMyAllCrowdFundingRecyclerView.setFocusable(false);//导航栏切换不再focuse
//        String epNm="";
//        int pageNumber=1;
////        HttpMethods.getInstance().getCrowdList(new ProgressSubscriber(getCrowdListOnNextListener, YuanXinCrowdFundingActivity.this),epNm,pageNumber);
//        getGankInto();
    }

    private void getGankInto(int pageNumber) {
        final String url = Url.getMCrowdfund;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        params.put("pageNumber", pageNumber);
        params.put("type", 2);//1:所有众筹 2:我发起的 3：我参与的
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
                        JSONArray jsonArray = object.getJSONArray("data");
//                        crowdFundingAllEntities.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String schedule = dataObject.getString("schedule");//进度
                            String participations = dataObject.getString("participations");//参与人数
                            String surplusDay = dataObject.getString("surplusDay");//还剩多少天
                            String crowdfundImage1 = dataObject.getString("crowdfundImage1");
                            String crowdfundImageOne = Url.img_domain + crowdfundImage1+Url.imageStyle640x640;//图片
                            int crowdfundId = dataObject.getInt("crowdfundId");//众筹id
                            String crowdfundNm = dataObject.getString("crowdfundNm");//众筹名称
                            String crowdfundSum = dataObject.getString("crowdfundSum");//已认筹金额
                            String crowdfundAll = dataObject.getString("crowdfundAll");//众筹总金额
                            String crowdfundImage = dataObject.getString("crowdfundImage");//标题图片
                            String crowdfundImageTwo = Url.urlHost + crowdfundImage;
                            CrowdFundingAllEntity entity = new CrowdFundingAllEntity();
                            entity.setSchedule(schedule);//进度
                            entity.setParticipations(participations);
                            entity.setSurplusDay(surplusDay);
                            entity.setCrowdfundNm(crowdfundNm);
                            entity.setCrowdfundImage1(crowdfundImageOne);
                            entity.setCrowdfundSum(crowdfundSum);
                            entity.setCrowdfundAll(crowdfundAll);
                            entity.setCrowdfundId(crowdfundId);
                            crowdFundingAllEntities.add(entity);
                        }
                        crowdFundingAdapter.notifyDataSetChanged();
                    } else {

                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }


                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

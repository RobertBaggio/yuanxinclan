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
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
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
 * Created by lenovo1 on 2017/2/24.
 */
//我的众筹
public class FragmentMyCollectCrowdFunding extends BaseFragment {

    @BindView(R.id.activity_yuan_xin_crowd_recycler_view)
    RecyclerView activityYuanXinCrowdRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;
    private List<CrowdFundingEntity> crowdFundingEntities = new ArrayList<>();//想要的数据
    private List<CrowdFundingAllEntity> crowdFundingAllEntities = new ArrayList<>();//所有数据
    private CrowdFundingAdapter crowdFundingAdapter;
    private SubscriberOnNextListener getCrowdListOnNextListener;
    private String crowdfundSum, crowdfundAll;
    private int currentPage = 1;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_collect_funding;
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
        activityYuanXinCrowdRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        activityOneRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
//        platformContactRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

//        activityYuanXinCrowdRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        activityYuanXinCrowdRecyclerView.setAdapter(crowdFundingAdapter);
        activityYuanXinCrowdRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityYuanXinCrowdRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getWebInfo(1);
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
                getWebInfo(currentPage);
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
                getWebInfo(currentPage);
            }
        });

    }

    private void getWebInfo(int pageNumber) {//还差图片
        String url = Url.getMyCollect;
        RequestParams params = new RequestParams();
        params.put("keyId", 0);//收藏项目ID
        params.put("userId", UserNative.getId());//用户Id
        params.put("pageNumber", pageNumber);//当前显示第几页
        params.put("type", 4);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
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
                        crowdFundingAllEntities.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            JSONObject crowdfundObject = dataObject.getJSONObject("crowdfund");
//                            schedule = dataObject.getString("schedule");//进度
//                        if (schedule.equals("null")) {
//                            schedule = "0";
//                        }
//                        String participations = dataObject.getString("participations");//参与人数
//                        String surplusDay = dataObject.getString("surplusDay");//还剩多少天
                            String crowdfundImage1 = crowdfundObject.getString("crowdfundImage1");
                            String crowdfundImageOne = Url.img_domain + crowdfundImage1+Url.imageStyle640x640;//图片
                            int crowdfundId = crowdfundObject.getInt("crowdfundId");//众筹id
                            String crowdfundNm = crowdfundObject.getString("crowdfundNm");//众筹名称
                            crowdfundSum = crowdfundObject.getString("crowdfundSum");//已认筹金额
                            if (crowdfundSum.equals("null")) {
                                crowdfundSum = "0";
                            }
                            crowdfundAll = crowdfundObject.getString("crowdfundAll");//众筹总金额
                            if (crowdfundAll.equals("null")) {
                                crowdfundAll = "0";
                            }
//                        String crowdfundImage = dataObject.getString("crowdfundImage");//标题图片
//                        String crowdfundImageTwo = Url.urlHost + crowdfundImage;
                            CrowdFundingAllEntity entity = new CrowdFundingAllEntity();
//                        entity.setSchedule(schedule);//进度
//                        entity.setParticipations(participations);
//                        entity.setSurplusDay(surplusDay);
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

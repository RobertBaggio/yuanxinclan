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
import com.yuanxin.clan.core.adapter.CompanyPresentCustomMadeAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.CompanyPresentCustomMadeEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.view.GoodsDetailActivity;
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
//商品

public class FragmentMyCollectCommodity extends BaseFragment {

    @BindView(R.id.activity_present_custom_made_recycler_view)
    RecyclerView activityPresentCustomMadeRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;
    private List<CompanyPresentCustomMadeEntity> companyPresentCustomMadeEntityList = new ArrayList<>();
    private CompanyPresentCustomMadeAdapter adapter;
    private int currentPage = 1;// 当前页面，从0开始计数
    private String epNm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_collect_commodity;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new CompanyPresentCustomMadeAdapter(getContext(), companyPresentCustomMadeEntityList);
        adapter.setOnItemClickListener(new CompanyPresentCustomMadeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //礼品详情
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                intent.putExtra("commodityId", companyPresentCustomMadeEntityList.get(position).getCommodityId());
                intent.putExtra("type", GoodsDetailActivity.GIFT);
                startActivity(intent);
            }
        });
        activityPresentCustomMadeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activityPresentCustomMadeRecyclerView.setAdapter(adapter);
        activityPresentCustomMadeRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityPresentCustomMadeRecyclerView.setNestedScrollingEnabled(false);//禁止滑动

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
                companyPresentCustomMadeEntityList.clear();
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
        params.put("userId", UserNative.getId());//用户Id
        params.put("pageNumber", pageNumber);//当前显示第几页
        params.put("type", 5);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
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
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            JSONObject commodity = dataObject.getJSONObject("commodity");
                            String commodityImage1 = commodity.getString("commodityImage1");
                            String imgOne = Url.img_domain + commodityImage1+Url.imageStyle640x640;//图片

                            //commodityNm
                            String commodityNm = commodity.getString("commodityNm");

                            epNm = commodity.getString("epNm");

                            String commodityId = commodity.getString("commodityId");


                            String commodityPrice = commodity.getString("commodityPrice");

//                            if(!enterprise.equals("null")){
//                                JSONObject enterpriseObect=dataObject.getJSONObject("enterprise");
//                                epNm=enterpriseObect.getString("epNm");
//
//                            }

                            CompanyPresentCustomMadeEntity one = new CompanyPresentCustomMadeEntity();
                            one.setImage(imgOne);//图片
                            one.setName(commodityNm);//标题

                            one.setCompany(epNm);//新闻类型
//                            one.setArea(createDt);//创建时间
                            one.setPrice(commodityPrice);//内容
                            one.setCommodityId(commodityId);//新闻id
                            companyPresentCustomMadeEntityList.add(one);

                        }

                        adapter.notifyDataSetChanged();
//                        yuanXinFairNewGoodsAdapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }

//    public void onDestroy() {
//        super.onDestroy();
//        unbinder.unbind();
//
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}

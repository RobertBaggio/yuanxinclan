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
import com.yuanxin.clan.core.activity.ThinkTankDetailActivity;
import com.yuanxin.clan.core.adapter.ThinkTankAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.ThinkTankEntity;
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
 * Created by lenovo1 on 2017/4/23.
 */
public class FragmentMyCollectThinkTank extends BaseFragment {
    @BindView(R.id.fragment_my_collect_article_recyclerview)
    RecyclerView fragmentMyCollectArticleRecyclerview;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;
    private List<ThinkTankEntity> thinkTankEntities = new ArrayList<>();
    private ThinkTankAdapter thinkTankAdapter;
    private int currentPage = 1;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_collect_think_tank;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        thinkTankAdapter = new ThinkTankAdapter(getContext(), thinkTankEntities);
        fragmentMyCollectArticleRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        thinkTankAdapter.setOnItemClickListener(new ThinkTankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ThinkTankDetailActivity.class);
                intent.putExtra("expertId", thinkTankEntities.get(position).getExpertId());
//                Toast.makeText(getApplication(),thinkTankEntities.get(position).getExpertId(),Toast.LENGTH_SHORT).show();
//                intent.putExtra("extra_data",data);
                startActivity(intent);
//                startActivity(new Intent(ThinkTankActiivty.this, ThinkTankDetailActivity.class));
//                Intent intent = new Intent(getApplicationContext(), BaseWebActivity.class);
//                intent.putExtra("url", gankEntities.get(position).getUrl());
//                startActivity(intent);
            }
        });

//        activityOneRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
//        platformContactRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

//        fragmentMyCollectArticleRecyclerview.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        fragmentMyCollectArticleRecyclerview.setAdapter(thinkTankAdapter);
        fragmentMyCollectArticleRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentMyCollectArticleRecyclerview.setNestedScrollingEnabled(false);//禁止滑动

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
                thinkTankEntities.clear();
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
        params.put("type", 9);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
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
                        thinkTankEntities.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            JSONObject expertInfo = dataObject.getJSONObject("expertInfo");
                            String image = expertInfo.getString("image1");
                            String imageOne = Url.img_domain + image+Url.imageStyle640x640;
                            String expertNm = expertInfo.getString("expertNm");
                            int expertId = expertInfo.getInt("expertId");

                            String company = expertInfo.getString("company");

                            String title = expertInfo.getString("title");

                            String position = expertInfo.getString("position");

                            ThinkTankEntity entity = new ThinkTankEntity();
                            entity.setImage1(imageOne);
                            entity.setExpertNm(expertNm);
                            entity.setCompany(company);
                            entity.setTitle(title);
                            entity.setPosition(position);
                            entity.setExpertId(expertId);
                            thinkTankEntities.add(entity);


                        }

                        thinkTankAdapter.notifyDataSetChanged();
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

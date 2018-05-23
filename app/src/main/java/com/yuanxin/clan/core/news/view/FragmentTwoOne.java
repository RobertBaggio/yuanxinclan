package com.yuanxin.clan.core.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.adapter.NewsAdapter;
import com.yuanxin.clan.core.news.bean.NewEntity;
import com.yuanxin.clan.core.news.bean.NewsBanner;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseLazyFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lenovo1 on 2017/1/23.
 * 资讯fragment
 */
public class FragmentTwoOne extends BaseLazyFragment {

    @BindView(R.id.fragment_two_one_recyclerview)
    RecyclerView fragmentTwoOneRecyclerview;
    @BindView(R.id.fragmnet_two_one_springview)
    SpringView fragmnetTwoOneSpringview;

    private List<NewEntity> newEntityOnes = new ArrayList<>();
    private NewsAdapter mNewsAdapter;
    private int currentPage = 1;// 当前页面，从0开始计数
    public Unbinder unbinder;
    private String newsTypeIds;
    private My_LoadingDialog mMy_loadingDialog;
    private Boolean hasBanner = false;
    private int maxMultiStyle = 20;


    public static FragmentTwoOne newInstance(String newsTypeIds) {
        FragmentTwoOne fragmentTwoOne = new FragmentTwoOne();
        Bundle bundle = new Bundle();
        bundle.putString("newsTypeIds", newsTypeIds);
        fragmentTwoOne.setArguments(bundle);
        return fragmentTwoOne;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMy_loadingDialog = My_LoadingDialog.getInstance(getContext());
        View view = inflater.inflate(R.layout.fragment_two_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsTypeIds = getArguments().getString("newsTypeIds");
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();
        initView();
        currentPage = 1;
        getWebInfo(currentPage);
    }

    private void getWebInfo(final int pageNumber) {
        String url = Url.getMyNewsList;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pageNumber);
        // 是否是关注
        if ("0".equals(newsTypeIds)) {
//            if (newEntityOnes.size()<1)
            mMy_loadingDialog.show();
            url = Url.getRecommendNews;
            params.put("userId", UserNative.getId());
            doHttpGet(url, params, new RequestCallback() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    mMy_loadingDialog.dismiss();
                    ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        JSONObject object = new JSONObject(s);
//                    Log.v("lgq","ssssssssssssssFragmentTwoOne"+s);
                        if (object.getString("success").equals("true")) {
                            if (currentPage == 1) {
                                newEntityOnes.clear();
                            }
                            // 资讯轮播
                            if (object.has("img")) {
                                List<NewsBanner> banner = FastJsonUtils.getObjectsList(object.getJSONArray("img").toString(), NewsBanner.class);
                                for (int j = 0; j < banner.size(); j++) {
                                    String imgUrl = banner.get(j).getNewsCarouselImg();
                                    banner.get(j).setNewsCarouselImg(Url.img_domain + imgUrl);
                                }
                                if (banner.size() > 0) {
                                    NewEntity bannerNews = new NewEntity();
                                    bannerNews.setBanner(banner);
                                    bannerNews.setItemType(NewEntity.style_0);
                                    newEntityOnes.add(bannerNews);
                                    hasBanner = true;
                                }
                            }

                            // 列表资讯
                            JSONArray epnews = object.getJSONArray("data").getJSONObject(0).getJSONArray("epNews");
                            JSONArray bzAreaNews = object.getJSONArray("data").getJSONObject(0).getJSONArray("bzAreaNews");
                            if (epnews.length() == 0 && bzAreaNews.length() == 0) {
                                newsTypeIds = "8";
                                getWebInfo(pageNumber);
                            } else {
                                int j = 0;
                                for (; j < epnews.length(); j++){
                                    // 企业动态2 商圈动态3
                                    newEntityOnes.add(recommendEpNew2New(epnews.getJSONObject(j), 2));
                                }
                                for (j = 0; j < bzAreaNews.length(); j++) {
                                    newEntityOnes.add(recommendBzAreaNew2New(bzAreaNews.getJSONObject(j), 3));
                                }
                            }
                            // 横向的新闻
                            for (int g = 0; g < newEntityOnes.size(); g++) {
                                if (hasBanner) {
                                    if (g != 0 && g % 4 == 0) {
                                        newEntityOnes.get(g).setItemType(NewEntity.style_2);
                                    }
                                } else {
                                    if ((g + 1) % 4 == 0) {
                                        newEntityOnes.get(g).setItemType(NewEntity.style_2);
                                    }
                                }
                            }
                            mNewsAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                        Logger.e("数据解析出错");
                    } finally {
                        if (mMy_loadingDialog != null) {
                            mMy_loadingDialog.dismiss();
                        }
                    }
                }
            });
        } else {
            if ("-1".equals(newsTypeIds)) {
                params.put("showHomepage",1);
            } else {
                params.put("newsTypeIds", newsTypeIds);
            }
//            if (newEntityOnes.size()<1)
            mMy_loadingDialog.show();
            doHttpGet(url, params, new RequestCallback() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    mMy_loadingDialog.dismiss();
                    ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        JSONObject object = new JSONObject(s);
//                    Log.v("lgq","ssssssssssssssFragmentTwoOne"+s);
                        if (object.getString("success").equals("true")) {
                            if (currentPage == 1) {
                                newEntityOnes.clear();
                            }
                            // 资讯轮播
                            if (object.has("img")) {
                                List<NewsBanner> banner = FastJsonUtils.getObjectsList(object.getJSONArray("img").toString(), NewsBanner.class);
                                for (int j = 0; j < banner.size(); j++) {
                                    String imgUrl = banner.get(j).getNewsCarouselImg();
                                    banner.get(j).setNewsCarouselImg(Url.img_domain + imgUrl);
                                }
                                if (banner.size() > 0) {
                                    NewEntity bannerNews = new NewEntity();
                                    bannerNews.setBanner(banner);
                                    bannerNews.setItemType(NewEntity.style_0);
                                    newEntityOnes.add(bannerNews);
                                    hasBanner = true;
                                }
                            }
                            // 列表资讯
                            JSONArray dataArray = object.getJSONArray("data");
                            for (int d = 0; d < dataArray.length(); d++) {
                                JSONObject dataObject = dataArray.getJSONObject(d);
                                int newsId = dataObject.getInt("newsId");
                                int epId = dataObject.getInt("epId");
                                String title=dataObject.getString("title");
                                String img=dataObject.getString("img");
                                String createDt=dataObject.getString("createDt");
                                String newsTypeNm=dataObject.getString("newsTypeNm");
                                String epNm=dataObject.getString("epNm");//amp;
                                String epShortName = dataObject.getString("epShortNm");
                                int browse = dataObject.getInt("browse");
                                int randomBrowse = dataObject.getInt("randomBrowse");

                                NewEntity mnewEntity =new NewEntity(NewEntity.style_1);
                                mnewEntity.setEpNm(epNm);
                                mnewEntity.setNewsId(newsId);
                                mnewEntity.setImg(img);
                                mnewEntity.setTitle(title);
                                mnewEntity.setNewsTypeNm(newsTypeNm);
                                mnewEntity.setCreateDt(createDt);
                                mnewEntity.setBrowse(browse);
                                mnewEntity.setRandomBrowse(randomBrowse);
                                mnewEntity.setEpShortNm(epShortName);
//                                Log.v("lgq", "id====" + img);

                                newEntityOnes.add(mnewEntity);
                            }
//                            newEntityOnes.addAll(parseArray(object.getString("data"), NewEntity.class));
                            // 横向的新闻
                            for (int g = 0; g < newEntityOnes.size(); g++) {
                                if (hasBanner) {
                                    if (g != 0 && g % 4 == 0) {
                                        newEntityOnes.get(g).setItemType(NewEntity.style_2);
                                    }
                                } else {
                                    if ((g + 1) % 4 == 0) {
                                        newEntityOnes.get(g).setItemType(NewEntity.style_2);
                                    }
                                }

                            }

                            mNewsAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                        Logger.e("数据解析出错");
                    } finally {
                        if (mMy_loadingDialog != null) {
                            mMy_loadingDialog.dismiss();
                        }
                    }
                }
            });
        }

    }

    private NewEntity recommendEpNew2New(JSONObject jo, int type) throws JSONException{
        NewEntity ne = new NewEntity(NewEntity.style_1);
        try {
            ne.setNewsId(jo.getInt("epNewsId"));
            String reg="(?i).+?\\.(jpg|gif|jpeg|png|bmp)";
            String imageUrl = jo.getString("titleImage");
            if (!imageUrl.toLowerCase().matches(reg)) {
                String logo = jo.getJSONObject("enterprise").getString("epLogo");
                imageUrl = logo;
            }
            ne.setImg(imageUrl.startsWith("http")? imageUrl : (Url.img_domain + imageUrl+Url.imageStyle640x640));
            ne.setTitle(jo.getString("title"));
            ne.setEpShortNm(jo.getJSONObject("enterprise").getString("epShortNm"));
            ne.setEpNm(jo.getJSONObject("enterprise").getString("epNm"));
            ne.setBrowse(jo.getInt("browse"));
            ne.setRandomBrowse(jo.getInt("randomBrowse"));
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            ne.setCreateDt(df.format(jo.getLong("createDt")));
            ne.setCreateDt(jo.getString("createDt"));
        }catch (Exception e) {
            e.printStackTrace();
        }
        ne.setNewsBasicTypeId(type);
//            ne.setCollect(re.get);
        return ne;
    }

    private NewEntity recommendBzAreaNew2New(JSONObject jo, int type) throws JSONException{
        NewEntity ne = new NewEntity(NewEntity.style_1);
        try {
            ne.setNewsId(jo.getInt("bzAreaNewsId"));
            String reg="(?i).+?\\.(jpg|gif|jpeg|png|bmp)";
            String imageUrl = jo.getString("titleImage");
            if (!imageUrl.toLowerCase().matches(reg)) {
                String logo = jo.getString("epLogo");
                imageUrl = logo;
            }
            ne.setImg(imageUrl.startsWith("http")? imageUrl : (Url.img_domain + imageUrl+Url.imageStyle640x640));
            ne.setTitle(jo.getString("bzAreaTitle"));
            ne.setEpShortNm(jo.getString("epShortNm"));
            ne.setEpNm(jo.getString("epNm"));
            ne.setBrowse(jo.getInt("browse"));
            ne.setRandomBrowse(jo.getInt("randomBrowse"));
    //        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
//            ne.setCreateDt(df.format(jo.getLong("createDt")));
            ne.setCreateDt(jo.getString("createDt"));
        }catch (Exception e) {
            Logger.e(e.toString());
        }
        ne.setNewsBasicTypeId(type);
//            ne.setCollect(re.get);
        return ne;
    }

    private void initView() {
        mNewsAdapter = new NewsAdapter(newEntityOnes);
        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailWebActivity.class);
                intent.putExtra("news", newEntityOnes.get(position));
                startActivity(intent);
            }
        });
        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentTwoOneRecyclerview.setAdapter(mNewsAdapter);
        fragmentTwoOneRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentTwoOneRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
        fragmnetTwoOneSpringview.setHeader(new RotationHeader(getContext()));
        fragmnetTwoOneSpringview.setFooter(new RotationFooter(getContext()));
        fragmnetTwoOneSpringview.setType(SpringView.Type.OVERLAP);
        fragmnetTwoOneSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetTwoOneSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage = 1;
                Log.v("lgq","j.t...."+currentPage);
                getWebInfo(currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetTwoOneSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                Log.v("lgq","j...b.."+currentPage);
                currentPage += 1;
                getWebInfo(currentPage);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

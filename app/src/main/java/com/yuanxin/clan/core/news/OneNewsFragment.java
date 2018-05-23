package com.yuanxin.clan.core.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.adapter.MyNewsFragmentAdapter;
import com.yuanxin.clan.core.news.bean.NewsType;
import com.yuanxin.clan.core.news.view.InformationChooseActivity;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.InitInterface;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.zxing.SubBasicFragment;
import com.yuanxin.clan.mvp.manager.PreferenceManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/11 0011 9:46
 */

public class OneNewsFragment extends SubBasicFragment implements InitInterface {

    @BindView(R.id.fragment_a_tablayout)
    TabLayout fragmentATablayout;
    @BindView(R.id.activity_two_image)
    LinearLayout activityTwoImage;
    @BindView(R.id.fragment_a_viewPager)
    ViewPager fragmentAViewPager;
    private MyNewsFragmentAdapter adapter;
    private List<NewsType> typeTitle = new ArrayList<>();
    private LinearLayout left_layout;
    private AsyncHttpClient client = new AsyncHttpClient();


//    @Override
//    public int getViewLayout() {
//        return R.layout.fragment_news;
//    }
//
//    @Override
//    protected void initView(Bundle savedInstanceState) {
//        initInfo();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_news, container, false);
        initViews();
        return contentView;
    }

    @Override
    public void initViews() {
        initInfo();
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void releaseRes() {

    }

    private void initInfo() {
//        getTypeTitle();
        getNewsTypeByUser();
        fragmentATablayout = (TabLayout) contentView.findViewById(R.id.fragment_a_tablayout);
        fragmentAViewPager =(ViewPager)contentView.findViewById(R.id.fragment_a_viewPager);
        activityTwoImage =(LinearLayout) contentView.findViewById(R.id.activity_two_image);
        left_layout =(LinearLayout) contentView.findViewById(R.id.left_layout);

        activityTwoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Intent intent = new Intent(getActivity(), InformationChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("typesByUser", FastJsonUtils.toJSONString(typeTitle.subList(2, typeTitle.size())));
                intent.putExtras(bundle);
                startActivityForResult(intent, 2011);
            }
        });
        left_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        fragmentATablayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可以滚动
        adapter = new MyNewsFragmentAdapter(getActivity().getSupportFragmentManager(), typeTitle);
        fragmentAViewPager.setAdapter(adapter);
        fragmentATablayout.setupWithViewPager(fragmentAViewPager);
    }

    private void getTypeTitle() {
        String type = PreferenceManager.getString("article_type", "1-推荐_2-关注");
        typeTitle.clear();
        if(!TextUtil.isEmpty(type)) {
            List<String> typeTemp = new ArrayList<String>(Arrays.asList(type.split("_")));
            for (int i = 0; i < typeTemp.size(); i++) {
                String[] typeSplit = typeTemp.get(i).split("-");
                NewsType item = new NewsType();
                item.setNewsTypeId(typeSplit[0]);
                item.setNewsTypeNm(typeSplit[1]);
                typeTitle.add(item);
            }
        }
        NewsType defaultItemRecommend = new NewsType();
        defaultItemRecommend.setNewsTypeId("-1");
        defaultItemRecommend.setNewsTypeNm("推荐");
        typeTitle.add(0, defaultItemRecommend);
        NewsType defaultItemAttention = new NewsType();
        defaultItemAttention.setNewsTypeId("0");
        defaultItemAttention.setNewsTypeNm("关注");
        typeTitle.add(1, defaultItemAttention);
    }

    private void getNewsTypeByUser() {
        String url = Url.getNewsTypesByUser;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户名
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        typeTitle.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String newsTypeId = dataObject.getString("newsTypeId");
                            String newsTypeNm = dataObject.getString("newsTypeNm");
                            NewsType item = new NewsType();
                            item.setNewsTypeId(newsTypeId);
                            item.setNewsTypeNm(newsTypeNm);
                            typeTitle.add(item);
                        }
                        NewsType defaultItemRecommend = new NewsType();
                        defaultItemRecommend.setNewsTypeId("-1");
                        defaultItemRecommend.setNewsTypeNm("推荐");
                        typeTitle.add(0, defaultItemRecommend);
                        NewsType defaultItemAttention = new NewsType();
                        defaultItemAttention.setNewsTypeId("0");
                        defaultItemAttention.setNewsTypeNm("关注");
                        typeTitle.add(1, defaultItemAttention);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @OnClick({R.id.activity_two_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_two_image:
                if (!UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Intent intent = new Intent(getActivity(), InformationChooseActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("typesByUser", FastJsonUtils.toJSONString(typeTitle.subList(2, typeTitle.size())));
                intent.putExtras(bundle);
                startActivityForResult(intent, 2011);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2011 && resultCode == 2011) {
            initInfo();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        client.cancelAllRequests(true);
    }

}

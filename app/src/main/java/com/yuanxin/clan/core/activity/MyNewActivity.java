package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
import com.yuanxin.clan.core.news.view.NewsDetailWebActivity;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/9.
 */
//我的资讯
public class MyNewActivity extends BaseActivity {
    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.fragment_two_one_recyclerview)
    RecyclerView fragmentTwoOneRecyclerview;
    @BindView(R.id.fragmnet_two_one_springview)
    SpringView fragmnetTwoOneSpringview;
    private List<NewEntity> newEntityOnes = new ArrayList<>();
    private NewsAdapter mNewsAdapter;
    private int currentPage = 1;// 当前页面，从0开始计数

    @Override
    public int getViewLayout() {
        return R.layout.activity_my_new;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();
        currentPage = 1;
        getMyNewsInto(currentPage);
    }

    private void initRecyclerView() {
        mNewsAdapter = new NewsAdapter(newEntityOnes);
        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(MyNewActivity.this, NewsDetailWebActivity.class);
                intent.putExtra("news", newEntityOnes.get(position));
                startActivity(intent);
            }
        });
        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fragmentTwoOneRecyclerview.setAdapter(mNewsAdapter);
        fragmentTwoOneRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentTwoOneRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
        fragmnetTwoOneSpringview.setHeader(new RotationHeader(this));
        fragmnetTwoOneSpringview.setFooter(new RotationFooter(this));
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
                newEntityOnes.clear();
                currentPage = 1;
                getMyNewsInto(currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetTwoOneSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage += 1;
                getMyNewsInto(currentPage);
            }
        });

    }

    private void getMyNewsInto(int pageNumber) {
        String url = Url.myNewsList;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pageNumber);//用户id
        params.put("createId", UserNative.getId());//用户id
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    Log.v("Lgq","w d z   MyNewActivity===="+s);

                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        newEntityOnes.addAll(FastJsonUtils.getObjectsList(object.getString("data"), NewEntity.class));
                        mNewsAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @OnClick(R.id.activity_exchange_phone_left_layout)
    public void onClick() {
        finish();
    }
}

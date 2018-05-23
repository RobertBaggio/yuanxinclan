package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.FragmentTwoOneAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FragmentTwoOneEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.adapter.CollectNewsAdapter;
import com.yuanxin.clan.core.news.bean.NewEntity;
import com.yuanxin.clan.core.news.view.NewsDetailWebActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/2/24.
 * 我的收藏资讯
 */
public class FragmentMyCollectArticle extends BaseFragment {

    @BindView(R.id.fragment_my_collect_article_recyclerview)
    RecyclerView fragmentMyCollectArticleRecyclerview;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;

    private List<FragmentTwoOneEntity> fragmentTwoOneEntities = new ArrayList<>();
    private FragmentTwoOneAdapter fragmentTwoOneAdapter;
    private int currentPage = 1;// 当前页面，从0开始计数
    private List<NewEntity> newEntityOnes = new ArrayList<>();
    private CollectNewsAdapter mNewsAdapter;
    private int pageall;

    //编辑还是不编辑
    public void changeStatus() {
        // TODO: 2017/6/9 0009 这里有问题.........适配器可能无效的...........
        if (fragmentTwoOneAdapter != null) {
            fragmentTwoOneAdapter.changeStatus();
        }
    }

    public void allCheck() {
        fragmentTwoOneAdapter.allCheck();
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_collect_article;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
        currentPage = 1;
        getWebInfo(currentPage);
    }

    private void getWebInfo(int pageNumber) {//还差图片
        String url = Url.getMyCollect;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户Id
        params.put("pageNumber", pageNumber);//当前显示第几页
        params.put("type", 1);////1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
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
                        pageall = object.getInt("pageCount");
                        newEntityOnes.addAll(JSON.parseArray(object.getString("data"), NewEntity.class));
                        mNewsAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    private void initRecyclerView() {
        mNewsAdapter = new CollectNewsAdapter(newEntityOnes);
        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailWebActivity.class);
                intent.putExtra("newsid",newEntityOnes.get(position).getNews().getNewsId());
                intent.putExtra("mc", 1);
                startActivity(intent);
            }
        });
        fragmentMyCollectArticleRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMyCollectArticleRecyclerview.setAdapter(mNewsAdapter);
        fragmentMyCollectArticleRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentMyCollectArticleRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
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
                newEntityOnes.clear();
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
                if (pageall<currentPage){
                    ToastUtil.showInfo(getContext(), "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getWebInfo(currentPage);
            }
        });
    }
}


package com.yuanxin.clan.core.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.FragmentNewConversationListAdapter;
import com.yuanxin.clan.core.entity.FragmentNewConversationListEntity;
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
 * Created by lenovo1 on 2017/5/10.
 */
//会话
public class FragmentNewConversationList extends BaseFragment {
    @BindView(R.id.fragment_two_one_recyclerview)
    RecyclerView fragmentTwoOneRecyclerview;
    @BindView(R.id.fragmnet_two_one_springview)
    SpringView fragmnetTwoOneSpringview;
    //    private List<NewEntityOne> newEntityOnes=new ArrayList<>();
    private List<FragmentNewConversationListEntity> fragmentNewConversationListEntity = new ArrayList<>();//自己要的

    private String title, newsTypeNm, createDt, content, imgOne;
    private int newsId, newsTypeId;
    //    private GankAdapter gankAdapter;
    private FragmentNewConversationListAdapter fragmentNewConversationListAdapter;//自己要的
    private int currentPage = 1;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_new_conversation_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initView();
    }

    private void getWebInfo(int pageNumber) {//还差图片
        String url = Url.getMyNewsList;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pageNumber);
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
                        ToastUtil.showSuccess(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        JSONArray jsonArray = object.getJSONArray("data");
//                        newEntityOnes.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            newsId = dataObject.getInt("newsId");//id
                            String newsIdOne = String.valueOf(newsId);
                            title = dataObject.getString("title");//标题
                            newsTypeId = dataObject.getInt("newsTypeId");//新闻类型id
                            String newsTypeIdOne = String.valueOf(newsTypeId);
                            newsTypeNm = dataObject.getString("newsTypeNm");//新闻类型
                            createDt = dataObject.getString("createDt");//创建时间
                            content = dataObject.getString("content");//新闻内容
                            String img = dataObject.getString("img");
                            imgOne = Url.img_domain + img+Url.imageStyle640x640;
                            if (newsTypeId == 1) {
                                FragmentNewConversationListEntity one = new FragmentNewConversationListEntity();
                                one.setImage(imgOne);//标题
//                                one.setNewsId(newsId);//新闻id
                                one.setName(newsTypeNm);//新闻类型名称
                                one.setTime(createDt);//创建时间
                                one.setMessage(newsTypeNm);//内容
//                                one.setNewsId(newsId);//新闻id
//                                one.setImages(imgOne);
                                fragmentNewConversationListEntity.add(one);
                            }

                        }

                        fragmentNewConversationListAdapter.notifyDataSetChanged();
                    }


                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }

    private void initView() {

        fragmentNewConversationListAdapter = new FragmentNewConversationListAdapter(getContext(), fragmentNewConversationListEntity);
//        fragmentNewConversationListAdapter.setOnItemClickListener(new GankAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
////                Intent intent = new Intent(getActivity(), NewsDetailWebActivity.class);
////                intent.putExtra("newsId", fragmentNewConversationListEntity.get(position).getNewsId());
//                startActivity(new Intent(getActivity(), NewsDetailWebActivity.class));
//            }
//        });
//        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
//        activityOneRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
//        platformContactRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
//
//        activityOneRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        fragmentTwoOneRecyclerview.setAdapter(fragmentNewConversationListAdapter);
        fragmentTwoOneRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentTwoOneRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
        getWebInfo(1);
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
                fragmentNewConversationListEntity.clear();
                currentPage = 1;
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

                currentPage += 1;
                getWebInfo(currentPage);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

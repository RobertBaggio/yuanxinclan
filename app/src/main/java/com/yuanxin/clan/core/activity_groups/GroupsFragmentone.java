package com.yuanxin.clan.core.activity_groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.bean.NewEntity;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseLazyFragment;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ProjectName: yuanxinclan_new
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/3/27 0027 11:12
 */

public class GroupsFragmentone extends BaseLazyFragment implements ActivitToFragmentITF {

    @BindView(R.id.groups_recyclerview)
    RecyclerView fragmentTwoOneRecyclerview;


    private List<NewEntity> newEntityOnes = new ArrayList<>();
    private int currentPage = 1;// 当前页面，从0开始计数
    public Unbinder unbinder;
    private String newsTypeIds;
    private My_LoadingDialog mMy_loadingDialog;
    private Boolean hasBanner = false;
    private int maxMultiStyle = 20;
    private int ab =1;
    private int pageCount ;
    private List<GroupsEntity> businessDistrictListEntities = new ArrayList<>();
    private GroupsMainAdapter adapter;
    private int lastposion;
    private boolean ifmy;
    private int statusid,timesid;
    private String mcity;

    public static GroupsFragmentone newInstance(String newsTypeIds) {
        GroupsFragmentone fragmentTwoOne = new GroupsFragmentone();
        Bundle bundle = new Bundle();
        bundle.putString("newsTypeIds", newsTypeIds);
        fragmentTwoOne.setArguments(bundle);
        return fragmentTwoOne;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mMy_loadingDialog = My_LoadingDialog.getInstance(getContext());
        View view = inflater.inflate(R.layout.fragment_groups_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsTypeIds = getArguments().getString("newsTypeIds");
        ifmy = false;
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();
        initView();
        currentPage = 1;
        getBusinessDistrictList();
    }

    @Override
    public void show(String name1,int status,int times) {
        mcity = name1;
        statusid = status;
        timesid = times;
        businessDistrictListEntities.clear();
        ifmy = true;
        getBusinessDistrictList();
    }


    private void getBusinessDistrictList() {

            String url = Url.activityGroup;
            RequestParams params = new RequestParams();
            params.put("pageNumber", ab);
            params.put("appFlg", 1);
            params.put("industryId", newsTypeIds);
            params.put("type", statusid+"");
            params.put("timeInterval", timesid+"");
            params.put("city", mcity);
        if (ifmy==false){
            mMy_loadingDialog.show();
        }
            doHttpGet(url, params, new RequestCallback() {
                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    mMy_loadingDialog.dismiss();
                    try {
                        org.json.JSONObject object = new org.json.JSONObject(s);
                        if (object.getString("success").equals("true")) {
                            pageCount = object.getInt("pageCount");
                            org.json.JSONArray jsonArray = object.getJSONArray("data");
                            if (jsonArray.length()==0){
                                ToastUtil.showWarning(getContext(), "没有相关数据", Toast.LENGTH_SHORT);
                            }
                            businessDistrictListEntities.clear();
                            businessDistrictListEntities.addAll(FastJsonUtils.getObjectsList(object.getString("data"), GroupsEntity.class));
                            adapter.notifyDataSetChanged();
                        } else {
                            ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } finally {
                        if (mMy_loadingDialog==null){
                            return;
                        }
                        mMy_loadingDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
                    if (mMy_loadingDialog==null){
                        return;
                    }
                    mMy_loadingDialog.dismiss();
                }
            });
        }

    private void initView() {
        adapter = new GroupsMainAdapter(getContext(), businessDistrictListEntities);
        adapter.setOnItemClickListener(new GroupsMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ActivityGroupsDetail.class);//商圈详情
                intent.putExtra("id", businessDistrictListEntities.get(position).getActivityId());
                intent.putExtra("gid", businessDistrictListEntities.get(position).getGroupId());
                intent.putExtra("gname", businessDistrictListEntities.get(position).getActivityTheme());
                intent.putExtra("gimage", businessDistrictListEntities.get(position).getActivityImage());
                startActivity(intent);
            }
        });
        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentTwoOneRecyclerview.setAdapter(adapter);
        fragmentTwoOneRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentTwoOneRecyclerview.setNestedScrollingEnabled(false);//禁止滑动

        fragmentTwoOneRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    lastposion = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastposion == (totalItemCount - 1) && isSlidingToLast) {
                        ab++;
                        if (ab>pageCount){
                            Toast.makeText(getContext(), "已到底~~", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        getBusinessDistrictList();
                    }
                    if (lastposion==2){
                        businessDistrictListEntities.clear();
                        ab = 1;
                        getBusinessDistrictList();
                        return;
                    }

                    // 判断是否滚动到底部，并且是向右滚动


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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

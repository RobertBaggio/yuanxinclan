package com.yuanxin.clan.core.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.FragmentBusinessDistrictMemberAdapter;
import com.yuanxin.clan.core.entity.BusinessDistrictMemberEntity;
import com.yuanxin.clan.core.recyclerview.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lenovo1 on 2017/2/23.
 */
//商圈详情 成员  item_business_district_member BusinessDistrictMemberEntity FragmentBusinessDistrictMemberAdapter
public class FragmentBusinessDistrictMember extends Fragment {
    @BindView(R.id.fragment_business_district_member_recycler_view)
    RecyclerView fragmentBusinessDistrictMemberRecyclerView;

    private List<BusinessDistrictMemberEntity> businessDistrictMemberEntities = new ArrayList<>();
    private FragmentBusinessDistrictMemberAdapter adapter;
    public Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_business_district_member, container, false);
//        initRecyclerView();
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    private void initRecyclerView() {
        adapter = new FragmentBusinessDistrictMemberAdapter(getContext(), businessDistrictMemberEntities);
        adapter.setOnItemClickListener(new FragmentBusinessDistrictMemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getContext(), BusinessDistrictActivity.class);//商圈详情
////                intent.putExtra("url", gankEntities.get(position).getUrl());
//                startActivity(intent);
            }
        });
        fragmentBusinessDistrictMemberRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//这里有报错
//        activityOneRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
//        platformContactRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

        fragmentBusinessDistrictMemberRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        fragmentBusinessDistrictMemberRecyclerView.setAdapter(adapter);
        fragmentBusinessDistrictMemberRecyclerView.setFocusable(false);//导航栏切换不再focuse
//        HttpMethods.getInstance()开始网络请求
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        unbinder.unbind();
    }
}

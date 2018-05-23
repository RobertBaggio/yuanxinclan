package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.FragmentBusinessDistrictMemberAdapter;
import com.yuanxin.clan.core.entity.BusinessDistrictMemberEntity;
import com.yuanxin.clan.core.recyclerview.DividerGridItemDecoration;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/27.
 */
public class AddDeleteMemberActivity extends BaseActivity {
    @BindView(R.id.window_head_left_image)
    ImageView windowHeadLeftImage;
    @BindView(R.id.window_head_left_text)
    TextView windowHeadLeftText;
    @BindView(R.id.window_head_left_layout)
    LinearLayout windowHeadLeftLayout;
    @BindView(R.id.window_head_name)
    TextView windowHeadName;
    @BindView(R.id.center_headname_ll)
    LinearLayout centerHeadnameLl;
    @BindView(R.id.window_head_right_layout)
    LinearLayout windowHeadRightLayout;
    @BindView(R.id.activity_add_delete_member_recycler_view)
    RecyclerView activityAddDeleteMemberRecyclerView;
    private List<BusinessDistrictMemberEntity> businessDistrictMemberEntities = new ArrayList<>();
    private FragmentBusinessDistrictMemberAdapter adapter;

    @Override
    public int getViewLayout() {
        return R.layout.activity_add_delete_member;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initView();
        initRecyclerView();
    }

    private void initView() {
        windowHeadLeftImage.setImageResource(R.drawable.biaoqian_dingdan1);
        windowHeadName.setText("成员管理");
    }

    private void initRecyclerView() {
        adapter = new FragmentBusinessDistrictMemberAdapter(getApplicationContext(), businessDistrictMemberEntities);
        adapter.setOnItemClickListener(new FragmentBusinessDistrictMemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        activityAddDeleteMemberRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
        activityAddDeleteMemberRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        activityAddDeleteMemberRecyclerView.setAdapter(adapter);
        activityAddDeleteMemberRecyclerView.setFocusable(false);//导航栏切换不再focuse
    }


    @OnClick({R.id.window_head_left_image,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.window_head_left_image:
                finish();
                break;

        }
    }
}

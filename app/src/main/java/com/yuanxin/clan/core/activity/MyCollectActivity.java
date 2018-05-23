package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.fragment.FragmentMyCollectArticle;
import com.yuanxin.clan.core.fragment.FragmentMyCollectAssociation;
import com.yuanxin.clan.core.fragment.FragmentMyCollectCommodity;
import com.yuanxin.clan.core.fragment.FragmentMyCollectCompany;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/24.
 * 我的收藏类
 */
public class MyCollectActivity extends BaseActivity {


    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.my_collect_activity_head)
    RelativeLayout myCollectActivityHead;
    @BindView(R.id.activity_my_collect_tabLayout)
    TabLayout activityMyCollectTabLayout;
    @BindView(R.id.activity_my_collect_viewPager)
    ViewPager activityMyCollectViewPager;
    @BindView(R.id.my_collect_checkbox)
    CheckBox myCollectCheckbox;
    @BindView(R.id.my_collect_num_text)
    TextView myCollectNumText;
    @BindView(R.id.my_collect_delete_btn)
    Button myCollectDeleteBtn;
    @BindView(R.id.activity_my_collect_bottom_layout)
    RelativeLayout activityMyCollectBottomLayout;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private MyFragmentAdapter adapter;
    private FragmentMyCollectArticle fragmentMyCollectArticle;//资讯
    private FragmentMyCollectAssociation fragmentMyCollectAssociation;//商圈
    private FragmentMyCollectCompany fragmentMyCollectCompany;//企业
//    private FragmentMyCollectCrowdFunding fragmentMyCollectCrowdFunding;//众筹
    private FragmentMyCollectCommodity fragmentMyCollectCommodity;//商品
//    private FragmentMyCollectThinkTank fragmentMyCollectThinkTank;//智囊团
//    FragmentMyCollectArticle fragment =(FragmentMyCollectArticle)getFragmentManager().findFragmentById(R.id.fragment_my_collect_article);


    @Override
    public int getViewLayout() {
        return R.layout.activity_my_collect;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initViewPager();
    }

    private void initViewPager() {
//        windowHeadLeftImage.setImageResource(R.drawable.biaoqian_dingdan1);
//        windowHeadLeftText.setVisibility(View.GONE);
//        windowHeadName.setText("我的收藏");
//        windowHeadRightTextview.setText("搜索");
        fragmentMyCollectArticle = new FragmentMyCollectArticle();//资讯 没问题
        fragmentMyCollectAssociation = new FragmentMyCollectAssociation();//商圈
        fragmentMyCollectCompany = new FragmentMyCollectCompany();//企业
//        fragmentMyCollectCrowdFunding = new FragmentMyCollectCrowdFunding();//众筹
        fragmentMyCollectCommodity = new FragmentMyCollectCommodity();//商品 没问题
//        fragmentMyCollectThinkTank = new FragmentMyCollectThinkTank();//智囊团

        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyCollectArticle);
        fragmentList.add(fragmentMyCollectAssociation);
        fragmentList.add(fragmentMyCollectCompany);
//        fragmentList.add(fragmentMyCollectCrowdFunding);
        fragmentList.add(fragmentMyCollectCommodity);
//        fragmentList.add(fragmentMyCollectThinkTank);//隐藏智囊团


        titleList = new ArrayList<>();
        titleList.add("资讯");
        titleList.add("商圈");
        titleList.add("企业");
//        titleList.add("众筹");
        titleList.add("商品");
//        titleList.add("智囊团");


//        activityMyCollectTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可以滚动
        activityMyCollectTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动

        activityMyCollectTabLayout.addTab(activityMyCollectTabLayout.newTab().setText(titleList.get(0)));
        activityMyCollectTabLayout.addTab(activityMyCollectTabLayout.newTab().setText(titleList.get(1)));
        activityMyCollectTabLayout.addTab(activityMyCollectTabLayout.newTab().setText(titleList.get(2)));
//        activityMyCollectTabLayout.addTab(activityMyCollectTabLayout.newTab().setText(titleList.get(3)));
//        activityMyCollectTabLayout.addTab(activityMyCollectTabLayout.newTab().setText(titleList.get(4)));
//        activityMyCollectTabLayout.addTab(activityMyCollectTabLayout.newTab().setText(titleList.get(5)));


        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyCollectViewPager.setAdapter(adapter);
        activityMyCollectViewPager.setOffscreenPageLimit(11);
        activityMyCollectTabLayout.setupWithViewPager(activityMyCollectViewPager);


    }


    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout, R.id.my_collect_checkbox, R.id.my_collect_num_text, R.id.my_collect_delete_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_right_layout://编辑
                if (activityMyCollectBottomLayout.getVisibility() == View.GONE) {
                    activityMyCollectBottomLayout.setVisibility(View.VISIBLE);
                    fragmentMyCollectArticle.changeStatus();//编辑还是不编辑
                } else {
                    activityMyCollectBottomLayout.setVisibility(View.GONE);
                    fragmentMyCollectArticle.changeStatus();//编辑还是不编辑
                }
                break;
            case R.id.my_collect_checkbox://全选
                fragmentMyCollectArticle.allCheck();
                break;
            case R.id.my_collect_num_text:
                break;
            case R.id.my_collect_delete_btn:
                break;
        }
    }

}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.HuiyiAdapter;
import com.yuanxin.clan.core.market.bean.HuiyiBean;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/15 0015 17:09
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class HuiyiZiliaoActivity extends BaseActivity {

    @BindView(R.id.rlLeft)
    LinearLayout rlLeft;
    @BindView(R.id.activity_shopping_cart_new_new_recycler_view)
    RecyclerView activity_shopping_cart_new_new_recycler_view;

    private List<HuiyiBean> mHuiyiBeen=new ArrayList<>();
    private HuiyiAdapter adapter;


    @Override
    public int getViewLayout() {
        return R.layout.huiyizliaolayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }


    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        adapter =new HuiyiAdapter(this,mHuiyiBeen);
        adapter.setOnItemClickListener(new HuiyiAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mHuiyiBeen.get(position).getUrl()));
                startActivity(webIntent);
//                getActivity().finish();
            }
        });
        activity_shopping_cart_new_new_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activity_shopping_cart_new_new_recycler_view.setAdapter(adapter);
        activity_shopping_cart_new_new_recycler_view.setNestedScrollingEnabled(false);//禁止滑动
        activity_shopping_cart_new_new_recycler_view.setFocusable(false);//导航栏切换不再focuse


        String nums = getIntent().getStringExtra("file");
        String [] stringArr= nums.split(",");
//        if ()
        for (int i=0;i<stringArr.length;i++){
            Log.v("Lgq","url==="+Url.img_domain+"/"+stringArr[i]);
            String one = stringArr[i];
            if (TextUtils.isEmpty(one)) {
                continue;
            }
            String [] stringArr2= one.split("\\.");
            String two = stringArr2[0];
            String type=stringArr2[1];
            String [] stringArr3= two.split("/");
            String name = stringArr3[stringArr3.length-1];
            String url = Url.img_domain+"/"+stringArr[i];

            HuiyiBean huiyiBean = new HuiyiBean();
            huiyiBean.setName(name);
            huiyiBean.setUrl(url);
            huiyiBean.setType(type);

            mHuiyiBeen.add(huiyiBean);
            adapter.notifyDataSetChanged();
        }
    }

    @OnClick({R.id.rlLeft})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                onBackPressed();
                break;

        }
    }
}

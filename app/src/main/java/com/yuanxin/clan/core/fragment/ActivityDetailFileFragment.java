package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.PhotoScanActivity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.ActivityDetailAdatper;
import com.yuanxin.clan.core.market.bean.HuiyiBean;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2017/12/26 0026 18:10
 */

public class ActivityDetailFileFragment extends BaseFragment{
    @BindView(R.id.activity_yuan_xin_fair_new_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;

    protected Bundle fragmentArgs;
    private List<HuiyiBean> mHuiyiBeen=new ArrayList<>();
    private ActivityDetailAdatper adapter;


    @Override
    public int getViewLayout() {
        return R.layout.activitydetailfile;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragmentArgs = getArguments();
        String filestring = MyShareUtil.getSharedString("file");

        Log.v("Lgq","url=== "+ filestring);
        String [] stringArr= filestring.split(",");
        for (int i=0;i<stringArr.length;i++){
            Log.v("Lgq","url==="+ Url.img_domain+"/"+stringArr[i]);
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
        }
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new ActivityDetailAdatper(getContext(), mHuiyiBeen);
        adapter.setOnItemClickListener(new ActivityDetailAdatper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                if (mHuiyiBeen.get(position).getUrl().contains("png")||mHuiyiBeen.get(position).getUrl().contains("jpg")||mHuiyiBeen.get(position).getUrl().contains("jpeg")){
                    Intent intent = new Intent();
                    intent.putExtra("image", mHuiyiBeen.get(position).getUrl());
                    intent.setClass(getContext(), PhotoScanActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mHuiyiBeen.get(position).getUrl()));
                startActivity(webIntent);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(adapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse

        adapter.notifyDataSetChanged();

    }
}

package com.yuanxin.clan.core.company.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.adapter.IndustryClassifyAdapter;
import com.yuanxin.clan.core.company.bean.IndustryClassifyEntity;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.recyclerview.DividerItemDecoration;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/20.
 */
public class IndustryClassifyActviity extends BaseActivity {
    @BindView(R.id.activity_industry_classify_head_image_layout)
    LinearLayout activityIndustryClassifyHeadImageLayout;
    @BindView(R.id.activity_industry_classify_right_layout)
    LinearLayout activityIndustryClassifyRightLayout;
    @BindView(R.id.activity_industry_classify_recycler_view)
    RecyclerView activityIndustryClassifyRecyclerView;

    private List<IndustryClassifyEntity> industryClassifyEntities = new ArrayList<>();
    private IndustryClassifyAdapter industryClassifyAdapter;
    private SubscriberOnNextListener getIndustryListOnNextListener;
    private String industryId, industryNm, industryNumber, province, city, area, epNm;
    private List<IndustryListEntity> entityList = new ArrayList<IndustryListEntity>();
    private int nameIdInt;


    @Override
    public int getViewLayout() {
        return R.layout.activity_industry_classify;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        initView();
    }

    private void initOnNext() {
        getIndustryListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    entityList.clear();
                    entityList.addAll(FastJsonUtils.getObjectsList(t.getData(), IndustryListEntity.class));
                    industryClassifyAdapter.notifyDataSetChanged();
                    for (int i = 0; i < entityList.size(); i++) {
                        industryId = String.valueOf(entityList.get(i).getIndustryId());//id String
                        industryNm = entityList.get(i).getIndustryNm();//名称
                        SharedPreferences sharedPreferences = getSharedPreferences("industryInfo", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString(industryNm, industryId);//名称 id String
                        editor.putString(industryId, industryNm);//id 名称
                        editor.commit();//提交修改
                    }
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
    }

    private void initView() {
        industryClassifyAdapter = new IndustryClassifyAdapter(IndustryClassifyActviity.this, entityList);
        industryClassifyAdapter.setOnItemClickListener(new IndustryClassifyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = entityList.get(position).getIndustryNm();//名称
                SharedPreferences share = getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
                String nameId = share.getString(name, "");//行业名称 对应 idString
                //增加
                try {
                    nameIdInt = Integer.parseInt(nameId);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent();
                intent.putExtra("industryName", name);
                intent.putExtra("industryId", nameIdInt);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        activityIndustryClassifyRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityIndustryClassifyRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        activityIndustryClassifyRecyclerView.setAdapter(industryClassifyAdapter);
        activityIndustryClassifyRecyclerView.setFocusable(false);//导航栏切换不再focuse
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, IndustryClassifyActviity.this));
    }


    @OnClick(R.id.activity_industry_classify_head_image_layout)
    public void onClick() {
        finish();
    }
}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.CompanySearchAdapter;
import com.yuanxin.clan.core.entity.CompanySearchEntity;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/17.
 */
public class SearchActivity extends BaseActivity {


    @BindView(R.id.activity_search_left_image)
    ImageView activitySearchLeftImage;
    @BindView(R.id.activity_search_edit)
    EditText activitySearchEdit;
    @BindView(R.id.activity_search_layout)
    LinearLayout activitySearchLayout;
    @BindView(R.id.activity_search_right_image)
    TextView activitySearchRightImage;
    @BindView(R.id.activity_search_clear_text)
    TextView activitySearchClearText;
    @BindView(R.id.activity_search_recycler_view)
    RecyclerView activitySearchRecyclerView;

    private List<CompanySearchEntity> companySearchEntities = new ArrayList<>();//企业名称列表
    private CompanySearchAdapter companySearchAdapter;

    @Override
    public int getViewLayout() {
        return R.layout.activity_search;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        companySearchAdapter = new CompanySearchAdapter(SearchActivity.this, companySearchEntities);
        companySearchAdapter.setOnItemClickListener(new CompanySearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = companySearchEntities.get(position).getName();
//                SharedPreferences share=getSharedPreferences("industryInfo",Activity.MODE_PRIVATE);
//                String nameId=share.getString(name,"");//行业名称 对应 idString
////                industryPut=Integer.parseInt(industryNumber);//id
                Intent intent = new Intent();
                intent.putExtra("companyInputString", name);
//                intent.putExtra("industryId",nameId);
                setResult(RESULT_OK, intent);
                finish();
//                //把企业id传过来 详情 第二期做
//                Intent intent = new Intent(getApplicationContext(), CompanyInformationCompanyDetailActivity.class);
//                startActivity(intent);
//                Intent intent = new Intent(getApplicationContext(), CompanyDetailWebActivity.class);//有个聊天的标志
////                intent.putExtra("url", entityList.get(position).getUrl());
//                startActivity(intent);

            }
        });
        activitySearchRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        activityCompanyInformationDetailRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        activitySearchRecyclerView.setAdapter(companySearchAdapter);

//        getGankInto();//网络请求
    }

    @OnClick({R.id.activity_search_left_image, R.id.activity_search_right_image, R.id.activity_search_clear_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_search_left_image://返回
                finish();
                break;
            case R.id.activity_search_right_image://搜索
                String inputString = activitySearchEdit.getText().toString().trim();
                Intent intent = new Intent();
                intent.putExtra("companyInputString", inputString);
                setResult(RESULT_OK, intent);
                finish();

                break;
            case R.id.activity_search_clear_text://清空
                break;
        }
    }
}

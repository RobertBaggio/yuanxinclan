package com.yuanxin.clan.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.adapter.IndustryClassifyAdapter;
import com.yuanxin.clan.core.company.bean.IndustryClassifyEntity;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.recyclerview.DividerItemDecoration;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/28.
 */
public class BusinessDistrictClassifyActviity extends BaseActivity {
    @BindView(R.id.rlLeft)
    LinearLayout backLayout;
    @BindView(R.id.allIndustry)
    TextView allIndustry;
    @BindView(R.id.activity_business_district_classify_recycler_view)
    RecyclerView activityBusinessDistrictClassifyRecyclerView;
    private List<IndustryClassifyEntity> industryClassifyEntities = new ArrayList<>();
    private IndustryClassifyAdapter industryClassifyAdapter;
    private SubscriberOnNextListener getIndustryListOnNextListener;
    private String industryId, industryNm, industryNumber, province, city, area, epNm;
    private List<IndustryListEntity> entityList = new ArrayList<IndustryListEntity>();

    @Override
    public int getViewLayout() {
        return R.layout.activity_business_district_classify;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
//        initOnNext();
        initView();
    }

    private void initOnNext() {
        getIndustryListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {

            }
        };
    }

    private void initView() {
        industryClassifyAdapter = new IndustryClassifyAdapter(BusinessDistrictClassifyActviity.this, entityList);
        industryClassifyAdapter.setOnItemClickListener(new IndustryClassifyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = entityList.get(position).getIndustryNm();
                SharedPreferences share = getSharedPreferences("businessIndustryInfo", Activity.MODE_PRIVATE);
                String nameId = share.getString(name, "");//行业名称 对应 idString
                Intent intent = new Intent();
                intent.putExtra("industryName", name);
                intent.putExtra("industryId", nameId);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        activityBusinessDistrictClassifyRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityBusinessDistrictClassifyRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        activityBusinessDistrictClassifyRecyclerView.setAdapter(industryClassifyAdapter);
        activityBusinessDistrictClassifyRecyclerView.setFocusable(false);//导航栏切换不再focuse
        String url = Url.getindustrylist;
        RequestParams params = new RequestParams();
        if (TextUtil.isEmpty(getIntent().getStringExtra("search_type"))) {
            url = Url.getindustrylist;
            allIndustry.setVisibility(View.GONE);
        } else {
            url = Url.getBusinessIndustryList;
            params.put("businessAreaId", getIntent().getIntExtra("businessAreaId", 501));
//            HttpMethods.getInstance().getBusinessIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, BusinessDistrictClassifyActviity.this), getIntent().getIntExtra("businessAreaId", 501));
        }
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String array = object.getString("data");
                        entityList.addAll(FastJsonUtils.getObjectsList(array, IndustryListEntity.class));
                        industryClassifyAdapter.notifyDataSetChanged();
                        for (int j = 0; j < entityList.size(); j++) {
                            industryId = String.valueOf(entityList.get(j).getIndustryId());//id String
                            industryNm = entityList.get(j).getIndustryNm();//名称
                            SharedPreferences sharedPreferences = getSharedPreferences("businessIndustryInfo", Context.MODE_PRIVATE); //私有数据
                            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                            editor.putString(industryNm, industryId);//名称 id String
                            editor.putString(industryId, industryNm);//id 名称
                            editor.commit();//提交修改
                        }
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络原因，无法获取行业数据", Toast.LENGTH_SHORT);
            }
        });
    }

    @OnClick({R.id.rlLeft, R.id.allIndustry})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                finish();
                break;
            case R.id.allIndustry:
                Intent intent = new Intent();
                intent.putExtra("industryName", allIndustry.getText());
                intent.putExtra("industryId", "0");
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }
}

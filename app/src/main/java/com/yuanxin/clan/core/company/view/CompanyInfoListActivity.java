package com.yuanxin.clan.core.company.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.adapter.CompanyInfoListAdapter;
import com.yuanxin.clan.core.company.bean.CompanyInfoListEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.recyclerviewhelper.OnStartDragListener;
import com.yuanxin.clan.core.recyclerviewhelper.SimpleItemTouchHelperCallback;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/23.
 */
//企业动态
public class CompanyInfoListActivity extends BaseActivity implements OnStartDragListener {

    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_enterprises_industry_recycler_view)
    RecyclerView activityEnterprisesIndustryRecyclerView;
    @BindView(R.id.activity_company_info_list_layout)
    RelativeLayout activityCompanyInfoListLayout;
    @BindView(R.id.activity_company_info_list_publish_image)
    ImageView activityCompanyInfoListPublishImage;
    @BindView(R.id.my_collect_checkbox)
    CheckBox myCollectCheckbox;
    @BindView(R.id.my_collect_num_text)
    TextView myCollectNumText;
    @BindView(R.id.my_collect_delete_btn)
    Button myCollectDeleteBtn;
    @BindView(R.id.my_collect_bottom_layout)
    RelativeLayout myCollectBottomLayout;
    private int epId, m_num;
    private List<CompanyInfoListEntity> companyInfoListEntities = new ArrayList<>();
    private List<String> m_job_ids = new ArrayList<String>();
    private CompanyInfoListAdapter activityEnterprisesIndustryAdapter;
    private boolean flag;
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private ItemTouchHelper mItemTouchHelper;


    @Override
    public int getViewLayout() {
        return R.layout.activity_company_info_list;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        activityEnterprisesIndustryAdapter.notifyDataSetChanged();
    }

    protected void onStart() {
        super.onStart();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.PUBLISH_COMPANY_INFO_REFRESH");
        localReceiver = new LocalReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);

    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            ToastUtil.showInfo(context, "received local broadcast",
                    Toast.LENGTH_SHORT);
        }
    }


    private void initView() {
        activityEnterprisesIndustryAdapter = new CompanyInfoListAdapter(CompanyInfoListActivity.this, companyInfoListEntities, this);
        activityEnterprisesIndustryAdapter.setOnItemClickListener(new CompanyInfoListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtil.showInfo(CompanyInfoListActivity.this, "请在PC端编辑动态", Toast.LENGTH_SHORT);
//                startActivity(new Intent(getApplicationContext(), EnterprisesIndustryDetailActivity.class));
            }
        });
        activityEnterprisesIndustryAdapter.setOnMyCheckedChangeListener(new CompanyInfoListAdapter.OnMyCheckedChangeListener() {
            @Override
            public void onCheckedChange(int num, List<String> jobIdList) {
                m_num = num;//几件被选中
//                my_collect_num_text.setText("共" + num + "件");
                if (jobIdList.size() > 0) {//件id集合
                    m_job_ids.clear();
                    m_job_ids.addAll(jobIdList);
                }
                if (num == companyInfoListEntities.size() && num > 0) {//我的收藏实体
                    myCollectCheckbox.setChecked(true);
                } else {
                    myCollectCheckbox.setChecked(false);
                }
            }
        });
        activityEnterprisesIndustryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        activityEnterprisesIndustryRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
        activityEnterprisesIndustryRecyclerView.setAdapter(activityEnterprisesIndustryAdapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(activityEnterprisesIndustryAdapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(activityEnterprisesIndustryRecyclerView);
        getInfo();
    }


    private void getInfo() {
        Intent intent = getIntent();
        epId = intent.getIntExtra("epId", 0);
        getPublishCompanyInfo(epId);//获取企业动态 感觉不需要 不弄了
    }

    private void getPublishCompanyInfo(int epId) {//我的企业动态
        String url = Url.getCompanyMessage;
        RequestParams params = new RequestParams();
        params.put("epId", epId);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        companyInfoListEntities.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String epNewsId = dataObject.getString("epNewsId");
                            String title = dataObject.getString("title");
                            String content = dataObject.getString("content");
                            String createDt = dataObject.getString("createDt");
                            CompanyInfoListEntity entity = new CompanyInfoListEntity();
                            entity.setEpNewsId(epNewsId);
                            entity.setTitle(title);
                            entity.setTime(createDt);
                            entity.setContent(content);
                            companyInfoListEntities.add(entity);
                        }
                        activityEnterprisesIndustryAdapter.notifyDataSetChanged();

                    } else {

                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }


                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 407) {
            if (resultCode == RESULT_OK) {
//                String image = data.getStringExtra("image");//淘宝id
//                Toast.makeText(getApplicationContext(),image,Toast.LENGTH_SHORT).show();
//                getGoodsAddress();
                getInfo();
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout, R.id.activity_company_info_list_publish_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_info_list_publish_image://发布
//                Intent intent = new Intent(CompanyInfoListActivity.this, PublishCompanyInfoActivity.class);
//                intent.putExtra("epId", epId);
//                startActivityForResult(intent,407);
//                Intent intent = new Intent(CompanyInfoListActivity.this, PublishCompanyInfoActivity.class);
//                intent.putExtra("epId", epId);
//                startActivity(intent);
                break;
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_right_layout://编辑
                Intent intent = new Intent(CompanyInfoListActivity.this, PublishCompanyInfoActivity.class);
                intent.putExtra("epId", epId);
                startActivityForResult(intent, 407);
//                if (!flag) {
//                    activityEnterprisesIndustryAdapter.changeStatus();
//
////                    my_collect_bottom_layout.setVisibility(View.VISIBLE);
////                    setHeadTextClick(R.string.finish,this);
//                } else {
//
//                }
//                flag = !flag;

                break;
        }
    }


}

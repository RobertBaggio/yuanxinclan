package com.yuanxin.clan.core.company.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.adapter.CompanyStoreAdapter;
import com.yuanxin.clan.core.company.bean.CompanyStoreEntity;
import com.yuanxin.clan.core.http.Url;
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
 * Created by lenovo1 on 2017/3/21.
 */
public class CompanyShopActivity extends BaseActivity {

    @BindView(R.id.activity_company_shop_head_image_layout)
    LinearLayout activityCompanyShopHeadImageLayout;
    @BindView(R.id.activity_company_shop_right_layout)
    LinearLayout activityCompanyShopRightLayout;
    @BindView(R.id.activity_company_information_detail_hot_service_recycler_view)
    RecyclerView activityCompanyInformationDetailHotServiceRecyclerView;
    //    private String taoBao, tianMao, jingDong, weiPinHui;
    private List<CompanyStoreEntity> companyStore = new ArrayList<>();
    private CompanyStoreAdapter adapter;
    private List<String> epStoreIdList = new ArrayList<String>();//删除集合
    private ArrayList<String> epStoreNmList = new ArrayList<>();//删除集合

    private static String idStr;

    @Override
    public int getViewLayout() {
        return R.layout.activity_company_shop;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        String epStore = getIntent().getStringExtra("epStore");
        getRecyclerView();
    }

    private void getRecyclerView() {
        adapter = new CompanyStoreAdapter(CompanyShopActivity.this, companyStore);
        adapter.setOnItemClickListener(new CompanyStoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView checkImage = (ImageView)view.findViewById(R.id.item_shopping_cart_check_image);
                if (!companyStore.get(position).is_checked()) {
                    companyStore.get(position).setIs_checked(true);
                    checkImage.setBackgroundResource(R.drawable.btn_selected);
                } else {
                    companyStore.get(position).setIs_checked(false);
                    checkImage.setBackgroundResource(R.drawable.btn_unselected);

                }
            }
        });
//        adapter.setOnMyCheckedChangeListener(new CompanyStoreAdapter.OnMyCheckedChangeListener() {
//            @Override
//            public void onCheckedChange(int num, List<CompanyStoreEntity> buyList) {
//                m_buyList.clear();
//                m_buyList.addAll(buyList);
//
//            }
//        });
        activityCompanyInformationDetailHotServiceRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityCompanyInformationDetailHotServiceRecyclerView.setAdapter(adapter);
        getCompanyStore();//区域定位 东莞
    }

    private void getCompanyStore() {
        AsyncHttpClient client = new AsyncHttpClient();
        String url = Url.getStoreList;
        RequestParams params = new RequestParams();
        params.put("pageNumber", 0);//用户id
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        companyStore.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String epStoreId = dataObject.getString("epStoreId"); //
                            String epStoreNm = dataObject.getString("epStoreNm"); //
                            String [] epStores = getIntent().getStringExtra("epStore").split(",");
                            CompanyStoreEntity entity = new CompanyStoreEntity();//
                            entity.setStore(epStoreNm);
                            entity.setEpStoreId(epStoreId);
                            for (String store: epStores) {
                                if (store.equals(epStoreId)) {
                                    entity.setIs_checked(true);
                                }
                            }
                            SharedPreferences sharedPreferences = getSharedPreferences("companyShopInfo", Context.MODE_PRIVATE); //私有数据
                            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                            editor.putString(epStoreNm, epStoreId);//名称 id String
                            editor.commit();//提交修改
                            companyStore.add(entity);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }
        });
    }

    @OnClick({R.id.activity_company_shop_head_image_layout, R.id.activity_company_shop_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_shop_head_image_layout:
                finish();
                break;
            case R.id.activity_company_shop_right_layout://确定
                for (int i = 0; i < companyStore.size(); i++) {
                    CompanyStoreEntity entity = companyStore.get(i);
                    if (!entity.is_checked()) {
                        continue;
                    }
                    String epStoreId = entity.getEpStoreId();
                    String epStoreNm = entity.getStore();
                    epStoreIdList.add(epStoreId);
                    epStoreNmList.add(epStoreNm);
                }
                String epStoreIds = list2string(epStoreIdList);
                Intent intent = new Intent();
                intent.putExtra("all", epStoreIds);
                intent.putStringArrayListExtra("allName", epStoreNmList);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    public static String list2string(List<String> stringList) {
        idStr = ",";
        for (int i = 0; i < stringList.size(); i++) {
            idStr += stringList.get(i) + ",";
        }
        return idStr.substring(1, idStr.length() - 1);
    }
}

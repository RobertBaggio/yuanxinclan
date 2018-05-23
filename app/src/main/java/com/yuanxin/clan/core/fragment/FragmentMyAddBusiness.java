package com.yuanxin.clan.core.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.BusinessDetailWebActivity;
import com.yuanxin.clan.core.adapter.BusinessDistrictLibraryAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.BusinessDistrictLibraryEntity;
import com.yuanxin.clan.core.entity.BusinessDistrictListEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/4/26.
 */
//我加入的商圈
public class FragmentMyAddBusiness extends BaseFragment {
    @BindView(R.id.fragment_my_all_crowd_funding_recycler_view)
    RecyclerView fragmentMyAllCrowdFundingRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;
    private List<BusinessDistrictLibraryEntity> businessDistrictLibraryEntities = new ArrayList<>();
    private BusinessDistrictLibraryAdapter adapter;
    private SubscriberOnNextListener getBusinessSearchListOnNextListener;
    private List<BusinessDistrictListEntity> businessDistrictListEntities = new ArrayList<>();//BusinessDistrictLibraryEntity
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数
    private String detail;
    private More_LoadDialog mMore_loadDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_add_business;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mMore_loadDialog = new More_LoadDialog(getContext());

        initRecyclerView();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcast.SET_UP_BUSINESS_DISTRICT");
        localReceiver = new LocalReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getMyBusinessDistrict(1);//创建商圈后刷新
        }
    }

    private void getMyBusinessDistrict(int pageNumber) {
        String url = Url.getBusinessDetail;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        params.put("pageNumber", pageNumber);//用户id
        params.put("type", 2);//类型（1.我创建的 2.我加入的）
        mMore_loadDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);
                    pageCount = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject businessObject = jsonArray.getJSONObject(a);
                            String businessAreaId = businessObject.getString("businessAreaId");//商圈id
                            String baImage1 = businessObject.getString("baImage1");//图片
                            String image = Url.img_domain + baImage1+Url.imageStyle640x640;
                            String businessAreaNm = businessObject.getString("businessAreaNm");//商圈名称
                            String businessAreaType = businessObject.getString("businessAreaType");//商圈名称
                            int memberShip = businessObject.getInt("memberShip");
                            int collectionCount = businessObject.getInt("collectionCount");
//                            String accessPath = businessObject.getString("accessPath");
                            JSONObject epView = businessObject.optJSONObject("epView");
                            String epAccessPath;//商圈名称
                            if (epView == null) {
                                epAccessPath = null;
                            } else {
                                epAccessPath = epView.getString("epAccessPath");
                            }
                            String address = businessObject.getString("address");
                            if (!address.equals("null")) {
                                JSONObject addressObject = businessObject.getJSONObject("address");
                                detail = addressObject.getString("area");
                            }
                            String address1 = businessObject.getString("address");
                            String city="";
                            String industryNm="";
                            String areaTen="";
                            if (!address1.equals("null")) {
                                JSONObject addressObject = businessObject.getJSONObject("address");
                                areaTen = addressObject.getString("area");//地区
                                city = addressObject.getString("city");
                            }
                            String ind = businessObject.getString("industry");
                            if (!ind.equals("null")){
                                JSONObject industry = businessObject.getJSONObject("industry");
                                industryNm=industry.getString("industryNm");
                            }

                            BusinessDistrictListEntity entity = new BusinessDistrictListEntity();
                            entity.setImage(image);//图片
                            entity.setArea(detail);//区域
                            entity.setBusinessAreaId(businessAreaId);//商圈id
                            entity.setBusinessAreaNm(businessAreaNm);//名称
                            entity.setEpAccessPath(epAccessPath);
                            entity.setArea(areaTen);
                            entity.setCity(city);
                            entity.setIndustryNm(industryNm);
                            entity.setMemberShip(memberShip);
                            entity.setBusinessAreaType(businessAreaType);
                            entity.setCollectionCount(collectionCount);
//                            entity.setAccessPath(accessPath);
                            businessDistrictListEntities.add(entity);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    private void initRecyclerView() {
        adapter = new BusinessDistrictLibraryAdapter(getContext(), businessDistrictListEntities);
        adapter.setOnItemClickListener(new BusinessDistrictLibraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), BusinessDetailWebActivity.class);//商圈详情
                intent.putExtra("businessAreaId", businessDistrictListEntities.get(position).getBusinessAreaId());
                intent.putExtra("epStyleType", businessDistrictListEntities.get(position).getEpAccessPath());
                intent.putExtra("name", businessDistrictListEntities.get(position).getBusinessAreaNm());
//                intent.putExtra("accessPath", businessDistrictListEntities.get(position).getAccessPath());
                startActivity(intent);
            }
        });
        fragmentMyAllCrowdFundingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMyAllCrowdFundingRecyclerView.setAdapter(adapter);
        fragmentMyAllCrowdFundingRecyclerView.setFocusable(false);//导航栏切换不再focuse
        fragmentMyAllCrowdFundingRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getMyBusinessDistrict(1);
        fragmnetMyCollectArticleSpringview.setHeader(new RotationHeader(getContext()));
        fragmnetMyCollectArticleSpringview.setFooter(new RotationFooter(getContext()));
        fragmnetMyCollectArticleSpringview.setType(SpringView.Type.OVERLAP);
        fragmnetMyCollectArticleSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetMyCollectArticleSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                businessDistrictListEntities.clear();
                currentPage = 1;
                getMyBusinessDistrict(currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetMyCollectArticleSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);

                currentPage += 1;
                if (currentPage>pageCount){
                    ToastUtil.showInfo(getContext(), "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getMyBusinessDistrict(currentPage);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

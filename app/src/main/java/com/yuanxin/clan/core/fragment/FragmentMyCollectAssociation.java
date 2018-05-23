package com.yuanxin.clan.core.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.yuanxin.clan.core.entity.BusinessDistrictListEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
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
 * Created by lenovo1 on 2017/2/24.
 * //我的收藏商圈
 */
public class FragmentMyCollectAssociation extends BaseFragment {
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityBusinessDistrictLibraryRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;

    private BusinessDistrictLibraryAdapter adapter;
    private List<BusinessDistrictListEntity> businessDistrictListEntities = new ArrayList<>();//BusinessDistrictLibraryEntity
    private int currentPage = 1;// 当前页面，从0开始计数
    private int pageall;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_collect_association;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new BusinessDistrictLibraryAdapter(getContext(), businessDistrictListEntities);
        adapter.setOnItemClickListener(new BusinessDistrictLibraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), BusinessDetailWebActivity.class);//商圈详情
                intent.putExtra("businessAreaId", businessDistrictListEntities.get(position).getBusinessAreaId());
                intent.putExtra("myc", 1);
                intent.putExtra("epStyleType", businessDistrictListEntities.get(position).getAccessPath());
                intent.putExtra("name",businessDistrictListEntities.get(position).getBusinessAreaNm());
                startActivity(intent);
            }
        });
        activityBusinessDistrictLibraryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activityBusinessDistrictLibraryRecyclerView.setAdapter(adapter);
        activityBusinessDistrictLibraryRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityBusinessDistrictLibraryRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getWebInfo(1);
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
                getWebInfo(currentPage);
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
                if (pageall<currentPage){
                    ToastUtil.showWarning(getContext(), "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getWebInfo(currentPage);
            }
        });
    }

    private void getWebInfo(int pageNumber) {//还差图片
        String url = Url.getMyCollect;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户Id
        params.put("pageNumber", pageNumber);//当前显示第几页
        params.put("type", 2);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户 9 智囊团
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
//                    Log.v("lgq","收藏返回。。22222。。"+s);
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        pageall = object.getInt("pageCount");
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String accessPath = dataObject.getString("accessPath");
                            JSONObject businessAreaObject = dataObject.getJSONObject("businessArea");//商圈

                            String businessAreaId = businessAreaObject.getString("businessAreaId");//商圈id
                            String businessAreaNm = businessAreaObject.getString("businessAreaNm");//商圈名称
                            String businessAreaType = businessAreaObject.getString("businessAreaType");//商系名称
                            String city = dataObject.getString("city");//商系名称
                            String industryNm = businessAreaObject.getString("industryNm");//商系名称
                            String businessAreaDetail = businessAreaObject.getString("businessAreaDetail");//商系名称
                            int collectionCount = businessAreaObject.getInt("collectionCount");
                            int memberShip = businessAreaObject.getInt("memberShip");
                            String baImage1 = businessAreaObject.getString("baImage1");//商圈名称
                            String image = Url.img_domain + baImage1+Url.imageStyle640x640;//图片
//                            String address1=businessAreaObject.getString("address");
                            //要修改要修改要修改要修改要修改要修改要修改要修改要修改要修改要修改
//                            if(!address1.equals("null")){
//                                JSONObject addressObject=businessObject.getJSONObject("address");
//                                areaTen=addressObject.getString("area");//地区
////
//                            }

                            BusinessDistrictListEntity entity = new BusinessDistrictListEntity();
                            entity.setImage(image);
                            entity.setCity(city);
                            entity.setBusinessAreaType(businessAreaType);
                            entity.setBusinessAreaNm(businessAreaNm);
                            entity.setBusinessAreaId(businessAreaId);
                            entity.setMemberShip(memberShip);
                            entity.setIndustryNm(industryNm);
                            entity.setCollectionCount(collectionCount);
                            entity.setBusinessAreaDetail(businessAreaDetail);
//                            entity.setEpAccessPath(epAccessPath);
                            Log.v("Lgq","accessPath======="+accessPath);
                            entity.setAccessPath(accessPath);
                            businessDistrictListEntities.add(entity);

                        }
                        adapter.notifyDataSetChanged();
                    } else {

//                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
                int sc = MyShareUtil.getSharedInt("sc");
        if (sc==1) {
            businessDistrictListEntities.clear();
            getWebInfo(1);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        MyShareUtil.sharedPint("sc",0);
    }
    //    @Override
//    protected void onResume() {
//        super.onResume();
//        int sc = MyShareUtil.getSharedInt("sc");
//        Log.v("lgq","huilail ====="+sc);
//        if (sc==1) {
//            ab=1;
//            businessDistrictListEntities.clear();
//            getBusinessDistrictList();
//        }
//
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        MyShareUtil.sharedPint("sc",0);
//    }

}

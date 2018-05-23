package com.yuanxin.clan.core.mineclass;

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
import com.yuanxin.clan.core.activity.GongXuDetailActivity;
import com.yuanxin.clan.core.adapter.AllGongXuAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.GongXuEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * ProjectName: yuanxinclan_new
 */

public class MyAllgxFragment extends BaseFragment {
    @BindView(R.id.fragment_my_all_crowd_funding_recycler_view)
    RecyclerView fragmentMyAllCrowdFundingRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;
    private List<GongXuEntity> mGongXuEntities = new ArrayList<>();
    private AllGongXuAdapter adapter;
    private SubscriberOnNextListener getBusinessSearchListOnNextListener;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数
    private More_LoadDialog mMore_loadDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    @Override
    public int getViewLayout() {
        return R.layout.mygxfragmentlayout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
        mMore_loadDialog = new More_LoadDialog(getContext());
    }

    private void getMyBusinessDistrict(int pageNumber) {
        String url = Url.getgongxu;
        RequestParams params = new RequestParams();
        mMore_loadDialog.show();
        params.put("pageNumber", pageNumber);
        params.put("userId", UserNative.getId());
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
                            String supplyDemandId = businessObject.getString("supplyDemandId");//商圈id
                            String image1 = businessObject.getString("image1");//图片
                            String image11 = Url.img_domain + image1+Url.imageStyle640x640;
                            String image2 = businessObject.getString("image2");//图片
                            String image22 = Url.img_domain + image2+Url.imageStyle640x640;
                            String image3 = businessObject.getString("image3");//图片
                            String image33 = Url.img_domain + image3+Url.imageStyle640x640;
                            String image4 = businessObject.getString("image4");//图片
                            String image44 = Url.img_domain + image4+Url.imageStyle640x640;
                            String image5 = businessObject.getString("image5");//图片
                            String image55 = Url.img_domain + image5+Url.imageStyle640x640;
                            String image6 = businessObject.getString("image6");//图片
                            String image66 = Url.img_domain + image6+Url.imageStyle640x640;
                            String content = businessObject.getString("content");//商圈名称
                            String createDt = businessObject.getString("createDt");
                            String epId = businessObject.getString("epId");
                            String supplyDemand = businessObject.getString("supplyDemand");
                            String userId = businessObject.getString("userId");
                            String title = businessObject.getString("title");
                            String number = businessObject.getString("number");
                            String budget = businessObject.getString("budget");
                            String deliveryCycle = businessObject.getString("deliveryCycle");
                            String endTime = businessObject.getString("endTime");
                            String address = businessObject.getString("address");
                            JSONObject twoob=new JSONObject(address);
                            String city = twoob.getString("city");
                            String addressId = twoob.getString("addressId");
                            String province = twoob.getString("province");
                            String area = twoob.getString("area");
                            String addresstwo = province+"-"+city+"-"+area;
                            String industry = businessObject.getString("industry");
                            JSONObject threeob = new JSONObject(industry);
                            String industryId = threeob.getString("industryId");
                            String industryNm = threeob.getString("industryNm");
//                            Log.v("Lgq","....... " +image11+"  ...   "+image22+"  。。。 "+image33);

                            GongXuEntity entity = new GongXuEntity();

                            entity.setImage1(TextUtil.isEmpty(image1)?image1:image11);
                            entity.setImage2(TextUtil.isEmpty(image2)?image2:image22);
                            entity.setImage3(TextUtil.isEmpty(image3)?image3:image33);
                            entity.setImage4(TextUtil.isEmpty(image4)?image4:image44);
                            entity.setImage5(TextUtil.isEmpty(image5)?image5:image55);
                            entity.setImage6(TextUtil.isEmpty(image6)?image6:image66);


                            entity.setContent(content);
                            entity.setCreateDt(createDt);
                            entity.setEpId(epId);
                            entity.setSupplyDemandId(supplyDemandId);
                            entity.setSupplyDemand(supplyDemand);
                            entity.setUserId(userId);
                            entity.setTitle(title);
                            entity.setCity(city);
                            entity.setIndustryNm(industryNm);
                            entity.setIndustryId(industryId);
                            entity.setNumber(number);
                            entity.setBudget(budget);
                            entity.setEndTime(endTime);
                            entity.setDeliveryCycle(deliveryCycle);
                            entity.setAddress(addresstwo);
                            entity.setProvince(province);
                            entity.setArea(area);
                            entity.setAddressId(addressId);

                            mGongXuEntities.add(entity);
                        }
                        adapter.notifyDataSetChanged();


                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    private void initRecyclerView() {
        adapter = new AllGongXuAdapter(getContext(), mGongXuEntities);
        adapter.setOnItemClickListener(new AllGongXuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                http://192.168.1.106/yuanxinbuluo/weixin/getJsp?url=wechatweb/market-supply_demand-info&param=10000002042271297&appFlg=0
                String link = Url.urlWeb+"/my-demand-info&param="+mGongXuEntities.get(position).getSupplyDemandId()+"&appFlg=0";
                Intent intent = new Intent(getContext(), GongXuDetailActivity.class);//商圈详情
                intent.putExtra("url", link);
                intent.putExtra("title", mGongXuEntities.get(position).getTitle());
                intent.putExtra("datas", (Serializable) mGongXuEntities.get(position));
                startActivity(intent);
            }
        });
        fragmentMyAllCrowdFundingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMyAllCrowdFundingRecyclerView.setAdapter(adapter);
        fragmentMyAllCrowdFundingRecyclerView.setFocusable(false);//导航栏切换不再focuse
        fragmentMyAllCrowdFundingRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
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
                mGongXuEntities.clear();
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
    public void onResume() {
        super.onResume();
        mGongXuEntities.clear();
        getMyBusinessDistrict(1);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}

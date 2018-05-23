package com.yuanxin.clan.core.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.adapter.indicator.MycardAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.entity.MyCardEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
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
import butterknife.Unbinder;

/**
 * ProjectName: yuanxinclan_new.
 *
 */

public class MyCardFragment extends BaseFragment {

    @BindView(R.id.fragment_my_all_crowd_funding_recycler_view)
    RecyclerView fragmentMyAllCrowdFundingRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;

    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    public Unbinder unbinder;
    private int currentPage = 1;// 当前页面，从0开始计数
    private int maxpage;
    private MycardAdapter adapter;
    private List<MyCardEntity> businessDistrictListEntities = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.mycardfragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
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
        }
    }

    private void initRecyclerView() {
        adapter = new MycardAdapter(getContext(), businessDistrictListEntities);
        adapter.setOnItemClickListener(new MycardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String url =Url.urlWeb + "/personalCard&cardId=" + businessDistrictListEntities.get(position).getEnterpriseCardInfoId();
                startActivity(new Intent(getActivity(), HomeADactivity.class).putExtra("url", url));
            }
        });
        adapter.setOnLockClickListener(new MycardAdapter.OnLockClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), CompanyDetailWebActivity.class);
                intent.putExtra("epId", businessDistrictListEntities.get(position).getEid());
                intent.putExtra("accessPath", businessDistrictListEntities.get(position).getAccessPath());
                startActivity(intent);
            }
        });
        adapter.setOnlickClickListener(new MycardAdapter.OnOnlickKClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, businessDistrictListEntities.get(position).getPhone());
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra(Constant.USER_NAME, UserNative.getName());
                intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                intent.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
//                intent.putExtra("type", "与商家聊天中");
                startActivity(intent);
            }
        });
        adapter.setOnPhonelickListener(new MycardAdapter.OnPhoneKClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+businessDistrictListEntities.get(position).getPhone()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
                if (currentPage>maxpage){
                    ToastUtil.showInfo(getContext(), "已加载完！", Toast.LENGTH_SHORT);
                    return;
                }
                getMyBusinessDistrict(currentPage);
            }
        });
    }

    private void getMyBusinessDistrict(int pageNumber) {
        String url = Url.getpersoncard;
        RequestParams params = new RequestParams();
        params.put("epId", UserNative.getEpId());//用户id
        params.put("pageNumber", pageNumber);//用户id
//        params.put("activeId", UserNative.getId());
        params.put("activeId", UserNative.getId());
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    maxpage = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject businessObject = jsonArray.getJSONObject(a);
                            String enterpriseCardInfoId = businessObject.getString("enterpriseCardInfoId");//商圈id
                            String epImage1 = businessObject.getString("epImage1");//图片
                            int eid = businessObject.getInt("epId");
                            String image = Url.img_domain + epImage1+Url.imageStyle640x640;
                            String epNm = businessObject.getString("epNm");//商圈名称
                            String userNm = businessObject.getString("userNm");
                            String userPhone = businessObject.getString("userPhone");
                            String accessPath = businessObject.getString("accessPath");


                            MyCardEntity entity = new MyCardEntity();
                            entity.setAccessPath(accessPath);
                            entity.setCompanyname(epNm);
                            entity.setUname(userNm);
                            entity.setPhone(userPhone);
                            entity.setImage(image);
                            entity.setEid(eid);
                            entity.setEnterpriseCardInfoId(enterpriseCardInfoId);
                            businessDistrictListEntities.add(entity);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.e("数据解析出错");
                }
            }
        });
    }

}

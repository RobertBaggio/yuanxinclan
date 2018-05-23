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
import com.yuanxin.clan.core.activity.ToMeAppointDetailActivity;
import com.yuanxin.clan.core.adapter.TomeAppointmentAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.AppointmentEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
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
import butterknife.Unbinder;

/**
 * ProjectName: yuanxinclan_new
 *
 */

public class ToMeAppointmentFmt extends BaseFragment {

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
    private TomeAppointmentAdapter adapter;
    private List<AppointmentEntity> businessDistrictListEntities = new ArrayList<>();
    private More_LoadDialog mMore_loadDialog;

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
        mMore_loadDialog = new More_LoadDialog(getContext());
        initRecyclerView();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcast.SET_UP_BUSINESS_DISTRICT");
        localReceiver = new LocalReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();
        businessDistrictListEntities.clear();
        getMyBusinessDistrict(1);

    }
    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    }

    private void initRecyclerView() {
        adapter = new TomeAppointmentAdapter(getContext(), businessDistrictListEntities);
        adapter.setOnItemClickListener(new TomeAppointmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                getMyBusinessUD(businessDistrictListEntities.get(position).getVisitAppointmentId(),position);

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
                if (currentPage==maxpage){
                    ToastUtil.showInfo(getContext(), "已加载完！", Toast.LENGTH_SHORT);
                    return;
                }
                currentPage += 1;
                getMyBusinessDistrict(currentPage);
            }
        });
    }

    private void getMyBusinessDistrict(int pageNumber) {
        String url = Url.myappoint;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pageNumber);//用户id
        params.put("bVisitorId", UserNative.getId());
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
                            int visitAppointmentId = businessObject.getInt("visitAppointmentId");
                            String bVisitorNm = businessObject.getString("aVisitorNm");
                            String bVisitorCompany = businessObject.getString("bVisitorCompany");
                            String time = businessObject.getString("time");
                            String statusNm = businessObject.getString("statusNm");
                            String reason = businessObject.getString("reason");
                            String bVisitorPhone = businessObject.getString("aVisitorPhone");
                            int status = businessObject.getInt("status");
                            int aVisitorId = businessObject.getInt("aVisitorId");
                            int bVisitorId = businessObject.getInt("bVisitorId");
                            String bVisitorRead = businessObject.getString("bVisitorRead");




                            AppointmentEntity entity = new AppointmentEntity();
                            entity.setVisitAppointmentId(visitAppointmentId);
                            entity.setEname(bVisitorCompany);
                            entity.setUname(bVisitorNm);
                            entity.setTime(time);
                            entity.setStatus(statusNm);
                            entity.setStatusid(status);
                            entity.setReason(reason);
                            entity.setbVisitorPhone(bVisitorPhone);
                            entity.setaVisitorId(aVisitorId);
                            entity.setbVisitorId(bVisitorId);
                            entity.setbVisitorRead(bVisitorRead);

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

    private void getMyBusinessUD(int pageNumber,final int poin) {
        String url = Url.myappointYUEDU;
        RequestParams params = new RequestParams();
        params.put("visitAppointmentId", pageNumber);//用户id
        params.put("bVisitorRead", 1);
        params.put("userId", UserNative.getId());
        params.put("userNm", UserNative.getName());
        mMore_loadDialog.show();
        doHttpPost(url, params, new RequestCallback() {
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
                    if (object.getString("success").equals("true")) {
                        Intent intent = new Intent(getActivity(), ToMeAppointDetailActivity.class);
                        AppointmentEntity a = new AppointmentEntity();
                        a = businessDistrictListEntities.get(poin);
                        intent.putExtra("datas", (Serializable) a);
                        intent.putExtra("tome","1");
                        startActivity(intent);
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

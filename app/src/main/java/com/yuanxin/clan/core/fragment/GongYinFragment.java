package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.GongXuDetailActivity;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.adapter.AllGongXuAdapter;
import com.yuanxin.clan.core.adapter.verticalHorizontalRollingview.MarqueeView;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.GongXuEntity;
import com.yuanxin.clan.core.entity.HomePageAnnouncementEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.mineclass.GongxuSOS_Activity;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseFragment;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/26 0026 18:01
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class GongYinFragment extends BaseFragment implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
    @BindView(R.id.fragment_my_all_crowd_funding_recycler_view)
    RecyclerView fragmentMyAllCrowdFundingRecyclerView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.sosli)
    LinearLayout sosli;
    @BindView(R.id.noticeBoard)
    MarqueeView noticeBoard;
    @BindView(R.id.kongli)
    LinearLayout kongli;
    private List<GongXuEntity> mGongXuEntities = new ArrayList<>();
    private AllGongXuAdapter adapter;
    private SubscriberOnNextListener getBusinessSearchListOnNextListener;
    private IntentFilter intentFilter;
    private LocalBroadcastManager localBroadcastManager;
    private int ab = 1,pageCount;// 当前页面，从0开始计数
    private String detail;
    public Unbinder unbinder;
    private More_LoadDialog mMore_loadDialog;
    private List<HomePageAnnouncementEntity> mStrings=new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.allgongxufragmentla;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
        mMore_loadDialog = new More_LoadDialog(getContext());
//        sosli.setVisibility(View.GONE);
        getMyBusinessDistrict(1);
        initNoticeBoard();
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
    }
    private void initNoticeBoard() {
        String url = Url.homePageAnnouncement;
        RequestParams params = new RequestParams();
        /*1:首页广告位 2:供需成功案例*/
        params.put("announcementType", 2);
        doHttpGet(url, params, new RequestCallback(){
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toasty.error(getActivity(), "网络连接异常", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        mStrings.addAll(FastJsonUtils.getObjectsList(object.getString("data"), HomePageAnnouncementEntity.class));
                        ArrayList<String> notices = new ArrayList<String>();
                        for (HomePageAnnouncementEntity str: mStrings) {
                            notices.add(str.getAnnouncementTitle());
                        }
                        noticeBoard.startWithList(notices, R.anim.anim_bottom_in, R.anim.anim_top_out);
                        noticeBoard.setOnItemClickListener(new MarqueeView.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position, TextView textView) {
                                String url = mStrings.get(position).getAnnouncementContent();
                                if (url.startsWith("http")) {
                                    startActivity(new Intent(getActivity(), HomeADactivity.class).putExtra("url", url));
                                }
                            }
                        });
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getActivity(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.d("json 解析出错");
                }
            }
        });
    }
    private void getMyBusinessDistrict(int pageNumber) {
        String url = Url.getgongxu;
        RequestParams params = new RequestParams();
        mMore_loadDialog.show();
        params.put("pageNumber", pageNumber);
        params.put("supplyDemand", 0);
        params.put("status", 1);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                try {
                    JSONObject object = new JSONObject(s);
                    pageCount = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        if (jsonArray.length()==0){
                            kongli.setVisibility(View.VISIBLE);
                        }
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject businessObject = jsonArray.getJSONObject(a);
                            String supplyDemandId = businessObject.getString("supplyDemandId");//商圈id
                            String image1 = businessObject.getString("image1");//图片
                            String image11 = Url.img_domain + image1+Url.imageStyle640x640;
                            String image2 = businessObject.getString("image2");//图片
                            String image22 = Url.img_domain + image2+Url.imageStyle640x640;
                            String image3 = businessObject.getString("image3");//图片
                            String image33 = Url.img_domain + image3+Url.imageStyle640x640;
                            String content = businessObject.getString("content");//商圈名称
                            String createDt = businessObject.getString("createDt");
                            String epId = businessObject.getString("epId");
                            String supplyDemand = businessObject.getString("supplyDemand");
                            String userId = businessObject.getString("userId");
                            String title = businessObject.getString("title");
                            String address = businessObject.getString("address");
                            JSONObject twoob=new JSONObject(address);
                            String city = twoob.getString("city");
//                            Log.v("Lgq","....... " +image11+"  ...   "+image22+"  。。。 "+image33);

                            GongXuEntity entity = new GongXuEntity();

                            entity.setImage1(TextUtil.isEmpty(image1)?image1:image11);
                            entity.setImage2(TextUtil.isEmpty(image2)?image2:image22);
                            entity.setImage3(TextUtil.isEmpty(image3)?image3:image33);

                            entity.setContent(content);
                            entity.setCreateDt(createDt);
                            entity.setEpId(epId);
                            entity.setSupplyDemandId(supplyDemandId);
                            entity.setSupplyDemand(supplyDemand);
                            entity.setUserId(userId);
                            entity.setTitle(title);
                            entity.setCity(city);

                            mGongXuEntities.add(entity);
                        }
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) fragmentMyAllCrowdFundingRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(179)*mGongXuEntities.size();// 控件的高强制设成20
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
        sosli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), GongxuSOS_Activity.class).putExtra("supplyDemand", 0));
            }
        });
    }

    private void initRecyclerView() {
        adapter = new AllGongXuAdapter(getContext(), mGongXuEntities);
        adapter.setOnItemClickListener(new AllGongXuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 未登陆要求登陆
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                String link = Url.urlWeb+"/market-supply_demand-info&param="+mGongXuEntities.get(position).getSupplyDemandId()+"&appFlg=0";
                Intent intent = new Intent(getContext(), GongXuDetailActivity.class);//商圈详情
                intent.putExtra("url", link);
                intent.putExtra("title", mGongXuEntities.get(position).getTitle());
                startActivity(intent);
            }
        });
        fragmentMyAllCrowdFundingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMyAllCrowdFundingRecyclerView.setAdapter(adapter);
        fragmentMyAllCrowdFundingRecyclerView.setFocusable(false);//导航栏切换不再focuse
        fragmentMyAllCrowdFundingRecyclerView.setNestedScrollingEnabled(false);//禁止滑动

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        ab++;
        if (ab> pageCount) {
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showWarning(getContext(), "没有更多数据", Toast.LENGTH_SHORT);
            return;
        }
        getMyBusinessDistrict(ab);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mGongXuEntities.clear();
        ab = 1;
        getMyBusinessDistrict(ab);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        unbinder.unbind();
    }

}

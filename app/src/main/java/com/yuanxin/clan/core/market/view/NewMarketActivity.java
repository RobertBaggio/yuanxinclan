package com.yuanxin.clan.core.market.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.NoFinishActivity;
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.MyTradesAdapter;
import com.yuanxin.clan.core.market.adapter.YuanXinFairAdapter;
import com.yuanxin.clan.core.market.bean.NewMarketitem;
import com.yuanxin.clan.core.market.bean.TradesEntity;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 *新集市
 */

public class NewMarketActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.zhanhuili)
    LinearLayout zhanhuili;
    @BindView(R.id.gongxuli)
    LinearLayout gongxuli;
    @BindView(R.id.newgoodsli)
    LinearLayout newgoodsli;
    @BindView(R.id.talentli)
    LinearLayout talentli;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.bannerTop)
    ConvenientBanner mBannerTop;


    private SubscriberOnNextListener getMarketListOnNextListener;

    private List<AdvertisementsEntity> adsTopList = new ArrayList<>();
    private List<TradesEntity> mEntities = new ArrayList<>();
    private List<NewMarketitem>  mNewMarketitems = new ArrayList<>();
    private YuanXinFairAdapter yuanXinFairNewAdapter;
    private MyTradesAdapter mMyTradesAdapter;
    private int currentPage ;// 当前页面，从0开始计数
    private int totalPage = 1;
    private boolean  mIsRefreshing = false;
    private My_LoadingDialog mMy_loadingDialog;
    private String image1,image2,image3,image4;


    @Override
    public int getViewLayout() {
        return R.layout.newmarketlayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        mMy_loadingDialog = My_LoadingDialog.getInstance(NewMarketActivity.this);
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        initRecyclerView();
        getBusinessTop();
        getBusinesslist(1);
    }

    private void initRecyclerView() {
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse
        mMyTradesAdapter = new MyTradesAdapter(NewMarketActivity.this,mEntities);
        mMyTradesAdapter.setOnItemClickListener(new MyTradesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(NewMarketActivity.this, TradesDetailActivity.class);
                intent.putExtra("id", String.valueOf(mEntities.get(position).getExhibitionId()));
                intent.putExtra("name",mEntities.get(position).getHallNm());
                intent.putExtra("image",mEntities.get(position).getExhibitionTitleImg());
                String time = DateDistance.getDistanceTimeToZW(mEntities.get(position).getStarTime())+" 至 "+DateDistance.getDistanceTimeToZW(mEntities.get(position).getEndTime());
                intent.putExtra("time","会展时间："+time);
                startActivity(intent);
            }
        });
        activityYuanXinFairNewRecyclerView.setAdapter(mMyTradesAdapter);

    }

    private void getBusinessTop() {
        String url = Url.newmarketbennar;
        RequestParams params = new RequestParams();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
//                        org.json.JSONObject jsonObject = object.getJSONObject("data");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            AdvertisementsEntity advertisementsEntity = new AdvertisementsEntity();
                            String advertisementImg = businessObject.getString("advertisementImg");//商圈id
                            String string = Url.img_domain  + advertisementImg + Url.imageStyle750x350;
                            advertisementsEntity.setAdvertisementImg(string);
                            adsTopList.add(advertisementsEntity);
                        }
                        initConvenientBanner(mBannerTop, adsTopList);
                        mBannerTop.startTurning(4000);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void getBusinesslist(int id) {
        String url = Url.newmarketlist;
        RequestParams params = new RequestParams();
        params.put("pageNumber",id);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    currentPage = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
//                        org.json.JSONObject jsonObject = object.getJSONObject("data");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            TradesEntity advertisementsEntity = new TradesEntity();
                            String exhibitionTitle = businessObject.getString("exhibitionTitle");
                            String hallNm = businessObject.getString("hallNm");
                            String status = businessObject.getString("status");
                            String exhibitionTitleImg = businessObject.getString("exhibitionTitleImg");
                            String starTime = businessObject.getString("starTime");
                            String endTime = businessObject.getString("endTime");
                            int exhibitionId=businessObject.getInt("exhibitionId");
                            String string = Url.img_domain  + exhibitionTitleImg + Url.imageStyle750x350;
                            advertisementsEntity.setExhibitionTitleImg(string);
                            advertisementsEntity.setExhibitionTitle(exhibitionTitle);
                            advertisementsEntity.setEndTime(endTime);
                            advertisementsEntity.setStarTime(starTime);
                            advertisementsEntity.setHallNm(hallNm);
                            advertisementsEntity.setExhibitionId(exhibitionId);
                            advertisementsEntity.setStatus(status);
                            mEntities.add(advertisementsEntity);
                        }
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityYuanXinFairNewRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(180)*mEntities.size();// 控件的高强制设成20
                        mMyTradesAdapter.notifyDataSetChanged();
//                        initConvenientBanner(mBannerTop, adsTopList);
//                        mBannerTop.startTurning(4000);
//                        businessArea

//                        org.json.JSONArray businessArea = jsonObject.getJSONArray("businessArea");
//                        for (int b = 0; b < businessArea.length(); b++) {
//                            org.json.JSONObject businessObject = businessArea.getJSONObject(b);
//                            String businessAreaNm = businessObject.getString("businessAreaNm");//商圈id
//                            mStrings.add("欢迎"+businessAreaNm+"加入");
//                        }
//                        wellcomte.setText(mStrings.get(0));
//                        timerUtil.timeStart();
//                        initConvenientBanner(mBannerTop, adsTopList);
//                        mBannerTop.startTurning(4000);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void initConvenientBanner(ConvenientBanner cb, final List<AdvertisementsEntity> list) {
        if (cb==null){
            return;
        }
        CBViewHolderCreator cv = new CBViewHolderCreator<MarketImageHolder>() {
            public MarketImageHolder createHolder() {
                return new MarketImageHolder();
            }
        };
        cb.setPages(
                cv, list)
                .setPageIndicator(new int[]{R.drawable.banner_iocn_pre, R.drawable.banner_iocn_nomal})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }


    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.zhanhuili,R.id.talentli,R.id.gongxuli,R.id.newgoodsli})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
            case R.id.zhanhuili:
                startActivity(new Intent(NewMarketActivity.this, TradesShowActivity.class));
                break;
            case R.id.gongxuli:
                startActivity(new Intent(NewMarketActivity.this, GongXuActivity.class).putExtra("title", "供需"));
                break;
            case R.id.newgoodsli:
                startActivity(new Intent(NewMarketActivity.this, NoFinishActivity.class).putExtra("title", "新品"));
                break;
            case R.id.talentli:
                startActivity(new Intent(NewMarketActivity.this, NoFinishActivity.class).putExtra("title", "人才"));
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        totalPage++;
        if (totalPage>currentPage){
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showInfo(getApplicationContext(), "已加载完！", Toast.LENGTH_SHORT);
            return;
        }
        getBusinesslist(totalPage);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        totalPage=1;
        mEntities.clear();
        getBusinesslist(totalPage);
    }
}

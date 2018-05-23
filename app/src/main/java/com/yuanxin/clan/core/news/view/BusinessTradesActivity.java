package com.yuanxin.clan.core.news.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.BusinesslistAdapter;
import com.yuanxin.clan.core.market.bean.BusinesslistEntity;
import com.yuanxin.clan.core.market.view.MarketImageHolder;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
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
 * 行业展会
 */

public class BusinessTradesActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;



    private SubscriberOnNextListener getMarketListOnNextListener;

    private List<BusinesslistEntity> adsTopList = new ArrayList<>();
    private BusinesslistAdapter mBusinesslistAdapter;

    @Override
    public int getViewLayout() {
        return R.layout.busitradeslayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        initRecyclerView();
        getBusinessTop();
    }



    private void initRecyclerView() {
        mBusinesslistAdapter = new BusinesslistAdapter(BusinessTradesActivity.this, adsTopList);
        mBusinesslistAdapter.setOnItemClickListener(new BusinesslistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent2 = new Intent(BusinessTradesActivity.this, MiniTradesListActivity.class);//有个聊天的标志
                intent2.putExtra("id",String.valueOf(adsTopList.get(position).getExIndustryId()));
                intent2.putExtra("title",adsTopList.get(position).getExIndustryNm());
                startActivity(intent2);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(mBusinesslistAdapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse

    }

    private void getBusinessTop() {
        String url = Url.getexIndustry;
        RequestParams params = new RequestParams();
        doHttpGet(url, params, new RequestCallback() {
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
                    if (object.getString("success").equals("true")) {
//                        org.json.JSONObject jsonObject = object.getJSONObject("data");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            BusinesslistEntity advertisementsEntity = new BusinesslistEntity();
                            String exIndustryNm = businessObject.getString("exIndustryNm");
                            String exIndustryLog = businessObject.getString("exIndustryLog");
                            int exIndustryId = businessObject.getInt("exIndustryId");
                            String string = Url.img_domain  + exIndustryLog + Url.imageStyle750x350;
                            advertisementsEntity.setExIndustryLog(string);
                            advertisementsEntity.setExIndustryId(exIndustryId);
                            advertisementsEntity.setExIndustryNm(exIndustryNm);
//                            if (exIndustryId == )
                            adsTopList.add(advertisementsEntity);
                        }
                        mBusinesslistAdapter.notifyDataSetChanged();
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityYuanXinFairNewRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(85)*adsTopList.size()/4+UIUtils.dip2px(30);// 控件的高强制设成20

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
        CBViewHolderCreator cv = new CBViewHolderCreator<MarketImageHolder>() {
            public MarketImageHolder createHolder() {
                return new MarketImageHolder();
            }
        };
        cb.setPages(
                cv, list)
                .setPageIndicator(new int[]{R.drawable.banner_iocn_pre, R.drawable.banner_iocn_nomal})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }


    @OnClick(R.id.activity_yuan_xin_fair_new_left_layout)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        p2rv.onFooterRefreshComplete(1);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        adsTopList.clear();
        getBusinessTop();
    }
}

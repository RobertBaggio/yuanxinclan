package com.yuanxin.clan.core.activity_groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.core.event.OutLoginByJpushEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.view.NearAndYearActivity;
import com.yuanxin.clan.core.news.view.TradesListActivity;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/3/26 0026 11:09
 */

public class ActivityGroupshome extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{


    @BindView(R.id.activity_business_district_library_left_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.activity_business_district_library_right_layout)
    LinearLayout activityBusinessDistrictLibraryRightLayout;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityBusinessDistrictLibraryRecyclerView;
    @BindView(R.id.bannerTop)
    ConvenientBanner mBannerTop;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.shanghuili)
    LinearLayout shanghuili;
    @BindView(R.id.xiehuili)
    LinearLayout xiehuili;
    @BindView(R.id.quanzili)
    LinearLayout quanzili;
    @BindView(R.id.yuanquli)
    LinearLayout yuanquli;
    @BindView(R.id.upimage)
    ImageView upimage;


    private GroupsMainAdapter adapter;
    private List<GroupsEntity> businessDistrictListEntities = new ArrayList<>();
    private More_LoadDialog mMy_loadingDialog;
    private int ab =1;
    private int pageCount ;
    List<AdvertisementsEntity> adsTopList = new ArrayList<>();


    @Override
    public int getViewLayout() {
        return R.layout.huodongquan_home;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
//        setStatusBar(getResources().getColor(R.color.businesstop));
        mMy_loadingDialog = new More_LoadDialog(ActivityGroupshome.this);
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        String city = Utils.getCity();
        if (!TextUtil.isEmpty(city))
//            activityBusinessDistrictLibraryArea.setText(city);
//        cityOne = city;

        getIndustryList();//行业id
        initRecyclerView();
        getBusinessDistrictList(false);
        getTopimage();
    }
    private void initRecyclerView() {
        adapter = new GroupsMainAdapter(ActivityGroupshome.this, businessDistrictListEntities);
        adapter.setOnItemClickListener(new GroupsMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ActivityGroupsDetail.class);//商圈详情
                intent.putExtra("id", businessDistrictListEntities.get(position).getActivityId());
                intent.putExtra("gid", businessDistrictListEntities.get(position).getGroupId());
                intent.putExtra("gname", businessDistrictListEntities.get(position).getActivityTheme());
                intent.putExtra("gimage", businessDistrictListEntities.get(position).getActivityImage());
                intent.putExtra("title", businessDistrictListEntities.get(position).getActivityTheme());
                startActivity(intent);
            }
        });

        activityBusinessDistrictLibraryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityBusinessDistrictLibraryRecyclerView.setBackgroundResource(R.color.red);
        activityBusinessDistrictLibraryRecyclerView.setAdapter(adapter);
        activityBusinessDistrictLibraryRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityBusinessDistrictLibraryRecyclerView.setNestedScrollingEnabled(false);//禁止滑动

        activityBusinessDistrictLibraryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                int totalItemCount = manager.getItemCount();
                Log.i("lgq","a=====lastVisibleItem====="+lastVisibleItem+"..totalItemCount......"+totalItemCount);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition

                    // 判断是否滚动到底部，并且是向右滚动
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
//                        if (enable)
//                            loadData();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //大于0表示，正在向右滚动;小于等于0 表示停止或向左滚动
                isSlidingToLast = dy > 0;
            }
        });

    }



    @OnClick({R.id.activity_business_district_library_left_layout,R.id.activity_business_district_library_right_layout,R.id.yuanquli,R.id.quanzili,R.id.shanghuili,
            R.id.xiehuili,R.id.upimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_district_library_left_layout:
                finish();
                break;
            case R.id.activity_business_district_library_right_layout:
                startActivity(new Intent(ActivityGroupshome.this, GroupsSosAcitvity.class));
                break;
            case R.id.yuanquli:
                Intent intent = new Intent(ActivityGroupshome.this, NearAndYearActivity.class);//有个聊天的标志
                intent.putExtra("type","y");
                startActivity(intent);
                break;
            case R.id.xiehuili:
                startActivity(new Intent(ActivityGroupshome.this, TradesListActivity.class));
                break;
            case R.id.shanghuili:
                startActivity(new Intent(ActivityGroupshome.this, GroupsClassify.class));
                break;
            case R.id.quanzili:
                Intent intent2 = new Intent(ActivityGroupshome.this, NearAndYearActivity.class);//有个聊天的标志
                intent2.putExtra("type","n");
                startActivity(intent2);
                break;
            case R.id.upimage:
                businessDistrictListEntities.clear();
                ab = 1;
                getBusinessDistrictList(true);
                break;
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OutLoginByJpushEvent outLoginEvent) {
        finish();
    }
    private void getIndustryList() {//获取行业列表
//        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, ActivityGroupshome.this));
    }



    private void getBusinessDistrictList(boolean ifupdate) {

        String url = Url.activityGroup;
        RequestParams params = new RequestParams();
        params.put("pageNumber", ab);
        params.put("appFlg", 1);
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
//                        businessDistrictListEntities.clear();
                        pageCount = object.getInt("pageCount");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        if (jsonArray.length()==0){
                            ToastUtil.showWarning(getApplicationContext(), "没有相关数据", Toast.LENGTH_SHORT);
                        }
                        businessDistrictListEntities.clear();
                        businessDistrictListEntities.addAll(FastJsonUtils.getObjectsList(object.getString("data"), GroupsEntity.class));
                        if (activityBusinessDistrictLibraryRecyclerView!=null){
                            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityBusinessDistrictLibraryRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                            linearParams.height = UIUtils.dip2px(125)*businessDistrictListEntities.size();// 控件的高强制设成20
                        }
                        adapter.notifyDataSetChanged();
//                        activityCompanyInformationDetailRecyclerView.setLayoutParams(linearParams);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog==null){
                        return;
                    }
                    mMy_loadingDialog.dismiss();
                    if (p2rv!=null){
                        p2rv.setRefreshComplete();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
                if (mMy_loadingDialog==null){
                    return;
                }
                mMy_loadingDialog.dismiss();
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
            }
        });
    }

    private void getTopimage() {

        String url = Url.advertisement;
        RequestParams params = new RequestParams();
        params.put("advertisementType", 8);
        params.put("appFlg", 1);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            String advertisementLink = businessObject.getString("advertisementLink");//商圈id

                            String advertisementImg = businessObject.getString("advertisementImg");//商圈名称
                            String image = Url.img_domain + advertisementImg+Url.imageStyle640x640;//图片

                            AdvertisementsEntity entity = new AdvertisementsEntity();
                            entity.setAdvertisementLink(advertisementLink);
                            entity.setAdvertisementImg(image);

                            adsTopList.add(entity);
                        }
                        initConvenientBanner(mBannerTop, adsTopList);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
                if (mMy_loadingDialog==null){
                    return;
                }
                mMy_loadingDialog.dismiss();
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
            }
        });
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        ab++;
        if (ab> pageCount) {
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showWarning(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT);
            return;
        }
        getBusinessDistrictList(true);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        businessDistrictListEntities.clear();
        ab = 1;
        getBusinessDistrictList(true);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String companyInputString = data.getStringExtra("companyInputString");//企业名称
//                    mbusinessAreaType = companyInputString;
                    businessDistrictListEntities.clear();
                    getBusinessDistrictList(true);
                }
                break;
            default:
        }
    }




    @Override
    protected void onResume() {
        super.onResume();
        int sc = MyShareUtil.getSharedInt("sc");
        Log.v("lgq","huilail ====="+sc);
        if (sc==1) {
            ab=1;
            businessDistrictListEntities.clear();
            getBusinessDistrictList(true);
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        MyShareUtil.sharedPint("sc",0);
    }

    private void initConvenientBanner(ConvenientBanner cb, final List<AdvertisementsEntity> list) {
        CBViewHolderCreator cv = new CBViewHolderCreator<MyBannerViewUtil>() {
            public MyBannerViewUtil createHolder() {
                return new MyBannerViewUtil();
            }
        };
        cb.setPages(
                cv, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.banner_iocn_pre, R.drawable.banner_iocn_nomal})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
//        convenientBanner.setManualPageable(false);//设置不能手动影响
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
        }catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().unregister(this);
    }
}

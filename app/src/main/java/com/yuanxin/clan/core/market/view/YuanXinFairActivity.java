package com.yuanxin.clan.core.market.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.YuanXinFairAdapter;
import com.yuanxin.clan.core.market.bean.MarketDetail;
import com.yuanxin.clan.core.market.bean.NewMarketitem;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/23.
 * 集市
 */
public class YuanXinFairActivity extends BaseActivity {
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_right_layout)
    LinearLayout activityYuanXinFairNewRightLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    //    @BindView(R.id.address_search_springview)
//    SpringView addressSearchSpringview;
    private SubscriberOnNextListener getMarketListOnNextListener;

    private List<MarketDetail> yuanXinFairNewEntities = new ArrayList<>();
    private List<NewMarketitem>  mNewMarketitems = new ArrayList<>();
    private YuanXinFairAdapter yuanXinFairNewAdapter;
    private int currentPage ;// 当前页面，从0开始计数
    private int totalPage = 0;
    private boolean  mIsRefreshing = false;
    private My_LoadingDialog mMy_loadingDialog;
    private String image1,image2,image3,image4;


    @Override
    public int getViewLayout() {
        return R.layout.activity_yuan_xin_fair;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.delete_button));
        mMy_loadingDialog = My_LoadingDialog.getInstance(YuanXinFairActivity.this);
        initOnNext();
        initRecyclerView();
    }

    private void initOnNext() {
        getMarketListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {//行业列表

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    totalPage = t.getPageCount();
                    try {
                        JSONArray dataob = new JSONArray(t.getData());
                        for (int d = 0; d < dataob.length(); d++) {
                            NewMarketitem entity = new NewMarketitem();//外层数据

                            JSONObject dataObject = dataob.getJSONObject(d);
                            int marketId = dataObject.getInt("marketId");//订单号
//                           String i=  Url.urlHost+dataObject.getString("marketImg");
                            String marketImg = Url.img_domain+ dataObject.getString("marketImg")+Url.imageStyle640x640;//订单状态 1待付款 2待收货 3 订单完成
//                            Log.v("lgq","......."+marketImg+".."+marketId+"...."+dataob.length());
                            String marketTypes = dataObject.getString("marketTypes");//总价
                            String commodity = dataObject.getString("commodity");
                            String marketNm = dataObject.getString("marketNm");
                            String city = dataObject.getString("city");
//                            Log.v("lgq","...commodity==="+commodity);

                            if (!commodity.equals("null")&&!commodity.equals("[]")) {
//                                Log.v("lgq","...commodity=333=="+commodity);
//                                JSONArray dataArray = object.getJSONArray("data");
                                JSONArray shopListArray =  dataObject.getJSONArray("commodity");
                                int leng = shopListArray.length();
//                                Log.v("lgq","...commodity=4444=="+shopListArray.length());
                                if (!TextUtil.isEmpty(shopListArray.getJSONObject(0).getString("commodityImage1"))){

                                    image1 =Url.img_domain+ shopListArray.getJSONObject(0).getString("commodityImage1")+Url.imageStyle640x640;
//                                    Log.v("lgq","...commodity=4444=="+image1);
                                    entity.setMimage1(image1);
                                }
                                if (leng>1)
                                if (!TextUtil.isEmpty(shopListArray.getJSONObject(1).getString("commodityImage1"))){

//                                    image2 =Url.urlHost+ shopListArray.getJSONObject(1).getString("commodityImage1");
                                    image2 =Url.img_domain+ shopListArray.getJSONObject(1).getString("commodityImage1")+Url.imageStyle640x640;
//                                    Log.v("lgq","...commodity=4444=="+image2);

                                    entity.setMimage2(image2);
                                }
                                if (leng>2)
                                    if (!TextUtil.isEmpty(shopListArray.getJSONObject(2).getString("commodityImage1"))){
                                        image3 =Url.img_domain+ shopListArray.getJSONObject(2).getString("commodityImage1")+Url.imageStyle640x640;
                                        entity.setMimage3(image3);
                                }
                                if (leng>3)
                                    if (!TextUtil.isEmpty(shopListArray.getJSONObject(3).getString("commodityImage1"))){
                                        image4 =Url.img_domain+ shopListArray.getJSONObject(3).getString("commodityImage1")+Url.imageStyle640x640;
                                        entity.setMimage4(image4);

                                }

                            }

                            entity.setMarketId(marketId);
                            entity.setMarketImg(marketImg);
                            entity.setMarketTypes(marketTypes);
                            entity.setCity(city);

                            entity.setMarketNm(marketNm);
                            mNewMarketitems.add(entity);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Logger.d("json 解析出错");
                    } finally {
                        if (mMy_loadingDialog != null) {
                            mMy_loadingDialog.dismiss();
                        }
                    }
//                    yuanXinFairNewEntities.addAll(FastJsonUtils.getObjectsList(t.getData(), MarketDetail.class));
                    yuanXinFairNewAdapter.notifyDataSetChanged();
                } else {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
    }

    private void initRecyclerView() {
        yuanXinFairNewAdapter = new YuanXinFairAdapter(YuanXinFairActivity.this, mNewMarketitems);
        yuanXinFairNewAdapter.setOnItemClickListener(new YuanXinFairAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), NewFairDetailActivity.class);
                intent.putExtra("marketId", mNewMarketitems.get(position).getMarketId());
                startActivity(intent);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(yuanXinFairNewAdapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse
        HttpMethods.getInstance().getMarketList(new ProgressSubscriber(getMarketListOnNextListener, YuanXinFairActivity.this), 1);
//        addressSearchSpringview.setHeader(new RotationHeader(this));
//        addressSearchSpringview.setFooter(new RotationFooter(this));
//        addressSearchSpringview.setType(SpringView.Type.OVERLAP);
//        addressSearchSpringview.setListener(new SpringView.OnFreshListener() {
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        addressSearchSpringview.onFinishFreshAndLoad();
//                    }
//                }, 1000);
//                yuanXinFairNewEntities.clear();
//                currentPage = 1;
//                HttpMethods.getInstance().getMarketList(new ProgressSubscriber(getMarketListOnNextListener, YuanXinFairActivity.this), currentPage);
//            }
//
//            @Override
//            public void onLoadmore() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        addressSearchSpringview.onFinishFreshAndLoad();
//                    }
//                }, 1000);
//                if (currentPage >= totalPage) {
//                    return;
//                }
//                currentPage += 1;
//                HttpMethods.getInstance().getMarketList(new ProgressSubscriber(getMarketListOnNextListener, YuanXinFairActivity.this), currentPage);
//
//            }
//        });
        activityYuanXinFairNewRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
//                    Log.v("Lgq","shuxing....."+lastVisibleItem);
                    if (lastVisibleItem==3){
//                        Log.v("Lgq","eeeeeeeeeeeee");
                        mNewMarketitems.clear();
                        mMy_loadingDialog.show();
                        currentPage=1;
                        HttpMethods.getInstance().getMarketList(new ProgressSubscriber(getMarketListOnNextListener, YuanXinFairActivity.this), currentPage);
                        return;
                    }

                    // 判断是否滚动到底部，并且是向右滚动
//                    Log.v("lgq","......."+totalItemCount+"....."+"....."+totalPage+"....."+"......dijige==="+lastVisibleItem);
                    if (currentPage==totalPage){
                        ToastUtil.showInfo(getApplicationContext(), "已加载完", Toast.LENGTH_SHORT);
                        return;
                    }
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
//                        if (enable)
                        mMy_loadingDialog.show();
                        currentPage++;
//                        Log.v("lgq","..........dddddd====="+"....."+currentPage);
//                            companyInformationDetailNewEntityList.clear();
//                        getWebInfo(province, city, area, edit, currentPage+1);
                        HttpMethods.getInstance().getMarketList(new ProgressSubscriber(getMarketListOnNextListener, YuanXinFairActivity.this), currentPage+1);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //大于0表示，正在向右滚动；小于等于0 表示停止或向左滚动
                isSlidingToLast = dy > 0;
            }
        });
        activityYuanXinFairNewRecyclerView.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        return mIsRefreshing;
                    }
                }
        );

    }

    @OnClick(R.id.activity_yuan_xin_fair_new_left_layout)
    public void onClick() {
        finish();
    }
}

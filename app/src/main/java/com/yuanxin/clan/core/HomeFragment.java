package com.yuanxin.clan.core;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.jaredrummler.android.widget.AnimatedSvgView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.lypeer.fcpermission.FcPermissions;
import com.lypeer.fcpermission.impl.FcPermissionsCallbacks;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.AnimatorPath.AnimatorPath;
import com.yuanxin.clan.core.AnimatorPath.PathEvaluator;
import com.yuanxin.clan.core.AnimatorPath.PathPoint;
import com.yuanxin.clan.core.activity.BusinessDistrictLibraryActivity;
import com.yuanxin.clan.core.activity.BusinessSonActivity;
import com.yuanxin.clan.core.activity.GongXuDetailActivity;
import com.yuanxin.clan.core.activity.GreetingCardWebActivity;
import com.yuanxin.clan.core.activity.HBusinessDetailwebActivity;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.activity.HomeSosActivity;
import com.yuanxin.clan.core.activity.TestActivity;
import com.yuanxin.clan.core.activity.YxServiceActivity;
import com.yuanxin.clan.core.activity_groups.ActivityGroupsDetail;
import com.yuanxin.clan.core.activity_groups.ActivityGroupshome;
import com.yuanxin.clan.core.activity_groups.GroupsEntity;
import com.yuanxin.clan.core.activity_groups.GroupsMainAdapter;
import com.yuanxin.clan.core.adapter.HomeGridViewAdapter;
import com.yuanxin.clan.core.adapter.HomePageServiceAdapter;
import com.yuanxin.clan.core.adapter.Home_ServiceAdapter;
import com.yuanxin.clan.core.adapter.YuanQuhomeAdapter;
import com.yuanxin.clan.core.adapter.indicator.CirclePageIndicator;
import com.yuanxin.clan.core.adapter.verticalHorizontalRollingview.Utils;
import com.yuanxin.clan.core.adapter.verticalRollingTextView.DataSetAdapter;
import com.yuanxin.clan.core.adapter.verticalRollingTextView.VerticalRollingTextView;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.core.entity.EpClassEntity;
import com.yuanxin.clan.core.entity.GongXuEntity;
import com.yuanxin.clan.core.entity.HomePageAnnouncementEntity;
import com.yuanxin.clan.core.entity.SmallServiceEntity;
import com.yuanxin.clan.core.event.ChangeFragmentEvent;
import com.yuanxin.clan.core.event.QiandaoEvent;
import com.yuanxin.clan.core.fillableloader.FillableLoader;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.loading.Paths;
import com.yuanxin.clan.core.loading.SVG;
import com.yuanxin.clan.core.news.adapter.NewsAdapter;
import com.yuanxin.clan.core.news.bean.NewEntity;
import com.yuanxin.clan.core.news.view.NetworkImageHolderView;
import com.yuanxin.clan.core.news.view.NewsDetailWebActivity;
import com.yuanxin.clan.core.recyclerview.HorizontalListView;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.ColorUtils;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.ScrollListener;
import com.yuanxin.clan.core.util.ScrollListenerView;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.zxing.FragmentID;
import com.yuanxin.clan.core.zxing.SubActivity;
import com.yuanxin.clan.mvp.MainApplication;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseFragment;
import com.yuanxin.clan.mvp.view.PullToRefreshView;
import com.yuanxin.clan.mvp.view.twoWayGridview.TwoWayAdapterView;
import com.zaaach.citypicker.CityPickerActivity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;

import static android.app.Activity.RESULT_OK;
import static com.yuanxin.clan.R.id.banner_midle;

/**
 * ProjectName: yuanxinclan
 * Describe: 首页类
 * Date: 2017/6/19 0019 12:49
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, FcPermissionsCallbacks ,PullToRefreshView.OnHeaderRefreshListener,ScrollListener, View.OnTouchListener {

    @BindView(R.id.window_head_left_text)
    TextView windowHeadLeftText;
    @BindView(R.id.window_head_left_layout)
    LinearLayout mWindowHeadLeftLayout;
    @BindView(R.id.window_head_right_layout)
    LinearLayout mWindowHeadRightLayout;
    @BindView(R.id.window_head_layout)
    LinearLayout mWindowHeadLayout;
    @BindView(R.id.bannerTop)
    ConvenientBanner mBannerTop;
    @BindView(R.id.saomaimage)
    ImageView saomaimage;
    @BindView(R.id.activity_one_recycler_view)
    RecyclerView activityOneRecyclerView;
    @BindView(R.id.layout)
    LinearLayout mLayout;
    @BindView(R.id.gridview)
    com.yuanxin.clan.mvp.view.twoWayGridview.TwoWayGridView mGridView;

    @BindView(R.id.homeqykimageli)
    LinearLayout homeqykimageli;
    @BindView(R.id.homeyxsqimageli)
    TextView homeyxsqimageli;
    @BindView(R.id.bussh_shanji_more)
    LinearLayout bussh_shanji_more;
    @BindView(R.id.homeqyfwimageli)
    LinearLayout homeqyfwimageli;
    @BindView(R.id.business_sh_more)
    LinearLayout business_sh_more;
    @BindView(R.id.home_zx_more)
    LinearLayout home_zx_more;


    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(banner_midle)
    ImageView greetingCard;


    @BindView(R.id.reward_appbar)
    AppBarLayout mappBarLayout;
    @BindView(R.id.slv)
    ScrollListenerView slv;

    @BindView(R.id.activity_viewpager)
    ViewPager activity_viewpager;
    @BindView(R.id.indicator)
    CirclePageIndicator indicator;

    @BindView(R.id.selectBusinessContent)
    HorizontalListView selectBusinessContent;

    @BindView(R.id.saoyisaote)
    TextView saoyisaote;
    // 公告  https://github.com/shubowen/VerticalRollingTextView
    @BindView(R.id.noticeBoard)
    VerticalRollingTextView noticeBoard;
    @BindView(R.id.fab)
    ImageView fab;

    // 热门服务radiobutton
    @BindView(R.id.hotServiceGroup)
    RadioRealButtonGroup hotServiceGroup;
    @BindView(R.id.hotServiceButton1)
    RadioRealButton hotServiceButton1;
    @BindView(R.id.hotServiceButton2)
    RadioRealButton hotServiceButton2;
    @BindView(R.id.hotServiceButton3)
    RadioRealButton hotServiceButton3;
    @BindView(R.id.hotServiceButton4)
    RadioRealButton hotServiceButton4;

    //    精选商会、协会、圈子、园区
    @BindView(R.id.selectBusinessGroup)
    RadioGroup selectBusinessGroup;

    // 活动圈推荐
    @BindView(R.id.home_activity_more)
    LinearLayout homeActivityMore;
    @BindView(R.id.activities_recycler_view)
    RecyclerView activitiesRecyclerView;
    private List<GroupsEntity> mActivitiesList = new ArrayList<GroupsEntity>();
    private GroupsMainAdapter mGroupsMainAdapter;

    @BindView(R.id.homesosli)
    LinearLayout homesosli;

    @BindView(R.id.fillableLoader)
    FillableLoader fillableLoader;
    @BindView(R.id.animated_svg)
    AnimatedSvgView animatedSvgView;

    @BindView(R.id.serviceLayout)
    CardView serviceLayout;
    @BindView(R.id.companyLayout)
    CardView companyLayout;
    @BindView(R.id.businessLayout)
    CardView businessLayout;
    @BindView(R.id.huodongLayout)
    CardView huodongLayout;

    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;

    //声明AMapLocationClient类对象
    private AMapLocationClient locationClient = null;
//    private ConvenientBanner convenientBanner;//顶部广告栏控件

    private int REQUEST_PERMISSION_SETTING = 123;
    private static final int REQUEST_CODE_PICK_CITY = 0;

    private SubscriberOnNextListener getShowAdvertisementsOnNextListener;
    private List<AdvertisementsEntity> adsLeftList;
    private List<AdvertisementsEntity> jxyqlist = new ArrayList<>();
    private List<AdvertisementsEntity> adsRightList;
    private List<AdvertisementsEntity> mHomeADEntities=new ArrayList<>();
    private List<GongXuEntity> mGongXuEntities = new ArrayList<>();
    private List<EpClassEntity> mServicetypelist = new ArrayList<>();
    private List<SmallServiceEntity> mSmallServiceEntityList = new ArrayList<>();

    private List<AdvertisementsEntity> selectShanghuiList = new ArrayList<>();
    private  List<AdvertisementsEntity> jingxshanghuilist=new ArrayList<>();
    private List<AdvertisementsEntity> jingxxiehuilist=new ArrayList<>();
    private List<AdvertisementsEntity> jingxqzlist=new ArrayList<>();

    private com.yuanxin.clan.core.adapter.ViewPagerAdapter mAdapter;
    private List<GridView> gridList = new ArrayList<>();
    public static int item_grid_num = 5;//每一页中GridView中item的数量
    public static int number_columns = 1;//gridview一行展示的数目


    private List<NewEntity> newEntityOnes = new ArrayList<>();
    private NewsAdapter mNewsAdapter;
    private int ifcomback=0;

    protected static final float FLIP_DISTANCE = 50;
    GestureDetector mDetector;
    private boolean ifdown;

    private List<HomePageAnnouncementEntity> announcementEntities = new ArrayList<>();

    private HomePageServiceAdapter homePageServiceAdapter;

    private AnimatorPath path;//声明动画集合

    // 精选商会adapter
    private YuanQuhomeAdapter mYuanQuhomeAdapter;
    private  Home_ServiceAdapter serviceAdapter ;
    private AsyncHttpClient client = new AsyncHttpClient();
    @Override
    public int getViewLayout() {
        return R.layout.home_layout_five;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        isNeedLocation();
//        initOnNext();
        initView();
//        getBannerAll();//获取所有的轮播图片
        getWebInfo();
        initNoticeBoard();
        getWebInfoBannerAll();
        getHostsj();
        getServiceType();
        String city = MyShareUtil.getSharedString("city");
        if (!TextUtil.isEmpty(city)){
            String mycity = city;
            if (mycity.contains("市")){
                mycity=mycity.substring(0,mycity.length()-1);
            }
            windowHeadLeftText.setText(mycity);
        }
//        reward_toolbar_layout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        slv.setScrollListener(this);
        slv.setOnTouchListener(this);
        p2rv.setOnTouchListener(this);

        mAdapter = new com.yuanxin.clan.core.adapter.ViewPagerAdapter();
        activity_viewpager.setAdapter(mAdapter);
        serviceAdapter = new Home_ServiceAdapter(mSmallServiceEntityList);
        mGridView.setAdapter(serviceAdapter);
        mGridView.setFocusable(false);
        mGridView.setSelector(R.drawable.kongbg);
        //圆点指示器
        indicator.setVisibility(View.VISIBLE);
        indicator.setViewPager(activity_viewpager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        updapp();
        //设置飞行路径
        setPath();
        Typeface iconfont = Typeface.createFromAsset(getContext().getAssets(), "iconfont.ttf");
        saoyisaote.setTypeface(iconfont);

        fillableLoader.setSvgPath(Paths.YXBL);
        fillableLoader.start();
        fillableLoader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillableLoader.reset();
                fillableLoader.start();
            }
        });
        setSvg(animatedSvgView, SVG.YUANXIN_LOGO);

        // 热门服务radiobutton 点击事件
        hotServiceGroup.setOnPositionChangedListener(new RadioRealButtonGroup.OnPositionChangedListener() {
            @Override
            public void onPositionChanged(RadioRealButton button, int currentPosition, int lastPosition) {
                getyxServiceMall(String.valueOf(mServicetypelist.get(currentPosition).getIndustryId()));
            }
        });

        // 活动圈详情点击
        mGroupsMainAdapter = new GroupsMainAdapter(getContext(), mActivitiesList);
        mGroupsMainAdapter.setOnItemClickListener(new GroupsMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), ActivityGroupsDetail.class);//商圈详情
                intent.putExtra("id", mActivitiesList.get(position).getActivityId());
                intent.putExtra("gid", mActivitiesList.get(position).getGroupId());
                intent.putExtra("gname", mActivitiesList.get(position).getActivityTheme());
                intent.putExtra("gimage", mActivitiesList.get(position).getActivityImage());
                intent.putExtra("title", mActivitiesList.get(position).getActivityTheme());
                startActivity(intent);
            }
        });
        activitiesRecyclerView.setAdapter(mGroupsMainAdapter);
        activitiesRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        activitiesRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activitiesRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activitiesRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getContext())
                        .color(getResources().getColor(R.color.gray_b7))
                        .sizeResId(R.dimen.divideWidth)
                        .marginResId(R.dimen.divider_margin_left, R.dimen.divider_margin_right)
                        .build());
        GetSelectGroups(com.yuanxin.clan.core.util.Utils.getCity());
    }

//    获取活动圈推荐
    private void GetSelectGroups (String city) {
        String url = Url.homePageSelectActivities;
        RequestParams params = new RequestParams();
//        根据定位城市推荐活动
        params.put("city", city);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toasty.error(getActivity(), "网络连接异常", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        mActivitiesList.clear();
                        mActivitiesList.addAll(FastJsonUtils.getObjectsList(object.getString("data"), GroupsEntity.class));
                        mGroupsMainAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getActivity(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.d("json 解析出错");
                }
            }
        });
    }
    private void setSvg(final AnimatedSvgView animatedSvgView, SVG svg) {
        animatedSvgView.setGlyphStrings(svg.glyphs);
        animatedSvgView.setFillColors(svg.colors);
        animatedSvgView.setViewportSize(svg.width, svg.height);
        animatedSvgView.setTraceResidueColor(0x32000000);
        animatedSvgView.setTraceColors(svg.colors);
        animatedSvgView.rebuildGlyphData();
        animatedSvgView.start();
        animatedSvgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animatedSvgView.getState() == AnimatedSvgView.STATE_FINISHED)
                {
                    animatedSvgView.start();
                }
            }
        });
    }
    /*设置动画路径*/
    public void setPath(){
        path = new AnimatorPath();
        path.moveTo(0,0);
        path.lineTo(250,0);
//        path.secondBesselCurveTo(600, 200, 800, 400); //订单
        path.thirdBesselCurveTo(550, 0, 450, -300, 600, -300);
        path.lineTo(Utils.getWindowWidth((Activity) getContext()),-300);
    }

    /**
     * 设置动画
     * @param view 使用动画的View
     * @param propertyName 属性名字
     * @param path 动画路径集合
     */
    private void startAnimatorPath(View view, String propertyName, AnimatorPath path, Animator.AnimatorListener al) {
        AnimatorSet set = new AnimatorSet() ;
        ObjectAnimator anim = ObjectAnimator.ofObject(this, propertyName, new PathEvaluator(), path.getPoints().toArray());
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(this, "rotation", 0, 0, -30f, 0, 0);
        set.setInterpolator(new LinearInterpolator());
        set.setDuration(500);
        set.play(anim).with(anim1);
        set.start();
        //监听方式一 实现全部的接口
        set.addListener(al);
        // 播放声音
//        SoundUtils.playSound(getContext(), R.raw.sou);
    }

    public void setRotation(float r) {
        fab.setRotation(r);
    }
    /**
     * 设置View的属性通过ObjectAnimator.ofObject()的反射机制来调用
     * @param newLoc
     */
    public void setFab(PathPoint newLoc) {
        fab.setTranslationX(newLoc.mX);
        fab.setTranslationY(newLoc.mY);
    }

    public void resetFab() {
        fab.setTranslationX(0);
        fab.setTranslationY(0);
    }


    private void initNoticeBoard() {
        String url = Url.homePageAnnouncement;
        RequestParams params = new RequestParams();
        /*1:首页广告位 2:供需成功案例*/
        params.put("announcementType", 1);
        client.get(url, params, new TextHttpResponseHandler() {
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
                        announcementEntities.addAll(FastJsonUtils.getObjectsList(object.getString("data"), HomePageAnnouncementEntity.class));
                        //        String [] mStrs = {"喜贺东莞市湖北商会入驻圆心部落！", "企业福利，现移动官网开通，低至365", "圆心部落（1.3.12）版本已上新，更多精彩内容等着你，快快更新手中的版本吧"};
                        noticeBoard.setDataSetAdapter(new DataSetAdapter<HomePageAnnouncementEntity>(announcementEntities) {
                            @Override
                            protected String text(HomePageAnnouncementEntity s) {
                                return s.getAnnouncementTitle();
                            }
                        });
                        noticeBoard.run();
                        noticeBoard.setOnItemClickListener(new VerticalRollingTextView.OnItemClickListener() {
                            @Override
                            public void onItemClick(VerticalRollingTextView view, int index) {
                                String url = announcementEntities.get(index).getAnnouncementContent();
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

    //是否需要定位
    private void isNeedLocation() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE); //私有数据
        String city = sharedPreferences.getString("city", null);
        if (city != null) {
//            windowHeadLeftText.setText(city);
        } else {
//            initLocation();
        }
    }

    private void getWebInfoBannerAll() {//还差图片

        mHomeADEntities.clear();
        String url = Url.getadvertisement;
        RequestParams params = new RequestParams();
        params.put("city", com.yuanxin.clan.core.util.Utils.getCity());

        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showError(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int a = 0;a<array.length();a++){
                            JSONObject adObject = array.getJSONObject(a);
                            int  advertisementId = adObject.getInt("advertisementId");
                            String  updateId = adObject.getString("updateId");
                            String advertisementLink = adObject.getString("advertisementLink");
                            String advertisementNm = adObject.getString("advertisementNm");
                            int isShow = adObject.getInt("isShow");
                            int advertisementType = adObject.getInt("advertisementType");
                            String advertisementImg = adObject.getString("advertisementImg");
                            String advertisementTypeNm = adObject.getString("advertisementTypeNm");

                            AdvertisementsEntity entity = new AdvertisementsEntity();
                            entity.setAdvertisementId(advertisementId);
                            entity.setAdvertisementLink(advertisementLink);
                            entity.setIsShow(isShow);
                            entity.setAdvertisementType(advertisementType);
                            entity.setAdvertisementImg(advertisementImg);
                            entity.setAdvertisementTypeNm(advertisementTypeNm);
                            entity.setUpdateId(updateId);
                            entity.setAdvertisementNm(advertisementNm);
                            entity.setProvince(adObject.getString("province"));
                            entity.setCity(adObject.getString("city"));
                            entity.setAdvertisementFrom(adObject.getString("advertisementFrom"));
                            mHomeADEntities.add(entity);
                        }
                        showAD();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void getHostsj() {
        mHomeADEntities.clear();
        String url = Url.getsupplyDemand;
        RequestParams params = new RequestParams();
        params.put("city", com.yuanxin.clan.core.util.Utils.getCity());

        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showError(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int a = 0;a<array.length();a++){
                            JSONObject adObject = array.getJSONObject(a);
                            String  supplyDemandId = adObject.getString("supplyDemandId");
                            String content = adObject.getString("content");
                            String title = adObject.getString("title");
                            String supplyDemand = adObject.getString("supplyDemand");
                            String address = adObject.getString("address");
                            JSONObject addressobj = new JSONObject(address);
                            String city = addressobj.getString("city");

                            GongXuEntity entity = new GongXuEntity();
                            entity.setTitle(title);
                            entity.setCity(city);
                            entity.setSupplyDemandId(supplyDemandId);
                            entity.setContent(content);
                            entity.setSupplyDemand(supplyDemand);

                            mGongXuEntities.add(entity);
                        }
                        showHostsj();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void getServiceType() {

        mHomeADEntities.clear();
        String url = Url.getserviceType;
        RequestParams params = new RequestParams();

        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showError(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int a = 0;a<array.length();a++){
                            JSONObject adObject = array.getJSONObject(a);
                            int  maxTypeId = adObject.getInt("maxTypeId");
                            String typeNm = adObject.getString("typeNm");

                            EpClassEntity entity = new EpClassEntity();
                            entity.setIndustryNm(typeNm);
                            entity.setIndustryId(maxTypeId);

                            mServicetypelist.add(entity);
                        }
                        showServiceTy();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    private void getyxServiceMall(String id ) {

        mSmallServiceEntityList.clear();
        String url = Url.getyxServiceMall;
        RequestParams params = new RequestParams();
        params.put("maxTypeId",id );
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showError(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int a = 0;a<array.length();a++){
                            JSONObject adObject = array.getJSONObject(a);
//                            int  maxTypeId = adObject.getInt("maxTypeId");
                            String commodityImage1 = adObject.getString("commodityImage1");
                            String commodityNm = adObject.getString("commodityNm");
                            String minPrice = adObject.getString("minPrice");
                            String commodityId = adObject.getString("commodityId");
                            String image = Url.img_domain  + commodityImage1 + Url.imageStyle640x640;
                            SmallServiceEntity entity = new SmallServiceEntity();
                            entity.setId(commodityId);
                            entity.setName(commodityNm);
                            entity.setPrice("¥ "+minPrice);
                            entity.setImage(image);

                            mSmallServiceEntityList.add(entity);
                        }
                        showServicelist();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void showServicelist(){
        mGridView.setOnItemClickListener(new TwoWayAdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(TwoWayAdapterView<?> parent, View view, int position, long id) {
                String link = Url.urlWeb+"/serviceMall-info&shopId="+mSmallServiceEntityList.get(position).getId()+"&appFlg=1";
                if (!link.contains("http")){
                    return;
                }
                getActivity().startActivity(new Intent(getActivity(), HomeADactivity.class).putExtra("url", link));
            }
        } );
        serviceAdapter.notifyDataSetChanged();
    }


    private void getWebInfo() {//还差图片
        String url = Url.getNewsList;
        RequestParams params = new RequestParams();
        params.put("pageNumber", 1);
        params.put("showHomepage", 1);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
//                Toasty.error(getActivity(), "网络连接异常", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        if (p2rv!=null){
                            p2rv.setRefreshComplete();
                        }
//                        fragmnetTwoOneSpringview.onFinishFreshAndLoad();
                        newEntityOnes.clear();

                        newEntityOnes.addAll(JSON.parseArray(object.getString("data"), NewEntity.class));
                        mNewsAdapter.notifyDataSetChanged();

                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityOneRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(98)*6;

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void showServiceTy(){
        if (mServicetypelist.size()>0){
            hotServiceButton1.setText(mServicetypelist.get(0).getIndustryNm());
            hotServiceButton2.setText(mServicetypelist.get(1).getIndustryNm());
            hotServiceButton3.setText(mServicetypelist.get(2).getIndustryNm());
            hotServiceButton4.setText(mServicetypelist.get(3).getIndustryNm());
            getyxServiceMall(String.valueOf(mServicetypelist.get(0).getIndustryId()));
        }
    }

    public void showHostsj(){

        //计算viewpager一共显示几页
        final int pageSize = mGongXuEntities.size() % item_grid_num == 0
                ? mGongXuEntities.size() / item_grid_num
                : mGongXuEntities.size() / item_grid_num + 1;
        for (int j = 0; j < pageSize; j++) {
            GridView gridView = new GridView(getActivity());
            HomeGridViewAdapter adapter = new HomeGridViewAdapter(mGongXuEntities, j);
            gridView.setTag(j);
            gridView.setNumColumns(number_columns);
            gridView.setAdapter(adapter);
            gridList.add(gridView);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int index = Integer.parseInt(parent.getTag().toString()) * item_grid_num + position;

                    if (! UserNative.readIsLogin()){
                        toLogin();
                        return;
                    }
                    String link = Url.urlWeb+"/market-supply_demand-info&param="+mGongXuEntities.get(index).getSupplyDemandId()+"&appFlg=0";
                    if (!link.contains("http")){
                        return;
                    }
                    Intent intent = new Intent(getContext(), GongXuDetailActivity.class);//商圈详情
                    intent.putExtra("url", link);
                    intent.putExtra("title", mGongXuEntities.get(index).getTitle());
                    startActivity(intent);
                }
            });
        }
        mAdapter.add(gridList);



    }

    public void showAD(){
        try {
            List<AdvertisementsEntity> adsTopList = new ArrayList<>();
            List<AdvertisementsEntity> adsImageList = new ArrayList<>();
            jingxshanghuilist.clear();
            jingxxiehuilist.clear();
            jxyqlist.clear();
            jingxqzlist.clear();
            adsLeftList = new ArrayList<>();
            final List<AdvertisementsEntity> adsCenterList = new ArrayList<>();
            adsRightList = new ArrayList<>();
            for (int i = 0; i < mHomeADEntities.size(); i++) {
                AdvertisementsEntity ae = mHomeADEntities.get(i);
                if (ae.getAdvertisementType() == 1) {
                    //banner
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle750x350);
                    adsTopList.add(ae);
                } else if (ae.getAdvertisementType() == 2) {
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    adsImageList.add(ae);
                } else if (ae.getAdvertisementType() == 5) {
                    //企业
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    adsLeftList.add(ae);
                } else if (ae.getAdvertisementType() == 41) {
                    // 商圈推荐
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    adsCenterList.add(ae);
                } else if (ae.getAdvertisementType() == 7) {
                    //服务
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    adsRightList.add(ae);
                }else if (ae.getAdvertisementType()==4){
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    jingxshanghuilist.add(ae);
                }else if (ae.getAdvertisementType()==42){
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    jingxxiehuilist.add(ae);
                }else if (ae.getAdvertisementType()==43){
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    jingxqzlist.add(ae);
                }else if (ae.getAdvertisementType()==44){
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    jxyqlist.add(ae);
                }
            }
            initConvenientBanner(mBannerTop, adsTopList);

            // 默认商会
            selectShanghuiList.clear();
            selectShanghuiList.addAll(jingxshanghuilist);
            mYuanQuhomeAdapter = new YuanQuhomeAdapter(getContext(),selectShanghuiList);
            selectBusinessContent.setAdapter(mYuanQuhomeAdapter);
            selectBusinessContent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String link = selectShanghuiList.get(position).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                    if (! UserNative.readIsLogin()){
                        toLogin();
                        return;
                    }
                    if (!link.contains("http")){
                        return;
                    }
                    startActivity(new Intent(getContext(), HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectShanghuiList.get(position).getAdvertisementNm()));
                }
            });
            //精选商会、协会、圈子、园区
            selectBusinessGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.selectBusinessButton1:
                        default:
                            selectShanghuiList.clear();
                            selectShanghuiList.addAll(jingxshanghuilist);
                            break;
                        case R.id.selectBusinessButton2:
                            selectShanghuiList.clear();
                            selectShanghuiList.addAll(jingxxiehuilist);
                            break;
                        case R.id.selectBusinessButton3:
                            selectShanghuiList.clear();
                            selectShanghuiList.addAll(jingxqzlist);
                            break;
                        case R.id.selectBusinessButton4:
                            selectShanghuiList.clear();
                            selectShanghuiList.addAll(jxyqlist);
                            break;
                    }
                    mYuanQuhomeAdapter.notifyDataSetChanged();
                }
            });
//            homePageCompanyAdapter = new HomePageCompanyAdapter(getContext(), jxyqlist);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void startLocation() {
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void stopLocation() {
        // 停止定位
        if (locationClient != null) {
            locationClient.stopLocation();
        }
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
        }
    }



    private void updapp() {
        String url = Url.updload;
        RequestParams params = new RequestParams();
        params.put("versionCode", MainApplication.getVersionCode());//用户id
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq","upd............."+s);
//                http://www.tonixtech.com/yuanxinbuluo//upload/images/advertisement/20170509102158447.png
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        Boolean isGreetingCard = array.getBoolean("isGreetingCard");
                        String imgUrl = array.getString("greetingCardImg");
                        String cityList = array.getString("cityList");
                        MyShareUtil.sharedPstring("cityList",cityList);


                        if (isGreetingCard) {
                            greetingCard.setVisibility(View.VISIBLE);
                        } else {
                            greetingCard.setVisibility(View.GONE);
                        }
                        //测试用，正式发布隐藏
//                        greetingCard.setVisibility(View.VISIBLE);

                        ImageManager.load(getContext(), imgUrl, greetingCard);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(MySettingActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }

    private void initView() {
        mNewsAdapter = new NewsAdapter(newEntityOnes);
        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(getActivity(), NewsDetailWebActivity.class);
                intent.putExtra("news", newEntityOnes.get(position));
                intent.putExtra("one", position);
                startActivity(intent);
            }
        });
        activityOneRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        activityOneRecyclerView.setAdapter(mNewsAdapter);
        activityOneRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityOneRecyclerView.setFocusable(false);//导航栏切换不再focuse

        p2rv.setOnHeaderRefreshListener(this);
        p2rv.setEnablePullLoadMoreDataStatus(false);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
//        getBannerAll();//获取所有的轮播图片
        getWebInfoBannerAll();
        getWebInfo();
        initNoticeBoard();
        GetSelectGroups(com.yuanxin.clan.core.util.Utils.getCity());
    }

    private void initConvenientBanner(ConvenientBanner cb, final List<AdvertisementsEntity> list) {
        CBViewHolderCreator cv = new CBViewHolderCreator<NetworkImageHolderView>() {
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        };
        cb.setPages(
                cv, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.banner_iocn_pre, R.drawable.banner_iocn_nomal})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
//        mBannerTop.startAutoPlay();
//        convenientBannerLeft.startTurning(BannerConfig.TIME);
//        convenientBannerLeft.startTurning(4000);
//        convenientBannerCenter.startTurning(4000);
//        convenientBannerRight.startTurning(4000);
        mBannerTop.startTurning(8000);
//        mBannerLeft.startAutoPlay();
//        mBannerCenter.startAutoPlay();
//        mBannerRight.startAutoPlay();
        if (ifcomback==1)
            getWebInfo();
        mNewsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        ifcomback=1;
//        convenientBannerLeft.stopTurning();
//        convenientBannerCenter.stopTurning();
//        convenientBannerRight.stopTurning();
        mBannerTop.stopTurning();
//        mBannerTop.stopAutoPlay();
//        mBannerLeft.stopAutoPlay();
//        mBannerCenter.stopAutoPlay();
//        mBannerRight.stopAutoPlay();
    }

    public void sendBr(){
//        IntentFilter intentFilter = new IntentFilter("com.cj.reciver");
//        registerReceiver(new MyBroadcastReciver(),intentFilter);//这是代码动态注册的广
        getActivity().sendBroadcast(new Intent("com.cj.reciver"));//发送广播
    }

    @OnClick({R.id.homeqykimageli,R.id.window_head_right_layout, R.id.bussh_shanji_more,
            R.id.homeqyfwimageli, R.id.homeyxsqimageli, R.id.window_head_left_layout, R.id.banner_midle, R.id.service_more, R.id.home_zx_more, R.id.fab,
            R.id.business_sh_more,R.id.homesosli,R.id.news_more, R.id.serviceLayout, R.id.companyLayout, R.id.businessLayout, R.id.huodongLayout, R.id.home_activity_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.homesosli:
                startActivity(new Intent(getActivity(), HomeSosActivity.class));
                sendBr();
                break;
            case R.id.fab:
                startAnimatorPath(fab, "fab", path, new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        resetFab();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                break;
            case R.id.companyLayout:
            case R.id.homeqykimageli://企业库
                // 企业更多
            case R.id.company_more:
                startActivity(new Intent(getActivity(), TestActivity.class));
                break;
            case R.id.bussh_shanji_more:
//                startActivity(new Intent(getActivity(), YuanXinFairActivity.class));
//                startActivity(new Intent(getActivity(), NewMarketActivity.class)); //圆心集市
//                startActivity(new Intent(getActivity(), GongXuActivity.class).putExtra("title", "供需"));
                EventBus.getDefault().post(new ChangeFragmentEvent(R.id.lyMenuNews));
//                startActivity(new Intent(getActivity(), CompleteEpActivity.class));
                break;
//            case R.id.activity_one_crowd_funding://圆心众筹
//                startActivity(new Intent(getActivity(), YuanXinCrowdFundingActivity.class));//圆心众筹
//                break;
            case R.id.homeqyfwimageli://企业服务
                // 服务更多
            case R.id.service_more:
            case R.id.serviceLayout:
                startActivity(new Intent(getActivity(), YxServiceActivity.class).putExtra("url", Url.urlWebShopStore));
                break;
//            case R.id.activity_one_think_tank://智囊团
//                Toast.makeText(getActivity(), "模块开发中", Toast.LENGTH_SHORT).show();
////                startActivity(new Intent(getActivity(), ThinkTankActiivty.class));//开发中
//                break;
//            case R.id.activity_one_present://礼品定制
////                Toast.makeText(getActivity(), "模块开发中", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(getActivity(), PresentCustomMadeActivity.class));
//                break;
            case R.id.homeyxsqimageli:////展会
            case R.id.huodongLayout:
                // 活动圈更多按钮
            case R.id.home_activity_more:
                startActivity(new Intent(getActivity(), ActivityGroupshome.class));//改版后的展会
                break;
            // 商会更多
            case R.id.business_more:
            case R.id.businessLayout:
                startActivity(new Intent(getActivity(), BusinessDistrictLibraryActivity.class));////圆心商圈
//                startActivity(new Intent(getActivity(), TradesShowActivity.class));//改版后的展会

                break;
            case R.id.window_head_left_layout:
//                initLocation();
                startActivityForResult(new Intent(getContext(), CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
                break;
            case R.id.window_head_right_layout://扫码
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Bundle bundle1 = new Bundle();
                bundle1.putInt(FragmentID.ID, 1);
                Intent intent = new Intent();
                intent.setClass(getActivity(), SubActivity.class);
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
            case banner_midle:
                // 跳转贺卡
                String eid =UserNative.getEpId();
                if (!TextUtil.isEmpty(eid)){
                    //http://192.168.1.102/yuanxinbuluo/weixin/getJsp?url=wechatweb/yxGreetingCards&epId=936&appFlg=0&userNm=!E5!AD!99!E5!85!89!E7!A3!8A
                    String name = UserNative.getName();
                    try {
                        name = URLEncoder.encode(name, "utf-8").replace("%", "!");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    startActivity(new Intent(getActivity(), GreetingCardWebActivity.class).putExtra("url", Url.urlWeb + "/yxGreetingCards&appFlg=0" + "&epId=" + UserNative.getEpId() + "&userNm=" + name));
                }else {
                    ToastUtil.showInfo(getContext(), "企业用户方可发送贺卡，请完善企业资料！", Toast.LENGTH_SHORT);
                }
                break;
            case R.id.home_zx_more:
            case R.id.news_more:
                Bundle bundle = new Bundle();
                bundle.putInt(FragmentID.ID, 2);
                Intent intent1 = new Intent();
                intent1.setClass(getActivity(), SubActivity.class);
                intent1.putExtras(bundle);
                startActivity(intent1);
                break;
            case R.id.business_sh_more:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                switch (selectBusinessGroup.getCheckedRadioButtonId()) {
                    case R.id.selectBusinessButton1:
                    default:
                        startActivity(new Intent(getActivity(), BusinessSonActivity.class).putExtra("title", "商会"));
                        break;
                    case R.id.selectBusinessButton2:
                        startActivity(new Intent(getActivity(), BusinessSonActivity.class).putExtra("title", "协会"));
                        break;
                    case R.id.selectBusinessButton3:
                        startActivity(new Intent(getActivity(), BusinessSonActivity.class).putExtra("title", "圈子"));
                        break;
                    case R.id.selectBusinessButton4:
                        startActivity(new Intent(getActivity(), BusinessSonActivity.class).putExtra("title", "园区"));
                        break;
                }
                break;
        }
    }

    //二维码回掉
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK){
            if (data != null){
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                MyShareUtil.sharedPstring("city",city);
//                if (!city.equals("全国")) {
//                    city += "市";
//                }
                if (!TextUtil.isEmpty(city)){
                    String mycity = city;
                    if (mycity.contains("市")){
                        mycity=mycity.substring(0,mycity.length()-1);
                    }
                    windowHeadLeftText.setText(mycity);
                }
//                windowHeadLeftText.setText(mycity);
                MyShareUtil.sharedPstring("city",city);
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE); //私有数据
                SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                editor.putString("city", city);
                editor.commit();//提交修改
                getWebInfoBannerAll();
            }
        } else {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    ToastUtil.showWarning(getActivity(), "Cancelled", Toast.LENGTH_LONG);
                } else {
//                activityLoginQrCode.setText(result.getContents());//把结果放在View上
                    String textResult = result.getContents();
                    ToastUtil.showInfo(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_LONG);
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }


    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
        mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
        mOption.setMockEnable(false);//设置是否允许模拟位置,默认为false，不允许模拟位置
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (null != amapLocation) {
                if (amapLocation.getErrorCode() == 0) {
                    String province = amapLocation.getProvince();//省信息
                    String city = amapLocation.getCity();//城市信息
                    city = city.replace(city.charAt(city.length() - 1) + "", "");
//                    windowHeadLeftText.setText(city);
                    String district = amapLocation.getDistrict();//城区信息
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("location", Context.MODE_PRIVATE); //私有数据
                    SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                    editor.putString("province", province);
                    editor.putString("city", city);
                    editor.putString("district", district);
                    editor.commit();//提交修改
                }
//可在其中解析amapLocation获取相应内容。
            } else {
                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                ToastUtil.showWarning(getActivity(), "定位失败", Toast.LENGTH_SHORT);
            }
        }
    };


    public void onDestroy() {
        super.onDestroy();
        stopLocation();//停止定位后，本地定位服务并不会被销毁
        destroyLocation();//销毁定位
        client.cancelRequests(this.getContext(), true);
        try{
            noticeBoard.stop();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FcPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int i, List<String> list) {//当申请的权限被同意之后会调用这个方法，requestCode为请求码，perms为申请同意的权限列表。
//        initLocation();
    }

    @Override
    public void onPermissionsDenied(int i, List<String> list) {//当申请的权限被拒绝之后会调用这个方法，requestCode为请求码，perms为被拒绝的权限列表。
        ToastUtil.showInfo(getActivity(), getString(R.string.prompt_been_denied), Toast.LENGTH_LONG);
        FcPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.prompt_we_need),
                R.string.setting, R.string.cancel, null, list);
    }

    @Override
    public void onScrollChanged(ScrollListenerView scrollView, int x, int h, int oldX, int oldY) {
//        Log.v("lgq","ssss====  "+h+"......."+ifdown);

        if (h > 0) {
            mWindowHeadLayout.setBackgroundResource(R.color.white);

//            getActivity().getWindow().setStatusBarColor(this.getResources().getColor(R.color.huan_xin_title));
//            setStatusBar(this.getResources().getColor(R.color.my_info_green));
        }
        float f = (h + 0f) / 350;
        if (f > 1) {
            f = 1f;
        }
        if (f < 0) {
            f = 0;
        }
//                headLayout.setAlpha(f*1);chat_icon_sao
//        setStatusBar(ColorUtils.changeAlpha(ContextCompat.getColor(getActivity(), R.color.transparent),(int)(f * 1 * 0xff)));
        mWindowHeadLayout.setBackgroundColor(ColorUtils.changeAlpha(ContextCompat.getColor(getActivity(), R.color.white),(int)(f * 1 * 0xff)));
        if (h<130){
            windowHeadLeftText.setTextColor(getResources().getColor(R.color.white));
            saoyisaote.setTextColor(getResources().getColor(R.color.white));
            saomaimage.setImageResource(R.drawable.index_saowi);
        }else {
            saomaimage.setImageResource(R.drawable.index_sao);
            windowHeadLeftText.setTextColor(ColorUtils.changeAlpha(ContextCompat.getColor(getActivity(), R.color.login_black),(int)(f * 1 * 0xff)));
            saoyisaote.setTextColor(ColorUtils.changeAlpha(ContextCompat.getColor(getActivity(), R.color.login_black),(int)(f * 1 * 0xff)));
        }

    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //继承了Activity的onTouchEvent方法，直接监听点击事件
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //当手指按下的时候
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE) {
            //当手指离开的时候
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > 50) {
                ifdown =false;
//                Toast.makeText(MyServiceTestActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 > 50) {
                ifdown=true;
//                Toast.makeText(MyServiceTestActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
            } else if(x1 - x2 > 50) {
//                Toast.makeText(MyServiceTestActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
            } else if(x2 - x1 > 50) {
//                Toast.makeText(MyServiceTestActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        resetFab();
    }

    //新的卖家订单消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(QiandaoEvent qiandaoEvent) {
        qiandaoDialog(qiandaoEvent.getImgUrl());
    }

    //签到弹框
    public void qiandaoDialog(String imgUrl) {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.qiandao_status, null);
        dialog = new Dialog(getContext(), R.style.WhiteDialog);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

        lp.width = UIUtils.dip2px(301); // 宽度
        lp.height = UIUtils.dip2px(247); // 高度
//        lp.alpha = 0.3f; // 透明度

        dialogWindow.setAttributes(lp);

        ImageView imageView = (ImageView)view.findViewById(R.id.qiandao_image);

        ImageManager.loadBitmap(getContext(), imgUrl, R.drawable.bg_imgge, imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

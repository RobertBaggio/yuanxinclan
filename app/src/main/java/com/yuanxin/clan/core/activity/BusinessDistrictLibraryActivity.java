package com.yuanxin.clan.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.BusinessDistrictLibraryAdapter;
import com.yuanxin.clan.core.adapter.YuanQuhomeAdapter;
import com.yuanxin.clan.core.adapter.verticalRollingTextView.DataSetAdapter;
import com.yuanxin.clan.core.adapter.verticalRollingTextView.VerticalRollingTextView;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.entity.BusinessDistrictListEntity;
import com.yuanxin.clan.core.event.OutLoginByJpushEvent;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.view.BusinessImageHolder;
import com.yuanxin.clan.core.recyclerview.HorizontalListView;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

/**
 * Created by lenovo1 on 2017/2/24.
 * 圆心商圈类
 */
public class BusinessDistrictLibraryActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{


    @BindView(R.id.activity_business_district_library_left_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.activity_business_district_library_right_layout)
    LinearLayout activityBusinessDistrictLibraryRightLayout;
    @BindView(R.id.activity_business_district_library_area)
    TextView activityBusinessDistrictLibraryArea;
    @BindView(R.id.activity_business_district_library_shangxi)
    TextView activityBusinessDistrictLishangxi;
    @BindView(R.id.activity_business_district_library_industry)
    TextView activityBusinessDistrictLibraryIndustry;
    @BindView(R.id.noticeBoard)
    VerticalRollingTextView noticeBoard;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityBusinessDistrictLibraryRecyclerView;
    @BindView(R.id.bannerTop)
    ConvenientBanner mBannerTop;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;

    //精选协会
    @BindView(R.id.xiehui)
    HorizontalListView selectXiehui;

    @BindView(R.id.business1)
    LinearLayout business1;
    @BindView(R.id.businessImage1)
    ImageView businessImage1;
    @BindView(R.id.businessImageBg1)
    TextView businessImageBg1;
    @BindView(R.id.businessName1)
    TextView businessName1;
    @BindView(R.id.business2)
    LinearLayout business2;
    @BindView(R.id.businessImage2)
    ImageView businessImage2;
    @BindView(R.id.businessImageBg2)
    TextView businessImageBg2;
    @BindView(R.id.businessName2)
    TextView businessName2;
    @BindView(R.id.business3)
    LinearLayout business3;
    @BindView(R.id.businessImage3)
    ImageView businessImage3;
    @BindView(R.id.businessImageBg3)
    TextView businessImageBg3;
    @BindView(R.id.businessName3)
    TextView businessName3;
    @BindView(R.id.business4)
    LinearLayout business4;
    @BindView(R.id.businessImage4)
    ImageView businessImage4;
    @BindView(R.id.businessImageBg4)
    TextView businessImageBg4;
    @BindView(R.id.businessName4)
    TextView businessName4;

    //精选圈子
    @BindView(R.id.selectQuanzi1)
    RelativeLayout selectQuanzi1;
    @BindView(R.id.selectQuanziImage1)
    ImageView selectQuanziImage1;
    @BindView(R.id.selectQuanziName1)
    TextView selectQuanziName1;
    @BindView(R.id.selectQuanzi2)
    RelativeLayout selectQuanzi2;
    @BindView(R.id.selectQuanziImage2)
    ImageView selectQuanziImage2;
    @BindView(R.id.selectQuanziName2)
    TextView selectQuanziName2;
    @BindView(R.id.selectQuanzi3)
    RelativeLayout selectQuanzi3;
    @BindView(R.id.selectQuanziImage3)
    ImageView selectQuanziImage3;
    @BindView(R.id.selectQuanziName3)
    TextView selectQuanziName3;
    @BindView(R.id.selectQuanzi4)
    RelativeLayout selectQuanzi4;
    @BindView(R.id.selectQuanziImage4)
    ImageView selectQuanziImage4;
    @BindView(R.id.selectQuanziName4)
    TextView selectQuanziName4;
    //精选园区
    @BindView(R.id.selectYuanqu1)
    RelativeLayout selectYuanqu1;
    @BindView(R.id.selectYuanquImage1)
    ImageView selectYuanquImage1;
    @BindView(R.id.selectYuanquName1)
    TextView selectYuanquName1;
    @BindView(R.id.selectYuanqu2)
    RelativeLayout selectYuanqu2;
    @BindView(R.id.selectYuanquImage2)
    ImageView selectYuanquImage2;
    @BindView(R.id.selectYuanquName2)
    TextView selectYuanquName2;
    @BindView(R.id.selectYuanqu3)
    RelativeLayout selectYuanqu3;
    @BindView(R.id.selectYuanquImage3)
    ImageView selectYuanquImage3;
    @BindView(R.id.selectYuanquName3)
    TextView selectYuanquName3;
    @BindView(R.id.selectYuanqu4)
    RelativeLayout selectYuanqu4;
    @BindView(R.id.selectYuanquImage4)
    ImageView selectYuanquImage4;
    @BindView(R.id.selectYuanquName4)
    TextView selectYuanquName4;


    private BusinessDistrictLibraryAdapter adapter;
    private ArrayList<String> industryList = new ArrayList();//行业
    private SubscriberOnNextListener getIndustryListOnNextListener;//商圈列表 行业分类
    private String industryId, industryNm, provinceOne, cityOne, areaOne, epNmSearch, areaTen,aaaaa;
    private int industry_id;
    private List<BusinessDistrictListEntity> businessDistrictListEntities = new ArrayList<>();//BusinessDistrictLibraryEntity
    private String mbusinessAreaType;
    private int mScrollThreshold,lastposion;
    private More_LoadDialog mMy_loadingDialog;
    private int ab =1;
    private int pageCount ;
    private List<AdvertisementsEntity> adsTopList = new ArrayList<>();
    private List<String> mStrings=new ArrayList<>();
    private String[] imageUrls = new String[]{};
    private boolean enable;

    // 精选商会
    private List<AdvertisementsEntity> mHomeADEntities=new ArrayList<>();
    private List<AdvertisementsEntity> selectShanghuiList = new ArrayList<>();
    private List<AdvertisementsEntity> selectXiehuiList=new ArrayList<>();
    private List<AdvertisementsEntity> selectQuanziList=new ArrayList<>();
    private List<AdvertisementsEntity> selectYuanquList=new ArrayList<>();
    private YuanQuhomeAdapter mXiehuihomeAdapter;

    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public int getViewLayout() {
        return R.layout.activity_business_district_library;
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
        setStatusBar(getResources().getColor(R.color.businesstop));
        mMy_loadingDialog = new More_LoadDialog(BusinessDistrictLibraryActivity.this);
//        p2rv.setOnFooterRefreshListener(this);
        p2rv.setEnablePullLoadMoreDataStatus(false);
        p2rv.setOnHeaderRefreshListener(this);
        String city = Utils.getCity();
        if (!TextUtil.isEmpty(city))
            activityBusinessDistrictLibraryArea.setText(city);
        cityOne = city;
        initOnNext();
        getIndustryList();//行业id
        initRecyclerView();
        getBusinessDistrictList(false);
        getBusinessTop();
        getWebInfoBannerAll();
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
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray array = new JSONArray(object.getString("data"));
                        for (int a = 0; a < array.length(); a++) {
                            JSONObject adObject = array.getJSONObject(a);
                            int advertisementId = adObject.getInt("advertisementId");
                            String updateId = adObject.getString("updateId");
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

    //    展示精选商圈、协会、圈子、园区
    public void showAD(){
        try {
            selectShanghuiList.clear();
            selectXiehuiList.clear();
            selectQuanziList.clear();
            selectYuanquList.clear();
            for (int i = 0; i < mHomeADEntities.size(); i++) {
                AdvertisementsEntity ae = mHomeADEntities.get(i);
                if (ae.getAdvertisementType() == 4){
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    selectShanghuiList.add(ae);
                }else if (ae.getAdvertisementType() == 42){
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    selectXiehuiList.add(ae);
                }else if (ae.getAdvertisementType()==43){
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    selectQuanziList.add(ae);
                }else if (ae.getAdvertisementType()==44){
                    ae.setAdvertisementImg(Url.img_domain  + ae.getAdvertisementImg() + Url.imageStyle640x640);
                    selectYuanquList.add(ae);
                }
            }

            //精选商会
            if (selectShanghuiList.size() == 4){
                ImageManager.loadAndPalette(this, selectShanghuiList.get(0).getAdvertisementImg(), businessImage1, businessImageBg1);
                businessName1.setText(selectShanghuiList.get(0).getAdvertisementNm());
                ImageManager.loadAndPalette(this, selectShanghuiList.get(1).getAdvertisementImg(), businessImage2, businessImageBg2);
                businessName2.setText(selectShanghuiList.get(1).getAdvertisementNm());
                ImageManager.loadAndPalette(this, selectShanghuiList.get(2).getAdvertisementImg(), businessImage3, businessImageBg3);
                businessName3.setText(selectShanghuiList.get(2).getAdvertisementNm());
                ImageManager.loadAndPalette(this, selectShanghuiList.get(3).getAdvertisementImg(), businessImage4, businessImageBg4);
                businessName4.setText(selectShanghuiList.get(3).getAdvertisementNm());

                business1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (! UserNative.readIsLogin()){
                            toLogin();
                            return;
                        }
                        String link = selectShanghuiList.get(0).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")){
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectShanghuiList.get(0).getAdvertisementNm()));
                    }
                });
                business2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (! UserNative.readIsLogin()){
                            toLogin();
                            return;
                        }
                        String link = selectShanghuiList.get(1).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")){
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectShanghuiList.get(1).getAdvertisementNm()));
                    }
                });
                business3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (! UserNative.readIsLogin()){
                            toLogin();
                            return;
                        }
                        String link = selectShanghuiList.get(2).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")){
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectShanghuiList.get(2).getAdvertisementNm()));
                    }
                });
                business4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (! UserNative.readIsLogin()){
                            toLogin();
                            return;
                        }
                        String link = selectShanghuiList.get(3).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")){
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectShanghuiList.get(3).getAdvertisementNm()));
                    }
                });
            }
            // 精选协会
            mXiehuihomeAdapter = new YuanQuhomeAdapter(this, selectXiehuiList);
            selectXiehui.setAdapter(mXiehuihomeAdapter);
            selectXiehui.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                    startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectXiehuiList.get(position).getAdvertisementNm()));
                }
            });

            // 精选圈子
            if (selectQuanziList.size() == 4) {
                ImageManager.load(BusinessDistrictLibraryActivity.this, selectQuanziList.get(0).getAdvertisementImg(), selectQuanziImage1);
                ImageManager.load(BusinessDistrictLibraryActivity.this, selectQuanziList.get(1).getAdvertisementImg(), selectQuanziImage2);
                ImageManager.load(BusinessDistrictLibraryActivity.this, selectQuanziList.get(2).getAdvertisementImg(), selectQuanziImage3);
                ImageManager.load(BusinessDistrictLibraryActivity.this, selectQuanziList.get(3).getAdvertisementImg(), selectQuanziImage4);
                selectQuanziName1.setText(selectQuanziList.get(0).getAdvertisementNm());
                selectQuanziName2.setText(selectQuanziList.get(1).getAdvertisementNm());
                selectQuanziName3.setText(selectQuanziList.get(2).getAdvertisementNm());
                selectQuanziName4.setText(selectQuanziList.get(3).getAdvertisementNm());
                selectQuanzi1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserNative.readIsLogin()) {
                            toLogin();
                            return;
                        }
                        String link = selectQuanziList.get(0).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")) {
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectQuanziList.get(0).getAdvertisementNm()));
                    }
                });
                selectQuanzi2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserNative.readIsLogin()) {
                            toLogin();
                            return;
                        }
                        String link = selectQuanziList.get(1).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")) {
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectQuanziList.get(1).getAdvertisementNm()));
                    }
                });
                selectQuanzi3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserNative.readIsLogin()) {
                            toLogin();
                            return;
                        }
                        String link = selectQuanziList.get(2).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")) {
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectQuanziList.get(2).getAdvertisementNm()));
                    }
                });
                selectQuanzi4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserNative.readIsLogin()) {
                            toLogin();
                            return;
                        }
                        String link = selectQuanziList.get(3).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")) {
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectQuanziList.get(3).getAdvertisementNm()));
                    }
                });
            }
            //精选园区
            try {
                ImageManager.load(BusinessDistrictLibraryActivity.this, selectYuanquList.get(0).getAdvertisementImg(), selectYuanquImage1);
                selectYuanquName1.setText(selectYuanquList.get(0).getAdvertisementNm());
                selectYuanqu1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserNative.readIsLogin()) {
                            toLogin();
                            return;
                        }
                        String link = selectYuanquList.get(0).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")) {
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectYuanquList.get(0).getAdvertisementNm()));
                    }
                });
            } catch (Exception e) {
                selectYuanqu1.setVisibility(View.GONE);
            }
            try {
                ImageManager.load(BusinessDistrictLibraryActivity.this, selectYuanquList.get(1).getAdvertisementImg(), selectYuanquImage2);
                selectYuanquName2.setText(selectYuanquList.get(1).getAdvertisementNm());
                selectYuanqu2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserNative.readIsLogin()) {
                            toLogin();
                            return;
                        }
                        String link = selectYuanquList.get(1).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")) {
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectYuanquList.get(1).getAdvertisementNm()));
                    }
                });
            } catch (Exception e) {
                selectYuanqu2.setVisibility(View.GONE);
            }
            try {
                ImageManager.load(BusinessDistrictLibraryActivity.this, selectYuanquList.get(2).getAdvertisementImg(), selectYuanquImage3);
                selectYuanquName3.setText(selectYuanquList.get(2).getAdvertisementNm());
                selectYuanqu3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserNative.readIsLogin()) {
                            toLogin();
                            return;
                        }
                        String link = selectYuanquList.get(2).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")) {
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectYuanquList.get(2).getAdvertisementNm()));
                    }
                });
            } catch (Exception e) {
                selectYuanqu3.setVisibility(View.GONE);
            }
            try {
                ImageManager.load(BusinessDistrictLibraryActivity.this, selectYuanquList.get(3).getAdvertisementImg(), selectYuanquImage4);
                selectYuanquName4.setText(selectYuanquList.get(3).getAdvertisementNm());
                selectYuanqu4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!UserNative.readIsLogin()) {
                            toLogin();
                            return;
                        }
                        String link = selectYuanquList.get(3).getAdvertisementLink().replace("appFlg=0", "appFlg=1");
                        if (!link.contains("http")) {
                            return;
                        }
                        startActivity(new Intent(BusinessDistrictLibraryActivity.this, HBusinessDetailwebActivity.class).putExtra("url", link).putExtra("title", selectYuanquList.get(3).getAdvertisementNm()));
                    }
                });
            } catch (Exception e) {
                selectYuanqu4.setVisibility(View.GONE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView() {
        adapter = new BusinessDistrictLibraryAdapter(BusinessDistrictLibraryActivity.this, businessDistrictListEntities);
        adapter.setOnItemClickListener(new BusinessDistrictLibraryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 未登陆要求登陆
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                if (businessDistrictListEntities.get(position).getBusinessAreaGenre().equals("3")){
                    Intent intent = new Intent(getApplicationContext(), QuanziDetailwbActivity.class);//圈子详情
                    intent.putExtra("businessAreaId", businessDistrictListEntities.get(position).getBusinessAreaId());
                    intent.putExtra("intype", "qz");
                    intent.putExtra("enshrine", businessDistrictListEntities.get(position).getEnshrine());
                    intent.putExtra("title", businessDistrictListEntities.get(position).getBusinessAreaNm());
                    startActivity(intent);
                    return;

                }
                Intent intent = new Intent(getApplicationContext(), BusinessDetailWebActivity.class);//商圈详情
                intent.putExtra("businessAreaId", businessDistrictListEntities.get(position).getBusinessAreaId());
                intent.putExtra("epStyleType", businessDistrictListEntities.get(position).getEpAccessPath());
                intent.putExtra("accessPath", businessDistrictListEntities.get(position).getAccessPath());
                intent.putExtra("gid", businessDistrictListEntities.get(position).getGroupId());
                intent.putExtra("name", businessDistrictListEntities.get(position).getBusinessAreaNm());
                intent.putExtra("enshrine", businessDistrictListEntities.get(position).getEnshrine());
                Log.v("lgq","epStyleType====="+businessDistrictListEntities.get(position).getEpAccessPath());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OutLoginByJpushEvent outLoginEvent) {
        finish();
    }
    private void getIndustryList() {//获取行业列表
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, BusinessDistrictLibraryActivity.this));
    }

    private void initNoticeBoard() {
        noticeBoard.setDataSetAdapter(new DataSetAdapter<String>(mStrings) {
            @Override
            protected String text(String s) {
                return s;
            }
        });
        noticeBoard.run();
        noticeBoard.setOnItemClickListener(new VerticalRollingTextView.OnItemClickListener() {
            @Override
            public void onItemClick(VerticalRollingTextView view, int index) {
            }
        });
    }

    private void initOnNext() {
        getIndustryListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {//行业列表

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<IndustryListEntity> indutryEntityList = new ArrayList<IndustryListEntity>();//行业id 名称 还有很多
                    indutryEntityList.addAll(FastJsonUtils.getObjectsList(t.getData(), IndustryListEntity.class));
                    for (int i = 0; i < indutryEntityList.size(); i++) {
                        industryId = String.valueOf(indutryEntityList.get(i).getIndustryId());//把数字变成String 行业id
                        String industryNm = indutryEntityList.get(i).getIndustryNm();//行业名称
                        SharedPreferences sharedPreferences = getSharedPreferences("industryInfo", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString(industryNm, industryId);//名称 id
                        editor.putString(industryId, industryNm);//id  名称
                        editor.commit();//提交修改
                        industryList.add(industryNm);//行业id 行业名称 获取最终行业名称列表
                    }
                } else {
                    ToastUtil.showError(getApplicationContext(), "解析出错", Toast.LENGTH_SHORT);
                }
            }
        };
    }

    private void getBusinessDistrictList(boolean ifupdate) {
        MyShareUtil.sharedPint("sc",0);
        String saves = MyShareUtil.getSharedString("shangquantop");
        if (saves.length()>10&&ifupdate==false){
            if (p2rv!=null){
                p2rv.setRefreshComplete();
            }
            try{
                org.json.JSONObject object = new org.json.JSONObject(saves);
                org.json.JSONArray jsonArray = object.getJSONArray("data");
                if (jsonArray.length()==0){
                    ToastUtil.showWarning(getApplicationContext(), "没有相关数据", Toast.LENGTH_SHORT);
                }
                pageCount = object.getInt("pageCount");
                for (int b = 0; b < jsonArray.length(); b++) {
                    org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                    String businessAreaId = businessObject.getString("businessAreaId");//商圈id
                    String businessAreaGenre = businessObject.getString("businessAreaGenre");
                    String enshrine = businessObject.getString("enshrine");//商圈id
                    String businessAreaNm = businessObject.getString("businessAreaNm");//商圈名称
                    String businessAreaDetail = businessObject.getString("businessAreaDetail");//商圈名称
                    String businessAreaType = businessObject.getString("businessAreaType");//商圈名称
                    int collectionCount = businessObject.getInt("collectionCount");
                    int memberShip = businessObject.getInt("memberShip");
                    String groudid = businessObject.getString("groupId");
//                            String accessPath = businessObject.getString("accessPath");
                    JSONObject epView = businessObject.optJSONObject("epView");
                    String epAccessPath;//商圈名称
                    if (epView == null) {
                        epAccessPath = null;
                    } else {
                        epAccessPath = epView.getString("epAccessPath");
                    }
                    String baImage1 = businessObject.getString("baImage1");//商圈名称
                    String image = Url.img_domain + baImage1+Url.imageStyle640x640;//图片
                    String address1 = businessObject.getString("address");
                    String city="";
                    String industryNm="";
                    if (!address1.equals("null")) {
                        JSONObject addressObject = businessObject.getJSONObject("address");
                        areaTen = addressObject.getString("area");//地区
                        city = addressObject.getString("city");
                    }
                    String ind = businessObject.getString("industry");
                    if (!ind.equals("null")){
                        JSONObject industry = businessObject.getJSONObject("industry");
                        industryNm=industry.getString("industryNm");
                    }

                    BusinessDistrictListEntity entity = new BusinessDistrictListEntity();
                    entity.setBusinessAreaGenre(businessAreaGenre);
                    entity.setImage(image);
                    entity.setArea(areaTen);
                    entity.setBusinessAreaType(businessAreaType);
                    entity.setBusinessAreaNm(businessAreaNm);
                    entity.setBusinessAreaId(businessAreaId);
                    entity.setEpAccessPath(epAccessPath);
                    entity.setGroupId(groudid);
                    entity.setCity(city);
                    entity.setIndustryNm(industryNm);
                    entity.setCollectionCount(collectionCount);
                    entity.setMemberShip(memberShip);
                    entity.setEnshrine(enshrine);
                    entity.setBusinessAreaDetail(businessAreaDetail);
//                            entity.setAccessPath(accessPath);
                    businessDistrictListEntities.add(entity);
                }
                if (activityBusinessDistrictLibraryRecyclerView!=null){
                    LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityBusinessDistrictLibraryRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                    linearParams.height = UIUtils.dip2px(88)*businessDistrictListEntities.size();// 控件的高强制设成20
                }
                adapter.notifyDataSetChanged();

            }catch (JSONException e){
                e.printStackTrace();
            }
            return;
        }
        String url = Url.getBusinessList;
        RequestParams params = new RequestParams();
        mMy_loadingDialog.show();
        if (!TextUtil.isEmpty(cityOne)&&cityOne.equals("全国")){
            cityOne= "";
            provinceOne="";
        }else {
            params.put("city", cityOne);//用户id

        }
        params.put("province", provinceOne);//用户id
        params.put("userId", UserNative.getId());//用户id
        params.put("area", areaOne);//类型（1.我创建的 2.我加入的）
        params.put("industryNm", industryNm);//行业String
        params.put("businessAreaNm", mbusinessAreaType);//商圈名字（模糊搜索）
        if (!TextUtil.isEmpty(epNmSearch)) {
            params.put("status", 1);
            params.put("businessAreaType", epNmSearch);//商会String（模糊搜索）
        }
        params.put("pageNumber", ab);
//        params.put("business_area_detail", epNmSearch);//商圈描述（模糊搜索）
//        Log.v("Lgq","..........."+cityOne+"........."+industry_id+">......"+epNmSearch+"...."+areaOne+"....."+provinceOne);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                try {
                    if (ab==1){
                        MyShareUtil.sharedPstring("shangquantop",s);
                    }
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
//                        businessDistrictListEntities.clear();
                        pageCount = object.getInt("pageCount");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        if (jsonArray.length()==0){
                            ToastUtil.showWarning(getApplicationContext(), "没有相关数据", Toast.LENGTH_SHORT);
                        }
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            String businessAreaId = businessObject.getString("businessAreaId");//商圈id
                            String businessAreaGenre = businessObject.getString("businessAreaGenre");
                            String enshrine = businessObject.getString("enshrine");//商圈id
                            String businessAreaNm = businessObject.getString("businessAreaNm");//商圈名称
                            String businessAreaDetail = businessObject.getString("businessAreaDetail");//商圈名称
                            String businessAreaType = businessObject.getString("businessAreaType");//商圈名称
                            int collectionCount = businessObject.getInt("collectionCount");
                            int memberShip = businessObject.getInt("memberShip");
                            String groudid = businessObject.getString("groupId");
//                            String accessPath = businessObject.getString("accessPath");
                            JSONObject epView = businessObject.optJSONObject("epView");
                            String epAccessPath;//商圈名称
                            if (epView == null) {
                                epAccessPath = null;
                            } else {
                                epAccessPath = epView.getString("epAccessPath");
                            }
                            String baImage1 = businessObject.getString("baImage1");//商圈名称
                            String image = Url.img_domain + baImage1+Url.imageStyle640x640;//图片
                            String address1 = businessObject.getString("address");
                            String city="";
                            String industryNm="";
                            if (!address1.equals("null")) {
                                JSONObject addressObject = businessObject.getJSONObject("address");
                                areaTen = addressObject.getString("area");//地区
                                city = addressObject.getString("city");
                            }
                            String ind = businessObject.getString("industry");
                            if (!ind.equals("null")){
                                JSONObject industry = businessObject.getJSONObject("industry");
                                industryNm=industry.getString("industryNm");
                            }

                            BusinessDistrictListEntity entity = new BusinessDistrictListEntity();
                            entity.setBusinessAreaGenre(businessAreaGenre);
                            entity.setImage(image);
                            entity.setArea(areaTen);
                            entity.setBusinessAreaType(businessAreaType);
                            entity.setBusinessAreaNm(businessAreaNm);
                            entity.setBusinessAreaId(businessAreaId);
                            entity.setEpAccessPath(epAccessPath);
                            entity.setGroupId(groudid);
                            entity.setCity(city);
                            entity.setIndustryNm(industryNm);
                            entity.setCollectionCount(collectionCount);
                            entity.setMemberShip(memberShip);
                            entity.setEnshrine(enshrine);
                            entity.setBusinessAreaDetail(businessAreaDetail);
//                            entity.setAccessPath(accessPath);
                            businessDistrictListEntities.add(entity);
                        }
                        if (activityBusinessDistrictLibraryRecyclerView!=null){
                            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityBusinessDistrictLibraryRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                            linearParams.height = UIUtils.dip2px(88)*businessDistrictListEntities.size();// 控件的高强制设成20
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
    private void getBusinessTop() {
        String url = Url.newBusinessArea;
        RequestParams params = new RequestParams();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        org.json.JSONObject jsonObject = object.getJSONObject("data");
                        org.json.JSONArray jsonArray = jsonObject.getJSONArray("advertisement");
                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            AdvertisementsEntity advertisementsEntity = new AdvertisementsEntity();
                            String advertisementImg = businessObject.getString("advertisementImg");//商圈id
                            String string = Url.img_domain  + advertisementImg + Url.imageStyle750x350;
                            advertisementsEntity.setAdvertisementImg(string);
                            adsTopList.add(advertisementsEntity);
                        }
//                        businessArea

                        org.json.JSONArray businessArea = jsonObject.getJSONArray("businessArea");
                        for (int b = 0; b < businessArea.length(); b++) {
                            org.json.JSONObject businessObject = businessArea.getJSONObject(b);
                            String businessAreaNm = businessObject.getString("businessAreaNm");//商圈id
                            mStrings.add("欢迎"+businessAreaNm+"加入");
                        }
                        initConvenientBanner(mBannerTop, adsTopList);
                        if (mBannerTop==null){
                            return;
                        }
                        mBannerTop.startTurning(4000);
                        initNoticeBoard();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.d("json 解析出错");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
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
        MyShareUtil.sharedPstring("shangquantop","0");
        getBusinessDistrictList(true);
    }
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    provinceOne = province.getAreaName();
                    cityOne = city.getAreaName();
                    activityBusinessDistrictLibraryArea.setText(city.getAreaName());//市
                    String industryId = activityBusinessDistrictLibraryIndustry.getText().toString().trim();//获取行业
                    if (industryId.equals("行业")) {
                        industry_id = 0;
                    } else {
                        SharedPreferences share = getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
                        String industry = share.getString(industryId, "");//名称 id
                        industry_id = Integer.parseInt(industry);//把 String id 转化为 int
                    }
                    industry_id = 0;
                    activityBusinessDistrictLibraryIndustry.setText("行业");
                    activityBusinessDistrictLishangxi.setText("商会");
                    industryNm="";
                    epNmSearch="";
                    mbusinessAreaType="";
                    businessDistrictListEntities.clear();
                    getBusinessDistrictList(true);
                } else {
                    provinceOne = province.getAreaName();
                    cityOne = city.getAreaName();
                    areaOne = county.getAreaName();
                    activityBusinessDistrictLibraryArea.setText(county.getAreaName());
                    businessDistrictListEntities.clear();
                    getBusinessDistrictList(true);
                }
            }
        });
        task.execute("广东", "东莞", "东城");
    }

    public void onConstellationPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        OptionPicker picker = new OptionPicker(this, industryList);//行业列表名称
        picker.setCycleDisable(true);//不禁用循环
        picker.setTopBackgroundColor(0xFFEEEEEE);//头部背景
        picker.setTopHeight(50);
        picker.setTopLineColor(0xFF33B5E5);//中间分割线
        picker.setTopLineHeight(1);
        picker.setTitleText(isChinese ? "请选择" : "Please pick");
        picker.setTitleTextColor(0xFF999999);//中间字颜色
        picker.setTitleTextSize(13);
        picker.setCancelTextColor(0xFF33B5E5);//取消颜色
        picker.setCancelTextSize(14);
        picker.setSubmitTextColor(0xFF33B5E5);//确定颜色
        picker.setSubmitTextSize(14);
        picker.setTextColor(0xFF33B5E5, 0xFF999999);//选中，非选中
        WheelView.LineConfig config = new WheelView.LineConfig();
        config.setColor(0xFF33B5E5);//线颜色
        config.setAlpha(140);//线透明度
        config.setRatio((float) (1.0 / 8.0));//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(0xFFE1E1E1);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        picker.setSelectedIndex(3);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                activityBusinessDistrictLibraryIndustry.setText(item);
                SharedPreferences share = getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
                String industryOne = share.getString(item, "");//名称 获取id
                industryNm = item;
                industry_id = Integer.parseInt(industryOne);//id string 转int 行业 参数
                activityBusinessDistrictLishangxi.setText("商会");
                epNmSearch="";
                cityOne="全国";
                activityBusinessDistrictLibraryArea.setText("地区");
                Log.v("lgq","llllllll选择结果==="+industryOne);
                businessDistrictListEntities.clear();
                getBusinessDistrictList(true);
            }
        });
        picker.show();
    }

    public void onShangxiPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        ArrayList<String> shangxiList = new ArrayList();//行业
        shangxiList.add("莞系商会");
        shangxiList.add("潮系商会");
        shangxiList.add("浙系商会");
        shangxiList.add("晋系商会");
        shangxiList.add("豫系商会");
        shangxiList.add("卾系商会");
        shangxiList.add("沪系商会");
        shangxiList.add("惠系商会");
        OptionPicker picker = new OptionPicker(this, shangxiList);//行业列表名称
        picker.setCycleDisable(true);//不禁用循环
        picker.setTopBackgroundColor(0xFFEEEEEE);//头部背景
        picker.setTopHeight(50);
        picker.setTopLineColor(0xFF33B5E5);//中间分割线
        picker.setTopLineHeight(1);
        picker.setTitleText(isChinese ? "请选择" : "Please pick");
        picker.setTitleTextColor(0xFF999999);//中间字颜色
        picker.setTitleTextSize(13);
        picker.setCancelTextColor(0xFF33B5E5);//取消颜色
        picker.setCancelTextSize(14);
        picker.setSubmitTextColor(0xFF33B5E5);//确定颜色
        picker.setSubmitTextSize(14);
        picker.setTextColor(0xFF33B5E5, 0xFF999999);//选中，非选中
        WheelView.LineConfig config = new WheelView.LineConfig();
        config.setColor(0xFF33B5E5);//线颜色
        config.setAlpha(140);//线透明度
        config.setRatio((float) (1.0 / 8.0));//线比率
        picker.setLineConfig(config);
        picker.setItemWidth(200);
        picker.setBackgroundColor(0xFFE1E1E1);
        //picker.setSelectedItem(isChinese ? "处女座" : "Virgo");
        picker.setSelectedIndex(3);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                activityBusinessDistrictLishangxi.setText(item);
                epNmSearch=item;
                industry_id = 0;
                cityOne="全国";
                activityBusinessDistrictLibraryArea.setText("地区");
//                SharedPreferences share = getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
//                String industryOne = share.getString(item, "");//名称 获取id
//                industry_id = Integer.parseInt(industryOne);//id string 转int 行业 参数
//                Log.v("lgq","llllllll选择结果==="+industryOne);
                businessDistrictListEntities.clear();
                getBusinessDistrictList(true);
            }
        });
        picker.show();
    }

    @OnClick({R.id.activity_business_district_library_left_layout, R.id.activity_business_district_library_right_layout,
            R.id.activity_business_district_library_area,R.id.yuanquli,R.id.quanzili,R.id.shanghuili,
            R.id.xiehuili, R.id.activity_business_district_library_industry,R.id.activity_business_district_library_shangxi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_district_library_left_layout:
                finish();
                break;
            case R.id.activity_business_district_library_right_layout:
//                Intent intent = new Intent(BusinessDistrictLibraryActivity.this, BusinessSearchActivity.class);
                Intent intent = new Intent(BusinessDistrictLibraryActivity.this, BusinessSosOneActivity.class);
                startActivityForResult(intent, 1);
                break;
            case R.id.activity_business_district_library_area:
                onAddressPicker();
                break;
            case R.id.activity_business_district_library_shangxi:
                onShangxiPicker();
                break;
            case R.id.activity_business_district_library_industry:
                if (industryList != null && industryList.size() > 0) {
                    onConstellationPicker();
                }
                break;
            case R.id.shanghuili:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(BusinessDistrictLibraryActivity.this, BusinessSonActivity.class).putExtra("title", "商会"));
                break;
            case R.id.xiehuili:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(BusinessDistrictLibraryActivity.this, BusinessSonActivity.class).putExtra("title", "协会"));
                break;
            case R.id.quanzili:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(BusinessDistrictLibraryActivity.this, BusinessSonActivity.class).putExtra("title", "圈子"));
//                startActivity(new Intent(BusinessDistrictLibraryActivity.this, XmtouzActivity.class));
                break;
            case R.id.yuanquli:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(BusinessDistrictLibraryActivity.this, BusinessSonActivity.class).putExtra("title", "园区"));
                break;
//            startActivity(new Intent(NewMarketActivity.this, TradesShowActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String companyInputString = data.getStringExtra("companyInputString");//企业名称
                    mbusinessAreaType = companyInputString;
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
        CBViewHolderCreator cv = new CBViewHolderCreator<BusinessImageHolder>() {
            public BusinessImageHolder createHolder() {
                return new BusinessImageHolder();
            }
        };
        if (cb!=null){
            cb.setPages(
                    cv, list)
                    .setPageIndicator(new int[]{R.drawable.banner_iocn_pre, R.drawable.banner_iocn_nomal})
                    .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            noticeBoard.stop();
        }catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().unregister(this);
    }
}

package com.yuanxin.clan.core.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.BusinessDistrictLibraryAdapter;
import com.yuanxin.clan.core.adapter.CommdssAdapter;
import com.yuanxin.clan.core.adapter.CompanyInformationDetailAdapter;
import com.yuanxin.clan.core.adapter.HomeSosListAdapter;
import com.yuanxin.clan.core.adapter.HomeSos_Adapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.CommodityEntity;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.entity.BusinessDistrictListEntity;
import com.yuanxin.clan.core.entity.HomeSostypeEntity;
import com.yuanxin.clan.core.entity.SmallServiceEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.MyTradesStatusAdapter;
import com.yuanxin.clan.core.market.bean.TradesEntity;
import com.yuanxin.clan.core.market.view.TradesDetailActivity;
import com.yuanxin.clan.core.news.adapter.NewsAdapter;
import com.yuanxin.clan.core.news.bean.NewEntity;
import com.yuanxin.clan.core.news.view.NewsDetailWebActivity;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;
import com.yuanxin.clan.mvp.view.twoWayGridview.TwoWayAdapterView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import es.dmoral.toasty.Toasty;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/9 0009 11:46
 */

public class HomeSosActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{

    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.mytopli)
    LinearLayout mytopli;
    @BindView(R.id.sosRecyclerView)
    RecyclerView sosRecyclerView;
    @BindView(R.id.sosnewsRecyclerView)
    RecyclerView sosnewsRecyclerView;
    @BindView(R.id.soshzRecyclerView)
    RecyclerView soshzRecyclerView;
    @BindView(R.id.sosepRecyclerView)
    RecyclerView sosepRecyclerView;
    @BindView(R.id.sosbsRecyclerView)
    RecyclerView sosbsRecyclerView;
    @BindView(R.id.onbackte)
    TextView onbackte;
    @BindView(R.id.sostypete)
    TextView sostypete;
    @BindView(R.id.homesosoedit)
    EditText homesosoedit;
    @BindView(R.id.historylistview)
    ListView historylistview;
    @BindView(R.id.login_errowimage)
    ImageView login_errowimage;
    @BindView(R.id.deleimage)
    ImageView deleimage;
    @BindView(R.id.hostsosgv)
    com.yuanxin.clan.mvp.view.twoWayGridview.TwoWayGridView hostsosgv;

    private String type="2";


    private List<SmallServiceEntity> mSmallServiceEntityList = new ArrayList<>();
    private List<HomeSostypeEntity> mHomehotypeEntities = new ArrayList<>();
    private List<HomeSostypeEntity> mHomehistoryEntities = new ArrayList<>();
    private List<CommodityEntity> mCommodityEntityList = new ArrayList<>();
    private List<CompanyDetail> companyInformationDetailNewEntityList = new ArrayList<>();
    private List<BusinessDistrictListEntity> businessDistrictListEntities = new ArrayList<>();
    private List<TradesEntity> mEntities = new ArrayList<>();
    private List<NewEntity> newEntityOnes = new ArrayList<>();

    private NewsAdapter mNewsAdapter;
    private CommdssAdapter mAdapter;
    private CompanyInformationDetailAdapter Epadapter;
    private BusinessDistrictLibraryAdapter businessadapter;
    private MyTradesStatusAdapter mMyTradesStatusAdapter;

    private HomeSos_Adapter mHotSos_adapter;
    private HomeSosListAdapter mHistorySos_adapter;
    private More_LoadDialog mMy_loadingDialog;
    private int pageContent;
    private int lastposion,pagenum=1;
    private String name;
    private int beforlong,bhlong;

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    public int getViewLayout() {
        return R.layout.home_sos_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mHotSos_adapter = new HomeSos_Adapter(mHomehotypeEntities);
        mHistorySos_adapter = new HomeSosListAdapter(mHomehistoryEntities);
        hostsosgv.setAdapter(mHotSos_adapter);
        hostsosgv.setSelector(R.drawable.kongbg);
        historylistview.setAdapter(mHistorySos_adapter);
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        mMy_loadingDialog = new More_LoadDialog(HomeSosActivity.this);
        p2rv.setEnablePullLoadMoreDataStatus(false);
        p2rv.setEnablePullTorefresh(false);
        hostsosgv.setOnItemClickListener(new TwoWayAdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(TwoWayAdapterView<?> parent, View view, int position, long id) {
                type = mHomehotypeEntities.get(position).getType();
                pagenum=1;
                homesosoedit.setText(mHomehotypeEntities.get(position).getName());
                homesosoedit.setSelection(homesosoedit.getText().length());

            }
        });
        historylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = mHomehistoryEntities.get(position).getType();
                pagenum=1;
                homesosoedit.setText(mHomehistoryEntities.get(position).getName());
                homesosoedit.setSelection(homesosoedit.getText().length());

            }
        });
        homesosoedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                beforlong = s.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bhlong = s.toString().length();
                name=s.toString();
                if (beforlong>bhlong&&!TextUtil.isEmpty(name)){
                    return;
                }
                pagenum=1;
                if (TextUtil.isEmpty(name)){
                    login_errowimage.setVisibility(View.GONE);
                    sosRecyclerView.setVisibility(View.GONE);
                    sosepRecyclerView.setVisibility(View.GONE);
                    sosbsRecyclerView.setVisibility(View.GONE);
                    soshzRecyclerView.setVisibility(View.GONE);
                    sosnewsRecyclerView.setVisibility(View.GONE);
                    mytopli.setVisibility(View.VISIBLE);
                    getHotandHistorySos();
                }else {
                    login_errowimage.setVisibility(View.VISIBLE);

                }
            }
        });
        initRecyclerView();
        getHotandHistorySos();

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            if (TextUtil.isEmpty(homesosoedit.getText().toString())){
                ToastUtil.showWarning(HomeSosActivity.this, "请输入内容！", Toast.LENGTH_SHORT);
                return false;
            }
            name = homesosoedit.getText().toString();
            if (type.equals("1")){
                mCommodityEntityList.clear();
                getServiceInfo(name);
            }else if (type.equals("2")){
                companyInformationDetailNewEntityList.clear();
                getEpSos(name);
            }else if (type.equals("5")){
                businessDistrictListEntities.clear();
                getBusinessSos(name);
            }else if (type.equals("4")){
                mEntities.clear();
                getZhanhuiSos(name);
            }else if (type.equals("3")){
                newEntityOnes.clear();
                getNewsSos(name);
            }
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @OnClick({R.id.onbackte,R.id.sostypete,R.id.login_errowimage,R.id.deleimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onbackte:
                onBackPressed();
                break;
            case R.id.sostypete:
                homesosoedit.setText("");
                onShangxiPicker();
                break;
            case R.id.login_errowimage:
                homesosoedit.setText("");
                break;
            case R.id.deleimage:
                showNormalDialogOne();
//                delehistory();
                break;

        }
    }

    private void getNewsSos(String name ) {

        String url = Url.getsearch;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pagenum);
        params.put("type","3" );
        params.put("keyword",name );
        params.put("userId", UserNative.getId());
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                Toasty.error(HomeSosActivity.this, "网络连接异常", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String datastring = object.getString("data");
                        JSONObject dataob = new JSONObject(datastring);
                        String servicestring = dataob.getString("news");
                        JSONObject serviceobj = new JSONObject(servicestring);

//                        pageContent = serviceobj.getInt("totalPages");
                        newEntityOnes.addAll(JSON.parseArray(serviceobj.getString("data"), NewEntity.class));
                        mNewsAdapter.notifyDataSetChanged();

                        if (newEntityOnes.size() == 0) {
                            Toast.makeText(getApplicationContext(), "无此相关数据", Toast.LENGTH_SHORT).show();
                            mytopli.setVisibility(View.VISIBLE);
                            sosnewsRecyclerView.setVisibility(View.GONE);
                        } else {
                            mytopli.setVisibility(View.GONE);
                            sosnewsRecyclerView.setVisibility(View.VISIBLE);
                        }

                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) sosnewsRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(98)*newEntityOnes.size();


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });

    }

    private void showNormalDialogOne() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("确定删除所有！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delehistory();//dosomething
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    private void delehistory() {

        String url = Url.getsearchdelete;
        RequestParams params = new RequestParams();
        params.put("appFlg",1 );
        params.put("userId", UserNative.getId());
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(HomeSosActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(HomeSosActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        getHotandHistorySos();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });

    }
    private void getZhanhuiSos(String name ) {
        String url = Url.getsearch;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pagenum);
        params.put("type","4" );
        params.put("keyword",name );
        params.put("userId", UserNative.getId());
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
                ToastUtil.showWarning(HomeSosActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String datastring = object.getString("data");
                        JSONObject dataob = new JSONObject(datastring);
                        String servicestring = dataob.getString("exhibition");
                        JSONObject serviceobj = new JSONObject(servicestring);
                        pageContent = serviceobj.getInt("totalPages");
                        org.json.JSONArray jsonArray = serviceobj.getJSONArray("data");

                        for (int b = 0; b < jsonArray.length(); b++) {
                            org.json.JSONObject businessObject = jsonArray.getJSONObject(b);
                            TradesEntity advertisementsEntity = new TradesEntity();
                            String exhibitionTitle = businessObject.getString("exhibitionTitle");
                            String hallNm = businessObject.getString("hallNm");
                            String status = businessObject.getString("status");
                            String businessPhone = businessObject.getString("businessPhone");
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
                            advertisementsEntity.setBusinessPhone(businessPhone);
                            mEntities.add(advertisementsEntity);
                        }

                        if (mEntities.size() == 0) {
                            ToastUtil.showInfo(getApplicationContext(), "无此相关数据", Toast.LENGTH_SHORT);
                            mytopli.setVisibility(View.VISIBLE);
                            soshzRecyclerView.setVisibility(View.GONE);
                        } else {
                            mytopli.setVisibility(View.GONE);
                            soshzRecyclerView.setVisibility(View.VISIBLE);
                        }
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) soshzRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(175)*mEntities.size();// 控件的高强制设成20
                        mMyTradesStatusAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });

    }
    private void getEpSos(String name ) {

        String url = Url.getsearch;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pagenum);
        params.put("type","2" );
        params.put("keyword",name );
        params.put("userId", UserNative.getId());
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
                ToastUtil.showWarning(HomeSosActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    String datastring = object.getString("data");
                    JSONObject dataob = new JSONObject(datastring);
                    String servicestring = dataob.getString("enterprise");
                    JSONObject serviceobj = new JSONObject(servicestring);

                    pageContent = serviceobj.getInt("totalPages");
                    if (object.getString("success").equals("true")) {
                        pageContent = serviceobj.getInt("totalPages");
                        companyInformationDetailNewEntityList.addAll(FastJsonUtils.getObjectsList(serviceobj.getString("data"), CompanyDetail.class));
//                        Log.v("lgq","多少个。。。。。。。。。"+companyInformationDetailNewEntityList.size());
                        if (companyInformationDetailNewEntityList.size() == 0) {
                            ToastUtil.showInfo(getApplicationContext(), "无此相关数据", Toast.LENGTH_SHORT);
                            mytopli.setVisibility(View.VISIBLE);
                            sosepRecyclerView.setVisibility(View.GONE);
                        } else {
                            mytopli.setVisibility(View.GONE);
                            sosepRecyclerView.setVisibility(View.VISIBLE);
                        }

                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) sosepRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(95)*companyInformationDetailNewEntityList.size();// 控件的高强制设成20
                        Epadapter.notifyDataSetChanged();
//                        showServicelist();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });

    }
    private void getBusinessSos(String name ) {

        String url = Url.getsearch;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pagenum);
        params.put("type","5" );
        params.put("keyword",name );
        params.put("userId", UserNative.getId());
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
                ToastUtil.showWarning(HomeSosActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String datastring = object.getString("data");
                        JSONObject dataob = new JSONObject(datastring);
                        String servicestring = dataob.getString("business");
                        JSONObject serviceobj = new JSONObject(servicestring);
                        pageContent = serviceobj.getInt("totalPages");
                        org.json.JSONArray jsonArray = serviceobj.getJSONArray("data");
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
                            String areaTen="";
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
                        if (businessDistrictListEntities.size() == 0) {
                            ToastUtil.showInfo(getApplicationContext(), "无此相关数据", Toast.LENGTH_SHORT);
                            mytopli.setVisibility(View.VISIBLE);
                            sosbsRecyclerView.setVisibility(View.GONE);
                        } else {
                            mytopli.setVisibility(View.GONE);
                            sosbsRecyclerView.setVisibility(View.VISIBLE);
                        }

                        if (sosbsRecyclerView!=null){
                            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) sosbsRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                            linearParams.height = UIUtils.dip2px(88)*businessDistrictListEntities.size();// 控件的高强制设成20
                        }
                        businessadapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });

    }

    private void getServiceInfo(String name) {
        String url = Url.getsearch;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pagenum);
        params.put("type","1" );
        params.put("keyword",name );
        params.put("userId", UserNative.getId());
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    String datastring = object.getString("data");
                    JSONObject dataob = new JSONObject(datastring);
                    String servicestring = dataob.getString("service");
                    JSONObject serviceobj = new JSONObject(servicestring);

                    pageContent = serviceobj.getInt("totalPages");
//                    Log.v("lgq",".....企业库。。数据。。test==="+s);
                    if (object.getString("success").equals("true")) {
                        JSONArray dataArray = serviceobj.getJSONArray("data");
                        if (dataArray.length()==0){
                            ToastUtil.showInfo(getApplicationContext(), "无此相关数据", Toast.LENGTH_SHORT);
                            mytopli.setVisibility(View.VISIBLE);
                            sosRecyclerView.setVisibility(View.GONE);
                        }else {
                            mytopli.setVisibility(View.GONE);
                            sosRecyclerView.setVisibility(View.VISIBLE);
                        }
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            String image =Url.img_domain+ dataObject.getString("commodityImage1")+Url.imageStyle640x640;
                            String name = dataObject.getString("commodityNm");
                            String detail = dataObject.getString("commodityDetail");
                            String area = dataObject.getString("area");
//                            String price = dataObject.getString("commodityPrice");
                            double max =dataObject.getDouble("maxPrice");
                            double min =dataObject.getDouble("minPrice");
                            int id= dataObject.getInt("commodityId");

                            CommodityEntity entity = new CommodityEntity();
                            entity.setCommodityDetail(detail);
                            entity.setCommodityId(id);
                            entity.setMaxPrice(max);
                            entity.setMinPrice(min);
                            entity.setCommodityImage1(image);
                            entity.setCommodityNm(name);

                            mCommodityEntityList.add(entity);

                        }

                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) sosRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(283)*mCommodityEntityList.size()/2;// 控件的高强制设成20
                        mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    private void getHotandHistorySos() {

        mHomehistoryEntities.clear();
        mHomehotypeEntities.clear();
        String url = Url.getsearchtype;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        params.put("searchClassify", "0");
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                ToastUtil.showWarning(HomeSosActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    if (p2rv!=null){
                        p2rv.setRefreshComplete();
                    }
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String datastring = object.getString("data");
                        JSONObject dataobject = new JSONObject(datastring);
                        String hotSearch =dataobject.getString("hotSearch");
                        String searchHistory =dataobject.getString("searchHistory");
                        JSONArray harray = new JSONArray(hotSearch);
                        JSONArray historyarray = new JSONArray(searchHistory);
                        for (int a = 0;a<harray.length();a++){
                            JSONObject adObject = harray.getJSONObject(a);
                            String searchHistoryId = adObject.getString("searchHistoryId");
                            String searchType = adObject.getString("searchType");
                            String searchKey = adObject.getString("searchKey");
                    ;
                            HomeSostypeEntity entity = new HomeSostypeEntity();
                            entity.setId(searchHistoryId);
                            entity.setType(searchType);
                            entity.setName(searchKey);

                            mHomehotypeEntities.add(entity);
                        }
                        mHotSos_adapter.notifyDataSetChanged();
                        for (int a = 0;a<historyarray.length();a++){
                            JSONObject adObject = historyarray.getJSONObject(a);
                            String searchHistoryId = adObject.getString("searchHistoryId");
                            String searchType = adObject.getString("searchType");
                            String searchKey = adObject.getString("searchKey");
                    ;
                            HomeSostypeEntity entity = new HomeSostypeEntity();
                            entity.setId(searchHistoryId);
                            entity.setType(searchType);
                            entity.setName(searchKey);

                            mHomehistoryEntities.add(entity);
                        }
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) historylistview.getLayoutParams(); //取控件textView当前的布局参数
                        if (mHomehistoryEntities.size()>=20){
                            linearParams.height = UIUtils.dip2px(48)*20+30;// 控件的高强制设成20
                        }else {
                            linearParams.height = UIUtils.dip2px(48)*mHomehistoryEntities.size()+30;// 控件的高强制设成20
                        }
                        mHistorySos_adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void onShangxiPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        ArrayList<String> shangxiList = new ArrayList();//行业
        shangxiList.add("服务");
        shangxiList.add("企业");
        shangxiList.add("资讯");
        shangxiList.add("会展");
        shangxiList.add("商圈");
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
        picker.setSelectedIndex(1);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                sostypete.setText(item);
                type = String.valueOf(index+1);
            }
        });
        picker.show();
    }

    public void initRecyclerView(){
        mAdapter = new CommdssAdapter(HomeSosActivity.this,mCommodityEntityList);
        mAdapter.setOnItemClickListener(new CommdssAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent1 = new Intent(HomeSosActivity.this, My_ServiceInfoActivity.class);
                intent1.putExtra("id",mCommodityEntityList.get(position).getCommodityId());
                startActivity(intent1);
            }
        });
        Epadapter = new CompanyInformationDetailAdapter(HomeSosActivity.this, companyInformationDetailNewEntityList);
        Epadapter.setOnItemClickListener(new CompanyInformationDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                //把企业id传过来 详情 第二期做
                try{
                    Intent intent = new Intent(getApplicationContext(), CompanyDetailWebActivity.class);//有个聊天的标志
                    intent.putExtra("epId", companyInformationDetailNewEntityList.get(position).getEpId());
                    //                intent.putExtra("epLinktel", companyInformationDetailNewEntityList.get(position).getEpLinktel());
                    intent.putExtra("logo", companyInformationDetailNewEntityList.get(position).getEpImage1());
                    intent.putExtra("title", companyInformationDetailNewEntityList.get(position).getEpShortNm());
                    intent.putExtra("content", companyInformationDetailNewEntityList.get(position).getEpDetail());
                    intent.putExtra("collect", companyInformationDetailNewEntityList.get(position).getEnshrine());
                    if (!(companyInformationDetailNewEntityList.get(position).getEpUser()==null))
                        intent.putExtra("uid", companyInformationDetailNewEntityList.get(position).getEpUser().getUserId());
                    intent.putExtra("accessPath", companyInformationDetailNewEntityList.get(position).getEpView().getEpAccessPath());
                    Log.v("lgq","..log==...."+companyInformationDetailNewEntityList.get(position).getEpImage1());

                    startActivity(intent);
                }catch (Exception e){
                    ToastUtil.showWarning(getApplicationContext(), "企业数据缺失", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        });


        businessadapter = new BusinessDistrictLibraryAdapter(HomeSosActivity.this, businessDistrictListEntities);
        businessadapter.setOnItemClickListener(new BusinessDistrictLibraryAdapter.OnItemClickListener() {
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

        mMyTradesStatusAdapter = new MyTradesStatusAdapter(HomeSosActivity.this, mEntities);
        mMyTradesStatusAdapter.setOnItemClickListener(new MyTradesStatusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // 未登陆要求登陆
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Intent intent = new Intent(HomeSosActivity.this, TradesDetailActivity.class);
                intent.putExtra("id", String.valueOf(mEntities.get(position).getExhibitionId()));
                intent.putExtra("name",mEntities.get(position).getHallNm());
                intent.putExtra("image",mEntities.get(position).getExhibitionTitleImg());
                String time = DateDistance.getDistanceTimeToZW(mEntities.get(position).getStarTime())+" 至 "+DateDistance.getDistanceTimeToZW(mEntities.get(position).getEndTime());
                intent.putExtra("time","会展时间："+time);
                startActivity(intent);
            }
        });

        mNewsAdapter = new NewsAdapter(newEntityOnes);
        mNewsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(HomeSosActivity.this, NewsDetailWebActivity.class);
                intent.putExtra("news", newEntityOnes.get(position));
                startActivity(intent);
            }
        });

//        activity_company_information_detail_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        sosRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        sosRecyclerView.setAdapter(mAdapter);
        sosRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        sosRecyclerView.setFocusable(false);//导航栏切换不再focuse

         sosepRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        sosepRecyclerView.setAdapter(Epadapter);
        sosepRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        sosepRecyclerView.setFocusable(false);//导航栏切换不再focuse

        sosbsRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        sosbsRecyclerView.setAdapter(businessadapter);
        sosbsRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        sosbsRecyclerView.setFocusable(false);//导航栏切换不再focuse

        soshzRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        soshzRecyclerView.setAdapter(mMyTradesStatusAdapter);
        soshzRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        soshzRecyclerView.setFocusable(false);//导航栏切换不再focuse

        sosnewsRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        sosnewsRecyclerView.setAdapter(mNewsAdapter);
        sosnewsRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        sosnewsRecyclerView.setFocusable(false);//导航栏切换不再focuse




        sosRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    lastposion = manager.findLastCompletelyVisibleItemPosition();
                    Log.v("lgq","..........dddddd====="+"....."+lastposion);
                    int totalItemCount = manager.getItemCount();
                    if (lastposion == (totalItemCount - 1) && isSlidingToLast) {
//                        if (enable)
//                        mMy_loadingDialog.show();
                        pagenum++;
                        if (pagenum>pageContent){
                            ToastUtil.showInfo(getApplicationContext(), "已加载完", Toast.LENGTH_SHORT);
                            return;
                        }
//                            companyInformationDetailNewEntityList.clear();
                        if (type.equals("1")){

                        }
                        getServiceInfo(name);
                    }
                    if (lastposion==1){
                        mCommodityEntityList.clear();
                        pagenum=1;
//                        mMy_loadingDialog.show();
                        getServiceInfo(name);
                        Log.v("lgq",".......shuaxing.....");
                        return;
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

    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {

    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {

    }
}

package com.yuanxin.clan.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.BusinessWholeSearchAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.entity.BusinessDistrictListEntity;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;

import static com.yuanxin.clan.core.util.FastJsonUtils.getObjectsList;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/18 0018 9:45
 */

public class BusinessSosTwoActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener, PullToRefreshView.OnHeaderRefreshListener{

    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.homesosoedit)
    EditText homesosoedit;
    @BindView(R.id.login_errowimage)
    ImageView login_errowimage;
    @BindView(R.id.backacli)
    LinearLayout backacli;
    @BindView(R.id.sosRecyclerView)
    RecyclerView sosRecyclerView;

    @BindView(R.id.allbsosoli)
    LinearLayout allbsosoli;
    @BindView(R.id.shanghuisosli)
    LinearLayout shanghuisosli;
    @BindView(R.id.xiehuisosli)
    LinearLayout xiehuisosli;
    @BindView(R.id.quanzisosli)
    LinearLayout quanzisosli;
    @BindView(R.id.yuanqusosli)
    LinearLayout yuanqusosli;
    @BindView(R.id.quyusosli)
    LinearLayout quyusosli;
    @BindView(R.id.hangyesosli)
    LinearLayout hangyesosli;
    @BindView(R.id.shangxisosli)
    LinearLayout shangxisosli;

    @BindView(R.id.tososte)
    TextView tososte;

    @BindView(R.id.quyusoste)
    TextView quyusoste;
    @BindView(R.id.hangyesoste)
    TextView hangyesoste;
    @BindView(R.id.shangxisoste)
    TextView shangxisoste;

    @BindView(R.id.allsoste)
    TextView allsoste;
    @BindView(R.id.shanghuisoste)
    TextView shanghuisoste;
    @BindView(R.id.xiehuisoste)
    TextView xiehuisoste;
    @BindView(R.id.quanzisoste)
    TextView quanzisoste;
    @BindView(R.id.yuanqusoste)
    TextView yuanqusoste;
    @BindView(R.id.allsoslite)
    TextView allsoslite;
    @BindView(R.id.shanghuilite)
    TextView shanghuilite;
    @BindView(R.id.xiehuilite)
    TextView xiehuilite;
    @BindView(R.id.quanzilite)
    TextView quanzilite;
    @BindView(R.id.yuanqulite)
    TextView yuanqulite;


    private List<BusinessDistrictListEntity> businessDistrictListEntities = new ArrayList<>();
    private ArrayList<String> industryList = new ArrayList();//行业
    private BusinessWholeSearchAdapter businessadapter;
    private SubscriberOnNextListener getIndustryListOnNextListener;//商圈列表 行业分类
    private More_LoadDialog mMy_loadingDialog;
    private int pageCount;
    private int lastposion,pagenum=1;
    private String name ,industryId,mprovince,mcity,businessAreaType,businessAreaGenre;
    private Boolean add[] = {false, false, false, false};

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }
    @Override
    public int getViewLayout() {
        return R.layout.business_sos_two;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mMy_loadingDialog = new More_LoadDialog(BusinessSosTwoActivity.this);
        name = getIntent().getStringExtra("name");

        initOnNext();
        getIndustryList();
        businessAreaGenre="-1";

        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
//        p2rv.setEnablePullLoadMoreDataStatus(false);
        p2rv.setEnablePullTorefresh(false);
        if (businessAreaGenre.equals("-1")) {
            p2rv.setEnablePullLoadMoreDataStatus(false);
        } else {
            p2rv.setEnablePullLoadMoreDataStatus(true);
        }


        homesosoedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name=s.toString();
                pagenum=1;
                if (TextUtil.isEmpty(name)){
                    login_errowimage.setVisibility(View.GONE);

                }else {
                    login_errowimage.setVisibility(View.VISIBLE);

                }
            }
        });

        businessadapter = new BusinessWholeSearchAdapter(businessDistrictListEntities, name);
        businessadapter.setOnItemClickListener(new BusinessWholeSearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, BusinessDistrictListEntity entity) {
                // 未登陆要求登陆
//                if (! UserNative.readIsLogin()){
//                    toLogin();
//                    return;
//                }
                if (entity.getBusinessAreaGenre().equals("3")){
                    Intent intent = new Intent(getApplicationContext(), QuanziDetailwbActivity.class);//圈子详情
                    intent.putExtra("businessAreaId", entity.getBusinessAreaId());
                    intent.putExtra("intype", "qz");
                    intent.putExtra("enshrine", entity.getEnshrine());
                    intent.putExtra("title", entity.getBusinessAreaNm());
                    startActivity(intent);
                    return;

                }
                Intent intent = new Intent(getApplicationContext(), BusinessDetailWebActivity.class);//商圈详情
                intent.putExtra("businessAreaId", entity.getBusinessAreaId());
                intent.putExtra("epStyleType", entity.getEpAccessPath());
                intent.putExtra("accessPath", entity.getAccessPath());
                intent.putExtra("gid", entity.getGroupId());
                intent.putExtra("name", entity.getBusinessAreaNm());
                intent.putExtra("enshrine", entity.getEnshrine());
                startActivity(intent);
            }
        });
        sosRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        sosRecyclerView.setAdapter(businessadapter);
        sosRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        sosRecyclerView.setFocusable(false);//导航栏切换不再focuse
        homesosoedit.setText(name);
        getBusinessSos();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            if (TextUtil.isEmpty(homesosoedit.getText().toString())){
                ToastUtil.showWarning(BusinessSosTwoActivity.this, "请输入内容！", Toast.LENGTH_SHORT);
                return false;
            }
            mcity = "";
            mprovince = "";
            businessDistrictListEntities.clear();
            getBusinessSos();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @OnClick({R.id.backacli,R.id.allbsosoli,R.id.shanghuisosli,R.id.xiehuisosli,R.id.quanzisosli,R.id.yuanqusosli,
    R.id.quyusosli,R.id.hangyesosli,R.id.shangxisosli, R.id.login_errowimage,R.id.tososte})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backacli:
                onBackPressed();
                break;
            case R.id.tososte:
                if (TextUtil.isEmpty(homesosoedit.getText().toString())){
                    ToastUtil.showWarning(BusinessSosTwoActivity.this, "请输入内容！", Toast.LENGTH_SHORT);
                    return;
                }
                mcity = "";
                mprovince = "";
                businessDistrictListEntities.clear();
                getBusinessSos();
                break;
            case R.id.quyusosli:
                onAddressPicker();
                p2rv.setEnablePullLoadMoreDataStatus(true);
                break;
            case R.id.hangyesosli:
                if (industryList != null && industryList.size() > 0) {
                    onConstellationPicker();
                }
                p2rv.setEnablePullLoadMoreDataStatus(true);
                break;
            case R.id.shangxisosli:
                onShangxiPicker();
                p2rv.setEnablePullLoadMoreDataStatus(true);
                break;
            case R.id.allbsosoli:
                allsoste.setTextColor(getResources().getColor(R.color.businesstop));
                allsoslite.setBackgroundResource(R.color.businesstop);
                shanghuisoste.setTextColor(getResources().getColor(R.color.login_black));
                shanghuilite.setBackgroundResource(R.color.white);
                xiehuisoste.setTextColor(getResources().getColor(R.color.login_black));
                xiehuilite.setBackgroundResource(R.color.white);
                quanzisoste.setTextColor(getResources().getColor(R.color.login_black));
                quanzilite.setBackgroundResource(R.color.white);
                yuanqusoste.setTextColor(getResources().getColor(R.color.login_black));
                yuanqulite.setBackgroundResource(R.color.white);
                businessAreaGenre = "-1";
                p2rv.setEnablePullTorefresh(false);
                p2rv.setEnablePullLoadMoreDataStatus(false);
                businessDistrictListEntities.clear();
                getBusinessSos();
                break;
            case R.id.shanghuisosli:
                allsoste.setTextColor(getResources().getColor(R.color.login_black));
                allsoslite.setBackgroundResource(R.color.white);
                shanghuisoste.setTextColor(getResources().getColor(R.color.businesstop));
                shanghuilite.setBackgroundResource(R.color.businesstop);
                xiehuisoste.setTextColor(getResources().getColor(R.color.login_black));
                xiehuilite.setBackgroundResource(R.color.white);
                quanzisoste.setTextColor(getResources().getColor(R.color.login_black));
                quanzilite.setBackgroundResource(R.color.white);
                yuanqusoste.setTextColor(getResources().getColor(R.color.login_black));
                yuanqulite.setBackgroundResource(R.color.white);
                businessAreaGenre = "1";
                p2rv.setEnablePullTorefresh(true);
                p2rv.setEnablePullLoadMoreDataStatus(true);
                businessDistrictListEntities.clear();
                getBusinessSos();
                break;
            case R.id.xiehuisosli:
                allsoste.setTextColor(getResources().getColor(R.color.login_black));
                allsoslite.setBackgroundResource(R.color.white);
                shanghuisoste.setTextColor(getResources().getColor(R.color.login_black));
                shanghuilite.setBackgroundResource(R.color.white);
                xiehuisoste.setTextColor(getResources().getColor(R.color.businesstop));
                xiehuilite.setBackgroundResource(R.color.businesstop);
                quanzisoste.setTextColor(getResources().getColor(R.color.login_black));
                quanzilite.setBackgroundResource(R.color.white);
                yuanqusoste.setTextColor(getResources().getColor(R.color.login_black));
                yuanqulite.setBackgroundResource(R.color.white);
                businessAreaGenre = "2";
                p2rv.setEnablePullTorefresh(true);
                p2rv.setEnablePullLoadMoreDataStatus(true);
                businessDistrictListEntities.clear();
                getBusinessSos();
                break;
            case R.id.quanzisosli:
                allsoste.setTextColor(getResources().getColor(R.color.login_black));
                allsoslite.setBackgroundResource(R.color.white);
                shanghuisoste.setTextColor(getResources().getColor(R.color.login_black));
                shanghuilite.setBackgroundResource(R.color.white);
                xiehuisoste.setTextColor(getResources().getColor(R.color.login_black));
                xiehuilite.setBackgroundResource(R.color.white);
                quanzisoste.setTextColor(getResources().getColor(R.color.businesstop));
                quanzilite.setBackgroundResource(R.color.businesstop);
                yuanqusoste.setTextColor(getResources().getColor(R.color.login_black));
                yuanqulite.setBackgroundResource(R.color.white);
                businessAreaGenre = "3";
                p2rv.setEnablePullTorefresh(true);
                p2rv.setEnablePullLoadMoreDataStatus(true);
                businessDistrictListEntities.clear();
                getBusinessSos();
                break;
            case R.id.yuanqusosli:
                allsoste.setTextColor(getResources().getColor(R.color.login_black));
                allsoslite.setBackgroundResource(R.color.white);
                shanghuisoste.setTextColor(getResources().getColor(R.color.login_black));
                shanghuilite.setBackgroundResource(R.color.white);
                xiehuisoste.setTextColor(getResources().getColor(R.color.login_black));
                xiehuilite.setBackgroundResource(R.color.white);
                quanzisoste.setTextColor(getResources().getColor(R.color.login_black));
                quanzilite.setBackgroundResource(R.color.white);
                yuanqusoste.setTextColor(getResources().getColor(R.color.businesstop));
                yuanqulite.setBackgroundResource(R.color.businesstop);
                businessAreaGenre = "4";
                p2rv.setEnablePullTorefresh(true);
                p2rv.setEnablePullLoadMoreDataStatus(true);
                businessDistrictListEntities.clear();
                getBusinessSos();
                break;
            case R.id.login_errowimage:
                homesosoedit.setText("");
                break;

        }
    }

    private void getBusinessWhole() {
        String url = Url.getBusinessWhole;
        RequestParams params = new RequestParams();
        params.put("businessAreaNm",name );
        params.put("userId", UserNative.getId());
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog==null){
                    return;
                }
                mMy_loadingDialog.dismiss();
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                ToastUtil.showWarning(BusinessSosTwoActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                try {
                    // 增加的标题数量
                    int addNum = 0;
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        mMy_loadingDialog.dismiss();
                        JSONArray shanghuiArray = object.getJSONObject("data").getJSONArray("chamberCommerce");
                        JSONArray xiehuiArray = object.getJSONObject("data").getJSONArray("association");
                        JSONArray quanziArray = object.getJSONObject("data").getJSONArray("circle");
                        JSONArray yuanquArray = object.getJSONObject("data").getJSONArray("park");
                        if (shanghuiArray.length() > 0) {
                            addNum += 1;
                            BusinessDistrictListEntity addEntity = new BusinessDistrictListEntity();
                            addEntity.setBusinessAreaGenre("1");
                            addEntity.setItemType(BusinessDistrictListEntity.style_0);
                            businessDistrictListEntities.add(addEntity);
                            for (int b = 0; b < shanghuiArray.length(); b++) {
                                businessDistrictListEntities.add(analysisBusinessInfo(shanghuiArray.getJSONObject(b)));
                            }
//                            businessDistrictListEntities.addAll(FastJsonUtils.getObjectsList(shanghuiArray.toString(), BusinessDistrictListEntity.class));
                        }
                        if (xiehuiArray.length() > 0) {
                            addNum += 1;
                            BusinessDistrictListEntity addEntity = new BusinessDistrictListEntity();
                            addEntity.setBusinessAreaGenre("2");
                            addEntity.setItemType(BusinessDistrictListEntity.style_0);
                            businessDistrictListEntities.add(addEntity);
                            for (int b = 0; b < xiehuiArray.length(); b++) {
                                businessDistrictListEntities.add(analysisBusinessInfo(xiehuiArray.getJSONObject(b)));
                            }
//                            businessDistrictListEntities.addAll(FastJsonUtils.getObjectsList(xiehuiArray.toString(), BusinessDistrictListEntity.class));
                        }
                        if (quanziArray.length() > 0) {
                            addNum += 1;
                            BusinessDistrictListEntity addEntity = new BusinessDistrictListEntity();
                            addEntity.setBusinessAreaGenre("3");
                            addEntity.setItemType(BusinessDistrictListEntity.style_0);
                            businessDistrictListEntities.add(addEntity);
                            for (int b = 0; b < quanziArray.length(); b++) {
                                businessDistrictListEntities.add(analysisBusinessInfo(quanziArray.getJSONObject(b)));
                            }
//                            businessDistrictListEntities.addAll(FastJsonUtils.getObjectsList(quanziArray.toString(), BusinessDistrictListEntity.class));
                        }
                        if (yuanquArray.length() > 0) {
                            addNum += 1;
                            BusinessDistrictListEntity addEntity = new BusinessDistrictListEntity();
                            addEntity.setBusinessAreaGenre("4");
                            addEntity.setItemType(BusinessDistrictListEntity.style_0);
                            businessDistrictListEntities.add(addEntity);
                            for (int b = 0; b < yuanquArray.length(); b++) {
                                businessDistrictListEntities.add(analysisBusinessInfo(yuanquArray.getJSONObject(b)));
                            }
//                            businessDistrictListEntities.addAll(FastJsonUtils.getObjectsList(yuanquArray.toString(), BusinessDistrictListEntity.class));
                        }
                        if (sosRecyclerView!=null){
                            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) sosRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                            linearParams.height = UIUtils.dip2px(33) * addNum +  UIUtils.dip2px(88) * (businessDistrictListEntities.size() - addNum);// 控件的高强制设成20
                        }
                        businessadapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getBusinessByType() {
        String url = Url.getbusinesssearch;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pagenum);
        params.put("businessAreaNm",name );
        params.put("industryId",industryId );
        params.put("province",mprovince );
        params.put("city",mcity );
        params.put("area","" );
        params.put("businessAreaType",businessAreaType );
        params.put("businessAreaGenre",businessAreaGenre );
        params.put("userId", UserNative.getId());
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog==null){
                    return;
                }
                mMy_loadingDialog.dismiss();
                if (p2rv!=null){
                    p2rv.setRefreshComplete();
                }
                ToastUtil.showWarning(BusinessSosTwoActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("Lgq","w d z   HomeFragment===="+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        mMy_loadingDialog.dismiss();
                        pageCount = object.getInt("pageCount");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        for (int b = 0; b < jsonArray.length(); b++) {
//                            entity.setAccessPath(accessPath);
                            businessDistrictListEntities.add(analysisBusinessInfo(jsonArray.getJSONObject(b)));
                        }
                        if (businessDistrictListEntities.size() == 0) {
                            ToastUtil.showWarning(getApplicationContext(), "无此相关数据", Toast.LENGTH_SHORT);

                        } else {

                        }
                        if (sosRecyclerView!=null){
                            int addNum = 0;
//                            for (Boolean addValue: add) {
//                                addNum = addValue ? (addNum + 1) : addNum;
//                            }
                            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) sosRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                            linearParams.height = UIUtils.dip2px(33) * addNum +  UIUtils.dip2px(88) * (businessDistrictListEntities.size() - addNum);// 控件的高强制设成20
                        }
                        businessadapter.notifyDataSetChanged();
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
        });
    }

    private BusinessDistrictListEntity analysisBusinessInfo(JSONObject businessObject) {
        BusinessDistrictListEntity entity = new BusinessDistrictListEntity();
        try {
            String businessAreaId = businessObject.getString("businessAreaId");//商圈id
            String businessAreaGenre = businessObject.getString("businessAreaGenre");
            String enshrine = businessObject.getString("enshrine");//商圈id
            String businessAreaNm = businessObject.getString("businessAreaNm");//商圈名称
            String businessAreaDetail = businessObject.getString("businessAreaDetail");//商圈名称
            String businessAreaType = businessObject.getString("businessAreaType");//商圈名称
            String collectionCount = businessObject.getString("collectionCount");
            String memberShip = businessObject.getString("memberShip");
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
            entity.setCollectionCount((collectionCount.equals("null") || Utils.isEmpty(collectionCount))?0:Integer.parseInt(collectionCount));
            entity.setMemberShip((memberShip.equals("null") ||Utils.isEmpty(memberShip))?0:Integer.parseInt(memberShip));
            entity.setEnshrine(enshrine);
            entity.setBusinessAreaDetail(businessAreaDetail);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }

    private void getBusinessSos( ) {
        if (businessAreaGenre.equals("-1")) {
            getBusinessWhole();
        } else {
            getBusinessByType();
        }
    }

    private void sortBusinessList() {
        Collections.sort(businessDistrictListEntities, new Comparator<BusinessDistrictListEntity>() {
            @Override
            public int compare(final BusinessDistrictListEntity con1, final BusinessDistrictListEntity con2) {

                if (con1.getBusinessAreaGenre().equals(con2.getBusinessAreaGenre())) {
                    return 0;
                } else if (Integer.parseInt(con2.getBusinessAreaGenre()) > Integer.parseInt(con1.getBusinessAreaGenre())) {
                    return -1;
                } else {
                    return 1;
                }
            }

        });
        int size = businessDistrictListEntities.size();
        for (int i = 0; i < size; i++) {
            BusinessDistrictListEntity entity = businessDistrictListEntities.get(i);
            BusinessDistrictListEntity addEntity = new BusinessDistrictListEntity();
            int add_index = (i == 0) ? 0: (i - 1);
            switch (entity.getBusinessAreaGenre()) {
                case "1":
                    if (!add[0]) {
                        add[0] = !add[0];
                        addEntity.setBusinessAreaGenre("1");
                        addEntity.setItemType(BusinessDistrictListEntity.style_0);
                        businessDistrictListEntities.add(add_index, addEntity);
                    }
                    break;
                case "2":
                    if (!add[1]) {
                        add[1] = !add[1];
                        addEntity.setBusinessAreaGenre("2");
                        addEntity.setItemType(BusinessDistrictListEntity.style_0);
                        businessDistrictListEntities.add(add_index, addEntity);
                    }
                    break;
                case "3":
                    if (!add[2]) {
                        add[2] = !add[2];
                        addEntity.setBusinessAreaGenre("3");
                        addEntity.setItemType(BusinessDistrictListEntity.style_0);
                        businessDistrictListEntities.add(add_index, addEntity);
                    }
                    break;
                case "4":
                    if (!add[3]) {
                        add[3] = !add[3];
                        addEntity.setBusinessAreaGenre("4");
                        addEntity.setItemType(BusinessDistrictListEntity.style_0);
                        businessDistrictListEntities.add(add_index, addEntity);
                    }
                    break;
            }
        }
    }

    private void getIndustryList() {//获取行业列表
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, BusinessSosTwoActivity.this));
    }

    private void initOnNext() {
        getIndustryListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {//行业列表

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<IndustryListEntity> indutryEntityList = new ArrayList<>();//行业id 名称 还有很多
                    indutryEntityList.addAll(getObjectsList(t.getData(), IndustryListEntity.class));
                    for (int i = 0; i < indutryEntityList.size(); i++) {
//                        industryId = String.valueOf(indutryEntityList.get(i).getIndustryId());//把数字变成String 行业id
                        String industryNm = indutryEntityList.get(i).getIndustryNm();//行业名称
                        SharedPreferences sharedPreferences = getSharedPreferences("industryInfo", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString(industryNm, industryId);//名称 id
                        editor.putString(industryId, industryNm);//id  名称
                        editor.commit();//提交修改
                        industryList.add(industryNm);//行业id 行业名称 获取最终行业名称列表
                    }
                    industryList.add(0,"全部");
                } else {
                    ToastUtil.showWarning(getApplicationContext(), "解析出错", Toast.LENGTH_SHORT);
                }
            }
        };
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
        picker.setSelectedIndex(1);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {

                businessDistrictListEntities.clear();
                SharedPreferences share = getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
                String industryOne = share.getString(item, "");//名称 获取id
                if (item.equals("全部")){
                    industryId = "";
                    hangyesoste.setText("行业");
                }else {
                    industryId = industryOne;
                    hangyesoste.setText(item);
                }

                getBusinessSos();
            }
        });
        picker.show();
    }

    public void onShangxiPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        ArrayList<String> shangxiList = new ArrayList();//行业
        shangxiList.add("全部商系");
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
        picker.setSelectedIndex(1);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {

                if (item.equals("全部商系")){
                    businessAreaType="";
                    shangxisoste.setText("商系");
                }else {
                    businessAreaType=item;
                    shangxisoste.setText(item);
                }

                businessDistrictListEntities.clear();
                getBusinessSos();

            }
        });
        picker.show();
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
                if (province.getAreaName().equals("全国")){
                    mcity ="";
                    mprovince = "";
                    quyusoste.setText("区域");
                }else {
                    mcity =city.getAreaName();
                    mprovince = province.getAreaName();
                    quyusoste.setText(mcity);
                }

                businessDistrictListEntities.clear();
                getBusinessSos();
            }
        });
        task.execute("广东", "东莞", "东城");
    }
    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        pagenum++;
        if (pagenum > pageCount) {
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showInfo(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT);
            return;
        }
        getBusinessSos();
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        businessDistrictListEntities.clear();
        pagenum = 1;
        getBusinessSos();
    }
}

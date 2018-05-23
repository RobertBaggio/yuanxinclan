package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.PaiZhaoAdapter;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.company.bean.PaizhaoEntity;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
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
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2017/12/29 0029 16:40
 */

public class PaiZhaolistActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{

    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityBusinessDistrictLibraryRecyclerView;
    @BindView(R.id.activity_business_district_library_industry)
    TextView activityBusinessDistrictLibraryIndustry;
    @BindView(R.id.activity_business_district_library_area)
    TextView activity_business_district_library_area;
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activity_yuan_xin_fair_new_left_layout;
    @BindView(R.id.sosli)
    LinearLayout sosli;


    private More_LoadDialog mMore_loadDialog;
    private PaiZhaoAdapter adapter;
    private int ab =1;
    private int pageCount ;
    private String industryId, industryNm, provinceOne, cityOne, areaOne, epNmSearch, businessAreaId,licensePlateName;
    private List<PaizhaoEntity> mPaizhaoEntities = new ArrayList<>();
    private ArrayList<String> industryList = new ArrayList();//行业
    private SubscriberOnNextListener getIndustryListOnNextListener;//商圈列表 行业分类
    private ArrayList<String> idlist = new ArrayList<>();
    private ArrayList<String> namelist = new ArrayList<>();

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    public int getViewLayout() {
        return R.layout.paizhaolayout;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

        mMore_loadDialog= new More_LoadDialog(this);
//        businessAreaId="501";//测试
        businessAreaId=getIntent().getStringExtra("id");//测试
        initOnNext();
        getIndustryList();
        initRecyclerView();
        getMyLicensePlate(1);
        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
    }

    private void initRecyclerView() {
        adapter = new PaiZhaoAdapter(PaiZhaolistActivity.this, mPaizhaoEntities);
        adapter.setOnItemClickListener(new PaiZhaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                http://192.168.1.102/yuanxinbuluo/weixin/getJsp?url=wechatweb/license-plate-info&param=1
                Intent intent = new Intent(getApplicationContext(), GongXuDetailActivity.class);//商圈详情
                String link = Url.urlWeb+"/license-plate-info&param="+mPaizhaoEntities.get(position).getLicensePlateId()+"&appFlg=1";
                intent.putExtra("url", link);
                intent.putExtra("title", "牌照详情");
                startActivity(intent);
            }
        });

        activityBusinessDistrictLibraryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityBusinessDistrictLibraryRecyclerView.setBackgroundResource(R.color.red);
        activityBusinessDistrictLibraryRecyclerView.setAdapter(adapter);
        activityBusinessDistrictLibraryRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityBusinessDistrictLibraryRecyclerView.setNestedScrollingEnabled(false);//禁止滑动

    }



    private void initOnNext() {
        getIndustryListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {//行业列表

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<IndustryListEntity> indutryEntityList = new ArrayList<IndustryListEntity>();//行业id 名称 还有很多
                    indutryEntityList.addAll(FastJsonUtils.getObjectsList(t.getData(), IndustryListEntity.class));
                    for (int i = 0; i < indutryEntityList.size(); i++) {
                         String industryId = String.valueOf(indutryEntityList.get(i).getIndustryId());//把数字变成String 行业id
                        String industryNm = indutryEntityList.get(i).getIndustryNm();//行业名称
                        idlist.add(industryId);
                        industryList.add(industryNm);
                    }
                } else {
                    ToastUtil.showWarning(getApplicationContext(), "解析出错", Toast.LENGTH_SHORT);
                }
            }
        };
    }

    private void getIndustryList() {//获取行业列表
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, PaiZhaolistActivity.this));
    }

    @OnClick({ R.id.activity_business_district_library_industry,R.id.activity_business_district_library_area,
            R.id.sosli,R.id.activity_business_district_library_shangxi,R.id.activity_yuan_xin_fair_new_left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                finish();
                break;
            case R.id.activity_business_district_library_area:
                onAddressPicker();
                break;
            case R.id.activity_business_district_library_shangxi:
//                onShangxiPicker();
                break;
            case R.id.activity_business_district_library_industry:
                    onConstellationPicker();
                break;
            case R.id.sosli:
                Intent intent = new Intent(PaiZhaolistActivity.this, PaiZhaoSosActivity.class);
                intent.putExtra("businessAreaId", Integer.parseInt(businessAreaId));
                startActivity(intent);
                break;
        }
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
                activityBusinessDistrictLibraryIndustry.setText(item);
                industryId = idlist.get(index);//名称 获取id
                industryNm = item;
//                industry_id = Integer.parseInt(industryOne);//id string 转int 行业 参数
                epNmSearch="";
                cityOne="";
//                Log.v("lgq","llllllll选择结果==="+industryOne);
                mPaizhaoEntities.clear();
                getMyLicensePlate(1);
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
                if (city.getAreaName().equals("全国") ) {
                    provinceOne = null;
                    cityOne = null;
                    areaOne=null;
                    industryId =null;
                    activity_business_district_library_area.setText(city.getAreaName());
                    mPaizhaoEntities.clear();
                    getMyLicensePlate(1);
                } else {
                    provinceOne = province.getAreaName();
                    cityOne = city.getAreaName();
//                    areaOne = county.getAreaName();
                    activity_business_district_library_area.setText(city.getAreaName());
                    mPaizhaoEntities.clear();
                    getMyLicensePlate(1);
                }
            }
        });
        task.execute("广东", "东莞", "东城");
    }

    private void getMyLicensePlate(int pageNumber) {
        String url = Url.businessLicensePlate;
        RequestParams params = new RequestParams();
        mMore_loadDialog.show();
        params.put("pageNumber", pageNumber);
        params.put("businessAreaId", businessAreaId);
        params.put("appFlg", 1);
        params.put("licensePlateName", licensePlateName);
        params.put("industryId", industryId);
        params.put("province", provinceOne);
        params.put("city", cityOne);
        params.put("area", areaOne);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(PaiZhaolistActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
                if (p2rv != null) {
                    p2rv.setRefreshComplete();
                }
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                if (p2rv != null) {
                    p2rv.setRefreshComplete();
                }
                try {
                    JSONObject object = new JSONObject(s);
                    pageCount = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        if (jsonArray.length() == 0) {
//                            kongli.setVisibility(View.VISIBLE);
                        }
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject businessObject = jsonArray.getJSONObject(a);
                            String licensePlateId = businessObject.getString("licensePlateId");//商圈id
                            String image1 = businessObject.getString("licensePlateImage1");//图片
                            String imageend = Url.img_domain + image1 + Url.imageStyle640x640;
                            String businessAreaId = businessObject.getString("businessAreaId");//图片
                            String licensePlateName = businessObject.getString("licensePlateName");//图片
                            String licensePlatePrice = businessObject.getString("licensePlatePrice");//商圈名称
                            String establishYear = businessObject.getString("establishYear");
                            String companyType = businessObject.getString("companyType");
                            String registeredCapital = businessObject.getString("registeredCapital");
                            String createDt = businessObject.getString("createDt");
                            String industry = businessObject.getString("industry");
                            String address = businessObject.getString("address");
                            JSONObject twoob = new JSONObject(address);
                            JSONObject twoobi = new JSONObject(industry);
                            String city = twoob.getString("city");
                            String industryNm = twoobi.getString("industryNm");
//                            Log.v("Lgq","....... " +image11+"  ...   "+image22+"  。。。 "+image33);

                            PaizhaoEntity entity = new PaizhaoEntity();
                            entity.setBusinessAreaId(businessAreaId);
                            entity.setCity(city);
                            entity.setIndustryNm(industryNm);
                            entity.setLicensePlateName(licensePlateName);
                            entity.setCreateDt(createDt);
                            entity.setLicensePlatePrice(licensePlatePrice);
                            entity.setRegisteredCapital(registeredCapital);
                            entity.setLicensePlateId(licensePlateId);
                            entity.setEstablishYear(establishYear);

                            mPaizhaoEntities.add(entity);
                        }
                        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) activityBusinessDistrictLibraryRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(208) * mPaizhaoEntities.size();// 控件的高强制设成20
                        adapter.notifyDataSetChanged();


                    } else {
                        ToastUtil.showWarning(PaiZhaolistActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        ab++;
        if (ab> pageCount) {
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showWarning(PaiZhaolistActivity.this, "没有更多数据", Toast.LENGTH_SHORT);
            return;
        }
        getMyLicensePlate(ab);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        mPaizhaoEntities.clear();
        ab = 1;
        getMyLicensePlate(ab);
    }

}

package com.yuanxin.clan.core.activity_groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.bean.BusinesslistEntity;
import com.yuanxin.clan.core.news.bean.NewsType;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;

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
 * Date: 2018/3/27 0027 9:31
 */

public class GroupsClassify extends BaseActivity{


    @BindView(R.id.activity_business_district_library_left_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.homesosli)
    LinearLayout tososogroupsli;
    @BindView(R.id.fragment_a_tablayout)
    TabLayout fragmentATablayout;
    @BindView(R.id.fragment_a_viewPager)
    ViewPager fragmentAViewPager;
    @BindView(R.id.gstatuste)
    TextView gstatuste;
    @BindView(R.id.gadresste)
    TextView gadresste;
    @BindView(R.id.gtimeste)
    TextView gtimeste;
    @BindView(R.id.gallte)
    TextView gallte;


    private ArrayList<NewsType> mNewsTypes = new ArrayList();//行业
    private GroupsFragmentAdapter adapter;
    private SubscriberOnNextListener getIndustryListOnNextListener;//商圈列表 行业分类
    private String status,adress,times,allstring;
    private String industryId, industryNm, provinceOne, cityOne, areaOne, epNmSearch, areaTen,aaaaa;
    private int intstatus,inttiem,pageid=0;
//    private List<BusinesslistEntity> adsTopList = new ArrayList<>();

    @Override
    public int getViewLayout() {
        return R.layout.grousclassifyly;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {

//        initOnNext();
//        getIndustryList();//行业id
        getBusinessTop();
    }

    private String makeFragmentName(int viewId, long id) {
        return "android:switcher:" + viewId + ":" + id;
    }

    @OnClick({R.id.activity_business_district_library_left_layout,R.id.homesosli,R.id.gstatuste,R.id.gadresste,R.id.gtimeste})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_district_library_left_layout:
                finish();
//                EventBus.getDefault().post(new ActivityData(intstatus, cityOne, inttiem));
                break;
            case R.id.homesosli:
                startActivity(new Intent(GroupsClassify.this, GroupsSosAcitvity.class));
                break;
            case R.id.gstatuste:
                onStatusPicker();
                break;
            case R.id.gadresste:
                onAddressPicker();
                break;
            case R.id.gtimeste:
                onTimesPicker();
                break;
        }
    }

    public String getStatus(){
        return "ssssss";
    }


    private void initInfo() {
//        getTypeTitle();
        fragmentATablayout.setTabMode(TabLayout.MODE_SCROLLABLE);//可以滚动
        adapter = new GroupsFragmentAdapter(getSupportFragmentManager(), mNewsTypes);
        fragmentAViewPager.setAdapter(adapter);
        fragmentATablayout.setupWithViewPager(fragmentAViewPager);


        fragmentAViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pageid = position;
//                Log.i("lgqq","body=====333====adapter=222222222========="+position);
                gstatuste.setText("全部");
                gadresste.setText("全国");
                gtimeste.setText("全时段");
                cityOne = "";
                intstatus = -1;
                inttiem = -1;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
                        String industryId = String.valueOf(indutryEntityList.get(i).getIndustryId());//把数字变成String 行业id
                        String industryNm = indutryEntityList.get(i).getIndustryNm();//行业名称
                        NewsType newsType = new NewsType();
                        newsType.setNewsTypeId(industryId);
                        newsType.setNewsTypeNm(industryNm);
                        mNewsTypes.add(newsType);
                        if (i==indutryEntityList.size()-1){
                            initInfo();
                        }
                    }
                } else {
                    ToastUtil.showError(getApplicationContext(), "解析出错", Toast.LENGTH_SHORT);
                }
            }
        };
    }
    private void getIndustryList() {//获取行业列表
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, GroupsClassify.this));

    }

    private void getBusinessTop() {
        String url = Url.getexIndustry;
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
                            BusinesslistEntity advertisementsEntity = new BusinesslistEntity();
                            String exIndustryNm = businessObject.getString("exIndustryNm");
                            String exIndustryLog = businessObject.getString("exIndustryLog");
                            String exIndustryId = businessObject.getString("exIndustryId");
                            String string = Url.img_domain  + exIndustryLog + Url.imageStyle750x350;

                            NewsType newsType = new NewsType();
                            newsType.setNewsTypeId(exIndustryId);
                            newsType.setNewsTypeNm(exIndustryNm);
                            mNewsTypes.add(newsType);
//                            if (exIndustryId == )

                            if (b==jsonArray.length()-1){
                                initInfo();
                            }
                        }
                        adapter.notifyDataSetChanged();

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

    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                provinceOne = province.getAreaName();
                cityOne = city.getAreaName();
                gadresste.setText(cityOne);

                GEmployee e=new GEmployee();
                //需要调用的时候先注册,传入Boss类型对象和员工参数
                GEmployee e1=e.zhuce((GroupsFragmentone) (Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(fragmentAViewPager.getId(), fragmentAViewPager.getCurrentItem())),e);
                e1.dosomething(cityOne,intstatus,inttiem);
            }
        });
        task.execute("全国", "全国");
    }

    public void onStatusPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        ArrayList<String> shangxiList = new ArrayList();//行业
        shangxiList.add("全部");
        shangxiList.add("进行中");
        shangxiList.add("未开始");
        shangxiList.add("已结束");
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
                gstatuste.setText(item);
                if (index ==0){
                    intstatus = -1;
                }else {
                    intstatus =index;
                }

                GEmployee e=new GEmployee();
                //需要调用的时候先注册,传入Boss类型对象和员工参数
                GEmployee e1=e.zhuce((GroupsFragmentone) (Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(fragmentAViewPager.getId(), fragmentAViewPager.getCurrentItem())),e);
                e1.dosomething(cityOne,intstatus,inttiem);
            }
        });
        picker.show();
    }
    public void onTimesPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        ArrayList<String> shangxiList = new ArrayList();//行业
        shangxiList.add("全时段");
        shangxiList.add("当天");
        shangxiList.add("本周");
        shangxiList.add("本月");
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
                gtimeste.setText(item);
                if (index ==0){
                    inttiem = -1;
                }else {
                    inttiem =index;
                }

                GEmployee e=new GEmployee();
                //需要调用的时候先注册,传入Boss类型对象和员工参数
                GEmployee e1=e.zhuce((GroupsFragmentone) (Fragment) getSupportFragmentManager().findFragmentByTag(makeFragmentName(fragmentAViewPager.getId(), fragmentAViewPager.getCurrentItem())),e);
                e1.dosomething(cityOne,intstatus,inttiem);
            }
        });
        picker.show();
    }

}

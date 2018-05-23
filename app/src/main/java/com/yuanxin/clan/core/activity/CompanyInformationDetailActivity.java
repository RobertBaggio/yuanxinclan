package com.yuanxin.clan.core.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.CompanyInformationDetailAdapter;
import com.yuanxin.clan.core.adapter.CompanyInformationTextChooseAdapter;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.entity.CompanyInformationTextChooseEntity;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

/**
 * Created by lenovo1 on 2017/2/9.
 * 旧企业库
 */
public class CompanyInformationDetailActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.activity_company_information_detail_left_image)
    ImageView activityCompanyInformationDetailLeftImage;
    @BindView(R.id.activity_company_information_detail_middle_text)
    TextView activityCompanyInformationDetailMiddleText;
    @BindView(R.id.activity_company_information_detail_right_image)
    ImageView activityCompanyInformationDetailRightImage;
    @BindView(R.id.activity_company_information_detail_choice_area_text)
    TextView activityCompanyInformationDetailChoiceAreaText;
    @BindView(R.id.activity_company_information_detail_choice_area_layout)
    LinearLayout activityCompanyInformationDetailChoiceAreaLayout;
    @BindView(R.id.activity_company_information_detail_choice_edit)
    EditText activityCompanyInformationDetailChoiceEdit;
    @BindView(R.id.activity_company_information_detail_choice_area_image)
    ImageView activityCompanyInformationDetailChoiceAreaImage;
    @BindView(R.id.activity_company_information_detail_hot_service_recycler_view)
    RecyclerView activityCompanyInformationDetailHotServiceRecyclerView;
    @BindView(R.id.activity_company_information_detail_recycler_view)
    RecyclerView activityCompanyInformationDetailRecyclerView;
    @BindView(R.id.activity_company_information_detail_left_image_layout)
    LinearLayout activityCompanyInformationDetailLeftImageLayout;
    @BindView(R.id.address_search_springview)
    SpringView addressSearchSpringview;
    private CompanyInformationDetailAdapter adapter;
    private CompanyInformationTextChooseAdapter companyInformationTextChooseAdapter; //搜索那里的选择项
    private List<CompanyInformationTextChooseEntity> companyInformationTextChooseEntities = new ArrayList<>();//搜索那里的选择项
    private List<CompanyDetail> companyInformationDetailNewEntityList = new ArrayList<>();//真正需要的企业信息
    private List<String> stys =new ArrayList<>();
    private String province, city, area, edit;
    private int currentPage = 1;// 当前页面，从0开始计数
//    private NewQiYeKuAdater mNewQiYeKuAdater;


    @Override
    public int getViewLayout() {
        return R.layout.activity_company_information_detail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initTitle();//区域定位 东莞
        initView();
        currentPage = 1;

        edit = activityCompanyInformationDetailChoiceEdit.getText().toString().trim();//搜索
//        getWebInfo(province, city, area, edit, currentPage);//企业列表
        getWebInfo("", "", "", "", currentPage);//企业列表
    }

    //恢复
    private void getWebInfo(String province, String city, final String area, String epNm, int pageNumber) {
        String url = Url.getCompanyInfo;
        RequestParams params = new RequestParams();
        params.put("province", province);
        params.put("city", city);
        params.put("area", area);
        params.put("searchNm", epNm);
        params.put("pageNumber", pageNumber);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq",".....企业库。。数据。。"+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        addressSearchSpringview.setVisibility(View.GONE);
//                        String one = object.getString("data");
//                        JSONObject mobject = new JSONObject(one);
//                        String two = mobject.getString("epView");
//                        JSONObject tmobject = new JSONObject(two);
//                        String epAccessPath = tmobject.getString("epAccessPath");
//                        mCompanyDetail.setAccessPath(epAccessPath);
                        companyInformationDetailNewEntityList.addAll(FastJsonUtils.getObjectsList(object.getString("data"), CompanyDetail.class));
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    private void initTitle() {////区域定位 省 东莞 区
        SharedPreferences share = getSharedPreferences("location", Activity.MODE_PRIVATE);
        province = share.getString("province", "");
        city = share.getString("city", "");//城市
        area = share.getString("district", "");
        activityCompanyInformationDetailChoiceAreaText.setText(city);
    }


    private void initView() {

//        mNewQiYeKuAdater = new NewQiYeKuAdater(companyInformationDetailNewEntityList);
//        mNewQiYeKuAdater.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                Intent intent = new Intent(getApplicationContext(), CompanyDetailWebActivity.class);//有个聊天的标志
//                intent.putExtra("epId", companyInformationDetailNewEntityList.get(position).getEpId());
//                intent.putExtra("epLinktel", companyInformationDetailNewEntityList.get(position).getEpLinktel());
//                intent.putExtra("epStyleType", companyInformationDetailNewEntityList.get(position).getEpView().getEpAccessPath());
//                startActivity(intent);
//            }
//        });

        //搜索结果上面的选择项
        final CompanyInformationTextChooseEntity entity = new CompanyInformationTextChooseEntity();
        entity.setText("知识产权");
        CompanyInformationTextChooseEntity entityOne = new CompanyInformationTextChooseEntity();
        entityOne.setText("注册代账");
        CompanyInformationTextChooseEntity entityTwo = new CompanyInformationTextChooseEntity();
        entityTwo.setText("法律咨询");
        CompanyInformationTextChooseEntity entityThree = new CompanyInformationTextChooseEntity();
        entityThree.setText("品牌设计");
        CompanyInformationTextChooseEntity entityFour = new CompanyInformationTextChooseEntity();
        entityFour.setText("技术研发");
        CompanyInformationTextChooseEntity entityFive = new CompanyInformationTextChooseEntity();
        entityFive.setText("电商服务");
        CompanyInformationTextChooseEntity entitySix = new CompanyInformationTextChooseEntity();
        entitySix.setText("营销推广");
        companyInformationTextChooseEntities.add(entity);
        companyInformationTextChooseEntities.add(entityOne);
        companyInformationTextChooseEntities.add(entityTwo);
        companyInformationTextChooseEntities.add(entityThree);
        companyInformationTextChooseEntities.add(entityFour);
        companyInformationTextChooseEntities.add(entityFive);
        companyInformationTextChooseEntities.add(entitySix);

        companyInformationTextChooseAdapter = new CompanyInformationTextChooseAdapter(CompanyInformationDetailActivity.this, companyInformationTextChooseEntities);
        companyInformationTextChooseAdapter.setOnItemClickListener(new CompanyInformationTextChooseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                activityCompanyInformationDetailChoiceEdit.setText(companyInformationTextChooseEntities.get(position).getText());
                edit = activityCompanyInformationDetailChoiceEdit.getText().toString().trim();//搜索
                companyInformationDetailNewEntityList.clear();
                currentPage = 1;
                getWebInfo(province, city, area, edit, currentPage);//pageNumber
            }
        });
        activityCompanyInformationDetailHotServiceRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        activityCompanyInformationDetailHotServiceRecyclerView.setAdapter(companyInformationTextChooseAdapter);
        activityCompanyInformationDetailHotServiceRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        //搜索结果
        adapter = new CompanyInformationDetailAdapter(CompanyInformationDetailActivity.this, companyInformationDetailNewEntityList);
        adapter.setOnItemClickListener(new CompanyInformationDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                //把企业id传过来 详情 第二期做
                Intent intent = new Intent(getApplicationContext(), CompanyDetailWebActivity.class);//有个聊天的标志
                intent.putExtra("epId", companyInformationDetailNewEntityList.get(position).getEpId());
                intent.putExtra("epLinktel", companyInformationDetailNewEntityList.get(position).getEpLinktel());
                intent.putExtra("epStyleType", companyInformationDetailNewEntityList.get(position).getEpView().getEpAccessPath());
                intent.putExtra("accessPath", companyInformationDetailNewEntityList.get(position).getAccessPath());
                startActivity(intent);
            }
        });
        activityCompanyInformationDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityCompanyInformationDetailRecyclerView.setAdapter(adapter);
        activityCompanyInformationDetailRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityCompanyInformationDetailRecyclerView.setFocusable(false);//导航栏切换不再focuse
        addressSearchSpringview.setHeader(new RotationHeader(this));
        addressSearchSpringview.setFooter(new RotationFooter(this));
        addressSearchSpringview.setType(SpringView.Type.OVERLAP);
        addressSearchSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addressSearchSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                companyInformationDetailNewEntityList.clear();
                currentPage = 1;
                getWebInfo(province, city, area, edit, currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addressSearchSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage += 1;
                getWebInfo(province, city, area, edit, currentPage);
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
            public void onAddressPicked(Province provinceFirst, City cityFirst, County countyFirst) {
                if (countyFirst == null) {
                    activityCompanyInformationDetailChoiceAreaText.setText(cityFirst.getAreaName());//市
                    edit = activityCompanyInformationDetailChoiceEdit.getText().toString().trim();//搜索
                    province = provinceFirst.getAreaName();
                    city = cityFirst.getAreaName();
                } else {
                    activityCompanyInformationDetailChoiceAreaText.setText(countyFirst.getAreaName());//区
                    edit = activityCompanyInformationDetailChoiceEdit.getText().toString().trim();//搜索
                    province = provinceFirst.getAreaName();
                    city = cityFirst.getAreaName();
                    area = countyFirst.getAreaName();
                }
            }
        });
        task.execute("广东", "东莞", "东城");
    }


    @OnClick({R.id.activity_company_information_detail_left_image_layout, R.id.activity_company_information_detail_choice_area_layout, R.id.activity_company_information_detail_choice_area_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_information_detail_left_image_layout:
                finish();
                break;
            case R.id.activity_company_information_detail_choice_area_layout:
                onAddressPicker();
                break;
            case R.id.activity_company_information_detail_choice_area_image:
                edit = activityCompanyInformationDetailChoiceEdit.getText().toString().trim();//搜索
                companyInformationDetailNewEntityList.clear();
                currentPage = 1;
                getWebInfo(province, city, area, edit, currentPage);
                break;
        }
    }
}

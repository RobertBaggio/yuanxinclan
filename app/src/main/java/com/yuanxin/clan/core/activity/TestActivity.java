package com.yuanxin.clan.core.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.BestCompanyAdapter;
import com.yuanxin.clan.core.adapter.CompanyInformationDetailAdapter;
import com.yuanxin.clan.core.adapter.verticalHorizontalRollingview.MarqueeView;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.adapter.RecommendCompanyAdapter;
import com.yuanxin.clan.core.company.bean.BestCompanyEntity;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.entity.EpClassEntity;
import com.yuanxin.clan.core.entity.HomePageAnnouncementEntity;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.recyclerview.HorizontalListView;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

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
import co.ceryle.radiorealbutton.RadioRealButton;
import co.ceryle.radiorealbutton.RadioRealButtonGroup;


/**
 * ProjectName: yuanxincla
 * 新企业库
 */

public class TestActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{

    @BindView(R.id.activity_company_information_detail_left_image_layout)
    LinearLayout activityCompanyInformationDetailLeftImageLayout;
    @BindView(R.id.noticeBoard)
    MarqueeView noticeBoard;
    @BindView(R.id.activity_company_information_detail_recycler_view)
    RecyclerView activityCompanyInformationDetailRecyclerView;
    @BindView(R.id.activity_company_information_detail_choice_area_text)
    TextView activityCompanyInformationDetailChoiceAreaText;
    @BindView(R.id.activity_company_information_detail_choice_edit)
    EditText activityCompanyInformationDetailChoiceEdit;
    @BindView(R.id.address_arrow)
    ImageView addressArrow;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;

    // 推荐优质企业
    @BindView(R.id.best_company)
    HorizontalListView bestCompany;
    @BindView(R.id.recommendGroup)
    RadioRealButtonGroup recommendRadioGroup;
    //为你推荐
    @BindView(R.id.recommendCompanyList)
    RecyclerView recommendCompanyList;

    private List<BestCompanyEntity> bestCompanyEntities = new ArrayList<>();
    private BestCompanyAdapter bestCompanyAdapter;

    private List<BestCompanyEntity> correlationCompanyEntities = new ArrayList<>();
    private List<BestCompanyEntity> collectCompanyEntities = new ArrayList<>();
    private List<BestCompanyEntity> hotCompanyEntities = new ArrayList<>();
    private List<BestCompanyEntity> recommendCompanyEntities = new ArrayList<>();
    private RecommendCompanyAdapter recommendCompanyAdapter;

    private CompanyInformationDetailAdapter adapter;
    private List<CompanyDetail> companyInformationDetailNewEntityList = new ArrayList<>();//真正需要的企业信息
    //分类
    public static int item_grid_num = 15;//每一页中GridView中item的数量
    public static int number_columns = 5;//gridview一行展示的数目
    private List<EpClassEntity> mEpClassEntities = new ArrayList<>();
    private List<GridView> gridList = new ArrayList<>();

    private String province, city, area, edit;
    private int pageCount ;// 当前页面，从0开始计数
    private int ab =1;
    private int typeid;
    private More_LoadDialog mMore_loadDialog ;
    private List<HomePageAnnouncementEntity> mStrings=new ArrayList<>();
    private String[] imageUrls = new String[]{};

    @Override
    public int getViewLayout() {
        return R.layout.testlayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.epblue));
        city = Utils.getCity();
        activityCompanyInformationDetailChoiceAreaText.setText(city);
        bestCompanyAdapter = new BestCompanyAdapter(this, bestCompanyEntities);
        bestCompany.setAdapter(bestCompanyAdapter);

        recommendCompanyAdapter = new RecommendCompanyAdapter(recommendCompanyEntities);
        recommendCompanyList.setAdapter(recommendCompanyAdapter);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.recommend_footer, null);
        recommendCompanyAdapter.addFooterView(view);

        adapter = new CompanyInformationDetailAdapter(TestActivity.this, companyInformationDetailNewEntityList);
        adapter.setOnItemClickListener(new CompanyInformationDetailAdapter.OnItemClickListener() {
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

        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        mMore_loadDialog = new More_LoadDialog(this);

        activityCompanyInformationDetailRecyclerView.setLayoutManager(new LinearLayoutManager(TestActivity.this));
        activityCompanyInformationDetailRecyclerView.setAdapter(adapter);
        activityCompanyInformationDetailRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityCompanyInformationDetailRecyclerView.setFocusable(false);//导航栏切换不再focuse

        getWebInfo("", city, "", "",0,1 );//企业列表
        getData();
    }

    private void getData() {
        String url = Url.companyFirstPage;
        RequestParams params = new RequestParams();
        /*1:首页广告位 2:供需成功案例3:企业首页公告*/
        String label = MyShareUtil.getSharedString("label");
        params.put("city", city);
        params.put("label", "金融");
        doHttpGet(url, params, new RequestCallback(){
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toasty.error(getActivity(), "网络连接异常", Toast.LENGTH_SHORT, true).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject str = new JSONObject(s);
                    JSONObject object = str.getJSONObject("data");

                    if (str.getString("success").equals("true")) {
                        bestCompanyEntities.addAll(FastJsonUtils.getObjectsList(object.getString("enterpriseRecommend"), BestCompanyEntity.class));
                        bestCompanyAdapter.notifyDataSetChanged();
                        correlationCompanyEntities.addAll(FastJsonUtils.getObjectsList(object.getString("enterpriseCorrelation"), BestCompanyEntity.class));
                        collectCompanyEntities.addAll(FastJsonUtils.getObjectsList(object.getString("enterpriseCollect"), BestCompanyEntity.class));
                        hotCompanyEntities.addAll(FastJsonUtils.getObjectsList(object.getString("enterpriseHot"), BestCompanyEntity.class));

                        recommendCompanyEntities.clear();
                        recommendCompanyEntities.addAll(correlationCompanyEntities);
                        recommendCompanyAdapter.notifyDataSetChanged();
                        //精选商会、协会、圈子、园区
                        recommendRadioGroup.setOnPositionChangedListener(new RadioRealButtonGroup.OnPositionChangedListener() {
                            @Override
                            public void onPositionChanged(RadioRealButton button, int currentPosition, int lastPosition) {
                                switch (button.getId()) {
                                    case R.id.recommendButton1:
                                    default:
                                        recommendCompanyEntities.clear();
                                        recommendCompanyEntities.addAll(correlationCompanyEntities);
                                        break;
                                    case R.id.recommendButton2:
                                        recommendCompanyEntities.clear();
                                        recommendCompanyEntities.addAll(collectCompanyEntities);
                                        break;
                                    case R.id.recommendButton3:
                                        recommendCompanyEntities.clear();
                                        recommendCompanyEntities.addAll(hotCompanyEntities);
                                        break;
                                }
                                recommendCompanyAdapter.notifyDataSetChanged();
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

    //恢复/industrylist
    private void getWebInfo(final String province, String city, final String area, final String epNm, final int id, final int pageNumber) {
        String saves = MyShareUtil.getSharedString("qiyekutop");
        if (saves.length()>10&&pageNumber==1&&epNm.equals("")&&id==0&&city.equals("")){
            if (p2rv!=null){
                p2rv.setRefreshComplete();
            }
            try {
                JSONObject object = new JSONObject(saves);
                pageCount = object.getInt("pageCount");
                companyInformationDetailNewEntityList.addAll(FastJsonUtils.getObjectsList(object.getString("data"), CompanyDetail.class));
                adapter.notifyDataSetChanged();

                LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityCompanyInformationDetailRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                linearParams.height = UIUtils.dip2px(95)*companyInformationDetailNewEntityList.size();// 控件的高强制设成20

            }catch (JSONException e){
                e.printStackTrace();
            }

            return;
        }

        String url = Url.getCompanyInfo;
        RequestParams params = new RequestParams();
        if (!TextUtil.isEmpty(city)&&city.equals("全国")){
            city="";
            params.put("province", "");
        }else {
            params.put("province", province);
        }
//        params.put("city", city);
        params.put("city", city);
        params.put("area", area);
        params.put("userId", UserNative.getId());
        if (id>0){
            params.put("industryId", id);
        }else {
            params.put("searchNm", epNm);
        }
        params.put("pageNumber", pageNumber);
        mMore_loadDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (p2rv!=null){

                    p2rv.setRefreshComplete();
                }
                if (mMore_loadDialog==null){
                    return;
                }
                mMore_loadDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    if (p2rv!=null){
                        p2rv.setRefreshComplete();
                        mMore_loadDialog.dismiss();
                    }
                    JSONObject object = new JSONObject(s);
                    if (pageNumber==1&&TextUtil.isEmpty(epNm)&&id==0&&TextUtil.isEmpty(province)){
                        MyShareUtil.sharedPstring("qiyekutop",s);
                    }
//                    Log.v("lgq",".....企业库。。数据。。test==="+s);
                    if (object.getString("success").equals("true")) {
                        pageCount = object.getInt("pageCount");
                        companyInformationDetailNewEntityList.addAll(FastJsonUtils.getObjectsList(object.getString("data"), CompanyDetail.class));
                        adapter.notifyDataSetChanged();

                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityCompanyInformationDetailRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(95)*companyInformationDetailNewEntityList.size();// 控件的高强制设成20

                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @OnClick({R.id.activity_company_information_detail_left_image_layout, R.id.activity_company_information_detail_choice_area_layout, R.id.activity_company_information_detail_choice_area_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_information_detail_left_image_layout:
                finish();
                break;
            case R.id.activity_company_information_detail_choice_area_layout:
                addressArrow.animate().rotation(-180);
                onAddressPicker();
                break;
            case R.id.activity_company_information_detail_choice_area_image:
                edit = activityCompanyInformationDetailChoiceEdit.getText().toString().trim();//搜索
                companyInformationDetailNewEntityList.clear();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(activityCompanyInformationDetailChoiceEdit.getWindowToken(), 0);
                getWebInfo(province, city, "", edit,0, 1);
                break;
        }
    }
    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setPickerDismiss(new AddressPickTask.PickerDismiss() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                addressArrow.animate().rotation(0);
            }
        });
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
                    companyInformationDetailNewEntityList.clear();
                    getWebInfo(province, city, area, edit,0, ab);
                } else {
                    activityCompanyInformationDetailChoiceAreaText.setText(countyFirst.getAreaName());//区
                    edit = activityCompanyInformationDetailChoiceEdit.getText().toString().trim();//搜索
                    province = provinceFirst.getAreaName();
                    city = cityFirst.getAreaName();
                    area = countyFirst.getAreaName();
                }
            }
        });
        task.execute("全国", "全国", "");
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
        ab++;
        if (ab==pageCount||ab>pageCount){
            p2rv.onFooterRefreshComplete(1);
            ToastUtil.showInfo(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT);
            return;
        }
        getWebInfo(province, city, area, edit,typeid, ab);
    }

    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
        ab=1;
        companyInformationDetailNewEntityList.clear();
        MyShareUtil.sharedPstring("qiyekutop","0");
        MyShareUtil.sharedPstring("qiyekutitle","0");
        getWebInfo(province, city, area, edit,typeid, ab);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        addressArrow.animate().rotation(360);
    }
}

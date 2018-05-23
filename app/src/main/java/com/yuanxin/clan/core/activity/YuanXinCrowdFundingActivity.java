package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.CrowdFundingAdapter;
import com.yuanxin.clan.core.entity.CrowdFundingAllEntity;
import com.yuanxin.clan.core.entity.CrowdFundingEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

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

/**
 * Created by lenovo1 on 2017/2/21.
 */
//圆心众筹
public class YuanXinCrowdFundingActivity extends BaseActivity {


    @BindView(R.id.activity_yuan_xin_crowd_left_image)
    ImageView activityYuanXinCrowdLeftImage;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activityYuanXinCrowdLeftLayout;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activityYuanXinCrowdMiddleText;
    @BindView(R.id.activity_t_yuan_xin_crowd_right_image)
    ImageView activityTYuanXinCrowdRightImage;
    @BindView(R.id.activity_yuan_xin_crowd_type)
    TextView activityYuanXinCrowdType;
    @BindView(R.id.activity_yuan_xin_crowd_sort)
    TextView activityYuanXinCrowdSort;
    @BindView(R.id.activity_yuan_xin_crowd_state)
    TextView activityYuanXinCrowdState;
    @BindView(R.id.activity_yuan_xin_crowd_recycler_view)
    RecyclerView activityYuanXinCrowdRecyclerView;
    @BindView(R.id.address_search_springview)
    SpringView addressSearchSpringview;
    private List<CrowdFundingEntity> crowdFundingEntities = new ArrayList<>();//想要的数据
    private List<CrowdFundingAllEntity> crowdFundingAllEntities = new ArrayList<>();//所有数据
    private CrowdFundingAdapter crowdFundingAdapter;
    private SubscriberOnNextListener getCrowdListOnNextListener;
    private String schedule, crowdfundSum, crowdfundAll;
    private int currentPage = 1;// 当前页面，从0开始计数
    private String searchNm;


    @Override
    public int getViewLayout() {
        return R.layout.activity_yuan_xin_crowd;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();
        searchNm= "";
        currentPage = 1;
        getGankInto(searchNm, currentPage);
    }

    private void initRecyclerView() {
        crowdFundingAdapter = new CrowdFundingAdapter(YuanXinCrowdFundingActivity.this, crowdFundingAllEntities);
        crowdFundingAdapter.setOnItemClickListener(new CrowdFundingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), CrowdFundingDetailActivity.class);
                intent.putExtra("crowdfundId", crowdFundingAllEntities.get(position).getCrowdfundId());
                startActivity(intent);
            }
        });
        activityYuanXinCrowdRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityYuanXinCrowdRecyclerView.setAdapter(crowdFundingAdapter);
        activityYuanXinCrowdRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityYuanXinCrowdRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
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
                currentPage = 1;
                getGankInto("", currentPage);
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
                getGankInto("", currentPage);
            }
        });

    }

    private void getGankInto(String epNm, int pageNumber) {
        String url = Url.myCrowdFunding;
        RequestParams params = new RequestParams();
        params.put("epNm", epNm);
        params.put("pageNumber", pageNumber);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        if (currentPage == 1) {
                            crowdFundingAllEntities.clear();
                        }
                        for (int b = 0; b < jsonArray.length(); b++) {
                            JSONObject dataObject = jsonArray.getJSONObject(b);
                            schedule = dataObject.getString("schedule");//进度
                            if (schedule.equals("null")) {
                                schedule = "0";
                            }
                            String participations = dataObject.getString("participations");//参与人数
                            String surplusDay = dataObject.getString("surplusDay");//还剩多少天
                            String crowdfundImage1 = dataObject.getString("crowdfundImage1");
                            String crowdfundImageOne = Url.img_domain + crowdfundImage1+Url.imageStyle640x640;//图片
                            int crowdfundId = dataObject.getInt("crowdfundId");//众筹id
                            String crowdfundNm = dataObject.getString("crowdfundNm");//众筹名称
                            crowdfundSum = dataObject.getString("crowdfundSum");//已认筹金额
                            if (crowdfundSum.equals("null")) {
                                crowdfundSum = "0";
                            }
                            crowdfundAll = dataObject.getString("crowdfundAll");//众筹总金额
                            if (crowdfundAll.equals("null")) {
                                crowdfundAll = "0";
                            }
                            String crowdfundImage = dataObject.getString("crowdfundImage");//标题图片
                            String crowdfundImageTwo = Url.img_domain + crowdfundImage +Url.imageStyle640x640;;
                            CrowdFundingAllEntity entity = new CrowdFundingAllEntity();
                            entity.setSchedule(schedule);//进度
                            entity.setParticipations(participations);
                            entity.setSurplusDay(surplusDay);
                            entity.setCrowdfundNm(crowdfundNm);
                            entity.setCrowdfundImage1(crowdfundImageOne);
                            entity.setCrowdfundSum(crowdfundSum);
                            entity.setCrowdfundAll(crowdfundAll);
                            entity.setCrowdfundId(crowdfundId);
                            crowdFundingAllEntities.add(entity);
                        }
                        crowdFundingAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    @OnClick({R.id.activity_yuan_xin_crowd_left_layout, R.id.activity_t_yuan_xin_crowd_right_image, R.id.activity_yuan_xin_crowd_type, R.id.activity_yuan_xin_crowd_sort, R.id.activity_yuan_xin_crowd_state})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_crowd_type:
                onConstellationPicker();
                break;
            case R.id.activity_yuan_xin_crowd_sort:
                onSortPicker();
                break;
            case R.id.activity_yuan_xin_crowd_state:
                onStatePicker();
                break;
            case R.id.activity_yuan_xin_crowd_left_layout:
                finish();
                break;
            case R.id.activity_t_yuan_xin_crowd_right_image://搜索
                Intent intent = new Intent(YuanXinCrowdFundingActivity.this, CrowdFundingSearchActivity.class);
                startActivityForResult(intent, 31);
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 31:
                if (resultCode == RESULT_OK) {
                    String crowdFundingInputString = data.getStringExtra("crowdFundingInputString");//企业名称
                    searchNm = crowdFundingInputString;
                    getGankInto(searchNm, 1);
                }
                break;
            default:
        }
    }

    public void onConstellationPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        OptionPicker picker = new OptionPicker(this,
                isChinese ? new String[]{
                        "筹资金", "筹人", "筹资源", "公益"
                } : new String[]{
                        "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
                        "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
                });
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
        picker.setSelectedIndex(2);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                activityYuanXinCrowdType.setText(item);
            }
        });
        picker.show();
    }

    public void onSortPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        OptionPicker picker = new OptionPicker(this,
                isChinese ? new String[]{
                        "最新上线", "目标金额", "支持人数", "筹资额"
                } : new String[]{
                        "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
                        "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
                });
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
        picker.setSelectedIndex(2);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                activityYuanXinCrowdSort.setText(item);
            }
        });
        picker.show();
    }

    public void onStatePicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        OptionPicker picker = new OptionPicker(this,
                isChinese ? new String[]{
                        "众筹中", "将要结束", "成功结束"
                } : new String[]{
                        "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
                        "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
                });
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
        picker.setSelectedIndex(2);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                activityYuanXinCrowdState.setText(item);
            }
        });
        picker.show();
    }

}

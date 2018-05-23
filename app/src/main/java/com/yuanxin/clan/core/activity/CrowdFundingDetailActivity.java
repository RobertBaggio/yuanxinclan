package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.CrowdFundingDetailImageAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.CorwdFundingDetailImageEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/22.
 */
public class CrowdFundingDetailActivity extends BaseActivity {


    @BindView(R.id.activity_yuan_xin_crowd_left_image)
    ImageView activityYuanXinCrowdLeftImage;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activityYuanXinCrowdLeftLayout;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activityYuanXinCrowdMiddleText;
    @BindView(R.id.activity_t_yuan_xin_crowd_right_image)
    ImageView activityTYuanXinCrowdRightImage;
    @BindView(R.id.activity_crowd_funding_detail_head)
    RelativeLayout activityCrowdFundingDetailHead;
    @BindView(R.id.activity_crowd_funding_detail_name)
    TextView activityCrowdFundingDetailName;
    @BindView(R.id.activity_crowd_funding_detail_round_corner_progress_bar)
    RoundCornerProgressBar activityCrowdFundingDetailRoundCornerProgressBar;
    @BindView(R.id.activity_crowd_funding_detail_percent)
    TextView activityCrowdFundingDetailPercent;
    @BindView(R.id.activity_crowd_funding_detail_people_number)
    TextView activityCrowdFundingDetailPeopleNumber;
    @BindView(R.id.activity_crowd_funding_have_money)
    TextView activityCrowdFundingHaveMoney;
    @BindView(R.id.activity_crowd_funding_detail_time)
    TextView activityCrowdFundingDetailTime;
    @BindView(R.id.activity_crowd_funding_introduce)
    TextView activityCrowdFundingIntroduce;
    @BindView(R.id.activity_crowd_funding_introduce_all_button)
    TextView activityCrowdFundingIntroduceAllButton;
    @BindView(R.id.activity_crowd_funding_detail_project_introduce)
    TextView activityCrowdFundingDetailProjectIntroduce;
    @BindView(R.id.activity_crowd_funding_detail_recycler_view)
    RecyclerView activityCrowdFundingDetailRecyclerView;
    @BindView(R.id.activity_crowd_funding_detail_project_look_all_button_layout)
    LinearLayout activityCrowdFundingDetailProjectLookAllButtonLayout;
    @BindView(R.id.activity_crowd_funding_detail_participation_project)
    TextView activityCrowdFundingDetailParticipationProject;
    @BindView(R.id.activity_crowd_funding_detail_contact)
    TextView activityCrowdFundingDetailContact;
    @BindView(R.id.activity_t_yuan_xin_crowd_right_image_layout)
    LinearLayout activityTYuanXinCrowdRightImageLayout;
    @BindView(R.id.activity_crowd_funding_detail_progress_layout_layout)
    LinearLayout activityCrowdFundingDetailProgressLayoutLayout;
    @BindView(R.id.activity_crowd_funding_detail_line)
    TextView activityCrowdFundingDetailLine;
    @BindView(R.id.activity_crowd_funding_detail_introduce_layout)
    LinearLayout activityCrowdFundingDetailIntroduceLayout;
    private int crowdfundId;
    private boolean flag;
    private int newsId, userId, epId;
    private String userNm, userPhone;
    private LocalBroadcastManager localBroadcastManager;

    private List<CorwdFundingDetailImageEntity> corwdFundingDetailImageEntities = new ArrayList<>();//众筹详情 图片
    private CrowdFundingDetailImageAdapter crowdFundingDetailImageAdapter;//众筹详情 图片


    @Override
    public int getViewLayout() {
        return R.layout.activity_crowd_funding_detail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRoundCornerProgressBar();
        getInfo();
        initRecyclerView();

    }

    private void getInfo() {
        Intent intent = getIntent();
        crowdfundId = intent.getIntExtra("crowdfundId", 0);
        getGankInto(crowdfundId);
    }

    private void getGankInto(int crowdfundId) {
        String url = Url.myCrowdFundingDetail;
        RequestParams params = new RequestParams();
        params.put("crowdfundId", crowdfundId);
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
                        JSONObject jsonObject = object.getJSONObject("data");
                        String user = jsonObject.getString("user");
                        if (!user.equals("null")) {
                            JSONObject userObject = jsonObject.getJSONObject("user");
                            userPhone = userObject.getString("userPhone");
                        }

                        String crowdfundImage1 = jsonObject.getString("crowdfundImage1");
                        String crowdfundImageOne = Url.img_domain + crowdfundImage1+Url.imageStyle640x640;//图片
                        String crowdfundImage2 = jsonObject.getString("crowdfundImage2");
                        String crowdfundImageTwo = Url.img_domain + crowdfundImage2+Url.imageStyle640x640;//图片

                        String crowdfundImage3 = jsonObject.getString("crowdfundImage3");
                        String crowdfundImageTree = Url.img_domain + crowdfundImage3+Url.imageStyle640x640;//图片

                        String crowdfundImage4 = jsonObject.getString("crowdfundImage4");
                        String crowdfundImageFour = Url.img_domain + crowdfundImage4 +Url.imageStyle640x640;//图片

                        String crowdfundImage5 = jsonObject.getString("crowdfundImage5");
                        String crowdfundImageFive = Url.img_domain + crowdfundImage5 +Url.imageStyle640x640;//图片

                        String crowdfundImage6 = jsonObject.getString("crowdfundImage6");
                        String crowdfundImageSix = Url.img_domain + crowdfundImage6 +Url.imageStyle640x640;//图片

                        String crowdfundImage7 = jsonObject.getString("crowdfundImage7");
                        String crowdfundImageSeven = Url.img_domain + crowdfundImage7 +Url.imageStyle640x640;//图片

                        String crowdfundImage8 = jsonObject.getString("crowdfundImage8");
                        String crowdfundImageEight = Url.img_domain + crowdfundImage8 +Url.imageStyle640x640;//图片

                        String crowdfundImage9 = jsonObject.getString("crowdfundImage9");
                        String crowdfundImageNine = Url.img_domain + crowdfundImage9 +Url.imageStyle640x640;//图片

                        String crowdfundImage10 = jsonObject.getString("crowdfundImage10");
                        String crowdfundImageTen = Url.img_domain + crowdfundImage10 +Url.imageStyle640x640;//图片

                        CorwdFundingDetailImageEntity entity = new CorwdFundingDetailImageEntity();
                        entity.setImage(crowdfundImageOne);
                        corwdFundingDetailImageEntities.clear();//图片列表数据
                        corwdFundingDetailImageEntities.add(entity);

                        CorwdFundingDetailImageEntity entityOne = new CorwdFundingDetailImageEntity();
                        entityOne.setImage(crowdfundImageTwo);
                        corwdFundingDetailImageEntities.add(entityOne);

                        CorwdFundingDetailImageEntity entityTwo = new CorwdFundingDetailImageEntity();
                        entityTwo.setImage(crowdfundImageTree);
                        corwdFundingDetailImageEntities.add(entityTwo);
                        crowdFundingDetailImageAdapter.notifyDataSetChanged();


                        String schedule = jsonObject.getString("schedule");//进度
                        if (TextUtil.isEmpty(schedule)) {
                            activityCrowdFundingDetailPercent.setText(null);
                        } else {
                            activityCrowdFundingDetailPercent.setText("0%");
                        }
                        String participations = jsonObject.getString("participations");//参数人数
                        if (TextUtil.isEmpty(participations)) {
                            activityCrowdFundingHaveMoney.setText("参与人数0");
                        } else {
                            activityCrowdFundingHaveMoney.setText("参与人数" + participations);
                        }

                        String surplusDay = jsonObject.getString("surplusDay");//还剩多少天
                        if (TextUtil.isEmpty(surplusDay)) {
                            activityCrowdFundingDetailTime.setText("剩余" + 0 + "天");
                        } else {
                            activityCrowdFundingDetailTime.setText("剩余" + surplusDay + "天");
                        }

                        String crowdfundId = jsonObject.getString("crowdfundId");//众筹id
                        String crowdfundNm = jsonObject.getString("crowdfundNm");//众筹名称
                        if (TextUtil.isEmpty(crowdfundNm)) {
                            activityCrowdFundingDetailName.setText(null);
                        } else {
                            activityCrowdFundingDetailName.setText(crowdfundNm);
                        }
                        String crowdfundSum = jsonObject.getString("crowdfundSum");//已认筹金额

                        int crowdfundSumOne = Integer.valueOf(crowdfundSum);
                        activityCrowdFundingDetailPeopleNumber.setText("已筹款" + crowdfundSumOne + "万");

                        String crowdfundAll = jsonObject.getString("crowdfundAll");//众筹总金额
                        int crowdfundAllOne = Integer.valueOf(crowdfundAll);

                        activityCrowdFundingDetailRoundCornerProgressBar.setMax(crowdfundAllOne);//crowdfundAll//8600 最大
                        activityCrowdFundingDetailRoundCornerProgressBar.setProgress(crowdfundSumOne);//800 有多少

                        String crowdfundImage = jsonObject.getString("crowdfundImage");//
                        String crowdfundImageAll = Url.img_domain + crowdfundImage +Url.imageStyle640x640;;
                        String crowdfundDetail = jsonObject.getString("crowdfundDetail");//项目介绍
                        activityCrowdFundingDetailProjectIntroduce.setText(crowdfundDetail);
//                            String reason=dataObject.getString("reason");//众筹说明 要改要改要改要改要改要改
                        String crowdfundSingle = jsonObject.getString("crowdfundSingle");//每份多少
                        activityCrowdFundingDetailParticipationProject.setText("[" + crowdfundSingle + "元]参与项目");
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


    private void initRecyclerView() {
        crowdFundingDetailImageAdapter = new CrowdFundingDetailImageAdapter(CrowdFundingDetailActivity.this, corwdFundingDetailImageEntities);
        activityCrowdFundingDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        activityCrowdFundingDetailRecyclerView.setFocusable(false);//导航栏切换不再focuse
    }

    private void initRoundCornerProgressBar() {
        activityCrowdFundingDetailRoundCornerProgressBar.setMax(100);
        activityCrowdFundingDetailRoundCornerProgressBar.setProgress(75);
    }


    @OnClick({R.id.activity_yuan_xin_crowd_left_layout, R.id.activity_crowd_funding_detail_participation_project, R.id.activity_crowd_funding_detail_contact, R.id.activity_t_yuan_xin_crowd_right_image_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_crowd_left_layout:
                finish();
                break;
            case R.id.activity_crowd_funding_detail_participation_project://参与项目
                break;
            case R.id.activity_crowd_funding_detail_contact://在线联系
                Intent intent = new Intent(CrowdFundingDetailActivity.this, PersionChatActivity.class);
                if (TextUtil.isEmpty(userPhone)) {
                    ToastUtil.showWarning(getApplicationContext(), "暂时联系不上发起人", Toast.LENGTH_SHORT);
                    return;
                }
                //userId 一定加""
                intent.putExtra(Constant.EXTRA_USER_ID, TextUtil.isEmpty(userPhone) ? 1 : userPhone);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra(Constant.USER_NAME, UserNative.getName());
                intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                intent.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                intent.putExtra("type", "与发起人聊天中");
                startActivity(intent);
                break;
            case R.id.activity_t_yuan_xin_crowd_right_image_layout://收藏
                if (!flag) {
                    activityTYuanXinCrowdRightImage.setImageResource(R.drawable.news_collecte_pre);
                    collecte();//收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intentTwo = new Intent("com.example.broadcasttest.CROWD_FRESH");
                    localBroadcastManager.sendBroadcast(intentTwo); // 发送本地广播
                } else {
                    activityTYuanXinCrowdRightImage.setImageResource(R.drawable.news_collecte_nomal);
                    deleteCollecte();//取消收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intentOne = new Intent("com.example.broadcasttest.CROWD_FRESH");
                    localBroadcastManager.sendBroadcast(intentOne); // 发送本地广播
                }
                flag = !flag;
                break;
            case R.id.activity_crowd_funding_introduce_all_button://显示全部
                if (View.GONE == activityCrowdFundingDetailProjectLookAllButtonLayout.getVisibility()) {
                    activityCrowdFundingDetailProjectLookAllButtonLayout.setVisibility(View.VISIBLE);
                } else {
                    activityCrowdFundingDetailProjectLookAllButtonLayout.setVisibility(View.GONE);

                }
                break;
        }
    }

    private void deleteCollecte() {
        userId = UserNative.getId();
        userNm = UserNative.getName();
        String url = Url.DeleteCollecte;
        RequestParams params = new RequestParams();
        params.put("keyId", crowdfundId);//收藏项目ID newsId
        params.put("type", 4);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
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

    private void collecte() {
        userId = UserNative.getId();
        userNm = UserNative.getName();
        String url = Url.addCollecte;
        RequestParams params = new RequestParams();
        params.put("keyId", crowdfundId);//收藏项目ID newsId
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        params.put("type", 4);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
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
}

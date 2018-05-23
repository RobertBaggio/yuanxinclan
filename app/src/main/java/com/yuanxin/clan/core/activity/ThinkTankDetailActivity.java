package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.ThinkTankDetailAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.ThinkTankEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
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
 * Created by lenovo1 on 2017/2/20.
 */
//专家详细介绍
public class ThinkTankDetailActivity extends BaseActivity {


    @BindView(R.id.activity_think_tank_detail_left_layout)
    LinearLayout activityThinkTankDetailLeftLayout;
    @BindView(R.id.activity_think_tank_detail_right_layout)
    LinearLayout activityThinkTankDetailRightLayout;
    @BindView(R.id.activity_think_tank_detail_title_layout)
    RelativeLayout activityThinkTankDetailTitleLayout;
    @BindView(R.id.activity_think_tank_detail_line_one)
    TextView activityThinkTankDetailLineOne;
    @BindView(R.id.activity_think_tank_detail_head_image)
    MLImageView activityThinkTankDetailHeadImage;
    @BindView(R.id.activity_think_tank_detail_name)
    TextView activityThinkTankDetailName;
    @BindView(R.id.activity_think_tank_detail_company)
    TextView activityThinkTankDetailCompany;
    @BindView(R.id.activity_think_tank_detail_position)
    TextView activityThinkTankDetailPosition;
    @BindView(R.id.activity_think_tank_detail_head_layout)
    LinearLayout activityThinkTankDetailHeadLayout;
    @BindView(R.id.activity_think_tank_detail_line_two)
    TextView activityThinkTankDetailLineTwo;
    @BindView(R.id.activity_think_tank_detail_specialist)
    TextView activityThinkTankDetailSpecialist;
    @BindView(R.id.activity_think_tank_detail_introduce)
    TextView activityThinkTankDetailIntroduce;
    @BindView(R.id.activity_think_tank_detail_honor)
    TextView activityThinkTankDetailHonor;
    @BindView(R.id.activity_think_tank_detail_recycler_view)
    RecyclerView activityThinkTankDetailRecyclerView;
    @BindView(R.id.activity_think_tank_detail_online_consult)
    TextView activityThinkTankDetailOnlineConsult;
    @BindView(R.id.activity_think_tank_detail_title)
    TextView activityThinkTankDetailTitle;
    @BindView(R.id.activity_think_tank_detail_right_image)
    ImageView activityThinkTankDetailRightImage;
    private List<String> itemThinkDetailEntity = new ArrayList<>();
    private ThinkTankDetailAdapter adapter;
    private SubscriberOnNextListener getByIdOnNextListener;
    private boolean flag;
    private int newsId, userId, expertId;
    private String userNm;
    private LocalBroadcastManager localBroadcastManager;
    private ThinkTankEntity mThinkTankEntity;

    @Override
    public int getViewLayout() {
        return R.layout.activity_think_tank_detail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();
    }


    private void initRecyclerView() {
        adapter = new ThinkTankDetailAdapter(ThinkTankDetailActivity.this, itemThinkDetailEntity);
        activityThinkTankDetailRecyclerView.setLayoutManager(new GridLayoutManager(activityThinkTankDetailRecyclerView.getContext(), 3));//有问题
        activityThinkTankDetailRecyclerView.setAdapter(adapter);
        getGankInto();
    }

    private void getGankInto() {
        String url = Url.getExpertDetail;
        Intent intent = getIntent();
        expertId = intent.getIntExtra("expertId", 0);
        RequestParams params = new RequestParams();
        params.put("expertId", expertId);
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
                        itemThinkDetailEntity.clear();
                        mThinkTankEntity = JSON.parseObject(object.getString("data"), ThinkTankEntity.class);
                        itemThinkDetailEntity.add(mThinkTankEntity.getImage2());
                        itemThinkDetailEntity.add(mThinkTankEntity.getImage3());
                        itemThinkDetailEntity.add(mThinkTankEntity.getImage4());
                        itemThinkDetailEntity.add(mThinkTankEntity.getImage5());
                        itemThinkDetailEntity.add(mThinkTankEntity.getImage6());
                        itemThinkDetailEntity.add(mThinkTankEntity.getImage7());
                        adapter.notifyDataSetChanged();
                        ImageManager.loadBitmap(ThinkTankDetailActivity.this, Url.img_domain + mThinkTankEntity.getImage1() + Url.imageStyle208x208, R.drawable.list_img, activityThinkTankDetailHeadImage);
                        activityThinkTankDetailName.setText(mThinkTankEntity.getExpertNm());//专家名称
                        activityThinkTankDetailIntroduce.setText(mThinkTankEntity.getExpertDetail());//专家描述
                        activityThinkTankDetailPosition.setText(mThinkTankEntity.getPosition());//职位
                        activityThinkTankDetailCompany.setText(mThinkTankEntity.getCompany());//公司
                        activityThinkTankDetailTitle.setText(mThinkTankEntity.getTitle());//头衔
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    @OnClick({R.id.activity_think_tank_detail_left_layout, R.id.activity_think_tank_detail_right_layout, R.id.activity_think_tank_detail_online_consult})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_think_tank_detail_left_layout:
                finish();
                break;
            case R.id.activity_think_tank_detail_right_layout://收藏
                if (!flag) {
                    activityThinkTankDetailRightImage.setImageResource(R.drawable.news_collecte_pre);
                    collecte();//收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.EXPERT_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                } else {
                    activityThinkTankDetailRightImage.setImageResource(R.drawable.news_collecte_nomal);
                    deleteCollecte();//取消收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intent = new Intent("com.example.broadcasttest.EXPERT_FRESH");
                    localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                }
                flag = !flag;

                break;
            case R.id.activity_think_tank_detail_online_consult:
                if (mThinkTankEntity != null) {
                    if (TextUtil.isEmpty(mThinkTankEntity.getUserPhone())) {
                        ToastUtil.showInfo(getApplicationContext(), "暂时联系不上专家", Toast.LENGTH_SHORT);
                        return;
                    }
                    Intent intent = new Intent(ThinkTankDetailActivity.this, PersionChatActivity.class);
                    intent.putExtra(Constant.EXTRA_USER_ID, mThinkTankEntity.getUserPhone());
                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                    intent.putExtra("type", "与专家聊天中");
                    startActivity(intent);
                }
                break;
        }
    }

    private void deleteCollecte() {
        userId = UserNative.getId();
        userNm = UserNative.getName();

        String url = Url.DeleteCollecte;
        RequestParams params = new RequestParams();
        params.put("keyId", expertId);//收藏项目ID newsId
        params.put("type", 9);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
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
        params.put("keyId", expertId);//收藏项目ID newsId
        params.put("userId", userId);//省userId
        params.put("userNm", userNm);//用户名
        params.put("type", 9);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
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
                    Logger.d("json 解析出错");
                }
            }
        });

    }
}

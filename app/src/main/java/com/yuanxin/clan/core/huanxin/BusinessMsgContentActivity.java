package com.yuanxin.clan.core.huanxin;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.*;
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
 * Created by lenovo1 on 2017/3/27.
 */
public class BusinessMsgContentActivity extends BaseActivity {
    @BindView(R.id.msg_content)
    TextView msgContent;
    @BindView(R.id.msg_title)
    TextView msgTitle;
    @BindView(R.id.business_logo)
    ImageView businessLogo;
    @BindView(R.id.create_date)
    TextView createDate;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.propertyApplyLayout)
    LinearLayout propertyApplyLayout;
    @BindView(R.id.property)
    TextView property;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.date_icon)
    TextView dateIcon;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.time_icon)
    TextView timeIcon;
    @BindView(R.id.explain)
    TextView explain;
    @BindView(R.id.explain_icon)
    TextView explainIcon;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.name_icon)
    TextView nameIcon;
    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.phone_icon)
    TextView phoneIcon;
    @BindView(R.id.agreeLayout)
    LinearLayout agreeLayout;
    @BindView(R.id.agree)
    Button agree;
    @BindView(R.id.refuse)
    Button refuse;
    @BindView(R.id.status_tip)
    TextView statusTip;
    @BindView(R.id.repair_layout)
    LinearLayout repairLayout;
    @BindView(R.id.repair_address)
    TextView repairAddr;
    @BindView(R.id.repair_question)
    TextView repairQuestion;
    @BindView(R.id.repair_image1)
    ImageView repairImage1;
    @BindView(R.id.repair_image2)
    ImageView repairImage2;
    @BindView(R.id.repair_image3)
    ImageView repairImage3;
    @BindView(R.id.fix)
    Button fix;
    @BindView(R.id.refuse_fix)
    Button refuseFix;
    @BindView(R.id.repair_status_tip)
    TextView repairStatusTip;

    @Override
    public int getViewLayout() {
        return R.layout.activity_business_msg_content;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        Typeface iconfont = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        dateIcon.setTypeface(iconfont);
        timeIcon.setTypeface(iconfont);
        explainIcon.setTypeface(iconfont);
        nameIcon.setTypeface(iconfont);
        phoneIcon.setTypeface(iconfont);
        BusinessMessage msg = intent.getParcelableExtra("msg");
        SiteData siteData = msg.getSiteData();
        RepairsData repairsData = msg.getRepairsData();
        ImageManager.load(BusinessMsgContentActivity.this, Url.img_domain + msg.getBusinessAreaLogo() + Url.imageStyle640x640, businessLogo);
        msgTitle.setText(msg.getBusinessMsgTitle());
        createDate.setText(msg.getCreateDt());
        businessName.setText(msg.getBusinessAreaNm());
        msgContent.setText(msg.getBusinessMsgContent());
        if (siteData == null && repairsData == null) {
            // 普通文本消息
            msgContent.setVisibility(View.VISIBLE);
            propertyApplyLayout.setVisibility(View.GONE);
            repairLayout.setVisibility(View.GONE);
        } else if (siteData == null && repairsData != null){
            // 商圈报修消息
            msgContent.setVisibility(View.GONE);
            propertyApplyLayout.setVisibility(View.GONE);
            repairLayout.setVisibility(View.VISIBLE);
            repairAddr.setText(repairsData.getRepairsAddress());
            repairQuestion.setText(repairsData.getFaultDescribe());
            if (!TextUtil.isEmpty(repairsData.getFaultImage1())) {
                ImageManager.load(this, Url.img_domain + repairsData.getFaultImage1(), repairImage1);
            } else {
                repairImage1.setVisibility(View.GONE);
            }
            if (!TextUtil.isEmpty(repairsData.getFaultImage2())) {
                ImageManager.load(this, Url.img_domain + repairsData.getFaultImage2(), repairImage2);
            } else {
                repairImage2.setVisibility(View.GONE);
            }
            if (!TextUtil.isEmpty(repairsData.getFaultImage3())) {
                ImageManager.load(this, Url.img_domain + repairsData.getFaultImage3(), repairImage3);
            } else {
                repairImage3.setVisibility(View.GONE);
            }
            switch (repairsData.getProcessState()) {
                case 2:
                    fix.setVisibility(View.GONE);
                    refuseFix.setVisibility(View.GONE);
                    repairStatusTip.setText("已派人处理");
                    repairStatusTip.setTextColor(getResources().getColor(R.color.businesstop));
                    repairStatusTip.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    fix.setVisibility(View.GONE);
                    refuseFix.setVisibility(View.GONE);
                    repairStatusTip.setText("无法处理");
                    repairStatusTip.setTextColor(getResources().getColor(R.color.angred));
                    repairStatusTip.setVisibility(View.VISIBLE);
                    break;
            }
        }else {
            // 商圈场地预约消息
            msgContent.setVisibility(View.GONE);
            propertyApplyLayout.setVisibility(View.VISIBLE);
            repairLayout.setVisibility(View.GONE);
            property.setText(siteData.getSiteNm());
            date.setText(siteData.getDateUsed());
            time.setText(siteData.getHoursUsed());
            explain.setText(TextUtil.isEmpty(siteData.getApplyExplain()) ? "无" : siteData.getApplyExplain());
            name.setText(siteData.getSiteProposerName());
            phone.setText(siteData.getContactNumber());
            switch (siteData.getProcessState()) {
                case 2:
                    agree.setVisibility(View.GONE);
                    refuse.setVisibility(View.GONE);
                    statusTip.setText("同意");
                    statusTip.setTextColor(getResources().getColor(R.color.businesstop));
                    statusTip.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    agree.setVisibility(View.GONE);
                    refuse.setVisibility(View.GONE);
                    statusTip.setText("拒绝");
                    statusTip.setTextColor(getResources().getColor(R.color.angred));
                    statusTip.setVisibility(View.VISIBLE);
                    break;
            }
        }

    }

    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.agree, R.id.refuse, R.id.fix, R.id.refuse_fix, R.id.repair_image1, R.id.repair_image2, R.id.repair_image3})
    public void onClick(View view) {
        BusinessMessage msg = getIntent().getParcelableExtra("msg");
        SiteData siteData = msg.getSiteData();
        RepairsData repairsData = msg.getRepairsData();
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.agree:
                propertyApplyDoAgreeOrRefuse(siteData, 2);
                break;
            case R.id.refuse:
                propertyApplyDoAgreeOrRefuse(siteData, 3);
                break;
            case R.id.fix:
                repairDoAgreeOrRefuse(repairsData, 2);
                break;
            case R.id.refuse_fix:
                repairDoAgreeOrRefuse(repairsData, 3);
                break;
            case R.id.repair_image1:
                com.yuanxin.clan.core.util.Utils.openImage(this, com.alibaba.fastjson.JSONArray.toJSONString(getAllFaultPic(repairsData)), Url.img_domain + repairsData.getFaultImage1());
                break;
            case R.id.repair_image2:
                com.yuanxin.clan.core.util.Utils.openImage(this, com.alibaba.fastjson.JSONArray.toJSONString(getAllFaultPic(repairsData)), Url.img_domain + repairsData.getFaultImage2());
                break;
            case R.id.repair_image3:
                com.yuanxin.clan.core.util.Utils.openImage(this, com.alibaba.fastjson.JSONArray.toJSONString(getAllFaultPic(repairsData)), Url.img_domain + repairsData.getFaultImage3());
                break;
        }
    }

    private List<String> getAllFaultPic(RepairsData repairsData) {
        List<String> result = new ArrayList<String>();
        if (!TextUtil.isEmpty(repairsData.getFaultImage1())) {
            result.add(Url.img_domain + repairsData.getFaultImage1());
        }
        if (!TextUtil.isEmpty(repairsData.getFaultImage2())) {
            result.add(Url.img_domain + repairsData.getFaultImage2());
        }
        if (!TextUtil.isEmpty(repairsData.getFaultImage3())) {
            result.add(Url.img_domain + repairsData.getFaultImage3());
        }
        return result;
    }

    private void propertyApplyDoAgreeOrRefuse(final SiteData siteData, final int agree) {
        RequestParams params = new RequestParams();
        params.put("siteApplyId", siteData.getSiteApplyId());
        //处理状态 2同意 3拒绝
        params.put("applyUserId", siteData.getApplyUserId());
        params.put("processState", agree);
        params.put("businessAreaId", siteData.getBusinessAreaId());
        params.put("userId", UserNative.getId());
        params.put("userNm", UserNative.getName());
        params.put("appFlg", 1);
        doHttpPost(Url.businessPropertySiteApply, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                Logger.e(s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        siteData.setProcessState(agree);
                        BusinessMessageDao businessMessageDao = new BusinessMessageDao(BusinessMsgContentActivity.this);
                        BusinessMessage businessMessage = getIntent().getParcelableExtra("msg");
                        businessMessage.setSiteData(siteData);
                        businessMessage.setId(String.valueOf(businessMessage.getBusinessMsgId()));
                        businessMessageDao.updateMessage(businessMessage);
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void repairDoAgreeOrRefuse(final RepairsData repairsData, final int agree) {
        RequestParams params = new RequestParams();
        //处理状态 2同意 3拒绝
        params.put("repairsId", repairsData.getRepairsId());
        params.put("processState", agree);
        params.put("businessAreaId", repairsData.getBusinessAreaId());
        params.put("userId", UserNative.getId());
        params.put("userNm", UserNative.getName());
        params.put("appFlg", 1);
        doHttpPost(Url.businessRepair, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                Logger.e(s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        repairsData.setProcessState(agree);
                        BusinessMessageDao businessMessageDao = new BusinessMessageDao(BusinessMsgContentActivity.this);
                        BusinessMessage businessMessage = getIntent().getParcelableExtra("msg");
                        businessMessage.setRepairsData(repairsData);
                        businessMessage.setId(String.valueOf(businessMessage.getBusinessMsgId()));
                        businessMessageDao.updateMessage(businessMessage);
                        finish();
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

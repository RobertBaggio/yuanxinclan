package com.yuanxin.clan.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.MainActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
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
 * Created by lenovo1 on 2017/5/10.
 * 完善资料
 */
public class BecomeCompanyLeaderActivity extends BaseActivity {

    @BindView(R.id.activity_company_info_left_layout)
    LinearLayout activityCompanyInfoLeftLayout;
    @BindView(R.id.activity_company_info_right_layout)
    LinearLayout activityCompanyInfoRightLayout;
    @BindView(R.id.activity_login_member_register_company_name)
    EditText activityLoginMemberRegisterCompanyName;
    @BindView(R.id.activity_login_member_register_company_persion)
    EditText activityLoginMemberRegisterCompanyPersion;
    @BindView(R.id.activity_login_member_register_classification_of_industry)
    TextView activityLoginMemberRegisterClassificationOfIndustry;
    @BindView(R.id.activity_login_member_register_company_code)
    EditText activityLoginMemberRegisterCompanyCode;
    @BindView(R.id.activity_login_member_register_name)
    EditText activityLoginMemberRegisterName;
    @BindView(R.id.activity_login_member_register_position)
    EditText activityLoginMemberRegisterPosition;
    @BindView(R.id.activity_login_member_register_province)
    TextView activityLoginMemberRegisterProvince;
    @BindView(R.id.activity_login_member_register_city)
    TextView activityLoginMemberRegisterCity;
    @BindView(R.id.activity_login_member_register_district)
    TextView activityLoginMemberRegisterDistrict;
    @BindView(R.id.activity_login_member_register_address_choose_layout)
    LinearLayout activityLoginMemberRegisterAddressChooseLayout;
    @BindView(R.id.activity_login_member_register_address)
    EditText activityLoginMemberRegisterAddress;
    @BindView(R.id.activity_login_member_register_register)
    Button activityLoginMemberRegisterRegister;
    @BindView(R.id.activity_login_member_register_login)
    TextView activityLoginMemberRegisterLogin;
    private SubscriberOnNextListener getSmsCodeOnNext, memberRegisterOnNext, getIndustryListOnNextListener;
    private String code;
    private ArrayList<String> industryList = new ArrayList();//行业
    private String industryId, industryNm, industryNumber, province, city, area, epNm;
    private int industryPut, industryNumberNew;
    private int userId;
    private String epId;
    private String easemobUuid;
    private static final String TAG = "LoginActivity";

    @Override
    public int getViewLayout() {
        return R.layout.activity_become_company_leader;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        getIndustryList();//行业id
    }

    private void getIndustryList() {
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, BecomeCompanyLeaderActivity.this));
    }

    private void initOnNext() {

        getIndustryListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {
            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
//                    ToastUtil.showSuccess(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                    List<IndustryListEntity> entityList = new ArrayList<IndustryListEntity>();
                    entityList.addAll(FastJsonUtils.getObjectsList(t.getData(), IndustryListEntity.class));
                    for (int i = 0; i < entityList.size(); i++) {
                        industryId = String.valueOf(entityList.get(i).getIndustryId());//id String
                        industryNm = entityList.get(i).getIndustryNm();//名称
                        SharedPreferences sharedPreferences = getSharedPreferences("industryInfo", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString(industryNm, industryId);//名称 id String
                        editor.commit();//提交修改
                        industryList.add(industryNm);
                    }
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
    }

    @OnClick({R.id.activity_company_info_left_layout, R.id.activity_login_member_register_classification_of_industry, R.id.activity_login_member_register_province, R.id.activity_login_member_register_city, R.id.activity_login_member_register_district, R.id.activity_login_member_register_address_choose_layout, R.id.activity_login_member_register_register})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_info_left_layout:
                finish();
                break;
            case R.id.activity_login_member_register_classification_of_industry:
                onClassification0fIndustryPicker();
                break;
            case R.id.activity_login_member_register_province:
                onAddressPicker();
                break;
            case R.id.activity_login_member_register_city:
                onAddressPicker();
                break;
            case R.id.activity_login_member_register_district:
                onAddressPicker();
                break;
            case R.id.activity_login_member_register_address_choose_layout:

                break;
            case R.id.activity_login_member_register_register:
                String epNm = activityLoginMemberRegisterCompanyName.getText().toString().trim();
                if (epNm.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入企业名称", Toast.LENGTH_SHORT);
                    return;
                }
                String legalNm = (activityLoginMemberRegisterCompanyPersion.getText().toString().trim());//企业法人

                if (legalNm.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入企业法人", Toast.LENGTH_SHORT);
                    return;
                }
                String industry = activityLoginMemberRegisterClassificationOfIndustry.getText().toString().trim();//行业分类

                if (industry.equals("行业")) {
                    ToastUtil.showInfo(getApplicationContext(), "请选择行业分类", Toast.LENGTH_SHORT);
                    return;
                }
                SharedPreferences share = getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
                String industrySp = share.getString(industry, "");//行业名称 对应 idString
                try {
                    industryNumberNew = Integer.parseInt(industrySp);//id
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                String epCreditCd = activityLoginMemberRegisterCompanyCode.getText().toString().trim();//机构代码
                if (epCreditCd.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入机构代码", Toast.LENGTH_SHORT);
                    return;
                }
                String postion = activityLoginMemberRegisterPosition.getText().toString().trim();//职        位
                if (postion.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入职位", Toast.LENGTH_SHORT);
                    return;
                }
                String province = activityLoginMemberRegisterProvince.getText().toString().trim();//省
                if (province.equals("选择省")) {
                    ToastUtil.showInfo(getApplicationContext(), "请选择省", Toast.LENGTH_SHORT);
                    return;
                }
                String city = activityLoginMemberRegisterCity.getText().toString().trim();//市
                if (city.equals("选择市")) {
                    ToastUtil.showInfo(getApplicationContext(), "请选择市", Toast.LENGTH_SHORT);
                    return;
                }
                String area = activityLoginMemberRegisterDistrict.getText().toString().trim();//县
                if (area.equals("选择县")) {
                    ToastUtil.showInfo(getApplicationContext(), "请选择县", Toast.LENGTH_SHORT);
                    return;
                }
                String detail = activityLoginMemberRegisterAddress.getText().toString().trim();//地址
                if (detail.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入详细地址", Toast.LENGTH_SHORT);
                    return;
                }
                String userNm = activityLoginMemberRegisterName.getText().toString().trim();
                if (userNm.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入名字", Toast.LENGTH_SHORT);
                    return;
                }
                addCompanyInfoNew(epNm, legalNm, industryNumberNew, epCreditCd, userNm, postion, province, city, area, detail);
                break;
            default:
                break;
        }
    }

    private void addCompanyInfoNew(String epNm, String legalNm, int industryId, String epCreditCd, String userNm, String postion, String province, String city, String area, String detail) {//上传企业信息

        String url = Url.addNewCompany;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//企业id 1
        params.put("userNm", userNm);//企业名称1
//        params.put("epId",epIdTwo);//企业id 1
        params.put("epNm", epNm);//企业名称1
        params.put("legalNm", legalNm);//企业法人1
//        params.put("epStore",all);//企业店铺
//        params.put("epStore","");
//        params.put("addressId", addressId);
//        params.put("epScale","");//企业规模1
        params.put("industryId", industryId);//所属行业1
//        params.put("epDetail",epDetailOne);//企业详情1
//        params.put("epScope",epScopeOne);//经营范围1
//        params.put("epLogo",imageLogo);//epLogo1
//        params.put("epImage1",imageOne);//用户id
//        params.put("epImage2",imageTwo);//用户id
//        params.put("epImage3",imageThree);//用户id
//        params.put("epImage4",imageFour);//用户id
//        params.put("epImage5",imageFive);//用户id
//        params.put("epImage6",imageSix);//用户id
//        params.put("epImage7",imageSeven);//用户id
//        params.put("epImage8",imageEight);//用户id
//        params.put("epImage9",imageNine);//用户id
//        params.put("epImage10", "");
//        params.put("epLinkman", epLinkman);//联系人1
        params.put("epLinktel", UserNative.getPhone());//联系电话1
        params.put("epCreditCd", epCreditCd);//企业信用代码1
        params.put("updateEpuser", 1);//企业信用代码1
//        params.put("addressId","");//1
        params.put("province", province);//省1
        params.put("city", city);//1
        params.put("area", area);//1
        params.put("detail", detail);//1
        params.put("status", 0);//1

        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
//                        Login(UserNative.getPhone(), UserNative.getPwd());
                        onBackPressed();
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

    private void LoginWenXin(final String username, String password) {
        //开始 环信
        if (!EaseCommonUtils.isNetWorkConnected(this)) {
            ToastUtil.showWarning(this, getString(R.string.network_isnot_available), Toast.LENGTH_SHORT);
            return;
        }


        if (TextUtils.isEmpty(username)) {
            ToastUtil.showWarning(this, getString(R.string.User_name_cannot_be_empty), Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.showWarning(this, getString(R.string.Password_cannot_be_empty), Toast.LENGTH_SHORT);
            return;
        }

//        progressShow = true;
//        final ProgressDialog pd = new ProgressDialog(BecomeCompanyLeaderActivity.this);
//        pd.setCanceledOnTouchOutside(false);
//        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {//取消
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                //Log.d(TAG, "EMClient.getInstance().onCancel");
////                progressShow = false;
//            }
//        });
//        pd.setMessage(getString(R.string.Is_landing));
//        pd.show();


//        final long start = System.currentTimeMillis();
//        // call login method
//        //Log.d(TAG, "EMClient.getInstance().login");
////                if (easemobUuid.equals("0")) {
////                    Toast.makeText(getApplicationContext(), "没有注册环信", Toast.LENGTH_SHORT).show();
////                    return;
////                }

        EMClient.getInstance().login(username, password, new EMCallBack() {

            @Override
            public void onSuccess() {
                //Log.d(TAG, "login: onSuccess");


                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                boolean updatenick = EMClient.getInstance().pushManager().updatePushNickname(username);//昵称
                if (!updatenick) {//没有更新头像
                    //Log.e("LoginActivity", "update current user nick fail");
                }

//                if (!BecomeCompanyLeaderActivity.this.isFinishing() && pd.isShowing()) {
//                    pd.dismiss();
//                }
                DemoHelper.getInstance().getUserProfileManager().asyncGetCurrentUserInfo();//获取用户信息
                DemoHelper.getInstance().setCurrentUserName(username);
                Intent intent = new Intent(BecomeCompanyLeaderActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onProgress(int progress, String status) {
                //Log.d(TAG, "login: onProgress");
            }

            @Override
            public void onError(final int code, final String message) {
                //Log.d(TAG, "login: onError: " + code);
//                if (!progressShow) {
//                    return;
//                }
                runOnUiThread(new Runnable() {
                    public void run() {
//                        pd.dismiss();
                        ToastUtil.showError(getApplicationContext(), getString(R.string.Login_failed) + message,
                                Toast.LENGTH_SHORT);
                    }
                });
            }
        });

    }


    public void onAddressPicker() {
        BecomePicktask task = new BecomePicktask(this);
        task.setCallback(new BecomePicktask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {

                if (county == null) {
                    activityLoginMemberRegisterProvince.setText(province.getAreaName());
                    activityLoginMemberRegisterCity.setText(city.getAreaName());
                } else {
                    activityLoginMemberRegisterProvince.setText(province.getAreaName());
                    activityLoginMemberRegisterCity.setText(city.getAreaName());
                    activityLoginMemberRegisterDistrict.setText(county.getAreaName());
                }
            }
        });
        task.execute("广东", "东莞", "东城");
    }

    private void onClassification0fIndustryPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
//        OptionPicker picker = new OptionPicker(this,
//                isChinese ? new String[]{
//                        "制造业", "建筑业", "批发和零售业", "金融业", "房地产业", "石油化工",
//                        "交通运输", "信息产业", "机械机电", "天蝎座", "射手座", "摩羯座"
//                } : new String[]{
//                        "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
//                        "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
//                });
        OptionPicker picker = new OptionPicker(this, industryList);
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
        picker.setSelectedIndex(7);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                activityLoginMemberRegisterClassificationOfIndustry.setText(item);
                SharedPreferences share = getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
                industryNumber = share.getString(item, "");//行业名称 对应 idString
                industryPut = Integer.parseInt(industryNumber);//id

//                HttpMethods.getInstance().areaSeek(new ProgressSubscriber(industrySeekNextListener, CompanyInformationDetailActivity.this),item);
            }
        });
        picker.show();
    }
}

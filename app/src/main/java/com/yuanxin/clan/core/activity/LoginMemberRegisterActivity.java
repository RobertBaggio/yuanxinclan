package com.yuanxin.clan.core.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.TimerUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

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
 * Created by lenovo1 on 2017/2/20.
 * 会员注册
 */
//会员用户登录
public class LoginMemberRegisterActivity extends BaseActivity {


    @BindView(R.id.window_head_left_image)
    ImageView windowHeadLeftImage;
    @BindView(R.id.window_head_left_text)
    TextView windowHeadLeftText;
    @BindView(R.id.window_head_left_layout)
    LinearLayout windowHeadLeftLayout;
    @BindView(R.id.window_head_name)
    TextView windowHeadName;
    @BindView(R.id.center_headname_ll)
    LinearLayout centerHeadnameLl;
    @BindView(R.id.window_head_right_layout)
    LinearLayout windowHeadRightLayout;
    @BindView(R.id.kejianimage)
    ImageView kejianimage;
    @BindView(R.id.window_head_layout)
    RelativeLayout windowHeadLayout;
    @BindView(R.id.activity_login_member_register_phone)
    EditText activityLoginMemberRegisterPhone;
    @BindView(R.id.activity_login_member_register_code)
    EditText activityLoginMemberRegisterCode;
    @BindView(R.id.activity_login_member_register_send_code)
    TextView activityLoginMemberRegisterSendCode;
    @BindView(R.id.activity_login_member_register_password)
    EditText activityLoginMemberRegisterPassword;
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
    private int ifkejian=0;
    private int mMin = 31;
    private TimerUtil timerUtil;

    @Override
    public int getViewLayout() {
        return R.layout.activity_login_member_register;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        getIndustryList();//行业id
        initView();
        initTimerUtil();
    }

    private void getIndustryList() {
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, LoginMemberRegisterActivity.this));
    }

    private void initOnNext() {
        getSmsCodeOnNext = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    ToastUtil.showSuccess(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                    code = t.getData().toString();
                    timeStart();
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
        memberRegisterOnNext = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    ToastUtil.showSuccess(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                    startActivity(new Intent(LoginMemberRegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
        getIndustryListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {
            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
//                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
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

    private void initView() {
        windowHeadLayout.setBackgroundColor(getResources().getColor(R.color.businesstop));
        windowHeadLeftText.setVisibility(View.GONE);
        windowHeadLeftImage.setImageResource(R.drawable.ease_mm_title_back);
        windowHeadRightLayout.setVisibility(View.GONE);
        windowHeadName.setTextColor(getResources().getColor(R.color.white));
        windowHeadName.setText("完善资料");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @OnClick({R.id.window_head_left_layout, R.id.activity_login_member_register_send_code,R.id.kejianimage, R.id.activity_login_member_register_classification_of_industry, R.id.activity_login_member_register_province, R.id.activity_login_member_register_city, R.id.activity_login_member_register_district, R.id.activity_login_member_register_address_choose_layout, R.id.activity_login_member_register_register, R.id.activity_login_member_register_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.window_head_left_layout:
                finish();
                break;
            case R.id.activity_login_member_register_send_code://验证码
                String phone = activityLoginMemberRegisterPhone.getText().toString().trim();
                if (phone.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!Utils.isPhoneNumber(phone)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入正确的手机号", Toast.LENGTH_SHORT);
                    activityLoginMemberRegisterPhone.setText("");
                    return;
                }
                HttpMethods.getInstance().getSmsCode(new ProgressSubscriber<BaseJsonEntity>(getSmsCodeOnNext, LoginMemberRegisterActivity.this), phone);

//                String phone=activityLoginMemberRegisterPhone.getText().toString().trim();
//                if(phone.equals("")&& !Utils.isPhoneNumber(phone)){
//                    Toast.makeText(getApplicationContext(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
//                }
//                HttpMethods.getInstance().getSmsCode(new ProgressSubscriber<BaseJsonEntity>(getSmsCodeOnNext,LoginMemberRegisterActivity.this),phone);
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
            case R.id.activity_login_member_register_register://userPhone,userPwd,epNm,legalNm,industryId,epCreditCd,userNm,postion,province,city,area,detail
                String userPhone = activityLoginMemberRegisterPhone.getText().toString().trim();
                if (userPhone.equals("") && !Utils.isPhoneNumber(userPhone)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入正确的手机码", Toast.LENGTH_SHORT);
                    return;
                }
                String codeText = activityLoginMemberRegisterCode.getText().toString().trim();
//
                if (codeText.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入验证码", Toast.LENGTH_SHORT);
                    return;
                }
                if (!codeText.equals(code)) {
                    ToastUtil.showInfo(getApplicationContext(), "输入的验证码有误，请重新输入", Toast.LENGTH_LONG);
                    activityLoginMemberRegisterCode.setText("");
                    return;
                }
                String userPwd = activityLoginMemberRegisterPassword.getText().toString().trim();
                if (userPwd.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT);
                    return;
                }
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
//                int industryNumber=Integer.parseInt(industrySp);//id
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
//                String addressAll=province+city+district+address;
//
//                Toast.makeText(getApplicationContext(),addressAll,Toast.LENGTH_SHORT).show();

                String userNm = activityLoginMemberRegisterName.getText().toString().trim();
                if (userNm.equals("")) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入名字", Toast.LENGTH_SHORT);
                    return;
                }
//                String userPhone="13669872913";//手机
//                String userPwd="123456";//密码
//                String epNm="美宜佳";//企业名称
//                String legalNm="法人";//法人
//                int industryId=5;//行业id
//                String epCreditCd="123456";//信用代码
//                String userNm="圆心";//姓名
//                String postion="总经理";//职位
//                String province="广东省";//省
//                String city="东莞市";//市
//                String area="万江区";//区
//                String detail="东城中路";//详细地址
//                userPhone,userPwd,epNm,legalNm,industryId,epCreditCd,userNm,postion,province,city,area,detail
                HttpMethods.getInstance().memberRegister(new ProgressSubscriber<BaseJsonEntity>(memberRegisterOnNext, LoginMemberRegisterActivity.this), userPhone, userPwd, epNm, legalNm, industryNumberNew, epCreditCd, userNm, postion, province, city, area, detail);
//                HttpMethods.getInstance().memberRegister(new ProgressSubscriber<BaseJsonEntity>(memberRegisterOnNext, LoginMemberRegisterActivity.this),
//                        userPhone,userPwd,epNm,legalNm,industryId,epCreditCd,userNm,postion,province,city,area,detail);


//                HttpMethods.getInstance().memberRegister(new ProgressSubscriber<BaseJsonEntity>(memberRegisterOnNext, LoginMemberRegisterActivity.this), phoneNumber,password,companyName,companyPersion,industryPut,position,addressAll,CompanyCode);
                break;
            case R.id.activity_login_member_register_login://登录
//                startActivity(new Intent(LoginMemberRegisterActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.kejianimage://返回
                if (ifkejian==0){
                    activityLoginMemberRegisterPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    activityLoginMemberRegisterPassword.setSelection(activityLoginMemberRegisterPassword.getText().length());
                    ifkejian=1;
                }else if (ifkejian==1){
                    //设置密码为隐藏的
                    activityLoginMemberRegisterPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    activityLoginMemberRegisterPassword.setSelection(activityLoginMemberRegisterPassword.getText().length());
                    ifkejian=0;
                }
                break;
        }
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

    private void initTimerUtil() {
        TimerUtil.OnTimerChangeListener onTimerChangeListener = new TimerUtil.OnTimerChangeListener() {
            @Override
            public void doChange() {
                doTimeChange();
            }
        };
        timerUtil = new TimerUtil(0, 1000, onTimerChangeListener);
    }
    private void doTimeChange() {
        if (activityLoginMemberRegisterSendCode == null)
            return;
        mMin--;
        if (mMin < 0) {
            timeStop();
            activityLoginMemberRegisterSendCode.setText("获取验证码");
            return;
        }
        activityLoginMemberRegisterSendCode.setText(String.valueOf(mMin)+"秒后重发");
    }
    /**
     * 停止计时器
     */
    private void timeStop() {
        mMin = 31;
        activityLoginMemberRegisterSendCode.setEnabled(true);
        timerUtil.timeStop();
    }
    /**
     * 计时器开始
     */
    private void timeStart() {
        activityLoginMemberRegisterSendCode.setEnabled(false);
        timerUtil.timeStart();
    }
}

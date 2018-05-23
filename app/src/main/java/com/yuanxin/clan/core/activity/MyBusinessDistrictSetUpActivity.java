package com.yuanxin.clan.core.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.lypeer.fcpermission.FcPermissions;
import com.lypeer.fcpermission.impl.FcPermissionsCallbacks;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/2/27.
 * 创建商圈类
 */
//我创建的商圈  修改
public class MyBusinessDistrictSetUpActivity extends BaseActivity implements FcPermissionsCallbacks {
    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_INDUSTRY = 11;//行业分类
    private static final int REQUEST_INTRODUCE = 13;//企业简介
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.activity_my_business_district_set_up_left_layout)
    LinearLayout activityMyBusinessDistrictSetUpLeftLayout;
    @BindView(R.id.activity_my_business_district_set_up_right_layout)
    LinearLayout activityMyBusinessDistrictSetUpRightLayout;
    @BindView(R.id.activity_my_business_district_set_up_logo_text)
    TextView activityMyBusinessDistrictSetUpLogoText;
    @BindView(R.id.activity_my_business_district_set_up_logo_image)
    ImageView activityMyBusinessDistrictSetUpLogoImage;
    @BindView(R.id.activity_my_business_district_set_up_logo_layout)
    RelativeLayout activityMyBusinessDistrictSetUpLogoLayout;
    @BindView(R.id.activity_my_business_district_setshanghui)
    RelativeLayout activity_my_business_district_setshanghui;
    @BindView(R.id.activity_my_business_district_set_up_name_text)
    TextView activityMyBusinessDistrictSetUpNameText;
    @BindView(R.id.activity_my_business_district_set_up_name_edit)
    EditText activityMyBusinessDistrictSetUpNameEdit;
    @BindView(R.id.activity_my_business_district_set_up_name_layout)
    RelativeLayout activityMyBusinessDistrictSetUpNameLayout;
    @BindView(R.id.activity_my_business_district_set_up_introduce)
    TextView activityMyBusinessDistrictSetUpIntroduce;
    @BindView(R.id.activity_my_business_district_set_up_introduce_text)
    TextView activityMyBusinessDistrictSetUpIntroduceText;
    @BindView(R.id.activity_my_business_district_set_up_introduce_image)
    ImageView activityMyBusinessDistrictSetUpIntroduceImage;
    @BindView(R.id.activity_my_business_district_set_up_introduce_layout)
    RelativeLayout activityMyBusinessDistrictSetUpIntroduceLayout;
    @BindView(R.id.activity_my_business_district_set_up_industry_classify_text)
    TextView activityMyBusinessDistrictSetUpIndustryClassifyText;
    @BindView(R.id.activity_my_business_district_set_up_industry_classify_text_text)
    TextView activityMyBusinessDistrictSetUpIndustryClassifyTextText;
    @BindView(R.id.activity_my_business_district_set_up_industry_classify_image)
    ImageView activityMyBusinessDistrictSetUpIndustryClassifyImage;
    @BindView(R.id.activity_company_info_style_layout)
    RelativeLayout activity_company_info_style_layout;
    @BindView(R.id.shangquanleixinselectre)
    RelativeLayout shangquanleixinselectre;
    @BindView(R.id.activity_my_business_district_set_up_industry_classify_layout)
    RelativeLayout activityMyBusinessDistrictSetUpIndustryClassifyLayout;
    @BindView(R.id.activity_my_business_district_set_up_member)
    TextView activityMyBusinessDistrictSetUpMember;
    @BindView(R.id.activity_my_business_district_set_up_member_text)
    TextView activityMyBusinessDistrictSetUpMemberText;
    @BindView(R.id.activity_my_business_district_set_up_member_image)
    ImageView activityMyBusinessDistrictSetUpMemberImage;
    @BindView(R.id.activity_my_business_district_set_up_member_layout)
    RelativeLayout activityMyBusinessDistrictSetUpMemberLayout;
    @BindView(R.id.activity_my_business_district_set_up_image1)
    ImageView activityMyBusinessDistrictSetUpImage1;
    @BindView(R.id.activity_my_business_district_set_up_image2)
    ImageView activityMyBusinessDistrictSetUpImage2;
    @BindView(R.id.activity_my_business_district_set_up_image3)
    ImageView activityMyBusinessDistrictSetUpImage3;
    @BindView(R.id.activity_my_business_district_set_up_image4)
    ImageView activityMyBusinessDistrictSetUpImage4;
    @BindView(R.id.activity_my_business_district_set_up_image5)
    ImageView activityMyBusinessDistrictSetUpImage5;
    @BindView(R.id.activity_my_business_district_set_up_image6)
    ImageView activityMyBusinessDistrictSetUpImage6;
    @BindView(R.id.activity_my_business_district_set_up_image7)
    ImageView activityMyBusinessDistrictSetUpImage7;
    @BindView(R.id.activity_my_business_district_set_up_image8)
    ImageView activityMyBusinessDistrictSetUpImage8;
    @BindView(R.id.activity_my_business_district_set_up_image9)
    ImageView activityMyBusinessDistrictSetUpImage9;
    @BindView(R.id.activity_my_business_district_set_up_province)
    TextView activityMyBusinessDistrictSetUpProvince;
    @BindView(R.id.activity_my_business_district_set_up_city)
    TextView activityMyBusinessDistrictSetUpCity;
    @BindView(R.id.activity_my_business_district_set_up_district)
    TextView activityMyBusinessDistrictSetUpDistrict;
    @BindView(R.id.activity_my_business_district_set_up_address_choose_layout)
    LinearLayout activityMyBusinessDistrictSetUpAddressChooseLayout;
    @BindView(R.id.activity_my_business_district_set_up_detail_address)
    TextView activityMyBusinessDistrictSetUpDetailAddress;
    @BindView(R.id.activity_my_business_district_set_up_detail_address_edit)
    EditText activityMyBusinessDistrictSetUpDetailAddressEdit;
    @BindView(R.id.activity_my_business_district_set_up_button)
    Button activityMyBusinessDistrictSetUpButton;
    @BindView(R.id.activity_company_info_style_text)
    TextView activityCompanyInfoStyleText;
    @BindView(R.id.activityp_industry_classify_text_text)
    TextView activityp_industry_classify_text_text;
    @BindView(R.id.activityp_industry_classify_text_textt)
    TextView activityp_industry_classify_text_textt;
    @BindView(R.id.sqteli)
    TextView sqteli;

    private String detail, city, area, province, industryNm;//企业简介 经营范围

    private String businessAreaId, industryId,typeid;
    private boolean isAdd = true;
    private int addressId;
    private int businessAreaGenre;
    private More_LoadDialog mMore_loadDialog;
    private int baViewId;

    private int preStatus = 0;

    @Override
    public int getViewLayout() {
        return R.layout.activity_my_business_district_set_up;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        mMore_loadDialog = new More_LoadDialog(this);
        businessAreaId = getIntent().getStringExtra("businessAreaId");
        typeid  = getIntent().getStringExtra("ty");
        if (TextUtil.isEmpty(businessAreaId)) {

            isAdd = true;
            tvTitle.setText("创建商圈");
            activityMyBusinessDistrictSetUpMemberLayout.setVisibility(View.GONE);
            shangquanleixinselectre.setVisibility(View.GONE);
            sqteli.setVisibility(View.GONE);
        } else {
            isAdd = false;
            tvTitle.setText("修改商圈");
            getBusinessDetail(businessAreaId);
        }
    }

    private void getBusinessDetail(String businessAreaId) {
        String url = Url.getOneBusinessDetail;
        RequestParams params = new RequestParams();
        params.put("businessAreaId", businessAreaId);//用户id
        mMore_loadDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMore_loadDialog.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String data = object.getString("data");
                        if (!data.equals("null")) {
                            JSONObject jsonObject = object.getJSONObject("data");
                            String businessAreaId = jsonObject.getString("businessAreaId");//商圈id
                            String businessAreaNm = jsonObject.getString("businessAreaNm");//商圈名称
                            addressId = jsonObject.getInt("addressId");
                            activityMyBusinessDistrictSetUpNameEdit.setText(businessAreaNm);
                            String businessAreaDetail = jsonObject.getString("businessAreaDetail");//描述
                            activityMyBusinessDistrictSetUpIntroduceText.setText(businessAreaDetail);
                            preStatus = Integer.parseInt(jsonObject.getString("status"));//

                            String baImage1 = jsonObject.getString("baImage1");//图片
                            String image11 = Url.img_domain + baImage1 + Url.imageStyle750x450;
                            image1 = baImage1;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image11, R.drawable.list_img, activityMyBusinessDistrictSetUpLogoImage);

                            String baImage22 = jsonObject.getString("baImage2");//商圈名称
                            String image22 = Url.img_domain + baImage22 + Url.imageStyle750x450;
                            image2=baImage22;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image22, R.drawable.list_img, activityMyBusinessDistrictSetUpImage1);

                            String baImage33 = jsonObject.getString("baImage3");//商圈名称
                            String image33 = Url.img_domain + baImage33 + Url.imageStyle750x450;
                            image3=baImage33;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image33, R.drawable.list_img, activityMyBusinessDistrictSetUpImage2);

                            String baImage44 = jsonObject.getString("baImage4");//商圈名称
                            String image44 = Url.img_domain + baImage44 + Url.imageStyle750x450;
                            image4=baImage44;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image44, R.drawable.list_img, activityMyBusinessDistrictSetUpImage3);

                            String baImage55 = jsonObject.getString("baImage5");//商圈名称
                            String image55 = Url.img_domain + baImage55 + Url.imageStyle750x450;
                            image5=baImage55;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image55, R.drawable.list_img, activityMyBusinessDistrictSetUpImage4);

                            String baImage66 = jsonObject.getString("baImage6");//商圈名称
                            String image66 = Url.img_domain + baImage66 + Url.imageStyle750x450;
                            image6 = baImage66;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image66, R.drawable.list_img, activityMyBusinessDistrictSetUpImage5);

                            String baImage77 = jsonObject.getString("baImage7");//商圈名称
                            String image77 = Url.img_domain + baImage77 + Url.imageStyle750x450;
                            image7 = baImage77;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image77, R.drawable.list_img, activityMyBusinessDistrictSetUpImage6);

                            String baImage88 = jsonObject.getString("baImage8");//商圈名称
                            String image88 = Url.img_domain + baImage88 + Url.imageStyle750x450;
                            image8 = baImage88;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image88, R.drawable.list_img, activityMyBusinessDistrictSetUpImage7);

                            String baImage99 = jsonObject.getString("baImage9");//商圈名称
                            String image99 = Url.img_domain + baImage99 + Url.imageStyle750x450;
                            image9 = baImage99;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image99, R.drawable.list_img, activityMyBusinessDistrictSetUpImage8);

                            String baImage100 = jsonObject.getString("baImage10");//商圈名称
                            String image100 = Url.img_domain + baImage100 + Url.imageStyle750x450;
                            image10 =baImage100;
                            ImageManager.load(MyBusinessDistrictSetUpActivity.this, image100, R.drawable.list_img, activityMyBusinessDistrictSetUpImage9);

                            String address = jsonObject.getString("address");
                            if (!address.equals("null")) {
                                JSONObject addressObject = jsonObject.getJSONObject("address");
                                province = addressObject.getString("province");
                                city = addressObject.getString("city");
                                area = addressObject.getString("area");
                                detail = addressObject.getString("detail");

                            }
                            baViewId = Integer.parseInt(jsonObject.getString("baViewId"));
                            String businessAreaGenre2 = jsonObject.getString("businessAreaGenre");
                            businessAreaGenre = Integer.parseInt(businessAreaGenre2);
                            if (businessAreaGenre2.equals("1")){
                                activityp_industry_classify_text_textt.setText("商会");
                            }else if (businessAreaGenre2.equals("2")){
                                activityp_industry_classify_text_textt.setText("协会");
                            }else if (businessAreaGenre2.equals("3")){
                                activityp_industry_classify_text_textt.setText("圈子");
                            }else if (businessAreaGenre2.equals("4")){
                                activityp_industry_classify_text_textt.setText("园区");
                            }
                            String businessAreaType = jsonObject.getString("businessAreaType");
                            activityp_industry_classify_text_text.setText(businessAreaType);
                            activityCompanyInfoStyleText.setText("风格 "+baViewId);
                            activityMyBusinessDistrictSetUpProvince.setText(province);
                            activityMyBusinessDistrictSetUpCity.setText(city);
                            activityMyBusinessDistrictSetUpDistrict.setText(area);
                            activityMyBusinessDistrictSetUpDetailAddressEdit.setText(detail);
                            mMore_loadDialog.dismiss();
                            String industry = jsonObject.getString("industry");
                            if (!industry.equals("null")) {
                                JSONObject addressObject = jsonObject.getJSONObject("industry");
                                industryNm = addressObject.getString("industryNm");
                            }
                            activityMyBusinessDistrictSetUpIndustryClassifyTextText.setText(industryNm);
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    private void initOnNext() {
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);
                    for (int i = 0; i < listString.size(); i++) {
                        saveImageUrl(chooseImageIndex, listString.get(i));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    @OnClick({R.id.activity_my_business_district_set_up_left_layout,R.id.shangquanleixinselectre,R.id.activity_my_business_district_setshanghui,R.id.activity_company_info_style_layout, R.id.activity_my_business_district_set_up_logo_layout, R.id.activity_my_business_district_set_up_introduce_layout, R.id.activity_my_business_district_set_up_industry_classify_layout, R.id.activity_my_business_district_set_up_member_layout, R.id.activity_my_business_district_set_up_image1, R.id.activity_my_business_district_set_up_image2, R.id.activity_my_business_district_set_up_image3, R.id.activity_my_business_district_set_up_image4, R.id.activity_my_business_district_set_up_image5, R.id.activity_my_business_district_set_up_image6, R.id.activity_my_business_district_set_up_image7, R.id.activity_my_business_district_set_up_image8, R.id.activity_my_business_district_set_up_image9, R.id.activity_my_business_district_set_up_province, R.id.activity_my_business_district_set_up_city, R.id.activity_my_business_district_set_up_district, R.id.activity_my_business_district_set_up_address_choose_layout, R.id.activity_my_business_district_set_up_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_my_business_district_set_up_left_layout://返回
                finish();
                break;
            case R.id.shangquanleixinselectre:
                onShanquanPicker();
                break;
            case R.id.activity_my_business_district_setshanghui:
                onShangxiPicker();
                break;
            case R.id.activity_my_business_district_set_up_logo_layout://名片
                findImage(0);
                break;
            case R.id.activity_my_business_district_set_up_introduce_layout://简介
                Intent companyIntroduce = new Intent(MyBusinessDistrictSetUpActivity.this, BusinessDistrictSetUpIntroduceActivity.class);
                companyIntroduce.putExtra("introduce", activityMyBusinessDistrictSetUpIntroduceText.getText().toString());
                startActivityForResult(companyIntroduce, REQUEST_INTRODUCE);
                break;
            case R.id.activity_my_business_district_set_up_industry_classify_layout://行业分类
                Intent publishIntent = new Intent(MyBusinessDistrictSetUpActivity.this, BusinessDistrictClassifyActviity.class);
                startActivityForResult(publishIntent, REQUEST_INDUSTRY);
                break;
            case R.id.activity_my_business_district_set_up_member_layout://商圈成员
                Intent intentpeople = new Intent(MyBusinessDistrictSetUpActivity.this, BusinessMemberActivity.class);
                intentpeople.putExtra("id", businessAreaId);
                startActivity(intentpeople);
                break;
            case R.id.activity_my_business_district_set_up_image1:
                findImage(1);
                break;
            case R.id.activity_my_business_district_set_up_image2:
                findImage(2);
                break;
            case R.id.activity_my_business_district_set_up_image3:
                findImage(3);
                break;
            case R.id.activity_my_business_district_set_up_image4:
                findImage(4);
                break;
            case R.id.activity_my_business_district_set_up_image5:
                findImage(5);
                break;
            case R.id.activity_my_business_district_set_up_image6:
                findImage(6);
                break;
            case R.id.activity_my_business_district_set_up_image7:
                findImage(7);
                break;
            case R.id.activity_my_business_district_set_up_image8:
                findImage(8);
                break;
            case R.id.activity_my_business_district_set_up_image9:
                findImage(9);
                break;
            case R.id.activity_my_business_district_set_up_province://省
                onAddressPicker();
                break;
            case R.id.activity_my_business_district_set_up_city://市
                onAddressPicker();
                break;
            case R.id.activity_my_business_district_set_up_district://县
                onAddressPicker();
                break;
            case R.id.activity_my_business_district_set_up_address_choose_layout:
                onAddressPicker();
                break;
            case R.id.activity_my_business_district_set_up_button://提交申请
                if (TextUtil.isEmpty(activityp_industry_classify_text_text.getText().toString())){
                    Toast.makeText(getApplicationContext(), "请选择商会类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtil.isEmpty(image1)){
                    Toast.makeText(getApplicationContext(), "请选择商圈名片", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtil.isEmpty(activityMyBusinessDistrictSetUpNameEdit.getText().toString())){
                    Toast.makeText(getApplicationContext(), "请输入商圈名称", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtil.isEmpty(activityMyBusinessDistrictSetUpIndustryClassifyTextText.getText().toString())){
                    Toast.makeText(getApplicationContext(), "请选择行业类型", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtil.isEmpty(activityMyBusinessDistrictSetUpDetailAddressEdit.getText().toString())){
                    Toast.makeText(getApplicationContext(), "请完善地址", Toast.LENGTH_SHORT).show();
                    return;
                }
//                if (TextUtil.isEmpty(activityp_industry_classify_text_textt.getText().toString())){
//                    Toast.makeText(getApplicationContext(), "请选择商圈类型", Toast.LENGTH_SHORT).show();
//                    return;
//                }

                addInfo();
                break;
            case R.id.activity_company_info_style_layout://风格
                Intent chooseStyle = new Intent(MyBusinessDistrictSetUpActivity.this, BusinessViewChooseActivity.class);//风格
                chooseStyle.putExtra("type",typeid);
                chooseStyle.putExtra("baViewId", baViewId);
                startActivityForResult(chooseStyle, 18);
                break;
        }
    }


    private void addInfo() {
        String name = activityMyBusinessDistrictSetUpNameEdit.getText().toString().trim();//名称
        String introduce = activityMyBusinessDistrictSetUpIntroduceText.getText().toString().trim();//简介
        String provice = activityMyBusinessDistrictSetUpProvince.getText().toString().trim();
        String city = activityMyBusinessDistrictSetUpCity.getText().toString().trim();
        String district = activityMyBusinessDistrictSetUpDistrict.getText().toString().trim();
        String address = activityMyBusinessDistrictSetUpDetailAddressEdit.getText().toString().trim();
        String url;
        RequestParams params = new RequestParams();
        if (isAdd) {
            url = Url.addBusinessDistrict;
            params.put("userId", UserNative.getId());//企业id 1
            params.put("status", 0);
        } else {
            url = Url.editOneBusinessDetail;
            params.put("businessAreaId", businessAreaId);//企业id 1
            params.put("updateAddress", 1);//修改地址
            params.put("address.addressId", addressId);//修改地址
            if (preStatus != 1) {
                params.put("status", 0);
            }
        }
        params.put("businessAreaNm", name);//商圈名称
        params.put("businessAreaGenre", businessAreaGenre);//商圈类型
        params.put("userPhone", UserNative.getPhone());//商圈名称
        params.put("businessAreaDetail", introduce);//商圈描述
        params.put("userPhone", UserNative.getPhone());//商圈名称
        params.put("address.province", provice);//省
        params.put("address.city", city);//市
        //提交更新后不需要审核，注释
//        params.put("status", 0);//市
//        updateAddress = 1
        params.put("address.area", district);//区
        params.put("address.detail", address);//详细地址
        params.put("baViewId", baViewId);//首页风格1
        params.put("userNm", UserNative.getName());//name
        params.put("businessAreaType", activityp_industry_classify_text_text.getText().toString());//首页风格1
        if (!TextUtil.isEmpty(industryId)) {
            params.put("industryId", industryId);
        }
        if (!TextUtil.isEmpty(image1)) {
            params.put("baImage1", image1);//商圈楼够
        }
        if (!TextUtil.isEmpty(image2)) {
            params.put("baImage2", image2);//用户id
        }
        if (!TextUtil.isEmpty(image3)) {
            params.put("baImage3", image3);//用户id
        }
        if (!TextUtil.isEmpty(image4)) {
            params.put("baImage4", image4);//用户id
        }
        if (!TextUtil.isEmpty(image5)) {
            params.put("baImage5", image5);//用户id
        }
        if (!TextUtil.isEmpty(image6)) {
            params.put("baImage6", image6);//用户id
        }
        if (!TextUtil.isEmpty(image7)) {
            params.put("baImage7", image7);//用户id
        }
        if (!TextUtil.isEmpty(image8)) {
            params.put("baImage8", image8);//用户id
        }
        if (!TextUtil.isEmpty(image9)) {
            params.put("baImage9", image9);//用户id
        }
        if (!TextUtil.isEmpty(image10)) {
            params.put("baImage10", image10);//用户id
        }
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
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
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
                    activityMyBusinessDistrictSetUpDistrict.setText(null);
                } else {
                    activityMyBusinessDistrictSetUpDistrict.setText(county.getAreaName());
                }
                activityMyBusinessDistrictSetUpProvince.setText(province.getAreaName());
                activityMyBusinessDistrictSetUpCity.setText(city.getAreaName());
            }
        });
        task.execute("广东", "东莞", "东城");
    }

    //    多图选择开始
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        FcPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void requestPermission() {
        FcPermissions.requestPermissions(this, getString(R.string.prompt_request_storage), FcPermissions.REQ_PER_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionsGranted(int i, List<String> list) {

    }

    @Override
    public void onPermissionsDenied(int i, List<String> list) {
        FcPermissions.checkDeniedPermissionsNeverAskAgain(this,
                getString(R.string.prompt_we_need),
                R.string.setting, R.string.cancel, null, list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("lgq","requestCode==="+requestCode);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int i = 0; i < pathImage.size(); i++) {
                    String path = pathImage.get(0);
                    File compressedImage1File = Compressor.getDefault(this).compressToFile(new File(path));//压缩图片文件
                    ImageManager.loadFilePath(this, path, R.drawable.list_img, chooseImageView(chooseImageIndex));
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, this), "image", compressedImage1File);
                }
            }
        } else if (requestCode == REQUEST_INDUSTRY) {
            if (resultCode == RESULT_OK) {//行业分类
                String industryName = data.getStringExtra("industryName");//行业名称
                industryId = data.getStringExtra("industryId");//对应id
                activityMyBusinessDistrictSetUpIndustryClassifyTextText.setText(industryName);
            }
        } else if (requestCode == REQUEST_INTRODUCE) {//商圈简介
            if (resultCode == RESULT_OK) {
                String BusinessDistrictIntroduce = data.getStringExtra("BusinessDistrictIntroduce");
                activityMyBusinessDistrictSetUpIntroduceText.setText(BusinessDistrictIntroduce);
            }
        }else if (requestCode==18){
            try {
                baViewId = data.getIntExtra("index", 0);
                activityCompanyInfoStyleText.setText("风格" + data.getIntExtra("index", 0));
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private SubscriberOnNextListener fileUploadOnNextListener;
    private String  image1, image2, image3, image4, image5, image6, image7, image8, image9,image10;
    private int chooseImageIndex = -1;//要选择的图片的序号

    /**
     * 找到对应的图片控件
     *
     * @param chooseImageIndex
     * @return
     */
    private ImageView chooseImageView(int chooseImageIndex) {
        if (chooseImageIndex == -1) {
            return activityMyBusinessDistrictSetUpLogoImage;
        }
        switch (chooseImageIndex) {
            case 0:
                return activityMyBusinessDistrictSetUpLogoImage;
            case 1:
                return activityMyBusinessDistrictSetUpImage1;
            case 2:
                return activityMyBusinessDistrictSetUpImage2;
            case 3:
                return activityMyBusinessDistrictSetUpImage3;
            case 4:
                return activityMyBusinessDistrictSetUpImage4;
            case 5:
                return activityMyBusinessDistrictSetUpImage5;
            case 6:
                return activityMyBusinessDistrictSetUpImage6;
            case 7:
                return activityMyBusinessDistrictSetUpImage7;
            case 8:
                return activityMyBusinessDistrictSetUpImage8;
            case 9:
                return activityMyBusinessDistrictSetUpImage9;
            default:
                return activityMyBusinessDistrictSetUpLogoImage;
        }
    }

    /**
     * 查找根据对应控件去找图片
     *
     * @param i
     */
    private void findImage(int i) {
        chooseImageIndex = i;
        requestPermission();
        MultiImageSelector.create(this)
                .showCamera(true) // 是否显示相机. 默认为显示
//                .count(int) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
//                    .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                .start(this, REQUEST_IMAGE);
    }

    /**
     * 保存上传后的图片地址
     *
     * @param chooseImageIndex
     * @param imageUrl
     */
    private void saveImageUrl(int chooseImageIndex, String imageUrl) {
        if (chooseImageIndex == -1) {
            image1 = imageUrl;
        }
        switch (chooseImageIndex) {
            case 0:
                image1 = imageUrl;
                break;
            case 1:
                image2 = imageUrl;
                break;
            case 2:
                image3 = imageUrl;
                break;
            case 3:
                image4 = imageUrl;
                break;
            case 4:
                image5 = imageUrl;
                break;
            case 5:
                image6 = imageUrl;
                break;
            case 6:
                image7 = imageUrl;
                break;
            case 7:
                image8 = imageUrl;
                break;
            case 8:
                image9 = imageUrl;
                break;
            case 9:
                image10 = imageUrl;
                break;
        }
    }

    public void onShangxiPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        ArrayList<String> shangxiList = new ArrayList();//行业
        shangxiList.add("莞系商会");
        shangxiList.add("潮系商会");
        shangxiList.add("浙系商会");
        shangxiList.add("晋系商会");
        shangxiList.add("豫系商会");
        shangxiList.add("卾系商会");
        shangxiList.add("沪系商会");
        shangxiList.add("惠系商会");
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
                activityp_industry_classify_text_text.setText(item);
            }
        });
        picker.show();
    }

    public void onShanquanPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        ArrayList<String> shangxiList = new ArrayList();//行业
        shangxiList.add("商会");
        shangxiList.add("协会");
        shangxiList.add("圈子");
        shangxiList.add("园区");
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
                businessAreaGenre=index+1;
                activityp_industry_classify_text_textt.setText(item);
            }
        });
        picker.show();
    }
}

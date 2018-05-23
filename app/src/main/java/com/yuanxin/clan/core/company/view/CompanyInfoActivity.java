package com.yuanxin.clan.core.company.view;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.company.bean.IndustryEntity;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.My_LoadingDialog;
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

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/2/27.
 * 企业资料
 */
public class CompanyInfoActivity extends BaseActivity implements FcPermissionsCallbacks {

    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_INDUSTRY = 11;//行业分类
    private static final int REQUEST_MANAGE = 12;//经营范围
    private static final int REQUEST_INTRODUCE = 13;//企业简介
    private static final int REQUEST_SHOP = 14;
    private static final int REQUEST_STYLE = 15;
    @BindView(R.id.activity_company_info_left_layout)
    LinearLayout activityCompanyInfoLeftLayout;
    @BindView(R.id.activity_company_info_right_layout)
    LinearLayout activityCompanyInfoRightLayout;
    @BindView(R.id.save_ep_button)
    Button save_ep_button;
    @BindView(R.id.activity_company_info_logo_text)
    TextView activityCompanyInfoLogoText;
    @BindView(R.id.activity_company_info_logo_image)
    ImageView activityCompanyInfoLogoImage;
    @BindView(R.id.activity_company_info_logo_layout)
    RelativeLayout activityCompanyInfoLogoLayout;
    @BindView(R.id.activity_company_info_company_name_text)
    TextView activityCompanyInfoCompanyNameText;
    @BindView(R.id.activity_company_info_company_name_edit)
    EditText activityCompanyInfoCompanyNameEdit;
    @BindView(R.id.activity_company_info_company_name_layout)
    RelativeLayout activityCompanyInfoCompanyNameLayout;
    @BindView(R.id.activity_company_info_company_introduce)
    TextView activityCompanyInfoCompanyIntroduce;
    @BindView(R.id.activity_company_info_company_introduce_text)
    TextView activityCompanyInfoCompanyIntroduceText;
    @BindView(R.id.activity_company_info_company_introduce_image)
    ImageView activityCompanyInfoCompanyIntroduceImage;
    @BindView(R.id.activity_company_info_company_introduce_layout)
    RelativeLayout activityCompanyInfoCompanyIntroduceLayout;
    @BindView(R.id.activity_company_info_manage_area)
    TextView activityCompanyInfoManageArea;
    @BindView(R.id.activity_company_info_manage_area_text)
    TextView activityCompanyInfoManageAreaText;
    @BindView(R.id.activity_company_info_manage_area_image)
    ImageView activityCompanyInfoManageAreaImage;
    @BindView(R.id.activity_company_info_manage_area_layout)
    RelativeLayout activityCompanyInfoManageAreaLayout;
    @BindView(R.id.activity_company_info_company_member)
    TextView activityCompanyInfoCompanyMember;
    @BindView(R.id.activity_company_info_company_member_text)
    TextView activityCompanyInfoCompanyMemberText;
    @BindView(R.id.activity_company_info_company_member_image)
    ImageView activityCompanyInfoCompanyMemberImage;
    @BindView(R.id.activity_company_info_company_member_layout)
    RelativeLayout activityCompanyInfoCompanyMemberLayout;
    @BindView(R.id.activity_company_info_company_type)
    TextView activityCompanyInfoCompanyType;
    @BindView(R.id.activity_company_info_company_type_image)
    ImageView activityCompanyInfoCompanyTypeImage;
    @BindView(R.id.activity_company_info_company_type_layout)
    RelativeLayout activityCompanyInfoCompanyTypeLayout;
    @BindView(R.id.activity_company_info)
    TextView activityCompanyInfo;
    @BindView(R.id.activity_company_info_image)
    ImageView activityCompanyInfoImage;
    @BindView(R.id.activity_company_info_layout)
    RelativeLayout activityCompanyInfoLayout;
    @BindView(R.id.activity_company_info_company_trends)
    TextView activityCompanyInfoCompanyTrends;
    @BindView(R.id.activity_company_info_company_trends_text)
    TextView activityCompanyInfoCompanyTrendsText;
    @BindView(R.id.activity_company_info_company_trends_image)
    ImageView activityCompanyInfoCompanyTrendsImage;
    @BindView(R.id.activity_company_info_company_trends_layout)
    RelativeLayout activityCompanyInfoCompanyTrendsLayout;
    @BindView(R.id.activity_company_info_image1)
    ImageView activityCompanyInfoImage1;
    @BindView(R.id.activity_company_info_image2)
    ImageView activityCompanyInfoImage2;
    @BindView(R.id.activity_company_info_image3)
    ImageView activityCompanyInfoImage3;
    @BindView(R.id.activity_company_info_image4)
    ImageView activityCompanyInfoImage4;
    @BindView(R.id.activity_company_info_image5)
    ImageView activityCompanyInfoImage5;
    @BindView(R.id.activity_company_info_image6)
    ImageView activityCompanyInfoImage6;
    @BindView(R.id.activity_company_info_image7)
    ImageView activityCompanyInfoImage7;
    @BindView(R.id.activity_company_info_image8)
    ImageView activityCompanyInfoImage8;
    @BindView(R.id.activity_company_info_image9)
    ImageView activityCompanyInfoImage9;
    @BindView(R.id.activity_company_info_persion_text)
    TextView activityCompanyInfoPersionText;
    @BindView(R.id.activity_company_info_persion_edit)
    EditText activityCompanyInfoPersionEdit;
    @BindView(R.id.activity_company_info_phone_text)
    TextView activityCompanyInfoPhoneText;
    @BindView(R.id.activity_company_info_phone_edit)
    EditText activityCompanyInfoPhoneEdit;
    @BindView(R.id.activity_login_member_register_province)
    TextView activityLoginMemberRegisterProvince;
    @BindView(R.id.activity_login_member_register_city)
    TextView activityLoginMemberRegisterCity;
    @BindView(R.id.activity_login_member_register_district)
    TextView activityLoginMemberRegisterDistrict;
    @BindView(R.id.activity_login_member_company_address_choose_layout)
    LinearLayout activityLoginMemberCompanyAddressChooseLayout;
    @BindView(R.id.activity_company_info_area_address_text)
    TextView activityCompanyInfoAreaAddressText;
    @BindView(R.id.activity_company_info_area_address_edit)
    EditText activityCompanyInfoAreaAddressEdit;
    @BindView(R.id.activity_company_info_style)
    TextView activityCompanyInfoStyle;
    @BindView(R.id.activity_company_info_style_text)
    TextView activityCompanyInfoStyleText;
    @BindView(R.id.activity_company_info_style_image)
    ImageView activityCompanyInfoStyleImage;
    @BindView(R.id.activity_company_info_style_layout)
    RelativeLayout activityCompanyInfoStyleLayout;

    private int epid;
    private int epViewId;
    private CompanyDetail mCompanyDetail;
    private My_LoadingDialog mMy_loadingDialog;
    private String epAccessPath,viewColor;


    @Override
    public int getViewLayout() {
        return R.layout.activity_company_info;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mMy_loadingDialog = My_LoadingDialog.getInstance(CompanyInfoActivity.this);
        epAccessPath = getIntent().getStringExtra("epAccessPath");
        initOnNext();
        editView();
        getMyCompanyInfo();
    }

    private void editView() {
        activityCompanyInfoCompanyNameEdit.setSelection(activityCompanyInfoCompanyNameEdit.getText().length());
        activityCompanyInfoPersionEdit.setSelection(activityCompanyInfoPersionEdit.getText().length());
        activityCompanyInfoPhoneEdit.setSelection(activityCompanyInfoPhoneEdit.getText().length());
        activityCompanyInfoAreaAddressEdit.setSelection(activityCompanyInfoAreaAddressEdit.getText().length());
    }

    private void getMyCompanyInfo() {
        String url = Url.getCompanyDetail;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        mCompanyDetail = FastJsonUtils.parseObject(object.getString("data"), CompanyDetail.class);

                        String one = object.getString("data");
                        JSONObject mobject = new JSONObject(one);
                        if (mobject.get("epViewId") == null || mobject.get("epViewId").toString().equals("null")) {
                            epViewId = 1;
                        } else {
                            try {
                                epViewId = Integer.parseInt(mobject.get("epViewId").toString());
                            }catch (Exception e) {
                                Logger.e(e.toString());
                            }
                        }
                        String two = mobject.getString("epView");
                        JSONObject tmobject = new JSONObject(two);
                        mCompanyDetail.setEpViewId(epViewId);
                        String epAccessPath = tmobject.getString("epAccessPath");
                        Logger.v(".........."+epAccessPath);
                        mCompanyDetail.setAccessPath(epAccessPath);
                        bindDataView();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void bindDataView() {
        if (mCompanyDetail == null) {
            mCompanyDetail = new CompanyDetail();
        }
        if (mCompanyDetail.getIndustry() == null) {
            mCompanyDetail.setIndustry(new IndustryEntity());
        }
        if (mCompanyDetail.getAddress() == null) {
            mCompanyDetail.setAddress(new CompanyDetail.AddressBean());
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage1())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage1() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoLogoImage);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage2())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage2() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage1);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage3())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage3() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage2);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage4())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage4() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage3);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage5())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage5() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage4);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage6())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage6() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage5);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage7())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage7() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage6);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage8())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage8() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage7);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage9())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage9() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage8);
        }
        if (!TextUtil.isEmpty(mCompanyDetail.getEpImage10())) {
            ImageManager.load(CompanyInfoActivity.this, Url.img_domain + mCompanyDetail.getEpImage10() + Url.imageStyle640x640, R.drawable.personal_center_photo, activityCompanyInfoImage9);
        }
        activityCompanyInfoCompanyNameEdit.setText(mCompanyDetail.getEpNm());//企业名称
        activityCompanyInfoCompanyIntroduceText.setText(mCompanyDetail.getEpDetail());//企业简介
        activityCompanyInfoManageAreaText.setText(mCompanyDetail.getIndustry().getIndustryNm());//行业分类
//        activityCompanyInfoStyleText.setText("风格" + mCompanyDetail.getEpViewId());
        activityCompanyInfoStyleText.setText("风格" + epViewId);
        activityCompanyInfoCompanyMemberText.setText(mCompanyDetail.getEpScope());//经营范围
        String epStore = "";
        String [] epStores = mCompanyDetail.getEpStore().split(",");
        if (epStores.length > 0 ) {
            for (String store: epStores) {
                if (store.equals("2")) {
                    epStore += " 淘宝";
                } else if (store.equals("4")) {
                    epStore += " 京东";
                } else if (store.equals("5")) {
                    epStore += " 阿里巴巴";
                }
            }
        }
//        activityCompanyInfoCompanyTrendsText.setText(mCompanyDetail.getEpStore());
        activityCompanyInfoCompanyTrendsText.setText(epStore);
        activityCompanyInfoPersionEdit.setText(mCompanyDetail.getEpLinkman());//联系人
        activityCompanyInfoPhoneEdit.setText(mCompanyDetail.getEpLinktel());//联系方式
        activityLoginMemberRegisterProvince.setText(mCompanyDetail.getAddress().getProvince());
        activityLoginMemberRegisterCity.setText(mCompanyDetail.getAddress().getCity());
        activityLoginMemberRegisterDistrict.setText(mCompanyDetail.getAddress().getArea());
        activityCompanyInfoAreaAddressEdit.setText(mCompanyDetail.getAddress().getDetail());//详细地址
    }

    private void initOnNext() {//获取企业信息
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);//完整路径
                    for (int b = 0; b < listString.size(); b++) {
                        saveImageUrl(chooseImageIndex, listString.get(b));
                    }
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
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
                    mCompanyDetail.getAddress().setArea("");
                } else {
                    mCompanyDetail.getAddress().setArea(county.getAreaName());
                }
                mCompanyDetail.getAddress().setProvince(province.getAreaName());
                mCompanyDetail.getAddress().setCity(city.getAreaName());
                activityLoginMemberRegisterProvince.setText(mCompanyDetail.getAddress().getProvince());
                activityLoginMemberRegisterCity.setText(mCompanyDetail.getAddress().getCity());
                activityLoginMemberRegisterDistrict.setText(mCompanyDetail.getAddress().getArea());
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
        Log.v("lgq","hjj=="+requestCode);
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int a = 0; a < pathImage.size(); a++) {
                    String path = pathImage.get(0);
                    File compressedImage1File1 = Compressor.getDefault(this).compressToFile(new File(path));//压缩图片文件
                    ImageManager.loadFilePath(this, path, R.drawable.list_img, chooseImageView(chooseImageIndex));
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, CompanyInfoActivity.this), "image", compressedImage1File1);
                }
            }
        } else if (requestCode == REQUEST_INDUSTRY) {
            if (resultCode == RESULT_OK) {//行业分类
                SharedPreferences sharedPreferencesOne = getSharedPreferences("CompanyInfo", Context.MODE_PRIVATE); //私有数据
                SharedPreferences.Editor editorOne = sharedPreferencesOne.edit();//获取编辑器
                editorOne.putString("industryId", String.valueOf(data.getIntExtra("industryId", 0)));//环信昵称
                editorOne.commit();//提交修改
                IndustryEntity temp;
                if (mCompanyDetail.getIndustry() == null) {
                    temp = new IndustryEntity();
                } else {
                    temp = mCompanyDetail.getIndustry();
                }
                temp.setIndustryId(data.getIntExtra("industryId", 0));
                temp.setIndustryNm(data.getStringExtra("industryName"));
                mCompanyDetail.setIndustry(temp);
                mCompanyDetail.setIndustryId(data.getIntExtra("industryId", 0));
                activityCompanyInfoManageAreaText.setText(mCompanyDetail.getIndustry().getIndustryNm());
            }
        } else if (requestCode == REQUEST_MANAGE) {//经营范围
            if (resultCode == RESULT_OK) {
                mCompanyDetail.setEpScope(data.getStringExtra("manageRange"));
                activityCompanyInfoCompanyMemberText.setText(mCompanyDetail.getEpScope());
            }
        } else if (requestCode == REQUEST_INTRODUCE) {//企业简介
            if (resultCode == RESULT_OK) {
                mCompanyDetail.setEpDetail(data.getStringExtra("companyIntroduce"));
                activityCompanyInfoCompanyIntroduceText.setText(mCompanyDetail.getEpDetail());
            }
        } else if (requestCode == REQUEST_SHOP) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> allName = data.getStringArrayListExtra("allName");//淘宝名称
                String shopList = list2string(allName);
                activityCompanyInfoCompanyTrendsText.setText(shopList);
                mCompanyDetail.setEpStore(data.getStringExtra("all"));
            }
        } else if (requestCode == REQUEST_STYLE) {
            Log.v("lgq","hjj=="+resultCode+"..."+RESULT_OK);
                try {
                    epViewId = data.getIntExtra("index", 0);
                    activityCompanyInfoStyleText.setText("风格" + epViewId);
                    mCompanyDetail.setAccessPath(data.getStringExtra("epAccessPath"));
                    viewColor = data.getStringExtra("viewColor");
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }

    public static String list2string(List<String> stringList) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < stringList.size(); i++) {
            if (i == 0) {
                sb.append(stringList.get(i));
            } else {
                sb.append("," + stringList.get(i));
            }
        }
        return sb.toString();
    }


    @OnClick({R.id.activity_company_info_style_layout, R.id.activity_login_member_company_address_choose_layout,
            R.id.activity_company_info_left_layout, R.id.activity_company_info_right_layout, R.id.activity_company_info_logo_layout,
            R.id.activity_company_info_company_name_layout, R.id.activity_company_info_company_introduce_layout, R.id.activity_company_info_manage_area_layout,
            R.id.activity_company_info_company_member_layout, R.id.activity_company_info_company_type_layout,
            R.id.activity_company_info_layout, R.id.activity_company_info_company_trends_layout, R.id.activity_company_info_image1,
            R.id.activity_company_info_image2, R.id.activity_company_info_image3, R.id.activity_company_info_image4,
            R.id.activity_company_info_image5, R.id.activity_company_info_image6, R.id.activity_company_info_image7,
            R.id.activity_company_info_image8, R.id.activity_company_info_image9,R.id.save_ep_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_info_style_layout:
                Intent chooseStyle = new Intent(CompanyInfoActivity.this, ChooseStyleActivity.class);//风格
                chooseStyle.putExtra("type",1);
                chooseStyle.putExtra("epViewId", epViewId);
                chooseStyle.putExtra("epAccessPath", epAccessPath);
//                chooseStyle.putExtra("", );
                startActivityForResult(chooseStyle, REQUEST_STYLE);
                break;
            case R.id.activity_login_member_company_address_choose_layout://请输入省市区
                onAddressPicker();
                break;
            case R.id.activity_company_info_left_layout:
                finish();
                break;
            case R.id.activity_company_info_right_layout://提交
                Intent intent11 = new Intent(CompanyInfoActivity.this, CompanyDetailWebActivity.class);
                intent11.putExtra("epId", mCompanyDetail.getEpId());
                intent11.putExtra("epLinktel", mCompanyDetail.getEpLinktel());
//                        intent.putExtra("epStyleType", mCompanyDetail.getEpView().getEpAccessPath());
                if (TextUtil.isEmpty(mCompanyDetail.getAccessPath())){
                    intent11.putExtra("accessPath", "epdetail");
                }else {
                    intent11.putExtra("accessPath", mCompanyDetail.getAccessPath());
                }
                Log.v("lgq","fhuipath==="+mCompanyDetail.getAccessPath());
                startActivity(intent11);
                break;
            case R.id.save_ep_button:
                if (TextUtil.isEmpty(activityCompanyInfoCompanyNameEdit.getText().toString())) {
                    ToastUtil.showWarning(getApplicationContext(), "请输入公司名称", Toast.LENGTH_SHORT);
                    return;
                }
                mCompanyDetail.setEpNm(activityCompanyInfoCompanyNameEdit.getText().toString());
                mCompanyDetail.getAddress().setDetail(activityCompanyInfoAreaAddressEdit.getText().toString());
                mCompanyDetail.setEpLinkman(activityCompanyInfoPersionEdit.getText().toString());
                mCompanyDetail.setEpLinktel(activityCompanyInfoPhoneEdit.getText().toString());
                SharedPreferences sharedPreferencesOne = getSharedPreferences("CompanyInfo", Context.MODE_PRIVATE); //私有数据
                SharedPreferences.Editor editorOne = sharedPreferencesOne.edit();//获取编辑器
                editorOne.putString("province", mCompanyDetail.getAddress().getProvince());
                editorOne.putString("city", mCompanyDetail.getAddress().getCity());
                editorOne.putString("area", mCompanyDetail.getAddress().getArea());
                editorOne.commit();//提交修改
                addCompanyInfo();
                break;
            case R.id.activity_company_info_logo_layout://企业LOGO
                findImage(0);
                break;
            case R.id.activity_company_info_company_name_layout://企业名称
                break;
            case R.id.activity_company_info_company_introduce_layout://企业简介
                Intent companyIntroduce = new Intent(CompanyInfoActivity.this, CompanyIntroduceActivity.class);
                companyIntroduce.putExtra("introduce", mCompanyDetail.getEpDetail());
                startActivityForResult(companyIntroduce, REQUEST_INTRODUCE);
                break;
            case R.id.activity_company_info_manage_area_layout://行业分类
                Intent publishIntent = new Intent(CompanyInfoActivity.this, IndustryClassifyActviity.class);
                startActivityForResult(publishIntent, REQUEST_INDUSTRY);
                break;
            case R.id.activity_company_info_company_member_layout://经营范围
                Intent manageRange = new Intent(CompanyInfoActivity.this, ManageRangeActivity.class);
                manageRange.putExtra("range", mCompanyDetail.getEpScope());
                startActivityForResult(manageRange, REQUEST_MANAGE);
                break;
            case R.id.activity_company_info_company_type_layout://成员管理
                Intent member = new Intent(CompanyInfoActivity.this, CompanyMemberActivity.class);
                member.putExtra("epIdTwo", mCompanyDetail.getEpId());
                startActivity(member);
                break;
            case R.id.activity_company_info_layout://企业动态
                Intent intent = new Intent(CompanyInfoActivity.this, CompanyInfoListActivity.class);
                intent.putExtra("epId", mCompanyDetail.getEpId());
                startActivity(intent);
                break;
            case R.id.activity_company_info_company_trends_layout://企业店铺
                Intent companyShop = new Intent(CompanyInfoActivity.this, CompanyShopActivity.class);
                companyShop.putExtra("epStore", mCompanyDetail.getEpStore());
                startActivityForResult(companyShop, REQUEST_SHOP);
                break;
            case R.id.activity_company_info_image1:
                findImage(1);
                break;
            case R.id.activity_company_info_image2:
                findImage(2);
                break;
            case R.id.activity_company_info_image3:
                findImage(3);
                break;
            case R.id.activity_company_info_image4:
                findImage(4);
                break;
            case R.id.activity_company_info_image5:
                findImage(5);
                break;
            case R.id.activity_company_info_image6:
                findImage(6);
                break;
            case R.id.activity_company_info_image7:
                findImage(7);
                break;
            case R.id.activity_company_info_image8:
                findImage(8);
                break;
            case R.id.activity_company_info_image9:
                findImage(9);
                break;
        }
    }

    //
    private void addCompanyInfo() {//上传企业信息
        mMy_loadingDialog.show();
        String url = Url.updateCompanyInfo;
        RequestParams params = new RequestParams();
        try {
            params.put("userId", UserNative.getId());
            params.put("userNm", UserNative.getName());
            params.put("epLogo", mCompanyDetail.getEpImage1().replaceFirst(Url.urlHost, ""));
            params.put("epId", mCompanyDetail.getEpId());//企业id 1
            params.put("epNm", mCompanyDetail.getEpNm());//企业名称1
            params.put("epDetail", mCompanyDetail.getEpDetail());//企业详情1
            params.put("industryId", mCompanyDetail.getIndustryId());//所属行业1
            params.put("updateAddress", 1);
//        epid=mCompanyDetail.getEpViewId();
            if (epViewId == 0) {
                epViewId = 1;
            }
            params.put("epViewId", epViewId);//首页风格1
            params.put("viewColor", viewColor);
//        params.put("epViewId", mCompanyDetail.getEpViewId());
            params.put("epScope", mCompanyDetail.getEpScope());//经营范围1
            params.put("epStore", mCompanyDetail.getEpStore());//企业店铺
            params.put("epImage1", mCompanyDetail.getEpImage1().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage2", mCompanyDetail.getEpImage2().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage3", mCompanyDetail.getEpImage3().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage4", mCompanyDetail.getEpImage4().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage5", mCompanyDetail.getEpImage5().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage6", mCompanyDetail.getEpImage6().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage7", mCompanyDetail.getEpImage7().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage8", mCompanyDetail.getEpImage8().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage9", mCompanyDetail.getEpImage9().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epImage10", mCompanyDetail.getEpImage10().replaceFirst(Url.urlHost, ""));//用户id
            params.put("epLinkman", mCompanyDetail.getEpLinkman());//联系人1
            params.put("epLinktel", mCompanyDetail.getEpLinktel());//联系电话1
            params.put("address.province", mCompanyDetail.getAddress().getProvince());//省1
            params.put("address.city", mCompanyDetail.getAddress().getCity());
            params.put("address.area", mCompanyDetail.getAddress().getArea());
            params.put("address.detail", mCompanyDetail.getAddress().getDetail());
        }catch (Exception e) {
            e.printStackTrace();
        }
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.e("lgq","tiajiaofh===="+s);
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        //修改后去查看一下

                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    private SubscriberOnNextListener fileUploadOnNextListener;
    private int chooseImageIndex = -1;//要选择的图片的序号

    /**
     * 找到对应的图片控件
     *
     * @param chooseImageIndex
     * @return
     */
    private ImageView chooseImageView(int chooseImageIndex) {
        if (chooseImageIndex == -1) {
            return activityCompanyInfoLogoImage;
        }
        switch (chooseImageIndex) {
            case 0:
                return activityCompanyInfoLogoImage;
            case 1:
                return activityCompanyInfoImage1;
            case 2:
                return activityCompanyInfoImage2;
            case 3:
                return activityCompanyInfoImage3;
            case 4:
                return activityCompanyInfoImage4;
            case 5:
                return activityCompanyInfoImage5;
            case 6:
                return activityCompanyInfoImage6;
            case 7:
                return activityCompanyInfoImage7;
            case 8:
                return activityCompanyInfoImage8;
            case 9:
                return activityCompanyInfoImage9;
            default:
                return activityCompanyInfoLogoImage;
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
            mCompanyDetail.setEpLogo(imageUrl);
        }
        switch (chooseImageIndex) {
            case 0:
                mCompanyDetail.setEpImage1(imageUrl);
                break;
            case 1:
                mCompanyDetail.setEpImage2(imageUrl);
                break;
            case 2:
                mCompanyDetail.setEpImage3(imageUrl);
                break;
            case 3:
                mCompanyDetail.setEpImage4(imageUrl);
                break;
            case 4:
                mCompanyDetail.setEpImage5(imageUrl);
                break;
            case 5:
                mCompanyDetail.setEpImage6(imageUrl);
                break;
            case 6:
                mCompanyDetail.setEpImage7(imageUrl);
                break;
            case 7:
                mCompanyDetail.setEpImage8(imageUrl);
                break;
            case 8:
                mCompanyDetail.setEpImage9(imageUrl);
                break;
            case 9:
                mCompanyDetail.setEpImage10(imageUrl);
                break;
            default:
                mCompanyDetail.setEpLogo(imageUrl);
                break;
        }
    }
}


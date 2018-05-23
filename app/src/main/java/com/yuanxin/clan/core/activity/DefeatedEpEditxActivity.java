package com.yuanxin.clan.core.activity;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.loopj.android.http.RequestParams;
import com.lypeer.fcpermission.FcPermissions;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
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
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/4/4 0004 13:50
 */

public class DefeatedEpEditxActivity extends BaseActivity {

    @BindView(R.id.actimember_epbsinesste)
    TextView actimember_epbsinesste;
    @BindView(R.id.activity_logiser_jiguangte)
    TextView activity_logiser_jiguangte;
    @BindView(R.id.activity_loister_adresste)
    TextView activity_loister_adresste;
    @BindView(R.id.buyserverte)
    TextView buyserverte;
    @BindView(R.id.gotologinte)
    TextView gotologinte;
    @BindView(R.id.activity_login_epnameet)
    EditText activity_login_epnameet;
    @BindView(R.id.activity_login_epmenet)
    EditText activity_login_epmenet;
    @BindView(R.id.aivity_logister_unameet)
    EditText aivity_logister_unameet;
    @BindView(R.id.activity_logister_adreestwoet)
    EditText activity_logister_adreestwoet;
    @BindView(R.id.activity_zhiweiet)
    EditText activity_zhiweiet;
    @BindView(R.id.activity_logister_maet)
    EditText activity_logister_maet;
    @BindView(R.id.addoneimage)
    ImageView addoneimage;
    @BindView(R.id.addtwoimage)
    ImageView addtwoimage;
    @BindView(R.id.addthreeimage)
    ImageView addthreeimage;
    @BindView(R.id.zhizhaoimage)
    ImageView zhizhaoimage;
    @BindView(R.id.sfidimage)
    ImageView sfidimage;
    @BindView(R.id.sfidtwoimage)
    ImageView sfidtwoimage;
    @BindView(R.id.ifbugimage)
    ImageView ifbugimage;
    @BindView(R.id.deleoneimage)
    ImageView deleoneimage;
    @BindView(R.id.deletwoimage)
    ImageView deletwoimage;
    @BindView(R.id.delethreeimage)
    ImageView delethreeimage;
    @BindView(R.id.commit_register)
    Button commit_register;
    @BindView(R.id.defete)
    TextView defete;

    @BindView(R.id.window_head_left_layout)
    LinearLayout window_head_left_layout;
    @BindView(R.id.defenoseleli)
    LinearLayout defenoseleli;

    private String image1,image2,image3;
    private String epId,epNm,legalNm,epCreditCd,position,nAddressId,addressId;
    private int chooseImageIndex = -1;//要选择的图片的序号
    private static final int REQUEST_IMAGE = 1;
    private SubscriberOnNextListener fileUploadOnNextListener;
    private SubscriberOnNextListener getSmsCodeOnNext, memberRegisterOnNext, getIndustryListOnNextListener;
    private ArrayList<String> industryList = new ArrayList();//行业
    private ArrayList<String> industryListid = new ArrayList();//行业
    private String industryId, industryNm, industryNumber, provinceone, cityone, areaone, nProvince,nCity;
    private More_LoadDialog mMore_loadDialog;
    private String ssimage = "http://images.yxtribe.com/register_service_info.png";
    private boolean ifbuy=true,serviceMall=true;

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }
    @Override
    public int getViewLayout() {
        return R.layout.wanshanzl_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mMore_loadDialog = new More_LoadDialog(this);
        initOnNext();
        String st = getIntent().getStringExtra("st");
        if (!TextUtil.isEmpty(st)){
            defete.setText("未通过原因："+st);
        }
//        defenoseleli.setVisibility(View.INVISIBLE);
        getIndustryList();
        getaddInfo();


    }
    public void initview(){

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
                        industryListid.add(industryId);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    private void getIndustryList() {
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, DefeatedEpEditxActivity.this));
    }

    @OnClick({R.id.window_head_left_layout, R.id.addoneimage,R.id.addtwoimage,R.id.addthreeimage,R.id.commit_register
            ,R.id.actimember_epbsinesste,R.id.activity_logiser_jiguangte,R.id.activity_loister_adresste,R.id.buyserverte,R.id.gotologinte
            ,R.id.delethreeimage,R.id.deleoneimage,R.id.deletwoimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.window_head_left_layout:
                finish();
                break;
            case R.id.addoneimage:
                findImage(1);
                break;
            case R.id.addtwoimage:
                findImage(2);
                break;
            case R.id.addthreeimage:
                findImage(3);
                break;
            case R.id.deleoneimage:
                image1 = "";
                ImageManager.loadFilePath(this, "", R.drawable.list_img, chooseImageView(1));
                addoneimage.setVisibility(View.VISIBLE);
                deleoneimage.setVisibility(View.GONE);
                break;
            case R.id.deletwoimage:
                image2 = "";
                ImageManager.loadFilePath(this, "", R.drawable.list_img, chooseImageView(2));
                addtwoimage.setVisibility(View.VISIBLE);
                deletwoimage.setVisibility(View.GONE);
                break;
            case R.id.delethreeimage:
                image3 = "";
                ImageManager.loadFilePath(this, "", R.drawable.list_img, chooseImageView(3));
                addthreeimage.setVisibility(View.VISIBLE);
                delethreeimage.setVisibility(View.GONE);
                break;
            case R.id.actimember_epbsinesste:
                onClassification0fIndustryPicker();
                break;
            case R.id.activity_logiser_jiguangte:
                onAddressPickerjg(2);
                break;
            case R.id.activity_loister_adresste:
                onAddressPicker(1);
                break;
            case R.id.buyserverte:
                if (ifbuy){
                    ifbugimage.setImageResource(R.drawable.eq_edit_choice_pre);
                    serviceMall = true;
                    dad();
                }else {
                    serviceMall = false;
                    ifbugimage.setImageResource(R.drawable.eq_edit_choice_nomer);
                }
                ifbuy=!ifbuy;

                break;
            case R.id.commit_register:
                if (ifokOutput()){
                    addInfo();
                }
                break;
            case R.id.gotologinte:
                startActivity(new Intent(DefeatedEpEditxActivity.this, LoginActivity.class));
                break;

        }
    }


    private void addInfo() {
        mMore_loadDialog.show();

        String epNm = activity_login_epnameet.getText().toString().trim();//名称
        String legalNm = activity_login_epmenet.getText().toString().trim();//简介
        String epCreditCd = activity_logister_maet.getText().toString().trim();
        String userNm = aivity_logister_unameet.getText().toString().trim();
        String postion = activity_zhiweiet.getText().toString().trim();
        String detail = activity_logister_adreestwoet.getText().toString().trim();
//        String nProvince = activity_logiser_jiguangte.getText().toString().trim();
        String url= Url.updateMemberRegister;
        RequestParams params = new RequestParams();
        params.put("epNm", epNm);//商圈名称
        params.put("legalNm", legalNm);//商圈类型
        params.put("epCreditCd", epCreditCd);//商圈名称
        params.put("epPosition", postion);//商圈描述
        params.put("userNm", userNm);//商圈名称
        params.put("detail", detail);//省
        params.put("nProvince", nProvince);//市
        params.put("nCity", nCity);//区
        params.put("province", provinceone);
        params.put("city", cityone);
        params.put("area", areaone);
        params.put("businessLicense", image1);
        params.put("identityCardFrontage", image2);
        params.put("identityCardVerso", image3);
        params.put("industryId", industryId);
//        Log.i("lgq","huoooo===="+postion);
        params.put("serviceMall", serviceMall);
        params.put("userId", UserNative.getId());
        params.put("addressId", addressId);
        params.put("nAddressId", nAddressId);
        params.put("epId", epId);

        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);
                    Log.i("lgq","huoooo===="+s);
                    if (object.getString("success").equals("true")) {
                        onBackPressed();
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }
    private void getaddInfo() {
        mMore_loadDialog.show();
        String url= Url.getUserEpInfo;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
//        http://192.168.1.200:8080/yuanxinbuluo/user/getUserEpInfo?userId=11455
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                try {
                        JSONObject object = new JSONObject(s);
                    Log.i("lgq","ssssss==="+s);
                        if (object.getString("success").equals("true")) {
                            JSONObject array = object.getJSONObject("data");

                            epId = array.getString("epId");
                            epNm = array.getString("epNm");
                            legalNm = array.getString("legalNm");
                            epCreditCd = array.getString("epCreditCd");
                           String  businessLicense = array.getString("businessLicense");
                           String  identityCardFrontage = array.getString("identityCardFrontage");
                           String  identityCardVerso = array.getString("identityCardVerso");
                            String epLinkman = array.getString("epLinkman");
                            String  industry = array.getString("industry");
                            JSONObject objectindustry = new JSONObject(industry);
                            industryNm = objectindustry.getString("industryNm");
                            String  epUser = array.getString("epUser");
                            JSONObject objectepUser = new JSONObject(epUser);
                            position = objectepUser.getString("position");
                            String  nativePlace = array.getString("nativePlace");
                            JSONObject objectnativePlace = new JSONObject(nativePlace);
                            nAddressId = objectnativePlace.getString("nAddressId");
                            nProvince = objectnativePlace.getString("nProvince");
                            nCity = objectnativePlace.getString("nCity");
                            String jiguang = objectnativePlace.getString("nProvince")+"-"+objectnativePlace.getString("nCity");
                            String  address = array.getString("address");
                            JSONObject objectaddress = new JSONObject(address);

                            String  user = array.getString("user");
                            JSONObject userobjectaddress = new JSONObject(user);
                            String namete = userobjectaddress.getString("userNm");
                            aivity_logister_unameet.setText(namete);
                            addressId = objectaddress.getString("addressId");
                            provinceone = objectaddress.getString("province");
                            cityone = objectaddress.getString("city");
                            areaone = objectaddress.getString("area");
                            String detail = objectaddress.getString("detail");

                            String epadress =provinceone+"-"+cityone+"-"+areaone;

                            activity_login_epnameet.setText(epNm);
                            activity_login_epmenet.setText(legalNm);
                            actimember_epbsinesste.setText(industryNm);
                            activity_logister_maet.setText(epCreditCd);
//                            aivity_logister_unameet.setText(epLinkman);
                            activity_logiser_jiguangte.setText(jiguang);
                            activity_loister_adresste.setText(epadress);

                            if (!TextUtil.isEmpty(detail)) {
                                activity_logister_adreestwoet.setText(detail);
                            }
                            if (!TextUtil.isEmpty(position)) {
                                activity_zhiweiet.setText(position);
                            }
                            if (!TextUtil.isEmpty(businessLicense)) {
                                deleoneimage.setVisibility(View.VISIBLE);
                                addoneimage.setVisibility(View.GONE);
                                String mone;
                                image1 = businessLicense;
                                if (businessLicense.contains("http")){
                                    mone =businessLicense;
                                }else {
                                    mone = Url.img_domain + businessLicense+Url.imageStyle640x640;
                                }
                                ImageManager.load(DefeatedEpEditxActivity.this, mone, R.drawable.banner01, zhizhaoimage);
                            }
                            if (!TextUtil.isEmpty(identityCardFrontage)) {
                                image2 = identityCardFrontage;
                                String mtwo ;
                                if (identityCardFrontage.contains("http")){
                                    mtwo = identityCardFrontage ;
                                }else {
                                    mtwo = Url.img_domain + identityCardFrontage+Url.imageStyle640x640;
                                }
                                ImageManager.load(DefeatedEpEditxActivity.this, mtwo, R.drawable.banner01,sfidimage );
                                deletwoimage.setVisibility(View.VISIBLE);
                                addtwoimage.setVisibility(View.GONE);
                            }
                            if (!TextUtil.isEmpty(identityCardVerso)) {
                                image3 = identityCardVerso;
                                String mthree;
                                if (identityCardVerso.contains("http")){
                                    mthree =identityCardVerso;
                                }else {
                                    mthree = Url.img_domain + identityCardVerso+Url.imageStyle640x640;
                                }
                                ImageManager.load(DefeatedEpEditxActivity.this, mthree, R.drawable.banner01,sfidtwoimage );
                                delethreeimage.setVisibility(View.VISIBLE);
                                addthreeimage.setVisibility(View.GONE);
                            }
                        } else {
                            ToastUtil.showWarning(DefeatedEpEditxActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        }
                } catch (JSONException e) {
                   e.printStackTrace();
                }
            }
        });
    }



    public boolean ifokOutput(){
        if (TextUtil.isEmpty(activity_login_epnameet.getText().toString())){
            ToastUtil.showInfo(this, "请输入企业名称", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(activity_login_epmenet.getText().toString())){
            ToastUtil.showInfo(this, "请输入法人代表", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(industryNm)){
            ToastUtil.showInfo(this, "请选择行业分类", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(activity_logister_maet.getText().toString())){
            ToastUtil.showInfo(this, "请输入营业执照信用码", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(aivity_logister_unameet.getText().toString())){
            ToastUtil.showInfo(this, "请输入姓名", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(activity_logiser_jiguangte.getText().toString())){
            ToastUtil.showInfo(this, "请选择籍贯", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(activity_zhiweiet.getText().toString())){
            ToastUtil.showInfo(this, "请输入职位", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(activity_loister_adresste.getText().toString())){
            ToastUtil.showInfo(this, "请选择公司地址", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(image1)){
            ToastUtil.showInfo(this, "请上传营业执照", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(image2)){
            ToastUtil.showInfo(this, "请上传身份证正面照", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtil.isEmpty(image3)){
            ToastUtil.showInfo(this, "请上传身份证背面照", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
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

    private void requestPermission() {
        FcPermissions.requestPermissions(this, getString(R.string.prompt_request_storage), FcPermissions.REQ_PER_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    /**
     * 找到对应的图片控件
     *
     * @param chooseImageIndex
     * @return
     */
    private ImageView chooseImageView(int chooseImageIndex) {
//        if (chooseImageIndex == -1) {
//            return activityMyBusinessDistrictSetUpLogoImage;
//        }
        switch (chooseImageIndex) {
//            case 0:
//                return activityMyBusinessDistrictSetUpLogoImage;
            case 1:
                return zhizhaoimage;
            case 2:
                return sfidimage;
            case 3:
                return sfidtwoimage;

            default:
                return addoneimage;
        }
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

            case 1:
                image1 = imageUrl;
                addoneimage.setVisibility(View.GONE);
                deleoneimage.setVisibility(View.VISIBLE);
                break;
            case 2:
                image2 = imageUrl;
                addtwoimage.setVisibility(View.GONE);
                deletwoimage.setVisibility(View.VISIBLE);
                break;
            case 3:
                image3 = imageUrl;
                addthreeimage.setVisibility(View.GONE);
                delethreeimage.setVisibility(View.VISIBLE);
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("lgq", "requestCode===" + requestCode);
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
        }
    }

    private void onClassification0fIndustryPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
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
                actimember_epbsinesste.setText(item);
                SharedPreferences share = getSharedPreferences("industryInfo", Activity.MODE_PRIVATE);
                industryId = industryListid.get(index);//行业名称 对应 idString

//                industryPut = Integer.parseInt(industryNumber);//id

//                HttpMethods.getInstance().areaSeek(new ProgressSubscriber(industrySeekNextListener, CompanyInformationDetailActivity.this),item);
            }
        });
        picker.show();
    }
    public void onAddressPicker(final int a) {
        BecomePicktask task = new BecomePicktask(this);
        task.setCallback(new BecomePicktask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    provinceone=province.getAreaName();
                    cityone=city.getAreaName();
                } else {
                    provinceone=province.getAreaName();
                    cityone=city.getAreaName();
                    areaone=county.getAreaName();
                    activity_loister_adresste.setText(provinceone+"-"+cityone+"-"+areaone);
                }
            }
        });
        task.execute("广东", "东莞", "东城");
    }

    public void onAddressPickerjg(final int a) {
        AddressPickTask task = new AddressPickTask(this);
        task.setCallback(new AddressPickTask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {

                nProvince=province.getAreaName();
                nCity=city.getAreaName();

                activity_logiser_jiguangte.setText(nProvince+"-"+nCity);

            }
        });
        task.execute("广东", "东莞", "东城");
    }


    //更新dialog
    public void dad() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(DefeatedEpEditxActivity.this);
        View view = inflater.inflate(R.layout.server_detail_dialog, null);
        dialog = new Dialog(DefeatedEpEditxActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

        lp.width = Utils.getScreenWidth(DefeatedEpEditxActivity.this) - 40; // 宽度
        lp.height = Utils.getViewMeasureHeight(view) + 170; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(false);
//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        LinearLayout xx = (LinearLayout)view.findViewById(R.id.xxli);
        ImageView toimage = (ImageView)view.findViewById(R.id.serverdetailimage);
//        TextView co = (TextView)view.findViewById(R.id.qcapkcontent);
        StringBuilder sb = new StringBuilder();

        ImageManager.loadBitmap(DefeatedEpEditxActivity.this, ssimage, R.drawable.bg_imgge, toimage);
        xx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent updateIntent = new Intent(CompleteEpActivity.this, MainActivity.class);
//                CompleteEpActivity.this.startActivity(updateIntent);
                dialog.dismiss();
//                finish();
            }
        });
//        toep.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent updateIntent = new Intent(CompleteEpActivity.this, CompleteEpActivity.class);
////                LoginCommonRegisterActivity.this.startActivity(updateIntent);
//                dialog.dismiss();
////                finish();
//            }
//        });
        dialog.show();
    }

}

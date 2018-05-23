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
import com.yuanxin.clan.core.MainActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.IndustryListEntity;
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.bean.CommdoityDetail;
import com.yuanxin.clan.core.market.bean.OrderCommodity;
import com.yuanxin.clan.core.market.view.BuyOrderActivityWeb;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
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
 * 完善资料
 */

public class CompleteEpActivity extends BaseActivity{

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

    private String image1,image2,image3;
    private String businessid;
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
        getIndustryList();

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
        HttpMethods.getInstance().getIndustryList(new ProgressSubscriber(getIndustryListOnNextListener, CompleteEpActivity.this));
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
                startActivity(new Intent(CompleteEpActivity.this, LoginActivity.class));
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
        String url= Url.memberRegister;
        RequestParams params = new RequestParams();
        params.put("userPhone", UserNative.getPhone());
//        params.put("userPhone", "13164716840");
        params.put("epNm", epNm);
        params.put("legalNm", legalNm);
        params.put("epCreditCd", epCreditCd);
        params.put("epPosition", postion);
        params.put("userNm", userNm);
        params.put("detail", detail);
        params.put("nProvince", nProvince);
        params.put("nCity", nCity);
        params.put("province", provinceone);
        params.put("city", cityone);
        params.put("area", areaone);
        params.put("businessLicense", image1);
        params.put("identityCardFrontage", image2);
        params.put("identityCardVerso", image3);
        params.put("industryId", industryNumber);

        params.put("serviceMall", serviceMall);
//        params.put("userNm", UserNative.getName());
        params.put("userId", UserNative.getId());

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

                    if (object.getString("success").equals("true")) {
                        MyShareUtil.sharedPstring("buyepname",activity_login_epnameet.getText().toString());
//                        MyShareUtil.sharedPstring("buyid",object.getString("data"));
                        if (TextUtil.isEmpty(object.getString("data"))){
                            startActivity(new Intent(CompleteEpActivity.this, MainActivity.class));
                            finish();
                            return;
                        }

                        CommdoityDetail commdoityDetail = new CommdoityDetail();
                        commdoityDetail.setCommodityId(Integer.valueOf(object.getString("data")));
                        commdoityDetail.setCommodityNm("移动官网");
                        commdoityDetail.setCommodityImage1("upload/images/ep/936mall/20170826150133703.jpg");
                        commdoityDetail.setCommodityPrice(Double.valueOf(365));
                        commdoityDetail.setCommodityQuantity(Integer.valueOf(1));
                        commdoityDetail.setCommodityColor("1");
                        commdoityDetail.setCommoditySp("年");
                        commdoityDetail.setCreateId(Integer.valueOf("1130"));
                        goToBuy(commdoityDetail, 1);
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    //立即购买
    private void goToBuy(final CommdoityDetail mCommdoityDetail, int shopDataFrom) {
        if (mCommdoityDetail == null) {
            return;
        }
        String url = Url.postOrderForm;
        mMore_loadDialog.show();
        RequestParams params = new RequestParams();
        params.put("businessId", String.valueOf(mCommdoityDetail.getCreateId()));//商家Id
        params.put("orderPrice", mCommdoityDetail.getCommodityPrice());//价格
        params.put("expressCost", 0);//运费
        params.put("userId", UserNative.getId());//用户Id
        params.put("userNm", UserNative.getName());//商品价钱
        params.put("commodityId", mCommdoityDetail.getCommodityId());//商品id
        params.put("commodityColor", mCommdoityDetail.getCommodityColor());//颜色
        params.put("commoditySp", mCommdoityDetail.getCommoditySp());
        params.put("commodityNumber", mCommdoityDetail.getCommodityQuantity());
        params.put("commodityPrice", mCommdoityDetail.getCommodityPrice());
        params.put("delFlag", 1);
        params.put("shopDataFrom", shopDataFrom);
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMore_loadDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    mMore_loadDialog.dismiss();
                    if (object.getString("success").equals("true")) {
//                        startActivity(new Intent(ExternalWebActivity.this, ShoppingCartActivity.class));
                        //把数据发送到订单页面
                        //把当前的商品数据打包处理
                        List<OrderCommodity> temp = new ArrayList<>();
                        OrderCommodity orderCommdoity = new OrderCommodity();
                        orderCommdoity.setCommodityId(String.valueOf(mCommdoityDetail.getCommodityId()));
                        //设置选择的颜色
                        orderCommdoity.setCommodityColor(mCommdoityDetail.getCommodityColor());
                        //设置选择的规格
                        orderCommdoity.setCommoditySp(mCommdoityDetail.getCommoditySp());
                        orderCommdoity.setCommodityNumber(mCommdoityDetail.getCommodityQuantity());
                        orderCommdoity.setCommodityNm(mCommdoityDetail.getCommodityNm());
                        orderCommdoity.setCommodityImage1Full(mCommdoityDetail.getCommodityImage1());
                        orderCommdoity.setCommodityPrice(mCommdoityDetail.getCommodityPrice());
                        orderCommdoity.setExpressCost(0);//默认0元运费
                        orderCommdoity.setBusinessId(String.valueOf(mCommdoityDetail.getCreateId()));
                        temp.add(orderCommdoity);

                        Intent intent = new Intent(CompleteEpActivity.this, BuyOrderActivityWeb.class);
                        intent.putExtra("orderUuid", object.getString("data"));
                        intent.putExtra("datas", (Serializable) temp);
                        intent.putExtra("type", BuyOrderActivityWeb.SINGLE);
                        intent.putExtra("intype", "fw");
                        intent.putExtra("intypeone", "yes");
                        intent.putExtra("servicetype", "no");
                        startActivity(intent);
                        finish();
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
            ToastUtil.showInfo(this, "请输入营业执照统一社会信用代码", Toast.LENGTH_SHORT);
            return false;
        }
        if (activity_logister_maet.getText().toString().length()<18){
            ToastUtil.showInfo(this, "请输入18位统一社会信用代码", Toast.LENGTH_SHORT);
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
                industryNumber = industryListid.get(index);//行业名称 对应 idString

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
        LayoutInflater inflater = LayoutInflater.from(CompleteEpActivity.this);
        View view = inflater.inflate(R.layout.server_detail_dialog, null);
        dialog = new Dialog(CompleteEpActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

        lp.width = Utils.getScreenWidth(CompleteEpActivity.this) - 40; // 宽度
        lp.height = Utils.getViewMeasureHeight(view) + 170; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(false);
//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        LinearLayout xx = (LinearLayout)view.findViewById(R.id.xxli);
        ImageView toimage = (ImageView)view.findViewById(R.id.serverdetailimage);
//        TextView co = (TextView)view.findViewById(R.id.qcapkcontent);
        StringBuilder sb = new StringBuilder();

        ImageManager.loadBitmap(CompleteEpActivity.this, ssimage, R.drawable.bg_imgge, toimage);
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

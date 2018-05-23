package com.yuanxin.clan.core.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
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
import com.yuanxin.clan.core.extendsclass.AddressPickTask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
 * Created by lenovo1 on 2017/4/19.
 */
//在售商品 在售礼品 编辑
public class EditPresentCustomMadeActivity extends BaseActivity implements FcPermissionsCallbacks {

    private static final int REQUEST_INTRODUCE = 8;//企业简介
    private static final int REQUEST_IMAGE = 1;

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.activity_think_tank_detail_edit_left_layout)
    LinearLayout activityThinkTankDetailEditLeftLayout;
    @BindView(R.id.activity_think_tank_detail_edit_right_layout)
    LinearLayout activityThinkTankDetailEditRightLayout;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_relative_text)
    TextView activityThinkTankDetailEditCompanyNameRelativeText;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_relative_edit)
    EditText activityThinkTankDetailEditCompanyNameRelativeEdit;
    @BindView(R.id.activity_think_tank_detail_edit_name_relative_layout)
    RelativeLayout activityThinkTankDetailEditNameRelativeLayout;
    @BindView(R.id.activity_think_tank_detail_edit_introduce)
    TextView activityThinkTankDetailEditIntroduce;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_text)
    TextView activityThinkTankDetailEditIntroduceText;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_image)
    ImageView activityThinkTankDetailEditIntroduceImage;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_layout)
    RelativeLayout activityThinkTankDetailEditIntroduceLayout;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_text_text)
    TextView activityThinkTankDetailEditCompanyNameTextText;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_edit_edit)
    EditText activityThinkTankDetailEditCompanyNameEditEdit;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_layout)
    RelativeLayout activityThinkTankDetailEditCompanyNameLayout;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text)
    TextView activityHinkTankDetailEditPositionText;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit)
    EditText activityHinkTankDetailEditPositionEdit;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout)
    RelativeLayout activityThinkTankDetailEditPositionLayout;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text_one)
    TextView activityHinkTankDetailEditPositionTextOne;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit_one)
    EditText activityHinkTankDetailEditPositionEditOne;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout_one)
    RelativeLayout activityThinkTankDetailEditPositionLayoutOne;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text_two)
    TextView activityHinkTankDetailEditPositionTextTwo;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit_two)
    EditText activityHinkTankDetailEditPositionEditTwo;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout_two)
    RelativeLayout activityThinkTankDetailEditPositionLayoutTwo;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text_three)
    TextView activityHinkTankDetailEditPositionTextThree;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit_three)
    EditText activityHinkTankDetailEditPositionEditThree;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout_three)
    RelativeLayout activityThinkTankDetailEditPositionLayoutThree;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text_four)
    TextView activityHinkTankDetailEditPositionTextFour;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit_four)
    EditText activityHinkTankDetailEditPositionEditFour;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout_four)
    RelativeLayout activityThinkTankDetailEditPositionLayoutFour;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text_five)
    TextView activityHinkTankDetailEditPositionTextFive;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit_five)
    EditText activityHinkTankDetailEditPositionEditFive;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout_five)
    RelativeLayout activityThinkTankDetailEditPositionLayoutFive;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text_six)
    TextView activityHinkTankDetailEditPositionTextSix;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit_six)
    EditText activityHinkTankDetailEditPositionEditSix;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout_six)
    RelativeLayout activityThinkTankDetailEditPositionLayoutSix;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text_seven)
    TextView activityHinkTankDetailEditPositionTextSeven;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit_seven)
    EditText activityHinkTankDetailEditPositionEditSeven;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout_seven)
    RelativeLayout activityThinkTankDetailEditPositionLayoutSeven;
    @BindView(R.id.activity_hink_tank_detail_edit_position_text_eight)
    TextView activityHinkTankDetailEditPositionTextEight;
    @BindView(R.id.activity_hink_tank_detail_edit_position_edit_eight)
    EditText activityHinkTankDetailEditPositionEditEight;
    @BindView(R.id.activity_think_tank_detail_edit_position_layout_eight)
    RelativeLayout activityThinkTankDetailEditPositionLayoutEight;
    @BindView(R.id.activity_think_tank_detail_edit_image1)
    ImageView activityThinkTankDetailEditImage1;
    @BindView(R.id.activity_think_tank_detail_edit_image2)
    ImageView activityThinkTankDetailEditImage2;
    @BindView(R.id.activity_think_tank_detail_edit_image3)
    ImageView activityThinkTankDetailEditImage3;
    @BindView(R.id.activity_think_tank_detail_edit_image4)
    ImageView activityThinkTankDetailEditImage4;
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
    @BindView(R.id.activity_login_member_register_province)
    TextView activityLoginMemberRegisterProvince;
    @BindView(R.id.activity_login_member_register_city)
    TextView activityLoginMemberRegisterCity;
    @BindView(R.id.activity_login_member_register_district)
    TextView activityLoginMemberRegisterDistrict;
    @BindView(R.id.activity_login_member_register_address_choose_layout)
    LinearLayout activityLoginMemberRegisterAddressChooseLayout;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_text)
    TextView activityThinkTankDetailEditCompanyNameText;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_edit)
    EditText activityThinkTankDetailEditCompanyNameEdit;
    @BindView(R.id.activity_think_tank_detail_edit_name_layout)
    RelativeLayout activityThinkTankDetailEditNameLayout;
    @BindView(R.id.activity_think_tank_detail_edit_button)
    Button activityThinkTankDetailEditButton;
    private String province, city, area, detail, epId, commodityId;
    private int userId;
    private LocalBroadcastManager localBroadcastManager;
    private boolean isAdd = true;


    @Override
    public int getViewLayout() {
        return R.layout.activity_edit_present_custom_made;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        epId = getIntent().getStringExtra("epId");
        commodityId = getIntent().getStringExtra("commodityId");
        if (TextUtil.isEmpty(commodityId)) {
            isAdd = true;
            tvTitle.setText("商品发布");
            activityThinkTankDetailEditRightLayout.setVisibility(View.GONE);
        } else {
            isAdd = false;
            tvTitle.setText("编辑资料");
            activityThinkTankDetailEditRightLayout.setVisibility(View.VISIBLE);
            getMyMarketDetail(epId, commodityId);
        }
        initOnNext();
    }

    private void initOnNext() {
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);
                    for (int b = 0; b < listString.size(); b++) {
                        saveImageUrl(chooseImageIndex, listString.get(b));
                    }
                } else {
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }


    private void getMyMarketDetail(String epId, String commodityId) {
        userId = UserNative.getId();
        String url = Url.getEditMyTwoGiftList;
        RequestParams params = new RequestParams();
        params.put("epId", epId);//
        params.put("commodityId", commodityId);
        params.put("userId ", userId);//1:集市发布 2:礼品定制发布
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray array = object.getJSONArray("data");
                        for (int c = 0; c < array.length(); c++) {
                            JSONObject dataObject = array.getJSONObject(c);
                            //商品名称 具体描述 商品价格 联系方式 三张图片 省 市 区 详细地址
                            String commodityId = dataObject.getString("commodityId");//商品id
                            String commodityColor = dataObject.getString("commodityColor");//商品id
                            activityHinkTankDetailEditPositionEditTwo.setText(commodityColor);//颜色
                            //commoditySpecification 规格
                            String commoditySpecification = dataObject.getString("commoditySpecification");//
                            activityHinkTankDetailEditPositionEditOne.setText(commoditySpecification);
                            String commodityStock = dataObject.getString("commodityStock");//商品id
                            String commodityType = dataObject.getString("commodityType");//商品id
                            String commodityNumber = dataObject.getString("commodityNumber");//商品id
                            String commodityMaterial = dataObject.getString("commodityMaterial");//商品id
                            String commodityStyle = dataObject.getString("commodityStyle");//商品id
                            String commodityWeight = dataObject.getString("commodityWeight");//商品id

                            activityHinkTankDetailEditPositionEditThree.setText(commodityStock);
                            activityHinkTankDetailEditPositionEditFour.setText(commodityType);
                            activityHinkTankDetailEditPositionEditFive.setText(commodityNumber);
                            activityHinkTankDetailEditPositionEditSix.setText(commodityMaterial);
                            activityHinkTankDetailEditPositionEditSeven.setText(commodityStyle);
                            activityHinkTankDetailEditPositionEditEight.setText(commodityWeight);

                            String commodityNm = dataObject.getString("commodityNm");//商品名称
                            activityThinkTankDetailEditCompanyNameRelativeEdit.setText(commodityNm + "");
                            String commodityPrice = dataObject.getString("commodityPrice");//商品单价
                            activityThinkTankDetailEditCompanyNameEditEdit.setText(commodityPrice + "");
                            String commodityDetail = dataObject.getString("commodityDetail");//具体描述
                            activityThinkTankDetailEditIntroduceText.setText(commodityDetail);
                            String commodityImage1 = dataObject.getString("commodityImage1");//图一
                            String imageOne = Url.img_domain + commodityImage1 + Url.imageStyle750x300;
                            ImageManager.load(getApplicationContext(), imageOne, R.drawable.by, activityThinkTankDetailEditImage1);

                            //缺了联系方式
                            String commodityImage2 = dataObject.getString("commodityImage2");//图二
                            String imageTwo = Url.img_domain + commodityImage2 + Url.imageStyle750x300;
                            ImageManager.load(getApplicationContext(), imageTwo, R.drawable.by, activityThinkTankDetailEditImage2);
                            String commodityImage3 = dataObject.getString("commodityImage3");//图三
                            String imageThree = Url.img_domain + commodityImage3 + Url.imageStyle230x160;
                            ImageManager.load(getApplicationContext(), imageThree, R.drawable.by, activityCompanyInfoImage4);

                            String commodityImage4 = dataObject.getString("commodityImage4");//图四
                            String imageFour = Url.img_domain + commodityImage4 + Url.imageStyle230x160;
                            ImageManager.load(getApplicationContext(), imageFour, R.drawable.by, activityCompanyInfoImage5);

                            String commodityImage5 = dataObject.getString("commodityImage5");//图五
                            String imageFive =  Url.img_domain + commodityImage5 + Url.imageStyle230x160;
                            ImageManager.load(getApplicationContext(), imageFive, R.drawable.by, activityCompanyInfoImage6);

                            String commodityImage6 = dataObject.getString("commodityImage6");//图五
                            String imageSix =  Url.img_domain + commodityImage6 + Url.imageStyle230x160;
                            ImageManager.load(getApplicationContext(), imageSix, R.drawable.by, activityCompanyInfoImage7);

                            String commodityImage7 = dataObject.getString("commodityImage7");//图五
                            String imageSeven =  Url.img_domain + commodityImage7 + Url.imageStyle230x160;
                            ImageManager.load(getApplicationContext(), imageSeven, R.drawable.by, activityCompanyInfoImage8);

                            String commodityImage8 = dataObject.getString("commodityImage8");//图五
                            String imageEight =  Url.img_domain + commodityImage8 + Url.imageStyle230x160;
                            ImageManager.load(getApplicationContext(), imageEight, R.drawable.by, activityCompanyInfoImage9);
                            String contact = dataObject.getString("contact");
                            activityHinkTankDetailEditPositionEdit.setText(contact + "");
                            String address = dataObject.getString("address");
                            if (!address.equals("null")) {
                                JSONObject addressObject = dataObject.getJSONObject("address");
                                province = addressObject.getString("province");
                                city = addressObject.getString("city");
                                area = addressObject.getString("area");
                                detail = addressObject.getString("detail");
                                activityLoginMemberRegisterProvince.setText(province);
                                activityLoginMemberRegisterCity.setText(city);
                                activityLoginMemberRegisterDistrict.setText(area);
                                activityThinkTankDetailEditCompanyNameEdit.setText(detail + "");
                            }
                        }
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @OnClick({R.id.activity_company_info_image4, R.id.activity_company_info_image5, R.id.activity_company_info_image6, R.id.activity_company_info_image7, R.id.activity_company_info_image8, R.id.activity_company_info_image9, R.id.activity_think_tank_detail_edit_left_layout, R.id.activity_think_tank_detail_edit_introduce_layout, R.id.activity_think_tank_detail_edit_image1, R.id.activity_think_tank_detail_edit_image2, R.id.activity_login_member_register_province, R.id.activity_login_member_register_city, R.id.activity_login_member_register_district, R.id.activity_login_member_register_address_choose_layout, R.id.activity_think_tank_detail_edit_button, R.id.activity_think_tank_detail_edit_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.activity_think_tank_detail_edit_left_layout:
                finish();
                break;
            case R.id.activity_think_tank_detail_edit_introduce_layout:
                Intent companyIntroduce = new Intent(EditPresentCustomMadeActivity.this, PresentCustomMadeIntroduceActivity.class);
                companyIntroduce.putExtra("introduce", activityThinkTankDetailEditIntroduceText.getText().toString());
                startActivityForResult(companyIntroduce, REQUEST_INTRODUCE);
                break;
            case R.id.activity_think_tank_detail_edit_image1:
                findImage(1);
                break;
            case R.id.activity_think_tank_detail_edit_image2:
                findImage(2);
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
                onAddressPicker();
                break;
            case R.id.activity_think_tank_detail_edit_button://完成
                EditPresentCustom(1);
                //发送广播
                localBroadcastManager = LocalBroadcastManager.getInstance(this);
// 获取实例
                Intent intent = new Intent("com.example.broadcasttest.PRESENT_CUSTOM_FRESH");
                localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                break;
            case R.id.activity_think_tank_detail_edit_right_layout://下架
                EditPresentCustom(0);
                //发送广播
                localBroadcastManager = LocalBroadcastManager.getInstance(this);
// 获取实例
                Intent intentOne = new Intent("com.example.broadcasttest.PRESENT_CUSTOM_FRESH");
                localBroadcastManager.sendBroadcast(intentOne); // 发送本地广播
                break;
        }
    }

    private void EditPresentCustom(int under) {
        String commodityNmOne = activityThinkTankDetailEditCompanyNameRelativeEdit.getText().toString();
        String commodityDetailOne = activityThinkTankDetailEditIntroduceText.getText().toString();
        String commodityPriceOne = activityThinkTankDetailEditCompanyNameEditEdit.getText().toString();
        String userPhoneOne = activityHinkTankDetailEditPositionEdit.getText().toString();
        String commodityStock = activityHinkTankDetailEditPositionEditThree.getText().toString();
        String commodityColor = activityHinkTankDetailEditPositionEditTwo.getText().toString();
        String commoditySpecification = activityHinkTankDetailEditPositionEditOne.getText().toString();
        String commodityNumber = activityHinkTankDetailEditPositionEditFive.getText().toString();
        String commodityMaterial = activityHinkTankDetailEditPositionEditSix.getText().toString();
        String commodityStyle = activityHinkTankDetailEditPositionEditSeven.getText().toString();
        String commodityWeight = activityHinkTankDetailEditPositionEditEight.getText().toString();
        String commodityType = activityHinkTankDetailEditPositionEditFour.getText().toString();
        RequestParams params = new RequestParams();
        String url;
        if (isAdd) {
            url = Url.publishPresentMade;
            params.put("userId", UserNative.getId());//商品名称
        } else {
            url = Url.updateGift;
            params.put("commodityId", commodityId);//(更新)商品ID
        }
        params.put("commodityNm", commodityNmOne);//商品名称
        params.put("commodityPrice", commodityPriceOne);//商品价格
        params.put("commodityDetail", commodityDetailOne);//商品描述
        params.put("epId", UserNative.getEpId());//企业ID
        params.put("colorId", "");//颜色ID
        params.put("specificationId", "");//规格ID
        if (!TextUtil.isEmpty(image1)) {
            params.put("commodityImage1", image1);//图片1
        }
        if (!TextUtil.isEmpty(image2)) {
            params.put("commodityImage2", image2);//商品描述
        }
        if (!TextUtil.isEmpty(image4)) {
            params.put("commodityImage3", image4);//
        }
        if (!TextUtil.isEmpty(image5)) {
            params.put("commodityImage4", image5);//
        }
        if (!TextUtil.isEmpty(image6)) {
            params.put("commodityImage5", image6);//图片1
        }
        if (!TextUtil.isEmpty(image7)) {
            params.put("commodityImage6", image7);//商品描述
        }
        if (!TextUtil.isEmpty(image8)) {
            params.put("commodityImage7", image8);//
        }
        if (!TextUtil.isEmpty(image9)) {
            params.put("commodityImage8", image9);//
        }
        params.put("contact", userPhoneOne);//联系方式
        params.put("under", under);//（上下架 默认1上架
        params.put("commodityStock", commodityStock);//礼品库存
        params.put("commodityColor", commodityColor);//颜色
        params.put("commoditySpecification", commoditySpecification);//规格
        params.put("commodityNumber", commodityNumber);//货号
        params.put("commodityMaterial", commodityMaterial);//材质
        params.put("commodityStyle", commodityStyle);//风格
//        params.put("commoditySize",commoditySize);//尺寸
        params.put("commodityWeight", commodityWeight);//重量
        params.put("commodityType", commodityType);//品牌名称

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
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }
        });
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
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int a = 0; a < pathImage.size(); a++) {
                    String path = pathImage.get(0);
                    File compressedImage1File = Compressor.getDefault(this).compressToFile(new File(path));//压缩图片文件
                    ImageManager.loadFilePath(this, path, R.drawable.list_img, chooseImageView(chooseImageIndex));
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, this), "image", compressedImage1File);
                }
            }
        } else if (requestCode == REQUEST_INTRODUCE) {//具体描述
            if (resultCode == RESULT_OK) {
                String companyIntroduce = data.getStringExtra("companyIntroduce");
                activityThinkTankDetailEditIntroduceText.setText(companyIntroduce);
            }
        }
    }

    public void onAddressPicker() {
        AddressPickTask task = new AddressPickTask(this);
        task.setCallback(new AddressPickTask.Callback() {
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

    private SubscriberOnNextListener fileUploadOnNextListener;
    private String image1, image2, image4, image5, image6, image7, image8, image9;
    private int chooseImageIndex = -1;//要选择的图片的序号

    /**
     * 找到对应的图片控件
     *
     * @param chooseImageIndex
     * @return
     */
    private ImageView chooseImageView(int chooseImageIndex) {
        if (chooseImageIndex == -1) {
            return activityThinkTankDetailEditImage1;
        }
        switch (chooseImageIndex) {
            case 1:
                return activityThinkTankDetailEditImage1;
            case 2:
                return activityThinkTankDetailEditImage2;
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
                return activityThinkTankDetailEditImage1;
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
            case 1:
                image1 = imageUrl;
                break;
            case 2:
                image2 = imageUrl;
                break;
            case 4:
                image4 = imageUrl;
                break;
            case 5:
                image5 = imageUrl;
                break;
            case 6:
                image6 = imageUrl;
                break;
            case 7:
                image7 = imageUrl;
                break;
            case 8:
                image8 = imageUrl;
                break;
            case 9:
                image9 = imageUrl;
                break;
            default:
                image1 = imageUrl;
                break;
        }
    }

}



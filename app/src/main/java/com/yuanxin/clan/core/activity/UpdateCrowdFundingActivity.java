package com.yuanxin.clan.core.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.DatePicker;
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/5/3.
 */
public class UpdateCrowdFundingActivity extends BaseActivity implements FcPermissionsCallbacks {


    private static final int REQUEST_IMAGE = 1;
    private static final int REQUEST_INTRODUCE = 20;//项目简介
    private static final int REQUEST_CHOOSE = 21;//项目简介
    @BindView(R.id.activity_launch_crowd_funding_left_layout)
    LinearLayout activityLaunchCrowdFundingLeftLayout;
    @BindView(R.id.activity_launch_crowd_funding_right_layout)
    LinearLayout activityLaunchCrowdFundingRightLayout;
    @BindView(R.id.activity_launch_crowd_funding_launch_person_text)
    TextView activityLaunchCrowdFundingLaunchPersonText;
    @BindView(R.id.activity_launch_crowd_funding_launch_person_edit)
    TextView activityLaunchCrowdFundingLaunchPersonEdit;
    @BindView(R.id.activity_launch_crowd_funding_launch_person_image)
    ImageView activityLaunchCrowdFundingLaunchPersonImage;
    @BindView(R.id.activity_launch_crowd_funding_launch_person_layout)
    RelativeLayout activityLaunchCrowdFundingLaunchPersonLayout;
    @BindView(R.id.activity_launch_crowd_funding_launch_person_name_text)
    TextView activityLaunchCrowdFundingLaunchPersonNameText;
    @BindView(R.id.activity_launch_crowd_funding_launch_person_name_edit)
    EditText activityLaunchCrowdFundingLaunchPersonNameEdit;
    @BindView(R.id.activity_launch_crowd_funding_launch_person_name_layout)
    RelativeLayout activityLaunchCrowdFundingLaunchPersonNameLayout;
    @BindView(R.id.activity_launch_crowd_funding_introduce_text)
    TextView activityLaunchCrowdFundingIntroduceText;
    @BindView(R.id.activity_launch_crowd_funding_introduce_edit)
    TextView activityLaunchCrowdFundingIntroduceEdit;
    @BindView(R.id.activity_launch_crowd_funding_introduce_image)
    ImageView activityLaunchCrowdFundingIntroduceImage;
    @BindView(R.id.activity_launch_crowd_funding_introduce_layout)
    RelativeLayout activityLaunchCrowdFundingIntroduceLayout;
    @BindView(R.id.activity_launch_crowd_funding_bondsman_text)
    TextView activityLaunchCrowdFundingBondsmanText;
    @BindView(R.id.activity_launch_crowd_funding_bondsman_edit)
    EditText activityLaunchCrowdFundingBondsmanEdit;
    @BindView(R.id.activity_launch_crowd_funding_bondsman_image)
    ImageView activityLaunchCrowdFundingBondsmanImage;
    @BindView(R.id.activity_launch_crowd_funding_bondsman_layout)
    RelativeLayout activityLaunchCrowdFundingBondsmanLayout;
    @BindView(R.id.activity_launch_crowd_funding_bondsman_text_one)
    TextView activityLaunchCrowdFundingBondsmanTextOne;
    @BindView(R.id.activity_launch_crowd_funding_bondsman_edit_one)
    EditText activityLaunchCrowdFundingBondsmanEditOne;
    @BindView(R.id.activity_launch_crowd_funding_bondsman_image_one)
    ImageView activityLaunchCrowdFundingBondsmanImageOne;
    @BindView(R.id.activity_launch_crowd_funding_bondsman_layout_one)
    RelativeLayout activityLaunchCrowdFundingBondsmanLayoutOne;
    @BindView(R.id.activity_launch_crowd_funding_money_text)
    TextView activityLaunchCrowdFundingMoneyText;
    @BindView(R.id.activity_launch_crowd_funding_money_edit)
    EditText activityLaunchCrowdFundingMoneyEdit;
    @BindView(R.id.activity_launch_crowd_funding_money_layout)
    RelativeLayout activityLaunchCrowdFundingMoneyLayout;
    @BindView(R.id.activity_launch_crowd_funding_price_text)
    TextView activityLaunchCrowdFundingPriceText;
    @BindView(R.id.activity_launch_crowd_funding_price_edit)
    EditText activityLaunchCrowdFundingPriceEdit;
    @BindView(R.id.activity_launch_crowd_funding_price_layout)
    RelativeLayout activityLaunchCrowdFundingPriceLayout;
    @BindView(R.id.activity_launch_crowd_funding_time_text)
    TextView activityLaunchCrowdFundingTimeText;
    @BindView(R.id.activity_launch_crowd_funding_time_edit)
    TextView activityLaunchCrowdFundingTimeEdit;
    @BindView(R.id.activity_launch_crowd_funding_time_layout)
    RelativeLayout activityLaunchCrowdFundingTimeLayout;
    @BindView(R.id.activity_company_info_image1)
    ImageView activityCompanyInfoImage1;
    @BindView(R.id.activity_company_info_image2)
    ImageView activityCompanyInfoImage2;
    @BindView(R.id.activity_company_info_image3)
    ImageView activityCompanyInfoImage3;
    @BindView(R.id.activity_company_info_image4)
    ImageView activityCompanyInfoImage4;
    @BindView(R.id.activity_launch_crowd_funding_button)
    Button activityLaunchCrowdFundingButton;


    private String crowdfundType, person, name, detail, importantPerson, total, price, stopTime, financExpertId;
    private int crowdfundId;


    @Override
    public int getViewLayout() {
        return R.layout.activity_update_crowd_funding;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        getInfo();
    }

    private void getInfo() {
        Intent intent = getIntent();
        crowdfundId = intent.getIntExtra("crowdfundId", 0);
        getGankInto(crowdfundId);//获取数据
    }

    private void getGankInto(int crowdfundId) {//要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改
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
                        for (int a = 0; a < jsonObject.length(); a++) {
                            String crowdfundImage1 = jsonObject.getString("crowdfundImage1");
                            String crowdfundImageOne = Url.img_domain + crowdfundImage1 + Url.imageStyle208x208;//图片
                            ImageManager.load(UpdateCrowdFundingActivity.this, crowdfundImageOne, R.drawable.banner01, activityCompanyInfoImage1);
                            String crowdfundImage2 = jsonObject.getString("crowdfundImage2");
                            String crowdfundImageTwo = Url.img_domain + crowdfundImage2 + Url.imageStyle750x350;//图片
                            ImageManager.load(UpdateCrowdFundingActivity.this, crowdfundImageTwo, R.drawable.banner01, activityCompanyInfoImage2);
                            String crowdfundImage3 = jsonObject.getString("crowdfundImage3");
                            String crowdfundImageTree = Url.img_domain + crowdfundImage3 + Url.imageStyle750x350;//图片
                            ImageManager.load(UpdateCrowdFundingActivity.this, crowdfundImageTree, R.drawable.banner01, activityCompanyInfoImage3);
                            String crowdfundImage4 = jsonObject.getString("crowdfundImage4");
                            String crowdfundImageFour = Url.img_domain + crowdfundImage4 + Url.imageStyle750x350;//图片
                            ImageManager.load(UpdateCrowdFundingActivity.this, crowdfundImageFour, R.drawable.banner01, activityCompanyInfoImage4);

                            String schedule = jsonObject.getString("schedule");//进度

                            String participations = jsonObject.getString("participations");//参数人数

//                            activityCrowdFundingHaveMoney.setText("参与人数"+participations);
                            String endDt = jsonObject.getString("endDt");//还剩多少天
                            long data = Long.valueOf(endDt).longValue();

//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            Timestamp ts = new Timestamp(data);
                            String str = sdf.format(ts);
                            activityLaunchCrowdFundingTimeEdit.setText(str);//毫秒转换成日期

                            String crowdfundId = jsonObject.getString("crowdfundId");//众筹id
                            String crowdfundNm = jsonObject.getString("crowdfundNm");//众筹名称
                            activityLaunchCrowdFundingLaunchPersonNameEdit.setText(crowdfundNm);
//                            activityCrowdFundingDetailName.setText(crowdfundNm);
                            String crowdfundSum = jsonObject.getString("crowdfundSum");//已认筹金额

                            int crowdfundSumOne = Integer.valueOf(crowdfundSum);

                            String crowdfundAll = jsonObject.getString("crowdfundAll");//众筹总金额
                            activityLaunchCrowdFundingMoneyEdit.setText(crowdfundAll);
                            int crowdfundAllOne = Integer.valueOf(crowdfundAll);
//                            if (crowdfundSum != 0) {
//                                resultDiv = crowdfundSum / crowdfundAll;
//                            }


                            String crowdfundImage = jsonObject.getString("crowdfundImage");//
                            String crowdfundImageAll = Url.img_domain + crowdfundImage +Url.imageStyle640x640;;
                            String crowdfundDetail = jsonObject.getString("crowdfundDetail");//项目介绍
                            activityLaunchCrowdFundingIntroduceEdit.setText(crowdfundDetail);
                            String crowdfundSingle = jsonObject.getString("crowdfundSingle");//每份多少
                            activityLaunchCrowdFundingPriceEdit.setText(crowdfundSingle);
                            String lawExpertId = jsonObject.getString("lawExpertId");//法律担保人
                            activityLaunchCrowdFundingBondsmanEdit.setText(lawExpertId);
                            String financExpertId = jsonObject.getString("financExpertId");//财务担保人
                            activityLaunchCrowdFundingBondsmanEditOne.setText(financExpertId);
                            crowdfundType = jsonObject.getString("crowdfundType");//众筹类型
                            if (crowdfundType.equals("1")) {
                                crowdfundType = "商圈";
                            } else if (crowdfundType.equals("2")) {
                                crowdfundType = "全部";
                            } else {
                                crowdfundType = "";
                            }
                            activityLaunchCrowdFundingLaunchPersonEdit.setText(crowdfundType);
                        }
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private String timeToString(Long millisecond) {
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp ts = new Timestamp(millisecond);
        str = sdf.format(ts);
        return str;
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
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
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
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, UpdateCrowdFundingActivity.this), "a", compressedImage1File);
                }
            }
        } else if (requestCode == REQUEST_INTRODUCE) {//项目简介
            if (resultCode == RESULT_OK) {
                String companyIntroduce = data.getStringExtra("companyIntroduce");
                activityLaunchCrowdFundingIntroduceEdit.setText(companyIntroduce);
            }
        } else if (requestCode == REQUEST_CHOOSE) {//发布人群
            if (resultCode == RESULT_OK) {//行业分类
                String name = data.getStringExtra("Name");//行业名称
                activityLaunchCrowdFundingLaunchPersonEdit.setText(name);
            }
        }
    }

    @OnClick({R.id.activity_launch_crowd_funding_time_layout, R.id.activity_launch_crowd_funding_left_layout, R.id.activity_launch_crowd_funding_launch_person_layout, R.id.activity_launch_crowd_funding_introduce_layout, R.id.activity_company_info_image1, R.id.activity_company_info_image2, R.id.activity_company_info_image3, R.id.activity_company_info_image4, R.id.activity_launch_crowd_funding_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_launch_crowd_funding_time_layout:
                onYearMonthDayPicker();
                break;
            case R.id.activity_launch_crowd_funding_left_layout:
                finish();
                break;
            case R.id.activity_launch_crowd_funding_launch_person_layout://发布人群
                Intent publishIntent = new Intent(UpdateCrowdFundingActivity.this, ChooseCrowdActviity.class);
                startActivityForResult(publishIntent, REQUEST_CHOOSE);
                break;
            case R.id.activity_launch_crowd_funding_introduce_layout://项目简介
                Intent companyIntroduce = new Intent(UpdateCrowdFundingActivity.this, CrowdFundingIntroduceActivity.class);
                startActivityForResult(companyIntroduce, REQUEST_INTRODUCE);
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
            case R.id.activity_launch_crowd_funding_button://申请发布
                person = activityLaunchCrowdFundingLaunchPersonEdit.getText().toString();
                if (person.equals("商圈")) {
                    person = "1";
                } else if (person.equals("全部")) {
                    person = "2";
                } else {
                    person = "0";
                }
                name = activityLaunchCrowdFundingLaunchPersonNameEdit.getText().toString();
                detail = activityLaunchCrowdFundingIntroduceEdit.getText().toString();
                importantPerson = activityLaunchCrowdFundingBondsmanEdit.getText().toString();//法律担保人
                total = activityLaunchCrowdFundingMoneyEdit.getText().toString();
                price = activityLaunchCrowdFundingPriceEdit.getText().toString();
                stopTime = activityLaunchCrowdFundingTimeEdit.getText().toString();

                financExpertId = activityLaunchCrowdFundingBondsmanEditOne.getText().toString();//财务负责人
                updateCrowdFunding();
                break;
        }
    }

    public void onYearMonthDayPicker() {
        final DatePicker picker = new DatePicker(this);
        picker.setTopPadding(2);
        picker.setRangeStart(2016, 8, 29);
        picker.setRangeEnd(2111, 1, 11);
        picker.setSelectedItem(2050, 10, 14);
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                activityLaunchCrowdFundingTimeEdit.setText(year + "/" + month + "/" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }

    private void updateCrowdFunding() {
        String url = Url.updateCrowdFund;
        RequestParams params = new RequestParams();
        params.put("crowdfundId", crowdfundId);
        params.put("userId", UserNative.getId());
        params.put("area", person);
        params.put("crowdfundNm", name);//众筹名
        params.put("crowdfundDetail", detail);//众筹详情
        params.put("crowdfundType", person);//众筹类型
//        params.put("crowdfundSum", crowdfundSum);//已认筹金额
        params.put("crowdfundAll", total);//众筹总金额
        params.put("crowdfundSingle", price);//众筹单份认筹金额
        if (!TextUtil.isEmpty(image1)) {
            params.put("crowdfundImage1", image1);
        }
        if (!TextUtil.isEmpty(image2)) {
            params.put("crowdfundImage2", image2);
        }
        if (!TextUtil.isEmpty(image3)) {
            params.put("crowdfundImage3", image3);
        }
        if (!TextUtil.isEmpty(image4)) {
            params.put("crowdfundImage4", image4);
        }
        params.put("lawExpertId", importantPerson);//法律负责人
        params.put("financExpertId", financExpertId);//财务负责人
//        params.put("status", pageNumber);//财务负责人
//        params.put("reason", 2);//reason 审核失败原因
        params.put("endDt", stopTime);//结束时间

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
                    } else {

                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }

                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private SubscriberOnNextListener fileUploadOnNextListener;
    private String image1, image2, image3, image4;
    private int chooseImageIndex = -1;//要选择的图片的序号

    /**
     * 找到对应的图片控件
     *
     * @param chooseImageIndex
     * @return
     */
    private ImageView chooseImageView(int chooseImageIndex) {
        if (chooseImageIndex == -1) {
            return activityCompanyInfoImage1;
        }
        switch (chooseImageIndex) {
            case 1:
                return activityCompanyInfoImage1;
            case 2:
                return activityCompanyInfoImage2;
            case 3:
                return activityCompanyInfoImage3;
            case 4:
                return activityCompanyInfoImage4;
            default:
                return activityCompanyInfoImage1;
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
            case 3:
                image3 = imageUrl;
                break;
            case 4:
                image4 = imageUrl;
                break;
            default:
                image1 = imageUrl;
                break;
        }
    }
}



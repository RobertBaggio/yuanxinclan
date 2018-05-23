package com.yuanxin.clan.core.company.view;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
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
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/5/16.
 */
public class CompanyBusinessCardActivity extends BaseActivity implements FcPermissionsCallbacks {
    @BindView(R.id.activity_company_info_left_layout)
    LinearLayout activityCompanyInfoLeftLayout;
    @BindView(R.id.activity_company_info_right_layout)
    LinearLayout activityCompanyInfoRightLayout;
    @BindView(R.id.activity_my_info_head_image)
    ImageView activityMyInfoHeadImage;
    @BindView(R.id.activity_my_info_head_layout)
    RelativeLayout activityMyInfoHeadLayout;
    @BindView(R.id.activity_my_info_text)
    TextView activityMyInfoText;
    @BindView(R.id.activity_my_info_name_edit)
    EditText activityMyInfoNameEdit;
    @BindView(R.id.activity_company_business_card_get_button)
    TextView activityCompanyBusinessCardGetButton;
    @BindView(R.id.item_gank_image)
    ImageView itemGankImage;
    @BindView(R.id.item_gank_desc)
    TextView itemGankDesc;
    @BindView(R.id.item_gank_type)
    TextView itemGankType;
    @BindView(R.id.item_gank_who)
    TextView itemGankWho;
    @BindView(R.id.item_gank_createdAt)
    TextView itemGankCreatedAt;
    @BindView(R.id.item_gank_layout)
    RelativeLayout itemGankLayout;
    @BindView(R.id.activity_company_business_card_talk)
    TextView activityCompanyBusinessCardTalk;

    private static final int REQUEST_IMAGE3 = 5;
    private SubscriberOnNextListener fileUploadOnNextListener;
    private CompanyDetail mCompanyDetail;


    @Override
    public int getViewLayout() {
        return R.layout.activity_company_business_card;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        getMyCompanyInfo();
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
                        if (mCompanyDetail != null) {
                            bindDataView();
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


    private void initOnNext() {//获取企业信息
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {

                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);
                    for (int b = 0; b < listString.size(); b++) {
                        String imageLogo = listString.get(b);
                    Log.v("lgq","log===="+imageLogo);
                        mCompanyDetail.setEpLogo(imageLogo);
                    }
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
        activityMyInfoNameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                activityCompanyBusinessCardTalk.setText(s.toString());
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
        activityMyInfoNameEdit.setText(mCompanyDetail.getEpSlogan());
        if (!TextUtil.isEmpty(mCompanyDetail.getEpLogo())) {
            ImageManager.load(CompanyBusinessCardActivity.this, Url.img_domain+mCompanyDetail.getEpLogo()+Url.imageStyle640x640, R.drawable.list_img, activityMyInfoHeadImage);
            ImageManager.load(CompanyBusinessCardActivity.this, Url.img_domain+mCompanyDetail.getEpLogo()+Url.imageStyle640x640, R.drawable.list_img, itemGankImage);
        }
        itemGankDesc.setText(mCompanyDetail.getEpNm());//公司名称
        itemGankType.setText(mCompanyDetail.getEpLinktel());
        itemGankWho.setText(mCompanyDetail.getAddress().getProvince() + "-" + mCompanyDetail.getAddress().getCity() + "-" + mCompanyDetail.getAddress().getArea());
        itemGankCreatedAt.setText(mCompanyDetail.getAddress().getDetail());
        activityMyInfoNameEdit.setText(mCompanyDetail.getEpSlogan());
    }


    @OnClick({R.id.activity_company_info_left_layout, R.id.activity_my_info_head_image, R.id.activity_company_business_card_get_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_info_left_layout:
                finish();
                break;
            case R.id.activity_my_info_head_image:
                requestPermission();
                MultiImageSelector.create(this)
                        .showCamera(true) // 是否显示相机. 默认为显示
//                .count(int) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
//                    .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(this, REQUEST_IMAGE3);
                break;
            case R.id.activity_company_business_card_get_button://开始生成
                mCompanyDetail.setEpSlogan(activityMyInfoNameEdit.getText().toString());
                addCompanyInfo();
                break;
        }
    }

    private void addCompanyInfo() {//上传企业信息
        String url = Url.updateCompanyInfo;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//企业id 1
        params.put("epId", UserNative.getEpId());//企业id 1
        params.put("epLogo", mCompanyDetail.getEpLogo());//epLogo1
        params.put("epSlogan", mCompanyDetail.getEpSlogan());//epLogo1
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
                        onBackPressed();
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
        if (requestCode == REQUEST_IMAGE3) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int a = 0; a < pathImage.size(); a++) {
                    String path = pathImage.get(0);
                    File compressedImage1File = Compressor.getDefault(this).compressToFile(new File(path));//压缩图片文件
                    ImageManager.loadFilePath(this, path, R.drawable.list_img, activityMyInfoHeadImage);
                    ImageManager.loadFilePath(this, path, R.drawable.list_img, itemGankImage);
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, CompanyBusinessCardActivity.this), "image", compressedImage1File);
                }
            }
        }
    }
}

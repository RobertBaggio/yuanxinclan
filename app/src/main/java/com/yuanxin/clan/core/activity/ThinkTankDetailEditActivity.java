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
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/2/21.
 */
public class ThinkTankDetailEditActivity extends BaseActivity implements FcPermissionsCallbacks {

    private static final int REQUEST_IMAGE = 1;
    @BindView(R.id.activity_think_tank_detail_edit_left_layout)
    LinearLayout activityThinkTankDetailEditLeftLayout;
    @BindView(R.id.activity_think_tank_detail_edit_right_layout)
    LinearLayout activityThinkTankDetailEditRightLayout;
    @BindView(R.id.activity_think_tank_detail_edit_logo_text)
    TextView activityThinkTankDetailEditLogoText;
    @BindView(R.id.activity_think_tank_detail_edit_logo_image)
    ImageView activityThinkTankDetailEditLogoImage;
    @BindView(R.id.activity_think_tank_detail_edit_logo_layout)
    RelativeLayout activityThinkTankDetailEditLogoLayout;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_text)
    TextView activityThinkTankDetailEditCompanyNameText;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_edit)
    EditText activityThinkTankDetailEditCompanyNameEdit;
    @BindView(R.id.activity_think_tank_detail_edit_name_layout)
    RelativeLayout activityThinkTankDetailEditNameLayout;
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
    @BindView(R.id.activity_think_tank_detail_edit_position_text_title_text)
    TextView activityThinkTankDetailEditPositionTextTitleText;
    @BindView(R.id.activity_think_tank_detail_edit_position_text_title_edit)
    EditText activityThinkTankDetailEditPositionTextTitleEdit;
    @BindView(R.id.activity_think_tank_detail_edit_position_text_title_layout)
    RelativeLayout activityThinkTankDetailEditPositionTextTitleLayout;
    @BindView(R.id.activity_think_tank_detail_edit_introduce)
    TextView activityThinkTankDetailEditIntroduce;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_text)
    TextView activityThinkTankDetailEditIntroduceText;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_image)
    ImageView activityThinkTankDetailEditIntroduceImage;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_layout)
    RelativeLayout activityThinkTankDetailEditIntroduceLayout;
    @BindView(R.id.activity_think_tank_detail_edit_image1)
    ImageView activityThinkTankDetailEditImage1;
    @BindView(R.id.activity_think_tank_detail_edit_image2)
    ImageView activityThinkTankDetailEditImage2;
    @BindView(R.id.activity_think_tank_detail_edit_image3)
    ImageView activityThinkTankDetailEditImage3;
    @BindView(R.id.activity_think_tank_detail_edit_image4)
    ImageView activityThinkTankDetailEditImage4;
    @BindView(R.id.activity_think_tank_detail_edit_image5)
    ImageView activityThinkTankDetailEditImage5;
    @BindView(R.id.activity_think_tank_detail_edit_image6)
    ImageView activityThinkTankDetailEditImage6;
    @BindView(R.id.activity_think_tank_detail_edit_button)
    Button activityThinkTankDetailEditButton;

    private static final int REQUEST_INTRODUCE = 8;//企业简介
    private int userId;

    private String expertDetail, state;


    @Override
    public int getViewLayout() {
        return R.layout.activity_think_tank_detail_edit;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        getInfo();
        editView();
        getThinkTankDetailInfo();
    }

    private void getInfo() {
        Intent intent = getIntent();
        state = intent.getStringExtra("state");
    }

    private void editView() {
        activityThinkTankDetailEditCompanyNameEdit.setSelection(activityThinkTankDetailEditCompanyNameEdit.getText().length());
        activityThinkTankDetailEditCompanyNameEditEdit.setSelection(activityThinkTankDetailEditCompanyNameEditEdit.getText().length());
        activityHinkTankDetailEditPositionEdit.setSelection(activityHinkTankDetailEditPositionEdit.getText().length());
        activityThinkTankDetailEditPositionTextTitleEdit.setSelection(activityThinkTankDetailEditPositionTextTitleEdit.getText().length());
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

    private void getThinkTankDetailInfo() {
        userId = UserNative.getId();
        String url = Url.getMyThinkTank;
        RequestParams params = new RequestParams();
        params.put("userId", userId);
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
                        String expertNm = jsonObject.getString("expertNm");//专家名称
                        activityThinkTankDetailEditCompanyNameEdit.setText(TextUtil.formatString(expertNm));
                        expertDetail = jsonObject.getString("expertDetail");//专家简介
                        activityThinkTankDetailEditIntroduceText.setText(TextUtil.formatString(expertDetail));
                        String position = jsonObject.getString("position");//职位
                        activityHinkTankDetailEditPositionEdit.setText(TextUtil.formatString(position));
                        String company = jsonObject.getString("company");//所在公司
                        activityThinkTankDetailEditCompanyNameEditEdit.setText(TextUtil.formatString(company));
                        String title = jsonObject.getString("title");//头衔
                        activityThinkTankDetailEditPositionTextTitleEdit.setText(TextUtil.formatString(title));

                        String image1 = jsonObject.getString("image1");//头像
                        String epImage1New = Url.img_domain + image1 + Url.imageStyle208x208;
                        ImageManager.load(ThinkTankDetailEditActivity.this, epImage1New, R.drawable.by, activityThinkTankDetailEditLogoImage);
                        String image2 = jsonObject.getString("image2");//头像
                        String epImage2New = Url.img_domain + image2 + Url.imageStyle750x300;
                        ImageManager.load(ThinkTankDetailEditActivity.this, epImage2New, R.drawable.by, activityThinkTankDetailEditImage1);
                        String image3 = jsonObject.getString("image3");//头像
                        String epImage3New = Url.img_domain + image3 + Url.imageStyle750x300;
                        ImageManager.load(ThinkTankDetailEditActivity.this, epImage3New, R.drawable.by, activityThinkTankDetailEditImage2);
                        String image4 = jsonObject.getString("image4");//头像
                        String epImage4New = Url.img_domain + image4 + Url.imageStyle230x160;
                        ImageManager.load(ThinkTankDetailEditActivity.this, epImage4New, R.drawable.by, activityThinkTankDetailEditImage3);
                        String image5 = jsonObject.getString("image5");//头像
                        String epImage5New = Url.img_domain + image5 + Url.imageStyle230x160;
                        ImageManager.load(ThinkTankDetailEditActivity.this, epImage5New, R.drawable.by, activityThinkTankDetailEditImage4);
                        String image6 = jsonObject.getString("image6");//头像
                        String epImage6New = Url.img_domain + image6 + Url.imageStyle230x160;
                        ImageManager.load(ThinkTankDetailEditActivity.this, epImage6New, R.drawable.by, activityThinkTankDetailEditImage5);
                        String image7 = jsonObject.getString("image7");//头像
                        String epImage7New = Url.img_domain + image7 + Url.imageStyle230x160;
                        ImageManager.load(ThinkTankDetailEditActivity.this, epImage7New, R.drawable.by, activityThinkTankDetailEditImage6);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @OnClick({R.id.activity_think_tank_detail_edit_left_layout, R.id.activity_think_tank_detail_edit_logo_layout, R.id.activity_think_tank_detail_edit_introduce_layout, R.id.activity_think_tank_detail_edit_image1, R.id.activity_think_tank_detail_edit_image2, R.id.activity_think_tank_detail_edit_image3, R.id.activity_think_tank_detail_edit_image4, R.id.activity_think_tank_detail_edit_image5, R.id.activity_think_tank_detail_edit_image6, R.id.activity_think_tank_detail_edit_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_think_tank_detail_edit_left_layout:
                finish();
                break;
            case R.id.activity_think_tank_detail_edit_logo_layout:
                findImage(0);
                break;
            case R.id.activity_think_tank_detail_edit_introduce_layout://专家简介
                Intent companyIntroduce = new Intent(ThinkTankDetailEditActivity.this, ThinkTankIntroduceActivity.class);
                companyIntroduce.putExtra("introduce", expertDetail);
                startActivityForResult(companyIntroduce, REQUEST_INTRODUCE);
                break;
            case R.id.activity_think_tank_detail_edit_button://资料上传
                String expertNm = activityThinkTankDetailEditCompanyNameEdit.getText().toString();//专家名称
                String expertDetailOne = activityThinkTankDetailEditIntroduceText.getText().toString();//专家简介
                String position = activityHinkTankDetailEditPositionEdit.getText().toString();//职位
                String company = activityThinkTankDetailEditCompanyNameEditEdit.getText().toString();//所在公司
                String title = activityThinkTankDetailEditPositionTextTitleEdit.getText().toString();//头衔
                editThinkTank(expertNm, expertDetailOne, position, company, title);
                break;
            case R.id.activity_think_tank_detail_edit_image1:
                findImage(1);
                break;
            case R.id.activity_think_tank_detail_edit_image2:
                findImage(2);
                break;
            case R.id.activity_think_tank_detail_edit_image3:
                findImage(3);
                break;
            case R.id.activity_think_tank_detail_edit_image4:
                findImage(4);
                break;
            case R.id.activity_think_tank_detail_edit_image5:
                findImage(5);
                break;
            case R.id.activity_think_tank_detail_edit_image6:
                findImage(6);
                break;
        }
    }

    private void editThinkTank(String expertNm, String expertDetailOne, final String position, final String company, final String title) {
        if (!state.equals("4")) {
            userId = UserNative.getId();
            String url = Url.editMyThinkTank;//我的专家认证资料修改
            RequestParams params = new RequestParams();
            params.put("userId", userId);//用户id
            params.put("expertNm", expertNm);//专家名称
            params.put("expertDetail", expertDetailOne);//专家描述
            params.put("position", position);//职位
            params.put("company", company);//所在企业
            params.put("title", title);//头衔
            if (!TextUtil.isEmpty(image0)) {
                params.put("image1", image0);//用户id
            }
            if (!TextUtil.isEmpty(image1)) {
                params.put("image2", image1);//用户id
            }
            if (!TextUtil.isEmpty(image2)) {
                params.put("image3", image2);//用户id
            }
            if (!TextUtil.isEmpty(image3)) {
                params.put("image4", image3);//用户id
            }
            if (!TextUtil.isEmpty(image4)) {
                params.put("image5", image4);//用户id
            }
            if (!TextUtil.isEmpty(image5)) {
                params.put("image6", image5);//用户id
            }
            if (!TextUtil.isEmpty(image6)) {
                params.put("image7", image6);
            }
            params.put("state", state);//审核状态 0 认证中 1通过 2 失败3 删除 4未认证

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
        } else {
            userId = UserNative.getId();
            String url = Url.addExpert;//我的专家认证资料修改
            RequestParams params = new RequestParams();
            params.put("userId", userId);//用户id
            params.put("expertNm", expertNm);//专家名称
            params.put("expertDetail", expertDetailOne);//专家描述
            params.put("position", position);//职位
            params.put("company", company);//所在企业
            params.put("title", title);//头衔
            if (!TextUtil.isEmpty(image0)) {
                params.put("image1", image0);//用户id
            }
            if (!TextUtil.isEmpty(image1)) {
                params.put("image2", image1);//用户id
            }
            if (!TextUtil.isEmpty(image2)) {
                params.put("image3", image2);//用户id
            }
            if (!TextUtil.isEmpty(image3)) {
                params.put("image4", image3);//用户id
            }
            if (!TextUtil.isEmpty(image4)) {
                params.put("image5", image4);//用户id
            }
            if (!TextUtil.isEmpty(image5)) {
                params.put("image6", image5);//用户id
            }
            if (!TextUtil.isEmpty(image6)) {
                params.put("image7", image6);
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
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, ThinkTankDetailEditActivity.this), "image", compressedImage1File);
                }
            }
        } else if (requestCode == REQUEST_INTRODUCE) {//企业简介
            if (resultCode == RESULT_OK) {
                String companyIntroduce = data.getStringExtra("companyIntroduce");
                activityThinkTankDetailEditIntroduceText.setText(TextUtil.formatString(companyIntroduce));
            }
        }
    }

    private SubscriberOnNextListener fileUploadOnNextListener;
    private String image0, image1, image2, image3, image4, image5, image6;
    private int chooseImageIndex = -1;//要选择的图片的序号

    /**
     * 找到对应的图片控件
     *
     * @param chooseImageIndex
     * @return
     */
    private ImageView chooseImageView(int chooseImageIndex) {
        if (chooseImageIndex == -1) {
            return activityThinkTankDetailEditLogoImage;
        }
        switch (chooseImageIndex) {
            case 0:
                return activityThinkTankDetailEditLogoImage;
            case 1:
                return activityThinkTankDetailEditImage1;
            case 2:
                return activityThinkTankDetailEditImage2;
            case 3:
                return activityThinkTankDetailEditImage3;
            case 4:
                return activityThinkTankDetailEditImage4;
            case 5:
                return activityThinkTankDetailEditImage5;
            case 6:
                return activityThinkTankDetailEditImage6;
            default:
                return activityThinkTankDetailEditLogoImage;
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
            image0 = imageUrl;
        }
        switch (chooseImageIndex) {
            case 0:
                image0 = imageUrl;
                break;
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
            case 5:
                image5 = imageUrl;
                break;
            case 6:
                image6 = imageUrl;
                break;
            default:
                image0 = imageUrl;
                break;
        }
    }
}

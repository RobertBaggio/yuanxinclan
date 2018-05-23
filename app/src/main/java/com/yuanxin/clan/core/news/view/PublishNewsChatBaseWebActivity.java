package com.yuanxin.clan.core.news.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.widget.WheelView;
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/4/19.
 */
public class PublishNewsChatBaseWebActivity extends BaseActivity implements FcPermissionsCallbacks {
    @BindView(R.id.activity_think_tank_detail_edit_left_layout)
    LinearLayout activityThinkTankDetailEditLeftLayout;
    @BindView(R.id.activity_think_tank_detail_edit_right_layout)
    LinearLayout activityThinkTankDetailEditRightLayout;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_text)
    TextView activityThinkTankDetailEditCompanyNameText;
    @BindView(R.id.activity_think_tank_detail_edit_company_name_edit)
    EditText activityThinkTankDetailEditCompanyNameEdit;
    @BindView(R.id.activity_think_tank_detail_edit_name_layout)
    RelativeLayout activityThinkTankDetailEditNameLayout;
    @BindView(R.id.activity_think_tank_detail_edit_introduce)
    TextView activityThinkTankDetailEditIntroduce;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_text)
    TextView activityThinkTankDetailEditIntroduceText;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_image)
    ImageView activityThinkTankDetailEditIntroduceImage;
    @BindView(R.id.activity_think_tank_detail_edit_introduce_layout)
    RelativeLayout activityThinkTankDetailEditIntroduceLayout;
    @BindView(R.id.activity_think_tank_detail_edit_detail)
    TextView activityThinkTankDetailEditDetail;
    @BindView(R.id.activity_think_tank_detail_edit_detail_text)
    TextView activityThinkTankDetailEditDetailText;
    @BindView(R.id.activity_think_tank_detail_edit_detail_image)
    ImageView activityThinkTankDetailEditDetailImage;
    @BindView(R.id.activity_think_tank_detail_edit_detail_layout)
    RelativeLayout activityThinkTankDetailEditDetailLayout;
    @BindView(R.id.activity_think_tank_detail_edit_image1)
    ImageView activityThinkTankDetailEditImage1;
    @BindView(R.id.activity_think_tank_detail_edit_button)
    Button activityThinkTankDetailEditButton;
    private static final int REQUEST_IMAGE = 1;
    private String imageLogo, typeNm;
    private SubscriberOnNextListener fileUploadOnNextListener;
    private static final int REQUEST_INTRODUCE = 2;//企业简介
    private ArrayList<String> newsList = new ArrayList();//新闻列表


    @Override
    public int getViewLayout() {
        return R.layout.activity_publish_news_chat_base_web;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        getNewsType();
    }

    private void getNewsType() {
        String url = Url.getNewsType;
        RequestParams params = new RequestParams();
        doHttpGet(url,params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        newsList.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String newsTypeId = dataObject.getString("newsTypeId");
                            String newsTypeNm = dataObject.getString("newsTypeNm");
                            SharedPreferences sharedPreferences = getSharedPreferences("newPublishType", Context.MODE_PRIVATE); //私有数据
                            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                            editor.putString(newsTypeNm, newsTypeId);//名称 id String
                            editor.commit();//提交修改
                            newsList.add(newsTypeNm);
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

    private void initOnNext() {
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {

                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);
                    for (int b = 0; b < listString.size(); b++) {
                        imageLogo = listString.get(b);
                    }
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };

    }

    @OnClick({R.id.activity_think_tank_detail_edit_detail_layout, R.id.activity_think_tank_detail_edit_left_layout, R.id.activity_think_tank_detail_edit_introduce_layout, R.id.activity_think_tank_detail_edit_image1, R.id.activity_think_tank_detail_edit_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_think_tank_detail_edit_detail_layout://详情内容
                Intent companyIntroduce = new Intent(PublishNewsChatBaseWebActivity.this, NewsIntroduceActivity.class);
//                companyIntroduce.putExtra("introduce", epDetail);
                startActivityForResult(companyIntroduce, REQUEST_INTRODUCE);
                break;
            case R.id.activity_think_tank_detail_edit_left_layout:
                finish();
                break;
            case R.id.activity_think_tank_detail_edit_introduce_layout://发布类型
                onConstellationPicker();
                break;
            case R.id.activity_think_tank_detail_edit_image1://图片
                requestPermission();
                MultiImageSelector.create(this)
                        .showCamera(true) // 是否显示相机. 默认为显示
//                .count(int) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
//                    .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(this, REQUEST_IMAGE);
                break;
            case R.id.activity_think_tank_detail_edit_button://确认发布
                String title = activityThinkTankDetailEditCompanyNameEdit.getText().toString();
                String type = activityThinkTankDetailEditIntroduceText.getText().toString();
                String detail = activityThinkTankDetailEditDetailText.getText().toString();
                String image = imageLogo;
                if (TextUtil.isEmpty(title)) {
                    ToastUtil.showInfo(getApplicationContext(), "请编辑标题", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtil.isEmpty(typeNm)) {
                    ToastUtil.showInfo(getApplicationContext(), "请选择类型", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtil.isEmpty(detail)) {
                    ToastUtil.showInfo(getApplicationContext(), "请编辑内容", Toast.LENGTH_SHORT);
                    return;
                }
                publishNews(detail, image, title);
                break;
        }
    }

    private void publishNews(String content, String img, String title) {//还差图片
        String url = Url.publishNews;
        RequestParams params = new RequestParams();
        params.put("createId", UserNative.getId());//发布人id
        params.put("createNm", UserNative.getName());//发布人姓名
        params.put("content", content);//内容
        params.put("img", img);
        params.put("title", title);
        params.put("newsTypeId", typeNm);//新闻类型id 要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改
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

    public void onConstellationPicker() {
        boolean isChinese = Locale.getDefault().getDisplayLanguage().contains("中文");
        OptionPicker picker = new OptionPicker(this, newsList);
//        OptionPicker picker = new OptionPicker(this,
//                isChinese ? new String[]{
//                        "筹资金", "筹人", "筹资源", "公益"
//                } : new String[]{
//                        "Aquarius", "Pisces", "Aries", "Taurus", "Gemini", "Cancer",
//                        "Leo", "Virgo", "Libra", "Scorpio", "Sagittarius", "Capricorn"
//                });
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
        picker.setSelectedIndex(2);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                activityThinkTankDetailEditIntroduceText.setText(item);
                SharedPreferences share = getSharedPreferences("newPublishType", Activity.MODE_PRIVATE);
                typeNm = share.getString(item, "");//行业名称 对应 idString
            }
        });
        picker.show();
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
                    ImageManager.loadFilePath(this, path, R.drawable.list_img, activityThinkTankDetailEditImage1);//文件图片加载
                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, PublishNewsChatBaseWebActivity.this), "image", compressedImage1File);
                }
            }
        } else if (requestCode == REQUEST_INTRODUCE) {//新闻简介
            if (resultCode == RESULT_OK) {
                String companyIntroduce = data.getStringExtra("companyIntroduce");
                activityThinkTankDetailEditDetailText.setText(companyIntroduce);
            }
        }
    }

}

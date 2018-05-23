package com.yuanxin.clan.core.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.hyphenate.easeui.domain.EaseUser;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import com.loopj.android.http.RequestParams;
import com.lypeer.fcpermission.FcPermissions;
import com.lypeer.fcpermission.impl.FcPermissionsCallbacks;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.PreferenceManager;
import com.yuanxin.clan.core.huanxin.UserProfileManager;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FileUtil;
import com.yuanxin.clan.core.util.ImageCacheSavePathUtil;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.MainApplication;
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
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/2/21.
 * 个人资料
 */
public class MyInfoActivity extends BaseActivity implements FcPermissionsCallbacks {

    private static final int REQUEST_IMAGE3 = 5;
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
    @BindView(R.id.activity_my_info_sex_text)
    TextView activityMyInfoSexText;
    @BindView(R.id.activity_my_info_sex)
    TextView activityMyInfoSex;
    @BindView(R.id.activity_my_info_phone_text)
    TextView activityMyInfoPhoneText;
    @BindView(R.id.activity_my_info_phone)
    EditText activityMyInfoPhone;
    @BindView(R.id.activity_my_info_area_text)
    TextView activityMyInfoAreaText;
    @BindView(R.id.activity_my_info_area)
    TextView activityMyInfoArea;
    @BindView(R.id.activity_my_info_title_image)
    ImageView activityMyInfoTitleImage;


    private String  imageLogo, provinceOne, cityOne, areaOne, userSex, sex, addressId;
    private File compressedImage1File;
    private File filePhoto;
    private SubscriberOnNextListener fileUploadOnNextListener;
    private More_LoadDialog mMy_loadingDialog;
    private ProgressDialog pd;
    public final static int MSG_DO_CUTOUT_GET_PIC = 3;
    private int nPreX = 3;
    private int nPreY = 3;
    private int nMultiple;
    private String path;
    private String strTakePicName = "yxbl.jpg";
    private String PhotoPath;

    @Override
    public int getViewLayout() {
        return R.layout.activity_my_info;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        initView();
        mMy_loadingDialog =new  More_LoadDialog(this);
        PhotoPath = ImageCacheSavePathUtil.getImageSavePath(this) + "";
        FileUtil.createFolder(PhotoPath);
    }

    private void initOnNext() {//获取企业信息
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);
                    for (int b = 0; b < listString.size(); b++) {
                        imageLogo = listString.get(b);

                    }

                } else {
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                }
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
            }
        };
    }


    private void initView() {
        userSex = UserNative.getSex();
        if (userSex.equals("null")) {//（0男，1女）
            userSex = "";
        } else if (userSex.equals("0")) {
            userSex = "男";
        } else if (userSex.equals("1")) {
            userSex = "女";
        }
        nMultiple = MainApplication.getnScreenWidth();
        Bitmap picture = encodeAsBitmap(UserNative.getPhone());
        activityMyInfoTitleImage.setImageBitmap(picture);
        ViewGroup.LayoutParams para;
        int screenWidth = com.yuanxin.clan.core.util.Utils.getScreenWidth(MyInfoActivity.this);
        para = activityMyInfoTitleImage.getLayoutParams();
        para.height = para.width = screenWidth/2;
        activityMyInfoTitleImage.setLayoutParams(para);
        provinceOne = UserNative.getProvince();
        cityOne = UserNative.getCity();
        areaOne = UserNative.getArea();
        ImageManager.load(MyInfoActivity.this, UserNative.getImage(), R.drawable.list_img, activityMyInfoHeadImage);
        activityMyInfoNameEdit.setText(TextUtil.formatString(UserNative.getName()));
        activityMyInfoSex.setText(TextUtil.formatString(userSex));
        activityMyInfoPhone.setText(TextUtil.formatString(UserNative.getPhone()));
        if (!TextUtil.isEmpty(provinceOne)) {
            if (provinceOne.equals("全国")) {
                activityMyInfoArea.setText("请选择地区");
            } else {
                activityMyInfoArea.setText(provinceOne + "-" + cityOne + "-" + areaOne);
            }
        }
//        if (!TextUtil.isEmpty(province) && !TextUtil.isEmpty(city) && !TextUtil.isEmpty(area)) {
//        } else {
//            activityMyInfoArea.setText(null);
//        }

        pd = new ProgressDialog(MyInfoActivity.this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("正在提交...");

    }

    //生成二维码代码  传入一个字符串，生成一个二维码的Bitmap
    Bitmap encodeAsBitmap(String str) {
        Bitmap bitmap = null;
        BitMatrix result = null;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            result = multiFormatWriter.encode(str, BarcodeFormat.QR_CODE, 200, 200);
            // 使用 ZXing Android Embedded 要写的代码
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            bitmap = barcodeEncoder.createBitmap(result);
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException iae) { // ?
            return null;
        }

        // 如果不使用 ZXing Android Embedded 的话，要写的代码
//        int w = result.getWidth();
//        int h = result.getHeight();
//        int[] pixels = new int[w * h];
//        for (int y = 0; y < h; y++) {
//            int offset = y * w;
//            for (int x = 0; x < w; x++) {
//                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
//            }
//        }
//        bitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
//        bitmap.setPixels(pixels,0,100,0,0,w,h);

        return bitmap;
    }


    @OnClick({R.id.activity_my_info_sex, R.id.activity_company_info_right_layout, R.id.activity_my_info_title_image, R.id.activity_company_info_left_layout, R.id.activity_my_info_head_layout, R.id.activity_my_info_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_my_info_sex:
                Intent intentOne = new Intent(MyInfoActivity.this, ChooseNanWomanActivity.class);
                startActivityForResult(intentOne, 403);
                break;
            case R.id.activity_company_info_right_layout://提交资料
                String name = activityMyInfoNameEdit.getText().toString().trim();
                if (name.equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入姓名", Toast.LENGTH_SHORT).show();
                    return;
                }
                sex = activityMyInfoSex.getText().toString().trim();
                if (sex.equals("")) {
                    Toast.makeText(getApplicationContext(), "请选择性别", Toast.LENGTH_SHORT).show();
                    return;
                } else if (sex.equals("男")) {
                    sex = "0";
                } else if (sex.equals("女")) {
                    sex = "1";
                }
//                mMy_loadingDialog.show();
                String phone = activityMyInfoPhone.getText().toString().trim();
                editMyInfo(name, sex, phone);                //provinceOne cityOne areaOne  imageLogo 头像
                //上传到后台
//                Intent intent = new Intent();//原来想把头像 名称 传过去的
//                intent.putExtra("name", name);
////                intent.putExtra("image",bmpDefaultPic1);
//                setResult(RESULT_OK, intent);
                break;
            case R.id.activity_my_info_title_image://二维码
                break;
            case R.id.activity_my_info_head_layout://头像
                requestPermission();
                MultiImageSelector.create(this)
                        .showCamera(true) // 是否显示相机. 默认为显示
//                .count(int) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
//                    .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(this, REQUEST_IMAGE3);
                break;
            case R.id.activity_company_info_left_layout://返回
                finish();
                break;
            case R.id.activity_my_info_area:
                onAddressPicker();
                break;
        }
    }

    private void editMyInfo(final String userNm, final String userSex, final String userPhone) {
        addressId = UserNative.getAddressId();
        if (addressId.equals("null")) {
            addressId = "0";
        }
        pd.show();
        String url = Url.editMyInfo;//我的专家认证资料修改
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id 324
        params.put("userNm", userNm);//
        params.put("userSex", userSex);//（0男，1女）
        params.put("userPhone", userPhone);//
        if (!TextUtil.isEmpty(addressId)) {
            params.put("addressId", addressId);//
        }
        if (!TextUtil.isEmpty(provinceOne)) {
            params.put("province", provinceOne);//
        }
        if (!TextUtil.isEmpty(cityOne)) {
            params.put("city", cityOne);//
        }
        if (!TextUtil.isEmpty(areaOne)) {
            params.put("area", areaOne);//
        }
        if (!TextUtil.isEmpty(imageLogo)) {
            params.put("userImage", imageLogo);
        }

        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                pd.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        Log.v("Lgq","111111111111");
                        JSONObject dataObject = object.getJSONObject("data");
                        String userNmThree = dataObject.getString("userNm");
                        String userPhoneThree = dataObject.getString("userPhone");
                        String userSexThree = dataObject.getString("userSex");
                        String addressIdThree = dataObject.getString("addressId");
                        String userImageThree = dataObject.getString("userImage");
                        String userImageAll = Url.img_domain + userImageThree +Url.imageStyle208x208;;//全头像
                        String epRoleNameThree = dataObject.getString("epRoleName");//
                        String roleNmThree = dataObject.getString("roleNm");
                        String companyThree = dataObject.getString("company");
                        String expertPositionThree = dataObject.getString("expertPosition");
                        String epPositionThree = dataObject.getString("epPosition");
                        String epNmThree = dataObject.getString("epNm");
                        if (epNmThree.equals("")) {
                            epNmThree = "null";
                        }
                        String provinceThree = dataObject.getString("province");
                        String cityThree = dataObject.getString("city");
                        String areaThree = dataObject.getString("area");
                        if (areaThree.equals("")) {
                            areaThree = "null";
                        }
                        Log.v("Lgq","2222222222222222");
                        SharedPreferences sharedPreferences = getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("userNm", userNmThree);
                        editor.putString("userPhone", userPhoneThree);
                        editor.putString("userImage", userImageAll);//头像
                        editor.putString("userSex", userSexThree);//性别
                        editor.putString("province", provinceThree);//性别
                        editor.putString("city", cityThree);//性别
                        editor.putString("area", areaThree);//性别
                        editor.putString("company", companyThree);//性别
                        editor.putString("expertPosition", expertPositionThree);//性别
                        editor.putString("epNm", epNmThree);//性别
                        editor.putString("epPosition", epPositionThree);//性别
                        editor.putString("addressId", addressIdThree);//性别
                        editor.commit();//提交修改

                        SharedPreferences sharedPreferencesOne = getSharedPreferences("huanXin", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editorOne = sharedPreferencesOne.edit();//获取编辑器
                        editorOne.putString("userNm", userNmThree);//环信昵称
                        editorOne.putString("userPhone", userPhoneThree);//电话
                        editorOne.putString("userImage", userImageAll);//头像
                        editorOne.commit();//提交修改

                        // 将自己服务器返回的环信账号、昵称和头像URL设置到帮助类中。
                        UserProfileManager userProfileManager = new UserProfileManager();
                        userProfileManager.getCurrentUserInfo().setNick(userNmThree);
                        PreferenceManager.getInstance().setCurrentUserNick(userNmThree);
                        userProfileManager.getCurrentUserInfo().setAvatar(userImageAll);
                        PreferenceManager.getInstance().setCurrentUserAvatar(userImageAll);

                        MyShareUtil.sharedPstring("userImage",userImageAll);
                        EaseUser easeUser = new EaseUser(UserNative.getPhone());
                        easeUser.setAvatar(userImageAll);
                        PreferenceManager.getInstance().setCurrentUserAvatar(userImageAll);
                        pd.dismiss();
                        Intent intent = new Intent();
                        setResult(RESULT_OK, intent);
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    ToastUtil.showWarning(getApplicationContext(), "json 解析出错", Toast.LENGTH_SHORT);
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
                    activityMyInfoArea.setText(null);
                } else {
                    provinceOne = province.getAreaName();
                    cityOne = city.getAreaName();
                    areaOne = county.getAreaName();
                    activityMyInfoArea.setText(provinceOne + "-" + cityOne + "-" + areaOne);
                }
            }
        });
        task.execute("广东", "东莞", "东城");
    }

    public void onAddressPickertwo() {
        BecomePicktask task = new BecomePicktask(this);
        task.setCallback(new BecomePicktask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    provinceOne = province.getAreaName();
                    cityOne = city.getAreaName();
                    if (provinceOne.equals("全国")) {
                        activityMyInfoArea.setText(provinceOne);
                    } else {
                        activityMyInfoArea.setText(provinceOne + "-" + cityOne);
                    }
                } else {
                    provinceOne = province.getAreaName();
                    cityOne = city.getAreaName();
                    areaOne = county.getAreaName();
                    if (provinceOne.equals("全国")) {
                        activityMyInfoArea.setText(provinceOne);
                    } else {
                        activityMyInfoArea.setText(provinceOne + "-" + cityOne + "-" + areaOne);
                    }
                }
            }
        });
        if (TextUtil.isEmpty(provinceOne) || TextUtil.isEmpty(cityOne)) {
            return;
        }
        if (TextUtil.isEmpty(areaOne)) {
            task.execute(provinceOne, cityOne);
        }else {
            task.execute(provinceOne, cityOne, areaOne);
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
        if (requestCode == REQUEST_IMAGE3) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int a = 0; a < pathImage.size(); a++) {
                    path = pathImage.get(0);
//                    compressedImage1File = Compressor.getDefault(this).compressToFile(new File(path));//压缩图片文件
                    compressedImage1File = new File(path);
                    Log.i("lgq","startPhotoZoom======"+compressedImage1File+".....");
                    startPhotoZoom(Uri.fromFile(compressedImage1File));
//                    ImageManager.loadFilePath(this, path, R.drawable.list_img, activityMyInfoHeadImage);
//                    mMy_loadingDialog.show();
//                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, this), "user", compressedImage1File);
                }
            }
        } else if (requestCode == 403) {
            if (resultCode == RESULT_OK) {
                String boy = data.getStringExtra("boy");
                activityMyInfoSex.setText(boy);
            }
        }else if (requestCode==MSG_DO_CUTOUT_GET_PIC){
            if (resultCode == RESULT_OK) {
//                Log.v("lgq", "startPhotoZoom======" + compressedImage1File + "....." + requestCode);
                ImageManager.loadFilePath(this, filePhoto.getPath(), R.drawable.list_img, activityMyInfoHeadImage);
                mMy_loadingDialog.show();
                HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, this), "user", filePhoto);
            }
        }
    }

    //开启截图
    public void startPhotoZoom(Uri uri) {
        strTakePicName = getTime()+"yxbl.jpg";
        filePhoto = new File(PhotoPath, strTakePicName);

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", nPreX);
        intent.putExtra("aspectY", nPreY);
        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", nMultiple);
        intent.putExtra("outputY", nPreY * nMultiple / nPreX);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filePhoto));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        Log.v("lgq","startPhotoZoom=//开启截图====="+compressedImage1File);
        startActivityForResult(intent, MSG_DO_CUTOUT_GET_PIC);
    }

    public String getTime(){
        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳
        String  str=String.valueOf(time);
        return str;
    }
}

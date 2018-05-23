package com.yuanxin.clan.core.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.yuanxin.clan.core.adapter.NewAddGroupsNumberAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FriendListEntity;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.MainApplication;
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
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/5/13.
 * 新建群聊
 */
public class NewGroupsActivity extends BaseActivity implements FcPermissionsCallbacks {
    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_company_info_company_name_text)
    TextView activityCompanyInfoCompanyNameText;
    @BindView(R.id.activity_company_info_company_name_edit)
    EditText activityCompanyInfoCompanyNameEdit;
    @BindView(R.id.activity_company_info_company_name_layout)
    RelativeLayout activityCompanyInfoCompanyNameLayout;
    @BindView(R.id.activity_company_info_company_introduce)
    TextView activityCompanyInfoCompanyIntroduce;
    @BindView(R.id.activity_company_info_company_introduce_text)
    TextView IntroduceText;
    @BindView(R.id.activity_company_info_company_introduce_image)
    ImageView activityCompanyInfoCompanyIntroduceImage;
    @BindView(R.id.activity_company_info_company_introduce_layout)
    RelativeLayout activityCompanyInfoCompanyIntroduceLayout;
    @BindView(R.id.activity_member_head_image)
    MLImageView activityMemberHeadImage;
    @BindView(R.id.activity_company_member_add_layout)
    LinearLayout activityCompanyMemberAddLayout;
    @BindView(R.id.activity_company_member_recycler_view)
    RecyclerView activityCompanyMemberRecyclerView;
    @BindView(R.id.activity_my_info_head_image)
    ImageView activityMyInfoHeadImage;
    @BindView(R.id.activity_my_info_head_layout)
    RelativeLayout activityMyInfoHeadLayout;
    private List<FriendListEntity> friendList = new ArrayList<FriendListEntity>();//选中要结算的购物车商品
    private NewAddGroupsNumberAdapter addGroupsNumberAdapter;
    private String memberNumberList;
    private static final int REQUEST_IMAGE3 = 5;
    private String imageLogo;
    public final static int MSG_DO_CUTOUT_GET_PIC = 3;
    private int nPreX = 3;
    private int nPreY = 3;
    private int nMultiple;
    private File compressedImage1File;
    private String path;
    private More_LoadDialog mMore_loadDialog;

    private SubscriberOnNextListener fileUploadOnNextListener;

    @Override
    public int getViewLayout() {
        return R.layout.activity_new_groups;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
    }

    private void initOnNext() {//获取企业信息
        nMultiple = MainApplication.getnScreenWidth();
        mMore_loadDialog = new More_LoadDialog(this);
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                mMore_loadDialog.dismiss();
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

    private void initView(List<FriendListEntity> friendLists) {
        addGroupsNumberAdapter = new NewAddGroupsNumberAdapter(getApplicationContext(), friendLists);
        addGroupsNumberAdapter.setOnItemClickListener(new NewAddGroupsNumberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }
        });
        activityCompanyMemberRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        activityCompanyMemberRecyclerView.setAdapter(addGroupsNumberAdapter);
        activityCompanyMemberRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityCompanyMemberRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
    }

    @OnClick({R.id.activity_my_info_head_layout, R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout, R.id.activity_company_info_company_name_edit, R.id.activity_company_info_company_introduce_text, R.id.activity_company_info_company_introduce_image, R.id.activity_company_info_company_introduce_layout, R.id.activity_member_head_image, R.id.activity_company_member_add_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_my_info_head_layout:
                requestPermission();
                MultiImageSelector.create(this)
                        .showCamera(true) // 是否显示相机. 默认为显示
//                .count(int) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                        .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
//                    .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                        .start(this, REQUEST_IMAGE3);
                break;
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_right_layout://创建
                String name = activityCompanyInfoCompanyNameEdit.getText().toString();
                String detail = IntroduceText.getText().toString();
                String numberList = memberNumberList;
                Log.v("lgq","创建群参数；、、、"+name+"..."+detail+"..."+numberList);
//                if (TextUtil.isEmpty(name)||TextUtil.isEmpty(detail)||TextUtil.isEmpty(numberList)){
                if (TextUtil.isEmpty(name)||TextUtil.isEmpty(numberList)){
                    ToastUtil.showInfo(getApplicationContext(), "请完善数据", Toast.LENGTH_SHORT);
                    return;
                }
                if (name.length()>16){
                    ToastUtil.showInfo(getApplicationContext(), "名称不得大于16位！", Toast.LENGTH_SHORT);
                    return;
                }
                addGroups(name, detail, numberList);
                break;
            case R.id.activity_company_info_company_introduce_text:
                Intent companyIntroduce = new Intent(NewGroupsActivity.this, NewGroupsIntroduceActivity.class).putExtra("introduce",IntroduceText.getText().toString());
                startActivityForResult(companyIntroduce, 404);
                break;
            case R.id.activity_member_head_image:
                Intent contactList = new Intent(NewGroupsActivity.this, NewOldContactListActivity.class);//没有abcd
                startActivityForResult(contactList, 405);
                break;
            case R.id.activity_company_member_add_layout:
//                startActivity(new Intent(NewGroupsActivity.this,NewContactListActivity.class));
                break;
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
        if (requestCode == 404) {
            if (resultCode == RESULT_OK) {
                String companyIntroduce = data.getStringExtra("companyIntroduce");
                IntroduceText.setText(companyIntroduce);
            }
        }
        if (requestCode == 405) {
            if (resultCode == RESULT_OK) {
                memberNumberList = data.getStringExtra("memberNumberList");
                ArrayList<String> images = data.getStringArrayListExtra("memberImageList");
                ArrayList<String> names = data.getStringArrayListExtra("memberNameList");
                friendList.clear();
                for (int c = 0; c < names.size(); c++) {
                    FriendListEntity entity = new FriendListEntity();
                    entity.setName(names.get(c));
//                    String imageOne= Url.urlHost+images.get(c);
                    entity.setImage(images.get(c));
                    friendList.add(entity);
                }
                initView(friendList);
//                activityCompanyInfoCompanyIntroduceText.setText(companyIntroduce);
            }
        }
        if (requestCode == REQUEST_IMAGE3) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                for (int a = 0; a < pathImage.size(); a++) {
                    path = pathImage.get(0);
                    compressedImage1File = new File(path);
                    startPhotoZoom(Uri.fromFile(compressedImage1File));
//                    compressedImage1File = Compressor.getDefault(this).compressToFile(new File(path));//压缩图片文件
//                    ImageManager.loadFilePath(this, path, R.drawable.list_img, activityMyInfoHeadImage);
//                    HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, NewGroupsActivity.this), "groups", compressedImage1File);
                }
            }
        }else if (requestCode==MSG_DO_CUTOUT_GET_PIC){
            Log.v("lgq","startPhotoZoom======"+compressedImage1File+"....."+requestCode);
            ImageManager.loadFilePath(this, path, R.drawable.list_img, activityMyInfoHeadImage);
            mMore_loadDialog.show();
            HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, this), "user", compressedImage1File);
        }
    }

    //开启截图
    public void startPhotoZoom(Uri uri) {
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
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(compressedImage1File));
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        Log.v("lgq","startPhotoZoom=//开启截图====="+compressedImage1File);
        startActivityForResult(intent, MSG_DO_CUTOUT_GET_PIC);
    }

    //多图选择结束
    private void addGroups(String groupNm, String desc, String users) {
        String url = Url.addGroups;
        RequestParams params = new RequestParams();
        params.put("groupNm", groupNm);
        params.put("owner", UserNative.getPhone());
        params.put("desc", desc);
        params.put("users", users);
        params.put("userId", UserNative.getId());
        params.put("userNm", UserNative.getName());
        params.put("groupImage", imageLogo);
        mMore_loadDialog.show();

        doHttpPost(url, params, new BaseActivity.RequestCallback() {
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

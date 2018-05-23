package com.yuanxin.clan.core.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.EaseConstant;
import com.loopj.android.http.RequestParams;
import com.lypeer.fcpermission.FcPermissions;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.AddGroupsNumberAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FriendListEntity;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.UserProfileActivity;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.manager.PreferenceManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import id.zelory.compressor.Compressor;
import me.nereo.multi_image_selector.MultiImageSelector;
import me.nereo.multi_image_selector.MultiImageSelectorActivity;

/**
 * Created by lenovo1 on 2017/5/18.
 * 群聊设置  群管理
 */
public class GroupChatDetailActivity extends BaseActivity {


    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.shanchutianjiali)
    LinearLayout shanchutianjiali;
    @BindView(R.id.qunchengyuanli)
    LinearLayout qunchengyuanli;
    @BindView(R.id.shanchutianjialitrue)
    LinearLayout shanchutianjialitrue;
    @BindView(R.id.group_chat_detail_image)
    ImageView groupChatDetailImage;
    @BindView(R.id.activity_company_member_recycler_view)
    RecyclerView activityCompanyMemberRecyclerView;
    @BindView(R.id.group_chat_detail_add_button)
    ImageView groupChatDetailAddButton;
    @BindView(R.id.activity_my_info_text_one)
    TextView activityMyInfoTextOne;
    @BindView(R.id.activity_my_info_name_edit_one)
    TextView activityMyInfoNameEditOne;
    @BindView(R.id.activity_my_info_text)
    TextView activityMyInfoText;
    @BindView(R.id.activity_my_info_name_edit)
    TextView activityMyInfoNameEdit;
    @BindView(R.id.activity_company_info_style)
    TextView activityCompanyInfoStyle;
    @BindView(R.id.activity_company_info_style_text)
    TextView activityCompanyInfoStyleText;
    @BindView(R.id.activity_company_info_style_image)
    ImageView activityCompanyInfoStyleImage;
    @BindView(R.id.activity_company_info_style_layout)
    LinearLayout activityCompanyInfoStyleLayout;
    @BindView(R.id.activit_onenamegrorpre)
    RelativeLayout activit_onenamegrorpre;
    @BindView(R.id.group_chat_detail_dissolve_button)
    TextView groupChatDetailDissolveButton;
    @BindView(R.id.group_chat_detail_dissolve_buttontc)
    TextView group_chat_detail_dissolve_buttontc;
    @BindView(R.id.activititle)
    TextView activititle;
    @BindView(R.id.qunzhunamete)
    TextView qunzhunamete;
    @BindView(R.id.textView62)
    TextView textView62;
    @BindView(R.id.numpeoplete)
    TextView numpeoplete;
    @BindView(R.id.group_chat_detail_add_button_one)
    ImageView groupChatDetailAddButtonOne;
    @BindView(R.id.quntoux_image)
    ImageView quntoux_image;
    @BindView(R.id.qunzhuimage)
    ImageView qunzhuimage;
    @BindView(R.id.switch_group_msg)
    Switch mSwitchGroupMsg;
    @BindView(R.id.quntouxre)
    RelativeLayout quntouxre;


    private static final int REQUEST_INTRODUCE = 13;//企业简介
    private static final int REQUEST_IMAGE3 = 5;
    private ArrayList<String> memberNumberList;
    private List<FriendListEntity> friendList = new ArrayList<FriendListEntity>();//选中要结算的购物车商品
    private AddGroupsNumberAdapter addGroupsNumberAdapter;
    private String groupId, groupDetail, groupImage;
    ArrayList<String> phoneList = new ArrayList<>();
    private EMGroup group;
    private int groupType;
    private String groupmin;
    private File compressedImage1File;
    private SubscriberOnNextListener fileUploadOnNextListener;
    private More_LoadDialog mMy_loadingDialog;
    private String groupImagead,adminNm;

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    public int getViewLayout() {
        return R.layout.newgroupset;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mMy_loadingDialog = new More_LoadDialog(GroupChatDetailActivity.this);

        groupId = getIntent().getStringExtra("groupId");
        Log.v("lgq","groupid==="+groupId);
        group = EMClient.getInstance().groupManager().getGroup(groupId);
        if (group != null) {
            //消息免打扰列表
            List<String> ids = getTypeTitle();
            if (ids != null && ids.contains(groupId)) {
                mSwitchGroupMsg.setChecked(true);
            } else {
                mSwitchGroupMsg.setChecked(false);
            }
        }
        initOnNext();
        initView();
        getMyGroupsInfo();
        filledData();
    }

    private void filledData() {
        String url = Url.getFriendsList;
        RequestParams params = new RequestParams();
        params.put("userUUID", groupId);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                Log.v("lgq","filledData==="+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String data = object.getString("data");
                        if (!data.equals("null")) {
                            JSONArray jsonArray = object.getJSONArray("data");
                            friendList.clear();
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject dataObject = jsonArray.getJSONObject(a);
                                String groupDetailImage = dataObject.getString("groupDetailImage");//头像
                                String imgOne = Url.img_domain + groupDetailImage+Url.imageStyle208x208;
                                String groupDetailNm = dataObject.getString("groupDetailNm");//名字
                                String userUuid = dataObject.getString("userUuid");//聊天 电话
                                FriendListEntity entity = new FriendListEntity();
                                entity.setName(groupDetailNm);//名称
                                entity.setImage(imgOne);//头像
                                entity.setPhone(userUuid);//电话
                                friendList.add(entity);
                            }

//                            addGroupsNumberAdapter.notifyDataSetChanged();
//                            LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityCompanyMemberRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
//                            linearParams.height = UIUtils.dip2px(70)*friendList.size()/5+UIUtils.dip2px(70);// 控件的高强制设成20
                            if (friendList.size()==0){
                                numpeoplete.setText("0 人");
                            }else
                            numpeoplete.setText(friendList.size()+1+" 人");
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }
        });

    }

    private void getMyGroupsInfo() {
        String url = Url.getMyGroupsInfo;
        RequestParams params = new RequestParams();
        params.put("groupId", groupId);//用户id
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                Log.v("lgq","getMyGroupsInfo==="+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject dataObject = object.getJSONObject("data");
                        String groupId = dataObject.getString("groupId");//群id
                        String groupImg = dataObject.getString("groupImage");//群头像
                        String adminImage = dataObject.getString("adminImage");//群头像
                        adminNm = dataObject.getString("adminNm");//群头像
                        String groupImageOne = Url.img_domain + groupImg + Url.imageStyle208x208;
                        groupImagead = Url.img_domain + adminImage + Url.imageStyle208x208;
                        String groupNm = dataObject.getString("groupNm");//群
                        groupmin = dataObject.getString("groupAdminUuid");//群
                        String createNm = dataObject.getString("createNm");//群主
                        //groupDetail
                        groupDetail = dataObject.getString("groupDetail");//群主
                        groupImage = groupImg;
                        groupType = dataObject.getInt("groupType");
//                        if (groupType==1||groupType==2){
//                        }else {
//                            shanchutianjiali.setVisibility(View.VISIBLE);
//
//                        }
                        if (!groupmin.equals(UserNative.getPhone())){
//                            shanchutianjialitrue.setVisibility(View.GONE);
                            shanchutianjiali.setVisibility(View.GONE);
                            group_chat_detail_dissolve_buttontc.setVisibility(View.VISIBLE);
                        }else {
//                            shanchutianjiali.setVisibility(View.VISIBLE);
//                            group_chat_detail_dissolve_buttontc.setVisibility(View.VISIBLE);
                            if (groupType==3)
                                groupChatDetailDissolveButton.setVisibility(View.VISIBLE);
                        }
//                        ImageManager.loadCircleImage(getActivity(), UserNative.getImage(), R.drawable.list_img, activityFiveHeadImage);
                        ImageManager.loadBitmap(GroupChatDetailActivity.this, groupImageOne, R.drawable.by, quntoux_image);
//                        ImageManager.loadCircleImage(GroupChatDetailActivity.this, groupImageOne, R.drawable.chat_icon_personal, quntoux_image);
                        ImageManager.loadCircleImage(GroupChatDetailActivity.this, groupImagead, R.drawable.chat_icon_personal, qunzhuimage);
                        Log.v("lgq",".........."+groupImageOne);
                        activityMyInfoNameEditOne.setText(groupId);
                        activityMyInfoNameEdit.setText(groupNm);
                        qunzhunamete.setText(adminNm+" "+"群主");
                        activityCompanyInfoStyleText.setText(groupDetail);
                    } else {
                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    @OnClick({R.id.group_chat_detail_add_button_one,R.id.qunchengyuanli,R.id.qunzhuimage,R.id.group_chat_detail_image,R.id.group_chat_detail_dissolve_buttontc,R.id.activity_my_info_name_edit, R.id.activity_company_info_style_layout, R.id.activity_exchange_phone_left_layout, R.id.group_chat_detail_add_button, R.id.group_chat_detail_dissolve_button, R.id.quntouxre})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.group_chat_detail_add_button_one://删除
                addGroupsNumberAdapter.setEdit();//编辑 自己删除
                break;
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.group_chat_detail_dissolve_buttontc:
                showoutgroupdialog();
                break;
            case R.id.qunchengyuanli:
                FriendListEntity entity = new FriendListEntity();
                entity.setImage(groupImagead);
                entity.setName(adminNm);
                entity.setPhone(groupmin);
                if (friendList.size()==0){
                    Intent companyIntr = new Intent(GroupChatDetailActivity.this, NewGroupsDetailActivity.class);
                    companyIntr.putExtra("groupId", groupId);
                    companyIntr.putExtra("groupType", groupType);
                    companyIntr.putExtra("groupmin", groupmin);
                    companyIntr.putExtra("person_data", (Serializable)friendList);
                    startActivityForResult(companyIntr, REQUEST_INTRODUCE);
                }else {
                    if (!friendList.get(0).getPhone().equals(groupmin)){
                        friendList.add(0,entity);}
                    Intent companyIntr = new Intent(GroupChatDetailActivity.this, NewGroupsDetailActivity.class);
                    companyIntr.putExtra("groupId", groupId);
                    companyIntr.putExtra("groupType", groupType);
                    companyIntr.putExtra("groupmin", groupmin);
                    companyIntr.putExtra("person_data", (Serializable)friendList);
                    startActivityForResult(companyIntr, REQUEST_INTRODUCE);
                }


                break;
            case R.id.group_chat_detail_add_button://添加
                phoneList.clear();
                for (int b = 0; b < friendList.size(); b++) {
                    String phone = friendList.get(b).getPhone();
                    phoneList.add(phone);
                }
                Intent groupIntroduce = new Intent(GroupChatDetailActivity.this, OldContactListActivity.class);
                groupIntroduce.putStringArrayListExtra("memberPhoneList", phoneList);
                startActivityForResult(groupIntroduce, 406);
                break;
            case R.id.group_chat_detail_dissolve_button://解散群聊
                showdelegroupdialog();
                break;
            case R.id.activity_company_info_style_layout://描述
                if (!groupmin.equals(UserNative.getPhone())||1==1){//无接口
                    return;
                }
                Intent companyIntroduce = new Intent(GroupChatDetailActivity.this, GroupIntroduceActivity.class);
                companyIntroduce.putExtra("introduce", groupDetail);
                startActivityForResult(companyIntroduce, REQUEST_INTRODUCE);
                break;
            case R.id.activity_my_info_name_edit:
                Log.v("lgq","..........."+groupType+"...."+groupmin+"...."+UserNative.getPhone());
                if (groupType==3&&groupmin.equals(UserNative.getPhone())){
                    dad();
                }
                break;
            case R.id.group_chat_detail_image:
                if (groupmin.equals(UserNative.getPhone())){
                    return;
                }
                break;
            case R.id.qunzhuimage:
                Intent intent = new Intent(GroupChatDetailActivity.this, UserProfileActivity.class); //点击头像去会员资料
                intent.putExtra(EaseConstant.EXTRA_USER_ID, groupmin);
                intent.putExtra("groupType", groupType);
                startActivity(intent);
                break;
            case R.id.quntouxre:
                if (groupmin.equals(UserNative.getPhone())){
                    requestPermission();
                    MultiImageSelector.create(this)
                            .showCamera(true) // 是否显示相机. 默认为显示
//                .count(int) // 最大选择图片数量, 默认为9. 只有在选择模式为多选时有效
                            .single() // 单选模式
//                    .multi() // 多选模式, 默认模式;
//                    .origin(ArrayList<String>) // 默认已选择图片. 只有在选择模式为多选时有效
                            .start(this, REQUEST_IMAGE3);
                } else {
                    Intent intentPhoto = new Intent();
                    intentPhoto.putExtra("image", Url.img_domain + groupImage + Url.imageStyle640x640);
                    intentPhoto.setClass(GroupChatDetailActivity.this, PhotoScanActivity.class);
                    startActivity(intentPhoto);
                }
                break;
        }
    }
    private void requestPermission() {
        FcPermissions.requestPermissions(this, getString(R.string.prompt_request_storage), FcPermissions.REQ_PER_CODE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE);
    }
    private void showoutgroupdialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定退出该群吗！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        outgroup();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    private void showdelegroupdialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定删除该群吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delegroup();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE3) {
            if (resultCode == RESULT_OK) {
                List<String> pathImage = data.getStringArrayListExtra(MultiImageSelectorActivity.EXTRA_RESULT);
                String path = pathImage.get(0);
                compressedImage1File = Compressor.getDefault(this).compressToFile(new File(path));//压缩图片文件
                ImageManager.loadFilePath(this, path, R.drawable.list_img, quntoux_image);
                HttpMethods.getInstance().fileUpload(new ProgressSubscriber(fileUploadOnNextListener, this), "user", compressedImage1File);
            }
        } else if (requestCode == REQUEST_INTRODUCE) {
            if (resultCode == RESULT_OK) {
                filledData();
//                String companyIntroduce = data.getStringExtra("companyIntroduce");
//                activityCompanyInfoStyleText.setText(companyIntroduce);
            }
        }else if (requestCode == 406) {
            if (resultCode == RESULT_OK) {
                memberNumberList = data.getStringArrayListExtra("memberNumberList");
                if (memberNumberList.size() == 0) {
                    return;
                }
                addGroupsNumber(Utils.list2string(memberNumberList));
                ArrayList<String> images = data.getStringArrayListExtra("memberImageList");//完整路径
                ArrayList<String> names = data.getStringArrayListExtra("memberNameList");
                for (int c = 0; c < names.size(); c++) {
                    FriendListEntity entity = new FriendListEntity();
                    entity.setName(names.get(c));
                    entity.setImage(images.get(c));
                    entity.setPhone(memberNumberList.get(c));
                    if (!friendList.contains(entity)) {
                        friendList.add(entity);
                    }
                }
                addGroupsNumberAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initOnNext() {
        fileUploadOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {

            @Override
            public void onNext(BaseJsonEntity t) {
                Log.v("lgq","shanccc========"+t);
                if (t.getSuccess().equals("true")) {
                    mMy_loadingDialog.dismiss();
                    List<String> listString = JSON.parseArray(t.getData().toString(), String.class);
                    for (int b = 0; b < listString.size(); b++) {
                        groupImage = listString.get(b);
                    }
                    updateGroup(groupId, activityMyInfoNameEdit.getText().toString(), groupDetail, groupImage);
                } else {
                    Toast.makeText(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    private void addGroupsNumber(String memberNumberList) {//添加成员
        String url = Url.addGroupUsers;
        RequestParams params = new RequestParams();
        params.put("groupId", groupId);//用户id
        params.put("users", memberNumberList);//群成员(用户名通过","分割)
        params.put("userId", UserNative.getId());//用户id
        params.put("userNm", UserNative.getName());//用户id

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
                        JSONObject dataObject = object.getJSONObject("data");
                        String groupId = dataObject.getString("groupId");//群id
                        String groupImg = dataObject.getString("groupImage");//群头像
                        String groupImageOne = Url.img_domain + groupImg + Url.imageStyle208x208;
                        String groupNm = dataObject.getString("groupNm");//群
                        String createNm = dataObject.getString("createNm");//群主
                        //groupDetail
                        groupDetail = dataObject.getString("groupDetail");//群主
                        ImageManager.load(GroupChatDetailActivity.this, groupImageOne, R.drawable.chat_icon_personal, groupChatDetailImage);
                        activityMyInfoNameEditOne.setText(groupId);
                        activityMyInfoNameEdit.setText(groupNm);
                        activityCompanyInfoStyleText.setText(groupDetail);
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

    private void initView() {
        addGroupsNumberAdapter = new AddGroupsNumberAdapter(getApplicationContext(), friendList, groupId, GroupChatDetailActivity.this);
        addGroupsNumberAdapter.setOnItemClickListener(new AddGroupsNumberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int posi) {
                Log.v("lgq","....hh....."+friendList.get(posi).getPhone());
//                if (friendList.get(posi).getPhone().equals(UserNative.getPhone())){
                    Intent intent = new Intent(GroupChatDetailActivity.this, UserProfileActivity.class); //点击头像去会员资料
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, friendList.get(posi).getPhone());
//                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
                    intent.putExtra("groupType", groupType);
//                    Log.v("lgq","个人信息。。。"+username+"..."+chatType);
                    startActivity(intent);


//                    dad();
//                }
            }
        });
        addGroupsNumberAdapter.setOnItemClickListener(new AddGroupsNumberAdapter.OnItemClickListenerupd() {
            @Override
            public void onItemClick(int position) {
                deleteCompanyMemberToWeb(friendList.get(position).getPhone(),position);
            }
        });
        activityCompanyMemberRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
        activityCompanyMemberRecyclerView.setAdapter(addGroupsNumberAdapter);
        activityCompanyMemberRecyclerView.setFocusable(false);//导航栏切换不再focuse



        mSwitchGroupMsg.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                toggleBlockGroup(isChecked);
            }
        });
    }

    ProgressDialog progressDialog = null;

    private void toggleBlockGroup(boolean isChecked) {
        if (!isChecked) {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setCanceledOnTouchOutside(false);
            }
            progressDialog.setMessage(getString(R.string.Is_unblock));
            progressDialog.show();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //移除免打扰
                        List<String> ids = getTypeTitle();
                        if (ids != null) {
                            ids.remove(groupId);
                            saveTypeTitle(ids);
                        }
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                ToastUtil.showError(getApplicationContext(), getString(R.string.remove_group_of), Toast.LENGTH_LONG);
                            }
                        });

                    }
                }
            }).start();

        } else {
            String st8 = getResources().getString(R.string.group_is_blocked);
            final String st9 = getResources().getString(R.string.group_of_shielding);
            if (progressDialog == null) {
                progressDialog = new ProgressDialog(this);
                progressDialog.setCanceledOnTouchOutside(false);
            }
            progressDialog.setMessage(st8);
            progressDialog.show();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        //加入免打扰
                        List<String> ids = getTypeTitle();
                        ids.add(groupId);
                        saveTypeTitle(ids);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                progressDialog.dismiss();
                                ToastUtil.showError(getApplicationContext(), st9, Toast.LENGTH_LONG);
                            }
                        });
                    }
                }
            }).start();
        }
    }
    private void deleteCompanyMemberToWeb(String phone, final int position) {
        String url = Url.deleteFriendSingle;
        RequestParams params = new RequestParams();
        params.put("groupId", groupId);//群Id
        params.put("userName", phone);//用户UUID
        params.put("userId", UserNative.getId());//(当前登录用户ID)
        params.put("userNm", UserNative.getName());//(当前登录用户名)
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(GroupChatDetailActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        friendList.remove(position);
                        addGroupsNumberAdapter.notifyDataSetChanged();
                        ToastUtil.showSuccess(GroupChatDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(GroupChatDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.e("数据解析出错");
                }
            }
        });

    }


    private void saveTypeTitle(List<String> ids) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ids.size(); i++) {
            if (i == 0) {
                sb.append(ids.get(i));
                sb.append(ids.get(i));
            } else {
                sb.append("_" + ids.get(i));
            }
        }
        PreferenceManager.putString("no_disturbing", sb.toString());
    }

    private List<String> getTypeTitle() {
        String type = PreferenceManager.getString("no_disturbing", "");
        return new ArrayList<String>(Arrays.asList(type.split("_")));
    }

    private void updateGroup(String groupId, final String groupNm, String desc, String groupImage) {
        String url = Url.upGroupname;
        RequestParams params = new RequestParams();
        params.put("groupId", groupId);
        params.put("groupNm", groupNm);//用户id
        params.put("desc", desc);
        params.put("groupImage", groupImage);

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
                        activityMyInfoNameEdit.setText(groupNm);
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getApplicationContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }
    //更改群名
    public void dad() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(GroupChatDetailActivity.this);
        View view = inflater.inflate(R.layout.update_group_name, null);
        dialog = new Dialog(GroupChatDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });


        TextView qd = (TextView)view.findViewById(R.id.qdBtn);
        TextView qx = (TextView)view.findViewById(R.id.qcBtn);
        final EditText et_shuju = (EditText)view.findViewById(R.id.et_shuju);
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_shuju.getText().toString().length()<1){
                    ToastUtil.showInfo(getApplicationContext(), "名称不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                updateGroup(groupId, et_shuju.getText().toString(), groupDetail, groupImage);
//            	timer.schedule(task, 1000, 1000);//开始循环倒计时
                dialog.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private void delegroup() {//删除群
        String url = Url.delegroup;
        RequestParams params = new RequestParams();
        params.put("groupId", groupId);//用户id
        mMy_loadingDialog.show();
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        EMClient.getInstance().chatManager().deleteConversation(groupId, true);
                        MyShareUtil.sharedPint("delegroup",1);
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }
    private void outgroup() {//退出群
        String url = Url.deleteFriendSingle;
        RequestParams params = new RequestParams();
        params.put("userName",UserNative.getPhone());//用户电话
        params.put("groupId", groupId);//好友电话
        mMy_loadingDialog.show();
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(GroupChatDetailActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        EMClient.getInstance().chatManager().deleteConversation(groupId, true);
                        MyShareUtil.sharedPint("delegroup",1);
                        finish();
                        ToastUtil.showSuccess(GroupChatDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(GroupChatDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.e("数据解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });

    }
}

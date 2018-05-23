package com.yuanxin.clan.core.huanxin;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.BusinessDetailWebActivity;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.activity.PhotoScanActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.entity.FriendDetail;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.bean.businessArea;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
/**
 *个人资料
* */
public class UserProfileActivity extends com.yuanxin.clan.mvp.view.BaseActivity {

    @BindView(R.id.imgUserImage)
    ImageView mImgUserImage;
    @BindView(R.id.mTvUserName)
    TextView mTvUserName;
    @BindView(R.id.scqycyte)
    TextView scqycyte;
    @BindView(R.id.imgUserSex)
    TextView mImgUserSex;
    @BindView(R.id.tvUserEp)
    TextView mTvUserEp;
    @BindView(R.id.tvUserZw)
    TextView mTvUserZw;
    @BindView(R.id.tvSendMessage)
    TextView mTvSendMessage;
    @BindView(R.id.sendmessagete)
    TextView sendmessagete;
    @BindView(R.id.imgUserSexphone)
    TextView imgUserSexphone;
    @BindView(R.id.shangquannamete)
    TextView shangquannamete;
    @BindView(R.id.shangquanrolete)
    TextView shangquanrolete;
    @BindView(R.id.line2)
    TextView line2;
    @BindView(R.id.line22)
    TextView line22;
    @BindView(R.id.lyBack)
    LinearLayout mLyBack;
    @BindView(R.id.shangquannameli)
    LinearLayout shangquannameli;
    @BindView(R.id.epnameli)
    LinearLayout epnameli;
    @BindView(R.id.epzhiweili)
    RelativeLayout epzhiweili;
    @BindView(R.id.shangqualorere)
    RelativeLayout shangqualorere;

    private List<businessArea> mBusinessAreas=new ArrayList<>();
    private FriendDetail mFriendDetail;
    private String userPhone;
    private int chatType;
    private int saoma;
    private int groupid;
    private int inType;
    private int posi;
    private String epId;
    private String uid;
    private ProgressDialog progressDialog;
    private More_LoadDialog mMy_loadingDialog;
    private int ifsendmessage=0;
    private String image;
    private String epAccessPath;
    private String baViewPath;

    @Override
    public int getViewLayout() {
//        return R.layout.em_activity_user_profile;
        return R.layout.new_user_profile;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mMy_loadingDialog =new More_LoadDialog(this);
        progressDialog = new ProgressDialog(this);
        userPhone = getIntent().getStringExtra(EaseConstant.EXTRA_USER_ID);
        chatType = getIntent().getIntExtra(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        groupid = getIntent().getIntExtra("groupType",0);
        inType = getIntent().getIntExtra("inType",0);
        saoma = getIntent().getIntExtra("sm",0);
        posi = getIntent().getIntExtra("posi",0);
        uid = getIntent().getStringExtra("uid");
        Log.i("lgq","个人信息返回。。。userPhone==="+userPhone+"......."+uid);

        if (saoma==1||groupid>0){
            mTvSendMessage.setVisibility(View.VISIBLE);
        }
        if (groupid==3&&userPhone.equals(UserNative.getPhone())){
//            persionupdli.setVisibility(View.VISIBLE);//隐藏修改昵称
        }
        if (inType==1){
            scqycyte.setVisibility(View.VISIBLE);
        } else if (inType == 2) {

//            fragmentTwoOneRecyclerview.setVisibility(View.GONE);
        }
        getUserInfo(userPhone);
        checkFriendSingle(userPhone);
    }

    //showNormalDialogOne
    private void showNormalDialogOne() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("警告");
        normalDialog.setMessage("您确定删除该企业成员吗！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                    deleteList.add(entity);
                        //entityList.get(position).getUserId()
                        deleteCompanyMemberToWeb(uid);
//                        notifyDataSetChanged();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void deleteCompanyMemberToWeb(String userId) {
        String url = Url.deleteCompanyMemberToWeb;
        RequestParams params = new RequestParams();
        params.put("epId", UserNative.getEpId());//企业id
        params.put("userId", userId);//要删除的id
        params.put("updateId", UserNative.getId());//用户id
        params.put("updateNm", UserNative.getName());//用户id
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(UserProfileActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
//                        entityList.remove(position);
                        MyShareUtil.sharedPint("posi",posi);
                        onBackPressed();
                        ToastUtil.showSuccess(UserProfileActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(UserProfileActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(UserProfileActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }

    //获取用户信息
    private void getUserInfo(final String phone) {
        String url = Url.findFriend;
//        String url = urlone+"/user/getUserByPhone";
        RequestParams params = new RequestParams();
        params.put("userPhone", phone);//用户id
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(UserProfileActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.i("lgq","shu====="+s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        image = Url.img_domain  + array.getString("userImage") + Url.imageStyle640x640;
                        String name = array.getString("userNm");
                        epAccessPath = array.getString("epAccessPath");
                        String sex = array.getString("userSex");
                        epId = array.getString("epId");
                        String qyname = array.getString("epNm");
                        String epRole = array.getString("roleNm");
                        userPhone = array.getString("userPhone");
                        String qyzw =array.getString("epRoleName");
                        ImageManager.loadRoundCornerImage(UserProfileActivity.this, image, R.drawable.by, mImgUserImage);
                        mTvUserName.setText(name);
                        mTvUserEp.setText(qyname);
                        imgUserSexphone.setText(userPhone);
                        if (TextUtil.isEmpty(qyzw)) {
                            mTvUserZw.setText(epRole);
                        }else {
                            mTvUserZw.setText(qyzw);
                        }
                        if (sex.equals("0")) {
                            mImgUserSex.setText("男");
                        } else if (sex.equals("1")){
                            mImgUserSex.setText("女");
                        }else {
                            mImgUserSex.setText("");
                        }
                        JSONArray arr= new JSONArray(array.getString("businessArea"));
                        for (int a = 0;a<arr.length();a++){
                            JSONObject temp = (JSONObject) arr.get(a);
                            if(TextUtil.isEmpty(temp.getString("businessAreaNm")) || temp.getString("businessAreaNm").equals("null") || TextUtil.isEmpty(temp.getString("baRole")) || temp.getString("baRole").equals("null")) {
                                continue;
                            }
                            businessArea businessArea = new businessArea();
                            businessArea.setBusinessAreaNm(temp.getString("businessAreaNm"));
                            businessArea.setBaPosition(temp.getString("baPosition"));
                            businessArea.setBusinessAreaId(temp.getInt("businessAreaId"));
                            businessArea.setBaViewPath(temp.getString("baViewPath"));
                            mBusinessAreas.add(businessArea);
                        }
                        if (epId.equals("null")||TextUtil.isEmpty(epId)){
                            epnameli.setVisibility(View.GONE);
                            epzhiweili.setVisibility(View.GONE);
                        }
                        if (mBusinessAreas.size()==0){
                            shangqualorere.setVisibility(View.GONE);
                            shangquannameli.setVisibility(View.GONE);
                            line2.setVisibility(View.GONE);
                            line22.setVisibility(View.GONE);
                            return;
                        }
                        if (mBusinessAreas.get(0).getBusinessAreaId()<1){
                            shangqualorere.setVisibility(View.GONE);
                            shangquannameli.setVisibility(View.GONE);
                            line2.setVisibility(View.GONE);
                            line22.setVisibility(View.GONE);
                            return;
                        }
                        if (mBusinessAreas.get(0).getBaPosition().equals("null")||TextUtil.isEmpty(mBusinessAreas.get(0).getBusinessAreaNm())){
                            shangquannamete.setText(mBusinessAreas.get(0).getBusinessAreaNm());
                            shangquanrolete.setText("");
                            return;
                        }
                        shangquanrolete.setText(mBusinessAreas.get(0).getBaPosition());
                        shangquannamete.setText(mBusinessAreas.get(0).getBusinessAreaNm());
//                        mFriendDetails.addAll(JSON.parseArray(object.getString("data"), FriendDetail.class));
//                        mFriendDetails = FastJsonUtils.getObjectsList(object.getString("data"), FriendDetail.class);
//                        mPersionAdapter.notifyDataSetChanged();
//                        setDataView();  chat_peronal_sex_woman
                    } else {
                        ToastUtil.showWarning(UserProfileActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(UserProfileActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }

//    private void setDataView() {
//        if (mFriendDetails != null && mFriendDetails.size() > 0) {
//            mFriendDetail = mFriendDetails.get(0);
//            ImageManager.loadBitmap(UserProfileActivity.this, mFriendDetail.getUserImage(), R.drawable.by, mImgUserImage);
//            mTvUserName.setText(mFriendDetail.getUserNm());
//            mTvUserEp.setText(mFriendDetail.getEpNm());
//            mTvUserZw.setText(mFriendDetail.getEpRoleName());
//            if (mFriendDetail.getUserSex() == 0) {
//                mImgUserSex.setImageResource(R.drawable.chat_peronal_sex_man);
//            } else {
//                mImgUserSex.setImageResource(R.drawable.chat_peronal_sex_woman);
//            }
//        }
//    }


    @OnClick({R.id.lyBack, R.id.tvSendMessage,R.id.scqycyte,R.id.sendmessagete,R.id.imgUserImage,R.id.epnameli,R.id.shangquannameli})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgUserImage:
                Intent intent = new Intent();
                intent.putExtra("image", image);
                intent.setClass(UserProfileActivity.this, PhotoScanActivity.class);
                startActivity(intent);
                break;
            case R.id.tvSendMessage:
                if (ifsendmessage==1){
                    ToastUtil.showSuccess(UserProfileActivity.this, "申请已发送！", Toast.LENGTH_SHORT);
                    return;
                }
                    addContact(userPhone);
                    addCard(userPhone);
//                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
//                    finish();
//                } else {
//                    Intent intent = new Intent(UserProfileActivity.this, PersionChatActivity.class);//跳转到个人聊天页面
//                    intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);//聊天室 群聊
//                    intent.putExtra(Constant.EXTRA_USER_ID, userPhone);//个人
//                    intent.putExtra("toNick", userPhone);//个人
//                    startActivity(intent);
//                    finish();
//                }
                break;
            case R.id.lyBack:
                finish();
                break;
            case R.id.scqycyte:
                if (inType==1){
                    showNormalDialogOne();
                    return;
                }
                showDeleteFriendDialog();
                break;
            case R.id.sendmessagete:
                Intent intent2 = new Intent(UserProfileActivity.this, PersionChatActivity.class);
                intent2.putExtra(Constant.EXTRA_USER_ID, userPhone);
                intent2.putExtra(Constant.USER_NAME, UserNative.getName());
                intent2.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                intent2.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                intent2.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent2.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent2.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                startActivity(intent2);
                finish();
                break;
            case R.id.epnameli:
                Intent intent22 = new Intent(this, CompanyDetailWebActivity.class);
                intent22.putExtra("epId", Integer.parseInt(epId));
                intent22.putExtra("accessPath", epAccessPath);
                startActivity(intent22);
                break;
            case R.id.shangquannameli:
                Intent intent3 = new Intent(this, BusinessDetailWebActivity.class);//商圈详情
                intent3.putExtra("businessAreaId", String.valueOf(mBusinessAreas.get(0).getBusinessAreaId()));
                intent3.putExtra("epStyleType", mBusinessAreas.get(0).getBaViewPath());
                intent3.putExtra("name", mBusinessAreas.get(0).getBusinessAreaNm());
//                intent.putExtra("accessPath", businessDistrictListEntities.get(position).getAccessPath());
                intent3.putExtra("gid",userPhone);
//                intent.putExtra("enshrine",businessDistrictListEntities.get(position).getEnshrine());
                startActivity(intent3);
                break;
        }
    }

    public void deleteFriendDialog() {
        String msg = "是否删除该好友";
        new EaseAlertDialog(this, null, msg, null, new EaseAlertDialog.AlertDialogUser() {

            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    deleteFriend(userPhone);
                    EMClient.getInstance().chatManager().deleteConversation(userPhone, true);//删除会话
//                    finish();
                }
            }
        }, true).show();
    }
    private void showDeleteFriendDialog() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(UserProfileActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否删除该好友");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFriend(userPhone);
                        EMClient.getInstance().chatManager().deleteConversation(userPhone, true);
                        //                    finish();
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
    private void deleteFriend(final String friendPhone) {//删除好友
        String url = Url.deleteFriend;
        RequestParams params = new RequestParams();
        Log.v("lgq",".......username="+UserNative.getPhone()+"...friendname="+friendPhone+"...userid="+UserNative.getId()+"...d登录="+UserNative.getName());
        params.put("userName", UserNative.getPhone());//用户电话
        params.put("friendName", friendPhone);//好友电话
        params.put("userId", UserNative.getId());//当前登录用户ID
        params.put("userNm", UserNative.getName());//当前登录用户名
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(UserProfileActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.v("lgq","....删除好友返回。。"+object);
                    if (object.getString("success").equals("true")) {
                        MyShareUtil.sharedPint("delegroup",1);
                        ToastUtil.showSuccess(UserProfileActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        EMClient.getInstance().contactManager().deleteContact(friendPhone);
                        finish();
                    } else {
                        ToastUtil.showWarning(UserProfileActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }

    private void addCard(final String friendPhone) {//删除好友
        String url = Url.addcard;
        RequestParams params = new RequestParams();
        Log.v("lgq",".......username="+UserNative.getPhone()+"...friendname="+friendPhone+"...userid="+UserNative.getId()+"...d登录="+UserNative.getName());
        params.put("activePhone", UserNative.getPhone());//用户电话
        params.put("passivePhone", friendPhone);//好友电话
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(UserProfileActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
//                        Toast.makeText(UserProfileActivity.this, object.getString("msg"), Toast.LENGTH_SHORT).show();

                    } else {
                        ToastUtil.showWarning(UserProfileActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
//                    Logger.e("数据解析出错");
                }
            }
        });

    }


    /**
     * add contact
     */
    //点击查找  查找到 显示 添加
    public void addContact(final String phone) {
//        if (EMClient.getInstance().getCurrentUser().equals(phone)) {
//        Log.v("lgq","..........."+phone+"...."+ UserNative.getPhone()+"...."+EMClient.getInstance().getCurrentUser());
        if (UserNative.getPhone().equals(phone)) {
            new EaseAlertDialog(this, R.string.not_add_myself).show();//不能添加自己
            return;
        }

//        if (DemoHelper.getInstance().getContactList().containsKey(phone)) {
//            //let the user know the contact already in your contact list
//            if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(phone)) { //黑名单
//                new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();//This user already is your friend, only need remove out from the black list
//                return;
//            }
//            new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();//已经是好友  //不走环信验证是否是好友
//            return;
//        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);//正在发送请求...
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);//加个好友呗
                    EMClient.getInstance().contactManager().addContact(phone, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
//                            mTvSendMessage.setEnabled(false);
                            ifsendmessage=1;
                            String s1 = getResources().getString(R.string.send_successful);//发送请求成功,等待对方验证
                            ToastUtil.showInfo(getApplicationContext(), s1, Toast.LENGTH_SHORT);
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            ToastUtil.showInfo(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG);//请求添加好友失败
                        }
                    });
                }
            }
        }).start();
        Log.v("lgq","添加好友。。。。。"+phone);
//        addFriendSingle(phone);//只走环信
    }


    private void checkFriendSingle(String username) {//还差图片
        String url = Url.checkFriendSingle;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//登录者号
        params.put("userNm", UserNative.getName());//登录者名字
        params.put("userName", UserNative.getPhone());//登录者环信账号
        params.put("friendName", username);//加好友环信账号
//        Log.v("lgq",".....uid="+UserNative.getId()+"...uname="+UserNative.getName()+"....uphone="+UserNative.getPhone()+".....friendname="+username);
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getBoolean("success")==true){
                        mTvSendMessage.setVisibility(View.GONE);
                        sendmessagete.setVisibility(View.VISIBLE);
                        if (!(inType==1)){
                            scqycyte.setText("删除好友");
                            scqycyte.setVisibility(View.VISIBLE);
                        }
                    }else{
                        if (!userPhone.equals(UserNative.getPhone())){
                            mTvSendMessage.setVisibility(View.VISIBLE);
                        }
                    }

                    if (object.getString("success").equals("true")) {
//                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else {
//                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });

    }
    //更新dialog
    public void dad() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(UserProfileActivity.this);
        View view = inflater.inflate(R.layout.dialoglg, null);
        dialog = new Dialog(UserProfileActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        TextView bc =(TextView)view.findViewById(R.id.baocun);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface arg0) {
                // TODO Auto-generated method stub

            }
        });


        TextView qd = (TextView)view.findViewById(R.id.qdapk);
        TextView qx = (TextView)view.findViewById(R.id.qcapk);
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("lgq",".....qd......");
//            	timer.schedule(task, 1000, 1000);//开始循环倒计时
                dialog.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("lgq",".....qx......");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}

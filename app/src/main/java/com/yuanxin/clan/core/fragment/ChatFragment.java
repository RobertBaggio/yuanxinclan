package com.yuanxin.clan.core.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.easemob.redpacket.utils.RedPacketUtil;
import com.easemob.redpacket.widget.ChatRowRandomPacket;
import com.easemob.redpacket.widget.ChatRowRedPacket;
import com.easemob.redpacket.widget.ChatRowRedPacketAck;
import com.easemob.redpacketsdk.RPSendPacketCallback;
import com.easemob.redpacketsdk.bean.RedPacketInfo;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMCmdMessageBody;
import com.hyphenate.chat.EMImageMessageBody;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.easeui.ui.EaseChatFragment.EaseChatFragmentHelper;
import com.hyphenate.easeui.utils.ShareTypes;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.chatrow.EaseBusinessCardChatrow;
import com.hyphenate.easeui.widget.chatrow.EaseBusinessCardTipChatrow;
import com.hyphenate.easeui.widget.chatrow.EaseChatRow;
import com.hyphenate.easeui.widget.chatrow.EaseCustomChatRowProvider;
import com.hyphenate.easeui.widget.chatrow.EaseShareQiliaoChatrow;
import com.hyphenate.exceptions.HyphenateException;
import com.hyphenate.util.EasyUtils;
import com.hyphenate.util.PathUtil;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.MainActivity;
import com.yuanxin.clan.core.activity.BusinessDetailWebActivity;
import com.yuanxin.clan.core.activity.ExternalWebActivity;
import com.yuanxin.clan.core.activity.GreetingCardWebActivity;
import com.yuanxin.clan.core.activity.GroupChatDetailActivity;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.activity.My_CommodityActivity;
import com.yuanxin.clan.core.activity.My_ServiceInfoActivity;
import com.yuanxin.clan.core.activity.NewMyBargainActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.entity.BargainEntity;
import com.yuanxin.clan.core.entity.BusinessCardEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.huanxin.ContextMenuActivity;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.huanxin.ForwardMessageActivity;
import com.yuanxin.clan.core.huanxin.ImageGridActivity;
import com.yuanxin.clan.core.huanxin.PermissionsManager;
import com.yuanxin.clan.core.huanxin.PickAtUserActivity;
import com.yuanxin.clan.core.huanxin.RobotUser;
import com.yuanxin.clan.core.huanxin.UserProfileActivity;
import com.yuanxin.clan.core.huanxin.VideoCallActivity;
import com.yuanxin.clan.core.huanxin.VoiceCallActivity;
import com.yuanxin.clan.core.news.view.PhotoBrowserActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.share.ShareInfoVo;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * //聊天头部  聊天界面
 */
public class ChatFragment extends EaseChatFragment implements EaseChatFragmentHelper {

    // constant start from 11 to avoid conflict with constant in base class
    private static final int ITEM_VIDEO = 11;
    private static final int ITEM_FILE = 12;
    private static final int ITEM_VOICE_CALL = 13;
    private static final int ITEM_VIDEO_CALL = 14;

    private static final int REQUEST_CODE_SELECT_VIDEO = 11;
    private static final int REQUEST_CODE_SELECT_FILE = 12;
    private static final int REQUEST_CODE_GROUP_DETAIL = 13;
    private static final int REQUEST_CODE_CONTEXT_MENU = 14;
    private static final int REQUEST_CODE_SELECT_AT_USER = 15;


    private static final int MESSAGE_TYPE_SENT_VOICE_CALL = 1;
    private static final int MESSAGE_TYPE_RECV_VOICE_CALL = 2;
    private static final int MESSAGE_TYPE_SENT_VIDEO_CALL = 3;
    private static final int MESSAGE_TYPE_RECV_VIDEO_CALL = 4;

    //red packet code : 红包功能使用的常量
    private static final int MESSAGE_TYPE_RECV_RED_PACKET = 5;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET = 6;
    private static final int MESSAGE_TYPE_SEND_RED_PACKET_ACK = 7;
    private static final int MESSAGE_TYPE_RECV_RED_PACKET_ACK = 8;
    private static final int MESSAGE_TYPE_RECV_TRANSFER_PACKET = 9;
    private static final int MESSAGE_TYPE_SEND_TRANSFER_PACKET = 10;
    private static final int MESSAGE_TYPE_RECV_RANDOM = 11;
    private static final int MESSAGE_TYPE_SEND_RANDOM = 12;
    private static final int REQUEST_CODE_SEND_RED_PACKET = 16;
    private static final int ITEM_RED_PACKET = 16;
    private static final int REQUEST_CODE_SEND_TRANSFER_PACKET = 17;
    private static final int ITEM_TRANSFER_PACKET = 17;
    //end of red packet code
    private static final int REQUEST_CHOOSER = 1234;

    private static final int MESSAGE_TYPE_SENT_BUSINESS_CARD_CALL = 18;
    private static final int MESSAGE_TYPE_RECV_BUSINESS_CARD_CALL = 19;

    private static final int MESSAGE_TYPE_SENT_BUSINESS_CARD_TIP = 20;
    private static final int MESSAGE_TYPE_RECV_BUSINESS_CARD_TIP = 21;

    private static final int MESSAGE_TYPE_SENT_URL = 22;
    private static final int MESSAGE_TYPE_RECV_URL = 23;


    /**
     * if it is chatBot
     */
    private boolean isRobot;
    private LinearLayout oneLayout;
    private LinearLayout twoLayout;
    private LinearLayout leftLayout;
    private TextView rightLayout;
    private ImageView compneyimage;
    private TextView middleText;
    private ImageView imageOne;
    private ImageView imageTwo;
    private TextView textOne;
    private TextView textTwo;
    private String toNick, toImage, companyDetail;
    private String type;
    private TextView lockcompanyte;
    private TextView longtextte;
    private TextView ifkefute;
    private int epid;
    private String epAccessPath;
    private LinearLayout noisf,businessli;
    private int groupType;
    private ProgressDialog progressDialog;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initView() {
        super.initView();
        if (toChatUsername.length()==11){
            checkFriendSingle(toChatUsername);
        }else {
            getMyGroupsInfo(toChatUsername);
        }
        hideTitleBar();///隐藏标题栏
        View errorView = (LinearLayout) View.inflate(getActivity(), R.layout.new_service_layout, null);//自己改的标题 企业主页 企业资料
        errorItemContainer.setVisibility(View.VISIBLE);
        FrameLayout.LayoutParams viewPream =new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                Gravity.TOP
        );//设置布局控件的属性
        viewPream.topMargin = 0;
        errorItemContainer.addView(errorView, viewPream);
        noisf = (LinearLayout) errorView.findViewById(R.id.noisfridentli);
        lockcompanyte = (TextView)errorView.findViewById(R.id.lockcompanyte);
        ifkefute = (TextView)errorView.findViewById(R.id.overeasete);
        longtextte = (TextView)errorView.findViewById(R.id.longtextte);
        businessli =(LinearLayout)errorView.findViewById(R.id.lyChatViewMenuhui) ;
        imageOne = (ImageView) errorView.findViewById(R.id.new_service_layout_one_image_one);//商圈主页
        imageTwo = (ImageView) errorView.findViewById(R.id.new_service_layout_one_image_two);//商圈成员

        textOne = (TextView) errorView.findViewById(R.id.new_service_layout_one_text_one);//商圈主页
        textOne.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), NewMyBargainActivity.class);
                intent.putExtra("isSelect", true);
                startActivityForResult(intent, 321);
            }
        });
        textTwo = (TextView) errorView.findViewById(R.id.new_service_layout_one_text_two); //商圈成员
        compneyimage = (ImageView)errorView.findViewById(R.id.compneyimage);
        compneyimage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("lgq","聊天企业ID==="+epid);
                if (groupType==2){
                    Intent intent = new Intent(getActivity(), BusinessDetailWebActivity.class);//商圈详情
                    intent.putExtra("businessAreaId", String.valueOf(MyShareUtil.getSharedInt("bid")));
                    intent.putExtra("epStyleType", MyShareUtil.getSharedString("bp"));
//                intent.putExtra("accessPath", businessDistrictListEntities.get(position).getAccessPath());
                    intent.putExtra("gid",toChatUsername);
                    intent.putExtra("name",MyShareUtil.getSharedString("bn"));
//                intent.putExtra("enshrine",businessDistrictListEntities.get(position).getEnshrine());
                    startActivity(intent);
                    return;
                }
                if (epid>0){
                    Utils.gotoCompanyDetail(getContext(), epid, epAccessPath);
//                    Intent intent = new Intent(getContext(), CompanyDetailWebActivity.class);//有个聊天的标志
//                    intent.putExtra("epId",epid );
//                    startActivity(intent);
                }else
                    ToastUtil.showWarning(getContext(), "该好友暂友无企业", Toast.LENGTH_SHORT);

            }
        });
        leftLayout = (LinearLayout) errorView.findViewById(R.id.activity_yuan_xin_crowd_left_layout);
        leftLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });
        lockcompanyte.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                addContact(toChatUsername);
                addCard(toChatUsername);
            }
        });
        ifkefute.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                overEC();
            }
        });
        rightLayout = (TextView) errorView.findViewById(R.id.myheadimage);

        rightLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    Intent intent = new Intent(getActivity(), UserProfileActivity.class); //点击头像去会员资料
                    intent.putExtra(EaseConstant.EXTRA_USER_ID, toChatUsername);
                    // 1：从企业成员管理进入 2：好友进入
                    intent.putExtra("inType", 2);
                    startActivity(intent);
                    return;
                } else {
//                    if (groupType==2){
//                        Intent intent = new Intent(getActivity(), My_BusMemberActivity.class);
//                        String businessAreaId =String.valueOf(MyShareUtil.getSharedInt("bid"));
//                        String url  = Url.urlWeb + "/" + "business-member-two" + "&param=" + businessAreaId + "&appFlg=1";
//                        intent.putExtra("url",url);
//                        intent.putExtra("ch",1);
//                        startActivity(intent);
////                www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/business-member-two&param=385&appFlg=1
////                www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/business-style-two&param=385&appFlg=1
//                        return;
//                    }
//                    if (groupType==3){
//                        startActivity(
//                                //GroupDetailsActivity 原来是这个activity GroupChatDetailActivity
//                                new Intent(getActivity(), GroupChatDetailActivity.class).putExtra("groupId", toChatUsername));
//                        return;
//                    }
                    toGroupDetails();
                }
            }
        });
        middleText = (TextView) errorView.findViewById(R.id.activity_yuan_xin_crowd_middle_text);

        String relo = UserNative.readRole();
        Log.v("lgq","......."+toChatUsername+"===="+relo);
        if (relo.equals("39")){
//            ifkefute.setVisibility(View.VISIBLE);
        }

        Bundle bundle = getArguments();//从activity传过来的Bundle
        if (bundle != null) {
            toNick = bundle.getString("toNick");//对方的昵称
            toImage = bundle.getString("toImage");//对方的头像
            companyDetail = bundle.getString("companyDetail");//公司详情
            type = bundle.getString("type");
            if (TextUtil.isEmpty(type)){
                type="no";
            }
            if (type.equals("圆心客服")){
                noisf.setVisibility(View.GONE);
                rightLayout.setVisibility(View.GONE);
                middleText.setText("圆心客服");
            }
            if (!TextUtils.isEmpty(type)) {
                if (type.equals("与专家聊天中")) {
                    imageTwo.setImageResource(R.drawable.chat_icon_specialist);
                    textTwo.setText("专家资料");
                }
                if (type.equals("与企业聊天中")) {
                    middleText.setText(type);
                } else if (type.equals("与商家聊天中")) {
                    middleText.setText(type);
                } else if (type.equals("与发起人聊天中")) {
                    middleText.setText(type);
                } else if (type.equals("与专家聊天中")) {
                    middleText.setText(type);
                } else if (type.equals("圆心客服")) {
//                    middleText.setText(type);
                } else {
                    // TODO: 2017/6/10 0010 没有昵称的要去查下数据库，陌生人要。。。。。。。。。。
                    middleText.setText(toNick);
                }
            } else {
                // TODO: 2017/6/10 0010 没有昵称的要去查下数据库，陌生人要。。。。。。。。。。
                middleText.setText(toNick);
            }
        }

    }
    @Override
    protected void setUpView() {//对话监听
        setChatFragmentListener(this);
        if (chatType == Constant.CHATTYPE_SINGLE) { //个人对个人聊天
            Map<String, RobotUser> robotMap = DemoHelper.getInstance().getRobotList();
            if (robotMap != null && robotMap.containsKey(toChatUsername)) {
                isRobot = true;
            }
        }
        super.setUpView();
        // set click listener
        titleBar.setLeftLayoutClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (EasyUtils.isSingleActivity(getActivity())) {
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
                onBackPressed();
            }
        });
        titleBar.setRightLayoutClickListener(new OnClickListener() {//右边的这个点击事件自己加的，原来整个都没有

            @Override
            public void onClick(View v) {
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    String msg = "是否删除该好友";
                    new EaseAlertDialog(getActivity(), null, msg, null, new EaseAlertDialog.AlertDialogUser() {

                        @Override
                        public void onResult(boolean confirmed, Bundle bundle) {
                            if (confirmed) {
                                deleteFriend();
                                EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);
                                messageList.refresh();
                            }
                        }
                    }, true).show();
                } else {
                    toGroupDetails();
                }
            }
        });
//        ((BQMMKeyboard) inputMenu.getEmojiconMenu()).add.addEmojiconGroup(EmojiconExampleGroupData.getData());
        /**
         * BQMM集成
         * 删去对表情栏内容的设置
         * 增加 @
         */
        if (chatType == EaseConstant.CHATTYPE_GROUP) {
            inputMenu.getPrimaryMenu().getEditText().addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (count == 1 && "@".equals(String.valueOf(s.charAt(start)))) {
                        startActivityForResult(new Intent(getActivity(), PickAtUserActivity.class).
                                putExtra("groupId", toChatUsername), REQUEST_CODE_SELECT_AT_USER);
                    }
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
        ShareInfoVo shareInfoVo = getArguments().getParcelable("share_info");
        if (shareInfoVo != null) {
            sendUrlMsg(shareInfoVo.getShareQiliaoType(), shareInfoVo.getTitle(), shareInfoVo.getContent(), shareInfoVo.getImgUrl(), shareInfoVo.getUrl());
        }
    }

    private void deleteFriend() {//删除好友
        String url = Url.deleteFriendSingle;
        RequestParams params = new RequestParams();
//        Log.v("lgq",".......username="+UserNative.getPhone()+"...friendname="+toChatUsername+"...userid="+UserNative.getId()+"...d登录="+UserNative.getName());
        params.put("userName", UserNative.getPhone());//用户电话
        params.put("friendName", toChatUsername);//好友电话
        params.put("userId", UserNative.getId());//当前登录用户ID
        params.put("userNm", UserNative.getName());//当前登录用户名
        params.put("key", UserNative.getAesKes());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
//                    Log.v("lgq","....删除好友返回。。"+object);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        EMClient.getInstance().contactManager().deleteContact(toChatUsername);
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }
    private void checkFriendSingle(String username) {//还差图片
        String url = Url.checkFriendSingle;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//登录者号
        params.put("userNm", UserNative.getName());//登录者名字
        params.put("userName", UserNative.getPhone());//登录者环信账号
        params.put("friendName", username);//加好友环信账号
        params.put("key", UserNative.getAesKes());
//        Log.v("lgq",".....uid="+UserNative.getId()+"...uname="+UserNative.getName()+"....uphone="+UserNative.getPhone()+".....friendname="+username);
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getBoolean("success")==true){
                        getUserInfo(toChatUsername);
                    }else{
                        if (Utils.isPhoneNumber(toChatUsername)){
                            noisf.setVisibility(View.VISIBLE);
                        }else {
                            rightLayout.setVisibility(View.GONE);
                        }
                    }

                    if (object.getString("msg").equals("好友不存在")) {

                        getUserInfo(toChatUsername);

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

    //获取用户信息
    private void getUserInfo(String phone) {
        String url = Url.findFriend;
//        String url = urlone+"/user/getUserByPhone";
        RequestParams params = new RequestParams();
        params.put("userPhone", phone);//用户id
        params.put("key", UserNative.getAesKes());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
//                    Log.v("lgq","个人信息返回。。。"+s);
                    if (object.getString("success").equals("true")) {
                        JSONObject item = object.getJSONObject("data");
                        String oneid = item.getString("epId");
                        String role = item.getString("role");
                        if (oneid.equals("null")||TextUtil.isEmpty(oneid)){
                            epid=0;
                        }else {
                            epid=Integer.parseInt(oneid);
                        }
//                        epid = item.getInt("epId");
                        if (epid>0){
                            compneyimage.setVisibility(View.VISIBLE);
                        }

                        if (type.equals("圆心客服")||role.equals("39")){
                            middleText.setText("圆心客服");
                            noisf.setVisibility(View.GONE);
//                            rightLayout.setVisibility(View.GONE);
                        }else {
                            middleText.setText(item.getString("userNm"));
                        }
                        if (UserNative.readRole().equals("39")){
                            noisf.setVisibility(View.GONE);
                        }
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }



    private void getMyGroupsInfo(String groupId) {
        String url = Url.getMyGroupsInfo;
        RequestParams params = new RequestParams();
        params.put("groupId", groupId);//用户id
        params.put("key", UserNative.getAesKes());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                Log.v("lgq","getMyGroupsInfo==="+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject dataObject = object.getJSONObject("data");
                        String groupId = dataObject.getString("groupId");//群id
                        String groupImage = dataObject.getString("groupImage");//群头像
                        String groupImageOne = Url.urlHost + groupImage;
                        String groupNm = dataObject.getString("groupNm");//群
//                        groupmin = dataObject.getString("groupAdminUuid");//群
                        String createNm = dataObject.getString("createNm");//群主
                        //groupDetail
//                        groupDetail = dataObject.getString("groupDetail");//群主
                        groupType = dataObject.getInt("groupType");
                        epAccessPath = dataObject.getString("epAccessPath");
                        if (groupType==1){
                            compneyimage.setVisibility(View.VISIBLE);
                            epid= dataObject.getInt("businessAreaId");
//                            epli.setVisibility(View.VISIBLE);
                        }else if (groupType==2){
//                            shanchutianjiali.setVisibility(View.VISIBLE);
                            compneyimage.setImageResource(R.drawable.chat_business_area_index);
                            compneyimage.setVisibility(View.VISIBLE);
//                            businessli.setVisibility(View.VISIBLE);
                        }else if (groupType==3){

                        }
                        middleText.setText(groupNm);
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    @Override
    protected void registerExtendMenuItem() {
        //use the menu in base class
        super.registerExtendMenuItem();
        //extend menu items
        //先不要发视频
        inputMenu.registerExtendMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, ITEM_VIDEO, extendMenuItemClickListener);
        inputMenu.registerExtendMenuItem(R.string.attach_file, R.drawable.em_chat_file_selector, ITEM_FILE, extendMenuItemClickListener);
//        if(chatType == Constant.CHATTYPE_SINGLE){
//            inputMenu.registerExtendMenuItem(R.string.attach_voice_call, R.drawable.em_chat_voice_call_selector, ITEM_VOICE_CALL, extendMenuItemClickListener);
//            inputMenu.registerExtendMenuItem(R.string.attach_video_call, R.drawable.em_chat_video_call_selector, ITEM_VIDEO_CALL, extendMenuItemClickListener);
//        }
        //聊天室暂时不支持红包功能
//        red packet code : 注册红包菜单选项
        if (chatType != Constant.CHATTYPE_CHATROOM) {
            inputMenu.registerExtendMenuItem(R.string.attach_red_packet, R.drawable.em_chat_red_packet_selector, ITEM_RED_PACKET, extendMenuItemClickListener);
        }
//        //red packet code : 注册转账菜单选项
//        if (chatType == Constant.CHATTYPE_SINGLE) {
//            inputMenu.registerExtendMenuItem(R.string.attach_transfer_money, R.drawable.em_chat_transfer_selector, ITEM_TRANSFER_PACKET, extendMenuItemClickListener);
//        }
        //end of red packet code
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 321 && resultCode == Activity.RESULT_OK) {
            //选择了合同
            BargainEntity bargainEntity = (BargainEntity) data.getSerializableExtra("bargain");
            if (bargainEntity != null && !TextUtil.isEmpty(bargainEntity.getContractPath())) {
                sendFileByRemoteUrl(bargainEntity.getContractNm(), bargainEntity.getContractPath());
            }
        }
        if (requestCode == REQUEST_CODE_CONTEXT_MENU) {
            switch (resultCode) {
                case ContextMenuActivity.RESULT_CODE_COPY: // copy
//                    clipboard.setPrimaryClip(ClipData.newPlainText(null, ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    /**
                     * BQMM集成
                     * 在进行复制时，对待复制的内容进行预处理
                     */
                    try {
                        String txtMsgType = contextMenuMessage.getStringAttribute(EaseConstant.BQMM_MESSAGE_KEY_TYPE);
                        JSONArray msg_Data = contextMenuMessage.getJSONArrayAttribute(EaseConstant.BQMM_MESSAGE_KEY_CONTENT);
                        if (txtMsgType.equals(EaseConstant.BQMM_MESSAGE_TYPE_MIXED)) {
                            StringBuilder message = new StringBuilder();
                            try {
                                for (int i = 0; i < msg_Data.length(); i++) {
                                    JSONArray childArray = msg_Data.getJSONArray(i);
                                    if (childArray.get(1).equals("1")) {
                                        message.append("[").append(childArray.get(0)).append("]");
                                    } else {
                                        message.append(childArray.get(0));
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            clipboard.setPrimaryClip(ClipData.newPlainText(null, message.toString()));
                            break;
                        }
                    } catch (HyphenateException e) {
                        e.printStackTrace();
                    }
                    clipboard.setPrimaryClip(ClipData.newPlainText(null,
                            ((EMTextMessageBody) contextMenuMessage.getBody()).getMessage()));
                    break;
                case ContextMenuActivity.RESULT_CODE_DELETE: // delete
                    conversation.removeMessage(contextMenuMessage.getMsgId());
                    messageList.refresh();
                    break;
                case ContextMenuActivity.RESULT_CODE_FORWARD: // forward
                    Intent intent = new Intent(getActivity(), ForwardMessageActivity.class);
                    intent.putExtra("forward_msg_id", contextMenuMessage.getMsgId());
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SELECT_VIDEO: //send the video
                    if (data != null) {
                        int duration = data.getIntExtra("dur", 0);
                        String videoPath = data.getStringExtra("path");
                        File file = new File(PathUtil.getInstance().getImagePath(), "thvideo" + System.currentTimeMillis());
                        try {
                            FileOutputStream fos = new FileOutputStream(file);
                            Bitmap ThumbBitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
                            ThumbBitmap.compress(CompressFormat.JPEG, 100, fos);
                            Log.v("lgq","wj===="+fos);
                            fos.close();
                            sendVideoMessage(videoPath, file.getAbsolutePath(), duration);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
//                case REQUEST_CODE_SELECT_FILE: //send the file 文件
//                    if (data != null) {
//                        Uri uri = data.getData();
//                        if (uri != null) {
//                            sendFileByUri(uri);//发送文件
//                        }
//                    }
//                    break;
                case REQUEST_CHOOSER:
                    if (data != null) {
                        Uri uri = data.getData();
                        // Get the File path from the Uri
                        String path = FileUtils.getPath(getContext(), uri);
                        if (path != null) {
//                            sendFileByUri(uri);//发送文件
                            sendFileByPath(path);
                        }
                    }
                    break;
                case REQUEST_CODE_SELECT_AT_USER:
                    if (data != null) {
                        Bundle bundle = data.getBundleExtra("user");
                        String username = bundle.getString("username");
                        String usernick = bundle.getString("usernick");
                        inputAtUsername(TextUtils.isEmpty(usernick) ? username: usernick, false);
                    }
                    break;
                //red packet code : 发送红包消息到聊天界面
//                case REQUEST_CODE_SEND_RED_PACKET:
//                    if (data != null) {
//                        sendMessage(RedPacketUtil.createRPMessage(getActivity(), data, toChatUsername));
//                    }
//                    break;
//                case REQUEST_CODE_SEND_TRANSFER_PACKET://发送转账消息
//                    if (data != null) {
//                        sendMessage(RedPacketUtil.createTRMessage(getActivity(), data, toChatUsername));
//                    }
//                    break;
                //end of red packet code
                default:
                    break;
            }
        }

    }

    @Override
    public void onSetMessageAttributes(EMMessage message) {
        // TODO: 2017/6/8 0008 这里拓展属性信息。。。。。。。。。
        if (isRobot) {
            //set message extension
            message.setAttribute("em_robot_message", isRobot);
        }
        // 通过扩展属性，将userPic和userName发送出去。
//        String userPic = AppSPUtils.getValueFromPrefrences("logoUrl", "");
        SharedPreferences pref = getContext().getSharedPreferences("huanXin", getContext().MODE_PRIVATE);
//        String name = pref.getString("userId", "");
        String userNm = pref.getString("userNm", "");
        String userPhone = pref.getString("userPhone", "");
//
        //昵称
        String userImage = pref.getString("userImage", "");//头像

        if (!TextUtils.isEmpty(userImage)) {
            message.setAttribute("userPic", userImage);
        }
//        String userName = AppSPUtils.getValueFromPrefrences("name", "");
        if (!TextUtils.isEmpty(userNm)) {
            message.setAttribute("userName", userNm);
        }

        if (!TextUtil.isEmpty("userPhone")) {
            message.setAttribute("userPhone", userPhone);
        }
//
////        String name = pref.getString("userId", "");
//
//        String userNm=pref.getString("userNm","");//昵称
//        String userImage=pref.getString("userImage","");
//        String userPhone=pref.getString("userPhone","");
//        String userImageOne= Url.urlHost+userImage;//头像
//
//        //设置要发送扩展消息用户昵称
//        message.setAttribute(Constant.USER_NAME,userNm);
//        message.setAttribute(Constant.USER_ID,userPhone);
//        //设置要发送扩展消息用户头像
//        message.setAttribute(Constant.HEAD_IMAGE_URL,userImageOne);
    }

    @Override
    public EaseCustomChatRowProvider onSetCustomChatRowProvider() {
        return new CustomChatRowProvider();
    }


    @Override
    public void onEnterToChatDetails() {
        if (chatType == Constant.CHATTYPE_GROUP) {
            startActivityForResult(
                    //GroupDetailsActivity 原来是这个activity GroupChatDetailActivity
                    (new Intent(getActivity(), GroupChatDetailActivity.class).putExtra("groupId", toChatUsername)),
                    REQUEST_CODE_GROUP_DETAIL);
        } else if (chatType == Constant.CHATTYPE_CHATROOM) {
//            startActivityForResult(new Intent(getActivity(), ChatRoomDetailsActivity.class).putExtra("roomId", toChatUsername), REQUEST_CODE_GROUP_DETAIL);
        }
    }

    @Override
    public void onAvatarClick(String username, int chatType) {
//        if (TextUtils.equals(username, EMClient.getInstance().getCurrentUser())) {
//            //不查看自己的资料
//            return;
//        }
        //handling when user click avatar
        Intent intent = new Intent(getActivity(), UserProfileActivity.class); //点击头像去会员资料
        intent.putExtra(EaseConstant.EXTRA_USER_ID, username);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        Log.v("lgq","个人信息。。。"+username+"..."+chatType);
        startActivity(intent);
    }

    @Override
    public void onAvatarLongClick(String username) {
        inputAtUsername(username);
    }


    @Override
    public boolean onMessageBubbleClick(EMMessage message) {
        if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)){
            RedPacketUtil.openRedPacket(getActivity(), chatType, message, toChatUsername, messageList);
            return true;
        } else if (message.getType().equals(EMMessage.Type.IMAGE)) {
            // 打开图片消息
            EMImageMessageBody eb = (EMImageMessageBody)message.getBody();
            Intent intent = new Intent();
//            intent.putExtra("image", eb.getRemoteUrl());
//            intent.setClass(getActivity(), PhotoScanActivity.class);
            //查看所有聊天历史图片
            intent.putExtra("imageUrls", com.alibaba.fastjson.JSONArray.toJSONString(getAllHistoryPic()));
            intent.putExtra("curImageUrl", eb.getRemoteUrl());
            intent.setClass(getActivity(), PhotoBrowserActivity.class);
            startActivity(intent);
            return true;
        } else if(message.getBooleanAttribute(ShareTypes.SHARE_URL_MSG, false)) {
            // 资讯、企业、商品、服务、贺卡分享
            if (!TextUtil.isEmpty(message.getStringAttribute(ShareTypes.SHARE_URL, ""))){
                int shareType = message.getIntAttribute(ShareTypes.SHARE_TYPE, -1);
                Intent intent = new Intent();
                switch (shareType) {
                    case ShareTypes.COMPANY_MAIN_PAGE:
                        //企业主页
                        String link = message.getStringAttribute(ShareTypes.SHARE_URL ,"");
                        String epId = TextUtil.URLRequest(link).get("param");
                        if (TextUtil.isEmpty(epId)) {
                            intent.setClass(getContext(), HomeADactivity.class);//有个聊天的标志
                        } else {
                            intent.setClass(getContext(), CompanyDetailWebActivity.class);//有个聊天的标志
                            intent.putExtra("epId", Integer.valueOf(epId));
                        }
                        intent.putExtra("url", link);
                        break;
                    case ShareTypes.COMPANY_MALL:
                        // 商城
                        String link1 = message.getStringAttribute(ShareTypes.SHARE_URL ,"");
                        String epId1 = TextUtil.URLRequest(link1).get("param");
                        intent.setClass(getContext(), ExternalWebActivity.class);
                        intent.putExtra("logo", message.getStringAttribute(ShareTypes.SHARE_LOGO, ""));
                        intent.putExtra("title", message.getStringAttribute(ShareTypes.SHARE_TITLE, ""));
                        intent.putExtra("content", message.getStringAttribute(ShareTypes.SHARE_CONTENT, ""));
                        intent.putExtra("url",message.getStringAttribute(ShareTypes.SHARE_URL, ""));
                        intent.putExtra("epId",epId1);
                        break;
                    case ShareTypes.COMMODITY_INFO:
                        //商品详情
                        intent.setClass(getContext(), My_CommodityActivity.class);
                        intent.putExtra("url", message.getStringAttribute(ShareTypes.SHARE_URL, ""));
                        intent.putExtra("logo", message.getStringAttribute(ShareTypes.SHARE_LOGO, ""));
                        intent.putExtra("title", message.getStringAttribute(ShareTypes.SHARE_TITLE, ""));
                        intent.putExtra("content", message.getStringAttribute(ShareTypes.SHARE_CONTENT, ""));
                        break;
                    case ShareTypes.SERVICE_INFO:
                        // 服务详情
                        intent.setClass(getContext(), My_ServiceInfoActivity.class);
                        intent.putExtra("url", message.getStringAttribute(ShareTypes.SHARE_URL, ""));
                        intent.putExtra("logo", message.getStringAttribute(ShareTypes.SHARE_LOGO, ""));
                        intent.putExtra("title", message.getStringAttribute(ShareTypes.SHARE_TITLE, ""));
                        intent.putExtra("content", message.getStringAttribute(ShareTypes.SHARE_CONTENT, ""));
                        break;
                    case ShareTypes.GREETING_CARD:
                        //贺卡
                        startActivity(new Intent(getActivity(), GreetingCardWebActivity.class).putExtra("url", Url.urlWeb + "/card-list"));
                        intent.setClass(getContext(), GreetingCardWebActivity.class);
                        intent.putExtra("url", message.getStringAttribute(ShareTypes.SHARE_URL, ""));
                        break;
                    case ShareTypes.INFORMATION_INFO:
                        //资讯详情
                    case ShareTypes.SERVICE_MAIN_PAGE:
                        // 服务
                    default:
                        intent.setClass(getContext(), HomeADactivity.class);
                        intent.putExtra("url", message.getStringAttribute(ShareTypes.SHARE_URL, ""));
                        break;
                }
                startActivity(intent);
            }
        }
        //end of red packet code
        return false;
    }

    private List<EMMessage> getAllHistoryMessages() {
        List<EMMessage> msgs = conversation.getAllMessages();
        try {
            int msgCount = msgs != null ? msgs.size() : 0;
            if (msgCount < conversation.getAllMsgCount() && msgCount < pagesize) {
                String msgId = null;
                if (msgs != null && msgs.size() > 0) {
                    msgId = msgs.get(0).getMsgId();
                }
                msgs.addAll(0, conversation.loadMoreMsgFromDB(msgId, pagesize - msgCount));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  msgs;
    }

    private List<String> getAllHistoryPic() {
        List<EMMessage> msgs = getAllHistoryMessages();
        List<String> pics = new ArrayList<>();
        for (EMMessage em: msgs) {
            if (em.getType().equals(EMMessage.Type.IMAGE)) {
                EMImageMessageBody body = (EMImageMessageBody)em.getBody();
                pics.add(body.getRemoteUrl());
            }
        }
        return pics;
    }
    @Override
    public void onCmdMessageReceived(List<EMMessage> messages) {
        //red packet code : 处理红包回执透传消息
        for (EMMessage message : messages) {
            EMCmdMessageBody cmdMsgBody = (EMCmdMessageBody) message.getBody();
            String action = cmdMsgBody.action();//获取自定义action
            if (action.equals(RPConstant.REFRESH_GROUP_RED_PACKET_ACTION)){
                RedPacketUtil.receiveRedPacketAckMessage(message);
                messageList.refresh();
            }
        }
        //end of red packet code
        super.onCmdMessageReceived(messages);
    }

    @Override
    public void onMessageBubbleLongClick(EMMessage message) {
        // no message forward when in chat room
        startActivityForResult((new Intent(getActivity(), ContextMenuActivity.class)).putExtra("message", message)
                        .putExtra("ischatroom", chatType == EaseConstant.CHATTYPE_CHATROOM),
                REQUEST_CODE_CONTEXT_MENU);
    }

    @Override
    public boolean onExtendMenuItemClick(int itemId, View view) {
        switch (itemId) {
            case ITEM_VIDEO:
                Intent intent = new Intent(getActivity(), ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT_VIDEO);
                break;
            case ITEM_FILE: //file 文件
                selectFileFromLocal();
                break;
            case ITEM_VOICE_CALL:
                startVoiceCall();
                break;
            case ITEM_VIDEO_CALL:
                startVideoCall();
                break;
            //red packet code : 进入发红包页面
            case ITEM_RED_PACKET:
//                       //注意：不再支持原有的startActivityForResult进入红包相关页面
                int itemType;
                if (chatType == EaseConstant.CHATTYPE_SINGLE) {
                    itemType = RPConstant.RP_ITEM_TYPE_SINGLE;
                    //小额随机红包
                    //itemType = RPConstant.RP_ITEM_TYPE_RANDOM;
                } else {
                    itemType = RPConstant.RP_ITEM_TYPE_GROUP;
                }
                RedPacketUtil.startRedPacket(getActivity(), itemType, toChatUsername, new RPSendPacketCallback() {
                    @Override
                    public void onGenerateRedPacketId(String redPacketId) {

                    }

                    @Override
                    public void onSendPacketSuccess(RedPacketInfo redPacketInfo) {
                        sendMessage(RedPacketUtil.createRPMessage(getActivity(), redPacketInfo, toChatUsername));
                    }
                });
                break;
//            case ITEM_TRANSFER_PACKET://进入转账页面
//                RedPacketUtil.startTransferActivityForResult(this, toChatUsername, REQUEST_CODE_SEND_TRANSFER_PACKET);
//                break;
            //end of red packet code
            default:
                break;
        }
        //keep exist extend menu
        return false;
    }

    /**
     * select file
     */
    //文件
    protected void selectFileFromLocal() {
//        Intent intent = null;
//        if (Build.VERSION.SDK_INT < 19) { //api 19 and later, we can't use this way, demo just select from images
//            intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//
//        } else {
//            intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        }
//        startActivityForResult(intent, REQUEST_CODE_SELECT_FILE);
        Intent getContentIntent = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
        startActivityForResult(intent, REQUEST_CHOOSER);
    }

    /**
     * make a voice call
     */
    protected void startVoiceCall() {
        if (!EMClient.getInstance().isConnected()) {
            ToastUtil.showWarning(getActivity(), getString(R.string.not_connect_to_server), Toast.LENGTH_SHORT);
        } else {
            startActivity(new Intent(getActivity(), VoiceCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // voiceCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * make a video call
     */
    protected void startVideoCall() {
        if (!EMClient.getInstance().isConnected())
            ToastUtil.showWarning(getActivity(), getString(R.string.not_connect_to_server), Toast.LENGTH_SHORT);
        else {
            startActivity(new Intent(getActivity(), VideoCallActivity.class).putExtra("username", toChatUsername)
                    .putExtra("isComingCall", false));
            // videoCallBtn.setEnabled(false);
            inputMenu.hideExtendMenuContainer();
        }
    }

    /**
     * chat row provider
     */
    private final class CustomChatRowProvider implements EaseCustomChatRowProvider {
        @Override
        public int getCustomChatRowTypeCount() {
            //here the number is the message type in EMMessage::Type
            //which is used to count the number of different chat row
            return 22;
        }

        @Override
        public int getCustomChatRowType(EMMessage message) {
            if(message.getType() == EMMessage.Type.TXT){
//            voice call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false)){
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VOICE_CALL : MESSAGE_TYPE_SENT_VOICE_CALL;
                }else if (message.getBooleanAttribute(BusinessCardEntity.CARD_TYPE, false)){
                    //名片
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_BUSINESS_CARD_CALL : MESSAGE_TYPE_SENT_BUSINESS_CARD_CALL;
                }else if (message.getBooleanAttribute(BusinessCardEntity.CARD_TIP_TYPE, false)) {
                    //名片提示“已索要名片”
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_BUSINESS_CARD_TIP : MESSAGE_TYPE_SENT_BUSINESS_CARD_TIP;
                } else if (message.getBooleanAttribute(ShareTypes.SHARE_URL_MSG, false)) {
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_URL : MESSAGE_TYPE_SENT_URL;
                }else if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
                    //video call
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_VIDEO_CALL : MESSAGE_TYPE_SENT_VIDEO_CALL;
                }
                //red packet code : 红包消息、红包回执消息以及转账消息的chatrow type
                else if (RedPacketUtil.isRandomRedPacket(message)) {
                    //小额随机红包
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RANDOM : MESSAGE_TYPE_SEND_RANDOM;
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {
                    //发送红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET : MESSAGE_TYPE_SEND_RED_PACKET;
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {
                    //领取红包消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_RED_PACKET_ACK : MESSAGE_TYPE_SEND_RED_PACKET_ACK;
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_TRANSFER_PACKET_MESSAGE, false)) {
                    //转账消息
                    return message.direct() == EMMessage.Direct.RECEIVE ? MESSAGE_TYPE_RECV_TRANSFER_PACKET : MESSAGE_TYPE_SEND_TRANSFER_PACKET;
                }
//            end of red packet code
            }
            return 0;
        }

        @Override
        public EaseChatRow getCustomChatRow(EMMessage message, int position, BaseAdapter adapter) {
            Log.v("Lgq","ger========getCustomChatRow====");//聊天头像
            if(message.getType() == EMMessage.Type.TXT){
//             voice call or video call
                if (message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VOICE_CALL, false) ||
                        message.getBooleanAttribute(Constant.MESSAGE_ATTR_IS_VIDEO_CALL, false)){
                    return new ChatRowVoiceCall(getActivity(), message, position, adapter);
                }
//            red packet code : 红包消息、红包回执消息以及转账消息的chat row
                else if (RedPacketUtil.isRandomRedPacket(message)) {//小额随机红包
                    return new ChatRowRandomPacket(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_MESSAGE, false)) {//红包消息
                    return new ChatRowRedPacket(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE, false)) {//红包回执消息
                    return new ChatRowRedPacketAck(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(RPConstant.MESSAGE_ATTR_IS_TRANSFER_PACKET_MESSAGE, false)) {//转账消息
                    return new ChatRowRedPacketAck(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(BusinessCardEntity.CARD_TYPE, false)) {
                    return new EaseBusinessCardChatrow(getActivity(), message, position, adapter);
                } else if (message.getBooleanAttribute(BusinessCardEntity.CARD_TIP_TYPE, false)) {
                    return new EaseBusinessCardTipChatrow(getActivity(), message, position, adapter);
                }
                else if (message.getBooleanAttribute(ShareTypes.SHARE_URL_MSG, false)) {
                    // 资讯分享
                    return new EaseShareQiliaoChatrow(getActivity(), message, position, adapter);
                }
//            end of red packet code
            }
            return null;
        }
    }

    public void addCard(final String friendPhone) {//好友
        String url = Url.addcard;
        RequestParams params = new RequestParams();
//        Log.v("lgq",".......username="+UserNative.getPhone()+"...friendname="+friendPhone+"...userid="+UserNative.getId()+"...d登录="+UserNative.getName());
        params.put("activePhone", UserNative.getPhone());//用户电话
        params.put("passivePhone", friendPhone);//好友电话
        params.put("key", UserNative.getAesKes());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        longtextte.setText("已发送添加请求，请耐心等待对方通过");
                        lockcompanyte.setVisibility(View.GONE);
                        // 显示“已申请索要名片提示”
                        EMMessage message = EMMessage.createTxtSendMessage("已申请索要名片", toChatUsername);
                        message.setAttribute(BusinessCardEntity.CARD_TIP_TYPE, true);
                        message.setAttribute(BusinessCardEntity.CARD_TIP, "已申请索要名片");
                        sendMsg(message);

                        EMMessage msg = EMMessage.createTxtSendMessage(Uname + "的名片", toChatUsername);
                        msg.setAttribute(BusinessCardEntity.CARD_TYPE, true);
                        msg.setAttribute(BusinessCardEntity.CARD_POSITION, UserNative.getEpPosition());
                        msg.setAttribute(BusinessCardEntity.CARD_USER_NAME, Uname );
                        msg.setAttribute(BusinessCardEntity.CARD_USER_PHONE, UserNative.getPhone() );
                        msg.setAttribute(BusinessCardEntity.CARD_EP_NAME, EPname );
                        msg.setAttribute(BusinessCardEntity.CARD_ADDRESS, Uaddress );
                        msg.setAttribute(BusinessCardEntity.CARD_LOGO, EPlogoUrl );//设置消息类型,默认是单聊，如果不设置，在群聊中就收不到消息

                        sendMsg(msg);
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
//                    Logger.e("数据解析出错");
                }
            }
        });

    }

    protected void sendMsg(EMMessage msg) {
        sendMessage(msg);
    }

    public void addContact(final String phone) {
//        if (EMClient.getInstance().getCurrentUser().equals(phone)) {
//        Log.v("lgq","..........."+phone+"...."+ UserNative.getPhone()+"...."+EMClient.getInstance().getCurrentUser());
        if (UserNative.getPhone().equals(phone)) {
            new EaseAlertDialog(getContext(), R.string.not_add_myself).show();//不能添加自己
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

        progressDialog = new ProgressDialog(getContext());
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
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
//                            mTvSendMessage.setEnabled(false);
                            String s1 = getResources().getString(R.string.send_successful);//发送请求成功,等待对方验证
                            ToastUtil.showSuccess(getContext(), s1, Toast.LENGTH_SHORT);
                        }
                    });
                } catch (final Exception e) {
                    getActivity().runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            ToastUtil.showError(getContext(), s2 + e.getMessage(), Toast.LENGTH_LONG);//请求添加好友失败
                        }
                    });
                }
            }
        }).start();
        Log.v("lgq","添加好友。。。。。"+phone);
//        addFriendSingle(phone);//只走环信
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                                     @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
    public void overEC() {//结束会话
        String url = Url.releaseServerPhone;
        RequestParams params = new RequestParams();
        params.put("userPhone", toChatUsername);//用户电话
        params.put("key", UserNative.getAesKes());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        EMClient.getInstance().chatManager().deleteConversation(toChatUsername, true);//删除会话
                        ToastUtil.showSuccess(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        getActivity().finish();

                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
//                    Logger.e("数据解析出错");
                }
            }
        });

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        client.cancelAllRequests(true);
    }
}

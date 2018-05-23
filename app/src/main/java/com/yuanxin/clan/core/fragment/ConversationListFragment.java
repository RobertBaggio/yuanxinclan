package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.adapter.EaseConversationAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.ui.EaseConversationListFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.event.ChatContactEvent;
import com.yuanxin.clan.core.event.ChatGroupEvent;
import com.yuanxin.clan.core.event.FriendChangeEvent;
import com.yuanxin.clan.core.event.MessageChangeEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.BusinessMessageDao;
import com.yuanxin.clan.core.huanxin.BuyerMessageDao;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.huanxin.DemoDBManager;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.huanxin.InviteMessgeDao;
import com.yuanxin.clan.core.huanxin.NewMsgActivity;
import com.yuanxin.clan.core.huanxin.SellerMessageDao;
import com.yuanxin.clan.core.util.JpushCode;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo1 on 2017/2/4.
 * 会话fragment
 */
public class ConversationListFragment extends EaseConversationListFragment {

    private TextView notifyText;
    private TextView notifyPoint;
    private int  numnews;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void initView() {
        super.initView();
        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());
        hideTitleBar();///隐藏标题栏
        View serviceView = (LinearLayout) View.inflate(getActivity(), R.layout.service_layout, null);
        headerView.setVisibility(View.VISIBLE);
        headerView.addView(serviceView);
        LinearLayout oneLayout = (LinearLayout) serviceView.findViewById(R.id.item_present_custom_made_layout_one);
        //未读通知数量
        notifyText = (TextView) serviceView.findViewById(R.id.item_present_custom_made_money);
        notifyPoint = (TextView) serviceView.findViewById(R.id.tvChatNum);
        getNotifyCount();
        oneLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overEC();
            }
        });
        LinearLayout twoLayout = (LinearLayout) serviceView.findViewById(R.id.item_present_custom_made_layout);
        twoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getContext(), NewFriendsMsgActivity.class)); //添加  对方同意后 加到通讯录 点击通讯录 跳到聊天详情 当有聊天记录 会话列表就会有显示
                startActivity(new Intent(getContext(), NewMsgActivity.class)); //添加  对方同意后 加到通讯录 点击通讯录 跳到聊天详情 当有聊天记录 会话列表就会有显示
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getNotifyCount();
        refresh();
    }

    @Override
    protected void setUpView() {
        super.setUpView();

        conversationListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    position = position - 1;
                }
                EMConversation conversation = conversationListView.getItem(position);
                String username = conversation.conversationId();
                EaseUser easeUser = DemoHelper.getInstance().getUserInfo(username);

                if (username.equals(EMClient.getInstance().getCurrentUser()))//不能跟自己聊天
                    ToastUtil.showWarning(getActivity(), getString(R.string.Cant_chat_with_yourself), Toast.LENGTH_SHORT);
                else {
                    // start chat acitivity
                    Intent intent = new Intent(getActivity(), PersionChatActivity.class);//跳转到个人聊天页面
                    intent.putExtra("type", "");
                    if (conversation.getType() == EMConversation.EMConversationType.GroupChat) {
                        // intent.putExtra("toNick", easeUser.getNick());
                        EMGroup group = EMClient.getInstance().groupManager().getGroup(username);
                        intent.putExtra("toNick", group != null ? group.getGroupName() : easeUser.getNick());
                    } else {
                        intent.putExtra("toNick", easeUser.getNick());
                    }
                    intent.putExtra("toImage", easeUser.getAvatar());
                    if (conversation.isGroup()) {
                        if (conversation.getType() == EMConversation.EMConversationType.ChatRoom) {
                            // it's group chat
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_CHATROOM);//聊天室 群聊
                        } else {
                            intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);//群聊
                        }
                    }
                    // it's single chat
                    intent.putExtra(Constant.EXTRA_USER_ID, username);//个人
                    startActivity(intent);
                }
            }
        });
        //增加从网络获取头像的监听
        ListAdapter listAdapter = ((HeaderViewListAdapter) conversationListView.getAdapter()).getWrappedAdapter();
        EaseConversationAdapter easeConversationAdapter = (EaseConversationAdapter) listAdapter;
        easeConversationAdapter.setAvatarGetByHttp(new EaseConversationAdapter.AvatarGetByHttp() {
            @Override
            public void getGroupAvater(String username, ImageView imageView, TextView name) {
                getGroupAvaterByHttp(username, imageView, name);
            }

            @Override
            public void getUserAvater(String username, ImageView imageView, TextView name) {
                getUserAvaterByHttp(username, imageView, name);
            }
        });
        easeConversationAdapter.notifyDataSetChanged();
    }


    @Override
    public void deleteConversation(String id) {
        super.deleteConversation(id);
        InviteMessgeDao inviteMessgeDao = new InviteMessgeDao(getActivity());
        inviteMessgeDao.deleteMessage(id);
        // update unread count
        EventBus.getDefault().post(new MessageChangeEvent());
    }

    private void getUserAvaterByHttp(final String username, final ImageView imageView, final TextView name) {
        String url = Url.findFriend;
        RequestParams params = new RequestParams();
        params.put("userPhone", username);//用户id
//        Log.v("lgq","name=]]=====getUserAvaterByHttp========"+username);
        doHttpGet(url, params, new BaseFragment.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                Log.v("lgq","..getUserAvaterByHttp..."+s);

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject item = object.getJSONObject("data");
                        String userPhone = item.getString("userPhone");
                        if (TextUtils.equals(userPhone, username)) {
                            String userImage = item.getString("userImage");
                            final String userImageOne = Url.img_domain + userImage+Url.imageStyle640x640;
//                            Log.v("lgq","网络头像。。。"+userImageOne);
                            final String userNm = item.getString("userNm");
//                            Log.v("lgq","网络名字。。。"+userNm);
                            ImageManager.load(getActivity(), userImageOne, R.drawable.ease_default_avatar, imageView);
                            name.setText(userNm);
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    EaseUser easeUser = DemoHelper.getInstance().getUserInfo(username);
                                    if (easeUser != null) {
                                        easeUser.setAvatar(userImageOne);
                                        easeUser.setNick(userNm);
                                        easeUser.setNickname(userNm);
                                        DemoHelper.getInstance().getModel().saveContact(easeUser);
                                    }
                                }
                            }).start();
                        }
                    } else {
//                        Toast.makeText(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getGroupAvaterByHttp(String username, final ImageView imageView, final TextView name) {
        String url = Url.getMyGroupsInfo;
        RequestParams params = new RequestParams();
        params.put("groupId", username);//用户id
//        Log.v("lgq","name=]]=====getGroupAvaterByHttp========"+username);
        doHttpGet(url, params, new BaseFragment.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject dataObject = object.getJSONObject("data");
                        final String groupId = dataObject.getString("groupId");//群id
                        String groupImage = dataObject.getString("groupImage");//群头像
                        final String groupImageOne = Url.img_domain + groupImage+Url.imageStyle640x640;
                        final String groupNm = dataObject.getString("groupNm");//群
                        String createNm = dataObject.getString("createNm");//群主
                        ImageManager.load(getActivity(), groupImageOne, R.drawable.ease_groups_icon, imageView);
                        name.setText(groupNm);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                EaseUser easeUser = DemoHelper.getInstance().getUserInfo(groupId);
                                if (easeUser != null) {
                                    easeUser.setAvatar(groupImageOne);
                                    easeUser.setNick(groupNm);
                                    easeUser.setNickname(groupNm);
                                    DemoHelper.getInstance().getModel().saveContact(easeUser);
                                }
                            }
                        }).start();
                    } else {
//                        Toast.makeText(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                        Log.v("lgq","......"+object.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void overEC() {//结束会话
        String url = Url.getServerPhone;
        RequestParams params = new RequestParams();
        doHttpGet(url, params, new BaseFragment.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String ph = object.getString("data");
                        Intent intent = new Intent(getContext(), PersionChatActivity.class);
                        //userId 一定加""
                        intent.putExtra(Constant.EXTRA_USER_ID, ph);
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        intent.putExtra(Constant.USER_NAME, UserNative.getName());
                        intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                        String add = UserNative.getCity()+UserNative.getArea()+UserNative.getDetail();
                        intent.putExtra(Constant.ADDRESS,add );
                        intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                        intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                        intent.putExtra("type", "圆心客服");
                        startActivity(intent);
//                        dad(ph);
//                        Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatGroupEvent chatGroupEvent) {
        refresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatContactEvent chatContactEvent) {
        if (chatContactEvent.getCode() == JpushCode.DELETE_FRIEND) {
            EMClient.getInstance().chatManager().deleteConversation(chatContactEvent.getFriendName(), true);
            Log.v("lgq","ccccccccccChatContactEventcccccccMAIN");
        }
        refresh();
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(FriendChangeEvent friendChangeEvent) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                getNotifyCount();
                refresh();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        client.cancelAllRequests(true);
        EventBus.getDefault().unregister(this);
    }


    //获取未操作的消息数量
    private void getNotifyCount() {
        int inviteNumber = DemoDBManager.getInstance().getUnreadNotifyCount();
        SellerMessageDao sellerMessageDao = new SellerMessageDao(getContext());
        BuyerMessageDao buyerMessageDao = new BuyerMessageDao(getContext());
        BusinessMessageDao businessMessageDao = new BusinessMessageDao(getContext());
        int sellerMsgNumber = sellerMessageDao.getUnreadMessagesCount();
        int buyerMsgNumber = buyerMessageDao.getUnreadMessagesCount();
        int businessMsgNumber = businessMessageDao.getUnreadMessagesCount();
        int number = inviteNumber + sellerMsgNumber + buyerMsgNumber + businessMsgNumber;
        if (number > 0) {
            notifyText.setText("你有" + number + "条新信息需要处理");
            notifyPoint.setVisibility(View.VISIBLE);
        } else {
            notifyText.setText("暂无新信息");
            notifyPoint.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        numnews=0;
    }

    /***
     * 好友变化listener
     *
     */
    public class MyContactListener implements EMContactListener {

        @Override
        public void onContactAdded(String username) {
            numnews++;
            handler.sendMessage(new Message());
        }

        @Override
        public void onContactDeleted(String username) {
            numnews++;
            handler.sendMessage(new Message());
        }

        @Override
        public void onContactInvited(String username, String reason) {
            numnews++;
            Message msg = new Message();
            msg.what = 1;
            handler.sendMessage( msg);
        }

        @Override
        public void onFriendRequestAccepted(String username) {
            numnews++;
            handler.sendMessage(new Message());

        }

        @Override
        public void onFriendRequestDeclined(String username) {
            // your request was refused
            Log.d(username, username + " refused to your request");
        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getNotifyCount();
        }
    };

    protected void doHttpGet(String url, RequestParams params, final BaseFragment.RequestCallback c) {
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {
//            ViewUtils.AlertDialog(getContext(), "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//        } else {
            params.put("key", aesKes);
            client.get(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    c.onFailure(i, headers, s, throwable);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        c.onSuccess(i, headers, s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//        }
    }

    /**
     * connected to server
     */
    protected static void onConnectionConnected() {
//        refresh();
    }

    /**
     * disconnected with server
     */
    protected static void onConnectionDisconnected() {
//        refresh();
    }
}

package com.yuanxin.clan.core.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.event.MessageChangeEvent;
import com.yuanxin.clan.core.fragment.ChatFragment;
import com.yuanxin.clan.core.huanxin.BaseActivity;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.huanxin.PermissionsManager;
import com.yuanxin.clan.core.util.MyShareUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by lenovo1 on 2017/3/9.
 * 聊天类
 */
//个人会话 单聊
public class PersionChatActivity extends BaseActivity {
    public static PersionChatActivity activityInstance;
    private EaseChatFragment chatFragment;//EaseUI里的
    String toChatUsername, type;//type 与企业聊天中
    private int busnissid;


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        setContentView(R.layout.activity_persion_chat); //是一个FrameLayout
        activityInstance = this;

        //get user id or group id


        toChatUsername = getIntent().getExtras().getString(Constant.EXTRA_USER_ID);//传入对方用户id chatType  群聊2  聊天室3
        type = getIntent().getExtras().getString("type");//与企业聊天中
        String toNick = getIntent().getExtras().getString("toNick");//对方的昵称
        String toImage = getIntent().getExtras().getString("toImage");//对方的头像
        MyShareUtil.sharedPint("delegroup",0);
        //use EaseChatFratFragment
//        chatFragment = new ChatFragment();//继承EaseUI的
//        //pass parameters to chat fragment
//        chatFragment.setArguments(getIntent().getExtras());
//        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();//把Fragment添加到Activity

        /**
         * BQMM集成
         * 这里原先的逻辑是直接new EaseChatFragment使用，但这样做是会引起问题的
         * 在应用被后台清理掉之后再次启动时，原有逻辑会造成两个该Fragment的实例同时存在，导致BQMM界面异常
         * 现在将逻辑改为先从FragmentManager中查找原先的Fragment，如果没有再新建
         */
        chatFragment = (EaseChatFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        if (chatFragment == null) {
            chatFragment = new ChatFragment();
            //传入参数
            chatFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
        }

        //通知未读消息数量变化
        EventBus.getDefault().post(new MessageChangeEvent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // make sure only one chat activity is opened
        String username = intent.getStringExtra(Constant.EXTRA_USER_ID);
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }
    }


    public String getToChatUsername() {
        return toChatUsername;
    }//MainActivity

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int a = MyShareUtil.getSharedInt("delegroup");
        if (a==1){
            finish();
        }
    }
}

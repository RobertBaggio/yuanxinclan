package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.ui.EaseChatFragment;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.fragment.ChatCollectiveFragment;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/1/26.
 */
public class ChatActivity extends BaseActivity {
    @BindView(R.id.content)
    FrameLayout content;
    @BindView(R.id.activity_chat_new_persion)
    TextView activityChatNewPersion;
    @BindView(R.id.activity_chat_new_collective)
    TextView activityChatNewCollective;
    @BindView(R.id.layout_navigation)
    LinearLayout layoutNavigation;
    private ChatCollectiveFragment chatCollectiveFragment;
    private EaseChatFragment easeChatFragment;
    //    private ChatPersionFragment chatPersionFragment;
    private TextView persionText;
    private TextView collectiveText;
    private FragmentManager fragmentManager;


    @Override
    public int getViewLayout() {
        return R.layout.activity_chat_new;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        fragmentManager = getSupportFragmentManager();
        //避免Fragment重叠
        if (savedInstanceState != null) {
//            chatPersionFragment = (ChatPersionFragment) fragmentManager.findFragmentByTag("chatPersionFragment");
            easeChatFragment = (EaseChatFragment) fragmentManager.findFragmentByTag("easeChatFragment");
            chatCollectiveFragment = (ChatCollectiveFragment) fragmentManager.findFragmentByTag("chatCollectiveFragment");
        }
        // 第一次启动时选中第0个tab
        setTabSelection(0);
    }


    @OnClick({R.id.activity_chat_new_persion, R.id.activity_chat_new_collective})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_chat_new_persion:
                setTabSelection(0);
                break;
            case R.id.activity_chat_new_collective:
                setTabSelection(1);
                break;
        }
    }

    public void setTabSelection(int index) {
//        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        //更新消息图标
//        Intent startIntent = new Intent(this, InfoNumService.class);
//        startService(startIntent);
        switch (index) {
            case 0:
                // 当点击了主页tab时，改变控件的图片颜色
//                homeImage.setImageResource(R.drawable.n_home_l);
//                if (easeChatFragment == null) {
//                    // 如果HomeFragment为空，则创建一个并添加到界面上
//                    easeChatFragment = new ChatPersionFragment();
//                    transaction.add(R.id.content, easeChatFragment, "easeChatFragment");
//                } else {
//                    // 如果HomeFragment不为空，则直接将它显示出来
//                    transaction.show(chatPersionFragment);
//                }
                if (easeChatFragment == null) {
                    // 如果HomeFragment为空，则创建一个并添加到界面上
                    easeChatFragment = new EaseChatFragment();
                    transaction.add(R.id.content, easeChatFragment, "easeChatFragment");
                } else {
                    // 如果HomeFragment不为空，则直接将它显示出来
                    transaction.show(easeChatFragment);
                }
                break;
            case 1:
                // 当点击了我的tab时，改变控件的图片颜色
//                myImage.setImageResource(R.drawable.n_my_l);
                if (chatCollectiveFragment == null) {
                    // 如果myFragment为空，则创建一个并添加到界面上
                    chatCollectiveFragment = new ChatCollectiveFragment();
                    transaction.add(R.id.content, chatCollectiveFragment, "chatCollectiveFragment");
                } else {
                    // 如果myFragment不为空，则直接将它显示出来
                    transaction.show(chatCollectiveFragment);
                }
                break;
            default:
                break;

        }
        transaction.commit();
    }

//    private void clearSelection() {
//        activityChatNewPersion.set;
//        myImage.setImageResource(R.drawable.n_my_h);
//    }

    private void hideFragments(FragmentTransaction transaction) {
        if (chatCollectiveFragment != null) {
            transaction.hide(chatCollectiveFragment);
        }
        if (easeChatFragment != null) {
            transaction.hide(easeChatFragment);
        }
    }
}

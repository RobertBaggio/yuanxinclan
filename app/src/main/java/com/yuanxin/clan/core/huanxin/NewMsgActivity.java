/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuanxin.clan.core.huanxin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.event.AgreeRefundEvent;
import com.yuanxin.clan.core.event.BusinessMsgEvent;
import com.yuanxin.clan.core.event.BusinessShortMsgEvent;
import com.yuanxin.clan.core.event.DeliverGoodsEvent;
import com.yuanxin.clan.core.event.FriendChangeEvent;
import com.yuanxin.clan.core.event.RefundEvent;
import com.yuanxin.clan.core.event.RefundSuccessEvent;
import com.yuanxin.clan.core.event.TakeDeliverGoodsEvent;
import com.yuanxin.clan.core.event.WaitingDeliverGoodsEvent;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * Application and notification
 */
// 进入申请与通知页面
public class NewMsgActivity extends BaseActivity{

    @BindView(R.id.item_new_message_layout)
    LinearLayout newFriendsMsgLayout;
    @BindView(R.id.item_seller_message_layout)
    LinearLayout newSellerMessageLayout;
    @BindView(R.id.item_buyer_message_layout)
    LinearLayout newBuyerMessageLayout;
    @BindView(R.id.item_business_message_layout)
    LinearLayout newBusinessMessageLayout;
    @BindView(R.id.new_friend_count)
    TextView newFriendCount;
    @BindView(R.id.seller_count)
    TextView sellerCount;
    @BindView(R.id.buyer_count)
    TextView buyerCount;
    @BindView(R.id.business_count)
    TextView businessCount;
    @BindView(R.id.new_friend_tip)
    TextView newFriendTip;
    @BindView(R.id.seller_msg_tip)
    TextView sellerMsgTip;
    @BindView(R.id.buyer_msg_tip)
    TextView buyerMsgTip;
    @BindView(R.id.business_msg_tip)
    TextView businessMsgTip;
    private InviteMessgeDao inviteMessgeDao;
    private SellerMessageDao sellerMessageDao;
    private BuyerMessageDao buyerMessageDao;
    private BusinessMessageDao businessMessageDao;
    private int unreadFriendsMsgCount = 0;
    private int unreadSellerMsgCount = 0;
    private int unreadBuyerMsgCount = 0;
    private int unreadBusinessMsgCount = 0;

    @Override
    public int getViewLayout() {
        return R.layout.em_activity_new_msg;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        EventBus.getDefault().register(this);
        showUnreadFriendMessage();
        showSellerCount();
        showBuyerCount();
        showBusinessCount();
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @OnClick({R.id.item_new_message_layout, R.id.item_seller_message_layout, R.id.item_buyer_message_layout, R.id.item_business_message_layout, R.id.rlLeft})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_new_message_layout:
                inviteMessgeDao.saveUnreadMessageCount(0);
                startActivity(new Intent(NewMsgActivity.this, NewFriendsMsgActivity.class));
                break;
            case R.id.item_seller_message_layout:
//                startActivity(new Intent(NewMsgActivity.this, NewFriendsMsgActivity.class));
                break;
            case R.id.item_buyer_message_layout:
//                startActivity(new Intent(NewMsgActivity.this, NewFriendsMsgActivity.class));
                break;
            case R.id.item_business_message_layout:
                startActivity(new Intent(NewMsgActivity.this, BusinessMsgActivity.class));
                break;
            case R.id.rlLeft:
                onBackPressed();
                break;
            default:
                break;
        }
    }
    //新的联系人消息
    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(FriendChangeEvent friendChangeEvent) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                //设置未读消息数量
                showUnreadFriendMessage();
            }
        });
    }
    //新的卖家订单消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefundEvent refundEvent) {
        showSellerCount();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(TakeDeliverGoodsEvent refundEvent) {
        showSellerCount();
    }
    // 新的买家订单消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(DeliverGoodsEvent refundEvent) {
        showBuyerCount();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(WaitingDeliverGoodsEvent refundEvent) {
        showSellerCount();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(AgreeRefundEvent refundEvent) {
        showSellerCount();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefundSuccessEvent refundEvent) {
        showSellerCount();
    }

    // 新的商圈消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BusinessMsgEvent businessMsgEvent) {
        showBusinessCount();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BusinessShortMsgEvent businessShortMsgEvent) {
        showBusinessCount();
    }

    private void showUnreadFriendMessage() {
        if (inviteMessgeDao == null) {
            inviteMessgeDao = new InviteMessgeDao(this);
        }
        int number = inviteMessgeDao.getUnreadMessagesCount();
        if (number > 0) {
            newFriendCount.setVisibility(View.VISIBLE);
            newFriendCount.setText(String.valueOf(number));
            newFriendTip.setText("有" + number +"位新的联系人请求添加");
            ShortcutBadger.applyCount(getApplicationContext(), number); //for 1.1.4+
        } else {
            newFriendCount.setVisibility(View.GONE);
            newFriendTip.setText(getString(R.string.no_unread_msg));
            ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
        }
    }

    private void showSellerCount() {
        if (sellerMessageDao == null) {
            sellerMessageDao = new SellerMessageDao(this);
        }
        //设置未读消息数量
        unreadSellerMsgCount = sellerMessageDao.getUnreadMessagesCount();
        if (unreadSellerMsgCount > 0) {
            sellerCount.setVisibility(View.VISIBLE);
            sellerCount.setText(String.valueOf(unreadSellerMsgCount));
            sellerMsgTip.setText("有"+ unreadSellerMsgCount +"笔订单待处理");
//            ShortcutBadger.applyCount(getApplicationContext(), number); //for 1.1.4+
        } else {
            sellerCount.setVisibility(View.GONE);
            sellerMsgTip.setText(getString(R.string.no_unread_msg));
//            ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
        }
    }

    private void showBuyerCount() {
        if (buyerMessageDao == null) {
            buyerMessageDao = new BuyerMessageDao(this);
        }
        //设置未读消息数量
        unreadBuyerMsgCount = buyerMessageDao.getUnreadMessagesCount();
        if (unreadBuyerMsgCount > 0) {
            buyerCount.setVisibility(View.VISIBLE);
            buyerCount.setText(String.valueOf(unreadBuyerMsgCount));
            buyerMsgTip.setText("有"+ unreadBuyerMsgCount +"笔订单待处理");
//            ShortcutBadger.applyCount(getApplicationContext(), number); //for 1.1.4+
        } else {
            buyerCount.setVisibility(View.GONE);
            buyerMsgTip.setText(getString(R.string.no_unread_msg));
//            ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
        }
    }

    private void showBusinessCount() {
        if (businessMessageDao == null) {
            businessMessageDao = new BusinessMessageDao(this);
        }
        //设置未读消息数量
        unreadBusinessMsgCount = businessMessageDao.getUnreadMessagesCount();
        if (unreadBusinessMsgCount > 0) {
            businessCount.setVisibility(View.VISIBLE);
            businessCount.setText(String.valueOf(unreadBusinessMsgCount));
            businessMsgTip.setText("有"+ unreadBusinessMsgCount +"条未读消息");
//            ShortcutBadger.applyCount(getApplicationContext(), number); //for 1.1.4+
        } else {
            businessCount.setVisibility(View.GONE);
            businessMsgTip.setText(getString(R.string.no_unread_msg));
//            ShortcutBadger.removeCount(getApplicationContext()); //for 1.1.4+
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUnreadFriendMessage();
        showBuyerCount();
        showSellerCount();
        showBusinessCount();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

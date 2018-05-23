package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;

import static com.hyphenate.easeui.EaseConstant.CARD_USER_NAME;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/20 0020 18:07
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class EaseBusinessCardChatrow extends EaseChatRow{
    /**
     * 环信信息样式
     * 名片
     * 接收消息
     */
    private ImageView logoImageView;
    private TextView userNameTextView;
    private TextView userPositionTextView;
    private TextView phoneTextView;
    private TextView addressTextView;
    private TextView epNameTextView;

    public EaseBusinessCardChatrow(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(EaseConstant.CARD_TYPE, false)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_receive_business_card : R.layout.ease_row_sent_business_card, this);
        }
    }

    @Override
    protected void onFindViewById() {
//		contentView = (TextView) findViewById(R.id.tv_chatcontent);
        logoImageView = (ImageView) findViewById(R.id.card_logo);
        userNameTextView = (TextView) findViewById(R.id.user_name);
        userPositionTextView = (TextView) findViewById(R.id.user_position);
        phoneTextView = (TextView) findViewById(R.id.phone);
        addressTextView = (TextView) findViewById(R.id.address);
        epNameTextView = (TextView) findViewById(R.id.ep_name);

    }

    @Override
    public void onSetUpView() {
        EMTextMessageBody textMessageBody = (EMTextMessageBody) message.getBody();
        if (!isEmpty(message.getStringAttribute(EaseConstant.CARD_LOGO, null))) {
            Glide.with(context).load(message.getStringAttribute(EaseConstant.CARD_LOGO, null)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(logoImageView);
        }
        if (!isEmpty(message.getStringAttribute(CARD_USER_NAME, null))) {
            userNameTextView.setText(message.getStringAttribute(CARD_USER_NAME, null));
        }
        if (!isEmpty(message.getStringAttribute(EaseConstant.CARD_POSITION, null))) {
            userPositionTextView.setText(message.getStringAttribute(EaseConstant.CARD_POSITION, null));
        }
        if (!isEmpty(message.getStringAttribute(EaseConstant.CARD_USER_PHONE, null))) {
            phoneTextView.setText(message.getStringAttribute(EaseConstant.CARD_USER_PHONE, null));
        }
        if (!isEmpty(message.getStringAttribute(EaseConstant.CARD_ADDRESS, null))) {
            addressTextView.setText(message.getStringAttribute(EaseConstant.CARD_ADDRESS, null));
        }
        if (!isEmpty(message.getStringAttribute(EaseConstant.CARD_EP_NAME, null))) {
            epNameTextView.setText(message.getStringAttribute(EaseConstant.CARD_EP_NAME, null));
        }
    }

    private boolean isEmpty(String str) {

        return TextUtils.isEmpty(str) || "null".equals(str);
    }

    @Override
    protected void onUpdateView() {
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onBubbleClick() {
        // TODO Auto-generated method stub

    }



}


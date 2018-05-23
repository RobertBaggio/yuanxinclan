package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.TextUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/20 0020 18:07
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class EaseBusinessCardTipChatrow extends EaseChatRow{
    /**
     * 环信信息样式
     * 名片
     * 接收消息
     */
    private TextView tipTextView;

    public EaseBusinessCardTipChatrow(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        if (message.getBooleanAttribute(EaseConstant.CARD_TIP_TYPE, false)) {
            inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_send_card_tip : R.layout.ease_row_send_card_tip, this);
        }
    }

    @Override
    protected void onFindViewById() {
        tipTextView = (TextView) findViewById(R.id.tip);
    }

    @Override
    public void onSetUpView() {
        if (!isEmpty(message.getStringAttribute(EaseConstant.CARD_TIP, null))) {
            tipTextView.setText(message.getStringAttribute(EaseConstant.CARD_TIP, null));
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


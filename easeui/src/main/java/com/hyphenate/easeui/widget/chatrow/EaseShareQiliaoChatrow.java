package com.hyphenate.easeui.widget.chatrow;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.utils.ShareTypes;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/20 0020 18:07
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class EaseShareQiliaoChatrow extends EaseChatRow{
    /**
     * 环信信息样式
     * 名片
     * 接收消息
     */
    private ImageView logoImageView;
    private TextView titleTextView;
    private TextView contentTextView;
    private TextView epNameTextView;

    public EaseShareQiliaoChatrow(Context context, EMMessage message, int position, BaseAdapter adapter) {
        super(context, message, position, adapter);
    }

    @Override
    protected void onInflateView() {
        inflater.inflate(message.direct() == EMMessage.Direct.RECEIVE ? R.layout.ease_row_receive_share_qiliao: R.layout.ease_row_sent_share_qiliao, this);
    }

    @Override
    protected void onFindViewById() {
//		contentView = (TextView) findViewById(R.id.tv_chatcontent);
        logoImageView = (ImageView) findViewById(R.id.share_logo);
        titleTextView = (TextView) findViewById(R.id.title);
        contentTextView = (TextView) findViewById(R.id.content);
        epNameTextView = (TextView) findViewById(R.id.ep_name);

    }

    @Override
    public void onSetUpView() {
        EMTextMessageBody textMessageBody = (EMTextMessageBody) message.getBody();
        if (!isEmpty(message.getStringAttribute(ShareTypes.SHARE_LOGO, null))) {
            Glide.with(context).load(message.getStringAttribute(ShareTypes.SHARE_LOGO, null)).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(logoImageView);
        }
        if (!isEmpty(message.getStringAttribute(ShareTypes.SHARE_TITLE, null))) {
            titleTextView.setText(message.getStringAttribute(ShareTypes.SHARE_TITLE, null));
        }
        if (!isEmpty(message.getStringAttribute(ShareTypes.SHARE_CONTENT, null))) {
            contentTextView.setText(message.getStringAttribute(ShareTypes.SHARE_CONTENT, null));
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
        Log.e("hxw", message.getStringAttribute(ShareTypes.SHARE_URL, ""));
        int type = message.getIntAttribute(ShareTypes.SHARE_TYPE, -1);
        if (type == -1) {
            return;
        }
        switch (type) {
            case ShareTypes.COMPANY_MAIN_PAGE:

                break;
            case ShareTypes.COMMODITY_INFO:
                break;
            case ShareTypes.COMPANY_MALL:
                break;
            case ShareTypes.GREETING_CARD:
                break;
            case ShareTypes.INFORMATION_INFO:
                break;
            case ShareTypes.SERVICE_INFO:
                break;
            case ShareTypes.SERVICE_MAIN_PAGE:
                break;
        }
//        context.startActivity(new Intent(context, HomeADactivity.class).putExtra("url", link));
    }

}


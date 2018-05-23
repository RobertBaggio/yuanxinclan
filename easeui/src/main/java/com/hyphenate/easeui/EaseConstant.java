/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hyphenate.easeui;

public class EaseConstant {
    public static final String MESSAGE_ATTR_IS_VOICE_CALL = "is_voice_call";
    public static final String MESSAGE_ATTR_IS_VIDEO_CALL = "is_video_call";
    
    public static final String MESSAGE_TYPE_RECALL = "message_recall";
    
    public static final String MESSAGE_ATTR_IS_BIG_EXPRESSION = "em_is_big_expression";
    public static final String MESSAGE_ATTR_EXPRESSION_ID = "em_expression_id";
    
    public static final String MESSAGE_ATTR_AT_MSG = "em_at_list";
    public static final String MESSAGE_ATTR_VALUE_AT_MSG_ALL = "ALL";

    
    
	public static final int CHATTYPE_SINGLE = 1;
    public static final int CHATTYPE_GROUP = 2;
    public static final int CHATTYPE_CHATROOM = 3;

    public static final String USER_EPNAME = "epusername";
    public static final String USER_NAME = "username";
    public static final String HEAD_IMAGE_URL = "headImageUrl";//发送人的头像
    public static final String HEAD_EPIMAGE_URL = "EPheadImageUrl";//发送人的头像
    public static final String ADDRESS = "address";
    public static final String EP_POSITION = "epPosition";
    
    public static final String EXTRA_CHAT_TYPE = "chatType";
    public static final String EXTRA_USER_ID = "userId";
    //环信客服账号
    public static final String EXTRA_HX_YXKF = "88888888888";

    /**
     * BQMM集成
     * 添加BQMM消息相关常量
     */
    public static final String BQMM_MESSAGE_KEY_CONTENT = "msg_data";
    public static final String BQMM_MESSAGE_KEY_TYPE = "txt_msgType";
    public static final String BQMM_MESSAGE_TYPE_STICKER = "facetype";
    public static final String BQMM_MESSAGE_TYPE_MIXED = "emojitype";

    public static String CARD_TIP_TYPE = "cardTipType";
    public static String CARD_TIP = "cardTip";
    public static String CARD_TYPE = "cardType";
    public static String CARD_USER_NAME = "cardUserName";
    public static String CARD_POSITION = "card_positon";
    public static String CARD_USER_PHONE = "cardUserPhone";
    public static String CARD_EP_NAME = "cardEpNm";
    public static String CARD_ADDRESS = "cardAddress";
    public static String CARD_LOGO = "EPlogoUrl";
}

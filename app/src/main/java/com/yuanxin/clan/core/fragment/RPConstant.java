package com.yuanxin.clan.core.fragment;

public class RPConstant {
    public static final String EXTRA_CHAT_TYPE = "chat_type";
    public static final String EXTRA_GROUP_MEMBERS = "group_members";
    public static final String EXTRA_GROUP_USER = "group_member";
    public static final String EXTRA_GROUP_ID = "group_id";
    public static final String EXTRA_FROM_USER_NAME = "from_user_id";
    public static final String EXTRA_TRANSFER_RECEIVER_ID = "money_transfer_receiver_id";
    public static final String EXTRA_TRANSFER_AMOUNT = "money_transfer_amount";
    public static final String MESSAGE_ATTR_IS_TRANSFER_PACKET_MESSAGE = "money_is_transfer_message";
    public static final String EXTRA_TRANSFER_PACKET_TIME = "money_transfer_time";
    public static final String EXTRA_SPONSOR_NAME = "money_sponsor_name";
    public static final String EXTRA_RED_PACKET_GREETING = "money_greeting";
    public static final String EXTRA_RED_PACKET_SENDER_ID = "money_sender_id";
    public static final String EXTRA_RED_PACKET_RECEIVER_ID = "money_receiver_id";
    public static final String MESSAGE_ATTR_IS_RED_PACKET_MESSAGE = "is_money_msg";
    public static final String MESSAGE_ATTR_IS_RED_PACKET_ACK_MESSAGE = "is_open_money_msg";
    public static final String MESSAGE_ATTR_RED_PACKET_TYPE = "money_type_special";
    public static final String MESSAGE_ATTR_SPECIAL_RECEIVER_ID = "special_money_receiver_id";
    public static final String EXTRA_RED_PACKET_SENDER_NAME = "money_sender";
    public static final String EXTRA_RED_PACKET_RECEIVER_NAME = "money_receiver";
    public static final String EXTRA_RED_PACKET_ID = "ID";
    public static final String REFRESH_GROUP_RED_PACKET_ACTION = "refresh_group_money_action";
    public static final String EXTRA_RED_PACKET_GROUP_ID = "money_from_group_id";
    public static final String EXTRA_RED_PACKET_TYPE = "red_packet_type";
    public static final String EXTRA_RED_PACKET_INFO = "red_packet_info";
    public static final String EXTRA_MESSAGE_DIRECT = "message_direct";
    public static final String HEADER_KEY_AUTH_TOKEN = "x-auth-token";
    public static final String HEADER_KEY_DEVICE_ID = "device-id";
    public static final String HEADER_KEY_VERSION_CODE = "version";
    public static final String HEADER_KEY_REQUEST_ID = "request-id";
    public static final String MESSAGE_DIRECT_SEND = "SEND";
    public static final String MESSAGE_DIRECT_RECEIVE = "RECEIVE";
    public static final int CHATTYPE_SINGLE = 1;
    public static final int CHATTYPE_GROUP = 2;
    public static final String REQUEST_CODE_SUCCESS = "0000";
    public static final String EXTRA_WEBVIEW_FROM = "webview_from";
    public static final int FROM_QA = 1003;
    public static final int FROM_CHANGE_DETAIL = 1006;
    public static final int FROM_USER_AGREEMENT = 1008;
    public static final int FROM_AMOUNT_AGREEMENT = 1009;
    public static final int EVENT_REFRESH_DATA = 20;
    public static final int EVENT_LOAD_MORE_DATA = 30;
    public static final int FROM_FLAG_TRANSFER = 101;
    public static final int FROM_FLAG_RECHARGE = 102;
    public static final String DEVICE_CHANGE_ERROR_CODE = "101";
    public static final String DEVICE_CHANGE_NO_CARD_ERROR_CODE = "1025";
    public static final String REMOVE_BANKCARD_ERROR_CODE = "100";
    public static final String GROUP_RED_PACKET_TYPE_RANDOM = "rand";
    public static final String GROUP_RED_PACKET_TYPE_AVERAGE = "avg";
    public static final String GROUP_RED_PACKET_TYPE_EXCLUSIVE = "member";
    public static final String GROUP_RED_PACKET_TYPE_PRI = "randpri";
    public static final String GROUP_RED_PACKET_TYPE = "group_red_packet_type";
    public static final String AD_RED_PACKET_TYPE = "advertisement";
    public static final String RED_PACKET_TYPE_RANDOM = "const";
    public static final String RED_PACKET_TYPE_TRANSFER = "transfer";
    public static final int PAY_TYPE_CHANGE = 0;
    public static final int PAY_TYPE_JD = 1;
    public static final int PAY_TYPE_ADD = 2;
    public static final int PAY_TYPE_ALI = 3;
    public static final int PAY_TYPE_WX = 4;
    public static final String EM_META_KEY = "EASEMOB_APPKEY";
    public static final int EVENT_LOAD_BANK_BIN = 266;
    public static final int EVENT_LOAD_BANK_PROVINCE = 276;
    public static final int EVENT_LOAD_BANK_CITY = 286;
    public static final int EVENT_LOAD_BANK_BRANCH = 296;
    public static final String BIND_FROM_TAG = "bind_from_tag";
    public static final int FROM_ADD_CARD_FOR_PAY_BIND = 1;
    public static final int FROM_WITHDRAW_BIND_NO_CARD = 2;
    public static final int FROM_SET_PASSWORD_BIND = 3;
    public static final int FROM_FORGET_PASSWORD_BIND = 4;
    public static final int FROM_BANKCARD_LIST_BIND = 5;
    public static final int FROM_SWITCH_DEVICE_BIND = 6;
    public static final int FROM_JD_LIMITED_BIND = 7;
    public static final int FROM_WITHDRAW_BIND_NO_PWD = 8;
    public static final int FROM_WITHDRAW_BIND_ALL_NO = 9;
    public static final String EXTRA_BANK_INFO = "extra_bank_info";
    public static final String EXTRA_BANK_LIST_INFO = "extra_bank_list_info";
    public static final String EXTRA_CHARGE_AMOUNT = "extra_charge_amount";
    public static final int RECORD_TAG_SEND = 0;
    public static final int RECORD_TAG_RECEIVED = 1;
    public static final int PAY_STATUS_JD_LIMITED = 0;
    public static final int PAY_STATUS_CHANGE_LIMITED = 1;
    public static final int PAY_STATUS_SINGLE_LIMITED = 2;
    public static final int PAY_STATUS_SINGLE_OUT = 3;
    public static final int PAY_STATUS_SMS_ERROR = 4;
    public static final int PAY_STATUS_PWD_ERROR = 5;
    public static final int PAY_STATUS_PWD_ERROR_LIMIT = 6;
    public static final int PAY_STATUS_OTHER_ERROR = 7;
    public static final int PAY_STATUS_DEVICE_CHANGE_NO_CARD_ERROR = 12;
    public static final int PAY_SUCCESS_FOR_SEND_ERROR = 9;
    public static final int SEND_PACKET_STATUS_ID_NONE_ERROR = 8;
    public static final int SEND_PACKET_STATUS_DISMISS_ERROR = 11;
    public static final int SEND_PACKET_STATUS_TIMEOUT_ERROR = 10;
    public static final String PWD_ERROR_CODE = "3003";
    public static final String PWD_ERROR_LIMIT_CODE = "3005";
    public static final String TIMEOUT_ERROR_CODE = "-100";
    public static final String SMS_ERROR_CODE = "1005";
    public static final String JD_PAY_SINGLE_LIMIT_CODE = "10001";
    public static final String EXTRA_FROM_TYPE = "extra_from_type";
    public static final String FROM_SEND_PACKET = "from_send_packet";
    public static final String ID_ERROR_CODE_NONE = "1021";
    public static final String ID_ERROR_CODE_PROCESS = "1023";
    public static final String EXTRA_TOKEN_DATA = "token_data";
    public static final String AUTH_METHOD_EASEMOB = "AUTH_METHOD_EASEMOB";
    public static final String AUTH_METHOD_SIGN = "AUTH_METHOD_SIGN";
    public static final String AUTH_METHOD_YTX = "AUTH_METHOD_YTX";
    public static final String PAY_MODEL_ALI = "AliPay";
    public static final String PAY_MODEL_JD = "JdPay";
    public static final String PAY_MODEL_WX = "WxPay";
    public static final String TOKEN_PARAM_ERROR_CODE = "-999";
    public static final String STATISTICS_TYPE_AD_OPEN = "rp.hb.ad.open_hb";
    public static final String STATISTICS_TYPE_VIEW_AD = "rp.hb.ad.view_ad";
    public static final String STATISTICS_TYPE_CLICK_AD = "rp.hb.ad.click_ad";
    public static final int RP_ITEM_TYPE_SINGLE = 1;
    public static final int RP_ITEM_TYPE_GROUP = 2;
    public static final int RP_ITEM_TYPE_RANDOM = 3;
    public static final int RP_ITEM_TYPE_TRANSFER = 4;
    public static final String RP_PACKET_DIALOG_TAG = "RP_PACKET_DIALOG_TAG";
    public static final String RP_AD_PACKET_OUT = "3013";

    public RPConstant() {
    }
}

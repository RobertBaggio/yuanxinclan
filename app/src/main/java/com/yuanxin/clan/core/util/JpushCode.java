package com.yuanxin.clan.core.util;

public class JpushCode {
	
	public static final String EXTRA_KEY = "param";
	public static final String CODE_KEY = "code";
	public static final String FRIEND_NAME_KEY = "friendName";

	//单点登陆
	public static final int LOG_OUT = 1;
	//聊天
	public static final int DELETE_FRIEND = 100;//删除好友
	public static final String DELETE_FRIEND_STRING = "已经把你解除好友关系";
	
	public static final int ADD_FRIEND = 102;//添加好友
	public static final String ADD_FRIEND_STRING = "申请添加为好友";
	
	public static final int EP_DELETE_FRIEND = 103;//群删除群成员
	public static final String EP_DELETE_FRIEND_STRING = "群已经把您踢出该群";
	
	
	
	//订单
	public static final int ORDER_REFUNDING = 200;//申请退款
	public static final String ORDER_REFUNDING_STRING = "您有一笔订单正在申请退款";
	
	public static final int ORDER_SEND_GODDS = 201;//发货
	public static final String ORDER_SEND_GODDS_STRING = "您购买的商品已发货";
	
	public static final int ORDER_SUCCESS = 202;//收货成功
	public static final String ORDER_SUCCESS_STRING = "您有一笔订单已完成";
	
	public static final int ORDER_PENDING_GOODS = 203;//待发货
	public static final String ORDER_PENDING_GOODS_STRING = "您有一笔订单正在等待发货";
	
	public static final int ORDER_REFUND = 204;//同意退款
	public static final String ORDER_REFUND_STRING = "您有一笔订单已同意退款，等待资金原路返回";
	
	public static final int ORDER_REFUNDED = 205;//退款返回
	public static final String ORDER_REFUNDED_STRING = "您有一笔订单已退款完成";

	//拜访预约
	public static final int VISIT_ADD = 300;//创建新预约
	public static final String VISIT_ADD_STRING = "您有一个新的预约，请确认";

	public static final int VISIT_AGREE = 301;//同意预约
	public static final String VISIT_AGREE_STRING = "您的预约已被同意，请查看";

	public static final int VISIT_CHANGE_TIME = 302;//变更时间
	public static final String VISIT_CHANGE_TIME_STRING = "您的预约已变更时间，请确认";

	public static final int VISIT_REFUSE = 303;//拒绝预约
	public static final String VISIT_REFUSE_STRING = "您的预约已被拒绝";

	public static final int VISIT_CHANGE_TIME_AGREE = 304;//同意变更时间
	public static final String VISIT_CHANGE_TIME_AGREE_STRING = "您的预约变更时间已同意，请查看";

	public static final int VISIT_CHANGE_TIME_REFUSE = 305;//不同意变更时间
	public static final String VISIT_CHANGE_TIME_REFUSE_STRING = "您的预约变更时间已被拒绝";

	//商圈消息
	public static final int BUSINESS_MSG = 400;
	public static final String BUSINESS_MSG_STRING = "商圈收到一条消息";
	
}

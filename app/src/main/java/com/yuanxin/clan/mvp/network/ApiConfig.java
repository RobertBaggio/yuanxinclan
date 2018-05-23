package com.yuanxin.clan.mvp.network;

/**
 * @author lch
 *         date 2016/7/5.
 */
public class ApiConfig {
    //http://172.16.0.203:8080
//    public static final String HOST = "http://172.16.0.203:8080/";//开发环境
    public static final String HOST = "http://172.16.0.23/";//开发环境
    //        public static final String HOST = "http://xml.easyder.com/";//外网测试环境
    //    public static final String HOST = "http://172.16.0.63:8080/";//开发环境
//        public static final String HOST = "http://120.77.218.159:8086/";//正式环境
//        public static final String HOST = "http://172.16.0.69:8080/";//开发环境
    public static final String secondaryURL = "xinmilan-web/";//开发环境

    public static final String API_QUERY_MEMBER_INFO = "API_QUERY_MEMBER_INFO";
    public static final String API_QUERY_MEMBER_LIST = "API_QUERY_MEMBER_LIST";
    public static final String API_CREATE_MEMBER_INFO = "API_CREATE_MEMBER_INFO";
    public static final String API_UPDATE_MEMBER_INFO = "API_UPDATE_MEMBER_INFO";
    public static final String API_DELETE_MEMBER_INFO = "API_DELETE_MEMBER_INFO";

    public static final String API_RECHARGE_RETURN_URL = "http://web.sv.easyder.com/user";


    /*bj==start*/
    /**
     * 获取到点途径
     */
    public static final String get_wap = secondaryURL + "api/getsources.ed";
    /**
     * 添加会员
     */
    public static final String add_member = secondaryURL + "api/addcustomer.ed";
    /**
     * 会员详情
     */
    public static final String member_detail = secondaryURL + "api/customerdetail.ed";
    /**
     * 卡详情
     */
    public static final String card_detail = secondaryURL + "api/customercarddetail.ed";
    /**
     * 购买会员卡
     */
    public static final String shop_card = secondaryURL + "api/purchasecard.ed";
    /**
     * 续卡
     */
    public static final String renrw = secondaryURL + "api/addcontinuecardorder.ed";
    /**
     * 续卡单号
     */
    public static final String renrw_orderno = secondaryURL + "api/createcontinueno.ed";
    /**
     * 购买会员卡页面数据
     */
    public static final String shop_card_info = secondaryURL + "api/getnewcardinfo.ed";
    /**
     * 上传头像
     */
    public static final String upload_head = secondaryURL + "api/uploadheadpic.ed";
    /**
     * 会员充值
     */
    public static final String member_recharge = secondaryURL + "api/recharge.ed";
    /**
     * 会员消费记录
     */
    public static final String member_expense = secondaryURL + "api/getaccountdetailed.ed";
    /**
     * 查询该用户是否设置了支付密码
     */
    public static final String password_info = secondaryURL + "api/issettingpaypassword.ed";
    /**
     * 发送短信验证码
     */
    public static final String get_smscode = secondaryURL + "api/sendverifycode.ed";
    /**
     * 验证验证码
     */
    public static final String check_smscode = secondaryURL + "api/verify.ed";
    /**
     * 修改支付密码
     */
    public static final String update_pay_password = secondaryURL + "api/setpaypassword.ed";
    /*bj==end*/
    /**
     * 获取项目分类
     */
    public static final String get_project_group = secondaryURL + "api/getprojectgroup.ed";
    /**
     * 获取项目
     */
    public static final String get_project = secondaryURL + "api/getserviceitem.ed";
    /**
     * 获取产品分类
     */
    public static final String get_product_group = secondaryURL + "api/getproductgroup.ed";
    /**
     * 获取产品
     */
    public static final String get_product = secondaryURL + "api/getproduct.ed";
    /**
     * 会员列表
     */
    public static final String customer_list = secondaryURL + "api/customers.ed";
    /**
     * 会员查询
     */
    public static final String customer_query = secondaryURL + "api/customersearch.ed";
    /**
     * 会员详情
     */
    public static final String customer_detail = secondaryURL + "api/customerdetail.ed";
    /**
     * 销售单据
     */
    public static final String saleorder_list = secondaryURL + "api/saleorderlist.ed";
    /**
     * 销售单据详情
     */
    public static final String saleorder_detail = secondaryURL + "api/saleorderdetail.ed";
    /**
     * 登录
     */
    public static final String login = secondaryURL + "api/login.ed";
    /**
     * 退出登录
     */
    public static final String outLogin = secondaryURL + "api/logout.ed";
    /**
     * 获取同一部门的员工
     */
    public static final String employee_list = secondaryURL + "api/getemployeebysid.ed";
    /**
     * 订单价格计算
     */
    public static final String request_order = secondaryURL + "api/calcsaleorderprice.ed";
    /**
     * PAD收银创建订单
     */
    public static final String generate_order = secondaryURL + "api/createsaleorderfrompad.ed";
    /**
     * 获取可用的会员卡
     */
    public static final String getMemberPayCard = secondaryURL + "api/getavailablecustomercard.ed";
    /**
     * 生成订单号
     */
    public static final String generate_no = secondaryURL + "api/createsaleorderno.ed";
    /**
     * 订单支付
     */
    public static final String pay_order = secondaryURL + "api/saleorderpayment.ed";
    /**
     * 小票
     */
    public static final String print_data = secondaryURL + "api/getSmallticket.ed";
    /**
     * 取消单据
     */
    public static final String cancel_order = secondaryURL + "api/cancelsaleorder.ed";
    /**
     * 申请退货单
     */
    public static final String return_product = secondaryURL + "api/addsalerefundorder.ed";
    /**
     * 销售单上传签名
     */
    public static final String upload_sign = secondaryURL + "api/uploadsign.ed";

}

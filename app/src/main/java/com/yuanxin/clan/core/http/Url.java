package com.yuanxin.clan.core.http;

/**
 * Created by lenovo1 on 2017/4/10.
 * IP地址管理
 */

public class Url {
    public static final String urlHost = "http://www.yxtribe.com/yuanxinbuluo";//云服务器
//    public static final String urlHost = "http://120.78.57.43/yuanxinbuluo";//云服务器
//    public static final String urlHost = "http://192.168.1.200:8080/yuanxinbuluo";//yan
//
//    public static final String urlHost = "http://192.168.1.101:8080/yuanxinbuluo";//虎http://10.198.4.190
//    public static final String urlHost = "http://192.168.1.106:8080/yuanxinbuluo";//小谢http://10.198.4.190  222
//    public static final String urlHost = "http://192.168.1.102/yuanxinbuluo";//小谢http://10.198.4.190  222guanglei
//    public static final String urlHost = "http://192.168.1.107:8080/yuanxinbuluo";//阿强
//    public static final String urlHost = "http://10.198.4.23/yuanxinbuluo";//acheng

    public static final String urlWeb = urlHost + "/weixin/getJsp?url=wechatweb";
    public static final String urlWebCompany = urlHost + "/weixin/getJsp?url=wechatweb/epdetail&param=";//企业
    public static final String urlWebNews = urlHost + "/weixin/getJsp?url=wechatweb/news-detail&param=";//新闻
    public static final String urlWebBusiness = urlHost + "/weixin/getJsp?url=wechatweb/business&param=";//商圈
    public static final String urlWebPresentMade = urlHost + "/weixin/getJsp?url=wechatweb/gift-info&param=";//商圈
    public static final String urlWebGift = urlHost + "/weixin/getJsp?url=wechatweb/gift-content&param=";//新闻
    public static final String urlWebCommodity = urlHost +  "/weixin/getJsp?url=wechatweb/commodity-content&commodityId=";//新闻
    //服务商城  yxtribe  tonixtech
    public static final String urlWebShopStore = urlHost + "/weixin/getJsp?url=wechatweb/serviceMall";

    public static final String urlByUserId = urlHost + "/order/getByUserId";
    public static final String urlMyBuyOrder = urlHost + "/order/getByUserId";//获取用户的订单列表
    public static final String urlMyMarketOrder = urlHost + "/commodity/myCommodity";//在售商品 集市商品
    public static final String login = urlHost + "/user/login";//登录
    public static final String supplyDemand = urlHost + "/supplyDemand/add";
    public static final String isSign = urlHost + "/business/draw/isSign";
    public static final String supplyDemandupdate = urlHost + "/supplyDemand/update";
    public static final String getbusinesspeople = urlHost + "/baUser/user";//登录
    public static final String register = urlHost + "/user/register";//登录
    public static final String memberRegister = urlHost + "/user/memberRegister";//完善资料
    public static final String updateMemberRegister = urlHost + "/user/updateMemberRegister";//完善资料
    public static final String getUserEpInfo = urlHost + "/user/getUserEpInfo";//完善资料
    public static final String delBaUser = urlHost + "/baUser/delBaUser";//删除商圈成员
    public static final String addBaUser = urlHost + "/baUser/addBaUser";//添加商圈成员
    public static final String  updload= urlHost + "/androidVersionUpload/checkVersion";//更新
    public static final String myBargain = urlHost + "/contract/getContractByUser";//我的合同
    public static final String myCrowdFunding = urlHost + "/crowdfund/search";//圆心众筹列表
    public static final String myCrowdFundingDetail = urlHost + "/crowdfund/getById";//圆心众筹详情
    public static final String myNewsList = urlHost + "/news/list";//我的资讯列表
    public static final String immediateBuyOrder = urlHost + "/order/buy";//直接购买
    public static final String updateAddress = urlHost + "/user/updateAddress";//编辑收货地址
    public static final String orderupdateAddress = urlHost + "/order/updateAddress";//编辑收货地址
    public static final String addAddress = urlHost + "/user/addAddress";//增加收货地址
    public static final String getMyAddress = urlHost + "/user/myAddress";//获取我的收货地址
    public static final String getMyDeftaultAddress = urlHost + "/user/myDeftaultAddress";//我的默认地址
    public static final String deleteMyAddress = urlHost + "/user/delAddress";//删除我的收货地址
    public static final String getGiftList = urlHost + "/gift/page";//礼品定制列表
    public static final String delByCa = urlHost + "/car/delByCar";//删除购物车
    public static final String getCartDetail = urlHost + "/car/getByUserId";//购物车详情
    ///user/getUserEpInfo
    public static final String getCompanyDetail = urlHost + "/user/getUserEpInfo";//企业详情
    public static final String getepUsers = urlHost + "/ep/epUsers";//企业详情
    //http://192.168.1.111:8080/yuanxinbuluo/user/getUserBusiness
    public static final String getUserBusiness = urlHost + "/user/getUserBusiness";//企业详情
    //http://192.168.1.111:8080/yuanxinbuluo/news/list
    public static final String getNewsList = urlHost + "/news/list";//企业详情
    public static final String getadvertisement = urlHost + "/advertisement/getShowAdvertisements";
    public static final String getsupplyDemand = urlHost + "/supplyDemand/homePage";
    public static final String getserviceType = urlHost + "/serviceType/list";
    public static final String getyxServiceMall = urlHost + "/yxServiceMall/getSubYxServiceMallInfo";
    public static final String getsearch = urlHost + "/advertisement/search";
    public static final String getbusinesssearch = urlHost + "/business/search";
    public static final String getBusinessWhole = urlHost + "/business/searchPage";
    public static final String getsearchdelete = urlHost + "/search/web/delete";
    public static final String getsearchtype = urlHost + "/search/history/hot";
    //http://192.168.1.111:8080/yuanxinbuluo/car/getByUserId
    public static final String getCarList = urlHost + "/car/getByUserId";//企业详情
    //http://192.168.1.111:8080/yuanxinbuluo/car/getByUserId
    public static final String getGetCartDetailList = urlHost + "/car/getByUserId";//企业详情
    public static final String setBusinessSee = urlHost + "/user/setBusinessSee";//企业详情
    public static final String updateById = urlHost + "/ep/updateById";
    public static final String getById = urlHost + "/ep/getById";
    //http://192.168.1.111:8080/yuanxinbuluo/market/getById
    public static final String getMarketDetailList = urlHost + "/market/getById";//企业详情
    //http://192.168.1.111:8080/yuanxinbuluo/commodity/getCommodityById
    public static final String getCommodityDetailList = urlHost + "/commodity/getCommodityById";//企业详情
    //http://192.168.1.111:8080/yuanxinbuluo/crowdfund/myCrowdfund
    public static final String getMyCrowdfund = urlHost + "/crowdfund/myCrowdfund";//企业详情
    //http://192.168.1.111:8080/yuanxinbuluo/crowdfund/myCrowdfund
    public static final String getCrowdfund = urlHost + "/crowdfund/insert";//企业详情
    //http://192.168.1.111:8080/yuanxinbuluo/crowdfund/myCrowdfund
    public static final String getMCrowdfund = urlHost + "/crowdfund/myCrowdfund";//企业详情
    public static final String getMyNewsList = urlHost + "/news/list";//

    public static final String getRecommendNews = urlHost + "/news/recommendPage"; // 获取推荐资讯
    public static final String getMyGiftList = urlHost + "/gift/page";//礼品定制列表
    //gift/getCommodityById
    public static final String getCommodityDetailDetailList = urlHost + "/gift/getCommodityById";//礼品定制详情
    //gift/myCommodity
    public static final String getMyTwoGiftList = urlHost + "/gift/myCommodity";//在售礼品列表
    //gift/myCommodity//在售礼品列表 点击编辑
    public static final String getEditMyTwoGiftList = urlHost + "/gift/myCommodity";//在售礼品列表 编辑
    //enshrine/insert
    public static final String addCollecte = urlHost + "/enshrine/insert";//资讯中心 收藏
    //enshrine/delete//资讯中心 删除收藏
    public static final String DeleteCollecte = urlHost + "/enshrine/delete";//资讯中心 收藏
    public static final String DeleteCollecteone = urlHost + "/enshrine/insert";//资讯中心 收藏
    //epServerDetail/insert 圆心服务
    public static final String addCompanyInfo = urlHost + "/epServerDetail/insert";
    //epServer/search 圆心服务类型
    public static final String serviceType = urlHost + "/epServer/search";
    // 搜索服务
    public static final String searchService = urlHost + "/yxServiceMall/page";
    //gift/updateGift 在售礼品 编辑 提交
    public static final String updateGift = urlHost + "/gift/updateGift";
    //ep/search
    public static final String getCompanyInfo = urlHost + "/ep/search";
    public static final String getcommdss = urlHost + "/epMall/getByEpIdHotCommodity";
    public static final String getindustrylist = urlHost + "/industry/list";
    //http://192.168.1.111:8080/yuanxinbuluo/expert/getById 专家详情
    public static final String getExpertDetail = urlHost + "/expert/getById";
    ///expert/myExpertInfo 获取我的专家资料
    public static final String getMyThinkTank = urlHost + "/expert/myExpertInfo";
    public static final String selectByUserId = urlHost + "/ep/selectByUserId";
    ///exper/updateMyExpertInfo 提交专家资料
    ///exper/updateMyExpertInfo 我的专家认证资料修改
    public static final String editMyThinkTank = urlHost + "/expert/updateMyExpertInfo";
    ///enshrine/getEnshrine 我的收藏
    public static final String getMyCollect = urlHost + "/enshrine/getEnshrine";
    public static final String gotoliaotian = urlHost + "/baUser/getById";
    ///epStore/search 店铺列表
    public static final String getStoreList = urlHost + "/epStore/search";
    ///epNews/insert 我的企业 企业动态
    public static final String addEpNews = urlHost + "/epNews/insert";
    public static final String updateCompanyInfo = urlHost + "/ep/updateById";
    ///ep/addEpUser
    public static final String addCompanyMemberToWeb = urlHost + "/ep/addEpUser";
    ///ep/deleteEpUser
    public static final String deleteCompanyMemberToWeb = urlHost + "/ep/deleteEpUser";
    public static final String getqiandaocontent = urlHost + "/businessActivity/page";
    public static final String businessActivitySign = urlHost + "/businessActivitySign/dataUrl";
    ///order/getByUserId老的
    public static final String getMymarketOrder = urlHost + "/order/getByUserId";
    public static final String newgetMymarketOrder = urlHost + "/order/page";
    public static final String orderdata = urlHost + "/order/orderinquiry";
    public static final String qrshouhuo = urlHost + "/order/receipt";
    public static final String sqtuik = urlHost + "/order/refund";
    public static final String commitwuliu = urlHost + "/order/sendGoods";
    public static final String tobuy = urlHost + "/order/buy";
    public static final String updatePrice = urlHost + "/order/updateAmount";
    public static final String agreeteturnm = urlHost + "/order/businessRefund";
    ///user/registerCode
    public static final String getSmsCode = urlHost + "/user/registerCode";
    //加入购物车
    //http://192.168.1.111:8080/yuanxinbuluo/car/addToCar
    public static final String addToCart = urlHost + "/car/addToCar";
    //提交订单
    public static final String postOrderForm = urlHost + "/order/buy";
    //user/changePwdCode 忘记密码 验证码
    public static final String forgetSendCode = urlHost + "/user/changePwdCode";
    //user/changePwd 忘记密码 修改密码
    public static final String forgetCode = urlHost + "/user/changePwd";
    //user/changePhone 修改手机号
    public static final String changePhone = urlHost + "/user/changePhone";
    //user/changePhoneCode 修改手机号 验证码
    public static final String changePhoneCode = urlHost + "/user/changePhoneCode";
    ///business/add 创建商圈
    public static final String addBusinessDistrict = urlHost + "/business/add";
    //http://192.168.1.127:8080/yuanxinbuluo/car/delByCar 删除购物车
//    public static final String deleteCar = urlHost + "/car/delByCar";
    public static final String updataDeleteCar = urlHost + "/car/saveShopCar";
    //http://192.168.1.111:8080/yuanxinbuluo/epNews/search
    public static final String getCompanyMessage = urlHost + "/epNews/search";
    //我的订单 卖家订单
    ///order/getByUserId
    public static final String getSellerOrder = urlHost + "/order/getByUserId";
    public static final String addfriend = urlHost + "/easemob/addFriendSingle";//加好友
    ///order/settlement
    public static final String carBuy = urlHost + "/order/settlementbuy";//购物车结算
    ///business/myBusiness 我的商圈详情
    public static final String getBusinessDetail = urlHost + "/business/myBusiness";//购物车结算
    public static final String getgongxu = urlHost + "/supplyDemand/page";
    public static final String businessLicensePlate = urlHost + "/businessLicensePlate/web/page";
    public static final String getpersoncard = urlHost + "/epCard/personalCard";
    public static final String servicePage = urlHost + "/order/servicePage";
    public static final String orderByUuidAndShopId = urlHost + "/order/orderByUuidAndShopId";
    public static final String yxServiceProcedure = urlHost + "/yxServiceProcedure/procedureInfo";
    public static final String myappoint = urlHost + "/visit/page";//购物车结算
    public static final String myappointYUEDU = urlHost + "/visit/update";//购物车结算
    public static final String agreevisit = urlHost + "/visit/agreeVisit";//购物车结算
    public static final String refuseVisit = urlHost + "/visit/refuseVisit";//购物车结算
    public static final String refuseChangeTime = urlHost + "/visit/refuseChangeTime";//购物车结算
    public static final String changeTime = urlHost + "/visit/changeTime";//购物车结算
    public static final String visitadd = urlHost + "/visit/add";//购物车结算
    public static final String agreeChangeTime = urlHost + "/visit/agreeChangeTime";//购物车结算
    public static final String getmyactivity = urlHost + "/businessActivity/myParticipate";//购物车结算
    ///ep/addEpUser
    public static final String addCompanyMember = urlHost + "/ep/addEpUser";
    ///gift/add
    public static final String publishPresentMade = urlHost + "/gift/add";//购物车结算
    ///business/getBusiness
    public static final String getOneBusinessDetail = urlHost + "/business/getBusiness";//我创建的商圈详情
    ///business/update
    public static final String editOneBusinessDetail = urlHost + "/business/update";//我创建的商圈 修改
    ///epNews/deleteById
    public static final String deleteCompanyInfo = urlHost + "/epNews/deleteById";//删除企业动态
    ///exper/add
    public static final String addExpert = urlHost + "/expert/add";//我的专家认证
    ///business/search
    public static final String getBusinessList = urlHost + "/business/search";//首页 商圈列表
    public static final String newBusinessArea = urlHost + "/business/newBusinessArea";//首页 商圈列表
    public static final String activityGroup = urlHost + "/activityGroup/web/page";//首页 活动圈
    public static final String advertisement = urlHost + "/advertisement/web/page";//首页 活动圈
    public static final String newmarketbennar = urlHost + "/advertisement/marketAvertisement";
    public static final String getexIndustry = urlHost + "/exIndustry/list";
    public static final String businessSon = urlHost + "/business/search";
    public static final String businessInvestmentProject = urlHost + "/businessInvestmentProject/web/page";
    public static final String businessxmidlist= urlHost + "/businessInvestmentIndustry/list";
    public static final String getnearPage = urlHost + "/exhibition/nearPage";
    public static final String gethall = urlHost + "/hall/page";//首页 商圈列表
    public static final String newmarketlist = urlHost + "/exhibition/page";//首页 商圈列表

    public static final String getBusinessMember = urlHost + "/business/user";//商圈成员搜索
    //企业库 获取行业列表
    public static final String getIndustryList = urlHost + "/industry/list";
    //商圈 获取行业列表
    public static final String getBusinessIndustryList = urlHost + "/baUser/industryList";

    // 商圈准则显示
    public static final String getBusinessInfo = urlHost + "/business/getById";
    ///user/updateUser
    public static final String editMyInfo = urlHost + "/user/updateUser";//我的个人资料
    ///easemob/getMyFriend
    public static final String getChatList = urlHost + "/easemob/getMyFriend";//通讯录
    //新企业注册/ep/insert
    public static final String addNewCompany = urlHost + "/ep/insert";//通讯录
    ///easemob/getMyGroups
    public static final String getGroupsList = urlHost + "/easemob/getMyGroups";//群聊列表
    ///easemob/getMyFriend //通讯录
    public static final String getFriendsList = urlHost + "/easemob/getMyFriend";//群聊列表
    ///easemob/addFriendSingle
    public static final String addFriendSingle = urlHost + "/easemob/addFriendSingle";//群聊列表
    public static final String checkFriendSingle = urlHost + "/easemob/checkFriend";//群聊列表
    ///easemob/createChatgroups
    public static final String addGroups = urlHost + "/easemob/createChatgroups";//群聊列表
    ///easemob/getGroupsDetail
    public static final String getMyGroupsInfo = urlHost + "/easemob/getGroupsDetail";//群聊列表
    ///easemob/addGroupUsers
    public static final String addGroupUsers = urlHost + "/easemob/addGroupUsers";//群聊列表
    public static final String upGroupname = urlHost + "/easemob/updateChatgroups";//群聊改名
    public static final String delegroup = urlHost + "/easemob/deleteChatgroups";//群聊改名
    ///easemob/deleteFriendSingle
    public static final String deleteFriend = urlHost + "/easemob/deleteFriendSingle";//群聊列表
    public static final String addcard = urlHost + "/epCard/addEpCard";//群聊列表
    public static final String releaseServerPhone = urlHost + "/easemob/releaseServerPhone";
    public static final String getServerPhone = urlHost + "/easemob/getServerPhone";
    public static final String getquestion = urlHost + "/question/page";
    public static final String deleteFriendSingle = urlHost + "/easemob/deleteGroupUsers";//群聊列表
    ///news/newsTypes
    public static final String getNewsType = urlHost + "/news/newsTypes";//新闻类型
    ///news/addUserNewsType
    public static final String saveNewsType = urlHost + "/news/addUserNewsType";//新闻类型
    //查找用户新闻类型
    public static final String getNewsTypesByUser = urlHost + "/news/selectByUserId";
    ///news/release
    public static final String publishNews = urlHost + "/news/release";//发布新闻
    ///newsComment/add
    public static final String addNewsTalk = urlHost + "/newsComment/add";//新闻评论
    public static final String qiyemobang = urlHost + "/epView/search";//企业模板
    public static final String businessViewPage = urlHost + "/epView/web/businessViewPage";//企业模板
    //    /
    // crowdfund/updateById
    public static final String updateCrowdFund = urlHost + "/crowdfund/updateById";//众筹更新
    ///file/web/fileUpload
    public static final String fileUpload = urlHost + "/file/web/fileUpload";//合同上传
    //suggestion/add
    public static final String addAdvice = urlHost + "/suggestion/add";//提交建议
    // 查找好友
    public static final String findFriend = urlHost + "/user/getUserByPhone";//查找好友
    public static final String updateEpUser = urlHost + "/ep/updateEpUser";//查找好友
    public static final String getyuyuenum = urlHost + "/visit/readCount";//查找好友
    public static final String myaccounturl = urlHost + "/acount/selectByUserId";//w我的账户
    public static final String commitpassword = urlHost + "/acount/update";//w我的账户
    public static final String checkdetail = urlHost + "/bill/page";//账单明细
    public static final String tixianurl = urlHost + "/reflect/embodied";//账单明细


    //微信支付回调地址
    public static final String WECHAT_CALLBACK_URL = "";
    //获取微信签名的支付参数
    public static final String getWxSignPayInfo = urlHost + "/pay/weiXinPay";
    //获取支付宝签名的支付参数
    public static final String getAliSignPayInfo = urlHost + "/pay/alipay";
    //验证支付宝支付回调
    public static final String getAlipayNotice = urlHost + "/pay/alipayNotice";
    //验证微信支付回调
    public static final String getWXpayNotice=urlHost+"/pay/weixinNotice";
    public static final String getpayNotice=urlHost+"/pay/payNotice";//新

    //第三方登陆 微信
    public static final String bindWechat = urlHost + "/user/bindByWechat";
    public static final String getWechatLogin = urlHost + "/user/wechatLogin";

    //查询企业信息
    public static final String getEpInfo = urlHost + "/ep/getById";

    //商圈信息标记已读
    public static final String updateBusinessMsgRead = urlHost + "/businessMsg/updateRead";
    public static final String personCenter = urlHost + "/user/personCenter";

    //场地申请同意和拒绝
    public static final String businessPropertySiteApply = urlHost + "/businessPropertySiteApply/web/update";

    // 处理报修
    public static final String businessRepair = urlHost + "/businessPropertyRepairs/web/update";

    // 首页公告栏
    public static final String homePageAnnouncement = urlHost + "/homePageAnnouncement/list";

    // 首页活动圈推荐
    public static final String homePageSelectActivities = urlHost + "/activityGroup/homePage";

    //企业首页
    public static final String companyFirstPage = urlHost + "/ep/community";
    /**
     * 七牛图片
     */
    public static final String img_domain = "http://images.yxtribe.com/";
    public static final String imageStyle90x90 = "-style_webp_90x90";
    public static final String imageStyle104x104 = "-style_webp_104x104";
    public static final String imageStyle108x108 = "-style_webp_108x108";
    public static final String imageStyle230x150 = "-style_webp_230x150";
    public static final String imageStyle230x160 = "-style_webp_230x160";
    public static final String imageStyle640x640 = "-style_webp_640x640";
    public static final String imageStyle750x350 = "-style_webp_750x350";
    public static final String imageStyle750x450 = "-style_webp_750x450";
    public static final String imageStyle210x210 = "-style_webp_210x210";
    public static final String imageStyle750x300 = "-style_webp_750x300";
    public static final String imageStyle208x208 = "-style_webp_208x208";

}

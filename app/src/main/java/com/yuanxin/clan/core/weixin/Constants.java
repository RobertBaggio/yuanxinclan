package com.yuanxin.clan.core.weixin;

public class Constants {
    // APP_ID �滻Ϊ���Ӧ�ôӹٷ���վ���뵽�ĺϷ�appId
//    public static final String APP_ID = "wxd930ea5d5a258f4f";

    public static class ShowMsgActivity {
        public static final String STitle = "showmsg_title";
        public static final String SMessage = "showmsg_message";
        public static final String BAThumbData = "showmsg_thumb_data";
    }

    //最新。。。
    // appid
    // 请同时修改 androidmanifest.xml里面，.PayActivityd里的属性<data
    // android:scheme="wxb4ba3c02aa476ea1"/>为新设置的appid
//    public static final String APP_ID = "wxf660cc9ea105ccae";
    public static final String APP_ID = "wx0e2ee22e4f6cf2a6";

    // 商户号
//    public static final String MCH_ID = "1312941001";
    public static final String MCH_ID = "1482407092";

    // API密钥，在商户平台设置
//    public static final String API_KEY = "531e32533dd191175c27d48c23c77931";
    public static final String API_KEY = "559effd3fdf55ef349e94b6cc3be6fef";

    public static String Login_code = "";

    /**
     * 微信开放平台和商户约定的密钥
     *
     * 注意：不能hardcode在客户端，建议genSign这个过程由服务器端完成
     */
//    public static final String APP_SECRET = "531e32533dd191175c27d48c23c77931"; // wxf660cc9ea105ccae
    public static final String APP_SECRET = "a3058b4012f514601ea2b1ba6894a72e"; // wxf660cc9ea105ccae

    /**
     * 微信开放平台和商户约定的支付密钥
     *
     * 注意：不能hardcode在客户端，建议genSign这个过程由服务器端完成
     */
    public static final String APP_KEY = "0aab1d828dff5a1e30df18a0a7d1570d";
    /** 商家向财付通申请的商家id */
    public static final String PARTNER_ID = "1312941001";

}

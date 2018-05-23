package com.yuanxin.clan.mvp.utils;

import com.yuanxin.clan.core.app.UserNative;

/**
 * Created by Administrator on 2017/7/11 0011.
 */

public class CommonString {
    public static final String js2Android = "yxbl_app";
    public static final String appTag = "yxbl_app/android";
    public static final String epid = UserNative.getEpId();

    // 遍历所有的img节点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
    public static final String imgClick = "javascript:(function(){" +
            "var srcs = [];" +
            "$.each( $(\"img\"), function(i, n){ srcs.push($(n).attr(\"src\"));});" +
            "$(\"img\").click(function(){" +
                "openImg(srcs, $(this).attr(\"src\"));" +
            "});" +
            "})();" +
            "function openImg(srcs, src){" +
                "window.yxbl_app.openImage(JSON.stringify(srcs), src);" +
            "};";
    public static final String imgClickWithShowClass = "javascript:(function(){" +
            "var srcs = [];" +
            "$.each( $(\"img.show\"), function(i, n){ srcs.push($(n).attr(\"src\"));});" +
            "$(\"img.show\").click(function(){" +
            "openImg(srcs, $(this).attr(\"src\"));" +
            "});" +
            "})();" +
            "function openImg(srcs, src){" +
            "window.yxbl_app.openImage(JSON.stringify(srcs), src);" +
            "};";
    public static final String imgClickWithOutOnClick = "javascript:(function(){" +
            "var srcs = [];" +
            "$.each( $(\"img:not([onclick])\"), function(i, n){ srcs.push($(n).attr(\"src\"));});" +
            "$(\"img:not([onclick])\").click(function(){" +
            "openImg(srcs, $(this).attr(\"src\"));" +
            "});" +
            "})();" +
            "function openImg(srcs, src){" +
            "window.yxbl_app.openImage(JSON.stringify(srcs), src);" +
            "};";
    public static final String getCommodityDescription = "javascript:(function(){" +
            "var titleString = $(\"title\").text();" +
            "var logoString = $(\"img:first\").attr(\"src\");" +
            "var description = $(\"#commodityDetail\").text();" +
            "window.yxbl_app.getCommodityDescription(titleString, logoString, description);" +
            "})();";
}

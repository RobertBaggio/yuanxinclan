package com.yuanxin.clan.mvp.share;

import android.content.Context;
import android.text.TextUtils;

import com.mob.MobSDK;
import com.yuanxin.clan.core.event.ShareCardEvent;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

public class ShareUtil {

    //关闭sso授权
    // title标题，印象笔记、邮箱、信息、微信、人人网、QQ和QQ空间使用
    // titleUrl是标题的网络链接，仅在Linked-in,QQ和QQ空间使用
    // text是分享文本，所有平台都需要这个字段
    //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
    // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
    //oks.setImagePath("/sdcard/Test.jpg");//确保SDcard下面存在此张图片
    // url仅在微信（包括好友和朋友圈）中使用
    // comment是我对这条分享的评论，仅在人人网和QQ空间使用
    // site是分享此内容的网站名称，仅在QQ空间使用
    // siteUrl是分享此内容的网站地址，仅在QQ空间使用
    // 启动分享GUI

    public static void share(final ShareInfoVo shareInfo, Context context) {
        ToastUtil.showLong(UIUtils.getContext(), "正在分享中，请稍候");
        try {
//            <!-- 填写您从Mob开发者后台中得到的Appkey和AppSecret -->
//        <meta-data android:name="Mob-AppKey" android:value="1ec1fb0830e8f"/>
//        <meta-data android:name="Mob-AppSecret" android:value="3554e2c9da53a7bf6fd5ac5ef878349a"/>
            // 通过代码注册你的AppKey和AppSecret
            MobSDK.init(context, "1b4f2089393d6", "428b8c761d0cd45297cd569f45a1ed59");
//            MobSDK.init(context, "1ec1fb0830e8f", "3554e2c9da53a7bf6fd5ac5ef878349a");
//            MobSDK.init(context, "1fb7825fc70c8", "6c77fe58845d0783b477cfc2ad7a0cc2");
            Platform platform = ShareSDK.getPlatform(shareInfo.getPlatformName());
            Platform.ShareParams params = new Platform.ShareParams();
            params.setTitle(shareInfo.getTitle());
            params.setText(shareInfo.getContent());
            if (!TextUtils.isEmpty(shareInfo.getImgFilePath())) {
                params.setImagePath(shareInfo.getImgFilePath());
            }
            params.setUrl(shareInfo.getUrl());
            params.setTitleUrl(shareInfo.getUrl());
            params.setImageUrl(shareInfo.getImgUrl());
            if (0 != shareInfo.getShareType()) {
                params.setShareType(shareInfo.getShareType());
            }
            platform.setPlatformActionListener(new PlatformActionListener() {
                public void onError(Platform arg0, int arg1, Throwable arg2) {
                    //失败的回调，arg:平台对象，arg1:表示当前的动作，arg2:异常信息
                    ToastUtil.showLong(UIUtils.getContext(), "分享失败！");
                }

                public void onComplete(Platform arg0, int arg1, HashMap<String, Object> arg2) {
                    //分享成功的回调
                    if (shareInfo.getShareCardNum() > 0) {
                        EventBus.getDefault().post(new ShareCardEvent(shareInfo.getShareCardNum()));
                    }
                    ToastUtil.showLong(UIUtils.getContext(), "分享成功！");
                }

                public void onCancel(Platform arg0, int arg1) {
                    //取消分享的回调
                    ToastUtil.showLong(UIUtils.getContext(), "分享取消！");
                }
            });
            platform.share(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\android-sdk-windows-xiaojian\android-sdk-windows/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile


 # ProGuard configurations for Bugtags
  -keepattributes LineNumberTable,SourceFile
  -keep class com.bugtags.library.** {*;}
  -dontwarn com.bugtags.library.**
  -keep class io.bugtags.** {*;}
  -dontwarn io.bugtags.**
  -dontwarn org.apache.http.**
  -dontwarn android.net.http.AndroidHttpClient
 # End Bugtags

 # ProGuard configurations for HuanXin
  -keep class com.hyphenate.** {*;}
  -dontwarn  com.hyphenate.**
 # End HuanXin

     # ProGuard configurations for GaoDe
      #3D 地图 V5.0.0之前：
      -keep   class com.amap.api.maps.**{*;}
       -keep   class com.autonavi.amap.mapcore.*{*;}
        -keep   class com.amap.api.trace.**{*;}
        #3D 地图 V5.0.0之后：
        -keep   class com.amap.api.maps.**{*;}
        -keep   class com.autonavi.**{*;}
        -keep   class com.amap.api.trace.**{*;}
        #定位
        -keep class com.amap.api.location.**{*;}
        -keep class com.amap.api.fence.**{*;}
        -keep class com.autonavi.aps.amapapi.model.**{*;}
        #搜索
        -keep   class com.amap.api.services.**{*;}
        #2D地图
        -keep class com.amap.api.maps2d.**{*;}
        -keep class com.amap.api.mapcore2d.**{*;}
       #导航
        -keep class com.amap.api.navi.**{*;}
        -keep class com.autonavi.**{*;}
        # End GaoDe

     # ProGuard configurations for leakcanary
        -dontwarn com.squareup.haha.guava.**
        -dontwarn com.squareup.haha.perflib.**
        -dontwarn com.squareup.haha.trove.**
        -dontwarn com.squareup.leakcanary.**
        -keep class com.squareup.haha.** { *; }
        -keep class com.squareup.leakcanary.** { *; }
        # Marshmallow removed Notification.setLatestEventInfo()
        -dontwarn android.app.Notification
        # End leakcanary

    #BaseRecyclerViewAdapterHelper
        -keep class com.chad.library.adapter.** {
           *;
        }

    #    支付宝
    #sdk 通过 proguard 混淆代码时默认已经将 lib目录中的 jar 都已经添加到打包脚本中，所以不需要再次手动添加。

        -keep class com.alipay.android.app.IAlixPay{*;}
        -keep class com.alipay.android.app.IAlixPay$Stub{*;}
        -keep class com.alipay.android.app.IRemoteServiceCallback{*;}
        -keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
        -keep class com.alipay.sdk.app.PayTask{ public *;}
        -keep class com.alipay.sdk.app.AuthTask{ public *;}

    # 微信
    # shareSdk
    -keep class cn.sharesdk.**{*;}
        -keep class com.sina.**{*;}
        -keep class **.R$* {*;}
        -keep class **.R{*;}
        -keep class com.mob.**{*;}
        -dontwarn com.mob.**
        -dontwarn cn.sharesdk.**
        -dontwarn **.R$*
    # 友盟

    # glide
    -keep public class * implements com.bumptech.glide.module.GlideModule
    -keep public class * extends com.bumptech.glide.AppGlideModule
    -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
      **[] $VALUES;
      public *;
    }

    # for DexGuard only
    -keepresourcexmlelements manifest/application/meta-data@value=GlideModule

    # banner 的混淆代码
    -keep class com.youth.banner.** {
        *;
     }

    # agentweb
    # -keep class com.just.library.** {
    #     *;
    # }
    # -dontwarn com.just.library.**
    # -keepclassmembers class com.just.library.agentweb.AndroidInterface{ *; }
    -dontoptimize
    -dontpreverify

    -dontwarn cn.jpush.**
    -keep class cn.jpush.** { *; }
    -keep class * extends cn.jpush.android.helpers.JPushMessageReceiver { *; }

    -dontwarn cn.jiguang.**
    -keep class cn.jiguang.** { *; }

    -keepclassmembers class * {
       public <init> (org.json.JSONObject);
    }

    -keep public class com.yuanxin.clan.R$*{
    public static final int *;
    }

    -keepclassmembers enum * {
        public static **[] values();
        public static ** valueOf(java.lang.String);
    }
    -dontwarn com.melink.**
    -keep class com.melink.** {*;}

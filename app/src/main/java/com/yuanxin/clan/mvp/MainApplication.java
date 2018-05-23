package com.yuanxin.clan.mvp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;
import com.easemob.redpacketsdk.RPInitRedPacketCallback;
import com.easemob.redpacketsdk.RPValueCallback;
import com.easemob.redpacketsdk.RedPacket;
import com.easemob.redpacketsdk.bean.RedPacketInfo;
import com.easemob.redpacketsdk.bean.TokenData;
import com.easemob.redpacketsdk.constant.RPConstant;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.PersistentCookieStore;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.test.DBHelper;
import com.yuanxin.clan.core.test.ToastHelper;
import com.yuanxin.clan.core.test.VollyHelperNew;
import com.yuanxin.clan.core.util.CrashHandler;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.manager.CacheManager;
//import com.zxy.recovery.core.Recovery;

/**
 * @author lch
 *         date 2015/10/12.
 */
public class MainApplication extends MultiDexApplication {
    public static final String MAIN_PREFERENCE = "INFO_PREFERENCE";
    public SharedPreferences mainPreferences;
    //主线程handler
    private static Handler mMainThreadHandler = new Handler();
    //主线程
    private static Thread mMainThread = Thread.currentThread();
    //主线程Id
    private static int mMainThreadId = android.os.Process.myTid();
    //context
    private static MainApplication instance;
    private static Context mTotalContext;
    /**
     * 屏幕宽度
     */
    private static int nScreenWidth;
    /**
     * 屏幕高度
     */
    private static int nScreenHeight;


    //支付相关密匙信息
//    public static PaymentKey paymentKey = new PaymentKey();

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
        instance = this;
        //bugtags在这里初始化调试可用true
        BugtagsOptions options = new BugtagsOptions.Builder().trackingConsoleLog(true).trackingCrashLog(true).crashWithScreenshot(true).build();
        Bugtags.start("4d357f8f465f830d1c5fcd8101284378", this, false ? Bugtags.BTGInvocationEventBubble : Bugtags.BTGInvocationEventNone,options);
        mTotalContext = this.getApplicationContext();

        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());

//        RedPacket.getInstance().initContext(mTotalContext);
        //打开Log开关 正式发布时请关闭
        RedPacket.getInstance().setDebugMode(true);
        RedPacket.getInstance().initRedPacket(mTotalContext, RPConstant.AUTH_METHOD_EASEMOB, new RPInitRedPacketCallback() {

            @Override
            public void initTokenData(RPValueCallback<TokenData> callback) {
                TokenData tokenData = new TokenData();
                tokenData.imUserId = EMClient.getInstance().getCurrentUser();
                //此处使用环信id代替了appUserId 开发者可传入App的appUserId
                tokenData.appUserId = EMClient.getInstance().getCurrentUser();
                tokenData.imToken = EMClient.getInstance().getAccessToken();
                //同步或异步获取TokenData 获取成功后回调onSuccess()方法
                callback.onSuccess(tokenData);
            }

            @Override
            public RedPacketInfo initCurrentUserSync() {
                //这里需要同步设置当前用户id、昵称和头像url
                String fromAvatarUrl = "";
                String fromNickname = EMClient.getInstance().getCurrentUser();
                EaseUser easeUser = EaseUserUtils.getUserInfo(fromNickname);
                if (easeUser != null) {
                    fromAvatarUrl = TextUtils.isEmpty(easeUser.getAvatar()) ? "none" : easeUser.getAvatar();
                    fromNickname = TextUtils.isEmpty(easeUser.getNick()) ? easeUser.getUsername() : easeUser.getNick();
                }
                RedPacketInfo redPacketInfo = new RedPacketInfo();
                redPacketInfo.fromUserId = EMClient.getInstance().getCurrentUser();
                redPacketInfo.fromAvatarUrl = fromAvatarUrl;
                redPacketInfo.fromNickName = fromNickname;
                return redPacketInfo;
            }
        });


        CacheManager.openCache(getInstance(), "data");
        mainPreferences = getSharedPreferences(MAIN_PREFERENCE, MODE_PRIVATE);

        OkGo.init(this);
        try {
            OkGo.getInstance()
                    //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                    .debug("OkHttpUtils")
                    .setCookieStore(new PersistentCookieStore());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        Logger.addLogAdapter(new AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
//                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
//                .methodCount(1)         // (Optional) How many method line to show. Default 2
//                .tag("YXBL")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
//                .build()) {
//            @Override
//            public boolean isLoggable(int priority, String tag) {
//                return BuildConfig.DEBUG;
//            }
//        });
        //自己项目结束
        //init demo helper
        DemoHelper.getInstance().init(this);
        //red packet code : 初始化红包上下文，开启日志输出开关
//        RedPacket.getInstance().initContext(applicationContext);
//        RedPacket.getInstance().setDebugMode(true);
        //end of red packet code
        //购物车开始


        initVollyHelper();
        initToastHelper();
        initDBHelper();
        //LeakCanary 内存泄漏检查调试可用true
        if (false) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }
            LeakCanary.install(this);
        }
        //规避FileProvider文件权限问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
//        QbSdk.initX5Environment(getApplicationContext(), 1, cb);
//        QbSdk.initX5Environment(getApplicationContext(), cb);
        // 应用奔溃处理，可以跳转奔溃页面，可以静默执行重启
//        Recovery.getInstance()
//                .debug(true)
//                .recoverInBackground(false)
//                .recoverStack(true)
//                .mainPage(MainActivity.class)
//                .recoverEnabled(true)
//                .callback(new CrashCallbak())
//                .silent(false, Recovery.SilentMode.RECOVER_ACTIVITY_STACK)
//                .skip(TestActivity.class)
//                .init(this);
        // 初始化数据库存储 Hawk
//        Hawk.init(this).build();
//        ImageView imageView = new ImageView(getApplicationContext());
//        imageView.setImageResource(R.drawable.airplane);
//        FloatWindow
//                .with(getApplicationContext())
//                .setView(imageView)
//                .setWidth(Screen.width,0.2f)
//                .setHeight(Screen.width,0.2f)
//                .setX(Screen.width,0f)
//                .setY(Screen.height,0.2f)
//                .setMoveType(MoveType.inactive)
////                .setMoveStyle(300,null)
//                .setFilter(true, MainActivity.class)
//                .build();
    }
    public static Context getTotalContext() {
        return mTotalContext;
    }

    /**
     * 获取app的版本号
     *
     * @return 返回当前的版本号
     */
    public static int getVersionCode() {
        try {
            return mTotalContext.getPackageManager().getPackageInfo(mTotalContext.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    /**
     * 获取app的版本名称
     *
     * @return 返回当前的版本名称
     */
    public static String getVersionName() {
        try {
            return mTotalContext.getPackageManager().getPackageInfo(mTotalContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    public static Thread getMainThread() {
        return mMainThread;
    }

    public static int getMainThreadId() {
        return mMainThreadId;
    }

    public static Handler getMainThreadHandler() {
        return mMainThreadHandler;
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public static SharedPreferences getMainPreferences() {
        return getInstance().mainPreferences;
    }

    private void initDBHelper() {
        DBHelper.init(getApplicationContext());
    }

    private void initVollyHelper() {
        VollyHelperNew.getInstance().initVollyHelper(getApplicationContext());
    }

    private void initToastHelper() {
        ToastHelper.getInstance().init(getApplicationContext());
    }

    /**
     * 获取屏幕宽高
     */
    public static void initScreen(Activity mActivity) {
        DisplayMetrics dm = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        MainApplication.setnScreenWidth(dm.widthPixels);
        MainApplication.setnScreenHeight(dm.heightPixels);
    }

    /**
     * 设置屏幕高度，在MainActivity,LoadingActivity调用
     */
    public static void setnScreenHeight(int nScreenHeight) {
        MainApplication.nScreenHeight = nScreenHeight;
        if (nScreenHeight != 0)
            MyShareUtil.sharedPint(R.string.SCREEN_HEIGHT, nScreenHeight);
    }

    public static int getnScreenHeight() {
        if (nScreenHeight == 0)
            nScreenHeight = MyShareUtil.getSharedInt(R.string.SCREEN_HEIGHT);
        return nScreenHeight;
    }

    /**
     * 设置屏幕宽度，在MainActivity,LoadingActivity调用
     */
    public static void setnScreenWidth(int nScreenWidth) {
        MainApplication.nScreenWidth = nScreenWidth;
        if (nScreenWidth != 0)
        MyShareUtil.sharedPint(R.string.SCREEN_WIDTH, nScreenHeight);
    }

    public static int getnScreenWidth() {
        if (nScreenWidth == 0)
            nScreenWidth = MyShareUtil.getSharedInt(R.string.SCREEN_WIDTH);
        return nScreenWidth;
    }

}

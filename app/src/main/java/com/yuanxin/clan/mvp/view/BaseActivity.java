package com.yuanxin.clan.mvp.view;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.LoginActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.mvp.library.SwipeBackActivity;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author lch
 *         date 2015/11/2.
 */
public abstract class BaseActivity extends SwipeBackActivity {
    //不加载标题栏
    public static final int NO_TITLE_VIEW = -1;
    //默认的标题栏
    public static final int DEFAULT_TITLE_VIEW = 0;
    protected RelativeLayout toolbar;
    protected TextView titleView;
    protected TextView menuView;
    protected ImageView rightIcon;
    protected FrameLayout containerView;
    private Unbinder unbinder;

    private static final String LAYOUT_LINEARLAYOUT = "LinearLayout";
    private static final String LAYOUT_FRAMELAYOUT = "FrameLayout";
    private static final String LAYOUT_RELATIVELAYOUT = "RelativeLayout";
    private static final String LAYOUT_RADIOGROUP = "RadioGroup";

    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        getWindow().setBackgroundDrawable(null);
        View view = View.inflate(this, R.layout.activity_base, null);
        setContentView(view);

        containerView = (FrameLayout) findViewById(R.id.content_main);
        int titleViewId = getTitleViewLayout();
        toolbar = (RelativeLayout) findViewById(R.id.toolbar);
        if (titleViewId == NO_TITLE_VIEW) {
            toolbar.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) containerView.getLayoutParams();
            layoutParams.topMargin = 0;
        } else {
            ButterKnife.findById(toolbar, R.id.nav_return_iv).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });

            titleView = (TextView) toolbar.findViewById(R.id.title);
            menuView = (TextView) toolbar.findViewById(R.id.menuView);
            if (titleViewId > 0) {
                titleView.setVisibility(View.GONE);
                menuView.setVisibility(View.GONE);
                ViewGroup titleLayout = (ViewGroup) View.inflate(this, getTitleViewLayout(), null);
                FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                titleLayout.setLayoutParams(layoutParams);
                toolbar.addView(titleLayout);
            }

            onTitleViewCreated(toolbar);
        }

        View contentView = View.inflate(this, getViewLayout(), null);
        addContentView(contentView);
        unbinder = ButterKnife.bind(this);
        initView(savedInstanceState, getIntent());
    }

    /**
     * @return 布局resourceId
     */
    public abstract int getViewLayout();

    /**
     * 从intent获取数据，初始化View。或者其他view级第三方控件的初始化,及相关点击事件的绑定
     *
     * @param savedInstanceState
     * @param intent
     */
    protected abstract void initView(Bundle savedInstanceState, Intent intent);


    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = null;
//        if (name.equals(LAYOUT_FRAMELAYOUT)) {
//            view = new AutoFrameLayout(context, attrs);
//        } else if (name.equals(LAYOUT_LINEARLAYOUT)) {
//            view = new AutoLinearLayout(context, attrs);
//        } else if (name.equals(LAYOUT_RELATIVELAYOUT)) {
//            view = new AutoRelativeLayout(context, attrs);
//        } else if (name.equals(LAYOUT_RADIOGROUP)) {
//            view = new AutoRadioGroup(context, attrs);
//        }

        if (view != null) {
            return view;
        }
        return super.onCreateView(name, context, attrs);
    }


    protected void addContentView(View view) {
        containerView.addView(view);
    }


    public void setTitle(String title) {
        if (getTitleViewLayout() != 0) {
            throw new IllegalArgumentException("getTitleViewLayout()被重写，无法使用默认标题View");
        }
        titleView.setText(title);
        titleView.setVisibility(View.VISIBLE);
    }

    public void setMenuText(String menuName) {
        if (getTitleViewLayout() != 0) {
            throw new IllegalArgumentException("getTitleViewLayout()被重写，无法使用默认菜单View");
        }
        menuView.setText(menuName);
        menuView.setVisibility(View.VISIBLE);
    }

    /**
     * 如果子类想要使用自定义的标题View，那么请重写此方法，然后重写onTitleViewCreated()
     * 在onTitleViewCreated中处理相关的背景设置和时间处理
     * 如果返回NO_TITLE_VIEW那么标题栏则不会加载出来
     *
     * @return 标题View的布局文件ID
     */
    protected int getTitleViewLayout() {
        return DEFAULT_TITLE_VIEW;
    }


    /**
     * 当标题View创建并被添加到ActionBar里面以后，会回调此方法，
     * 并把标题View作为参数
     *
     * @param titleView 创建成功后的标题View
     */
    public void onTitleViewCreated(ViewGroup titleView) {
        if (getTitleViewLayout() == NO_TITLE_VIEW) {
            throw new IllegalStateException("当前标题栏未创建，titleView为空！");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.cancelRequests(this, true);
        unbinder.unbind();
//        MainApplication.getRefWatcher().watch(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    protected void doHttpPost(String url, RequestParams params, final RequestCallback c) {
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {//测试1》2
//            ViewUtils.AlertDialog(this, "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//        } else {
            params.put("key", aesKes);
            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    c.onFailure(i, headers, s, throwable);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        c.onSuccess(i, headers, s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//        }
    }

    protected void doHttpGet(String url, RequestParams params, final RequestCallback c) {
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {
//            ViewUtils.AlertDialog(this, "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//        } else {
            params.put("key", aesKes);
            client.get(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    c.onFailure(i, headers, s, throwable);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        c.onSuccess(i, headers, s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//        }
    }

    protected void doHttpPostNotVerify(String url, RequestParams params, final RequestCallback c) {
        String aesKes = UserNative.getAesKes();
        params.put("key", aesKes);
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                c.onFailure(i, headers, s, throwable);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    c.onSuccess(i, headers, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    protected void doHttpGetNotVerify(String url, RequestParams params, final RequestCallback c) {
        String aesKes = UserNative.getAesKes();
        params.put("key", aesKes);
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                c.onFailure(i, headers, s, throwable);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    c.onSuccess(i, headers, s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public interface RequestCallback {
        void onSuccess(int i, Header[] headers, String s);
        void onFailure(int i, Header[] headers, String s, Throwable throwable);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setStatusBarColor(color);
        }
    }
    public void toLogin(){
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }
}

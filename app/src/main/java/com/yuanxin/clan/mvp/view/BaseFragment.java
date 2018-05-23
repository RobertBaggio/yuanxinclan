package com.yuanxin.clan.mvp.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bugtags.library.Bugtags;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.core.activity.LoginActivity;
import com.yuanxin.clan.core.app.UserNative;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/6/19 0019 12:52
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public abstract class BaseFragment extends Fragment {

    private Unbinder unbinder;
    private static int nOpeningSecondCount = 0;
    private AsyncHttpClient client = new AsyncHttpClient();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(getViewLayout(), container, false);
        unbinder = ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
//        QuicklyBackNotice.getInstance().addObserver(quicklyBackObserver);
        nOpeningSecondCount++;

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
//        QuicklyBackNotice.getInstance().removeObserver(quicklyBackObserver);
        nOpeningSecondCount--;
        if (nOpeningSecondCount < 0)
            nOpeningSecondCount = 0;
        super.onDetach();
    }

    /**
     * @return 布局resourceId
     */
    public abstract int getViewLayout();

    /**
     * 从intent获取数据，初始化View。或者其他view级第三方控件的初始化,及相关点击事件的绑定
     *
     * @param savedInstanceState
     */
    protected abstract void initView(Bundle savedInstanceState);

    @Override
    public void onDestroy() {
        super.onDestroy();
        client.cancelRequests(this.getContext(), true);
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }
    protected void setStatusBar(@ColorInt int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getActivity().getWindow().setStatusBarColor(color);
        }
    }

    protected void doHttpPost(String url, RequestParams params, final RequestCallback c) {
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {
//            ViewUtils.AlertDialog(getContext(), "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
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
//            ViewUtils.AlertDialog(getContext(), "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
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
    public void toLogin(){
        startActivity(new Intent(getContext(), LoginActivity.class));
    }
}

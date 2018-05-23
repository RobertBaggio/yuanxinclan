package com.yuanxin.clan.core.util;

import android.app.Dialog;
import android.content.Context;

import com.yuanxin.clan.R;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/18 0018 10:23
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class My_LoadingDialog_Singleton extends Dialog {
    private static Context mContext;
    private static class SingletonHolder {
        private static final My_LoadingDialog_Singleton INSTANCE = new My_LoadingDialog_Singleton(mContext);
    }
    private My_LoadingDialog_Singleton(Context context){
        super(context, R.style.loading_dialog);
        init();
    }
    public static final My_LoadingDialog_Singleton getInstance(Context context) {
        mContext = context;
        return SingletonHolder.INSTANCE;
    }
//    public My_LoadingDialog(Context context) {
//        super(context, R.style.loading_dialog);
//        init();
//    }

    private void init() {
        setContentView(R.layout.myloaddialog);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        if (!this.isShowing())
            super.show();
    }

    @Override
    public void dismiss() {
        if (this.isShowing())
            super.dismiss();
    }
}

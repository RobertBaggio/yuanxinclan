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

public class My_LoadingDialog extends Dialog {
    private static Context mContext;
    private static class SingletonHolder {
        private static final My_LoadingDialog INSTANCE = new My_LoadingDialog(mContext);
    }
    private My_LoadingDialog (Context context){
        super(context, R.style.loading_dialog);
        init();
    }
    public static final My_LoadingDialog getInstance(Context context) {
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
//        setCancelable(false);
    }

    @Override
    public void show() {
        if (!this.isShowing())
            try {
                super.show();
            }catch (Exception e) {
                e.printStackTrace();
            }
    }

    @Override
    public void dismiss() {
        if (this.isShowing())
            try {
                super.dismiss();
            }catch (Exception e) {
                e.printStackTrace();
            }
    }
}

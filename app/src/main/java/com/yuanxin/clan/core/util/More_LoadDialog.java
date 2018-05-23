package com.yuanxin.clan.core.util;

import android.app.Dialog;
import android.content.Context;

import com.yuanxin.clan.R;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/20 0020 10:01
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class More_LoadDialog extends Dialog {


    public More_LoadDialog(Context context) {
        super(context, R.style.loading_dialog);
        init();
    }

    private void init() {
        setContentView(R.layout.myloaddialog);
        setCanceledOnTouchOutside(false);
//        setCancelable(false);
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

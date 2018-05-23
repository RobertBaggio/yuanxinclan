package com.yuanxin.clan.core.company.view;

import android.content.Context;
import android.view.View;

/**
 * ProjectName: new_yuanxin
 * Describe:
 * Author: xjc
 * Date: 2017/8/24 0024 17:51
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public abstract class BaseDialog extends android.app.Dialog{

    private View rootView;

    public View getRootView() {
        return rootView;
    }

    public abstract View createView();

    public abstract void setOnListener();

    public BaseDialog(Context context) {
        super(context);
        this.setCanceledOnTouchOutside(true);
        rootView = createView();
        setOnListener();
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.setCanceledOnTouchOutside(true);
        rootView = createView();
        setOnListener();
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.setCanceledOnTouchOutside(true);
        rootView = createView();
        setOnListener();
    }
}

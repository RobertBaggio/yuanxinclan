package com.yuanxin.clan.core.util;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/22 0022 18:15
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */
public class ScrollListenerView extends ScrollView {
    private ScrollListener scrollViewListener = null;

    public ScrollListenerView(Context context) {
        super(context);
    }

    public ScrollListenerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollListenerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setScrollListener(ScrollListener scrollViewListener) {
        this.scrollViewListener = scrollViewListener;
    }

    @Override
    protected void onScrollChanged(int x, int y, int oldX, int oldY) {
        super.onScrollChanged(x, y, oldX, oldY);
        if (scrollViewListener != null) {
            scrollViewListener.onScrollChanged(this, x, y, oldX, oldY);
        }
    }
}

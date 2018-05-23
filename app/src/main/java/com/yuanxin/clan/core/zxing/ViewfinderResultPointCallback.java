package com.yuanxin.clan.core.zxing;

import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

public final class ViewfinderResultPointCallback implements ResultPointCallback {

    private final com.yuanxin.clan.mvp.view.ViewfinderView viewfinderView;

    public ViewfinderResultPointCallback(com.yuanxin.clan.mvp.view.ViewfinderView viewfinderView) {
        this.viewfinderView = viewfinderView;
    }

    public void foundPossibleResultPoint(ResultPoint point) {
        viewfinderView.addPossibleResultPoint(point);
    }

}

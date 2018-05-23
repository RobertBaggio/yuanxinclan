package com.yuanxin.clan.mvp.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * @author lch
 *         date 2016/3/17.
 */
public class AnimationUtils {
    public static void startAlphaShow(View view, long duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
    }

    /**
     * 显示alpha动画
     *
     * @param view
     */
    public static void startAlphaShow(View view) {
        startAlphaShow(view, 1000);
    }

    public static void startAlphaHide(View view, long duration) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(duration);
        view.startAnimation(alphaAnimation);
    }

    /**
     * 隐藏alpha动画
     *
     * @param view
     */
    public static void startAlphaHide(View view) {
        startAlphaHide(view, 1000);
    }

}

package com.yuanxin.clan.mvp.utils;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/7 0007 16:10
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class TimerUtil {
    private static final int nHandlerMsg = 23566124;

    private int nStartDelayTime;
    private int nIntervalTime;

    private Timer mTimer;
    private TimerTask mTimeTask;
    private Handler mHandler;
    private OnTimerChangeListener onTimerChangeListener;

    public TimerUtil(int nStartDelayTime, int nIntervalTime, OnTimerChangeListener onTimerChangeListener) {
        this.nStartDelayTime = nStartDelayTime;
        this.nIntervalTime = nIntervalTime;
        this.onTimerChangeListener = onTimerChangeListener;
    }

    /**
     * 计时器开始
     */
    public void timeStart() {
        timeStop();
        mHandler = new TimeHandler(this);
        mTimeTask = new TimerTask() {

            @Override
            public void run() {
                if (mHandler != null)
                    mHandler.obtainMessage(nHandlerMsg).sendToTarget();
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTimeTask, nStartDelayTime, nIntervalTime);
    }

    /**
     * 停止计时器
     */
    public void timeStop() {
        if (mTimer != null)
            mTimer.cancel();
        if (mTimeTask != null)
            mTimeTask.cancel();
        mTimeTask = null;
        mHandler = null;
        mTimer = null;
    }

    private void doTimeChange() {
        if (onTimerChangeListener != null)
            onTimerChangeListener.doChange();
    }

    private static class TimeHandler extends Handler {
        WeakReference<TimerUtil> weakReference;

        public TimeHandler(TimerUtil timerUtil) {
            weakReference = new WeakReference<>(timerUtil);
        }

        @Override
        public void handleMessage(Message msg) {
            TimerUtil timerUtil = weakReference.get();
            if (msg.what == nHandlerMsg)
                timerUtil.doTimeChange();

            super.handleMessage(msg);
        }
    }


    public interface OnTimerChangeListener {
        void doChange();
    }
}

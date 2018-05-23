package com.yuanxin.clan.core.widget.activity;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;


public abstract class CommonActivity extends FragmentActivity {
    //	private RunManProgressDialog progressDialog;
    private ProgressDialog progressDialog;
    private boolean backEnabled;
    private boolean isExit = false;


    /**
     * 创建等待框
     */
    private void createProgressDialog() {
        if (progressDialog != null) return;
        //	progressDialog = new ProgressDialog(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
    }

    /**
     * 启动等待框
     *
     * @return
     */
    public void showProgressDialog(int resourcesid) {
        createProgressDialog();
        //progressDialog.setMessage(getResources().getString(resourcesid));
//		progressDialog.setContent(resourcesid);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    /**
     * 关闭等待框
     */
    public void dismissProgressDialog() {
        if (progressDialog == null) return;
        progressDialog.dismiss();
    }

    /**
     * 返回键对出激活
     *
     * @param enabled (true 两次对出程序 )(false 正常启动返回)
     */
    public void setBackEnabled(boolean enabled) {
        backEnabled = enabled;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (backEnabled) {
            if (keyCode == KeyEvent.KEYCODE_BACK) exitByTwoClick();
            return true;
        } else return super.onKeyDown(keyCode, event);
    }

    /**
     * 两次退出程序
     */
    private void exitByTwoClick() {
        if (!isExit) {
            isExit = !isExit;
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
            exitByOne();
        } else exitApp();
    }

    private void exitApp() {
        finish();
        System.exit(0);
    }

    protected void exitByOne() {

    }
}

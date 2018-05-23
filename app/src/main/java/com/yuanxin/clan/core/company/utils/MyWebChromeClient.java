package com.yuanxin.clan.core.company.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ProgressBar;

import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.Logger;

/**
 * Created by Administrator on 2017/7/10 0010.
 */
public class MyWebChromeClient extends WebChromeClient {
    private Context mContext;
    private ProgressBar progressBar;
    public MyWebChromeClient(Context context, ProgressBar progressBar) {
        this.mContext = context;
        this.progressBar = progressBar;
    }

    public MyWebChromeClient(Context context) {
        this.mContext = context;
        this.progressBar = null;
    }
    @Override
    public boolean onJsAlert(WebView view, String url, String message,
                             final JsResult result) {
// 弹窗处理
        AlertDialog.Builder b2 = new AlertDialog.Builder(mContext)
                .setTitle(R.string.app_name).setMessage(message)
                .setPositiveButton("确定", new AlertDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                });


        b2.setCancelable(false);
        b2.create();
        b2.show();


        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {

        if (progressBar != null) {
            if (newProgress == 100) {
                progressBar.setVisibility(View.GONE);
            } else {
                if (progressBar.getVisibility() == View.GONE)
                    progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
        } else {
            Logger.e("progress null");
        }
//
//        if (mListener != null) {
//            mListener.onProgressChange(view, newProgress);
//        }

        super.onProgressChanged(view, newProgress);
    }
}
package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.X5WebView;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/3/27.
 *二维码详情
 */
public class ImgScanWebActivity extends BaseActivity {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    @BindView(R.id.tbsContent)
    X5WebView activityBaseWebAddWebView;
    @BindView(R.id.activity_yuan_xin_crowd_left_image)
    ImageView activityYuanXinCrowdLeftImage;
    @BindView(R.id.zixunshouchangimage)
    ImageView zixunshouchangimage;
    @BindView(R.id.activity_yuan_xin_crowd_close)
    LinearLayout activityYuanXinCrowdClose;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activityYuanXinCrowdLeftLayout;
    @BindView(R.id.activity_yuan_xin_crowd_middle_text)
    TextView activityYuanXinCrowdMiddleText;
    @BindView(R.id.contentte)
    TextView contentte;
//    @BindView(R.id.activity_t_yuan_xin_crowd_right_image) zixunshouchangimage
//    ImageView activityTYuanXinCrowdRightImage;
//    @BindView(R.id.activity_t_yuan_xin_crowd_right_image_layout)
//    LinearLayout activityTYuanXinCrowdRightImageLayout;
    @BindView(R.id.activity_news_chat_base_web_head_layout)
    RelativeLayout activityNewsChatBaseWebHeadLayout;

    private String content;

    private int refreshTime = 0;
    @Override
    public int getViewLayout() {
        return R.layout.imgscanwblayout;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        activityNewsChatBaseWebHeadLayout.setBackgroundResource(R.color.mine_page_color);
        activityBaseWebAddWebView.setProgressbarDrawable(getResources().getDrawable(R.color.mine_page_color));
        activityYuanXinCrowdMiddleText.setText("二维码详情");
        content = intent.getStringExtra("url");
        Log.v("lgq","uuuuuuuuuuuuuu===="+content);
        if (content.indexOf("http") > -1){
            zixunshouchangimage.setBackgroundResource(R.drawable.chat_icon_add);
            activityBaseWebAddWebView.loadUrl(intent.getStringExtra("url"));
        }else {
            zixunshouchangimage.setVisibility(View.GONE);
            contentte.setVisibility(View.VISIBLE);
            contentte.setText(content);
            activityBaseWebAddWebView.setVisibility(View.GONE);
        }
        activityYuanXinCrowdLeftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        zixunshouchangimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
                startActivity(webIntent);
            }
        });
        activityYuanXinCrowdLeftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if (refreshTime != 0) {
                    activityBaseWebAddWebView.reload();
                }
                refreshTime ++;
            }
        });
        refreshLayout.autoRefresh();

        activityBaseWebAddWebView.setListener(new X5WebView.OnScrollListener() {
            @Override
            public void onScrollUp() {

            }

            @Override
            public void onScrollDown() {

            }

            @Override
            public void scrollHeight(int h) {
                // 这里解决SmartRefreshLayout与X5Webview的滚动冲突
                if (h == 0) {
                    refreshLayout.setEnableRefresh(true);
                    refreshLayout.setEnableOverScrollDrag(true);
                } else {
                    refreshLayout.setEnableRefresh(false);
                    refreshLayout.setEnableOverScrollDrag(false);
                }
            }
        });
        activityBaseWebAddWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageFinished(view, url);
                refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (activityBaseWebAddWebView.canGoBack()) {
            activityBaseWebAddWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && activityBaseWebAddWebView.canGoBack()) {
            activityBaseWebAddWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

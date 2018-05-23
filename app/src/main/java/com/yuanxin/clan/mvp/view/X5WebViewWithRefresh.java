package com.yuanxin.clan.mvp.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.yuanxin.clan.R;

public class X5WebViewWithRefresh extends SmartRefreshLayout {

	private X5WebView x5WebView;
	private Context mContext;
	public X5WebViewWithRefresh(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		final View view = LayoutInflater.from(context).inflate(R.layout.x5webview_refresh, this);
		x5WebView = (X5WebView) view.findViewById(R.id.webview);
		this.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(RefreshLayout refreshlayout) {
				x5WebView.reload();
			}
		});
		this.setEnableLoadMore(false);
		this.setEnableOverScrollDrag(false);
//		this.autoRefresh();
		this.setEnableScrollContentWhenRefreshed(true);
		//设置 Header 为 BezierRadar 样式
//        refreshLayout.setRefreshHeader(new BezierRadarHeader(this).setEnableHorizontalDrag(true));
//		this.setRefreshHeader(new TaurusHeader(context));
//		this.setPrimaryColorsId(R.color.login_grey_bottomtwo, R.color.login_grey_bottom);
//        refreshLayout.setRefreshHeader(new MaterialHeader(this));

		x5WebView.setListener(new X5WebView.OnScrollListener() {
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
					((SmartRefreshLayout)view).setEnableRefresh(true);
				} else {
					((SmartRefreshLayout)view).setEnableRefresh(false);
				}
			}
		});
	}
}

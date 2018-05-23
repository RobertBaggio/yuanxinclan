package com.yuanxin.clan.mvp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.utils.MyWebChromeClient;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.entity.UserAgentParam;
import com.yuanxin.clan.mvp.utils.CommonString;

import java.util.ArrayList;
import java.util.List;

public class X5WebView extends WebView {
	private ProgressBar progressbar;  //进度条
	private int progressHeight = 10;  //进度条的高度，默认10px
	TextView title;
	private ActionMode mActionMode;
	private long last_time = 0L;
	private List<String> mActionList = new ArrayList<>();
	private WebViewClient client = new WebViewClient() {
		/**
		 * 防止加载网页时调起系统浏览器
		 */
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			view.loadUrl(url);
			return true;
		}

        // 重写 WebViewClient  的  shouldInterceptRequest （）
        // API 21 以下用shouldInterceptRequest(WebView view, String url)
        // API 21 以上用shouldInterceptRequest(WebView view, WebResourceRequest request)
        // 下面会详细说明

        // API 21 以下用shouldInterceptRequest(WebView view, String url)
//        @Override
//        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
//            if (url.contains("jquery.js"))
//			{
//				String localPath = url.replaceFirst("^http.*[tag]\\]", "");
//				try
//				{
//					InputStream is = getContext().getAssets().open(localPath);
//					String mimeType = "text/javascript";
//					if (localPath.endsWith("css"))
//					{
//						mimeType = "text/css";
//					}
//					return new WebResourceResponse(mimeType, "UTF-8", is);
//				}
//				catch (Exception e)
//				{
//					e.printStackTrace();
//				}
//			}
//            return super.shouldInterceptRequest(view, url);
//        }
//
//
//        // API 21 以上用shouldInterceptRequest(WebView view, WebResourceRequest request)
//        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//        @Override
//        public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
//            String url = request.getUrl().toString();
//            if (url.contains("[tag]"))
//            {
//                String localPath = url.replaceFirst("^http.*[tag]\\]", "");
//                try
//                {
//                    InputStream is = getContext().getAssets().open(localPath);
//                    String mimeType = "text/javascript";
//                    if (localPath.endsWith("css"))
//                    {
//                        mimeType = "text/css";
//                    }
//                    return new WebResourceResponse(mimeType, "UTF-8", is);
//                }
//                catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
//            }
//            return super.shouldInterceptRequest(view, request);
//        }
	};
	public OnScrollListener listener;
	/**
	 * This is called in response to an internal scroll in this view (i.e., the
	 * view scrolled its own contents). This is typically as a result of
	 * {@link #scrollBy(int, int)} or {@link #scrollTo(int, int)} having been
	 * called.
	 *
	 * @param l Current horizontal scroll origin.
	 * @param t Current vertical scroll origin.
	 * @param oldl Previous horizontal scroll origin.
	 * @param oldt Previous vertical scroll origin.
	 */

	private static final int[] mAttr = { R.attr.defaultProgress };
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);

		if (listener != null){
			if (t - oldt <= 2){
				listener.onScrollDown();
			}
			if(oldt - t >= 2) {
				listener.onScrollUp();
			}
			listener.scrollHeight(t);
		}
	}
	public void setListener(OnScrollListener listener){
		this.listener = listener;
	}

	public interface OnScrollListener{
		void onScrollUp();//上滑
		void onScrollDown();//下滑
		void scrollHeight(int h);
	}
	//这两个方法会在用户长按选择web文本时，在弹出菜单前被调用。
	@Override
	public ActionMode startActionMode(ActionMode.Callback callback) {
		ActionMode actionMode = startActionMode(callback);
		Log.e("hxw", actionMode.toString());
		return resolveActionMode(actionMode);
	}

	@Override
	public ActionMode startActionMode(ActionMode.Callback callback, int type) {
		ActionMode actionMode = startActionMode(callback, type);
		Log.e("hxw", actionMode.toString() + " " + type);
		return resolveActionMode(actionMode);
	}

//	@Override//双击
//	public boolean onTouchEvent(MotionEvent event) {
//		switch (event.getAction()) {
//			case MotionEvent.ACTION_DOWN:
//				long current_time = System.currentTimeMillis();
//				long d_time = current_time - last_time;
////                System.out.println(d_time);
//				if (d_time < 300) {
//					last_time = current_time;
//					return true;
//				} else {
//					last_time = current_time;
//				}
//				break;
//		}
//		return super.onTouchEvent(event);
//	}

	//处理item，处理点击
	private ActionMode resolveActionMode(ActionMode actionMode) {
		if (actionMode != null) {
			final Menu menu = actionMode.getMenu();
			mActionMode = actionMode;
			menu.clear();
			Log.e("hxw", mActionList.toString());
			for (int i = 0; i < mActionList.size(); i++) {
				menu.add(mActionList.get(i));
			}
			for (int i = 0; i < menu.size(); i++) {
				MenuItem menuItem = menu.getItem(i);
				menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						// getSelectedData((String) item.getTitle());
						// releaseAction();

						return true;
					}
				});
			}
		}
		mActionMode = actionMode;
		return actionMode;
	}
	//设置弹出action列表

	public void setActionList(List<String> actionList) {
		mActionList = actionList;
	}

	@SuppressLint("SetJavaScriptEnabled")
	public X5WebView(Context arg0, AttributeSet arg1) {
		super(arg0, arg1);
		TypedArray ta = arg0.obtainStyledAttributes(arg1, mAttr);
		Boolean enableDefaultProgress = ta.getBoolean(0, true);
		if (!enableDefaultProgress) {
			initWebViewSettings(arg0, false);
			this.setWebChromeClient(new MyWebChromeClient(arg0));
		} else {
			initWebViewSettings(arg0, true);
			this.setWebChromeClient(new MyWebChromeClient(arg0, progressbar));
		}
		this.setWebViewClient(client);
		// this.setWebChromeClient(chromeClient);
		// WebStorage webStorage = WebStorage.getInstance();
		this.getView().setClickable(true);
	}

	public void setProgressbarDrawable(Drawable d) {
		if (progressbar != null) {
			progressbar.setProgressDrawable(d);
		}
	}

		private void initWebViewSettings(Context context, Boolean enable) {
		if (enable) {
			//创建进度条
			progressbar = new ProgressBar(context, null,
					android.R.attr.progressBarStyleHorizontal);
			//设置加载进度条的高度
			progressbar.setLayoutParams(new AbsoluteLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, progressHeight, 0, 0));

			Drawable drawable = context.getResources().getDrawable(R.drawable.progressbar_blue);
			progressbar.setProgressDrawable(drawable);

			//添加进度到WebView
			addView(progressbar);
		}


		WebSettings webSetting = this.getSettings();
		webSetting.setJavaScriptEnabled(true);
		webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
		webSetting.setAllowFileAccess(true);
		webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
		webSetting.setSupportZoom(true);
		webSetting.setBuiltInZoomControls(true);
		webSetting.setUseWideViewPort(true);
		webSetting.setSupportMultipleWindows(true);
		// webSetting.setLoadWithOverviewMode(true);
		webSetting.setAppCacheEnabled(false);
		// webSetting.setDatabaseEnabled(true);
		webSetting.setDomStorageEnabled(true);
		webSetting.setGeolocationEnabled(true);
		webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
		webSetting.setTextSize(WebSettings.TextSize.NORMAL);
		// webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
		webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
		// webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
		webSetting.setCacheMode(WebSettings.LOAD_DEFAULT);
		webSetting.setBuiltInZoomControls(false);
		webSetting.setDisplayZoomControls(false);
		webSetting.setSupportZoom(false);
		this.setWebContentsDebuggingEnabled(true);
		String ua = webSetting.getUserAgentString();
		UserAgentParam up;
		if (UserNative.readIsLogin()) {
			up = new UserAgentParam(CommonString.appTag, UserNative.getId(), UserNative.getName(), UserNative.getPhone(), UserNative.getPwd(), UserNative.getEpId(), UserNative.getImage(), UserNative.getAesKes(), MyShareUtil.getSharedString("city"));
		} else {
			up = new UserAgentParam(CommonString.appTag, -1, "", "", "", "", "", "","");
		}
		webSetting.setUserAgent(ua + "&" + FastJsonUtils.toJSONString(up));
		webSetting.setLoadsImagesAutomatically(true);
		Log.e("hxw", webSetting.getUserAgentString());
		// this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
		// settings 的设计
	}

//	@Override
//	protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
//		boolean ret = super.drawChild(canvas, child, drawingTime);
//		canvas.save();
//		Paint paint = new Paint();
//		paint.setColor(0x7fff0000);
//		paint.setTextSize(24.f);
//		paint.setAntiAlias(true);
//		if (getX5WebViewExtension() != null) {
//			canvas.drawText(this.getContext().getPackageName() + "-pid:"
//					+ android.os.Process.myPid(), 10, 50, paint);
//			canvas.drawText(
//					"X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10,
//					100, paint);
//		} else {
//			canvas.drawText(this.getContext().getPackageName() + "-pid:"
//					+ android.os.Process.myPid(), 10, 50, paint);
//			canvas.drawText("Sys Core", 10, 100, paint);
//		}
//		canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
//		canvas.drawText(Build.MODEL, 10, 200, paint);
//		canvas.restore();
//		return ret;
//	}

	/*public X5WebView(Context arg0) {
		super(arg0);
		setBackgroundColor(85621);
	}*/
}

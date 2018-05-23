package com.yuanxin.clan.mvp.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jaredrummler.android.widget.AnimatedSvgView;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.loading.SVG;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PullToRefreshView extends LinearLayout {
    private static final int REFRESHING_TYPE_HEADER = 1;
    private static final int REFRESHING_TYPE_FOOTER = 2;
    private int nRefreshingType = 0;
    // refresh states
    private static final int PULL_TO_REFRESH = 2;
    private static final int RELEASE_TO_REFRESH = 3;
    private static final int REFRESHING = 4;
    // pull state
    private static final int PULL_UP_STATE = 0;
    private static final int PULL_DOWN_STATE = 1;
    private boolean enablePullTorefresh = true;
    private boolean enablePullLoadMoreDataStatus = true;
    /**
     * last y
     */
    private int mLastMotionY;
    /**
     * last x
     */
    private int mLastMotionX;
    /**
     * lock
     */
    private boolean mLock;
    /**
     * header view
     */
    private View mHeaderView;
    /**
     * footer view
     */
    private View mFooterView;
    /**
     * list or grid
     */
    private AdapterView<?> mAdapterView;
    /**
     * scrollview
     */
    private ScrollView mScrollView;
    /**
     * header view height
     */
    private int mHeaderViewHeight;
    /**
     * footer view height
     */
    private int mFooterViewHeight;
    /**
     * header view image
     */
    private ImageView mHeaderImageView;
    /**
     * footer view image
     */
    private ImageView mFooterImageView;
    /**
     * header tip text
     */
    private TextView mHeaderTextView;
    /**
     * footer tip text
     */
    private TextView mFooterTextView;
    /**
     * header refresh time
     */
    private TextView mHeaderUpdateTextView;
    /**
     * footer refresh time
     */
    // private TextView mFooterUpdateTextView;
    /**
     * header progress bar
     */
    private ProgressBar mHeaderProgressBar;
    /**
     * footer progress bar
     */
    private ProgressBar mFooterProgressBar;
    /**
     * layout inflater
     */
    private LayoutInflater mInflater;
    /**
     * header view current state
     */
    private int mHeaderState;
    /**
     * footer view current state
     */
    private int mFooterState;
    /**
     * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
     */
    private int mPullState;
    /**
     * 变为向下的箭头,改变箭头方向
     */
    private RotateAnimation mFlipAnimation;
    /**
     * 变为逆向的箭头,旋转
     */
    private RotateAnimation mReverseFlipAnimation;
    /**
     * footer refresh listener
     */
    private OnFooterRefreshListener mOnFooterRefreshListener;
    /**
     * footer refresh listener
     */
    private OnHeaderRefreshListener mOnHeaderRefreshListener;

    private AnimatedSvgView svgHeaderView;
    /**
     * last update time
     */
    private Context context;

    private Boolean playHeadSound = false;

    public PullToRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public PullToRefreshView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    /**
     * init
     *
     * @description
     */
    private void init() {
        // Load all of the animations we need in code rather than through XML
        mFlipAnimation = new RotateAnimation(0, -180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mFlipAnimation.setInterpolator(new LinearInterpolator());
        mFlipAnimation.setDuration(100);
        mFlipAnimation.setFillAfter(true);
        mReverseFlipAnimation = new RotateAnimation(-180, 0, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
        mReverseFlipAnimation.setDuration(100);
        mReverseFlipAnimation.setFillAfter(true);

        mInflater = LayoutInflater.from(getContext());
        // header view 在此添加,保证是第一个添加到linearlayout的最上端
        addHeaderView();
    }

    private void addHeaderView() {
        // header view
        mHeaderView = mInflater.inflate(R.layout.refresh_header_svg, this, false);

        mHeaderImageView = (ImageView) mHeaderView.findViewById(R.id.pull_to_refresh_image);
        mHeaderTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_text);
        mHeaderUpdateTextView = (TextView) mHeaderView.findViewById(R.id.pull_to_refresh_updated_at);
        mHeaderProgressBar = (ProgressBar) mHeaderView.findViewById(R.id.pull_to_refresh_progress);
        // header layout
        svgHeaderView = (AnimatedSvgView) mHeaderView.findViewById(R.id.animated_svg_view);
        measureView(mHeaderView);
        mHeaderViewHeight = mHeaderView.getMeasuredHeight();
        LinearLayout.LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // 设置topMargin的值为负的header View高度,即将其隐藏在最上方
        params.topMargin = -(mHeaderViewHeight);
        mHeaderView.setLayoutParams(params);
        addView(mHeaderView, params);
        setSvg(svgHeaderView, SVG.YUANXIN_LOGO);
    }
    private void setSvg(final AnimatedSvgView animatedSvgView, SVG svg) {
        animatedSvgView.setGlyphStrings(svg.glyphs);
        animatedSvgView.setFillColors(svg.colors);
        animatedSvgView.setViewportSize(svg.width, svg.height);
        animatedSvgView.setTraceResidueColor(0x32000000);
        animatedSvgView.setTraceColors(svg.colors);
        animatedSvgView.rebuildGlyphData();
        animatedSvgView.start();
//        animatedSvgView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (animatedSvgView.getState() == AnimatedSvgView.STATE_FINISHED)
//                {
//                    animatedSvgView.start();
//                }
//            }
//        });
        animatedSvgView.setOnStateChangeListener(new AnimatedSvgView.OnStateChangeListener() {
            @Override
            public void onStateChange(int state) {
                if (animatedSvgView.getState() == AnimatedSvgView.STATE_FINISHED)
                {
                    animatedSvgView.start();
                }
            }
        });
    }
    //    private void startSvg() {
//        if (svgHeaderView.getState() == AnimatedSvgView.STATE_FINISHED)
//        {
//            svgHeaderView.start();
//        }
//    }
    public void setBarColor(int nColor) {
        mHeaderView.setBackgroundColor(nColor);
        mFooterView.setBackgroundColor(nColor);
    }

    private void addFooterView() {
        // footer view
        mFooterView = mInflater.inflate(R.layout.refresh_footer, this, false);
        mFooterImageView = (ImageView) mFooterView.findViewById(R.id.pull_to_load_image);
        mFooterTextView = (TextView) mFooterView.findViewById(R.id.pull_to_load_text);
        mFooterProgressBar = (ProgressBar) mFooterView.findViewById(R.id.pull_to_load_progress);
        // footer layout
        measureView(mFooterView);
        mFooterViewHeight = mFooterView.getMeasuredHeight();
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, mFooterViewHeight);
        // int top = getHeight();
        // params.topMargin
        // =getHeight();//在这里getHeight()==0,但在onInterceptTouchEvent()方法里getHeight()已经有值了,不再是0;
        // getHeight()什么时候会赋值,稍候再研究一下
        // 由于是线性布局可以直接添加,只要AdapterView的高度是MATCH_PARENT,那么footer view就会被添加到最后,并隐藏
        addView(mFooterView, params);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        // footer view 在此添加保证添加到linearlayout中的最后
        addFooterView();
        initContentAdapterView();
    }

    /**
     * init AdapterView like ListView,GridView and so on;or init ScrollView
     */
    private void initContentAdapterView() {
        int count = getChildCount();
        if (count < 3) {
            throw new IllegalArgumentException(
                    "this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
        }
        View view = null;
        for (int i = 0; i < count - 1; ++i) {
            view = getChildAt(i);
            if (view instanceof AdapterView<?>) {
                mAdapterView = (AdapterView<?>) view;
            }
            if (view instanceof ScrollView) {
                // finish later
                mScrollView = (ScrollView) view;
            }
        }
        if (mAdapterView == null && mScrollView == null) {
            throw new IllegalArgumentException(
                    "must contain a AdapterView or ScrollView in this layout!");
        }
    }

    private void measureView(View child) {
        ViewGroup.LayoutParams p = child.getLayoutParams();
        if (p == null) {
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
        int lpHeight = p.height;
        int childHeightSpec;
        if (lpHeight > 0) {
            childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
        } else {
            childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
        }
        child.measure(childWidthSpec, childHeightSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {
        int y = (int) e.getRawY();
        int x = (int) e.getRawX();
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 首先拦截down事件,记录y坐标
                mLastMotionY = y;
                mLastMotionX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                // deltaY > 0 是向下运动,< 0是向上运动
                int deltaY = y - mLastMotionY;
                int deltaX = x - mLastMotionX;
                if (Math.abs(deltaY) > Math.abs(deltaX) * 1.5
                        && isRefreshViewScroll(deltaY)) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return false;
    }

    /*
     * 如果在onInterceptTouchEvent()方法中没有拦截(即onInterceptTouchEvent()方法中 return
     * false)则由PullToRefreshView 的子View来处理;否则由下面的方法来处理(即由PullToRefreshView自己来处理)
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLock) {
            return true;
        }
        int y = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // onInterceptTouchEvent已经记录
                // mLastMotionY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int deltaY = y - mLastMotionY;
                if (mPullState == PULL_DOWN_STATE) {
                    // PullToRefreshView执行下拉
                    headerPrepareToRefresh(deltaY);
                    // setHeaderPadding(-mHeaderViewHeight);
                } else if (mPullState == PULL_UP_STATE) {
                    // PullToRefreshView执行上拉
                    footerPrepareToRefresh(deltaY);
                }
                mLastMotionY = y;
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                int topMargin = getHeaderTopMargin();
                if (mPullState == PULL_DOWN_STATE) {
                    if (topMargin >= 0) {
                        // 开始刷新
                        headerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                } else if (mPullState == PULL_UP_STATE) {
                    if (Math.abs(topMargin) >= mHeaderViewHeight
                            + mFooterViewHeight) {
                        // 开始执行footer 刷新
                        footerRefreshing();
                    } else {
                        // 还没有执行刷新，重新隐藏
                        setHeaderTopMargin(-mHeaderViewHeight);
                    }
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 是否应该到了父View,即PullToRefreshView滑动
     *
     * @param deltaY , deltaY > 0 是向下运动,< 0是向上运动
     * @return
     */
    private boolean isRefreshViewScroll(int deltaY) {
        if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
            return false;
        }
        // 对于ListView和GridView
        if (mAdapterView != null) {
            // 子view(ListView or GridView)滑动到最顶端
            if (deltaY > 0) {
                // 判断是否禁用下拉刷新操作
                if (!enablePullTorefresh) {
                    return false;
                }
                View child = mAdapterView.getChildAt(0);
                if (child == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && child.getTop() == 0) {
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }
                int top = child.getTop();
                int padding = mAdapterView.getPaddingTop();
                if (mAdapterView.getFirstVisiblePosition() == 0
                        && Math.abs(top - padding) <= 11) {// 这里之前用3可以判断,但现在不行,还没找到原因
                    mPullState = PULL_DOWN_STATE;
                    return true;
                }

            } else if (deltaY < 0) {
                // 判断是否禁用上拉加载更多操作
                if (!enablePullLoadMoreDataStatus) {
                    return false;
                }
                View lastChild = mAdapterView.getChildAt(mAdapterView
                        .getChildCount() - 1);
                if (lastChild == null) {
                    // 如果mAdapterView中没有数据,不拦截
                    return false;
                }
                // 最后一个子view的Bottom小于父View的高度说明mAdapterView的数据没有填满父view,
                // 等于父View的高度说明mAdapterView已经滑动到最后
                if (lastChild.getBottom() <= getHeight()
                        && mAdapterView.getLastVisiblePosition() == mAdapterView
                        .getCount() - 1) {
                    mPullState = PULL_UP_STATE;
                    return true;
                }
            }
        }
        // 对于ScrollView
        if (mScrollView != null) {
            // 子scroll view滑动到最顶端
            View child = mScrollView.getChildAt(0);
            if (deltaY > 0 && mScrollView.getScrollY() == 0) {
                if (!enablePullTorefresh) {
                    return false;
                }
                mPullState = PULL_DOWN_STATE;
                return true;
            } else if (deltaY < 0
                    && child.getMeasuredHeight() <= getHeight()
                    + mScrollView.getScrollY()) {
                if (!enablePullLoadMoreDataStatus) {
                    return false;
                }
                mPullState = PULL_UP_STATE;
                return true;
            }
        }
        return false;
    }

    /**
     * header 准备刷新,手指移动过程,还没有释放
     *
     * @param deltaY ,手指滑动的距离
     */
    private void headerPrepareToRefresh(int deltaY) {
        playHeadSound = false;
        int newTopMargin = changingHeaderViewTopMargin(deltaY, 0.3f);
        // 当header view的topMargin>=0时，说明已经完全显示出来了,修改header view 的提示状态
        if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
            mHeaderTextView.setText(R.string.pull_to_refresh_release_label);
            mHeaderUpdateTextView.setVisibility(View.GONE);
//            mHeaderImageView.clearAnimation();
//            mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderState = RELEASE_TO_REFRESH;
//            startSvg();
        } else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 拖动时没有释放
//            mHeaderImageView.clearAnimation();
//            mHeaderImageView.startAnimation(mFlipAnimation);
            mHeaderUpdateTextView.setVisibility(View.GONE);
            // mHeaderImageView.
            mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
            mHeaderState = PULL_TO_REFRESH;
//            startSvg();
        }
    }

    /**
     * footer 准备刷新,手指移动过程,还没有释放 移动footer view高度同样和移动header view
     * 高度是一样，都是通过修改header view的topmargin的值来达到
     *
     * @param deltaY ,手指滑动的距离
     */
    private void footerPrepareToRefresh(int deltaY) {
        int newTopMargin = changingHeaderViewTopMargin(deltaY, 1.0f);
        // 如果header view topMargin 的绝对值大于或等于header + footer 的高度
        // 说明footer view 完全显示出来了，修改footer view 的提示状态
        if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
                && mFooterState != RELEASE_TO_REFRESH) {
            mFooterTextView
                    .setText(R.string.pull_to_refresh_footer_release_label);
            mFooterImageView.clearAnimation();
            mFooterImageView.startAnimation(mFlipAnimation);
            mFooterState = RELEASE_TO_REFRESH;
        } else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
            mFooterImageView.clearAnimation();
            mFooterImageView.startAnimation(mFlipAnimation);
            mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
            mFooterState = PULL_TO_REFRESH;
        }
    }

    /**
     * 修改Header view top margin的值
     *
     * @param deltaY
     * @description
     */
    private int changingHeaderViewTopMargin(int deltaY, float percentage) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        float newTopMargin = params.topMargin + deltaY * percentage;
        // 这里对上拉做一下限制,因为当前上拉后然后不释放手指直接下拉,会把下拉刷新给触发了,感谢网友yufengzungzhe的指出
        // 表示如果是在上拉后一段距离,然后直接下拉
        if (deltaY > 0 && mPullState == PULL_UP_STATE && Math.abs(params.topMargin) <= mHeaderViewHeight) {
            return params.topMargin;
        }
        // 同样地,对下拉做一下限制,避免出现跟上拉操作时一样的bug
        if (deltaY < 0 && mPullState == PULL_DOWN_STATE && Math.abs(params.topMargin) >= mHeaderViewHeight) {
            return params.topMargin;
        }
        params.topMargin = (int) newTopMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
        return params.topMargin;
    }

    /**
     * header refreshing
     */
    public void headerRefreshing() {
        mHeaderState = REFRESHING;
        setHeaderTopMargin(0);
//        mHeaderImageView.setVisibility(View.GONE);
//        mHeaderImageView.clearAnimation();
//        mHeaderImageView.setImageDrawable(null);
//        mHeaderProgressBar.setVisibility(View.VISIBLE);
        mHeaderTextView.setText(R.string.pull_to_refresh_refreshing_label);
        if (!TextUtils.isEmpty(mHeaderUpdateTextView.getText().toString().trim()))
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
        if (mOnHeaderRefreshListener != null) {
            mOnHeaderRefreshListener.onHeaderRefresh(this);
        }
//        startSvg();
        nRefreshingType = REFRESHING_TYPE_HEADER;
    }

    /**
     * footer refreshing
     */
    private void footerRefreshing() {

        mFooterState = REFRESHING;
        int top = mHeaderViewHeight + mFooterViewHeight;
        setHeaderTopMargin(-top);
        mFooterImageView.setVisibility(View.GONE);
        mFooterImageView.clearAnimation();
        mFooterImageView.setImageDrawable(null);
        mFooterProgressBar.setVisibility(View.VISIBLE);
        mFooterTextView
                .setText(R.string.pull_to_refresh_footer_refreshing_label);
        if (mOnFooterRefreshListener != null) {
            mOnFooterRefreshListener.onFooterRefresh(this);
        }
        nRefreshingType = REFRESHING_TYPE_FOOTER;
    }

    /**
     * 设置header view 的topMargin的值
     *
     * @param topMargin ，为0时，说明header view 刚好完全显示出来； 为-mHeaderViewHeight时，说明完全隐藏了
     * @description
     */
    private void setHeaderTopMargin(int topMargin) {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        params.topMargin = topMargin;
        mHeaderView.setLayoutParams(params);
        invalidate();
    }

    /**
     * header view 完成更新后恢复初始状态
     */
    private void onHeaderRefreshComplete() {
        //播放音效
//        if (!playHeadSound) {
//            SoundUtils.playSoundByVolume(context, R.raw.button13, 0.1f);
//            playHeadSound = !playHeadSound;
//        }
        setHeaderTopMargin(-mHeaderViewHeight);
//        mHeaderImageView.setVisibility(View.VISIBLE);
//        mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
        mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
//        mHeaderProgressBar.setVisibility(View.GONE);
        mHeaderState = PULL_TO_REFRESH;
        long between = Calendar.getInstance().getTimeInMillis() - System.currentTimeMillis();
        setLastUpdated(String.format(context
                .getString(R.string.pull_to_refresh_refresh_time_label), new SimpleDateFormat((between >= 86400000 ? "MM-dd" : "HH:mm")).format(System.currentTimeMillis())));
    }

    /**
     * Resets the list to a normal state after a refresh.
     *
     * @param lastUpdated Last updated at.
     */
    public void onHeaderRefreshComplete(CharSequence lastUpdated) {
        setLastUpdated(lastUpdated);
        onHeaderRefreshComplete();
    }

    /**
     * footer view 完成更新后恢复初始状态
     */
    private void onFooterRefreshComplete() {
        setHeaderTopMargin(-mHeaderViewHeight);
        mFooterImageView.setVisibility(View.VISIBLE);
        mFooterImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
        mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
        mFooterProgressBar.setVisibility(View.GONE);
        mHeaderUpdateTextView.setText("");
        mFooterState = PULL_TO_REFRESH;
    }

    /**
     * footer view 完成更新后恢复初始状态
     */
    public void onFooterRefreshComplete(int size) {
        if (size > 0) {
            mFooterView.setVisibility(View.VISIBLE);
        } else {
            mFooterView.setVisibility(View.GONE);
        }
        setHeaderTopMargin(-mHeaderViewHeight);
        mFooterImageView.setVisibility(View.VISIBLE);
        mFooterImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
        mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
        mFooterProgressBar.setVisibility(View.GONE);
        mHeaderUpdateTextView.setText("");
        mFooterState = PULL_TO_REFRESH;
    }

    /**
     * 完成更新后恢复初始状态
     */
    public void setRefreshComplete() {
        switch (nRefreshingType) {
            case REFRESHING_TYPE_HEADER:
                onHeaderRefreshComplete();
                break;
            case REFRESHING_TYPE_FOOTER:
                onFooterRefreshComplete();
                break;

            default:
                break;
        }
    }

    /**
     * Set a text to represent when the list was last updated.
     *
     * @param lastUpdated Last updated at.
     */
    public void setLastUpdated(CharSequence lastUpdated) {
        if (lastUpdated != null) {
            mHeaderUpdateTextView.setVisibility(View.VISIBLE);
            mHeaderUpdateTextView.setText(lastUpdated);
        } else {
            mHeaderUpdateTextView.setVisibility(View.GONE);
        }
    }

    /**
     * 获取当前header view 的topMargin
     *
     * @description
     */
    private int getHeaderTopMargin() {
        LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
        return params.topMargin;
    }

    // /**
    // * lock
    // *
    // */
    // private void lock() {
    // mLock = true;
    // }
    //
    // /**
    // * unlock
    // *
    // */
    // private void unlock() {
    // mLock = false;
    // }

    /**
     * set headerRefreshListener
     *
     * @param headerRefreshListener
     * @description
     */
    public void setOnHeaderRefreshListener(
            OnHeaderRefreshListener headerRefreshListener) {
        mOnHeaderRefreshListener = headerRefreshListener;
    }

    public void setOnFooterRefreshListener(
            OnFooterRefreshListener footerRefreshListener) {
        mOnFooterRefreshListener = footerRefreshListener;
    }

    /**
     * Interface definition for a callback to be invoked when list/grid footer
     * view should be refreshed.
     */
    public interface OnFooterRefreshListener {
        public void onFooterRefresh(PullToRefreshView view);
    }

    /**
     * Interface definition for a callback to be invoked when list/grid header
     * view should be refreshed.
     */
    public interface OnHeaderRefreshListener {
        public void onHeaderRefresh(PullToRefreshView view);
    }

    public boolean isEnablePullTorefresh() {
        return enablePullTorefresh;
    }

    public void setEnablePullTorefresh(boolean enablePullTorefresh) {
        this.enablePullTorefresh = enablePullTorefresh;
    }

    public boolean isEnablePullLoadMoreDataStatus() {
        return enablePullLoadMoreDataStatus;
    }

    public void setEnablePullLoadMoreDataStatus(
            boolean enablePullLoadMoreDataStatus) {
        this.enablePullLoadMoreDataStatus = enablePullLoadMoreDataStatus;
    }
}
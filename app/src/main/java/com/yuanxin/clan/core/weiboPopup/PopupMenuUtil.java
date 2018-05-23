package com.yuanxin.clan.core.weiboPopup;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.Utils;

import butterknife.BindView;

/**
 * Created by mj
 * on 2016/10/28.
 */
public class PopupMenuUtil {

    @BindView(R.id.image_gy)
    ImageView imageGy;
    @BindView(R.id.image_xq)
    ImageView imageXq;
    @BindView(R.id.image_hd)
    ImageView imageHd;
    @BindView(R.id.image_dt)
    ImageView imageDt;

    private static final String TAG = "PopupMenuUtil";

    public static PopupMenuUtil getInstance() {
        return MenuUtilHolder.INSTANCE;
    }

    private static class MenuUtilHolder {
        public static PopupMenuUtil INSTANCE = new PopupMenuUtil();
    }

    private View rootVew;
    private PopupWindow popupWindow;

    private RelativeLayout rlClick;
    private ImageView ivBtn;
    private LinearLayout llTest1, llTest2, llTest3, llTest4;

    private Context mContext;
    /**
     * 动画执行的 属性值数组
     */
    float animatorProperty[] = null;
    /**
     * 第一排图 距离屏幕底部的距离
     */
    int top = 0;
    /**
     * 第二排图 距离屏幕底部的距离
     */
    int bottom = 0;

    /**
     * 创建 popupWindow 内容
     *
     * @param context context
     */
    private void _createView(final Context context) {
        mContext = context;
        rootVew = LayoutInflater.from(context).inflate(R.layout.center_music_more_window, null);
        popupWindow = new PopupWindow(rootVew,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        //设置为失去焦点 方便监听返回键的监听
        popupWindow.setFocusable(false);

        // 如果想要popupWindow 遮挡住状态栏可以加上这句代码
        //popupWindow.setClippingEnabled(false);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(false);

        if (animatorProperty == null) {
            top = dip2px(context, 310);
            bottom = dip2px(context, 210);
            animatorProperty = new float[]{bottom, 60, -30, -20 - 10, 0};
        }

        initLayout(context);
    }

    /**
     * dp转化为px
     *
     * @param context  context
     * @param dipValue dp value
     * @return 转换之后的px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 初始化 view
     */
    private void initLayout(Context context) {
        rlClick = (RelativeLayout) rootVew.findViewById(R.id.close_layout);
        ivBtn = (ImageView) rootVew.findViewById(R.id.center_music_window_close);
        llTest1 = (LinearLayout) rootVew.findViewById(R.id.pulish_gongying);
        llTest2 = (LinearLayout) rootVew.findViewById(R.id.pulish_xuqiu);
        llTest3 = (LinearLayout) rootVew.findViewById(R.id.pulish_huodong);
        llTest4 = (LinearLayout) rootVew.findViewById(R.id.pulish_dongtai);

        rlClick.setOnClickListener(new MViewClick(0, context));

        llTest1.setOnClickListener(new MViewClick(1, context));
        llTest2.setOnClickListener(new MViewClick(2, context));
        llTest3.setOnClickListener(new MViewClick(3, context));
        llTest4.setOnClickListener(new MViewClick(4, context));

    }

    /**
     * 点击事件
     */
    private class MViewClick implements View.OnClickListener {

        public int index;
        public Context context;

        public MViewClick(int index, Context context) {
            this.index = index;
            this.context = context;
        }

        @Override
        public void onClick(View v) {
            if (index == 0) {
                //加号按钮点击之后的执行
                _rlClickAction();
            } else {
                showToast(context, "index=" + index);
            }
        }
    }

    Toast toast = null;

    /**
     * 防止toast 多次被创建
     *
     * @param context context
     * @param str     str
     */
    private void showToast(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        } else {
            toast.setText(str);
        }
        toast.show();
    }

    /**
     * 刚打开popupWindow 执行的动画
     */
    private void _openPopupWindowAction() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivBtn, "rotation", 0f, 135f);
        objectAnimator.setDuration(200);
        objectAnimator.start();

        _startAnimation(imageGy, llTest1, 500, animatorProperty);
        _startAnimation(imageXq, llTest2, 430, animatorProperty);
        _startAnimation(imageHd, llTest3, 430, animatorProperty);
        _startAnimation(imageDt, llTest4, 500, animatorProperty);
    }


    /**
     * 关闭 popupWindow执行的动画
     */
    public void _rlClickAction() {
        if (ivBtn != null && rlClick != null) {

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(ivBtn, "rotation", 135f, 0f);
            ObjectAnimator objectAnimatorAlpha = ObjectAnimator.ofFloat(rlClick, "alpha", 1f, 0f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(objectAnimator, objectAnimatorAlpha);
            animatorSet.setDuration(300);
            animatorSet.start();

            int screenWidth = Utils.getScreenWidth(mContext);
            _closeAnimation(llTest1, 500, (screenWidth - dip2px(mContext,50)) * 2/3, top);
            _closeAnimation(llTest2, 300, -(screenWidth - dip2px(mContext,50))/3, top);
            _closeAnimation(llTest3, 300, (screenWidth - dip2px(mContext,50))/3, top);
            _closeAnimation(llTest4, 500, -(screenWidth - dip2px(mContext,50))*2/3, top);

            rlClick.postDelayed(new Runnable() {
                @Override
                public void run() {
                    _close();
                }
            }, 500);

        }
    }


    /**
     * 弹起 popupWindow
     *
     * @param context context
     * @param parent  parent
     */
    public void _show(Context context, View parent) {
        _createView(context);
        if (popupWindow != null && !popupWindow.isShowing()) {
            popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, 0, 0);
            _openPopupWindowAction();
        }
    }

    /**
     * 关闭popupWindow
     */

    public void _close() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * @return popupWindow 是否显示了
     */
    public boolean _isShowing() {
        if (popupWindow == null) {
            return false;
        } else {
            return popupWindow.isShowing();
        }
    }

    /**
     * 关闭 popupWindow 时的动画
     *
     * @param view     mView
     * @param duration 动画执行时长
     * @param nextX     平移量
     */
    private void _closeAnimation(View view, int duration, int nextX, int nextY) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animY = ObjectAnimator.ofFloat(view, "translationY", 0f, nextY);
        ObjectAnimator animX = ObjectAnimator.ofFloat(view, "translationX", 0f, nextX);
        animatorSet.playTogether(animX, animY);
        animatorSet.setDuration(duration);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.setStartDelay(50);
        animatorSet.start();
    }

    /**
     * 启动动画
     *
     * @param view     view
     * @param duration 执行时长
     * @param distance 执行的轨迹数组
     */
    private void _startAnimation(ImageView imageView, View view, int duration, float[] distance) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "translationY", distance);
        ObjectAnimator animRotate = ObjectAnimator.ofFloat(view, "rotation", 0f, 360f);
        animatorSet.playTogether(anim, animRotate);
        animatorSet.setInterpolator(new OvershootInterpolator(0.9f));
        animatorSet.setStartDelay(50);
        animatorSet.setDuration(duration);
        animatorSet.start();
    }


}

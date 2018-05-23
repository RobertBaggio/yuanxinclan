package com.yuanxin.clan.mvp.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.TextView;


/**
 * @author lch
 *         date 2016/1/13.
 */
public class DrawableClickableTextView extends TextView {

    private DrawableClickListener drawableClickListener;

    public DrawableClickableTextView(Context context) {
        super(context);
    }

    public DrawableClickableTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableClickableTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DrawableClickableTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            Drawable[] drawables = getCompoundDrawables();

            Drawable drawable = drawables[0];
            rect.right = rect.left + Math.abs(drawable == null ? 0 : drawable.getIntrinsicWidth()) + getCompoundPaddingLeft();
            if (rect.contains(eventX, eventY)) {
                if (drawableClickListener != null) {
                    drawableClickListener.onDrawableClick(Gravity.START, this);
                }
            }

            getGlobalVisibleRect(rect);
            drawable = drawables[2];
            rect.left = rect.right - Math.abs(drawable == null ? 0 : drawable.getIntrinsicWidth()) - getCompoundPaddingRight();
            if (rect.contains(eventX, eventY)) {
                if (drawableClickListener != null) {
                    drawableClickListener.onDrawableClick(Gravity.END, this);
                }
            }

        }

        return super.onTouchEvent(event);
    }

    public void setDrawableClickListener(DrawableClickListener drawableClickListener) {
        this.drawableClickListener = drawableClickListener;
    }

    public interface DrawableClickListener {
        void onDrawableClick(int gravity, DrawableClickableTextView drawableClickableEditText);
    }
}

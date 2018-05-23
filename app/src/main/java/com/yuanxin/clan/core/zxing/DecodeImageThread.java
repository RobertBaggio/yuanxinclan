package com.yuanxin.clan.core.zxing;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.google.zxing.Result;

/**
 * Created by xingli on 1/4/16.
 *
 * 解析图像二维码线程
 */
public class DecodeImageThread implements Runnable {
    private static final int MAX_PICTURE_PIXEL = 256;
    private byte[] mData;
    private int mWidth;
    private int mHeight;
    private String mImgPath;
    private DecodeImageCallback mCallback;
    private Context mContext;

    public DecodeImageThread(Context context,String imgPath, DecodeImageCallback callback) {
        this.mImgPath = imgPath;
        this.mCallback = callback;
        this.mContext = context;
    }

    @Override
    public void run() {
        if (null == mData) {
            if (!TextUtils.isEmpty(mImgPath)) {
//                Bitmap bitmap = QrUtils.decodeSampledBitmapFromFile(mImgPath, MAX_PICTURE_PIXEL, MAX_PICTURE_PIXEL);
                try {
                    Glide.with(mContext)
                            .load(mImgPath)
                            .asBitmap() //必须
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                                    mData = QrUtils.getYUV420sp(resource.getWidth(), resource.getHeight(), resource);
                                    mWidth = resource.getWidth();
                                    mHeight = resource.getHeight();
                                    if (mData == null || mData.length == 0 || mWidth == 0 || mHeight == 0) {
                                        if (null != mCallback) {
                                            mCallback.decodeFail(0, "无法获取图片数据");
                                        }
                                        return;
                                    }

                                    final Result result = QrUtils.decodeImage(mData, mWidth, mHeight);

                                    if (null != mCallback) {
                                        if (null != result) {
                                            mCallback.decodeSucceed(result);
                                        } else {
                                            mCallback.decodeFail(0, "无法识别二维码");
                                        }
                                    }
                                }
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

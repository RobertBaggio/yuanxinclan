package com.yuanxin.clan.mvp.manager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.github.florent37.glidepalette.GlidePalette;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.CropCircleTransformation;
import com.yuanxin.clan.core.util.RoundedCornersTransformation;

import java.io.File;

/**
 * 本地及远程服务器的图片管理
 *
 * @author lch
 *         date 2016/6/20.
 */
public class ImageManager {

    private static ImageManager instance;

    private ImageManager() {

    }

    public static ImageManager getDefault() {
        if (instance == null) {
            synchronized (ImageManager.class) {
                instance = new ImageManager();
            }
            return instance;
        }
        return instance;
    }

    /*
        加载指定URL的图片并设置到targetView
     */
    public static void load(Context context, String url, ImageView targetView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(targetView);
    }

    /*
        加载指定URL的图片并设置到targetView, 取主色调palete
     */
    public static void loadAndPalette(Context context, String url, ImageView targetView, TextView textView) {
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(GlidePalette.with(url)
                        .use(GlidePalette.Profile.VIBRANT)
                        .intoBackground(textView, GlidePalette.Swatch.RGB)
                        .intoTextColor(textView, GlidePalette.Swatch.BODY_TEXT_COLOR)
                        .crossfade(true)
                )
         .into(targetView);
    }

    /*
        加载指定URL的图片并设置到targetView
     */
    public static void loadAsGif(Context context, String url, ImageView targetView) {
        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(targetView);
    }

    /*
        加载指定URL的图片并设置到targetView
     */
    public static void loadDefault(Context context, String url, ImageView targetView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(R.drawable.list_img).into(targetView);
    }


    /*
        加载指定URL的图片并设置到targetView
     */
    public static void loadBitmap(Context context, String url, ImageView targetView) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(targetView);
    }

    /*
         初始化占位图，加载指定URL的图片并设置到targetView
      */
    public static void loadBitmap(Context context, String url, int placeHolder, ImageView targetView) {
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(placeHolder).into(targetView);
    }

    /*
      初始化占位图，加载指定URL的图片并设置到targetView
   */
    public static void load(Context context, String url, int placeHolder, ImageView targetView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeHolder).into(targetView);
    }

    /*
      初始化占位图，加载指定URL的图片并设置到targetView
   */
    public static void loadRoundCornerImage(Context context, String url, int placeHolder, ImageView targetView) {
        Glide.with(context).load(url.replace("https", "http").startsWith("http")? url : Url.img_domain + url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeHolder).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 10, 10, RoundedCornersTransformation.CornerType.ALL)).into(targetView);
    }

    /*
      初始化占位图，加载指定URL的图片并设置到targetView
   */
    public static void loadRoundCornerImageWithMargin(Context context, String url, int placeHolder, ImageView targetView, int margin) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeHolder).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 10, margin, RoundedCornersTransformation.CornerType.ALL)).into(targetView);
    }

    /*
      初始化占位图，加载指定URL的图片并设置到targetView
      圆头像
   */
    public static void loadCircleImage(Context context, String url, int placeHolder, ImageView targetView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeHolder).bitmapTransform(new CenterCrop(context), new CropCircleTransformation(context)).into(targetView);
    }

    /*
      初始化占位图，加载指定URL的图片并设置到targetView
   */
    public static void load(Context context, String url, Drawable placeHolder, ImageView targetView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeHolder).into(targetView);
    }

    /*
      初始化占位图，加载指定URL的图片并设置到targetView
   */
    public static void loadRoundCornerImage(Context context, String url, Drawable placeHolder, ImageView targetView) {
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).bitmapTransform(new CenterCrop(context), new RoundedCornersTransformation(context, 10, 10, RoundedCornersTransformation.CornerType.ALL)).into(targetView);
    }

    /*
      初始化占位图，加载指定文件的图片并设置到targetView
   */
    public static void load(Context context, File file, int placeHolder, ImageView targetView) {
        Glide.with(context).load(file).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeHolder).into(targetView);
    }

    /*
      初始化占位图，加载指定文件的图片并设置到targetView
   */
    public static void loadFilePath(Context context, String filePeth, int placeHolder, ImageView targetView) {
        Glide.with(context).load(filePeth).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(placeHolder).into(targetView);
    }
}

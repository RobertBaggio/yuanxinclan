package com.yuanxin.clan.core.news.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.news.bean.NewsBanner;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子  轮播图点击
 */
public class NetworkImageHolderNewsBannerView extends NetworkImageHolderBannerView {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, int position, final NewsBanner data) {
        try{
            ImageManager.load(context, data.getNewsCarouselImg(), imageView);
            final String link = data.getNewsCarouselLink().replace("appFlg=0", "appFlg=1");
            if (TextUtil.isEmpty(link)){
                return;
            }
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(new Intent(context, HomeADactivity.class).putExtra("url", link));
                }
            });//        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

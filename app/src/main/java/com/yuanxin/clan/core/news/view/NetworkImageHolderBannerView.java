package com.yuanxin.clan.core.news.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.yuanxin.clan.core.activity.BusinessDetailWebActivity;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.news.bean.NewsBanner;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

/**
 * Created by Sai on 15/8/4.
 * 网络图片加载例子  轮播图点击
 */
public class NetworkImageHolderBannerView implements Holder<NewsBanner> {
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
        final String epId = TextUtil.URLRequest(link).get("param");
//        if (!TextUtil.isEmpty(data.getAdvertisementLink())&&!TextUtil.isEmpty(epId)) {
//            "http://www.yxtribe.com/yuanxinbuluo/weixin/getJsp?url=wechatweb/business-style-five¶m=501&appFlg=0",
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int typeid = data.getNewsTypeId();
                    switch (typeid) {
                        case 3:
                            Intent intent = new Intent(context, CompanyDetailWebActivity.class);//有个聊天的标志
                            intent.putExtra("epId", Integer.valueOf(epId));
                            intent.putExtra("url", link);
                            context.startActivity(intent);
                            break;
                        case 4:
                            context.startActivity(new Intent(context, BusinessDetailWebActivity.class).putExtra("url", link));
                            break;
                        case 5:
                        case 1:
                        default:
                            context.startActivity(new Intent(context, HomeADactivity.class).putExtra("url", link));
                                break;
                    }

                }
            });
//        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

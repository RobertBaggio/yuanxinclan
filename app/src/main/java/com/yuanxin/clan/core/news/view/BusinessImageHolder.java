package com.yuanxin.clan.core.news.view;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/9/26 0026 18:19
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class BusinessImageHolder implements Holder<AdvertisementsEntity> {
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        //你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(final Context context, int position, final AdvertisementsEntity data) {
        try{
            ImageManager.load(context, data.getAdvertisementImg(), imageView);
            final String link = data.getAdvertisementLink().replace("appFlg=0", "appFlg=1");
            if (TextUtil.isEmpty(link)){
                return;
            }
            final String epId = TextUtil.URLRequest(link).get("param");
//        if (!TextUtil.isEmpty(data.getAdvertisementLink())&&!TextUtil.isEmpty(epId)) {
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int typeid = data.getAdvertisementType();
//                    Log.v("lgq","type=-=-==="+typeid+"...."+link);
//                    if (typeid==3){
//                        Intent intent = new Intent(context, CompanyDetailWebActivity.class);//有个聊天的标志
//                        intent.putExtra("epId", Integer.valueOf(epId));
//                        intent.putExtra("url", link);
//                        context.startActivity(intent);
//                    }else if (typeid==4){
////                        context.startActivity(new Intent(context, BusinessCrowdFoundingWebActivity.class).putExtra("url", link));
//                        context.startActivity(new Intent(context, BusinessDetailWebActivity.class).putExtra("url", link));
//                    }else if (typeid==5){
//                        context.startActivity(new Intent(context, HomeADactivity.class).putExtra("url", link));
////                        context.startActivity(new Intent(context, YxServiceActivity.class).putExtra("url", link));
//                    }else if (typeid==1){
//                        context.startActivity(new Intent(context, HomeADactivity.class).putExtra("url", link));
//                    }
//
//                }
//            });
//        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

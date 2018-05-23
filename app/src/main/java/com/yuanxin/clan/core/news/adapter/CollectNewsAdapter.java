package com.yuanxin.clan.core.news.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.news.bean.NewEntity;
import com.yuanxin.clan.core.news.bean.NewsBanner;
import com.yuanxin.clan.core.news.view.NetworkImageHolderBannerView;
import com.yuanxin.clan.core.news.view.NetworkImageHolderNewsBannerView;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe: 资讯页面适配器
 * Author: xjc
 * Date: 2017/6/20 0020 14:35
 * Date: 2017/6/20 0020 14:35
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class CollectNewsAdapter extends BaseMultiItemQuickAdapter<NewEntity, BaseViewHolder> {
    public CollectNewsAdapter(@Nullable List<NewEntity> data) {
        super(data);
        addItemType(NewEntity.style_0, R.layout.item_news_0);
        addItemType(NewEntity.style_1, R.layout.item_news_1);
        addItemType(NewEntity.style_2, R.layout.item_news_2);
    }

    @Override
    protected void convert(BaseViewHolder helper, NewEntity item) {
        switch (helper.getItemViewType()) {
            case NewEntity.style_0:
                initConvenientBanner((ConvenientBanner) helper.getView(R.id.bannerNews), item.getBanner());
                break;
            case NewEntity.style_1:
            case NewEntity.style_2:
//                helper.setImageUrl(R.id.iv, item.getContent());
                ImageManager.loadRoundCornerImageWithMargin(mContext, item.getNews().getImg(), R.drawable.list_img, (ImageView) helper.getView(R.id.item_gank_image), 0);
                helper.setText(R.id.item_gank_desc, item.getNews().getTitle());//标题
//        helper.setText(R.id.item_gank_who, TextUtil.isEmpty(item.getEpShortNm())?item.getEpNm():item.getEpShortNm());//类型
                if (TextUtil.isEmpty(item.getEpShortNm())) {
                    helper.setVisible(R.id.item_gank_who, false);
                } else {
                    helper.setVisible(R.id.item_gank_who, true);
                    helper.setText(R.id.item_gank_who, item.getEpShortNm());//企业名
                }
                helper.setText(R.id.item_read, "阅读" + String.valueOf(item.getBrowse() + item.getRandomBrowse()));
                helper.setVisible(R.id.item_read,false);
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                String thistime = df.format(new Date());
                String time = DateDistance.getDistanceTime(item.getNews().getCreateDt(),thistime);
//                    try{
                        helper.setText(R.id.item_gank_createdAt, DateDistance.getDistanceTime(item.getNews().getCreateDt(),thistime));
//                    }catch(Exception e){
//                        e.printStackTrace();
//                    }
//            helper.setText(R.id.item_gank_createdAt, DateFormatUtil.fromTheTimeNow(data.getnCreateTime()));//创建时间

                break;
        }
    }

    private void initConvenientBanner(ConvenientBanner cb, final List<NewsBanner> list) {
        CBViewHolderCreator cv = new CBViewHolderCreator<NetworkImageHolderBannerView>() {
            public NetworkImageHolderBannerView createHolder() {
                return new NetworkImageHolderNewsBannerView();
            }
        };
        cb.setPages(
                cv, list)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.banner_iocn_pre, R.drawable.banner_iocn_nomal})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        cb.setManualPageable(true);//设置不能手动影响
        cb.setCanLoop(false);
        cb.startTurning(5000);
    }
}

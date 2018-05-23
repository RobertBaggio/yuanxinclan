package com.yuanxin.clan.core.news.adapter;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.news.bean.businessArea;
import com.yuanxin.clan.core.util.TextUtil;

import java.util.List;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/10 0010 19:27
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class PersionAdapter {
//}

public class PersionAdapter extends BaseQuickAdapter<businessArea, BaseViewHolder> {
    public PersionAdapter(@Nullable List<businessArea> data) {
        super(R.layout.itempersionla, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, businessArea item) {
        Log.v("lgq","gerzhiliao...."+item.getBusinessAreaNm());
        if (TextUtil.isEmpty(item.getBusinessAreaNm())){
            return;
        }
//        ImageManager.load(mContext, item.getImg(), R.drawable.list_img, (ImageView) helper.getView(R.id.item_gank_image));
        helper.setText(R.id.tvUsterEp, item.getBusinessAreaNm());//标题
        String baPosition = item.getBaPosition();
        if (TextUtil.isEmpty(baPosition)|| baPosition.equals("null")) {
            baPosition = "";
        }
        helper.setText(R.id.tvUrserZw, baPosition);//类型
//        if (item.getCreateDt() == null) {
//            helper.setText(R.id.item_gank_createdAt, null);
//        } else {
//            helper.setText(R.id.item_gank_createdAt, TimeUtil.timeFormat(item.getCreateDt()));//创建时间
//        }
    }
}

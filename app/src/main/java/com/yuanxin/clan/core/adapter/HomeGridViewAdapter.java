package com.yuanxin.clan.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.GongXuEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/8 0008 16:49
 */

public class HomeGridViewAdapter extends BaseAdapter {
    private List<GongXuEntity> dataList;

    public HomeGridViewAdapter(List<GongXuEntity> datas, int page) {
        dataList = new ArrayList<>();
        //start end分别代表要显示的数组在总数据List中的开始和结束位置
        int start = page * 5;
        int end = start + 5;
        while ((start < datas.size()) && (start < end)) {
            dataList.add(datas.get(start));
            start++;
        }
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View itemView, ViewGroup viewGroup) {
        ViewHolder mHolder;
        if (itemView == null) {
            mHolder = new ViewHolder();
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_home_gongxu, viewGroup, false);
            mHolder.titlete = (TextView) itemView.findViewById(R.id.item_gxname);
            mHolder.typete = (TextView) itemView.findViewById(R.id.item_gongxutype);
            mHolder.addresste = (TextView) itemView.findViewById(R.id.item_gongxuaddress);
            itemView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) itemView.getTag();
        }
        GongXuEntity bean = dataList.get(i);
        if (bean != null) {
//            ImageManager.load(viewGroup.getContext(), bean.getImage(), mHolder.iv_img);
            String ty ="";
            if (bean.getSupplyDemand().equals("0")){
                ty="供应";
            }else {
                ty="需求";
            }
            mHolder.titlete.setText(bean.getContent());
            mHolder.typete.setText(ty);
            mHolder.addresste.setText(bean.getCity());
        }
        return itemView;
    }

    private class ViewHolder {
        private TextView titlete;
        private TextView addresste;
        private TextView typete;
    }
}

package com.yuanxin.clan.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.HomeSostypeEntity;

import java.util.List;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/9 0009 15:37
 */

public class HomeSos_Adapter extends BaseAdapter {
    private List<HomeSostypeEntity> dataList;

    public HomeSos_Adapter(List<HomeSostypeEntity> datas) {
        dataList = datas;
        //start end分别代表要显示的数组在总数据List中的开始和结束位置
//        int start = page * TestActivity.item_grid_num;
//        int end = start + TestActivity.item_grid_num;
//        while ((start < datas.size()) && (start < end)) {
//            dataList.add(datas.get(start));
//            start++;
//        }
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
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homesos, viewGroup, false);
            mHolder.namete = (TextView) itemView.findViewById(R.id.soste);
            itemView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) itemView.getTag();
        }
        HomeSostypeEntity bean = dataList.get(i);
        if (bean != null) {
            mHolder.namete.setText(bean.getName());
        }
        return itemView;
    }

    private class ViewHolder {
        private TextView namete;
    }
}

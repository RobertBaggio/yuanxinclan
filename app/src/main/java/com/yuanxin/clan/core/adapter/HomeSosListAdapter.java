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
 * Date: 2018/1/9 0009 16:39
 */

public class HomeSosListAdapter extends BaseAdapter {
    private List<HomeSostypeEntity> dataList;

    public HomeSosListAdapter(List<HomeSostypeEntity> datas) {
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
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_homelistsos, viewGroup, false);
            mHolder.namete = (TextView) itemView.findViewById(R.id.sosname);
            mHolder.typete = (TextView) itemView.findViewById(R.id.sostype);
            itemView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) itemView.getTag();
        }
        HomeSostypeEntity bean = dataList.get(i);
        if (bean != null) {
            mHolder.namete.setText(bean.getName());
            String ty="";
            if (bean.getType().equals("1")){
                ty="服务";
            }else if (bean.getType().equals("2")){
                ty="企业";
            }else if (bean.getType().equals("3")){
                ty="资讯";
            } else if (bean.getType().equals("4")){
                ty="会展";
            }else if (bean.getType().equals("5")){
                ty="商圈";
            } else {
                mHolder.typete.setVisibility(View.GONE);
            }
            mHolder.typete.setText(ty);
        }
        return itemView;
    }

    private class ViewHolder {
        private TextView namete;
        private TextView typete;
    }
}

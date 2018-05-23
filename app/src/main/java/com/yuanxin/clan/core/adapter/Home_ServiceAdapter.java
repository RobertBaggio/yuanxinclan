package com.yuanxin.clan.core.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.SmallServiceEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/8 0008 19:53
 */

public class Home_ServiceAdapter extends BaseAdapter {
    private List<SmallServiceEntity> dataList;

    public Home_ServiceAdapter(List<SmallServiceEntity> datas) {
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
            itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_service_home, viewGroup, false);
            mHolder.iv_img = (ImageView) itemView.findViewById(R.id.serviecimage);
            mHolder.name = (TextView) itemView.findViewById(R.id.servicename);
            mHolder.price = (TextView) itemView.findViewById(R.id.serviceprice);
            itemView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) itemView.getTag();
        }
        SmallServiceEntity bean = dataList.get(i);
        if (bean != null) {
            ImageManager.load(viewGroup.getContext(), bean.getImage(), mHolder.iv_img);

            mHolder.name.setText(bean.getName());
            mHolder.price.setText(bean.getPrice());
        }
        return itemView;
    }

    private class ViewHolder {
        private ImageView iv_img;
        private TextView name;
        private TextView price;
    }
}

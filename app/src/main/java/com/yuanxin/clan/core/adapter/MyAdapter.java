package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.Bean;

import java.util.ArrayList;

/**
 * Created by lenovo1 on 2017/4/1.
 */
public class MyAdapter extends BaseAdapter {
    private Context context;
    ArrayList<Bean> list;
    private LayoutInflater mInflater;// 得到一个LayoutInfalter对象用来导入布局

    public MyAdapter(ArrayList<Bean> list, Context context) {
        super();
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.gridview_item, null);
            /** 得到各个控件的对象 */
            holder.title = (TextView) convertView
                    .findViewById(R.id.ItemText);//文本
            holder.layout = (LinearLayout) convertView
                    .findViewById(R.id.layout);
            convertView.setTag(holder);// 绑定ViewHolder对象
        } else {
            holder = (ViewHolder) convertView.getTag();// 取出ViewHolder对象
        }
        /** 设置TextView显示的内容，即我们存放在动态数组中的数据 */
        holder.title.setText(list.get(position).getName());//文本子项赋值
        // 点击改变选中listItem的背景色
        if (list.get(position).getNameIsSelect()) {// 第几项是不是选中
            holder.layout.setBackgroundResource(R.drawable.shape2);
        } else {
            holder.layout.setBackgroundResource(R.drawable.shape1);
        }
        return convertView;
    }

    public final class ViewHolder {
        public TextView title;
        public LinearLayout layout;
    }
}

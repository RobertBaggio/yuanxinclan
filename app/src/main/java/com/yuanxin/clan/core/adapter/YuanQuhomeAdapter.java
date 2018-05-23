package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.AdvertisementsEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/8 0008 14:11
 */

public class YuanQuhomeAdapter extends ArrayAdapter<AdvertisementsEntity> {
    private LayoutInflater mInflater;

    public YuanQuhomeAdapter(Context context, List<AdvertisementsEntity> values) {
        super(context, R.layout.yuanquhome_item, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.yuanquhome_item, parent, false);
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.jxxhimage);
            holder.textView = (TextView) convertView.findViewById(R.id.jxxhname);
            holder.city = (TextView) convertView.findViewById(R.id.city);
            holder.form = (TextView) convertView.findViewById(R.id.form);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // 标题
        holder.textView.setText(getItem(position).getAdvertisementNm());
        ImageManager.loadCircleImage(getContext(), getItem(position).getAdvertisementImg(), R.drawable.list_img, holder.imageView);
        holder.city.setText(getItem(position).getCity());
        holder.form.setText(getItem(position).getAdvertisementFrom());
        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public ImageView imageView;
        public TextView textView;
        public TextView city;
        public TextView form;
    }
}

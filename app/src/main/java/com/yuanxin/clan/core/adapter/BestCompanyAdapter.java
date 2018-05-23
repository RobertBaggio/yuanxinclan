package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.bean.BestCompanyEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/8 0008 14:11
 */

public class BestCompanyAdapter extends ArrayAdapter<BestCompanyEntity> {
    private LayoutInflater mInflater;

    public BestCompanyAdapter(Context context, List<BestCompanyEntity> values) {
        super(context, R.layout.best_company_item, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.best_company_item, parent, false);
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.logoImg);
            holder.logoMini = (ImageView) convertView.findViewById(R.id.logoMini);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.label = (TextView) convertView.findViewById(R.id.label);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // 标题
        holder.title.setText(getItem(position).getEpNm());
        ImageManager.load(getContext(), Url.img_domain + getItem(position).getEpImage1(), R.drawable.list_img, holder.imageView);
        ImageManager.load(getContext(), Url.img_domain + getItem(position).getEpImage4(), R.drawable.list_img, holder.logoMini);
        holder.label.setText(getItem(position).getLabel());
        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public ImageView imageView;
        public ImageView logoMini;
        public TextView title;
        public TextView label;
    }
}

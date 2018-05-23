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

/** An array adapter that knows how to render views when given CustomData classes */
public class HomePageCompanyAdapter extends ArrayAdapter<AdvertisementsEntity> {
    private LayoutInflater mInflater;

    public HomePageCompanyAdapter(Context context, List<AdvertisementsEntity> values) {
        super(context, R.layout.homepage_company_data_view, values);
        mInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            // Inflate the view since it does not exist
            convertView = mInflater.inflate(R.layout.homepage_company_data_view, parent, false);

            // Create and save off the holder in the tag so we get quick access to inner fields
            // This must be done for performance reasons
            holder = new Holder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.textView = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        // Populate the text
        holder.textView.setText(getItem(position).getAdvertisementNm());
        ImageManager.load(getContext(), getItem(position).getAdvertisementImg(), holder.imageView);
        return convertView;
    }

    /** View holder for the views we need access to */
    private static class Holder {
        public ImageView imageView;
        public TextView textView;
    }
}

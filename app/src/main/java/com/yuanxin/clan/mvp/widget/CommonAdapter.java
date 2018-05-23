package com.yuanxin.clan.mvp.widget;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yuanxin.clan.mvp.utils.UIUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * @author lch
 *         date 2016/2/25.
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    protected List<T> mDataList;
    protected WeakReference<Context> contextRef;
    private int layoutResourceId;

    public CommonAdapter(List<T> mDataList, Context context, int layoutResourceId) {
        this.mDataList = mDataList;
        this.contextRef = new WeakReference<Context>(context);
        this.layoutResourceId = layoutResourceId;
    }

    @Override
    public int getCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public T getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.getViewHolder(convertView, layoutResourceId, parent, position);
        bindData(holder, getItem(position), contextRef.get());
        return holder.getConvertView();
    }

    /**
     * @param viewHolder 保存view
     * @param dataVo
     */
    protected abstract void bindData(ViewHolder viewHolder, T dataVo, Context context);

    public void setDataList(List<T> dataList) {
        this.mDataList = dataList;
    }

    protected static class ViewHolder {
        private int layoutResourceId;
        private int position;
        SparseArray<View> mViews;
        View convertView;

        public ViewHolder(int layoutResourceId, View convertView, int position) {
            this.layoutResourceId = layoutResourceId;
            this.position = position;
            this.convertView = convertView;
            convertView.setTag(layoutResourceId, this);
            mViews = new SparseArray<>();
        }

        public static ViewHolder getViewHolder(View convertView, int layoutResourceId, ViewGroup parent, int position) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = UIUtils.inflate(layoutResourceId, parent);
//                AutoUtils.autoSize(convertView);
                viewHolder = new ViewHolder(layoutResourceId, convertView, position);
            } else {
                viewHolder = (ViewHolder) convertView.getTag(layoutResourceId);
                viewHolder.setPosition(position);
            }

            return viewHolder;
        }

        public <T extends View> T getViewById(int viewId) {
            View view = mViews.get(viewId);
            if (view == null) {
                view = convertView.findViewById(viewId);
                mViews.put(viewId, view);
            }

            return (T) view;
        }

        public View getConvertView() {
            return convertView;
        }

        public ViewHolder setText(int viewId, String content) {
            TextView textView = getViewById(viewId);
            textView.setText(content);
            textView.setTag(null);
            return this;
        }

        public ViewHolder setImageResource(int viewId, Context context, String url) {
            ImageView imageView = getViewById(viewId);
            Glide.with(context).load(url).into(imageView);
            return this;
        }

        public ViewHolder setImageResource(int viewId, int resourceId) {
            ImageView imageView = getViewById(viewId);
            imageView.setImageResource(resourceId);
            return this;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}

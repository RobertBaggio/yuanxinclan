package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

/**
 * Created by lenovo1 on 2017/3/4.
 * 无用
 */
public class ViewFlowerNoAdapter extends BaseAdapter {

    private Context context;
    private List<String> imageIdList;
    private List<String> linkUrlArray;
    private int size;
    private boolean isInfiniteLoop;
//	private ImageLoader imageLoader;
//	private DisplayImageOptions options;

    public ViewFlowerNoAdapter(Context context, List<String> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        if (imageIdList != null) {
            this.size = imageIdList.size();
        }
        isInfiniteLoop = false;


    }

    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE : imageIdList.size();
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new ImageView(context);
            holder.imageView
                    .setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
            holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        ImageManager.load(context, imageIdList.get(getPosition(position)), R.drawable.index_image_banner03, holder.imageView);
////		imageLoader.displayImage(imageIdList.get(getPosition(position)),
//				holder.imageView, options);

//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                String url = linkUrlArray.get(ViewflowAdapter.this
//                        .getPosition(position));
////				String title = urlTitlesList.get(ImagePagerAdapter.this
////						.getPosition(position));
//				/*
//				 * if (TextUtils.isEmpty(url)) {
//				 * holder.imageView.setEnabled(false); return; }
//				 */
//                Bundle bundle = new Bundle();
//
//                bundle.putString("url", url);
////				bundle.putString("title", title);
//                Intent intent = new Intent(context, BaseWebActivity.class);
//                intent.putExtras(bundle);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//
////				Intent intent=new Intent(context, BaseWebActivity.class);
////				intent.putExtra("url",url);
////				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////				context.startActivity(intent);
//
////				Toast.makeText(context, "点击了第" + getPosition(position) + "张图片",
////						0).show();
//
//            }
//        });

        return view;
    }

    private static class ViewHolder {

        ImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ViewFlowerNoAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

}

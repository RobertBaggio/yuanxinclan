package com.yuanxin.clan.core.market.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.market.bean.NewMarketitem;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/3/23.
 */
public class YuanXinFairAdapter extends RecyclerView.Adapter<YuanXinFairAdapter.ViewHolder> {

    private List<NewMarketitem> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;

    public YuanXinFairAdapter(Context context, List<NewMarketitem> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newfairlayout, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        NewMarketitem entity = entityList.get(position);
//        Log.v("lgq","..........."+entity.getMarketImg());
        ImageManager.load(context, entity.getMarketImg(), R.drawable.banner01, holder.mItemYuanXinFairNewImage);
//        ImageManager.load(context, "http://images.yxtribe.com//upload/images/market/20170701145124172.jpg-style_webp_640x640", R.drawable.banner01, holder.mItemYuanXinFairNewImage);
        try{
//            Log.v("lgq","....555..."+entity.getMimage1());
        if (!TextUtil.isEmpty(entity.getMimage1())&&!entity.getMimage1().equals("null")){
            ImageManager.load(context, entity.getMimage1(), holder.item_yuan_xin_fair_new_namerei1);
        }
            if (!TextUtil.isEmpty(entity.getMimage2())&&!entity.getMimage2().equals("null")){
                ImageManager.load(context, entity.getMimage2(), holder.item_yuan_xin_fair_new_namerei2);
            }
            if (!TextUtil.isEmpty(entity.getMimage3())&&!entity.getMimage3().equals("null")){
                ImageManager.load(context, entity.getMimage3(), holder.item_yuan_xin_fair_new_namerei3);
            }
            if (!TextUtil.isEmpty(entity.getMimage4())&&!entity.getMimage4().equals("null")){
                ImageManager.load(context, entity.getMimage4(), holder.item_yuan_xin_fair_new_namerei4);
            }

        String type = entity.getMarketTypes();
        String[] aa = type.split(",");
            if (!TextUtil.isEmpty(aa[0])){
                holder.item_yuan_xin_fair_new_namere1.setText(aa[0]);
                holder.item_yuan_xin_fair_new_namere1.setVisibility(View.VISIBLE);
            }
        if (!TextUtil.isEmpty(aa[1])){
            holder.item_yuan_xin_fair_new_namere2.setText(aa[1]);
            holder.item_yuan_xin_fair_new_namere2.setVisibility(View.VISIBLE);
        }
        if (!TextUtil.isEmpty(aa[2])){
            holder.item_yuan_xin_fair_new_namere3.setText(aa[2]);
            holder.item_yuan_xin_fair_new_namere3.setVisibility(View.VISIBLE);
        }
        if (!TextUtil.isEmpty(aa[3])){
            holder.item_yuan_xin_fair_new_namere4.setText(aa[3]);
            holder.item_yuan_xin_fair_new_namere4.setVisibility(View.VISIBLE);
        }
        if (!TextUtil.isEmpty(aa[4])){
            holder.item_yuan_xin_fair_new_namere5.setText(aa[4]);
            holder.item_yuan_xin_fair_new_namere5.setVisibility(View.VISIBLE);
        }
        if (!TextUtil.isEmpty(aa[5])){
            holder.item_yuan_xin_fair_new_namere6.setText(aa[5]);
            holder.item_yuan_xin_fair_new_namere6.setVisibility(View.VISIBLE);
        }

        }catch (Exception e){
            e.printStackTrace();
        }
//        Log.v("lgq","tp===="+entity.getMarketImg());
//        holder.mItemYuanXinFairNewName.setText(TextUtil.formatString(entity.getMarketNm()));
//        holder.mItemYuanXinFairNewArea.setText(TextUtil.formatString(entity.getCity()));
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_yuan_xin_fair_new_image)
        ImageView mItemYuanXinFairNewImage;
        @BindView(R.id.item_yuan_xin_fair_new_namere1)
        TextView item_yuan_xin_fair_new_namere1;
        @BindView(R.id.item_yuan_xin_fair_new_namere2)
        TextView item_yuan_xin_fair_new_namere2;
        @BindView(R.id.item_yuan_xin_fair_new_namere3)
        TextView item_yuan_xin_fair_new_namere3;
        @BindView(R.id.item_yuan_xin_fair_new_namere4)
        TextView item_yuan_xin_fair_new_namere4;
        @BindView(R.id.item_yuan_xin_fair_new_namere5)
        TextView item_yuan_xin_fair_new_namere5;
        @BindView(R.id.item_yuan_xin_fair_new_namere6)
        TextView item_yuan_xin_fair_new_namere6;
        @BindView(R.id.item_yuan_xin_fair_new_namerei1)
        ImageView item_yuan_xin_fair_new_namerei1;
        @BindView(R.id.item_yuan_xin_fair_new_namerei2)
        ImageView item_yuan_xin_fair_new_namerei2;
        @BindView(R.id.item_yuan_xin_fair_new_namerei3)
        ImageView item_yuan_xin_fair_new_namerei3;
        @BindView(R.id.item_yuan_xin_fair_new_namerei4)
        ImageView item_yuan_xin_fair_new_namerei4;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}

package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.PresentCustomMadeEntity;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/15.
 */

public class FragmentMyMarketNeedAdapter extends RecyclerView.Adapter<FragmentMyMarketNeedAdapter.ViewHolder> {
    private List<PresentCustomMadeEntity> entityList;
    private Context context;
    private FragmentMyMarketNeedAdapter.OnItemClickListener mOnItemClickListener;
    private String state;

    public FragmentMyMarketNeedAdapter(Context context, List<PresentCustomMadeEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_my_market_need, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PresentCustomMadeEntity entity = entityList.get(position);
        ImageManager.loadBitmap(context, entity.getImage(), R.drawable.by, holder.itemPresentCustomMadeImage);//图片
        holder.itemPresentCustomMadeCommodity.setText(entity.getName());//商品
        state = "审核中";
        switch (entity.getState()) {
            case 1:
                state = "审核通过";
                break;
            case 2:
                state = "审核失败";
                break;
            case 3:
                state = "审核中";
                break;
        }
        holder.itemPresentCustomMadeState.setText(state);//审核中
//        holder.itemPresentCustomMadeState.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context,EditPresentCustomMadeActivity.class);
//                intent.putExtra("epId",entity.getEpId());//企业id
//                intent.putExtra("commodityId",entity.getCommodityId());//商品id
//                context.startActivity(intent);
//            }
//        });
//        holder.itemYuanXinFairIntroduce.setText(entity.getIntroduce());
        holder.itemPresentCustomMadeMoney.setText(entity.getMoney());//价格
//        holder.itemPresentCustomMadeStartTime.setText(entity.getStartTime());//开始
//        holder.itemPresentCustomMadeEndTime.setText(entity.getEndTime());//结束
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

    static

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_present_custom_made_image)
        MLImageView itemPresentCustomMadeImage;
        @BindView(R.id.item_present_custom_made_commodity)
        TextView itemPresentCustomMadeCommodity;
        @BindView(R.id.item_present_custom_made_state)
        TextView itemPresentCustomMadeState;
        @BindView(R.id.item_present_custom_made_money)
        TextView itemPresentCustomMadeMoney;
        @BindView(R.id.item_present_custom_made_layout)
        LinearLayout itemPresentCustomMadeLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(FragmentMyMarketNeedAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}

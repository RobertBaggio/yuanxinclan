package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.CrowChatEntity;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/5/12.
 */
public class CrowChatAdapter extends RecyclerView.Adapter<CrowChatAdapter.ViewHolder> {
    private List<CrowChatEntity> entityList;
    private Context context;
    private CrowChatAdapter.OnItemClickListener mOnItemClickListener;

    public CrowChatAdapter(Context context, List<CrowChatEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    public List<CrowChatEntity> getEntityList() {
        return entityList;
    }

    public void setEntityList(List<CrowChatEntity> entityList) {
        this.entityList = entityList;
    }

    public void setNewEntityList(List<CrowChatEntity> list) {
        if (entityList != null) {
            entityList.clear();
        }
        this.entityList.addAll(list);
    }

    public CrowChatAdapter.OnItemClickListener getmOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setmOnItemClickListener(CrowChatAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_crow_chat, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final CrowChatEntity entity = entityList.get(position);
//        //保存群聊信息
//        if (!TextUtil.isEmpty(entity.getGroupId())) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    EaseUser easeUser = DemoHelper.getInstance().getUserInfo(entity.getGroupId());
//                    if (easeUser != null) {
//                        easeUser.setAvatar(entity.getGroupImage());
//                        easeUser.setNick(entity.getGroupNm());
//                        easeUser.setNickname(entity.getGroupNm());
//                        DemoHelper.getInstance().getModel().saveContact(easeUser);
//                    }
//                }
//            }).start();
//        }
        if (!entity.getGroupImage().equals("")) {
            ImageManager.loadBitmap(context, entity.getGroupImage(), R.drawable.ease_groups_icon, holder.itemPresentCustomMadeImage);//图片
        }
        holder.itemPresentCustomMadeCommodity.setText(entity.getGroupNm());//用户
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
        @BindView(R.id.item_present_custom_made_image)
        MLImageView itemPresentCustomMadeImage;
        @BindView(R.id.item_present_custom_made_commodity)
        TextView itemPresentCustomMadeCommodity;
        @BindView(R.id.item_present_custom_made_state)
        TextView itemPresentCustomMadeState;
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

    public void setOnItemClickListener(CrowChatAdapter.OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}

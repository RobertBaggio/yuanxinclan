package com.yuanxin.clan.core.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.NewGroupsDetailActivity;
import com.yuanxin.clan.core.entity.FriendListEntity;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/10/18 0018 11:29
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

//public class NewgroupsAdapter {
//}
public class NewgroupsAdapter extends RecyclerView.Adapter<NewgroupsAdapter.ViewHolder> {
    private List<FriendListEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private OnItemClickListenerupd mOnItemClickListenerupd;
    private Boolean is_edit = false;
    private String groupId;
    private NewGroupsDetailActivity activity;

    public NewgroupsAdapter(Context context, List<FriendListEntity> list, String groupId, NewGroupsDetailActivity activity) {
        this.context = context;
        this.entityList = list;
        this.groupId = groupId;//群id
        this.activity = activity;
    }

    public void setEdit() {
        is_edit = !is_edit;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_groups_number, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final FriendListEntity entity = entityList.get(position);
        if (is_edit) {
            holder.itemInformationChooseImage.setVisibility(View.VISIBLE);
        } else {
            holder.itemInformationChooseImage.setVisibility(View.GONE);

        }
        ImageManager.loadBitmap(context, entity.getImage(), R.drawable.by, holder.itemCompanyMemberHeadImage);

        holder.itemCompanyMemberName.setText(entity.getName());//标题
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition(); // 1
                    Log.v("lgq",".........adapter........"+position);
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
            });
        }
        holder.itemInformationChooseImage.setOnClickListener(new View.OnClickListener() {//点击删除
            @Override
            public void onClick(View view) {
                if (is_edit) {
                    showNormalDialogOne(position);

                }
            }
        });
        //itemCompanyMemberHeadImage
        holder.itemCompanyMemberHeadImage.setOnClickListener(new View.OnClickListener() {//点击删除
            @Override
            public void onClick(View view) {
                if (is_edit) {
                    showNormalDialogOne(position);
//                    entityList.remove(position);
                }else {
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
                }
//                mOnMyCheckedChangeListener.onCheckedChange(deleteList);
            }
        });


    }

    //showNormalDialogOne
    private void showNormalDialogOne(final int position) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(activity);
        normalDialog.setTitle("警告");
        normalDialog.setMessage("您确定删除该企业成员吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        FriendListEntity entity = entityList.get(position);
//                        deleteCompanyMemberToWeb(entity.getPhone(), position);
                        mOnItemClickListenerupd.onItemClick(position);

                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        normalDialog.show();
    }



    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_information_choose_image)
        ImageView itemInformationChooseImage;
        @BindView(R.id.item_company_member_head_image)
        ImageView itemCompanyMemberHeadImage;
        @BindView(R.id.item_company_member_name)
        TextView itemCompanyMemberName;
        @BindView(R.id.item_company_member_layout)
        RelativeLayout itemCompanyMemberLayout;

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
    public  interface OnItemClickListenerupd {
        void onItemClick( int position);
    }
    public void setOnItemClickListener(OnItemClickListenerupd mOnItemClickListener) {
        this.mOnItemClickListenerupd = mOnItemClickListener;
    }

}

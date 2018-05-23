package com.yuanxin.clan.core.activity_groups;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.DateDistance;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.manager.ImageManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/3/26 0026 14:25
 */

public class GroupsMainAdapter extends RecyclerView.Adapter<GroupsMainAdapter.ViewHolder> {
    private List<GroupsEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private ArrayList<String> imageUrlList = new ArrayList<String>();//图片

    public GroupsMainAdapter(Context context, List<GroupsEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_huodongquan_main, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final GroupsEntity entity = entityList.get(position);
        String image = Url.img_domain + entity.getActivityImage() + Url.imageStyle640x640;//图片
        ImageManager.load(context, image, R.drawable.banner01, holder.hdqimage);
        holder.hdqnamete.setText(entity.getActivityTheme()+"（"+entity.getCity()+"）");////名称
        holder.item_hdqtiemte.setText(DateDistance.getDistanceTimeToHM(entity.getStarTime()));//地址
        if (entity.getStatus().equals("未开始")) {
            holder.joinBtn.setVisibility(View.VISIBLE);
        } else {
            holder.joinBtn.setVisibility(View.GONE);
        }
        //报名参加
        holder.joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //192.168.1.107:8080/yuanxinbuluo/weixin/getJsp?url=wechatweb/act-signup&activityId=512&appFlg=1&groupId=48262105071619
                String url = Url.urlHost + "/weixin/getJsp?url=wechatweb/act-signup&activityId=" + entity.getActivityId() + "&appFlg=1&groupId=" +  entity.getGroupId();
                if (url.startsWith("http")) {
                    context.startActivity(new Intent(context, HomeADactivity.class).putExtra("url", url));
                }
            }
        });
        holder.hdqadresste.setText(entity.getProvince() + "-" + entity.getCity() + "-" + entity.getArea());
        holder.hdqepte.setText(entity.getActivitySponsor());//收藏人数
        if (Utils.isEmpty(entity.getActivityLabel())) {
            holder.tag.setVisibility(View.GONE);
        } else {
            holder.tag.setText(entity.getActivityLabel().replace("、", "  "));
        }
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
        @BindView(R.id.hdqimage)
        ImageView hdqimage;
        @BindView(R.id.item_hdqtiemte)
        TextView item_hdqtiemte;
        @BindView(R.id.hdqadresste)
        TextView hdqadresste;
        @BindView(R.id.hdqnamete)
        TextView hdqnamete;
        @BindView(R.id.hdqepte)
        TextView hdqepte;
        @BindView(R.id.joinBtn)
        TextView joinBtn;
        @BindView(R.id.activityTag)
        TextView tag;

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

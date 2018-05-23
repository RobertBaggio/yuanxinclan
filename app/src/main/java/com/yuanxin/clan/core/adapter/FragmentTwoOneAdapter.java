package com.yuanxin.clan.core.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.FragmentTwoOneEntity;
import com.yuanxin.clan.core.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/1/24.
 */
public class FragmentTwoOneAdapter extends RecyclerView.Adapter<FragmentTwoOneAdapter.ViewHolder> {

    private List<FragmentTwoOneEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private boolean isEdit = false;
    private int num = 0;
    private boolean flag = false;
    private boolean isAllCheck = false;
    private List<String> companyInfoIdList = new ArrayList<>();//选中的数据
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;


    public FragmentTwoOneAdapter(Context context, List<FragmentTwoOneEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    public FragmentTwoOneAdapter(Context context) {
        this.context = context;
    }

    //编辑还是不编辑
    public void changeStatus() {
        if (isEdit) {
            isEdit = false;
//            num=0;
        } else {
            isEdit = true;
        }
        notifyDataSetChanged();
    }

    //全选
    public void allCheck() {
        if (isAllCheck) {
            isAllCheck = false;//不选
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).setIs_select(false);
            }
            notifyDataSetChanged();
        } else {
            isAllCheck = true;
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).setIs_select(true);
            }
            notifyDataSetChanged();
        }
    }

    public void checkAll(boolean is_checked) {//删除
        flag = true;
        if (is_checked) {//如果有打勾
            companyInfoIdList.clear();//全部清理
            for (int i = 0; i < entityList.size(); i++) {//统计 个数
//                companyInfoIdList.add(entityList.get(i).getJob_id());
            }
//            num = companyInfoIdList.size();
        } else {//如果没有打勾
            companyInfoIdList.clear();
//            num = 0;
        }
        mOnMyCheckedChangeListener.onCheckedChange(companyInfoIdList);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_fragment_two_one, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final FragmentTwoOneEntity entity = entityList.get(position);
        if (isEdit) {//编辑
            holder.itemMyCollectCheckbox.setVisibility(View.VISIBLE);//所有可见
            if (entity.is_select()) {
                holder.itemMyCollectCheckbox.setChecked(true);
            } else {
                holder.itemMyCollectCheckbox.setChecked(false);
            }
//            holder.itemMyCollectCheckbox.setChecked(entity.is_checked());//
        } else {
            holder.itemMyCollectCheckbox.setVisibility(View.GONE);
            holder.itemMyCollectCheckbox.setChecked(false);
        }
//        if(isAllCheck){
//            holder.itemMyCollectCheckbox.setChecked(true);
//        }else{
//            holder.itemMyCollectCheckbox.setChecked(false);
//        }
        holder.itemMyCollectCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {//选择框 选没选 监听
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {//状态切换控件CompoundButton及其子类CheckBox、RadioButton、ToggleButton、switch事件监听

//                if (!flag){
//                    if (b){
//                        num+=1;
//                        jobIdList.add(entity.getJob_id());
//                    }else{
//                        if(num > 0){
//                            num-=1;
//                            jobIdList.remove(entity.getJob_id());
//                        }
//                    }
//                    mOnMyCheckedChangeListener.onCheckedChange(num, jobIdList);
//                }
            }
        });
//        ImageManager.load(mContext,entity.getLogo())
//                .placeholder(R.drawable.default_image).error(R.drawable.default_image)
//                .into(holder.item_my_collect_image);
//        ImageManager.load(context,entity.getImages(),holder.itemGankImage);
        holder.itemFragmentTwoOneDesc.setText(entity.getDesc());
        holder.itemGankfragmentTwoOneType.setText(entity.getType());
        holder.itemGankfragmentTwoOneWho.setText(entity.getWho());
        holder.itemFragmentTwoOneCreatedAt.setText(TimeUtil.timeFormat(entity.getCreatedAt()));
        //判断是否设置了监听器
        if (mOnItemClickListener != null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int position = holder.getLayoutPosition(); // 1
//                    mOnItemClickListener.onItemClick(holder.itemView,position); // 2
                    if (isEdit) {//编辑
                        if (holder.itemMyCollectCheckbox.isChecked()) {//点击CheckBox选中
                            holder.itemMyCollectCheckbox.setChecked(false);//设置没有选中
//                            if(num > 0){//如果有选中
//                                num-=1;//总数-
////                                companyInfoIdList.remove(entity.getJob_id());//移除选中的
//                            }
                        } else {
                            holder.itemMyCollectCheckbox.setChecked(true);//设置选中
//                            num+=1;//总数+1
//                            companyInfoIdList.add(entity.getJob_id());
                        }
//                        mOnMyCheckedChangeListener.onCheckedChange(companyInfoIdList);//选择框 选没选 监听
                    } else {
//                        if (entity.getValidity().equals("0")){//有效性
//                            Utils.showShortToast(mContext,"该岗位已失效");
//                        }else {
                        int position = holder.getLayoutPosition(); // 1
                        mOnItemClickListener.onItemClick(holder.itemView, position); // 2
//                        }
                    }
//                    flag=false;
//                    if (isEdit){//编辑
//                        if (holder.itemMyCollectCheckbox.isChecked()){//点击CheckBox选中
//                            holder.itemMyCollectCheckbox.setChecked(false);//设置没有选中
//                            if(num > 0){//如果有选中
//                                num-=1;//总数-
////                                companyInfoIdList.remove(entity.getJob_id());//移除选中的
//                            }
//                        }else {
//                            holder.itemMyCollectCheckbox.setChecked(true);//设置选中
//                            num+=1;//总数+1
////                            companyInfoIdList.add(entity.getJob_id());
//                        }
//                        mOnMyCheckedChangeListener.onCheckedChange(num, companyInfoIdList);//选择框 选没选 监听
//                    }else {
////                        if (entity.getValidity().equals("0")){//有效性
////                            Utils.showShortToast(mContext,"该岗位已失效");
////                        }else {
//                            int position = holder.getLayoutPosition(); // 1
//                            mOnItemClickListener.onItemClick(holder.itemView,position); // 2
////                        }
//                    }

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        //        @BindView(R.id.item_fragment_two_one_image)
//        ImageView itemFragmentTwoOneImage;
        @BindView(R.id.item_fragment_two_one_desc)
        TextView itemFragmentTwoOneDesc;
        @BindView(R.id.item_gankfragment_two_one_type)
        TextView itemGankfragmentTwoOneType;
        @BindView(R.id.item_gankfragment_two_one_who)
        TextView itemGankfragmentTwoOneWho;
        @BindView(R.id.item_fragment_two_one_createdAt)
        TextView itemFragmentTwoOneCreatedAt;
        @BindView(R.id.item_my_collect_checkbox)
        CheckBox itemMyCollectCheckbox;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    //返回选中的ITEM数目
    public interface OnMyCheckedChangeListener {
        void onCheckedChange(List<String> companyInfoIdList);
    }

    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }


}

package com.yuanxin.clan.core.company.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.CompanyInfoListEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.recyclerviewhelper.ItemTouchHelperAdapter;
import com.yuanxin.clan.core.recyclerviewhelper.ItemTouchHelperViewHolder;
import com.yuanxin.clan.core.recyclerviewhelper.OnStartDragListener;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/4/23.
 * 无用
 */
//ItemTouchHelperAdapter
public class CompanyInfoListAdapter extends RecyclerView.Adapter<CompanyInfoListAdapter.ItemViewHolder> implements ItemTouchHelperAdapter {
    private List<CompanyInfoListEntity> entityList;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private boolean isEdit = false;//编辑不编辑
    private int num = 0;
    private boolean flag = false; // 标志  当收藏列表大于5条，超出屏幕时，“共2件”这里显示不准确
    private List<String> jobIdList = new ArrayList<String>();//子项id 删除的实体
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;
    private final OnStartDragListener mDragStartListener;


    public CompanyInfoListAdapter(Context context, List<CompanyInfoListEntity> list, OnStartDragListener dragStartListener) {
        this.context = context;
        this.entityList = list;
        mDragStartListener = dragStartListener;

    }

//    //编辑还是不编辑
//    public void changeStatus() {
//        if (isEdit) {//编辑 点击编辑 变成不编辑 共件数等于0
//            isEdit = false;//不编辑
//            num = 0;//一共多少件
//        } else {
//            isEdit = true;
//        }
//        notifyDataSetChanged();
//    }
//
//    //  删除 打勾 全选
//    public void cleckAll(boolean is_checked) { //全选 删除多少那里要删除全部
//        flag = true;
//        if (is_checked) {//如果有打勾 全选
//            jobIdList.clear();//全部清理
//            for (int i = 0; i < entityList.size(); i++) {//统计 个数 实体
//                jobIdList.add(entityList.get(i).getEpNewsId());//全部删除
//            }
//            num = entityList.size();//共多少件 全部
//        } else {//如果没有打勾全选
//            jobIdList.clear();
//            num = 0;
//        }
//        mOnMyCheckedChangeListener.onCheckedChange(num, jobIdList);
//        notifyDataSetChanged();
//    }


    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company_info_list, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        final CompanyInfoListEntity entity = entityList.get(position);
        // Start a drag whenever the handle view it touched
        holder.itemCompanyInfoListTitle.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
//        if (isEdit) {//编辑
//            holder.itemMyCollectCheckbox.setVisibility(View.VISIBLE);//选择框 显示
//            holder.itemMyCollectCheckbox.setChecked(entity.is_checked());//设置是不是选中
//        } else {
//            holder.itemMyCollectCheckbox.setVisibility(View.GONE);//选择框 隐藏
//            holder.itemMyCollectCheckbox.setChecked(false);//设置没有选中
//        }
//        holder.itemMyCollectCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {//选择框 选没选 监听
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {//状态切换控件CompoundButton及其子类CheckBox、RadioButton、ToggleButton、switch事件监听
//
////                if (!flag){
////                    if (b){
////                        num+=1;
////                        jobIdList.add(entity.getJob_id());
////                    }else{
////                        if(num > 0){
////                            num-=1;
////                            jobIdList.remove(entity.getJob_id());
////                        }
////                    }
////                    mOnMyCheckedChangeListener.onCheckedChange(num, jobIdList);
////                }
//            }
//        });
        holder.itemCompanyInfoListTitle.setText(entity.getTitle());
        holder.itemCompanyInfoListTime.setText(entity.getTime());
        //判断是否设置了监听器
        if (mOnItemClickListener == null) {
            //为ItemView设置监听器
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = false;
//                    if (isEdit){//编辑
//                        if (holder.itemMyCollectCheckbox.isChecked()){//点击CheckBox选中
//                            holder.itemMyCollectCheckbox.setChecked(false);//设置没有选中
//                            if(num > 0){//如果有选中
//                                num-=1;//总数-
//                                jobIdList.remove(entity.getEpNewsId());//移除选中的
//                            }
//                        }else {
//                            holder.itemMyCollectCheckbox.setChecked(true);//设置选中
//                            num+=1;//总数+1
//                            jobIdList.add(entity.getEpNewsId());
//                        }
//                        mOnMyCheckedChangeListener.onCheckedChange(num, jobIdList);//选择框 选没选 监听
//                    }else {
////                        if (entity.getValidity().equals("0")){//无效性
////                            Utils.showShortToast(mContext,"该岗位已失效");
////                        }else {//有效性
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

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(entityList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        String epNewsId = entityList.get(position).getEpNewsId();
        deleteCompanyInfoToWeb(epNewsId, position);

    }

    private void deleteCompanyInfoToWeb(String epNewsId, final int position) {//删除企业动态
        AsyncHttpClient client = new AsyncHttpClient();
        String url = Url.deleteCompanyInfo;
        RequestParams params = new RequestParams();
        params.put("epNewsId", epNewsId);//动态id
        params.put("userId", UserNative.getId());//
        params.put("userNm", UserNative.getName());//
        params.put("key",UserNative.getAesKes());
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(context, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
//                        entityList.remove(position);
//                        entityList.remove(position);
                        notifyItemRemoved(position);
                        entityList.remove(position);

                        ToastUtil.showSuccess(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(context, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });

    }


    public class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {
//


        @BindView(R.id.item_company_info_list_image)
        TextView itemCompanyInfoListImage;
        @BindView(R.id.item_company_info_list_title)
        TextView itemCompanyInfoListTitle;
        @BindView(R.id.item_company_info_list_time)
        TextView itemCompanyInfoListTime;

        public ItemViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);

        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

    }

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //返回选中的ITEM数目
    public interface OnMyCheckedChangeListener {
        void onCheckedChange(int num, List<String> jobIdList);
    }

    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
    }

}

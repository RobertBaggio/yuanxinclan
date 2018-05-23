package com.yuanxin.clan.core.company.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.CompanyMemberEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.MainApplication;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo1 on 2017/3/21.
 */
public class CompanyMemberAdapter extends RecyclerView.Adapter<CompanyMemberAdapter.ViewHolder> {
    private List<CompanyMemberEntity> entityList = new ArrayList<>();
    private List<CompanyMemberEntity> deleteList = new ArrayList<>();//选中的数据
    private OnMyCheckedChangeListener mOnMyCheckedChangeListener;
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private Boolean is_edit = false;


//    private ArrayList<CompanyMemberEntity> datas = new ArrayList<>();

    public CompanyMemberAdapter(Context context, List<CompanyMemberEntity> list) {
        this.context = context;
        this.entityList = list;
    }

    public void setEdit() {
        is_edit = !is_edit;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_company_member, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final CompanyMemberEntity entity = entityList.get(position);
        if (!entity.getImage().equals("")) {
            ImageManager.loadBitmap(context, entity.getImage(), R.drawable.chat_icon_personal, holder.itemCompanyMemberHeadImage);
        }

        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) holder.itemCompanyMemberLayout.getLayoutParams(); //取控件textView当前的布局参数

        linearParams.width = MainApplication.getnScreenWidth()/4;// 控件的宽强制设成30
//        linearParams.height = 47*c+80;// 控件的高强制设成20

        holder.itemCompanyMemberLayout.setLayoutParams(linearParams);
//        holder.itemCompanyMemberLayout.
        holder.itemCompanyMemberName.setText(TextUtil.formatString(entity.getName()));
        holder.itemCompanyMemberPosition.setText(TextUtil.formatString(entity.getPosition()));
        if (is_edit) {
            holder.itemInformationChooseImage.setVisibility(View.VISIBLE);
        } else {
            holder.itemInformationChooseImage.setVisibility(View.GONE);

        }
        holder.itemInformationChooseImage.setOnClickListener(new View.OnClickListener() {//点击删除
            @Override
            public void onClick(View view) {
//                if (is_edit) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
//                    showNormalDialogOne(position);


//                }
            }
        });
        //itemCompanyMemberHeadImage
        holder.itemCompanyMemberHeadImage.setOnClickListener(new View.OnClickListener() {//点击删除
            @Override
            public void onClick(View view) {
//                if (is_edit) {
                    int position = holder.getLayoutPosition(); // 1
                    mOnItemClickListener.onItemClick(holder.itemView, position); // 2
//                    showNormalDialogOne(position);
//                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition(); // 1
                mOnItemClickListener.onItemClick(holder.itemView, position); // 2
            }
        });
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

    //showNormalDialogOne
    private void showNormalDialogOne(final int position) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("警告");
        normalDialog.setMessage("您确定删除该企业成员吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

//                    deleteList.add(entity);
                        //entityList.get(position).getUserId()
                        CompanyMemberEntity entity = entityList.get(position);
                        Log.v("lgq","...userid==="+entity.getUserId());
                        deleteCompanyMemberToWeb(entity.getUserId(), position);
//                        notifyDataSetChanged();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void deleteCompanyMemberToWeb(String userId, final int position) {
        String url = Url.deleteCompanyMemberToWeb;
        RequestParams params = new RequestParams();
        params.put("epId", UserNative.getEpId());//企业id
        params.put("userId", userId);//要删除的id
        params.put("updateId", UserNative.getId());//用户id
        params.put("updateNm", UserNative.getName());//用户id
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(context, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
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

    @Override
    public int getItemCount() {
        return entityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_information_choose_image)
        ImageView itemInformationChooseImage;
        @BindView(R.id.item_company_member_head_image)
        MLImageView itemCompanyMemberHeadImage;
        @BindView(R.id.item_company_member_name)
        TextView itemCompanyMemberName;
        @BindView(R.id.item_company_member_position)
        TextView itemCompanyMemberPosition;
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

    //返回选中的ITEM数目
    public interface OnMyCheckedChangeListener {
        void onCheckedChange(int userId);
    }

    public void setOnMyCheckedChangeListener(OnMyCheckedChangeListener mOnMyCheckedChangeListener) {
        this.mOnMyCheckedChangeListener = mOnMyCheckedChangeListener;
    }

    protected void doHttpPost(String url, RequestParams params, final BaseActivity.RequestCallback c) {
        AsyncHttpClient client = new AsyncHttpClient();
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {//测试1》2
//            ViewUtils.AlertDialog(context, "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(context, LoginActivity.class);
//                    context.startActivity(intent);
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//        } else {
            params.put("key", aesKes);
            client.post(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    c.onFailure(i, headers, s, throwable);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        c.onSuccess(i, headers, s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//        }
    }


}

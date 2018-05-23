package com.yuanxin.clan.core.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.adapter.BusinessAreaAdapter;
import com.yuanxin.clan.core.entity.BusinessAreaEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/28.
 * 商圈成员管理
 */
public class BusinessMemberActivity extends BaseActivity {
    @BindView(R.id.activity_business_member_left_layout)
    LinearLayout activityBusinessMemberLeftLayout;
    @BindView(R.id.activity_business_member_right_layout)
    LinearLayout activityBusinessMemberRightLayout;
    @BindView(R.id.activity_business_member_recycler_view)
    RecyclerView activityBusinessMemberRecyclerView;
    @BindView(R.id.activity_business_member_head_image)
    MLImageView activityBusinessMemberHeadImage;
    @BindView(R.id.activity_business_member_add_layout)
    LinearLayout activityBusinessMemberAddLayout;

    private String bid;

    private static final int REQUEST_REFRESHADD = 14;//企业简介

    private List<BusinessAreaEntity> companyMemberEntities = new ArrayList<>();
    private BusinessAreaAdapter companyMemberAdapter;


    @Override
    public int getViewLayout() {
        return R.layout.activity_business_member;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();

//        intentFilter = new IntentFilter();
//        intentFilter.addAction("yuanxin.clan.refresh.localBroadcast");
//        localReceiver = new LocalReceiver();
//        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
//// 注册本地广播监听器
    }

    private void initRecyclerView() {
        bid = getIntent().getStringExtra("id");
        companyMemberAdapter = new BusinessAreaAdapter(BusinessMemberActivity.this, companyMemberEntities);
        companyMemberAdapter.setOnItemDeleClickListener(new BusinessAreaAdapter.OnItemDeleClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (companyMemberEntities.get(position).getUserId().equals(String.valueOf(UserNative.getId()))){
                    ToastUtil.showWarning(getApplicationContext(), "不可删除自己！", Toast.LENGTH_SHORT);
                    return;
                }
                showDeleteFriendDialog(companyMemberEntities.get(position).getMemberId(),companyMemberEntities.get(position).getUserId());
//                delepeople(companyMemberEntities.get(position).getMemberId(),companyMemberEntities.get(position).getUserId());
            }
        });
        activityBusinessMemberRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
//
//        activityOneRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        activityBusinessMemberRecyclerView.setAdapter(companyMemberAdapter);
        activityBusinessMemberRecyclerView.setFocusable(false);//导航栏切换不再focuse
        getBusinessDetail(bid); //请求网络数据
    }

    //    class LocalReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            //添加了新成员 要进行刷新
//            Toast.makeText(context, "received local broadcast",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }
    private void getBusinessDetail(String businessAreaId) {
        String url = Url.getbusinesspeople;
        RequestParams params = new RequestParams();
        params.put("businessAreaId", businessAreaId);//用户id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String data = object.getString("data");
                        if (!data.equals("null")) {
                            JSONArray jsonArray = object.getJSONArray("data");
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject businessObject = jsonArray.getJSONObject(a);

                                String user = businessObject.getString("user");
                                String memberId = businessObject.getString("memberId");
                                if (!user.equals("null")) {
                                    JSONObject addressObject = businessObject.getJSONObject("user");
                                    String userId = addressObject.getString("userId");
                                    String userNm = addressObject.getString("userNm");
                                    String userPhone = addressObject.getString("userPhone");
                                    String userImage = addressObject.getString("userImage");
                                    String image= Url.img_domain+userImage+Url.imageStyle640x640;

                                    BusinessAreaEntity entity = new BusinessAreaEntity();
                                    entity.setUserId(userId);
                                    entity.setMemberId(memberId);
                                    entity.setUserNm(userNm);
                                    entity.setUserPhone(userPhone);
                                    entity.setUserImage(image);

                                    companyMemberEntities.add(entity);
                                }

                            }
                            companyMemberAdapter.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void delepeople(String memberId,String uid) {
        String url = Url.delBaUser;
        RequestParams params = new RequestParams();
        params.put("businessAreaId", bid);//用户id
        params.put("memberId", memberId);//用户id
        params.put("delFlg", 0);//用户id
        params.put("userId", uid);//用户id
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        companyMemberEntities.clear();
                        getBusinessDetail(bid);
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    @OnClick({R.id.activity_business_member_left_layout, R.id.activity_business_member_right_layout, R.id.activity_business_member_head_image, R.id.activity_business_member_add_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_member_left_layout:
                finish();
                break;
            case R.id.activity_business_member_right_layout://编辑
                companyMemberAdapter.setEdit();//编辑 自己删除
                break;
            case R.id.activity_business_member_head_image:
//                startActivity(new Intent(BusinessMemberActivity.this, AddBusinessMemberActivity.class));
                Intent intentpeople = new Intent(BusinessMemberActivity.this, AddBusinessMemberActivity.class);
                intentpeople.putExtra("id", bid);
                startActivityForResult(intentpeople, 6);
                break;
            case R.id.activity_business_member_add_layout:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("lgq","requestCode==="+requestCode);
        if (requestCode == 6) {
            if (resultCode == RESULT_OK) {
                companyMemberEntities.clear();
                getBusinessDetail(bid); //请求网络数据
                }
            }
        }

    private void showDeleteFriendDialog(final String id, final String uid) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(BusinessMemberActivity.this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否删除该商圈成员");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delepeople(id,uid);
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
}

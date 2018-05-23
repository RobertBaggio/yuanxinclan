package com.yuanxin.clan.core.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.ShouyeAdapter;
import com.yuanxin.clan.core.company.bean.ShouyeEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.Logger;
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
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/3 0003 14:29
 */

public class BusinessViewChooseActivity extends BaseActivity {

    @BindView(R.id.activity_my_bargain_right_layout)
    LinearLayout activityMyBargainRightLayout;
    @BindView(R.id.activity_my_bargain_recycler_view)
    RecyclerView activityMyBargainRecyclerView;
    @BindView(R.id.activity_my_bargain_left_layout)
    LinearLayout activityMyBargainLeftLayout;
    private ShouyeAdapter chooseStyleAdapter;
    private List<ShouyeEntity> shouyeEntities = new ArrayList<>();
    private int  viewId;
    private String typeid;
    private SubscriberOnNextListener getShowAdvertisementsOnNextListener;

    @Override
    public int getViewLayout() {
        return R.layout.activity_choose_style;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
//        initOnNext();
//        getBannerAll();
        typeid= getIntent().getStringExtra("type");
        addFriendSingle();
        initRecyclerView();
    }


    private void addFriendSingle() {
        String url = Url.businessViewPage;
        RequestParams params = new RequestParams();
        params.put("epType", typeid);
        params.put("appFlg", 1);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("lgq","..........."+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String sb = object.getString("data");
                        JSONArray arr= new JSONArray(sb);
                        shouyeEntities.clear();
                        for (int j = 0; j < arr.length(); j++) {
                            JSONObject temp = (JSONObject) arr.get(j);
                            int id = temp.getInt("epViewId");
                            String epViewNm = temp.getString("epViewNm");
                            String epViewImage = temp.getString("epViewImage");
                            String epAccessPath = temp.getString("epAccessPath");
                            ShouyeEntity shouyeEntity = new ShouyeEntity();
                            shouyeEntity.setEpViewId(id);
                            shouyeEntity.setEpViewImage(epViewImage);
                            shouyeEntity.setEpViewNm(epViewNm);
                            shouyeEntity.setEpAccessPath(epAccessPath);
                            // 是否是当前视图
                            if (id == viewId) {
                                shouyeEntity.setCurrent(true);
                            } else {
                                shouyeEntity.setCurrent(false);
                            }
                            shouyeEntities.add(shouyeEntity);
                            chooseStyleAdapter.notifyDataSetChanged();

                        }

//                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });

    }


    private void initRecyclerView() {
        chooseStyleAdapter = new ShouyeAdapter(BusinessViewChooseActivity.this, shouyeEntities);
        chooseStyleAdapter.setOnItemClickListener(new ShouyeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showNormalDialogOne(shouyeEntities.get(position));
            }
        });
        activityMyBargainRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        activityMyBargainRecyclerView.setAdapter(chooseStyleAdapter);
    }


    @OnClick(R.id.activity_my_bargain_left_layout)
    public void onClick() {
        finish();
    }

    private void showNormalDialogOne(final ShouyeEntity entity) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定使用该风格吗！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mIntent = new Intent(BusinessViewChooseActivity.this,MyBusinessDistrictSetUpActivity.class);
                        mIntent.putExtra("index", entity.getEpViewId());
                        BusinessViewChooseActivity.this.setResult(18, mIntent);
                        finish();
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
}

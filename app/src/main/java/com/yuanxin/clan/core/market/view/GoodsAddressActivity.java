package com.yuanxin.clan.core.market.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.GoodsAddressAdapter;
import com.yuanxin.clan.core.market.bean.PostAddress;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/2.
 */
//收货地址
public class GoodsAddressActivity extends BaseActivity {
    @BindView(R.id.activity_goods_address_left_layout)
    LinearLayout activityGoodsAddressLeftLayout;
    @BindView(R.id.activity_goods_address_right_layout)
    LinearLayout activityGoodsAddressRightLayout;
    @BindView(R.id.activity_goods_address_recycler_view)
    RecyclerView activityGoodsAddressRecyclerView;

    private GoodsAddressAdapter adapter;
    private List<PostAddress> goodsAddressEntities = new ArrayList<>();
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(this);
    private boolean isChooseAddress = false;


    @Override
    public int getViewLayout() {
        return R.layout.activity_goods_address;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        isChooseAddress = getIntent().getBooleanExtra("isChooseAddress", false);
        initRecyclerView();
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getGoodsAddress();
        }
    }

    protected void onStart() {
        super.onStart();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.ADD_GOOD_FRESH_BROADCAST");
        localReceiver = new LocalReceiver();
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }


    private void initRecyclerView() {
        //搜索结果
        adapter = new GoodsAddressAdapter(GoodsAddressActivity.this, goodsAddressEntities, GoodsAddressActivity.this);
        adapter.setOnItemClickListener(new GoodsAddressAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isChooseAddress) {
                    //选中了商品再发送回去
                    setResult(RESULT_OK, new Intent().putExtra("address", goodsAddressEntities.get(position)));
                    finish();
                }
            }
        });
        activityGoodsAddressRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityGoodsAddressRecyclerView.setAdapter(adapter);
        getGoodsAddress();
    }

    private void getGoodsAddress() {
        String url = Url.getMyAddress;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        goodsAddressEntities.clear();
                        goodsAddressEntities.addAll(FastJsonUtils.getObjectsList(object.getString("data"), PostAddress.class));
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            getGoodsAddress();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    @OnClick({R.id.activity_goods_address_left_layout, R.id.activity_goods_address_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_goods_address_left_layout:
                finish();
                break;
            case R.id.activity_goods_address_right_layout://添加
                Intent chooseStyle = new Intent(GoodsAddressActivity.this, EditGoodsAddressActivity.class);
                startActivityForResult(chooseStyle, 401);
                break;
        }
    }
}

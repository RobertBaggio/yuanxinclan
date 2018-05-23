package com.yuanxin.clan.core.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.NewMyBargainAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.BargainEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/27.
 */
//我的合同
public class NewMyBargainActivity extends BaseActivity {

    private static final int REQUEST_IMAGE1 = 1;
    @BindView(R.id.activity_my_bargain_left_layout)
    LinearLayout activityMyBargainLeftLayout;
    @BindView(R.id.activity_my_bargain_right_layout)
    LinearLayout activityMyBargainRightLayout;
    @BindView(R.id.activity_my_bargain_recycler_view)
    RecyclerView activityMyBargainRecyclerView;

    private NewMyBargainAdapter myBargainAdapter;
    private List<BargainEntity> myBargainEntityList = new ArrayList<>();
    private static final int REQUEST_CODE = 6384;
    private static final String TAG = "NewMyBargainActivity";
    private boolean isSelect = false;


    @Override
    public int getViewLayout() {
        return R.layout.activity_my_bargain;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        isSelect = getIntent().getBooleanExtra("isSelect", false);
        initRecyclerView();
        getMyBargain();
    }

    private void getMyBargain() {
        String url = Url.myBargain;
        RequestParams params = new RequestParams();
        params.put("createId", UserNative.getId());//用户id
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
                        myBargainEntityList.clear();
                        myBargainEntityList.addAll(FastJsonUtils.getObjectsList(object.getString("data"), BargainEntity.class));
                        myBargainAdapter.notifyDataSetChanged();
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
        myBargainAdapter = new NewMyBargainAdapter(NewMyBargainActivity.this, myBargainEntityList);
        myBargainAdapter.setOnItemClickListener(new NewMyBargainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (isSelect) {
                    setResult(RESULT_OK, new Intent().putExtra("bargain", (Serializable) myBargainEntityList.get(position)));
                    finish();
                }
            }
        });
        activityMyBargainRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activityMyBargainRecyclerView.setAdapter(myBargainAdapter);

    }

    @OnClick({R.id.activity_my_bargain_left_layout, R.id.activity_my_bargain_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_my_bargain_left_layout:
                finish();
                break;
            case R.id.activity_my_bargain_right_layout:
                showChooser();
                break;
        }
    }

    private void showChooser() {
        // Use the GET_CONTENT intent from the utility class
        Intent target = FileUtils.createGetContentIntent();
        // Create the chooser Intent
        Intent intent = Intent.createChooser(
                target, getString(R.string.chooser_title));
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            // The reason for the existence of aFileChooser
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                // If the file selection was successful
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        // Get the URI of the selected file
                        final Uri uri = data.getData();
                        try {
                            // Get the file path from the URI
                            final String path = FileUtils.getPath(this, uri);
//                            File compressedImage1File = Compressor.getDefault(this).compressToFile(new File(path));//我加的压缩成File
                        } catch (Exception e) {
                            ToastUtil.showWarning(getApplicationContext(), "文件选择出错", Toast.LENGTH_SHORT);
                        }
                    }
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}

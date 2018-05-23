package com.yuanxin.clan.core.company.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/21.
 * 企业添加成员
 */
public class AddCompanyMemberActivity extends BaseActivity {


    @BindView(R.id.activity_company_member_add_head_image_layout)
    LinearLayout activityCompanyMemberAddHeadImageLayout;
    @BindView(R.id.activity_company_member_add_right_layout)
    LinearLayout activityCompanyMemberAddRightLayout;
    @BindView(R.id.activity_company_member_add_name)
    EditText activityCompanyMemberAddName;
    @BindView(R.id.activity_company_member_add_phone)
    EditText activityCompanyMemberAddPhone;
    @BindView(R.id.activity_company_member_add_position)
    EditText activityCompanyMemberAddPosition;
    @BindView(R.id.activity_company_member_add_button)
    Button activityCompanyMemberAddButton;
    private LocalBroadcastManager localBroadcastManager;
    private int epId;

    @Override
    public int getViewLayout() {
        return R.layout.activity_company_member_add;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        getInfo();
    }

    private void getInfo() {
        Intent intent = getIntent();
        epId = intent.getIntExtra("epIdNew", 0);
    }


    @OnClick({R.id.activity_company_member_add_head_image_layout, R.id.activity_company_member_add_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_member_add_head_image_layout:
                finish();
                break;
            case R.id.activity_company_member_add_button://添加企业成员
                String name = activityCompanyMemberAddName.getText().toString().trim();
                String phone = activityCompanyMemberAddPhone.getText().toString().trim();
                String position = activityCompanyMemberAddPosition.getText().toString().trim();
                if (TextUtil.isEmpty(name) || TextUtil.isEmpty(phone) || TextUtil.isEmpty(position)) {
                    ToastUtil.showInfo(AddCompanyMemberActivity.this, "请输入正确信息", Toast.LENGTH_SHORT);
                    return;
                }
                addCompanyMember(name, phone, position);
                Intent intentOne = new Intent();
//                intent.putExtra("image","刷新");
                setResult(RESULT_OK, intentOne);
                //进行网络请求 上传后台
                //之后发送广播 刷新数据
                localBroadcastManager = LocalBroadcastManager.getInstance(this);
                Intent intent = new Intent("yuanxin.clan.refresh.localBroadcast");
                localBroadcastManager.sendBroadcast(intent);


        }
    }

    private void addCompanyMember(String name, String phone, String position) {
        String url = Url.addCompanyMember;
        RequestParams params = new RequestParams();
        params.put("epUser.epId", epId);//企业id
        params.put("epUser.position", position);//职位
        params.put("epUser.createId", UserNative.getId());// 用户ID(当前登录用户ID)
        params.put("epUser.createNm", UserNative.getName());//用户名(当前登录用户名)
        params.put("userPhone", phone);//手机号
        params.put("userNm", name);//要加的名字
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq",".......添加。。。。"+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        onBackPressed();
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

}

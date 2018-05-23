package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/28.
 * 商圈添加成员
 */
public class AddBusinessMemberActivity extends BaseActivity {
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

    private String bid;
    private More_LoadDialog mMore_loadDialog;


    @Override
    public int getViewLayout() {
        return R.layout.activity_business_member_add;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        bid = getIntent().getStringExtra("id");
        mMore_loadDialog = new More_LoadDialog(this);
    }

    @OnClick({R.id.activity_company_member_add_head_image_layout, R.id.activity_company_member_add_right_layout, R.id.activity_company_member_add_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_member_add_head_image_layout:
                finish();
                break;
            case R.id.activity_company_member_add_right_layout:
                break;
            case R.id.activity_company_member_add_button:
                String name = activityCompanyMemberAddName.getText().toString();
                String phone=activityCompanyMemberAddPhone.getText().toString();
                String price = activityCompanyMemberAddPosition.getText().toString();
                if (TextUtil.isEmpty(name)||TextUtil.isEmpty(phone)||TextUtil.isEmpty(price)){
                    ToastUtil.showInfo(getApplicationContext(), "完善填写内容", Toast.LENGTH_SHORT);
                    return;
                }
                mMore_loadDialog.show();
                addpeople(name,phone,price);
                break;
        }
    }
    private void addpeople(String name,String phone,String price) {
        String url = Url.addBaUser;
        RequestParams params = new RequestParams();
        params.put("baUser.businessAreaId", bid);//企业id
        params.put("baUser.position", price);//职位
        params.put("baUser.createId", UserNative.getId());// 用户ID(当前登录用户ID)
        params.put("baUser.createNm", UserNative.getName());//用户名(当前登录用户名)
        params.put("userPhone", phone);
        params.put("userNm", name);
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMore_loadDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        mMore_loadDialog.dismiss();
                        setResult(RESULT_OK);
                        finish();
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
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

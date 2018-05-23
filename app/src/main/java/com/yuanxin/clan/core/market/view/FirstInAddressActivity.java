package com.yuanxin.clan.core.market.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.extendsclass.BecomePicktask;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.bean.PostAddress;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/4/3 0003 9:40
 */

public class FirstInAddressActivity extends BaseActivity {
    @BindView(R.id.activity_add_goods_address_receive_persion_edit)
    EditText activityAddGoodsAddressReceivePersionEdit;
    @BindView(R.id.activity_add_goods_address_phone_edit)
    EditText activityAddGoodsAddressPhoneEdit;
    @BindView(R.id.activity_add_goods_address_address_edit)
    TextView activityAddGoodsAddressAddressEdit;

    @BindView(R.id.activity_add_goods_address_detail_address_edit)
    EditText activityAddGoodsAddressDetailAddressEdit;
    @BindView(R.id.activity_edit_goods_address_delete)
    TextView activityEditGoodsAddressDelete;
    @BindView(R.id.activity_add_goods_address_default_address_edit)
    Switch mActivityAddGoodsAddressDefaultAddressEdit;
    private PostAddress mPostAddress;
    private String provinceStr;
    private String cityStr;
    private String areaStr;
    private boolean isDefault = true;
    private boolean isAdd = true;


    @Override
    public int getViewLayout() {
        return R.layout.firstinaddadress;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        loadView();
        mPostAddress = (PostAddress) getIntent().getSerializableExtra("address");
        if (null == mPostAddress) {
            isAdd = true;
            activityEditGoodsAddressDelete.setVisibility(View.GONE);
        } else {
            isAdd = false;
            setData();//从前一个activity中传过来
        }
    }

    private void loadView() {
        mActivityAddGoodsAddressDefaultAddressEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isDefault = isChecked;
            }
        });
    }

    private void setData() {
        activityAddGoodsAddressReceivePersionEdit.setText(mPostAddress.getReceiver());//收件人
        activityAddGoodsAddressPhoneEdit.setText(mPostAddress.getPhone());//手机号码
        activityAddGoodsAddressAddressEdit.setText(mPostAddress.getProvince() + mPostAddress.getCity() + mPostAddress.getArea());//省市区
        activityAddGoodsAddressDetailAddressEdit.setText(mPostAddress.getDetail());//
        mActivityAddGoodsAddressDefaultAddressEdit.setChecked(mPostAddress.getDefaultAddress() == 1 ? true : false);
    }


    @OnClick({R.id.activity_add_goods_address_left_layout, R.id.activity_add_goods_address_right_layout, R.id.activity_add_goods_address_address_layout, R.id.activity_edit_goods_address_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_add_goods_address_left_layout:
                finish();
                break;
            case R.id.activity_add_goods_address_right_layout://保存
                String persion = activityAddGoodsAddressReceivePersionEdit.getText().toString();
                if (TextUtil.isEmpty(persion)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入收件人", Toast.LENGTH_SHORT);
                    return;
                }
                String phone = activityAddGoodsAddressPhoneEdit.getText().toString();
                if (TextUtil.isEmpty(phone)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入手机号码", Toast.LENGTH_SHORT);
                    return;
                }
                String address = activityAddGoodsAddressAddressEdit.getText().toString();
                if (TextUtil.isEmpty(address)) {
                    ToastUtil.showInfo(getApplicationContext(), "请选择地区", Toast.LENGTH_SHORT);
                    return;
                }
                String addressDetail = activityAddGoodsAddressDetailAddressEdit.getText().toString();
                if (TextUtil.isEmpty(addressDetail)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入详细地址", Toast.LENGTH_SHORT);
                    return;
                }
                if (isAdd) {
                    mPostAddress = new PostAddress();
                }
                if (!TextUtil.isEmpty(provinceStr)) {
                    mPostAddress.setProvince(provinceStr);
                    mPostAddress.setCity(cityStr);
                    mPostAddress.setArea(areaStr);
                }
                mPostAddress.setReceiver(activityAddGoodsAddressReceivePersionEdit.getText().toString());
                mPostAddress.setDetail(activityAddGoodsAddressDetailAddressEdit.getText().toString());
                mPostAddress.setPhone(activityAddGoodsAddressPhoneEdit.getText().toString());
                mPostAddress.setDefaultAddress(1);//是否默认
                addGoodsAddress(isAdd ? 0 : mPostAddress.getUserAddressId(), mPostAddress.getProvince(), mPostAddress.getCity(),
                        mPostAddress.getArea(), mPostAddress.getDetail(), mPostAddress.getReceiver(), mPostAddress.getPhone(), mPostAddress.getDefaultAddress());//
                break;
            case R.id.activity_add_goods_address_address_layout:
                onAddressPicker();
                break;
            case R.id.activity_edit_goods_address_delete://删除
                deleteAddress();
                break;
        }
    }

    private void deleteAddress() {
        String url = Url.deleteMyAddress;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        params.put("userAddressId", mPostAddress.getUserAddressId());
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void addGoodsAddress(int userAddressId, String province, String city, String area, String detail, String receiver, String phone, int defaultAddress) {
        String url;
        RequestParams params = new RequestParams();
        if (isAdd) {
            url = Url.addAddress;
        } else {
            url = Url.updateAddress;
            params.put("userAddressId", userAddressId);
        }
        params.put("userId", UserNative.getId());
        params.put("province", province);
        params.put("city", city);
        params.put("area", area);
        params.put("detail", detail);//收件人
        params.put("receiver", receiver);//收件人
        params.put("phone", phone);//手机号码
        params.put("defaultAddress", defaultAddress);//是否默认
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        setResult(RESULT_OK);
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }


    public void onAddressPicker() {
        BecomePicktask task = new BecomePicktask(this);
        task.setCallback(new BecomePicktask.Callback() {
            @Override
            public void onAddressInitFailed() {
                ToastUtil.showWarning(getApplicationContext(), "数据初始化失败", Toast.LENGTH_SHORT);
            }

            @Override
            public void onAddressPicked(Province province, City city, County county) {
                if (county == null) {
                    activityAddGoodsAddressAddressEdit.setText(province.getAreaName() + "-" + city.getAreaName());
                    areaStr = "";
                } else {
                    activityAddGoodsAddressAddressEdit.setText(province.getAreaName() + "-" + city.getAreaName() + "-" + county.getAreaName());
                    areaStr = county.getAreaName();
                }
                provinceStr = province.getAreaName();
                cityStr = city.getAreaName();
            }
        });
        task.execute("广东", "东莞", "东城");
    }
}

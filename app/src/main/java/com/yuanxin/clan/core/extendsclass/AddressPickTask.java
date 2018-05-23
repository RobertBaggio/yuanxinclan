package com.yuanxin.clan.core.extendsclass;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;

import com.yuanxin.clan.core.util.FastJsonUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.util.ConvertUtils;

/**
 * 获取地址数据并显示地址选择器
 *
 * @author 李玉江[QQ:1032694760]
 * @since 2015/12/15
 */
public class AddressPickTask extends AsyncTask<String, Void, ArrayList<Province>> {
    private WeakReference<Activity> weakReference;
    private ProgressDialog dialog;
    private Callback callback;
    private String selectedProvince = "", selectedCity = "", selectedCounty = "";
    private boolean hideProvince = false;
    private boolean hideCounty = true;//隐藏区true
    private PickerDismiss pickerDismiss;

    public AddressPickTask(Activity activity) {
        weakReference = new WeakReference<>(activity);
    }

    public void setHideProvince(boolean hideProvince) {
        this.hideProvince = hideProvince;
    }

    public void setHideCounty(boolean hideCounty) {
        this.hideCounty = hideCounty;
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void setPickerDismiss(PickerDismiss pickerDismiss) {
        this.pickerDismiss = pickerDismiss;
    }

    @Override
    protected void onPreExecute() {
        Activity activity = weakReference.get();
        if (activity != null) {
            dialog = ProgressDialog.show(activity, null, "正在初始化数据...", true, true);
        }
    }

    @Override
    protected ArrayList<Province> doInBackground(String... params) {
        if (params != null) {
            switch (params.length) {
                case 1:
                    selectedProvince = params[0];
                    break;
                case 2:
                    selectedProvince = params[0];
                    selectedCity = params[1];
                    break;
                case 3:
                    selectedProvince = params[0];
                    selectedCity = params[1];
//                    selectedCounty = params[2];//隐藏区
                    break;
                default:
                    break;
            }
        }
        ArrayList<Province> data = new ArrayList<>();
        try {
            Activity activity = weakReference.get();
            if (activity != null) {
                String json = ConvertUtils.toString(activity.getAssets().open("city.json"));
                data.addAll(FastJsonUtils.getObjectsList(json, Province.class));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(ArrayList<Province> result) {
        dialog.dismiss();
        if (result.size() > 0) {
            Activity activity = weakReference.get();
            if (activity != null) {
                AddressPicker picker = new AddressPicker(activity, result);
                picker.setHideProvince(hideProvince);
                picker.setHideCounty(hideCounty);
                if (hideCounty) {
                    picker.setColumnWeight(1 / 3.0, 2 / 3.0);//将屏幕分为3份，省级和地级的比例为1:2
                } else {
                    picker.setColumnWeight(2 / 8.0, 3 / 8.0, 3 / 8.0);//省级、地级和县级的比例为2:3:3
                }
//                picker.setSelectedItem(selectedProvince, selectedCity, selectedCounty);//隐藏区
                picker.setSelectedItem(selectedProvince, selectedCity);
                picker.setOnAddressPickListener(callback);
                picker.setOnDismissListener(pickerDismiss);
                picker.show();
            }
        } else {
            callback.onAddressInitFailed();
        }
    }

    public interface PickerDismiss extends DialogInterface.OnDismissListener {
        @Override
        void onDismiss(DialogInterface dialog);
    }

    public interface Callback extends AddressPicker.OnAddressPickListener {
        void onAddressInitFailed();
    }

}


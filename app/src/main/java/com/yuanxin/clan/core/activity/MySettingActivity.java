package com.yuanxin.clan.core.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.AppManager;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.DemoHelper;
import com.yuanxin.clan.core.util.DataCleanUtil;
import com.yuanxin.clan.core.util.JPushUtil;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.MainApplication;
import com.yuanxin.clan.mvp.service.UpdateAppService;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by lenovo1 on 2017/2/28.
 * 我的设置
 */
public class MySettingActivity extends BaseActivity {


    @BindView(R.id.window_head_left_image)
    ImageView windowHeadLeftImage;
    @BindView(R.id.window_head_left_image_layout)
    LinearLayout windowHeadLeftImageLayout;
    @BindView(R.id.window_head_left_text)
    TextView windowHeadLeftText;
    @BindView(R.id.window_head_left_layout)
    LinearLayout windowHeadLeftLayout;
    @BindView(R.id.window_head_name)
    TextView windowHeadName;
    @BindView(R.id.center_headname_ll)
    LinearLayout centerHeadnameLl;
    @BindView(R.id.window_head_right_layout)
    LinearLayout windowHeadRightLayout;
    @BindView(R.id.window_head_layout)
    RelativeLayout windowHeadLayout;
    @BindView(R.id.activity_my_setting_account_safe)
    RelativeLayout activityMySettingAccountSafe;
    @BindView(R.id.activity_my_setting_about_us)
    RelativeLayout activityMySettingAboutUs;
    @BindView(R.id.activity_my_setting_networking_protocol)
    RelativeLayout activityMySettingNetworkingProtocol;
    @BindView(R.id.activity_my_setting_complaint_advice)
    RelativeLayout activityMySettingComplaintAdvice;
    @BindView(R.id.rl_check_update)
    RelativeLayout rl_check_update;
    @BindView(R.id.activity_my_setting_clear_cache_text)
    TextView activityMySettingClearCacheText;
    @BindView(R.id.activity_my_setting_clear_cache_how_much)
    TextView activityMySettingClearCacheHowMuch;
    @BindView(R.id.activity_my_setting_clear_cache)
    RelativeLayout activityMySettingClearCache;
    @BindView(R.id.activity_my_setting_quit)
    TextView activityMySettingQuit;
    @BindView(R.id.versionte)
    TextView versionte;
    @BindView(R.id.ycsw)
    Switch ycsw;
    private String file_path;
    private String wbovs;
    private JSONArray updateContent;
    private My_LoadingDialog mMy_loadingDialog;
    private int ifone=0;

    @Override
    public int getViewLayout() {
        return R.layout.new_myset;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initView();
        initInfo();//清理缓存
        getstatus();
        mMy_loadingDialog = My_LoadingDialog.getInstance(this);
        if (!UserNative.readIsLogin()){
            activityMySettingQuit.setVisibility(View.GONE);
        }
    }

    private void initInfo() {
        File cache = getCacheDir();//获取缓存
        try {
            long size = DataCleanUtil.getFolderSize(cache);
            String sizeNew = String.valueOf(size / 1024 / 1024);
            activityMySettingClearCacheHowMuch.setText(sizeNew + "MB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        windowHeadLayout.setBackgroundColor(getResources().getColor(R.color.businesstop));
        windowHeadLeftText.setVisibility(View.GONE);
        windowHeadLeftImage.setImageResource(R.drawable.ease_mm_title_back);
        windowHeadName.setText("设置");
        windowHeadName.setTextColor(getResources().getColor(R.color.white));
        windowHeadRightLayout.setVisibility(View.GONE);
//        versionte.setText("当前版本 V"+MainApplication.getVersionName());
        versionte.setText("V"+MainApplication.getVersionName());
        ycsw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ifone>0){
                    nosee(isChecked);
                }
            }
        });
    }

    @OnClick({R.id.window_head_left_image_layout, R.id.ycsw,R.id.activity_my_setting_account_safe,R.id.rl_check_update, R.id.activity_my_setting_about_us, R.id.activity_my_setting_networking_protocol, R.id.activity_my_setting_complaint_advice, R.id.activity_my_setting_clear_cache, R.id.activity_my_setting_quit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.window_head_left_image_layout:
                finish();
                break;
            case R.id.activity_my_setting_account_safe://账号安全
//                startActivity(new Intent(MySettingActivity.this, MyAccountSafeActivity.class));
                startActivity(new Intent(MySettingActivity.this, ChangePasswordActivity.class));
                break;
            case R.id.activity_my_setting_about_us://关于我们
                startActivity(new Intent(MySettingActivity.this, AboutUsActivity.class));
                break;
            case R.id.activity_my_setting_networking_protocol://网络协议
                startActivity(new Intent(MySettingActivity.this, NetworkDetailActivity.class));
                break;
            case R.id.activity_my_setting_complaint_advice://投诉建议
                startActivity(new Intent(MySettingActivity.this, ComplaintAdviceActivity.class));
                break;
            case R.id.rl_check_update://检测更新
                updapp();
//                dad();
                break;
            case R.id.activity_my_setting_clear_cache://清理缓存
                DataCleanUtil.cleanInternalCache(this);
                Utils.showShortToast(getApplicationContext(), "清除缓存成功");
                File cache = getCacheDir();//获取缓存
                try {
                    long size = DataCleanUtil.getFolderSize(cache);
                    String sizeNew = String.valueOf(size);
                    activityMySettingClearCacheHowMuch.setText(sizeNew + "M");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.activity_my_setting_quit:
               showNormalDialogOne();
                break;
        }
    }
    private void showNormalDialogOne() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定退出账号吗！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        outlogin();
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

    private void outlogin(){
        // UMENG 统计退出登陆
        MobclickAgent.onProfileSignOff();
        JPushInterface.deleteAlias(MySettingActivity.this, JPushUtil.sequence);
        UserNative.saveIsLogin(false);
        UserNative.clearData();
        AppManager.getAppManager().AppExit(this);
        if (!TextUtils.isEmpty(EMClient.getInstance().getCurrentUser())) {
            activityMySettingQuit.setText(getString(R.string.button_logout) + "(" + EMClient.getInstance().getCurrentUser() + ")");
        }
        //环信退出
        final ProgressDialog pd = new ProgressDialog(this);
        String st = getResources().getString(R.string.Are_logged_out);
        pd.setMessage(st);
        pd.setCanceledOnTouchOutside(false);
        pd.show();
        DemoHelper.getInstance().logout(false, new EMCallBack() {

            @Override
            public void onSuccess() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        JPushUtil.deleteJPushAlias(MySettingActivity.this);
                        pd.dismiss();
                        UserNative.saveIsLogin(false);
//                        startActivity(new Intent(MySettingActivity.this, MainActivity.class));
//                        EventBus.getDefault().post(new OutLoginEvent());
                        MyShareUtil.sharedPint("ifchat",0);
                        finish();
                    }
                });
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        pd.dismiss();
                        ToastUtil.showWarning(MySettingActivity.this, "unbind devicetokens failed", Toast.LENGTH_SHORT);
                    }
                });
            }
        });
    }

    private void updapp() {
        String url = Url.updload;
        RequestParams params = new RequestParams();
        params.put("versionCode", MainApplication.getVersionCode());//用户id
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(MySettingActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq","upd............."+s);
//                http://www.tonixtech.com/yuanxinbuluo//upload/images/advertisement/20170509102158447.png
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        updateContent = array.getJSONArray("updateContent");
                        String url = array.getString("url");
//                        String url = "http://www.tonixtech.com/yuanxinbuluo//upload/images/advertisement/20170509102158447.png";
                        int web = array.getInt("versionCode");
                        int loca = MainApplication.getVersionCode();
                        if (web>loca){
                            dad();
                        }else {
                            ToastUtil.showInfo(MySettingActivity.this, "当前是最新版本", Toast.LENGTH_SHORT);
                        }
                        wbovs = String.valueOf(web);
                        Log.v("lgq","======="+web+"...."+url+"....."+updateContent+"..."+loca);
                        MyShareUtil.sharedPstring(R.string.APP_UPDATE_URL,url);

                    }else {
                        ToastUtil.showWarning(MySettingActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(MySettingActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    //更新dialog
    public void dad() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(MySettingActivity.this);
        View view = inflater.inflate(R.layout.updateappla, null);
        dialog = new Dialog(MySettingActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);
//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        lp.width = Utils.getScreenWidth(MySettingActivity.this) - 40; // 宽度
        lp.alpha = 0.7f; // 透明度
        lp.height = Utils.getViewMeasureHeight(view) + 100; // 高度
        dialogWindow.setAttributes(lp);


        dialog.setCanceledOnTouchOutside(true);

        TextView qd = (TextView)view.findViewById(R.id.qdapk);
        TextView qx = (TextView)view.findViewById(R.id.qcapk);
        TextView co = (TextView)view.findViewById(R.id.qcapkcontent);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < updateContent.length(); i++) {
            try {
                if (i == updateContent.length()-1) {
                    sb.append((i+1) + "、" + updateContent.getString(i));
                } else {
                    sb.append((i+1) + "、" + updateContent.getString(i) + "\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        co.setText(sb.toString());
//        co.setText(updateContent);
        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(MySettingActivity.this, UpdateAppService.class);
                updateIntent.putExtra("titleId", R.string.app_name);
                updateIntent.putExtra(UpdateAppService.APK_VERSION, wbovs);
                MySettingActivity.this.startService(updateIntent);
                dialog.dismiss();
            }
        });
        qx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void nosee(Boolean check) {
        String url = Url.updateById;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        params.put("epId", UserNative.getEpId());//用户id

        if (check){
            params.put("telFlg", 1);
        }else {
            params.put("telFlg", 0);//用户id
        }
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
    private void getstatus() {
        String url = Url.getById;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        params.put("epId", UserNative.getEpId());//用户id
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
                        JSONObject array = object.getJSONObject("data");
                        String telFlg = array.getString("telFlg");
                        if (telFlg.equals("0")){
                            ycsw.setChecked(false);
                        }else {
                            ycsw.setChecked(true);
                        }
                        ifone++;
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
//    private void getseestatus() {
//        String url = Url.getEpInfo;
//        RequestParams params = new RequestParams();
//        params.put("userId", UserNative.getId());//用户id
//        params.put("epId", UserNative.getEpId());//用户id
//        doHttpPost(url, params, new BaseActivity.RequestCallback() {
//            @Override
//            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
//            }
//
//            @Override
//            public void onSuccess(int i, Header[] headers, String s) {
//                try {
//                    JSONObject object = new JSONObject(s);
//                    if (object.getString("success").equals("true")) {
//                        JSONObject array = object.getJSONObject("data");
//                        String telFlg = array.getString("telFlg");
//                        if (telFlg.equals("0")){
//                            ifshow=false;
////                            ycsw.setChecked(false);
//                        }else {
//                            ifshow=true;
//                            ycsw.setChecked(true);
//                        }
////                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
//                    } else {
//                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
//                    }
//                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
//                }
//            }
//        });
//    }
}

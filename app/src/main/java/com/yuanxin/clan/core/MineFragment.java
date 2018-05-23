package com.yuanxin.clan.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.AppointmentActivity;
import com.yuanxin.clan.core.activity.BecomeCompanyLeaderActivity;
import com.yuanxin.clan.core.activity.CompleteEpActivity;
import com.yuanxin.clan.core.activity.DefeatedEpEditxActivity;
import com.yuanxin.clan.core.activity.MyActivityactivity;
import com.yuanxin.clan.core.activity.MyBusinessDistrictActivity;
import com.yuanxin.clan.core.activity.MyBusinsCActivity;
import com.yuanxin.clan.core.activity.MyCollectActivity;
import com.yuanxin.clan.core.activity.MyInfoActivity;
import com.yuanxin.clan.core.activity.MyMarketActivity;
import com.yuanxin.clan.core.activity.MyNewActivity;
import com.yuanxin.clan.core.activity.MyOrderActivity;
import com.yuanxin.clan.core.activity.MySettingActivity;
import com.yuanxin.clan.core.activity.MyaccountActivity;
import com.yuanxin.clan.core.activity.MyserviceActivity;
import com.yuanxin.clan.core.activity.OneCardActivity;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.activity.TeamworkBusinessActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.view.CompanyInfoActivity;
import com.yuanxin.clan.core.event.OutLoginByJpushEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.market.view.GoodsAddressActivity;
import com.yuanxin.clan.core.market.view.ShoppingCartActivity;
import com.yuanxin.clan.core.mineclass.HelpCentreActivity;
import com.yuanxin.clan.core.mineclass.MyAllgxActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan
 * Describe: 个人中心
 * Author: xjc
 * Date: 2017/6/19 0019 13:04
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class MineFragment extends BaseFragment {


    @BindView(R.id.activity_five_head_image)
    ImageView activityFiveHeadImage;
    @BindView(R.id.activity_five_name)
    TextView activityFiveName;
    @BindView(R.id.activity_five_company_name)
    TextView activityFiveCompanyName;
    @BindView(R.id.unread_msg_number)
    TextView unread_msg_number;
    @BindView(R.id.ep_numberte)
    TextView ep_numberte;
    @BindView(R.id.business_number)
    TextView business_number;
    @BindView(R.id.service_number)
    TextView service_number;
    @BindView(R.id.activity_five_company_position)
    TextView activityFiveCompanyPosition;
    @BindView(R.id.activity_five_other_company_name)
    TextView activityFiveOtherCompanyName;
    @BindView(R.id.activity_five_other_company_position)
    TextView activityFiveOtherCompanyPosition;
    @BindView(R.id.activity_five_head_layout)
    LinearLayout activityFiveHeadLayout;
    @BindView(R.id.activity_five_companyInfo_layout)
    LinearLayout activityFiveCompanyInfoLayout;
    @BindView(R.id.activity_five_my_business_layout)
    LinearLayout activityFiveMyBusinessLayout;
    @BindView(R.id.activity_five_my_collect_layout)
    LinearLayout activityFiveMyCollectLayout;
    @BindView(R.id.activity_five_my_Info_layout)
    LinearLayout activityFiveMyInfoLayout;
    @BindView(R.id.activity_five_company_business_card_layout)
    LinearLayout activityFiveCompanyBusinessCardLayout;
    @BindView(R.id.activity_five_company_business_card_layouttwo)
    LinearLayout activityFiveCompanyBusinessCardLayouttwo;
    @BindView(R.id.activity_my_shopping_car_layout)
    LinearLayout activityMyShoppingCarLayout;
    @BindView(R.id.activity_five_my_market_layout)
    LinearLayout activityFiveMyMarketLayout;
    @BindView(R.id.activity_five_my_goods_address_layout)
    LinearLayout activityFiveMyGoodsAddressLayout;
//    @BindView(R.id.activity_five_my_crowd_funding_layout)
//    RelativeLayout activityFiveMyCrowdFundingLayout;
//    @BindView(R.id.activity_five_bargain_layout)
//    RelativeLayout activityFiveBargainLayout;
    @BindView(R.id.activity_five_my_teamwork_company_layout)
    LinearLayout activityFiveMyTeamworkCompanyLayout;
    @BindView(R.id.activity_five_my_teamwork_company_layouttwo)
    LinearLayout activityFiveMyTeamworkCompanyLayouttwo;
    @BindView(R.id.activity_five_my_setting)
    LinearLayout activityFiveMySetting;
    @BindView(R.id.activity_five_my_settingtwo)
    LinearLayout activityFiveMySettingtwo;
    @BindView(R.id.activity_five_my_account_layout)
    LinearLayout activityFiveMyAccountLayout;
    @BindView(R.id.zaixiankefulitwo)
    LinearLayout zaixiankefulitwo;
    @BindView(R.id.activity_f_layoutre)
    LinearLayout activity_f_layoutre;
    @BindView(R.id.activity_f_yuyuetre)
    LinearLayout activity_f_yuyuetre;
    @BindView(R.id.activity_f_layoutregxli)
    LinearLayout activity_f_layoutregxli;
//    @BindView(R.id.lipingkuli)
//    LinearLayout lipingkuli;
    @BindView(R.id.zaixianfuwuli)
    LinearLayout zaixianfuwuli;
    private String mdata, reason,epAccessPath;
    private int epRole;
    private int status;
    private More_LoadDialog mMore_loadDialog;
    private int visitNumber = 0;
    private int businessNumber = 0;
    private int serviceNumber = 0;


    @Override
    public int getViewLayout() {
        return R.layout.new_mine_five_layout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mMore_loadDialog = new More_LoadDialog(getContext());
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        getUserInfo(false);

        getUserInfo(UserNative.getPhone());

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OutLoginByJpushEvent outLoginEvent) {
//        Logger.e("out login");
        getUserInfo(false);
//        finish();
    }


    @Override
    public void onResume() {
        super.onResume();
        getCompanyBusinessServiceNumber();
        getUserInfo(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * @param fromLocal
     *  true: 只从本地缓存中读取个人信息刷新UI
     *  false: 从本地缓存以及网络读取个人信息刷新UI
     * */
    private void getUserInfo(Boolean fromLocal) {
        if (! UserNative.readIsLogin()){
            activityFiveName.setText("未登录");
            activityFiveCompanyName.setText("登录后，可查看更多精彩内容");
//            activityFiveHeadImage.setBackgroundResource(R.drawable.chat_icon_personal);
            activityFiveHeadImage.setImageResource(R.drawable.chat_icon_personal);
            ep_numberte.setText("0");
            business_number.setText("0");
            service_number.setText("0");
            unread_msg_number.setVisibility(View.INVISIBLE);
        }else {
            if (!TextUtil.isEmpty(UserNative.getEpRole())) {
                try {
                    epRole = Integer.valueOf(UserNative.getEpRole());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            ImageManager.loadCircleImage(getActivity(), UserNative.getImage(), R.drawable.list_img, activityFiveHeadImage);
            if (TextUtil.isEmpty(UserNative.getEpNm())){
                activityFiveCompanyName.setText("");
//                activityFiveCompanyName.setText("（待完善资料）");
            }else {
                activityFiveCompanyName.setText(TextUtil.formatString(UserNative.getEpNm()));
            }
            if (TextUtil.isEmpty(UserNative.getName())){
                activityFiveName.setText(TextUtil.formatString(UserNative.getPhone()));
            }else {
                activityFiveName.setText(TextUtil.formatString(UserNative.getName()));
            }

            if (!fromLocal) {
                getUserInfo(UserNative.getPhone());
                getselectByUserId();
                getUserInfoyuyue();
            }
        }
        activityFiveCompanyPosition.setText(TextUtil.formatString(UserNative.getEpPosition()));
        activityFiveOtherCompanyName.setText(TextUtil.formatString(UserNative.getCompany()));
        activityFiveOtherCompanyPosition.setText(TextUtil.formatString(UserNative.getExpertPosition()));
    }


    @OnClick({R.id.order_manage, R.id.activity_f_yuyuetre,R.id.zaixianfuwuli,R.id.zaixiankefulitwo,R.id.activity_f_layoutre, R.id.activity_five_my_account_layout,
            R.id.activity_five_company_business_card_layout, R.id.activity_my_shopping_car_layout,R.id.activity_f_layoutregxli,
            R.id.activity_five_head_layout, R.id.activity_five_companyInfo_layout, R.id.activity_five_my_Info_layout, R.id.activity_five_my_collect_layout, R.id.activity_five_my_business_layout, R.id.activity_five_my_market_layout,
            R.id.activity_five_my_teamwork_company_layout,R.id.activity_five_my_teamwork_company_layouttwo,
            R.id.activity_five_company_business_card_layouttwo,R.id.activity_five_my_settingtwo,R.id.activity_five_my_setting, R.id.activity_five_my_goods_address_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zaixianfuwuli://我的服务
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyserviceActivity.class));
                break;
            case R.id.activity_f_layoutregxli:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyAllgxActivity.class));
                break;
//            case R.id.lipingkuli://礼品定制
//                startActivity(new Intent(getActivity(), LipingkuActivity.class));
//                break;
            case R.id.activity_f_yuyuetre://我的预约
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), AppointmentActivity.class));
                break;
            case R.id.activity_f_layoutre://我的活动
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyActivityactivity.class));
                break;
            case R.id.activity_five_my_account_layout://我的账户
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyaccountActivity.class));//未完成
                break;
            case R.id.activity_five_company_business_card_layout://企业名片
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//                startActivity(new Intent(getActivity(), CompanyBusinessCardActivity.class));
                if (TextUtil.isEmpty(UserNative.getEpId())||UserNative.getEpId().equals("0")){
                    startActivity(new Intent(getActivity(), OneCardActivity.class));
                    return;
                }
                if (epRole!=1){
                    startActivity(new Intent(getActivity(), OneCardActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyBusinsCActivity.class));
                break;
            case R.id.activity_five_company_business_card_layouttwo:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                if (TextUtil.isEmpty(UserNative.getEpId())||UserNative.getEpId().equals("0")){
                    startActivity(new Intent(getActivity(), OneCardActivity.class));
                    return;
                }
                if (epRole!=1){
                    startActivity(new Intent(getActivity(), OneCardActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyBusinsCActivity.class));
                break;
            case R.id.activity_my_shopping_car_layout://购物车
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), ShoppingCartActivity.class));
                break;
            case R.id.zaixiankefulitwo://帮助中心
                startActivity(new Intent(getActivity(), HelpCentreActivity.class));
                break;
            case R.id.activity_five_head_layout://个人信息
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                Intent intent = new Intent(getActivity(), MyInfoActivity.class);
                startActivityForResult(intent, 402);//请求码
                break;   
            case R.id.activity_five_companyInfo_layout://企业资料
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                if (status==0&&!TextUtil.isEmpty(mdata)){
                    ToastUtil.showInfo(getActivity(), "企业审核中！", Toast.LENGTH_SHORT);
                    return;
                }else if (status==2){
//                    ToastUtil.showWarning(getActivity(), "企业审核失败！", Toast.LENGTH_SHORT);
                    getaddInfo();

                    return;
                }
                if (UserNative.getEpId().equals("0")) {
                    dad();
//                    showComapnyDialog();
//                    showComapnyDialogdefe();
                } else {
                    if (epRole==1){//判断出错
                        Intent intentOne = new Intent(getActivity(), CompanyInfoActivity.class).putExtra("epAccessPath",epAccessPath);
                        startActivityForResult(intentOne, 408);//请求码
                    }else {
                        com.yuanxin.clan.core.util.Utils.gotoCompanyDetail(getContext(), Integer.parseInt(UserNative.getEpId()), epAccessPath);

                    }
                }
                break;
//            case R.id.activity_five_specialist_info_layout://专家资料
//                getThinkTankDetailInfo();
//                break;
            case R.id.activity_five_my_Info_layout://我的资讯
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyNewActivity.class));
                break;
            case R.id.activity_five_my_collect_layout://我的收藏
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                startActivity(new Intent(getActivity(), TeamworkBusinessActivity.class));
                break;
            case R.id.activity_five_my_business_layout://我的商圈
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyBusinessDistrictActivity.class));
                break;
//            case R.id.activity_five_my_crowd_funding_layout://我的众筹
//                startActivity(new Intent(getActivity(), MyCrowdFundingActivity.class));
//                break;
            case R.id.activity_five_my_market_layout://在售商品
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyMarketActivity.class));
                break;
            case R.id.order_manage:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyOrderActivity.class));//源我的订单
                break;
//            case R.id.activity_five_bargain_layout://我的合同
//                startActivity(new Intent(getActivity(), NewMyBargainActivity.class));
//                break;
            case R.id.activity_five_my_teamwork_company_layout://合作商家
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//                startActivity(new Intent(getActivity(), TeamworkBusinessActivity.class));
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
            case R.id.activity_five_my_teamwork_company_layouttwo:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), MyCollectActivity.class));
                break;
            case R.id.activity_five_my_setting://设置
                startActivity(new Intent(getActivity(), MySettingActivity.class));
                break;
            case R.id.activity_five_my_settingtwo:
                startActivity(new Intent(getActivity(), MySettingActivity.class));
                break;
            case R.id.activity_five_my_goods_address_layout://收货地址
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
                startActivity(new Intent(getActivity(), GoodsAddressActivity.class));
                break;
        }
    }

    private void getselectByUserId() {
        String url = Url.selectByUserId;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getActivity(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        mdata = object.getString("data");
                        if (!mdata.equals("null")) {
                            JSONObject jsonObject = new JSONObject(mdata);
                            status = jsonObject.getInt("status");
                            if (status ==2){
                                getEpInfo();
                            }
                        }
                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getActivity(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.d("json 解析出错");
                }
            }
        });

    }

    private void getEpInfo() {
        String url = Url.getEpInfo;
        RequestParams params = new RequestParams();
        params.put("epId", UserNative.getEpId());//收藏项目ID newsId
        params.put("userId", UserNative.getId());//省userId
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showWarning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        try {
                            JSONObject ep = object.getJSONObject("data");
                            reason = ep.getString("reason");
                        }catch (Exception e) {

                        }
                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                   e.printStackTrace();
                }
            }
        });
    }

    private void getCompanyBusinessServiceNumber() {
        String url = Url.personCenter;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        String data = object.getString("data");
                        if (!data.equals("null")) {
                            JSONObject dataJson = new JSONObject(data);
                            visitNumber = dataJson.getInt("cntVisit");
                            businessNumber = dataJson.getInt("cntBusiness");
                            serviceNumber = dataJson.getInt("cntServer");
                            unread_msg_number.setText(""+visitNumber);
                            if (visitNumber>0){
                                unread_msg_number.setVisibility(View.VISIBLE);
                            }else {
                                unread_msg_number.setVisibility(View.INVISIBLE);
                            }
                            business_number.setText("" + businessNumber);
                            service_number.setText("" + serviceNumber);
                            if (UserNative.getEpId().equals("0")) {
                                ep_numberte.setText("0");
                            } else {
                                ep_numberte.setText("1");
                            }
                        }
                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getActivity(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.d("json 解析出错");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }
        });

    }


    private void showComapnyDialog() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("提示");
        normalDialog.setMessage("还没有贵公司资料，请前往填写！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getActivity(), BecomeCompanyLeaderActivity.class));
                        startActivity(new Intent(getActivity(), CompleteEpActivity.class));
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        normalDialog.show();
    }

    //更新dialog
    public void dad() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.intoeplog, null);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();

        dialogWindow.setGravity(Gravity.CENTER);

        lp.width = Utils.getScreenWidth(getActivity()) - 90; // 宽度
        lp.height = Utils.getViewMeasureHeight(view) + 100; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);
//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        LinearLayout qd = (LinearLayout)view.findViewById(R.id.litoepedit);
        TextView qx = (TextView)view.findViewById(R.id.cancelte);
        StringBuilder sb = new StringBuilder();

        qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), BecomeCompanyLeaderActivity.class));
                startActivity(new Intent(getActivity(), CompleteEpActivity.class));
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


    private void showComapnyDialogdefe() {
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(getActivity());
        normalDialog.setTitle("提示");
        normalDialog.setMessage("公司资料审核失败，请前往编辑！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        startActivity(new Intent(getActivity(), BecomeCompanyLeaderActivity.class));
                        startActivity(new Intent(getActivity(), DefeatedEpEditxActivity.class).putExtra("st",reason));
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                });
        normalDialog.show();
    }

    private void getaddInfo() {
        mMore_loadDialog.show();
        String url= Url.getUserEpInfo;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
//        http://192.168.1.200:8080/yuanxinbuluo/user/getUserEpInfo?userId=11455
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showWarning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");

                       String epId = array.getString("epId");
                        Log.i("lgq","ssssss==="+epId);
                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE); //私有数据
                        SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                        editor.putString("epId", epId);
                        editor.commit();//提交修改

                        showComapnyDialogdefe();
                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 402://把个人资料中的名称 头像传过来
                if (resultCode == Activity.RESULT_OK) {
                    getUserInfo(true);
                }
                break;
            case 408://把个人资料中的名称 头像传过来
                if (resultCode == Activity.RESULT_OK) {
                    getUserInfo(true);
                }
                break;
            default:
        }
    }
    //获取用户信息
    private void getUserInfo(String phone) {
        String url = Url.findFriend;
//        String url = urlone+"/user/getUserByPhone";
        RequestParams params = new RequestParams();
        params.put("userPhone", phone);//用户id
        mMore_loadDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMore_loadDialog.dismiss();
//                Toast.makeText(getActivity(), "网络连接异常", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    mMore_loadDialog.dismiss();
                    JSONObject object = new JSONObject(s);
//                    Log.v("lgq","个人信息返回。。。"+s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");
                        String image = Url.img_domain + array.getString("userImage") + Url.imageStyle208x208;
                        String name = array.getString("userNm");
                        epRole = array.getInt("epRole");
                        epAccessPath = array.getString("epAccessPath");
                        if (!TextUtil.isEmpty(array.getString("userImage"))) {
                            ImageManager.loadCircleImage(getActivity(), image, R.drawable.list_img, activityFiveHeadImage);
                        }
                        if (!TextUtil.isEmpty(name)) {
                            activityFiveName.setText(name);
                        }else {
                            activityFiveName.setText(UserNative.getPhone());
                        }
                        if (!TextUtil.isEmpty(array.getString("epNm"))) {
                            activityFiveCompanyName.setText(array.getString("epNm"));
                        }
                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getActivity(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    //获取用户信息
    private void getUserInfoyuyue() {
        String url = Url.getyuyuenum;
//        String url = urlone+"/user/getUserByPhone";
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showWarning(getActivity(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
//                    Log.v("lgq","个人信息返回。。。"+s);
                    if (object.getString("success").equals("true")) {
                        String a = object.getString("data");
                        JSONObject object2 = new JSONObject(a);
                        int bReadCount = object2.getInt("bReadCount");
                        int aReadCount = object2.getInt("aReadCount");
                        int num = bReadCount+aReadCount;
                        unread_msg_number.setText(""+num);
                        if (num>0){
                            unread_msg_number.setVisibility(View.VISIBLE);
                        }else {
                            unread_msg_number.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getActivity(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    //更新dialog
    public void dad(final String phone) {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.phone_e_dialog, null);
        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.AnimViewshow);

//        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        dialogWindow.setGravity(Gravity.CENTER);

//        lp.width = 900; // 宽度
//        lp.height = 650; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        LinearLayout ph = (LinearLayout) view.findViewById(R.id.callphoneli);
        LinearLayout ea = (LinearLayout) view.findViewById(R.id.easelikeli);
        TextView dismiss = (TextView)view.findViewById(R.id.dissmisste);
        StringBuilder sb = new StringBuilder();

        ea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID,phone);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra(Constant.USER_NAME, UserNative.getName());
                intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                String add = UserNative.getCity()+UserNative.getArea()+UserNative.getDetail();
                intent.putExtra(Constant.ADDRESS,add );
                intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                intent.putExtra("type", "圆心客服");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"4006661386"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    public void overEC() {//结束会话
        mMore_loadDialog.show();
        String url = Url.getServerPhone;
        RequestParams params = new RequestParams();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//               ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String ph = object.getString("data");
                        dad(ph);
//                        Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();

                    } else {
                        ToastUtil.showWarning(getActivity(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }


}


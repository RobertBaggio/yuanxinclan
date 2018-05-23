package com.yuanxin.clan.core.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
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

/**
 * ProjectName: yuanxinclan
 * Date: 2017/7/1 0001 13:44
 * Copyright 搜索类
 */

public class TiXianActivity extends BaseActivity implements View.OnClickListener{

    private LinearLayout backacli;
    private String accountpassword,mymoney;
    private TextView typete,txaccountte,txnamete,yuerte;
//    private EditText moneyet,passwordet;
    private EditText passwordet;
    private Button mButton;
    private PopupWindow popupWindow;
    private String wxname,wxaccount;
    private String aliname,aliaccount;
    private int type;

    @BindView(R.id.tixianetmoneyet)
    EditText moneyet;

    @Override
    public int getViewLayout() {
        return R.layout.tixianla;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        backacli =(LinearLayout)findViewById(R.id.backacli);
        typete =(TextView)findViewById(R.id.tixiantypete);
        yuerte =(TextView)findViewById(R.id.yuerte);
        txaccountte =(TextView)findViewById(R.id.tixianaccountte);
        txnamete =(TextView)findViewById(R.id.tixianname);
//        moneyet =(EditText)findViewById(R.id.tixianetmoneyet);
        passwordet =(EditText)findViewById(R.id.tixianpasswordet);
        mButton =(Button)findViewById(R.id.szzfmmbt_register);
        backacli.setOnClickListener(this);
        mButton.setOnClickListener(this);
        typete.setOnClickListener(this);
        wxaccount = UserNative.getWxaccount();
        aliaccount =UserNative.getAliaccount();
        wxname = UserNative.getWxname();
        aliname =UserNative.getAliname();
        mymoney=UserNative.getAcountBalance();
        yuerte.setText(mymoney);
        if (wxaccount.length()>3){
            if (TextUtil.isEmpty(wxaccount)){
                txaccountte.setText("");
                txnamete.setText("");
            }else {
                txaccountte.setText(wxaccount);
                txnamete.setText(wxname);
            }

        }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.backacli:
                onBackPressed();
                break;
            case R.id.szzfmmbt_register:
                String name = moneyet.getText().toString();
                if (TextUtil.isEmpty(name)){
                    ToastUtil.showInfo(TiXianActivity.this, "请输入金额", Toast.LENGTH_SHORT);
                    return;
                }
//                double = Integer.parseInt(name);
                double a = Double.valueOf(name);
                double b =  Double.valueOf(mymoney);
                Log.v("lgq","....yuer=="+b);
                String account = passwordet.getText().toString();
                accountpassword = UserNative.getAccountPwd();
                if (accountpassword.length()<5){
                    ToastUtil.showInfo(TiXianActivity.this, "请设置支付密码", Toast.LENGTH_SHORT);
                    startActivity(new Intent(TiXianActivity.this, SetmmActivity.class));
                    return;
                }
                if (!accountpassword.equals(account)){
                    ToastUtil.showInfo(TiXianActivity.this, "支付密码错误", Toast.LENGTH_SHORT);
                    return;
                }
                if (a<1){
                    ToastUtil.showInfo(TiXianActivity.this, "请输入金额", Toast.LENGTH_SHORT);
                    return;
                }
                if (a>b){
                    ToastUtil.showInfo(TiXianActivity.this, "余额不足", Toast.LENGTH_SHORT);
                    return;
                }
                commitpassword();
                break;
            case R.id.nanselect:
                typete.setText("微信");
                if (!TextUtil.isEmpty(wxaccount)){
                    txaccountte.setText(wxaccount);
                    txnamete.setText(wxname);
                }
                type = 1;
                popupWindow.dismiss();
                break;
            case R.id.nvselect:
                typete.setText("支付宝");
                if (!TextUtil.isEmpty(aliaccount)){
                    txaccountte.setText(aliaccount);
                    txnamete.setText(aliname);
                }
                type = 2;
                popupWindow.dismiss();
                break;
            case R.id.tixiantypete:
                showxiala();
                break;

        }}
    //    wechatAcount   wechatNm
    private void commitpassword(){
        String url = Url.tixianurl;
        String money = moneyet.getText().toString();
        String password = passwordet.getText().toString();

        RequestParams params = new RequestParams();
        params.put("acountId", UserNative.getAccoutid());
        params.put("userId", UserNative.getId());
        params.put("userNm", UserNative.getName());
        params.put("reflectType", type);
        params.put("reflectBalance", money);
        params.put("accountPwd", password);
//        Log.v("lgq",".......acountId="+UserNative.getAccoutid()+"...accountPwd="+password+"...userid="+UserNative.getId()+"...d登录="+UserNative.getName());
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(TiXianActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.v("lgq","绑定返回。。。。"+s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(TiXianActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        onBackPressed();
                    } else {
                    }
                } catch (JSONException e) {
//                    Toast.makeText(TiXianActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }
    public void showxiala() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.neirong, null);
        contentview.setFocusable(true); // 这个很重要
        contentview.setFocusableInTouchMode(true);
        popupWindow = new PopupWindow(contentview, 200, 250);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        TextView tt = (TextView) contentview.findViewById(R.id.nanselect);
        TextView nv = (TextView) contentview.findViewById(R.id.nvselect);
        tt.setOnClickListener(this);
        nv.setOnClickListener(this);

        contentview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow.dismiss();

                    return true;
                }
                return false;
            }
        });
        popupWindow.setBackgroundDrawable(new Drawable() {
            @Override
            public void draw(Canvas canvas) {

            }

            @Override
            public void setAlpha(int alpha) {

            }

            @Override
            public void setColorFilter(ColorFilter colorFilter) {

            }

            @Override
            public int getOpacity() {
                return 0;
            }
        });
        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popupWindow.showAsDropDown(typete);
        popupWindow.showAtLocation(typete, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.entity.WelcomeBannerEntity;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/2/2.
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.register_first)
    EditText registerFirst;
    @BindView(R.id.register_two)
    EditText registerTwo;
    @BindView(R.id.register_three)
    EditText registerThree;
    @BindView(R.id.register_button)
    Button registerButton;
    private String username, pwd, three;
    private SubscriberOnNextListener registerOnNextListener;
    private List<WelcomeBannerEntity> bannerEntity = new ArrayList<WelcomeBannerEntity>();


    @Override
    public int getViewLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        registerButton.setOnClickListener(this);
//        initOnNext();
//        registerButton.setOnClickListener(this);
    }

//    private void initOnNext() {
////        Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
//        registerOnNextListener=new SubscriberOnNextListener<BaseJsonEntity>() {
//
//            @Override
//            public void onNext(BaseJsonEntity t) {
////                Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
////                if(t.getStatus()==1){
////                    Toast.makeText(getApplicationContext(),t.getInfo(),Toast.LENGTH_SHORT).show();
////                    MyApplication.userPF.savedData(t.getData());
////                    MyApplication.userPF.savedTel(username);
////                    MyApplication.userPF.savedPassword(pwd);
////                    MyApplication.userPF.savedCity(three);
////                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
////                }else {
////                    Toast.makeText(getApplicationContext(),t.getInfo(),Toast.LENGTH_SHORT).show();
////                }
//            }
//        };
//    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_button:
//                Toast.makeText(getApplicationContext(),"1",Toast.LENGTH_SHORT).show();
                username = registerFirst.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入账号", Toast.LENGTH_SHORT);
                    registerFirst.requestFocus();
                    break;
                }
                pwd = registerTwo.getText().toString().trim();
                if (TextUtils.isEmpty(pwd)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入密码", Toast.LENGTH_SHORT);
                    registerTwo.requestFocus();
                    break;
                }
                three = registerThree.getText().toString().trim();
                if (TextUtils.isEmpty(three)) {
                    ToastUtil.showInfo(getApplicationContext(), "请输入城市名称", Toast.LENGTH_SHORT);
                    registerThree.requestFocus();
                }
//                HttpMethods.getInstance().register(new ProgressSubscriber(registerOnNextListener,RegisterActivity.this),username,pwd,three);

//                if(!TextUtils.isEmpty(first)&&!TextUtils.isEmpty(two)&&!TextUtils.isEmpty(three)){
//                    final ProgressDialog pd=new ProgressDialog(this);
//                    pd.setMessage("Registering...");
//                    pd.show();
//                }
//                new Thread(new Runnable(){
//                    public void run(){
//                        try{
//                            EMClient.getInstance().createAccount(username,pwd);
//                            HttpMethods.getInstance().register(new ProgressSubscriber(registerOnNextListener,RegisterActivity.this),username,pwd,three);
//
//                        }catch (final HyphenateException e){
//                            Toast.makeText(getApplicationContext(),"注册失败",Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                }).start();
//                HttpMethods.getInstance().register(new ProgressSubscriber<RenPayJsonEntity>(registerOnNextListener,RegisterActivity.this),first,two,three);
//                HttpMethods.getInstance().register(new ProgressSubscriber(registerOnNextListener,RegisterActivity.this),first,two,three);


        }

    }


}

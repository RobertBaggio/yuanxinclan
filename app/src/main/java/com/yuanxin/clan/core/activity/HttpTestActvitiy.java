package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Api;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.mvp.view.BaseActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;

/**
 * Created by lenovo1 on 2017/3/30.
 */
public class HttpTestActvitiy extends BaseActivity {

    @Override
    public int getViewLayout() {
        return R.layout.activity_five;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        intView();
    }

    private void intView() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://119.23.144.62:8080/yuanxinbuluo/")
                .addConverterFactory(FastJsonConverterFactory.create())
                .build();

        Api service = retrofit.create(Api.class);
        Call<BaseJsonEntity> call = service.login("13888888888", "123456");
        call.enqueue(new Callback<BaseJsonEntity>() {
            @Override
            public void onResponse(Call<BaseJsonEntity> call, Response<BaseJsonEntity> response) {
            }

            @Override
            public void onFailure(Call<BaseJsonEntity> call, Throwable t) {
            }
        });
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://192.168.1.111:8080/yuanxinbuluo/")
//                .addConverterFactory(FastJsonConverterFactory.create())
//                .build();
//
//        Api service = retrofit.create(Api.class);
//        Call<BaseJsonEntity> call = service.getById(15);
//        call.enqueue(new Callback<BaseJsonEntity>() {
//            @Override
//            public void onResponse(Call<BaseJsonEntity> call, Response<BaseJsonEntity> response) {
//                Toast.makeText(getApplicationContext(),"成功",Toast.LENGTH_SHORT).show();
//            }
//            @Override
//            public void onFailure(Call<BaseJsonEntity> call, Throwable t) {
//                Toast.makeText(getApplicationContext(),"失败",Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }


}

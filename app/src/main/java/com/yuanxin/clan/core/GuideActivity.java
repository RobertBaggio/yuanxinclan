package com.yuanxin.clan.core;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.MainApplication;

/**
 * ProjectName: yuanxinclan
 * Describe:启动页
 * Author: xjc
 * Date: 2017/6/6 0006 9:02
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class GuideActivity extends AppCompatActivity {

    private LocationClient mLocClient;
    static BDLocation lastLocation = null;
    public MyLocationListenner myListener = new MyLocationListenner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 隐藏标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        final int vc = MyShareUtil.getSharedInt("vc");
        final int topin = MyShareUtil.getSharedInt("topin");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (topin!=1||MainApplication.getVersionCode()>vc){
                    Intent intent2 = new Intent();
                    intent2.putExtra("type",1);
                    intent2.setClass(GuideActivity.this, com.yuanxin.clan.core.activity.GuideActivity.class);
                    startActivity(intent2);
                    finish();
                    return;
                }

                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                GuideActivity.this.finish();
            }
        }, 1500);
//        MyShareUtil.s
        showMapWithLocationClient();


    }


    private void showMapWithLocationClient() {

        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// open gps
        // option.setCoorType("bd09ll");
        // Johnson change to use gcj02 coordination. chinese national standard
        // so need to conver to bd09 everytime when draw on baidu map
        option.setCoorType("gcj02");
        option.setScanSpan(30000);
        option.setAddrType("all");
        mLocClient.setLocOption(option);
    }

    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null) {
                return;
            }
            Log.d("map", "On location change received:" + location);
            Log.d("map", "addr:" + location.getAddrStr());

            if (lastLocation != null) {
                if (lastLocation.getLatitude() == location.getLatitude() && lastLocation.getLongitude() == location.getLongitude()) {
                    Log.d("map", "same location, skip refresh");
                    // mMapView.refresh(); //need this refresh?
                    return;
                }
            }
            lastLocation = location;
            String city = lastLocation.getCity();
            double lat = lastLocation.getLatitude();
            double lot = lastLocation.getLongitude();
            Log.i("map","............"+city+"........"+lat+"......."+lot);


            MyShareUtil.sharedPstring("city",city);
            MyShareUtil.sharedPstring("lot",String.valueOf(lot));
            MyShareUtil.sharedPstring("lat",String.valueOf(lat));
//            mBaiduMap.animateMapStatus(u);
        }
    }

    public void navigationToActivity(Class cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mLocClient != null) {
            mLocClient.start();
        }
    }
}

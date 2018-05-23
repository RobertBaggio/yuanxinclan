package com.yuanxin.clan.core.huanxin;

import android.app.Activity;
import android.os.Bundle;

import com.yuanxin.clan.R;

/**
 * Created by lenovo1 on 2017/3/9.
 */
//视频
public class VideoCallActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//
//        unregisterReceiver(changeCityReceiver);
//
//        home = false;
    }
}

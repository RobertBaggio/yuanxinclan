package com.yuanxin.clan.core.huanxin;

import android.app.Activity;
import android.os.Bundle;

import com.yuanxin.clan.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lenovo1 on 2017/3/9.
 */
public class VoiceCallActivity extends Activity {
    public Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_call);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
//
//        unregisterReceiver(changeCityReceiver);
//
//        home = false;
    }
}

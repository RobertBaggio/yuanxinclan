package com.yuanxin.clan.core.activity;

import android.app.Activity;
import android.os.Bundle;

import com.yuanxin.clan.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by lenovo1 on 2017/5/14.
 */
public class AddGroupsNumberActivity extends Activity {
    public Unbinder unbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_epchat);
        unbinder = ButterKnife.bind(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbinder.unbind();
    }


}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

/**
 * Created by lenovo1 on 2017/3/6.
 */
public class PersonageChatActivity extends BaseActivity {

    @Override
    public int getViewLayout() {
        return R.layout.activity_personage_chat;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }
}

package com.yuanxin.clan.core.zxing;

import android.os.Bundle;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.FragmentOldContactList;
import com.yuanxin.clan.core.news.OneNewsFragment;
import com.yuanxin.clan.mvp.view.BaseFragment;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/4 0004 16:46
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class SubActivity extends BaseFragmentActivity {

    private SubBasicFragment contentFg;
    private BaseFragment contentbFg;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.sub_activity);
        switchContent();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState == null)
            return;
        outState.clear();
        outState.putAll(getIntent().getExtras());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            return;
        getIntent().getExtras().clear();
        getIntent().getExtras().putAll(savedInstanceState);
        switchContent();
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void switchContent() {
        Bundle bundle = getIntent().getExtras();
        int nFragmentId = bundle.getInt(FragmentID.ID);
        SubBasicFragment fg = null;
        BaseFragment bfg = null;

        switch (nFragmentId) {
            case FragmentID.CAPTURE_FRAGMENT:
                fg = new ScanQrCodeFragment();
                break;
            case FragmentID.TO_TONGXUNLU:
                fg = new FragmentOldContactList();
                break;
            case FragmentID.FRIEND_FRAGMENT:
                fg = new OneNewsFragment();
                break;
        }

        if (fg != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.sub_content, fg).commit();
            contentFg = fg;
        }
        if (bfg != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.sub_content, bfg).commit();
            contentbFg = bfg;
        }

    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        if (contentFg != null)
            contentFg.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        contentFg.onDestroy();
        super.onDestroy();
    }

}

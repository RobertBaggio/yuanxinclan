package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.view.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/5 0005 14:16
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class OkPayActivity extends BaseActivity{
    @BindView(R.id.okzhifuli)
    LinearLayout okli;
    @BindView(R.id.nookzhifuli)
    LinearLayout nookli;
    @BindView(R.id.backacli)
    LinearLayout backli;

    @BindView(R.id.qrzfddhaote)
    TextView okddhao;
    @BindView(R.id.qrzfckddte)
    TextView ckddte;
    @BindView(R.id.nookzhifuddhte)
    TextView nookddhao;
    @BindView(R.id.nookzhifulxsjte)
    TextView nooklxkf;

    private String  servicetype;

    @Override
    public int getViewLayout() {
        return R.layout.qrzhifula;
    }
    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
//        EventBus.getDefault().register(this);

        String ord = getIntent().getStringExtra("ord");
        String ifok = getIntent().getStringExtra("ifok");
        servicetype = getIntent().getStringExtra("servicetype");
        Log.v("lgq","l.okpay////.."+ord+"..."+ifok);
//        if (ifok.equals("ok")){
//            okli.setVisibility(View.VISIBLE);
//            nookli.setVisibility(View.GONE);
//        }else {
//            okli.setVisibility(View.GONE);
//            nookli.setVisibility(View.VISIBLE);
//        }
        nookddhao.setText(ord);
        okddhao.setText(ord);

    }
    @OnClick({R.id.qrzfckddte, R.id.nookzhifulxsjte,R.id.backacli})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.backacli:
                onBackPressed();
                break;
            case R.id.qrzfckddte:
                if (servicetype.equals("yes")){
                    startActivity(new Intent(this, MyserviceActivity.class));
                }else {
                    startActivity(new Intent(this, MyOrderActivity.class));
                }

                break;
            case R.id.nookzhifulxsjte:

                break;

            default:
                break;
        }
    }

}

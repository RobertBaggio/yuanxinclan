package com.yuanxin.clan.core.company.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.utils.ToastUtil;

/**
 * ProjectName: new_yuanxin
 * Describe:
 * Author: xjc
 * Date: 2017/8/25 0025 9:18
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class SelectDialog extends AlertDialog {
    private Context mContext;

    public SelectDialog(Context context, int theme) {
        super(context, theme);
        mContext = context;
    }

    public SelectDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.phone_e_dialog);

        TextView textView = (TextView)findViewById(R.id.dissmisste);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showSuccess(mContext, "收藏成功", Toast.LENGTH_SHORT);

            }
        });
    }
}

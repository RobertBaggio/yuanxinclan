package com.yuanxin.clan.core.fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import butterknife.BindView;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2017/12/26 0026 18:00
 */

public class ActivityDetailOneFragment extends BaseFragment{

    @BindView(R.id.activitycontentte)
    TextView activitycontentte;


    protected Bundle fragmentArgs;

    @Override
    public int getViewLayout() {
        return R.layout.activitdetailone;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        fragmentArgs = getArguments();
        String s = MyShareUtil.getSharedString("te");
        activitycontentte.setText(s);

    }
}

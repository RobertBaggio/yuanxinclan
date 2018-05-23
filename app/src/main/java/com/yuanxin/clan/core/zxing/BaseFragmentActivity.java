package com.yuanxin.clan.core.zxing;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.umeng.analytics.MobclickAgent;

public class BaseFragmentActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }


    private String pageFunction = new String();

    /**
     * 设置页面的功能
     *
     * @param pageFunction 功能的详细说明
     */
    public void setPageFunction(String pageFunction) {
        this.pageFunction = pageFunction;
    }

    /**
     * Activity跳转
     *
     */
    public void navigationToSecondActivity(int fragmentID, Bundle bundle) {
        if (bundle == null || fragmentID == 0)
            return;
        bundle.putInt(FragmentID.ID, fragmentID);
        Intent intent = new Intent();
        intent.setClass(BaseFragmentActivity.this, SubActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * Activity跳转
     */
    public void navigationToSecondActivity(int fragmentID) {
        Bundle bundle = new Bundle();
        navigationToSecondActivity(fragmentID, bundle);
    }

}

package com.yuanxin.clan.core.activity;

import android.app.TabActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.yuanxin.clan.R;

/**
 * Created by lenovo1 on 2017/3/6.
 */
public class HuanXinChatActivity extends TabActivity implements RadioGroup.OnCheckedChangeListener {
    private TabHost th;
    private RadioGroup main_buttom_tabbar_group;
    private RadioButton verify_tab;
    private RadioButton order_tab;
    private RadioButton sale_tab;
    private RadioButton center_tab;
    private LinearLayout repairLayout;
    private TextView repairText;

    private static final String VERIFY_ITEM = "verify";
    private static final String ORDER_ITEM = "order";
    private static final String APPRAISE_ITEM = "Appraise";
    private static final String CENTER_ITEM = "center";
    private ChangeCityReceiver changeCityReceiver;
    public static final String RECEIVER_FLAG = "toTwo";

    public static boolean home = false;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_huan_xin_chat);

        home = true;
        initBroadCast();
        initView();
        setListener();
    }

    private void initView() {
//        repairText=(TextView)findViewById(R.id.repair_text);
//        repairLayout=(LinearLayout)findViewById(R.id.repair_layout);
//        repairLayout.setOnClickListener(this);

        th = (TabHost) findViewById(android.R.id.tabhost);
        main_buttom_tabbar_group = (RadioGroup) findViewById(R.id.main_buttom_tabbar_group);

        order_tab = (RadioButton) findViewById(R.id.order_tab);
        order_tab.setId(0);
        verify_tab = (RadioButton) findViewById(R.id.verify_tab);
        verify_tab.setId(1);
//        sale_tab = (RadioButton) findViewById(R.id.sale_tab);
//        sale_tab.setId(2);
//        center_tab = (RadioButton) findViewById(R.id.center_tab);
//        center_tab.setId(3);


        TabHost.TabSpec tabShop = th.newTabSpec(ORDER_ITEM);
        tabShop.setIndicator(ORDER_ITEM);
        Intent shopIntent = new Intent(this, PersonageChatActivity.class);
//        Toast.makeText(getApplicationContext(),"2",Toast.LENGTH_SHORT).show();
        tabShop.setContent(shopIntent);
        th.addTab(tabShop);

        TabHost.TabSpec tabHome = th.newTabSpec(VERIFY_ITEM);
        tabHome.setIndicator(VERIFY_ITEM);
        Intent homeIntent = new Intent(this, CollectiveChatActivity.class);
        tabHome.setContent(homeIntent);
        th.addTab(tabHome);

//        TabHost.TabSpec tabUser = th.newTabSpec(APPRAISE_ITEM);
//        tabUser.setIndicator(APPRAISE_ITEM);
//        Intent userIntent = null;
//        //圆心商圈 BusinessDistrictLibraryActivity 企聊
//        userIntent = new Intent(this,HuanXinChatActivity.class);
//        tabUser.setContent(userIntent);
//        th.addTab(tabUser);
//
//        TabHost.TabSpec tabMore = th.newTabSpec(CENTER_ITEM);
//        tabMore.setIndicator(CENTER_ITEM);
//        Intent moreIntent = new Intent(this,FiveActivity.class);//我的
//        tabMore.setContent(moreIntent);
//        th.addTab(tabMore);

        order_tab.setChecked(true);

    }

    public void setTwo() {
        order_tab.setChecked(true);
    }

    private void initBroadCast() {
        changeCityReceiver = new ChangeCityReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(RECEIVER_FLAG);    //只有持有相同的action的接受者才能接收此广播
        registerReceiver(changeCityReceiver, filter);
    }

    private class ChangeCityReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            order_tab.setChecked(true);
        }
    }

    private void setListener() {
        main_buttom_tabbar_group.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
//		if(checkedId == 2) {
//			if(!((O2oApplication)getApplication()).getHappyOrderAppSPF().readIsLogin()) {
//				home_item.setChecked(true);
//				Intent userIntent = new Intent(this,LoginActivity.class);
//	        	startActivity(userIntent);
//	        	return;
//			}
//		}
        th.setCurrentTab(checkedId);
    }

    long waitTime = 3000;
    long touchTime = 0;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(changeCityReceiver);

        home = false;

    }
}

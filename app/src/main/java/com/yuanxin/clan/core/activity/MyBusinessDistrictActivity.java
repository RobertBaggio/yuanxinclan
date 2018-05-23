package com.yuanxin.clan.core.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.fragment.FragmentMyAddBusiness;
import com.yuanxin.clan.core.fragment.FragmentMyPublishBusiness;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/26.
 * 我的商圈
 */
public class MyBusinessDistrictActivity extends BaseActivity {
    @BindView(R.id.activity_my_crowd_funding_tab_layout)
    TabLayout activityMyCrowdFundingTabLayout;
    @BindView(R.id.activity_my_crowd_funding_viewPager)
    ViewPager activityMyCrowdFundingViewPager;
    @BindView(R.id.activity_business_district_library_left_layout)
    LinearLayout activityBusinessDistrictLibraryLeftLayout;
    @BindView(R.id.activity_business_district_library_right_layout)
    LinearLayout activityBusinessDistrictLibraryRightLayout;
    @BindView(R.id.testclick)
    TextView testclick;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private FragmentMyAddBusiness fragmentMyAddBusiness;//我加入的商圈
    private FragmentMyPublishBusiness fragmentMyPublishBusiness;//我的创建的商圈
    private MyFragmentAdapter adapter;


    @Override
    public int getViewLayout() {
        return R.layout.activity_my_new_crowd_funding;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initViewPager();
    }


    private void initViewPager() {
        fragmentMyAddBusiness = new FragmentMyAddBusiness();
        fragmentMyPublishBusiness = new FragmentMyPublishBusiness();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyAddBusiness);
        fragmentList.add(fragmentMyPublishBusiness);
        titleList = new ArrayList<>();
        titleList.add("我参与的");
        titleList.add("我创建的");
        activityMyCrowdFundingTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(0)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(1)));
        adapter = new MyFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        activityMyCrowdFundingViewPager.setAdapter(adapter);
        activityMyCrowdFundingViewPager.setOffscreenPageLimit(11);
        activityMyCrowdFundingTabLayout.setupWithViewPager(activityMyCrowdFundingViewPager);
    }

    @OnClick({R.id.activity_business_district_library_left_layout, R.id.activity_business_district_library_right_layout,R.id.testclick})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_business_district_library_left_layout:
                finish();
                break;
            case R.id.activity_business_district_library_right_layout:
                showAddDialog();

//                startActivity(new Intent(MyBusinessDistrictActivity.this, MyBusinessDistrictSetUpActivity.class));//创建
                break;
        }
    }
    Dialog addDlg;
    private void showAddDialog() {
        if (addDlg == null) {
            addDlg = new Dialog(MyBusinessDistrictActivity.this, R.style.WhiteDialog);
            Window window = addDlg.getWindow();
            window.setContentView(R.layout.cleatbusiness_dialog);
            View oneLayout = window.findViewById(R.id.dialog_huan_xin_layout_one);
            View twoLayout = window.findViewById(R.id.dialog_huan_xin_layout_two);
            View threeLayout = window.findViewById(R.id.dialog_huan_xin_layout_three);
            View threeLayoutset = window.findViewById(R.id.dialog_huan_xin_layout_set);
            oneLayout.setTag(addDlg);
            twoLayout.setTag(addDlg);
            threeLayout.setTag(addDlg);
            threeLayoutset.setTag(addDlg);
            oneLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MyBusinessDistrictActivity.this, MyBusinessDistrictSetUpActivity.class).putExtra("ty","1"));
                    addDlg.dismiss();
                }
            });
            twoLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MyBusinessDistrictActivity.this, MyBusinessDistrictSetUpActivity.class).putExtra("ty","2")); //添加  对方同意后 加到通讯录 点击通讯录 跳到聊天详情 当有聊天记录 会话列表就会有显示
                    addDlg.dismiss();
                }
            });
            threeLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MyBusinessDistrictActivity.this, MyBusinessDistrictSetUpActivity.class).putExtra("ty","3"));
                    addDlg.dismiss();
                }
            });
            threeLayoutset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyBusinessDistrictActivity.this, MyBusinessDistrictSetUpActivity.class).putExtra("ty","4"));
                    addDlg.dismiss();
                }
            });
            Display display = getWindowManager().getDefaultDisplay();
            int minHeight = (int) (display.getHeight() * 0.2);              //使用这种方式更改了dialog的框高
            LinearLayout layout = (LinearLayout) window.findViewById(R.id.dialog_huan_xin_layout);
            layout.setMinimumHeight(minHeight);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (display.getWidth() * 0.4);          //使用这种方式更改了dialog的框宽 和位置
            window.setGravity(Gravity.TOP | Gravity.RIGHT);
            window.setAttributes(params);
        }
        addDlg.show();
    }
}

package com.yuanxin.clan.core.news;

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
import android.widget.PopupWindow;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.event.MainEvent;
import com.yuanxin.clan.core.event.OutLoginByJpushEvent;
import com.yuanxin.clan.core.fragment.AllGongXuFragment;
import com.yuanxin.clan.core.fragment.GongYinFragment;
import com.yuanxin.clan.core.fragment.XuQiuFragment;
import com.yuanxin.clan.core.market.view.OutputGongyActivity;
import com.yuanxin.clan.core.market.view.OutputXuQiuActivity;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/1/11 0011 9:31
 */

public class HomeGongxuFragment extends BaseFragment {

    @BindView(R.id.activity_shangji_tab_layout)
    TabLayout activityShangjiTabLayout;
    @BindView(R.id.activity_my_crowd_funding_viewPager)
    ViewPager activityShangjiViewPager;
    @BindView(R.id.left_layout)
    LinearLayout left_layout;
    @BindView(R.id.right_layout)
    LinearLayout right_layout;
    private List<Fragment> fragmentList;
    private List<String> titleList;
    private AllGongXuFragment fragmentMyAddBusiness;
    private GongYinFragment fragmentMyPublishBusiness;
    private XuQiuFragment mXuQiuFragment;
    private MyFragmentAdapter adapter;
    private PopupWindow popupWindow;


    @Override
    public int getViewLayout() {
        return R.layout.gongxulayout;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        left_layout.setVisibility(View.GONE);
        initViewPager();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(OutLoginByJpushEvent outLoginEvent) {
        EventBus.getDefault().post(new MainEvent(R.id.lyMenuHome));
    }

    private void initViewPager() {
        fragmentMyAddBusiness = new AllGongXuFragment();
        fragmentMyPublishBusiness = new GongYinFragment();
        mXuQiuFragment = new XuQiuFragment();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyAddBusiness);
        fragmentList.add(fragmentMyPublishBusiness);
        fragmentList.add(mXuQiuFragment);
        titleList = new ArrayList<>();
        titleList.add("全部");
        titleList.add("供应");
        titleList.add("需求");
        activityShangjiTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityShangjiTabLayout.addTab(activityShangjiTabLayout.newTab().setText(titleList.get(0)));
        activityShangjiTabLayout.addTab(activityShangjiTabLayout.newTab().setText(titleList.get(1)));
        activityShangjiTabLayout.addTab(activityShangjiTabLayout.newTab().setText(titleList.get(2)));
        adapter = new MyFragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, titleList);
        activityShangjiViewPager.setAdapter(adapter);
//        activityMyCrowdFundingViewPager.setOffscreenPageLimit(11);
        activityShangjiTabLayout.setupWithViewPager(activityShangjiViewPager);
    }

    @OnClick({R.id.left_layout,R.id.right_layout, })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
//                finish();
                break;
            case R.id.right_layout:
                if (! UserNative.readIsLogin()){
                    toLogin();
                    return;
                }
//                showxiala();
                showAddDialog();
                break;
//            case R.id.activity_business_district_library_right_layout:
//                startActivity(new Intent(MyBusinessDistrictActivity.this, MyBusinessDistrictSetUpActivity.class));//创建
//                break;
        }
    }

    Dialog addDlg;
    private void showAddDialog() {
        if (addDlg == null) {
            addDlg = new Dialog(getActivity(), R.style.WhiteDialog);
            Window window = addDlg.getWindow();
            window.setContentView(R.layout.gxfabuxiala);
            View oneLayout = window.findViewById(R.id.dialog_huan_xin_layout_one);
            View twoLayout = window.findViewById(R.id.dialog_huan_xin_layout_two);

            oneLayout.setTag(addDlg);
            twoLayout.setTag(addDlg);

            oneLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), OutputGongyActivity.class).putExtra("type","new"));
                    addDlg.dismiss();
                }
            });
            twoLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), OutputXuQiuActivity.class).putExtra("type","new")); //添加  对方同意后 加到通讯录 点击通讯录 跳到聊天详情 当有聊天记录 会话列表就会有显示
                    addDlg.dismiss();
                }
            });

            Display display = getActivity().getWindowManager().getDefaultDisplay();
            int minHeight = (int) (display.getHeight() * 0.2);              //使用这种方式更改了dialog的框高
            LinearLayout layout = (LinearLayout) window.findViewById(R.id.dialog_huan_xin_layout);
            layout.setMinimumHeight(minHeight);
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = (int) (display.getWidth() * 0.5);                     //使用这种方式更改了dialog的框宽
            window.setGravity(Gravity.TOP | Gravity.RIGHT);
            window.setAttributes(params);
        }
        addDlg.show();
    }
}

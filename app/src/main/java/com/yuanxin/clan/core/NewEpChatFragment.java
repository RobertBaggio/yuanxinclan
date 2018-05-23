package com.yuanxin.clan.core;

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

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.ChatSetActivity;
import com.yuanxin.clan.core.activity.FragmentOldContactList;
import com.yuanxin.clan.core.activity.NewGroupsActivity;
import com.yuanxin.clan.core.adapter.MyFragmentAdapter;
import com.yuanxin.clan.core.event.MainEvent;
import com.yuanxin.clan.core.event.OutLoginByJpushEvent;
import com.yuanxin.clan.core.fragment.ConversationListFragment;
import com.yuanxin.clan.core.fragment.FragmentNewContactList;
import com.yuanxin.clan.core.huanxin.AddContactActivity;
import com.yuanxin.clan.core.zxing.FragmentID;
import com.yuanxin.clan.core.zxing.SubActivity;
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
 * 新企聊
 */

//public class NewEpChatFragment {
//}
public class NewEpChatFragment extends BaseFragment {

    @BindView(R.id.activity_huan_xin_add_button)
    LinearLayout mActivityHuanXinAddButton;
    @BindView(R.id.tongxunluli)
    LinearLayout tongxunluli;
    @BindView(R.id.activity_esui_tab_layout)
    TabLayout activityMyCrowdFundingTabLayout;
    @BindView(R.id.activity_esui_viewPager)
    ViewPager activityMyCrowdFundingViewPager;

    private List<Fragment> fragmentList;
    private List<String> titleList;
    private ConversationListFragment fragmentMyAddBusiness;//会话
    private FragmentNewContactList fragmentMyPublishBusiness;//企聊
    private FragmentOldContactList contactFragment;//新的通讯录
    private MyFragmentAdapter adapter;

    @Override
    public int getViewLayout() {
        return R.layout.new_epchat_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
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
        fragmentMyAddBusiness = new ConversationListFragment();
        fragmentMyPublishBusiness = new FragmentNewContactList();
        contactFragment = new FragmentOldContactList();
        fragmentList = new ArrayList<>();
        fragmentList.add(fragmentMyAddBusiness);
        fragmentList.add(fragmentMyPublishBusiness);
        fragmentList.add(contactFragment);
        titleList = new ArrayList<>();
        titleList.add("会话");
        titleList.add("群聊");
        titleList.add("通讯录");
        activityMyCrowdFundingTabLayout.setTabMode(TabLayout.MODE_FIXED);//不可以轮动
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(0)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(1)));
        activityMyCrowdFundingTabLayout.addTab(activityMyCrowdFundingTabLayout.newTab().setText(titleList.get(2)));
        adapter = new MyFragmentAdapter(getActivity().getSupportFragmentManager(), fragmentList, titleList);
        activityMyCrowdFundingViewPager.setAdapter(adapter);
//        activityMyCrowdFundingViewPager.setOffscreenPageLimit(11);
        activityMyCrowdFundingTabLayout.setupWithViewPager(activityMyCrowdFundingViewPager);
    }

    @OnClick({ R.id.tongxunluli,R.id.activity_huan_xin_add_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_huan_xin_add_button:
                showAddDialog();
                break;
//            case R.id.tongxunluli:
//                Bundle bundle1 = new Bundle();
//                bundle1.putInt(FragmentID.ID, 15);
//                Intent intent = new Intent();
//                intent.setClass(getActivity(), SubActivity.class);
//                intent.putExtras(bundle1);
//                startActivity(intent);
//                break;
        }
    }


    Dialog addDlg;

    private void showAddDialog() {
        if (addDlg == null) {
            addDlg = new Dialog(getActivity(), R.style.WhiteDialog);
            Window window = addDlg.getWindow();
            window.setContentView(R.layout.dialog_huan_xin);
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
                    startActivity(new Intent(getActivity(), NewGroupsActivity.class));
                    addDlg.dismiss();
                }
            });
            twoLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), AddContactActivity.class)); //添加  对方同意后 加到通讯录 点击通讯录 跳到聊天详情 当有聊天记录 会话列表就会有显示
                    addDlg.dismiss();
                }
            });
            threeLayout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
//                    IntentIntegrator integrator = new IntentIntegrator(getActivity());
//                    integrator.setOrientationLocked(false);
//                    integrator.setCaptureActivity(SmallCaptureActivity.class);
//                    integrator.initiateScan();
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt(FragmentID.ID, 1);
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), SubActivity.class);
                    intent.putExtras(bundle1);
                    startActivity(intent);
                    addDlg.dismiss();
                }
            });
            threeLayoutset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), ChatSetActivity.class));
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

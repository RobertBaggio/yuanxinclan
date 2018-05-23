package com.yuanxin.clan.core;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.ChatSetActivity;
import com.yuanxin.clan.core.activity.FragmentOldContactList;
import com.yuanxin.clan.core.activity.NewGroupsActivity;
import com.yuanxin.clan.core.fragment.ConversationListFragment;
import com.yuanxin.clan.core.fragment.FragmentNewContactList;
import com.yuanxin.clan.core.huanxin.AddContactActivity;
import com.yuanxin.clan.core.huanxin.UserProfileManager;
import com.yuanxin.clan.core.zxing.FragmentID;
import com.yuanxin.clan.core.zxing.SubActivity;
import com.yuanxin.clan.mvp.view.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan
 * Describe: 企业聊天
 * Author: xjc
 * Date: 2017/6/19 0019 13:15
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class EpChatFragment extends BaseFragment {

    @BindView(R.id.btn_conversation)
    TextView mBtnConversation;
    @BindView(R.id.unread_msg_number1)
    TextView mUnreadMsgNumber1;
    @BindView(R.id.btn_container_conversation)
    RelativeLayout mBtnContainerConversation;
    @BindView(R.id.btn_address_list)
    TextView mBtnAddressList;
    @BindView(R.id.unread_address_number)
    TextView mUnreadAddressNumber;
    @BindView(R.id.btn_container_address_list)
    RelativeLayout mBtnContainerAddressList;
    @BindView(R.id.btn_setting)
    TextView mBtnSetting;
    @BindView(R.id.hhli)
    TextView hhli;
    @BindView(R.id.qlli)
    TextView qlli;
    @BindView(R.id.txlli)
    TextView txlli;
    @BindView(R.id.btn_container_setting)
    RelativeLayout mBtnContainerSetting;
    @BindView(R.id.main_bottom)
    LinearLayout mMainBottom;
    @BindView(R.id.activity_huan_xin_add_button)
    LinearLayout mActivityHuanXinAddButton;
    @BindView(R.id.fragment_container)
    RelativeLayout mFragmentContainer;
    @BindView(R.id.mainLayout)
    RelativeLayout mMainLayout;

    private Fragment conversationcFragment;//会话
    private Fragment groupFragment;//群聊
    private Fragment contactFragment;//新的通讯录
    private FragmentTransaction fragmentTransaction;
    private boolean ifhh,ifql,iftxl;
    private UserProfileManager mUserProfileManager = new UserProfileManager();
//    android:background="@drawable/em_main_bottom_item_bg"


    @Override
    public int getViewLayout() {
        return R.layout.fragment_epchat;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        onClick(mBtnConversation);
    }

    @OnClick({R.id.btn_conversation, R.id.btn_address_list, R.id.btn_setting, R.id.activity_huan_xin_add_button})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_conversation:
                hhli.setVisibility(View.VISIBLE);
                qlli.setVisibility(View.GONE);
                txlli.setVisibility(View.GONE);//#e1e1e1 #FFFFFF
                mBtnConversation.setTextColor(getResources().getColor(R.color.white));
                mBtnAddressList.setTextColor(getResources().getColor(R.color.gray_white));
                mBtnSetting.setTextColor(getResources().getColor(R.color.gray_white));
                changeTag(view.getId());
                break;
            case R.id.btn_address_list:
                hhli.setVisibility(View.GONE);
                qlli.setVisibility(View.VISIBLE);
                txlli.setVisibility(View.GONE);
                mBtnConversation.setTextColor(getResources().getColor(R.color.gray_white));
                mBtnAddressList.setTextColor(getResources().getColor(R.color.white));
                mBtnSetting.setTextColor(getResources().getColor(R.color.gray_white));
                changeTag(view.getId());
                break;
            case R.id.btn_setting:
                hhli.setVisibility(View.GONE);
                qlli.setVisibility(View.GONE);
                txlli.setVisibility(View.VISIBLE);
                mBtnConversation.setTextColor(getResources().getColor(R.color.gray_white));
                mBtnAddressList.setTextColor(getResources().getColor(R.color.gray_white));
                mBtnSetting.setTextColor(getResources().getColor(R.color.white));
                changeTag(view.getId());
                break;
            case R.id.activity_huan_xin_add_button:
                showAddDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 根据控件id切换菜单选项
     *
     * @param id
     */
    void changeTag(int id) {
        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        clearAllStyle();
        hideAll();
        switch (id) {
            case R.id.btn_conversation:
                mBtnConversation.setSelected(true);
                if (conversationcFragment == null) {
                    conversationcFragment = new ConversationListFragment();
                    fragmentTransaction.add(R.id.fragment_container, conversationcFragment);
                } else {
                    fragmentTransaction.show(conversationcFragment);
                }
                break;
            case R.id.btn_address_list:
                mBtnAddressList.setSelected(true);
                if (groupFragment == null) {
                    groupFragment = new FragmentNewContactList();
                    fragmentTransaction.add(R.id.fragment_container, groupFragment);
                } else {
                    fragmentTransaction.show(groupFragment);
                }
                break;
            case R.id.btn_setting:
                mBtnSetting.setSelected(true);
                if (contactFragment == null) {
                    contactFragment = new FragmentOldContactList();
                    fragmentTransaction.add(R.id.fragment_container, contactFragment);
                } else {
                    fragmentTransaction.show(contactFragment);
                }
                break;
            default:
                break;
        }
        fragmentTransaction.commitAllowingStateLoss();
    }

    /**
     * 清除所有的菜单样式
     */
    private void clearAllStyle() {
        mBtnConversation.setSelected(false);
        mBtnAddressList.setSelected(false);
        mBtnSetting.setSelected(false);
    }

    /**
     * 隐藏所有碎片
     */
    void hideAll() {
        if (conversationcFragment != null) {
            fragmentTransaction.hide(conversationcFragment);
        }
        if (groupFragment != null) {
            fragmentTransaction.hide(groupFragment);
        }
        if (contactFragment != null) {
            fragmentTransaction.hide(contactFragment);
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

                 /*
  * lp.x与lp.y表示相对于原始位置的偏移.
  * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
  * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
  * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
  * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
  * 当参数值包含Gravity.CENTER_HORIZONTAL时
  * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
  * 当参数值包含Gravity.CENTER_VERTICAL时
  * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
  * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
  * Gravity.CENTER_VERTICAL.
  *
  * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
  * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
  * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
//  */
//                params.x = 100; // 新位置X坐标
//                params.y = 100; // 新位置Y坐标
//                params.width = 300; // 宽度
//                params.height = 300; // 高度
//                params.alpha = 0.7f; // 透明度
            window.setAttributes(params);
        }
        addDlg.show();
    }
}

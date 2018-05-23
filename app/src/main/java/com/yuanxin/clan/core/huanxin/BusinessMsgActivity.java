/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuanxin.clan.core.huanxin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.HomeADactivity;
import com.yuanxin.clan.core.adapter.BusinessMsgAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.event.BusinessMsgEvent;
import com.yuanxin.clan.core.event.RefreshBusinessListEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Application and notification
 */
// 进入申请与通知页面
public class BusinessMsgActivity extends com.yuanxin.clan.mvp.view.BaseActivity {
    @BindView(R.id.business_msg_recyclerview)
    RecyclerView businessMsgRecyclerview;
    @BindView(R.id.business_msg_left_layout)
    LinearLayout backLayout;
    private BusinessMsgAdapter adapter;
    private List<BusinessMessage> msgs;
    private BusinessMessageDao dao;
    @Override
    public int getViewLayout() {
        return R.layout.qiliao_msg_list;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        EventBus.getDefault().register(this);
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        dao = new BusinessMessageDao(this);//邀请通信通道
        msgs = dao.getMessagesList();//id from groupid groupname reason time status groupInviter

        adapter = new BusinessMsgAdapter(this, msgs);
        businessMsgRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        businessMsgRecyclerview.setAdapter(adapter);
        businessMsgRecyclerview.setFocusable(false);//导航栏切换不再focuse
        businessMsgRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
        adapter.setOnItemClickListener(new BusinessMsgAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BusinessMessage businessMessage = msgs.get(position);
                if (businessMessage.getMsgRead() == 0) {
                    businessMessage.setMsgRead(1);
                    dao.saveMessage(businessMessage);
                    setMsgReadToServer(businessMessage);
                }
                switch (businessMessage.getType()) {
                    // 商圈活动
                    case 1:
                        // 商圈动态
                    case 2:
                    default:
                        startActivity(new Intent(BusinessMsgActivity.this, HomeADactivity.class).putExtra("url", businessMessage.getBusinessMsgContent().replace("appFlg=0", "appFlg=1")));
                        break;
                    // 商圈短消息
                    case 3:
                        startActivity(new Intent(BusinessMsgActivity.this, BusinessMsgContentActivity.class).putExtra("msg", businessMessage));
                        break;
                    // 园区场地预约
                    case 4:
                        //场地预约信息
                        SiteData siteData = businessMessage.getSiteData();
                        if (siteData.getSiteApplyId() == UserNative.getId()) {
                            startActivity(new Intent(BusinessMsgActivity.this, BusinessMsgApplyResultActivity.class).putExtra("msg", businessMessage));
                        } else {
                            startActivity(new Intent(BusinessMsgActivity.this, BusinessMsgContentActivity.class).putExtra("msg", businessMessage));
                        }
                        break;
                    // 报修
                    case 5:
                        RepairsData repairsData = businessMessage.getRepairsData();
                        if (repairsData.getRepairsUserId() == UserNative.getId()) {
                            startActivity(new Intent(BusinessMsgActivity.this, BusinessMsgApplyResultActivity.class).putExtra("msg", businessMessage));
                        } else {
                            startActivity(new Intent(BusinessMsgActivity.this, BusinessMsgContentActivity.class).putExtra("msg", businessMessage));
                        }
                        break;
                }
            }
        });
        adapter.setOnItemLongClickListener(new BusinessMsgAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                showNormalDialog(position);
            }
        });
    }
    @OnClick({R.id.business_msg_left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.business_msg_left_layout:
                finish();
                break;
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void setMsgReadToServer(BusinessMessage businessMessage) {
        RequestParams params = new RequestParams();
        params.put("businessMsgReadId", businessMessage.getBusinessMsgReadId());
        params.put("userNm", UserNative.getName());
        params.put("userId", UserNative.getId());
        params.put("msgRead", 1);
        doHttpPost(Url.updateBusinessMsgRead, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                Logger.e(s);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void showNormalDialog(final int posion) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(BusinessMsgActivity.this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定删除消息吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dao.deleteMessage(msgs.get(posion));
                        RefreshBusinessListEvent refreshBusinessListEvent = new RefreshBusinessListEvent();
                        EventBus.getDefault().post(refreshBusinessListEvent);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }

    private void refresh() {
        msgs.clear();
        msgs.addAll(dao.getMessagesList());
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BusinessMsgEvent businessMsgEvent) {
        refresh();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(RefreshBusinessListEvent refreshBusinessListEvent) {
        refresh();
    }
}

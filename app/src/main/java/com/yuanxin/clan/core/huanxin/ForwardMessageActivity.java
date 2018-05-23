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

import android.content.Intent;
import android.os.Bundle;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.hyphenate.easeui.widget.EaseAlertDialog.AlertDialogUser;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.mvp.share.ShareInfoVo;

////转发 复制信息 跳转到选择联系人列表 选择一个联系人 提示 确定转发到：谁 确定 取消
public class ForwardMessageActivity extends PickContactNoCheckboxActivity {
    private EaseUser selectUser;
    private String forward_msg_id;
    private ShareInfoVo shareInfoVo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        forward_msg_id = getIntent().getStringExtra("forward_msg_id");
        shareInfoVo = getIntent().getParcelableExtra("share_info");
    }

    @Override
    protected void onListItemClick(int position) {
        selectUser = contactAdapter.getItem(position);
        new EaseAlertDialog(this, null, getString(R.string.confirm_forward_to, selectUser.getNick()), null, new AlertDialogUser() {
            @Override
            public void onResult(boolean confirmed, Bundle bundle) {
                if (confirmed) {
                    if (selectUser == null)
                        return;
                    try {
                        PersionChatActivity.activityInstance.finish();
                    } catch (Exception e) {
                    }
                    Intent intent = new Intent(ForwardMessageActivity.this, PersionChatActivity.class);
                    // it is single chat
                    if (forward_msg_id != null) {
                        intent.putExtra(Constant.EXTRA_USER_ID, selectUser.getUsername());
                        intent.putExtra("forward_msg_id", forward_msg_id);
                        intent.putExtra("toNick", selectUser.getNick());
                    }
                    if (shareInfoVo != null) {
                        intent.putExtra(Constant.EXTRA_USER_ID, selectUser.getUsername());
                        intent.putExtra("share_info", shareInfoVo);
                    }
                    startActivity(intent);
                    finish();
                }
            }
        }, true).show();
    }

}

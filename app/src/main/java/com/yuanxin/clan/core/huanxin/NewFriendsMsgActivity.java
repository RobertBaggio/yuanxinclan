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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hyphenate.easeui.EaseConstant;
import com.yuanxin.clan.R;

import java.util.List;

/**
 * Application and notification
 */
// 进入申请与通知页面
public class NewFriendsMsgActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_new_friends_msg);

        final ListView listView = (ListView) findViewById(R.id.em_activity_new_friends_list);
        InviteMessgeDao dao = new InviteMessgeDao(this);//邀请通信通道
        final List<InviteMessage> msgs = dao.getMessagesList();//id from groupid groupname reason time status groupInviter

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(this, 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewFriendsMsgActivity.this, UserProfileActivity.class); //点击头像去会员资料
                intent.putExtra(EaseConstant.EXTRA_USER_ID, msgs.get(position).getFrom());
//                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
//                intent.putExtra("groupType", groupType);
//                    Log.v("lgq","个人信息。。。"+username+"..."+chatType);
                startActivity(intent);
            }
        });

    }

    public void back(View view) {
        finish();
    }
}

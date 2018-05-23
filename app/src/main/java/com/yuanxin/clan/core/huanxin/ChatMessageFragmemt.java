package com.yuanxin.clan.core.huanxin;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.yuanxin.clan.R;

import java.util.List;

/**
 * Created by lenovo1 on 2017/3/15.
 */
public class ChatMessageFragmemt extends Fragment {
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_message, container, false);
//        LinearLayout back=(LinearLayout)view.findViewById(R.id.em_activity_new_friends_msg_back);

        ListView listView = (ListView) view.findViewById(R.id.fragment_chat_message_list);
        InviteMessgeDao dao = new InviteMessgeDao(getContext());//邀请通信通道
        List<InviteMessage> msgs = dao.getMessagesList();//id from groupid groupname reason time status groupInviter

        NewFriendsMsgAdapter adapter = new NewFriendsMsgAdapter(getContext(), 1, msgs);
        listView.setAdapter(adapter);
        dao.saveUnreadMessageCount(0);
        return view;
    }


}

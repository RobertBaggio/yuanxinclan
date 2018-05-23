package com.yuanxin.clan.core.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yuanxin.clan.R;

/**
 * Created by lenovo1 on 2017/3/6.
 */
//企聊 集体聊天
public class ChatCollectiveFragment extends Fragment {
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat_collective, container, false);
        return view;
    }
}

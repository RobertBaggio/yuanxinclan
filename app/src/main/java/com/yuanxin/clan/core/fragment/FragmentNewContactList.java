package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.adapter.CrowChatAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.CrowChatEntity;
import com.yuanxin.clan.core.event.ChatGroupEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/5/10.
 * 群聊列表
 */
public class FragmentNewContactList extends BaseFragment {
    @BindView(R.id.fragment_two_one_recyclerview)
    RecyclerView fragmentTwoOneRecyclerview;
    @BindView(R.id.fragmnet_two_one_springview)
    SpringView fragmnetTwoOneSpringview;
    private List<CrowChatEntity> crowChatEntities = new ArrayList<>();
    private CrowChatAdapter crowChatAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        MyShareUtil.sharedPint("bid",0);
        MyShareUtil.sharedPstring("bp","");
        MyShareUtil.sharedPstring("bn","");
    }

    @Override
    public int getViewLayout() {
        return R.layout.activity_crow_chat;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        getWebInfo();
    }

    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    private void getWebInfo() {//群聊列表
        String url = Url.getGroupsList;
        RequestParams params = new RequestParams();
        params.put("userUUID", UserNative.getPhone());
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        crowChatEntities.clear();
                        crowChatEntities.addAll(FastJsonUtils.getObjectsList(object.getString("data"), CrowChatEntity.class));
                        crowChatAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        });
    }

    private void initView() {
        crowChatAdapter = new CrowChatAdapter(getContext(), crowChatEntities);
        crowChatAdapter.setOnItemClickListener(new CrowChatAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, crowChatEntities.get(position).getGroupId());//群聊id
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_GROUP);
                intent.putExtra(Constant.USER_NAME, UserNative.getName());
                intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                intent.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                intent.putExtra("type", "");
                intent.putExtra("toNick", crowChatEntities.get(position).getGroupNm());
                intent.putExtra("toImage", crowChatEntities.get(position).getGroupImage());
                MyShareUtil.sharedPint("bid",crowChatEntities.get(position).getBusinessAreaId());
                MyShareUtil.sharedPstring("bp",crowChatEntities.get(position).getEpAccessPath());
                MyShareUtil.sharedPstring("bn",crowChatEntities.get(position).getGroupNm());
                startActivity(intent);
            }
        });
        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentTwoOneRecyclerview.setAdapter(crowChatAdapter);
        fragmentTwoOneRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentTwoOneRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
        fragmnetTwoOneSpringview.setHeader(new RotationHeader(getContext()));
        fragmnetTwoOneSpringview.setType(SpringView.Type.OVERLAP);
        fragmnetTwoOneSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetTwoOneSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                getWebInfo();
            }

            @Override
            public void onLoadmore() {
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(ChatGroupEvent chatGroupEvent) {
        getWebInfo();
    }

}

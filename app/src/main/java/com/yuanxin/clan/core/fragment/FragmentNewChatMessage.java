package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.adapter.FriendListAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FriendListEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/5/10.
 * 通讯录
 */
public class FragmentNewChatMessage extends BaseFragment {
    @BindView(R.id.fragment_two_one_recyclerview)
    RecyclerView fragmentTwoOneRecyclerview;
    @BindView(R.id.fragmnet_two_one_springview)
    SpringView fragmnetTwoOneSpringview;


    private List<FriendListEntity> friendListEntities = new ArrayList<>();
    private FriendListAdapter friendListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getViewLayout() {
        return R.layout.fragment_new_chat_message;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        getWebInfo();
//        EMClient.getInstance().contactManager().setContactListener(new MyContactListener());//联系监听

    }


    private void getWebInfo() {//还差图片
        String url = Url.getFriendsList;
        RequestParams params = new RequestParams();
        params.put("userUUID", UserNative.getPhone());
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);


                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        JSONArray jsonArray = object.getJSONArray("data");
                        friendListEntities.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String groupDetailImage = dataObject.getString("groupDetailImage");//头像
                            String imgOne = Url.img_domain + groupDetailImage+Url.imageStyle208x208;
                            String groupDetailNm = dataObject.getString("groupDetailNm");//名字
                            String userUuid = dataObject.getString("userUuid");//聊天
                            FriendListEntity one = new FriendListEntity();
                            one.setName(groupDetailNm);//标题
                            one.setImage(imgOne);
                            one.setPhone(userUuid);
                            friendListEntities.add(one);

                        }

                        friendListAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);

                    }


                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }

    private void initView() {

        friendListAdapter = new FriendListAdapter(getContext(), friendListEntities);
        friendListAdapter.setOnItemClickListener(new FriendListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, friendListEntities.get(position).getPhone());
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                startActivity(intent);
            }
        });
//        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentTwoOneRecyclerview.setAdapter(friendListAdapter);
        fragmentTwoOneRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentTwoOneRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
        getWebInfo();

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("lgq","tttttttttttt0");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.v("lgq","ssssssssssssssss");
    }
}

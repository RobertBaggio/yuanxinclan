package com.yuanxin.clan.core.huanxin;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.easeui.adapter.EaseContactAdapter;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.widget.EaseSidebar;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

//群组选人
public class PickAtUserActivity extends BaseActivity {
    ListView listView;
    private String groupId;
    private ArrayList<EaseUser> friendList;
    private PickUserAdapter pickUserAdapter;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_pick_at_user);

        groupId = getIntent().getStringExtra("groupId");//从ChatFragment传过来的
        EMGroup group = EMClient.getInstance().groupManager().getGroup(groupId);
        EaseSidebar sidebar = (EaseSidebar) findViewById(com.hyphenate.easeui.R.id.sidebar);//右边一条
        listView = (ListView) findViewById(R.id.list);
        sidebar.setListView(listView);
//        List<String> members = group.getMembers();
        friendList = new ArrayList<EaseUser>();
//        for (String username : members) {
//            EaseUser user = EaseUserUtils.getUserInfo(username);
//            friendList.add(user);
//        }

        Collections.sort(friendList, new Comparator<EaseUser>() {

            @Override
            public int compare(EaseUser lhs, EaseUser rhs) {
                if (lhs.getInitialLetter().equals(rhs.getInitialLetter())) {
                    return lhs.getNick().compareTo(rhs.getNick());
                } else {
                    if ("#".equals(lhs.getInitialLetter())) {
                        return 1;
                    } else if ("#".equals(rhs.getInitialLetter())) {
                        return -1;
                    }
                    return lhs.getInitialLetter().compareTo(rhs.getInitialLetter());
                }

            }
        });
        final boolean isOwner = EMClient.getInstance().getCurrentUser().equals(group.getOwner());
        if (isOwner) {
            addHeadView();
        }
        pickUserAdapter = new PickUserAdapter(this, 0, friendList);
        listView.setAdapter(pickUserAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                if (isOwner) {
                    if (position != 0) {
                        EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                        if (EMClient.getInstance().getCurrentUser().equals(user.getUsername()))
                            return;
                        bundle.putString("username", user.getUsername());
                        bundle.putString("usernick", user.getNick());
                    } else {
                        bundle.putString("username", "0");
                        bundle.putString("usernick", getString(R.string.all_members));
                    }
                } else {
                    EaseUser user = (EaseUser) listView.getItemAtPosition(position);
                    if (EMClient.getInstance().getCurrentUser().equals(user.getUsername()))
                        return;
                    bundle.putString("username", user.getUsername());
                    bundle.putString("usernick", user.getNick());
                }
                setResult(RESULT_OK, new Intent().putExtra("user", bundle));
                finish();
            }
        });
        filledData();
    }

    private void filledData() {
        String url = Url.getFriendsList;
        RequestParams params = new RequestParams();
        params.put("userUUID", groupId);
        doHttpGet(url, params, new com.yuanxin.clan.mvp.view.BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq","filledData==="+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String data = object.getString("data");
                        if (!data.equals("null")) {
                            JSONArray jsonArray = object.getJSONArray("data");
                            friendList.clear();
                            for (int a = 0; a < jsonArray.length(); a++) {
                                JSONObject dataObject = jsonArray.getJSONObject(a);
                                String groupDetailImage = dataObject.getString("groupDetailImage");//头像
                                String imgOne = Url.img_domain + groupDetailImage+Url.imageStyle208x208;
                                String groupDetailNm = dataObject.getString("groupDetailNm");//名字
                                String userUuid = dataObject.getString("userUuid");//聊天 电话
                                EaseUser entity = new EaseUser(userUuid);
                                entity.setNickname(groupDetailNm);
                                entity.setAvatar(imgOne);
                                friendList.add(entity);
                            }
                            pickUserAdapter.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void addHeadView() {
        View view = LayoutInflater.from(this).inflate(R.layout.ease_row_contact, listView, false);
        ImageView avatarView = (ImageView) view.findViewById(R.id.avatar);
        TextView textView = (TextView) view.findViewById(R.id.name);
        textView.setText(getString(R.string.all_members));
        avatarView.setImageResource(R.drawable.ease_groups_icon);
        listView.addHeaderView(view);
    }

    public void back(View view) {
        finish();
    }

    private class PickUserAdapter extends EaseContactAdapter {

        public PickUserAdapter(Context context, int resource, List<EaseUser> objects) {
            super(context, resource, objects);
        }
    }
}

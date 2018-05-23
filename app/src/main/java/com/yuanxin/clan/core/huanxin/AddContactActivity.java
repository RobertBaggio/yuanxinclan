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

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.widget.EaseAlertDialog;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.FindFriendAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FriendDetail;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

//添加
//添加  对方同意后 加到通讯录 点击通讯录 跳到聊天详情 当有聊天记录 会话列表就会有显示
public class AddContactActivity extends BaseActivity {
    @BindView(R.id.add_list_friends_text)
    TextView mAddListFriendsText;
    @BindView(R.id.em_activity_add_contact_search)
    Button mEmActivityAddContactSearch;
    @BindView(R.id.title)
    RelativeLayout mTitle;
    @BindView(R.id.edit_note)
    EditText mEditNote;
    @BindView(R.id.frvFriendList)
    FamiliarRecyclerView mFrvFriendList;
    @BindView(R.id.findnameimage)
    ImageView fimage;
    @BindView(R.id.findname)
    TextView finame;
    @BindView(R.id.findnamephone)
    TextView fiphone;
    @BindView(R.id.findnameli)
    LinearLayout fili;
    @BindView(R.id.activity_five_head_image)
    MLImageView activityFiveHeadImage;


    private ProgressDialog progressDialog;
    private List<FriendDetail> mFriendDetails = new ArrayList<>();
    private String phoneend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.em_activity_add_contact);
        ButterKnife.bind(this);
        String strAdd = getResources().getString(R.string.add_friend);
        mAddListFriendsText.setText(strAdd);
        String strUserName = getResources().getString(R.string.user_name);
        mEditNote.setHint(strUserName);//提示 用户名
        mEmActivityAddContactSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findFriend(mEditNote.getText().toString());
            }
        });
        fili.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addContact(phoneend);
                Intent intent = new Intent(AddContactActivity.this, UserProfileActivity.class); //点击头像去会员资料
                intent.putExtra(EaseConstant.EXTRA_USER_ID, phoneend);
                startActivity(intent);
            }
        });
    }

    private void findFriend(String phone) {
        String url = Url.findFriend;
        RequestParams params = new RequestParams();
        params.put("userPhone", phone);//用户id
        doHttpGet(url, params, new com.yuanxin.clan.mvp.view.BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(AddContactActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.v("lgq","查找好友。。。"+s);
                    if (object.getString("success").equals("true")) {
                        JSONObject array = object.getJSONObject("data");

                        String name = array.getString("updateNm");
                        phoneend=array.getString("userPhone");
                        if (phoneend.length()>6){
                            fili.setVisibility(View.VISIBLE);
                        }
                        String image=Url.img_domain+array.getString("userImage")+Url.imageStyle640x640;
//                        String image="http://www.tonixtech.com/yuanxinbuluo/upload/images/user/20170701190928750.jpeg";
//                        ImageManager.loadBitmap(AddContactActivity.this,image,fimage);//图片
                        ImageManager.loadBitmap(AddContactActivity.this, image, R.drawable.list_img, activityFiveHeadImage);
//                        ImageManager.loadBitmap(AddContactActivity.this, image, R.drawable.list_img, activityFiveHeadImage);
                        finame.setText(name);
                        fiphone.setText(phoneend);

                        mFriendDetails = FastJsonUtils.getObjectsList(object.getString("data"), FriendDetail.class);
                        initRecyclerView();
                    } else {
                        ToastUtil.showWarning(AddContactActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    fili.setVisibility(View.GONE);
//                    Toast.makeText(AddContactActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    ToastUtil.showWarning(AddContactActivity.this, "没有该用户", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void initRecyclerView() {
        if (mFriendDetails == null) {
            mFriendDetails = new ArrayList<>();
        }
        FindFriendAdapter adapter = new FindFriendAdapter(mFriendDetails);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.indicator) {
                    addContact(mFriendDetails.get(position).getUserPhone());
                }
            }
        });
        mFrvFriendList.setAdapter(adapter);
    }


    /**
     * search contact
     *
     * @param v
     */
    //编辑搜索框
    //点击查找
    public void searchContact(View v) {
        String name = mEditNote.getText().toString();
        String saveText = mEmActivityAddContactSearch.getText().toString();//查找

        if (getString(R.string.button_search).equals(saveText)) {
            if (TextUtils.isEmpty(name)) {//如果搜索那栏不填，直接点击查找 弹出对话框
                new EaseAlertDialog(this, R.string.Please_enter_a_username).show();
                return;
            }
            findFriend(name);
        }
    }

    /**
     * add contact
     */
    //点击查找  查找到 显示 添加
    public void addContact(final String phone) {
//        if (EMClient.getInstance().getCurrentUser().equals(phone)) {
        Log.v("lgq","..........."+phone+"...."+UserNative.getPhone()+"...."+EMClient.getInstance().getCurrentUser());
        if (UserNative.getPhone().equals(phone)) {
            new EaseAlertDialog(this, R.string.not_add_myself).show();//不能添加自己
            return;
        }

        if (DemoHelper.getInstance().getContactList().containsKey(phone)) {
            //let the user know the contact already in your contact list
            if (EMClient.getInstance().contactManager().getBlackListUsernames().contains(phone)) { //黑名单
                new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();//This user already is your friend, only need remove out from the black list
                return;
            }
            new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();//已经是好友
            return;
        }

        progressDialog = new ProgressDialog(this);
        String stri = getResources().getString(R.string.Is_sending_a_request);//正在发送请求...
        progressDialog.setMessage(stri);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        new Thread(new Runnable() {
            public void run() {

                try {
                    //demo use a hardcode reason here, you need let user to input if you like
                    String s = getResources().getString(R.string.Add_a_friend);//加个好友呗
                    EMClient.getInstance().contactManager().addContact(phone, s);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s1 = getResources().getString(R.string.send_successful);//发送请求成功,等待对方验证
                            ToastUtil.showInfo(getApplicationContext(), s1, Toast.LENGTH_LONG);
                        }
                    });
                } catch (final Exception e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            String s2 = getResources().getString(R.string.Request_add_buddy_failure);
                            ToastUtil.showError(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG);//请求添加好友失败
                        }
                    });
                }
            }
        }).start();
//        addFriendSingle(phone);//只走环信
    }

    public void back(View v) {
        finish();
    }
}

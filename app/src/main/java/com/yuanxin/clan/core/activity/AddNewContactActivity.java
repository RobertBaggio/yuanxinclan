package com.yuanxin.clan.core.activity;

import android.app.Activity;

/**
 * Created by lenovo1 on 2017/5/13.
 */
public class AddNewContactActivity extends Activity {
//    @BindView(R.id.activity_exchange_phone_left_layout)
//    LinearLayout activityExchangePhoneLeftLayout;
//    @BindView(R.id.activity_exchange_phone_right_layout)
//    LinearLayout activityExchangePhoneRightLayout;
//    @BindView(R.id.title)
//    RelativeLayout title;
//    @BindView(R.id.add_new_contact_search)
//    EditText addNewContactSearch;
//    @BindView(R.id.avatar)
//    ImageView avatar;
//    @BindView(R.id.em_activity_add_contact_name)
//    TextView emActivityAddContactName;
//    @BindView(R.id.indicator)
//    Button indicator;
//    @BindView(R.id.ll_user)
//    RelativeLayout llUser;
//    @BindView(R.id.activity_exchange_phone_right_search)
//    TextView activityExchangePhoneRightSearch;
//    private String toAddUsername;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_new_contact);
//        unbinder = ButterKnife.bind(this);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//
//    }
//
//    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout, R.id.indicator})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.activity_exchange_phone_left_layout:
//                finish();
//                break;
//            case R.id.activity_exchange_phone_right_layout:
//                final String name = addNewContactSearch.getText().toString();
//                String saveText = activityExchangePhoneRightSearch.getText().toString();//查找
//
//                if (getString(R.string.button_search).equals(saveText)) {
//                    toAddUsername = name;
//                    if (TextUtils.isEmpty(name)) {//如果搜索那栏不填，直接点击查找 弹出对话框
//                        new EaseAlertDialog(this, R.string.Please_enter_a_username).show();
//                        return;
//                    }
//
//                    // TODO you can search the user from your app server here.
//
//                    //show the userame and add button if user exist
//                    llUser.setVisibility(View.VISIBLE);
//                    emActivityAddContactName.setText(toAddUsername);
//
//
//                }
//                break;
//            case R.id.indicator:
//                if(EMClient.getInstance().getCurrentUser().equals(nameText.getText().toString())){
//                    new EaseAlertDialog(this, R.string.not_add_myself).show();//不能添加自己
//                    return;
//                }
//
//                if(DemoHelper.getInstance().getContactList().containsKey(nameText.getText().toString())){
//                    //let the user know the contact already in your contact list
//                    if(EMClient.getInstance().contactManager().getBlackListUsernames().contains(nameText.getText().toString())){ //黑名单
//                        new EaseAlertDialog(this, R.string.user_already_in_contactlist).show();//This user already is your friend, only need remove out from the black list
//                        return;
//                    }
//                    new EaseAlertDialog(this, R.string.This_user_is_already_your_friend).show();//已经是好友
//                    return;
//                }
//
//                progressDialog = new ProgressDialog(this);
//                String stri = getResources().getString(R.string.Is_sending_a_request);//正在发送请求...
//                progressDialog.setMessage(stri);
//                progressDialog.setCanceledOnTouchOutside(false);
//                progressDialog.show();
//
//                new Thread(new Runnable() {
//                    public void run() {
//
//                        try {
//                            //demo use a hardcode reason here, you need let user to input if you like
//                            String s = getResources().getString(R.string.Add_a_friend);//加个好友呗
//                            EMClient.getInstance().contactManager().addContact(toAddUsername, s);
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    progressDialog.dismiss();
//                                    String s1 = getResources().getString(R.string.send_successful);//发送请求成功,等待对方验证
//                                    Toast.makeText(getApplicationContext(), s1, Toast.LENGTH_LONG).show();
//                                }
//                            });
//                        } catch (final Exception e) {
//                            runOnUiThread(new Runnable() {
//                                public void run() {
//                                    progressDialog.dismiss();
//                                    String s2 = getResources().getString(R.string.Request_add_buddy_failure);
//                                    Toast.makeText(getApplicationContext(), s2 + e.getMessage(), Toast.LENGTH_LONG).show();//请求添加好友失败
//                                }
//                            });
//                        }
//                    }
//                }).start();
//                break;
//        }
//    }

}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.NewgroupsAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FriendListEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.UserProfileActivity;
import com.yuanxin.clan.core.market.bean.TradesEntity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.Utils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.PullToRefreshView;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * 群成员类
 */

public class NewGroupsDetailActivity extends BaseActivity implements PullToRefreshView.OnFooterRefreshListener,PullToRefreshView.OnHeaderRefreshListener{
    @BindView(R.id.activity_yuan_xin_fair_new_left_layout)
    LinearLayout activityYuanXinFairNewLeftLayout;
    @BindView(R.id.activity_business_district_library_recycler_view)
    RecyclerView activityYuanXinFairNewRecyclerView;
    @BindView(R.id.activity_company_information_detail_middle_text)
    TextView mTextView;
    @BindView(R.id.p2rv)
    PullToRefreshView p2rv;
    @BindView(R.id.shanchutianjiali)
    LinearLayout shanchutianjiali;
    @BindView(R.id.addMemberLayout)
    LinearLayout addMemberLayout;
    @BindView(R.id.shanchutianjialitrue)
    LinearLayout shanchutianjialitrue;
    @BindView(R.id.group_chat_detail_add_button_one)
    ImageView group_chat_detail_add_button_one;


    private List<TradesEntity> mEntities = new ArrayList<>();

    private ArrayList<String> memberNumberList;
    private ArrayList<String> phoneList = new ArrayList<>();
    private NewgroupsAdapter addGroupsNumberAdapter;
    private List<FriendListEntity> friendList = new ArrayList<>();//选中要结算的购物车商品
    private String groupId,groupmin;
    private int groupType;
    private String url;


    @Override
    public int getViewLayout() {
        return R.layout.newgroupsdetail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        setStatusBar(this.getResources().getColor(R.color.businesstop));
        try{

        groupId = getIntent().getStringExtra("groupId");
        groupType = getIntent().getIntExtra("groupType",0);
        groupmin = getIntent().getStringExtra("groupmin");
        friendList =(List<FriendListEntity>) getIntent().getSerializableExtra("person_data");
        if (groupmin.equals(UserNative.getPhone())&&groupType==3){
            shanchutianjiali.setVisibility(View.VISIBLE);
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        p2rv.setOnFooterRefreshListener(this);
        p2rv.setOnHeaderRefreshListener(this);
        initRecyclerView();
        p2rv.setEnablePullLoadMoreDataStatus(false);
        p2rv.setEnablePullTorefresh(false);
    }



    private void initRecyclerView() {
        addGroupsNumberAdapter = new NewgroupsAdapter(NewGroupsDetailActivity.this, friendList, groupId, NewGroupsDetailActivity.this);
        addGroupsNumberAdapter.setOnItemClickListener(new NewgroupsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int posi) {
                Log.v("lgq","....hh....."+friendList.get(posi).getPhone());
//                if (friendList.get(posi).getPhone().equals(UserNative.getPhone())){
                Intent intent = new Intent(NewGroupsDetailActivity.this, UserProfileActivity.class); //点击头像去会员资料
                intent.putExtra(EaseConstant.EXTRA_USER_ID, friendList.get(posi).getPhone());
//                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
                intent.putExtra("groupType", groupType);
//                    Log.v("lgq","个人信息。。。"+username+"..."+chatType);
                startActivity(intent);
            }
        });
        addGroupsNumberAdapter.setOnItemClickListener(new NewgroupsAdapter.OnItemClickListenerupd() {
            @Override
            public void onItemClick(int position) {
                deleteCompanyMemberToWeb(friendList.get(position).getPhone(),position);
            }
        });
        activityYuanXinFairNewRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
        activityYuanXinFairNewRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        activityYuanXinFairNewRecyclerView.setAdapter(addGroupsNumberAdapter);
        activityYuanXinFairNewRecyclerView.setFocusable(false);//导航栏切换不再focuse
        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) activityYuanXinFairNewRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = UIUtils.dip2px(90)*friendList.size()/5+UIUtils.dip2px(90);// 控件的高强制设成20

    }




    @OnClick({R.id.activity_yuan_xin_fair_new_left_layout,R.id.addMemberLayout,R.id.group_chat_detail_add_button_one})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_left_layout:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.addMemberLayout:
                phoneList.clear();
                for (int b = 0; b < friendList.size(); b++) {
                    String phone = friendList.get(b).getPhone();
                    phoneList.add(phone);
                }
                Intent groupIntroduce = new Intent(NewGroupsDetailActivity.this, OldContactListActivity.class);
                groupIntroduce.putStringArrayListExtra("memberPhoneList", phoneList);
                startActivityForResult(groupIntroduce, 406);
                break;
            case R.id.group_chat_detail_add_button_one:
                addGroupsNumberAdapter.setEdit();//编辑 自己删除
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
     if (requestCode == 406) {
            if (resultCode == RESULT_OK) {
                memberNumberList = data.getStringArrayListExtra("memberNumberList");
                if (memberNumberList.size() == 0) {
                    return;
                }
                addGroupsNumber(Utils.list2string(memberNumberList));
                ArrayList<String> images = data.getStringArrayListExtra("memberImageList");//完整路径
                ArrayList<String> names = data.getStringArrayListExtra("memberNameList");
                for (int c = 0; c < names.size(); c++) {
                    FriendListEntity entity = new FriendListEntity();
                    entity.setName(names.get(c));
                    entity.setImage(images.get(c));
                    entity.setPhone(memberNumberList.get(c));
                    if (!friendList.contains(entity)) {
                        friendList.add(entity);
                    }
                }
                addGroupsNumberAdapter.notifyDataSetChanged();
            }
        }
    }

    private void addGroupsNumber(String memberNumberList) {//添加成员
        String url = Url.addGroupUsers;
        RequestParams params = new RequestParams();
        params.put("groupId", groupId);//用户id
        params.put("users", memberNumberList);//群成员(用户名通过","分割)
        params.put("userId", UserNative.getId());//用户id
        params.put("userNm", UserNative.getName());//用户id

        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);

                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void deleteCompanyMemberToWeb(String phone, final int position) {
        String url = Url.deleteFriendSingle;
        RequestParams params = new RequestParams();
        params.put("groupId", groupId);//群Id
        params.put("userName", phone);//用户UUID
        params.put("userId", UserNative.getId());//(当前登录用户ID)
        params.put("userNm", UserNative.getName());//(当前登录用户名)
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(NewGroupsDetailActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);

                    if (object.getString("success").equals("true")) {
                        friendList.remove(position);
                        addGroupsNumberAdapter.notifyDataSetChanged();
                        ToastUtil.showSuccess(NewGroupsDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    } else {
                        ToastUtil.showWarning(NewGroupsDetailActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
                    Logger.e("数据解析出错");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent home=new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
//        long secondTime = System.currentTimeMillis();
//        if (secondTime - firstTime > 1500) {// 如果两次按键时间间隔大于800毫秒，则不退出
//            Toast.makeText(getApplicationContext(), "再按一次退出程序...",
//                    Toast.LENGTH_SHORT).show();
//            firstTime = secondTime;// 更新firstTime
//        } else {
//            moveTaskToBack(false);
//            finish();
////            System.exit(0);// 否则退出程序
//        }
    }

    @Override
    public void onFooterRefresh(PullToRefreshView view) {
    }
    @Override
    public void onHeaderRefresh(PullToRefreshView view) {
    }
}


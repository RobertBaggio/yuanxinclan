package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.ChooseFriendListAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.FriendListEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by lenovo1 on 2017/5/13.
 */
public class NewContactListActivity extends BaseActivity {
    @BindView(R.id.fragment_two_one_recyclerview)
    RecyclerView fragmentTwoOneRecyclerview;
    @BindView(R.id.fragmnet_two_one_springview)
    SpringView fragmnetTwoOneSpringview;
    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;

    //    private List<NewEntityOne> newEntityOnes=new ArrayList<>();
    private List<FriendListEntity> friendListEntities = new ArrayList<>();
    private String title, newsTypeNm, createDt, content, imgOne;
    private int newsId, newsTypeId;
    //    private GankAdapter gankAdapter;
    private ChooseFriendListAdapter chooseFriendListAdapter;
    private int currentPage = 1;// 当前页面，从0开始计数
    private List<FriendListEntity> m_buyList = new ArrayList<FriendListEntity>();//选中要结算的购物车商品
    private double total_money;
    private int m_num;
    private List<String> m_job_ids = new ArrayList<String>();//删除集合
    private ArrayList<String> images = new ArrayList<String>();//头像集合
    private ArrayList<String> names = new ArrayList<String>();//删除集合
    public Unbinder unbinder;

    private String abc, imagesOne, namesOne;

    @Override
    public int getViewLayout() {
        return R.layout.activity_new_contact_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initView();
    }

    private void getWebInfo() {//还差图片
        String url = Url.getFriendsList;
        RequestParams params = new RequestParams();
        params.put("userUUID", UserNative.getPhone());
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
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

                        chooseFriendListAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);

                    }


                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });

    }

    private void initView() {

        chooseFriendListAdapter = new ChooseFriendListAdapter(getApplicationContext(), friendListEntities);
        chooseFriendListAdapter.setOnItemClickListener(new ChooseFriendListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Intent intent = new Intent(getApplicationContext(), PersionChatActivity.class);
//                intent.putExtra(Constant.EXTRA_USER_ID,friendListEntities.get(position).getPhone());
//                intent.putExtra(Constant.EXTRA_CHAT_TYPE,1);
//                startActivity(intent);
            }
        });
        chooseFriendListAdapter.setOnMyCheckedChangeListener(new ChooseFriendListAdapter.OnMyCheckedChangeListener() {
            @Override
            public void onCheckedChange(int num, List<FriendListEntity> buyList) {
                m_buyList.clear();
                m_buyList.addAll(buyList);//所有数据
//                m_job_ids.clear();//删除id
//                shopIds.clear();//
//                total_money = 0;
                m_num = buyList.size();


            }
        });
        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        fragmentTwoOneRecyclerview.setAdapter(chooseFriendListAdapter);
        fragmentTwoOneRecyclerview.setFocusable(false);//导航栏切换不再focuse
        fragmentTwoOneRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
        getWebInfo();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();


    }

    @OnClick({R.id.activity_exchange_phone_left_layout, R.id.activity_exchange_phone_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_exchange_phone_left_layout:
                finish();
                break;
            case R.id.activity_exchange_phone_right_layout:
                m_job_ids.clear();//选中的成员电话号码
                if (m_num > 0) {

                    for (int a = 0; a < m_buyList.size(); a++) {

                        String jobId = m_buyList.get(a).getPhone();
                        m_job_ids.add(jobId);
                        String image = m_buyList.get(a).getImage();
                        images.add(image);
                        String name = m_buyList.get(a).getName();
                        names.add(name);

                    }
                    abc = "";
                    abc = list2string(m_job_ids);
//                    imagesOne="";
//                    imagesOne=list2string(images);
//                    namesOne="";
//                    namesOne=list2string(names);


                    Intent intent = new Intent();
                    intent.putExtra("memberNumberList", abc);
                    intent.putStringArrayListExtra("memberImageList", images);
                    intent.putStringArrayListExtra("memberNameList", names);
                    setResult(RESULT_OK, intent);
                    finish();


                    break;
                }
        }
    }

    public static String list2string(List<String> stringList) {
        String idStr = ",";
        for (int i = 0; i < stringList.size(); i++) {
            idStr += stringList.get(i) + ",";
        }
        return idStr.substring(1, idStr.length() - 1);
    }
}

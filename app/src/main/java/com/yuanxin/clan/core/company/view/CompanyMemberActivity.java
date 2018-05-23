package com.yuanxin.clan.core.company.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.company.adapter.CompanyMemberAdapter;
import com.yuanxin.clan.core.company.bean.CompanyMemberEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.CompanyPeopleDetailActivity;
import com.yuanxin.clan.core.util.MLImageView;
import com.yuanxin.clan.core.util.MyShareUtil;
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

/**
 * Created by lenovo1 on 2017/2/27.
 * 企业成员
 */
public class CompanyMemberActivity extends BaseActivity {
    @BindView(R.id.activity_company_member_head_image_layout)
    LinearLayout activityCompanyMemberHeadImageLayout;
    @BindView(R.id.activity_company_member_right_layout)
    LinearLayout activityCompanyMemberRightLayout;
    @BindView(R.id.activity_company_member_recycler_view)
    RecyclerView activityCompanyMemberRecyclerView;
    @BindView(R.id.activity_member_head_image)
    MLImageView activityMemberHeadImage;
    @BindView(R.id.activity_company_member_add_layout)
    LinearLayout activityCompanyMemberAddLayout;
//    private static final int REQUEST_REFRESHADD = 14;//企业简介
    private List<CompanyMemberEntity> companyMemberEntities = new ArrayList<>();
    private CompanyMemberAdapter companyMemberAdapter;
    private int epId;
    private String userId;//删除id
    private List<String> deleteIdList = new ArrayList<String>();//删除集合
    private static String idStr;
    private static final int REQUEST_MEMBER = 400;


    @Override
    public int getViewLayout() {
        return R.layout.activity_company_member;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        getInfo();
        initRecyclerView();
    }

    private void getInfo() {
        epId = getIntent().getIntExtra("epIdTwo", 0);
        getCompanyMember(epId);
    }


    private void initRecyclerView() {
        companyMemberAdapter = new CompanyMemberAdapter(CompanyMemberActivity.this, companyMemberEntities);
        activityCompanyMemberRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        activityCompanyMemberRecyclerView.setAdapter(companyMemberAdapter);
        activityCompanyMemberRecyclerView.setFocusable(false);//导航栏切换不再focuse
        companyMemberAdapter.setOnItemClickListener(new CompanyMemberAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int posi) {

                Intent intent = new Intent(CompanyMemberActivity.this, CompanyPeopleDetailActivity.class); //点击头像去会员资料
                intent.putExtra(EaseConstant.EXTRA_USER_ID, companyMemberEntities.get(posi).getUserPhone());
                intent.putExtra("uid", companyMemberEntities.get(posi).getUserId());
                intent.putExtra("id", companyMemberEntities.get(posi).getId());
//                    intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
                intent.putExtra("inType", 1);
                intent.putExtra("epId", epId);
                intent.putExtra("id", companyMemberEntities.get(posi).getId());
                intent.putExtra("posi", posi+3);
                startActivityForResult(intent, REQUEST_MEMBER);
            }
        });
    }

    private void getCompanyMember(int epId) {
        String url = Url.getepUsers;
        RequestParams params = new RequestParams();
        params.put("epId", epId);//用户id
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
                        JSONArray jsonArray = object.getJSONArray("data");

//                        JSONObject adataObject = jsonArray.getJSONObject(29);
//                        String aposition = adataObject.getString("position");//职位
//                        JSONObject auserObject = adataObject.getJSONObject("user");
//                        String auserNm = auserObject.getString("userNm");//名称
//                        Log.v("lgq","shujgeshu===15="+"..."+auserNm);

                        Log.v("lgq","shujgeshu===="+jsonArray.length());
                        companyMemberEntities.clear();
                        for (int c = 0; c < jsonArray.length(); c++) {
                            JSONObject dataObject = jsonArray.getJSONObject(c);
                            String position = dataObject.getString("position");//职位
                            String id = dataObject.getString("id");//职位
                            JSONObject userObject = dataObject.getJSONObject("user");
                            String userNm = userObject.getString("userNm");//名称
                            Log.v("lgq","shujgeshu===="+"..."+userNm);
                            String userImage = userObject.getString("userImage");
                            String userPhone = userObject.getString("userPhone");
                            userId = userObject.getString("userId");
                            String userImageOne = Url.img_domain + userImage+Url.imageStyle640x640;
                            CompanyMemberEntity entity = new CompanyMemberEntity();
                            entity.setUserId(userId);
                            entity.setId(id);
                            entity.setImage(userImageOne);
                            entity.setName(userNm);
                            entity.setPosition(position);
                            entity.setUserPhone(userPhone);
                            entity.setId(id);
                            companyMemberEntities.add(entity);
                        }
                        companyMemberAdapter.notifyDataSetChanged();
                        Log.v("lgq","chulail .................");
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    companyMemberAdapter.notifyDataSetChanged();
                }
            }
        });
    }


    @OnClick({R.id.activity_company_member_head_image_layout, R.id.activity_company_member_right_layout, R.id.activity_member_head_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_company_member_head_image_layout:
                finish();
                break;
            case R.id.activity_company_member_right_layout://编辑
                companyMemberAdapter.setEdit();//编辑 自己删除
                break;
            case R.id.activity_member_head_image://增加成员
                Intent publishIntent = new Intent(CompanyMemberActivity.this, AddCompanyMemberActivity.class);
                publishIntent.putExtra("epIdNew", epId);
                startActivityForResult(publishIntent, REQUEST_MEMBER);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_MEMBER) {
            if (resultCode == RESULT_OK) {
                getCompanyMember(epId);
                Log.v("lgq",".....onActivityResult.....");
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getCompanyMember(epId);

    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v("lgq","....onStop....==");
        MyShareUtil.sharedPint("posi",0);
    }
}

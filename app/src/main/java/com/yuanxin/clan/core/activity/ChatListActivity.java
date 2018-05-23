package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.jiang.android.lib.adapter.expand.StickyRecyclerHeadersDecoration;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.indexRecyclerView.CharacterParser;
import com.yuanxin.clan.core.indexRecyclerView.CommonString;
import com.yuanxin.clan.core.indexRecyclerView.ContactAdapter;
import com.yuanxin.clan.core.indexRecyclerView.ContactModel;
import com.yuanxin.clan.core.indexRecyclerView.DividerDecoration;
import com.yuanxin.clan.core.indexRecyclerView.PinyinComparator;
import com.yuanxin.clan.core.indexRecyclerView.SideBar;
import com.yuanxin.clan.core.indexRecyclerView.TouchableRecyclerView;
import com.yuanxin.clan.core.indexRecyclerView.ZSideBar;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo1 on 2017/5/10.
 */
public class ChatListActivity extends BaseActivity {

    private SideBar mSideBar;
    private ZSideBar mZSideBar;
    private TextView mUserDialog;
    private TouchableRecyclerView mRecyclerView;
    ContactModel mModel;
    private List<ContactModel.MembersEntity> mMembers = new ArrayList<>();
    private CharacterParser characterParser;
    private PinyinComparator pinyinComparator;
    private ContactAdapter mAdapter;
    private List<ContactModel.MembersEntity> mAllLists = new ArrayList<>();
    private int mPermission;

    @Override
    public int getViewLayout() {
        return R.layout.activity_chat_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getPermission();
        initView();
    }

    private void getPermission() {
        mPermission = CommonString.PermissionCode.TEACHER;
    }


    private void initView() {
        characterParser = CharacterParser.getInstance();
        pinyinComparator = new PinyinComparator();
        mSideBar = (SideBar) findViewById(R.id.contact_sidebar);
        mZSideBar = (ZSideBar) findViewById(R.id.contact_zsidebar);
        mUserDialog = (TextView) findViewById(R.id.contact_dialog);
        mRecyclerView = (TouchableRecyclerView) findViewById(R.id.contact_member);
        mSideBar.setTextView(mUserDialog);


//        fillData();
        getNetData(0);


    }


    public void getNetData(final int type) {
//        getWebInfo(1);
//        getChatList();
        //id 已经被处理过
        String tempData = "{\"groupName\":\"中国\",\"admins\":[{\"id\":\"111221\",\"username\":\"程景瑞\",\"profession\":\"teacher\"},{\"id\":\"bfcd1feb5db2\",\"username\":\"钱黛\",\"profession\":\"teacher\"},{\"id\":\"bfcd1feb5db2\",\"username\":\"许勤颖\",\"profession\":\"teacher\"},{\"id\":\"bfcd1feb5db2\",\"username\":\"孙顺元\",\"profession\":\"teacher\"},{\"id\":\"fcd1feb5db2\",\"username\":\"朱佳\",\"profession\":\"teacher\"},{\"id\":\"bfcd1feb5db2\",\"username\":\"李茂\",\"profession\":\"teacher\"},{\"id\":\"d1feb5db2\",\"username\":\"周莺\",\"profession\":\"teacher\"},{\"id\":\"cd1feb5db2\",\"username\":\"任倩栋\",\"profession\":\"teacher\"},{\"id\":\"d1feb5db2\",\"username\":\"严庆佳\",\"profession\":\"teacher\"}],\"members\":[{\"id\":\"d1feb5db2\",\"username\":\"彭怡1\",\"profession\":\"student\"},{\"id\":\"d1feb5db2\",\"username\":\"方谦\",\"profession\":\"student\"},{\"id\":\"dd2feb5db2\",\"username\":\"谢鸣瑾\",\"profession\":\"student\"},{\"id\":\"dd2478fb5db2\",\"username\":\"孔秋\",\"profession\":\"student\"},{\"id\":\"dd24cd1feb5db2\",\"username\":\"曹莺安\",\"profession\":\"student\"},{\"id\":\"dd2478eb5db2\",\"username\":\"酆有松\",\"profession\":\"student\"},{\"id\":\"dd2478b5db2\",\"username\":\"姜莺岩\",\"profession\":\"student\"},{\"id\":\"dd2eb5db2\",\"username\":\"谢之轮\",\"profession\":\"student\"},{\"id\":\"dd2eb5db2\",\"username\":\"钱固茂\",\"profession\":\"student\"},{\"id\":\"dd2d1feb5db2\",\"username\":\"潘浩\",\"profession\":\"student\"},{\"id\":\"dd24ab5db2\",\"username\":\"花裕彪\",\"profession\":\"student\"},{\"id\":\"dd24ab5db2\",\"username\":\"史厚婉\",\"profession\":\"student\"},{\"id\":\"dd24a00d1feb5db2\",\"username\":\"陶信勤\",\"profession\":\"student\"},{\"id\":\"dd24a5db2\",\"username\":\"水天固\",\"profession\":\"student\"},{\"id\":\"dd24a5db2\",\"username\":\"柳莎婷\",\"profession\":\"student\"},{\"id\":\"dd2d1feb5db2\",\"username\":\"冯茜\",\"profession\":\"student\"},{\"id\":\"dd24a0eb5db2\",\"username\":\"吕言栋\",\"profession\":\"student\"}],\"creater\":{\"id\":\"1\",\"username\":\"褚奇清\",\"profession\":\"teacher\"}}";

        try {
            mModel = JSON.parseObject(tempData, ContactModel.class);
            setUI();
        } catch (Exception e) {

        }


    }

    private void getWebInfo(int pageNumber) {//还差图片
        String url = Url.getMyNewsList;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pageNumber);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);
                    String data = object.getString("data");
                    try {
                        mModel = JSON.parseObject(data, ContactModel.class);
                        setUI();
                    } catch (Exception e) {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void setUI() {

        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                if (mAdapter != null) {
                    mAdapter.closeOpenedSwipeItemLayoutWithAnim();
                }
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mRecyclerView.getLayoutManager().scrollToPosition(position);
                }

            }
        });
        seperateLists(mModel);
        getWebInfo(1);


        if (mAdapter == null) {
            mAdapter = new ContactAdapter(this, mAllLists, mPermission);
            int orientation = LinearLayoutManager.VERTICAL;
            final LinearLayoutManager layoutManager = new LinearLayoutManager(this, orientation, false);
            mRecyclerView.setLayoutManager(layoutManager);

            mRecyclerView.setAdapter(mAdapter);
            final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(mAdapter);
            mRecyclerView.addItemDecoration(headersDecor);
            mRecyclerView.addItemDecoration(new DividerDecoration(this));

            //   setTouchHelper();
            mAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
                @Override
                public void onChanged() {
                    headersDecor.invalidateHeaders();
                }
            });
        } else {
            mAdapter.notifyDataSetChanged();
        }
        mZSideBar.setupWithRecycler(mRecyclerView);
    }

    private void seperateLists(ContactModel mModel) {
//        //群主
//        ContactModel.CreaterEntity creatorEntity = mModel.getCreater();
//        ContactModel.MembersEntity tempMember = new ContactModel.MembersEntity();
//        tempMember.setUsername(creatorEntity.getUsername());
//        tempMember.setId(creatorEntity.getId());
//        tempMember.setProfession(creatorEntity.getProfession());
//        tempMember.setSortLetters("$");
//
//        mAllLists.add(tempMember);


        //管理员

//        if (mModel.getAdmins() != null && mModel.getAdmins().size() > 0) {
//            for (ContactModel.AdminsEntity e : mModel.getAdmins()) {
//                ContactModel.MembersEntity eMember = new ContactModel.MembersEntity();
//                eMember.setSortLetters("%");
//                eMember.setProfession(e.getProfession());
//                eMember.setUsername(e.getUsername());
//                eMember.setId(e.getId());
//                mAllLists.add(eMember);
//            }
//        }
        //members;
        if (mModel.getMembers() != null && mModel.getMembers().size() > 0) {
            for (int i = 0; i < mModel.getMembers().size(); i++) {
                ContactModel.MembersEntity entity = new ContactModel.MembersEntity();
                entity.setId(mModel.getMembers().get(i).getId());
                entity.setUsername(mModel.getMembers().get(i).getUsername());
                entity.setProfession(mModel.getMembers().get(i).getProfession());
                String pinyin = characterParser.getSelling(mModel.getMembers().get(i).getUsername());
                String sortString = pinyin.substring(0, 1).toUpperCase();

                if (sortString.matches("[A-Z]")) {
                    entity.setSortLetters(sortString.toUpperCase());
                } else {
                    entity.setSortLetters("#");
                }
                mMembers.add(entity);
            }
            Collections.sort(mMembers, pinyinComparator);
            mAllLists.addAll(mMembers);
        }


    }


    public void deleteUser(final int position) {
        mAdapter.remove(mAdapter.getItem(position));
        ToastUtil.showSuccess(getApplicationContext(), "删除成功",Toast.LENGTH_SHORT);

    }


}

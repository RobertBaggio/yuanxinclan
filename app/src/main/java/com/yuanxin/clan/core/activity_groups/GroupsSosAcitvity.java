package com.yuanxin.clan.core.activity_groups;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/3/27 0027 18:18
 */

public class GroupsSosAcitvity extends BaseActivity{

    @BindView(R.id.backacli)
    LinearLayout backacli;
    @BindView(R.id.tososte)
    TextView tososte;
    @BindView(R.id.homesosoedit)
    EditText homesosoedit;
    @BindView(R.id.login_errowimage)
    ImageView login_errowimage;
    @BindView(R.id.sosRecyclerView)
    RecyclerView activityBusinessDistrictLibraryRecyclerView;

    private GroupsMainAdapter adapter;
    private List<GroupsEntity> businessDistrictListEntities = new ArrayList<>();
    private More_LoadDialog mMy_loadingDialog;
    private int ab =1;
    private int pageCount ;
    private String name;
    private int lastposion;

    @Override
    public int getViewLayout() {
        return R.layout.groups_sos;
    }
    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }


    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initRecyclerView();

        mMy_loadingDialog = new More_LoadDialog(GroupsSosAcitvity.this);
        homesosoedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                name=s.toString();
                if (TextUtil.isEmpty(name)){
                    login_errowimage.setVisibility(View.GONE);

                }else {
                    login_errowimage.setVisibility(View.VISIBLE);

                }
            }
        });

    }



    @OnClick({R.id.backacli,R.id.tososte,R.id.login_errowimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backacli:
                finish();

                break;
            case R.id.tososte:
                if (TextUtil.isEmpty(name)){
                return;
            }
                getBusinessDistrictList();
                break;
            case R.id.login_errowimage:
                homesosoedit.setText("");
                break;

        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            //处理事件
            if (TextUtil.isEmpty(name)){
                return false;
            }
            getBusinessDistrictList();
            return true;
        }
        return super.dispatchKeyEvent(event);
    }


    private void initRecyclerView() {
        adapter = new GroupsMainAdapter(GroupsSosAcitvity.this, businessDistrictListEntities);
        adapter.setOnItemClickListener(new GroupsMainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), ActivityGroupsDetail.class);//商圈详情
                intent.putExtra("id", businessDistrictListEntities.get(position).getActivityId());
                intent.putExtra("gid", businessDistrictListEntities.get(position).getGroupId());
                intent.putExtra("gname", businessDistrictListEntities.get(position).getActivityTheme());
                intent.putExtra("gimage", businessDistrictListEntities.get(position).getActivityImage());
                startActivity(intent);
            }
        });

        activityBusinessDistrictLibraryRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        activityBusinessDistrictLibraryRecyclerView.setBackgroundResource(R.color.red);
        activityBusinessDistrictLibraryRecyclerView.setAdapter(adapter);
        activityBusinessDistrictLibraryRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityBusinessDistrictLibraryRecyclerView.setNestedScrollingEnabled(false);//禁止滑动

        activityBusinessDistrictLibraryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    lastposion = manager.findLastCompletelyVisibleItemPosition();
                    Log.i("lgq","..2222........dddddd====="+"....."+lastposion+"....."+pageCount);
                    int totalItemCount = manager.getItemCount();
                    if (lastposion == (totalItemCount - 1) && isSlidingToLast) {
                        ab++;
                        if (ab>pageCount){
                            Toast.makeText(GroupsSosAcitvity.this, "已到底~~", Toast.LENGTH_SHORT).show();
                            return;
                        }
//                            companyInformationDetailNewEntityList.clear();
                        getBusinessDistrictList();
                    }
                    if (lastposion==3){
                        businessDistrictListEntities.clear();
                        ab = 1;
                        getBusinessDistrictList();
                        return;
                    }

                    // 判断是否滚动到底部，并且是向右滚动


                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //dx用来判断横向滑动方向，dy用来判断纵向滑动方向
                //大于0表示，正在向右滚动；小于等于0 表示停止或向左滚动
                isSlidingToLast = dy > 0;
            }
        });

    }

    private void getBusinessDistrictList() {

        String url = Url.activityGroup;
        RequestParams params = new RequestParams();
        params.put("pageNumber", ab);
        params.put("appFlg", 1);
        params.put("activityTheme", name);
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    org.json.JSONObject object = new org.json.JSONObject(s);
                    if (object.getString("success").equals("true")) {
//                        businessDistrictListEntities.clear();
                        pageCount = object.getInt("pageCount");
                        org.json.JSONArray jsonArray = object.getJSONArray("data");
                        if (jsonArray.length()==0){
                            ToastUtil.showWarning(getApplicationContext(), "没有相关数据", Toast.LENGTH_SHORT);
                        }
                        businessDistrictListEntities.clear();
                        businessDistrictListEntities.addAll(FastJsonUtils.getObjectsList(object.getString("data"), GroupsEntity.class));
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog==null){
                        return;
                    }
                    mMy_loadingDialog.dismiss();

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
                if (mMy_loadingDialog==null){
                    return;
                }
                mMy_loadingDialog.dismiss();

            }
        });
    }

}

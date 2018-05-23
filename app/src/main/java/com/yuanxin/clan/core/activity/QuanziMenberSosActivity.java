package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.QuanzimenAdapter;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.entity.BusinessMemberEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2017/12/15 0015 16:51
 */

public class QuanziMenberSosActivity extends BaseActivity {

    @BindView(R.id.selecthyli)
    LinearLayout selecthyli;
    @BindView(R.id.rlLeft)
    LinearLayout rlLeft;
    @BindView(R.id.hyte)
    TextView hyte;
    @BindView(R.id.activi_middle_editextt)
    EditText activi_middle_editextt;
    @BindView(R.id.businessMemberList)
    RecyclerView businessMemberRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;

    private QuanzimenAdapter adapter;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数
    //    private More_LoadDialog mMore_loadDialog;
    private List<BusinessMemberEntity> mMembers = new ArrayList<>();
    private int industryId = -1;
    private static final int REQUEST_INDUSTRY = 11;//行业分类
    private int businessAreaId;

    @Override
    public int getViewLayout() {
        return R.layout.business_member_layout;
    }


    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        businessAreaId = getIntent().getIntExtra("businessAreaId", 501);
        initRecyclerView();
//        mMore_loadDialog = new More_LoadDialog(this);
        activi_middle_editextt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                currentPage = 1;
                mMembers.clear();
                getBusinessMember(currentPage,activi_middle_editextt.getText().toString());
            }
        });
        getBusinessMember(currentPage, null);
    }

    private void initRecyclerView() {
        adapter = new QuanzimenAdapter(this, mMembers);
        adapter.setOnItemClickListener(new QuanzimenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                String link = Url.urlWeb+"/market-supply_demand-info&param="+mMembers.get(position).getSupplyDemandId()+"&appFlg=0";
//                Intent intent = new Intent(BusinessMemberSearchActivity.this, GongXuDetailActivity.class);//商圈详情
//                intent.putExtra("url", link);
//                intent.putExtra("title", mMembers.get(position).getTitle());
//                startActivity(intent);
            }
        });
        adapter.setOnCompanyDetailClickListener(new QuanzimenAdapter.OnCompanyDetailClickListener() {
            @Override
            public void onItemClick(int position) {
                try{
                    Intent intent = new Intent(QuanziMenberSosActivity.this, CompanyDetailWebActivity.class);//有个聊天的标志
                    intent.putExtra("epId", mMembers.get(position).getEnterprise().getEpId());
                    intent.putExtra("title", mMembers.get(position).getEnterprise().getEpNm());
                    intent.putExtra("accessPath", mMembers.get(position).getEpView().getEpAccessPath());
                    startActivity(intent);
                }catch (Exception e){
                    ToastUtil.showInfo(getApplicationContext(), "企业数据缺失", Toast.LENGTH_SHORT);
                    e.printStackTrace();
                }
            }
        });
        adapter.setOnCompanyHistoryListener(new QuanzimenAdapter.OnCompanyHistoryListener() {
            @Override
            public void onItemClick(int position) {
                //跳转企业历程
                String url = Url.urlHost + "/weixin/getJsp?url=wechatweb/business-history&epId=" + mMembers.get(position).getEnterprise().getEpId() + "&businessAreaId=" + businessAreaId;
                startActivity(new Intent(QuanziMenberSosActivity.this, HomeADactivity.class).putExtra("url", url));
            }
        });
        adapter.setOnCompanyVisitListener(new QuanzimenAdapter.OnCompanyVisitListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(QuanziMenberSosActivity.this, AppointmentOneActivity.class);
                intent.putExtra("id", mMembers.get(position).getUser().getUserId());
                intent.putExtra("name", mMembers.get(position).getUser().getUserNm());
                intent.putExtra("ac", mMembers.get(position).getEnterprise().getEpNm());
                intent.putExtra("ph", mMembers.get(position).getUser().getUserPhone());
                startActivity(intent);
            }
        });
        businessMemberRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        businessMemberRecyclerView.setAdapter(adapter);
        businessMemberRecyclerView.setFocusable(false);//导航栏切换不再focuse
        businessMemberRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        fragmnetMyCollectArticleSpringview.setHeader(new RotationHeader(this));
        fragmnetMyCollectArticleSpringview.setFooter(new RotationFooter(this));
        fragmnetMyCollectArticleSpringview.setType(SpringView.Type.OVERLAP);
        fragmnetMyCollectArticleSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetMyCollectArticleSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                mMembers.clear();
                currentPage = 1;
                getBusinessMember(currentPage,activi_middle_editextt.getText().toString());
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetMyCollectArticleSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);

                currentPage += 1;
                if (currentPage>pageCount){
                    ToastUtil.showInfo(QuanziMenberSosActivity.this, "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getBusinessMember(currentPage, activi_middle_editextt.getText().toString());
            }
        });

    }

    @OnClick({R.id.rlLeft,R.id.selecthyli})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                onBackPressed();
                break;
            case R.id.selecthyli:
                Intent publishIntent = new Intent(QuanziMenberSosActivity.this, BusinessDistrictClassifyActviity.class);
                publishIntent.putExtra("search_type", "business_search");
                publishIntent.putExtra("businessAreaId", businessAreaId);
                startActivityForResult(publishIntent, REQUEST_INDUSTRY);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("lgq","requestCode==="+requestCode);
        if (requestCode == REQUEST_INDUSTRY) {
            if (resultCode == RESULT_OK) {//行业分类
                String industryName = data.getStringExtra("industryName");//行业名称
                try {
                    industryId = Integer.parseInt(data.getStringExtra("industryId"));//对应id
                } catch (Exception e) {
                    e.printStackTrace();
                }
                hyte.setText(industryName);
                currentPage = 1;
                mMembers.clear();
                getBusinessMember(currentPage, null);
            }
        }

    }

    private void getBusinessMember(int pageNumber,String userName) {
        String url = Url.getBusinessMember;
        RequestParams params = new RequestParams();
//        mMore_loadDialog.show();
        params.put("pageNumber", pageNumber);
        params.put("businessAreaId", businessAreaId);
        params.put("delFlg", 1);
        if (!TextUtil.isEmpty(userName)) {
            params.put("userNm", userName);
        }
        if (industryId != -1 && industryId != 0) {
            params.put("industryId", industryId);
        }
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(QuanziMenberSosActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
//                mMore_loadDialog.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
//                mMore_loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);
                    pageCount = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
                        mMembers.addAll(FastJsonUtils.getObjectsList(object.getString("data"), BusinessMemberEntity.class));
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(QuanziMenberSosActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }
}

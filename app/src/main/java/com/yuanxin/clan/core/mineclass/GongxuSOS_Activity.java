package com.yuanxin.clan.core.mineclass;

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
import com.yuanxin.clan.core.activity.BusinessDistrictClassifyActviity;
import com.yuanxin.clan.core.activity.GongXuDetailActivity;
import com.yuanxin.clan.core.adapter.AllGongXuAdapter;
import com.yuanxin.clan.core.entity.GongXuEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
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
 * ProjectName: yuanxinclan_new
 * Describe:
 * Author: xjc
 * Date: 2017/11/10 0010 16:39
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class GongxuSOS_Activity extends BaseActivity{

    @BindView(R.id.selecthyli)
    LinearLayout selecthyli;
    @BindView(R.id.rlLeft)
    LinearLayout rlLeft;
    @BindView(R.id.hyte)
    TextView hyte;
    @BindView(R.id.activi_middle_editextt)
    EditText activi_middle_editextt;
    @BindView(R.id.fragment_my_all_crowd_funding_recycler_view)
    RecyclerView fragment_my_all_crowd_funding_recycler_view;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;

    private AllGongXuAdapter adapter;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数
//    private More_LoadDialog mMore_loadDialog;
    private List<GongXuEntity> mGongXuEntities = new ArrayList<>();
    private String industryId;
    private static final int REQUEST_INDUSTRY = 11;//行业分类
    private int supplyDemand = -1;

    @Override
    public int getViewLayout() {
        return R.layout.gongxusoslayout;
    }


    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
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
                mGongXuEntities.clear();
                getMyBusinessDistrict(currentPage,activi_middle_editextt.getText().toString());
            }
        });
        supplyDemand = getIntent().getIntExtra("supplyDemand", -1);
    }

    private void initRecyclerView() {
        adapter = new AllGongXuAdapter(this, mGongXuEntities);
        adapter.setOnItemClickListener(new AllGongXuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                http://192.168.1.106/yuanxinbuluo/weixin/getJsp?url=wechatweb/market-supply_demand-info&param=10000002042271297&appFlg=0
                String link = Url.urlWeb+"/market-supply_demand-info&param="+mGongXuEntities.get(position).getSupplyDemandId()+"&appFlg=0";
                Intent intent = new Intent(GongxuSOS_Activity.this, GongXuDetailActivity.class);//商圈详情
                intent.putExtra("url", link);
                intent.putExtra("title", mGongXuEntities.get(position).getTitle());
                startActivity(intent);
            }
        });
        fragment_my_all_crowd_funding_recycler_view.setLayoutManager(new LinearLayoutManager(this));
        fragment_my_all_crowd_funding_recycler_view.setAdapter(adapter);
        fragment_my_all_crowd_funding_recycler_view.setFocusable(false);//导航栏切换不再focuse
        fragment_my_all_crowd_funding_recycler_view.setNestedScrollingEnabled(false);//禁止滑动
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
                mGongXuEntities.clear();
                currentPage = 1;
                getMyBusinessDistrict(currentPage,activi_middle_editextt.getText().toString());
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
                    ToastUtil.showInfo(GongxuSOS_Activity.this, "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getMyBusinessDistrict(currentPage,activi_middle_editextt.getText().toString());
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
                Intent publishIntent = new Intent(GongxuSOS_Activity.this, BusinessDistrictClassifyActviity.class);
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
                industryId = data.getStringExtra("industryId");//对应id
                hyte.setText(industryName);
            }
        }

    }

    private void getMyBusinessDistrict(int pageNumber,String pageNumberte) {
        String url = Url.getgongxu;
        RequestParams params = new RequestParams();
        if (supplyDemand != -1) {
            params.put("supplyDemand", supplyDemand);
        }
        params.put("pageNumber", pageNumber);
        params.put("status", 1);
        params.put("title", pageNumberte);
        if (!TextUtil.isEmpty(industryId)) {
            params.put("industryId", industryId);
        }
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(GongxuSOS_Activity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    pageCount = object.getInt("pageCount");
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject businessObject = jsonArray.getJSONObject(a);
                            String supplyDemandId = businessObject.getString("supplyDemandId");//商圈id
                            String image1 = businessObject.getString("image1");//图片
                            String image11 = Url.img_domain + image1+Url.imageStyle640x640;
                            String image2 = businessObject.getString("image2");//图片
                            String image22 = Url.img_domain + image2+Url.imageStyle640x640;
                            String image3 = businessObject.getString("image3");//图片
                            String image33 = Url.img_domain + image3+Url.imageStyle640x640;
                            String content = businessObject.getString("content");//商圈名称
                            String createDt = businessObject.getString("createDt");
                            String epId = businessObject.getString("epId");
                            String supplyDemand = businessObject.getString("supplyDemand");
                            String userId = businessObject.getString("userId");
                            String title = businessObject.getString("title");
                            String number = businessObject.getString("number");
                            String address = businessObject.getString("address");
                            JSONObject twoob=new JSONObject(address);
                            String city = twoob.getString("city");
//                            Log.v("Lgq","....... " +image11+"  ...   "+image22+"  。。。 "+image33);

                            GongXuEntity entity = new GongXuEntity();

                            entity.setImage1(TextUtil.isEmpty(image1)?image1:image11);
                            entity.setImage2(TextUtil.isEmpty(image2)?image2:image22);
                            entity.setImage3(TextUtil.isEmpty(image3)?image3:image33);

                            entity.setContent(content);
                            entity.setCreateDt(createDt);
                            entity.setEpId(epId);
                            entity.setSupplyDemandId(supplyDemandId);
                            entity.setSupplyDemand(supplyDemand);
                            entity.setUserId(userId);
                            entity.setTitle(title);
                            entity.setCity(city);

                            mGongXuEntities.add(entity);
                        }
                        adapter.notifyDataSetChanged();


                    } else {
                        ToastUtil.showWarning(GongxuSOS_Activity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });
    }
}

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.yuanxin.clan.core.adapter.PaiZhaoAdapter;
import com.yuanxin.clan.core.company.bean.PaizhaoEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.More_LoadDialog;
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
 * Author: lgq
 * Date: 2018/1/2 0002 14:46
 */

public class PaiZhaoSosActivity extends BaseActivity {

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

    private PaiZhaoAdapter adapter;
    private int currentPage = 1,pageCount;// 当前页面，从0开始计数
    private More_LoadDialog mMore_loadDialog;
    private int industryId = -1;
    private static final int REQUEST_INDUSTRY = 11;//行业分类
    private int businessAreaId;
    private List<PaizhaoEntity> mPaizhaoEntities = new ArrayList<>();

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
        mMore_loadDialog = new More_LoadDialog(this);
        selecthyli.setVisibility(View.GONE);
        initRecyclerView();
        activi_middle_editextt.setHint("输入牌照名称/类型");
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
                mPaizhaoEntities.clear();
                getMyLicensePlate(currentPage,activi_middle_editextt.getText().toString());
            }
        });

    }

    private void initRecyclerView() {
        adapter = new PaiZhaoAdapter(this, mPaizhaoEntities);
        adapter.setOnItemClickListener(new PaiZhaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext(), GongXuDetailActivity.class);//商圈详情
                String link = Url.urlWeb+"/license-plate-info&param="+mPaizhaoEntities.get(position).getLicensePlateId()+"&appFlg=1";
                intent.putExtra("url", link);
                intent.putExtra("title", "牌照详情");
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
                mPaizhaoEntities.clear();
                currentPage = 1;
                getMyLicensePlate(currentPage,activi_middle_editextt.getText().toString());
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
                    ToastUtil.showInfo(PaiZhaoSosActivity.this, "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getMyLicensePlate(currentPage, activi_middle_editextt.getText().toString());
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
                Intent publishIntent = new Intent(PaiZhaoSosActivity.this, BusinessDistrictClassifyActviity.class);
                publishIntent.putExtra("search_type", "business_search");
                publishIntent.putExtra("businessAreaId", businessAreaId);
                startActivityForResult(publishIntent, REQUEST_INDUSTRY);
                break;
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.v("lgq","requestCode==="+requestCode);
//        if (requestCode == REQUEST_INDUSTRY) {
//            if (resultCode == RESULT_OK) {//行业分类
//                String industryName = data.getStringExtra("industryName");//行业名称
//                try {
//                    industryId = Integer.parseInt(data.getStringExtra("industryId"));//对应id
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                hyte.setText(industryName);
//                currentPage = 1;
//                mMembers.clear();
//                getBusinessMember(currentPage, null);
//            }
//        }
//
//    }

    private void getMyLicensePlate( int pageNumber,String name) {
        String url = Url.businessLicensePlate;
        RequestParams params = new RequestParams();
        mMore_loadDialog.show();
        params.put("pageNumber", pageNumber);
        params.put("businessAreaId", businessAreaId);
        params.put("appFlg", 1);
        params.put("licensePlateName", name);
        params.put("industryId", "");
        params.put("province", "");
        params.put("city", "");
        params.put("area", "");
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(PaiZhaoSosActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
                mMore_loadDialog.dismiss();
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                mMore_loadDialog.dismiss();
                try {
                    JSONObject object = new JSONObject(s);
                    pageCount = object.getInt("pageCount");
                    if (object.getString("total").equals("0")){
                        ToastUtil.showInfo(PaiZhaoSosActivity.this, "无相应名称牌照数据", Toast.LENGTH_SHORT);
                        return;
                    }
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        if (jsonArray.length() == 0) {
//                            kongli.setVisibility(View.VISIBLE);
                        }
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject businessObject = jsonArray.getJSONObject(a);
                            String licensePlateId = businessObject.getString("licensePlateId");//商圈id
                            String image1 = businessObject.getString("licensePlateImage1");//图片
                            String imageend = Url.img_domain + image1 + Url.imageStyle640x640;
                            String businessAreaId = businessObject.getString("businessAreaId");//图片
                            String licensePlateName = businessObject.getString("licensePlateName");//图片
                            String licensePlatePrice = businessObject.getString("licensePlatePrice");//商圈名称
                            String establishYear = businessObject.getString("establishYear");
                            String companyType = businessObject.getString("companyType");
                            String registeredCapital = businessObject.getString("registeredCapital");
                            String createDt = businessObject.getString("createDt");
                            String industry = businessObject.getString("industry");
                            String address = businessObject.getString("address");
                            JSONObject twoob = new JSONObject(address);
                            JSONObject twoobi = new JSONObject(industry);
                            String city = twoob.getString("city");
                            String industryNm = twoobi.getString("industryNm");
//                            Log.v("Lgq","....... " +image11+"  ...   "+image22+"  。。。 "+image33);

                            PaizhaoEntity entity = new PaizhaoEntity();
                            entity.setBusinessAreaId(businessAreaId);
                            entity.setCity(city);
                            entity.setIndustryNm(industryNm);
                            entity.setLicensePlateName(licensePlateName);
                            entity.setCreateDt(createDt);
                            entity.setLicensePlatePrice(licensePlatePrice);
                            entity.setRegisteredCapital(registeredCapital);
                            entity.setLicensePlateId(licensePlateId);
                            entity.setEstablishYear(establishYear);

                            mPaizhaoEntities.add(entity);
                        }
//                        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) businessMemberRecyclerView.getLayoutParams(); //取控件textView当前的布局参数
//                        linearParams.height = UIUtils.dip2px(208) * mPaizhaoEntities.size();// 控件的高强制设成20
                        adapter.notifyDataSetChanged();


                    } else {
                        ToastUtil.showWarning(PaiZhaoSosActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

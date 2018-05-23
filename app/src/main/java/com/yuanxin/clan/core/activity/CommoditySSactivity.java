package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.CommdssAdapter;
import com.yuanxin.clan.core.company.bean.CommodityEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.My_LoadingDialog;
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
 * ProjectName: yuanxinclan
 * 商品搜索
 */

public class CommoditySSactivity extends BaseActivity{
    @BindView(R.id.activity_yuan_xin_crowd_right)
    LinearLayout activity_yuan_xin_crowd_right;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activity_yuan_xin_crowd_left_layout;
    @BindView(R.id.activi_middle_editext)
    EditText activi_middle_editext;
    @BindView(R.id.activity_company_information_detail_recycler_view)
    RecyclerView activity_company_information_detail_recycler_view;


    private int epId;
    private String name;
    private CommdssAdapter mAdapter;
    private List<CommodityEntity> mCommodityEntityList = new ArrayList<>();
    private int pageContent;
    private int lastposion,pagenum;
    private My_LoadingDialog mMy_loadingDialog;

    @Override
    public int getViewLayout() {
        return R.layout.commoditysslayout;

    }
    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, final Intent intent) {
        epId = getIntent().getIntExtra("epId",0);
        mMy_loadingDialog = My_LoadingDialog.getInstance(CommoditySSactivity.this);
        mAdapter = new CommdssAdapter(CommoditySSactivity.this,mCommodityEntityList);
        mAdapter.setOnItemClickListener(new CommdssAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent1 = new Intent(CommoditySSactivity.this,My_CommodityActivity.class);
                intent1.putExtra("id",mCommodityEntityList.get(position).getCommodityId());
                startActivity(intent1);
            }
        });
//        activity_company_information_detail_recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        activity_company_information_detail_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        activity_company_information_detail_recycler_view.setAdapter(mAdapter);
        activity_company_information_detail_recycler_view.setNestedScrollingEnabled(false);//禁止滑动
        activity_company_information_detail_recycler_view.setFocusable(false);//导航栏切换不再focuse

        activity_company_information_detail_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动，既是否向右滑动或向下滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //获取最后一个完全显示的ItemPosition
                    lastposion = manager.findLastCompletelyVisibleItemPosition();
//                    Log.v("lgq","..........dddddd====="+"....."+lastposion);
                    int totalItemCount = manager.getItemCount();
                    if (lastposion == (totalItemCount - 1) && isSlidingToLast) {
//                        if (enable)
//                        mMy_loadingDialog.show();
                        pagenum++;
                        if (pagenum>pageContent){
                            ToastUtil.showInfo(getApplicationContext(), "已加载完", Toast.LENGTH_SHORT);
                            return;
                        }
//                            companyInformationDetailNewEntityList.clear();
                        getWebInfo(name, epId  , pagenum);
                    }
                    if (lastposion==1){
                        mCommodityEntityList.clear();
                        pagenum=1;
//                        mMy_loadingDialog.show();
                        getWebInfo(name, epId  , pagenum);
                        Log.v("lgq",".......shuaxing.....");
                        return;
                    }

                    // 判断是否滚动到底部，并且是向右滚动
                    if (pagenum==pageContent){
                        ToastUtil.showInfo(getApplicationContext(), "已加载完", Toast.LENGTH_SHORT);
                        return;
                    }

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

    @OnClick({R.id.activity_yuan_xin_crowd_right,R.id.activity_yuan_xin_crowd_left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_crowd_left_layout:
                onBackPressed();
                break;
            case R.id.activity_yuan_xin_crowd_right:
                name = activi_middle_editext.getText().toString();
                if (TextUtil.isEmpty(name)){
                    ToastUtil.showInfo(getApplicationContext(), "请输入关键字", Toast.LENGTH_SHORT);
                    return;
                }
                getWebInfo(name,epId,1);
                break;

        }
    }

    //恢复/industrylist
    private void getWebInfo(String name, int id,int pageNumber) {
        String url = Url.getcommdss;
        RequestParams params = new RequestParams();
        mMy_loadingDialog.show();
        params.put("epId", id);
        params.put("under", 1);
        params.put("commodityNm", name);
        params.put("pageNumber", pageNumber);
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    pageContent = object.getInt("pageCount");
//                    Log.v("lgq",".....企业库。。数据。。test==="+s);
                    if (object.getString("success").equals("true")) {
                        JSONArray dataArray = object.getJSONArray("data");
                        for (int d = 0; d < dataArray.length(); d++) {
                            JSONObject dataObject = dataArray.getJSONObject(d);
                            String image =Url.img_domain+ dataObject.getString("commodityImage1")+Url.imageStyle640x640;
                            String name = dataObject.getString("commodityNm");
                            String detail = dataObject.getString("commodityDetail");
//                            String price = dataObject.getString("commodityPrice");
                            double max =dataObject.getDouble("maxPrice");
                            double min =dataObject.getDouble("minPrice");
                            int id= dataObject.getInt("commodityId");

                            CommodityEntity entity = new CommodityEntity();
                            entity.setCommodityDetail(detail);
                            entity.setCommodityId(id);
                            entity.setMaxPrice(max);
                            entity.setMinPrice(min);
                            entity.setCommodityImage1(image);
                            entity.setCommodityNm(name);

                            mCommodityEntityList.add(entity);

                        }
                            mAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }
}

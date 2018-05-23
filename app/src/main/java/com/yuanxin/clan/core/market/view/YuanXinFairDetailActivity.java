package com.yuanxin.clan.core.market.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.adapter.YuanXinFairDetailCommodityAdapter;
import com.yuanxin.clan.core.market.adapter.YuanXinFairDetailEnterpriseAdapter;
import com.yuanxin.clan.core.market.bean.CommdoityDetail;
import com.yuanxin.clan.core.market.bean.EnterpriseDetail;
import com.yuanxin.clan.core.market.bean.MarketDetail;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.familiarrecyclerview.FamiliarRecyclerView;

/**
 * ProjectName:无用
 * Describe: 集市详情
 */
public class YuanXinFairDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.rcvGoods)
    FamiliarRecyclerView mRcvGoods;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_title_left_layout)
    LinearLayout mActivityYuanXinFairNewDetailTitleLeftLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_right_layout)
    LinearLayout mActivityYuanXinFairNewDetailRightLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_title_layout)
    RelativeLayout mActivityYuanXinFairNewDetailTitleLayout;

    ImageView mTvFairImage;
    FamiliarRecyclerView mRcvBrand;
    ImageView mImgGoodImage1;
    TextView mTvGoodName1;
    TextView mTvGoodPrice1;
    ImageView mImgGoodImage2;
    TextView mTvGoodName2;
    TextView mTvGoodPrice2;
    ImageView mImgGoodImage3;
    ImageView mImgGoodImage4;
    ImageView mImgGoodImage5;

    private int marketId;
    private List<CommdoityDetail> mTopCommdoityDetails;//展销top商品
    private List<CommdoityDetail> mCommdoityDetails = new ArrayList<>();//展销商品
    private View headerView;
    private int pageNumber = 1;
    private YuanXinFairDetailCommodityAdapter mFairDetailCommodityAdapter;

    @Override
    public int getViewLayout() {
        return R.layout.activity_yuan_xin_fair_detail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        headerView = View.inflate(this, R.layout.activity_yuan_xin_fair_detail_header, null);
        loadView();
        marketId = getIntent().getIntExtra("marketId", 0);
        initRecyclerView();
        getMarketDetail();
    }

    private void loadView() {
        mTvFairImage = ButterKnife.findById(headerView, R.id.tvFairImage);
        mRcvBrand = ButterKnife.findById(headerView, R.id.rcvBrand);
        mImgGoodImage1 = ButterKnife.findById(headerView, R.id.imgGoodImage1);
        mTvGoodName1 = ButterKnife.findById(headerView, R.id.tvGoodName1);
        mTvGoodPrice1 = ButterKnife.findById(headerView, R.id.tvGoodPrice1);
        mImgGoodImage2 = ButterKnife.findById(headerView, R.id.imgGoodImage2);
        mTvGoodName2 = ButterKnife.findById(headerView, R.id.tvGoodName2);
        mTvGoodPrice2 = ButterKnife.findById(headerView, R.id.tvGoodPrice2);
        mImgGoodImage3 = ButterKnife.findById(headerView, R.id.imgGoodImage3);
        mImgGoodImage4 = ButterKnife.findById(headerView, R.id.imgGoodImage4);
        mImgGoodImage5 = ButterKnife.findById(headerView, R.id.imgGoodImage5);
        mImgGoodImage1.setOnClickListener(this);
        mImgGoodImage2.setOnClickListener(this);
        mImgGoodImage3.setOnClickListener(this);
        mImgGoodImage4.setOnClickListener(this);
        mImgGoodImage5.setOnClickListener(this);
    }


    private void getMarketDetail() {
        String url = Url.getMarketDetailList;
        RequestParams params = new RequestParams();
        params.put("marketId", marketId);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("Lgq","jishi.............."+s);
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        MarketDetail marketDetail = FastJsonUtils.parseObject(object.getString("data"), MarketDetail.class);
                        if (marketDetail != null) {
                            setViewData(marketDetail);
                        }
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void setViewData(MarketDetail marketDetail) {
        if (!TextUtil.isEmpty(marketDetail.getMarketImg())) {
            ImageManager.load(YuanXinFairDetailActivity.this, marketDetail.getMarketImg(), R.drawable.banner01, mTvFairImage);
        }
        List<EnterpriseDetail> mEnterpriseDetails = marketDetail.getEnterprise();//入驻企业
        YuanXinFairDetailEnterpriseAdapter fairDetailEnterpriseAdapter = new YuanXinFairDetailEnterpriseAdapter(mEnterpriseDetails);
        mRcvBrand.setAdapter(fairDetailEnterpriseAdapter);
        mTopCommdoityDetails = marketDetail.getCommodityTop();
        //第一个商品
        if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 0) {
            if (!TextUtil.isEmpty(mTopCommdoityDetails.get(0).getCommodityImage1())) {
                ImageManager.load(getApplicationContext(), mTopCommdoityDetails.get(0).getCommodityImage1(), R.drawable.banner01, mImgGoodImage1);
            }
            mTvGoodName1.setText(mTopCommdoityDetails.get(0).getCommodityNm());
            mTvGoodPrice1.setText("￥" + mTopCommdoityDetails.get(0).getCommodityPrice());
        }
        //第二个商品
        if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 1) {
            if (!TextUtil.isEmpty(mTopCommdoityDetails.get(1).getCommodityImage1())) {
                ImageManager.load(getApplicationContext(), mTopCommdoityDetails.get(1).getCommodityImage1(), R.drawable.banner01, mImgGoodImage2);
            }
            mTvGoodName2.setText(mTopCommdoityDetails.get(1).getCommodityNm());
            mTvGoodPrice2.setText("￥" + mTopCommdoityDetails.get(1).getCommodityPrice());
        }
        //第三个商品
        if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 2) {
            if (!TextUtil.isEmpty(mTopCommdoityDetails.get(2).getCommodityImage1())) {
                ImageManager.load(getApplicationContext(), mTopCommdoityDetails.get(2).getCommodityImage1(), R.drawable.banner01, mImgGoodImage3);
            }
        }
        //第四个商品
        if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 3) {
            if (!TextUtil.isEmpty(mTopCommdoityDetails.get(3).getCommodityImage1())) {
                ImageManager.load(getApplicationContext(), mTopCommdoityDetails.get(3).getCommodityImage1(), R.drawable.banner01, mImgGoodImage4);
            }
        }
        //第五个商品
        if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 4) {
            if (!TextUtil.isEmpty(mTopCommdoityDetails.get(4).getCommodityImage1())) {
                ImageManager.load(getApplicationContext(), mTopCommdoityDetails.get(4).getCommodityImage1(), R.drawable.banner01, mImgGoodImage5);
            }
        }
        mCommdoityDetails.clear();
        mCommdoityDetails.addAll(marketDetail.getCommodity());
        initRecyclerView();
    }


    private void initRecyclerView() {
        if (null == mFairDetailCommodityAdapter) {
            mFairDetailCommodityAdapter = new YuanXinFairDetailCommodityAdapter(mCommdoityDetails);
            mFairDetailCommodityAdapter.addHeaderView(headerView);
            mFairDetailCommodityAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (0 == mCommdoityDetails.get(position).getCommodityId()) {
                        ToastUtil.showInfo(getApplicationContext(), "该商品已下架", Toast.LENGTH_LONG);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), GoodsDetailActivity.class);
                        intent.putExtra("commodityId", String.valueOf(mCommdoityDetails.get(position).getCommodityId()));
                        intent.putExtra("type", GoodsDetailActivity.MARKET);
                        startActivity(intent);
                    }
                }
            });
            mRcvGoods.setAdapter(mFairDetailCommodityAdapter);
        } else {
            mFairDetailCommodityAdapter.setNewData(mCommdoityDetails);
            mFairDetailCommodityAdapter.notifyDataSetChanged();
        }
    }


    @OnClick({R.id.activity_yuan_xin_fair_new_detail_right_layout, R.id.activity_yuan_xin_fair_new_detail_title_left_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_detail_right_layout:
                startActivity(new Intent(YuanXinFairDetailActivity.this, ShoppingCartActivity.class));
                break;
            case R.id.activity_yuan_xin_fair_new_detail_title_left_layout:
                finish();
                break;
            case R.id.imgGoodImage1://1
                //第一个商品
                if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(), GoodsDetailActivity.class);
                    intent.putExtra("commodityId", String.valueOf(mTopCommdoityDetails.get(0).getCommodityId()));
                    intent.putExtra("type", GoodsDetailActivity.MARKET);
                    startActivity(intent);
                }
                break;
            case R.id.imgGoodImage2://2
                //第二个商品
                if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 1) {
                    Intent intentFour = new Intent(getApplicationContext(), GoodsDetailActivity.class);
                    intentFour.putExtra("commodityId", String.valueOf(mTopCommdoityDetails.get(1).getCommodityId()));
                    intentFour.putExtra("type", GoodsDetailActivity.MARKET);
                    startActivity(intentFour);
                }
                break;
            case R.id.imgGoodImage3://3
                //第三个商品
                if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 2) {
                    Intent intentOne = new Intent(getApplicationContext(), GoodsDetailActivity.class);
                    intentOne.putExtra("commodityId", String.valueOf(mTopCommdoityDetails.get(2).getCommodityId()));
                    intentOne.putExtra("type", GoodsDetailActivity.MARKET);
                    startActivity(intentOne);
                }
                break;
            case R.id.imgGoodImage4://4
                //第四个商品
                if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 3) {
                    Intent intentTwo = new Intent(getApplicationContext(), GoodsDetailActivity.class);
                    intentTwo.putExtra("commodityId", String.valueOf(mTopCommdoityDetails.get(3).getCommodityId()));
                    intentTwo.putExtra("type", GoodsDetailActivity.MARKET);
                    startActivity(intentTwo);
                }
                break;
            case R.id.imgGoodImage5://5
                //第五个商品
                if (mTopCommdoityDetails != null && mTopCommdoityDetails.size() > 4) {
                    Intent intentThree = new Intent(getApplicationContext(), GoodsDetailActivity.class);
                    intentThree.putExtra("commodityId", String.valueOf(mTopCommdoityDetails.get(4).getCommodityId()));
                    intentThree.putExtra("type", GoodsDetailActivity.MARKET);
                    startActivity(intentThree);
                }
                break;
        }
    }
}

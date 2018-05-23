package com.yuanxin.clan.core.market.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.easeui.EaseConstant;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.LoginActivity;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.market.bean.CommdoityDetail;
import com.yuanxin.clan.core.market.bean.OrderCommodity;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.manager.ImageManager;
import com.yuanxin.clan.mvp.utils.CommonString;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.WebViewJavaScriptFunction;
import com.yuanxin.clan.mvp.view.X5WebView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * ProjectName: yuanxinclan
 * Describe: 集市 商品详情
 * Author: xjc
 * Date: 2017/6/16 0016 15:29
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class GoodsDetailActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_image)
    ImageView activityYuanXinFairNewDetailGoodsDetailImage;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_title_left_layout)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailTitleLeftLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_right_image)
    ImageView activityYuanXinFairNewDetailGoodsDetailRightImage;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_right_layout)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailRightLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_title_layout)
    RelativeLayout activityYuanXinFairNewDetailGoodsDetailTitleLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_price)
    TextView activityYuanXinFairNewDetailGoodsDetailPrice;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_price_layout)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailPriceLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_name)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsName;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_introduce_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsIntroduceText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_introduce_one)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsIntroduceOne;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_choice_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsChoiceText;
    @BindView(R.id.flySize)
    TagFlowLayout mFlySize;
    @BindView(R.id.flyColor)
    TagFlowLayout mFlyColor;
    @BindView(R.id.btnDecrease)
    ImageView mBtnDecrease;
    @BindView(R.id.etAmount)
    TextView etAmount;
    @BindView(R.id.btnIncrease)
    ImageView mBtnIncrease;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_total)
    TextView activityYuanXinFairNewDetailGoodsDetailTotal;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_choice_layout)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailGoodsChoiceLayout;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_introduce_one_text_one)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsIntroduceOneTextOne;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_one)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextOne;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_compnay_company)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextCompnayCompany;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_compnay)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextCompnay;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_code_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextCodeText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_code)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextCode;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_cai)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextCai;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_style_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextStyleText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_style)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextStyle;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_size_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextSizeText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_size)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextSize;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_weight_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextWeightText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_weight)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextWeight;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_color_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextColorText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text_color)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextColor;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_introduce_one_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsIntroduceOneText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_text)
    TextView activityYuanXinFairNewDetailGoodsDetailGoodsPictureText;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_goods_picture_recycler_view)
    X5WebView activityYuanXinFairNewDetailGoodsDetailGoodsPictureRecyclerView;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_cart)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailCart;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_talk)
    LinearLayout activityYuanXinFairNewDetailGoodsDetailTalk;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_buy)
    TextView activityYuanXinFairNewDetailGoodsDetailBuy;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_put_shop_cart)
    TextView activityYuanXinFairNewDetailGoodsDetailPutShopCart;
    @BindView(R.id.lyMenuMarket)
    LinearLayout mLyMenuMarket;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_buy_gift)
    TextView mActivityYuanXinFairNewDetailGoodsDetailBuyGift;
    @BindView(R.id.activity_yuan_xin_fair_new_detail_goods_detail_put_shop_cart_gift)
    TextView mActivityYuanXinFairNewDetailGoodsDetailPutShopCartGift;
    @BindView(R.id.lyMenuGift)
    LinearLayout mLyMenuGift;

    private String commodityId;
    private boolean flag;
    private LocalBroadcastManager localBroadcastManager;

    private CommdoityDetail mCommdoityDetail;
    private int type = MARKET;
    public static final int MARKET = 1; //集市
    public static final int GIFT = 2;  //礼品

    @Override
    public int getViewLayout() {
        return R.layout.activity_yuan_xin_fair_new_detail_goods_detail;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        commodityId = getIntent().getStringExtra("commodityId");//商品id
        type = getIntent().getIntExtra("type", MARKET);//商品类型
        loadView();
        initModule(commodityId);
        getWebInfo(commodityId);
    }

    private void loadView() {
        if (type == GIFT) {
            mLyMenuGift.setVisibility(View.VISIBLE);
            mLyMenuMarket.setVisibility(View.GONE);
        } else {
            mLyMenuGift.setVisibility(View.GONE);
            mLyMenuMarket.setVisibility(View.VISIBLE);
        }
    }

    public void initModule(String commodityId) {//下面的商品详情
        String url;
        if (type == GIFT) {
            url = Url.urlWebGift + commodityId + "&appFlg=1";
        } else {
            url = Url.urlWebCommodity + commodityId + "&appFlg=1";
        }
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureRecyclerView.loadUrl(url);

        activityYuanXinFairNewDetailGoodsDetailGoodsPictureRecyclerView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }


            @JavascriptInterface
            public void toLogin() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
            @JavascriptInterface
            public void onPhone(String toChatUsername) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+toChatUsername));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            @JavascriptInterface
            public void toCall(String toChatUsername) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+toChatUsername));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            /**
             * 测试坐标上传是否成功
             *
             * @param latitude
             * @param longitude
             */
            @JavascriptInterface
            public void outLocation(String latitude, String longitude) {
                System.out.println("javascript输出：" + latitude + "  " + longitude);
            }
        }, CommonString.js2Android);
    }

    private void getWebInfo(String commodityId) {
        String url;
        if (type == GIFT) {
            url = Url.getCommodityDetailDetailList;
        } else {
            url = Url.getCommodityDetailList;
        }
        RequestParams params = new RequestParams();
        params.put("commodityId", commodityId);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        mCommdoityDetail = FastJsonUtils.parseObject(object.getString("data"), CommdoityDetail.class);
                        if (mCommdoityDetail != null) {
                            initDataView();
                        }
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }
//    commodityNumber
    private void initDataView() {
        if (null == mCommdoityDetail) {
            return;
        }
        if (!TextUtil.isEmpty(mCommdoityDetail.getCommodityImage1())) {
            ImageManager.load(GoodsDetailActivity.this, mCommdoityDetail.getCommodityImage1(), R.drawable.banner01, activityYuanXinFairNewDetailGoodsDetailImage);
        }
        activityYuanXinFairNewDetailGoodsDetailGoodsName.setText(mCommdoityDetail.getCommodityNm());
        activityYuanXinFairNewDetailGoodsDetailPrice.setText(String.valueOf(mCommdoityDetail.getCommodityPrice()));
        //commodityStock 库存
        activityYuanXinFairNewDetailGoodsDetailTotal.setText("库存：" + mCommdoityDetail.getCommodityStock());
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextCompnayCompany.setText(mCommdoityDetail.getEpNm());
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextCodeText.setText(mCommdoityDetail.getCommodityNumber());
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextText.setText(mCommdoityDetail.getCommodityMaterial());
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextStyleText.setText(mCommdoityDetail.getCommodityStyle());
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextSizeText.setText(mCommdoityDetail.getCommoditySize());
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextWeightText.setText(mCommdoityDetail.getCommodityWeight());
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureTextColorText.setText(mCommdoityDetail.getCommodityColor());
        activityYuanXinFairNewDetailGoodsDetailGoodsIntroduceText.setText(mCommdoityDetail.getCommodityDetail());
        initChooseColor();//颜色 大小 选择
        initChooseSize();
    }


    private void initChooseColor() {
        if (TextUtil.isEmpty(mCommdoityDetail.getCommodityColor())) {
            return;
        }
        TagAdapter mAdapter = new TagAdapter<String>(mCommdoityDetail.getCommodityColor().split(",")) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) View.inflate(GoodsDetailActivity.this, R.layout.item_yuan_xin_fair_detail_commodity_size, null);
                tv.setText(s);
                return tv;
            }
        };
        mFlyColor.setAdapter(mAdapter);
        //预先设置选中
        mAdapter.setSelectedList(0);
    }

    private void initChooseSize() {
        if (TextUtil.isEmpty(mCommdoityDetail.getCommoditySp())) {
            return;
        }
        TagAdapter mAdapter = new TagAdapter<String>(mCommdoityDetail.getCommoditySp().split(",")) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) View.inflate(GoodsDetailActivity.this, R.layout.item_yuan_xin_fair_detail_commodity_size, null);
                tv.setText(s);
                return tv;
            }
        };
        mFlySize.setAdapter(mAdapter);
        //预先设置选中
        mAdapter.setSelectedList(0);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityYuanXinFairNewDetailGoodsDetailGoodsPictureRecyclerView = null;
    }

    @OnClick({R.id.activity_yuan_xin_fair_new_detail_goods_detail_right_image, R.id.activity_yuan_xin_fair_new_detail_goods_detail_cart,
            R.id.btnDecrease, R.id.btnIncrease, R.id.activity_yuan_xin_fair_new_detail_goods_detail_title_left_layout,
            R.id.activity_yuan_xin_fair_new_detail_goods_detail_talk, R.id.activity_yuan_xin_fair_new_detail_goods_detail_buy,
            R.id.activity_yuan_xin_fair_new_detail_goods_detail_put_shop_cart, R.id.activity_yuan_xin_fair_new_detail_goods_detail_buy_gift,
            R.id.activity_yuan_xin_fair_new_detail_goods_detail_put_shop_cart_gift})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_yuan_xin_fair_new_detail_goods_detail_cart://查看购物车
                startActivity(new Intent(GoodsDetailActivity.this, ShoppingCartActivity.class));
                break;
            case R.id.btnDecrease://-1
                int amount = Integer.valueOf(etAmount.getText().toString());
                if (amount > 1) {
                    amount--;
                    etAmount.setText(String.valueOf(amount));
                }
                break;
            case R.id.btnIncrease://+1
                int amountNew = Integer.valueOf(etAmount.getText().toString());
                if (mCommdoityDetail != null) {
                    //大于库存就不能加了
                    if (amountNew < mCommdoityDetail.getCommodityStock()) {
                        amountNew++;
                    }
                } else {
                    amountNew++;
                }
                etAmount.setText(String.valueOf(amountNew));
                break;
            case R.id.activity_yuan_xin_fair_new_detail_goods_detail_title_left_layout:
                finish();
                break;
            case R.id.activity_yuan_xin_fair_new_detail_goods_detail_right_image://收藏
                if (!flag) {
                    activityYuanXinFairNewDetailGoodsDetailRightImage.setImageResource(R.drawable.news_collecte_pre);
                    collecte();//收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intentThree = new Intent("com.example.broadcasttest.GOODS_FRESH");
                    localBroadcastManager.sendBroadcast(intentThree); // 发送本地广播
                } else {
                    activityYuanXinFairNewDetailGoodsDetailRightImage.setImageResource(R.drawable.news_collecte_nomal);
                    deleteCollecte();//取消收藏
                    //发送广播
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent intentFour = new Intent("com.example.broadcasttest.GOODS_FRESH");
                    localBroadcastManager.sendBroadcast(intentFour); // 发送本地广播
                }
                flag = !flag;
                break;
            case R.id.activity_yuan_xin_fair_new_detail_goods_detail_buy://加入购物车
                addGoodsToCar();
                localBroadcastManager = LocalBroadcastManager.getInstance(this);
                Intent intentThree = new Intent("com.example.broadcasttest.ADD_GOODS_TO_CAR_FRESH");
                localBroadcastManager.sendBroadcast(intentThree); // 发送本地广播
                break;
            case R.id.activity_yuan_xin_fair_new_detail_goods_detail_put_shop_cart_gift://立即购买
            case R.id.activity_yuan_xin_fair_new_detail_goods_detail_put_shop_cart://立即购买
                goToBuy();
                break;
            case R.id.activity_yuan_xin_fair_new_detail_goods_detail_buy_gift://在线沟通
            case R.id.activity_yuan_xin_fair_new_detail_goods_detail_talk://在线沟通
                if (mCommdoityDetail != null) {
                    Intent intentTwo = new Intent(GoodsDetailActivity.this, PersionChatActivity.class);
                    if (type == GIFT) {
                        if (mCommdoityDetail.getUser() == null || TextUtil.isEmpty(mCommdoityDetail.getUser().getUserPhone())) {
                            ToastUtil.showInfo(getApplicationContext(), "暂时联系不上卖家", Toast.LENGTH_SHORT);
                            return;
                        }
                        intentTwo.putExtra(Constant.EXTRA_USER_ID, TextUtil.isEmpty(mCommdoityDetail.getUser().getUserPhone()) ? EaseConstant.EXTRA_HX_YXKF : mCommdoityDetail.getUser().getUserPhone());
                    } else {
                        if (TextUtil.isEmpty(mCommdoityDetail.getUserPhone())) {
                            ToastUtil.showInfo(getApplicationContext(), "暂时联系不上卖家", Toast.LENGTH_SHORT);
                            return;
                        }
                        intentTwo.putExtra(Constant.EXTRA_USER_ID, TextUtil.isEmpty(mCommdoityDetail.getUserPhone()) ? EaseConstant.EXTRA_HX_YXKF : mCommdoityDetail.getUserPhone());
                    }
                    intentTwo.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                    intentTwo.putExtra(Constant.USER_NAME, UserNative.getName());
                    intentTwo.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                    intentTwo.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                    intentTwo.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                    intentTwo.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                    intentTwo.putExtra("type", "与商家聊天中");
                    startActivity(intentTwo);
                }
        }
    }

    private void goToBuy() {
        if (mCommdoityDetail == null) {
            return;
        }
        int numberOne = Integer.valueOf(etAmount.getText().toString());
        //把当前的商品数据打包处理
        List<OrderCommodity> temp = new ArrayList<>();
        OrderCommodity orderCommdoity = new OrderCommodity();
        orderCommdoity.setCommodityId(String.valueOf(mCommdoityDetail.getCommodityId()));
        for (Integer integer : mFlyColor.getSelectedList()) {
            //设置选择的颜色
            orderCommdoity.setCommodityColor((String) mFlyColor.getAdapter().getItem(integer));
        }
        for (Integer integer : mFlySize.getSelectedList()) {
            //设置选择的规格
            orderCommdoity.setCommoditySp((String) mFlySize.getAdapter().getItem(integer));
        }
        orderCommdoity.setCommodityNumber(numberOne);
        orderCommdoity.setCommodityNm(mCommdoityDetail.getCommodityNm());
        orderCommdoity.setCommodityImage1Full(mCommdoityDetail.getCommodityImage1());
        orderCommdoity.setCommodityPrice(mCommdoityDetail.getCommodityPrice());
        orderCommdoity.setExpressCost(0);//默认0元运费
        orderCommdoity.setBusinessId(String.valueOf(mCommdoityDetail.getCreateId()));
        temp.add(orderCommdoity);
        //把数据发送到订单页面
        Intent intent = new Intent(GoodsDetailActivity.this, BuyOrderActivity.class);
        intent.putExtra("datas", (Serializable) temp);
        intent.putExtra("type", BuyOrderActivity.SINGLE);
        startActivity(intent);
    }

    private void deleteCollecte() {
        String url = Url.DeleteCollecte;
        RequestParams params = new RequestParams();
        params.put("keyId", commodityId);//收藏项目ID newsId
        params.put("type", 5);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户  9: 智郎团 Integer
        params.put("userId", UserNative.getId());//省userId
        params.put("userNm", UserNative.getName());//用户名
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void collecte() {
        String url = Url.addCollecte;
        RequestParams params = new RequestParams();
        params.put("keyId", commodityId);//收藏项目ID newsId
        params.put("userId", UserNative.getId());//省userId
        params.put("userNm", UserNative.getName());//用户名
        params.put("type", 5);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }

    private void addGoodsToCar() {
        if (mCommdoityDetail == null) {
            return;
        }
        String url = Url.addToCart;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户id
        for (Integer integer : mFlyColor.getSelectedList()) {
            //设置选择的颜色
            params.put("commodityColor", (String) mFlyColor.getAdapter().getItem(integer));//颜色
        }
        for (Integer integer : mFlySize.getSelectedList()) {
            //设置选择的规格
            params.put("commoditySp", (String) mFlySize.getAdapter().getItem(integer));//商品规格
        }
        int numberOne = Integer.valueOf(etAmount.getText().toString());
        params.put("commodityNumber", numberOne);//商品数量
        params.put("commodityPrice", mCommdoityDetail.getCommodityPrice());//商品价钱
        params.put("commodityId", commodityId);//商品id
        params.put("createId", mCommdoityDetail.getCreateId());//商家id
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        startActivity(new Intent(GoodsDetailActivity.this, ShoppingCartActivity.class));
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }


}

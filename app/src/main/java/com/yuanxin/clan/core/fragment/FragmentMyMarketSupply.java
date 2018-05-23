package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.PresentCustomMadeAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.PresentCustomMadeEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.market.view.GoodsDetailActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * Created by lenovo1 on 2017/2/28.
 */
//在售商品 集市商品
public class FragmentMyMarketSupply extends BaseFragment {


    @BindView(R.id.fragment_my_market_recycler_view)
    RecyclerView fragmentMyMarketRecyclerView;
    @BindView(R.id.fragmnet_two_one_springview)
    SpringView fragmnetTwoOneSpringview;
    private List<PresentCustomMadeEntity> presentCustomMadeEntities = new ArrayList<>();
    private PresentCustomMadeAdapter adapter;
    private int currentPage = 1;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_market_supply;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {


        adapter = new PresentCustomMadeAdapter(getContext(), presentCustomMadeEntities);
        adapter.setOnItemClickListener(new PresentCustomMadeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), GoodsDetailActivity.class);
                intent.putExtra("commodityId", presentCustomMadeEntities.get(position).getCommodityId());
                intent.putExtra("type", GoodsDetailActivity.MARKET);
                startActivity(intent);
            }
        });
        fragmentMyMarketRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        activityOneRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
//        activityYuanXinFairNewRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
//
//        activityOneRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        fragmentMyMarketRecyclerView.setAdapter(adapter);
        fragmentMyMarketRecyclerView.setFocusable(false);//导航栏切换不再focuse
        fragmentMyMarketRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getMyMarketDetail(1);//在售商品 我的集市
        fragmnetTwoOneSpringview.setHeader(new RotationHeader(getContext()));
        fragmnetTwoOneSpringview.setFooter(new RotationFooter(getContext()));
        fragmnetTwoOneSpringview.setType(SpringView.Type.OVERLAP);
        fragmnetTwoOneSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetTwoOneSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                presentCustomMadeEntities.clear();
                currentPage = 1;
                getMyMarketDetail(currentPage);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmnetTwoOneSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);

                currentPage += 1;
                getMyMarketDetail(currentPage);
            }
        });
    }


    private void getMyMarketDetail(int pageNumber) {
        String url = Url.urlMyMarketOrder;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pageNumber);
        params.put("userId", UserNative.getId());
        doHttpGet(url, params, new BaseFragment.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);


                    if (object.getString("success").equals("true")) {
                        JSONArray array = object.getJSONArray("data");
//                        presentCustomMadeEntities.clear();
                        for (int c = 0; c < array.length(); c++) {
                            JSONObject dataObject = array.getJSONObject(c);
                            String commodityId = dataObject.getString("commodityId");//商品id
                            String commodityNm = dataObject.getString("commodityNm");//商品名称
                            String commodityPrice = dataObject.getString("minPrice");//商品单价
                            String commodityImage1 = dataObject.getString("commodityImage1");
                            String image = Url.img_domain + commodityImage1+Url.imageStyle640x640;//图片
                            int state = dataObject.getInt("state");

//                            String del_flg=dataObject.getString("del_flg");//0 删除 1 申请通过 2申请失败 3 申请中
//                            if(del_flg.equals(1)){
//                                state="申请通过";
//                            }else if(del_flg.equals(2)){
//                                state="申请失败";
//                            }else if(del_flg.equals(3)){
//                                state="申请中";
//                            }
                            JSONObject marketObject = dataObject.getJSONObject("market");//毫秒转日期
                            String start = marketObject.getString("startTime");
                            long data = Long.valueOf(start).longValue();

//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            Timestamp ts = new Timestamp(data);
                            String startTime = sdf.format(ts);
//                            activityLaunchCrowdFundingTimeEdit.setText(str);//毫秒转换成日期

                            String end = marketObject.getString("endTime");
                            long dataOne = Long.valueOf(end).longValue();

//                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            SimpleDateFormat sdfOne = new SimpleDateFormat("yyyy/MM/dd");
                            Timestamp tsOne = new Timestamp(dataOne);
                            String endTime = sdfOne.format(tsOne);

                            PresentCustomMadeEntity entity = new PresentCustomMadeEntity();
                            entity.setImage(image);//图片
                            entity.setName(commodityNm);//商品
                            entity.setMoney(commodityPrice);//价格
                            entity.setCommodityId(commodityId);
                            entity.setState(state);//状态
                            entity.setStartTime(startTime);
                            entity.setEndTime(endTime);
                            presentCustomMadeEntities.add(entity);

                        }
                        adapter.notifyDataSetChanged();

                    }


                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");

                }
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}

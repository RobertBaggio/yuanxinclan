package com.yuanxin.clan.core.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.EditPresentCustomMadeActivity;
import com.yuanxin.clan.core.adapter.FragmentMyMarketNeedAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.PresentCustomMadeEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/2/28.
 */
//在售礼品
public class FragmentMyMarketNeed extends BaseFragment {
    @BindView(R.id.fragment_my_market_need_recycler_view)
    RecyclerView fragmentMyMarketNeedRecyclerView;
    @BindView(R.id.fragment_my_market_need_spring_view)
    SpringView fragmentMyMarketNeedSpringView;
    private List<PresentCustomMadeEntity> presentCustomMadeEntities = new ArrayList<>();
    private FragmentMyMarketNeedAdapter adapter;
    private String epId, commodityId;
    private int state;
    private int epInOne, userId;
    private IntentFilter intentFilter;
    private LocalReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private int currentPage = 1;// 当前页面，从0开始计数


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_market_need;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcasttest.PRESENT_CUSTOM_FRESH");
        localReceiver = new LocalReceiver();
        localBroadcastManager = LocalBroadcastManager.getInstance(getContext());
        localBroadcastManager.registerReceiver(localReceiver, intentFilter);
    }

    class LocalReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            getMyMarketDetail(1);
        }
    }

    private void initRecyclerView() {
        epId = UserNative.getEpId();
        userId = UserNative.getId();
        adapter = new FragmentMyMarketNeedAdapter(getContext(), presentCustomMadeEntities);
        adapter.setOnItemClickListener(new FragmentMyMarketNeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), EditPresentCustomMadeActivity.class);
                intent.putExtra("epId", epId);//企业id
                intent.putExtra("commodityId", presentCustomMadeEntities.get(position).getCommodityId());//商品id
                getContext().startActivity(intent);
            }
        });
        fragmentMyMarketNeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentMyMarketNeedRecyclerView.setAdapter(adapter);
        fragmentMyMarketNeedRecyclerView.setFocusable(false);//导航栏切换不再focuse
        fragmentMyMarketNeedRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getMyMarketDetail(1);//在售商品 我的集市
        fragmentMyMarketNeedSpringView.setHeader(new RotationHeader(getContext()));
        fragmentMyMarketNeedSpringView.setFooter(new RotationFooter(getContext()));
        fragmentMyMarketNeedSpringView.setType(SpringView.Type.OVERLAP);
        fragmentMyMarketNeedSpringView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fragmentMyMarketNeedSpringView.onFinishFreshAndLoad();
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
                        fragmentMyMarketNeedSpringView.onFinishFreshAndLoad();
                    }
                }, 1000);

                currentPage += 1;
                getMyMarketDetail(currentPage);
            }
        });
    }


    private void getMyMarketDetail(int pageNumber) {
        epId = UserNative.getEpId();
        userId = UserNative.getId();
        if (epId.equals("null")) {
            epInOne = 0;
        } else {
            epInOne = Integer.valueOf(epId);
        }
        String url = Url.getMyTwoGiftList;
        RequestParams params = new RequestParams();
        params.put("pageNumber", pageNumber);
        params.put("userId", userId);//1:集市发布 2:礼品定制发布
        doHttpGet(url, params, new RequestCallback() {
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
                            commodityId = dataObject.getString("commodityId");//商品id

                            String commodityNm = dataObject.getString("commodityNm");//商品名称
                            epId = dataObject.getString("epId");
                            state = dataObject.getInt("state");//状态 1:审核通过 2:审核失败 3:审核中 （状态）
                            String commodityPrice = dataObject.getString("commodityPrice");//商品单价
                            String reason = dataObject.getString("reason");//审核失败的原因
                            String commodityImage1 = dataObject.getString("commodityImage1");
                            String image = Url.img_domain + commodityImage1+Url.imageStyle640x640;//图片


//                            JSONObject marketObject=dataObject.getJSONObject("market");
//                            String startTime=marketObject.getString("startTime");
//                            String endTime=marketObject.getString("endTime");
                            PresentCustomMadeEntity entity = new PresentCustomMadeEntity();
                            entity.setCommodityId(commodityId);//商品id
                            entity.setImage(image);//图片
                            entity.setName(commodityNm);//商品
                            entity.setMoney(commodityPrice);//价格
                            entity.setState(state);//状态
                            entity.setEpId(epId);//企业id
//                            entity.setStartTime(startTime);
//                            entity.setEndTime(endTime);
                            entity.setReason(reason);//要处理审核失败的原因要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要改要
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
        localBroadcastManager.unregisterReceiver(localReceiver);

    }
}

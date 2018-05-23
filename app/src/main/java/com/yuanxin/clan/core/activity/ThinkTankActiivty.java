package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.ThinkTankAdapter;
import com.yuanxin.clan.core.entity.ThinkTankEntity;
import com.yuanxin.clan.core.http.BaseJsonEntity;
import com.yuanxin.clan.core.http.HttpMethods;
import com.yuanxin.clan.core.recyclerview.DividerItemDecoration;
import com.yuanxin.clan.core.subscriber.ProgressSubscriber;
import com.yuanxin.clan.core.subscriber.SubscriberOnNextListener;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/2/20.
 */
//智囊团
public class ThinkTankActiivty extends BaseActivity {


    @BindView(R.id.activity_think_tank_left_image)
    ImageView activityThinkTankLeftImage;
    @BindView(R.id.activity_think_tank_middle_text)
    TextView activityThinkTankMiddleText;
    @BindView(R.id.activity_think_tank_right_image)
    ImageView activityThinkTankRightImage;
    @BindView(R.id.activity_think_tank_recycler_view)
    RecyclerView activityThinkTankRecyclerView;
    @BindView(R.id.address_search_springview)
    SpringView addressSearchSpringview;
    private List<ThinkTankEntity> thinkTankEntities = new ArrayList<>();
    private ThinkTankAdapter thinkTankAdapter;
    private ThinkTankEntity entity = new ThinkTankEntity();

    private SubscriberOnNextListener getExpertListOnNextListener;
    private int currentPage = 1;// 当前页面，从0开始计数
    private String searchNm;

    @Override
    public int getViewLayout() {
        return R.layout.activity_think_tank;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initOnNext();
        initRecyclerView();
    }

    private void initOnNext() {
        getExpertListOnNextListener = new SubscriberOnNextListener<BaseJsonEntity>() {//行业列表

            @Override
            public void onNext(BaseJsonEntity t) {
                if (t.getSuccess().equals("true")) {
                    thinkTankEntities.addAll(FastJsonUtils.getObjectsList(t.getData(), ThinkTankEntity.class));
                    thinkTankAdapter.notifyDataSetChanged();
                } else {
                    ToastUtil.showWarning(getApplicationContext(), t.getMsg(), Toast.LENGTH_SHORT);
                }
            }
        };
    }

    private void getData() {
        if (currentPage == 1) {
            thinkTankEntities.clear();
        }
        HttpMethods.getInstance().getExpertList(new ProgressSubscriber(getExpertListOnNextListener, ThinkTankActiivty.this), searchNm, currentPage);
    }


    private void initRecyclerView() {
        thinkTankAdapter = new ThinkTankAdapter(ThinkTankActiivty.this, thinkTankEntities);
        activityThinkTankRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        thinkTankAdapter.setOnItemClickListener(new ThinkTankAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ThinkTankActiivty.this, ThinkTankDetailActivity.class);
                intent.putExtra("expertId", thinkTankEntities.get(position).getExpertId());
                startActivity(intent);
            }
        });
        activityThinkTankRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        activityThinkTankRecyclerView.setAdapter(thinkTankAdapter);
        activityThinkTankRecyclerView.setFocusable(false);//导航栏切换不再focuse
        activityThinkTankRecyclerView.setNestedScrollingEnabled(false);//禁止滑动

        currentPage = 1;
        searchNm = "";
        getData();
        addressSearchSpringview.setHeader(new RotationHeader(this));
        addressSearchSpringview.setFooter(new RotationFooter(this));
        addressSearchSpringview.setType(SpringView.Type.OVERLAP);
        addressSearchSpringview.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addressSearchSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage = 1;
                getData();
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addressSearchSpringview.onFinishFreshAndLoad();
                    }
                }, 1000);
                currentPage += 1;
                getData();
            }
        });


    }


    @OnClick({R.id.activity_think_tank_left_image, R.id.activity_think_tank_right_image})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_think_tank_left_image:
                finish();
                break;
            case R.id.activity_think_tank_right_image:
                Intent intent = new Intent(ThinkTankActiivty.this, ThinkSearchActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    String inputString = data.getStringExtra("inputString");//企业名称
                    searchNm = inputString;
                    currentPage = 1;
                    getData();
                }
                break;
            default:
        }
    }
}

package com.yuanxin.clan.core.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.liaoinstan.springview.container.RotationFooter;
import com.liaoinstan.springview.container.RotationHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.MySCqiyeAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by lenovo1 on 2017/2/24.
 */
//我的企业 收藏
public class FragmentMyCollectCompany extends BaseFragment {
    @BindView(R.id.activity_company_information_detail_recycler_view)
    RecyclerView activityCompanyInformationDetailRecyclerView;
    @BindView(R.id.fragmnet_my_collect_article_springview)
    SpringView fragmnetMyCollectArticleSpringview;
    private MySCqiyeAdapter adapter;//CompanyInformationDetailChooseAdapter
    private List<CompanyDetail> companyInformationDetailNewEntityList = new ArrayList<>();//真正需要的企业信息
    private int currentPage = 1;// 当前页面，从0开始计数
    private int pageall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_collect_company;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        //搜索结果
        adapter = new MySCqiyeAdapter(getContext(), companyInformationDetailNewEntityList);
        adapter.setOnItemClickListener(new MySCqiyeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CompanyDetailWebActivity.class);//有个聊天的标志
                intent.putExtra("epId", companyInformationDetailNewEntityList.get(position).getEnterprise().getEpId());
                intent.putExtra("epLinktel", companyInformationDetailNewEntityList.get(position).getEpLinktel());
//                intent.putExtra("epStyleType", companyInformationDetailNewEntityList.get(position).getEpView().getEpAccessPath());
                intent.putExtra("accessPath", companyInformationDetailNewEntityList.get(position).getAccessPath());
                startActivity(intent);
            }
        });
        activityCompanyInformationDetailRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        activityCompanyInformationDetailRecyclerView.setAdapter(adapter);
        activityCompanyInformationDetailRecyclerView.setNestedScrollingEnabled(false);//禁止滑动
        getWebInfo(1);
        fragmnetMyCollectArticleSpringview.setHeader(new RotationHeader(getContext()));
        fragmnetMyCollectArticleSpringview.setFooter(new RotationFooter(getContext()));
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
                companyInformationDetailNewEntityList.clear();
                currentPage = 1;
                getWebInfo(currentPage);
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
                if (pageall<currentPage){
                    ToastUtil.showWarning(getContext(), "已加载完", Toast.LENGTH_SHORT);
                    return;
                }
                getWebInfo(currentPage);
            }
        });
    }

    private void getWebInfo(final int pageNumber) {//还差图片
        String url = Url.getMyCollect;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//用户Id
        params.put("pageNumber", pageNumber);//当前显示第几页
        params.put("type", 3);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq","收藏企业。。。。"+s);

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        pageall = object.getInt("pageCount");
                        companyInformationDetailNewEntityList.addAll(FastJsonUtils.getObjectsList(object.getString("data"), CompanyDetail.class));
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
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

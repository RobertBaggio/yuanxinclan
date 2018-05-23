package com.yuanxin.clan.core.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.yuanxin.clan.core.adapter.HeZuoAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.CompanyDetail;
import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.entity.EnterpriseID;
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
 * Created by lenovo1 on 2017/2/28.
 */
//我的供应商
public class FragmentMyTeamworkBusinessSupply extends BaseFragment {
    @BindView(R.id.fragment_two_one_recyclerview)
    RecyclerView fragmentTwoOneRecyclerview;
    @BindView(R.id.fragmnet_two_one_springview)
    SpringView fragmnetTwoOneSpringview;

    private HeZuoAdapter adapter;//CompanyInformationDetailChooseAdapter
    private List<CompanyDetail> companyInformationDetailNewEntityList = new ArrayList<>();//真正需要的企业信息
    private int currentPage = 1;// 当前页面，从0开始计数

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public int getViewLayout() {
        return R.layout.fragment_my_teamwork_business_supply;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        //搜索结果
        adapter = new HeZuoAdapter(getContext(), companyInformationDetailNewEntityList);
        adapter.setOnItemClickListener(new HeZuoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getContext(), CompanyDetailWebActivity.class);//有个聊天的标志
                intent.putExtra("epId", companyInformationDetailNewEntityList.get(position).getEnterprise().getEpId());
                intent.putExtra("epLinktel", companyInformationDetailNewEntityList.get(position).getEnterprise().getEpLinktel());
                intent.putExtra("accessPath", companyInformationDetailNewEntityList.get(position).getAccessPath());
                startActivity(intent);
            }
        });
        adapter.setOnItemLongClickListener(new HeZuoAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, int position) {
                showNormalDialogOne("供应商", companyInformationDetailNewEntityList.get(position).getEnshrineId(),position);
            }
        });
        fragmentTwoOneRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentTwoOneRecyclerview.setAdapter(adapter);
        fragmentTwoOneRecyclerview.setNestedScrollingEnabled(false);//禁止滑动
        getWebInfo(1);
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
                companyInformationDetailNewEntityList.clear();
                currentPage = 1;
                getWebInfo(currentPage);
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
                getWebInfo(currentPage);
            }
        });
    }

    private void getWebInfo(int pageNumber) {//还差图片
        companyInformationDetailNewEntityList.clear();
        String url = Url.getMyCollect;
        RequestParams params = new RequestParams();
        params.put("keyId", 0);//收藏项目ID
        params.put("userId", UserNative.getId());//用户Id
        params.put("pageNumber", pageNumber);//当前显示第几页
        params.put("type", 7);//1：新闻 2：商圈 3：企业 4：众筹  5：商品 6: 服务商 7: 供应商  8: 客户
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
    private void showNormalDialogOne(String rel, final int epId,final int posion) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("是否删除该" + rel + "？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteCollecte(epId,posion);
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        // 显示
        normalDialog.show();
    }

    //删除标记
    private void deleteCollecte(int epId,final int posion) {
        RequestParams params = new RequestParams();
        String url = Url.DeleteCollecte;
        ArrayList<EnterpriseID> ids = new ArrayList<EnterpriseID>();
        EnterpriseID enterpriseID = new EnterpriseID(epId);
        ids.add(enterpriseID);
        params.put("jsonStr", FastJsonUtils.toJSONString(ids));//收藏项目ID newsId
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                Log.v("lgq","删除返回。。。。"+s);

                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        companyInformationDetailNewEntityList.remove(posion);
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

package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.CommdssAdapter;
import com.yuanxin.clan.core.adapter.SortGroupMemberAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.CommodityEntity;
import com.yuanxin.clan.core.entity.GroupMemberBean;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.indexRecyclerView.CharacterParser;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.jpinyin.PinyinHelper;
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
 * 通讯录搜索类
 */

public class TongxunluSosActivity extends BaseActivity {
    @BindView(R.id.activity_yuan_xin_crowd_right)
    LinearLayout activity_yuan_xin_crowd_right;
    @BindView(R.id.activity_yuan_xin_crowd_left_layout)
    LinearLayout activity_yuan_xin_crowd_left_layout;
    @BindView(R.id.country_lvcountry)
    ListView sortListView;
    @BindView(R.id.activi_middle_editextt)
    EditText activi_middle_editext;
    @BindView(R.id.title_layout_no_friends)
//    @BindView(R.id.em_search_bar_with_padding_query)
    TextView tvNofriends;


    private CharacterParser characterParser;
    private String name;
    private CommdssAdapter mAdapter;
    private List<CommodityEntity> mCommodityEntityList = new ArrayList<>();
    private List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();
    private My_LoadingDialog mMy_loadingDialog;
    private SortGroupMemberAdapter adapter;
    List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();

    @Override
    public int getViewLayout() {
        return R.layout.tongxunlusoslayout;

    }
    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, final Intent intent) {
        mSortList = (List<GroupMemberBean>) getIntent().getSerializableExtra("datas");
        characterParser = CharacterParser.getInstance();//字符解析器


        activi_middle_editext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterData(activi_middle_editext.getText().toString());
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
//                getWebInfo(name,epId,1);
//                String str = "你好世界";
//                try{
//                    PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_MARK); // nǐ,hǎo,shì,jiè
//                    PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITH_TONE_NUMBER); // ni3,hao3,shi4,jie4
//                    PinyinHelper.convertToPinyinString(str, ",", PinyinFormat.WITHOUT_TONE); // ni,hao,shi,jie
//                    PinyinHelper.getShortPinyin(str); // nhsj/
//                }catch (Exception e){
//                    e.printStackTrace();
//            }
                break;

        }
    }

    private void filterData(String filterStr) {
        try{


        if (TextUtils.isEmpty(filterStr)) {
//            filterDateList = mSortList;
            tvNofriends.setVisibility(View.GONE);
            sortListView.setVisibility(View.GONE);
        } else {
            filterDateList.clear();
            for (GroupMemberBean sortModel : mSortList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr) != -1 || characterParser.getSelling(name).contains(filterStr)||
                        PinyinHelper.getShortPinyin(name).indexOf(filterStr)!=-1) {
                    filterDateList.add(sortModel);
                }
            }
            if (filterDateList.size() == 0) {
                tvNofriends.setVisibility(View.VISIBLE);
                sortListView.setVisibility(View.GONE);
            }else {
                adapter = new SortGroupMemberAdapter(TongxunluSosActivity.this, filterDateList);
                adapter.updateListView(filterDateList);
                sortListView.setVisibility(View.VISIBLE);
                tvNofriends.setVisibility(View.GONE);
                sortListView.setAdapter(adapter);

                sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
                        Intent intent = new Intent(TongxunluSosActivity.this, PersionChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_ID, filterDateList.get(position).getPhone());
                        intent.putExtra(Constant.USER_NAME, UserNative.getName());
                        intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                        intent.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                        intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                        intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        intent.putExtra("type", "");
                        intent.putExtra("toNick", filterDateList.get(position).getName());//对方的昵称//已修改
                        intent.putExtra("toImage", filterDateList.get(position).getImage());//对方的头像
                        startActivity(intent);
                    }
                });
            }

        }

        // 根据a-z进行排序
//        Collections.sort(filterDateList, pinyinComparator);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //恢复/industrylist
    private void getWebInfo(String name, int id,int pageNumber) {
        if (pageNumber == 1) {
            mCommodityEntityList.clear();
        }
        String url = Url.getcommdss;
        RequestParams params = new RequestParams();
        mMy_loadingDialog.show();
//        Log.v("lgq",".....参数==province="+province+"...city="+city+"...area="+area+"...edit="+epNm+"...page="+pageNumber);
        params.put("epId", id);
        params.put("under", 1);
        params.put("commodityNm", name);
        params.put("pageNumber", pageNumber);
        doHttpPost(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
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

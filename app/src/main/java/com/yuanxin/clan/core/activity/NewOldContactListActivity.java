package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.NewNewSortGroupMemberAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.GroupMemberBean;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.indexRecyclerView.CharacterParser;
import com.yuanxin.clan.core.indexRecyclerView.SideBar;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo1 on 2017/5/19.
 */
public class NewOldContactListActivity extends BaseActivity implements SectionIndexer {
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private NewNewSortGroupMemberAdapter adapter;
//    private ClearEditText mClearEditText;

    private LinearLayout titleLayout;
    private TextView title;
    private TextView tvNofriends;
    private LinearLayout backLayout;
    private LinearLayout rightLayout;
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<GroupMemberBean> SourceDateList;
    private List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();
    private List<GroupMemberBean> m_buyList = new ArrayList<GroupMemberBean>();//选中要结算的购物车商品

    private double total_money;
    private int m_num;
    private List<String> m_job_ids = new ArrayList<String>();//删除集合
    private ArrayList<String> images = new ArrayList<String>();//头像集合
    private ArrayList<String> names = new ArrayList<String>();//删除集合
    private String abc, imagesOne, namesOne, name;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    public int getViewLayout() {
        return R.layout.activity_add_friends;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unbinder.unbind();


    }


    public static String list2string(List<String> stringList) {
        String idStr = ",";
        for (int i = 0; i < stringList.size(); i++) {
            idStr += stringList.get(i) + ",";
        }
        return idStr.substring(1, idStr.length() - 1);
    }

    private void initViews() {
        backLayout = (LinearLayout) findViewById(R.id.activity_exchange_phone_left_layout);
        backLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        rightLayout = (LinearLayout) findViewById(R.id.activity_exchange_phone_right_layout);
        rightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                m_job_ids.clear();//选中的成员电话号码
                if (m_num > 0) {

                    for (int a = 0; a < m_buyList.size(); a++) {

                        String jobId = m_buyList.get(a).getPhone();
                        m_job_ids.add(jobId);
                        String image = m_buyList.get(a).getImage();
                        images.add(image);
                        String name = m_buyList.get(a).getName();
                        names.add(name);

                    }
                    abc = "";
                    abc = list2string(m_job_ids);
//                    imagesOne="";
//                    imagesOne=list2string(images);
//                    namesOne="";
//                    namesOne=list2string(names);


                    Intent intent = new Intent();
                    intent.putExtra("memberNumberList", abc);
                    intent.putStringArrayListExtra("memberImageList", images);
                    intent.putStringArrayListExtra("memberNameList", names);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
        titleLayout = (LinearLayout) findViewById(R.id.title_layout);//标题
        title = (TextView) this.findViewById(R.id.title_layout_catalog);//标题
        tvNofriends = (TextView) this.findViewById(R.id.title_layout_no_friends);//没有匹配的联系人
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();//字符解析器

        pinyinComparator = new PinyinComparator();//汉语拼音

        sideBar = (SideBar) findViewById(R.id.sidrbar);//右边的abcd
        dialog = (TextView) findViewById(R.id.dialog);//中间的绿色方块
        sideBar.setTextView(dialog);//右边的abcd
        sortListView = (ListView) findViewById(R.id.country_lvcountry);//列表
        SourceDateList = filledData();


        // 根据a-z进行排序源数据
//        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new NewNewSortGroupMemberAdapter(this, SourceDateList);
        sortListView.setAdapter(adapter);

        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {//右边的abcd

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                int position = adapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });


        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 这里要利用adapter.getItem(position)来获取当前position所对应的对象
//                Toast.makeText(
//                        getApplication(),
//                        ((GroupMemberBean) adapter.getItem(position)).getName(),
//                        Toast.LENGTH_SHORT).show();
            }
        });

//        SourceDateList = filledData();
//
//        // 根据a-z进行排序源数据
//        Collections.sort(SourceDateList, pinyinComparator);
//        adapter = new NewSortGroupMemberAdapter(this, SourceDateList);
//        sortListView.setAdapter(adapter);
        adapter.setOnMyCheckedChangeListener(new NewNewSortGroupMemberAdapter.OnMyCheckedChangeListener() {
            @Override
            public void onCheckedChange(int num, List<GroupMemberBean> buyList) {
                m_buyList.clear();
                m_buyList.addAll(buyList);//所有数据
//                m_job_ids.clear();//删除id
//                shopIds.clear();//
//                total_money = 0;
                m_num = buyList.size();
            }
        });
        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (SourceDateList.size() > 1) {
                    int section = getSectionForPosition(firstVisibleItem);
                    int nextSection = getSectionForPosition(firstVisibleItem + 1);
                    int nextSecPosition = getPositionForSection(+nextSection);
                    if (firstVisibleItem != lastFirstVisibleItem) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                                .getLayoutParams();
                        params.topMargin = 0;
                        titleLayout.setLayoutParams(params);
                        title.setText(SourceDateList.get(
                                getPositionForSection(section)).getSortLetters());
                    }
                    if (nextSecPosition == firstVisibleItem + 1) {
                        View childView = view.getChildAt(0);
                        if (childView != null) {
                            int titleHeight = titleLayout.getHeight();
                            int bottom = childView.getBottom();
                            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                                    .getLayoutParams();
                            if (bottom < titleHeight) {
                                float pushedDistance = bottom - titleHeight;
                                params.topMargin = (int) pushedDistance;
                                titleLayout.setLayoutParams(params);
                            } else {
                                if (params.topMargin != 0) {
                                    params.topMargin = 0;
                                    titleLayout.setLayoutParams(params);
                                }
                            }
                        }
                    }
                    lastFirstVisibleItem = firstVisibleItem;
                }
            }
        });
//        mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);
//
//        // 根据输入框输入值的改变来过滤搜索
//        mClearEditText.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before,
//                                      int count) {
//                // 这个时候不需要挤压效果 就把他隐藏掉
//                titleLayout.setVisibility(View.GONE);
//                // 当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
//                filterData(s.toString());
//            }
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count,
//                                          int after) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//            }
//        });
//        getInfo();
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<GroupMemberBean> filledData() {
        final List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();
        String url = Url.getFriendsList;
        RequestParams params = new RequestParams();
        params.put("userUUID", UserNative.getPhone());
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {

                try {
                    JSONObject object = new JSONObject(s);


                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                        JSONArray jsonArray = object.getJSONArray("data");
                        mSortList.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String groupDetailImage = dataObject.getString("groupDetailImage");//头像
                            String imgOne = Url.img_domain + groupDetailImage+Url.imageStyle640x640;
                            String groupDetailNm = dataObject.getString("groupDetailNm");//名字
                            String userUuid = dataObject.getString("userUuid");//聊天
                            GroupMemberBean sortModel = new GroupMemberBean();
                            sortModel.setName(groupDetailNm);
//                            sortModel.setImage(imgOne);
                            // 汉字转换成拼音
                            if (groupDetailNm.equals("")) {
                                groupDetailNm = "null";
                            }
                            String pinyin = characterParser.getSelling(groupDetailNm);
                            String sortString = pinyin.substring(0, 1).toUpperCase();

                            // 正则表达式 判断首字母是否是英文字母
                            if (sortString.matches("[A-Z]")) {
                                sortModel.setSortLetters(sortString.toUpperCase());
                            } else {
                                sortModel.setSortLetters("#");
                            }
                            sortModel.setPhone(userUuid);
                            sortModel.setImage(imgOne);
                            mSortList.add(sortModel);
//                            SourceDateList.clear();
//                            SourceDateList=mSortList;

//                            FriendListEntity one = new FriendListEntity();
//                            one.setName(groupDetailNm);//标题
//                            one.setImage(imgOne);
//                            one.setPhone(userUuid);
//                            friendListEntities.add(one);

                        }


                        adapter.notifyDataSetChanged();
                        Collections.sort(SourceDateList, pinyinComparator);

                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);

                    }


                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
        return mSortList;

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView
     *
     * @param filterStr
     */
    private void filterData(String filterStr) {
        List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList;
            tvNofriends.setVisibility(View.GONE);
        } else {
            filterDateList.clear();
            for (GroupMemberBean sortModel : SourceDateList) {
                String name = sortModel.getName();
                if (name.indexOf(filterStr.toString()) != -1
                        || characterParser.getSelling(name).startsWith(
                        filterStr.toString())) {
                    filterDateList.add(sortModel);
                }
            }
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
        if (filterDateList.size() == 0) {
            tvNofriends.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {

        return SourceDateList.get(position).getSortLetters().charAt(0);


    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < SourceDateList.size(); i++) {
            String sortStr = SourceDateList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }
}

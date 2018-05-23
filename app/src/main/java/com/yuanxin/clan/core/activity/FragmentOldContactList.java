package com.yuanxin.clan.core.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.Toast;

import com.liaoinstan.springview.widget.SpringView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.SortGroupMemberAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.GroupMemberBean;
import com.yuanxin.clan.core.event.FriendChangeEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.indexRecyclerView.CharacterParser;
import com.yuanxin.clan.core.indexRecyclerView.SideBar;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.core.zxing.SubBasicFragment;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseFragment;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by lenovo1 on 2017/5/16.
 * 通讯录类
 */
//通讯录有 abcd 16090485817345
public class FragmentOldContactList extends SubBasicFragment implements SectionIndexer {

    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private SortGroupMemberAdapter adapter;
    private LinearLayout sosli;
//    private ClearEditText mClearEditText;

    private LinearLayout titleLayout;
    private TextView title;
    private TextView tvNofriends;
    private String groupDetailNm;
    private SpringView fragmnetTwoOneSpringview;
    private AsyncHttpClient client = new AsyncHttpClient();
    /**
     * 上次第一个可见元素，用于滚动时记录标识。
     */
    private int lastFirstVisibleItem = -1;
    /**
     * 汉字转换成拼音的类
     */
    private CharacterParser characterParser;
    private List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();


    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private PinyinComparator pinyinComparator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_old_contact_list_fragment, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        filledData();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
    public void onDestroy() {
        super.onDestroy();
        client.cancelAllRequests(true);
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void releaseRes() {
        //销毁
    }

    private void initViews(View view) {
        titleLayout = (LinearLayout) view.findViewById(R.id.title_layout);//标题
        title = (TextView) view.findViewById(R.id.title_layout_catalog);//标题
        sosli =(LinearLayout)view.findViewById(R.id.sosli);
        tvNofriends = (TextView) view.findViewById(R.id.title_layout_no_friends);//没有匹配的联系人
        // 实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();//字符解析器
        pinyinComparator = new PinyinComparator();//汉语拼音
        sideBar = (SideBar) view.findViewById(R.id.sidrbar);//右边的abcd
        dialog = (TextView) view.findViewById(R.id.dialog);//中间的绿色方块
        sideBar.setTextView(dialog);//右边的abcd
        sortListView = (ListView) view.findViewById(R.id.country_lvcountry);//列表
        fragmnetTwoOneSpringview = (SpringView) view.findViewById(R.id.fragmnet_two_one_springview);//
        // 设置右侧触摸监听
        sosli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mSortList.isEmpty()) {
                    ToastUtil.showShort(getContext(), "你还没有任何好友，请添加好友");
                    return;
                }
                Intent intent = new Intent(getContext(), TongxunluSosActivity.class);
                intent.putExtra("datas", (Serializable) mSortList);
                startActivity(intent);
            }
        });
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
                Intent intent = new Intent(getContext(), PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID, mSortList.get(position).getPhone());
                intent.putExtra(Constant.USER_NAME, UserNative.getName());
                intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                intent.putExtra(Constant.ADDRESS, UserNative.getCity()+UserNative.getArea()+UserNative.getDetail());
                intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra("type", "");
                intent.putExtra("toNick", mSortList.get(position).getName());//对方的昵称//已修改
                intent.putExtra("toImage", mSortList.get(position).getImage());//对方的头像
                startActivity(intent);
            }
        });

        sortListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (mSortList.size() > 1) {
                    int section = getSectionForPosition(firstVisibleItem);
                    int nextSection = getSectionForPosition(firstVisibleItem + 1);
                    int nextSecPosition = getPositionForSection(+nextSection);
                    if (firstVisibleItem != lastFirstVisibleItem) {
                        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) titleLayout
                                .getLayoutParams();
                        params.topMargin = 0;
                        titleLayout.setLayoutParams(params);
                        title.setText(mSortList.get(
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
        //        SourceDateList.clear();
        filledData();
        // 根据a-z进行排序源数据
//        Collections.sort(SourceDateList, pinyinComparator);
        adapter = new SortGroupMemberAdapter(getContext(), mSortList);
        sortListView.setAdapter(adapter);
//        fragmnetTwoOneSpringview.setHeader(new RotationHeader(getContext()));
//        fragmnetTwoOneSpringview.setType(SpringView.Type.OVERLAP);
//        fragmnetTwoOneSpringview.setListener(new SpringView.OnFreshListener() {//不可刷新
//            @Override
//            public void onRefresh() {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        fragmnetTwoOneSpringview.onFinishFreshAndLoad();
//                    }
//                }, 1000);
//                filledData();
//            }
//
//            @Override
//            public void onLoadmore() {
//            }
//        });
    }

    /**
     * 为ListView填充数据
     *
     * @param
     * @return
     */
    private List<GroupMemberBean> filledData() {
        String url = Url.getFriendsList;
        RequestParams params = new RequestParams();
        params.put("userUUID", UserNative.getPhone());
        client.get(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        mSortList.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String groupDetailImage = dataObject.getString("groupDetailImage");//头像
                            String imgOne = Url.img_domain + groupDetailImage+Url.imageStyle640x640;
                            groupDetailNm = dataObject.getString("groupDetailNm");//名字
                            String userUuid = dataObject.getString("userUuid");//聊天
                            GroupMemberBean sortModel = new GroupMemberBean();
                            sortModel.setName(groupDetailNm);
                            sortModel.setPhone(userUuid);
                            sortModel.setImage(imgOne);
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
                            mSortList.add(sortModel);
                        }
                        adapter.notifyDataSetChanged();
                        Collections.sort(mSortList, pinyinComparator);
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
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
            filterDateList = mSortList;
            tvNofriends.setVisibility(View.GONE);
        } else {
            filterDateList.clear();
            for (GroupMemberBean sortModel : mSortList) {
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
        return mSortList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < mSortList.size(); i++) {
            String sortStr = mSortList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }
        return -1;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(FriendChangeEvent friendChangeEvent) {
        UIUtils.runInMainThread(new Runnable() {
            @Override
            public void run() {
                filledData();
            }
        });
    }

    protected void doHttpGet(String url, RequestParams params, final BaseFragment.RequestCallback c) {
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {
//            ViewUtils.AlertDialog(getContext(), "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//        } else {
            params.put("key", aesKes);
            client.get(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    c.onFailure(i, headers, s, throwable);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        c.onSuccess(i, headers, s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
//    }
}

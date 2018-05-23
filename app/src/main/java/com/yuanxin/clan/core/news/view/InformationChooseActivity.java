package com.yuanxin.clan.core.news.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.news.adapter.ActivityInformationChooseAdapter;
import com.yuanxin.clan.core.news.adapter.ActivityInformationTypeAdapter;
import com.yuanxin.clan.core.news.bean.NewsType;
import com.yuanxin.clan.core.news.helper.MyItemTouchCallback;
import com.yuanxin.clan.core.news.helper.OnRecyclerItemClickListener;
import com.yuanxin.clan.core.news.helper.VibratorUtil;
import com.yuanxin.clan.core.recyclerview.DividerGridItemDecoration;
import com.yuanxin.clan.core.util.FastJsonUtils;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.My_LoadingDialog;
import com.yuanxin.clan.mvp.manager.PreferenceManager;
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
 * Created by lenovo1 on 2017/2/8.
 */
//资讯
public class InformationChooseActivity extends BaseActivity implements MyItemTouchCallback.OnDragListener{


    @BindView(R.id.activity_exchange_phone_left_layout)
    LinearLayout activityExchangePhoneLeftLayout;
    @BindView(R.id.activity_exchange_phone_right_layout)
    LinearLayout activityExchangePhoneRightLayout;
    @BindView(R.id.activity_information_choose_editor_text)
    TextView activityInformationChooseEditorText;
    @BindView(R.id.activity_information_choose_recycler_view)
    RecyclerView activityInformationChooseRecyclerView;
    @BindView(R.id.activity_information_type_recycler_view)
    RecyclerView activityInformationTypeRecyclerView;
    @BindView(R.id.activity_information_type_button)
    Button activityInformationTypeButton;
    @BindView(R.id.activity_information_choose_back_image)
    ImageView activityInformationChooseBackImage;
    private ActivityInformationTypeAdapter adapter;
    private ActivityInformationChooseAdapter chooseAdapter;
    private List<NewsType> datas = new ArrayList<>();
    private List<NewsType> dataChoice = new ArrayList<>();
    private My_LoadingDialog mMy_loadingDialog;
    private ItemTouchHelper itemTouchHelper;
    @Override
    public int getViewLayout() {
        return R.layout.activity_information_choose;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mMy_loadingDialog = My_LoadingDialog.getInstance(InformationChooseActivity.this);
        initView();
        initChoose();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getIntentInfo();
    }

    private void getIntentInfo() {
        activityInformationChooseBackImage.setVisibility(View.VISIBLE);
//        activityInformationChooseBackImage.setImageResource(R.drawable.ease_mm_title_back);
        activityExchangePhoneLeftLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTypeTitle();
                setResult(2011);
                finish();
            }
        });
    }

    private void saveTypeTitle() {
        StringBuffer sb = new StringBuffer();
        StringBuffer sb2Server = new StringBuffer();
        for (int i = 0; i < dataChoice.size(); i++) {
            if (i == 0) {
                sb.append(dataChoice.get(i).getNewsTypeId() + "-" + dataChoice.get(i).getNewsTypeNm());
                sb2Server.append(dataChoice.get(i).getNewsTypeId());
            } else {
                sb.append("_" + dataChoice.get(i).getNewsTypeId() + "-" + dataChoice.get(i).getNewsTypeNm());
                sb2Server.append("," + dataChoice.get(i).getNewsTypeId());
            }
        }
        PreferenceManager.putString("article_type", sb.toString());
        saveTypeTitleToServer(sb2Server);
    }

    private void saveTypeTitleToServer(StringBuffer str) {
        String url = Url.saveNewsType;
        RequestParams params = new RequestParams();
        params.put("ids", str.toString());//收藏项目ID newsId
        params.put("userId", UserNative.getId());//用户名
        mMy_loadingDialog.show();
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
                    if (object.getString("success").equals("true")) {
                        setResult(2011);
                        finish();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    private void getNewsType() {

        String url = Url.getNewsType;
        mMy_loadingDialog.show();
        RequestParams params = new RequestParams();
        doHttpGet(url,params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMy_loadingDialog.dismiss();
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        JSONArray jsonArray = object.getJSONArray("data");
                        datas.clear();
                        for (int a = 0; a < jsonArray.length(); a++) {
                            JSONObject dataObject = jsonArray.getJSONObject(a);
                            String newsTypeId = dataObject.getString("newsTypeId");
                            String newsTypeNm = dataObject.getString("newsTypeNm");
                            SharedPreferences sharedPreferences = getSharedPreferences("newPublishType", Context.MODE_PRIVATE); //私有数据
                            SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
                            editor.putString(newsTypeNm, newsTypeId);//名称 id String
                            editor.commit();//提交修改
                            NewsType item = new NewsType();
                            item.setNewsTypeId(newsTypeId);
                            item.setNewsTypeNm(newsTypeNm);
                            datas.add(item);
                        }
                        datas.removeAll(dataChoice);
                        adapter.notifyDataSetChanged();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }
        });
    }

    private void initChoose() {
        initDataChooseInformation();
        chooseAdapter = new ActivityInformationChooseAdapter(InformationChooseActivity.this, dataChoice);
        chooseAdapter.setOnItemClickListener(new ActivityInformationChooseAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                datas.add(dataChoice.get(position));
                adapter.notifyDataSetChanged();
                dataChoice.remove(position);
                chooseAdapter.notifyDataSetChanged();
            }
        });
        activityInformationChooseRecyclerView.setLayoutManager(new GridLayoutManager(activityInformationChooseRecyclerView.getContext(), 5));
        activityInformationChooseRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
        activityInformationChooseRecyclerView.setAdapter(chooseAdapter);
        chooseAdapter.setEdit();
        itemTouchHelper = new ItemTouchHelper(new MyItemTouchCallback(chooseAdapter).setOnDragListener(this));
        itemTouchHelper.attachToRecyclerView(activityInformationChooseRecyclerView);
        activityInformationChooseRecyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(activityInformationChooseRecyclerView) {
            @Override
            public void onLongClick(RecyclerView.ViewHolder vh) {
                itemTouchHelper.startDrag(vh);
                VibratorUtil.Vibrate(InformationChooseActivity.this, 70);   //震动70ms
            }
            @Override
            public void onItemClick(RecyclerView.ViewHolder vh) {

            }
        });
    }

    private void initDataChooseInformation() {
//        String type = PreferenceManager.getString("article_type", "1-快讯_2-关注");
//        dataChoice.clear();
//        if (TextUtil.isEmpty(type)) {
//            return;
//        }
//        List<String> typeTemp = new ArrayList<String>(Arrays.asList(type.split("_")));
//        for (int i = 0; i < typeTemp.size(); i++) {
//            String[] typeSplit = typeTemp.get(i).split("-");
//            NewsType item = new NewsType();
//            item.setNewsTypeId(typeSplit[0]);
//            item.setNewsTypeNm(typeSplit[1]);
//            dataChoice.add(item);
//        }
        String typesByUser = getIntent().getStringExtra("typesByUser");
        dataChoice.clear();
        dataChoice = FastJsonUtils.getObjectsList(typesByUser, NewsType.class);
    }

    private void initView() {
        adapter = new ActivityInformationTypeAdapter(InformationChooseActivity.this, datas);
        adapter.setOnItemClickListener(new ActivityInformationTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                if(!chooseAdapter.getIs_edit()) {
//                    return;
//                }
                //循环
                for (int i = 0; i < dataChoice.size(); i++) {
                    if (dataChoice.get(i).getNewsTypeId().equals(datas.get(position).getNewsTypeId())) {
                        //存在就不添加
                        return;
                    }
                }
                dataChoice.add(datas.get(position));
                datas.remove(position);
                adapter.notifyDataSetChanged();
                chooseAdapter.notifyDataSetChanged();
            }


        });
        activityInformationTypeRecyclerView.setLayoutManager(new GridLayoutManager(activityInformationTypeRecyclerView.getContext(), 5));
        activityInformationTypeRecyclerView.addItemDecoration(new DividerGridItemDecoration(getApplicationContext()));
        activityInformationTypeRecyclerView.setAdapter(adapter);
        getNewsType();
    }

    @OnClick({R.id.activity_information_type_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_information_type_button://开启旅程
                saveTypeTitle();
                break;
        }
    }
    @Override
    public void onFinishDrag() {
        //存入缓存

    }

}

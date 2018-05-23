package com.yuanxin.clan.core.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.adapter.HomeSosListAdapter;
import com.yuanxin.clan.core.adapter.HomeSos_Adapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.entity.HomeSostypeEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.twoWayGridview.TwoWayAdapterView;

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
 * Author: lgq
 * Date: 2018/1/17 0017 16:19
 */

public class BusinessSosOneActivity  extends BaseActivity{

    @BindView(R.id.hostsosgv)
    com.yuanxin.clan.mvp.view.twoWayGridview.TwoWayGridView hostsosgv;
    @BindView(R.id.historylistview)
    ListView historylistview;
    @BindView(R.id.homesosoedit)
    EditText homesosoedit;
    @BindView(R.id.onbackte)
    TextView onbackte;
    @BindView(R.id.deleimage)
    ImageView deleimage;
    @BindView(R.id.login_errowimage)
    ImageView login_errowimage;

    private List<HomeSostypeEntity> mHomehotypeEntities = new ArrayList<>();
    private List<HomeSostypeEntity> mHomehistoryEntities = new ArrayList<>();

    private HomeSos_Adapter mHotSos_adapter;
    private HomeSosListAdapter mHistorySos_adapter;
    private More_LoadDialog mMy_loadingDialog;
    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    public int getViewLayout() {
        return R.layout.business_sos_one;
//        return R.layout.tixianla;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mHotSos_adapter = new HomeSos_Adapter(mHomehotypeEntities);
        mHistorySos_adapter = new HomeSosListAdapter(mHomehistoryEntities);

        homesosoedit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // 当按了搜索之后关闭软键盘
                    ((InputMethodManager) homesosoedit.getContext().getSystemService(
                            Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            BusinessSosOneActivity.this.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    startActivity(new Intent(BusinessSosOneActivity.this, BusinessSosTwoActivity.class).putExtra("name", homesosoedit.getText().toString().trim()));
                    return true;
                }
                return false;
            }
        });
        homesosoedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtil.isEmpty(s.toString())){
                    login_errowimage.setVisibility(View.GONE);

                }else {
                    login_errowimage.setVisibility(View.VISIBLE);
                }
            }
        });
        historylistview.setAdapter(mHistorySos_adapter);
        hostsosgv.setAdapter(mHotSos_adapter);
        hostsosgv.setSelector(R.drawable.kongbg);
        mMy_loadingDialog = new More_LoadDialog(BusinessSosOneActivity.this);
        hostsosgv.setOnItemClickListener(new TwoWayAdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(TwoWayAdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(BusinessSosOneActivity.this, BusinessSosTwoActivity.class).putExtra("name",mHomehotypeEntities.get(position).getName()));
                homesosoedit.setText(mHomehotypeEntities.get(position).getName());
            }
        });

        historylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(BusinessSosOneActivity.this, BusinessSosTwoActivity.class).putExtra("name",mHomehistoryEntities.get(position).getName()));
                homesosoedit.setText(mHomehistoryEntities.get(position).getName());
//                homesosoedit.setSelection(homesosoedit.getText().length());

            }
        });
//        getHotandHistorySos();
    }


    @OnClick({R.id.onbackte, R.id.deleimage, R.id.login_errowimage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.onbackte:
                onBackPressed();
                break;
            case R.id.deleimage:
                showNormalDialogOne();
                break;
            case R.id.login_errowimage:
                homesosoedit.setText("");
                break;
        }
    }

    private void showNormalDialogOne() {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("确定删除所有！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delehistory();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        normalDialog.show();
    }

    private void delehistory() {
        String url = Url.getsearchdelete;
        RequestParams params = new RequestParams();
        params.put("appFlg",1 );
        params.put("userId", UserNative.getId());
        params.put("searchClassify", "1");
        mMy_loadingDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        ToastUtil.showSuccess(BusinessSosOneActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                        getHotandHistorySos();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (mMy_loadingDialog != null) {
                        mMy_loadingDialog.dismiss();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                if (mMy_loadingDialog != null) {
                    mMy_loadingDialog.dismiss();
                }
                ToastUtil.showWarning(BusinessSosOneActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getHotandHistorySos();
    }

    private void getHotandHistorySos( ) {

        mHomehistoryEntities.clear();
        mHomehotypeEntities.clear();
        String url = Url.getsearchtype;
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());
        params.put("searchClassify", "1");
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

                ToastUtil.showWarning(BusinessSosOneActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {

                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String datastring = object.getString("data");
                        JSONObject dataobject = new JSONObject(datastring);
                        String hotSearch =dataobject.getString("hotSearch");
                        String searchHistory =dataobject.getString("searchHistory");
                        JSONArray harray = new JSONArray(hotSearch);
                        JSONArray historyarray = new JSONArray(searchHistory);
                        for (int a = 0;a<harray.length();a++){
                            JSONObject adObject = harray.getJSONObject(a);
                            String searchHistoryId = adObject.getString("searchHistoryId");
                            String searchType = adObject.getString("searchType");
                            String searchKey = adObject.getString("searchKey");
                            ;
                            HomeSostypeEntity entity = new HomeSostypeEntity();
                            entity.setId(searchHistoryId);
                            entity.setType(searchType);
                            entity.setName(searchKey);

                            mHomehotypeEntities.add(entity);
                        }
                        mHotSos_adapter.notifyDataSetChanged();
                        for (int a = 0;a<historyarray.length();a++){
                            JSONObject adObject = historyarray.getJSONObject(a);
                            String searchHistoryId = adObject.getString("searchHistoryId");
                            String searchType = adObject.getString("searchType");
                            String searchKey = adObject.getString("searchKey");
                            ;
                            HomeSostypeEntity entity = new HomeSostypeEntity();
                            entity.setId(searchHistoryId);
                            entity.setType(searchType);
                            entity.setName(searchKey);

                            mHomehistoryEntities.add(entity);
                        }
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) historylistview.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(53)*mHomehistoryEntities.size();// 控件的高强制设成20
                        mHistorySos_adapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        //这里注意要作判断处理，ActionDown、ActionUp都会回调到这里，不作处理的话就会调用两次
        if (KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction()) {
            //处理事件
//            startActivity(new Intent(BusinessSosOneActivity.this, BusinessSosTwoActivity.class).putExtra("name",homesosoedit.getText().toString()));
            return true;
        }
        return super.dispatchKeyEvent(event);
    }
}

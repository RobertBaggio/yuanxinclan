package com.yuanxin.clan.core.company.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/3/20.
 */
////企业动态
public class PublishCompanyInfoActivity extends BaseActivity {
    @BindView(R.id.activity_publish_company_info_head_image_layout)
    LinearLayout activityPublishCompanyInfoHeadImageLayout;
    @BindView(R.id.activity_publish_company_info_right_layout)
    LinearLayout activityPublishCompanyInfoRightLayout;
    @BindView(R.id.activity_publish_company_info_title_text)
    TextView activityPublishCompanyInfoTitleText;
    @BindView(R.id.activity_publish_company_info_title_edit)
    EditText activityPublishCompanyInfoTitleEdit;
    @BindView(R.id.activity_company_info_company_name_layout)
    RelativeLayout activityCompanyInfoCompanyNameLayout;
    @BindView(R.id.activity_publish_company_info_detail_edit)
    EditText activityPublishCompanyInfoDetailEdit;
    private String userNm, epId;
    private int userId;
    private LocalBroadcastManager localBroadcastManager;


    @Override
    public int getViewLayout() {
        return R.layout.activity_publish_company_info;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        getInfo();
    }

    private void getInfo() {
        Intent intent = getIntent();
        int epId = intent.getIntExtra("epId", 0);
//        getPublishCompanyInfo(epId);//获取企业动态 感觉不需要 不弄了
    }
//    private void getPublishCompanyInfo(int epId ){//我的企业动态
//        AsyncHttpClient client = new AsyncHttpClient();
//        String url = "http://192.168.1.111:8080/yuanxinbuluo/epNews/search";
//        RequestParams params = new RequestParams();
//        params.put("epId", epId);
//        client.get(url, params, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onSuccess(int i, Header[] headers, String s) {
//
//                try {
//                    JSONObject object = new JSONObject(s);
//                    if (object.getString("success").equals("true")) {
//                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
//                        JSONObject jsonObject = object.getJSONObject("data");
////                        shoppingCartEntityList.clear();
//                        JSONArray dataArray = jsonObject.getJSONArray("businessArea");
//                        for (int a = 0; a < dataArray.length(); a++) {
//                            JSONObject businessObject=dataArray.getJSONObject(a);
//
//                            int businessAreaId=businessObject.getInt("businessAreaId");//商圈id
//                            String baImage1=businessObject.getString("baImage1");//图片
//                            String image= Url.urlHost+baImage1;
//                            String businessAreaNm=businessObject.getString("businessAreaNm");//商圈名称
//                        }
//
//                    } else {
//
//                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
//                    }
//
//
//                } catch (JSONException e) {
//                    Logger.d("json 解析出错");
//                }
//            }
//        });
//    }


    @OnClick({R.id.activity_publish_company_info_head_image_layout, R.id.activity_publish_company_info_right_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_publish_company_info_head_image_layout:
                finish();
                break;
            case R.id.activity_publish_company_info_right_layout:
                String title = activityPublishCompanyInfoTitleEdit.getText().toString().trim();
                String detail = activityPublishCompanyInfoDetailEdit.getText().toString().trim();
                addEpNews(title, detail);//增加企业动态
                localBroadcastManager = LocalBroadcastManager.getInstance(this);
// 获取实例
                Intent intent = new Intent("com.example.broadcasttest.PUBLISH_COMPANY_INFO_REFRESH");
                localBroadcastManager.sendBroadcast(intent); // 发送本地广播
                Intent intentOne = new Intent();
                intentOne.putExtra("image", "刷新");
                setResult(RESULT_OK, intentOne);
                finish();
                //上传
                break;
        }
    }

    private void addEpNews(String title, String detail) {
        userId = UserNative.getId();
        userNm = UserNative.getName();
        epId = UserNative.getEpId();
        String url = Url.addEpNews;
        RequestParams params = new RequestParams();
        params.put("epId", epId);//
        params.put("title", title);//标题
        params.put("titleImage", "");//标题图片
        params.put("content", detail);//内容
        params.put("userId", userId);//用户Id
        params.put("userNm", userNm);//用户名
        doHttpPost(url, params, new BaseActivity.RequestCallback() {
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

                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }

                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });
    }
}

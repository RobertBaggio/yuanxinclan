package com.yuanxin.clan.core.company.view;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.LoginActivity;
import com.yuanxin.clan.core.activity.MyBusinessDistrictSetUpActivity;
import com.yuanxin.clan.core.adapter.EpStyleAdapter;
import com.yuanxin.clan.core.adapter.EpcolorSelectAdapter;
import com.yuanxin.clan.core.adapter.ShouyeAdapter;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.company.bean.EpColorselectEntity;
import com.yuanxin.clan.core.company.bean.ShouyeEntity;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.CommonString;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;
import com.yuanxin.clan.mvp.view.WebViewJavaScriptFunction;
import com.yuanxin.clan.mvp.view.X5WebView;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by lenovo1 on 2017/4/22.
 * 企业风格类
 */
public class ChooseStyleActivity extends BaseActivity {

    @BindView(R.id.tbsContent)
    X5WebView activityBaseWebAddWebView;
    @BindView(R.id.activity_my_bargain_right_layout)
    LinearLayout activityMyBargainRightLayout;
    @BindView(R.id.style_recycler_view)
    RecyclerView style_recycler_view;
    @BindView(R.id.style_recycler_viewcolor)
    RecyclerView style_recycler_viewcolor;
    @BindView(R.id.activity_my_bargain_left_layout)
    LinearLayout activityMyBargainLeftLayout;
    @BindView(R.id.neili)
    LinearLayout neili;
    @BindView(R.id.neiscrollView_web)
    ScrollView neiscrollView_web;
    private ShouyeAdapter chooseStyleAdapter;
    private EpStyleAdapter mEpStyleAdapter;
    private EpcolorSelectAdapter mEpcolorSelectAdapter;
    private List<ShouyeEntity> shouyeEntities = new ArrayList<>();
    private List<EpColorselectEntity> colorsList = new ArrayList<>();
    private int typeid, viewId;
    private int index,userId;
    private int webViewHeight;
    private String mepAccessPath,viewColor,epId;

    @Override
    public int getViewLayout() {
        return R.layout.activity_chooseep_style;
    }

    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        typeid= getIntent().getIntExtra("type",0);
        mepAccessPath = getIntent().getStringExtra("epAccessPath");
        epId = UserNative.getEpId();
        userId = UserNative.getId();
        String url = Url.urlWeb + "/" + mepAccessPath + "&param=" + epId + "&appFlg=1" + "&userId=" + userId;
        activityBaseWebAddWebView.loadUrl(url);
        activityBaseWebAddWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
        activityBaseWebAddWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                view.loadUrl(url);
//                Log.i("lgq=verrideUrlLoading","1111");
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                view.getSettings().setJavaScriptEnabled(true);
//                Log.i("lgq===onPageStarted","2222222222222");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.getSettings().setJavaScriptEnabled(true);
                activityBaseWebAddWebView.loadUrl("javascript:window.yxbl_app.getBodyHeight($(document.body).height())");
                super.onPageFinished(view, url);
            }
        });


        activityBaseWebAddWebView.addJavascriptInterface(new WebViewJavaScriptFunction() {

            @Override
            public void onJsFunctionCalled(String tag) {
                // TODO Auto-generated method stub

            }
            /**
             * 高度
             */
            @JavascriptInterface
            public void getBodyHeight(String number) {
                webViewHeight =  Integer.parseInt(number.split("[.]")[0]);
                Log.i("lgqqqqq======  ", "webViewHeight" + webViewHeight);

//                Message msg = Message.obtain(null, 6, 0, 0);
                Message msg = new Message();
                msg.what = 1;
                msg.arg1 = webViewHeight;
                handler.sendMessage(msg);
//                ScrollView.LayoutParams linearParams =(ScrollView.LayoutParams) neili.getLayoutParams(); //取控件textView当前的布局参数
//
////                linearParams.width = 40;// 控件的宽强制设成30
//                linearParams.height =a/2;// 控件的高强制设成20
//
//                neili.setLayoutParams(linearParams);

            }
            @JavascriptInterface
            public void toLogin() {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }

            @JavascriptInterface
            public void shareToApp(String title, String content, String imgUrl, String url) {
            }

            /**
             * 测试坐标上传是否成功
             *
             * @param latitude
             * @param longitude
             */
            @JavascriptInterface
            public void outLocation(String latitude, String longitude) {
                System.out.println("javascript输出：" + latitude + "  " + longitude);
            }

        }, CommonString.js2Android);



        if (typeid == 2) {
            // 企业
            viewId = getIntent().getIntExtra("epViewId", 0);
        } else {
            // 商圈
            viewId = getIntent().getIntExtra("baViewId", 0);
        }
        Log.v("lgq","type==="+typeid);
        activityMyBargainRightLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                viewColor  = "#F43530";
//                activityBaseWebAddWebView.loadUrl("javascript:changeColor('" + viewColor + "')");
                Intent mIntent = new Intent(ChooseStyleActivity.this,CompanyInfoActivity.class);
                    mIntent.putExtra("index", index);
                    mIntent.putExtra("epAccessPath", mepAccessPath);
                    mIntent.putExtra("viewColor", viewColor);
                    ChooseStyleActivity.this.setResult(15, mIntent);
                    finish();
            }
        });
        initRecyclerView();
        addFriendSingle();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                LinearLayout.LayoutParams linearParams2 =(LinearLayout.LayoutParams) activityBaseWebAddWebView.getLayoutParams(); //取控件textView当前的布局参数
//                linearParams.width = 40;// 控件的宽强制设成30
//                linearParams2.height =webViewHeight*3;// 控件的高强制设成20
                Logger.e(getResources().getDisplayMetrics().density + "");
                linearParams2.height = (int)(webViewHeight*getResources().getDisplayMetrics().density)/2;// 控件的高强制设成20
                activityBaseWebAddWebView.setLayoutParams(linearParams2);
            }
        }
    };


    private void addFriendSingle() {//还差图片
        String url = Url.qiyemobang;
        RequestParams params = new RequestParams();
        if (typeid==2){
            params.put("epType", 2);//1企业   2  商圈

        }else {
            params.put("epType", 1);//1企业   2  商圈
        }
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
//                    Log.v("lgq","..........."+s);
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String sb = object.getString("data");
                        JSONArray arr= new JSONArray(sb);
                        shouyeEntities.clear();
                        for (int j = 0; j < arr.length(); j++) {
                            JSONObject temp = (JSONObject) arr.get(j);
                            int id = temp.getInt("epViewId");
                            String epViewNm = temp.getString("epViewNm");
                            String epViewImage = temp.getString("epViewImage");
                            String epAccessPath = temp.getString("epAccessPath");
                            String viewColor = temp.getString("viewColor");
                            ShouyeEntity shouyeEntity = new ShouyeEntity();
                            if (epAccessPath.equals(mepAccessPath)){
                                shouyeEntity.setIfselect(true);
                            }else {
                                shouyeEntity.setIfselect(false);
                            }
                            shouyeEntity.setEpViewId(id);
                            shouyeEntity.setColorString(viewColor);
                            shouyeEntity.setEpViewImage(epViewImage);
                            shouyeEntity.setEpViewNm(epViewNm);
                            shouyeEntity.setEpAccessPath(epAccessPath);
                            // 是否是当前视图
                            if (id == viewId) {
                                shouyeEntity.setCurrent(true);
                            } else {
                                shouyeEntity.setCurrent(false);
                            }
                            shouyeEntities.add(shouyeEntity);
                            chooseStyleAdapter.notifyDataSetChanged();
                            mEpStyleAdapter.notifyDataSetChanged();

                        }

//                        Toast.makeText(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();
                    } else {
                        ToastUtil.showWarning(getApplicationContext(), object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    Logger.d("json 解析出错");
                }
            }
        });

    }


    private void initRecyclerView() {
        chooseStyleAdapter = new ShouyeAdapter(ChooseStyleActivity.this, shouyeEntities);
        chooseStyleAdapter.setOnItemClickListener(new ShouyeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showNormalDialogOne(shouyeEntities.get(position));
            }
        });
//       cycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        mEpStyleAdapter = new EpStyleAdapter(ChooseStyleActivity.this,shouyeEntities);
        mEpStyleAdapter.setOnItemClickListener(new EpStyleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mEpStyleAdapter.cleckAll(position);
                index  = shouyeEntities.get(position).getEpViewId();
                mepAccessPath = shouyeEntities.get(position).getEpAccessPath();

                String url = Url.urlWeb + "/" + mepAccessPath + "&param=" + epId + "&appFlg=1" + "&userId=" + userId;
                activityBaseWebAddWebView.loadUrl(url);

                String ac = shouyeEntities.get(position).getColorString();
                if (!TextUtil.isEmpty(ac)){
                    String shuzu [] =null;
                    shuzu = ac.split(",");
                    if (colorsList.size()>0){
                        colorsList.clear();
                    }
                    for (int i = 0;i<shuzu.length;i++){
                        EpColorselectEntity entity = new EpColorselectEntity();
                        entity.setIfselect(false);
                        entity.setColorString(shuzu[i]);
                        colorsList.add(entity);
                    }

                    mEpcolorSelectAdapter.notifyDataSetChanged();

                }

//                ToastUtil.showWarning(getApplicationContext(), shouyeEntities.get(position).getEpViewNm(), Toast.LENGTH_SHORT);
            }
        });
        style_recycler_view.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
        style_recycler_view.addItemDecoration(new SpaceItemDecoration(40));
        style_recycler_view.setAdapter(mEpStyleAdapter);

        mEpcolorSelectAdapter = new EpcolorSelectAdapter(ChooseStyleActivity.this,colorsList);

        mEpcolorSelectAdapter.setOnItemClickListener(new EpcolorSelectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                mEpcolorSelectAdapter.cleckAll(position);
                viewColor = colorsList.get(position).getColorString();
                activityBaseWebAddWebView.loadUrl("javascript:changeColor('" + viewColor + "')");

            }
        });

        style_recycler_viewcolor.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
        style_recycler_viewcolor.addItemDecoration(new SpaceItemDecoration(40));
        style_recycler_viewcolor.setAdapter(mEpcolorSelectAdapter);


    }


    @OnClick(R.id.activity_my_bargain_left_layout)
    public void onClick() {
        finish();
    }

    private void showNormalDialogOne(final ShouyeEntity entity) {
        /* @setIcon 设置对话框图标
         * @setTitle 设置对话框标题
         * @setMessage 设置对话框消息提示
         * setXXX方法返回Dialog对象，因此可以链式设置属性
         */
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(this);
//        normalDialog.setIcon(R.drawable.icon_dialog);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定使用该风格吗！");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mIntent = new Intent(ChooseStyleActivity.this,CompanyInfoActivity.class);

                        if (typeid==2){
                            mIntent = new Intent(ChooseStyleActivity.this,MyBusinessDistrictSetUpActivity.class);
                            mIntent.putExtra("index", entity.getEpViewId());
                            ChooseStyleActivity.this.setResult(18, mIntent);
                            Log.v("lgq","...........sss"+entity.getEpViewId());
                            finish();
                        }else {
                            mIntent.putExtra("index", entity.getEpViewId());
                            mIntent.putExtra("epAccessPath", entity.getEpAccessPath());
                            ChooseStyleActivity.this.setResult(15, mIntent);
                            finish();
                        }

//                    deleteList.add(entity);
                        //entityList.get(position).getUserId()
//                        deleteCompanyMemberToWeb(uid, position);
//                        notifyDataSetChanged();
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

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {
        int mSpace;

        /**
         * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
         * the number of pixels that the item view should be inset by, similar to padding or margin.
         * The default implementation sets the bounds of outRect to 0 and returns.
         * <p>
         * <p>
         * If this ItemDecoration does not affect the positioning of item views, it should set
         * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
         * before returning.
         * <p>
         * <p>
         * If you need to access Adapter for additional data, you can call
         * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
         * View.
         *
         * @param outRect Rect to receive the output.
         * @param view    The child view to decorate
         * @param parent  RecyclerView this ItemDecoration is decorating
         * @param state   The current state of RecyclerView.
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
//            outRect.left = mSpace;
//            outRect.right = mSpace;
            outRect.bottom = mSpace;
//            if (parent.getChildAdapterPosition(view) == 0) {
//                outRect.top = mSpace;
//            }

        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }
}

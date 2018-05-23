package com.yuanxin.clan.core.mineclass;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.RequestParams;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.ComplaintAdviceActivity;
import com.yuanxin.clan.core.activity.PersionChatActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.Constant;
import com.yuanxin.clan.core.mineclass.adapter.HelpListAdapter;
import com.yuanxin.clan.core.mineclass.entity.QuestionEntity;
import com.yuanxin.clan.core.util.More_LoadDialog;
import com.yuanxin.clan.core.util.MyShareUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;
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
 * 帮助中心
 */

public class HelpCentreActivity extends BaseActivity {

    @BindView(R.id.rlLeft)
    LinearLayout right_layout;
    @BindView(R.id.zhongxkefuli)
    LinearLayout zhongxkefuli;
    @BindView(R.id.zhongxyijli)
    LinearLayout zhongxyijli;
    @BindView(R.id.zhongxiphoneli)
    LinearLayout zhongxiphoneli;
    @BindView(R.id.activity_five_my_gift_make_layout)
    LinearLayout activity_five_my_gift_make_layout;
    @BindView(R.id.activity_five_my_gift_make_layou)
    LinearLayout activity_five_my_gift_make_layou;
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;

    private More_LoadDialog mMore_loadDialog;

    private List<QuestionEntity> newEntityOnes = new ArrayList<>();
    private HelpListAdapter mTradeslistAdapter;
    public static final String SIGN_NOT_BUSINESS = "wechatweb/sign_not_business";


    @Override
    public int getViewLayout() {
        return R.layout.helpcentrelayout;
    }
    @Override
    protected int getTitleViewLayout() {
        return NO_TITLE_VIEW;
    }

    @Override
    protected void initView(Bundle savedInstanceState, Intent intent) {
        mMore_loadDialog = new More_LoadDialog(this);
        initRecyclerView();
        wenti();
    }


    private void initRecyclerView() {
        mTradeslistAdapter = new HelpListAdapter(HelpCentreActivity.this, newEntityOnes);
        mTradeslistAdapter.setOnItemClickListener(new HelpListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent2 = new Intent(HelpCentreActivity.this, MyhelpcentreDetail.class);//有个聊天的标志
                intent2.putExtra("id",String.valueOf(newEntityOnes.get(position).getQuestionId()));
                intent2.putExtra("title",newEntityOnes.get(position).getQuestionTitle());
                startActivity(intent2);
            }
        });
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));//listView
        recycler_view.setNestedScrollingEnabled(false);//禁止滑动
        recycler_view.setAdapter(mTradeslistAdapter);
        recycler_view.setFocusable(false);//导航栏切换不再focuse
    }

    @OnClick({R.id.rlLeft,R.id.zhongxyijli,R.id.zhongxiphoneli,R.id.zhongxkefuli,R.id.activity_five_my_gift_make_layout ,R.id.activity_five_my_gift_make_layou})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlLeft:
                finish();
                break;
            case R.id.zhongxiphoneli:
//                showxiala();
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"4006661386"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
//                dialog.dismiss();
                break;
            case R.id.zhongxyijli:
                startActivity(new Intent(HelpCentreActivity.this, ComplaintAdviceActivity.class));
                break;
            case R.id.zhongxkefuli:
                overEC();

                break;
            case R.id.activity_five_my_gift_make_layout:
                startActivity(new Intent(HelpCentreActivity.this, HelpCentreListActivity.class).putExtra("title","常见问题"));
                break;
            case R.id.activity_five_my_gift_make_layou:
                startActivity(new Intent(HelpCentreActivity.this, HelpCentreListActivity.class).putExtra("title","使用指南"));
                break;

        }
    }

    public void wenti() {//结束会话
        String url = Url.getquestion;
        RequestParams params = new RequestParams();
//        params.put("pageNumber",1);
        params.put("type",3);
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(HelpCentreActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getString("success").equals("true")) {
                        String ph = object.getString("data");
                        JSONArray array = new JSONArray(ph);
                        for (int d = 0; d < array.length(); d++) {
                            JSONObject dataObject = array.getJSONObject(d);
                            String questionId=dataObject.getString("questionId");
                            String questionTitle=dataObject.getString("questionTitle");
                            String questionContent=dataObject.getString("questionContent");


                            QuestionEntity mnewEntity =new QuestionEntity();
                            mnewEntity.setQuestionId(questionId);
                            mnewEntity.setQuestionTitle(questionTitle);
                            mnewEntity.setQuestionContent(questionContent);

                            newEntityOnes.add(mnewEntity);
                        }
                        mTradeslistAdapter.notifyDataSetChanged();
                        LinearLayout.LayoutParams linearParams =(LinearLayout.LayoutParams) recycler_view.getLayoutParams(); //取控件textView当前的布局参数
                        linearParams.height = UIUtils.dip2px(44)*newEntityOnes.size();// 控件的高强制设成20

                    } else {
                        ToastUtil.showWarning(HelpCentreActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public void overEC() {//结束会话
        String url = Url.getServerPhone;
        RequestParams params = new RequestParams();
        mMore_loadDialog.show();
        doHttpGet(url, params, new RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                mMore_loadDialog.dismiss();
                ToastUtil.showWarning(HelpCentreActivity.this, "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    mMore_loadDialog.dismiss();
                    if (object.getString("success").equals("true")) {
                        String ph = object.getString("data");
//                        dad(ph);
                        Intent intent = new Intent(HelpCentreActivity.this, PersionChatActivity.class);
                        intent.putExtra(Constant.EXTRA_USER_ID,ph);
                        intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                        intent.putExtra(Constant.USER_NAME, UserNative.getName());
                        intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                        String add = UserNative.getCity()+UserNative.getArea()+UserNative.getDetail();
                        intent.putExtra(Constant.ADDRESS,add );
                        intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                        intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                        intent.putExtra("type", "圆心客服");
                        startActivity(intent);
//                        dialog.dismiss();
//                        Toast.makeText(getContext(), object.getString("msg"), Toast.LENGTH_SHORT).show();

                    } else {
                        ToastUtil.showWarning(HelpCentreActivity.this, object.getString("msg"), Toast.LENGTH_SHORT);
                    }
                } catch (Exception e) {
//                    Toast.makeText(getContext(), "数据解析出错", Toast.LENGTH_SHORT).show();
//                    Logger.e("数据解析出错");
                }
            }
        });

    }

    //更新dialog
    public void dad(final String phone) {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(HelpCentreActivity.this);
        View view = inflater.inflate(R.layout.phone_e_dialog, null);
        dialog = new Dialog(HelpCentreActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.AnimViewshow);

//        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        dialogWindow.setGravity(Gravity.CENTER);

//        lp.width = 900; // 宽度
//        lp.height = 650; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
//        updateContent="1.添加扫码功能\n2.优化首页UI\n3.添加支付功能";
        LinearLayout ph = (LinearLayout) view.findViewById(R.id.callphoneli);
        LinearLayout ea = (LinearLayout) view.findViewById(R.id.easelikeli);
        TextView dismiss = (TextView)view.findViewById(R.id.dissmisste);
        StringBuilder sb = new StringBuilder();

        ea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HelpCentreActivity.this, PersionChatActivity.class);
                intent.putExtra(Constant.EXTRA_USER_ID,phone);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra(Constant.USER_NAME, UserNative.getName());
                intent.putExtra(Constant.USER_EPNAME, UserNative.getEpNm());
                String add = UserNative.getCity()+UserNative.getArea()+UserNative.getDetail();
                intent.putExtra(Constant.ADDRESS,add );
                intent.putExtra(Constant.HEAD_EPIMAGE_URL, MyShareUtil.getSharedString("eplogo"));
                intent.putExtra(Constant.EP_POSITION, UserNative.getEpPosition());
                intent.putExtra("type", "圆心客服");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        ph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"4006661386"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}

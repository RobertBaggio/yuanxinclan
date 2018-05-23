package com.yuanxin.clan.core.zxing;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

public abstract class BaseFragment extends Fragment {

    protected View contentView;
    protected Context mContext;
    protected Bundle mBundle;
    protected String strClassName;
    private Observer exitAccountObserver = new Observer() {
        @Override
        public void update(int id) {
            getActivity().finish();
        }
    };
    private Observer showLoginDialogObserver = new Observer() {
        @Override
        public void update(int id) {
        }
    };

    public abstract void releaseRes();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        mBundle = getArguments();
        strClassName = ((Object) this).getClass().getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(strClassName); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(strClassName);
    }

    @Override
    public void onDetach() {
        // TODO 自动生成的方法存根
        exitAccountObserver = null;
        showLoginDialogObserver = null;
        contentView = null;
        mContext = null;
        mBundle = null;
        releaseRes();
        super.onDetach();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


//    /**
//     * 如果堆栈里面还有fragment就返回上一层fragment，否则调用activity的finish()方法
//     */
//    public void popBack() {
//        if (getFragmentManager().getBackStackEntryCount() == 0) {
//            getActivity().finish();
//            return;
//        }
//        getFragmentManager().popBackStack();
//    }
//
//
//    public void addFragment(Fragment fragment, int contentId) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.add(contentId, fragment);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }
//
//    public void replaceFragment(Fragment fragment, int contentId) {
//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.replace(contentId, fragment);
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//        transaction.addToBackStack(null);
//        transaction.commit();
//    }

    /**
     * Activity跳转
     */
    public void toSecondActivity(int fragmentID, Bundle bundle) {
        if (bundle == null || fragmentID == 0)
            return;
        bundle.putInt(FragmentID.ID, fragmentID);
        Intent intent = new Intent();
        intent.setClass(mContext, SubActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }



    public void toSecondActivityForRequest(Class cls, Bundle bundle, int nRequestCode) {
        if (bundle == null || cls == null || nRequestCode == 0)
            return;
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, nRequestCode);
    }



    public void toSecondActivity(Class cls) {
        Intent intent = new Intent();
        intent.setClass(mContext, cls);
        startActivity(intent);
    }



    /**
     * Activity跳转
     */
    public void toSecondActivity(int fragmentID) {
        Bundle bundle = new Bundle();
        toSecondActivity(fragmentID, bundle);
    }





}

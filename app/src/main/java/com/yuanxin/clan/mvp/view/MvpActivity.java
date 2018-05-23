package com.yuanxin.clan.mvp.view;

import android.content.Intent;
import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.yuanxin.clan.R;
import com.yuanxin.clan.mvp.model.BaseVo;
import com.yuanxin.clan.mvp.network.ResponseInfo;
import com.yuanxin.clan.mvp.presenter.MvpBasePresenter;
import com.yuanxin.clan.mvp.presenter.MvpPresenter;
import com.yuanxin.clan.mvp.reveiver.NetworkStateEvent;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.ParameterizedType;

/**
 * @author lch
 *         date 2015/8/10.
 */
public abstract class MvpActivity<P extends MvpBasePresenter> extends BaseActivity implements MvpView {
    protected P presenter;
    protected MaterialDialog progressDialog;
    private boolean loadSuccess; //数据是否加载成功
    private boolean loadFinish; //请求是否成功


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<? extends MvpPresenter> presenterClass = (Class<? extends MvpPresenter>) type.getActualTypeArguments()[0];
        try {
            this.presenter = (P) presenterClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        presenter.attachView(this);
        progressDialog = new MaterialDialog.Builder(this)
                .customView(R.layout.load_data_progress_layout, false)
                .cancelable(true).build();
        EventBus.getDefault().register(this);
        loadData(savedInstanceState, getIntent());
    }


    @Override
    public void beforeSuccess() {
        loadSuccess = true;
        loadFinish = true;
    }

    @Override
    public void onLoading() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            loadSuccess = false;
        }
    }

    @Override
    public void onUploadSuccess(String url, BaseVo dataVo) {

    }

    @Override
    public void onDownloadSuccess(String url, BaseVo dataVo) {

    }

    @Override
    public void uploadProgress(String url, long currentSize, long totalSize, float progress, long networkSpeed) {

    }

    @Override
    public void downloadProgress(String url, long currentSize, long totalSize, float progress, long networkSpeed) {

    }

    @Override
    public void onError(ResponseInfo responseInfo) {
        loadFinish = true;
    }

    /**
     * 网络状态改变事件，由子类重写实现相关的逻辑
     *
     * @param networkStateEvent
     */
    @Subscribe
    public void onEvent(NetworkStateEvent networkStateEvent) {
        if (!networkStateEvent.hasNetworkConnected) {
            ToastUtil.showShort("网络连接已断开!");
        } else {
            if (loadFinish && !loadSuccess) {
                //网络已连接数据没有加载成功，重新加载
                loadData(null, getIntent());
            }
        }
    }

    /**
     * 从intent中获取请求参数，初始化vo对象，并发送请求
     *
     * @param savedInstanceState
     * @param intent
     */
    protected abstract void loadData(Bundle savedInstanceState, Intent intent);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        presenter = null;
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void onStopLoading() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}

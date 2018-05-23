package com.yuanxin.clan.mvp.view;


import com.yuanxin.clan.mvp.model.BaseVo;
import com.yuanxin.clan.mvp.network.ResponseInfo;

/**
 * Created by Administrator on 2015/8/10.
 */
public interface MvpView {
    /**
     * 开始加载数据时回调此方法，用以显示加载ProgressDialog或者其他的的操作
     */
    void onLoading();

    /**
     * @return 布局resourceId
     */
    int getViewLayout();

    /**
     * 数据加载成功后的回调
     */
    void beforeSuccess();

    /**
     * 默认请求数据解析成功后，将数据填充到View，并显示View
     *
     * @param url    请求的url
     * @param dataVo 解析成功后返回VO对象
     */
    void showContentView(String url, BaseVo dataVo);

    void onError(ResponseInfo responseInfo);

    /**
     * 加载数据完成回调方法
     */
    void onStopLoading();

    void onUploadSuccess(String url, BaseVo dataVo);

    void onDownloadSuccess(String url, BaseVo dataVo);

    void uploadProgress(String url, long currentSize, long totalSize, float progress, long networkSpeed);

    void downloadProgress(String url, long currentSize, long totalSize, float progress, long networkSpeed);
}

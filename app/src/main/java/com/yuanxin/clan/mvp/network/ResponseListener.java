package com.yuanxin.clan.mvp.network;


import com.yuanxin.clan.mvp.model.BaseVo;

/**
 * @author lch
 * @date 2015/8/10.
 */
public interface ResponseListener<M extends BaseVo> {

    /**
     * 请求成功回调方法
     */
    void onSuccess(M dataVo);

    /**
     * 请求失败回调方法
     */
    void onError(M dataVo);
}

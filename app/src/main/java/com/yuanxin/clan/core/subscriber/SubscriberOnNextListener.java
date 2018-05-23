package com.yuanxin.clan.core.subscriber;

/**
 * Created by liukun on 16/3/10.
 */
public interface SubscriberOnNextListener<BaseJsonEntity> {
    void onNext(BaseJsonEntity t);
}

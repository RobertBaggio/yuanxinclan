package com.yuanxin.clan.core.zxing;

public interface Subject {

    /**
     * 注册一个观察者
     */
    void addObserver(Observer observer);

    /**
     * 删除一个观察者
     *
     * @return 是否删除成功
     */
    boolean removeObserver(Observer observer);

    /**
     * 通知所有的观察者
     *
     * @param id 对应观察者的id
     */
    void notifyObserver(int id);

}

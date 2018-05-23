package com.yuanxin.clan.mvp.reveiver;

/**
 * @author lch
 * @date 2015/7/13
 */
public class NetworkStateEvent {
    public boolean hasNetworkConnected;

    public NetworkStateEvent(boolean hasNetworkConnected) {
        this.hasNetworkConnected = hasNetworkConnected;
    }
}

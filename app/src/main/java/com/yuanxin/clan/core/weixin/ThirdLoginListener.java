package com.yuanxin.clan.core.weixin;

/**
 * ProjectName: yuanxinclan
 * Describe:
 * Author: xjc
 * Date: 2017/7/6 0006 11:18
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public interface ThirdLoginListener {
    /**
     * 回调
     * @param strUniqueID 第三方userid
     * @param strAccessToken 第三方token
     * @param strExpiresIn 第三方token过期时间
     */
    void onComplete(String strThirdType,String strUniqueID, String strAccessToken, String strExpiresIn);

}

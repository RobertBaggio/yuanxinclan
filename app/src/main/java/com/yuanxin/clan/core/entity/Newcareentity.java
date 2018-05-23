package com.yuanxin.clan.core.entity;

import com.yuanxin.clan.core.market.bean.ShoppingCommodity;

/**
 * ProjectName: new_yuanxin
 * Describe:
 * Author: xjc
 * Date: 2017/9/7 0007 17:01
 * Copyright (c) 2017, tonixtech.com All Rights Reserved.
 */

public class Newcareentity {

    private BusinessIdsentity mBusinessIdsentity;
    private ShoppingCommodity ShoppingCommodity;

    public BusinessIdsentity getBusinessIdsentity() {
        return mBusinessIdsentity;
    }

    public void setBusinessIdsentity(BusinessIdsentity businessIdsentity) {
        mBusinessIdsentity = businessIdsentity;
    }

    public com.yuanxin.clan.core.market.bean.ShoppingCommodity getShoppingCommodity() {
        return ShoppingCommodity;
    }

    public void setShoppingCommodity(com.yuanxin.clan.core.market.bean.ShoppingCommodity shoppingCommodity) {
        ShoppingCommodity = shoppingCommodity;
    }
}

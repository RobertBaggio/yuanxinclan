package com.yuanxin.clan.core.util;

import com.yuanxin.clan.core.huanxin.BusinessMessage;

import java.util.Comparator;

/**
 * Created by Administrator on 2017/11/23 0023.
 */

public class SortComparator implements Comparator {
    @Override
    public int compare(Object lhs, Object rhs) {
        int result = 0;
        long dateSort = 0;
        int read = 0;
        switch (lhs.getClass().toString()) {
            case "class com.yuanxin.clan.core.huanxin.BusinessMessage":
                //商圈消息排序
                BusinessMessage a = (BusinessMessage) lhs;
                BusinessMessage b = (BusinessMessage) rhs;
                try {
                    dateSort = Long.parseLong(Utils.getTime3(b.getCreateDt())) - Long.parseLong(Utils.getTime3(a.getCreateDt()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                read = a.getMsgRead() - b.getMsgRead();
                if (read == 0) {
                    result = (int)dateSort;
                } else {
                    result = read;
                }
                break;
            case "class com.yuanxin.clan.core.huanxin.BuyerMessage":
                // 买家消息排序
//                BuyerMessage aBuy = (BuyerMessage) lhs;
//                BuyerMessage bBuy = (BuyerMessage) rhs;
//                try {
//                    dateSort = aBuy.getTime() - bBuy.getTime();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                result = ((int)dateSort + aBuy.getMsgRead() - bBuy.getMsgRead());
                break;
            case "class com.yuanxin.clan.core.huanxin.SellerMessage":
                // 卖家消息排序
                break;
        }
        return result;
    }
}

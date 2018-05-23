/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yuanxin.clan.core.huanxin;

import android.content.Context;

import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.util.SortComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import xiaofei.library.datastorage.DataStorageFactory;
import xiaofei.library.datastorage.IDataStorage;

public class BuyerMessageDao {
    static final String TABLE_NAME = "buyer_msgs";

    static final String COLUMN_NAME_UNREAD_MSG_COUNT = "unreadMsgCount";
    private IDataStorage dataStorage;

    public BuyerMessageDao(Context context) {
        dataStorage = DataStorageFactory.getInstance(
                context,
                DataStorageFactory.TYPE_DATABASE);
    }
    public Boolean exist(BuyerMessage bm) {
        return dataStorage.contains(bm);
    }
    /**
     * save message
     *
     * @param message
     * @return return cursor of the message
     */
    public void saveMessage(BuyerMessage message) {
        dataStorage.storeOrUpdate(message);
    }

    /**
     * update message
     *
     * @param values
     */
    public void updateMessage(BuyerMessage values) {
        dataStorage.storeOrUpdate(values);
    }

    /**
     * get messges
     *
     * @return
     */
    public List<BuyerMessage> getMessagesList() {
        ArrayList<BuyerMessage> result = new ArrayList<BuyerMessage>();
        List<BuyerMessage> list = dataStorage.loadAll(BuyerMessage.class);
        for (BuyerMessage bm: list) {
            if (bm.getId() == UserNative.getId()) {
                result.add(bm);
            }
        }
        Collections.sort(result, new SortComparator());
        return result;
    }

    public void deleteMessage(BuyerMessage bm) {
        dataStorage.delete(bm);
    }

    public int getUnreadMessagesCount() {
        List<BuyerMessage> messages = getMessagesList();
        int result = 0;
        for (BuyerMessage bm: messages) {
            if (bm.getStatus().equals(BuyerMessage.BuyerMesageStatus.UNREAD)) {
                result ++;
            }
        }
        return result;
    }
}

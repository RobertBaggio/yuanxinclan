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

import java.util.List;

import xiaofei.library.datastorage.DataStorageFactory;
import xiaofei.library.datastorage.IDataStorage;

public class SellerMessageDao {
    static final String TABLE_NAME = "seller_msgs";

    static final String COLUMN_NAME_UNREAD_MSG_COUNT = "unreadMsgCount";

    private IDataStorage dataStorage;
    public SellerMessageDao(Context context) {
        dataStorage = DataStorageFactory.getInstance(
                context,
                DataStorageFactory.TYPE_DATABASE);
    }

    public Boolean exist(SellerMessage sm) {
        return dataStorage.contains(sm);
    }
    /**
     * save message
     *
     * @param message
     * @return return cursor of the message
     */
    public void saveMessage(SellerMessage message) {
        dataStorage.storeOrUpdate(message);
    }

    /**
     * update message
     *
     * @param values
     */
    public void updateMessage( SellerMessage values) {
        dataStorage.storeOrUpdate(values);
    }

    /**
     * get messges
     *
     * @return
     */
    public List<SellerMessage> getMessagesList() {
        return dataStorage.loadAll(SellerMessage.class);
    }

    public void deleteMessage(SellerMessage sm) {
        dataStorage.delete(sm);
    }

    public int getUnreadMessagesCount() {
        List<SellerMessage> messages = getMessagesList();
        int result = 0;
        for (SellerMessage bm: messages) {
            if (bm.getStatus().equals(SellerMessage.SellerMesageStatus.UNREAD)) {
                result ++;
            }
        }
        return result;
    }
}

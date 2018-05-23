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

public class BusinessMessageDao {
    static final String TABLE_NAME = "business_msgs";

    static final String COLUMN_NAME_UNREAD_MSG_COUNT = "unreadMsgCount";
    private IDataStorage dataStorage;

    public BusinessMessageDao(Context context) {
        dataStorage = DataStorageFactory.getInstance(
                context,
                DataStorageFactory.TYPE_DATABASE);
    }

    public Boolean exist(BusinessMessage bm) {
        return dataStorage.contains(bm);
    }
    /**
     * save message
     *
     * @param message
     * @return return cursor of the message
     */
    public void saveMessage(BusinessMessage message) {
        dataStorage.storeOrUpdate(message);
    }

    /**
     * update message
     *
     * @param values
     */
    public void updateMessage(BusinessMessage values) {
        dataStorage.storeOrUpdate(values);
    }

    /**
     * get messges
     *
     * @return
     */
    public List<BusinessMessage> getMessagesList() {
        ArrayList<BusinessMessage> result = new ArrayList<BusinessMessage>();
        List<BusinessMessage> list = dataStorage.loadAll(BusinessMessage.class);
        for (BusinessMessage bm: list) {
            if (bm.getUserId() == UserNative.getId()) {
                result.add(bm);
            }
        }
        Collections.sort(result, new SortComparator());
        return result;
    }

    public void deleteMessage(BusinessMessage bm) {
        dataStorage.delete(bm);
    }

    public int getUnreadMessagesCount() {
        List<BusinessMessage> messages = getMessagesList();
        int result = 0;
        for (BusinessMessage bm: messages) {
            if (bm.getMsgRead() == 0) {
                result ++;
            }
        }
        return result;
    }
}

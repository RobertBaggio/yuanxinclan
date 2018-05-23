package com.yuanxin.clan.core.util;

import java.io.File;

/**
 * ProjectName: yuanxinclan_new
 * Author: lgq
 * Date: 2018/4/3 0003 15:15
 */

public class FileUtil {

    public static void createFolder(String strPath) {
        File file = new File(strPath);
        if (!file.exists())
            file.mkdirs();
    }
}

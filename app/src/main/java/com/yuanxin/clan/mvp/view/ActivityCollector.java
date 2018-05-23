package com.yuanxin.clan.mvp.view;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;

import com.yuanxin.clan.mvp.utils.UIUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Author: jack
 * Description:管理所有的栈中的Activity
 */
public class ActivityCollector {

    /**
     * 存放activity的列表
     */
    public static HashMap<String, Activity> activities = new LinkedHashMap<>();

    /**
     * 添加Activity
     *
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.put(UIUtils.getKeyForActivity(activity), activity);
    }

    /**
     * 判断一个Activity 是否存在
     *
     * @param str
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static <T extends Activity> boolean isActivityExist(String str) {
        boolean res;
        Activity activity = getActivity(str);
        if (activity == null) {
            res = false;
        } else {
            if (activity.isFinishing() || activity.isDestroyed()) {
                res = false;
            } else {
                res = true;
            }
        }

        return res;
    }

    /**
     * 获得指定activity实例
     *
     * @param str Activity 的类对象
     * @return
     */
    public static <T extends Activity> T getActivity(String str) {
        return (T) activities.get(str);
    }

    /**
     * 移除activity,代替finish
     *
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        if (activities.containsValue(activity)) {
            // 利用当前Activity 的class以及 hashcode 拼接出key(保证该Activity实例唯一性)
            activities.remove(UIUtils.getKeyForActivity(activity));
        }
    }

    /**
     * 移除所有的Activity
     */
    public static void removeAllActivity() {
        if (activities != null && activities.size() > 0) {
            Set<Entry<String, Activity>> sets = activities.entrySet();
            for (Entry<String, Activity> s : sets) {
                if (!s.getValue().isFinishing()) {
                    s.getValue().finish();
                }
            }
        }
        activities.clear();
    }
}
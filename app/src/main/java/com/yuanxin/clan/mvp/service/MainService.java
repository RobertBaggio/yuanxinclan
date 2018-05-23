package com.yuanxin.clan.mvp.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yuanxin.clan.mvp.model.BaseVo;
import com.yuanxin.clan.mvp.network.ResponseListener;


public class MainService extends IntentService implements ResponseListener<BaseVo> {
    private static final String ACTION_CHECK_PATCH_UPDATE = "com.easyder.mvp.service.action.ACTION_CHECK_PATCH_UPDATE";
    private static final String ACTION_CHECK_APK_UPDATE = "com.easyder.mvp.service.action.ACTION_CHECK_APK_UPDATE";
    private static final String ACTION_UPDATE_USER_PROFILE = "com.easyder.mvp.service.action.ACTION_UPDATE_USER_PROFILE";


    public MainService() {
        super("MainService");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if (ACTION_CHECK_PATCH_UPDATE.equals(action)) {
                handleActionPatchUpdate();
            } else if (ACTION_CHECK_APK_UPDATE.equals(action)) {
                handleActionApkUpdate();
            } else {
                handleUserProfileUpdate(intent);
            }
        }
    }


    public static void startActionCheckPatchUpdate(Context context) {
        Intent intent = new Intent(context, MainService.class);
        intent.setAction(ACTION_CHECK_PATCH_UPDATE);
        context.startService(intent);
    }

    public static void startActionApkUpdate(Context context) {
        Intent intent = new Intent(context, MainService.class);
        intent.setAction(ACTION_CHECK_APK_UPDATE);
        context.startService(intent);
    }

    public static void startUpdateUserProfile(Context context) {
        Intent intent = new Intent(context, MainService.class);
        intent.setAction(ACTION_UPDATE_USER_PROFILE);
        context.startService(intent);
    }


    private void handleUserProfileUpdate(Intent intent) {

    }

    private void handleActionPatchUpdate() {

    }

    private void handleActionApkUpdate() {
        // TODO: 2016/4/21 检查更新，处理更新
    }


    private void downloadAndInstallPath() {

    }


    @Override
    public void onSuccess(BaseVo dataVo) {

    }

    @Override
    public void onError(BaseVo dataVo) {

    }
}

package com.yuanxin.clan.mvp.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.yuanxin.clan.R;
import com.yuanxin.clan.core.util.MyShareUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * ProjectName: yuanxinclan
 * Describe:
 */

public class UpdateAppService extends Service {
    // 标题
    private int titleId;

    //版本
    public static final String APK_VERSION="APK_VERSION";

    // 文件存储
    private File updateDir = null;
    private File updateFile = null;

    // 通知栏
    private NotificationManager updateNotificationManager = null;
    private Notification updateNotification = null;

    private String strAppUrl;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        // 获取传值
        titleId = intent.getIntExtra("titleId", 0);
        String strVersion=intent.getStringExtra(APK_VERSION);
        strAppUrl = MyShareUtil.getSharedString(R.string.APP_UPDATE_URL);
        // 创建文件
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            updateDir = new File(Environment.getExternalStorageDirectory(), "data/com.yuanxin.clan");
            updateFile = new File(updateDir.getPath(), getResources().getString(titleId)  +"_V"+strVersion+".apk");
        }
        this.updateNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        this.updateNotification = new Notification();
        this.updateNotification.flags = Notification.FLAG_AUTO_CANCEL;

        // 设置下载过程中，点击通知栏，回到主界面

        // 设置通知栏显示内容
        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        // installIntent.setDataAndType(uri,
        // "application/vnd.android.package-archive");
//		PendingIntent Pendingintent = PendingIntent.getActivity(UpdateAppService.this, 0, installIntent, 0);
//        updateNotification.icon = R.drawable.ic_launcher;
//        updateNotification.tickerText = "开始下载";

//        Notification.Builder builder = new Notification.Builder(this);//新建Notification.Builder对象
//        PendingIntent tintent = PendingIntent.getActivity(UpdateAppService.this, 0, new Intent(Intent.ACTION_VIEW), 0);
//        builder.setContentTitle("开始下载");
//        builder.setContentText( "圆心部落  0%");
//        builder.setSmallIcon(R.mipmap.app);
//        builder.setContentIntent(tintent);//执行intent
//        updateNotification = builder.getNotification();//将builder对象转换为普通的notification

//        updateNotification.setLatestEventInfo(this, getApplicationContext()
//                .getResources().getString(R.string.app_name), "0%", null);
//        // 发出通知
//        updateNotificationManager.notify(0, updateNotification);
        // 开启一个新的线程下载，如果使用Service同步下载，会导致ANR问题，Service本身也会阻塞
        new Thread(new UpdateRunner()).start();

        return super.onStartCommand(intent, flags, startId);
    }

    private Handler updateHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    // 自动安装新版本
                    Log.e("自动安装新版本", updateFile.getName());
                    updateNotificationManager.cancel(0);
                    Intent installIntent = new Intent();
                    installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    installIntent.setAction(Intent.ACTION_VIEW);
                    installIntent.setDataAndType(Uri.fromFile(updateFile), "application/vnd.android.package-archive");
                    startActivity(installIntent);

                    // 停止服务
                    stopSelf();
                    break;
                case 0:
                    // 下载失败
//                    Notification.Builder builder = new Notification.Builder(this);//新建Notification.Builder对象
//                    PendingIntent  tintent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
//                    builder.setContentTitle("开始下载");
//                    builder.setContentText( "圆心部落  0%");
//                    builder.setSmallIcon(R.mipmap.app);
//                    builder.setContentIntent(tintent);//执行intent
//                    updateNotification = builder.getNotification();//将builder对象转换为普通的notification
//                    updateNotification.setLatestEventInfo(UpdateAppService.this, getApplicationContext().getResources().getString(R.string.app_name), "下载失败", null);
//                    updateNotificationManager.notify(0, updateNotification);
                    stopSelf();
                    break;
                default:
                    stopSelf();
            }
        }
    };

    class UpdateRunner implements Runnable {
        Message message = updateHandler.obtainMessage();

        @Override
        public void run() {
            // TODO Auto-generated method stub
            message.what = 1;
            try {
                if (!updateDir.exists()) {
                    updateDir.mkdirs();
                }
                if (!updateFile.exists()) {
                    updateFile.createNewFile();
                }
                Log.v("lgq下载地址", strAppUrl);
                long downloadSize = downloadUpdateFile(strAppUrl, updateFile);
                if (downloadSize > 0) {
                    // 下载成功
                    updateHandler.sendMessage(message);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message.what = 0;
                // 下载失败
                updateHandler.sendMessage(message);
            }
        }

    }

    public long downloadUpdateFile(String downloadUrl, File saveFile)
            throws Exception {
        int downloadCount = 0;
        int currentSize = 0;
        long totalSize = 0;
        int updateTotalSize = 0;
        PendingIntent Pendingintent = PendingIntent.getActivity(UpdateAppService.this, 0, new Intent(Intent.ACTION_VIEW), 0);

        HttpURLConnection httpConnection = null;
        InputStream is = null;
        FileOutputStream fos = null;

        try {
            URL url = new URL(downloadUrl);
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestProperty("User-Agent", "PacificHttpClient");
            if (currentSize > 0) {
                httpConnection.setRequestProperty("RANGE", "bytes=" + currentSize + "-");
            }
            httpConnection.setConnectTimeout(10000);
            httpConnection.setReadTimeout(20000);
            updateTotalSize = httpConnection.getContentLength();
            if (httpConnection.getResponseCode() == 404) {
                throw new Exception("fail!");
            }
            is = httpConnection.getInputStream();
            fos = new FileOutputStream(saveFile, false);
            byte buffer[] = new byte[4096];
            int readsize = 0;
            int persent = 0;
            while ((readsize = is.read(buffer)) > 0) {
                fos.write(buffer, 0, readsize);
                totalSize += readsize;
                persent = (int) totalSize * 100 / updateTotalSize;
                if ((downloadCount == 0) || persent - 1 > downloadCount) {
                    Notification.Builder builder = new Notification.Builder(this);//新建Notification.Builder对象
                    PendingIntent Pendingintentt = PendingIntent.getActivity(UpdateAppService.this, 0, new Intent(Intent.ACTION_VIEW), 0);
                    builder.setContentTitle("开始下载");
                    builder.setContentText( "正在下载"+ persent + "%");
                    builder.setSmallIcon(R.mipmap.app);
                    builder.setContentIntent(Pendingintentt);//执行intent
                    updateNotification = builder.getNotification();//将builder对象转换为普通的notification

                    Log.i("lgqq","body=====MyServiceTestActivity====="+persent);
//                    updateNotification.setLatestEventInfo(UpdateAppService.this, "正在下载", persent >= 0 ? persent + "%" :"努力下载中~", null);
                    downloadCount += 1;
                    Log.v("lgq", persent + "%");
                    updateNotificationManager.notify(0, updateNotification);
                }
            }
        } finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
            if (is != null) {
                is.close();
            }
            if (fos != null) {
                fos.close();
            }
        }
        return totalSize;
    }

}

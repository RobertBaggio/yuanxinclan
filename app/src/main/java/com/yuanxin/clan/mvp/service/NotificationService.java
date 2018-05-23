package com.yuanxin.clan.mvp.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotificationService extends Service {
    private static final int NOTIFY_ID = 0;
    private int progress;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder buider;

    // 返回的安装包url
    private String apkUrl;
    /* 下载包安装路径 */
    private static final String savePath = Environment.getExternalStorageDirectory() + File.separator;

    private static final String saveFileName = savePath + "BianLiDianUpdate_" + new SimpleDateFormat("yyyy_MM_dd").format(new Date()) + ".apk";

    private Context mContext = this;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    int rate = msg.arg1;
                    buider.setContentText("正在下载更新中...").setProgress(100, rate, false);
                    // Displays the progress bar for the first time.
                    mNotificationManager.notify(NOTIFY_ID, buider.build());
                    // 下载完毕后变换通知形式
                    if (rate == 100) {
//                        contentview.setTextViewText(R.id.name, "下载完成");
//                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        // When the loop is finished, updates the notification
                        buider.setContentText("下载完成").setProgress(0, 0, true);
                        Notification mNotification = buider.build();
                        mNotification.flags = Notification.FLAG_AUTO_CANCEL;
                        // Removes the progress bar
                        mNotificationManager.notify(NOTIFY_ID, mNotification);
                        mNotificationManager.cancel(NOTIFY_ID);
                    }
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        apkUrl = intent.getStringExtra("url");
        setUpNotification();
        downloadApk();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 创建通知
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setUpNotification() {
        buider = new NotificationCompat.Builder(this);
//        buider.setSmallIcon(R.drawable.list_into); // 设置状态栏中的小图片，尺寸一般建议在24×24， 这里也可以设置大图标
        buider.setTicker("便力电升级");// 设置显示的提示文字
        buider.setContentTitle("便力电升级");// 设置显示的标题
        buider.setContentText("开始下载");// 消息的详细内容
        buider.setFullScreenIntent(null, true);
        //.setNumber(1) // 在TextView的右方显示的数字，可以在外部定义一个变量，点击累加setNumber(count),这时显示的和
        buider.getNotification(); // 需要注意build()是在API level16及之后增加的，在API11中可以使用getNotificatin()来代替 notify.flags |= Notification.FLAG_AUTO_CANCEL;

        Notification mNotification = buider.build();
        // 放置在"正在运行"栏目中
        mNotification.flags = Notification.FLAG_ONGOING_EVENT;
        //设置优先级
        // 指定内容意图
        mNotificationManager.notify(NOTIFY_ID, mNotification);
    }

    private void downloadApk() {
        AsyncTask.execute(mdownApkRunnable);
    }

    /**
     * 安装apk
     */
    private void installApk() {
        File apkFile = new File(saveFileName);
        if (!apkFile.exists()) {
            return;
        }

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);

        stopSelf();// 停掉服务自身
    }

    private int lastRate = 0;
    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                int length = conn.getContentLength();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(conn.getInputStream());

                File file = new File(savePath);
                if (!file.exists()) {
                    file.mkdirs();
                }

                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(ApkFile));
                int count = 0;
                byte buf[] = new byte[1024];

                Message msg;

                int numread = bufferedInputStream.read(buf);
                while (numread > 0) {
                    count += numread;
                    progress = (int) (((float) count / length) * 100);
                    // 更新进度
                    msg = mHandler.obtainMessage();
                    msg.what = 1;
                    msg.arg1 = progress;

                    if (progress >= lastRate + 1) {
                        mHandler.sendMessage(msg);
                        lastRate = progress;
                    }

                    bufferedOutputStream.write(buf, 0, numread);
                    numread = bufferedInputStream.read(buf);
                }

                bufferedOutputStream.close();
                bufferedInputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 下载完成通知安装
            installApk();
        }
    };

}

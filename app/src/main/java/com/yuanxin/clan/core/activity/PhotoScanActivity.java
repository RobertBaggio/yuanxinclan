package com.yuanxin.clan.core.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.huanxin.BaseActivity;
import com.yuanxin.clan.core.huanxin.UserProfileActivity;
import com.yuanxin.clan.core.zxing.DecodeImageCallback;
import com.yuanxin.clan.core.zxing.DecodeImageThread;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * ProjectName: yuanxinclan
 *图片预览
 */

public class PhotoScanActivity extends BaseActivity implements View.OnClickListener {

    private PhotoView mPhotoView;
    private String img;
    private Bitmap bitmap;
    private LinearLayout backli,saveli;
    /**
     * 默认文件保存路径
     */
    private static final String SAVE_PIC_PATH = Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)
            ? Environment.getExternalStorageDirectory().getAbsolutePath() : "/mnt/sdcard";//保存到SD卡
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH + "/YXBL/savePic";//保存的确切位置

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_scan);
        init();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                mPhotoView.setImageBitmap(bitmap);
            }
        }
    };

    private void init() {
        mPhotoView = (PhotoView) findViewById(R.id.iv_photo);
        mPhotoView.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                onBackPressed();
            }
        });
        backli = (LinearLayout)findViewById(R.id.backacli) ;
        backli.setOnClickListener(this);
        saveli =(LinearLayout)findViewById(R.id.addli_right_layout);
        saveli.setOnClickListener(this);
        img = getIntent().getStringExtra("image");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new Thread(runnable).start();

        mPhotoView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ShowDialog();
                return true;
            }
        });
    }

    //更新dialog
    public void ShowDialog() {
        final Dialog dialog;
        LayoutInflater inflater = LayoutInflater.from(PhotoScanActivity.this);
        View view = inflater.inflate(R.layout.open_image_options_dialog, null);
        dialog = new Dialog(PhotoScanActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialogWindow.setWindowAnimations(R.style.AnimViewshow);

//        dialogWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
//        dialogWindow.setGravity(Gravity.CENTER);

//        lp.width = 900; // 宽度
//        lp.height = 650; // 高度
//        lp.alpha = 0.7f; // 透明度

        dialogWindow.setAttributes(lp);

        dialog.setCanceledOnTouchOutside(true);
        LinearLayout saveImg = (LinearLayout) view.findViewById(R.id.saveImg);
        LinearLayout getQr = (LinearLayout) view.findViewById(R.id.getQrCode);
        TextView dismiss = (TextView)view.findViewById(R.id.dissmissDialog);

        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    saveFile(bitmap, System.currentTimeMillis() + ".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
            }
        });
        getQr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new DecodeImageThread(PhotoScanActivity.this, img, new DecodeImageCallback() {
                    @Override
                    public void decodeSucceed(Result result) {
                        if (result.getText().length()==11){
                            Intent intent = new Intent();
                            intent.putExtra("userId",result.getText());
                            intent.putExtra("sm",1);
                            intent.setClass(PhotoScanActivity.this, UserProfileActivity.class);
                            startActivity(intent);
                            return;
                        }
                            Intent intent = new Intent(PhotoScanActivity.this, ImgScanWebActivity.class);
                            intent.putExtra("url", result.getText());
                            startActivity(intent);
                            return;
                    }

                    @Override
                    public void decodeFail(int type, String reason) {
                        ToastUtil.showError(PhotoScanActivity.this, reason, Toast.LENGTH_SHORT);
                    }
                }).run();
            }
        });
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            try {
                bitmap = ((BitmapDrawable) loadImageFromUrl(img)).getBitmap();
//                bitmap = Glide.with(PhotoScanActivity.this)
//                        .load(img)
//                        .asBitmap()
//                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
//                        .get();
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private static Drawable loadImageFromUrl(String url) throws IOException {
        URL m = new URL(url);
        InputStream i = (InputStream) m.getContent();
        return Drawable.createFromStream(i, "src");
    }
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
    //保存方法
    private void saveFile(Bitmap bm, String fileName) throws IOException {
        String subForder = SAVE_REAL_PATH;
        File foder = new File(subForder);
        if (!foder.exists()) foder.mkdirs();

        File myCaptureFile = new File(subForder, fileName);
        Log.e("lgq","图片保持。。。。wwww。。。。"+myCaptureFile);
        if (!myCaptureFile.exists()) myCaptureFile.createNewFile();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        bos.flush();
        bos.close();
        ToastUtil.showSuccess(getApplicationContext(), "已保存在/good/savePic目录下", Toast.LENGTH_SHORT);
        //发送广播通知系统
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        this.sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addli_right_layout:
                try {
                    saveFile(bitmap, System.currentTimeMillis() + ".jpg");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.backacli:
                onBackPressed();
                break;
            default:
                break;
        }
    }

}

package com.yuanxin.clan.core.zxing;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.activity.HengpingActivity;
import com.yuanxin.clan.core.activity.QiandaoActivity;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.event.QiandaoEvent;
import com.yuanxin.clan.core.http.Url;
import com.yuanxin.clan.core.huanxin.UserProfileActivity;
import com.yuanxin.clan.core.util.Logger;
import com.yuanxin.clan.core.util.QiandaoStatus;
import com.yuanxin.clan.core.util.TextUtil;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.view.BaseActivity;

import org.apache.http.Header;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

//扫码类
public class ScanQrCodeFragment extends SubBasicFragment implements SurfaceHolder.Callback {

    private CaptureActivityHandler handler;
    private com.yuanxin.clan.mvp.view.ViewfinderView viewfinderView;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private SubActivity context;
    private SurfaceView surfaceView;
    private String activityDrawId,activityDrawSignUserId;
    private AsyncHttpClient client = new AsyncHttpClient();

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化 CameraManager
        context = (SubActivity) getActivity();
        CameraManager.init(context.getApplication());
        hasSurface = false;
        inactivityTimer = new InactivityTimer(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.scan_qrcode_fragment, container, false);
//        contentView = inflater.inflate(R.layout.saoma_two_layout, container, false);
        viewfinderView = (com.yuanxin.clan.mvp.view.ViewfinderView) contentView.findViewById(R.id.viewfinder_view);
        surfaceView = (SurfaceView) contentView.findViewById(R.id.preview_view);
//        TextView textView = (TextView)contentView.findViewById(R.id.sfsdtextView4);
//        textView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mediaPlayer.start();
//            }
//        });
        return contentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }


    private void refresh() {
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        playBeep = true;
        AudioManager audioService = (AudioManager) context.getSystemService(context.AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    public void onDestroy() {
        inactivityTimer.shutdown();
        client.cancelAllRequests(true);
        super.onDestroy();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    public com.yuanxin.clan.mvp.view.ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();

        // 二维码内容判断及回调数据传送 userId
//        http://192.168.1.106:8080/yuanxinbuluo/jsp/yxblGames/hengping/Dadishu/index.html?activityDrawId=10000000663856267&activityDrawDetailedId=10000000959371877
//        http://192.168.1.106:8080/yuanxinbuluo/jsp/yxblGames/hengping/Dadishu/index.html
        String strQr = obj.getText();
        Log.v("lgq","saomao==--"+strQr);
//        activityDrawSignUserId =TextUtil.URLRequest(strQr).get("activityDrawDetailedId");
        activityDrawId =TextUtil.URLRequest(strQr).get("activityDrawId");
        if (strQr.indexOf("yxblGames") > -1){
            if (strQr.indexOf("shuping") > -1){
                if (strQr.contains("?")){
                    getqiandaocontentif(strQr,"sp");
//                    getActivity().finish();
                }else {
                    startActivity(new Intent(getContext(), HengpingActivity.class).putExtra("url", strQr).putExtra("hs","sp"));
                    getActivity().finish();
                }

//                startActivity(new Intent(getContext(), HengpingActivity.class).putExtra("url", strQr).putExtra("hs","sp"));
            }else {
                if (strQr.contains("?")){
                    getqiandaocontentif(strQr,"hp");
//                    getActivity().finish();
                }else {
                    startActivity(new Intent(getContext(), HengpingActivity.class).putExtra("url", strQr).putExtra("hs","hp"));
                    getActivity().finish();
                }
//                getqiandaocontentif(strQr,"hp");
//                startActivity(new Intent(getContext(), HengpingActivity.class).putExtra("url", strQr).putExtra("hs","hp"));
            }
            return;
        }
//        if (strQr.indexOf("scanCodeSign")>-1){
////            startActivity(new Intent(getContext(), HengpingActivity.class).putExtra("url", strQr).putExtra("hs","sp"));
//            startActivity(new Intent(getContext(), HomeADactivity.class).putExtra("url", strQr+"&userId="+ UserNative.getId()));
//            getActivity().finish();
//
//            return;
//        }

        final String epId = TextUtil.URLRequest(strQr).get("id");

        if (!TextUtil.isEmpty(epId)){
            String zl ;
            if (strQr.indexOf("scanCodeSign")>-1){
                zl="no";
                getQiandaoStatus(strQr);
                return;
            }else {
                zl="ys";
            }
            Intent intent = new Intent();
            intent.putExtra("id",epId);
            intent.putExtra("zl",zl);
            intent.putExtra("url",strQr);
            intent.setClass(getActivity(), QiandaoActivity.class);
            startActivity(intent);
            getActivity().finish();
            return;
        }
        if (strQr.length()==11){
            Intent intent = new Intent();
            intent.putExtra("userId",strQr);
            intent.putExtra("sm",1);
            intent.setClass(getActivity(), UserProfileActivity.class);
            startActivity(intent);
            getActivity().finish();
        }else if (strQr.indexOf("http") > -1) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(strQr));
            startActivity(webIntent);
            getActivity().finish();
        }

    }

    private void getQiandaoStatus(final String url) {
        RequestParams params = new RequestParams();
        params.put("userId", UserNative.getId());//企业id
        doHttpGet(url, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");//设置日期格式
                    String current = df.format(new Date());
                    if (object.getString("success").equals("true")) {
                        try {
                            JSONObject ob = object.getJSONObject("data");
                            String status = ob.getString("url");
                            String imgUrl = "";
                            switch (status) {
                                // 重复签到
                                case QiandaoStatus.SIGN_REPEAT:
                                    imgUrl = "http://images.yxtribe.com/tosp_yi_qian_dao@3x.png";
                                    break;
                                // 签到时间未到
                                case QiandaoStatus.SIGN_TIME_NOT_ARRIVED:
                                    imgUrl = "http://images.yxtribe.com/tosp_wei_kai_shi@3x.png";
                                    break;
                                // 不是商圈成员
                                case QiandaoStatus.SIGN_NOT_BUSINESS:
                                    imgUrl = "http://images.yxtribe.com/tosp_fei_qi_ye_cheng_yuan@3x.png";
                                    break;
                                // 二维码失效
                                case QiandaoStatus.INVALID:
                                    imgUrl = "http://images.yxtribe.com/tosp_qian_dao_shi_bai@3x.png";
                                    break;
                                // 已签退
                                case QiandaoStatus.SIGN_OUT:
                                    imgUrl = "http://images.yxtribe.com/tosp_qian_tui@3x.png";
                                    break;
                                default:
                                    // 签到成功
                                    imgUrl = "http://images.yxtribe.com/tosp_ti_shi@3x.png";
                                    break;
                            }
                            EventBus.getDefault().post(new QiandaoEvent(url, imgUrl));
                            getActivity().finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ToastUtil.showWarning(getContext(), object.getString("msg"), Toast.LENGTH_LONG);
                    }
                } catch (JSONException e) {
//                    Toast.makeText(UserProfileActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }

    private String getShortKey(String key, String QRtext) {
        int Start;
        int end;
        Start = QRtext.indexOf(key) + key.length();
        end = QRtext.length();
        QRtext = QRtext.substring(Start, end);
        return QRtext;
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            context.setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    // 声音、震动
    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() { // 声音
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    private void getqiandaocontentif(final String strQurl, final String ss) {
        RequestParams params = new RequestParams();
        params.put("activityDrawSignUserId", UserNative.getId());//企业id
        params.put("activityDrawId", activityDrawId);//企业id
        String testurl = "http://192.168.1.106:8080/yuanxinbuluo"+"/business/draw/isSign";
//        doHttpGet(testurl, params, new BaseActivity.RequestCallback() {
        doHttpGet(Url.isSign, params, new BaseActivity.RequestCallback() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                ToastUtil.showWarning(getContext(), "网络连接异常", Toast.LENGTH_SHORT);
            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    Log.v("lgq","......"+s);
                    if (object.getString("success").equals("true")) {
                        if (ss.equals("hp")){
                            String p = object.getString("data");
                            startActivity(new Intent(getContext(), HengpingActivity.class).putExtra("url", strQurl+"&"+p).putExtra("hs","hp"));
                            getActivity().finish();

                        }else {
                            String p = object.getString("data");
                            startActivity(new Intent(getContext(), HengpingActivity.class).putExtra("url", strQurl+"&"+p).putExtra("hs","sp"));
                            getActivity().finish();

                        }

                    } else {
                        ToastUtil.showInfo(getContext(),"您未签到！请前往签到", Toast.LENGTH_LONG);
                        getActivity().finish();

                    }
                } catch (JSONException e) {
//                    Toast.makeText(UserProfileActivity.this, "数据解析出错", Toast.LENGTH_SHORT).show();
                    Logger.e("数据解析出错");
                }
            }
        });

    }


    protected void doHttpGet(String url, RequestParams params, final BaseActivity.RequestCallback c) {
        String aesKes = UserNative.getAesKes();
//        if (TextUtil.isEmpty(aesKes)) {//测试1》2
//            ViewUtils.AlertDialog(getContext(), "提示", "登陆信息失效，请重新登陆", "确定", "取消", new ViewUtils.DialogCallback() {
//                @Override
//                public void onConfirm() {
//                    Intent intent = new Intent(getContext(), LoginActivity.class);
//                    startActivity(intent);
//                }
//
//                @Override
//                public void onCancel() {
//
//                }
//            });
//        } else {
            params.put("key", aesKes);
            client.get(url, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
//                Toast.makeText(getApplicationContext(), "网络连接异常", Toast.LENGTH_SHORT).show();
                    c.onFailure(i, headers, s, throwable);
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    try {
                        c.onSuccess(i, headers, s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
//        }
    }

    @Override
    public void releaseRes() {
        handler = null;
        viewfinderView = null;
        decodeFormats = null;
        characterSet = null;
        inactivityTimer = null;
        mediaPlayer = null;
        context = null;
        surfaceView = null;
    }
}

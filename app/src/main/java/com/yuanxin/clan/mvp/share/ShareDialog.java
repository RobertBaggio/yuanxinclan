package com.yuanxin.clan.mvp.share;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.yuanxin.clan.R;
import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.huanxin.ForwardMessageActivity;
import com.yuanxin.clan.mvp.utils.ToastUtil;
import com.yuanxin.clan.mvp.utils.UIUtils;

import java.util.EnumMap;
import java.util.Map;

import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

/**
 * @author lch
 *         date 2015/9/18.
 */
public class ShareDialog extends DialogFragment {
    private View.OnClickListener onClickListener;
    private ShareInfoVo shareInfoVo;
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;
    private static final int REQUEST_CODE_SHARE_QILIAO = 1;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        shareInfoVo = getArguments().getParcelable("shareInfoVo");
        onClickListener = new ShareClickListener();

        Dialog shareDlg = new Dialog(getActivity(), R.style.WhiteDialog);
        Window window = shareDlg.getWindow();
        window.setContentView(R.layout.dialog_share);
        window.findViewById(R.id.lyWechat).setOnClickListener(onClickListener);
        window.findViewById(R.id.lyWechatmoments).setOnClickListener(onClickListener);
        window.findViewById(R.id.lyQq).setOnClickListener(onClickListener);
        window.findViewById(R.id.lyQzone).setOnClickListener(onClickListener);
        window.findViewById(R.id.lyQiliao).setOnClickListener(onClickListener);
        window.findViewById(R.id.lyWeibo).setOnClickListener(onClickListener);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        if (!UserNative.readIsLogin()){
            window.findViewById(R.id.lyQiliao).setVisibility(View.INVISIBLE);
//            params.height = (int) (getActivity().getWindowManager().getDefaultDisplay().getHeight() * 0.14);
        }else {
//            params.height = (int) (getActivity().getWindowManager().getDefaultDisplay().getHeight() * 0.2);
        }

        window.setGravity(Gravity.BOTTOM);
        window.setAttributes(params);
        return shareDlg;
    }

    /**
     * 创建默认的分享页面
     *
     * @param fragmentManager
     * @param shareInfoVo
     * @return
     */
    public static ShareDialog showShareDialog(FragmentManager fragmentManager, ShareInfoVo shareInfoVo) {
        ShareDialog shareDialog = new ShareDialog();
        Bundle bundle = new Bundle();
        bundle.putParcelable("shareInfoVo", shareInfoVo);
        shareDialog.setArguments(bundle);
        shareDialog.show(fragmentManager, "shareDialog");
        return shareDialog;
    }

    public Bitmap encodeAsBitmap(String contents) {
        String contentsToEncode = contents;
        if (contentsToEncode == null) {
            return null;
        }
        Map<EncodeHintType, Object> hints = null;
        String encoding = guessAppropriateEncoding(contentsToEncode);
        if (encoding != null) {
            hints = new EnumMap<EncodeHintType, Object>(EncodeHintType.class);
            hints.put(EncodeHintType.CHARACTER_SET, encoding);
        }
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(contentsToEncode, BarcodeFormat.QR_CODE, 400, 400, hints);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    /**
     * 在二维码中间添加Logo图案
     */
    private static Bitmap addLogo(Bitmap src, Bitmap logo) {
        if (src == null) {
            return null;
        }

        if (logo == null) {
            return src;
        }

        //获取图片的宽高
        int srcWidth = src.getWidth();
        int srcHeight = src.getHeight();
        int logoWidth = logo.getWidth();
        int logoHeight = logo.getHeight();

        if (srcWidth == 0 || srcHeight == 0) {
            return null;
        }

        if (logoWidth == 0 || logoHeight == 0) {
            return src;
        }

        //logo大小为二维码整体大小的1/5
        float scaleFactor = srcWidth * 1.0f / 6 / logoWidth;
        Bitmap bitmap = Bitmap.createBitmap(srcWidth, srcHeight, Bitmap.Config.ARGB_8888);
        try {
            Canvas canvas = new Canvas(bitmap);
            canvas.drawBitmap(src, 0, 0, null);
            canvas.scale(scaleFactor, scaleFactor, srcWidth / 2, srcHeight / 2);
            canvas.drawBitmap(logo, (srcWidth - logoWidth) / 2, (srcHeight - logoHeight) / 2, null);

            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.restore();
        } catch (Exception e) {
            bitmap = null;
            e.getStackTrace();
        }

        return bitmap;
    }

    private static String guessAppropriateEncoding(CharSequence contents) {
        // Very crude at the moment
        for (int i = 0; i < contents.length(); i++) {
            if (contents.charAt(i) > 0xFF) {
                return "UTF-8";
            }
        }
        return null;
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    class ShareClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int viewId = v.getId();
            if (shareInfoVo == null) {
                ToastUtil.showLong(UIUtils.getContext(), "分享失败！");
                return;
            }
//              二维码分享
//            if (viewId == R.id.qrcode_share) {
//                View view = inflater.inflate(R.layout.qrcode_share, null);
//                ImageView qrCodeImage = (ImageView) view.findViewById(R.id.qrcode_image);
//                qrCodeImage.setImageBitmap(addLogo(encodeAsBitmap(shareInfoVo.getUrl()),
//                        BitmapFactory.decodeResource(UIUtils.getResources(), R.drawable.icon)));
//                getDialog().setContentView(view);
//                getDialog().show();
//                return;
//            }

            switch (viewId) {
                case R.id.lyWechat:
                    shareInfoVo.setPlatformName(Wechat.NAME);
                    ShareUtil.share(shareInfoVo, getActivity());
                    break;
                case R.id.lyWechatmoments:
                    shareInfoVo.setPlatformName(WechatMoments.NAME);
                    ShareUtil.share(shareInfoVo, getActivity());
                    break;
                case R.id.lyQq:
                    shareInfoVo.setPlatformName(QQ.NAME);
                    ShareUtil.share(shareInfoVo, getActivity());
                    break;
                case R.id.lyQzone:
                    shareInfoVo.setPlatformName(QZone.NAME);
                    ShareUtil.share(shareInfoVo, getActivity());
                    break;
                case R.id.lyQiliao:
                    //分享至企聊
                    startActivityForResult(new Intent(getActivity(), ForwardMessageActivity.class).putExtra("share_info", shareInfoVo), REQUEST_CODE_SHARE_QILIAO);
                    break;
                case R.id.lyWeibo:
                    shareInfoVo.setPlatformName(SinaWeibo.NAME);
                    ShareUtil.share(shareInfoVo, getActivity());
//                    SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
//                    sp.setText(shareInfoVo.getContent());
//                    sp.setImageUrl(shareInfoVo.getImgUrl());
//                    Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//                    weibo.setPlatformActionListener(new PlatformActionListener() {
//                        @Override
//                        public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                            ToastUtil.showLong(UIUtils.getContext(), "分享成功！");
//                        }
//
//                        @Override
//                        public void onError(Platform platform, int i, Throwable throwable) {
//                            ToastUtil.showLong(UIUtils.getContext(), "分享失败！");
//                        }
//
//                        @Override
//                        public void onCancel(Platform platform, int i) {
//                            ToastUtil.showLong(UIUtils.getContext(), "取消分享！");
//                        }
//                    });
//                    weibo.share(sp);
                    break;
            }
            dismiss();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SHARE_QILIAO:
                    if (data != null) {
                        Bundle bundle = data.getBundleExtra("user");
                        String username = bundle.getString("username");
                        String usernick = bundle.getString("usernick");
                    }
                    break;
                default:
                    break;
            }
        }

    }
}

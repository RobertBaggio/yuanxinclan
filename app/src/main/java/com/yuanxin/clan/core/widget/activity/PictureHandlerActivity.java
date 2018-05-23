package com.yuanxin.clan.core.widget.activity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.widget.ImageView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <strong>自定义图像处理</strong><br/>
 * <li>启动相册{@link #"startAlbum"()}</li>
 * <li>启动系统照相机 {@link #"startCamera"()}</li>
 * <li>拍照保存的绝对路径 {@link #getCameraAbsolutePath()}</li>
 * <li>图片剪切配置 {@link #getCrop()}</li>
 * <li>当使用者拍照结束或选择相册图片后会通过此方法返回图片路径 {@link #"resultPhotoPath"(String photoPath)}</li>
 *
 * @author 朱朝旭
 */
public abstract class PictureHandlerActivity extends CommonActivity {
    /**
     * 相册返回标识
     */
    public static final int FOR_ALBUM = 4001;

    /**
     * 照相机返回标识
     */
    public static final int FOR_CAMERA = 4002;

    /**
     * 切图返回标识
     */
    public static final int FOR_CROP = 4003;

    /**
     * 摄像机返回标识
     */
    public static final int FOR_VIDEO = 4004;

    /**
     * 拍照绝对路径，裁剪绝对路径
     */
    private Uri cameraUri, cropUri, videoUri;

    private ImageView pictureView;

    /**
     * 启动相册
     * <p>
     * 绝对路径
     */
    public void startAlbum(ImageView view) {
        pictureView = view;
        Intent intent = new Intent();//ACTION_OPEN_DOCUMENT
        intent = new Intent(Intent.ACTION_PICK,
                null);
        intent.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                "image/*");
        startActivityForResult(intent, FOR_ALBUM);
    }

    /**
     * 启动系统照相机-照相
     */
    public void startCamera(ImageView view) {
        pictureView = view;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, createCameraAbsoluteUri());
        startActivityForResult(intent, FOR_CAMERA);
    }

    /**
     * 启动系统照相机-摄像
     */
    public void startVideo(ImageView view) {
        pictureView = view;
        Intent intent_video = new Intent("android.media.action.VIDEO_CAPTURE");
        intent_video.putExtra(MediaStore.EXTRA_OUTPUT, createVideoAbsoluteUri());
        startActivityForResult(intent_video, FOR_VIDEO);
    }

    //当图片路径生成时，用接口进行回调
    private OnPicPathListener listener = null;

    public interface OnPicPathListener {
        public void getPicPath(String pic_path);
    }

    ;

    public void setOnPicPathListener(OnPicPathListener listener) {
        this.listener = listener;
    }

    ;


    /**
     * Intent返回值处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case FOR_ALBUM://相簿
                if (getCrop().isCrop()) {
                    //-----------找出相册中图片对应的路径--------------------
//                    Uri uri = data.getData();
//                    String[] proj = { MediaStore.Images.Media.DATA };
//                    Cursor actualimagecursor = managedQuery(uri,proj,null,null,null);
//                    int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//                    actualimagecursor.moveToFirst();
//                    String img_path = actualimagecursor.getString(actual_image_column_index);
//                    startCrop(getCrop().setOriginalUri(data.getData()), img_path);
                    startCrop(getCrop().setOriginalUri(data.getData()), getImagePath(data.getData()));
                } else
                    resultPhotoPath(pictureView, getPath(getApplicationContext(), data.getData()));
                break;
            case FOR_CAMERA://照相机
                if (getCrop().isCrop()) startCrop(getCrop().setOriginalUri(cameraUri), "");
                else resultPhotoPath(pictureView, cameraUri.getPath());
                break;
            case FOR_CROP:
                resultPhotoPath(pictureView, cropUri.getPath());
                listener.getPicPath(cropUri.getPath());
                break;
            case FOR_VIDEO:
                resultVideoPath(pictureView, videoUri.getPath());
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getImagePath(Uri uri) {
        String selectedImagePath;
        // 1:MEDIA GALLERY --- query from MediaStore.Images.Media.DATA
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            selectedImagePath = cursor.getString(column_index);
        } else {
            selectedImagePath = null;
        }

        if (selectedImagePath == null) {
            // 2:OI FILE Manager --- call method: uri.getPath()
            selectedImagePath = uri.getPath();
        }
        return selectedImagePath;
    }


    /**
     * 启动图片裁剪界面
     *
     * @param crop
     */
    private void startCrop(Crop crop, String uri) {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;
        if (!"".equals(uri)) {
            BitmapFactory.decodeFile(uri, newOpts);
        } else {
            BitmapFactory.decodeFile(crop.getOriginalUri().getPath(), newOpts);
        }
        int width = newOpts.outWidth;
        int height = newOpts.outHeight;

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(crop.getOriginalUri(), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("output", createCropUriBy(crop.getOriginalUri()));

        if (width >= height) {
            intent.putExtra("outputX", crop.getOutputX());
            intent.putExtra("outputY", crop.getOutputY());
            intent.putExtra("aspectX", crop.getAspectX());
            intent.putExtra("aspectY", crop.getAspectY());
        } else {
            intent.putExtra("outputX", crop.getOutputY());
            intent.putExtra("outputY", crop.getOutputX());
            intent.putExtra("aspectX", crop.getAspectY());
            intent.putExtra("aspectY", crop.getAspectX());
        }
        intent.putExtra("scale", crop.isScale());
        intent.putExtra("scaleUpIfNeeded", crop.isScaleUpIfNeeded());
        startActivityForResult(intent, FOR_CROP);
    }

    /**
     * 通过uri获取文件的绝对路径
     *
     * @param uri
     * @return
     */
    @SuppressWarnings("deprecation")
    private String getAbsoluteImagePath(Uri uri) {
        String imagePath = "";
        String[] proj =
                {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, proj, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.getCount() > 0 && cursor.moveToFirst()) imagePath = cursor
                    .getString(column_index);
        }
        return imagePath;
    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    private boolean isEmpty(String input) {
        if (input == null || "".equals(input)) return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') return false;
        }
        return true;
    }

    /**
     * 获取文件扩展名
     *
     * @param fileName
     * @return
     */
    private String getFileFormat(String fileName) {
        if (isEmpty(fileName)) return "";
        int point = fileName.lastIndexOf('.');
        return fileName.substring(point + 1);
    }

    /**
     * 判断当前Url是否标准的content://样式，如果不是，则返回绝对路径
     *
     * @param uri
     * @return
     */
    private String getAbsolutePathFromNoStandardUri(Uri uri) {
        String path = null;
        String uriPath = Uri.decode(uri.toString());
        String pre_sdcard = "file:///sdcard/";
        String pre_sdcard_mnt = "file:///mnt/sdcard/";
        if (uriPath.startsWith(pre_sdcard)) path = Environment
                .getExternalStorageDirectory().getPath()
                + File.separator
                + uriPath.substring(pre_sdcard.length());
        else if (uriPath.startsWith(pre_sdcard_mnt)) path = Environment
                .getExternalStorageDirectory().getPath()
                + File.separator
                + uriPath.substring(pre_sdcard_mnt.length());
        return path;
    }

    /**
     * 裁剪后图片的绝对路径
     *
     * @param uri
     * @return
     */
    private Uri createCropUriBy(Uri uri) {
        createAbsoluteFile();
        String thePath = getAbsolutePathFromNoStandardUri(uri);
        if (isEmpty(thePath)) thePath = getAbsoluteImagePath(uri);
        String ext = getFileFormat(thePath);
        cropUri = Uri.fromFile(new File(getCameraAbsolutePath() + "/crop_" + getTime()
                + "." + (isEmpty(ext) ? "jpg" : ext)));
        return cropUri;
    }

    /**
     * 创建拍照文件路径
     *
     * @return
     */
    private Uri createCameraAbsoluteUri() {
        createAbsoluteFile();
        cameraUri = Uri.fromFile(new File(getCameraAbsolutePath() + "/camera_"
                + getTime() + ".jpg"));
        return cameraUri;
    }

    /**
     * 创建摄像文件路径
     *
     * @return
     */
    private Uri createVideoAbsoluteUri() {
        videoAbsoluteFile();
        videoUri = Uri.fromFile(new File(getVideoAbsolutePath() + "/video_"
                + getTime() + ".mp4"));
        return videoUri;
    }

    /**
     * 创建照相根路径
     */
    private void createAbsoluteFile() {
        File file = new File(getCameraAbsolutePath());
        if (!file.exists()) file.mkdirs();
    }

    /**
     * 创建摄像根路径
     */
    private void videoAbsoluteFile() {
        File file = new File(getVideoAbsolutePath());
        if (!file.exists()) file.mkdirs();
    }

    /**
     * 获取时间
     *
     * @return
     */
    private String getTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    /**
     * 拍照保存的绝对路径
     *
     * @return
     */
    protected String getCameraAbsolutePath() {
        return null;
    }

    /**
     * 摄像保存的绝对路径
     *
     * @return
     */
    protected String getVideoAbsolutePath() {
        return null;
    }

    /**
     * 图片剪切配置（重写该方法,Crop对象setCrop参数true时启动剪切功能）
     *
     * @return
     */
    protected Crop getCrop() {
        return new Crop();
    }

    /**
     * 当使用者拍照结束或选择相册图片后会通过此方法返回图片路径
     *
     * @param photoPath 图片路径
     */
    protected void resultPhotoPath(ImageView view, String photoPath) {

    }

    ;

    /**
     * 当使用者摄像结束或选择相册图片后会通过此方法返回图片路径
     *
     * @param photoPath 图片路径
     */
    protected void resultVideoPath(ImageView view, String photoPath) {

    }

    ;

    /**
     * 图片剪切配置
     *
     * @author youdro 朱朝旭
     */
    public class Crop {
        private Uri originalUri;
        private int aspectX = 4, aspectY = 4, outputX = 900, outputY = 900;
        private boolean scale = true, scaleUpIfNeeded = true, isCrop = false;

        public Crop setCrop(boolean isCrop) {
            this.isCrop = isCrop;
            return this;
        }

        public boolean isCrop() {
            return isCrop;
        }

        public Crop setOriginalUri(Uri originalUri) {
            this.originalUri = originalUri;
            return this;
        }

        public Uri getOriginalUri() {
            return originalUri;
        }

        public Crop setAspectX(int aspectX) {
            this.aspectX = aspectX;
            return this;
        }

        public int getAspectX() {
            return aspectX;
        }

        public Crop setAspectY(int aspectY) {
            this.aspectY = aspectY;
            return this;
        }

        public int getAspectY() {
            return aspectY;
        }

        public Crop setOutputX(int outputX) {
            this.outputX = outputX;
            return this;
        }

        public int getOutputX() {
            return outputX;
        }

        public Crop setOutputY(int outputY) {
            this.outputY = outputY;
            return this;
        }

        public int getOutputY() {
            return outputY;
        }

        public Crop setScale(boolean scale) {
            this.scale = scale;
            return this;
        }

        public boolean isScale() {
            return scale;
        }

        public Crop setScaleUpIfNeeded(boolean scaleUpIfNeeded) {
            this.scaleUpIfNeeded = scaleUpIfNeeded;
            return this;
        }

        public boolean isScaleUpIfNeeded() {
            return scaleUpIfNeeded;
        }
    }

    /**
     * 根据url获取图片的路径
     *
     * @param context
     * @param uri
     * @return
     */
    public String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider  
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        docId = DocumentsContract.getDocumentId(uri);
                        final String[] split = docId.split(":");
                        final String type = split[0];

                        if ("primary".equalsIgnoreCase(type)) {
                            return Environment.getExternalStorageDirectory() + "/" + split[1];
                        }
                    }

                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        id = DocumentsContract.getDocumentId(uri);
                        final Uri contentUri = ContentUris.withAppendedId(
                                Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                        return getDataColumn(context, contentUri, null, null);
                    }

                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        docId = DocumentsContract.getDocumentId(uri);
                        final String[] split = docId.split(":");
                        final String type = split[0];
                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }

                        final String selection = "_id=?";
                        final String[] selectionArgs = new String[]{
                                split[1]
                        };

                        return getDataColumn(context, contentUri, selection, selectionArgs);
                    }
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


}

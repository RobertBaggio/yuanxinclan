package com.yuanxin.clan.core.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanxin.clan.core.company.view.CompanyDetailWebActivity;
import com.yuanxin.clan.core.news.view.PhotoBrowserActivity;
import com.yuanxin.clan.mvp.utils.ToastUtil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static long lastClick;

    //判断是否已登录
    /*public static boolean isLogin(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Mconfig.ACCOUNT_IMF,
				Context.MODE_PRIVATE);
		String phone = sp.getString("tel", "");
		String password = sp.getString("password", "");
		if ((!phone.equals("")) && (!password.equals(""))) {
			return true;
		} else {
			return false;
		}

	}*/


    //EditText监听小数点让用户只能输入小数点后两位
    public static void setPricePoint(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

        });

    }

    // 判断手机号码是否规则
    public static boolean isPhoneNumber(String input) {
        String regex = "(1[0-9][0-9]|15[0-9]|18[0-9])\\d{8}";
        Pattern p = Pattern.compile(regex);
        return p.matches(regex, input);
    }

    // 判断两次输入的密码是否一致
    public static boolean checkPassword(String password, String checkPassword) {

        return password.equals(checkPassword);

    }
    public static boolean isConnected(Context context) {
        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = conn.getActiveNetworkInfo();
        return (info != null && info.isConnected());
    }

    // 显示网络连接异常的toast
    public static void showNetErrorToast(Context context) {
//		Toast.makeText(context, "网络连接异常", Toast.LENGTH_SHORT).show();
    }

    // 显示数据解析出错的toast
    public static void showDataErrorToast(Context context) {
        Logger.d("json 解析出错");
    }

    // 显示没有数据的toast
    public static void showNoDataErrorToast(Context context) {
        ToastUtil.showInfo(context, "没有数据", Toast.LENGTH_SHORT);
    }

    // 显示最后一页的toast
    // public static void showLastPageToast(Context context) {
    // Toast.makeText(context, "亲，已经没有更多数据了", Toast.LENGTH_SHORT).show();
    // }

    // 显示短时间的toast
    public static void showShortToast(Context context, String text) {
        ToastUtil.showInfo(context, text, Toast.LENGTH_SHORT);
    }

    public static void showShortToast(Context context, int text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    // 防止多次点击
    public static void stopTooMuchClick() {
        // 大于一秒方个通过
        if (System.currentTimeMillis() - lastClick <= 3000) {
            return;
        }
        lastClick = System.currentTimeMillis();

    }

    public static String TimestampToDate(String time) {
        long timeI = Integer.parseInt(time);
        long temp = timeI * 1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String TimestampToDateDetail(String time) {
        long timeI = Integer.parseInt(time);
        long temp = timeI * 1000;
        Timestamp ts = new Timestamp(temp);
        Date date = new Date();
        try {
            date = ts;
        } catch (Exception e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String changeNullToEmpty(String nullString) {
        return nullString.equals("null") ? "" : nullString;
    }

    public static String changeNullToZero(String nullString) {
        return nullString.equals("null") ? "0" : nullString;
    }

    public static void setTextCuTi(TextView textview) {
        TextPaint tp = textview.getPaint();
        tp.setFakeBoldText(true);// 加粗
    }

    public static void telNumber(Context context, String phone) {
        /*Intent intent = new Intent(Intent.ACTION_CALL,
                Uri.parse("tel:" + phone));
		// 通知activtity处理传入的call服务
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);*/
    }

    public static void showSoftKeyboard(EditText edit, Context context) {
        final InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        } else {
            imm.hideSoftInputFromWindow(edit.getApplicationWindowToken(), 0);
        }

    }

    public static String getCurrentActivityName(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Activity.ACTIVITY_SERVICE);

        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        return componentInfo.getClassName();
    }

    public static void jumpActivity(Context context, Class<?> toClass) {
        Intent intent = new Intent(context, toClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public static void jumpActivity(Context context, Class<?> toClass,
                                    Bundle bundle) {
        Intent intent = new Intent(context, toClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("bundle", bundle);
        context.startActivity(intent);
    }

    public static void jumpActivityForResult(Activity activity,
                                             Class<?> toClass, int requestCode) {
        Intent intent = new Intent(activity, toClass);
        activity.startActivityForResult(intent, requestCode);
    }

    public static void jumpActivityForResult(Activity activity,
                                             Class<?> toClass, Bundle bundle, int requestCode) {
        Intent intent = new Intent(activity, toClass);
        intent.putExtra("bundle", bundle);
        activity.startActivityForResult(intent, requestCode);
    }

    public static Bundle getIntentBundle(Activity activity) {
        Bundle bundle = activity.getIntent().getBundleExtra("bundle");
        return bundle;
    }

    public static int getViewMeasureHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getMeasuredHeight();
        return height;
    }

    public static int getViewMeasureWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width = view.getMeasuredWidth();
        return width;
    }

    public static int getViewHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int height = view.getHeight();
        return height;
    }

    public static int getViewWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        int width = view.getWidth();
        return width;
    }

    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int width = display.getWidth();
        return width;
    }

    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        int height = display.getHeight();
        return height;
    }

    public static String getAppVerson(Context context) {
        PackageManager manager;
        PackageInfo info = null;
        manager = context.getPackageManager();
        try {
            info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "";
    }

    /*
    * 获取当前程序的版本号
    */
    public static int getVersionCode(Context context) throws Exception {
        PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        return info.versionCode;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            View localView = activity.getCurrentFocus();
            if (localView != null && localView.getWindowToken() != null) {
                IBinder windowToken = localView.getWindowToken();
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        }
    }

    // 将字符串转为时间戳
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date d;
        try {

            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re_time;
    }

    // 将字符串转为时间戳
    public static String getTime2(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        Date d;
        try {

            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re_time;
    }

    public static String getTime3(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d;
        try {

            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re_time;
    }

    // 将时间戳转为字符串
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }

    // 将时间戳转为字符串
    public static String getStrTime2(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;

    }

    // 将时间戳转为字符串
    public static String getStrTime3(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time));

        return re_StrTime;

    }

    //根据时间修改时间的显示格式
    public static String timeFormat(String time) {
        String newFormat = null;
        SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy-MM-dd",
                Locale.getDefault());
        Date date = new Date();
        String timeString = dataFormat.format(date);
        String[] timeArray = time.split(",");
        if (timeString.equals(timeArray[0])) {
            String[] time1 = timeArray[1].split(":");
            newFormat = time1[0] + ":" + time1[1];
            return newFormat;
        } else {
            String[] time0 = timeArray[0].split("-");
            newFormat = time0[1] + "月" + time0[2] + "日";
            return newFormat;
        }

    }

    // 将时间戳转为字符串+修改时间格式
    public static String getStrTimeFormat(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd,HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return timeFormat(re_StrTime);

    }

    //金额格式
    public static String moneyFormat(String money) {
        float moneyValue = (float) (Float.parseFloat(money));
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        return decimalFormat.format(moneyValue);//format 返回的是字符串
    }

    //Bitmap对象保存为图片文件
    public static File saveBitmapFile(Bitmap bitmap) {
        //将要保存图片的路径
        String path = Environment.getExternalStorageDirectory() + "/renpay/" + "headImage.jpg";
        File outputImage = new File(path);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputImage));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return outputImage;
    }

    //将Bitmap进行compress处理
    public static String bitmapCompress(Bitmap bitmap, String path) {
        //将要保存图片的路径
        File outputImage = new File(path);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputImage));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }

    //在字符串的每个字符后面加换行符，达到字符纵向排列的效果
    public static String StringPortrait(String a) {
        String b = "";
        for (int i = 0; i < a.length(); i++) {
            b = b + a.charAt(i) + "\n";
        }
        return b;

    }

    /**
     * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
     *
     * @param input
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断当前版本是否兼容目标版本的方法
     *
     * @param VersionCode
     * @return
     */
    public static boolean isMethodsCompat(int VersionCode) {
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        return currentVersion >= VersionCode;
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }

    public static String getDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(new Date());
        return date;
    }

    /**
     * List转变数组
     *
     * @param stringList
     * @return
     */
    public static String[] list2array(List<String> stringList) {
        String[] idStr = new String[stringList.size()];
        for (int i = 0; i < stringList.size(); i++) {
            idStr[i] = stringList.get(i);
        }
        return idStr;
    }

    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    public static File getFileFromServer(String path, ProgressDialog pd) throws Exception {
        // 如果相等的话表示当前的sdcard挂载在手机上并且是可用的
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            // 获取到文件的大小
            pd.setMax(conn.getContentLength());
            InputStream is = conn.getInputStream();
            File file = new File(Environment.getExternalStorageDirectory(), "updata.apk");
            FileOutputStream fos = new FileOutputStream(file);
            BufferedInputStream bis = new BufferedInputStream(is);
            byte[] buffer = new byte[1024];
            int len;
            int total = 0;
            while ((len = bis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                total += len;
                // 获取当前下载量
                pd.setProgress(total);
            }
            fos.close();
            bis.close();
            is.close();
            return file;
        } else {
            return null;
        }
    }

    public static int dip2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static void savePhoto(final Context context, final Bitmap bmp, final SaveResultCallback saveResultCallback) {
        final File sdDir = getSDPath();
        if (sdDir == null) {
            ToastUtil.showInfo(context,"设备自带的存储不可用",Toast.LENGTH_LONG);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                File appDir = new File(sdDir, "out_photo");
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置以当前时间格式为图片名称
                String fileName = df.format(new Date()) + ".png";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    fos.flush();
                    fos.close();
                    saveResultCallback.onSavedSuccess();
                } catch (FileNotFoundException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                } catch (IOException e) {
                    saveResultCallback.onSavedFailed();
                    e.printStackTrace();
                }

                //保存图片后发送广播通知更新数据库
                Uri uri = Uri.fromFile(file);
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            }
        }).start();
    }


    public static File getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir;
    }

    public interface SaveResultCallback {
        void onSavedSuccess();

        void onSavedFailed();
    }

    public static String [] returnImageUrlsFromHtml(String html) {
        List<String> imageSrcList = new ArrayList<String>();
        String htmlCode = html;
        Pattern p = Pattern.compile("<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic|\\b)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("//s+")[0] : m.group(2);
            imageSrcList.add(src);
        }
        if (imageSrcList == null || imageSrcList.size() == 0) {
            Log.e("imageSrcList","资讯中未匹配到图片链接");
            return null;
        }
        return imageSrcList.toArray(new String[imageSrcList.size()]);
    }
    /**
     * List转变字符串“1，2，3”
     *
     * @param stringList
     * @return
     */
    public static String list2string(List<String> stringList) {
        String idStr = "";
        for (int i = 0; i < stringList.size(); i++) {
            if (i == (stringList.size() - 1)) {
                idStr += stringList.get(i);
            } else {
                idStr += stringList.get(i) + ",";
            }
        }
        return idStr;
    }

    /**
     * 跳转企业
     *
     * */
    public static void gotoCompanyDetail(Context context,int epId, String epAccessPath) {
        Intent intent2 = new Intent(context, CompanyDetailWebActivity.class);
        intent2.putExtra("epId", epId);
//                        Log.v("lgq","epid==="+UserNative.getEpId());
//                        intent.putExtra("epLinktel", mCompanyDetail.getEpLinktel());
        intent2.putExtra("accessPath", epAccessPath);
        context.startActivity(intent2);
    }

    public static void openImage(Context context, String imageUrls, String img) {
        Intent intent = new Intent();
        intent.putExtra("imageUrls", imageUrls);
        intent.putExtra("curImageUrl", img);
        intent.setClass(context, PhotoBrowserActivity.class);
        context.startActivity(intent);
    }

    public static String getCity() {
        String[] imageUrls = new String[]{};
        String city = MyShareUtil.getSharedString("city");
        String cityList = MyShareUtil.getSharedString("cityList");
        String result = city;
        if (cityList.length()>0){
            com.alibaba.fastjson.JSONArray urlsJsonArray = com.alibaba.fastjson.JSONArray.parseArray(cityList);
            imageUrls = urlsJsonArray.toArray(new String[urlsJsonArray.size()]);
            for (int ab = 0;ab<imageUrls.length;ab++){
                if (city.contains(imageUrls[ab])){
                    result = city;
                    break;

                }else if (ab==imageUrls.length-1){
                    result = "全国";
                }
            }
        } else {
            if (TextUtil.isEmpty(city)) {
                result =  "全国";
            } else {
                result = city;
            }
        }
        return  result;
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max)%(max-min+1) + min;
    }

}

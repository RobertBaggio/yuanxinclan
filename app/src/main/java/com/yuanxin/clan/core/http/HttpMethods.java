package com.yuanxin.clan.core.http;

import com.yuanxin.clan.core.app.UserNative;
import com.yuanxin.clan.core.util.HttpLoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by lenovo1 on 2017/1/25.
 */
public class HttpMethods {

    private String BASE_URL = Url.urlHost + "/";//新云服务器
    private static final int DEFAULT_TIMEOUT = 20;
    private Retrofit retrofit;
    private ClanApi api;

    private HttpMethods() {
        //打印接口地址
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
            }
        });
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder()
                .client(builder.addInterceptor(interceptor).build())
                .addConverterFactory(FastJsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();

        api = retrofit.create(ClanApi.class);

    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


    private void toSubscribe(Observable<BaseJsonEntity> o, Subscriber<BaseJsonEntity> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    public void register(Subscriber<BaseJsonEntity> subscriber, String tel, String password) {
        Observable observable = api.register(tel, password);
        toSubscribe(observable, subscriber);
    }

    public void memberRegister(Subscriber<BaseJsonEntity> subscriber, String userPhone, String userPwd, String epNm, String legalNm, int industryId, String epCreditCd, String userNm, String postion, String province, String city, String area, String detail) {
        Observable observable = api.memberRegister(userPhone, userPwd, epNm, legalNm, industryId, epCreditCd, userNm, postion, province, city, area, detail);
        toSubscribe(observable, subscriber);
    }

    public void login(Subscriber<BaseJsonEntity> subscriber, String userNm, String userPwd) {
        Observable observable = api.login(userNm, userPwd);
        toSubscribe(observable, subscriber);
    }
//    public void getBannerUrl(Subscriber<BaseJsonEntity> subscriber){
//        Observable observable=api.getBannerUrl();
//        toSubscribe(observable,subscriber);
//
//    }

    //    public void getGankInfo(Subscriber<BaseJsonEntity> subscriber){
//        Observable observable=api.getGankInfo();
//        toSubscribe(observable,subscriber);
//    }
    public void getSmsCode(Subscriber<BaseJsonEntity> subscriber, String phone) {
        Observable observable = api.getSmsCode(phone);
        toSubscribe(observable, subscriber);
    }

    public void areaSeek(Subscriber<BaseJsonEntity> subscriber, String area) {
        Observable observable = api.getSmsCode(area);
        toSubscribe(observable, subscriber);
    }

    //企业库 获取行业列表
    public void getIndustryList(Subscriber<BaseJsonEntity> subscriber) {
        Observable observable = api.getIndustryList(UserNative.getAesKes());
        toSubscribe(observable, subscriber);
    }

    //获取商圈行业列表
    public void getBusinessIndustryList(Subscriber<BaseJsonEntity> subscriber, int businessAreaId) {
        Observable observable = api.getBusinessIndustryList(businessAreaId);
        toSubscribe(observable, subscriber);
    }

    public void getCompanyDetail(Subscriber<BaseJsonEntity> subscriber, int industryId, String province, String city, String area, String epNm, int pageNumber) {
        Observable observable = api.getCompanyDetail(industryId, province, city, area, epNm, pageNumber);
        toSubscribe(observable, subscriber);
    }

    //我的 获取企业信息
    public void getUserEpInfo(Subscriber<BaseJsonEntity> subscriber, int userId) {
        Observable observable = api.getUserEpInfo(userId);
        toSubscribe(observable, subscriber);
    }


    public void fileUpload(Subscriber<BaseJsonEntity> subscriber, String path, File myFile) {
        RequestBody pathBody = RequestBody.create(MediaType.parse("text/plain"), path);
        RequestBody aesKes = RequestBody.create(MediaType.parse("text/plain"), UserNative.getAesKes());
//        RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), token);
//        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("myFile", myFile.getName(), imageBody);
        Observable observable = api.fileUpload(pathBody, aesKes, imageBodyPart);

        toSubscribe(observable, subscriber);
    }

    public void businessSearch(Subscriber<BaseJsonEntity> subscriber, String province, String city, String area, int industry_id, String business_area_nm, String business_area_detail) {
        Observable observable = api.businessSearch(province, city, area, industry_id, business_area_nm, business_area_detail);
        toSubscribe(observable, subscriber);
    }

    public void getExpertList(Subscriber<BaseJsonEntity> subscriber, String expertNm, int pageNumber) {
        Observable observable = api.getExpertList(expertNm, pageNumber, UserNative.getAesKes());
        toSubscribe(observable, subscriber);
    }

    public void getById(Subscriber<BaseJsonEntity> subscriber, int expertId) {
        Observable observable = api.getById(expertId);
        toSubscribe(observable, subscriber);
    }

    public void getShowAdvertisements(Subscriber<BaseJsonEntity> subscriber, String city,String key) {
        Observable observable = api.getShowAdvertisements(city, key);
        toSubscribe(observable, subscriber);
    }

//    public void getShowAdverkey(Subscriber<BaseJsonEntity> subscriber, String key) {
//        Observable observable = api.getShowAdvertisements(key);
//        toSubscribe(observable, subscriber);
//    }

    public void getByCompanyId(Subscriber<BaseJsonEntity> subscriber, int epId) {
        Observable observable = api.getByCompanyId(epId);
        toSubscribe(observable, subscriber);
    }

    public void getMarketList(Subscriber<BaseJsonEntity> subscriber, int pageNumber) {
        Observable observable = api.getMarketList(pageNumber, UserNative.getAesKes());
        toSubscribe(observable, subscriber);
    }

    public void getMarketDetail(Subscriber<BaseJsonEntity> subscriber, int marketId) {
        Observable observable = api.getMarketDetail(marketId);
        toSubscribe(observable, subscriber);
    }

    public void getCrowdList(Subscriber<BaseJsonEntity> subscriber, String epNm, int pageNumber) {
        Observable observable = api.getCrowdList(epNm, pageNumber);
        toSubscribe(observable, subscriber);
    }

    public void getCrowdDetail(Subscriber<BaseJsonEntity> subscriber, int crowdfundId) {
        Observable observable = api.getCrowdDetail(crowdfundId);
        toSubscribe(observable, subscriber);
    }

    public void getUserBusiness(Subscriber<BaseJsonEntity> subscriber, int userId, int pageNumber) {
        Observable observable = api.getUserBusiness(userId, pageNumber);
        toSubscribe(observable, subscriber);
    }

    public void myBargainFileUpload(Subscriber<BaseJsonEntity> subscriber, String path, File myFile) {
        RequestBody pathBody = RequestBody.create(MediaType.parse("text/plain"), path);
//        RequestBody tokenBody = RequestBody.create(MediaType.parse("text/plain"), token);
//        RequestBody typeBody = RequestBody.create(MediaType.parse("text/plain"), type);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), myFile);
        MultipartBody.Part imageBodyPart = MultipartBody.Part.createFormData("myFile", myFile.getName(), imageBody);


        Observable observable = api.myBargainFileUpload(pathBody, imageBodyPart);

        toSubscribe(observable, subscriber);
    }


//public static final String BASE_URL="http://gank.io/api/";
//    private static final int DEFAULT_TIMEOUT=5;
//    private Retrofit retrofit;
//    private ClanApi api;
//
//    private HttpMethods(){
//        //打印接口地址
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
//            @Override
//            public void log(String message) {
//            }
//        });
//        //手动创建一个OkHttpClient并设置超时时间
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//
//        retrofit = new Retrofit.Builder()
//                .client(builder.addInterceptor(interceptor).build())
//                .addConverterFactory(FastJsonConverterFactory.create())
//                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .baseUrl(BASE_URL)
//                .build();
//
//        api = retrofit.create(ClanApi.class);
//
//    }
//    //在访问HttpMethods时创建单例
//    private static class SingletonHolder {
//        private static final HttpMethods INSTANCE = new HttpMethods();
//    }
//
//    //获取单例
//    public static HttpMethods getInstance() {
//        return SingletonHolder.INSTANCE;
//    }
//
//
//    private void toSubscribe(Observable<BaseJsonEntity> o, Subscriber<BaseJsonEntity> s) {
//        o.subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(s);
//    }
//
//    public void register(Subscriber<BaseJsonEntity> subscriber,String tel,String password,String city){
//        Observable observable=api.register(tel,password,city);
//        toSubscribe(observable,subscriber);
//    }
//    public void login(Subscriber<BaseJsonEntity> subscriber,String tel,String password){
//        Observable observable=api.login(tel,password);
//        toSubscribe(observable,subscriber);
//    }
//    public void getBannerUrl(Subscriber<BaseJsonEntity> subscriber){
//        Observable observable=api.getBannerUrl();
//        toSubscribe(observable,subscriber);
//
//    }
//
//    public void getMyInfoList(Subscriber<BaseJsonEntity> subscriber,int user_id){
//        Observable observable=api.getMyInfoList(user_id);
//        toSubscribe(observable,subscriber);
//    }


}

package com.yuanxin.clan.core.http;

import java.math.BigDecimal;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lenovo1 on 2017/1/25.
 */
public interface ClanApi {
//    @FormUrlEncoded
//    @GET("book/search")
//    Observable<BaseJsonEntity> getGankInfo(@Query("q") String name, @Query("tag") String tag, @Query("start") int start, @Query("count") int count);
//    @GET("data/Android/10/1")
//    Observable<BaseJsonEntity> getGankInfo(@Field("q") String name,@Field("tag") String tag, @Field("start") int start,@Field("count") int count);

//    @FormUrlEncoded
//    @GET("data/Android/10/1")
//    Observable<BaseJsonEntity> getGankInfo();

//    @POST("job/job/profession")
//    Observable<BaseJsonEntity> getJobProfession();

//    @POST("home/banner_list/0")
//    Observable<BaseJsonEntity> getBannerUrl();

//    @FormUrlEncoded
//    @POST()
//    Observable<BaseJsonEntity>getCompanyList();

    //普通注册
    @FormUrlEncoded
    @POST("user/register")
    Observable<BaseJsonEntity> register(@Field("userPhone") String tel, @Field("userPwd") String password);

    //会员注册
    @FormUrlEncoded
    @POST("user/memberRegister")
    Observable<BaseJsonEntity> memberRegister(@Field("userPhone") String userPhone, @Field("userPwd") String userPwd, @Field("epNm") String epNm, @Field("legalNm") String legalNm, @Field("industryId") int industryId, @Field("epCreditCd") String epCreditCd, @Field("userNm") String userNm, @Field("postion") String postion, @Field("province") String province, @Field("city") String city, @Field("area") String area, @Field("detail") String detail);

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<BaseJsonEntity> login(@Field("userNm") String userNm, @Field("userPwd") String userPwd);
//
//    @FormUrlEncoded
//    @POST("my/info_list")
//    Observable<BaseJsonEntity>getMyInfoList(@Field("user_id")int user_id);

    @FormUrlEncoded
    @POST("user/registerCode")
    Observable<BaseJsonEntity> getSmsCode(@Field("phone") String phone);

    //    @FormUrlEncoded
//    @POST
//    Observable<BaseJsonEntity>memberRegister(@Field(""))
    //企业库 企业详情  搜索
    @FormUrlEncoded
    @POST("ep/search")
    Observable<BaseJsonEntity> getCompanyDetail(@Field("industryId") int industryId, @Field("province") String province, @Field("city") String city, @Field("area") String area, @Field("epNm") String epNm, @Field("pageNumber") int pageNumber);

    //企业库 获取行业列表
    @GET("industry/list")
    Observable<BaseJsonEntity> getIndustryList(@Query("key") String aesKes);

    //商圈 获取行业列表
    @GET("baUser/industryList")
    Observable<BaseJsonEntity> getBusinessIndustryList(@Query("businessAreaId") int businessAreaId);

    //我的 获取企业信息
    @FormUrlEncoded
    @POST("user/getUserEpInfo")
    Observable<BaseJsonEntity> getUserEpInfo(@Field("userId") int userId);

    ////我的 获取企业信息
    @FormUrlEncoded
    @POST("ep/getById")
    Observable<BaseJsonEntity> getByCompanyId(@Field("epId") int epId);

    //    //上传图片
//    @FormUrlEncoded
//    @POST("file/web/fileUpload")
//    Observable<BaseJsonEntity> fileUpload(@Field("myFile") MultipartBody.Part upfile, @Field("path")String path);
//上传企业库图片
    @Multipart
    @POST("file/fileUpload")
    Observable<BaseJsonEntity> fileUpload(@Part("path") RequestBody path, @Part("key") RequestBody aesKes,@Part MultipartBody.Part myFile);

    //商圈列表
    @FormUrlEncoded
    @POST("business/search")
    Observable<BaseJsonEntity> businessSearch(@Field("province") String province, @Field("city") String city, @Field("area") String area, @Field("industry_id") int industry_id, @Field("business_area_nm") String business_area_nm, @Field("business_area_detail") String business_area_detail);

    //专家列表
    @FormUrlEncoded
    @POST("expert/list")
    Observable<BaseJsonEntity> getExpertList(@Field("expertNm") String expertNm, @Field("pageNumber") int pageNumber, @Field("key") String aesKes);

    //专家详情 这个有问题
    @FormUrlEncoded
    @POST("expert/getById")
    Observable<BaseJsonEntity> getById(@Field("expertId") int expertId);


    //首页 四个轮播
//    @FormUrlEncoded
    @GET("advertisement/getShowAdvertisements")
    Observable<BaseJsonEntity> getShowAdvertisements(@Query("city") String city,@Query("key") String key);

    //首页 四个轮播
//    @FormUrlEncoded
    @GET("advertisement/getShowAdvertisements")
    Observable<BaseJsonEntity> getShowAdverkey(@Query("city") String key);

    //集市列表
    @FormUrlEncoded
    @POST("market/list")
    Observable<BaseJsonEntity> getMarketList(@Field("pageNumber") int pageNumber, @Field("key") String aesKes);

    //集市详情
    @FormUrlEncoded
    @POST("/market/getById")
    Observable<BaseJsonEntity> getMarketDetail(@Field("marketId") int marketId);

    //添加购物车
    @FormUrlEncoded
    @POST("/car/addToCar")
    Observable<BaseJsonEntity> addToCarl(@Field("userId") int userId, @Field("commodityColor") String commodityColor, @Field("commoditySp") String commoditySp, @Field("commodityNumber") int commodityNumber, @Field("commodityPrice") BigDecimal commodityPrice, @Field("commodityId") int commodityId);

    //删除购物车
    @FormUrlEncoded
    @POST("/car/delByCar")
    Observable<BaseJsonEntity> delByCar(@Field("carId") int carId);

    //获取用户购物车信息
    @FormUrlEncoded
    @POST("/car/getByUserId")
    Observable<BaseJsonEntity> getCarDetail(@Field("userId") int userId);

    //购物车结算
    @FormUrlEncoded
    @POST("/order/settlement")
    Observable<BaseJsonEntity> settleAccounts(@Field("shopListId") int shopListId, @Field("commodityColor") String commodityColor, @Field("commoditySp") String commoditySp, @Field("commodityNumber") int commodityNumber, @Field("commodityPrice") BigDecimal commodityPrice, @Field("commodityId") int commodityId);

    //众筹列表
    @FormUrlEncoded
    @POST("/crowdfund/search")
    Observable<BaseJsonEntity> getCrowdList(@Field("epNm") String epNm, @Field("pageNumber") int pageNumber);


    //众筹详情
    @FormUrlEncoded
    @POST("/crowdfund/getById")
    Observable<BaseJsonEntity> getCrowdDetail(@Field("crowdfundId") int crowdfundId);

    //个人中心 我的商圈
    @FormUrlEncoded
    @POST("user/getUserBusiness")
    Observable<BaseJsonEntity> getUserBusiness(@Field("userId") int userId, @Field("pageNumber") int pageNumber);

    //上传合同图片
    @Multipart
    @POST("file/web/fileUpload")
    Observable<BaseJsonEntity> myBargainFileUpload(@Part("path") RequestBody path, @Part MultipartBody.Part myFile);

}

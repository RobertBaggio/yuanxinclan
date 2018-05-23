package com.yuanxin.clan.core.http;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lenovo1 on 2017/3/30.
 */
public interface Api {
    //    @GET("book/search")
//    Call<BookSearchResponse> getSearchBooks(@Query("q") String name,
//                                            @Query("tag") String tag, @Query("start") int start,
//                                            @Query("count") int count);
    //智囊团
    @POST("/expert/getById")
    Call<BaseJsonEntity> getById(@Query("expertId") int expertId);

    //集市详情
    @POST("/market/getById")
    Call<BaseJsonEntity> getMarketDetail(@Query("marketId") int marketId);

    @POST("user/login")
    Call<BaseJsonEntity> login(@Query("userNm") String userNm, @Query("userPwd") String userPwd);
//    @POST("/crowdfund/search")
//    Call<BaseJsonEntity> login(@Query("userNm") String userNm,@Query("userPwd") String userPwd);

}

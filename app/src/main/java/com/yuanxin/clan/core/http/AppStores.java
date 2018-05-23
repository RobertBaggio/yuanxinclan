package com.yuanxin.clan.core.http;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.fastjson.FastJsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by lenovo1 on 2017/1/27.
 */
public class AppStores {
    public interface TaobaoIpService {
        @GET("data/all/20/2")
        Call<BaseJsonEntity> getData();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")//★这里最后面必须能带“/”
                .addConverterFactory(FastJsonConverterFactory.create())//设置将json解析为javabean所用的方式
                .build();
        //    3.通过retrofit创建第一步定义的接口的实例，
//      供在外部直接通过该实例调用该接口的getIPad方法，完成网络请求
        AppStores.TaobaoIpService taobaoIPService =
                retrofit.create(AppStores.TaobaoIpService.class);
    }
}

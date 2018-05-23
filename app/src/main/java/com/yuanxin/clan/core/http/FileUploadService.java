package com.yuanxin.clan.core.http;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Created by lenovo1 on 2017/4/26.
 */
public interface FileUploadService {
    /**
     * 上传一张图片
     *
     * @param myFile
     * @param imgs
     * @return
     */
    @Multipart
    @POST("/file/fileUpload")
    Call<String> uploadImage(@Part("myFile") String myFile,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs);

    /**
     * 上传三张图片
     *
     * @param myFile
     * @param imgs
     * @param imgs1
     * @param imgs3
     * @return
     */
    @Multipart
    @POST("/file/fileUpload")
    Call<String> uploadImage(@Part("myFile") String myFile,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs1,
                             @Part("file\"; filename=\"image.png\"") RequestBody imgs3);
}


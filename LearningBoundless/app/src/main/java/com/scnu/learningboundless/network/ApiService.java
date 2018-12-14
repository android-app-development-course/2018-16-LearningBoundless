package com.scnu.learningboundless.network;


import com.scnu.learningboundless.bean.network.LoginResponseInfo;
import com.scnu.learningboundless.bean.network.RegisterResponseInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 * Created by WuchangI on 2018/11/17.
 */

public interface ApiService {

    /**
     * 获取用户登录状态
     *
     * @param email
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("login")
    Observable<LoginResponseInfo> getLoginStatus(@Field("email") String email, @Field("password") String password);


    /**
     * 获取用户注册状态
     *
     * @param username
     * @param email
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("register")
    Observable<RegisterResponseInfo> getRegisterStatus(@Field("name") String username, @Field("email") String email,
                                                       @Field("password") String password);

}

package com.scnu.learningboundless.utils;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by WuchangI on 2018/11/17.
 */


public class RetrofitUtils {

    /**
     * 获取一个带有日志拦截器的OkHttpClient实例
     *
     * @return
     */
    public static OkHttpClient getOkHttpClientWithLoggingInterceptor() {

        // 新建日志拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.i("请求参数:", message);
            }
        });

        // 设置日志显示级别
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // 定制OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        return okHttpClient;
    }

}

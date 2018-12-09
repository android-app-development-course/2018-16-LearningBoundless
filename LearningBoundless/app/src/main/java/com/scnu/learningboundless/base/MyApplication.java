package com.scnu.learningboundless.base;

import android.app.Application;
import android.content.Context;

import com.scnu.learningboundless.utils.TypefaceUtils;

/**
 * Created by WuchangI on 2018/12/10.
 */

public class MyApplication extends Application {

    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();

        initTypefaceUtil();

    }

    /**
     * 初始化字体工具类
     */
    public void initTypefaceUtil() {
        TypefaceUtils.getInstance().init(this);
    }

    /**
     * 获取全局上下文对象
     *
     * @return
     */
    public static Context getGlobalApplication() {
        return sContext;
    }
}

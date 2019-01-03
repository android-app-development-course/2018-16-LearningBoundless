package com.scnu.learningboundless.base;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.scnu.learningboundless.utils.Model;
import com.scnu.learningboundless.utils.TypefaceUtils;

/**
 * Created by WuchangI on 2018/12/10.
 */

public class MyApplication extends Application {

    private static Context sContext;


    @Override
    public void onCreate() {
        super.onCreate();

        sContext = this;

        initEaseUI();
        initModel();
        initTypefaceUtil();
    }

    /**
     * 初始化环信EaseUI
     */
    public void initEaseUI() {
        EMOptions options = new EMOptions();

        // 设置为需要对方同意后才接受好友邀请
        options.setAcceptInvitationAlways(false);

        EaseUI.getInstance().init(this, options);
    }


    /**
     * 初始化数据模型层类
     */
    public void initModel() {
        Model.getInstance().init(this);
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

package com.scnu.learningboundless.network;


import com.scnu.learningboundless.bean.network.BaseResponseInfo;
import com.scnu.learningboundless.bean.network.LoginResponseInfo;
import com.scnu.learningboundless.bean.network.RegisterResponseInfo;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class ReceiveMessageManager {

    private static ReceiveMessageManager receiveMessageManager;

    public static ReceiveMessageManager getInstance() {
        return receiveMessageManager == null ? receiveMessageManager = new ReceiveMessageManager() : receiveMessageManager;
    }

    private ReceiveMessageManager() {
    }


    /**
     * 分发消息
     *
     * @param baseResponseInfo
     * @param appendUrl
     */
    public void dispatchMessage(BaseResponseInfo baseResponseInfo, String appendUrl) {

        switch (appendUrl) {

            case "login":

                LoginResponseInfo loginResponseInfo = (LoginResponseInfo) baseResponseInfo;
                EventBus.getDefault().post(loginResponseInfo);

                break;

            case "register":

                RegisterResponseInfo registerResponseInfo = (RegisterResponseInfo) baseResponseInfo;
                EventBus.getDefault().post(registerResponseInfo);

                break;


            default:
                break;
        }
    }


}

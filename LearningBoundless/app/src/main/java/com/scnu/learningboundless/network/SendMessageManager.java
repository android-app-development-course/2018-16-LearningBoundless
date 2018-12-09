package com.scnu.learningboundless.network;


import com.scnu.learningboundless.bean.network.LoginResponseInfo;
import com.scnu.learningboundless.bean.network.RegisterResponseInfo;
import com.scnu.learningboundless.constant.Constants;

import io.reactivex.Observable;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class SendMessageManager {
    private static SendMessageManager sendMessageManager;

    private HttpChannel httpChannel;

    private ApiService apiService;

    public static SendMessageManager getInstance() {

        return sendMessageManager == null ? sendMessageManager = new SendMessageManager() : sendMessageManager;
    }

    private SendMessageManager() {

        httpChannel = HttpChannel.getInstance();
        apiService = httpChannel.getApiService();
    }

    /**
     * 发送“获取用户登录状态”的消息
     *
     * @param email
     * @param password
     */
    public void getLoginStatus(String email, String password) {

        Observable<LoginResponseInfo> observable = apiService.getLoginStatus(email, password);
        httpChannel.sendMessage(observable, Constants.GET_LOGIN_STATUS_URL);
    }


    /**
     * 发送“获取用户注册状态”的消息
     *
     * @param username
     * @param email
     * @param password
     */
    public void getRegisterStatus(String username, String email, String password) {

        Observable<RegisterResponseInfo> observable = apiService.getRegisterStatus(username, email, password);
        httpChannel.sendMessage(observable, Constants.GET_REGISTER_STATUS_URL);
    }


}

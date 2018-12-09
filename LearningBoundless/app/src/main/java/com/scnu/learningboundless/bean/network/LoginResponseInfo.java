package com.scnu.learningboundless.bean.network;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class LoginResponseInfo extends BaseResponseInfo
{
    /**
     * 登录状态，error = true表示登录成功，error = false 表示登录失败
     */
    public String error;

    public String getError()
    {
        return error;
    }

    public void setError(String error)
    {
        this.error = error;
    }
}

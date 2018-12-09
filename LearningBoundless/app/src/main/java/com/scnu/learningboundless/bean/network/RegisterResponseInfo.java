package com.scnu.learningboundless.bean.network;

/**
 * Created by WuchangI on 2018/11/17.
 */

public class RegisterResponseInfo extends BaseResponseInfo
{
    /**
     * 注册状态，error = true表示注册成功，error = false 表示注册失败
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

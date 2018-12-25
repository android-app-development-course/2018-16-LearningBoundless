package com.scnu.learningboundless.bean;

/**
 * Created by WuchangI on 2018/12/25.
 */

/**
 * 好友个人信息
 */
public class FriendInfo {

    /**
     * 用户名（用户环信id充当用户名）
     */
    private String userName;

    /**
     * 用户头像
     */
    private String avatar;


    public FriendInfo() {
    }


    public FriendInfo(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}

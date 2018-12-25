package com.scnu.learningboundless.bean;

/**
 * Created by WuchangI on 2018/11/20.
 */


/**
 * 好友的邀请信息（比如别人邀请添加你为好友）
 */
public class InvitationInfo {

    /**
     * 好友名称
     */
    private String userName;

    /**
     * 邀请原因
     */
    private String reason;

    /**
     * 邀请状态
     */
    private InvitationStatus status;

    public InvitationInfo() {
    }

    public InvitationInfo(String userName, String reason, InvitationStatus status) {
        this.userName = userName;
        this.reason = reason;
        this.status = status;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public InvitationStatus getStatus() {
        return status;
    }

    public void setStatus(InvitationStatus status) {
        this.status = status;
    }

    public enum InvitationStatus {

        /**
         * 联系人邀请信息状态：A给B发送好友邀请
         */
        NEW_INVITE,             // B收到新邀请
        INVITE_ACCEPT,          // B接受邀请
        INVITE_ACCEPT_BY_PEER,  // A的好友邀请被接受
    }

}

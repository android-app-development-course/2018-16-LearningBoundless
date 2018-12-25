package com.scnu.learningboundless.listener;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.hyphenate.EMContactListener;
import com.hyphenate.chat.EMClient;
import com.scnu.learningboundless.bean.FriendInfo;
import com.scnu.learningboundless.bean.InvitationInfo;
import com.scnu.learningboundless.constant.Constant;
import com.scnu.learningboundless.utils.Model;
import com.scnu.learningboundless.utils.SPUtils;

/**
 * Created by WuchangI on 2018/11/21.
 */


/**
 * 全局事件监听类
 */
public class GlobalEventListener
{
    private Context mContext;
    private LocalBroadcastManager mLBM;

    public GlobalEventListener(Context context)
    {
        mContext = context;

        mLBM = LocalBroadcastManager.getInstance(context);

        // 注册联系人变化的监听器
        EMClient.getInstance().contactManager().setContactListener(mEmContactListener);
    }


    private final EMContactListener mEmContactListener = new EMContactListener()
    {
        // 联系人增加后执行
        @Override
        public void onContactAdded(String userName)
        {
            // 数据库更新
            Model.getInstance().getFriendAndInvitationManager().getFriendInfoDao().saveFriend(new FriendInfo(userName));

            // 发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        // 联系人删除后执行
        @Override
        public void onContactDeleted(String userName)
        {
            // 数据库更新
            Model.getInstance().getFriendAndInvitationManager().getFriendInfoDao().deleteFriendByUserName(userName);
            Model.getInstance().getFriendAndInvitationManager().getInvitationInfoDao().deleteInvitationByUserName(userName);

            // 发送联系人变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_CHANGED));
        }

        // 接收到联系人的好友邀请后执行
        @Override
        public void onContactInvited(String userName, String reason)
        {
            // 数据库更新
            InvitationInfo invitationInfo = new InvitationInfo();

            invitationInfo.setUserName(userName);
            invitationInfo.setReason(reason);
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.NEW_INVITE);

            Model.getInstance().getFriendAndInvitationManager().getInvitationInfoDao().addInvitation(invitationInfo);

            // 红点显示
            SPUtils.getInstance().save(SPUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }


        // 别人同意了你的好友邀请后执行
        @Override
        public void onFriendRequestAccepted(String userName)
        {
            // 数据库更新
            InvitationInfo invitationInfo = new InvitationInfo();

            invitationInfo.setUserName(userName);
            invitationInfo.setStatus(InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER);

            Model.getInstance().getFriendAndInvitationManager().getInvitationInfoDao().addInvitation(invitationInfo);

            // 红点显示
            SPUtils.getInstance().save(SPUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }

        // 别人拒绝了你的好友邀请后执行
        @Override
        public void onFriendRequestDeclined(String s)
        {
            // 红点显示
            SPUtils.getInstance().save(SPUtils.IS_NEW_INVITE, true);

            // 发送邀请信息变化的广播
            mLBM.sendBroadcast(new Intent(Constant.CONTACT_INVITE_CHANGED));
        }
    };

}

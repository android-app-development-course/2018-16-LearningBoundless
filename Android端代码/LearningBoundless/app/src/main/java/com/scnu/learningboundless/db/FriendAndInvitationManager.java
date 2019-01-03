package com.scnu.learningboundless.db;

/**
 * Created by WuchangI on 2018/11/21.
 */

import android.content.Context;

import com.scnu.learningboundless.db.biz.FriendInfoDao;
import com.scnu.learningboundless.db.biz.InvitationInfoDao;
import com.scnu.learningboundless.db.helper.FriendAndInvitationDbHelper;

/**
 * 统一管理FriendInfoDao和InvitationInfoDao的一个管理类
 */
public class FriendAndInvitationManager {

    private final FriendAndInvitationDbHelper mDbHelper;

    private final FriendInfoDao mFriendInfoDao;

    private final InvitationInfoDao mInvitationInfoDao;


    public FriendAndInvitationManager(Context context, String databaseName) {
        // 创建数据库
        mDbHelper = new FriendAndInvitationDbHelper(context, databaseName);

        // 创建这个数据库中两张表的操作类
        mFriendInfoDao = new FriendInfoDao(mDbHelper);
        mInvitationInfoDao = new InvitationInfoDao(mDbHelper);
    }


    public FriendInfoDao getFriendInfoDao() {
        return mFriendInfoDao;
    }

    public InvitationInfoDao getInvitationInfoDao() {
        return mInvitationInfoDao;
    }


    public void close() {
        mDbHelper.close();
    }
}

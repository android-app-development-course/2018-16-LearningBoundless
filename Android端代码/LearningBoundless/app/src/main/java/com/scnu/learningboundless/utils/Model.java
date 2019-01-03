package com.scnu.learningboundless.utils;

/**
 * Created by WuchangI on 2018/11/19.
 */

import android.content.Context;

import com.scnu.learningboundless.bean.AccountInfo;
import com.scnu.learningboundless.db.FriendAndInvitationManager;
import com.scnu.learningboundless.db.biz.AccountInfoDao;
import com.scnu.learningboundless.listener.GlobalEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 数据模型层全局类
 */
public class Model {

    private static Model sModel = new Model();
    private Context mContext;
    private ExecutorService mExecutorService = Executors.newCachedThreadPool();
    private AccountInfoDao mAccountInfoDao;
    private FriendAndInvitationManager mFriendAndInvitationManager;

    private Model() {

    }


    public static Model getInstance() {
        return sModel;
    }

    public void init(Context context) {
        mContext = context;
        mAccountInfoDao = new AccountInfoDao(mContext);

        // 开启全局监听
        GlobalEventListener globalEventListener = new GlobalEventListener(context);
    }


    /**
     * 获取全局线程池对象
     *
     * @return
     */
    public ExecutorService getGlobalThreadPool() {
        return mExecutorService;
    }

    public AccountInfoDao getAccountInfoDao() {
        return mAccountInfoDao;
    }

    public FriendAndInvitationManager getFriendAndInvitationManager() {
        return mFriendAndInvitationManager;
    }


    /**
     * 用户登录成功后的处理
     *
     * @param accountInfo
     */
    public void loginSuccess(AccountInfo accountInfo) {
        if (accountInfo == null) {
            return;
        }

        if (mFriendAndInvitationManager != null) {
            mFriendAndInvitationManager.close();
        }

        // 创建一个名称为当前账户名的数据库
        mFriendAndInvitationManager
                = new FriendAndInvitationManager(mContext, accountInfo.getUserName());
    }
}

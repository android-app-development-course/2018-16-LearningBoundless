package com.scnu.learningboundless.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.scnu.learningboundless.constant.FriendInfoTable;
import com.scnu.learningboundless.constant.InvitationInfoTable;

/**
 * Created by WuchangI on 2018/11/20.
 */

/**
 * 存放好友信息、邀请信息的数据库
 */
public class FriendAndInvitationDbHelper extends SQLiteOpenHelper {

    /**
     * 不同的用户名，新建的数据库名 name 不同
     *
     * @param context
     * @param name    数据库名称，为了唯一标识特定用户使用的数据库，这里传入用户名
     */
    public FriendAndInvitationDbHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 创建好友列表
        db.execSQL(FriendInfoTable.CREATE_TABLE);

        // 创建相关邀请信息列表
        db.execSQL(InvitationInfoTable.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

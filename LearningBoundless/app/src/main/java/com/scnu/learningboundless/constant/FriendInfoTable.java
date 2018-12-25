package com.scnu.learningboundless.constant;

/**
 * Created by WuchangI on 2018/11/20.
 */


/**
 * 当前用户的好友列表
 */
public class FriendInfoTable {

    public static final String TABLE_NAME = "table_friend";

    public static final String COL_USER_NAME = "user_name";
    public static final String COL_AVATAR = "avatar";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + COL_USER_NAME + " TEXT PRIMARY KEY, "
            + COL_AVATAR + " TEXT);";
}

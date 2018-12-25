package com.scnu.learningboundless.constant;

/**
 * Created by WuchangI on 2018/11/19.
 */


/**
 * 本地账户（用户）数据建表语句，保存的是在本机中成功登录过的用户信息，因为通过 当前APP实例 登录的用户可能不止一个
 */
public class AccountInfoTable {

    public static final String TABLE_NAME = "table_account";

    public static final String COL_USER_NAME = "user_name";
    public static final String COL_AVATAR = "avatar";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + COL_USER_NAME + " TEXT PRIMARY KEY, "
            + COL_AVATAR + " TEXT);";
}

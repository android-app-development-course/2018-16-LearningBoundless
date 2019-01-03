package com.scnu.learningboundless.constant;

/**
 * Created by WuchangI on 2018/11/21.
 */

/**
 * 当前用户的相关邀请信息列表
 */
public class InvitationInfoTable {

    public static final String TABLE_NAME = "table_invitation";

    public static final String COL_USER_NAME = "user_name";
    public static final String COL_REASON = "reason";
    public static final String COL_STATUS = "status";

    public static final String CREATE_TABLE = "CREATE TABLE "
            + TABLE_NAME + " ("
            + COL_USER_NAME + " TEXT PRIMARY KEY, "
            + COL_REASON + " TEXT, "
            + COL_STATUS + " INTEGER);";
}

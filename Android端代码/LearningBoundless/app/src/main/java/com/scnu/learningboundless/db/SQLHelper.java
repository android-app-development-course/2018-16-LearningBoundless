package com.scnu.learningboundless.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "learningboundless.db";// 数据库名称
    public static final int VERSION = 1;

    public static final String TABLE_NAME = "task";//数据表

    public static final  String CONTENT="content";
    public static final  String STARTTIME="start_time";
    public static final  String ENDTIMR="end_time";
    public static final  String PRIORITY="priority";
    public static final  String ISFINISH="is_finish";

    private Context context;

    public SQLHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    public Context getContext(){
        return context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO 创建数据库后，对数据库的操作
        String sql = "create table if not exists "+ TABLE_NAME +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTENT + " TEXT , " +
                STARTTIME + " TEXT , " +
                ENDTIMR + " TEXT , " +
                PRIORITY + " INTEGER , " +
                ISFINISH + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO 更改数据库版本的操作
        onCreate(db);
    }

}

package com.scnu.learningboundless.db.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.scnu.learningboundless.bean.AccountInfo;
import com.scnu.learningboundless.constant.AccountInfoTable;
import com.scnu.learningboundless.db.helper.AccountInfoDbHelper;

/**
 * Created by WuchangI on 2018/11/19.
 */

public class AccountInfoDao {

    private final AccountInfoDbHelper mHelper;

    public AccountInfoDao(Context context) {
        mHelper = new AccountInfoDbHelper(context);
    }


    public void addAccountInfo(AccountInfo accountInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(AccountInfoTable.COL_USER_NAME, accountInfo.getUserName());
        values.put(AccountInfoTable.COL_AVATAR, accountInfo.getAvatar());

        db.replace(AccountInfoTable.TABLE_NAME, null, values);
    }


    public AccountInfo getAccountInfoByUserName(String userName) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + AccountInfoTable.TABLE_NAME
                + " WHERE " + AccountInfoTable.COL_USER_NAME + "=?";

        Cursor cursor = db.rawQuery(sql, new String[]{userName});

        AccountInfo accountInfo = null;

        if (cursor.moveToNext()) {
            accountInfo = new AccountInfo();
            accountInfo.setUserName(cursor.getString(cursor.getColumnIndex(AccountInfoTable.COL_USER_NAME)));
            accountInfo.setAvatar(cursor.getString(cursor.getColumnIndex(AccountInfoTable.COL_AVATAR)));
        }

        cursor.close();

        return accountInfo;
    }

}

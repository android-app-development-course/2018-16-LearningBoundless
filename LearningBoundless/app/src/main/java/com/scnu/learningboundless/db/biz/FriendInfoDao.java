package com.scnu.learningboundless.db.biz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.scnu.learningboundless.bean.FriendInfo;
import com.scnu.learningboundless.constant.FriendInfoTable;
import com.scnu.learningboundless.db.helper.FriendAndInvitationDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuchangI on 2018/11/20.
 */

public class FriendInfoDao {

    private final FriendAndInvitationDbHelper mHelper;

    public FriendInfoDao(FriendAndInvitationDbHelper friendAndInvitationDbHelper) {
        mHelper = friendAndInvitationDbHelper;
    }


    public List<FriendInfo> getFriendList() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + FriendInfoTable.TABLE_NAME;

        Cursor cursor = db.rawQuery(sql, null);

        List<FriendInfo> friendInfoList = new ArrayList<>();

        while (cursor.moveToNext()) {
            FriendInfo friendInfo = new FriendInfo();

            friendInfo.setUserName(cursor.getString(cursor.getColumnIndex(FriendInfoTable.COL_USER_NAME)));
            friendInfo.setAvatar(cursor.getString(cursor.getColumnIndex(FriendInfoTable.COL_AVATAR)));

            friendInfoList.add(friendInfo);
        }

        cursor.close();

        return friendInfoList;
    }


    public FriendInfo getFriendByUserName(String userName) {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + FriendInfoTable.TABLE_NAME + " WHERE " + FriendInfoTable.COL_USER_NAME + "=?";

        Cursor cursor = db.rawQuery(sql, new String[]{userName});

        FriendInfo friendInfo = null;

        if (cursor.moveToNext()) {
            friendInfo = new FriendInfo();

            friendInfo.setUserName(cursor.getString(cursor.getColumnIndex(FriendInfoTable.COL_USER_NAME)));
            friendInfo.setAvatar(cursor.getString(cursor.getColumnIndex(FriendInfoTable.COL_AVATAR)));
        }

        cursor.close();

        return friendInfo;
    }



    public void saveFriend(FriendInfo friendInfo) {
        if (friendInfo == null) {
            return;
        }

        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FriendInfoTable.COL_USER_NAME, friendInfo.getUserName());
        values.put(FriendInfoTable.COL_AVATAR, friendInfo.getAvatar());

        db.replace(FriendInfoTable.TABLE_NAME, null, values);
    }


    public void saveFriendList(List<FriendInfo> friendInfoList) {
        if (friendInfoList == null || friendInfoList.size() <= 0) {
            return;
        }

        for (FriendInfo friendInfo : friendInfoList) {
            saveFriend(friendInfo);
        }
    }


    public void deleteFriendByUserName(String userName) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.delete(FriendInfoTable.TABLE_NAME, FriendInfoTable.COL_USER_NAME + "=?", new String[]{userName});
    }
}

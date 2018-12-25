package com.scnu.learningboundless.db.biz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.scnu.learningboundless.bean.InvitationInfo;
import com.scnu.learningboundless.constant.InvitationInfoTable;
import com.scnu.learningboundless.db.helper.FriendAndInvitationDbHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WuchangI on 2018/11/21.
 */

public class InvitationInfoDao {

    private final FriendAndInvitationDbHelper mHelper;

    public InvitationInfoDao(FriendAndInvitationDbHelper friendAndInvitationDbHelper) {
        mHelper = friendAndInvitationDbHelper;
    }


    public void addInvitation(InvitationInfo invitationInfo) {
        if (invitationInfo == null) {
            return;
        }

        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InvitationInfoTable.COL_USER_NAME, invitationInfo.getUserName());
        values.put(InvitationInfoTable.COL_REASON, invitationInfo.getReason());
        values.put(InvitationInfoTable.COL_STATUS, invitationInfo.getStatus().ordinal());

        db.replace(InvitationInfoTable.TABLE_NAME, null, values);
    }


    public List<InvitationInfo> getInvitationList() {
        SQLiteDatabase db = mHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + InvitationInfoTable.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        List<InvitationInfo> invitationInfoList = new ArrayList<>();

        while (cursor.moveToNext()) {
            InvitationInfo invitationInfo = new InvitationInfo();

            invitationInfo.setUserName(cursor.getString(cursor.getColumnIndex(InvitationInfoTable.COL_USER_NAME)));
            invitationInfo.setReason(cursor.getString(cursor.getColumnIndex(InvitationInfoTable.COL_REASON)));
            invitationInfo.setStatus(int2InvitationStatus(cursor.getInt(cursor.getColumnIndex(InvitationInfoTable.COL_STATUS))));

            invitationInfoList.add(invitationInfo);
        }

        cursor.close();

        return invitationInfoList;
    }

    public void deleteInvitationByUserName(String userName) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        db.delete(InvitationInfoTable.TABLE_NAME, InvitationInfoTable.COL_USER_NAME + "=?", new String[]{userName});
    }

    public void updateInvitationStatus(String userName, InvitationInfo.InvitationStatus invitationStatus) {
        SQLiteDatabase db = mHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(InvitationInfoTable.COL_STATUS, invitationStatus.ordinal());

        db.update(InvitationInfoTable.TABLE_NAME, values, InvitationInfoTable.COL_USER_NAME + "=?", new String[]{userName});
    }


    public InvitationInfo.InvitationStatus int2InvitationStatus(int intStatus) {

        if (intStatus == InvitationInfo.InvitationStatus.NEW_INVITE.ordinal()) {
            return InvitationInfo.InvitationStatus.NEW_INVITE;
        } else if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT;
        } else if (intStatus == InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER.ordinal()) {
            return InvitationInfo.InvitationStatus.INVITE_ACCEPT_BY_PEER;
        } else {
            return null;
        }
    }

}

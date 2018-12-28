package com.scnu.learningboundless.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.scnu.learningboundless.bean.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskDao {
    private SQLHelper mDbHelper;

    public TaskDao(Context context){
        mDbHelper = new SQLHelper(context);
    }

    public List<Task> listTasks(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelper.TABLE_NAME + ";";
        Cursor cursor = db.rawQuery(sql, null);

        List<Task> taskList = new ArrayList<Task>();
        Task task = null;

        while(cursor.moveToNext()){
            task = new Task();
            task.setContent(cursor.getString(cursor.getColumnIndex(SQLHelper.CONTENT)));
            task.setStartTime(cursor.getString(cursor.getColumnIndex(SQLHelper.STARTTIME)));
            task.setEndTime(cursor.getString(cursor.getColumnIndex(SQLHelper.ENDTIMR)));
            task.setPriority(cursor.getInt(cursor.getColumnIndex(SQLHelper.PRIORITY)));
            task.setIs_finish(cursor.getInt(cursor.getColumnIndex(SQLHelper.ISFINISH)));

            taskList.add(task);
        }

        cursor.close();

        return taskList;
    }

    public void addTask(Task task) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLHelper.CONTENT, task.getContent());
        values.put(SQLHelper.STARTTIME, task.getStartTime());
        values.put(SQLHelper.ENDTIMR, task.getEndTime());
        values.put(SQLHelper.PRIORITY, task.getPriority());
        values.put(SQLHelper.ISFINISH, task.getIs_finish());


        db.replace(SQLHelper.TABLE_NAME, null, values);
    }

    public void updateTask(String data,Task task){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SQLHelper.CONTENT, task.getContent());
        values.put(SQLHelper.STARTTIME, task.getStartTime());
        values.put(SQLHelper.ENDTIMR, task.getEndTime());
        values.put(SQLHelper.PRIORITY, task.getPriority());
        values.put(SQLHelper.ISFINISH, task.getIs_finish());

        db.update(SQLHelper.TABLE_NAME, values, SQLHelper.STARTTIME + " =? ", new String[]{data});
    }

    public void removeAllTasks(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(SQLHelper.TABLE_NAME, null, null);
    }

    public Task findTaskByDate(String date){
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sql = "SELECT * FROM " + SQLHelper.TABLE_NAME
                + " WHERE " + SQLHelper.STARTTIME + "=?;";

        Cursor cursor = db.rawQuery(sql, new String[]{date});

        Task task = null;

        if (cursor.moveToNext()){
            task = new Task();
            task.setContent(cursor.getString(cursor.getColumnIndex(SQLHelper.CONTENT)));
            task.setStartTime(cursor.getString(cursor.getColumnIndex(SQLHelper.STARTTIME)));
            task.setEndTime(cursor.getString(cursor.getColumnIndex(SQLHelper.ENDTIMR)));
            task.setPriority(cursor.getInt(cursor.getColumnIndex(SQLHelper.PRIORITY)));
            task.setIs_finish(cursor.getInt(cursor.getColumnIndex(SQLHelper.ISFINISH)));
        }

        cursor.close();

        return task;
    }


    public void deleteTask(Task task) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(SQLHelper.TABLE_NAME, SQLHelper.STARTTIME + "=? ", new String[]{task.getStartTime()});
    }
}

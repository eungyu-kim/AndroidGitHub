package com.example.kug00.androidgithub;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by soslt on 2017-10-14.
 */

public class DBManager extends SQLiteOpenHelper {
    public DBManager(Context context) {
        super(context, "myDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table schedule(id integer primary key autoincrement, year text, month text," + " day text, hour text, minute text, todo text, place text, explain text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean selectData(String YY, String MM, String DD) {
        SQLiteDatabase db = getWritableDatabase();
        String[] Cquery = {YY,MM,DD};
        Cursor result = db.query("schedule", null, "year=? and month=? and day=?", Cquery, null, null, null);

        // result(Cursor 객체)가 비어 있으면 false 리턴
        if (result.moveToFirst()) {
            result.close();
            return true;
        }

        result.close();
        return false;
    }

    public void delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM schedule WHERE id='" + id + "';");
        db.close();
    }

    public void updateAll(int id, String year, String month, String day, String hour, String minute, String todo ,String place, String explain ) {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE schedule SET year = '"+year+"', " +
                "month = '"+month+"'," +
                "day = '"+day+"', " +
                "hour = '"+hour+"', " +
                "minute = '"+minute+"', " +
                "todo = '"+todo+"', " +
                "place = '"+place+"', " +
                "explain = '"+explain+"' WHERE id = '"+id+"';");
        db.close();
    }
}
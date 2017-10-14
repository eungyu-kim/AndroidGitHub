package com.example.kug00.androidgithub;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by soslt on 2017-10-14.
 */

//어댑터 객체 정의
public class MonthAdapter extends BaseAdapter{
    public static final String TAG = "MonthAdapter";
    Context mContext;
    public static int oddColor = Color.rgb(225, 225, 225);
    public static int headColor = Color.rgb(12, 32, 158);
    private int selectedPosition = -1;
    private MonthItem[] items;
    private int countColumn = 7;
    int mStartDay;
    int startDay;
    int curYear;
    int curMonth;
    int firstDay;
    int lastDay;
    Calendar mCalendar;
    boolean recreateItems = false;
    boolean recreate = false;

    DBManager dbmanager;
    SQLiteDatabase sqlitedb;

    public MonthAdapter(Context context) {
        super();
        mContext = context;
        init();
    }
    private void init() {
        items = new MonthItem[7 * 6];
        mCalendar = Calendar.getInstance();
        recalculate();
        resetDayNumbers();
    }
    public void recalculate() {
        mCalendar.set(Calendar.DAY_OF_MONTH, 1);
        int dayOfWeek = mCalendar.get(Calendar.DAY_OF_WEEK);
        firstDay = getFirstDay(dayOfWeek);
        //Log.d(TAG, "firstDay : " + firstDay);
        mStartDay = mCalendar.getFirstDayOfWeek();
        curYear = mCalendar.get(Calendar.YEAR);
        curMonth = mCalendar.get(Calendar.MONTH);
        lastDay = getMonthLastDay(curYear, curMonth);
        //Log.d(TAG, "curYear : " + curYear + ", curMonth : " + curMonth + ", lastDay : " + lastDay);
        int diff = mStartDay - Calendar.SUNDAY - 1;
        startDay = getFirstDayOfWeek();
    }
    public void setPreviousMonth() {
        mCalendar.add(Calendar.MONTH, -1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }
    public void setNextMonth() {
        mCalendar.add(Calendar.MONTH, 1);
        recalculate();
        resetDayNumbers();
        selectedPosition = -1;
    }
    public void resetDayNumbers() {
        for (int i = 0; i < 42; i++) {
            int dayNumber = (i+1) - firstDay;
            if (dayNumber < 1 || dayNumber > lastDay) {
                dayNumber = 0;
            }
            items[i] = new MonthItem(dayNumber);
        }
    }
    private int getFirstDay(int dayOfWeek) {
        int result = 0;
        if (dayOfWeek == Calendar.SUNDAY) {
            result = 0;
        } else if (dayOfWeek == Calendar.MONDAY) {
            result = 1;
        } else if (dayOfWeek == Calendar.TUESDAY) {
            result = 2;
        } else if (dayOfWeek == Calendar.WEDNESDAY) {
            result = 3;
        } else if (dayOfWeek == Calendar.THURSDAY) {
            result = 4;
        } else if (dayOfWeek == Calendar.FRIDAY) {
            result = 5;
        } else if (dayOfWeek == Calendar.SATURDAY) {
            result = 6;
        }
        return result;
    }
    public int getCurYear() {
        return curYear;
    }
    public int getCurMonth() {
        return curMonth;
    }
    public int getNumColumns() {
        return 7;
    }
    public int getCount() {
        return 7 * 6;
    }
    public Object getItem(int position) {
        return items[position];
    }
    public long getItemId(int position) {
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        //Log.d(TAG, "getView(" + position + ") called.");
        MonthItemView itemView;
        if (convertView == null) {
            itemView = new MonthItemView(mContext);
        } else {
            itemView = (MonthItemView) convertView;
        }
        GridView.LayoutParams params = new GridView.LayoutParams(
                GridView.LayoutParams.MATCH_PARENT, 120);
        int rowIndex = position / countColumn;
        int columnIndex = position % countColumn;
        //Log.d(TAG, "Index : " + rowIndex + ", " + columnIndex);
        // set item data and properties
        TrueSeting(items[position].getDay());
        if (recreate) {
            itemView.setItem(items[position], 1);

        }
        else {
            itemView.setItem(items[position]);
        }
        itemView.setLayoutParams(params);
        itemView.setPadding(2, 2, 2, 2);
        itemView.setGravity(Gravity.LEFT);
        if (columnIndex == 0) {
            itemView.setTextColor(Color.RED);
        } else if (columnIndex == 6) {
            itemView.setTextColor(Color.BLUE);
        }
        else {
            itemView.setTextColor(Color.BLACK);
        }
        if (recreate) {
            itemView.setTextColor(Color.GREEN);
        }
        recreate = false;
        // set background color
        if (position == getSelectedPosition()) {
            itemView.setBackgroundColor(Color.YELLOW);
        } else {
            itemView.setBackgroundColor(Color.WHITE);
        }

        return itemView;
    }
    public static int getFirstDayOfWeek() {
        int startDay = Calendar.getInstance().getFirstDayOfWeek();
        if (startDay == Calendar.SATURDAY) {
            return Time.SATURDAY;
        } else if (startDay == Calendar.MONDAY) {
            return Time.MONDAY;
        } else {
            return Time.SUNDAY;
        }
    }
    private int getMonthLastDay(int year, int month){
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return (31);
            case 3:
            case 5:
            case 8:
            case 10:
                return (30);

            default:
                if(((year%4==0)&&(year%100!=0)) || (year%400==0) ) {
                    return (29);   // 2월 윤년계산
                } else {
                    return (28);
                }
        }
    }
    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void TrueSeting(int dd) {
        //설정 하는 중
        try {
            dbmanager = new DBManager(mContext);
            String YY = String.valueOf(curYear);
            String MM = String.valueOf((curMonth+1));
            String DD = String.valueOf((dd));
            String[] Cquery = {YY,MM,DD};
            dbmanager = new DBManager(mContext);
            sqlitedb = dbmanager.getWritableDatabase();
            Cursor cursor = sqlitedb.query("schedule", null, "year=? and month=? and day=?", Cquery, null, null, null);
            if(cursor.moveToFirst()){
                recreate = true;
            }
        }
        catch (SQLiteException e) {
        }
    }
}

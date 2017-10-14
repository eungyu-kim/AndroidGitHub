package com.example.kug00.androidgithub;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by soslt on 2017-10-14.
 */

public class AddSchedule extends AppCompatActivity{
    int year, month, day, hour, minute;
    String Tyear, Tmonth, Tday, Thour, Tminute, Ttodo, Tplace, Texplain;
    TextView TXyear, TXmonth, TXday, TXhour, TXminute;
    EditText EdtTodo, EditPlace, EditExplain;
    DBManager dbmanager;
    SQLiteDatabase sqlitedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addschedule);

        //Txt id 찾기
        TXyear = (TextView)findViewById(R.id.txtYear);
        TXmonth = (TextView)findViewById(R.id.txtMonth);
        TXday = (TextView)findViewById(R.id.txtDay);
        TXhour = (TextView)findViewById(R.id.txtHour);
        TXminute = (TextView)findViewById(R.id.txtMinute);
        EdtTodo = (EditText) findViewById(R.id.todo);
        EditPlace = (EditText)findViewById(R.id.Location);
        EditExplain = (EditText)findViewById(R.id.explain);

        //날짜 시간 현재 시간으로 초기화
        DateTimeReset();

        //달력 표시
        LinearLayout cal = (LinearLayout)findViewById(R.id.cal);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog Ddialog = new DatePickerDialog(AddSchedule.this, Dlistener, year, (month-1), day);
                Ddialog.show();
            }
        });

        //시간 표시
        LinearLayout time = (LinearLayout)findViewById(R.id.laytime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog Tdialog = new TimePickerDialog(AddSchedule.this, Tlistener, hour, minute, false);
                Tdialog.show();
            }
        });

        //이전으로 가기 버튼
        ImageButton BtnPrev = (ImageButton)findViewById(R.id.btnprev);
        BtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(AddSchedule.this, PlanActivity.class);
                startActivity(it);
            }
        });

        //추가하기 버튼
        ImageButton BtnAdd = (ImageButton)findViewById(R.id.btnadd);
        BtnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Tyear = TXyear.getText().toString();
                Tmonth = TXmonth.getText().toString();
                Tday = TXday.getText().toString();
                Thour = TXhour.getText().toString();
                Tminute = TXminute.getText().toString();
                Ttodo = EdtTodo.getText().toString();
                Tplace = EditPlace.getText().toString();
                Texplain = EditExplain.getText().toString();

                try {
                    dbmanager = new DBManager(AddSchedule.this);
                    sqlitedb = dbmanager.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("year", Tyear);
                    values.put("month", Tmonth);
                    values.put("day", Tday);
                    values.put("hour", Thour);
                    values.put("minute", Tminute);
                    values.put("todo", Ttodo);
                    values.put("place", Tplace);
                    values.put("explain", Texplain);
                    long newRoewId = sqlitedb.insert("schedule", null, values);
                    if (newRoewId != -1) {
                        sqlitedb.close();
                        dbmanager.close();
                        Intent it = new Intent(AddSchedule.this, PlanActivity.class);
                        startActivity(it);
                    }
                }
                catch (SQLiteException e) {
                    Toast.makeText(AddSchedule.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private DatePickerDialog.OnDateSetListener Dlistener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            TXyear.setText(""+year);
            TXmonth.setText(""+(monthOfYear+1));
            TXday.setText(""+dayOfMonth);
            Toast.makeText(getApplicationContext(), year + "년" + (monthOfYear+1) + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
        }
    };

    private TimePickerDialog.OnTimeSetListener Tlistener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            TXhour.setText(""+hourOfDay);
            TXminute.setText(""+minute);
            // 설정버튼 눌렀을 때
            Toast.makeText(getApplicationContext(), hourOfDay + "시 " + minute + "분", Toast.LENGTH_SHORT).show();
        }
    };

    //현재 시간 설정 초기화
    private void DateTimeReset() {
        final long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat YY = new SimpleDateFormat("yyyy");
        String GetYear = YY.format(date);
        year = Integer.parseInt(GetYear);
        SimpleDateFormat MM = new SimpleDateFormat("MM");
        String GetMonth = MM.format(date);
        month = Integer.parseInt(GetMonth);
        SimpleDateFormat DD = new SimpleDateFormat("dd");
        String GetDay = DD.format(date);
        day = Integer.parseInt(GetDay);
        SimpleDateFormat HH = new SimpleDateFormat("HH");
        String GetHour = HH.format(date);
        hour = Integer.parseInt(GetHour);
        SimpleDateFormat mm = new SimpleDateFormat("mm");
        String GetMinute = mm.format(date);
        minute = Integer.parseInt(GetMinute);
        //현재 시간으로 설정 초기화
        TXyear.setText("" + year);
        TXmonth.setText("" + month);
        TXday.setText("" + day);
        TXhour.setText("" + hour);
        TXminute.setText("" + minute);
    }
}
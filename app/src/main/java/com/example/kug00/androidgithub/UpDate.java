package com.example.kug00.androidgithub;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
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
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by soslt on 2017-10-14.
 */

public class UpDate extends AppCompatActivity {
    int year, month, day, hour, minute, primaryKey;
    String Tyear, Tmonth, Tday, Thour, Tminute, Ttodo, Tplace, Texplain;
    TextView TXyear, TXmonth, TXday, TXhour, TXminute;
    EditText EdtTodo, EditPlace, EditExplain;
    DBManager dbmanager;
    SQLiteDatabase sqlitedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_remove);
        Intent it = getIntent();
        primaryKey = it.getExtras().getInt("itprimary");
        Toast t =Toast.makeText(UpDate.this,""+primaryKey,Toast.LENGTH_SHORT);t.show();

        //Txt id 찾기
        TXyear = (TextView)findViewById(R.id.txtYear);
        TXmonth = (TextView)findViewById(R.id.txtMonth);
        TXday = (TextView)findViewById(R.id.txtDay);
        TXhour = (TextView)findViewById(R.id.txtHour);
        TXminute = (TextView)findViewById(R.id.txtMinute);
        EdtTodo = (EditText) findViewById(R.id.todo);
        EditPlace = (EditText)findViewById(R.id.Location);
        EditExplain = (EditText)findViewById(R.id.explain);

        //연월일에 맞는 정보 찾아 로드
        DateTimeRest();

        //삭제
        ImageButton btnRemove = (ImageButton)findViewById(R.id.BtnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    dbmanager = new DBManager(UpDate.this);
                    dbmanager.delete(primaryKey);
                    Intent goMain = new Intent(UpDate.this, PlanActivity.class);
                    startActivity(goMain);
                }
                catch (SQLiteException e) {
                    Toast.makeText(UpDate.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        //달력 표시
        LinearLayout cal = (LinearLayout)findViewById(R.id.cal);
        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog Ddialog = new DatePickerDialog(UpDate.this, Dlistener, year, (month-1), day);
                Ddialog.show();
            }
        });

        //시간 표시
        LinearLayout time = (LinearLayout)findViewById(R.id.laytime);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog Tdialog = new TimePickerDialog(UpDate.this, Tlistener, hour, minute, false);
                Tdialog.show();
            }
        });

        //이전으로 가기 버튼
        ImageButton BtnPrev = (ImageButton)findViewById(R.id.btnprev);
        BtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(UpDate.this, PlanActivity.class);
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
                    dbmanager = new DBManager(UpDate.this);
                    dbmanager.updateAll(primaryKey,Tyear,Tmonth,Tday,Thour,Tminute,Ttodo,Tplace,Texplain);
                    Intent it = new Intent(UpDate.this, PlanActivity.class);
                    startActivity(it);
                }
                catch (SQLiteException e) {
                    Toast.makeText(UpDate.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
            Toast.makeText(getApplicationContext(), year + "년" + monthOfYear + "월" + dayOfMonth +"일", Toast.LENGTH_SHORT).show();
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

    //연월일에 맞는 정보 찾아 로드
    private void DateTimeRest() {
        try {
            dbmanager = new DBManager(UpDate.this);
            sqlitedb = dbmanager.getWritableDatabase();
            String pri = String.valueOf(primaryKey);
            String [] prikey = {pri};
            Cursor cursor = sqlitedb.query("schedule", null, "id=?", prikey, null,
                    null, null);
            while (cursor.moveToNext()) {
                Tyear = cursor.getString(1);
                Tmonth = cursor.getString(2);
                Tday = cursor.getString(3);
                Thour = cursor.getString(4);
                Tminute = cursor.getString(5);
                Ttodo = cursor.getString(6);
                Tplace = cursor.getString(7);
                Texplain = cursor.getString(8);
            }
            sqlitedb.close();
            dbmanager.close();
        }

        catch (SQLiteException e) {
            Toast.makeText(UpDate.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        year = Integer.parseInt(Tyear);
        month = Integer.parseInt(Tmonth);
        day = Integer.parseInt(Tday);
        hour = Integer.parseInt(Thour);
        minute = Integer.parseInt(Tminute);
        TXyear.setText(Tyear);
        TXmonth.setText(Tmonth);
        TXday.setText(Tday);
        TXhour.setText(Thour);
        TXminute.setText(Tminute);
        EdtTodo.setText(Ttodo);
        EditPlace.setText(Tplace);
        EditExplain.setText(Texplain);
    }
}
package com.example.kug00.androidgithub;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;

/**
 * Created by soslt on 2017-10-14.
 */

public class PlanActivity extends AppCompatActivity{
    GridView monthView;
   // MonthAdapter monthViewAdapter;
    TextView monthText;
    int curYear, curMonth, curday;
    private static ArrayList<View> saveList;
    int num = 0;
    ListView listview ;
    ArrayList<Integer> primary;
   // DBManager dbmanager;
    SQLiteDatabase sqlitedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);

        monthView = (GridView)findViewById(R.id.monthView);
      //  monthViewAdapter = new MonthAdapter(this);

        // 아이디 가져오기
        ImageButton BackHome = (ImageButton)findViewById(R.id.BackHome);

        // 여행계획으로 화면 이동
        BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PlanActivity.this, MainActivity.class);
                startActivity(it);
            }
        });
    }
}

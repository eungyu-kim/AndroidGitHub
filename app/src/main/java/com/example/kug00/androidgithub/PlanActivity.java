package com.example.kug00.androidgithub;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by soslt on 2017-10-14.
 */

public class PlanActivity extends AppCompatActivity{
    GridView monthView;
    MonthAdapter monthViewAdapter;
    TextView monthText;
    int curYear, curMonth, curday;
    private static ArrayList<View> saveList;
    int num = 0;
    ListView listview ;
    ArrayList<Integer> primary;
    DBManager dbmanager;
    SQLiteDatabase sqlitedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);

        monthView = (GridView)findViewById(R.id.monthView);
        monthViewAdapter = new MonthAdapter(this);
        monthView.setAdapter(monthViewAdapter);

        //동적 배열 색 초기화
        saveList = new ArrayList<View>();
        primary = new ArrayList<Integer>();
        listview = (ListView) findViewById(R.id.listview1);

        // 리스너 설정
        monthView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // 현재 선택한 일자 정보 표시
                MonthItem curItem = (MonthItem) monthViewAdapter.getItem(position);
                curday = curItem.getDay();

                //리셋
                num++;
                saveList.add(view);
                if(num != 1) {
                    saveList.get(0).setBackgroundColor(Color.WHITE);
                    saveList.remove(0);
                }

                //기본키 저장 초기화
                primary.clear();
                view.setBackgroundColor(Color.BLUE);
                String YY = String.valueOf(curYear);
                String MM = String.valueOf((curMonth+1));
                String DD = String.valueOf((curday));
                String[] Cquery = {YY,MM,DD};
                dbmanager = new DBManager(PlanActivity.this);
                sqlitedb = dbmanager.getWritableDatabase();
                Cursor cursor = sqlitedb.query("schedule", null, "year=? and month=? and day=?", Cquery, null, null, null);
                ListViewAdapter adapter;
                adapter = new ListViewAdapter() ;
                while (cursor.moveToNext()) {
                    primary.add(cursor.getInt(0));
                    String Chour = cursor.getString(4);
                    String Cminute = cursor.getString(5);
                    String Ctodo = cursor.getString(6);
                    // Adapter 생성
                    adapter.addItem(Chour,Cminute,Ctodo);
                }
                // 리스트뷰 참조 및 Adapter달기
                listview.setAdapter(adapter);
                sqlitedb.close();
                dbmanager.close();
            }
        });

        monthText = (TextView) findViewById(R.id.monthText);
        setMonthText();

        // 이전 월로 넘어가는 이벤트 처리
        Button monthPrevious = (Button) findViewById(R.id.monthPrevious);
        monthPrevious.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setPreviousMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

        // 다음 월로 넘어가는 이벤트 처리
        Button monthNext = (Button) findViewById(R.id.monthNext);
        monthNext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                monthViewAdapter.setNextMonth();
                monthViewAdapter.notifyDataSetChanged();
                setMonthText();
            }
        });

        // 여행계획으로 화면 이동
        ImageButton backHome = (ImageButton)findViewById(R.id.BackHome);
        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PlanActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        //스케줄 추가
        Button addSchedule = (Button)findViewById(R.id.add);
        addSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast tMsg = Toast.makeText(PlanActivity.this, "일정등록", Toast.LENGTH_SHORT);
                tMsg.show();
                Intent it = new Intent(PlanActivity.this, AddSchedule.class);
                startActivity(it);
            }
        });

        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent it = new Intent(PlanActivity.this, UpDate.class);
                it.putExtra("itprimary", primary.get(position));
                startActivity(it);
            }
        }) ;
    }

    private void setMonthText() {
        curYear = monthViewAdapter.getCurYear();
        curMonth = monthViewAdapter.getCurMonth();
        monthText.setText(curYear + "년 " + (curMonth + 1) + "월");
    }
}

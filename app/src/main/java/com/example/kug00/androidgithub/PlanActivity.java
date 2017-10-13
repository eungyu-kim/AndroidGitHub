package com.example.kug00.androidgithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by soslt on 2017-10-12.
 */

public class PlanActivity extends AppCompatActivity{
    long mNow;
    Date mDate;

    //
    final static int ROWS = 7 ; /// 줄/행수
    final static int COLS = 7 ; /// 칸/열수

    // 전체 일자 출력
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat mFormat = new SimpleDateFormat("MM");
    SimpleDateFormat YFormat = new SimpleDateFormat("yyyy년 MM월");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);

        // 아이디 가져오기
        ImageButton BackHome = (ImageButton)findViewById(R.id.BackHome);
        TextView YearMonth = (TextView)findViewById(R.id.YearMonth);

        // 여행계획으로 화면 이동
        BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PlanActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

        // 현재 월 텍스트 출력
        YearMonth.setText(getTime(2));

    }

    // 파라미터에 따라 0일때 전체 일자 1일때 월 출력 2일때 년월 출력
    private String getTime(int x){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        if (x==0)
            return Format.format(mDate);
        else if (x==1)
            return mFormat.format(mDate);
        else if (x==2)
            return YFormat.format(mDate);
        else
            return "현재일자를 받지 못했습니다.";
    }
}

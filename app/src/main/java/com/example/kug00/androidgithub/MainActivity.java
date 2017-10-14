package com.example.kug00.androidgithub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    long mNow;
    Date mDate;
    //전체 일자 출력
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat mFormat = new SimpleDateFormat("MM");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //아이디 가져오기
        ImageButton GoToPlan = (ImageButton)findViewById(R.id.goplan);
        LinearLayout GoParty = (LinearLayout)findViewById(R.id.goparty);
        TextView Month1 = (TextView)findViewById(R.id.month1);
        TextView Month2 = (TextView)findViewById(R.id.month2);

        //여행계획으로 화면 이동
        GoToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, PlanActivity.class);
                startActivity(it);
            }
        });

        //행사정보 화면으로 이동
        GoParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, PartyActivity.class);
                startActivity(it);
            }
        });

        //현재 월 텍스트 출력
        Month1.setText(getTime(1));
        Month2.setText(getTime(1));


    }

    //파라미터에 따라 0일때 전체 일자 1일때 월 출력
    private String getTime(int x){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        if (x==0)
            return Format.format(mDate);
        else if (x==1)
            return mFormat.format(mDate);
        else
            return "현재일자를 받지 못했습니다.";
    }
}

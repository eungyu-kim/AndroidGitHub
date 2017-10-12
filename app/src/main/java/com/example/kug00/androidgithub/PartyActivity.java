package com.example.kug00.androidgithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kug00 on 2017-10-12.
 */

public class PartyActivity extends AppCompatActivity {
    long mNow;
    Date mDate;
    //전체 일자 출력
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat mFormat = new SimpleDateFormat("MM");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party);

        //아이디 찾기
        ImageButton GoMain = (ImageButton)findViewById(R.id.gomain);
        TextView Time = (TextView)findViewById(R.id.time);
        TextView TMonth1 = (TextView)findViewById(R.id.tmonth1);
        TextView TMonth2 = (TextView)findViewById(R.id.tmonth2);

        //x버튼 누를 시 이전 메인화면으로 이동
        GoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PartyActivity.this,MainActivity.class);
                startActivity(it);
            }
        });

        //월 텍스트에 출력
        TMonth1.setText(getTime(1));
        TMonth2.setText(getTime(1));

        //현재시간 출력
        Time.setText(getTime(0));
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

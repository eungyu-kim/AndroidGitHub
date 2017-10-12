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
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party);

        //아이디 찾기
        ImageButton GoMain = (ImageButton)findViewById(R.id.gomain);
        TextView Time = (TextView)findViewById(R.id.time);

        //x버튼 누를 시 이전 메인화면으로 이동
        GoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PartyActivity.this,MainActivity.class);
                startActivity(it);
            }
        });

        //현재시간 출력
        Time.setText(getTime());
    }

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }
}

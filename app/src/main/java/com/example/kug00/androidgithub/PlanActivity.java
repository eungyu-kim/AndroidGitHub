package com.example.kug00.androidgithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by soslt on 2017-10-12.
 */

public class PlanActivity extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plan);

        //아이디 가져오기
        ImageButton BackHome = (ImageButton)findViewById(R.id.BackHome);

        //여행계획으로 화면 이동
        BackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(PlanActivity.this, MainActivity.class);
                startActivity(it);
            }
        });

    }
}

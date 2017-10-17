package com.example.kug00.androidgithub;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by kug00 on 2017-10-17.
 */

public class localSearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.localsearch);

        //아이디 찾기
        ImageButton Search_back = (ImageButton)findViewById(R.id.search_back);

        //맨 상당 뒤로가기 버튼
        Search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(localSearchActivity.this,MainActivity.class);
                finish();
            }
        });
    }

}

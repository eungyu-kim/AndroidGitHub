package com.example.kug00.androidgithub;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    long mNow;
    Date mDate;
    //전체 일자 출력
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat mFormat = new SimpleDateFormat("MM");

    //메인 카테고리 스피너 값
    private final String[] Main_Category = {"지역선택", "서울", "인천", "대전", "대구","광주","부산","울산","울산","세종특별자치시",
            "경기도","강원도","충청북도","충청남도","경상북도","경상남도","전라북도","전라남도"};
//스피너 선언
    Spinner spinner_main_category;
            //지역 대분류 선택
            String Selected_Category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //아이디 가져오기
        ImageButton GoToPlan = (ImageButton)findViewById(R.id.goplan);
        ImageButton GoTmap = (ImageButton)findViewById(R.id.goTmap);
        LinearLayout GoParty = (LinearLayout)findViewById(R.id.goparty);
        TextView Month1 = (TextView)findViewById(R.id.month1);
        TextView Month2 = (TextView)findViewById(R.id.month2);
        Button Search_Btn = (Button)findViewById(R.id.main_search_btn);
        TextView title = (TextView) findViewById(R.id.Title);

        // 타이틀글꼴 변경
        Typeface typeface = Typeface.createFromAsset(getAssets(), "NanumPen.ttf");
        title.setTypeface(typeface);
        title.append("PART");

        //여행계획으로 화면 이동
        GoToPlan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, PlanActivity.class);
                startActivity(it);
            }
        });

        //길찾기로 화면 이동!! 여기서 TmapMain으로 이동해서 시작합니다!!
        // 해주신 main페이지가 TmapMain입니다.
        GoTmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, TmapMain.class);
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

        //지역검색 버튼 누를 시
        Search_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, localSearchActivity.class);
                startActivity(it);
                finish();
            }
        });

        //현재 월 텍스트 출력
        Month1.setText(getTime(1));
        Month2.setText(getTime(1));

        //메인 카테고리 선택 (지역)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Main_Category);
        spinner_main_category = (Spinner) findViewById(R.id.spinner_main_category);
        spinner_main_category.setSelection(0);
        spinner_main_category.setAdapter(adapter);

        spinner_main_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String Selected_Category = Main_Category[position];
                Log.d("result","선택값 : "+Selected_Category);
            }
            public void onNothingSelected(AdapterView<?> parent){}

        });
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
package com.example.kug00.androidgithub;

import android.content.Intent;
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
    private final String[] Soul = {"시군구 선택", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구",
    "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구" ,"은평구", "종로구", "중구", "중랑구"};
    private final String[] Incheon = {"시군구 선택", "강화군", "계양구", "남구", "남동구", "동구", "부평구", "서구", "연수구", "웅진구", "중구"};
    private final String[] Daejeon = {"시군구 선택", "대덕구", "동구", "서구", "유성구", "중구"};
    private final String[] Daegu = {"시군구 선택", "남구", "달성구", "달성군", "동구", "북구", "서구", "수성구", "중구"};
    private final String[] Gwangju = {"시군구 선택", "광산구", "남구", "동구", "북구", "서구"};
    private final String[] Busan = {"시군구 선택", "강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구", "서구", "수영구",
    "영제구", "영도구", "중구", "해운대구"};
    private final String[] Ulsan = {"시군구 선택", "중구", "남구", "동구", "북구", "울주군"};
    private final String[] Sejong = {"시군구 선택", "세종 특별 자치시"};
    private final String[] Gyeonggido = {"시군구 선택", "가평군", "고양시", "과천시", "광명시", "광주시", "구리시", "군포시",  "김포시", "남양주시", "동두천시", "부천시", "성남시",
    "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시", "연천군", "오산시", "용인시", "의왕시", "의정부시", "인천시", "파주시", "평택시", "포천시", "하남시", "화천시"};
    private final String[] Gangwondo = {"시군구 선택", "강릉시", "고성군", "동해시", "삼척시", "속초시", "양양군", "월영군", "원주시", "인제군", "정선군", "철원군", "춘천시", "태백시", "평창군",
    "홍천군", "화천군", "횡성군"};
    private final String[] Chungcheongbukdo = {"시군구 선택", "괴산군", "단양군", "보은군", "영동군", "옥천군", "음천군", "제천시", "청원군", "청주시", "충주시", "증평군"};
    private final String[] Chungcheongnamdo = {"시군구 선택", "공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "서천군", "아산시", "예산군", "천안시", "청양군", "태안군", "홍성군", "계룡시"};
    private final String[] Gyeongsangbukdo = {"시군구 선택", "경산시", "경주시", "고령군", "구미시", "군위군", "김천시", "문경시", "봉화군", "상주시", "성주시", "안동시", "영덕군",
    "영양군", "영주시", "예천군", "울릉군", "울진군", "의성군", "창도군", "청성군", "칠곡군", "포항시"};
    private final String[] Gyeongsangnamdo = {"시군구 선택", "거제시", "거창군", "고성군", "김해시", "남해군", "마산시", "밀양시", "사천시", "산청군", "양산시", "의령군", "진주시", "진해시",
            "창녕군", "창원시", "통영시", "하동군", "함안군", "함양군", "함천군"};
    private final String[] Jeollabukdo = {"시군구 선택", "고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군", "익산시", ""};
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

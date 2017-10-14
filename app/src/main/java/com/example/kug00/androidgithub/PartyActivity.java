package com.example.kug00.androidgithub;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by kug00 on 2017-10-12.
 */

public class PartyActivity extends AppCompatActivity {
    long mNow;
    Date mDate;
    //전체 일자 출력
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    SimpleDateFormat mFormat = new SimpleDateFormat("MM");

    //JSON 문자열을 저장할 문자열
    String partydata;
    //JSON에서 목록을 만들기위한 배열
    private static final String PartyID="contentid", Title = "title", address = "addr1",
            Image = "firstimage", EventStart = "eventstartdate", EventEnd="eventenddate";

    //행사 정보를 담기위한 해쉬 리스트 선언
    ArrayList<HashMap<String, String>> PartyListHash;
    //행사정보를 표현하는 리스트 뷰 선언
    ListView PartyList;
    //여행 리스트뷰 어댑터 선언
    PartyAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.party);

        //아이디 찾기
        ImageButton GoMain = (ImageButton)findViewById(R.id.gomain);
        TextView Time = (TextView)findViewById(R.id.time);
        TextView TMonth1 = (TextView)findViewById(R.id.tmonth1);
        TextView TMonth2 = (TextView)findViewById(R.id.tmonth2);
        PartyList = (ListView)findViewById(R.id.partylist);

        //행사 정보를 담기위한 해쉬 리스트 생성
        PartyListHash = new ArrayList<HashMap<String,String>>();

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

        //여행 리스트뷰 어댑터 생성
        adapter = new PartyAdapter();

        // 위에서 생성한 listview에 클릭 이벤트 핸들러 정의.
        PartyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                PartyListViewItem item = (PartyListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                Drawable iconDrawable = item.getIcon() ;

                // TODO : use item data.
            }
        }) ;



        getData("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchFestival?serviceKey=8F4FRvrVqxyBojiBd%2F7SGgGkxpeG6bUdOfq3MHZFGEvVCs2rr%2FB8QBNsjAnt4JyqUK0hHYbb64Or9bcma65Tgw%3D%3D&MobileOS=ETC&MobileApp=AppTest&arrange=A&listYN=Y&eventStartDate=20170901&_type=json");
        // 세 번째 아이템 추가.
        //adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_person_pin_circle_black_36dp),"억새축제", "주소:...") ;

        //행사정보 어댑터 리스트 뷰에 달기
        //PartyList.setAdapter(adapter);
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

    public void getData(String url){
        class GetDataJSON extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while((json = bufferedReader.readLine())!= null) {
                        sb.append(json+"\n");
                    }
                    return sb.toString().trim();
                } catch(IOException e){
                    return "다운로드 실패";
                }
            }
            @Override
            protected void onPostExecute(String result) {
                partydata=result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    protected void showList(){
        try {
            //전체 데이터 출력 partydata는 json으로 파싱 받은 데이터가 String 형식으로 되있다.
            Log.d("Result","partydata 전체데이터출력 : "+partydata);

            //Json data -> JsonOject 변환
            JSONObject jsonObj = new JSONObject(partydata);
            //JsonObject -> 하위 JsonObject Get
            String response = jsonObj.getString("response");
            Log.d("Result","response 결과"+response);

            JSONObject Response = new JSONObject(response);
            String body = Response.getString("body");
            Log.d("Result","body 결과"+body);

            JSONObject Body = new JSONObject(body);
            String items = Body.getString("items");
            Log.d("Result","items 결과"+items);

            JSONObject Item = new JSONObject(items);
            String item = Item.getString("item");
            Log.d("Result","item 결과"+item);

            //JsonObject -> JSONArray 값 추출
            JSONArray ItemArray = Item.getJSONArray("item");

            //JSONArray 길이만큼 반복
            for(int i=0;i<ItemArray.length();i++){
                JSONObject c =ItemArray.getJSONObject(i);
                String contentid = c.getString(PartyID);
                //Log.d("Result","contentid 결과"+contentid);
                String title = c.getString(Title);
                //Log.d("Result","PartyTitle 결과"+title);
                String addr1 = c.getString(address);
                //Log.d("Result","addr1 결과"+addr1);
                String firstimage ="null";
                //존재하지 않는경우 저장하지 않는다.
                if(c.has(Image))
                    firstimage = c.getString(Image);
                //Log.d("Result","firstimage 결과"+firstimage);
                String eventstartdate = c.getString(EventStart);
                //Log.d("Result","eventstartdate 결과"+eventstartdate);
                String eventenddate = c.getString(EventEnd);
                //Log.d("Result","eventenddate 결과"+eventenddate);

                HashMap<String,String> PartyHash = new HashMap<String,String>();
                PartyHash.put(PartyID,contentid);
                PartyHash.put(Title,title);
                PartyHash.put(address,addr1);
                PartyHash.put(Image,firstimage);
                PartyHash.put(EventStart,eventstartdate);
                PartyHash.put(EventEnd,eventenddate);
                PartyListHash.add(PartyHash);
                // 첫 번째 아이템 추가.
                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ic_clear_white_36dp),title, addr1) ;
                //행사정보 어댑터 리스트 뷰에 달기
                PartyList.setAdapter(adapter);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

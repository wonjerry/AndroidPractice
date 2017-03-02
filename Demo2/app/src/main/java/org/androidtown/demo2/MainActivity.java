package org.androidtown.demo2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static org.androidtown.demo2.R.id.fab;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ItemListAdapter adapter;
    private FloatingActionButton addBtn;
    private String stationName;
    private String direction;
    private String startTime;
    private String days;
    private DBManager dbManager;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("지금 온다!");
        setSupportActionBar(toolbar);

        //DBManager 객체를 생성하면서 필요한 정보를 생성자로 전달
        dbManager = new DBManager(this, "coming.db", null, 1);
        db = dbManager.getWritableDatabase();
        db.close();
        dbManager.close();

        //view, button 들 참조
        addBtn = (FloatingActionButton) findViewById(fab);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ItemListAdapter(this);

        //DB 데이터들을 다시 리스트뷰로 뿌려준다.
        initMainListView();

        //listview를 띄웠을 때, 안눌리는 문제를 해결하기 위해 설정
        listView.setFocusable(false);

        //adpter 연결
        listView.setAdapter(adapter);

        //listview 의 아이템 클릭시 이벤트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item curItem = adapter.getItem(position);
                String[] curData = curItem.getData();
                Toast.makeText(getApplicationContext(),"Selected : "+ curData[0], Toast.LENGTH_LONG).show();
            }
        });

        //검색화면 띄우기
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingView.class);
                startActivityForResult(intent,1);
            }
        });
    }

    public void initMainListView() {
        String[][] dbData;
        int index = 0;

        dbData = dbManager.load();
        if (dbData == null)
            adapter.cleanItem();
        else {
            while (index < dbData.length) {
                adapter.addItem(new Item(dbData[index][0], dbData[index][1], dbData[index][2], dbData[index][3]));
                index++;
            }
        }//else
    }//onView

    //새로운 아이템을 추가하기 위한 메소드
    private void addNewItem(String stationName, String direction, String startTime, String duringTime){
        adapter.addItem(new Item(stationName , direction , startTime, duringTime));
    }

    //정보가 업데이트 된 후에 listView를 업데이트 하기 위한 메소드
    private void refresh(){
        adapter.notifyDataSetChanged();
    }

    // 알람 정보 세팅이 끝나면 아이템을 추가함
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1){
            boolean distinctCheck;
            stationName = data.getExtras().getString("stationName");
            direction = data.getExtras().getString("direction");
            startTime = data.getExtras().getString("startTime");
            days = data.getExtras().getString("days");

            //DB안의 데이터와 중복검사
            distinctCheck = dbManager.distinct(stationName, direction, startTime, days);
            if (distinctCheck == false) {
                dbManager.insert(stationName, direction, startTime, days);
                addNewItem(stationName , direction , startTime, days);
            }
            refresh();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
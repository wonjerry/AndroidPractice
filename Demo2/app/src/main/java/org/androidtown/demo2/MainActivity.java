package org.androidtown.demo2;

import android.content.Intent;
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

    ListView listView;
    ItemListAdapter adapter;
    FloatingActionButton addBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("지금 온다!");
        setSupportActionBar(toolbar);

        //view, button 들 참조
        addBtn = (FloatingActionButton) findViewById(fab);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ItemListAdapter(this);

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

        //아이템 추가 및 알람생성
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SettingView.class);
                startActivity(intent);
                //addNewItem();
                //refresh();
            }
        });

    }

    //새로운 아이템을 추가하기 위한 메소드
    private void addNewItem(){
        adapter.addItem(new Item("하계","공릉","8:30 am","금요일"));
        adapter.addItem(new Item("건대입구","어린이대공원","7:30 am","월요일"));
    }

    //정보가 업데이트 된 후에 listView를 업데이트 하기 위한 메소드
    private void refresh(){
        adapter.notifyDataSetChanged();
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
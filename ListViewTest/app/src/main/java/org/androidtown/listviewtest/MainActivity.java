package org.androidtown.listviewtest;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView1;
    IconTextListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView1 = (ListView) findViewById(R.id.listView1);

        adapter = new IconTextListAdapter(this);

        Resources res = getResources();

        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05),"추억의테트리스","30,000 다운로드","900원"));
        adapter.addItem(new IconTextItem(res.getDrawable(R.drawable.icon05),"지하철노선도","26,000 다운로드","1500원"));

        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                IconTextItem curItem = (IconTextItem) adapter.getItem(position);
                String[] curData = curItem.getData();
                Toast.makeText(getApplicationContext(),"Selected : "+ curData[0], Toast.LENGTH_LONG).show();
            }
        });
    }
}
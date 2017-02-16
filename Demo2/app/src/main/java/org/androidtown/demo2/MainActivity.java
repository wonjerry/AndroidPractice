package org.androidtown.demo2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ItemListAdapter adapter;
    android.support.design.widget.FloatingActionButton addBtn;
    Switch enableBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        enableBtn = (Switch) findViewById(R.id.enableBtn);
        addBtn = (android.support.design.widget.FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.listView);
        adapter = new ItemListAdapter(this);

        listView.setFocusable(false);

        addNewItem();

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item curItem = adapter.getItem(position);
                String[] curData = curItem.getData();
                Toast.makeText(getApplicationContext(),"Selected : "+ curData[0], Toast.LENGTH_LONG).show();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_LONG).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(new Item("하계","공릉","8:30 am","금요일"));
                refresh();
            }
        });

    }

    private void addNewItem(){
        adapter.addItem(new Item("하계","공릉","8:30 am","금요일"));
        adapter.addItem(new Item("건대입구","어린이대공원","7:30 am","월요일"));
    }

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
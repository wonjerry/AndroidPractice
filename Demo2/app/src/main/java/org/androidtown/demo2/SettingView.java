package org.androidtown.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by dldnj on 2017-02-19.
 */

public class SettingView extends Activity {

    String[] stations;

    AutoCompleteTextView autoTextView;
    ListView settingListView;
    Button okBtn;
    SettingItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        stations = getResources().getStringArray(R.array.stationNames);

        settingListView = (ListView) findViewById(R.id.settingListView);
        okBtn = (Button) findViewById(R.id.okBtn);
        autoTextView = (AutoCompleteTextView) findViewById(R.id.autoStationName);

        adapter = new SettingItemListAdapter(this);
        ArrayAdapter<String> autoTextadapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,stations);

        autoTextView.setAdapter(autoTextadapter);
        settingListView.setAdapter(adapter);

        settingListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SettingItem curItem = adapter.getItem(position);
                String[] curData = curItem.getData();
                Toast.makeText(getApplicationContext(),"Selected : "+ curData[0], Toast.LENGTH_LONG).show();
            }
        });

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.cleanItem();
                adapter.addItem(new SettingItem(autoTextView.getText().toString() , "랄라"));
                refresh();
            }
        });
    }

    private void addNewItem(){

    }

    private void refresh(){
        adapter.notifyDataSetChanged();
    }
}

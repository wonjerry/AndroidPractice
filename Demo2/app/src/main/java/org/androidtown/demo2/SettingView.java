package org.androidtown.demo2;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by dldnj on 2017-02-19.
 */

public class SettingView extends Activity {

    String[] stations;
    Handler handler;

    AutoCompleteTextView autoTextView;
    ListView settingListView;
    Button okBtn;
    SettingItemListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settingtoolbar);
        toolbar.setTitle("역 검색");

        stations = getResources().getStringArray(R.array.stationNames);

        settingListView = (ListView) findViewById(R.id.settingListView);
        okBtn = (Button) findViewById(R.id.okBtn);
        autoTextView = (AutoCompleteTextView) findViewById(R.id.autoStationName);

        handler = new Handler();
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
                //adapter.cleanItem();
                //adapter.addItem(new SettingItem(autoTextView.getText().toString() , "랄라"));
                String encodedStationName = null;
                try {
                    encodedStationName = URLEncoder.encode(autoTextView.getText().toString().trim(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String urlstr = "http://swopenapi.seoul.go.kr/api/subway/476f787954646c64313039455278624d/xml/stationSection/1/10/"+encodedStationName+"/";
                SubwayCommingInfoThread thread = new SubwayCommingInfoThread(urlstr);
                thread.start();
            }
        });
    }
    private void refresh(){
        adapter.notifyDataSetChanged();
    }

    private void addNewSettingItem(String startStationName, String finishStationName){
        adapter.addItem(new SettingItem(startStationName , finishStationName));
    }

    private void cleanSettingItem(){
        adapter.cleanItem();
    }


    class SubwayCommingInfoThread extends Thread {
        String urlStr;

        public SubwayCommingInfoThread(String inStr){
            urlStr = inStr;
        }

        @Override
        public void run() {
            try{
                final ArrayList<String> output = request(urlStr);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cleanSettingItem();
                        for(String e : output){
                            addNewSettingItem(autoTextView.getText().toString(), e+" 방면");
                        }
                        refresh();
                    }
                });

            }catch (Exception e){

            }
        }

        private Document parseXML(InputStream stream) throws Exception{
            DocumentBuilderFactory objDocumentBuilderFactory = null;
            DocumentBuilder objDocumentBuilder = null;
            Document doc = null;

            try{
                objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
                doc = objDocumentBuilder.parse(stream);

            }catch (Exception e){

            }
            return doc;
        }


        private ArrayList<String> request(String urlStr){

            StringBuilder output = new StringBuilder();
            ArrayList<String> results = new ArrayList<String>();
            try{
                URL url = new URL(urlStr);
                URLConnection connection = url.openConnection();
                HttpURLConnection conn = (HttpURLConnection) connection;
                if(conn != null){
                    conn.setConnectTimeout(1000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);

                    int resCode = conn.getResponseCode();
                    Log.e("hi:",""+resCode);
                    if (resCode == HttpURLConnection.HTTP_OK){
                        Document doc = parseXML(conn.getInputStream());
                        int total = Integer.parseInt(doc.getElementsByTagName("total").item(0).getTextContent());
                        NodeList finishStationName = doc.getElementsByTagName("statnTnm");
                        for(int i=0; i<total; i++){
                            results.add(finishStationName.item(i).getTextContent());
                        }
                    }
                }
            }catch (Exception e){
                Log.e("SampleHTTP", "Exception in processing response",e);
                e.printStackTrace();
            }
            return results;
        }

    }
}



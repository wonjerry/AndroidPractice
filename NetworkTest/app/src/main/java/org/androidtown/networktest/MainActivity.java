package org.androidtown.networktest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {

    Button requestBtn;
    EditText input;
    TextView result;

    public static String defaultUrl = "https://m.naver.com";
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.setProperty("http.keepAlive","false");
        requestBtn = (Button) findViewById(R.id.requestBtn);
        input = (EditText) findViewById(R.id.input1);
        result = (TextView) findViewById(R.id.txtMsg);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encodedStationName = null;

                try {
                    encodedStationName = URLEncoder.encode(input.getText().toString().trim(),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                String urlstr = "http://swopenapi.seoul.go.kr/api/subway/476f787954646c64313039455278624d/xml/realtimeStationArrival/1/10/"+encodedStationName+"/";
                ConnectThread thread = new ConnectThread(urlstr);
                thread.start();
            }
        });

    }

    class ConnectThread extends Thread {
        String urlStr;
        public ConnectThread(String inStr){
            urlStr = inStr;
        }
        @Override
        public void run() {
            try{
                final String output = request(urlStr);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(output);
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

            }catch (Exception e){}
            return doc;
        }


        public String request(String urlStr){

            StringBuilder output = new StringBuilder();
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
                        NodeList arrivalInformaltion = doc.getElementsByTagName("arvlMsg2");
                        NodeList subwayDirection = doc.getElementsByTagName("trainLineNm");

                        for(int i=0; i<total; i++){
                            output.append(subwayDirection.item(i).getTextContent()+'\n'
                                    +arrivalInformaltion.item(i).getTextContent()+'\n'
                                    +"------------------------------------"
                                    +'\n');
                        }
                    }
                }
            }catch (Exception e){
                Log.e("SampleHTTP", "Exception in processing response",e);
                e.printStackTrace();
            }
            return output.toString();
        }
    }
}

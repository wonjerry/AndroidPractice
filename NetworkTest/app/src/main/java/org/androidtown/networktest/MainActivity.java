package org.androidtown.networktest;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

        requestBtn = (Button) findViewById(R.id.requestBtn);
        input = (EditText) findViewById(R.id.input1);
        result = (TextView) findViewById(R.id.txtMsg);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlstr = input.getText().toString().trim();

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

        public String request(String urlStr){
            StringBuilder output = new StringBuilder();

            try{
                URL url = new URL(urlStr);

                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                if(conn != null){
                    conn.setConnectTimeout(1000);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

                    int resCode = conn.getResponseCode();
                    if (resCode == HttpURLConnection.HTTP_OK){
                        BufferedReader reader = new BufferedReader( new InputStreamReader(conn.getInputStream()));
                        String line = null;
                        while(true){
                            line = reader.readLine();
                            if(line == null){
                                break;
                            }
                            output.append(line+'\n');
                        }
                        reader.close();
                        conn.disconnect();
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

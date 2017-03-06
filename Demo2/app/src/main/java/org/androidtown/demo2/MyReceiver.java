package org.androidtown.demo2;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

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
 * Created by jerry on 2017-02-17.
 */

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver(){

    }
    Calendar calendar;
    Handler handler;
    Notification.Builder mBuilder;
    NotificationManager nm;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        calendar = Calendar.getInstance();
        mBuilder = new Notification.Builder(context);
        handler = new Handler();
        String encodedStationName = null;
        try {
            encodedStationName = URLEncoder.encode("하계","UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String urlstr = "http://swopenapi.seoul.go.kr/api/subway/476f787954646c64313039455278624d/xml/stationSection/1/10/"+encodedStationName+"/";
        SubwayCommingInfoThread thread = new SubwayCommingInfoThread(urlstr);
        thread.start();
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
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                        mBuilder.setTicker("hi");
                        mBuilder.setContentTitle("알리미");

                        for(String e : output) sb.append(e);

                        mBuilder.setContentText(sb.toString());

                        Log.e("hi : ",calendar.getTime().toString());

                        mBuilder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
                        mBuilder.setAutoCancel(true);
                        nm.notify(111,mBuilder.build());
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

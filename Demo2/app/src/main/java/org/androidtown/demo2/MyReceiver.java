package org.androidtown.demo2;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
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
 * Created by jerry on 2017-02-17.
 */

public class MyReceiver extends BroadcastReceiver {

    private Calendar calendar;
    private Handler handler;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager nm;
    private RemoteViews contentView;

    int repeatingAlarmId;
    private Context mContext;
    private String stationName;
    private String direction;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext = context;
        this.nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.calendar = Calendar.getInstance();
        this.handler = new Handler();
        this.repeatingAlarmId = intent.getExtras().getInt("id");
        this.stationName = intent.getExtras().getString("stationName");
        this.direction = intent.getExtras().getString("direction");

        String encodedStationName = null;
        try {
            encodedStationName = URLEncoder.encode(stationName,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String urlstr = "http://swopenapi.seoul.go.kr/api/subway/476f787954646c64313039455278624d/xml/realtimeStationArrival/1/10/"+encodedStationName+"/";
        SubwayCommingInfoThread thread = new SubwayCommingInfoThread(urlstr);
        thread.start();
    }

    private void repeatingAlarmCancel(){
        AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent receiverIntent = new Intent(mContext, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mContext,repeatingAlarmId,receiverIntent,PendingIntent.FLAG_NO_CREATE);

        if(pendingIntent != null){
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }else{
            Toast.makeText(mContext,"null 입니다.",Toast.LENGTH_LONG).show();
        }
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
                        /*
                        mBuilder.setVisibility(Notification.VISIBILITY_PUBLIC);
                        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
                        mBuilder.setTicker("hi");
                        mBuilder.setContentTitle("지금온다!");
                        */
                        contentView = new RemoteViews(mContext.getPackageName(), R.layout.custom_notification);
                        contentView.setImageViewResource(R.id.weatherImage,R.drawable.sunnyimage);
                        contentView.setTextViewText(R.id.setInformation,stationName + " " + direction);

                        for(String e : output) sb.append(e + " ");

                        contentView.setTextViewText(R.id.arrivalInfomation,sb.toString());
                        mBuilder = new NotificationCompat.Builder(mContext);
                        mBuilder.setSmallIcon(android.R.drawable.stat_notify_more);
                        mBuilder.setContent(contentView);

                        nm.notify(repeatingAlarmId,mBuilder.build());
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
                        NodeList arrivalInformaltion = doc.getElementsByTagName("arvlMsg2");
                        NodeList subwayDirection = doc.getElementsByTagName("trainLineNm");

                        for(int i=0; i<total; i++){
                            if(direction.equals(subwayDirection.item(i).getTextContent())){
                                results.add(arrivalInformaltion.item(i).getTextContent());
                            }
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

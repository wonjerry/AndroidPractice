package org.androidtown.alarmmanagertest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    Button createAlarmBtn;
    Button releaseAlarmBtn;
    AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAlarmBtn = (Button) findViewById(R.id.createAlarmBtn);
        releaseAlarmBtn = (Button) findViewById(R.id.releaseAlarmBtn);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        createAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent receiverIntent = new Intent(MainActivity.this, AlarmStartReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,1,receiverIntent,0);
                AlarmManagerUtil.getInstance(alarmManager).setOnceAlarm(11,18,pendingIntent);
            }
        });

        releaseAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receiverIntent = new Intent(MainActivity.this, MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,22,receiverIntent,PendingIntent.FLAG_NO_CREATE);
                if(pendingIntent != null){
                    alarmManager.cancel(pendingIntent);
                    pendingIntent.cancel();
                }else{
                    Toast.makeText(getApplicationContext(),"null 입니다.",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}

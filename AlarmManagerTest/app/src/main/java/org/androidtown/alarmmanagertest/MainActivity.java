package org.androidtown.alarmmanagertest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        createAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                Intent receiverIntent = new Intent(MainActivity.this, MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,receiverIntent,0);

                long period = 1000*5;
                long after = 1000*5;
                long t = SystemClock.elapsedRealtime();

                alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, t+after, period, pendingIntent);
            }
        });

        releaseAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent receiverIntent = new Intent(MainActivity.this, MyReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,receiverIntent,0);
                alarmManager.cancel(pendingIntent);
            }
        });
    }
}

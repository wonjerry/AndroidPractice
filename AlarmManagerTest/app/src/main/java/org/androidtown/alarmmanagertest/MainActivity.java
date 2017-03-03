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

        createAlarmBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                Intent receiverIntent = new Intent(MainActivity.this, AlarmStartReceiver.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,receiverIntent,0);
                setOnceAlarm(1,27,pendingIntent);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setOnceAlarm(int hourOfDay, int minute, PendingIntent alarmPendingIntent){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,getTriggerAtMillis(hourOfDay, minute),alarmPendingIntent);
            //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,getTriggerAtMillis(hourOfDay, minute),60*1000,alarmPendingIntent);
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP,getTriggerAtMillis(hourOfDay, minute),alarmPendingIntent);
        }else{
            alarmManager.set(AlarmManager.RTC_WAKEUP,getTriggerAtMillis(hourOfDay, minute),alarmPendingIntent);
        }

    }

    private long getTriggerAtMillis(int hourOfDay, int minute){
        GregorianCalendar currentCalendar = (GregorianCalendar) GregorianCalendar.getInstance();
        int currentHourOfDay = currentCalendar.get(GregorianCalendar.HOUR_OF_DAY);
        int currentMinute = currentCalendar.get(GregorianCalendar.MINUTE);

        if(currentHourOfDay < hourOfDay || (currentHourOfDay == hourOfDay && currentMinute < minute))
            return getTimeMillis(false, hourOfDay, minute);
        else
            return getTimeMillis(true, hourOfDay, minute);
    }

    private long getTimeMillis(boolean tomorrow, int hourOfDay, int minute){
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        if(tomorrow) calendar.add(GregorianCalendar.DAY_OF_YEAR, 1);

        calendar.set(GregorianCalendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(GregorianCalendar.MINUTE, minute);
        calendar.set(GregorianCalendar.SECOND, 0);
        calendar.set(GregorianCalendar.MILLISECOND, 0);

        return calendar.getTimeInMillis();
    }
}

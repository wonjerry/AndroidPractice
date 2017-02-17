package org.androidtown.demo2;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;

/**
 * Created by dldnj on 2017-02-17.
 */
public class AlarmController {
    private Context context;

    public AlarmController(Context context) {
        this.context = context;
    }

    public void Alarm(){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(MainActivity.this);
    }
}

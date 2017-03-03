package org.androidtown.alarmmanagertest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by jerry on 2017-02-26.
 */

public class AlarmScheduler {

    AlarmManager alarmManager;
    AlarmManagerUtil alarmManagerUtil;
    Context context;
    int BASE_REQUEST_CODE;

    public AlarmScheduler(Context context , AlarmManager alarmManager){
        this.alarmManager = alarmManager;
        //this.alarmManagerUtil = new AlarmManagerUtil(alarmManager);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void registerAlarm(int dayInt, int hourOfDay, int minute){
        alarmManagerUtil.setOnceAlarm(hourOfDay,minute, getRepeatingAlarmPendingIntent(dayInt));
    }

    private PendingIntent getRepeatingAlarmPendingIntent(int dayInt){

        return PendingIntent.getBroadcast(context, 0, null, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}

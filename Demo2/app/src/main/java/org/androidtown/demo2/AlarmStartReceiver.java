package org.androidtown.demo2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.GregorianCalendar;

/**
 * Created by jerry on 2017-03-04.
 */

public class AlarmStartReceiver extends BroadcastReceiver {
    AlarmManager alarmManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent receiverIntent = new Intent(context, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,22,receiverIntent,0);
        GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar.getInstance();

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),60*1000,pendingIntent);
    }
}

package org.androidtown.alarmmanagertest;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;

import static android.R.attr.id;

/**
 * Created by jerry on 2017-02-17.
 */

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver(){

    }
    Calendar calendar;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public void onReceive(Context context, Intent intent) {
            NotificationManager nm = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
            calendar = Calendar.getInstance();

            Notification.Builder mBuilder = new Notification.Builder(context);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setTicker("hi");
            mBuilder.setContentTitle("알리미");
            mBuilder.setContentText(calendar.getTime().toString() + id);


            mBuilder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);

        mBuilder.setAutoCancel(true);
        nm.notify(111,mBuilder.build());
    }
}

package org.androidtown.alarmmanagertest;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.GregorianCalendar;

/**
 * Created by jerry on 2017-02-26.
 */

public class AlarmManagerUtil {
    AlarmManager alarmManager;

    public AlarmManagerUtil(AlarmManager alarmManager){
        this.alarmManager = alarmManager;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void setOnceAlarm(int hourOfDay, int minute, PendingIntent alarmPendingIntent){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,getTriggerAtMillis(hourOfDay, minute),alarmPendingIntent);
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

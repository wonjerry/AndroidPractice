package org.androidtown.alarmmanagertest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by jerry on 2017-02-26.
 */

public class AlarmReciever extends WakefulBroadcastReceiver {

    public static final String KEY_DAY_INT = "AlarmReceiver.KEY_DAY_INT";

    @Override
    public void onReceive(Context context, Intent intent) {
        int dayInt = intent.getIntExtra(KEY_DAY_INT, -1);

    }
}

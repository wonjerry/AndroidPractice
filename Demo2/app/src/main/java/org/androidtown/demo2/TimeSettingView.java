package org.androidtown.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by dldnj on 2017-02-23.
 */

public class TimeSettingView extends Activity{
    String stationName;
    String direction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settingtime);

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            stationName = intent.getExtras().getString("stationName");
            direction = intent.getExtras().getString("direction");
        }
    }
}

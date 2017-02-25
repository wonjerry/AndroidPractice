package org.androidtown.demo2;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.ToggleButton;

/**
 * Created by dldnj on 2017-02-23.
 */

public class TimeSettingView extends Activity implements View.OnClickListener{
    String stationName;
    String direction;
    boolean[] resultDays;

    TimePicker timePicker;
    Button cancelBtn;
    Button saveBtn;
    ToggleButton mon;
    ToggleButton tue;
    ToggleButton wed;
    ToggleButton thr;
    ToggleButton fri;
    ToggleButton sat;
    ToggleButton sun;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.settingtime);

        timePicker = (TimePicker) findViewById(R.id.timePicker2);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);
        mon = (ToggleButton) findViewById(R.id.mon);
        tue = (ToggleButton) findViewById(R.id.tue);
        wed = (ToggleButton) findViewById(R.id.wed);
        thr = (ToggleButton) findViewById(R.id.thr);
        fri = (ToggleButton) findViewById(R.id.fri);
        sat = (ToggleButton) findViewById(R.id.sat);
        sun = (ToggleButton) findViewById(R.id.sun);

        resultDays = new boolean[7];

        Intent intent = getIntent();
        if(intent.getExtras() != null){
            stationName = intent.getExtras().getString("stationName");
            direction = intent.getExtras().getString("direction");
        }

        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thr.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);
        sun.setOnClickListener(this);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("stationName" , stationName);
                intent.putExtra("direction" , direction);
                intent.putExtra("startTime" , getTimePickerInfo());
                intent.putExtra("days" , getDaysInfo());
                setResult(1,intent);
                finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private String getTimePickerInfo(){
        int hour = timePicker.getHour();
        int min = timePicker.getMinute();

        if(hour > 12) return "오후 "+ (hour -= 12)  + " 시 "+ min + " 분";
        if(hour == 12) return "오후 "+ hour + " 시 "+ min + " 분";
        return "오전 "+ hour + " 시 "+ min + " 분";
    }

    private String getDaysInfo(){
        StringBuilder str = new StringBuilder();
        for(int i =0; i< 7; i++){
            if(resultDays[i]){
                switch (i){
                    case 0:
                        str.append("월 ");
                        break;
                    case 1:
                        str.append("화 ");
                        break;
                    case 2:
                        str.append("수 ");
                        break;
                    case 3:
                        str.append("목 ");
                        break;
                    case 4:
                        str.append("금 ");
                        break;
                    case 5:
                        str.append("토 ");
                        break;
                    case 6:
                        str.append("일 ");
                        break;
                }
            }
        }

        return str.toString().trim();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mon:
                resultDays[0] = mon.isChecked();
                break;
            case R.id.tue:
                resultDays[1] = tue.isChecked();
                break;
            case R.id.wed:
                resultDays[2] = wed.isChecked();
                break;
            case R.id.thr:
                resultDays[3] = thr.isChecked();
                break;
            case R.id.fri:
                resultDays[4] = fri.isChecked();
                break;
            case R.id.sat:
                resultDays[5] = sat.isChecked();
                break;
            case R.id.sun:
                resultDays[6] = sun.isChecked();
                break;
        }
    }
}

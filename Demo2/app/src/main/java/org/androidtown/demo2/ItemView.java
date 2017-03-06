package org.androidtown.demo2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jerry on 2017-02-16.
 */

public class ItemView extends LinearLayout {

    private TextView stationName;
    private TextView direction;
    private TextView startTime;
    private TextView days;
    private Switch enableBtn;

    public ItemView(final Context context, final Item item) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item , this, true);

        enableBtn = (Switch)findViewById(R.id.enableBtn);
        enableBtn.setFocusable(false);

        //enable button이 check 되면 알람 활성화 uncheck 되면 알람 cancel
        enableBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            // enable 버튼의 상태에 따라서 알람 설정 또는 해제한다.
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                item.setEnalble(isChecked);
                if(isChecked){
                    Intent receiverIntent = new Intent(context, AlarmStartReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,item.getId(),receiverIntent,0);
                    AlarmManagerUtil.getInstance(alarmManager).setOnceAlarm(11,18,pendingIntent);
                    Toast.makeText(context, "활성화" , Toast.LENGTH_LONG).show();
                }else{
                    Intent receiverIntent = new Intent(context, MyReceiver.class);
                    PendingIntent pendingIntent = PendingIntent.getBroadcast(context,item.getId(),receiverIntent,PendingIntent.FLAG_NO_CREATE);
                    if(pendingIntent != null){
                        alarmManager.cancel(pendingIntent);
                        pendingIntent.cancel();
                    }else{
                        Toast.makeText(context,"null 입니다.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        stationName = (TextView) findViewById(R.id.station);
        stationName.setText(item.getData(0));

        direction = (TextView) findViewById(R.id.direction);
        direction.setText(item.getData(1));

        startTime = (TextView) findViewById(R.id.startTime);
        startTime.setText(item.getData(2));

        days = (TextView) findViewById(R.id.days);
        days.setText(item.getData(3));
    }

    public void setEnableBtn(boolean enable){
        enableBtn.setChecked(enable);
    }

    public void setText(int index, String data){

        if(index == 0){
            stationName.setText(data);
        }else if(index == 1){
            direction.setText(data);
        }else if(index == 2){
            startTime.setText(data);
        }else if(index == 3){
            days.setText(data);
        }else{
            throw new IllegalArgumentException();
        }
    }
}

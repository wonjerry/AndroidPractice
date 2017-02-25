package org.androidtown.demo2;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by jerry on 2017-02-21.
 */

public class SettingItemView extends LinearLayout {


    private TextView stationName;
    private TextView direction;
    private Button alarmSettingBtn;

    public SettingItemView(final Context context, final SettingItem item) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.settingitem , this, true);

        stationName = (TextView) findViewById(R.id.settingStationName);
        stationName.setText(item.getData(0));

        direction = (TextView) findViewById(R.id.settingDirection);
        direction.setText(item.getData(1));

        alarmSettingBtn = (Button) findViewById(R.id.alarmSettingBtn);
        alarmSettingBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,TimeSettingView.class);
                intent.putExtra("stationName", stationName.getText().toString());
                intent.putExtra("direction" , direction.getText().toString());
                ((SettingView)context).startActivityForResult(intent, 1);
            }
        });

    }

    public void setText(int index, String data){

        if(index == 0){
            stationName.setText(data);
        }else if(index == 1){
            direction.setText(data);
        }else{
            throw new IllegalArgumentException();
        }
    }
}

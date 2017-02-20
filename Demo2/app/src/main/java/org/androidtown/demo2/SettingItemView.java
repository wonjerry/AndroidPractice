package org.androidtown.demo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by jerry on 2017-02-21.
 */

public class SettingItemView extends LinearLayout {


    private TextView stationName;
    private TextView direction;

    public SettingItemView(final Context context, final SettingItem item) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.settingitem , this, true);

        stationName = (TextView) findViewById(R.id.settingStationName);
        stationName.setText(item.getData(0));

        direction = (TextView) findViewById(R.id.settingDirection);
        direction.setText(item.getData(1));

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

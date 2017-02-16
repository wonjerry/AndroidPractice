package org.androidtown.demo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

/**
 * Created by jerry on 2017-02-16.
 */

public class ItemView extends LinearLayout {

    private TextView stationName;
    private TextView direction;
    private TextView startTime;
    private TextView days;
    private Switch enableBtn;

    public ItemView(Context context, Item item) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item , this, true);

        enableBtn = (Switch)findViewById(R.id.enableBtn);
        enableBtn.setFocusable(false);

        stationName = (TextView) findViewById(R.id.station);
        stationName.setText(item.getData(0));

        direction = (TextView) findViewById(R.id.direction);
        direction.setText(item.getData(1));

        startTime = (TextView) findViewById(R.id.startTime);
        startTime.setText(item.getData(2));

        days = (TextView) findViewById(R.id.days);
        days.setText(item.getData(3));
    }

    public Switch getEnableBtn() {
        return enableBtn;
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

package org.androidtown.demo2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by jerry on 2017-02-16.
 */

public class ItemListAdapter extends BaseAdapter {

    private Context mConText;
    private ArrayList<Item> mItems = new ArrayList<Item>();

    public ItemListAdapter(Context mConText) {
        this.mConText = mConText;
    }

    //public void addItem(String stationName, String direction, String startTime, String days){
    //    mItems.add(new Item(stationName, direction, startTime, days));
    //}

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void addItem(int id, String stationName, String direction, int startTimeHour, int startTimeMinute , String days, int enable){

        AlarmManager alarmManager = (AlarmManager) mConText.getSystemService(Context.ALARM_SERVICE);
        Intent receiverIntent = new Intent(mConText, AlarmStartReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mConText,id,receiverIntent,0);
        AlarmManagerUtil.getInstance(alarmManager).setOnceAlarm(startTimeHour,startTimeMinute,pendingIntent);

        mItems.add(new Item(id, stationName, direction, startTimeHour, startTimeMinute, days, enable));
    }

    public void cleanItem(){
        mItems.clear();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemView itemView;

        //성능을 향상시키기 위해서 아래 주석부분을 해야하긴 하는데, 이렇게하면 enable 버튼이 item의 상태와 관계없이 자기맘대로된다.
        //if(convertView == null) itemView = new ItemView(mConText, mItems.get(position));
        //else itemView = (ItemView) convertView;

        itemView = new ItemView(mConText, mItems.get(position));

        itemView.setText(0,mItems.get(position).getData(0));
        itemView.setText(1,mItems.get(position).getData(1));
        itemView.setText(2,mItems.get(position).getData(2));
        itemView.setText(3,mItems.get(position).getData(3));

        return itemView;
    }
}

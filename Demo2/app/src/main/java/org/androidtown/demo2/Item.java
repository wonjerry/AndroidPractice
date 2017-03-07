package org.androidtown.demo2;

import java.util.Arrays;

/**
 * Created by jerry on 2017-02-16.
 */

public class Item {
    private String[] mData;
    private int id = -1;
    private int startTimeHour = 0;
    private int startTimeMinute = 0;
    private boolean enable;

    public Item(String[] obj) {
        this.mData = obj;
        this.enable = true;
    }

    public Item(String stationName, String direction, String startTime, String duringTime) {
        this.mData = new String[4];

        mData[0] = stationName;
        mData[1] = direction;
        mData[2] = startTime;
        mData[3] = duringTime;
    }

    public Item(int id, String stationName, String direction, int startTimeHour , int startTimeMinute, String days, int enable) {

        setId(id);

        this.mData = new String[4];

        mData[0] = stationName;
        mData[1] = direction;

        if(startTimeHour > 12) mData[2] =  "오후 "+ (startTimeHour - 12)  + " 시 "+ startTimeMinute + " 분";
        else if(startTimeHour == 12) mData[2] =  "오후 "+ startTimeHour + " 시 "+ startTimeMinute + " 분";
        else mData[2] = "오전 "+ startTimeHour + " 시 "+ startTimeMinute + " 분";

        setStartTimeHour(startTimeHour);
        setStartTimeMinute(startTimeMinute);

        mData[3] = days;

        if(enable == 0) this.enable = false;
        else this.enable = true;

    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index) {
        if (mData == null || index >= mData.length) return null;

        return mData[index];
    }

    public boolean getEnable() {
        return enable;
    }

    public int getId() {
        return this.id;
    }

    public int getStartTimeHour() {
        return this.startTimeHour;
    }

    public int getStartTimeMinute() {
        return this.startTimeMinute;
    }

    public void setData(String[] obj) {
        mData = obj;
    }

    public void setEnalble(boolean enalble) {
        this.enable = enalble;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setData(String stationName, String direction, String startTime, String days) {
        this.mData = new String[4];

        mData[0] = stationName;
        mData[1] = direction;
        mData[2] = startTime;
        mData[3] = days;
    }

    public void setStartTimeHour(int hour) {
        this.startTimeHour = hour;
    }

    public void setStartTimeMinute(int minute) {
        this.startTimeMinute = minute;
    }
}

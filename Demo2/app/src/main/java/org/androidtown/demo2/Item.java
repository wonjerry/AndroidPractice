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
    private  boolean enalble;

    public Item(String[] obj) {
        this.mData = obj;
        this.enalble = true;
    }

    public Item(String stationName, String direction, String startTime, String duringTime) {
        this.mData = new String[4];

        mData[0] = stationName;
        mData[1] = direction;
        mData[2] = startTime;
        mData[3] = duringTime;
    }

    //upgrade version for checking enable and id
    public Item(String id, String stationName, String direction, String startTime, String duringTime, String enable) {
        this.mData = new String[4];
        mData[0] = stationName;
        mData[1] = direction;
        mData[2] = startTime;
        mData[3] = duringTime;

        this.id = Integer.parseInt(id);

        if("0".equals(enable)){
            this.enalble = false;
        }else{
            this.enalble = true;
        }
    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index){
        if(mData == null || index >= mData.length) return null;

        return mData[index];
    }

    public boolean getEnable(){
        return enalble;
    }

    public int getId(){
        return this.id;
    }

    public int getStartTimeHour(){
        return this.startTimeHour;
    }

    public int getStartTimeMinute(){
        return this.startTimeMinute;
    }

    public void setData(String[] obj){
        mData = obj;
    }

    public void setEnalble(boolean enalble){
        this.enalble = enalble;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setData(String stationName, String direction, String startTime, String days) {
        this.mData = new String[4];

        mData[0] = stationName;
        mData[1] = direction;
        mData[2] = startTime;
        mData[3] = days;
    }

    public void setStartTimeHour(int hour){
        this.startTimeHour = hour;
    }

    public void setStartTimeMinute(int minute){
        this.startTimeHour = minute;
    }

    @Override
    public String toString() {
        return "Item{" +
                "mData=" + Arrays.toString(mData) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(mData, item.mData);

    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(mData);
    }
}

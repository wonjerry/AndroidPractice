package org.androidtown.demo2;

import java.util.Arrays;

/**
 * Created by jerry on 2017-02-16.
 */

public class Item {
    private String[] mData;
    private  boolean enalble = true;

    public Item(String[] obj) {
        this.mData = obj;
    }

    public Item(String stationName, String direction, String startTime, String duringTime) {
        this.mData = new String[4];

        mData[0] = stationName;
        mData[1] = direction;
        mData[2] = startTime;
        mData[3] = duringTime;
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

    public void setData(String[] obj){
        mData = obj;
    }

    public void setEnalble(boolean enalble){
        this.enalble = enalble;
    }

    public void setData(String stationName, String direction, String startTime, String days) {
        this.mData = new String[4];

        mData[0] = stationName;
        mData[1] = direction;
        mData[2] = startTime;
        mData[3] = days;
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

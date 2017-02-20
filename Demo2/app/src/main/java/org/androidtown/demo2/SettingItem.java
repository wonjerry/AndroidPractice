package org.androidtown.demo2;

/**
 * Created by jerry on 2017-02-21.
 */

public class SettingItem {
    private String[] mData;

    public SettingItem(String[] obj) {
        this.mData = obj;
    }

    public SettingItem(String stationName, String direction) {
        this.mData = new String[2];
        mData[0] = stationName;
        mData[1] = direction;
    }

    public String[] getData() {
        return mData;
    }

    public String getData(int index){
        if(mData == null || index >= mData.length) return null;
        return mData[index];
    }

    public void setData(String[] obj){
        mData = obj;
    }

    public void setData(String stationName, String direction) {
        this.mData = new String[2];
        mData[0] = stationName;
        mData[1] = direction;
    }
}

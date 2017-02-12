package org.androidtown.listviewtest;

import android.graphics.drawable.Drawable;

/**
 * Created by jerry on 2017-02-12.
 */

public class IconTextItem {
    private Drawable mIcon;
    private String[] mData;

    public IconTextItem(Drawable icon, String[] obj){
        mIcon = icon;
        mData = obj;
    }

    public IconTextItem(Drawable icon, String obj01, String obj02, String obj03){
        mIcon = icon;

        mData = new String[3];
        mData[0] = obj01;
        mData[1] = obj02;
        mData[2] = obj03;
    }

    public String[] getData(){
        return mData;
    }

    public String getData(int index){
        if(mData == null || index >= mData.length) return null;

        return mData[index];
    }

    public void setData(String[] obj){
        mData = obj;
    }

    public void setIcon(Drawable mIcon) {
        this.mIcon = mIcon;
    }

    public Drawable getIcon() {
        return mIcon;
    }
}

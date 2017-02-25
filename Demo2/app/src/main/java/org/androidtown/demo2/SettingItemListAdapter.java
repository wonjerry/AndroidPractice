package org.androidtown.demo2;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by jerry on 2017-02-21.
 */

public class SettingItemListAdapter extends BaseAdapter {

    private Context mConText;
    private ArrayList<SettingItem> mItems = new ArrayList<SettingItem>();

    public SettingItemListAdapter(Context mConText) {
        this.mConText = mConText;
    }

    public void addItem(SettingItem item){
        mItems.add(item);
    }
    public void cleanItem(){
        mItems.clear();
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public SettingItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SettingItemView settingItemView;

        //성능을 향상시키기 위해서 아래 주석부분을 해야하긴 하는데, 이렇게하면 enable 버튼이 item의 상태와 관계없이 자기맘대로된다.
        //if(convertView == null) itemView = new ItemView(mConText, mItems.get(position));
        //else itemView = (ItemView) convertView;

        settingItemView = new SettingItemView(mConText, mItems.get(position));

        settingItemView.setText(0,mItems.get(position).getData(0));
        settingItemView.setText(1,mItems.get(position).getData(1)+" 방면");

        return settingItemView;
    }
}

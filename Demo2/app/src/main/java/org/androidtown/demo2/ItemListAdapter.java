package org.androidtown.demo2;

import android.content.Context;
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
    public void addItem(Item item){
        mItems.add(item);
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

        itemView.setEnableBtn(mItems.get(position).getEnable());
        itemView.setText(0,mItems.get(position).getData(0));
        itemView.setText(1,mItems.get(position).getData(1));
        itemView.setText(2,mItems.get(position).getData(2));
        itemView.setText(3,mItems.get(position).getData(3));

        return itemView;
    }
}

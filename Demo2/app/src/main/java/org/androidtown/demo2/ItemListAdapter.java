package org.androidtown.demo2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;

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


        if(convertView == null) itemView = new ItemView(mConText, mItems.get(position));
        else itemView = (ItemView) convertView;

        itemView.setEnable(mItems.get(position).getEnable());
        itemView.setText(0,mItems.get(position).getData(0));
        itemView.setText(1,mItems.get(position).getData(1));
        itemView.setText(2,mItems.get(position).getData(2));
        itemView.setText(3,mItems.get(position).getData(3));

        return itemView;
    }
}

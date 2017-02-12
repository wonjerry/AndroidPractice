package org.androidtown.listviewtest;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jerry on 2017-02-12.
 */

public class IconTextListAdapter extends BaseAdapter {
    private Context mConText;
    private List<IconTextItem> mItems = new ArrayList<IconTextItem>();

    public IconTextListAdapter(Context mConText) {
        this.mConText = mConText;
    }

    public void addItem(IconTextItem item){
        mItems.add(item);
    }

    @Override
    public IconTextItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    public View getView(int positon, View convertView, ViewGroup parent){
        IconTextView itemView;

        if(convertView == null) itemView = new IconTextView(mConText, mItems.get(positon));
        else itemView = (IconTextView) convertView;

        itemView.setIcon(mItems.get(positon).getIcon());
        itemView.setText(0,mItems.get(positon).getData(0));
        itemView.setText(1,mItems.get(positon).getData(1));
        itemView.setText(2,mItems.get(positon).getData(2));

        return itemView;
    }
}

package org.androidtown.listviewtest;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by jerry on 2017-02-12.
 */

public class IconTextView extends LinearLayout {

    ImageView mIcon;
    TextView mText01;
    TextView mText02;
    TextView mText03;


    public IconTextView(Context context, IconTextItem aItem){
        super(context);

        //메모리 객체화
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.listitem,this,true);

        //이미지뷰 객체참조
        mIcon = (ImageView) findViewById(R.id.iconItem);
        mIcon.setImageDrawable(aItem.getIcon());

        //텍스트뷰 객체참조
        mText01 = (TextView) findViewById(R.id.dataItem01);
        mText01.setText(aItem.getData(0));

        mText02 = (TextView) findViewById(R.id.dataItem02);
        mText02.setText(aItem.getData(1));

        mText03 = (TextView) findViewById(R.id.dataItem03);
        mText03.setText(aItem.getData(2));
    }

    public void setText(int index, String data){
        if(index == 0){
            mText01.setText(data);
        }else if(index == 1){
            mText02.setText(data);
        }else if(index == 2){
            mText03.setText(data);
        }else{
            throw new IllegalArgumentException();
        }
    }

    public void setIcon(Drawable icon){
        mIcon.setImageDrawable(icon);
    }
}

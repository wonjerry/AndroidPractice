package org.androidtown.linearparc;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView topImage;
    ImageView bottomImage;
    int upValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topImage = (ImageView) findViewById(R.id.imageView1);
        bottomImage = (ImageView) findViewById(R.id.imageView4);
        upValue = 1;
    }

    public void onUpButtonClicked(View v){
        changeImage();
    }

    public void onDownButtonClicked(View v){
        changeImage();
    }

    private void changeImage(){
        if(upValue == 0){
            Drawable temp = topImage.getDrawable();
            topImage.setImageDrawable(bottomImage.getDrawable());
            bottomImage.setImageDrawable(temp);
            upValue = 1;
        }else if(upValue == 1){
            Drawable temp = bottomImage.getDrawable();
            bottomImage.setImageDrawable(topImage.getDrawable());
            topImage.setImageDrawable(temp);
            upValue = 0;
        }
    }


}

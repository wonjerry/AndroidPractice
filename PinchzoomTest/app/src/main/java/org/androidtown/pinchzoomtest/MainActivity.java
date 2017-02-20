package org.androidtown.pinchzoomtest;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

public class MainActivity extends AppCompatActivity {

    PhotoViewAttacher mAttecher;
    ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.imageView);
        Drawable bitmap = (BitmapDrawable) getResources().getDrawable(R.drawable.seoulsubwaymap);
        //Bitmap.createScaledBitmap(bitmap, bitmap.getMinimumWidth(), bitmap.getIntrinsicHeight(), true)
        mImageView.setImageDrawable(bitmap);

        mAttecher = new PhotoViewAttacher(mImageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAttecher.update();
            }
        });
    }
}

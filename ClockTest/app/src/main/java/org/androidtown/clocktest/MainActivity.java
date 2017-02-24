package org.androidtown.clocktest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker2);
        NumberPicker numberPicker = (NumberPicker) findViewById(R.id.numberPicker);
        timePicker.setFocusable(false);
        numberPicker.setFocusable(false);
    }
}

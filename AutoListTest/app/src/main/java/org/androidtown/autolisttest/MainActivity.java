package org.androidtown.autolisttest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] stations = getResources().getStringArray(R.array.stationNames);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,stations);
        AutoCompleteTextView textView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        textView.setAdapter(adapter);
    }
}

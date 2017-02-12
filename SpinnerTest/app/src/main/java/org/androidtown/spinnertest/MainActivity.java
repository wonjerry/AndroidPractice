package org.androidtown.spinnertest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    String[] items = {"mike", "angel","crow","john","ginnie","sally","cohen","rice"};
    Spinner spinner;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner) findViewById(R.id.spinner);
        textView = (TextView) findViewById(R.id.textView1);

        ArrayAdapter<String> adpater = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,items);
        adpater.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adpater);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        textView.setText(items[position]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textView.setText("");
    }
}

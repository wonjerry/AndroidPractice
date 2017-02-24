package org.androidtown.garamtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityAlarmedTimeShow extends Activity {
	TextView textViewAlarmedTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarmed_time_show_layout);
		textViewAlarmedTime = (TextView)findViewById(R.id.textViewAlarmedTime);
		Intent intent = getIntent();
		String time = intent.getStringExtra("time");
		String data = intent.getStringExtra("data");
		int reqCode = intent.getIntExtra("reqCode", 0);
		textViewAlarmedTime.setText(time+"\n"+data+"\n"+reqCode);
	}
}

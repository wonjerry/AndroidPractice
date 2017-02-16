package org.androidtown.thread;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    BackGroundTask task;
    int value;
    ProgressBar progress;
    Button executeBtn;
    Button cancelBtn;
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        progress = (ProgressBar) findViewById(R.id.progressBar1);
        executeBtn = (Button) findViewById(R.id.executeBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);
        textView = (TextView) findViewById(R.id.textView);

        executeBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                task = new BackGroundTask();
                task.execute(100);
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                task.cancel(true);
            }
        });
    }

    class BackGroundTask extends AsyncTask<Integer, Integer, Integer>{
        @Override
        protected void onPreExecute() {
            value = 0;
            progress.setProgress(value);
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            while(isCancelled() == false){
                value++;
                if(value >= 100){
                    break;
                }else{
                    publishProgress(value);//Update 메소드 호출
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
            return value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progress.setProgress(values[0].intValue());
            textView.setText("Current value : "+values[0].toString());
        }

        @Override
        protected void onPostExecute(Integer integer) {
            progress.setProgress(0);
            textView.setText("finished");
        }

        @Override
        protected void onCancelled() {
            progress.setProgress(0);
            textView.setText("Cancelled");
        }
    }
}

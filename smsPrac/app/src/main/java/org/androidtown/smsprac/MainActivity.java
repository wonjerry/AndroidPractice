package org.androidtown.smsprac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText smsText;
    TextView cntText;
    Button send , exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        smsText = (EditText) findViewById(R.id.editText);
        cntText = (TextView) findViewById(R.id.textView);
        smsText.addTextChangedListener(watcher);

        send = (Button) findViewById(R.id.button1);
        exit = (Button) findViewById(R.id.button2);
    }

    public void onSendButtonClicked(View v){
        Toast.makeText(getApplicationContext(),smsText.getText(),Toast.LENGTH_LONG).show();
        smsText.setText("");
    }

    public void onExitButtonClicked(View v){
        this.finish();
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            cntText.setText(smsText.getText().length() + "/ 80 바이트");
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


}

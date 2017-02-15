package org.androidtown.databasetest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase db;

    Button createDatabaseBtn;
    Button createTableBtn;
    EditText databaseNameInput;
    EditText tableNameInput;
    TextView status;
    String databaseName;
    String tableName;
    boolean databaseCreated = false;
    boolean tableCreated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createDatabaseBtn = (Button)findViewById(R.id.createDatabaseBtn);
        createTableBtn = (Button) findViewById(R.id.createTableBtn);
        databaseNameInput = (EditText) findViewById(R.id.databaseNameInput);
        tableNameInput = (EditText) findViewById((R.id.tableNameInput));
        status = (TextView) findViewById(R.id.status);

        createDatabaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseName = databaseNameInput.getText().toString();
                createDatabase(databaseName);
            }
        });

        createTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableName = tableNameInput.getText().toString();
                createTable(tableName);
                int count = insertRecord(tableName);
            }
        });
    }

    protected void createDatabase(String name){

        println("creating datebase [ " +
                name +
                "].");
        try{
            db = openOrCreateDatabase(name, MODE_ENABLE_WRITE_AHEAD_LOGGING, null);
            databaseCreated = true;
            println("database is created");
        }catch (Exception e){
            println("database is not created");
        }
    }

    protected void createTable(String name){
        println("creating table [ " +name+ "].");
        try{
            db.execSQL("create table " + name + " ("
            + " _id integer PRIMARY KEY autoincrement , " +
                    " name text, " +
                    " age integer, " +
                    " phone text);");
            tableCreated = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    protected int insertRecord(String name){
        int count = 3;
        db.execSQL("insert into " + name + " (name,age,phone) values ('john',20,'010-7788-1234');");
        return count;
    }

    private void println(String str){
        status.setText(status.getText() + str + '\n');
    }
}
